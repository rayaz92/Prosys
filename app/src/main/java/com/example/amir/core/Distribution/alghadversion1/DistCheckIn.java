package com.example.amir.core.Distribution.alghadversion1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.amir.core.Distribution.alghadversion1.Classes.CheckIn;
import com.example.amir.core.Distribution.alghadversion1.Classes.DistributionLine;
import com.example.amir.core.Distribution.alghadversion1.Classes.Distributor;
import com.example.amir.core.Distribution.alghadversion1.Classes.GPSTracker;
import com.example.amir.core.Distribution.alghadversion1.Classes.Supervisor;
import com.example.amir.core.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DistCheckIn extends Activity implements OnClickListener{
	
	Button btnCheckIn,btnSearch,btnUpdate;
	Spinner spSupervisor ,spDistributor;
	int IDCheck;
	List<Supervisor> listSupervisor;
	ArrayAdapter<Supervisor> AdapterSupervisor;
	List<Distributor> listDistributor;
	ArrayAdapter<Distributor> AdapterDistributor;
	Supervisor objSupervisor = new Supervisor();
	Distributor objDistributor = new Distributor();
	GPSTracker GPS;
	CheckIn objcheckin;
	Context context = this;
	List<Distributor> listDailyDistributor;
	int SuperID	=0;
	int DistributorID=0;
	TextView  tvlan, tvlon,tvClient,tvContractID,tvStreet,tvaddress;
	ShowProgressDialog objShowProgressDialog;
	Double Latitude ,Longitue;

	DistributionLine objDistributionLine;
	List<DistributionLine> listDailyDistribution;
	ExpandableListView expandableListView;

	int ID= 0;
	Double Lon ,Lat;
	final Locale loca = new Locale("ar");

	boolean Updated = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distcheckin);{
			GPS = new GPSTracker(context);
			objcheckin = new CheckIn();
			objDistributionLine = new DistributionLine();
			InitailView();
			FillSpinner();
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


		}
		}
	private void InitailView() {
//		tvClient = (TextView) findViewById(R.id.tvClient );
//		tvContractID = (TextView) findViewById(R.id.tvContratID);
		tvlan = (TextView) findViewById(R.id.tvDistLat);
		tvlon = (TextView) findViewById(R.id.tvDistLon);
//		tvaddress = (TextView) findViewById(R.id.tvAddress_checkin);
		tvStreet = (TextView) findViewById(R.id.tvStreet_checkin);

		IDCheck=0;
		btnCheckIn = (Button) findViewById(R.id.BTNCheckIn);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnUpdate = (Button)findViewById(R.id.BTNUpdate);

		spSupervisor = (Spinner ) findViewById(R.id.SpSupervisor);
		spDistributor = (Spinner ) findViewById(R.id.SpDistributor);

		expandableListView=  (ExpandableListView) findViewById(R.id.expandableListViewSearchPro);

		expandableListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
										int groupPosition, int childPosition, long id) {

				ToDetails((Integer) ExpandbleListAdapter.childItem
						.get(3));

				if(objDistributionLine.GetLatitude()==0.0 && objDistributionLine.GetLongtiude()==0.0){

					v.setBackgroundColor(Color.GRAY);
					btnCheckIn.setEnabled(true);
					btnUpdate.setEnabled(false);

				}
				else{

				v.setBackgroundColor(Color.RED);
					btnCheckIn.setEnabled(false);
					btnUpdate.setEnabled(true);
				}
				try {

					ToDetails((Integer) ExpandbleListAdapter.childItem
							.get(3));
				} catch (Exception e) {
					Log.d("Error In  onChildClick ", e.getMessage());
				}

				return true;
			}
		});

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		spSupervisor.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				// TODO Auto-generated method stub

				SuperID = AdapterSupervisor.getItem(
						spSupervisor.getSelectedItemPosition()).GetID();
				FillSpinnerDistributor();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		spDistributor.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
									   int position, long id) {
				// TODO Auto-generated method stub

				DistributorID =AdapterDistributor.getItem(
						spDistributor.getSelectedItemPosition()).GetDistributorID();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});

		btnCheckIn.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);

		getlocation();
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
//				tvaddress.setText(strAdd);
			} else {
//				Log.w("My Current loction address", "No Address returned!");
			}

		} catch (Exception e) {
			e.printStackTrace();
//			Log.w("My Current loction address", "Canont get Address!");
		}

		return strAdd;
	}
	private void ToDetails(int id) {
		try {
			for (DistributionLine e : listDailyDistribution) {
				if (id == e.GetID()) {
					objDistributionLine = e;
				}// end if
			}// End For
		} catch (Exception e) {
			Log.d("Error in To Details ", e.getMessage());
			e.printStackTrace();
		}// End try Catch
	}// end Function
	public void FillSpinner() {
		try {
			Supervisor objSupervisor = new Supervisor();
			listSupervisor = objSupervisor.getSupervisor();
			AdapterSupervisor = new ArrayAdapter<Supervisor>(this,
					android.R.layout.simple_spinner_item, listSupervisor);
			AdapterSupervisor
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spSupervisor.setAdapter(AdapterSupervisor);

			 SuperID	= spSupervisor.getSelectedItemPosition();

			Distributor objDistributor = new Distributor();
			listDistributor = objDistributor.getDistributor(SuperID);
			AdapterDistributor = new ArrayAdapter<Distributor>(this,
					android.R.layout.simple_spinner_item, listDistributor);
			AdapterDistributor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spDistributor.setAdapter(AdapterDistributor);
		} catch (Exception e) {


		}// End Try Catch
	}// End FillSpinner

	public void FillSpinnerDistributor() {
		try {

			Distributor objDistributor = new Distributor();
			listDistributor = objDistributor.getDistributor(SuperID);
			AdapterDistributor = new ArrayAdapter<Distributor>(this,
					android.R.layout.simple_spinner_item, listDistributor);
			AdapterDistributor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spDistributor.setAdapter(AdapterDistributor);
		} catch (Exception e) {


		}// End Try Catch
	}// End FillSpinner
