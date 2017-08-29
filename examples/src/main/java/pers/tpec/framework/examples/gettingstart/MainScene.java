package pers.tpec.framework.examples.gettingstart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import pers.tpec.framework.tpecsurfaceview.controller.Controller;
import pers.tpec.framework.tpecsurfaceview.scene.Scene;
import pers.tpec.framework.tpecsurfaceview.service.Service;

/**
 * Created by Tony on 2017/8/17.
 *
 * Getting start
 * draw a rectangle
 */

public class MainScene extends Scene implements Service,Controller {
    private Paint paint;
    private Rect rect;

    public MainScene(){
        paint=new Paint();
        rect=new Rect(0,0,300,100);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(rect,paint);
    }

    @Override
    public void logic() {

    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return false;
    }
}
