package com.example.amir.core.Distribution.alghadversion1;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.amir.core.Distribution.alghadversion1.Classes.Area;
import com.example.amir.core.Distribution.alghadversion1.Classes.CheckIn;
import com.example.amir.core.Distribution.alghadversion1.Classes.Connection;
import com.example.amir.core.Distribution.alghadversion1.Classes.Country;
import com.example.amir.core.Distribution.alghadversion1.Classes.GPSTracker;
import com.example.amir.core.Distribution.alghadversion1.Classes.city;
import com.example.amir.core.R;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class CheckInClup extends Activity implements OnClickListener {

	EditText txtPhone, txtSubsName, txtPosName, txtNote;

	TextView tvlan, tvlon, tvaddress, tvposname, tvposaddress, tvposnote,
			tvStreet;
	Timer timer;
	Button btoSave, btoCancel;
	Spinner spreference, spCity, spArea, spCountry;;
	CheckIn objcheckin;

	GPSTracker GPS;
	Context context = this;
	Geocoder geocoder;

	List<CheckIn> listRefernce;
	List<city> listCity;
	List<Country> listcountry;
	List<Area> ListArea;
	List<Address> addresses;

	ArrayAdapter<city> adapterCity;
	ArrayAdapter<Country> adapterCountry;
	ArrayAdapter<Area> adapterArea;
	ArrayAdapter<CheckIn> adapterRef;

	LocationManager location_manager;
	Location loc;
	ShowProgressDialog objShowProgressDialog;
	double x;
	LinearLayout linPosName, linPosAddress, linNote;
	double y;
	String getLatitude;
	String getLongitude;
	final Locale loca = new Locale("ar");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distcheckin_clup);
		objcheckin = new CheckIn();
		GPS = new GPSTracker(context);
		if (Connection.isConnectingToInternet(context)) {
			InitialView();
		} else {
			Connection.showAlertDialog(context);
		}
	}

	private void InitialView() {

		txtPosName = (EditText) findViewById(R.id.txtPosName_checkInClup);
		txtNote = (EditText) findViewById(R.id.txtnote_checkInClup);
		txtPhone = (EditText) findViewById(R.id.txtPh_Mobile_checkInClup);
		txtSubsName = (EditText) findViewById(R.id.txtSubscriberName_checkInClup);

		tvlan = (TextView) findViewById(R.id.tvlan_checkinClup);
		tvlon = (TextView) findViewById(R.id.tvlon_checkinClup);
		tvaddress = (TextView) findViewById(R.id.tvAddress_checkinClup);
		tvposname = (TextView) findViewById(R.id.tvPosName_checkInClup);
		tvposaddress = (TextView) findViewById(R.id.tvPosAddress_checkInClup);
		tvposnote = (TextView) findViewById(R.id.tvPosNote_checkInClup);
		tvStreet = (TextView) findViewById(R.id.tvStreet_checkin);

		btoSave = (Button) findViewById(R.id.btoSave_CheckinClup);
		btoCancel = (Button) findViewById(R.id.btoCancel_checkinClup);

		spreference = (Spinner) findViewById(R.id.spRef_checkInClup);
		spCity = (Spinner) findViewById(R.id.SpCity_checkin);
		spArea = (Spinner) findViewById(R.id.spArea_checkin);
		spCountry = (Spinner) findViewById(R.id.spCountry_checkin);

		linPosName = (LinearLayout) findViewById(R.id.linposname);
		linPosAddress = (LinearLayout) findViewById(R.id.linPosAddress_CheckInClup);
		linNote = (LinearLayout) findViewById(R.id.linPosNote_CheckInClup);

		location_manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		GetReferance();
		GetCountry();
		GetCity();

		spCity.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				int CityID = adapterCity.getItem(
						spCity.getSelectedItemPosition()).GetID();
				GetArea(CityID);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		OpenTimer();
		btoSave.setOnClickListener(this);
		btoCancel.setOnClickListener(this);

	}

	private void OpenTimer() {

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				runOnUiThread(new Runnable() {

					public void run() {
						try {

							getlocation();

						} catch (Exception e) {
							Log.e("Error OpenTimer", e.getMessage());
						}
					}
				});

			}
		}, 0, 07000);

	}// End Open Timer

	private void GetReferance() {
		// list Refernce
		CheckIn objCheckIn = new CheckIn();
		listRefernce = objCheckIn.SelectRefernce();
		adapterRef = new ArrayAdapter<CheckIn>(this,
				android.R.layout.simple_spinner_item, listRefernce);
		adapterRef
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spreference.setAdapter(adapterRef);
	}

	private void getlocation() {
		try {

			if (GPS.canGetLocation()) {
				Location location = GPS.getLocation();
				if (location != null) {
					tvlan.setText("" + location.getLatitude());
					tvlon.setText("" + location.getLongitude());
					getCompleteAddressString(location.getLatitude(),
							location.getLongitude());
				}

			} else {
				objcheckin.setlatitude(0.0);
				objcheckin.setlongitude(0.0);
			}
		} catch (Exception e) {
			Log.e("Error GetLocation", e.getMessage());
		}
	}

	private void GetCountry() {
		try {
			Country objCountry = new Country();
			listcountry = objCountry.getCountry();
			adapterCountry = new ArrayAdapter<Country>(this,
					android.R.layout.simple_spinner_item, listcountry);
			adapterCountry
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spCountry.setAdapter(adapterCountry);
		} catch (Exception e) {
			Log.e("Error GetCountry", e.getMessage());
		}

	}

	private void GetArea(int id) {
		try {

			Area objArea = new Area();
			ListArea = objArea.getArea(id);
			if (ListArea == null) {
				if (adapterArea != null) {
					adapterArea.clear();
				}
				return;
			}
			adapterArea = new ArrayAdapter<Area>(this,
					android.R.layout.simple_spinner_item, ListArea);
			adapterArea
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spArea.setAdapter(adapterArea);
		} catch (Exception e) {
			Log.e("Error GetArea", e.getMessage());
		}
	}

	private void GetCity() {
		try {

			city objCity = new city();
			listCity = objCity.getCity();
			adapterCity = new ArrayAdapter<city>(this,
					android.R.layout.simple_spinner_item, listCity);
			adapterCity
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spCity.setAdapter(adapterCity);
		} catch (Exception e) {
			Log.e("Error GetCity", e.getMessage());
		}
	}

	private void insertCheckInLocation_Clup() {
		try {
			objcheckin.setPDAEntryUserID(DistributionMain.UserID);
			objcheckin.setDeviceID(DistributionShowmain.DeviceID);
			if (GPS.canGetLocation()) {
				Location location = GPS.getLocation();
				if (location != null) {
					objcheckin.setlatitude(location.getLatitude());
					objcheckin.setlongitude(location.getLongitude());
				}

			} else {
				objcheckin.setlatitude(0.0);
				objcheckin.setlongitude(0.0);
			}
			objcheckin.setSubsName(txtSubsName.getText().toString().trim());
			objcheckin.setPhone(txtPhone.getText().toString().trim());
			objcheckin.setPosName(txtPosName.getText().toString().trim());
			objcheckin.setPosAddress(tvaddress.getText().toString());
			objcheckin.setStreet(tvStreet.getText().toString());
			objcheckin.setRefID(adapterRef.getItem(
					spreference.getSelectedItemPosition()).GetID());
			objcheckin.setCity(adapterCity.getItem(
					spCity.getSelectedItemPosition()).GetID());
			objcheckin.setCountry(adapterCountry.getItem(
					spCountry.getSelectedItemPosition()).GetID());
			objcheckin.setArea(adapterArea.getItem(
					spArea.getSelectedItemPosition()).GetID());
			objcheckin.setNote(txtNote.getText().toString().toString().trim());
		} catch (Exception e) {
//			Log.e("Error insertCheckInLocation_Clup ", e.getMessage());
		}
	}

	protected static void setLocale(final Context ctx, final String lang) {

		final Locale loc = new Locale(lang);
		Locale.setDefault(loc);
		final Configuration cfg = new Configuration();
		cfg.locale = loc;
		ctx.getResources().updateConfiguration(cfg, null);
	}

	private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
		String strAdd = "";
		String Street = "";
		Geocoder geocoder = new Geocoder(this, loca);
		try {
			List<Address> addresses = geocoder.getFromLocation(LATITUDE,
					LONGITUDE, 1);
			if (addresses != null) {
				Address returnedAddress = addresses.get(0);
				StringBuilder strReturnedAddress = new StringBuilder("");

				for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
					strReturnedAddress
							.append(returnedAddress.getAddressLine(i)).append(
									"\n");
				}
				strAdd = returnedAddress.getCountryName() + "-"
						// + returnedAddress.getAdminArea() + "-"
						+ strReturnedAddress.toString() + "-"
						+ returnedAddress.getCountryCode();
				Street = strReturnedAddress.toString();
				if (Street == null) {
					return Street;
				}
				tvStreet.setText(Street);
				tvaddress.setText(strAdd);
//				Log.w("My Current loction address",
//						"" + strReturnedAddress.toString());
			} else {
//				Log.w("My Current loction address", "No Address returned!");
			}

		} catch (Exception e) {
			e.printStackTrace();
//			Log.w("My Current loction address", "Canont get Address!");
		}
		return strAdd;
	}

