package pers.tpec.framework.tpecsurfaceview.widget.animation;

import pers.tpec.framework.tpecsurfaceview.widget.animation.actions.ActionI;

/**
 * Created by Tony on 2017/8/28.
 */

public interface Action<T extends ActionI> {
    void init();

    boolean run(T object);
}
