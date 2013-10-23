package com.ginfoclique.aquastar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
    	
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position); 
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.customer_list, null);

        TextView customer_name = (TextView)vi.findViewById(R.id.customer_name); // customer_name
        TextView customer_location = (TextView)vi.findViewById(R.id.customer_location); // customer_location name
        TextView customer_phone = (TextView)vi.findViewById(R.id.customer_phone);
        //TextView customer_product_count = (TextView)vi.findViewById(R.id.customer_product_count);
        // customer_phone
       //ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        
        // Setting all values in listview
        customer_name.setText(song.get(CustomerListView.KEY_UNAME));
        customer_location.setText(song.get(CustomerListView.KEY_MESSAGE));
        customer_phone.setText(song.get(CustomerListView.KEY_DURATION));

        //imageLoader.DisplayImage(song.get(CustomerListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}