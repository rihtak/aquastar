package com.ginfoclique.aquastar;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by vinothrkumar on 20/10/13.
 */
public class CustomerList extends Activity implements OnItemClickListener {
	private static final String customerFeed = "http://www.ginfoclique.com/aquastar/customer_json.txt";
	private static final String ARRAY_NAME = "customer";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String CITY = "city";
	private static final String GENDER = "Gender";
	private static final String AGE = "age";
	

	List<CustomerBean> arrayOfList;
	ListView listView;
	CustomerRowAdapter objAdapter;
    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer);
        listView = (ListView) findViewById(R.id.cusomerListView);
		listView.setOnItemClickListener(this);

		arrayOfList = new ArrayList<CustomerBean>();

		if (Utils.isNetworkAvailable(CustomerList.this)) {
			new MyTask().execute(customerFeed);
		} else {
			showToast("No Network Connection!!!");
		}

    }
    class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(CustomerList.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			return Utils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == result || result.length() == 0) {
				showToast("No data found from web!!!");
				CustomerList.this.finish();
			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(ARRAY_NAME);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject objJson = jsonArray.getJSONObject(i);

						CustomerBean objItem = new CustomerBean();

						objItem.setId(objJson.getInt(ID));
						objItem.setName(objJson.getString(NAME));
						objItem.setCity(objJson.getString(CITY));
						objItem.setPhoneNumber(objJson.getString(GENDER));
						objItem.setProductCount(objJson.getInt(AGE));
						

						arrayOfList.add(objItem);

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				setAdapterToListview();

			}

		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//showDeleteDialog(position);
		Intent customerInfromation = new Intent(getApplicationContext(),CustomerInformation.class);
		startActivity(customerInfromation);
	}

	private void showDeleteDialog(final int position) {
		AlertDialog alertDialog = new AlertDialog.Builder(CustomerList.this)
				.create();
		alertDialog.setTitle("Delete ??");
		alertDialog.setMessage("Are you sure want to Delete it??");
		alertDialog.setButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alertDialog.setButton2("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				arrayOfList.remove(position);
				objAdapter.notifyDataSetChanged();

			}
		});
		alertDialog.show();

	}

	public void setAdapterToListview() {
		objAdapter = new CustomerRowAdapter(CustomerList.this, R.layout.customer_list_row,
				arrayOfList);
		listView.setAdapter(objAdapter);
	}

	public void showToast(String msg) {
		Toast.makeText(CustomerList.this, msg, Toast.LENGTH_LONG).show();
	}
}
