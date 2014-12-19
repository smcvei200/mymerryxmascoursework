package coursework.mymerryxmas;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Created by stephen on 04/12/2014.
 */
public class mxUpdate extends Activity implements View.OnClickListener {
    //declare variables
    TextView news;
    Button ok;
    Button viewFav;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mxnews_update);
        //retrieve views from layout
        news = (TextView)findViewById(R.id.tvNews);
        ok = (Button)findViewById(R.id.btnOk);
        viewFav = (Button)findViewById(R.id.btnFav);

        //attach listener to the buttons
        ok.setOnClickListener(this);
        viewFav.setOnClickListener(this);

        Intent iMainAct = getIntent();

        //Get North Pole Update from RSS feed
        mxRSSDataItem northPoleNews = new mxRSSDataItem();
        String RSSFeedURL = "http://mymerrychristmas.com/forum/external.php?forumids=35";
        mxAsyncRSSParser rssAsyncParse = new mxAsyncRSSParser(this, RSSFeedURL);
        try{
            northPoleNews = rssAsyncParse.execute("").get();
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(ExecutionException e){
            e.printStackTrace();
        }
        //set the text view with the parsed information
        news.setText(northPoleNews.getItemDesc());
    }

    @Override
    public void onClick(View v) {
        //if ok button pressed return user to home screen
        if(ok.isPressed()) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        //if view fav button pressed navigate to favourites screen
        else if(viewFav.isPressed())
        {
            //Starting a new Thread
            Intent mcSave_Screen = new Intent(getApplicationContext(), mxFavourites.class);
            startActivity(mcSave_Screen);
        }

    }
}
