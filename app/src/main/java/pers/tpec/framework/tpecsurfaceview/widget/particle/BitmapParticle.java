package pers.tpec.framework.tpecsurfaceview.widget.particle;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pers.tpec.framework.tpecsurfaceview.widget.ObjectInScene;

/**
 * Created by Tony on 2017/8/28.
 */

public class BitmapParticle extends ObjectInScene {
    private static final int RESIZE_LIFETIME = 30;

    private final List<ParticleUnit> units = new ArrayList<>();
    private int amount;
    private float x, y;
    private float v;
    private float r;
    private int life;
    private float ax, ay;
    private Paint paint;
    private int timeLeft;

    private Bitmap bmp=null;

    private boolean running;

    public BitmapParticle() {
        this(0, 0, 0, 0, 0, 0, 0, 0, Color.WHITE, 10);
    }

    public BitmapParticle(final int amount, final int lifeTime,
                          final float x, final float y,
                          final float v,
                          final float ax, final float ay,
                          final float radius, final int color, final float blurSize) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        setAmount(amount);
        setLifeTime(lifeTime);
        setPosition(x, y);
        setVelocity(v);
        setAcceleration(ax, ay);
        setRadius(radius);
        setColor(color);
        setBlurSize(blurSize);
        running = false;
    }

    public BitmapParticle(final int amount, final int lifeTime,
                          final float x, final float y,
                          final float v,
                          final float ax, final float ay,
                          final float scale,
                          @NonNull final Bitmap bmp) {
        paint=new Paint();
        setAmount(amount);
        setLifeTime(lifeTime);
        setPosition(x, y);
        setVelocity(v);
        setAcceleration(ax, ay);
        setRadius(0);
        this.bmp=Bitmap.createScaledBitmap(bmp,(int)(bmp.getWidth()*scale),(int)(bmp.getHeight()*scale),false);
        running=false;
    }

    public BitmapParticle setTimeLeft(final int timeLeft) {
        this.timeLeft = timeLeft;
        return this;
    }

    public BitmapParticle setAmount(final int amount) {
        this.amount = amount;
        return this;
    }

    public BitmapParticle setPosition(final float x, final float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public BitmapParticle setLifeTime(final int lifeTime) {
        this.life = lifeTime;
        return this;
    }

    public BitmapParticle setRadius(final float radius) {
        this.r = radius;
        return this;
    }

    public BitmapParticle setColor(final int color) {
        paint.setColor(color);
        return this;
    }

    public BitmapParticle setVelocity(final float v) {
        this.v = v;
        return this;
    }

    public BitmapParticle setAcceleration(final float ax, final float ay) {
        this.ax = ax;
        this.ay = ay;
        return this;
    }

    public BitmapParticle setBlurSize(final float blurSize) {
        paint.setMaskFilter(new BlurMaskFilter(blurSize, BlurMaskFilter.Blur.SOLID));
        return this;
    }

    public BitmapParticle play() {
        return play(1);
    }

    /**
     * @param time -1:  forever
     *             0:   null
     *             1+:  frames
     * @return
     */
    public BitmapParticle play(final int time) {
        setTimeLeft(time);
        running = true;
        return this;
    }

    public void pause() {
        running = false;
    }

    @Deprecated
    public BitmapParticle addUnit(@NonNull ParticleUnit unit) {
        synchronized (units) {
            units.add(unit);
        }
        return this;
    }

    public BitmapParticle addUnit() {
        Random rnd = new Random();
        synchronized (units) {
            for (int i = 0; i < amount; i++) {
                int life_ = (int) ((float) life * (rnd.nextFloat() * 0.45 + 0.8));
                float ang = (float) (rnd.nextFloat() * 2 * Math.PI);
                float v_ = rnd.nextFloat() * v;
                units.add(new ParticleUnit(life_, r, x, y, (float) (v_ * Math.cos(ang)), (float) (v_ * Math.sin(ang))));
            }
        }
        return this;
    }

    @Override
    public void logic() {
        if (running) {
            if (timeLeft != 0) {
                addUnit();
            }
            if (timeLeft > 0) {
                timeLeft--;
            }
            synchronized (units) {
                for (int i = 0; i < units.size(); i++) {
                    ParticleUnit u = units.get(i);
                    u.life--;
                    if (u.life == 0) {
                        units.remove(i);
                        i--;
                        if (units.size() == 0 && timeLeft == 0) {
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

    public final boolean isRunning() {
        return running;
    }

    @Override
    public void draw(Canvas canvas) {
        if(bmp==null) {
            synchronized (units) {
                for (ParticleUnit u : units) {
                    canvas.drawCircle(u.x, u.y, u.radius, paint);
                }
            }
        }else{
            synchronized (units) {
                for (ParticleUnit u : units) {
                    canvas.drawBitmap(bmp,u.x, u.y,paint);
                }
            }
        }
    }

    public void clear() {
        synchronized (units) {
            units.clear();
        }
    }

    class ParticleUnit {
        int life;
        float radius;
        float x, y;
        float vx, vy;

        public ParticleUnit(final int life, final float radius, final float x, final float y, final float vx, final float vy) {
            this.life = life;
            this.radius = radius;
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }
    }
}
