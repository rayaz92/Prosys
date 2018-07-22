package com.example.amir.core.ADR.activity;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.amir.core.ADR.activity.Classes.Contract;
import com.example.amir.core.R;


public class ExpandableListAdapter extends BaseExpandableListAdapter {

	Context mContext;
	List<String> Parent;
	ArrayList<Object> listData;
	String lab[];
	ArrayList<Contract> arrayListContracts;

	public ExpandableListAdapter(Context context, List<String> listparent,
			ArrayList<Object> listdata) {
		mContext = context;
		Parent = listparent;
		listData = listdata;
		lab = new String[5];
		lab[0] = mContext.getString(R.string.ContractID);
		lab[1] = mContext.getString(R.string.ContractNo);
		lab[2] = mContext.getString(R.string.ContractType);
		lab[3] = mContext.getString(R.string.ContractDate);
		lab[4] = mContext.getString(R.string.NetAmount);

	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {

		@SuppressWarnings("unchecked")
		ArrayList<Object> l = (ArrayList<Object>) listData.get(groupPosition);
		Object x = l.get(childPosititon);
		return x;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		@SuppressWarnings("unchecked")
		ArrayList<Object> l = (ArrayList<Object>) listData.get(groupPosition);

		LayoutInflater infalInflater = (LayoutInflater) this.mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = infalInflater.inflate(R.layout.adritem_expan, null);

		TextView tv = (TextView) convertView.findViewById(R.id.tv_ID);
		tv.setText(lab[childPosition]);
		TextView tvdata = (TextView) convertView.findViewById(R.id.tv_data);
		if (childPosition % 2 == 1) {
			tv.setBackgroundColor(Color.rgb(112, 162, 255));
			tvdata.setBackgroundColor(Color.rgb(112, 162, 255));
		}
		tvdata.setText("" + l.get(childPosition));

		return convertView;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return ((ArrayList<String>) listData.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return this.Parent.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return Parent.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.adrheader, null);
		}

		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;

	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
