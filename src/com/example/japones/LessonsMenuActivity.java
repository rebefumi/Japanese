package com.example.japones;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class LessonsMenuActivity  extends Activity implements LessonsList.SelectionListener {
	LessonsList mHiraganaList;
	LessonsText mHiraganaText;
	String root;
		
	@SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		   root = extras.getString("alphabet");
		}
        setContentView(R.layout.lessons_list);

         mHiraganaList = new LessonsList(root);

        if (!isInTwoPaneMode()) {
	        FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.add(R.id.hiragana_container, mHiraganaList);
			fragmentTransaction.commit();   
        }else{	
        	
        	FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.add(R.id.list_item, mHiraganaList);
			fragmentTransaction.commit();  
			
			mHiraganaText = new LessonsText(root);
			FragmentManager fragmentManagerText = getFragmentManager();
			FragmentTransaction fragmentTransactionText = fragmentManagerText.beginTransaction();
			fragmentTransactionText.add(R.id.text_item, mHiraganaText);
			fragmentTransactionText.commit();  
			
			mHiraganaText = (LessonsText) getFragmentManager().findFragmentById(R.id.text_item);	
		}
	}
	
	private boolean isInTwoPaneMode() {
		return findViewById(R.id.hiragana_container) == null;
	}
	
	@SuppressLint("NewApi") 
	public void onItemSelected(int position) {
		if (mHiraganaText == null){
			mHiraganaText = new LessonsText(root);	
		}
		
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		
		
		if (!isInTwoPaneMode()) {
			fragmentTransaction.replace(R.id.hiragana_container, mHiraganaText);
		}else{
			fragmentTransaction.replace(R.id.text_item, mHiraganaText);
		}

		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
		getFragmentManager().executePendingTransactions();
		
		mHiraganaText.updateFeedDisplay(position);

	}

}