//	public boolean UpdatecontractDetailsCoordinates(){
//		boolean Check = false;
//		try {
//
//			Dis objDistributionLine = new CheckIn();
//			objDistributionLine.setID(tvContractID.getId());
//			if (GPS.canGetLocation()) {
//				Location location = GPS.getLocation();
//				if (location != null) {
//					objDistributionLine.setlatitude(location.getLatitude());
//					objDistributionLine.setlongitude(location.getLongitude());
//				}
//
//			} else {
//				objDistributionLine.setlatitude(0.0);
//				objDistributionLine.setlongitude(0.0);
//			}
//			Check = objDistributionLine.UpdatecontractDetailsLocation(objDistributionLine);
//
//			if (Check == true ){
//				return true ;
//			}
//		}
//		catch (Exception e) {
//			// TODO: handle exception
//			Log.e("Error Area", e.getMessage());
//		}
//		return Check ;
//	}
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

		return objDistributionLine.InsertContractDetailsLocation(objDistributionLine,DistributorID);
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		try {
			objShowProgressDialog.EndDialog();
			if (result > 0) {
				new getdata().execute();
				GetToast.Toast(context, getString(R.string.Save_Done));
//				DistCheckIn.this.finish();
			}// end If

		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Check in Pos", e.getMessage());
			// CatchMsg.WriteMSG("RenewAppointment", e.getMessage());
		}
	}
}

