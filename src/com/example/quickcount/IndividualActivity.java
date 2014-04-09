package com.example.quickcount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.example.quickcount.database.CountDBAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class IndividualActivity extends ActionBarActivity implements
		AddIndividual.AddIndividualListener, OnItemSelectedListener {

	List<String> names = new ArrayList<String>();
	CountDBAdapter DB;
	ArrayAdapter<String> adapter;
	SimpleAdapter adapterList;
	Spinner spinner;
	Button addButton, subButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_individual);
		Calendar time = Calendar.getInstance();
		Date date = time.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date checkDate;
		try {
			
			checkDate = sdf.parse("2014-4-11");
			
			if(date.before(checkDate))
			{
				spinner = (Spinner) findViewById(R.id.individual_spinner);
				spinner.setOnItemSelectedListener(this);

				DB = new CountDBAdapter(this);

				List<HashMap<String, String>> nameList = DB.GetAllMain();
				List<String> nameListDropdown = DB.GetAlllist();

				adapter = new ArrayAdapter<String>(IndividualActivity.this,
						android.R.layout.simple_spinner_dropdown_item, nameListDropdown);
				spinner.setAdapter(adapter);

				adapterList = new SimpleAdapter(IndividualActivity.this, nameList,
						R.layout.row, new String[] { "name", "count" }, new int[] {
								R.id.first, R.id.third });

				ListView listView = (ListView) findViewById(R.id.individual_listview);
				listView.setAdapter(adapterList);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void AddIndividual() {
		DialogFragment dialog = new AddIndividual();
		dialog.show(getFragmentManager(), "Add Individual");
	}

	private void DeleteIndividual() {
		DialogFragment dialog = new DeleteIndividual();
		dialog.show(getFragmentManager(), "Delete Individual");
		RefreshBoth();
	}

	//Add Individual event listeners
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		DB.GetAllMain();
		CountDBAdapter countdb = new CountDBAdapter(this);
		EditText individualtext = (EditText) dialog.getDialog().findViewById(
				R.id.popup_text_add_individuals);
		String name = individualtext.getText().toString();

		countdb.insertEntry(name);
		
		RefreshBoth();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		
	}

	//Main page count add and count minus Buttons
	@Override
	public void onItemSelected(final AdapterView<?> parent, View view,
			final int position, long id) {		
		addButton = (Button) findViewById(R.id.individual_button_add);
		subButton = (Button) findViewById(R.id.individual_button_subtract);

		subButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DB.GetAllMain();
				
				String name = (String) parent.getItemAtPosition(position);

				DB.DeleteOne(name);
				
				RefreshMain();
			}
		});

		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				DB.GetAllMain();
				
				String name = (String) parent.getItemAtPosition(position);

				DB.AddOne(name);
				
				RefreshMain();
			}
		});
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
	}
	
	//Refresh Main page and Spinner
	public void RefreshBoth()
	{
		CountDBAdapter countdb = new CountDBAdapter(IndividualActivity.this);
		List<HashMap<String, String>> nameList = countdb.GetAllMain();
		
		adapter.clear();
		List<String> nameListDropdown = countdb.GetAlllist();

		adapter = new ArrayAdapter<String>(IndividualActivity.this,
				android.R.layout.simple_spinner_dropdown_item, nameListDropdown);
		spinner.setAdapter(adapter);
		
		adapterList = new SimpleAdapter(IndividualActivity.this, nameList,
				R.layout.row, new String[] { "name", "count" }, new int[] {
						R.id.first, R.id.third });

		ListView listView = (ListView) findViewById(R.id.individual_listview);
		listView.setAdapter(adapterList);
		adapterList.notifyDataSetChanged();
	}
	
	private void RefreshMain()
	{
		CountDBAdapter countdb = new CountDBAdapter(IndividualActivity.this);
		List<HashMap<String, String>> nameList = countdb.GetAllMain();
		
		adapterList = new SimpleAdapter(IndividualActivity.this, nameList,
				R.layout.row, new String[] { "name", "count" }, new int[] {
						R.id.first, R.id.third });

		ListView listView = (ListView) findViewById(R.id.individual_listview);
		listView.setAdapter(adapterList);
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.activity_individual,
					container, false);
			return rootView;
		}
	}
	
	//ActionBar onSelect
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.sub_minus: {
			DeleteIndividual();
			return true;
		}
		case R.id.sub_add: {
			AddIndividual();
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.individual, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
