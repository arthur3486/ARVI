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

package com.arthurivanets.arvi;

import com.arthurivanets.arvi.player.util.BaseMeter;
import com.arthurivanets.arvi.player.util.MediaSourceBuilder;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.cache.Cache;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.arthurivanets.arvi.util.misc.Preconditions.checkNonNull;
import static com.google.android.exoplayer2.DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;

/**
 * {@link com.arthurivanets.arvi.player.Player} configuration.
 */
public final class Config {


    @DefaultRenderersFactory.ExtensionRendererMode public final int extensionMode;

    @NonNull public final BaseMeter<?, ?> meter;
    @NonNull public final LoadControl loadControl;
    @NonNull public final MediaSourceBuilder mediaSourceBuilder;
    @NonNull public final DrmSessionManager[] drmSessionManagers;

    @Nullable public final Cache cache;
    @Nullable public final DataSource.Factory dataSourceFactory;




    private Config(Builder builder) {
        this.extensionMode = builder.extensionMode;
        this.meter = builder.meter;
        this.loadControl = builder.loadControl;
        this.mediaSourceBuilder = builder.mediaSourceBuilder;
        this.drmSessionManagers = builder.drmSessionManagers;
        this.cache = builder.cache;
        this.dataSourceFactory = builder.dataSourceFactory;
    }




    /**
     * Determines if the {@link Cache} is set.
     */
    public final boolean hasCache() {
        return (this.cache != null);
    }




    /**
     * Determines if the {@link DataSource.Factory} is set.
     */
    public final boolean hasDataSourceFactory() {
        return (this.dataSourceFactory != null);
    }




    /**
     * Determines if the {@link DrmSessionManager}s are set.
     */
    public final boolean hasDrmSessionManagers() {
        return (this.drmSessionManagers.length > 0);
    }




    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 17;
        result = ((prime * result) + this.extensionMode);
        result = ((prime * result) + this.meter.hashCode());
        result = ((prime * result) + this.loadControl.hashCode());
        result = ((prime * result) + this.mediaSourceBuilder.hashCode());
        result = ((prime * result) + (hasCache() ? this.cache.hashCode() : 0));
        result = ((prime * result) + (hasDataSourceFactory() ? this.dataSourceFactory.hashCode() : 0));
        result = ((prime * result) + (hasDrmSessionManagers() ? Arrays.hashCode(this.drmSessionManagers) : 0));

        return result;
    }




    @Override
    public final boolean equals(Object obj) {
        return ((obj instanceof Config) && (obj.hashCode() == hashCode()));
    }




    public static final class Builder {

        private final DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        private int extensionMode;

        private BaseMeter<?, ?> meter;
        private LoadControl loadControl;
        private MediaSourceBuilder mediaSourceBuilder;
        private DrmSessionManager[] drmSessionManagers;

        private Cache cache;
        private DataSource.Factory dataSourceFactory;


        public Builder() {
            this.extensionMode = EXTENSION_RENDERER_MODE_OFF;
            this.meter = new BaseMeter<>(bandwidthMeter, bandwidthMeter);
            this.loadControl = new DefaultLoadControl();
            this.mediaSourceBuilder = MediaSourceBuilder.DEFAULT;
            this.drmSessionManagers = new DrmSessionManager[0];
            this.cache = null;
            this.dataSourceFactory = null;
        }


        public Builder extensionMode(@DefaultRenderersFactory.ExtensionRendererMode int extensionMode) {
            this.extensionMode = extensionMode;
            return this;
        }


        public Builder meter(@NonNull BaseMeter<?, ?> meter) {
            this.meter = checkNonNull(meter);
            return this;
        }


        public Builder loadControl(@NonNull LoadControl loadControl) {
            this.loadControl = checkNonNull(loadControl);
            return this;
        }


        public Builder mediaSourceBuilder(@NonNull MediaSourceBuilder mediaSourceBuilder) {
            this.mediaSourceBuilder = checkNonNull(mediaSourceBuilder);
            return this;
        }


        public Builder looping(boolean isLooping) {
            return mediaSourceBuilder(isLooping ? MediaSourceBuilder.LOOPING : MediaSourceBuilder.DEFAULT);
        }


        public Builder drmSessionManagers(@NonNull DrmSessionManager[] drmSessionManagers) {
            this.drmSessionManagers = checkNonNull(drmSessionManagers);
            return this;
        }


        public Builder cache(@Nullable Cache cache) {
            this.cache = cache;
            return this;
        }


        public Builder dataSourceFactory(@Nullable DataSource.Factory dataSourceFactory) {
            this.dataSourceFactory = dataSourceFactory;
            return this;
        }


        public Config build() {
            return new Config(this);
        }


    }




}
