package pers.tpec.framework.tpecsurfaceview.widget.particle;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pers.tpec.framework.tpecsurfaceview.widget.ObjectInScene;

/**
 * Created by Tony on 2017/8/28.
 */

public class Particle extends ObjectInScene {
    private static final int RESIZE_LIFETIME = 30;

    private final List<Unit> units = new ArrayList<>();
    private float ax, ay;
    private Paint paint;

    private boolean running;

    public Particle() {
        this(10f);
    }

    public Particle(final float blurSize) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setMaskFilter(new BlurMaskFilter(blurSize, BlurMaskFilter.Blur.SOLID));
        ax = 0;
        ay = 0;
        running = false;
    }

    public Particle init(final int amount, final float x, final float y, final int life, final float radius, final int color, final float v, final float ax, final float ay) {
        paint.setColor(color);
        this.ax = ax;
        this.ay = ay;
        Random rnd = new Random();
        synchronized (units) {
            units.clear();
            for (int i = 0; i < amount; i++) {
                int life_ = (int) ((float) life * (rnd.nextFloat() * 0.45 + 0.8));
                float a = (float) (rnd.nextFloat() * 2 * Math.PI);
                float v_=rnd.nextFloat()*v;
                float vx = (float) (v_ * Math.cos(a));
                float vy = (float) (v_ * Math.sin(a));
                units.add(new Unit(life_, radius, x, y, vx, vy));
            }
        }
        running = false;
        return this;
    }

    public Particle play() {
        running = true;
        return this;
    }

    public void pause() {
        running = false;
    }

    @Deprecated
    public Particle addUnit(@NonNull Unit unit) {
        synchronized (units) {
            units.add(unit);
        }
        return this;
    }

    @Override
    public void logic() {
        if (running) {
            synchronized (units) {
                for (int i = 0; i < units.size(); i++) {
                    Unit u = units.get(i);
                    u.life--;
                    if (u.life == 0) {
                        units.remove(i);
                        i--;
                        if (units.size() == 0) {
                            running = false;
                        }
                    } else {
                        if (u.life < RESIZE_LIFETIME) {
                            u.radius *= 0.95f;
                        }
                        u.vx += ax;
                        u.vy += ay;
                        u.x += u.vx;
                        u.y += u.vy;
                    }
                }
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void draw(Canvas canvas) {
        synchronized (units) {
            for (Unit u : units) {
                canvas.drawCircle(u.x, u.y, u.radius, paint);
            }
        }
    }

    public void clear() {
        synchronized (units) {
            units.clear();
        }
    }

    class Unit {
        int life;
        float radius;
        float x, y;
        float vx, vy;

        public Unit(final int life, final float radius, final float x, final float y, final float vx, final float vy) {
            this.life = life;
            this.radius = radius;
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }
    }
}
