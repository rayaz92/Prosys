package com.example.amir.core.Distribution.alghadversion1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amir.core.Distribution.alghadversion1.Classes.Area;
import com.example.amir.core.Distribution.alghadversion1.Classes.CheckIn;
import com.example.amir.core.Distribution.alghadversion1.Classes.Connection;
import com.example.amir.core.Distribution.alghadversion1.Classes.Country;
import com.example.amir.core.Distribution.alghadversion1.Classes.GPSTracker;
import com.example.amir.core.Distribution.alghadversion1.Classes.city;
import com.example.amir.core.R;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class checkin_activity extends Activity implements OnClickListener {
	EditText txtPhone, txtSubsName, txtPosName, txtNote, txtSearchArea;
	TextView tvlan, tvlon, tvaddress, tvposname, tvposaddress, tvposnote,
			tvStreet;
	Button btoSave, btoCancel, btnSearch;
	
	Spinner spreference, spCity,  spCountry; //spArea
	CheckIn objcheckin;

	List<CheckIn> listRefernce;
	List<city> listCity;
	List<Country> listcountry;
	List<Address> addresses;
	List<Area> ListArea;

	ArrayAdapter<CheckIn> adapterRef;
	ArrayAdapter<city> adapterCity;
	ArrayAdapter<Country> adapterCountry;
	ArrayAdapter<Area> adapterArea;

	Timer timer;
	GPSTracker GPS;
	Context context = this;
	Geocoder geocoder;

	LocationManager location_manager;
	Location loc;
	ShowProgressDialog objShowProgressDialog;
	double x;
	LinearLayout linPosName, linPosAddress, linNote;

	double y;
	String getLatitude;
	String getLongitude;
	final Locale loca = new Locale("ar");
	boolean Done =false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkin);

		objcheckin = new CheckIn();
		GPS = new GPSTracker(context);
		try {

			if (Connection.isConnectingToInternet(context)) {
				InitialView();
			} else {
				Connection.showAlertDialog(context);
			}
		} catch (Exception e) {
			Log.e("Error Checkin activity", e.getMessage());
		}

	}

	private void InitialView() {

		txtPosName = (EditText) findViewById(R.id.txtPosName_checkIn);
		txtNote = (EditText) findViewById(R.id.txtnote_checkIn);
		txtPhone = (EditText) findViewById(R.id.txtPh_Mobile_checkIn);
		txtSubsName = (EditText) findViewById(R.id.txtSubscriberName_checkIn);
		txtSearchArea =(EditText) findViewById(R.id.txtSearchArea );

		tvlan = (TextView) findViewById(R.id.tvlan_checkin);
		tvlon = (TextView) findViewById(R.id.tvlon_checkin);
		tvaddress = (TextView) findViewById(R.id.tvAddress_checkin);
		tvposname = (TextView) findViewById(R.id.tvPosName_checkIn);
		tvposaddress = (TextView) findViewById(R.id.tvPosAddress_checkIn);
		tvposnote = (TextView) findViewById(R.id.tvPosNote_checkIn);
		tvStreet = (TextView) findViewById(R.id.tvStreet_checkin);

		btoSave = (Button) findViewById(R.id.btoSave_Checkin);
		btoCancel = (Button) findViewById(R.id.btoCancel_checkin);
		btnSearch=(Button) findViewById(R.id.btnSearchArea_checkin);

		spreference = (Spinner) findViewById(R.id.spRef_checkIn);
		spCity = (Spinner) findViewById(R.id.SpCity_checkin);
//		spArea = (Spinner) findViewById(R.id.spArea_checkin);
		spCountry = (Spinner) findViewById(R.id.spCountry_checkin);

		linPosName = (LinearLayout) findViewById(R.id.linposname);
		linPosAddress = (LinearLayout) findViewById(R.id.linPosAddress);
		linNote = (LinearLayout) findViewById(R.id.linPosNote);

		location_manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		spCountry.setEnabled(false);
		GetReferance();
		GetCountry();
		GetCity();

		spCity.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				int CityID = adapterCity.getItem(
//						spCity.getSelectedItemPosition()).GetID();
//				String FilterArea = txtSearchArea.getText().toString();
				//GetArea(CityID);
//				GetFilterArea(CityID , FilterArea);
				txtSearchArea.setText("");
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		//OpenTimer();
		getlocation();
		btoSave.setOnClickListener(this);
		btoCancel.setOnClickListener(this);
		btnSearch.setOnClickListener(this);


		txtSearchArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                   //here is your code

            	if (txtSearchArea.getText().toString().equals(""))
            	{
            		Done=false;
            	}

            	String FilterArea = txtSearchArea.getText().toString();

    			int CityID = adapterCity.getItem(
    					spCity.getSelectedItemPosition()).GetID();

    			if(Done == false){
            	if(txtSearchArea.getText().length()	> 2)
            		{
            			GetFilterArea(CityID , FilterArea);

            				}
    			}

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                // TODO Auto-generated method stub
            	if (txtSearchArea.getText().toString().equals(""))
            	{
            		Done=false;
            	}
            }
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

        });
	}


	private void GetFilterArea(int id,String FilterArea) {
		try {

			Area objArea = new Area();
			ListArea = objArea.getFilterArea(id,FilterArea);
			if (ListArea == null) {
				if (adapterArea != null) {
					adapterArea.clear();
				}
				return;
			}

			adapterArea = new ArrayAdapter<Area>(this,
					android.R.layout.simple_list_item_single_choice, ListArea);
			ListArea = new ArrayList<Area>(ListArea);

			Dialog AreaChoice=	onCreateDialogSingleChoice(adapterArea);
			AreaChoice.show();


//			adapterArea = new ArrayAdapter<Area>(this,
//					android.R.layout.simple_spinner_item, ListArea);
//			adapterArea
//					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//			spArea.setAdapter(adapterArea);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Error Area", e.getMessage());
		}
	}
	private void GetReferance() {
		try {

			CheckIn objCheckIn = new CheckIn();

			listRefernce = objCheckIn.SelectRefernce();
			adapterRef = new ArrayAdapter<CheckIn>(this,
					android.R.layout.simple_spinner_item, listRefernce);
			adapterRef
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spreference.setAdapter(adapterRef);
		} catch (Exception e) {
			Log.e("Error Referance", e.getMessage());
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
			// TODO: handle exception
			Log.e("Error Country", e.getMessage());
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
			Log.e("Error City", e.getMessage());
		}
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
							Log.e("Error GetLocation", e.getMessage());
						}

					}
				});

			}
		}, 0, 07000);

	}// End Open Timer
	// private void returntext() {
	// getlocation();
	// linPosName.setVisibility(View.GONE);
	// linPosAddress.setVisibility(View.GONE);
	// linNote.setVisibility(View.GONE);
	// linPosName.setBackgroundColor(getResources().getColor(R.color.Red));
	// linPosAddress.setBackgroundColor(getResources().getColor(
	// R.color.Green));
	// linNote.setBackgroundColor(getResources().getColor(R.color.Green));
	// txtPosName.setText("");
	// txtNote.setText("");
	// spreference.setSelection(0);
	// }

	// private int checkposname(String PosName) {
	//
	// objcheckin = new CheckIn();
	// return objcheckin.checkcountposname(PosName);
	//
	// }
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.btoSave_Checkin:
//			try {
//
//				btoSave.setEnabled(false);
//				objcheckin = objcheckin.checkcountposname(txtPosName.getText()
//						.toString());
//				// int countname = objcheckin.GetCountPosName();
//
//
//				insertPosLocation();
//				//DistributionMain.UserID=0;
//				if (DistributionMain.UserID == 0)
//				{
//					GetToast.Toast(context, getString(R.string.PleaseReLogIn));
//					checkin_activity.this.finish();
//					Intent intent = new Intent(context, DistributionMain .class);
//					startActivity(intent);
//					finish();
//					break;
//
//				}
//
//				if (
//						txtPosName.getText().toString().equals("")
//
//						|| txtNote.getText().toString().equals("")
//						|| txtSearchArea.getText().toString().equals("")) {
//					GetToast.Toast(context, getString(R.string.PosorNoteorAreaIsEmpty));
//					btoSave.setEnabled(true);
//					return;
//				}
//
//				// if (countname > 0) {
//				// GetToast.Toast(context,
//				// getString(R.string.ThisPOSNamealreadyexist));
//				//
//				// tvposname.setText(objcheckin.GetPosNameCheck());
//				// tvposaddress.setText(objcheckin.GetPosAddressCheck());
//				// tvposnote.setText(objcheckin.GetNoteCheck());
//				// linPosName.setVisibility(View.VISIBLE);
//				// linPosAddress.setVisibility(View.VISIBLE);
//				// linNote.setVisibility(View.VISIBLE);
//				// linPosName.setBackgroundColor(getResources().getColor(
//				// R.color.LIghtGreen));
//				// linPosAddress.setBackgroundColor(getResources().getColor(
//				// R.color.LIghtGreen));
//				// linNote.setBackgroundColor(getResources().getColor(
//				// R.color.LIghtGreen));
//				// } else {
//				new SaveContractTask().execute();
//				// if (x > 0) {
//				// GetToast.Toast(context, "Save Done");
//				// returntext();
//				// checkin_activity.this.finish();
//				// } else {
//				// GetToast.Toast(context, "Error");
//				// }
//				// }
//			} catch (Exception e) {
//				// TODO: handle exception
//				Log.e("Error InsertPOS", e.getMessage());
//			}
//			break;
//		case R.id.btnSearchArea_checkin:
//
//			String FilterArea = txtSearchArea.getText().toString();
//
//			int CityID = adapterCity.getItem(
//					spCity.getSelectedItemPosition()).GetID();
//			if (txtSearchArea.getText().length()> 2 || equals("")){
//			GetFilterArea(CityID , FilterArea);}
//			else{
//				GetToast.Toast(context, getString(R.string.PleaseEnterMinimum3characters));
//				return;
//			}
////				Dialog dialog = onCreateDialogSingleChoice();
////				dialog.show();
//			break;
//		case R.id.btoCancel_checkin:
//			try {
//				checkin_activity.this.finish();
//			} catch (Exception e) {
//				Log.e("Error Cancel", e.getMessage());
//			}
//			break;
//		default:
//			break;
//		}
//
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
			if(v.getId() == R.id.btoSave_Checkin) {
				try {

					btoSave.setEnabled(false);
					objcheckin = objcheckin.checkcountposname(txtPosName.getText()
							.toString());
					// int countname = objcheckin.GetCountPosName();


					insertPosLocation();
					//DistributionMain.UserID=0;
					if (DistributionMain.UserID == 0) {
						GetToast.Toast(context, getString(R.string.PleaseReLogIn));
						checkin_activity.this.finish();
						Intent intent = new Intent(context, DistributionMain.class);
						startActivity(intent);
						finish();
						return;

					}

					if (
							txtPosName.getText().toString().equals("")

									|| txtNote.getText().toString().equals("")
									|| txtSearchArea.getText().toString().equals("")) {
						GetToast.Toast(context, getString(R.string.PosorNoteorAreaIsEmpty));
						btoSave.setEnabled(true);
						return;
					}

					// if (countname > 0) {
					// GetToast.Toast(context,
					// getString(R.string.ThisPOSNamealreadyexist));
					//
					// tvposname.setText(objcheckin.GetPosNameCheck());
					// tvposaddress.setText(objcheckin.GetPosAddressCheck());
					// tvposnote.setText(objcheckin.GetNoteCheck());
					// linPosName.setVisibility(View.VISIBLE);
					// linPosAddress.setVisibility(View.VISIBLE);
					// linNote.setVisibility(View.VISIBLE);
					// linPosName.setBackgroundColor(getResources().getColor(
					// R.color.LIghtGreen));
					// linPosAddress.setBackgroundColor(getResources().getColor(
					// R.color.LIghtGreen));
					// linNote.setBackgroundColor(getResources().getColor(
					// R.color.LIghtGreen));
					// } else {
					new SaveContractTask().execute();
					// if (x > 0) {
					// GetToast.Toast(context, "Save Done");
					// returntext();
					// checkin_activity.this.finish();
					// } else {
					// GetToast.Toast(context, "Error");
					// }
					// }
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("Error InsertPOS", e.getMessage());
				}
			}else if(v.getId() == R.id.btnSearchArea_checkin) {

				String FilterArea = txtSearchArea.getText().toString();

				int CityID = adapterCity.getItem(
						spCity.getSelectedItemPosition()).GetID();
				if (txtSearchArea.getText().length() > 2 || equals("")) {
					GetFilterArea(CityID, FilterArea);
				} else {
					GetToast.Toast(context, getString(R.string.PleaseEnterMinimum3characters));
					return;
				}
//				Dialog dialog = onCreateDialogSingleChoice();
//				dialog.show();
			}else if (v.getId() == R.id.btoCancel_checkin) {
				try {
					checkin_activity.this.finish();
				} catch (Exception e) {
					Log.e("Error Cancel", e.getMessage());
				}
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

			return objcheckin.InsertPosLocation(objcheckin);
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			try {
				objShowProgressDialog.EndDialog();
				if (result > 0) {
					GetToast.Toast(context, getString(R.string.Save_Done));
					checkin_activity.this.finish();
				}// end If

			} catch (Exception e) {
				// TODO: handle exception
				Log.e("Check in Pos", e.getMessage());

				// CatchMsg.WriteMSG("RenewAppointment", e.getMessage());
			}

		}
	}

	private void insertPosLocation() {
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
			
//			ListView lw = ((AlertDialog)dialog).getListView();
//			Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
			
			objcheckin.setSubsName(txtSubsName.getText().toString().trim());
			objcheckin.setPhone(txtPhone.getText().toString().trim());
			objcheckin.setPosName(txtPosName.getText().toString().trim());
			objcheckin.setPosAddress(tvaddress.getText().toString());
			objcheckin.setRefID(adapterRef.getItem(
					spreference.getSelectedItemPosition()).GetID());
			objcheckin.setNote(txtNote.getText().toString().toString().trim());
			objcheckin.setStreet(tvStreet.getText().toString());
			objcheckin.setCity(adapterCity.getItem(
					spCity.getSelectedItemPosition()).GetID());
			objcheckin.setArea(txtSearchArea.getId());
			objcheckin.setCountry(adapterCountry.getItem(
					spCountry.getSelectedItemPosition()).GetID());
		} catch (Exception e) {
			Log.e("Error insertPosLocation", e.getMessage());
		}
	}

	protected static void setLocale(final Context ctx, final String lang) {
		final Locale loc = new Locale(lang);
		Locale.setDefault(loc);
		final Configuration cfg = new Configuration();
		cfg.locale = loc;
		ctx.getResources().updateConfiguration(cfg, null);
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

	private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {

		String strAdd = "", Street = "";

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
						+ strReturnedAddress.toString() + "-"
						+ returnedAddress.getCountryCode();
				Street = strReturnedAddress.toString();
//				Log.w("My Current loction address",
//						"" + strReturnedAddress.toString());
				if (Street == null) {
					return Street;
				}
				tvStreet.setText(Street);
				tvaddress.setText(strAdd);
			} else {
//				Log.w("My Current loction address", "No Address returned!");
			}

		} catch (Exception e) {
			e.printStackTrace();
//			Log.w("My Current loction address", "Canont get Address!");
		}

		return strAdd;
	}
	
		public Dialog onCreateDialogSingleChoice(ArrayAdapter adapterArea) {

			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
//			CharSequence[] array = {"High", "Medium", "Low"};

			builder.setTitle("Select Area")
			.setSingleChoiceItems(adapterArea, 0, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
		 	//TODO Auto-generated method stub

			}
			})
			.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
			public void onClick(DialogInterface dialog, int id) {
					ListView lw = ((AlertDialog)dialog).getListView();
					Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
					
					Area SelectedArea=(Area)checkedItem;
					
					  Toast.makeText(getApplicationContext(), 
							  checkedItem.toString(), Toast.LENGTH_LONG).show();
					  //txtSearchArea.setText(checkedItem.toString());
					  txtSearchArea.setText(SelectedArea.GetNameEn());
					  txtSearchArea.setId(SelectedArea.GetID());
					  Done=true;
					  
					  
			}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
	});
		return builder.create();
			}
			
		
}
