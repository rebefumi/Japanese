package com.example.japones;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("NewApi")
public class LessonsText extends Fragment {

	private TextView mTextView;
	private Button startButton;
	private final static  String [] TEXT = new String [9];
	private String root;
	private int level = 0;

	
	public LessonsText() {
		super ();
	}
	
	public LessonsText(String root) {
		super();
		this.root = root;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.lessons_text, container, false);
		startButton = (Button) view.findViewById(R.id.start);
		
		startButton.setOnClickListener(new OnClickListener() {
             @SuppressLint("NewApi") 
             public void onClick(View v) {
             	Intent intent = new Intent(getActivity(), LessonActivity.class);
             	intent.putExtra ("level", level);
             	intent.putExtra ("table", root);
             	startActivity(intent);
             }
         });
		
		setTextOptions();
		return view;
	}

	/*
	 * @Override public void onActivityCreated(Bundle savedInstanceState) {
	 * super.onActivityCreated(savedInstanceState); }
	 */

	private void setTextOptions() {
		if (this.root.equals("hiragana")){
			TEXT[0] = getString(R.string.firstLessonText);
			TEXT[1] = getString(R.string.secondLessonText);
			TEXT[2] = getString(R.string.thirdLessonText);
			TEXT[3] = getString(R.string.fourthLessonText);
			TEXT[4] = getString(R.string.fifthLessonText);
			TEXT[5] = getString(R.string.sixthLessonText);
			TEXT[6] = getString(R.string.seventhLessonText);
			TEXT[7] = getString(R.string.eighthLessonText);
			TEXT[8] = getString(R.string.ninthLessonText);
		}else if (this.root.equals("katakana")){
			TEXT[0] = getString(R.string.firstLessonTextKata);
			TEXT[1] = getString(R.string.secondLessonTextKata);
			TEXT[2] = getString(R.string.thirdLessonTextKata);
			TEXT[3] = getString(R.string.fourthLessonTextKata);
			TEXT[4] = getString(R.string.fifthLessonTextKata);
			TEXT[5] = getString(R.string.sixthLessonTextKata);
			TEXT[6] = getString(R.string.seventhLessonTextKata);
			TEXT[7] = getString(R.string.eighthLessonTextKata);
			TEXT[8] = getString(R.string.ninthLessonTextKata);
		}
	}

	void updateFeedDisplay(int position) {
		level = position+1;
		View thisView = getView ();
		mTextView = (TextView) thisView.findViewById(R.id.hiragana_texto);
		
		mTextView.setText(TEXT[position]);
		
		startButton.setVisibility(View.VISIBLE);
	
	}

}
