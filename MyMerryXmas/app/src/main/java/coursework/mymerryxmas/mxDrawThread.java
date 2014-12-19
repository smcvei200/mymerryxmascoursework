package coursework.mymerryxmas;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by rla on 29/10/2014.
 */
public class mxDrawThread extends Thread
{
    //declare variables
    private int canvasWidth;
    private int canvasHeight;
    private float xPos = 0.0f;
    private float yPos = 0.0f;
    private int i;

    private float HalfAppletHeight;
    private float HalfAppletWidth;

    private boolean first = true;
    private boolean run = false;

    private SurfaceHolder shDrawSurface;
    private Paint paint;
    private mxDrawSurfaceView drawSF;

    private Context appContext;

    public mxDrawThread(SurfaceHolder surfaceHolder, mxDrawSurfaceView drawSurfV,Context context) {
        //constructor to set variable values
        this.shDrawSurface = surfaceHolder;
        this.drawSF = drawSurfV;
        paint = new Paint();
        appContext = context;
    }

    public void doStart() {
        synchronized (shDrawSurface) {
            first = false;

        }
    }

    public void run() {
        //lock the canvas while drawing is taking place and then unlock and post
        while (run) {
            Canvas c = null;
            try {
                c = shDrawSurface.lockCanvas(null);
                synchronized (shDrawSurface) {
                    svDraw(c);
                }
            } finally {
                if (c != null) {
                    shDrawSurface.unlockCanvasAndPost(c);
                }
            }
        }
    }

    public void setRunning(boolean b) {
        run = b;
    }
    public void setSurfaceSize(int width, int height) {
        synchronized (shDrawSurface) {
            //set the size of the canvas
            canvasWidth = width;
            canvasHeight = height;
            HalfAppletHeight = canvasHeight / 2;
            HalfAppletWidth  = canvasWidth / 32;
            doStart();
        }
    }


    private void svDraw(Canvas canvas) {
        if(run) {
            //set the values of colour, size and font for the canvas
            canvas.save();
            canvas.restore();
            canvas.drawColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            //drawAxes(canvas);
            paint.setTextSize(75);
            paint.setColor(Color.GREEN);
            //create custom font
            Typeface newFont = Typeface.createFromAsset(appContext.getAssets(), "matura.TTF");
            //set the font
            paint.setTypeface(newFont);
            //draw the text at the provided coordinates
            canvas.drawText("Merry", 100, 200, paint);
            canvas.drawText("Christmas!!!", 100, 300, paint);
            //retrieve image from drawable folder
            BitmapDrawable drawable = (BitmapDrawable)appContext.getResources()
                    .getDrawable(R.drawable.santa);
            Bitmap bitmap = drawable.getBitmap();
            //draw image on the canvas
            canvas.drawBitmap(bitmap, 200.0f, 400.0f, paint);

        }
    }

}