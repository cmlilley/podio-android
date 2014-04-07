package com.podio.sdk.client.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.podio.sdk.internal.utils.Utils;

public final class SQLiteClientDelegate extends SQLiteOpenHelper implements DatabaseClientDelegate {

    private static final class SQLiteConstraint {
        private final String constraint;
        private final String[] arguments;

        private SQLiteConstraint(String constraint, String[] arguments) {
            this.constraint = constraint;
            this.arguments = arguments;
        }
    }

    public SQLiteClientDelegate(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public Cursor delete(Uri uri) {
        int count = 0;

        if (Utils.notEmpty(uri)) {
            SQLiteDatabase database = open();

            if (database != null) {
                String table = uri.getLastPathSegment();
                SQLiteConstraint sqliteConstraint = buildSQLiteConstraint(uri);

                count = database.delete(table, sqliteConstraint.constraint,
                        sqliteConstraint.arguments);
            }
        }

        String countString = Integer.toString(count, 10);
        Cursor result = buildResultCursor("count", countString);

        return result;
    }

    @Override
    public void initialize(SQLiteDatabase database) {
        if (database != null) {
            try {
                String query = new StringBuilder("CREATE TABLE app (")
                        .append(" app_id INTEGER PRIMARY KEY NOT NULL UNIQUE ON CONFLICT REPLACE,") //
                        .append(" space_id INTEGER NOT NULL DEFAULT (0), ") //
                        .append(" json_content TEXT NOT NULL DEFAULT ('{}'))") //
                        .toString();
                database.beginTransaction();
                database.execSQL(query);
                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
            }
        }
    }

    @Override
    public Cursor insert(Uri uri, ContentValues values) {
        long id = 0L;

        if (Utils.notEmpty(uri)) {
            SQLiteDatabase database = open();

            if (database != null) {
                String table = uri.getLastPathSegment();
                id = database.insertWithOnConflict(table, null, values,
                        SQLiteDatabase.CONFLICT_IGNORE);
            }
        }

        String idString = Long.toString(id, 10);
        Cursor result = buildResultCursor("id", idString);

        return result;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        clear(database);
        initialize(database);
    }

    @Override
    public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        clear(database);
        initialize(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        clear(database);
        initialize(database);
    }

    @Override
    public Cursor query(Uri uri) {
        Cursor result = null;

        if (Utils.notEmpty(uri)) {
            SQLiteDatabase database = open();

            if (database != null) {
                String table = uri.getLastPathSegment();
                SQLiteConstraint sqliteConstraint = buildSQLiteConstraint(uri);

                result = database.query(table, null, sqliteConstraint.constraint,
                        sqliteConstraint.arguments, null, null, null);
            }
        }

        if (result == null) {
            result = buildEmptyResultCursor();
        }

        return result;
    }

    @Override
    public Cursor update(Uri uri, ContentValues values) {
        int count = 0;

        if (Utils.notEmpty(uri)) {
            SQLiteDatabase database = open();

            if (database != null) {
                String table = uri.getLastPathSegment();
                SQLiteConstraint sqliteConstraint = buildSQLiteConstraint(uri);

                count = database.updateWithOnConflict(table, values, sqliteConstraint.constraint,
                        sqliteConstraint.arguments, SQLiteDatabase.CONFLICT_IGNORE);

            }
        }

        String countString = Integer.toString(count, 10);
        Cursor result = buildResultCursor("count", countString);
        return result;
    }

    private Cursor buildEmptyResultCursor() {
        MatrixCursor result = new MatrixCursor(new String[0]);
        return result;
    }

    private Cursor buildResultCursor(String name, String value) {
        MatrixCursor result = null;

        if (Utils.notEmpty(name)) {
            String[] columns = { name };
            String[] row = { value };
            result = new MatrixCursor(columns, 1);
            result.addRow(row);
        }

        return result;
    }

    private SQLiteConstraint buildSQLiteConstraint(Uri uri) {
        String constraint = null;
        String[] arguments = null;

        if (Utils.notEmpty(uri)) {
            StringBuilder constraintBuilder = new StringBuilder();
            List<String> argumentsBuilder = new ArrayList<String>();
            Set<String> fields = uri.getQueryParameterNames();

            // Iterate over all query names in the query.
            for (String field : fields) {
                List<String> values = uri.getQueryParameters(field);
                int valuesCount = values.size();
                int lastValueIndex = valuesCount - 1;

                // Build the SQL query string on the format "name=?". Multiple
                // occurrences of the same query parameter are joined with
                // boolean OR constraint. The different query parameters are
                // joined with boolean AND constraint.
                if (valuesCount > 0) {
                    constraintBuilder.append(" AND (");

                    for (int i = 0; i < valuesCount; i++) {
                        String value = values.get(i);
                        String operator = value.substring(0, 1);

                        // The first character in the field value may indicate
                        // an operator (note that we allow for escaping the
                        // first character).

                        if (">".equals(operator) || "<".equals(operator) || "=".equals(operator)) {
                            value = value.substring(1);
                        } else if ("\\".equals(operator)) {
                            value = value.substring(1);
                            operator = "=";
                        } else {
                            operator = "=";
                        }

                        constraintBuilder.append(field).append(operator).append("?");

                        if (i < lastValueIndex) {
                            constraintBuilder.append(" OR ");
                        }

                        argumentsBuilder.add(value);
                    }

                    constraintBuilder.append(")");
                }
            }

            constraint = constraintBuilder.substring(" AND ".length());
            arguments = argumentsBuilder.toArray(new String[0]);
        }

        SQLiteConstraint result = new SQLiteConstraint(constraint, arguments);
        return result;
    }

    private void clear(SQLiteDatabase database) {
        if (database != null) {
            Cursor cursor = null;

            try {
                // Drop all tables, views and triggers in the database.
                database.beginTransaction();
                String query = "SELECT type, name FROM sqlite_master WHERE type IN (?, ?, ?)";
                String[] arguments = { "table", "view", "trigger" };
                cursor = database.rawQuery(query, arguments);

                if (cursor != null && cursor.moveToFirst()) {
                    int typeColumn = cursor.getColumnIndex("type");
                    int nameColumn = cursor.getColumnIndex("name");

                    do {
                        String type = cursor.getString(typeColumn);
                        String name = cursor.getString(nameColumn);
                        database.execSQL("DROP " + type + " IF EXISTS " + name);
                    } while (cursor.moveToNext());
                }

                database.setTransactionSuccessful();
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
                database.endTransaction();
            }
        }
    }

    private SQLiteDatabase open() {
        SQLiteDatabase database;

        try {
            database = getWritableDatabase();
        } catch (SQLiteException e) {
            database = null;
        }

        return database;
    }
}
