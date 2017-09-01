package pers.tpec.framework.tpecsurfaceview.widget.particle;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pers.tpec.framework.tpecsurfaceview.widget.ObjectInScene;

/**
 * Created by Tony on 2017/8/28.
 */

public class ParticlePool extends ObjectInScene {
    private boolean autoRemove = true;
    private final List<Particle> particles = new ArrayList<>();

    public ParticlePool add(@NonNull final Particle... particle) {
        synchronized (particles) {
            Collections.addAll(particles, particle);
        }
        return this;
    }

    /**
     * Remove BitmapParticle when it is done.
     *
     * @param autoRemove
     */
    public void setAutoRemove(boolean autoRemove) {
        this.autoRemove = autoRemove;
    }

    @Deprecated
    public Particle get(final int index) {
        return particles.get(index);
    }

    public ParticlePool removeDeadParticle() {
        synchronized (particles) {
            for (int i = 0; i < particles.size(); i++) {
                if (!particles.get(i).running) {
                    particles.remove(i);
                    i--;
                }
            }
        }
        return this;
    }

    public ParticlePool clear() {
        synchronized (particles) {
            particles.clear();
        }
        return this;
    }

    @Override
    public void draw(Canvas canvas) {
        synchronized (particles) {
            for (Particle p : particles) {
                p.draw(canvas);
            }
        }
    }

    @Override
    public void logic() {
        synchronized (particles) {
            for (int i = 0; i < particles.size(); i++) {
                particles.get(i).logic();
                if (autoRemove && !particles.get(i).running) {
                    particles.remove(i);
                    i--;
                }
            }
        }
    }
}
