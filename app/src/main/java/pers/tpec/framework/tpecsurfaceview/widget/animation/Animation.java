package pers.tpec.framework.tpecsurfaceview.widget.animation;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pers.tpec.framework.tpecsurfaceview.widget.animation.actions.ActionI;

/**
 * Created by Tony on 2017/8/28.
 */

public class Animation {
    private ActionI object = null;
    private final List<Action> actions = new ArrayList<>();
    private int index;
    private Action nowAct = null;
    private boolean running;

    public Animation() {
        nowAct = null;
        running = false;
    }

    public Animation setObject(@NonNull final ActionI object) {
        this.object = object;
        return this;
    }

    public Animation addActions(@NonNull final Action... action) {
        synchronized (actions) {
            Collections.addAll(actions, action);
        }
        return this;
    }

    public void replay(){
        replay(0);
    }

    public void replay(final int index){
        this.index=index;
        running=true;
        nowAct=null;
    }

    public void stop(){
        running=false;
    }

    public Animation clearActions() {
        synchronized (actions) {
            actions.clear();
        }
        index = 0;
        return this;
    }

    public void play() {
        running = true;
    }

    public void logic() {
        if (object != null && running) {
            if (nowAct != null) {
                if (!nowAct.run(object)) {
                    nowAct = null;
                }
            } else {
                synchronized (actions) {
                    if (actions.size() > index) {
                        nowAct = actions.get(index);
                        index++;
                        nowAct.init();
                        logic();
                    } else {
                        running = false;
                    }
                }
            }
        }
    }

    public boolean isRunning() {
        return running;
    }
}
