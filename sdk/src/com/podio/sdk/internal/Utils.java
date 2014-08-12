/*
 *  Copyright (C) 2014 Copyright Citrix Systems, Inc.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of 
 *  this software and associated documentation files (the "Software"), to deal in 
 *  the Software without restriction, including without limitation the rights to 
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies 
 *  of the Software, and to permit persons to whom the Software is furnished to 
 *  do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all 
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 *  SOFTWARE.
 */

package com.podio.sdk.internal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.net.Uri;

public class Utils {

    @SuppressLint("SimpleDateFormat")
    public static String formatDateTime(Date dateTime) {
        try {
            return getSimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTime);
        } catch (NullPointerException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean getNative(Boolean object, boolean fallback) {
        return object != null ? object.booleanValue() : fallback;
    }

    public static double getNative(Double object, double fallback) {
        return object != null ? object.doubleValue() : fallback;
    }

    public static int getNative(Integer object, int fallback) {
        return object != null ? object.intValue() : fallback;
    }

    public static long getNative(Long object, long fallback) {
        return object != null ? object.longValue() : fallback;
    }

    public static boolean isAnyEmpty(String... strings) {
        boolean isEmpty = isEmpty(strings);

        for (int i = 0; !isEmpty && i < strings.length; i++) {
            isEmpty = isEmpty(strings[i]);
        }

        return isEmpty;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isEmpty(Uri uri) {
        return uri == null || uri.equals(Uri.EMPTY);
    }

    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public static boolean notAnyEmpty(String... strings) {
        return !isAnyEmpty(strings);
    }

    public static boolean notEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean notEmpty(Uri uri) {
        return !isEmpty(uri);
    }

    public static boolean notEmpty(byte[] array) {
        return !isEmpty(array);
    }

    public static boolean notEmpty(int[] array) {
        return !isEmpty(array);
    }

    public static boolean notEmpty(long[] array) {
        return !isEmpty(array);
    }

    public static boolean notEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean notEmpty(Object[] array) {
        return !isEmpty(array);
    }

    public static boolean notEmpty(String string) {
        return !isEmpty(string);
    }

    public static Date parseDateTime(String dateTime) {
        try {
            return getSimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
        } catch (NullPointerException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDate(String date) {
        try {
            return getSimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (NullPointerException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseTime(String time) {
        try {
            return getSimpleDateFormat("HH:mm:ss").parse(time);
        } catch (NullPointerException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }
    }

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat getSimpleDateFormat(String pattern) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat;
    }
}
