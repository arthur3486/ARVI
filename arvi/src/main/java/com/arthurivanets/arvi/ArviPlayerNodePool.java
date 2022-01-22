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

import com.arthurivanets.arvi.player.Player;
import com.arthurivanets.arvi.util.misc.Preconditions;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.arthurivanets.arvi.util.misc.CollectionUtils.toHashSet;

/**
 * A concrete implementation of the {@link PlayerNodePool}, used to
 * manage the {@link PlayerNode}s and corresponding {@link Player}s.
 */
final class ArviPlayerNodePool implements PlayerNodePool {


    private final int maxSize;

    private final Set<PlayerNode> playerNodeSet;
    private final Map<String, PlayerNode> keyPlayerNodeMap;




    ArviPlayerNodePool(int maxSize) {
        Preconditions.isTrue("You must specify a valid Pool Max Size.", (maxSize >= 0));

        this.maxSize = maxSize;
        this.playerNodeSet = new HashSet<>();
        this.keyPlayerNodeMap = new HashMap<>();
    }




    @Override
    public final void add(@NonNull PlayerNode playerNode) {
        Preconditions.nonNull(playerNode);

        this.playerNodeSet.add(playerNode);
        this.keyPlayerNodeMap.put(playerNode.getKey(), playerNode);
    }




    @Override
    public final void add(@NonNull String key, @NonNull Player player) {
        Preconditions.nonEmpty(key);
        Preconditions.nonNull(player);

        add(new PlayerNode(player).setKey(key));
    }




    @Override
    public final PlayerNode remove(@NonNull PlayerNode playerNode) {
        Preconditions.nonNull(playerNode);
        return remove(playerNode.getKey());
    }




    @Override
    public final PlayerNode remove(@NonNull String key) {
        Preconditions.nonEmpty(key);

        final PlayerNode playerNode = this.keyPlayerNodeMap.remove(key);

        if(playerNode != null) {
            this.playerNodeSet.remove(playerNode);
            playerNode.removeKey();
        }

        return playerNode;
    }




    @Override
    public final void unregister(@NonNull String key) {
        Preconditions.nonEmpty(key);

        final PlayerNode playerNode = get(key);

        if(playerNode != null) {
            unbind(playerNode, false);
        }

        this.keyPlayerNodeMap.remove(key);
    }




    @Nullable
    @Override
    public final PlayerNode acquire(@NonNull String key) {
        Preconditions.nonEmpty(key);

        final PlayerNode freePlayerNode = acquireFree(key);

        if(freePlayerNode != null) {
            return freePlayerNode;
        }

        return acquireOldest(key);
    }




    @Nullable
    @Override
    public final PlayerNode acquireFree(@NonNull String key) {
        Preconditions.nonEmpty(key);

        final PlayerNode freePlayerNode = getFree();

        if(freePlayerNode != null) {
            freePlayerNode.setKey(key);
            this.keyPlayerNodeMap.put(key, freePlayerNode);
        }

        return freePlayerNode;
    }




    @Override
    public final PlayerNode acquireOldest(@NonNull String key) {
        Preconditions.nonEmpty(key);

        final PlayerNode playerNode = getOldest();

        if(playerNode != null) {
            unbind(playerNode, false);
            playerNode.setKey(key);
            this.keyPlayerNodeMap.put(key, playerNode);
        }

        return playerNode;
    }




    @Override
    public final void release(@NonNull PlayerNode playerNode) {
        Preconditions.nonNull(playerNode);

        unbind(playerNode, true);

        if(playerNode.hasPlayer()) {
            playerNode.getPlayer().release();
            playerNode.setPlayer(null);
        }
    }




    @Override
    public final void release(@NonNull String key) {
        Preconditions.nonEmpty(key);

        final PlayerNode playerNode = get(key);

        if(playerNode != null) {
            release(playerNode);
        }
    }




    @Override
    public final void release() {
        for(PlayerNode playerNode : toHashSet(this.playerNodeSet)) {
            release(playerNode);
        }
    }




    @Override
    public final PlayerNode get(@NonNull String key) {
        Preconditions.nonEmpty(key);

        final PlayerNode playerNode = this.keyPlayerNodeMap.get(key);

        if(playerNode != null) {
            updateKey(playerNode, key);
        }

        return playerNode;
    }




    @Override
    public final PlayerNode getFree() {
        for(PlayerNode playerNode : this.playerNodeSet) {
            if(playerNode.hasPlayer()
                && !playerNode.getPlayer().isAttached()
                && !playerNode.isKeySet()
                && !this.keyPlayerNodeMap.containsKey(playerNode.getKey())) {
                return updateAccessTime(playerNode);
            }
        }

        return null;
    }




    @Override
    public final PlayerNode getOldest() {
        final PlayerNode playerNode = Collections.min(this.playerNodeSet);
        return ((playerNode != null) ? updateAccessTime(playerNode) : null);
    }




    @Override
    public final int getPlayerCount() {
        return this.playerNodeSet.size();
    }




    @Override
    public final boolean isFull() {
        return (getPlayerCount() == this.maxSize);
    }




    @Override
    public final boolean contains(@NonNull String key) {
        return (this.keyPlayerNodeMap.get(key) != null);
    }




    private void unbind(PlayerNode playerNode, boolean removeFromPool) {
        final Player player = playerNode.getPlayer();

        if(player != null) {
            player.stop(false);
            player.postDetachedEvent();
            player.setAttachmentStateDelegate(null);
            player.removeAllEventListeners();
        }

        if(playerNode.isKeySet() && removeFromPool) {
            remove(playerNode.getKey());
        } else {
            playerNode.removeKey();
        }
    }




    private PlayerNode updateAccessTime(PlayerNode playerNode) {
        return playerNode.setLastAccessTime(System.currentTimeMillis());
    }




    private PlayerNode updateKey(PlayerNode playerNode, String key) {
        playerNode.setLastAccessTime(System.currentTimeMillis());
        playerNode.setKey(key);

        return playerNode;
    }




}
