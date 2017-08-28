package pers.tpec.framework.tpecsurfaceview.controller.motion;

import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Tony on 2017/8/10.
 */

public class RectBorder implements MotionBorder {
    private RectF rectF=null;
    private Rect rect=null;

    public RectBorder(final RectF rect){
        this.rectF=rect;
    }

    public RectBorder(final Rect rect){
        this.rect=rect;
    }

    public RectBorder(final int left,final int top,final int right,final int bottom){
        this.rect=new Rect(left, top, right, bottom);
    }

    public RectBorder(final float left,final float top,final float right,final float bottom){
        this.rectF=new RectF(left, top, right, bottom);
    }

    @Override
    public boolean contains(float x, float y) {
        if(rectF!=null)
            return rectF.contains(x,y);
        else
            return rect.contains((int)x,(int)y);
    }
}
