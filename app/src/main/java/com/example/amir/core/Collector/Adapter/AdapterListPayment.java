package com.example.amir.core.Collector.Adapter;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.amir.core.R;

public class AdapterListPayment extends ArrayAdapter<PaymentItem> {

	List<PaymentItem> listPayment;
	Context context;

	public AdapterListPayment(Context context, int resource,
			List<PaymentItem> payments) {
		super(context, resource, payments);
		// TODO Auto-generated constructor stub
		this.listPayment = payments;
		this.context = context;
	}

	// public AdapterListPayment(List<PaymentItem> payments, Context context) {
	// this.listPayment = payments;
	// this.context = context;
	// }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listPayment.size();
	}

	@Override
	public PaymentItem getItem(int position) {
		// TODO Auto-generated method stub
		return listPayment.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {

		LayoutInflater infalInflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = infalInflater.inflate(R.layout.collpaymentitem, viewGroup, false);
		TextView tvNetAmount = (TextView) view
				.findViewById(R.id.tvNetAmount_PaymentItem);
		tvNetAmount.setText("" + listPayment.get(position).getCheuqeAmount());

		TextView tvDuDate = (TextView) view
				.findViewById(R.id.tvDueDate_PaymentItem);
		tvDuDate.setText(listPayment.get(position).getDueDate());

		TextView tvchequeNo = (TextView) view
				.findViewById(R.id.tvChequeNo_PaymentItem);
		tvchequeNo.setText(listPayment.get(position).getChequeNo());

		TextView tvBankNo = (TextView) view
				.findViewById(R.id.tvBankNo_PaymentItem);
		tvBankNo.setText("" + listPayment.get(position).getBankNo());

		return view;
	}

}// end Adapter
