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

package com.arthurivanets.arvi.player.util;

import android.content.Context;
import android.os.Handler;

import com.arthurivanets.arvi.util.misc.CollectionUtils;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * An implementation of the {@link DefaultRenderersFactory} that takes into account the Digital Rights Management.
 */
public class MultiDrmRendererFactory extends DefaultRenderersFactory {


    private final List<DrmSessionManager<FrameworkMediaCrypto>> drmSessionManagers;




    public MultiDrmRendererFactory(@NonNull Context context,
                                   @Nullable DrmSessionManager[] managers,
                                   @ExtensionRendererMode int extensionRendererMode) {
        super(context, extensionRendererMode);

        if((managers != null) && (managers.length > 1)) {
            this.drmSessionManagers = CollectionUtils.toListUnchecked(managers);
        } else {
            this.drmSessionManagers = new ArrayList<>();
        }
    }




    @Override
    protected void buildVideoRenderers(Context context,
                                       @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager,
                                       long allowedVideoJoiningTimeMs,
                                       Handler eventHandler,
                                       VideoRendererEventListener eventListener,
                                       int extensionRendererMode,
                                       ArrayList<Renderer> out) {
        final ArrayList<Renderer> localOut = new ArrayList<>();

        super.buildVideoRenderers(
            context,
            drmSessionManager,
            allowedVideoJoiningTimeMs,
            eventHandler,
            eventListener,
            extensionRendererMode,
            localOut
        );

        final Set<Renderer> outSet = new HashSet<>(localOut);

        if(!this.drmSessionManagers.isEmpty()) {
            localOut.clear();

            for(DrmSessionManager<FrameworkMediaCrypto> manager : this.drmSessionManagers) {
                super.buildVideoRenderers(
                    context,
                    manager,
                    allowedVideoJoiningTimeMs,
                    eventHandler,
                    eventListener,
                    EXTENSION_RENDERER_MODE_OFF,
                    localOut
                );
            }

            outSet.addAll(localOut);
        }

        out.addAll(outSet);
    }




    @Override
    protected void buildAudioRenderers(Context context,
                                       @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager,
                                       AudioProcessor[] audioProcessors,
                                       Handler eventHandler,
                                       AudioRendererEventListener eventListener,
                                       int extensionRendererMode,
                                       ArrayList<Renderer> out) {
        final ArrayList<Renderer> localOut = new ArrayList<>();

        super.buildAudioRenderers(
            context,
            drmSessionManager,
            audioProcessors,
            eventHandler,
            eventListener,
            extensionRendererMode,
            localOut
        );

        final Set<Renderer> outSet = new HashSet<>(localOut);

        if(!this.drmSessionManagers.isEmpty()) {
            localOut.clear();

            for(DrmSessionManager<FrameworkMediaCrypto> manager : this.drmSessionManagers) {
                super.buildAudioRenderers(
                    context,
                    manager,
                    audioProcessors,
                    eventHandler,
                    eventListener,
                    EXTENSION_RENDERER_MODE_OFF,
                    localOut
                );
            }

            outSet.addAll(localOut);
        }

        out.addAll(outSet);
    }




}
