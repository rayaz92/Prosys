package com.example.amir.core.Collector.Adapter;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.amir.core.R;

public class AdapterExpandableList extends BaseExpandableListAdapter {
	Context mContext;
	List<String> Parent;
	ArrayList<Object> listData;
	String lab[];

	public AdapterExpandableList(Context context, List<String> listparent,
			ArrayList<Object> listdata, String Col) {
		this.mContext = context;
		this.listData = listdata;
		this.Parent = listparent;
		lab = new String[3];
		lab[0] = mContext.getString(R.string.accn_no);
		lab[1] = Col;
		lab[2] = mContext.getString(R.string.Balance);
	}

	public AdapterExpandableList(Context context, List<String> listparent,
			ArrayList<Object> listdata, int x) {
		this.mContext = context;
		this.listData = listdata;
		this.Parent = listparent;
		lab = new String[3];
		lab[0] = mContext.getString(R.string.accn_no);
		lab[1] = "Note";
		lab[2] = mContext.getString(R.string.Balance);
	}

	public AdapterExpandableList(Context context, List<String> listparent,
			ArrayList<Object> listdata) {
		this.mContext = context;
		this.listData = listdata;
		this.Parent = listparent;
		lab = new String[9];
		lab[0] = mContext.getString(R.string.accn_no);
		lab[1] = "A0_30";
		lab[2] = "A31_60";
		lab[3] = "A61_90";
		lab[4] = "A91_120";
		lab[5] = "A121_150";
		lab[6] = "A151_180";
		lab[7] = "A181";
		lab[8] = mContext.getString(R.string.Balance);
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosititon) {
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		ArrayList<Object> l = (ArrayList<Object>) listData.get(groupPosition);
		LayoutInflater infalInflater = (LayoutInflater) this.mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = infalInflater.inflate(R.layout.collchild, null);
		TextView tvname = (TextView) convertView.findViewById(R.id.tvname);
		tvname.setText(lab[childPosition]);
		TextView tvData = (TextView) convertView.findViewById(R.id.tvdata);
		tvData.setText("" + l.get(childPosition));
		if (childPosition % 2 == 1) {
			tvname.setBackgroundColor(Color.rgb(112, 162, 255));
			tvData.setBackgroundColor(Color.rgb(112, 162, 255));
		}
		return convertView;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getChildrenCount(int groupPosition) {
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
			convertView = infalInflater.inflate(R.layout.collparent, null);
		}

		TextView tvname = (TextView) convertView
				.findViewById(R.id.tvparentname);
		tvname.setText(headerTitle);
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