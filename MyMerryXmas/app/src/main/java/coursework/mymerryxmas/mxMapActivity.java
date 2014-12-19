package coursework.mymerryxmas;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by stephen on 08/12/2014.
 */
public class mxMapActivity extends FragmentActivity{
    //declare variables
    List<mxMapData> mapDataLst;
    private Marker[] mapDataMarkerList = new Marker[5];
    private GoogleMap mapElves;
    int elf = 0;
    private LatLng latLangEKCentre = new LatLng(55.7591402, -4.1883331);
    private LatLng youAreHere;
    SharedPreferences mxSharedPrefs;
    Random random = new Random();

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.mx_map_view);

        mxSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    //determine which location the user selected and set appropriate coordinates
        if (mxSharedPrefs.getString("mx_City", "Empty") == "Aberdeen") {
            youAreHere = new LatLng(57.149717,-2.094278);
        } else if (mxSharedPrefs.getString("mx_City", "Empty") == "Birmingham") {
            youAreHere = new LatLng(33.520661, -86.802490);
        } else if (mxSharedPrefs.getString("mx_City", "Empty") == "Bradford") {
            youAreHere = new LatLng(53.795984, -1.759398);
        } else if (mxSharedPrefs.getString("mx_City", "Empty") == "Edinburgh") {
            youAreHere = new LatLng(55.953252, -3.188267);
        } else if (mxSharedPrefs.getString("mx_City", "Empty") == "Glasgow") {
            youAreHere = new LatLng(55.864237, -4.251806);
        } else if (mxSharedPrefs.getString("mx_City", "Empty") == "Leeds"){
            youAreHere = new LatLng(53.800755, -1.549077);
        }else if(mxSharedPrefs.getString("mx_City", "Empty") == "Liverpool"){
            youAreHere = new LatLng(53.408371, -2.991573);
        } else if(mxSharedPrefs.getString("mx_City", "Empty") == "London"){
            youAreHere = new LatLng(51.507351, -0.127758);
        } else if(mxSharedPrefs.getString("mx_City", "Empty") == "Manchester") {
            youAreHere = new LatLng(53.480759, -2.242631);
        } else if(mxSharedPrefs.getString("mx_City", "Empty") == "Sheffield"){
            youAreHere = new LatLng(53.381129, -1.470085);
        }
        //display message
        Toast.makeText(getApplicationContext(),"Locating Elves in your area!!!", Toast.LENGTH_LONG ).show();

        //create database object
        mapDataLst = new ArrayList<mxMapData>();
        mxMapDataDBMgr mapDB = new mxMapDataDBMgr(this, "mapElves.s3db", null, 1);
        try{
            mapDB.dbCreate();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        //retrieve information from the database
        mapDataLst = mapDB.allMapData();
        SetUpMap();
        AddMarkers();
    }

    public void SetUpMap()
    {
        //get the map and set initial location
        mapElves = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        if(mapElves != null){
            mapElves.moveCamera(CameraUpdateFactory.newLatLngZoom(youAreHere, 12));
            mapElves.setMyLocationEnabled(true);
            mapElves.getUiSettings().setCompassEnabled(true);
            mapElves.getUiSettings().setMyLocationButtonEnabled(true);
            mapElves.getUiSettings().setRotateGesturesEnabled(true);

        }

    }

    public void AddMarkers()
    {
        //declare variables
        MarkerOptions marker;
        mxMapData mapData;
        String mrkTitle;
        String mrkText;
        String mrkText2;

        for(int i = 0; i < mapDataLst.size();i++)
        {
            //for each item in the database retrieve the associated information
            mapData = mapDataLst.get(i);
            Log.e("here", mapData.getFirstname());
            mrkTitle = mapData.getFirstname();
            mrkText = "Occupation: " + mapData.getOccupation() ;

            //generate random number to scatter markers
            float random1 = random.nextFloat() * 2 -1;
            double random2 = (double)random1 / 50;

            //check which item from the database is currently active and set marker values accordingly
            switch (i){
                case 0:
                    marker = SetMarker(mrkTitle, mrkText, new LatLng(youAreHere.latitude + random2, youAreHere.longitude +random2),true);
                            mapDataMarkerList[i] = mapElves.addMarker(marker);
                    break;
                case 1:
                    marker = SetMarker(mrkTitle,mrkText, new LatLng(youAreHere.latitude - random2, youAreHere.longitude +random2),true);
                    mapDataMarkerList[i] = mapElves.addMarker(marker);
                    break;
                case 2:
                    marker = SetMarker(mrkTitle,mrkText, new LatLng(youAreHere.latitude, youAreHere.longitude),true);
                    mapDataMarkerList[i] = mapElves.addMarker(marker);
                    break;
                case 3:
                    marker = SetMarker(mrkTitle,mrkText, new LatLng(youAreHere.latitude +random2, youAreHere.longitude -random2),true);
                    mapDataMarkerList[i] = mapElves.addMarker(marker);
                    break;
                case 4:
                    marker = SetMarker(mrkTitle,mrkText,  new LatLng(youAreHere.latitude -random2, youAreHere.longitude - random2),true);
                    mapDataMarkerList[i] = mapElves.addMarker(marker);
                    break;
            }

        }
    }

    public MarkerOptions SetMarker(String title, String snippet, LatLng position, boolean centreAnchor)
    {

        float anchorX;
        float anchorY;

        if(centreAnchor)
        {
            anchorX = 0.5f;
            anchorY = 1.0f;
        }
        else
        {
            anchorX = 0.5f;
            anchorY = 1.0f;
        }

        MarkerOptions marker = new MarkerOptions().title(title).snippet(snippet).icon(BitmapDescriptorFactory.fromResource(R.drawable.elf3)).anchor(anchorX, anchorY).position(position);

        //retrieve different image for each elf being displayed
        switch(elf) {
            case 0:
                marker = new MarkerOptions().title(title).snippet(snippet).icon(BitmapDescriptorFactory.fromResource(R.drawable.buddy)).anchor(anchorX, anchorY).position(position);
                break;
            case 1:
                marker = new MarkerOptions().title(title).snippet(snippet).icon(BitmapDescriptorFactory.fromResource(R.drawable.elfie)).anchor(anchorX, anchorY).position(position);
                break;
            case 2:
                marker = new MarkerOptions().title(title).snippet(snippet).icon(BitmapDescriptorFactory.fromResource(R.drawable.elf3)).anchor(anchorX, anchorY).position(position);
                break;
            case 3:
                marker = new MarkerOptions().title(title).snippet(snippet).icon(BitmapDescriptorFactory.fromResource(R.drawable.elf4)).anchor(anchorX, anchorY).position(position);
                break;
            case 4:
                marker = new MarkerOptions().title(title).snippet(snippet).icon(BitmapDescriptorFactory.fromResource(R.drawable.elf5)).anchor(anchorX, anchorY).position(position);
                break;
            default:
                break;
        }
                elf++;
        return marker;
    }
}
