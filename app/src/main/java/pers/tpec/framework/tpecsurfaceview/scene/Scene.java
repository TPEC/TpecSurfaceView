package pers.tpec.framework.tpecsurfaceview.scene;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.TypedValue;

public abstract class Scene {
    private boolean visible = false;

    public abstract void draw(final Canvas canvas);

    public final boolean isVisible() {
        return visible;
    }

    public final void setVisible(final boolean visible) {
        this.visible = visible;
    }

    public void onPause() {

    }

    public void onResume() {

    }

    protected final Bitmap decodeResource(final Resources resources, final int id) {
        TypedValue value = new TypedValue();
        resources.openRawResource(id, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
        return BitmapFactory.decodeResource(resources, id, opts);
    }
}
