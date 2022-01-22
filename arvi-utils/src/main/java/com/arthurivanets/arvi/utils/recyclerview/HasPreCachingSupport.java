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

package com.arthurivanets.arvi.utils.recyclerview;

/**
 * A marker interface to be implemented by the concrete implementations of
 * the {@link androidx.recyclerview.widget.RecyclerView.LayoutManager}s in order
 * to provide the pre-caching support and the required apis.
 */
public interface HasPreCachingSupport {

    /**
     * Sets the extra space that will be used for the view pre-creation & caching purposes.
     *
     * @param extraLayoutSpace in pixels (depends on the orientation and the type of the Layout Manager)
     */
    void setExtraLayoutSpace(int extraLayoutSpace);

}
