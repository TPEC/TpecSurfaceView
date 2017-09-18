package pers.tpec.framework.examples.particles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

import pers.tpec.framework.tpecsurfaceview.controller.Controller;
import pers.tpec.framework.tpecsurfaceview.controller.motion.ClickCallback;
import pers.tpec.framework.tpecsurfaceview.controller.motion.MotionController;
import pers.tpec.framework.tpecsurfaceview.controller.motion.RectBorder;
import pers.tpec.framework.tpecsurfaceview.scene.Scene;
import pers.tpec.framework.tpecsurfaceview.service.Service;
import pers.tpec.framework.tpecsurfaceview.widget.particle.ColorParticle;
import pers.tpec.framework.tpecsurfaceview.widget.particle.ParticleFactory;
import pers.tpec.framework.tpecsurfaceview.widget.particle.ParticlePool;

/**
 * Created by Tony on 2017/8/17.
 * <p>
 * Getting start
 * draw a rectangle
 */

public class MainScene extends Scene implements Service, Controller {
    private ParticlePool pp;
    private MotionController mo = new MotionController(new RectBorder(0, 0, 1280, 720)).setClickCallback(new ClickCallback() {
        @Override
        public boolean onClick(final float x, final float y) {
            float s = 1f;
            pp.add(ParticleFactory.createFireEffects(60, x, y, 3f * s, 2f * s).play(-1));
            return true;
        }
    });

    public MainScene() {
        pp = new ParticlePool();
        Path path=new Path();
//        path.addCircle(640,360,60, Path.Direction.CW);
        path.addRect(30,30,330,330, Path.Direction.CW);
        pp.add(new ColorParticle(
                3, 0, 120, 30, path,0.01f, 2, 1, 0, 0, 0, 30, 10, 0,
                new int[]{
                        Color.argb(255, 255, 255, 255),
                        Color.argb(255, 255, 0, 255),
                        Color.argb(255, 255, 191, 255),
                        Color.argb(0, 255, 191, 255)
                },
                new float[]{
                        0, 0.1f, 0.8f, 1
                },
                10
        ).play(-1));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        pp.draw(canvas);
        Paint paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(10);
        canvas.drawLine(330,0,330,720,paint);
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
