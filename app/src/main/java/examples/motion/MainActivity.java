package examples.motion;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import pers.tpec.framework.tpecsurfaceview.TpecSurfaceView;

public class MainActivity extends AppCompatActivity {
    //The target size of TpecSurfaceView
    private static final int width=1280;
    private static final int height=720;
    private TpecSurfaceView tsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Disable the System state bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Create the instance of TpecSurfaceView
        tsv=new TpecSurfaceView(this,width,height,TpecSurfaceView.SCALEMOD_PRESERVE);

        setContentView(tsv);

        MainScene ms=new MainScene();
        tsv.setScene(ms).setService(ms).setController(ms);
    }

    @Override
    protected void onPause() {
        super.onPause();
        tsv.onPause();
    }

    @Override
    protected void onResume() {
        //Set the landscape orientation
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onResume();
        tsv.onResume();
    }
}
