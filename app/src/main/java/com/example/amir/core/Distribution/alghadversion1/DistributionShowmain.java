package com.example.amir.core.Distribution.alghadversion1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.example.amir.core.Distribution.alghadversion1.Classes.GPSTracker;
import com.example.amir.core.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class DistributionShowmain extends Activity implements LocationListener {
	EditText txtuserName;
	// TextView tvlon, tvlan, tvAddreass;
	Timer timer;

	Location location;
	public static String DeviceID;
	TelephonyManager telephonyManager;
	GPSTracker GPS;
	Context context = this;

    LinearLayout llDistAppoint ,llDistAddContract ,llDisReports ;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (android.os.Build.VERSION.SDK_INT > 8) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distributionshowmain);
		InitialView();
		//OpenTimer();

	}// End On Create

	public boolean onPrepareOptionsMenu(Menu menu)
	{
	    MenuItem  item1 = menu.findItem(R.id.Showmain_Appointments);
	    MenuItem  item2 = menu.findItem(R.id.Showmain_Field_Visit);
	    MenuItem  item3 = menu.findItem(R.id.Showmain_Profiles);
	    MenuItem  item4 = menu.findItem(R.id.Showmain_Reports);

	    if(DistributionMain.UserName.startsWith("dist"))//(DistributionMain.UserID == 300||DistributionMain.UserID == 301)
	    {           
	    	item1.setVisible(false);
	    	item2.setVisible(false);
	    	item3.setVisible(false);
	    	item4.setVisible(false);
	    	
	    }
	    else
	    {
	    	item1.setVisible(true);
	    	item2.setVisible(true);
	    	item3.setVisible(true);
	    	item4.setVisible(true);
	    }
	    return true;
	}
	
	private void InitialView() {
		txtuserName = (EditText) findViewById(R.id.ShowMain_TxTSalesRep);

		// tvlan = (TextView) findViewById(R.id.tvlan);
		// tvlon = (TextView) findViewById(R.id.tvlon);
		// tvAddreass = (TextView) findViewById(R.id.tvaddress);
		txtuserName.setText(DistributionMain.DriverName);
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        llDistAppoint = (LinearLayout)findViewById(R.id.llDistAppoint);
        llDistAddContract = (LinearLayout)findViewById(R.id.llDistAddContract);
        llDisReports = (LinearLayout)findViewById(R.id.llDisReports);

        llDistAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DistributionShowmain.this, AppointmentsClass.class));
            }
        });
        llDistAddContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DistributionShowmain.this, Profiles.class));
            }
        });
        llDisReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DistributionShowmain.this, DailyWorkShow.class));
            }
        });
		// DeviceID = telephonyManager.getDeviceId().toString();
		if (telephonyManager.getDeviceId()!=null) {
			
			DeviceID = telephonyManager.getDeviceId().toString();
			
		} else {
			DeviceID = Secure.getString(context.getContentResolver(),
					Secure.ANDROID_ID);
		}
		try {

			BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
					.getDefaultAdapter();
			if (mBluetoothAdapter == null) {
				// Device does not support Bluetooth
				GetToast.Toast(context, getString(R.string.DevicedoesnotsupportBluetooth));
			} else {
				if (!mBluetoothAdapter.isEnabled()) {
					// Bluetooth is not enable :)
					GetToast.Toast(context, getString(R.string.Bluetoothisnotenable));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void OpenTimer() {

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				runOnUiThread(new Runnable() {

					public void run() {
						Insertlocation();
					}
				});

			}
		}, 0, 15000);

	}// End Open Timer

	private void Insertlocation() {
		// GPS = new GPSTracker(context);
		// location = GPS.getLocation();
		new InsertLocation().execute(location);
	}

	@SuppressWarnings("static-access")
	@SuppressLint("NewApi")
	private void getAddress(Location location) {
		Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
		try {
			if (geocoder.isPresent()) {
				List<Address> addresses = geocoder.getFromLocation(
						location.getLatitude(), location.getLongitude(), 1);
				if (addresses != null) {
					Address returnedAddress = addresses.get(0);
					StringBuilder strReturnedAddress = new StringBuilder(
							"Street : ");
					for (int i = 0; i < returnedAddress
							.getMaxAddressLineIndex(); i++) {
						strReturnedAddress.append(
								returnedAddress.getAddressLine(i)).append("\n");
					}
					// tvAddreass.setText(strReturnedAddress.toString());
				} else {
					// tvAddreass.setText("No Address returned!");
				}
			} else {
				// tvAddreass.setText("geocoder not present");
				// tvAddreass.setTextColor(Color.RED);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// tvAddreass.setText("Canont get Address! \n" + e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.distshowmain, menu);

		return super.onCreateOptionsMenu(menu);
	}// End On Create Option Menu

	@Override
	public void onBackPressed() {
		System.exit(1);
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.Showmain_Appointments:
//			startActivity(new Intent(DistributionShowmain.this, AppointmentsClass.class));
//			break;
//		case R.id.Showmain_Change_Password:
//			startActivity(new Intent(DistributionShowmain.this, ChangePassword.class));
//			break;
//		// case R.id.Showmain_Check_For_Update:
//		// break;
//		case R.id.Showmain_Check_In:
//			try {
//				Intent objIntent = new Intent(DistributionShowmain.this,
//						checkin_activity.class);
//				startActivity(objIntent);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			break;
//		case R.id.Showmain_CheckIn_Clup:
//			try {
//				Intent objIntent = new Intent(DistributionShowmain.this,
//						CheckInClup.class);
//				startActivity(objIntent);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			break;
//		case R.id.Showmain_DistCheckIn:
//			startActivity(new Intent(DistributionShowmain.this, DistCheckIn.class));
//			break;
//		case R.id.Showmain_Daily_Work:
//			startActivity(new Intent(DistributionShowmain.this, DailyWorkShow.class));
//			break;
//		case R.id.Showmain_Device_ID:
//			GetToast.Toast(context, getString(R.string.DeviceID)+ DeviceID);
//			break;
//		case R.id.Showmain_Field_Visit:
//			startActivity(new Intent(DistributionShowmain.this, FieldVisit.class));
//			break;
//		case R.id.Showmain_Notification_Result:
//			startActivity(new Intent(DistributionShowmain.this, CallResultReportShow.class));
//			break;
//		case R.id.Showmain_Profiles:
//			startActivity(new Intent(DistributionShowmain.this, Profiles.class));
//			break;
//		case R.id.Showmain_Reports:
//			break;
//		default:
//			break;
//
//		}// switch
//
//		return false;
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
			if(item.getItemId() == R.id.Showmain_Appointments) {
				startActivity(new Intent(DistributionShowmain.this, AppointmentsClass.class));
			}else if(item.getItemId() == R.id.Showmain_Change_Password) {
				startActivity(new Intent(DistributionShowmain.this, ChangePassword.class));
			}else if(item.getItemId() == R.id.Showmain_Check_In) {
				try {
					Intent objIntent = new Intent(DistributionShowmain.this,
							checkin_activity.class);
					startActivity(objIntent);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}else if(item.getItemId() == R.id.Showmain_CheckIn_Clup){
				try {
					Intent objIntent = new Intent(DistributionShowmain.this,
							CheckInClup.class);
					startActivity(objIntent);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}else if(item.getItemId() == R.id.Showmain_DistCheckIn) {
				startActivity(new Intent(DistributionShowmain.this, DistCheckIn.class));
			}else if (item.getItemId() == R.id.Showmain_Daily_Work) {
				startActivity(new Intent(DistributionShowmain.this, DailyWorkShow.class));
			}else if(item.getItemId() == R.id.Showmain_Device_ID) {
				GetToast.Toast(context, getString(R.string.DeviceID) + DeviceID);
			}else if(item.getItemId() == R.id.Showmain_Field_Visit) {
				startActivity(new Intent(DistributionShowmain.this, FieldVisit.class));
			}else if(item.getItemId() == R.id.Showmain_Notification_Result) {
				startActivity(new Intent(DistributionShowmain.this, CallResultReportShow.class));
			}else if(item.getItemId() == R.id.Showmain_Profiles) {
				startActivity(new Intent(DistributionShowmain.this, Profiles.class));
			}else if (item.getItemId() == R.id.Showmain_Reports) {
			}
			return false;
	}

	// private void InsertCheckIn() {
	// CheckIn objCheckIn = new CheckIn();
	// String result = objCheckIn.InsertCheckIn(tvlan.getText().toString(),
	// tvlon.getText().toString());
	//
	// if (result.equals("Done"))
	// GetToast.Toast(context, getString(R.string.Done));
	// else
	// GetToast.Toast(context, getString(R.string.Error));
	// }

	private class InsertLocation extends AsyncTask<Location, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			GPS = new GPSTracker(context);
		}

		@Override
		protected Boolean doInBackground(Location... params) {
			// Looper.prepare();
			try {

				if (GPS.canGetLocation()) {
					location = GPS.getLocation();
					if (location != null) {
						GPS.InsertLocation("" + location.getLongitude(), ""
								+ location.getLatitude());
					}
					// getAddress(location);
				}

			} catch (Exception e) {
				Log.d("Do on back ", e.getMessage());
			}

			// location = GPS.getLocation();
			// GPS.InsertLocation("" + params[0].getLongitude(),
			// "" + params[0].getLatitude());

			return GPS.canGetLocation();
		}

		@Override
		protected void onPostExecute(Boolean result) {

			if (result) {
				// if (result == null) {
				// new GetToast(DistributionShowmain.this, "location ");
				// return;
				// }
				// location = GPS.getLocation();
				// tvlon.setText("" + GPS.getLongitude());
				// tvlan.setText("" + GPS.getLatitude());

				// getAddress(location);
			} else
				GPS.showSettingsAlert();
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}// End Class Show DistributionMain
