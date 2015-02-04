package com.example.japones;

import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    
    public Cursor getImagesHiragana (){
    	String sql = "SELECT kanji FROM hiragana WHERE nivel=1";
    	Cursor mCur = mDb.rawQuery(sql, null); 
    	return mCur; 
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
