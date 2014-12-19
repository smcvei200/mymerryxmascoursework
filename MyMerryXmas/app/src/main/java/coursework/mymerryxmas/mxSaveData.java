package coursework.mymerryxmas;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by stephen on 04/12/2014.
 */
public class mxSaveData extends Activity {
//Declare variables
SharedPreferences mxSharedPrefs;

private String mxPresent;
private String mxReindeer;
private String mxSong;
private String mxCity;
//Declare getters and setters

private void setmxPresent(String isPresent)
        {
        this.mxPresent = isPresent;
        }

public String getmxPresent()
        {
        return mxPresent;
        }

private void setmxReindeer(String isReindeer)
        {
        this.mxReindeer = isReindeer;
        }

public String getmxReindeer()
        {
        return mxReindeer;
        }

private void setmxSong(String isSong)
        {
        this.mxSong = isSong;
        }

public String getmxSong()
        {
        return mxSong;
        }

public void setmxCity(String isCity){this.mxCity = isCity;}

public String getmxCity(){return mxCity;}
//Declare constructor and date manipulation methods

public mxSaveData(SharedPreferences mxSDPrefs)
        {
        setmxPresent("");
        setmxReindeer("");
        setmxSong("");
        setmxCity("");

        try {
        this.mxSharedPrefs = mxSDPrefs;
        }
        catch(Exception e)
        {
        Log.e("n", "Pref Manager is NULL");
        }

        setDefaultPrefs();
        }

public void savePreferences(String key, boolean value)
        {
        SharedPreferences.Editor editor = mxSharedPrefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
        }

public void savePreferences(String key, String value)
        {
        SharedPreferences.Editor editor = mxSharedPrefs.edit();
        editor.putString(key, value);
        editor.commit();
        }

public void savePreferences(String key, int value)
        {
        SharedPreferences.Editor editor = mxSharedPrefs.edit();
        editor.putInt(key, value);
        editor.commit();
        }

public void setDefaultPrefs()
        {
            //save the user selected information
        savePreferences("mx_Present", "Empty");
        savePreferences("mx_Reindeer", "Empty");
        savePreferences("mx_Song", "Empty");
        savePreferences("mx_City", "Empty");
        }


}