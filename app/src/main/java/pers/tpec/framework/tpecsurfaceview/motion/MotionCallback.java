package pers.tpec.framework.tpecsurfaceview.motion;

/**
 * Created by Tony on 2017/8/10.
 */

public interface MotionCallback {
    boolean onClick();
    boolean onDrag(final float dx, final float dy);
    boolean onScale(final float mx, final float my, final float scale);
}
