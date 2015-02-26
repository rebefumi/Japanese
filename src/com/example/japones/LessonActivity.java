package com.example.japones;

import java.util.ArrayList;
import java.util.Collections;
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
	private Integer [] mAnswerIds = new Integer [6];
	private ArrayList<Integer> numbers = new ArrayList<Integer>();
	
	private int level;
	private String table;
	
	private int question;
	private int questPosition;
	
	private int count = 0;
	private int MAX_QUEST = 2;
	private int MAX_TYPE_QUEST = 2;
	
	private JapaneseAdapter mDbHelper;
	private ResultData results; 
	private GridView mGridView;
	

	
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
		for (int i=0; i<=5; i++){
			numbers.add(i);
		}
		
		Collections.shuffle(numbers);
		
		switch (selectQuestion()) {
		case 1: setContentView(R.layout.question_type_1);

				loadQuestionImage();	
				loadText();
				mGridView = (GridView) findViewById(R.id.gridText);
				mGridView.setAdapter(new MyAdapter(this));
				
				mGridView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
						verifyQuestionGrid (position, v, parent);
						
					}
				});
				break;
		case 2: setContentView(R.layout.question_type_2);

				loadQuestionText();
				loadImages();
				
				mGridView = (GridView) findViewById(R.id.gridImage);
				mGridView.setAdapter(new ImageAdapter(this));
				
				mGridView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
						verifyQuestionGrid (position, v, parent);
						
					}
				});
				break;
		default:
			break;
		}		
	}

	private int selectQuestion(){
		Random ran = new Random();
		int x = ran.nextInt(MAX_TYPE_QUEST) + 1;
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
		 
		Cursor testdata = mDbHelper.getAnswerHiragana(level, table, this.question); 
		
		int i=0;
		testdata.moveToFirst();
	    do {
	    	getAnswersId (testdata, i);
	    	
	    	String aux =  table + "_" + testdata.getString(testdata.getColumnIndex("kanji"));
	    	int id = this.getResources().getIdentifier(aux, "drawable", this.getPackageName());
	    	mThumbIds[numbers.get(i)] = id;
	    	
	    	i++;
	    } while (testdata.moveToNext());
	    
		mDbHelper.close();
	}
	
	public void loadText(){
        
		mDbHelper.open(); 
		 
		Cursor testdata = mDbHelper.getAnswerHiragana(level, table, this.question); 
		
		int i=0;
		testdata.moveToFirst();
	    do {
	    	getAnswersId (testdata, i);
	    	
	    	mTextIds[numbers.get(i)] = testdata.getString(testdata.getColumnIndex("lectura"));
	    
	    	i++;
	    } while (testdata.moveToNext());
	    
		mDbHelper.close();
	}
	
	private void getAnswersId(Cursor testdata, int i) {
		int elementId = testdata.getInt(testdata.getColumnIndex("_id"));
    	if (elementId == this.question){
    		questPosition = numbers.get(i);
    	}
    	mAnswerIds[numbers.get(i)] = elementId;		
	}

	public void loadQuestionImage(){
		mDbHelper.open();
		Cursor testdata = mDbHelper.getQuestion(level, table);
		try {
			testdata.moveToFirst();
			ImageView mImagetView = (ImageView) findViewById(R.id.imageQ);
	    	String aux = table + "_" + testdata.getString(testdata.getColumnIndex("kanji")); 
			mImagetView.setImageResource(this.getResources().getIdentifier(aux, "drawable", this.getPackageName()));
			this.question = testdata.getInt(testdata.getColumnIndex("_id"));
		} catch (Exception ex) {
			  throw new Error("UnableToGetQuestion"); 
		}
	}
	
	public void loadQuestionText(){
		mDbHelper.open();
		Cursor testdata = mDbHelper.getQuestion(level, table);
		try {
			testdata.moveToFirst();
			TextView mTextView = (TextView) findViewById(R.id.textQ);
	    	String aux =  testdata.getString(testdata.getColumnIndex("lectura"));
			mTextView.setText(aux);
			this.question = testdata.getInt(testdata.getColumnIndex("_id"));
		} catch (Exception ex) {
			  throw new Error("UnableToGetQuestion"); 
		}
	}
	
	private void verifyQuestionGrid (int position, View v, ViewGroup parent){
		count++;

		int answer = mAnswerIds[position];
		VerifyActivity mResult = new VerifyActivity(this.question, answer);
		if (mResult.verifyAnswer()){
			v.setBackgroundColor(Color.GREEN);
			results.incrementWin();
		}else{
			v.setBackgroundColor(Color.RED);
			View view  = parent.getChildAt(questPosition);
			view.setBackgroundColor(Color.GREEN);
			results.incrementLose();
		}	
		
		Button mButton = new Button(this);
		LinearLayout ll = new LinearLayout(this); 
		//LinearLayout ll = (LinearLayout) findViewById(R.id.quest_layout);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);		
		lp.gravity=Gravity.BOTTOM;
		
		if (count < MAX_QUEST){
			
			mButton.setText("Next");
			
			ll.addView(mButton, lp);
			
			mButton.setOnClickListener(new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	getViewQuestion();
		        }
		    });
		}else{

			mButton.setText("Finish");
			
			ll.addView(mButton, lp);
			
			mButton.setOnClickListener(new OnClickListener() {
		        @Override
		        public void onClick(View v) {
		        	results.insertBD(mDbHelper);
					showResults();		        }
		    });
		   
		}
		addContentView(ll, lp);
		removeListeners();
	}

	private void removeListeners() {
		mGridView.setOnItemClickListener(null);
	}

	private void showResults() {
		setContentView(R.layout.stadistics_lesson);
		String aux = "";
		float rating = (100 * results.getQuestWin())/MAX_QUEST;
		TextView mTextRating = (TextView) findViewById(R.id.textResultsRating);
		aux = rating + " %";
		mTextRating.setText(aux);
		TextView mTextGood = (TextView) findViewById(R.id.textResultsGood);
		aux = results.getQuestWin() + " questions";
		mTextGood.setText(aux);
		TextView mTextBad = (TextView) findViewById(R.id.textResultsBad);
		aux = results.getQuestLose() + " questions";
		mTextBad.setText(aux);
		
		Button mHomeButton = (Button) findViewById(R.id.home);
		Button mResetButton = (Button) findViewById(R.id.repeat);
		
		mHomeButton.setOnClickListener(new OnClickListener() {
            @SuppressLint("NewApi") 
            public void onClick(View v) {
            	Intent intent = new Intent(LessonActivity.this, LessonsMenuActivity.class);
            	intent.putExtra ("alphabet", table);
            	finish();
            	startActivity(intent);
            }
		});
		
		mResetButton.setOnClickListener(new OnClickListener() {
            @SuppressLint("NewApi") 
            public void onClick(View v) {
            	Intent intent = getIntent();
            	finish();
            	startActivity(intent);
            }
		});
	}
}