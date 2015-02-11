package com.example.japones;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi") public class LessonActivity extends Activity{
	
	private Integer [] mThumbIds = new Integer[6];
	private String [] mTextIds = new String[6];
	private JapaneseAdapter mDbHelper;
	private int level;
	private String table;
	private int question;
	private int questPosition;
	private Integer [] mAnswerIds = new Integer [6];
	private int count = 0;
	private int maxQuest = 2;
	private int maxTypeQuest = 2;
	private ResultData results; 
	
	static final int RESOLVER_QUESTION = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		   level = extras.getInt("level");
		   table = extras.getString("table");
		}
		
		results = new ResultData(level, table);
		
		japaneseDateBase();
       
		getViewQuestion();
		
	}

	private void getViewQuestion() {
		switch (selectQuestion()) {
		case 1: loadText();
				setContentView(R.layout.question_type_1);

				loadQuestionImage();		
				GridView mGridviewText = (GridView) findViewById(R.id.gridText);
				mGridviewText.setAdapter(new MyAdapter(this));
				
				mGridviewText.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
						verifyQuestionGrid (position, v);
						
					}
				});
				break;
		case 2: loadImages();
				setContentView(R.layout.question_type_2);

				loadQuestionText();
				
				GridView mGridviewImage = (GridView) findViewById(R.id.gridImage);
				mGridviewImage.setAdapter(new ImageAdapter(this));
				
				mGridviewImage.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
						verifyQuestionGrid (position, v);
						
					}
				});
				break;
		default:
			break;
		}		
	}

	private int selectQuestion(){
		Random ran = new Random();
		int x = ran.nextInt(maxTypeQuest) + 1;
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
            mTextView.setLayoutParams(new GridView.LayoutParams(85, 85));
            mTextView.setPadding(8, 8, 8, 8);
            mTextView.setGravity(Gravity.CENTER);
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
		 
		Cursor testdata = mDbHelper.getAnswerHiragana(level, table); 
		
		int i=0;
		testdata.moveToFirst();
	    do {
	    	getAnswersId (testdata, i);
	    	
	    	String aux =  table + "_" + testdata.getString(testdata.getColumnIndex("kanji"));
	    	int id = this.getResources().getIdentifier(aux, "drawable", this.getPackageName());
	    	mThumbIds[i] = id;
	    	
	    	i++;
	    } while (testdata.moveToNext());
	    
		mDbHelper.close();
	}
	
	public void loadText(){
        
		mDbHelper.open(); 
		 
		Cursor testdata = mDbHelper.getAnswerHiragana(level, table); 
		
		int i=0;
		testdata.moveToFirst();
	    do {
	    	getAnswersId (testdata, i);
	    	
	    	mTextIds[i] = testdata.getString(testdata.getColumnIndex("lectura"));
	    
	    	i++;
	    } while (testdata.moveToNext());
	    
		mDbHelper.close();
	}
	
	private void getAnswersId(Cursor testdata, int i) {
		int elementId = testdata.getInt(testdata.getColumnIndex("_id"));
    	if (elementId == this.question){
    		questPosition = i;
    	}
    	mAnswerIds[i] = elementId;		
	}

	public void loadQuestionImage(){
		mDbHelper.open();
		Cursor testdata = mDbHelper.getQuestion(level, table);
		getIdQuestion(testdata);
		try {
			testdata.moveToFirst();
			ImageView mImagetView = (ImageView) findViewById(R.id.imageQ);
	    	String aux = table + "_" + testdata.getString(testdata.getColumnIndex("kanji")); 
			mImagetView.setImageResource(this.getResources().getIdentifier(aux, "drawable", this.getPackageName()));
		} catch (Exception ex) {
			  throw new Error("UnableToGetQuestion"); 
		}
	}
	
	public void loadQuestionText(){
		mDbHelper.open();
		Cursor testdata = mDbHelper.getQuestion(level, table);
		getIdQuestion(testdata);
		try {
			testdata.moveToFirst();
			TextView mTextView = (TextView) findViewById(R.id.textQ);
	    	String aux =  testdata.getString(testdata.getColumnIndex("lectura"));
			mTextView.setText(aux);
		} catch (Exception ex) {
			  throw new Error("UnableToGetQuestion"); 
		}
	}
	
	private void getIdQuestion (Cursor mCursor){
		mCursor.moveToFirst();
		this.question = mCursor.getInt(mCursor.getColumnIndex("_id"));
	}
	
	private void verifyQuestionGrid (int position, View v){
		count++;

		int answer = mAnswerIds[position];
		VerifyActivity mResult = new VerifyActivity(question, answer);
		if (mResult.verifyAnswer()){
			v.setBackgroundColor(Color.GREEN);
			results.incrementWin();
		}else{
			v.setBackgroundColor(Color.RED);
			results.incrementLose();
		}	
		
		if (count < maxTypeQuest){
			Button mButton = new Button(this);
			mButton.setText("Next");
			
			LinearLayout ll = new LinearLayout(this); 
			//LinearLayout ll = (LinearLayout) findViewById(R.id.quest_layout);
			LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			ll.addView(mButton, lp);
			
			mButton.setOnClickListener(new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	getViewQuestion();
		        }
		    });
		    this.addContentView(ll, lp);
		}else{
			results.insertBD(mDbHelper);
			Intent intent = new Intent (LessonActivity.this, ResultsLesson.class);
         	startActivity(intent);	
		}
	}
}