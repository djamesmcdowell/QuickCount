package com.example.quickcount.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	static final String DATABASE_NAME = "QCount.db";
	static final int DATABASE_VERSION = 2;

	public static final String TABLE_COUNT = "COUNT";

	// Login table columns
	public static final String COUNT_COLUMN_ID = "ID";
	public static final String COUNT_COLUMN_NAME = "NAME";
	public static final String COUNT_COLUMN_COUNT = "COUNT";

	public static String[] COUNT_ALLCOLUMNS = { COUNT_COLUMN_ID, COUNT_COLUMN_NAME, COUNT_COLUMN_COUNT};

	// TODO: Create public field for each column in your table.
	// SQL Statement to create a new database.
	static final String CREATE_TABLE_COUNT = " create table " + TABLE_COUNT
			+ "(" + COUNT_COLUMN_ID + " integer primary key autoincrement, "
			+ COUNT_COLUMN_NAME + " text, " 
			+ COUNT_COLUMN_COUNT + " integer );";

	// Variable to hold the database instance
	public SQLiteDatabase db;
	// Context of the application using the database.
	public final Context context;
	// Database open/upgrade helper
	private DataBaseHelper dbHelper;

	public DBAdapter(Context _context) {
		context = _context;
		dbHelper = new DataBaseHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public DBAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		db.close();
	}

	public SQLiteDatabase getDatabaseInstance() {

		return db;
	}
}
