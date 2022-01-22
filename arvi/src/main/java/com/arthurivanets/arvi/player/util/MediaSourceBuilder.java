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

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.arthurivanets.arvi.util.misc.ExoPlayerUtils.addEventListenerIfNonNull;
import static com.google.android.exoplayer2.util.Util.inferContentType;

/**
 * A base contract to be used by the concrete implementations of the {@link MediaSource} builders.
 * Concrete implementations of the {@link MediaSourceBuilder} are responsible for the creation of the
 * appropriate {@link MediaSource}s.
 */
public interface MediaSourceBuilder {

    /**
     * The default implementation of the {@link MediaSourceBuilder} used to build
     * simple (non-looping) {@link MediaSource}s.
     */
    MediaSourceBuilder DEFAULT = new MediaSourceBuilder() {

        @NonNull @Override
        public MediaSource buildMediaSource(@NonNull Context context,
                                            @NonNull Uri fileUri,
                                            @Nullable String fileExtension,
                                            @Nullable Handler handler,
                                            @NonNull DataSource.Factory manifestDataSourceFactory,
                                            @NonNull DataSource.Factory mediaDataSourceFactory,
                                            @Nullable MediaSourceEventListener eventListener) {
            @C.ContentType final int type = TextUtils.isEmpty(fileExtension) ? inferContentType(fileUri) : inferContentType("." + fileExtension);

            switch(type) {

                case C.TYPE_SS:
                    final SsMediaSource ssMediaSource = new SsMediaSource.Factory(
                        new DefaultSsChunkSource.Factory(mediaDataSourceFactory),
                        manifestDataSourceFactory
                    ).createMediaSource(fileUri);

                    addEventListenerIfNonNull(
                        ssMediaSource,
                        handler,
                        eventListener
                    );

                    return ssMediaSource;

                case C.TYPE_DASH:
                    final DashMediaSource dashMediaSource = new DashMediaSource.Factory(
                        new DefaultDashChunkSource.Factory(mediaDataSourceFactory),
                        manifestDataSourceFactory
                    ).createMediaSource(fileUri);

                    addEventListenerIfNonNull(
                        dashMediaSource,
                        handler,
                        eventListener
                    );

                    return dashMediaSource;

                case C.TYPE_HLS:
                    final HlsMediaSource hlsMediaSource = new HlsMediaSource.Factory(mediaDataSourceFactory).createMediaSource(fileUri);

                    addEventListenerIfNonNull(
                        hlsMediaSource,
                        handler,
                        eventListener
                    );

                    return hlsMediaSource;

                case C.TYPE_OTHER:
                    final ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource.Factory(mediaDataSourceFactory).createMediaSource(fileUri);

                    addEventListenerIfNonNull(
                        extractorMediaSource,
                        handler,
                        eventListener
                    );

                    return extractorMediaSource;

                default:
                    throw new IllegalStateException("Unsupported type: " + type);

            }
        }

    };

    /**
     * The default implementation of the {@link MediaSourceBuilder} used to build
     * simple (looping) {@link MediaSource}s.
     */
    MediaSourceBuilder LOOPING = new MediaSourceBuilder() {

        @NonNull @Override
        public MediaSource buildMediaSource(@NonNull Context context,
                                            @NonNull Uri fileUri,
                                            @Nullable String fileExtension,
                                            @Nullable Handler handler,
                                            @NonNull DataSource.Factory manifestDataSourceFactory,
                                            @NonNull DataSource.Factory mediaDataSourceFactory,
                                            @Nullable MediaSourceEventListener eventListener) {
            return new LoopingMediaSource(DEFAULT.buildMediaSource(
                context,
                fileUri,
                fileExtension,
                handler,
                manifestDataSourceFactory,
                mediaDataSourceFactory,
                eventListener
            ));
        }

    };


    /**
     * Builds the {@link MediaSource} for the specified configuration.
     *
     * @param context
     * @param fileUri
     * @param fileExtension
     * @param handler
     * @param manifestDataSourceFactory
     * @param mediaDataSourceFactory
     * @param eventListener
     * @return the created {@link MediaSource}
     */
    @NonNull
    MediaSource buildMediaSource(@NonNull Context context,
                                 @NonNull Uri fileUri,
                                 @Nullable String fileExtension,
                                 @Nullable Handler handler,
                                 @NonNull DataSource.Factory manifestDataSourceFactory,
                                 @NonNull DataSource.Factory mediaDataSourceFactory,
                                 @Nullable MediaSourceEventListener eventListener);


}
