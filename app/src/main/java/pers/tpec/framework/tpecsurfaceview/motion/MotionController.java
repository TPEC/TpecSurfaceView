package pers.tpec.framework.tpecsurfaceview.motion;

import android.view.MotionEvent;

import java.util.HashSet;
import java.util.Set;

import pers.tpec.framework.tpecsurfaceview.controller.Controller;

/**
 * Created by Tony on 2017/8/10.
 */

public class MotionController implements Controller {
    private MotionBorder border;
    private MotionCallback callback;
    private Set<Integer> pointID;

    private boolean _click,_scale;
    private float x0,y0,x1,y1,xd,yd;

    public MotionController(final MotionBorder border){
        pointID=new HashSet<>();
        setCallback(null);
        setBorder(border);
    }

    public MotionController setBorder(final MotionBorder border){
        this.border=border;
        return this;
    }

    public MotionController setCallback(final MotionCallback callback){
        this.callback=callback;
        return this;
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        int action=event.getActionMasked();
        int index=event.getActionIndex();
        if(action== MotionEvent.ACTION_DOWN || action== MotionEvent.ACTION_POINTER_DOWN){
            if(border!=null && border.contains(event.getX(index),event.getY(index))){
                pointID.add(event.getPointerId(index));
                _click= pointID.size()==1;
                _scale=pointID.size()==2;
                if(_click){
                    x0=event.getX(index);
                    y0=event.getY(index);
                    xd=x0;
                    yd=y0;
                }else if(_scale){
                    x1=event.getX(index);
                    y1=event.getY(index);
                }
                return true;
            }
        }else if (action== MotionEvent.ACTION_UP || action== MotionEvent.ACTION_POINTER_UP) {
            if (pointID.contains(event.getPointerId(index))) {
                pointID.remove(event.getPointerId(index));
                if(_click && border!=null && border.contains(event.getX(index),event.getY(index))){
                    _click=false;
                    double dd=Math.sqrt(Math.pow(event.getX(index)-x0,2)+Math.pow(event.getY(index)-y0,2));
                    if(dd<32) {
                        if (callback != null) {
                            return callback.onClick();
                        }
                    }
                }
                _scale=false;
            }
        }else{
            if(_click){//drag
                if(callback!=null){
                    boolean f= callback.onDrag(event.getX(index)-xd,event.getY(index)-yd);
                    xd=event.getX(index);
                    yd=event.getY(index);
                    return f;
                }
            }else if(_scale){//scale
                if(callback!=null){
                    return callback.onScale(0,0,0);
                }
            }
        }
        return false;
    }
}
