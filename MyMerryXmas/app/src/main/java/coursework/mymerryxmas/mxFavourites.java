package coursework.mymerryxmas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by stephen on 04/12/2014.
 */
public class mxFavourites extends Activity implements View.OnClickListener {
    //declare variables
    SharedPreferences mxSharedPrefs;
    TextView tvTop;
    TextView tvReindeer;
    TextView tvSong;
    TextView tvCity;
    Button btnBack;
    MediaPlayer player;
    ImageView imgRein;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);

        Intent iMainAct = getIntent();
        //retrieve the views from the layout
        tvTop = (TextView)findViewById(R.id.tvTop);
        tvReindeer = (TextView)findViewById(R.id.tvReindeer);
        tvSong = (TextView)findViewById(R.id.tvSong);
        tvCity = (TextView)findViewById(R.id.tvCity);
        btnBack = (Button)findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        imgRein = (ImageView)findViewById(R.id.imgViewReindeer);
        mxSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //load the saved data
        loadSavedPreferences();
        //retrieve the appropriate image of reindeer from the drawable folder
        String sImagePath = "drawable/" + mxSharedPrefs.getString("mx_Reindeer","Empty").toLowerCase();
        Context appContext = getApplicationContext();
        int imgResID = appContext.getResources().getIdentifier(sImagePath, "drawable", "coursework.mymerryxmas");
        //set the image view with appropriate image
        imgRein.setImageResource(imgResID);
    //check which song the user selected and start playing
   if(mxSharedPrefs.getString("mx_Song", "Empty") == "Jingle Bell Rock") {
       player = MediaPlayer.create(mxFavourites.this, R.raw.jinglebell);
   }
   else if(mxSharedPrefs.getString("mx_Song", "Empty") == "Merry Xmas Everybody")
   {
       player = MediaPlayer.create(mxFavourites.this, R.raw.merryxmas);
   }
   else if(mxSharedPrefs.getString("mx_Song", "Empty") == "Santa Baby")
   {
       player = MediaPlayer.create(mxFavourites.this, R.raw.santababy);
   }
   else if(mxSharedPrefs.getString("mx_Song", "Empty") == "All I Want For XMas is You")
   {
       player = MediaPlayer.create(mxFavourites.this, R.raw.alliwant);
   }
   else if(mxSharedPrefs.getString("mx_Song", "Empty") == "FairyTale of New York"){
       player = MediaPlayer.create(mxFavourites.this, R.raw.fairytale);
   }
        player.start();

    }
    @Override
    public void onClick(View v) {
        //when the button is clicked stop the audio and return the user to the update screen
        player.stop();
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void loadSavedPreferences()
    {
        //retrieve the saved data
        tvReindeer.setText(tvReindeer.getText() + mxSharedPrefs.getString("mx_Reindeer", "Empty"));
        tvSong.setText(tvSong.getText() + mxSharedPrefs.getString("mx_Song", "Empty"));
        tvTop.setText(tvTop.getText() + mxSharedPrefs.getString("mx_Present", "Empty"));
        tvCity.setText(tvTop.getText() + mxSharedPrefs.getString("mx_City", "Empty"));
    }
}
