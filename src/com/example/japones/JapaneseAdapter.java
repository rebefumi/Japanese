package com.example.japones;

import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    
    public Cursor getAnswerHiragana (int level, String table){
    	String sql = "";

    	Cursor mCount= mDb.rawQuery("SELECT count(_id) FROM "+ table +" WHERE nivel ="+ level, null);
    	mCount.moveToFirst();
    	int count= mCount.getInt(0);
    	mCount.close();
    	
    	if (count < 6){
    		sql = "SELECT * FROM "+ table +" WHERE nivel != "+level+" ORDER BY RANDOM() LIMIT 1";
        	Cursor mCurId = mDb.rawQuery(sql, null); 
        	int id = 0;
        	if (mCurId.moveToFirst()) {
        		id = mCurId.getInt(mCurId.getColumnIndex("_id"));
        	}
        	mCurId.close();
        	sql = "SELECT * FROM "+ table +" WHERE nivel="+ level +" OR _id =" + id + " ORDER BY RANDOM() LIMIT 6";
    	}
    	else{
        	sql = "SELECT * FROM "+ table +" WHERE nivel="+ level +" ORDER BY RANDOM() LIMIT 6";
    	}
    	
    	Cursor mCur = mDb.rawQuery(sql, null); 
    	return mCur; 
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
    
//     public Cursor getTestData() { 
//         try { 
//             String sql ="   " ; 
// 
//             Cursor mCur = mDb.rawQuery(sql, null); 
//             if (mCur!=null) { 
//                mCur.moveToNext(); 
//             } 
//             return mCur; 
//         } catch (SQLException mSQLException) { 
//             throw mSQLException; 
//         } 
//     }
     
     

// 	public boolean SaveEmployee(String name, String email) {
// 		try{
// 			ContentValues cv = new ContentValues();
// 			cv.put("Name", name);
// 			cv.put("Email", email);
// 			
// 			mDb.insert("Employees", null, cv);
//
// 			return true;
// 			
// 		}
// 		catch(Exception ex){
// 			return false;
// 		}
// 	}
}
