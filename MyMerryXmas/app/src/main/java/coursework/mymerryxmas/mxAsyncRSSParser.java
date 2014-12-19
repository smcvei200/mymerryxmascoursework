package coursework.mymerryxmas;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.net.MalformedURLException;

/**
 * Created by stephen on 04/12/2014.
 */
public class mxAsyncRSSParser  extends AsyncTask<String, Integer, mxRSSDataItem>
{
    //declare variables
    private Context appContext;
    private String urlRSSToParse;

    public mxAsyncRSSParser(Context currentAppContext, String urlRSS)
    {
        //constructor to set the variables
        appContext = currentAppContext;
        urlRSSToParse = urlRSS;
    }

    // A callback method executed on UI thread on starting the task
    @Override
    protected void onPreExecute() {
        // Message to indicate start of parsing
        Toast.makeText(appContext, "Parsing started!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected mxRSSDataItem doInBackground(String... params)
    {
        mxRSSDataItem parsedData;
        //create new parser object
        mxRSSParser rssParser = new mxRSSParser();
        try {
            //call to parse the data from the supplied URL
            rssParser.parseRSSData(urlRSSToParse);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //store the parsed data
        parsedData = rssParser.getRSSDataItem();

        //return the parsed data
        return parsedData;
    }



    // A callback method executed on UI thread, invoked after the completion of the task
    // When doInbackground has completed, the return value from that method is passed into this event
    // handler.
    @Override
    protected void onPostExecute(mxRSSDataItem result) {
        // Message to indicate end of parsing
        Toast.makeText(appContext,"Parsing finished!", Toast.LENGTH_SHORT).show();
    }


}
