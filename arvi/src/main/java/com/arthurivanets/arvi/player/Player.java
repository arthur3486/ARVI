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

package com.arthurivanets.arvi.player;

import com.arthurivanets.arvi.player.util.VolumeController;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.FloatRange;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A base contract to be implemented by the concrete Player implementations.
 */
public interface Player {


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
        PlaybackState.IDLE,
        PlaybackState.BUFFERING,
        PlaybackState.READY,
        PlaybackState.ENDED
    })
    @interface PlaybackState {

        int IDLE = com.google.android.exoplayer2.Player.STATE_IDLE;
        int BUFFERING = com.google.android.exoplayer2.Player.STATE_BUFFERING;
        int READY = com.google.android.exoplayer2.Player.STATE_READY;
        int ENDED = com.google.android.exoplayer2.Player.STATE_ENDED;

    }


    /**
     * Initializes the player and related internal components.
     */
    void init();

    /**
     * Prepares the media to be played by the player.
     *
     * @param resetPosition whether to reset the playback position
     */
    void prepare(boolean resetPosition);

    /**
     * Starts/Resumes the video playback.
     */
    void play();

    /**
     * Pauses the video playback.
     */
    void pause();

    /**
     * Stops the video playback.
     *
     * @param resetPosition whether to reset the playback position
     */
    void stop(boolean resetPosition);

    /**
     * Seeks to a specific playback position specified in milliseconds.
     *
     * @param positionInMillis playback position in milliseconds
     */
    void seek(long positionInMillis);

    /**
     * Releases all the resources associated with the player, as well as the Player itself.
     */
    void release();

    /**
     * Attaches the specified {@link PlayerView} to the current {@link Player} instance.
     *
     * @param playerView the {@link PlayerView} to be attached
     */
    void attach(@NonNull PlayerView playerView);

    /**
     * Detaches the current {@link Player} instance from the specified {@link PlayerView}.
     *
     * @param playerView the {@link PlayerView} to detach the {@link Player} from
     */
    void detach(@NonNull PlayerView playerView);

    /**
     * Posts the "Attached" event to the associated {@link AttachmentStateDelegate}, to notify
     * about the fact that the current {@link Player} instance has been attached to the {@link PlayerView}.
     */
    void postAttachedEvent();

    /**
     * Posts the "Detached" event to the associated {@link AttachmentStateDelegate}, to notify
     * about the fact that the current {@link Player} instance has been detached from the {@link PlayerView}.
     * (Usually used to ensure that the associated "leakable" data gets disassociated from the player correctly)
     */
    void postDetachedEvent();

    /**
     * Sets the {@link AttachmentStateDelegate} to be used by the current instance of the {@link Player}
     * to delegate the handling of the attachment events.
     *
     * @param attachmentStateDelegate the attachment handling delegate
     */
    void setAttachmentStateDelegate(@Nullable AttachmentStateDelegate attachmentStateDelegate);

    /**
     * Registers the {@link Player.EventListener} to be used for the handling of the {@link Player}-related events.
     *
     * @param eventListener the player event listener
     */
    void addEventListener(@NonNull Player.EventListener eventListener);

    /**
     * Unregisters the registered {@link Player.EventListener}.
     *
     * @param eventListener the player event listener
     */
    void removeEventListener(@NonNull Player.EventListener eventListener);

    /**
     * Unregisters all the registered {@link Player.EventListener}s (if there's any).
     */
    void removeAllEventListeners();

    /**
     * Sets the {@link MediaSource} to be be used for the media data retrieval by the {@link Player}.
     *
     * @param mediaSource the video/audio media source
     */
    void setMediaSource(@NonNull MediaSource mediaSource);

    /**
     * Retrieves the associated {@link MediaSource}.
     *
     * @return the associated Media Source
     */
    @Nullable
    MediaSource getMediaSource();

    /**
     * Retrieves the {@link Player} {@link VolumeController}.
     *
     * @return the player volume controller
     */
    @NonNull
    VolumeController getVolumeController();

    /**
     * Retrieves the current {@link Player} playback state.
     *
     * @return the player playback state
     */
    @PlaybackState
    int getPlaybackState();

    /**
     * Retrieves the current {@link Player} playback position (in millis).
     *
     * @return the current player playback position (in millis)
     */
    long getPlaybackPosition();

    /**
     * Retrieves the duration of the media played by the {@link Player} (in millis).
     *
     * @return the duration of the media (in millis)
     */
    long getDuration();

    /**
     * Retrieves the current data buffered percentage (a value between 0.0 and 1.0).
     *
     * @return the data buffered percentage
     */
    @FloatRange(from = 0.0, to = 1.0)
    float getBufferedPercentage();

    /**
     * Retrieves the {@link MediaSource}'s looping state.
     *
     * @return whether the player's data source is a looping one or not
     */
    boolean isLooping();

    /**
     * Retrieves the {@link Player}'s initialized state.
     *
     * @return whether the player is initialized or not
     */
    boolean isInitialized();

    /**
     * Retrieves the {@link Player}'s "Playing" state.
     *
     * @return whether the player's playback is active or not
     */
    boolean isPlaying();

    /**
     * Determines whether the current {@link Player} is attached to the specified {@link PlayerView}.
     *
     * @param playerView
     * @return whether the current Player is attached to the specified Player View or not
     */
    boolean isAttached(@NonNull PlayerView playerView);

    /**
     * Determines whether the current {@link Player} is attached to a {@link PlayerView},
     * using the associated {@link AttachmentStateDelegate} (if there's any).
     *
     * @return whether the current Player is attached to a Player View or not
     */
    boolean isAttached();


    /**
     * A delegate used for the handling of the {@link Player}-related attachment events.
     */
    interface AttachmentStateDelegate {

        /**
         * Gets called when the {@link Player} is ready to delegate
         * the handling of the attachment event (Attachment to the {@link PlayerView}).
         *
         * @param player the player
         */
        void onAttach(@NonNull Player player);

        /**
         * Gets called when the {@link Player} is ready to delegate
         * the handling of the detachment event (Detachment from the {@link PlayerView}).
         *
         * @param player the player
         */
        void onDetach(@NonNull Player player);

        /**
         * Gets called internally by the {@link Player} to delegate the determination of
         * the {@link Player}'s attached state.
         *
         * @param player the player
         * @return whether the Player is attached to the Player View or not
         */
        boolean isAttached(@NonNull Player player);

    }


    /**
     * A {@link Player} event listener.
     */
    interface EventListener {

        /**
         * Gets called when the {@link Player}'s playback state changes.
         *
         * @param playbackState the new playback state
         */
        void onPlayerStateChanged(@PlaybackState int playbackState);

        /**
         * Gets called when the {@link Player}'s playback loading state changes.
         *
         * @param isLoading whether the data is loading or not
         */
        void onLoadingChanged(boolean isLoading);

        /**
         * Gets called when the {@link Player}'s tracks change.
         *
         * @param trackGroups
         * @param trackSelections
         */
        void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections);

        /**
         * Gets called when the {@link Player} encounters some sort of error.
         *
         * @param error the encountered error
         */
        void onPlayerError(ExoPlaybackException error);

    }


}