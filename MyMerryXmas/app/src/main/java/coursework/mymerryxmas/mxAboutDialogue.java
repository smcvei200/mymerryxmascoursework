package coursework.mymerryxmas;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

/**
 * Created by stephen on 19/11/2014.
 */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public class mxAboutDialogue extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle SavedInstanceState) {
            //instantiate a new dialogue box
            AlertDialog.Builder mxAboutDialog = new AlertDialog.Builder(getActivity());
            //set the text to be displayed in the dialogue and add an ok button
            mxAboutDialog.setMessage("This app allows you to see elves in your area and get updates from the North Pole!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            //set the box title
            mxAboutDialog.setTitle("About");
            //retrieve the icon from the drawable folder
            mxAboutDialog.setIcon(R.drawable.ic_menu_action_about);
            //return and display the dialogue box
            return mxAboutDialog.create();
        }

    }

