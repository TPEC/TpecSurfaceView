package pers.tpec.framework.tpecsurfaceview.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Tony on 2017/8/30.
 */

public abstract class AnimationObjectInScene extends ObjectInScene {
    protected RectF rectDst;
    protected Rect rectSrc;
    protected Bitmap bmp;
    protected Paint paint;
    protected int frameAmount;
    protected int frameTime;
    protected boolean running;

    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(bmp,rectSrc,rectDst,paint);
    }

    @Override
    public void logic() {
        if(running){

        }
    }
}
