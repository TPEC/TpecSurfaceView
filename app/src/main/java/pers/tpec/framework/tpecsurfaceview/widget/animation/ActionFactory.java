package pers.tpec.framework.tpecsurfaceview.widget.animation;

import pers.tpec.framework.tpecsurfaceview.widget.animation.actions.ActionEnabled;
import pers.tpec.framework.tpecsurfaceview.widget.animation.actions.ActionI;
import pers.tpec.framework.tpecsurfaceview.widget.animation.actions.ActionOffset;

/**
 * Created by Tony on 2017/8/28.
 */

public class ActionFactory {
    public static Action createEnabled(final boolean enabled) {
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
    public static Action createMove(final int time, final float vx, final float vy) {
        return createMove(time, vx, vy, 0, 0);
    }

    public static Action createMove(final int time, final float v0x, final float v0y, final float ax, final float ay) {
        return createMove(time, v0x, v0y, ax, ay, null);
    }

    public static Action createMove(final int time, final float v0x, final float v0y, final float ax, final float ay, final ActionCallback callback) {
        return new Action<ActionOffset>() {
            private int timeLeft;
            private float vx;
            private float vy;

            @Override
            public void init() {
                timeLeft=time;
                vx=v0x;
                vy=v0y;
            }

            @Override
            public boolean run(ActionOffset object) {
                timeLeft--;
                object.offset(vx, vy);
                vx += ax;
                vy += ay;
                if (callback != null)
                    callback.callbackRun();
                return timeLeft > 0;
            }
        };
    }

    public static Action createTimer(final int time, final ActionCallback callback) {
        return new Action<ActionI>() {
            private int timeLeft;

            @Override
            public void init() {
                timeLeft=time;
            }

            @Override
            public boolean run(ActionI object) {
                timeLeft--;
                callback.callbackRun();
                return timeLeft > 0;
            }
        };
    }
}
