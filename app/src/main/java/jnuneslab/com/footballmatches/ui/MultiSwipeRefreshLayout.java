package jnuneslab.com.footballmatches.ui;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * MultiSwipeRefreshLayout class is responsible for the appearing of the refresh icon when scroll down event is catch inside recycler view
 */
public class MultiSwipeRefreshLayout extends SwipeRefreshLayout {

    private CanChildScrollUpCallback mCanChildScrollUpCallback;

    /**
     * MultiSwipeRefreshLayout overloaded constructor
     * @param context - Application context
     */
    public MultiSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    /**
     * MultiSwipeRefreshLayout overloaded constructor
     * @param context - Application context
     * @param attrs - Collection of attributes
     */
    public MultiSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Setter of mCanChildScrollUpCallback
     * @param canChildScrollUpCallback - CanChildScrollUpCallback
     */
    public void setCanChildScrollUpCallback(CanChildScrollUpCallback canChildScrollUpCallback) {
        mCanChildScrollUpCallback = canChildScrollUpCallback;
    }

    /**
     * Interface used to force the implementation of CanChildScrollUpCallback in order to make canScrollVertically check
     */
    public interface CanChildScrollUpCallback {
        boolean canSwipeRefreshChildScrollUp();
    }

    @Override
    public boolean canChildScrollUp() {
        if (mCanChildScrollUpCallback != null) {
            return mCanChildScrollUpCallback.canSwipeRefreshChildScrollUp();
        }
        return super.canChildScrollUp();
    }
}