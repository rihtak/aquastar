package com.ginfoclique.aquastar;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProductRowAdapter extends ArrayAdapter<ProductBean> {

	private Activity activity;
	private List<ProductBean> customer;
	private ProductBean objBean;
	private int row;

	public ProductRowAdapter(Activity act, int resource, List<ProductBean> arrayList) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.customer = arrayList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);

			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if ((customer == null) || ((position + 1) > customer.size()))
			return view;

		objBean = customer.get(position);

		holder.tvProductName = (TextView) view.findViewById(R.id.issue_name);
		holder.tvDateDetails = (TextView) view.findViewById(R.id.date_details);
		
		

		if (holder.tvProductName != null && null != objBean.getName()
				&& objBean.getName().trim().length() > 0) {
			holder.tvProductName.setText(Html.fromHtml(objBean.getName()));
		}
		if (holder.tvDateDetails != null && null != objBean.getDateDetails()
				&& objBean.getDateDetails().trim().length() > 0) {
			holder.tvDateDetails.setText(Html.fromHtml(objBean.getDateDetails()));
		}
		

		return view;
	}

	public class ViewHolder {
		public TextView tvProductName, tvDateDetails;
	}
}