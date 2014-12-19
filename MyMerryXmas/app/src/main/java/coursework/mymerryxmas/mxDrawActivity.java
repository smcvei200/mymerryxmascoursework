package coursework.mymerryxmas;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;

/**
 * Created by stephen on 24/11/2014.
 */
public class mxDrawActivity extends Activity {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //set the appropriate display
        setContentView(R.layout.mx_draw_screen);
        //create a new draw surface object
        setContentView(new mxDrawSurfaceView(this));
    }
}
