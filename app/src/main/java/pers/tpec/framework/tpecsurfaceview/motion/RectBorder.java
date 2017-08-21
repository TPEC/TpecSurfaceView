package pers.tpec.framework.tpecsurfaceview.motion;

import android.graphics.RectF;

import pers.tpec.framework.tpecsurfaceview.motion.MotionBorder;

/**
 * Created by Tony on 2017/8/10.
 */

public class RectBorder implements MotionBorder {
    private RectF rect;

    public RectBorder(final RectF rect){
        this.rect=rect;
    }

    @Override
    public boolean contains(float x, float y) {
        return rect.contains(x,y);
    }
}
