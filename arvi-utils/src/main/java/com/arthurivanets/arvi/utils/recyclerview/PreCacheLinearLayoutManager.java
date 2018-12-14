package com.arthurivanets.arvi.utils.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A {@link LinearLayoutManager} with support for the Item View pre-caching.
 */
public class PreCacheLinearLayoutManager extends LinearLayoutManager implements HasPreCachingSupport {


    private int extraLayoutSpace = -1;




    public PreCacheLinearLayoutManager(Context context) {
        super(context);
    }




    public PreCacheLinearLayoutManager(Context context,
                                       int orientation,
                                       boolean reverseLayout) {
        super(
            context,
            orientation,
            reverseLayout
        );
    }




    public PreCacheLinearLayoutManager(Context context,
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
