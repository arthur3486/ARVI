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

package com.arthurivanets.arvi;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.arthurivanets.arvi.player.Player;
import com.arthurivanets.arvi.util.misc.Preconditions;

/**
 * An internal {@link Player} holder.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class PlayerNode implements Comparable<PlayerNode> {

    private long lastAccessTime;
    private String key;
    private Player player;

    public PlayerNode(@NonNull Player player) {
        this(System.currentTimeMillis(), player);
    }

    public PlayerNode(long lastAccessTime, @NonNull Player player) {
        this.lastAccessTime = lastAccessTime;
        this.player = Preconditions.checkNonNull(player);
        this.key = "";
    }

    @NonNull
    public final PlayerNode setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
        return this;
    }

    public final long getLastAccessTime() {
        return this.lastAccessTime;
    }

    @NonNull
    public final PlayerNode setPlayer(@Nullable Player player) {
        this.player = player;
        return this;
    }

    @Nullable
    public final Player getPlayer() {
        return this.player;
    }

    public final boolean hasPlayer() {
        return (this.player != null);
    }

    @NonNull
    public final PlayerNode setKey(@NonNull String key) {
        this.key = Preconditions.checkNonNull(key);
        return this;
    }

    @NonNull
    public final PlayerNode removeKey() {
        this.key = "";
        return this;
    }

    @NonNull
    public final String getKey() {
        return this.key;
    }

    public final boolean isKeySet() {
        return !TextUtils.isEmpty(this.key);
    }

    @Override
    public final int compareTo(@NonNull PlayerNode otherNode) {
        if (this.lastAccessTime > otherNode.lastAccessTime) {
            return 1;
        } else if (this.lastAccessTime < otherNode.lastAccessTime) {
            return -1;
        }

        return 0;
    }

    @Override
    public final int hashCode() {
        return ((this.player != null) ? this.player.hashCode() : super.hashCode());
    }

    @Override
    public final boolean equals(Object obj) {
        return ((obj instanceof PlayerProviderImpl) && (hashCode() == obj.hashCode()));
    }

}
