package pers.tpec.framework.examples.objectpool;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import pers.tpec.framework.tpecsurfaceview.controller.Controller;
import pers.tpec.framework.tpecsurfaceview.controller.motion.ClickCallback;
import pers.tpec.framework.tpecsurfaceview.controller.motion.MotionController;
import pers.tpec.framework.tpecsurfaceview.controller.motion.RectBorder;
import pers.tpec.framework.tpecsurfaceview.scene.Scene;
import pers.tpec.framework.tpecsurfaceview.service.Service;
import pers.tpec.framework.tpecsurfaceview.widget.ObjectPool;
import pers.tpec.framework.tpecsurfaceview.widget.particle.Particle;
import pers.tpec.framework.tpecsurfaceview.widget.particle.ParticlePool;

/**
 * Created by Tony on 2017/8/17.
 * <p>
 * Getting start
 * draw a rectangle
 */

public class MainScene extends Scene implements Service, Controller {
    private ObjectPool op=new ObjectPool();

    public MainScene(){
        ParticlePool pp=new ParticlePool();
        pp.add(new Particle().init(128, 640, 360, 120, 5, Color.WHITE, 7.5f, 0, 0.1f).play());
        op.add(this,pp);
        op.switchScene(this);
    }

    @Override
    public void draw(Canvas canvas) {
        op.draw(canvas);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void logic() {
        op.logic();
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return op.onTouch(event);
    }
}
