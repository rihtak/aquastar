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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class CustomerInformation extends Activity implements OnItemClickListener {

	private static final String productFeed = "http://www.ginfoclique.com/aquastar/product_json.txt";
	private static final String ARRAY_NAME = "product";
	private static final String ID = "id";
	private static final String PRODUCT_NAME = "name";
	private static final String DATE_DETIALS = "date";
	
	
	List<ProductBean> arrayOfList;
	ListView listView;
	ProductRowAdapter objAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_information);
		 listView = (ListView) findViewById(R.id.productListView);
			listView.setOnItemClickListener(this);

			arrayOfList = new ArrayList<ProductBean>();

			if (Utils.isNetworkAvailable(CustomerInformation.this)) {
				new MyTask().execute(productFeed);
			} else {
				showToast("No Network Connection!!!");
			}
	}
	 class MyTask extends AsyncTask<String, Void, String> {

			ProgressDialog pDialog;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();

				pDialog = new ProgressDialog(CustomerInformation.this);
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
					CustomerInformation.this.finish();
				} else {

					try {
						JSONObject mainJson = new JSONObject(result);
						JSONArray jsonArray = mainJson.getJSONArray(ARRAY_NAME);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject objJson = jsonArray.getJSONObject(i);

							ProductBean objItem = new ProductBean();

							objItem.setId(objJson.getInt(ID));
							objItem.setName(objJson.getString(PRODUCT_NAME));
							objItem.setDateDetails(objJson.getString(DATE_DETIALS));
							Log.i("ObjItem",objItem.toString());
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
			Intent productInfromation = new Intent(getApplicationContext(),ProductInformation.class);
			startActivity(productInfromation);
		}

		private void showDeleteDialog(final int position) {
			AlertDialog alertDialog = new AlertDialog.Builder(CustomerInformation.this)
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
			objAdapter = new ProductRowAdapter(CustomerInformation.this, R.layout.product_list_row,
					arrayOfList);
			listView.setAdapter(objAdapter);
		}

		public void showToast(String msg) {
			Toast.makeText(CustomerInformation.this, msg, Toast.LENGTH_LONG).show();
		}
	

}
