/*
 * Copyright 2017 Arthur Ivanets, arthur.ivanets.work@gmail.com
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

package com.arthurivanets.arvi.player.util;

import android.os.Handler;

import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A utility used for bandwidth metering, as well as observation of the data transfer-related events.
 */
public final class BaseMeter<T extends BandwidthMeter, S extends TransferListener> implements BandwidthMeter, TransferListener {


    @NonNull final T bandwidthMeter;
    @NonNull final S transferListener;




    @SuppressWarnings("WeakerAccess")
    public BaseMeter(@NonNull T bandwidthMeter, @NonNull S transferListener) {
        this.bandwidthMeter = bandwidthMeter;
        this.transferListener = transferListener;
    }




    @Override
    public final void addEventListener(Handler eventHandler, EventListener eventListener) {
        this.bandwidthMeter.addEventListener(eventHandler, eventListener);
    }




    @Override
    public final void removeEventListener(EventListener eventListener) {
        this.bandwidthMeter.removeEventListener(eventListener);
    }




    @Nullable
    @Override
    public final TransferListener getTransferListener() {
        return this.bandwidthMeter.getTransferListener();
    }




    @Override
    public final long getBitrateEstimate() {
        return this.bandwidthMeter.getBitrateEstimate();
    }




    @Override
    public final void onTransferInitializing(DataSource dataSource,
                                             DataSpec dataSpec,
                                             boolean isNetwork) {
        this.transferListener.onTransferInitializing(
            dataSource,
            dataSpec,
            isNetwork
        );
    }




    @Override
    public final void onTransferStart(DataSource dataSource,
                                      DataSpec dataSpec,
                                      boolean isNetwork) {
        this.transferListener.onTransferStart(
            dataSource,
            dataSpec,
            isNetwork
        );
    }




    @Override
    public final void onBytesTransferred(DataSource dataSource,
                                         DataSpec dataSpec,
                                         boolean isNetwork,
                                         int bytesTransferred) {
        this.transferListener.onBytesTransferred(
            dataSource,
            dataSpec,
            isNetwork,
            bytesTransferred
        );
    }




    @Override
    public final void onTransferEnd(DataSource dataSource,
                                    DataSpec dataSpec,
                                    boolean isNetwork) {
        this.transferListener.onTransferEnd(
            dataSource,
            dataSpec,
            isNetwork
        );
    }




}
