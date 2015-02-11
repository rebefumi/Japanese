package com.example.japones;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class ResultsLesson extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Toast.makeText(getBaseContext(), "hola", Toast.LENGTH_LONG).show();
	}
}
