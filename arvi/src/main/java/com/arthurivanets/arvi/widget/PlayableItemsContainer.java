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

package com.arthurivanets.arvi.widget;

import java.util.Set;

import androidx.annotation.NonNull;

/**
 * A base contract to be implemented by the concrete {@link PlayableItemsContainer}s.
 * (Defines a Playable Items Container, that is the collection of views which allows the item
 * video media autoplay)
 */
public interface PlayableItemsContainer {


    /**
     * Defines how many videos can be played at a time.
     */
    enum AutoplayMode {

        ONE_AT_A_TIME,
        MULTIPLE_SIMULTANEOUSLY

    }


    /**
     * Defines when the playback can be started.
     */
    enum PlaybackTriggeringState {

        DRAGGING,
        SETTLING,
        IDLING

    }


    /**
     * Starts the playback of the visible {@link Playable} items.
     * (The exact amount of the items playback of which is to be started
     * depends on the {@link AutoplayMode})
     */
    void startPlayback();

    /**
     * Stops the playback of the visible {@link Playable} items.
     */
    void stopPlayback();

    /**
     * Pauses the playback of the visible {@link Playable} items.
     */
    void pausePlayback();

    /**
     * Resumes the paused playbacks of the visible {@link Playable} items.
     * (to be used in the corresponding lifecycle method of the {@link android.app.Activity}/{@link androidx.fragment.app.Fragment}, etc.)
     */
    void onResume();

    /**
     * Pauses the playbacks of the visible {@link Playable} items.
     * (to be used in the corresponding lifecycle method of the {@link android.app.Activity}/{@link androidx.fragment.app.Fragment}, etc.)
     */
    void onPause();

    /**
     * Releases the active players associated with the visible {@link Playable} items.
     * (to be used in the corresponding lifecycle method of the {@link android.app.Activity}/{@link androidx.fragment.app.Fragment}, etc.)
     */
    void onDestroy();

    /**
     * Sets the {@link AutoplayMode}, which is going to define how the {@link Playable}
     * items will be played.
     *
     * @param autoplayMode the autoplay mode
     */
    void setAutoplayMode(@NonNull AutoplayMode autoplayMode);

    /**
     * Retrieves the current {@link AutoplayMode}.
     *
     * @return the current Autoplay Mode
     */
    @NonNull
    AutoplayMode getAutoplayMode();

    /**
     * Sets the {@link PlaybackTriggeringState}s, which are going to define which events
     * will be able to trigger the {@link Playable} items' playbacks.
     *
     * @param states the states
     */
    void setPlaybackTriggeringStates(@NonNull PlaybackTriggeringState... states);

    /**
     * Retrieves the current {@link PlaybackTriggeringState}s.
     *
     * @return the current Playback Triggering States.
     */
    @NonNull
    Set<PlaybackTriggeringState> getPlaybackTriggeringStates();

    /**
     * Used to enable/disable the item autoplay handling.
     *
     * @param isAutoplayEnabled whether to enable or disable the autoplay handling
     */
    void setAutoplayEnabled(boolean isAutoplayEnabled);

    /**
     * Retrieves the item autoplay handling state.
     *
     * @return <strong>true</strong> if the item autoplay is enabled, <strong>false</strong> otherwise
     */
    boolean isAutoplayEnabled();

}