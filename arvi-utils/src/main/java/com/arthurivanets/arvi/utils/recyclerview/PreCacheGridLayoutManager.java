package com.arthurivanets.arvi.utils.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A {@link GridLayoutManager} with support for the Item View pre-caching.
 */
public class PreCacheGridLayoutManager extends GridLayoutManager implements HasPreCachingSupport {


    private int extraLayoutSpace = -1;




    public PreCacheGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }




    public PreCacheGridLayoutManager(Context context,
                                     int spanCount,
                                     int orientation,
                                     boolean reverseLayout) {
        super(
            context,
            spanCount,
            orientation,
            reverseLayout
        );
    }




    public PreCacheGridLayoutManager(Context context,
                                     AttributeSet attrs,
                                     int defStyleAttr,
                                     int defStyleRes) {
        super(
            context,
            attrs,
            defStyleAttr,
            defStyleRes
        );
    }




    @Override
    public final void setExtraLayoutSpace(int extraLayoutSpace) {
        this.extraLayoutSpace = extraLayoutSpace;
    }




    @Override
    protected final int getExtraLayoutSpace(RecyclerView.State state) {
        return ((this.extraLayoutSpace > 0) ? this.extraLayoutSpace : super.getExtraLayoutSpace(state));
    }




}
