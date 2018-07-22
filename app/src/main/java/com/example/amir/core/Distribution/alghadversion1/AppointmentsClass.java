package com.example.amir.core.Distribution.alghadversion1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.ListView;


import com.example.amir.core.Distribution.alghadversion1.Classes.AppointmentByDriver;
import com.example.amir.core.Distribution.alghadversion1.Classes.Contract;
import com.example.amir.core.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SuppressLint("NewApi")
public class AppointmentsClass extends Activity implements OnClickListener {
	EditText TxTDate;
	Button btnGetData, btnReNewAppointement;
	int Year, Month, Day;
	DatePickerDialog.OnDateSetListener dateSetListener;

	List<AppointmentByDriver> listAppointmentByDrivers;
	ListView listView;

	int IDToReNew;
	Context context = this;
	String TimeFrom, TimeTo;
	ExpandableListView expandableListView;
	AppointmentByDriver objByDriver;
	// private ArrayList<Object> ToReNew;
	Intent ReNewAppShow;
	ExpandbleListAdapter objExpandbleList = new ExpandbleListAdapter();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distappointments);
		InitailView();
	}// End On Crate

	private void InitailView() {

		TxTDate = (EditText) findViewById(R.id.TxTDate_appointment);

		btnGetData = (Button) findViewById(R.id.btngetData);
		btnReNewAppointement = (Button) findViewById(R.id.BTNReNewAppointment);

		objByDriver = new AppointmentByDriver();

		ReNewAppShow = new Intent(AppointmentsClass.this,
				ReNewAppointment.class);
		final Calendar c = Calendar.getInstance();
		Year = c.get(Calendar.YEAR);
		Month = c.get(Calendar.MONTH);
		Day = c.get(Calendar.DAY_OF_MONTH);
		expandableListView = (ExpandableListView) findViewById(R.id.expandableListViewSearchPro);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		TxTDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog(TxTDate);
			}
		});
		expandableListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView arg0, View v,
					int postion, int arg3, long arg4) {
				IDToReNew = (Integer) ExpandbleListAdapter.childItem.get(0);
				Log.d("Group Position ", "" + postion + "*/*" + IDToReNew);
				v.setBackgroundColor(Color.GRAY);
				return true;
			}
		});

		btnReNewAppointement.setOnClickListener(this);
		btnGetData.setOnClickListener(this);
	}

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
//		case R.id.btngetData:
//			if (TxTDate.getText().toString().trim().equals("")) {
//
//				GetToast.Toast(context, getString(R.string.PleaseEnterDate));
//
//				return;
//			}
//			new getdata().execute(TxTDate.getText().toString());
//			break;
//		case R.id.BTNReNewAppointment:
//			if (IDToReNew == 0) {
//				GetToast.Toast(AppointmentsClass.this,
//						getString(R.string.Please_Select_Client));
//				return;
//			}// End If Id 0
//			FillObject(IDToReNew);
//			startActivity(ReNewAppShow);
//			this.finish();
//			break;
//		}
//	}// End On Click

	@Override
	public void onClick(View v) {
			if(v.getId() == R.id.btngetData) {
				if (TxTDate.getText().toString().trim().equals("")) {

					GetToast.Toast(context, getString(R.string.PleaseEnterDate));

					return;
				}
				new getdata().execute(TxTDate.getText().toString());
			}else if(v.getId() == R.id.BTNReNewAppointment) {
				if (IDToReNew == 0) {
					GetToast.Toast(AppointmentsClass.this,
							getString(R.string.Please_Select_Client));
					return;
				}// End If Id 0
				FillObject(IDToReNew);
				startActivity(ReNewAppShow);
				this.finish();
			}
	}// End On Click

	// @Override
	// public void onBackPressed() {
	// this.finish();
	// }

	public void FillObject(int id) {

		try {
			for (AppointmentByDriver e : listAppointmentByDrivers) {
				if (id == e.getID()) {
					// ReNewAppShow.putExtra(name, value)
					Contract objContract = new Contract();

					ReNewAppShow.putExtra("requestCode", "DateFromAppointment");

					ReNewAppShow.putExtra("ID", e.getID());

					ReNewAppShow.putExtra("ClientID", e.getClientID());
					objContract.SetClientID(e.getClientID());
					ReNewAppShow.putExtra("ClientName", e.getClientName());
					objContract.SetClientName(e.getClientName());
					int x = e.getSubscriptionTypeID();
					ReNewAppShow.putExtra("SubscriptionTypeID", x);

					ReNewAppShow.putExtra("ContractId", e.getContractID());
					objContract.SetRenewContractID(e.getContractID());

					ReNewAppShow.putExtra("NationalNumber", e.getNationalNo());
					objContract.SetNationalNo(e.getNationalNo());

					ReNewAppShow.putExtra("Note", e.getAppointmentNote());
					objContract.SetAddressComments(e.getAddressComments());

					ReNewAppShow.putExtra("Phone", e.getClientPhone());
					objContract.SetClientPhone(e.getClientPhone());

					ReNewAppShow.putExtra("Address", e.getAddress());
					objContract.SetAddress(e.getAddress());
					ReNewAppShow.putExtra("CopiesNo", e.getCopiesNo());

					ReNewAppShow.putExtra("CompanyName", e.getCompanyName());
					objContract.setCompanyName(e.getCompanyName());
					ReNewAppShow.putExtra("ContractTypeID",
							e.getContractTypeID());
					ReNewAppShow.putExtra("objContract", objContract);

					String arra[] = e.getToDate().split("T");
					ReNewAppShow.putExtra("TimeTo", arra[0]);
//					hoon lazm ekon al grace
					ReNewAppShow.putExtra("TimeFrom", TxTDate.getText()
							.toString());
					ReNewAppShow.putExtra("SubscriptionTypeID",
							e.getSubscriptionTypeID());
					ReNewAppShow.putExtra("AppointmentTypeName",
							e.GetAppointmentTypeName());
					ReNewAppShow.putExtra("AppointmentTypeID",
							e.GetAppointmentTypeID());

					break;
				}// End if
			}// End For

		} catch (Exception e) {
//			Log.d("Error in Appointment Class To ", e.getMessage());
		}
	}// End To

	private class getdata extends AsyncTask<String, Void, Void> {
		ShowProgressDialog showProgressDialog;

		@Override
		protected void onPreExecute() {
			showProgressDialog = new ShowProgressDialog(context,
					getString(R.string.Please_Wait));
			showProgressDialog.ShowDialog();

		}

		@Override
		protected Void doInBackground(String... params) {

			listAppointmentByDrivers = objByDriver.GetAppointmentByDriver(
					DistributionMain.DriverId, params[0]);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			showProgressDialog.EndDialog();
			if (listAppointmentByDrivers == null) {
				GetToast.Toast(AppointmentsClass.this,
						getString(R.string.NoData));
				return;
			}// End if
			ArrayList<String> parentItems = new ArrayList<String>();
			ArrayList<Object> childItems = new ArrayList<Object>();
			for (AppointmentByDriver e : listAppointmentByDrivers) {
				parentItems.add(e.getClientName());
				ArrayList<Object> child = new ArrayList<Object>();
				child.add(e.getID());
				child.add(e.getClientID());
				child.add(e.getClientPhone());
				child.add(e.getClientMobile());
				String s = String.valueOf(e.getToDate());
				String FromDate = String.valueOf(e.GetFromDate());
				String[] x = s.split("T");
				String[] FromDatesplite = FromDate.split("T");
				child.add(FromDatesplite[0]);
				child.add(x[0]);
				String g = String.valueOf(e.getAppointmentTime());
				String[] g2 = g.split("T");
				child.add(g2[1]);
				child.add(e.getAppointmentNote());
				child.add(e.getCopiesNo());
				child.add(e.getNetAmount());
				child.add(e.getAddress());
				child.add(e.GetAppointmentTypeName());
				childItems.add(child);
			}
			expandableListView
					.setAdapter(new ExpandbleListAdapter(
							context,
							parentItems,
							childItems,
							ExpandbleListAdapter.CurrentMode.Appointment));
		}

	}// getData
}// End Class AppointmentsClass
