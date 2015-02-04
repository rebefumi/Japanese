package com.example.japones;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("NewApi")
public class LessonsList extends ListFragment{

	private final static String[] LESSONS = new String[9];
	private String root;
	
	public interface SelectionListener {
		public void onItemSelected(int position);

	}

	private SelectionListener mCallback;

	public static LessonsList newInstance(String lessonType) {
        LessonsList f = new LessonsList(lessonType);

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("index", lessonType);
        f.setArguments(args);

        return f;
    }

	
	public LessonsList(String s) {
		super ();
		this.root = s;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.layout.simple_list_item_activated_1
				: android.R.layout.simple_list_item_1;
		
		//this.root = "hiragana";
		setLessonOptions();

		setListAdapter(new ArrayAdapter<String>(getActivity(), layout, LESSONS));
	}

	private void setLessonOptions() {
		
		if (this.root.equals("hiragana")){
			LESSONS[0] = getString(R.string.firstLesson);
			LESSONS[1] = getString(R.string.secondLesson);
			LESSONS[2] = getString(R.string.thirdLesson);
			LESSONS[3] = getString(R.string.fourthLesson);
			LESSONS[4] = getString(R.string.fifthLesson);
			LESSONS[5] = getString(R.string.sixthLesson);
			LESSONS[6] = getString(R.string.seventhLesson);
			LESSONS[7] = getString(R.string.eighthLesson);
			LESSONS[8] = getString(R.string.ninthLesson);
		}else if (this.root.equals("katakana")){
			LESSONS[0] = getString(R.string.firstLessonKata);
			LESSONS[1] = getString(R.string.secondLessonKata);
			LESSONS[2] = getString(R.string.thirdLessonKata);
			LESSONS[3] = getString(R.string.fourthLessonKata);
			LESSONS[4] = getString(R.string.fifthLessonKata);
			LESSONS[5] = getString(R.string.sixthLessonKata);
			LESSONS[6] = getString(R.string.seventhLessonKata);
			LESSONS[7] = getString(R.string.eighthLessonKata);
			LESSONS[8] = getString(R.string.ninthLessonKata);
		}
		
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {

			mCallback = (SelectionListener) activity;

		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement SelectionListener");
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (isInTwoPaneMode()) {
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		}

	}

	@Override
	public void onListItemClick(ListView l, View view, int position, long id) {
		mCallback.onItemSelected(position);
	}

	// If there is a FeedFragment, then the layout is two-pane
	private boolean isInTwoPaneMode() {
		return getFragmentManager().findFragmentById(R.id.text_item) != null;
	}
	
	public void setRoot (String s){
		this.root = s;
	}
}
