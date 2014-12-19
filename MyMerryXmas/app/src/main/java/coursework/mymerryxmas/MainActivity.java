package coursework.mymerryxmas;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    //Declare variables
    Spinner reinSpin;
    Spinner songSpin;
    Spinner areaSpin;
    Button newsUpdate;
    mxSaveData mxSDPrefs;
    SharedPreferences mySharedPrefs;
    EditText etPresent;
    FragmentManager fmAboutDialogue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //retrieve views from xml file and populate spinners
        reinSpin = (Spinner)findViewById(R.id.reinSpin);
        String[] reindeer={"Dasher", "Dancer", "Prancer", "Vixen", "Comet", "Cupid", "Donner", "Blitzen", "Rudolph"};
        ArrayAdapter<String> stringArrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,reindeer);
        reinSpin.setAdapter(stringArrayAdapter);

        songSpin = (Spinner)findViewById(R.id.songSpin);
        String[] songs={"Jingle Bell Rock", "Merry Xmas Everybody", "Santa Baby", "All I Want For XMas is You", "FairyTale of New York"};
        ArrayAdapter<String> stringArrayAdapter2= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,songs);
        songSpin.setAdapter(stringArrayAdapter2);

        areaSpin = (Spinner)findViewById(R.id.areaSpin);
        String[] cities={"Aberdeen","Birmingham","Bradford", "Edinburgh", "Glasgow","Leeds","Liverpool","London","Manchester",    "Sheffield"   };
        ArrayAdapter<String> stringArrayAdapter3= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,cities);
        areaSpin.setAdapter(stringArrayAdapter3);

        newsUpdate = (Button)findViewById(R.id.btnNews);
        //attach onclick listener to button
        newsUpdate.setOnClickListener(this);

        etPresent = (EditText)findViewById(R.id.etPresent);

        //instantiate shared preferences
        mySharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mxSDPrefs = new mxSaveData(mySharedPrefs);
        mxSDPrefs.setDefaultPrefs();

        fmAboutDialogue = this.getFragmentManager();
    }

    @Override
    public void onClick(View v) {

        //save preferences selected by user
        mxSDPrefs.savePreferences("mx_Present", etPresent.getText().toString() );
        mxSDPrefs.savePreferences("mx_Reindeer", reinSpin.getSelectedItem().toString());
        mxSDPrefs.savePreferences("mx_Song",songSpin.getSelectedItem().toString());
        mxSDPrefs.savePreferences("mx_City",areaSpin.getSelectedItem().toString());

        //Starting a new Thread
        Intent mx_NewsUpdate = new Intent(getApplicationContext(), mxUpdate.class);
        //Start the new Activity
        startActivity(mx_NewsUpdate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //create menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mc_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //check which menu item has been selected
        switch(item.getItemId()){
            //if draw item selected start draw activity
            case R.id.mDraw:
                Intent mxDraw = new Intent(this, mxDrawActivity.class);
                this.startActivity(mxDraw);
                return true;
            //if map item selected save selected area and start map activity
            case R.id.mMap:
                 mxSDPrefs.savePreferences("mx_City",areaSpin.getSelectedItem().toString());
                Intent mcMap = new Intent(this, mxMapActivity.class);
                this.startActivity(mcMap);
                return true;
            //if quit item selected close app
            case R.id.mQuit:
                finish();
                return true;
            //if about item selected show about dialogue
            case R.id.mAbout:
                DialogFragment mxAboutDlg = new mxAboutDialogue();
                mxAboutDlg.show(fmAboutDialogue, "mc_About_dlg");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
