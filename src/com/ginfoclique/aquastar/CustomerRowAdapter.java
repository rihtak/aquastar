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

public class CustomerRowAdapter extends ArrayAdapter<CustomerBean> {

	private Activity activity;
	private List<CustomerBean> customer;
	private CustomerBean objBean;
	private int row;

	public CustomerRowAdapter(Activity act, int resource, List<CustomerBean> arrayList) {
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

		holder.tvName = (TextView) view.findViewById(R.id.issue_name);
		holder.tvCity = (TextView) view.findViewById(R.id.customer_location);
		
		holder.tvPhoneNumber = (TextView) view.findViewById(R.id.customer_phone);
		holder.tvProductCount = (TextView) view.findViewById(R.id.customer_product_count);

		if (holder.tvName != null && null != objBean.getName()
				&& objBean.getName().trim().length() > 0) {
			holder.tvName.setText(Html.fromHtml(objBean.getName()));
		}
		if (holder.tvCity != null && null != objBean.getCity()
				&& objBean.getCity().trim().length() > 0) {
			holder.tvCity.setText(Html.fromHtml(objBean.getCity()));
		}
		
		if (holder.tvPhoneNumber != null && null != objBean.getGender()
				&& objBean.getGender().trim().length() > 0) {
			holder.tvPhoneNumber.setText(Html.fromHtml(objBean.getGender()));
		}
		if (holder.tvProductCount != null && objBean.getProductCount() > 0) {
			holder.tvProductCount.setText(Html.fromHtml("" + objBean.getProductCount()));
		}

		return view;
	}

	public class ViewHolder {
		public TextView tvName, tvCity,tvPhoneNumber, tvProductCount;
	}
}