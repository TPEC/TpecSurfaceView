package pers.tpec.framework.tpecsurfaceview.widget;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import pers.tpec.framework.tpecsurfaceview.scene.Scene;

/**
 * Created by Tony on 2017/8/28.
 */

public class ObjectPool {
    private final HashMap<Scene,List<ObjectInScene>> objects=new HashMap<>();
    private Scene scene=null;
    private List<ObjectInScene> nowList=new ArrayList<>();

    public ObjectPool add(@NonNull final Scene scene, @NonNull ObjectInScene... object){
        synchronized (objects){
            List<ObjectInScene> list=objects.get(scene);
            if(list==null){
                list=new ArrayList<>();
            }
            for(ObjectInScene o:object) {
                o.bind(scene);
                list.add(o);
            }
            objects.put(scene,list);
        }
        return this;
    }

    public void clearObjectInScene(@NonNull final Scene scene){
        synchronized (objects){
            objects.put(scene,null);
        }
    }

    public ObjectPool remove(@NonNull final Scene scene, final ObjectInScene object){
        synchronized (objects){
            List<ObjectInScene> list=objects.get(scene);
            if(list!=null){
                list.remove(object);
            }
        }
        return this;
    }

    /**
     * Asynchronously switch the scene
     * @param scene new Scene
     */
    public void switchScene(@NonNull final Scene scene){
        this.scene = scene;
    }

    /**
     * Only for initialization
     * @param scene
     */
    @Deprecated
    public void setScene(@NonNull final Scene scene){
        nowList=objects.get(scene);
        if(nowList==null){
            nowList=new ArrayList<>();
        }
    }

    public void logic(){
        if(scene!=null){
            synchronized (objects) {
                nowList=objects.get(scene);
            }
            if(nowList==null){
                nowList=new ArrayList<>();
            }
            scene=null;
        }
        synchronized (objects) {
            for (ObjectInScene o : nowList) {
                o.logic();
            }
        }
    }

    public void draw(Canvas canvas){
        synchronized (objects) {
            for (ObjectInScene o : nowList) {
                o.draw(canvas);
            }
        }
    }

    public boolean onTouch(MotionEvent event) {
        synchronized (objects) {
            for (ObjectInScene o : nowList) {
                if(o.onTouch(event)){
                    return true;
                }
            }
        }
        return false;
    }
}
