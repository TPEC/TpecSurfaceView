package pers.tpec.framework.tpecsurfaceview.motion;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import pers.tpec.framework.tpecsurfaceview.controller.Controller;

/**
 * Created by Tony on 2017/8/10.
 */

public class MotionDispatcher implements Controller {
    private final List<Controller> controller;

    public MotionDispatcher(){
        controller=new ArrayList<>();
    }

    public void addController(final Controller controller){
        synchronized (this.controller) {
            this.controller.add(controller);
        }
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        synchronized (controller) {
            for (Controller c : controller) {
                if(c.onTouch(event)){
                    return true;
                }
            }
        }
        return false;
    }
}
