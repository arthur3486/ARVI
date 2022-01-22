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

import com.arthurivanets.arvi.util.misc.Preconditions;
import com.google.android.exoplayer2.SimpleExoPlayer;

import androidx.annotation.NonNull;

/**
 * A concrete implementation of the {@link VolumeController} designed to manage the
 * volume-related settings of the {@link SimpleExoPlayer}.
 */
public final class DefaultVolumeController implements VolumeController {


    private final SimpleExoPlayer player;

    private float oldVolume;




    public DefaultVolumeController(@NonNull SimpleExoPlayer player) {
        this.player = Preconditions.checkNonNull(player);
        this.oldVolume = getVolume();
    }




    @Override
    public final void mute() {
        if(!isMuted()) {
            this.oldVolume = getVolume();

            setVolume(0f);
        }
    }




    @Override
    public final void unmute() {
        if(isMuted()) {
            setVolume(this.oldVolume);
        }
    }




    @Override
    public final void setVolume(float audioVolume) {
        this.player.setVolume(audioVolume);
    }




    @Override
    public final float getVolume() {
        return this.player.getVolume();
    }




    @Override
    public final void setMuted(boolean isMuted) {
        if(isMuted) {
            mute();
        } else {
            unmute();
        }
    }




    @Override
    public final boolean isMuted() {
        return (getVolume() <= 0f);
    }




}
