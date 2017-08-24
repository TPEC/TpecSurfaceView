package examples.motion;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import pers.tpec.framework.tpecsurfaceview.controller.Controller;
import pers.tpec.framework.tpecsurfaceview.controller.motion.MotionBorder;
import pers.tpec.framework.tpecsurfaceview.controller.motion.MotionCallback;
import pers.tpec.framework.tpecsurfaceview.controller.motion.MotionController;
import pers.tpec.framework.tpecsurfaceview.scene.Scene;
import pers.tpec.framework.tpecsurfaceview.service.Service;

/**
 * Created by Tony on 2017/8/17.
 *
 * Motion
 * click callback
 */

public class MainScene implements Scene,Service,Controller,MotionBorder {
    private Paint paint;
    private Rect rect;
    private MotionController mc;

    public MainScene(){
        paint=new Paint();
        rect=new Rect(0,0,300,100);
        mc=new MotionController(this).setCallback(new MotionCallback() {
            @Override
            public boolean onClick() {
                rect.offset(100,50);
                return true;
            }

            @Override
            public boolean onDrag(float dx, float dy) {
                return false;
            }

            @Override
            public boolean onScale(float mx, float my, float scale) {
                return false;
            }
        });
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(rect,paint);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void logic() {

    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return mc.onTouch(event);
    }

    @Override
    public boolean contains(float x, float y) {
        return rect.contains((int)x,(int)y);
    }
}
