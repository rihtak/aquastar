package com.ginfoclique.aquastar;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ginfoclique.aquastar.CustomerList.MyTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ProductInformation extends Activity implements OnItemClickListener {

	private static final String issueFeed = "http://www.ginfoclique.com/aquastar/issue_json.txt";
	private static final String ARRAY_NAME = "issues";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String DATE_DETIALS = "date";
	private static final String STATUS = "status";
	
	
	List<IssueBean> arrayOfList;
	ListView listView;
	IssueRowAdapter objAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_information);
		 listView = (ListView) findViewById(R.id.issuesListView);
			listView.setOnItemClickListener(this);

			arrayOfList = new ArrayList<IssueBean>();

			if (Utils.isNetworkAvailable(ProductInformation.this)) {
				new MyTask().execute(issueFeed);
			} else {
				showToast("No Network Connection!!!");
			}
	}
	 class MyTask extends AsyncTask<String, Void, String> {

			ProgressDialog pDialog;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();

				pDialog = new ProgressDialog(ProductInformation.this);
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
					ProductInformation.this.finish();
				} else {

					try {
						JSONObject mainJson = new JSONObject(result);
						JSONArray jsonArray = mainJson.getJSONArray(ARRAY_NAME);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject objJson = jsonArray.getJSONObject(i);

							IssueBean objItem = new IssueBean();
							objItem.setId(objJson.getInt(ID));
							objItem.setName(objJson.getString(NAME));
							objItem.setDateDetails(objJson.getString(DATE_DETIALS));
							objItem.setStatus(objJson.getString(STATUS));
							
							
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
			Intent issueInfromation = new Intent(getApplicationContext(),IssueInformation.class);
			startActivity(issueInfromation);
		}

		private void showDeleteDialog(final int position) {
			AlertDialog alertDialog = new AlertDialog.Builder(ProductInformation.this)
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
			objAdapter = new IssueRowAdapter(ProductInformation.this, R.layout.issues_list_row,
					arrayOfList);
			listView.setAdapter(objAdapter);
		}

		public void showToast(String msg) {
			Toast.makeText(ProductInformation.this, msg, Toast.LENGTH_LONG).show();
		}
	

}
