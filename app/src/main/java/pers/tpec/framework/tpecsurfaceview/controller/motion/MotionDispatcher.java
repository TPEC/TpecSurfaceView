package pers.tpec.framework.tpecsurfaceview.controller.motion;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pers.tpec.framework.tpecsurfaceview.controller.Controller;

/**
 * Created by Tony on 2017/8/10.
 */

public class MotionDispatcher implements Controller {
    public static final int PRIORITY_HIGH=0;
    public static final int PRIORITY_MEDIUM=10;
    public static final int PRIORITY_LOW=20;

    private static final Comparator<ControllerPair> comparator=new Comparator<ControllerPair>() {
        @Override
        public int compare(ControllerPair o1, ControllerPair o2) {
            return Integer.valueOf(o1.priority).compareTo(o2.priority);
        }
    };

    private final List<ControllerPair> cp;

    public MotionDispatcher(){
        cp=new ArrayList<>();
    }

    public MotionDispatcher addController(final Controller controller,final int priority){
        synchronized (cp){
            cp.add(new ControllerPair(controller,priority));
            Collections.sort(cp, comparator);
        }
        return this;
    }

    public MotionDispatcher addController(final Controller controller){
        return addController(controller,PRIORITY_MEDIUM);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        synchronized (cp) {
            for (ControllerPair c : cp) {
                if(c.controller.onTouch(event)){
                    return true;
                }
            }
        }
        return false;
    }

    class ControllerPair{
        Controller controller;
        int priority;

        public ControllerPair(final Controller controller, final int priority){
            this.controller=controller;
            this.priority=priority;
        }
    }
}
