package com.example.japones;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LessonActivity extends Activity{
	
	private Integer [] mThumbIds = new Integer[6];
	private String [] mTextIds = new String[6];
	private JapaneseAdapter mDbHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		japaneseDateBase();
       
		switch (selectQuestion()) {
		case 1: loadText();
				setContentView(R.layout.question_type_1);

				loadQuestionImage();		
				GridView mGridviewText = (GridView) findViewById(R.id.gridText);
				mGridviewText.setAdapter(new MyAdapter(this));
				
				mGridviewText.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
						Intent intent = new Intent(LessonActivity.this, VerifyActivity.class);
		             	//intent.putExtra ("level", level);
		             	//startActivityForResult(intent, requestCode, options);
					}
				});
				break;
		case 2: 
		case 3:
		case 4: loadImages();
				setContentView(R.layout.question_type_2);

				loadQuestionText();
				
				GridView mGridviewImage = (GridView) findViewById(R.id.gridImage);
				mGridviewImage.setAdapter(new ImageAdapter(this));
				
				mGridviewImage.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
						Toast.makeText(LessonActivity.this, "" + position, Toast.LENGTH_SHORT).show();
					}
				});
				break;
		default:
			break;
		}
	}

	private int selectQuestion(){
		Random ran = new Random();
		int x = ran.nextInt(4) + 1;
		return x;
	}

	
public class MyAdapter extends BaseAdapter {
    private Context mContext;

    public MyAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mTextIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent ) {
    	TextView mTextView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            mTextView = new TextView (getBaseContext());
            
        }else{
        	mTextView = (TextView) convertView;
        }
        
        mTextView.setText(mTextIds[position]);
        return mTextView;
    }
}

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

}

	public void japaneseDateBase (){
		mDbHelper = new JapaneseAdapter(this);         
		mDbHelper.createDatabase(); 
	}
	
	public void loadImages(){
	         
		mDbHelper.open(); 
		 
		Cursor testdata = mDbHelper.getAnswerHiragana(1, "hiragana"); 
		
		int i=0;
		testdata.moveToFirst();
	    do {
	    	String aux =  testdata.getString(testdata.getColumnIndex("kanji"));
	    	int id = this.getResources().getIdentifier(aux, "drawable", this.getPackageName());
	    	mThumbIds[i] = id;
	    	i++;
	    } while (testdata.moveToNext());
	    
		mDbHelper.close();
	}
	
	public void loadText(){
        
		mDbHelper.open(); 
		 
		Cursor testdata = mDbHelper.getAnswerHiragana(1, "hiragana"); 
		
		int i=0;
		testdata.moveToFirst();
	    do {
	    	mTextIds[i] = testdata.getString(testdata.getColumnIndex("lectura"));
	    	i++;
	    } while (testdata.moveToNext());
	    
		mDbHelper.close();
	}
	
	public void loadQuestionImage(){
		mDbHelper.open();
		Cursor testdata = mDbHelper.getQuestion(1, "hiragana");
		try {
			testdata.moveToFirst();
			ImageView mImagetView = (ImageView) findViewById(R.id.imageQ);
	    	String aux =  testdata.getString(testdata.getColumnIndex("kanji")); 
			mImagetView.setImageResource(this.getResources().getIdentifier(aux, "drawable", this.getPackageName()));
		} catch (Exception ex) {
			  throw new Error("UnableToGetQuestion"); 
		}
	}
	
	public void loadQuestionText(){
		mDbHelper.open();
		Cursor testdata = mDbHelper.getQuestion(1, "hiragana");
		try {
			testdata.moveToFirst();
			TextView mTextView = (TextView) findViewById(R.id.textQ);
	    	String aux =  testdata.getString(testdata.getColumnIndex("lectura"));
			mTextView.setText(aux);
		} catch (Exception ex) {
			  throw new Error("UnableToGetQuestion"); 
		}
	}
}