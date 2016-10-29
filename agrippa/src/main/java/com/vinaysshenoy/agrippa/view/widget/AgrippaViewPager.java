package com.vinaysshenoy.agrippa.view.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;

/**
 * Custom ViewPager that does not allow swiping to move between pages
 * <p>
 * Created by vinaysshenoy on 29/10/16.
 */
public class AgrippaViewPager extends ViewPager {

    public AgrippaViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return false;
    }
}
