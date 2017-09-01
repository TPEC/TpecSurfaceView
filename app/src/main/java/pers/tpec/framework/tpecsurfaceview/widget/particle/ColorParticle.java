package pers.tpec.framework.tpecsurfaceview.widget.particle;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Tony on 2017/8/31.
 */

public class ColorParticle extends Particle {
    protected final List<ColorParticleUnit> cpu = new ArrayList<>();
    protected int[] color;
    protected float[] colorAtTime;
    protected boolean smallerAtEnd;

    protected float startPathL;

    protected Paint paint;

    public ColorParticle(final float countPerFrame, final float countPerFrameRange,
                         final int lifeTime, final int lifeTimeRange,
                         final float x, final float y,
                         final float velocity, final float velocityRange,
                         final float gravityX, final float gravityY,
                         final float angle, final float angleRange,
                         final float size, final float sizeRange,
                         final int[] color, final float[] colorAtTime,
                         final float blurSize) {
        this(countPerFrame, countPerFrameRange,
                lifeTime, lifeTimeRange,
                ParticleFactory.getOnePointPath(x, y), 0f,
                velocity, velocityRange,
                gravityX, gravityY,
                angle, angleRange,
                size, sizeRange,
                color, colorAtTime,
                blurSize);
    }

    public ColorParticle(final float countPerFrame, final float countPerFrameRange,
                         final int lifeTime, final int lifeTimeRange,
                         final Path startPath, final float deltaStartPath,
                         final float velocity, final float velocityRange,
                         final float gravityX, final float gravityY,
                         final float angle, final float angleRange,
                         final float size, final float sizeRange,
                         final int[] color, final float[] colorAtTime,
                         final float blurSize) {
        super(countPerFrame, countPerFrameRange, lifeTime, lifeTimeRange, startPath, deltaStartPath, velocity, velocityRange, gravityX, gravityY, angle, angleRange, size, sizeRange);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setMaskFilter(new BlurMaskFilter(blurSize, BlurMaskFilter.Blur.SOLID));
        this.color = color;
        this.colorAtTime = colorAtTime;
        smallerAtEnd = true;
        startPathL = 0;
    }

    public ColorParticle setPosition(final float x,final float y){
        setStartPath(ParticleFactory.getOnePointPath(x,y),0);
        return this;
    }

    protected void addParticle() {
        Random rnd = new Random();
        float count = rnd.nextFloat() * countPerFrameRange + countPerFrame;
        float deltaPathLength = (count == 0) ? 0 : deltaStartPath * startPathMeasure.getLength() / count;
        synchronized (cpu) {
            for (; count >= 1 || count > rnd.nextFloat(); count--) {
                int lifeTime = rnd.nextInt(lifeTimeRange) + this.lifeTime;
                float angle = rnd.nextFloat() * angleRange + this.angle;
                float velocity = rnd.nextFloat() * velocityRange + this.velocity;
                float vx = (float) (velocity * Math.cos(angle));
                float vy = (float) (velocity * Math.sin(angle));
                float size = rnd.nextFloat() * sizeRange + this.size;
                float[] pos = new float[2];
                startPathL += deltaPathLength;
                while (startPathL > startPathMeasure.getLength()) {
                    startPathL -= startPathMeasure.getLength();
                }
                startPathMeasure.getPosTan(startPathL, pos, null);
                cpu.add(new ColorParticleUnit(lifeTime, pos[0], pos[1], vx, vy, size, color[0]));
            }
        }
    }

    public final ColorParticle play() {
        return (timeLeft == 0) ? play(1) : play(timeLeft);
    }

    public final ColorParticle play(final int time) {
        this.timeLeft = time;
        running = true;
        return this;
    }

    public final ColorParticle pause() {
        running = false;
        return this;
    }

    @Override
    public final void logic() {
        if (running) {
            if (timeLeft != 0) {
                addParticle();
            }
            if (timeLeft > 0) {
                timeLeft--;
            }
            synchronized (cpu) {
                for (int i = 0; i < cpu.size(); i++) {
                    ColorParticleUnit u = cpu.get(i);
                    u.timeLeft--;
                    if (u.timeLeft == 0) {
                        cpu.remove(i);
                        i--;
                        if (cpu.size() == 0 && timeLeft == 0) {
                            running = false;
                        }
                    } else {
                        float tp = u.getTimeSpent();
                        if (tp < colorAtTime[u.colorIndex]) {
                            u.color = getColorBetween(color[u.colorIndex - 1], color[u.colorIndex],
                                    (tp - colorAtTime[u.colorIndex - 1]) / (colorAtTime[u.colorIndex] - colorAtTime[u.colorIndex - 1]));
                        } else {
                            u.color = (tp == colorAtTime[u.colorIndex])
                                    ? color[u.colorIndex]
                                    : getColorBetween(color[u.colorIndex], color[u.colorIndex + 1],
                                    (tp - colorAtTime[u.colorIndex]) / (colorAtTime[u.colorIndex + 1] - colorAtTime[u.colorIndex]));
                            u.colorIndex++;
                        }
                        if (smallerAtEnd && tp >= 0.75f) {
                            u.size *= 0.9f;
                        }
                        u.vx += gravityX;
                        u.vy += gravityY;
                        u.x += u.vx;
                        u.y += u.vy;
                    }
                }
            }
        }
    }

    @Override
    public final void draw(Canvas canvas) {
        synchronized (cpu) {
            for (ColorParticleUnit u : cpu) {
                paint.setColor(u.color);
                canvas.drawCircle(u.x, u.y, u.size, paint);
            }
        }
    }

    private static int getColorBetween(final int color1, final int color2, final float f) {
        int colorA = (int) ((float) ((color1 >> 24) & 0xff) * (1 - f) + (float) ((color2 >> 24) & 0xff) * f);
        int colorR = (int) ((float) ((color1 >> 16) & 0xff) * (1 - f) + (float) ((color2 >> 16) & 0xff) * f);
        int colorG = (int) ((float) ((color1 >> 8) & 0xff) * (1 - f) + (float) ((color2 >> 8) & 0xff) * f);
        int colorB = (int) ((float) (color1 & 0xff) * (1 - f) + (float) (color2 & 0xff) * f);

        return (colorA << 24) | (colorR << 16) | (colorG << 8) | colorB;
    }

    protected class ColorParticleUnit extends Particle.ParticleUnit {
        public int color;
        public int colorIndex;

        public ColorParticleUnit(final int lifeTime,
                                 final float x, final float y,
                                 final float vx, final float vy,
                                 final float size,
                                 final int color) {
            super(lifeTime, x, y, vx, vy, size);
            this.color = color;
            colorIndex = 1;
        }
    }
}
