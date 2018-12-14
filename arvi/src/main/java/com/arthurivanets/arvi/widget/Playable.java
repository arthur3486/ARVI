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

import android.view.View;
import android.view.ViewParent;

import com.arthurivanets.arvi.Config;
import com.arthurivanets.arvi.model.PlaybackInfo;

import androidx.annotation.NonNull;

/**
 * A base contract to be implemented by the item that support the playback management.
 */
public interface Playable extends PlayabilityStateChangeObserver {

    /**
     * Starts the playback.
     */
    void start();

    /**
     * Restarts the playback.
     */
    void restart();

    /**
     * Pauses the playback.
     */
    void pause();

    /**
     * Stops the playback.
     */
    void stop();

    /**
     * Releases the {@link com.arthurivanets.arvi.player.Player} associated with this item.
     * (Stops the playback and unbinds the surface as well as listeners)
     */
    void release();

    /**
     * Seeks to a specific playback position (in milliseconds).
     *
     * @param positionInMillis the exact position to seek to
     */
    void seekTo(long positionInMillis);

    /**
     * Retrieves the player view associated with this item.
     *
     * @return the player view
     */
    View getPlayerView();

    /**
     * Retrieves the item's parent view.
     *
     * @return the parent view
     */
    ViewParent getParent();

    /**
     * Retrieves the corresponding {@link PlaybackInfo} (if there's any active playback).
     *
     * @return the corresponding playback info
     */
    PlaybackInfo getPlaybackInfo();

    /**
     * Retrieves the media Url associated with this item.
     *
     * @return the media url
     */
    @NonNull
    String getUrl();

    /**
     * Retrieves this {@link Playable}'s tag.
     * (Tags are used to help to differentiate the {@link com.arthurivanets.arvi.player.Player}s within the Player Pool,
     * as well as their corresponding {@link PlaybackInfo}s. See {@link #getKey()})
     *
     * @return the tag
     */
    @NonNull
    String getTag();

    /**
     * Composes the key out the url {@link #getUrl()}  and tag {@link #getTag()} to be used
     * for the internal management of the {@link com.arthurivanets.arvi.player.Player} instances.
     * (override the {@link #getTag()} in cases when you need to prevent the sharing of the {@link com.arthurivanets.arvi.player.Player}
     * instances and corresponding {@link PlaybackInfo}s between the {@link Playable}s with the same media urls.)
     *
     * @return the composed key
     */
    @NonNull
    String getKey();

    /**
     * The {@link com.arthurivanets.arvi.player.Player} configuration.
     * (Normally, you want to have a single instance of the {@link Config} for the whole application
     * [one per specific config]; do not generate new {@link Config}s within this method.)
     *
     * @return the config
     */
    @NonNull
    Config getConfig();

    /**
     * Determines whether the current item is in active playback.
     *
     * @return whether the current item is in active playback
     */
    boolean isPlaying();

    /**
     * Determines whether the current item can manage the {@link com.arthurivanets.arvi.player.Player}'s
     * playback via the available apis.
     *
     * @return if this item can manage the playback
     */
    boolean isTrulyPlayable();

    /**
     * Determines if this item's playback is a looping one.
     *
     * @return if the playback is a looping one
     */
    boolean isLooping();

    /**
     * Determines if the current item is visible enough to be able to start the playback.
     *
     * @return whether the item wants to start the playback
     */
    boolean wantsToPlay();

}
