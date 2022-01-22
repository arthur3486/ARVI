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

import androidx.annotation.FloatRange;

/**
 * A base contract to be used by the concrete implementations to provide
 * the proper {@link com.arthurivanets.arvi.player.Player} volume control functionality.
 */
public interface VolumeController {

    /**
     * Mutes the audio of the media.
     */
    void mute();

    /**
     * Unmutes the audio of the media.
     */
    void unmute();

    /**
     * Sets the media audio volume ratio. (a value between 0.0 and 1.0, where 0.0 is 0% and 1.0 is 100%)
     *
     * @param audioVolume the audio volume ratio
     */
    void setVolume(@FloatRange(from = 0.0, to = 1.0) float audioVolume);

    /**
     * Retrieves the media audio volume ratio.
     *
     * @return the audio volume ratio (a value between 0.0 and 1.0)
     */
    @FloatRange(from = 0.0, to = 1.0)
    float getVolume();

    /**
     * Sets the media audio muted state.
     *
     * @param isMuted whether to mute the media audio or not
     */
    void setMuted(boolean isMuted);

    /**
     * Retrieves the media audio muted state.
     *
     * @return <strong>true</strong> if the audio is muted, <strong>false</strong> otherwise.
     */
    boolean isMuted();

}
