package com.podio.sdk.domain;

import java.util.List;

import com.podio.sdk.Filter;
import com.podio.sdk.Provider;
import com.podio.sdk.ProviderListener;
import com.podio.sdk.RestClient;
import com.podio.sdk.client.RestRequest;
import com.podio.sdk.internal.request.RestOperation;
import com.podio.sdk.internal.request.ResultListener;

public class ItemProvider<T> implements Provider<T> {

    private final ResultListener resultListener = new ResultListener() {
        @Override
        public void onFailure(Object ticket, String message) {
            if (providerListener != null) {
                Filter filter = (Filter) ticket;
                providerListener.onRequestFailed(filter, message);
            }
        }

        @Override
        public void onSuccess(Object ticket, List<?> items) {
            if (providerListener != null) {
                Filter filter = (Filter) ticket;
                providerListener.onRequestCompleted(filter, items);
            }
        }
    };

    private ProviderListener providerListener;
    private RestClient client;

    @Override
    public void changeItem(Filter filter, T item) {
        if (client != null) {
            RestRequest restRequest = buildRestRequest(RestOperation.PUT, filter, item);
            client.perform(restRequest);
        }
    }

    @Override
    public void deleteItems(Filter filter) {
        if (client != null && filter != null) {
            RestRequest restRequest = buildRestRequest(RestOperation.DELETE, filter);
            client.perform(restRequest);
        }
    }

    @Override
    public void fetchItems(Filter filter) {
        if (client != null && filter != null) {
            RestRequest restRequest = buildRestRequest(RestOperation.GET, filter);
            client.perform(restRequest);
        }
    }

    @Override
    public void pushItem(T item) {
        if (client != null) {
            RestRequest restRequest = buildRestRequest(RestOperation.POST, item);
            client.perform(restRequest);
        }
    }

    @Override
    public void setProviderListener(ProviderListener providerListener) {
        this.providerListener = providerListener;
    }

    @Override
    public void setRestClient(RestClient client) {
        this.client = client;
    }

    private RestRequest buildRestRequest(RestOperation operation, Filter filter) {
        return buildRestRequest(operation, filter, null);
    }

    private RestRequest buildRestRequest(RestOperation operation, T content) {
        return buildRestRequest(operation, null, content);
    }

    private RestRequest buildRestRequest(RestOperation operation, Filter filter, T content) {
        Class<?> classOfContent = content != null ? content.getClass() : null;

        RestRequest request = new RestRequest() //
                .setContent(content) //
                .setItemType(classOfContent) //
                .setOperation(operation) //
                .setResultListener(resultListener) //
                .setFilter(filter);

        return request;
    }
}
