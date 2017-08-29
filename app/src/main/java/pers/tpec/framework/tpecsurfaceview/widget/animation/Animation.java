package pers.tpec.framework.tpecsurfaceview.widget.animation;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pers.tpec.framework.tpecsurfaceview.widget.ObjectInScene;
import pers.tpec.framework.tpecsurfaceview.widget.animation.actions.ActionI;

/**
 * Created by Tony on 2017/8/28.
 */

public class Animation extends ObjectInScene{
    private ActionI object = null;
    private final List<Action> actions = new ArrayList<>();
    private int index;
    private Action nowAct = null;
    private boolean running;
    private int loop;

    public Animation() {
        nowAct = null;
        running = false;
    }

    public Animation(@NonNull final ActionI object){
        this();
        setObject(object);
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

    public void pause(){
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
        play(0);
    }

    public void play(final int loop){
        running=true;
        this.loop=loop;
    }

    @Override
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
                        if(loop==-1){
                            replay();
                            logic();
                        }else if(loop>0){
                            loop--;
                            replay();
                            logic();
                        }else {
                            running = false;
                        }
                    }
                }
            }
        }
    }

    public boolean isRunning() {
        return running;
    }
}
