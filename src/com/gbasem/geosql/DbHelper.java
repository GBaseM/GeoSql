package com.gbasem.geosql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Spinner;
/**
 * sqlite database helper to create table into SQLite database
 * @author ketan(Visit my <a
 *         href="http://androidsolution4u.blogspot.in/">blog</a>)
 */
public class DbHelper extends SQLiteOpenHelper {
	static String DATABASE_NAME="userdata";
	public static final String TABLE_NAME="user";
	public static final String KEY_FNAME="fname";
	public static final String KEY_LNAME="lname";
	public static final String KEY_ID="id";
	public static final String KEY_MODE="";
	
	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY, "+KEY_FNAME+" TEXT, "+KEY_LNAME+" TEXT,"+KEY_MODE+"TEXT)";
		db.execSQL(CREATE_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		onCreate(db);

	}

}
