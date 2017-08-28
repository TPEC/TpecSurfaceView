# TpecSurfaceView

### Getting start

To make sure that your application be fullscreen, you need to disable the title by modifying *AndroidManifest.xml*
```
android:theme="@style/Theme.AppCompat.DayNight.NoActionBar">
```
To initialize and run the *TpecSurfaceView*
```java
//MainActivity.java

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

```

## [More examples](https://github.com/TPEC/TpecSurfaceView/tree/master/examples/src/main/java/pers/tpec/framework/examples)