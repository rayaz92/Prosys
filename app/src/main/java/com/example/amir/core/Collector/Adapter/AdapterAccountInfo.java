package com.example.amir.core.Collector.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.amir.core.Collector.Classes.GNPAY;
import com.example.amir.core.R;

public class AdapterAccountInfo extends BaseAdapter {
	Context context;
	List<GNPAY> listGnpays;

	public AdapterAccountInfo(Context context, List<GNPAY> listGnpays) {
		this.context = context;
		this.listGnpays = listGnpays;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listGnpays.size();
	}

	@Override
	public GNPAY getItem(int position) {
		// TODO Auto-generated method stub
		return listGnpays.get(position);
	}

	@Override
	public long getItemId(int ID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		convertView = mInflater.inflate(R.layout.collcustomaccount_info, viewGroup,
				false);
		TextView tvDR = (TextView) convertView
				.findViewById(R.id.tvDRAmount_CustomAccount);
		TextView tvser = (TextView) convertView
				.findViewById(R.id.tvShowSer_Acount);
		TextView tvdate = (TextView) convertView
				.findViewById(R.id.tvDate_Account_Info);
		TextView tvTY = (TextView) convertView
				.findViewById(R.id.tvTY_CustomAccount);
		TextView tvaccn_no = (TextView) convertView
				.findViewById(R.id.tvAccountNoShow_CustomAccount);
		TextView tvName = (TextView) convertView
				.findViewById(R.id.tvNameShow_CustomAccoutn);
		TextView tvRestAmount = (TextView) convertView
				.findViewById(R.id.tvRestAmountShow_CustomAccoutn);
		TextView tvReststatues = (TextView) convertView
				.findViewById(R.id.tvRestStatuesShow_CustomAccoutn);
		TextView tvDesc = (TextView) convertView
				.findViewById(R.id.tvDescShow_CustomAccoutn);

		CheckBox checkBox = (CheckBox) convertView
				.findViewById(R.id.ch_Acount_Info);

		checkBox.setTag(position);
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				getItem((Integer) buttonView.getTag()).setIsChecked(isChecked);
			}
		});

		String x[] = listGnpays.get(position).getdate().split("T");
		String datex[] = listGnpays.get(position).GetRestStatues().split("T");
		tvDR.setText("" + listGnpays.get(position).getDR());
		tvser.setText("" + listGnpays.get(position).getser());
		tvdate.setText("" + x[0]);
		tvTY.setText("" + listGnpays.get(position).getTY());
		//
//		checkBox.setEnabled(listGnpays.get(position).getIsChecked());
		tvaccn_no.setText("" + listGnpays.get(position).getaccn_no());
		tvName.setText("" + listGnpays.get(position).getClientName());
		tvRestAmount.setText("" + listGnpays.get(position).GetRestAmount());
		tvReststatues.setText("" + datex[0]);
		tvDesc.setText("" + listGnpays.get(position).GetDesc());
		return convertView;
	}

	public ArrayList<GNPAY> getlistChecked() {
		ArrayList<GNPAY> box = new ArrayList<GNPAY>();
		for (GNPAY e : listGnpays) {
			if (e.getIsChecked())
				box.add(e);
		}
		return box;
	}
	
}// Adapter
