/*
 * Copyright 2017 Arthur Ivanets, arthur.ivanets.l@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arthurivanets.arvi.player.datasource;

import com.arthurivanets.arvi.util.misc.Preconditions;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;

import androidx.annotation.NonNull;

/**
 * An implementation of the {@link HttpDataSource.Factory} with the support for the
 * Request Authorization using a dedicated {@link RequestAuthorizer}.
 */
public class ArviHttpDataSourceFactory extends HttpDataSource.BaseFactory {


    private final String userAgent;

    private final HttpDataSource.RequestProperties requestProperties;

    private final boolean allowCrossProtocolRedirects;

    private final TransferListener listener;

    private int connectTimeoutMillis;
    private int readTimeoutMillis;

    private RequestAuthorizer requestAuthorizer;




    /**
     * Constructs an ArviHttpDataSourceFactory. Sets {@link DefaultHttpDataSource#DEFAULT_CONNECT_TIMEOUT_MILLIS} as the connection timeout,
     * {@link DefaultHttpDataSource#DEFAULT_READ_TIMEOUT_MILLIS} as the read timeout and disables cross-protocol redirects.
     *
     * @param userAgent The User-Agent string that should be used.
     */
    public ArviHttpDataSourceFactory(String userAgent) {
        this(userAgent, null);
    }




    /**
     * Constructs an ArviHttpDataSourceFactory. Sets {@link DefaultHttpDataSource#DEFAULT_CONNECT_TIMEOUT_MILLIS} as the connection timeout,
     * {@link DefaultHttpDataSource#DEFAULT_READ_TIMEOUT_MILLIS} as the read timeout and disables cross-protocol redirects.
     *
     * @param userAgent The User-Agent string that should be used.
     * @param listener An optional listener.
     * @see #DefaultHttpDataSourceFactory(String, TransferListener, int, int, boolean)
     */
    public ArviHttpDataSourceFactory(String userAgent, TransferListener listener) {
        this(
            userAgent,
            listener,
            null,
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
            false
        );
    }




    /**
     * @param userAgent The User-Agent string that should be used.
     * @param listener An optional listener.
     * @param connectTimeoutMillis The connection timeout that should be used when requesting remote
     *        data, in milliseconds. A timeout of zero is interpreted as an infinite timeout.
     * @param readTimeoutMillis The read timeout that should be used when requesting remote data, in
     *        milliseconds. A timeout of zero is interpreted as an infinite timeout.
     * @param allowCrossProtocolRedirects Whether cross-protocol redirects (i.e. redirects from HTTP
     *        to HTTPS and vice versa) are enabled.
     */
    public ArviHttpDataSourceFactory(String userAgent,
                                     TransferListener listener,
                                     RequestAuthorizer requestAuthorizer,
                                     int connectTimeoutMillis,
                                     int readTimeoutMillis,
                                     boolean allowCrossProtocolRedirects) {
        this.userAgent = userAgent;
        this.listener = listener;
        this.requestAuthorizer = requestAuthorizer;
        this.connectTimeoutMillis = connectTimeoutMillis;
        this.readTimeoutMillis = readTimeoutMillis;
        this.allowCrossProtocolRedirects = allowCrossProtocolRedirects;
        this.requestProperties = new HttpDataSource.RequestProperties();
    }




    /**
     * Sets the Connect Timeout (In Milliseconds) for the Http Data Requests created by this factory.
     *
     * @param connectTimeoutInMillis
     */
    public final void setConnectTimeout(int connectTimeoutInMillis) {
        Preconditions.isTrue("The Connect Timeout value connot be negative.", (connectTimeoutInMillis >= 0));

        this.connectTimeoutMillis = connectTimeoutInMillis;
    }




    /**
     * Sets the Read Timeout (In Milliseconds) for the Http Data Requests created by this factory.
     *
     * @param readTimeoutInMillis
     */
    public final void setReadTimeout(int readTimeoutInMillis) {
        Preconditions.isTrue("The Read Timeout value connot be negative.", (readTimeoutInMillis >= 0));

        this.readTimeoutMillis = readTimeoutInMillis;
    }




    /**
     * Adds a Request Property (HTTP Header) to be used during the performance of the request.
     *
     * @param key
     * @param value
     */
    public final void addRequestProperty(@NonNull String key, @NonNull String value) {
        Preconditions.nonEmpty(key);
        Preconditions.nonEmpty(value);

        this.requestProperties.set(key, value);
    }




    /**
     * Sets the {@link RequestAuthorizer} to be used for the authorization of the requests performed
     * by the Data Sources created by this Factory.
     *
     * @param requestAuthorizer
     */
    public final void setRequestAuthorizer(RequestAuthorizer requestAuthorizer) {
        this.requestAuthorizer = requestAuthorizer;
    }




    @Override
    protected DefaultHttpDataSource createDataSourceInternal(HttpDataSource.RequestProperties defaultRequestProperties) {
        final HttpDataSource.RequestProperties finalRequestProperties = new HttpDataSource.RequestProperties();
        finalRequestProperties.set(this.requestProperties.getSnapshot());
        finalRequestProperties.set(defaultRequestProperties.getSnapshot());

        final DefaultHttpDataSource dataSource = new ArviHttpDataSource(
            this.userAgent,
            this.connectTimeoutMillis,
            this.readTimeoutMillis,
            this.allowCrossProtocolRedirects,
            finalRequestProperties
        ).setRequestAuthorizer(this.requestAuthorizer);

        if(this.listener != null) {
            dataSource.addTransferListener(this.listener);
        }

        return dataSource;
    }




}
