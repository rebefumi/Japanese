package com.example.japones;

import android.app.Activity;
import android.content.Context;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		switch (selectQuestion()) {
		case 1: setContentView(R.layout.question_type_1);
				GridView gridview = (GridView) findViewById(R.id.gridview);
				switch (selectTypeQuestion()){
				case 'a':
					gridview.setAdapter(new ImageAdapter(this));
					
					gridview.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
							Toast.makeText(LessonActivity.this, "" + position, Toast.LENGTH_SHORT).show();
						}
					});
					break;
				case 'b': 
					gridview.setAdapter(new MyAdapter(this));
					
					gridview.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
							Toast.makeText(LessonActivity.this, "" + position, Toast.LENGTH_SHORT).show();
						}
					});
				default:
					break;
				}
				
				break;
		case 2: setContentView(R.layout.question_type_2);

		default:
			break;
		}


	}

	private int selectQuestion(){
		return 1;
	}

	private char selectTypeQuestion(){
		return 'a';
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

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = convertView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater li = getLayoutInflater();
            mView = li.inflate(R.layout.question_type_1, null);
            
            // Add The Text!!!
            TextView tv = (TextView)mView.findViewById(R.id.gridview);
            tv.setText(mTextIds[position]);
        }
        
        return mView;
    }

    // references to our images
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

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.a, R.drawable.e,
            R.drawable.i, R.drawable.o,
            R.drawable.u, R.drawable.a
    };
}
}