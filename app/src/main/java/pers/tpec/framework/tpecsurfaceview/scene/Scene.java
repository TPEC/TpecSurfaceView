package pers.tpec.framework.tpecsurfaceview.scene;

import android.graphics.Canvas;

public interface Scene {
    void draw(final Canvas canvas);
    void onPause();
    void onResume();
}