//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.btoSave_CheckinClup:
//			try {
//
//				objcheckin = objcheckin.checkcountposname(txtPosName.getText()
//						.toString());
//				insertCheckInLocation_Clup();
//				if (txtPosName.getText().toString().equals("")
//						|| txtNote.getText().toString().equals("")) {
//					GetToast.Toast(context, "Pos or Note Is Empty");
//					return;
//				}
//
//				new SaveContractTask().execute();
//			} catch (Exception e) {
//				Log.e("Error Save Contract Task", e.getMessage());
//			}
//
//			break;
//		case R.id.btoCancel_checkinClup:
//			CheckInClup.this.finish();
//			break;
//		default:
//			break;
//		}
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
			if(v.getId()==R.id.btoSave_CheckinClup) {
				try {

					objcheckin = objcheckin.checkcountposname(txtPosName.getText()
							.toString());
					insertCheckInLocation_Clup();
					if (txtPosName.getText().toString().equals("")
							|| txtNote.getText().toString().equals("")) {
						GetToast.Toast(context, "Pos or Note Is Empty");
						return;
					}

					new SaveContractTask().execute();
				} catch (Exception e) {
//					Log.e("Error Save Contract Task", e.getMessage());
				}

			}else if (v.getId() == R.id.btoCancel_checkinClup){
				CheckInClup.this.finish();

		}
	}

	private class SaveContractTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute() {
			objShowProgressDialog = new ShowProgressDialog(context,
					getString(R.string.Please_Wait), getString(R.string.Save));
			objShowProgressDialog.ShowDialog();
			super.onPreExecute();

		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub

			return objcheckin.InsertCheckinLocation_Club(objcheckin);
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			try {
				objShowProgressDialog.EndDialog();
				if (result > 0) {
					GetToast.Toast(context, getString(R.string.Save_Done));
					CheckInClup.this.finish();
				}// end If

			} catch (Exception e) {
				// TODO: handle exception
				Log.e("Check in Pos", e.getMessage());
				// CatchMsg.WriteMSG("RenewAppointment", e.getMessage());
			}

		}
	}

}
