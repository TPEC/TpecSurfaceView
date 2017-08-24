package pers.tpec.framework.tpecsurfaceview.widget;

import android.view.MotionEvent;

import pers.tpec.framework.tpecsurfaceview.controller.motion.MotionBorder;
import pers.tpec.framework.tpecsurfaceview.controller.motion.MotionController;

/**
 * Created by Tony on 2017/8/24.
 */

public class ButtonMotionController extends MotionController {
    private boolean enabled;

    public ButtonMotionController(MotionBorder border) {
        super(border);
        enabled=true;
    }

    public void setEnabled(final boolean enabled){
        this.enabled=enabled;
        if(!enabled){
            super._click=false;
            super._scale=false;
            super.pointID.clear();
        }
    }

    public boolean isClickDown(){
        return super._click;
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        if(enabled)
            return super.onTouch(event);
        return false;
    }
}
