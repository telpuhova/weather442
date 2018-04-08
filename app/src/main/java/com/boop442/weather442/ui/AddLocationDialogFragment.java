package com.boop442.weather442.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.boop442.weather442.Constants;
import com.boop442.weather442.R;
import com.boop442.weather442.models.Location;

import org.parceler.Parcels;

/**
 * Created by boop442 on 3/16/2018.
 */

public class AddLocationDialogFragment extends DialogFragment {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    //new
    private DialogInterface.OnDismissListener onDismissListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Context context = getActivity().getApplicationContext();

//        mSharedPreferences = getActivity().getSharedPreferences("numberPicker.preferences", 0);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSharedPreferences.edit();


        final View rootView = inflater.inflate(R.layout.fragment_addloc_dialog, container, false);

        Button cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        Button submitButton = (Button) rootView.findViewById(R.id.submitButton);
        final EditText inputLocationEditText = (EditText) rootView.findViewById(R.id.inputLocationEditText);

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String newLocation = inputLocationEditText.getText().toString();
                Log.d("testing", newLocation);

//                Location bundleLocationObject = Parcels.unwrap(getArguments().getParcelable("locationObject"));
//                bundleLocationObject.setTitle(newLocation);

                addToSharedPreferences(newLocation);

                dismiss();
            }
        });

        return rootView;
    }

    private void addToSharedPreferences(String location) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, location).apply();
    }

    //new
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        if(onDismissListener != null){
            onDismissListener.onDismiss(dialog);
        }
//        final Activity activity = getActivity();
//        if (activity instanceof DialogInterface.OnDismissListener) {
//            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
//        }
    }


}
