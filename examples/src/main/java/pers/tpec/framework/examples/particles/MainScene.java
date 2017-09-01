package pers.tpec.framework.examples.particles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import java.util.Random;

import pers.tpec.framework.tpecsurfaceview.controller.Controller;
import pers.tpec.framework.tpecsurfaceview.controller.motion.ClickCallback;
import pers.tpec.framework.tpecsurfaceview.controller.motion.MotionController;
import pers.tpec.framework.tpecsurfaceview.controller.motion.RectBorder;
import pers.tpec.framework.tpecsurfaceview.scene.Scene;
import pers.tpec.framework.tpecsurfaceview.service.Service;
import pers.tpec.framework.tpecsurfaceview.widget.particle.BitmapParticle;
import pers.tpec.framework.tpecsurfaceview.widget.particle.ParticleFactory;
import pers.tpec.framework.tpecsurfaceview.widget.particle.ParticlePool;

/**
 * Created by Tony on 2017/8/17.
 * <p>
 * Getting start
 * draw a rectangle
 */

public class MainScene extends Scene implements Service, Controller {
    private ParticlePool pp = new ParticlePool();
    private MotionController mo = new MotionController(new RectBorder(0, 0, 1280, 720)).setClickCallback(new ClickCallback() {
        @Override
        public boolean onClick(final float x,final float y) {
            float s=1f;
            pp.add(ParticleFactory.createFireEffects(60,x,y,3f*s,2f*s).play(-1));
            return true;
        }
    });

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        pp.draw(canvas);
    }

    @Override
    public void logic() {
        pp.logic();
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return mo.onTouch(event);
    }
}
