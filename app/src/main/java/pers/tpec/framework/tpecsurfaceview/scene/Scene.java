package pers.tpec.framework.tpecsurfaceview.scene;

import android.graphics.Canvas;

public abstract class Scene {
    private boolean visible=false;

    public abstract void draw(final Canvas canvas);

    public final boolean isVisible(){
        return visible;
    }

    public final void setVisible(final boolean visible){
        this.visible=visible;
    }

    public void onPause(){

    }

    public void onResume(){

    }
}
