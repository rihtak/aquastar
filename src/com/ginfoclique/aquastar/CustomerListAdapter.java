package com.ginfoclique.aquastar;

import java.util.ArrayList;
import java.util.HashMap;



import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomerListAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    
    
    public CustomerListAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      
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
            vi = inflater.inflate(R.layout.customer_list_row, null);

        TextView customer_name = (TextView)vi.findViewById(R.id.customer_name); // title
        TextView customer_location = (TextView)vi.findViewById(R.id.customer_location); // artist name
        TextView customer_phone = (TextView)vi.findViewById(R.id.customer_phone); // duration
        TextView customer_product_count=(TextView)vi.findViewById(R.id.customer_product_count); // thumb image
        
        HashMap<String, String> customer_info = new HashMap<String, String>();
        customer_info = data.get(position);
        
        // Setting all values in listview
        customer_name.setText(customer_info.get(XMLKeyInfo.KEY_NAME));
        customer_location.setText(customer_info.get(XMLKeyInfo.KEY_ADDRESS_1));
        customer_phone.setText(customer_info.get(XMLKeyInfo.KEY_PHONE_NUMBER));
        customer_product_count.setText("49");
        return vi;
    }
}