package pers.tpec.framework.tpecsurfaceview.scene;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.TypedValue;

/**
 * Created by Tony on 2017/8/19.
 */

public class SceneFactory {
    public static Resources resources=null;

    public static Bitmap decodeResource(final Resources resources, final int id){
        TypedValue value = new TypedValue();
        resources.openRawResource(id, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
        return BitmapFactory.decodeResource(resources, id, opts);
    }

    public static Bitmap decodeResource(final int id) throws NullPointerException{
        if(resources!=null){
            return decodeResource(resources,id);
        }else{
            throw new NullPointerException("Resources is null. Make sure use SceneFactory.setResources(resources) first.");
        }
    }

    public static void setResources(@NonNull final Resources resources){
        SceneFactory.resources=resources;
    }
}
