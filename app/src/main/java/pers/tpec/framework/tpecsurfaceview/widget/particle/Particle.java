package pers.tpec.framework.tpecsurfaceview.widget.particle;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathMeasure;

import pers.tpec.framework.tpecsurfaceview.widget.ObjectInScene;

/**
 * Created by Tony on 2017/8/31.
 */

public abstract class Particle extends ObjectInScene {
    protected float countPerFrame, countPerFrameRange;
    protected int lifeTime, lifeTimeRange;
    protected PathMeasure startPathMeasure;
    protected float deltaStartPath;
    protected float velocity, velocityRange;
    protected float gravityX, gravityY;
    protected float angle, angleRange;
    protected float size, sizeRange;

    protected int timeLeft;
    protected boolean running;

    public Particle(final float countPerFrame, final float countPerFrameRange,
                    final int lifeTime, final int lifeTimeRange,
                    final Path startPath, final float deltaStartPath,
                    final float velocity, final float velocityRange,
                    final float gravityX, final float gravityY,
                    final float angle, final float angleRange,
                    final float size, final float sizeRange) {
        this.countPerFrame = countPerFrame;
        this.countPerFrameRange = countPerFrameRange;
        this.lifeTime = lifeTime;
        this.lifeTimeRange = lifeTimeRange;
        setStartPath(startPath,deltaStartPath);
        this.velocity = velocity;
        this.velocityRange = velocityRange;
        this.gravityX = gravityX;
        this.gravityY = gravityY;
        this.angle = (float) (angle * Math.PI / 180);
        this.angleRange = (float) (angleRange * Math.PI / 180);
        this.size = size;
        this.sizeRange = sizeRange;
        timeLeft = 0;
        running = false;
    }

    protected void setStartPath(final Path path,final float deltaStartPath){
        this.startPathMeasure = new PathMeasure(path, true);
        this.deltaStartPath = deltaStartPath;
        if (this.startPathMeasure.getLength() == 0) {
            this.deltaStartPath = 0;
        }
        while (this.deltaStartPath > 1) {
            this.deltaStartPath--;
        }
    }

    @Override
    public abstract void logic();

    @Override
    public abstract void draw(Canvas canvas);

    protected class ParticleUnit {
        public int lifeTime;
        public int timeLeft;
        public float x, y;
        public float vx, vy;
        public float size;

        public ParticleUnit(final int lifeTime,
                            final float x, final float y,
                            final float vx, final float vy,
                            final float size) {
            this.lifeTime = lifeTime;
            timeLeft = lifeTime;
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.size = size;
        }

        public float getTimeSpent() {
            return lifeTime > 0f ? (float) (lifeTime - timeLeft) / (float) lifeTime : 0f;
        }
    }
}
