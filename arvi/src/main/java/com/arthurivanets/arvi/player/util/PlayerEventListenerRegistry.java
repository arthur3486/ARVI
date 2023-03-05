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

import androidx.annotation.NonNull;

import com.arthurivanets.arvi.player.Player;
import com.arthurivanets.arvi.util.misc.Preconditions;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import java.util.HashSet;
import java.util.Set;

/**
 * A manager for the {@link Player.EventListener}s. Used to observe the {@link com.google.android.exoplayer2.Player.EventListener} events
 * and propagate them to all the subscribed {@link Player.EventListener}s.
 */
public final class PlayerEventListenerRegistry implements com.google.android.exoplayer2.Player.EventListener {

    private final Set<Player.EventListener> mEventListeners;

    public PlayerEventListenerRegistry() {
        mEventListeners = new HashSet<>();
    }

    public final void addListener(@NonNull Player.EventListener eventListener) {
        Preconditions.nonNull(eventListener);

        mEventListeners.add(eventListener);
    }

    public final void removeListener(@NonNull Player.EventListener eventListener) {
        Preconditions.nonNull(eventListener);

        mEventListeners.remove(eventListener);
    }

    public final void removeAllListeners() {
        mEventListeners.clear();
    }

    @Override
    public final void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
        // do nothing.
    }

    @Override
    public final void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        for (Player.EventListener eventListener : mEventListeners) {
            eventListener.onTracksChanged(trackGroups, trackSelections);
        }
    }

    @Override
    public final void onLoadingChanged(boolean isLoading) {
        for (Player.EventListener eventListener : mEventListeners) {
            eventListener.onLoadingChanged(isLoading);
        }
    }

    @Override
    public final void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        for (Player.EventListener eventListener : mEventListeners) {
            eventListener.onPlayerStateChanged(playbackState);
        }
    }

    @Override
    public final void onRepeatModeChanged(int repeatMode) {
        // do nothing.
    }

    @Override
    public final void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        // do nothing.
    }

    @Override
    public final void onPlayerError(ExoPlaybackException error) {
        for (Player.EventListener eventListener : mEventListeners) {
            eventListener.onPlayerError(error);
        }
    }

    @Override
    public final void onPositionDiscontinuity(int reason) {
        // do nothing.
    }

    @Override
    public final void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        // do nothing.
    }

    @Override
    public final void onSeekProcessed() {
        // do nothing.
    }

}
