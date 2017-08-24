package pers.tpec.framework.tpecsurfaceview.controller.motion;

/**
 * Created by Tony on 2017/8/10.
 */

public interface MotionCallback extends ClickCallback{
    boolean onDrag(final float dx, final float dy);
    boolean onScale(final float mx, final float my, final float scale);
}
