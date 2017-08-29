package pers.tpec.framework.examples.animation;

import android.graphics.Canvas;
import android.view.MotionEvent;

import pers.tpec.framework.tpecsurfaceview.controller.Controller;
import pers.tpec.framework.tpecsurfaceview.scene.Scene;
import pers.tpec.framework.tpecsurfaceview.service.Service;
import pers.tpec.framework.tpecsurfaceview.widget.ObjectPool;
import pers.tpec.framework.tpecsurfaceview.widget.animation.ActionFactory;
import pers.tpec.framework.tpecsurfaceview.widget.animation.Animation;

/**
 * Created by Tony on 2017/8/17.
 * <p>
 * Getting start
 * draw a rectangle
 */

public class MainScene extends Scene implements Service, Controller {
    private ObjectPool op = new ObjectPool();

    public MainScene() {
        AnimationObject ao = new AnimationObject();
        Animation animation = new Animation(ao);
        op.add(this, ao, animation);
        op.setScene(this);

        animation.addActions(
                ActionFactory.createMove(240, 2, 2),
                ActionFactory.createMove(240, 2, 2, -0.01f, -0.01f)
        );
        animation.play();
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
