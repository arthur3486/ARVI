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

package com.arthurivanets.arvi.util.misc;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;

import com.arthurivanets.arvi.widget.Playable;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A set of utils specific to Exo Player.
 */
public final class ExoPlayerUtils {


    /**
     * The default size of cache in bytes. (500MB in Bytes)
     */
    public static final long DEFAULT_CACHE_SIZE = (500 * 1024 * 1024);

    private static Cache sCache;




    /**
     * Creates/retrieves the {@link com.google.android.exoplayer2.ExoPlayer} {@link Cache} of the default
     * size {@link #DEFAULT_CACHE_SIZE}.
     *
     * @param context the context
     * @return the {@link com.google.android.exoplayer2.ExoPlayer} {@link Cache}
     */
    public static synchronized Cache getCache(@NonNull Context context) {
        return getCache(context, DEFAULT_CACHE_SIZE);
    }




    /**
     * Creates/retrieves the {@link com.google.android.exoplayer2.ExoPlayer} {@link Cache} of the specified size.
     *
     * @param context the context
     * @param cacheSize the desired cache size in bytes
     * @return the {@link com.google.android.exoplayer2.ExoPlayer} {@link Cache}
     */
    public static synchronized Cache getCache(@NonNull Context context, long cacheSize) {
        Preconditions.nonNull(context);

        if(sCache == null) {
            sCache = new SimpleCache(
                context.getCacheDir(),
                new LeastRecentlyUsedCacheEvictor(cacheSize)
            );
        }

        return sCache;
    }




    /**
     * Calculates the amount of the visibility of the {@link com.google.android.exoplayer2.ui.PlayerView}
     * on the screen.
     * Used to determine the visibility area ratio (a value between 0.0 and 1.0), so that further
     * playback management related actions can be taken by the host {@link com.arthurivanets.arvi.widget.PlayableItemsContainer}.
     *
     * @param playable the playable
     * @return the visibility are offset (a value between 0.0 and 1.0)
     */
    @FloatRange(from = 0.0, to = 1.0)
    public static float getVisibleAreaOffset(@NonNull Playable playable) {
        Preconditions.nonNull(playable);

        if(playable.getParent() == null) {
            return 0f;
        }

        final View playerView = playable.getPlayerView();

        final Rect drawRect = new Rect();
        final Rect playerRect = new Rect();

        playerView.getDrawingRect(drawRect);

        final int drawArea = (drawRect.width() * drawRect.height());
        final boolean isVisible = playerView.getGlobalVisibleRect(playerRect, new Point());

        if(isVisible && (drawArea > 0)) {
            final int visibleArea = (playerRect.height() * playerRect.width());
            return (visibleArea / (float) drawArea);
        }

        return 0f;
    }




    /**
     * Determines whether the specified {@link MediaSource} is a looping one ({@link LoopingMediaSource}).
     *
     * @param mediaSource the media source
     * @return <strong>true</strong> if the specified media source is not null and is a looping one, <strong>false</strong> otherwise
     */
    public static boolean isLooping(@Nullable MediaSource mediaSource) {
        return (mediaSource instanceof LoopingMediaSource);
    }




    /**
     * Adds the {@link MediaSourceEventListener} to the specified {@link MediaSource} only if both
     * the specified {@link Handler} and {@link MediaSourceEventListener} are not null.
     *
     * @param mediaSource the media source
     * @param handler the handler
     * @param listener the listener to be added
     */
    public static void addEventListenerIfNonNull(@NonNull MediaSource mediaSource,
                                                 @Nullable Handler handler,
                                                 @Nullable MediaSourceEventListener listener) {
        Preconditions.nonNull(mediaSource);

        if((handler != null) && (listener != null)) {
            mediaSource.addEventListener(handler, listener);
        }
    }




}