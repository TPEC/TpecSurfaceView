package pers.tpec.framework.tpecsurfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import pers.tpec.framework.tpecsurfaceview.controller.Controller;
import pers.tpec.framework.tpecsurfaceview.scene.Scene;
import pers.tpec.framework.tpecsurfaceview.service.Service;

public class TpecSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public static final int SCALEMOD_NULL = 0;
    public static final int SCALEMOD_STRETCH = 1;
    public static final int SCALEMOD_PRESERVE = 2;

    private SurfaceHolder holder;

    private Thread thread;
    private boolean threadFlag;

    private int screenWidth, screenHeight;
    private int windowWidth, windowHeight;
    private float scaleWidth, scaleHeight, scaleTranslateX, scaleTranslateY;
    private int scaleMod;

    private Paint paintBlack;

    private Scene scene = null;
    private Service service = null, serviceBack = null;
    private Controller controller = null;

    private long targetInterval = 16666667;

    private Lock controllerLock = new ReentrantLock();

    /**
     * @param context
     * @param windowWidth  target window width
     * @param windowHeight target window height
     * @param scaleMod
     */
    public TpecSurfaceView(Context context, int windowWidth, int windowHeight, int scaleMod) {
        super(context);

        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.scaleMod = scaleMod;

        holder = this.getHolder();
        holder.addCallback(this);

        paintBlack = new Paint();
    }

    final public TpecSurfaceView setScene(final Scene scene) {
        if(this.scene!=null){
            this.scene.setVisible(false);
        }
        this.scene = scene;
        this.scene.setVisible(true);
        return this;
    }

    @Deprecated
    final public TpecSurfaceView setService(final Service service) {
        this.service = service;
        return this;
    }

    final public TpecSurfaceView setController(final Controller controller) {
        controllerLock.lock();
        try {
            this.controller = controller;
        } finally {
            controllerLock.unlock();
        }
        return this;
    }

    final public TpecSurfaceView setTargetInterval(final long targetInterval) {
        this.targetInterval = targetInterval;
        return this;
    }

    private void applyResolutionRatio() {
        scaleTranslateX = 0;
        scaleTranslateY = 0;
        if (scaleMod == SCALEMOD_NULL) {
            scaleWidth = 1f;
            scaleHeight = 1f;
        } else if (scaleMod == SCALEMOD_STRETCH) {
            scaleWidth = (float) screenWidth / (float) windowWidth;
            scaleHeight = (float) screenHeight / (float) windowHeight;
        } else if (scaleMod == SCALEMOD_PRESERVE) {
            if ((float) windowWidth / (float) windowHeight >= (float) screenWidth / (float) screenHeight) {
                scaleWidth = (float) screenWidth / (float) windowWidth;
                scaleTranslateY = ((float) screenHeight - (float) windowHeight * scaleWidth) / 2;
            } else {
                scaleWidth = (float) screenHeight / (float) windowHeight;
                scaleTranslateX = ((float) screenWidth - (float) windowWidth * scaleWidth) / 2;
            }
            scaleHeight = scaleWidth;
        }
    }

    private void draw() {
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();
            if (canvas != null) {
                canvas.save();
                canvas.translate(scaleTranslateX, scaleTranslateY);
                canvas.scale(scaleWidth, scaleHeight);

                if (scene != null) {
                    scene.draw(canvas);
                }

                canvas.restore();
                if (scaleTranslateY > 0) {
                    canvas.drawRect(0, 0, screenWidth, scaleTranslateY, paintBlack);
                    canvas.drawRect(0, screenHeight - scaleTranslateY, screenWidth, screenHeight, paintBlack);
                }
                if (scaleTranslateX > 0) {
                    canvas.drawRect(0, 0, scaleTranslateX, screenHeight, paintBlack);
                    canvas.drawRect(screenWidth - scaleTranslateX, 0, screenWidth, screenHeight, paintBlack);
                }
            }
        } catch (Exception e) {

        } finally {
            if (canvas != null)
                holder.unlockCanvasAndPost(canvas);
        }
    }

    public void switchService(@NonNull final Service newService) {
        this.serviceBack = newService;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        threadFlag = true;
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
        applyResolutionRatio();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        threadFlag = false;
    }

    @Override
    final public boolean onTouchEvent(MotionEvent event) {
        if (controller != null) {
            event.setLocation((event.getX() - scaleTranslateX) / scaleWidth, (event.getY() - scaleTranslateY) / scaleHeight);
            controllerLock.lock();
            try {
                return controller.onTouch(event);
            } finally {
                controllerLock.unlock();
            }
        } else {
            return false;
        }
    }

    @Override
    public void run() {
        long startTime = System.nanoTime() - targetInterval;
        while (threadFlag) {
            while (System.nanoTime() - startTime >= targetInterval) {
                if (serviceBack != null) {
                    service = serviceBack;
                    serviceBack = null;
                }
                if (service != null) {
                    service.logic();
                }
                startTime += targetInterval;
            }
            draw();
        }
    }

    public void onPause() {
        if (scene != null) {
            scene.onPause();
        }
    }

    public void onResume() {
        if (scene != null) {
            scene.onResume();
        }
    }
}
