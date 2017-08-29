package pers.tpec.framework.tpecsurfaceview.widget.animation;

import pers.tpec.framework.tpecsurfaceview.widget.animation.actions.ActionEnabled;
import pers.tpec.framework.tpecsurfaceview.widget.animation.actions.ActionOffset;
import pers.tpec.framework.tpecsurfaceview.widget.animation.actions.ActionRotate;

/**
 * Created by Tony on 2017/8/28.
 */

public class ActionFactory {
    public static Action<ActionEnabled> createEnabled(final boolean enabled) {
        return new Action<ActionEnabled>() {

            @Override
            public void init() {

            }

            @Override
            public boolean run(ActionEnabled object) {
                object.setEnabled(enabled);
                return false;
            }
        };
    }

    /**
     * Linear movement
     *
     * @param time
     * @param vx
     * @param vy
     * @return
     */
    public static Action<ActionOffset> createMove(final int time, final float vx, final float vy) {
        return createMove(time, vx, vy, 0, 0);
    }

    public static Action<ActionOffset> createMove(final int time, final float v0x, final float v0y, final float ax, final float ay) {
        return createMove(time, v0x, v0y, ax, ay, null);
    }

    public static Action<ActionOffset> createMove(final int time, final float v0x, final float v0y, final float ax, final float ay, final ActionCallback callback) {
        return new Action<ActionOffset>() {
            private int timeLeft;
            private float vx;
            private float vy;

            @Override
            public void init() {
                timeLeft = time;
                vx = v0x;
                vy = v0y;
            }

            @Override
            public boolean run(ActionOffset object) {
                object.offset(vx, vy);
                vx += ax;
                vy += ay;
                if (callback != null)
                    callback.callbackRun(object);
                if (timeLeft > 0) {
                    timeLeft--;
                    return timeLeft > 0;
                }
                return true;
            }
        };
    }

    public static Action<ActionRotate> createRotate(final int time, final float vr, final float x, final float y, final ActionCallback callback) {
        return new Action<ActionRotate>() {
            private int timeLeft;

            @Override
            public void init() {
                timeLeft = time;
            }

            @Override
            public boolean run(ActionRotate object) {
                object.rotate(vr, x, y);
                if (callback != null)
                    callback.callbackRun(object);
                if (timeLeft > 0) {
                    timeLeft--;
                    return timeLeft > 0;
                }
                return true;
            }
        };
    }
}
