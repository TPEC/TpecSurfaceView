package pers.tpec.framework.examples.objectpool;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import pers.tpec.framework.tpecsurfaceview.controller.Controller;
import pers.tpec.framework.tpecsurfaceview.scene.Scene;
import pers.tpec.framework.tpecsurfaceview.service.Service;
import pers.tpec.framework.tpecsurfaceview.widget.ObjectPool;
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
    private ObjectPool op=new ObjectPool();

    public MainScene(){
        ParticlePool pp=new ParticlePool();
        pp.add(ParticleFactory.createFireEffects(60,640,360,5,1).play(-1));
        op.add(this,pp);
        op.setScene(this);
    }

    @Override
    public void draw(Canvas canvas) {
        op.draw(canvas);
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
