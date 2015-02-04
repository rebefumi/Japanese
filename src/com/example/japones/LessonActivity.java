package com.example.japones;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
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
	private JapaneseAdapter mDbHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		japaneseDateBase();
        loadImages();
		switch (selectQuestion()) {
		case 1: setContentView(R.layout.question_type_1);
				//esto se hara cuando se escoja la pregunta
				ImageView mImagetView = (ImageView) findViewById(R.id.imageQ);
				mImagetView.setImageResource(R.drawable.e);
		
				GridView mGridviewText = (GridView) findViewById(R.id.gridText);
				mGridviewText.setAdapter(new MyAdapter(this));
				
				mGridviewText.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
						Toast.makeText(LessonActivity.this, "" + position, Toast.LENGTH_SHORT).show();
					}
				});
				break;
		case 2: 
		case 3:
		case 4: 
				setContentView(R.layout.question_type_2);
				//esto se hara cuando se escoja la pregunta
				
				TextView mTextView = (TextView) findViewById(R.id.textQ);
				mTextView.setText("a");
				
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
		Toast.makeText(LessonActivity.this, "" + x, Toast.LENGTH_SHORT).show();
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

    // references to our strings
    private String[] mTextIds = {
            "a", "e",
            "i", "o",
            "u", "a"
    };
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
		 
		Cursor testdata = mDbHelper.getImagesHiragana(); 
		
		int i=0;
		testdata.moveToFirst();
	    do {
	    	String aux =  testdata.getString(testdata.getColumnIndex("kanji"));
	    	int id = this.getResources().getIdentifier(aux, "drawable", this.getPackageName());
	    	mThumbIds[i] = id;
	    	i++;
	    } while (testdata.moveToNext());
	
	    //ahora son 5 na mas quitar esto despues la sexta se contruye cogiendo otra de otro sitio
	    //hacer que los valores del vector sean aleatorios
	    mThumbIds[i] = mThumbIds[0];
	    
		mDbHelper.close();
	}

}