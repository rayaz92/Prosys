package com.example.amir.core.Distribution.alghadversion1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;


import com.example.amir.core.Distribution.alghadversion1.Classes.CallResult;
import com.example.amir.core.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FieldVisit extends Activity implements OnClickListener,
		OnChildClickListener {
	EditText TxTFieldDate;
	DatePickerDialog.OnDateSetListener DateSet;
	Button BtnSetResult, BtnGetData;
	ExpandableListView ExFieldVisit;

	String DateFrom, DateTo;
	int CalltypeIdToAddResult, CallIdToAddResult;
	List<CallResult> listCallResults;
	Intent AddResultShow;
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distfieldvisit);
		TxTFieldDate = (EditText) findViewById(R.id.TxTFieldVisitDate);
		TxTFieldDate.setOnClickListener(this);
		BtnSetResult = (Button) findViewById(R.id.BtnfieldvisitSetResult);
		BtnGetData = (Button) findViewById(R.id.btnFiledVisitGetData);
		ExFieldVisit = (ExpandableListView) findViewById(R.id.ExpandableListViewFiledVisit);
		AddResultShow = new Intent(FieldVisit.this, AddResult.class);
		BtnGetData.setOnClickListener(this);
		BtnSetResult.setOnClickListener(this);
		BtnSetResult.setEnabled(false);
		// This For Hidden Keyboard
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		ExFieldVisit.setOnChildClickListener(this);
	}// End On Create

	protected void Dialog(final EditText TxTDate) {
		Calendar calendar = Calendar.getInstance();
		final int Year = calendar.get(Calendar.YEAR);
		final int Month = calendar.get(Calendar.MONTH);
		final int Day = calendar.get(Calendar.DAY_OF_MONTH);
		final DatePickerDialog.OnDateSetListener dateSetListener;
		dateSetListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				int monthPlusone = (monthOfYear + 1);
				String DateAfter = "" + monthPlusone + "/" + dayOfMonth + "/"
						+ year;
				TxTDate.setText(DateAfter);
			}
		};
		new DatePickerDialog(context, dateSetListener, Year, Month, Day).show();
	}

//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.BtnfieldvisitSetResult:
//			if (CallIdToAddResult == 0) {
//				GetToast.Toast(context,
//						getString(R.string.Please_Select_Client));
//				return;
//			}// End if
//			startActivity(AddResultShow);
//			break;
//		case R.id.btnFiledVisitGetData:
//			GetData();
//			break;
//		case R.id.TxTFieldVisitDate:
//			Dialog(TxTFieldDate);
//			break;
//		}
//
//	}// End On click

	@Override
	public void onClick(View v) {
			if(v.getId() == R.id.BtnfieldvisitSetResult) {
				if (CallIdToAddResult == 0) {
					GetToast.Toast(context,
							getString(R.string.Please_Select_Client));
					return;
				}// End if
				startActivity(AddResultShow);
			}else if(v.getId() == R.id.btnFiledVisitGetData) {
				GetData();
			}else if(v.getId() == R.id.TxTFieldVisitDate) {
				Dialog(TxTFieldDate);
		}

	}// End On click

	private void GetData() {
		try {
			listCallResults = new CallResult().SelectCallsByDriverAndDate(
					DistributionMain.DriverId, TxTFieldDate.getText().toString());
			if (listCallResults == null) {
				GetToast.Toast(context, getString(R.string.NoData));
				return;
			}
			BtnSetResult.setEnabled(true);
			AddToExlist();
		} catch (Exception e) {
//			Log.d("Error in FiledVist Get Data", e.getMessage());
		}// End Try Catch

	}// End Get Data

	private void AddToExlist() {
		try {
			ArrayList<String> parentItems = new ArrayList<String>();
			ArrayList<Object> Child = new ArrayList<Object>();
			for (CallResult e : listCallResults) {
				parentItems.add(e.getClientName());
				ArrayList<Object> child = new ArrayList<Object>();
				child.add(e.getCallID());
				child.add(e.getClientID());
				child.add(e.getContractID());
				child.add(e.getClientPhone());
				child.add(e.getClientMobile());
				child.add(e.getFromDate());
				child.add(e.getToDate());
				CalltypeIdToAddResult = e.getCallTypeID();
				Log.d("Call Type id , CallID  ", "" + CallIdToAddResult + "**"
						+ CalltypeIdToAddResult);
				// child.add(e.getGraceEnd());
				child.add(e.getAddress());
				Child.add(child);
			}// End For
			AddResultShow.putExtra("CallTypeId", CalltypeIdToAddResult);

			ExFieldVisit
					.setAdapter(new ExpandbleListAdapter(
							context,
							parentItems,
							Child,
							ExpandbleListAdapter.CurrentMode.FieldVisit));
		} catch (Exception e) {
			Log.d("Error In Field Visit ", e.getMessage());
		}// End Try Catch
	}// End Function

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		v.setBackgroundColor(Color.GRAY);
		CallIdToAddResult = Integer.valueOf(ExpandbleListAdapter.childItem.get(0).toString());
		v.setBackgroundColor(Color.GRAY);
		AddResultShow.putExtra("CallIdToAddResult", CallIdToAddResult);
		return true;
	}
}// End Class
