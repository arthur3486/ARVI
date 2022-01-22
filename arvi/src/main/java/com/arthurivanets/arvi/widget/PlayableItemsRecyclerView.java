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

package com.arthurivanets.arvi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.arthurivanets.arvi.PlayerProviderImpl;
import com.arthurivanets.arvi.player.Player;
import com.arthurivanets.arvi.util.misc.Preconditions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import static com.arthurivanets.arvi.util.misc.CollectionUtils.hashSetOf;

/**
 * A concrete implementation of the {@link PlayableItemsContainer} based on the {@link RecyclerView}
 * which provides the management of the items' playbacks.
 */
public final class PlayableItemsRecyclerView extends RecyclerView implements PlayableItemsContainer {


    private static final Set<PlaybackTriggeringState> DEFAULT_PLAYBACK_TRIGGERING_STATES = hashSetOf(
        PlaybackTriggeringState.DRAGGING,
        PlaybackTriggeringState.IDLING
    );

    private final Set<PlaybackTriggeringState> mPlaybackTriggeringStates = new HashSet<>();

    private int mPreviousScrollDeltaX;
    private int mPreviousScrollDeltaY;

    private AutoplayMode mAutoplayMode;

    private boolean mIsAutoplayEnabled;
    private boolean mIsScrolling;




    public PlayableItemsRecyclerView(Context context) {
        super(context);
        init();
    }




