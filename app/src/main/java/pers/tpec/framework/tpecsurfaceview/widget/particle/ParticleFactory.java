package pers.tpec.framework.tpecsurfaceview.widget.particle;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PathMeasure;

/**
 * Created by Tony on 2017/8/31.
 */

public class ParticleFactory {
    public static ColorParticle createFireEffects(final float lifeTime,final float x,final float y,final float v,final float size){
        int[] color=new int[]{
                Color.argb(0,255,191,0),
                Color.argb(255,255,191,0),
                Color.argb(191,255,0,0),
                Color.argb(0,255,0,0)
        };
        float[] colorAtTime=new float[]{
            0,0.2f,0.8f,1
        };
        ColorParticle cp=new ColorParticle(2,0,(int)(lifeTime*0.8f),(int)(lifeTime*0.45f),x,y,v*0.5f,v*0.5f,0,0,-60,-60,6f*size,0,color,colorAtTime,8f*size);
        return cp;
    }

    public static Path getOnePointPath(final float x,final float y){
        Path path=new Path();
        path.moveTo(x,y);
        path.lineTo(x+1,y);
        return path;
    }

    public static Path getPointCirclePath(final float x,final float y,final float r){
        Path path=new Path();
        path.addCircle(x,y,r, Path.Direction.CW);
        return path;
    }
}
