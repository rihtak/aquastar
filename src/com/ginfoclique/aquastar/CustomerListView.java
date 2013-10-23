package com.ginfoclique.aquastar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

import static com.ginfo.mkce.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.ginfo.mkce.CommonUtilities.EXTRA_MESSAGE;

public class CustomerListView extends Activity implements OnItemClickListener {
	// All static variables
	static final String URL = "http://192.168.2.9/mkce_campus/noticeboard_xml.php";
	AsyncTask<Void, Void, Void> mRegisterTask;
	ProgressDialog dialog;
	AlertDialogManager alert = new AlertDialogManager();
	
	// XML node keys
	static final String KEY_SONG = "notice_board"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_UNAME = "user_id";
	static final String KEY_MESSAGE = "message";
	static final String KEY_DURATION = "date_time";
	static final String KEY_PATH = "path";
	TextView lblMessage;
	//static final String KEY_THUMB_URL = "thumb_url";
	
	ListView list;
    LazyAdapter adapter;
    final ArrayList<HashMap<String, String>> customerList = new ArrayList<HashMap<String, String>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer);
		//lblMessage = (TextView)findViewById(R.id.msg_txt);
		
		//registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));

		// Check if Internet present
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(CustomerListView.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			
			return;
			
		}
		else
		{
		mRegisterTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				XMLParser parser = new XMLParser();
				String xml = parser.getXmlFromUrl(URL); // getting XML from URL
				Document doc = parser.getDomElement(xml); // getting DOM element
				
				NodeList nl = doc.getElementsByTagName(KEY_SONG);
				// looping through all song nodes <song>
				for (int i = 0; i < nl.getLength(); i++) {
					// creating new HashMap
					HashMap<String, String> map = new HashMap<String, String>();
					Element e = (Element) nl.item(i);
					// adding each child node to HashMap key => value
					map.put(KEY_ID, parser.getValue(e, KEY_ID));
					map.put(KEY_UNAME, parser.getValue(e, KEY_UNAME));
					map.put(KEY_MESSAGE, parser.getValue(e, KEY_MESSAGE));
					map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
					map.put(KEY_PATH, parser.getValue(e, KEY_PATH));
					//map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

					// adding HashList to ArrayList
					customerList.add(map);
				}
				

				list=(ListView)findViewById(R.id.list);
				
				// Getting adapter by passing xml data ArrayList
		       
				
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				adapter=new LazyAdapter(CustomerListView.this, customerList);
			        list.setAdapter(adapter);
			        list.setOnItemClickListener(CustomerListView.this);
				
				mRegisterTask = null;
				 dialog.dismiss();
			}
			 protected void onPreExecute(){
		         dialog = ProgressDialog.show(CustomerListView.this, "",
                         "Loading. Please wait...", true);
		     }

		};
		mRegisterTask.execute(null, null, null);
		}
		
		
        

        // Click event for single list row
        	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int myItemInt, long arg3) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		//HashMap<String, String> o = (HashMap<String, String>) list.getItemAtPosition(position);
                HashMap<String, String> o = (HashMap<String, String>) list.getAdapter().getItem(myItemInt);
		String n_id = o.get(KEY_ID);
		String p_name = o.get(KEY_UNAME);
		String message = o.get(KEY_MESSAGE);
		String url = o.get(KEY_PATH);
        //Toast.makeText(getApplicationContext(), "ID '" + o.get(KEY_ID) + "' was clicked. Train No"+o.get(KEY_TRAIN_NO), Toast.LENGTH_SHORT).show(); 
		 //Toast.makeText(getApplicationContext(), "Clicked Train "+res, Toast.LENGTH_SHORT).show();
        
		Intent nbIntent = new Intent(getApplicationContext(),Noticeboard.class);
		nbIntent.putExtra("n_id", n_id);
		nbIntent.putExtra("p_name", p_name);
		nbIntent.putExtra("message", message);
		nbIntent.putExtra("path", url);
			 startActivity(nbIntent);
		
	}
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			
			/**
			 * Take appropriate action on this message
			 * depending upon your app requirement
			 * For now i am just displaying it on the screen
			 * */
			
			// Showing received message
			HashMap<String, String> map = new HashMap<String, String>();
			String printMessage = newMessage;
			
			if(newMessage.length()>20)
			{
				printMessage =newMessage.substring(0,20)+"....";
			}
			map.put(KEY_ID, "0");
			map.put(KEY_UNAME, printMessage);
			map.put(KEY_MESSAGE, "loading");
			map.put(KEY_DURATION, "Now");
			
			customerList.add(0, map);
			adapter.notifyDataSetChanged();
			
					
			Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();
			
			// Releasing wake lock
			WakeLocker.release();
		}

		
	};
	
	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}
}