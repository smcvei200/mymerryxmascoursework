package coursework.mymerryxmas;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by rla on 29/10/2014.
 */
public class mxDrawSurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
    //declare variables
    private SurfaceHolder shDrawSurface;
    mxDrawThread drawingThread = null;


    public mxDrawSurfaceView(Context context)
    {
        //get the application context
        super(context);
        shDrawSurface = getHolder();
        shDrawSurface.addCallback(this);
        drawingThread = new mxDrawThread(getHolder(), this, context);
        setFocusable(true);

    }

    public mxDrawThread getThread()
    {
        return drawingThread;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        //start the new thread
        drawingThread.setRunning(true);
        drawingThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        //set height and width of the surface
        drawingThread.setSurfaceSize(width,height);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        drawingThread.setRunning(false);
        while(retry)
        {
            try {
                drawingThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}