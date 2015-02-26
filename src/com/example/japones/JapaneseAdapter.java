package com.example.japones;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class JapaneseAdapter {

	private final Context mContext; 
	private SQLiteDatabase mDb; 
	private JapaneseDBHelper mDbHelper; 

	public JapaneseAdapter(Context context) { 
		this.mContext = context; 
		mDbHelper = new JapaneseDBHelper(mContext); 
	} 

	public JapaneseAdapter createDatabase() throws SQLException { 
		try{ 
			mDbHelper.createDataBase(); 
		}catch (IOException mIOException) {
			throw new Error("UnableToCreateDatabase"); 
		} 
		return this; 
	} 

	public JapaneseAdapter open() throws SQLException { 
		try { 
			mDbHelper.openDataBase(); 
			mDbHelper.close(); 
			mDb = mDbHelper.getReadableDatabase(); 
		} catch (SQLException mSQLException) { 
			throw mSQLException; 
		} 
		return this; 
	} 

	public void close() { 
		mDbHelper.close(); 
	} 

	public Cursor getAnswerHiragana (int level, String table, int id){
		String sql = "";

		Cursor mCount= mDb.rawQuery("SELECT count(_id) FROM "+ table +" WHERE nivel ="+ level, null);
		mCount.moveToFirst();
		int count= mCount.getInt(0);
		mCount.close();

		Cursor mCurExtra = null;
		
		sql = "SELECT * FROM ( SELECT DISTINCT * FROM "+ table +" WHERE nivel="+ level +" AND _id != "+ id +" ORDER BY RANDOM() LIMIT 5";
		sql += ") UNION ALL SELECT * FROM (SELECT * FROM " + table +" WHERE _id=" +  id + ")";
		
		if (count < 6){
			sql += " UNION ALL SELECT * FROM (SELECT * FROM "+ table +" WHERE nivel != "+level+" ORDER BY RANDOM() LIMIT " + (6-count)+")";
		}
	
		Cursor mCursor = mDb.rawQuery(sql, null);
		
		return mCursor; 
	}

	public Cursor getQuestion(int level, String table){
		String sql = "SELECT * FROM "+ table +" WHERE nivel="+ level +" ORDER BY RANDOM() LIMIT 1";
		Cursor mCur = mDb.rawQuery(sql, null);
		return mCur;
	}

	public boolean insertStadistics (ResultData result){
		try{
			ContentValues cv = new ContentValues();
			cv.put("correct", result.getQuestWin());
			cv.put("fail", result.getQuestLose());
			cv.put("curse", result.getCurse());
			cv.put("lesson", result.getLesson());
			cv.put("date", "datetime()");

			mDb.insert("results", null, cv);
			
			return true;

		}
		catch(Exception ex){
			return false;

		}

	}

	public Cursor getStats(int id){
		String sql = "SELECT * FROM results WHERE _id="+ id;
		Cursor mCur = mDb.rawQuery(sql, null);
		return mCur;
	}
}
