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
