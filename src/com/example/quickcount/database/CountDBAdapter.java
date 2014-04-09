package com.example.quickcount.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class CountDBAdapter {
	public SQLiteDatabase db;
	DBAdapter instance;

	public CountDBAdapter(Context _context) {
		instance = new DBAdapter(_context);
		instance.open();
		db = instance.getDatabaseInstance();
	}

	// Start of Personal Methods
	public void insertEntry(String name) {
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put(DBAdapter.COUNT_COLUMN_NAME, name);
		newValues.put(DBAdapter.COUNT_COLUMN_COUNT, 0);

		// Insert the row into your table
		//test
		long status = db.insert(DBAdapter.TABLE_COUNT, null, newValues);

		if (status == -1) {
			Toast.makeText(instance.context, "Not Inserted COUNT",
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(instance.context, "Added " + name, Toast.LENGTH_LONG)
					.show();
		}

	}

	public void AddOne(String name) {
		ContentValues newValue = new ContentValues();
		
		Cursor cursor = db.query(DBAdapter.TABLE_COUNT, null, " NAME=?",
				new String[] { name }, null, null, null);
		if (cursor.getCount() < 1) // UserName Not Exist
		{
			cursor.close();
		}
		cursor.moveToFirst();
		int count = cursor.getInt(cursor.getColumnIndex(DBAdapter.COUNT_COLUMN_COUNT));
		cursor.close();
		
		int newcount = count + 1;
		
		newValue.put(DBAdapter.COUNT_COLUMN_COUNT, newcount);
		
		String where = "NAME= '" + name + "'";
		db.update(DBAdapter.TABLE_COUNT, newValue, where, null);
	}
	
	public void DeleteOne(String name) {
		ContentValues newValue = new ContentValues();
		
		Cursor cursor = db.query(DBAdapter.TABLE_COUNT, null, " NAME=?",
				new String[] { name }, null, null, null);
		if (cursor.getCount() < 1) // UserName Not Exist
		{
			cursor.close();
		}
		cursor.moveToFirst();
		int count = cursor.getInt(cursor.getColumnIndex(DBAdapter.COUNT_COLUMN_COUNT));
		cursor.close();
		
		if(count > 0)
		{
			int newcount = count - 1;	
			
			newValue.put(DBAdapter.COUNT_COLUMN_COUNT, newcount);
			
			String where = "NAME= '" + name + "'";
			db.update(DBAdapter.TABLE_COUNT, newValue, where, null);
		}
	}

	public int deleteEntry(String name) {
		String where = " NAME=?";
		int numberOFEntriesDeleted = db.delete(DBAdapter.TABLE_COUNT, where,
				new String[] { name });
		return numberOFEntriesDeleted;
	}

	public int GetCount(String userName) {
		Cursor cursor = db.query(DBAdapter.TABLE_COUNT, null, " NAME=?",
				new String[] { userName }, null, null, null);
		if (cursor.getCount() < 1) // UserName Not Exist
		{
			cursor.close();
			return -1;
		}
		cursor.moveToFirst();
		int count = cursor.getInt(cursor.getColumnIndex(DBAdapter.COUNT_COLUMN_COUNT));
		cursor.close();
		
		return count;
	}	

	public void updateEntry(String userName, String password) {
		// Define the updated row content.
		ContentValues updatedValues = new ContentValues();
		// Assign values for each row.
		updatedValues.put("USERNAME", userName);
		updatedValues.put("COLUMN_PASSWORD", password);

		String where = "USERNAME = ?";
		db.update("TABLE", updatedValues, where, new String[] { userName });
	}



	public ArrayList<HashMap<String, String>> GetAllMain() {
		 List<String> nameList = new ArrayList<String>();
		 ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();;
		 String where = null;
		 Cursor c = db.query(true, DBAdapter.TABLE_COUNT, DBAdapter.COUNT_ALLCOLUMNS,
		 where, null, null, null, null, null);
		 if (c != null) {
		 c.moveToFirst();
		 }
		 
		 int i = 0;
		 while(c.isAfterLast() == false)
		 {
			 HashMap<String, String> map = new HashMap<String, String>();
			 String names = c.getString(c.getColumnIndex(DBAdapter.COUNT_COLUMN_NAME));
			 Integer count = c.getInt(c.getColumnIndex(DBAdapter.COUNT_COLUMN_COUNT));
			 
			 map.put("name", names);
			 map.put("count", count.toString());			 
			 nameList.add(names);
			 mylist.add(map);
			 c.moveToNext();
		 }		 
		 return mylist;
	}
	
	public List<String> GetAlllist() {
		 List<String> nameList = new ArrayList<String>();
		 String where = null;
		 Cursor c = db.query(true, DBAdapter.TABLE_COUNT, DBAdapter.COUNT_ALLCOLUMNS,
		 where, null, null, null, null, null);
		 if (c != null) {
		 c.moveToFirst();
		 }
		 
		 int i = 0;
		 while(c.isAfterLast() == false)
		 {
			 String names = c.getString(c.getColumnIndex(DBAdapter.COUNT_COLUMN_NAME));
			 Integer count = c.getInt(c.getColumnIndex(DBAdapter.COUNT_COLUMN_COUNT));
			 		 
			 nameList.add(names);

			 c.moveToNext();
		 }		 
		 return nameList;
	}
}