//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.BTNCheckIn:
//			try {
////				insertPosLocation();
//				if (GPS.canGetLocation()) {
//					Location location = GPS.getLocation();
//					if (location != null) {
//						objDistributionLine.setLatitude(location.getLatitude());
//						objDistributionLine.setLongtiude(location.getLongitude());
//						Latitude=location.getLatitude();
//						Longitue=location.getLongitude();
//
//					}
//
//				} else {
//					objDistributionLine.setLatitude(0.0);
//					objDistributionLine.setLongtiude(0.0);
//				}
//				new SaveContractTask().execute();
//
////			UpdatecontractDetailsCoordinates();
//			} catch (Exception e) {
//				// TODO: handle exception
//				Log.e("Error InsertPOS", e.getMessage());
//			}
//			break;
//			case R.id.BTNUpdate:
//
//				if (GPS.canGetLocation()) {
//					Location location = GPS.getLocation();
//					if (location != null) {
//						objDistributionLine.setLatitude(location.getLatitude());
//						objDistributionLine.setLongtiude(location.getLongitude());
//						Latitude=location.getLatitude();
//						Longitue=location.getLongitude();
//
//					}
//
//				} else {
//					objDistributionLine.setLatitude(0.0);
//					objDistributionLine.setLongtiude(0.0);
//				}
////				new UpdateContractTask().execute();
//
//				int ID = objDistributionLine.GetID();
//
//				Updated=objDistributionLine.UpdatecontractDetailsLocation(objDistributionLine);//ID,Longitue,Latitude);
//				if(Updated == true){
//					GetToast.Toast(context, getString(R.string.Save_Done));
//					new getdata().execute();
//				}
//
//				break;
//
//		case R.id.btnSearch:
//
//			new getdata().execute();
//		break;
//
//		default:
//
//	}
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
			if(v.getId() == R.id.BTNCheckIn) {
				try {
//				insertPosLocation();
					if (GPS.canGetLocation()) {
						Location location = GPS.getLocation();
						if (location != null) {
							objDistributionLine.setLatitude(location.getLatitude());
							objDistributionLine.setLongtiude(location.getLongitude());
							Latitude = location.getLatitude();
							Longitue = location.getLongitude();

						}

					} else {
						objDistributionLine.setLatitude(0.0);
						objDistributionLine.setLongtiude(0.0);
					}
					new SaveContractTask().execute();

//			UpdatecontractDetailsCoordinates();
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("Error InsertPOS", e.getMessage());
				}
			}else if(v.getId() == R.id.BTNUpdate) {

				if (GPS.canGetLocation()) {
					Location location = GPS.getLocation();
					if (location != null) {
						objDistributionLine.setLatitude(location.getLatitude());
						objDistributionLine.setLongtiude(location.getLongitude());
						Latitude = location.getLatitude();
						Longitue = location.getLongitude();

					}

				} else {
					objDistributionLine.setLatitude(0.0);
					objDistributionLine.setLongtiude(0.0);
				}
//				new UpdateContractTask().execute();

				int ID = objDistributionLine.GetID();

				Updated = objDistributionLine.UpdatecontractDetailsLocation(objDistributionLine);//ID,Longitue,Latitude);
				if (Updated == true) {
					GetToast.Toast(context, getString(R.string.Save_Done));
					new getdata().execute();
				}

			}else if(v.getId() ==  R.id.btnSearch){

				new getdata().execute();


		}
	}

	private void GetDailyDistributionByDistributor(int id) {
		try {

			Distributor objDistribution = new Distributor();
			listDailyDistributor = objDistribution.GetDailyDistributionByDistributor(DistributorID);
			if (listDailyDistributor == null) {
				if (AdapterDistributor != null) {
					AdapterDistributor.clear();
				}
				return;
			}

			AdapterDistributor = new ArrayAdapter<Distributor>(this,
					android.R.layout.simple_list_item_single_choice, listDailyDistributor);
			listDailyDistributor = new ArrayList<Distributor>(listDailyDistributor);

			Dialog AreaChoice=	onCreateDialogSingleChoice(AdapterDistributor);
			AreaChoice.show();


		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Error Area", e.getMessage());
		}
	}
	public Dialog onCreateDialogSingleChoice(ArrayAdapter adapterArea) {


		AlertDialog.Builder builder = new AlertDialog.Builder(this);

//			CharSequence[] array = {"High", "Medium", "Low"};

		builder.setTitle("Select Client")
				.setSingleChoiceItems(adapterArea, 1, new DialogInterface.OnClickListener() {

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

						Distributor SelectedClient=(Distributor)checkedItem;

						Toast.makeText(getApplicationContext(),
								checkedItem.toString(), Toast.LENGTH_LONG).show();
						//txtSearchArea.setText(checkedItem.toString());
						tvClient.setText(SelectedClient.GetSubscriberName());
//						tvContractID.setText(SelectedClient.GetContractID());
						tvContractID.setId(SelectedClient.GetID());
						btnCheckIn.setEnabled(true) ;

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

	private void AddToExpandList() {
		try {

			ArrayList<String> parent = new ArrayList<String>();
			ArrayList<Object> Child = new ArrayList<Object>();
			for (DistributionLine e : listDailyDistribution) {
				ArrayList<Object> child = new ArrayList<Object>();
				parent.add(e.GetSubscriberName());
				child.add(e.GetClientID());
				child.add(e.GetSubscriberName());
				child.add(e.GetContractID());
				child.add(e.GetID());
				child.add(e.GetLatitude());
				child.add(e.GetLongtiude());
				Child.add(child);
			}// End For
			expandableListView
					.setAdapter(new ExpandbleListAdapter(
							DistCheckIn.this,
							parent,
							Child,
							ExpandbleListAdapter.CurrentMode.Distribution));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}// End Function
	private class getdata extends AsyncTask<String, Void, Void> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {

			progressDialog = new ProgressDialog(context);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage(getString(R.string.Please_Wait));
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();

		}

		@Override
		protected Void doInBackground(String... params) {


			listDailyDistribution = objDistributionLine.GetDailyDistributionByDistributorAndSupervisor(DistributorID,SuperID
					,-1,-1);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			if (listDailyDistribution == null) {
				GetToast.Toast(context, getString(R.string.NoData));
				return;
			}// End if list =null
			AddToExpandList();
		}
	}// Get data

    }
