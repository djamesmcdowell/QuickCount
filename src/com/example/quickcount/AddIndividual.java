package com.example.quickcount;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class AddIndividual extends DialogFragment {	
	
	AddIndividualListener mListener;
	Context context;
	
	public interface AddIndividualListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getTag());
        final View nView = inflater.inflate(R.layout.activity_popup_add_individual,null);
        builder.setView(nView)
               .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {                	    
                	   mListener.onDialogPositiveClick(AddIndividual.this);
                   }


               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   mListener.onDialogPositiveClick(AddIndividual.this);
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddIndividualListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
