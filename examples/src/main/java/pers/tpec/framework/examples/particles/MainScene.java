package pers.tpec.framework.examples.particles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import pers.tpec.framework.tpecsurfaceview.controller.Controller;
import pers.tpec.framework.tpecsurfaceview.controller.motion.ClickCallback;
import pers.tpec.framework.tpecsurfaceview.controller.motion.MotionController;
import pers.tpec.framework.tpecsurfaceview.controller.motion.RectBorder;
import pers.tpec.framework.tpecsurfaceview.scene.Scene;
import pers.tpec.framework.tpecsurfaceview.service.Service;
import pers.tpec.framework.tpecsurfaceview.widget.particle.Particle;

/**
 * Created by Tony on 2017/8/17.
 * <p>
 * Getting start
 * draw a rectangle
 */

public class MainScene implements Scene, Service, Controller {
    private Particle particle = new Particle();
    private MotionController mo = new MotionController(new RectBorder(0, 0, 1280, 720)).setClickCallback(new ClickCallback() {
        @Override
        public boolean onClick() {
            particle.init(128, 640, 360, 120, 5, Color.WHITE, 7.5f, 0, 0.1f);
            particle.play();
            return true;
        }
    });

    @Override
    public void draw(Canvas canvas) {
        particle.draw(canvas);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void logic() {
        particle.logic();
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return mo.onTouch(event);
    }
}
