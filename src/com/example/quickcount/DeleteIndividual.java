package com.example.quickcount;

import java.util.ArrayList;
import java.util.List;

import com.example.quickcount.database.CountDBAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class DeleteIndividual extends DialogFragment {

	CountDBAdapter DB;
	ArrayAdapter<String> adapter;
	Spinner spinner;
	String out;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		DB = new CountDBAdapter(getActivity());
		final IndividualActivity refresh = (IndividualActivity)getActivity();
		final ArrayList<String> names = (ArrayList<String>) DB.GetAlllist();
		String[] name = names.toArray(new String[names.size()]);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Delete Individual");
		builder.setItems(name, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

				String name = names.get(which).toString();

				DB.deleteEntry(name);
				
				refresh.RefreshBoth();
			}
		});

		return builder.show();
	}

	private Context getContext() {
		// TODO Auto-generated method stub
		return null;
	}

}
