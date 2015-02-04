package com.example.japones;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JapaneseDBHelper extends SQLiteOpenHelper {

	private static String DB_PATH = "";  
	private static String DB_NAME ="japanese.sqlite";
	private SQLiteDatabase mDataBase;  
	private final Context mContext; 
	private static int version = 1; 

	public JapaneseDBHelper(Context context) {
		super(context, DB_NAME, null, version);
	    DB_PATH = "/data/data/" + context.getPackageName() + "/databases/"; 
		this.mContext = context; 
	}
	
	public void createDataBase() throws IOException { 
	    boolean mDataBaseExist = checkDataBase(); 
	    if(!mDataBaseExist) 
	    { 
	        this.getReadableDatabase(); 
	        try{ 
	            copyDataBase(); 
	        }catch (IOException mIOException) { 
	    		throw new Error("ErrorCopyingDataBase"); 
	        } 
	    } 
	} 
	
    private boolean checkDataBase() {
    	
       File dbFile = new File(DB_PATH + DB_NAME); 
       if (dbFile.exists()){
    	
    	   SQLiteDatabase checkDB = null;

    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	

    	if(checkDB != null){
    		checkDB.close();
    	}

    	return checkDB != null ? true : false;
       }else{
    	   return false;
       }
    } 

    private void copyDataBase() throws IOException { 
        InputStream mInput = mContext.getAssets().open(DB_NAME); 
        String outFileName = DB_PATH + DB_NAME; 
        OutputStream mOutput = new FileOutputStream(outFileName); 
        byte[] mBuffer = new byte[1024]; 
        int mLength; 
        while ((mLength = mInput.read(mBuffer))>0) { 
            mOutput.write(mBuffer, 0, mLength); 
        } 
        mOutput.flush(); 
        mOutput.close(); 
        mInput.close(); 
    } 
 
    public boolean openDataBase() throws SQLException { 
        String mPath = DB_PATH + DB_NAME; 
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY); 
        return mDataBase != null; 
    } 
 
    @Override 
    public synchronized void close() { 
        if(mDataBase != null) 
            mDataBase.close(); 
        super.close(); 
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
