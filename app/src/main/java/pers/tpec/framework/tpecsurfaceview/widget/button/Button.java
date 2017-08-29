package pers.tpec.framework.tpecsurfaceview.widget.button;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.MotionEvent;

import pers.tpec.framework.tpecsurfaceview.controller.motion.ClickCallback;
import pers.tpec.framework.tpecsurfaceview.controller.motion.MotionBorder;
import pers.tpec.framework.tpecsurfaceview.widget.ObjectInScene;

/**
 * Created by Tony on 2017/8/24.
 */

public class Button extends ObjectInScene implements MotionBorder {
    private RectF rectDst;
    private Rect rectSrc;
    private ButtonMotionController bmc;

    private boolean enabled;
    private boolean visible;

    private Bitmap bmpBg;
    //    private Bitmap bmpSf;
    private Paint paint;

    public Button(RectF rectF) {
        setRectF(rectF);
        bmc = new ButtonMotionController(this);
        enabled = true;
        visible = true;
        paint = new Paint();
    }

    public Button setClickCallback(final ClickCallback clickCallback) {
        bmc.setClickCallback(clickCallback);
        return this;
    }

    public Button setBmp(@NonNull Bitmap bmpBg) {
        this.bmpBg = bmpBg;
        return this;
    }

    public Button setRectF(@NonNull RectF rectF) {
        this.rectDst = rectF;
        return this;
    }

    public Button setEnabled(final boolean enabled) {
        this.enabled = enabled;
        bmc.setEnabled(enabled);
        if (enabled)
            paint = new Paint();
        else
            paint.setColorFilter(new ColorMatrixColorFilter(new float[]{
                    0.3086f, 0.6094f, 0.0820f, 0, 0,
                    0.3086f, 0.6094f, 0.0820f, 0, 0,
                    0.3086f, 0.6094f, 0.0820f, 0, 0,
                    0, 0, 0, 1, 0
            }));
        return this;
    }

    public Button setVisible(final boolean visible) {
        this.visible = visible;
        return this;
    }

    public ButtonMotionController getMotionController() {
        return bmc;
    }

    @Override
    public void draw(Canvas canvas) {
        if (visible && bmpBg != null) {
            if (!bmc.isClickDown()) {
                canvas.drawBitmap(bmpBg, rectSrc, rectDst, paint);
            } else {
                canvas.drawBitmap(bmpBg, rectSrc, rectDst, paint);
            }
        }
    }

    public boolean onTouch(MotionEvent event) {
        if (enabled)
            return bmc.onTouch(event);
        return false;
    }

    @Override
    public boolean contains(float x, float y) {
        return rectDst.contains(x, y);
    }
}