    public PlayableItemsRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }




    public PlayableItemsRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }




    private void init() {
        mPreviousScrollDeltaX = 0;
        mPreviousScrollDeltaY = 0;
        mAutoplayMode = AutoplayMode.ONE_AT_A_TIME;
        mIsAutoplayEnabled = true;

        mPlaybackTriggeringStates.addAll(DEFAULT_PLAYBACK_TRIGGERING_STATES);

        setRecyclerListener(this::onRecyclerViewViewRecycled);
    }




    @Override
    public final void startPlayback() {
        handleItemPlayback(true);
    }




    @Override
    public final void stopPlayback() {
        stopItemPlayback();
    }




    @Override
    public final void pausePlayback() {
        pauseItemPlayback();
    }




    @Override
    protected final void onAttachedToWindow() {
        super.onAttachedToWindow();
        startPlayback();
    }




    @Override
    protected final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releaseAllItems();
    }




    @Override
    public final void onChildAttachedToWindow(View child) {
        super.onChildAttachedToWindow(child);

        final ViewHolder viewHolder = getChildViewHolder(child);

        if(!(viewHolder instanceof Playable)) {
            return;
        }

        // if necessary, reattaching the active player to the Playable
        final Playable playable = (Playable) viewHolder;

        if(PlayerProviderImpl.getInstance(getContext()).hasPlayer(playable.getConfig(), playable.getKey())) {
            final Player player = PlayerProviderImpl.getInstance(getContext()).getPlayer(playable.getConfig(), playable.getKey());

            if(player.isPlaying() && playable.wantsToPlay()) {
                playable.start();
            }
        }
    }




    @Override
    public final void onChildDetachedFromWindow(View child) {
        super.onChildDetachedFromWindow(child);

        final ViewHolder viewHolder = getChildViewHolder(child);

        if(!(viewHolder instanceof Playable)) {
            return;
        }

        // releasing the associated player (Playable-wise) and other resources
        final Playable playable = (Playable) viewHolder;
        playable.release();
    }




    @Override
    public final void onResume() {
        startPlayback();
    }




    @Override
    public final void onPause() {
        pausePlayback();
    }




    @Override
    public final void onDestroy() {
        releaseAllItems();
    }




    private void onRecyclerViewViewRecycled(RecyclerView.ViewHolder holder) {
        if(!(holder instanceof Playable)) {
            return;
        }

        final Playable playable = (Playable) holder;

        if(playable.wantsToPlay()) {
            return;
        }

        playable.release();
    }




    private void handleItemPlayback(boolean allowPlay) {
        final List<Playable> playableItems = new ArrayList<>();
        final int childCount = getChildCount();
        final boolean canHaveMultipleActiveItems = AutoplayMode.MULTIPLE_SIMULTANEOUSLY.equals(mAutoplayMode);

        RecyclerView.ViewHolder viewHolder;
        boolean isInPlayableArea;
        boolean hasActiveItem = false;

        // extracting all the playable visible items
        for(int i = 0; i < childCount; i++) {
            viewHolder = findContainingViewHolder(getChildAt(i));

            if((viewHolder instanceof Playable)
                    && ((Playable) viewHolder).isTrulyPlayable()) {
                playableItems.add((Playable) viewHolder);
            }
        }

        // processing the extracted Playable items
        for(Playable playable : playableItems) {
            isInPlayableArea = playable.wantsToPlay();

            // handling the playback state
            if(isInPlayableArea && (!hasActiveItem || canHaveMultipleActiveItems)) {
                if(!playable.isPlaying()
                        && mIsAutoplayEnabled
                        && allowPlay) {
                    playable.start();
                }

                hasActiveItem = true;
            } else if(playable.isPlaying()) {
                playable.pause();
            }

            playable.onPlayabilityStateChanged(isInPlayableArea);
        }
    }




    private void stopItemPlayback() {
        final int childCount = getChildCount();
        RecyclerView.ViewHolder viewHolder;

        for(int i = 0; i < childCount; i++) {
            viewHolder = findContainingViewHolder(getChildAt(i));

            if((viewHolder instanceof Playable)
                    && ((Playable) viewHolder).isTrulyPlayable()) {
                ((Playable) viewHolder).stop();
            }
        }
    }




    private void pauseItemPlayback() {
        final int childCount = getChildCount();
        RecyclerView.ViewHolder viewHolder;

        for(int i = 0; i < childCount; i++) {
            viewHolder = findContainingViewHolder(getChildAt(i));

            if((viewHolder instanceof Playable)
                    && ((Playable) viewHolder).isTrulyPlayable()) {
                ((Playable) viewHolder).pause();
            }
        }
    }




    private void releaseAllItems() {
        final int childCount = getChildCount();
        RecyclerView.ViewHolder viewHolder;

        for(int i = 0; i < childCount; i++) {
            viewHolder = findContainingViewHolder(getChildAt(i));

            if((viewHolder instanceof Playable)
                    && ((Playable) viewHolder).isTrulyPlayable()) {
                ((Playable) viewHolder).release();
            }
        }
    }




    @Override
    public final void setAutoplayMode(@NonNull AutoplayMode autoplayMode) {
        mAutoplayMode = Preconditions.checkNonNull(autoplayMode);

        if(isAutoplayEnabled()) {
            startPlayback();
        }
    }




    @NonNull
    @Override
    public final AutoplayMode getAutoplayMode() {
        return mAutoplayMode;
    }




    @Override
    public final void setPlaybackTriggeringStates(@NonNull PlaybackTriggeringState... states) {
        Preconditions.nonNull(states);

        mPlaybackTriggeringStates.clear();
        mPlaybackTriggeringStates.addAll((states.length == 0) ? DEFAULT_PLAYBACK_TRIGGERING_STATES : hashSetOf(states));
    }




    @NonNull
    @Override
    public final Set<PlaybackTriggeringState> getPlaybackTriggeringStates() {
        return mPlaybackTriggeringStates;
    }




    private PlaybackTriggeringState getPlaybackStateForScrollState(int scrollState) {
        switch(scrollState) {

            case SCROLL_STATE_SETTLING:
                return PlaybackTriggeringState.SETTLING;

            case SCROLL_STATE_DRAGGING:
                return PlaybackTriggeringState.DRAGGING;

            default:
                return PlaybackTriggeringState.IDLING;

        }
    }




    @Override
    public final void setAutoplayEnabled(boolean isAutoplayEnabled) {
        mIsAutoplayEnabled = isAutoplayEnabled;

        if(isAutoplayEnabled) {
            startPlayback();
        } else {
            stopPlayback();
        }
    }




    @Override
    public final boolean isAutoplayEnabled() {
        return mIsAutoplayEnabled;
    }




    @Override
    public final void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        handleItemPlayback(canPlay());
    }




    @Override
    public final void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        mIsScrolling = ((Math.abs(mPreviousScrollDeltaX - dx) > 0) || (Math.abs(mPreviousScrollDeltaY - dy) > 0));

        handleItemPlayback(canPlay());

        mPreviousScrollDeltaX = dx;
        mPreviousScrollDeltaY = dy;
    }




    private boolean canPlay() {
        final PlaybackTriggeringState state = getPlaybackStateForScrollState(getScrollState());
        final boolean containsState = mPlaybackTriggeringStates.contains(state);
        final boolean isDragging = (PlaybackTriggeringState.DRAGGING.equals(state) && !mIsScrolling);
        final boolean isSettling = PlaybackTriggeringState.SETTLING.equals(state);
        final boolean isIdling = PlaybackTriggeringState.IDLING.equals(state);

        return (containsState && (isDragging || isSettling || isIdling));
    }




}
