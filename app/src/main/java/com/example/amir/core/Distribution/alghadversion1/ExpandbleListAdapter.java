package com.example.amir.core.Distribution.alghadversion1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import com.example.amir.core.R;

import java.util.ArrayList;

public class ExpandbleListAdapter extends BaseExpandableListAdapter {
	private Context mContext;
	private ArrayList<String> parent;
	private ArrayList<Object> Child;
	public static ArrayList<Object> childItem;
	private String[] Lab;

	public enum CurrentMode {
		Search, View, Report, DailyWork, FieldVisit, Appointment,Distribution
	};

	public CurrentMode currenMode;

	public ExpandbleListAdapter(Context MyContext, ArrayList<String> parent,
			ArrayList<Object> Child, CurrentMode currentMode) {
		this.Child = Child;
		this.parent = parent;
		this.mContext = MyContext;
		this.currenMode = currentMode;

		switch (currentMode) {
		case Search:
			Lab = new String[7];
			Lab[0] = mContext.getString(R.string.ID);
			Lab[1] = mContext.getString(R.string.Name);
			Lab[2] = mContext.getString(R.string.Phone);
			Lab[3] = mContext.getString(R.string.Mobile);
			// Lab[4] = mContext.getString(R.string.BuildingNo);
			Lab[4] = mContext.getString(R.string.Note);
			Lab[5] = mContext.getString(R.string.Latitude);
			Lab[6] = mContext.getString(R.string.Longitude);
			
			break;
			case Distribution:
				Lab = new String[6];
				Lab[0] = mContext.getString(R.string.ClientID);
				Lab[1] = mContext.getString(R.string.ClientName);
				Lab[2] = mContext.getString(R.string.ContractNo);
				Lab[3] = mContext.getString(R.string.ContractID);
				Lab[4] = mContext.getString(R.string.Latitude);
				Lab[5] = mContext.getString(R.string.Longitude);

//			Lab[3] = mContext.getString(R.string.Distributor);
				break;
		case Report:
			Lab = new String[2];
			Lab[0] = mContext.getString(R.string.ContractNo);
			Lab[1] = mContext.getString(R.string.ResultType);
			break;
		case View:
			Lab = new String[7];
			Lab[0] = mContext.getString(R.string.ContractNo);
			Lab[1] = mContext.getString(R.string.FromDate);
			Lab[2] = mContext.getString(R.string.ToDate);
			Lab[3] = mContext.getString(R.string.Grace);
			Lab[4] = mContext.getString(R.string.CopiesNo);
			Lab[5] = mContext.getString(R.string.NetAmount);
			Lab[6] = mContext.getString(R.string.Address);
			break;
		case DailyWork:
			Lab = new String[8];
			Lab[0] = mContext.getString(R.string.ID);
			Lab[1] = mContext.getString(R.string.ClientName);
			Lab[2] = mContext.getString(R.string.CopiesNo);
			Lab[3] = mContext.getString(R.string.Phone);
			Lab[4] = mContext.getString(R.string.Mobile);
			Lab[5] = mContext.getString(R.string.Address);
			Lab[6] = mContext.getString(R.string.Time);
			Lab[7] = mContext.getString(R.string.NetAmount);
			break;
		case FieldVisit:
			Lab = new String[8];
			Lab[0] = mContext.getString(R.string.CallID);
			Lab[1] = mContext.getString(R.string.ClientID);
			Lab[2] = mContext.getString(R.string.ContractID);
			// Lab[2] = "Client Name ";
			Lab[3] = mContext.getString(R.string.ClientPhone);
			Lab[4] = mContext.getString(R.string.ClientMobile);
			Lab[5] = mContext.getString(R.string.FromDate);
			Lab[6] = mContext.getString(R.string.ToDate);
			// Lab[7] = "Grace End ";
			Lab[7] = mContext.getString(R.string.Address);
			break;
		case Appointment:
			Lab = new String[12];
			Lab[0] = mContext.getString(R.string.ID);
			Lab[1] = mContext.getString(R.string.ClientID);
			Lab[2] = mContext.getString(R.string.Phone);
			Lab[3] = mContext.getString(R.string.Mobile);
			Lab[4] = mContext.getString(R.string.FromDate);
			Lab[5] = mContext.getString(R.string.ToDate);
			Lab[6] = mContext.getString(R.string.Time);
			Lab[7] = mContext.getString(R.string.Note);
			Lab[8] = mContext.getString(R.string.CopiesNo);
			Lab[9] = mContext.getString(R.string.NetAmount);
			Lab[10] = mContext.getString(R.string.Address);
			Lab[11] = mContext.getString(R.string.AppointmentTypeName);
			break;
			

		}
	}// end Constructor

	public ExpandbleListAdapter() {
	}// End Constructor

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getChildView(int GroupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		childItem = (ArrayList<Object>) Child.get(GroupPosition);
		// { TextView tv = new TextView(MyContext);
		//
		// tv.setText(Lab[childPosition] + childItem.get(childPosition));
		// tv.setPadding(30, 0, 0, 0);
		// tv.setTextSize(20);
		// // tv.setHeight(40);
		// return tv;}|
		LayoutInflater infalInflater = (LayoutInflater) this.mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = infalInflater.inflate(R.layout.distitem_expan, null);
		TextView tv = (TextView) convertView.findViewById(R.id.tv_ID);
		tv.setText(Lab[childPosition]);
		TextView tvdata = (TextView) convertView.findViewById(R.id.tv_data);
		if (childPosition % 2 == 1) {
			tv.setBackgroundColor(Color.rgb(184, 212, 255));
			tvdata.setBackgroundColor(Color.rgb(184, 212, 255));// 112, 162, 255
		}
		tvdata.setText("" + childItem.get(childPosition));
		return convertView;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return ((ArrayList<String>) Child.get(groupPosition)).size();
		// return (int) Child.get(groupPosition).size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getGroup(int GroupPosition) {
		// TODO Auto-generated method stub

		// return (ArrayList<Object>) childItem.get(GroupPosition);
		return this.parent.get(GroupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return parent.size();
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getGroupView(int GroupPosition, boolean arg1, View convertView,
			ViewGroup arg3) {
		// TextView tv = new TextView(MyContext);
		// tv.setText("" + parent.get(GroupPosition));
		// tv.setPadding(50, 0, 0, 0);
		// tv.setTextSize(20);
		// // tv.setHeight(40);
		// return tv;
		String headerTitle = (String) getGroup(GroupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.distheader, null);
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
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

}// End Class ExpandbleList
