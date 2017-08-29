package pers.tpec.framework.examples.animation;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import pers.tpec.framework.tpecsurfaceview.widget.ObjectInScene;
import pers.tpec.framework.tpecsurfaceview.widget.animation.actions.ActionOffset;

/**
 * Created by Tony on 2017/8/29.
 */

public class AnimationObject extends ObjectInScene implements ActionOffset {
    private RectF rect = new RectF(0, 0, 100, 100);

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawRect(rect, paint);
    }

    @Override
    public void offset(float dx, float dy) {
        rect.offset(dx, dy);
    }
}
