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

import android.content.Context;

import com.arthurivanets.arvi.player.util.DefaultVolumeController;
import com.arthurivanets.arvi.player.util.PlayerEventListenerRegistry;
import com.arthurivanets.arvi.player.util.VolumeController;
import com.arthurivanets.arvi.util.misc.ExoPlayerUtils;
import com.arthurivanets.arvi.util.misc.Preconditions;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.arthurivanets.arvi.util.misc.Preconditions.checkNonNull;

/**
 * A default implementation of the {@link Player} which has the most essential player-related
 * handling functionality implemented for you.
 */
public class DefaultPlayer implements Player {


    private final Context context;

    private PlayerEventListenerRegistry eventHandler;

    private RenderersFactory renderersFactory;
    private TrackSelector trackSelector;
    private LoadControl loadControl;
    private BandwidthMeter bandwidthMeter;
    private MediaSource mediaSource;

    private SimpleExoPlayer exoPlayer;
    private VolumeController volumeController;

    private AttachmentStateDelegate attachmentStateDelegate;




    public DefaultPlayer(@NonNull Context context,
                         @NonNull RenderersFactory renderersFactory,
                         @NonNull TrackSelector trackSelector,
                         @NonNull LoadControl loadControl) {
        this(
            context,
            renderersFactory,
            trackSelector,
            loadControl,
            null
        );
    }




    public DefaultPlayer(@NonNull Context context,
                         @NonNull RenderersFactory renderersFactory,
                         @NonNull TrackSelector trackSelector,
                         @NonNull LoadControl loadControl,
                         @Nullable BandwidthMeter bandwidthMeter) {
        this.context = checkNonNull(context).getApplicationContext();
        this.eventHandler = new PlayerEventListenerRegistry();
        this.renderersFactory = checkNonNull(renderersFactory);
        this.trackSelector = checkNonNull(trackSelector);
        this.loadControl = checkNonNull(loadControl);
        this.bandwidthMeter = bandwidthMeter;
    }




    @Override
    public final void init() {
        if(isInitialized()) {
            return;
        }

        this.exoPlayer = new SimpleExoPlayer.Builder(this.context, this.renderersFactory)
            .setTrackSelector(this.trackSelector)
            .setLoadControl(this.loadControl)
            .setBandwidthMeter(this.bandwidthMeter)
            .build();

        this.exoPlayer.addListener(this.eventHandler);
        this.volumeController = new DefaultVolumeController(this.exoPlayer);
    }




    @Override
    public final void prepare(final boolean resetPosition) {
        checkPlayerState();
        checkMediaSource();

        this.exoPlayer.setMediaSource(this.mediaSource, resetPosition);
        this.exoPlayer.prepare();
    }




    @Override
    public final void play() {
        checkPlayerState();

        this.exoPlayer.setPlayWhenReady(true);
    }




    @Override
    public final void pause() {
        checkPlayerState();

        this.exoPlayer.setPlayWhenReady(false);
    }




    @Override
    public final void stop(final boolean resetPosition) {
        checkPlayerState();

        this.exoPlayer.stop(resetPosition);
    }




    @Override
    public final void seek(final long positionInMillis) {
        checkPlayerState();

        this.exoPlayer.seekTo(positionInMillis);
    }




    @Override
    public final void release() {
        if(!isInitialized()) {
            return;
        }

        this.exoPlayer.release();
        this.exoPlayer = null;
        this.attachmentStateDelegate = null;

        removeAllEventListeners();
    }




    @Override
    public final void attach(@NonNull final PlayerView playerView) {
        Preconditions.nonNull(playerView);
        checkPlayerState();

        playerView.setPlayer(this.exoPlayer);
    }




    @Override
    public final void detach(@NonNull PlayerView playerView) {
        Preconditions.nonNull(playerView);
        checkPlayerState();

        playerView.setPlayer(null);
    }




    @Override
    public final void postAttachedEvent() {
        if(this.attachmentStateDelegate != null) {
            this.attachmentStateDelegate.onAttach(this);
        }
    }




    @Override
    public final void postDetachedEvent() {
        if(this.attachmentStateDelegate != null) {
            this.attachmentStateDelegate.onDetach(this);
        }
    }




    private void checkPlayerState() {
        if(!isInitialized()) {
            throw new IllegalStateException("The Player must be initialized first.");
        }
    }




    private void checkMediaSource() {
        if(this.mediaSource == null) {
            throw new IllegalStateException("The Media Source is required.");
        }
    }




    @Override
    public final void setAttachmentStateDelegate(@Nullable AttachmentStateDelegate attachmentStateDelegate) {
        this.attachmentStateDelegate = attachmentStateDelegate;
    }




    @Override
    public final void addEventListener(@NonNull EventListener eventListener) {
        Preconditions.nonNull(eventListener);

        this.eventHandler.addListener(eventListener);
    }




    @Override
    public final void removeEventListener(@NonNull EventListener eventListener) {
        Preconditions.nonNull(eventListener);

        this.eventHandler.removeListener(eventListener);
    }




    @Override
    public final void removeAllEventListeners() {
        this.eventHandler.removeAllListeners();
    }




    @Override
    public final void setMediaSource(@NonNull MediaSource mediaSource) {
        this.mediaSource = checkNonNull(mediaSource);
    }




    @Nullable
    @Override
    public final MediaSource getMediaSource() {
        return this.mediaSource;
    }




    @NonNull
    @Override
    public final VolumeController getVolumeController() {
        checkPlayerState();
        return this.volumeController;
    }




    @Override
    public final int getPlaybackState() {
        return (isInitialized() ? this.exoPlayer.getPlaybackState() : PlaybackState.IDLE);
    }




    @Override
    public final long getPlaybackPosition() {
        return (isInitialized() ? this.exoPlayer.getCurrentPosition() : 0L);
    }




    @Override
    public final long getDuration() {
        return (isInitialized() ? this.exoPlayer.getDuration() : 0L);
    }




    @Override
    public final float getBufferedPercentage() {
        return (isInitialized() ? this.exoPlayer.getBufferedPercentage() : 0L);
    }




    @Override
    public final boolean isLooping() {
        return ExoPlayerUtils.isLooping(this.mediaSource);
    }




    @Override
    public final boolean isInitialized() {
        return (this.exoPlayer != null);
    }




    @Override
    public final boolean isPlaying() {
        final int playbackState = getPlaybackState();

        return (
            isInitialized()
            && this.exoPlayer.getPlayWhenReady()
            && (playbackState != PlaybackState.IDLE)
            && ((playbackState != PlaybackState.ENDED) || isLooping())
        );
    }




    @Override
    public final boolean isAttached(@NonNull PlayerView playerView) {
        Preconditions.nonNull(playerView);
        return ((playerView.getPlayer() != null) && (playerView.getPlayer() == this.exoPlayer));
    }




    @Override
    public final boolean isAttached() {
        return ((this.attachmentStateDelegate != null) && this.attachmentStateDelegate.isAttached(this));
    }




}
