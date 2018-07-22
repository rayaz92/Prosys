package com.example.amir.core.Distribution.alghadversion1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;


import com.example.amir.core.Distribution.alghadversion1.Classes.Client;
import com.example.amir.core.Distribution.alghadversion1.Classes.ClientTypeGroup;
import com.example.amir.core.Distribution.alghadversion1.Classes.ClientTypeGroupClassification;
import com.example.amir.core.Distribution.alghadversion1.Classes.Connection;
import com.example.amir.core.Distribution.alghadversion1.Classes.Contract;
import com.example.amir.core.Distribution.alghadversion1.Classes.GPSTracker;
import com.example.amir.core.Distribution.alghadversion1.Classes.SubscriberLocation;
import com.example.amir.core.R;

import java.util.ArrayList;
import java.util.List;

public class Profiles extends Activity implements OnClickListener,
		OnItemSelectedListener {
	public enum Mode {
		Edit, Add,

	};

	public static String NationalNo = "", ClientMobile = "";
	public Mode CurrentMode;
	EditText TxTName, TxTPhone, TxTIDDetails, TxTNameDetails, TxTCompName,
			TxTNationalNo, TxTPhoneDetails, TxTMobileDetails, TxTEmailDetails,
			TxTNote, TxTAddress, edtchngName;

	Button BtnSearch, BtnAddDetails, BtnEditDetails, BtnSaveDetails,
			BtnRevertDetails, BtnViewContract, BtnReNew, BtnchngName,
			BtnchngCompany, BtnchngPhone, BtnChngEmail, BtnChngNationalNo,
			BtnChngMobile, BtnChngNote, BtnChngClassification, BtnChngOffer,
			BtnFillInfo, BtnClear, btnCheckin;
	String TxTchngName = "", TxTchngCompany = "", TxTchngPhone = "",
			TxTChngEmail = "", TxTChngNationalNo = "", TxTChngMobile = "",
			TxTChngNote = "", TxTChngClassification = "", TxTChngOffer = "",
			TxTFillInfo = "";

	String CollectedText = "";
	List<Client> listProfile;
	List<ClientTypeGroup> listclientTypeGroups;
	ArrayAdapter<ClientTypeGroup> AdapterTypeGroup;
	List<ClientTypeGroupClassification> listClassifications;
	ArrayAdapter<ClientTypeGroupClassification> AdapterClassification;
	ExpandableListView ExListProfile, ExViewContract;
	Client objClient;
	List<Contract> listContract;
	Intent ReNewShow;
	Integer ContractIdToReNew;
	String ToDateToReNew;
	int NewClientID;
	Spinner SpinnerGroup, SpinnerClassification;
	long ContractNo;
	Context context = this;
	GPSTracker GPS;
	SubscriberLocation objSubscriberLocation;

	// public static String Todate;
	// Integer IdFromExpandbleList=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.distprofiles);
		try {

			objClient = new Client();
			objSubscriberLocation = new SubscriberLocation();
			ExViewContract = (ExpandableListView) findViewById(R.id.expandableListViewContract);
			ExListProfile = (ExpandableListView) findViewById(R.id.expandableListViewSearchPro);
			SpinnerClassification = (Spinner) findViewById(R.id.spinnertabDetailsClassification);
			SpinnerGroup = (Spinner) findViewById(R.id.spinnerTabDetailsGroup);

			SpinnerGroup.setOnItemSelectedListener(this);
			SpinnerClassification.setOnItemSelectedListener(this);
			GPS = new GPSTracker(context);
			ExListProfile.setOnChildClickListener(new OnChildClickListener() {

				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
					v.setBackgroundColor(Color.GRAY);
					try {

						ToDetails((Integer) ExpandbleListAdapter.childItem
								.get(0));
					} catch (Exception e) {
						Log.d("Error In  onChildClick ", e.getMessage());
					}

					return true;
				}
			});
			ExViewContract.setOnChildClickListener(new OnChildClickListener() {

				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id) {
					ContractNo = Integer.valueOf(ExpandbleListAdapter.childItem
							.get(0).toString());
					Log.d("Group Position ", "" + ContractNo + "*/*");
					v.setBackgroundColor(Color.GRAY);
					return true;
				}
			});
			TabHost tabHost = (TabHost) findViewById(R.id.TabHostProfiles);
			tabHost.setup();

			TabSpec spec1 = tabHost.newTabSpec("Tab 1");
			spec1.setContent(R.id.tabSearch);
			spec1.setIndicator(getString(R.string.Search));
			tabHost.addTab(spec1);

			TabSpec spec2 = tabHost.newTabSpec("Tab 2");
			spec2.setIndicator(getString(R.string.Details));
			spec2.setContent(R.id.tabDetails);
			tabHost.addTab(spec2);

			TabSpec spec4 = tabHost.newTabSpec("Tab 3");
			spec4.setIndicator(getString(R.string.Notification));
			spec4.setContent(R.id.tabNotification);
			tabHost.addTab(spec4);

			TabSpec spec5 = tabHost.newTabSpec("Tab 4");
			spec5.setIndicator(getString(R.string.Contracts));
			spec5.setContent(R.id.tabContracts);
			tabHost.addTab(spec5);

			TxTName = (EditText) findViewById(R.id.TxTtabSearchName);
			TxTName.setOnEditorActionListener(new OnEditorActionListener() {

				@Override
				public boolean onEditorAction(TextView arg0, int actionId,
						KeyEvent arg2) {
					if (actionId == EditorInfo.IME_ACTION_SEARCH) {
						InputMethodManager imm = (InputMethodManager) context
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(TxTName.getWindowToken(), 0);

						BtnSearch.performClick();
						return true;
					}
					return false;
				}
			});

			TxTPhone = (EditText) findViewById(R.id.TxTtabSearchMobile);
			TxTPhone.setOnEditorActionListener(new OnEditorActionListener() {

				@Override
				public boolean onEditorAction(TextView arg0, int actionid,
						KeyEvent arg2) {
					if (actionid == EditorInfo.IME_ACTION_SEARCH) {
						InputMethodManager imm = (InputMethodManager) context
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(TxTName.getWindowToken(), 0);
						BtnSearch.performClick();
					}
					return false;
				}
			});

			TxTIDDetails = (EditText) findViewById(R.id.TxTIDTabDetails);
			TxTNameDetails = (EditText) findViewById(R.id.TxTNameDetails);
			TxTCompName = (EditText) findViewById(R.id.TxTCompNameDetails);
			TxTNationalNo = (EditText) findViewById(R.id.TxTNationalNoDetails);
			TxTMobileDetails = (EditText) findViewById(R.id.TxTMobileDetails);
			edtchngName = (EditText) findViewById(R.id.edtChngName);
			TxTPhoneDetails = (EditText) findViewById(R.id.TxTPhoneDetails);
			TxTNote = (EditText) findViewById(R.id.TxTNoteDetails);
			TxTEmailDetails = (EditText) findViewById(R.id.TxTEmailDetails);
			TxTAddress = (EditText) findViewById(R.id.TxTAddressDetails);

			btnCheckin = (Button) findViewById(R.id.buttonCheckIn);
			BtnSearch = (Button) findViewById(R.id.BTNtabSearchSearch);
			BtnAddDetails = (Button) findViewById(R.id.buttonAddDetails);
			BtnEditDetails = (Button) findViewById(R.id.buttonEditDetails);
			BtnSaveDetails = (Button) findViewById(R.id.buttonSaveDetails);
			BtnRevertDetails = (Button) findViewById(R.id.buttonRevertDetails);
			BtnViewContract = (Button) findViewById(R.id.BtnTabContractViewContract);
			BtnReNew = (Button) findViewById(R.id.BtnTabContractReNew);
			BtnchngName = (Button) findViewById(R.id.BtnTabChngName);
			BtnchngCompany = (Button) findViewById(R.id.BtnTabChngCompany);
			BtnchngPhone = (Button) findViewById(R.id.BtnTabChngPhone);
			BtnChngEmail = (Button) findViewById(R.id.BtnTabChngEmail);
			BtnChngNationalNo = (Button) findViewById(R.id.BtnTabChngNationalNo);
			BtnChngMobile = (Button) findViewById(R.id.BtnTabChngMobile);
			BtnChngNote = (Button) findViewById(R.id.BtnTabChngNote);
			BtnChngClassification = (Button) findViewById(R.id.BtnTabChngClassification);
			BtnChngOffer = (Button) findViewById(R.id.BtnTabChngOffer);
			BtnFillInfo = (Button) findViewById(R.id.BtnTabFillInfo);
			BtnClear = (Button) findViewById(R.id.BtnTabClear);

			BtnchngName.setOnClickListener(this);
			BtnchngCompany.setOnClickListener(this);
			BtnchngPhone.setOnClickListener(this);
			BtnChngEmail.setOnClickListener(this);
			BtnChngNationalNo.setOnClickListener(this);
			BtnChngMobile.setOnClickListener(this);
			BtnChngNote.setOnClickListener(this);
			BtnChngClassification.setOnClickListener(this);
			BtnChngOffer.setOnClickListener(this);
			BtnFillInfo.setOnClickListener(this);
			BtnClear.setOnClickListener(this);

			BtnSearch.setOnClickListener(this);
			BtnAddDetails.setOnClickListener(this);
			BtnEditDetails.setOnClickListener(this);
			BtnSaveDetails.setOnClickListener(this);
			BtnRevertDetails.setOnClickListener(this);
			BtnViewContract.setOnClickListener(this);
			BtnReNew.setOnClickListener(this);
			btnCheckin.setOnClickListener(this);
			// btnCheckin.setEnabled(false);
			DisableBtn();
			DisableTextNotification();
			FillSpinnerGroup();
			ReNewShow = new Intent(Profiles.this, ReNewAppointment.class);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}// End On Create

	private void DisableTextNotification() {
		edtchngName.setText(null);
	}

	// protected static void setLocale(final Context ctx, final String lang) {
	// final Locale loc = new Locale(lang);
	// Locale.setDefault(loc);
	// final Configuration cfg = new Configuration();
	// cfg.locale = loc;
	// ctx.getResources().updateConfiguration(cfg, null);
	// }

//	@Override
//	public void onClick(View v) {
//		CollectedText = edtchngName.getText().toString();
//
//		switch (v.getId()) {
//		case R.id.BtnTabChngName:
//			CollectedText = CollectedText + "\nتغير الاسم الى";
//			// TxTchngName="\nتغير الاسم الى";
//			// edtchngName.setText(TxTchngName);
//			break;
//		case R.id.BtnTabChngCompany:
//			CollectedText = CollectedText + "\nتغير اسم الشركة الى";
//			// TxTchngCompany="\nتغير اسم الشركة الى";
//			// edtchngName.setText(TxTchngCompany);
//			break;
//		case R.id.BtnTabChngPhone:
//			CollectedText = CollectedText + "\nتغير هاتف الى";
//			// TxTchngPhone="\nتغير هاتف الى";
//			// edtchngName.setText(TxTchngPhone);
//			break;
//		case R.id.BtnTabChngEmail:
//			CollectedText = CollectedText + "\nتغير الايميل الى";
//			// TxTChngEmail="\nتغير الايميل الى";
//			// edtchngName.setText(TxTChngEmail);
//			break;
//		case R.id.BtnTabChngNationalNo:
//			CollectedText = CollectedText + "\nتغير الوطني الى";
//			// TxTChngNationalNo="\nتغير الوطني الى";
//			// edtchngName.setText(TxTChngNationalNo);
//			break;
//		case R.id.BtnTabChngMobile:
//			CollectedText = CollectedText + "\nتغير المحمول الى";
//			// TxTChngMobile="\nتغير المحمول الى";
//			// edtchngName.setText(TxTChngMobile);
//			break;
//		case R.id.BtnTabChngNote:
//			CollectedText = CollectedText + "\nتغير المحلاظات الى";
//			// TxTChngNote="\nتغير المحلاظات الى";
//			// edtchngName.setText(TxTChngNote);
//			break;
//		case R.id.BtnTabChngClassification:
//			CollectedText = CollectedText + "\nتغير التصنيف الى";
//			// TxTChngClassification="\nتغير التصنيف الى";
//			// edtchngName.setText(TxTChngClassification);
//			break;
//		case R.id.BtnTabChngOffer:
//			CollectedText = CollectedText + "\nتغير العروض الى";
//			// TxTChngOffer="\nتغير العروض الى";
//			// edtchngName.setText(TxTChngOffer);
//			break;
//		case R.id.BtnTabFillInfo:
//			CollectedText = CollectedText + "\nاكمال معلومات";
//			// edtchngName.setText(TxTFillInfo);
//			break;
//		case R.id.BtnTabClear:
//			CollectedText = "";
//			break;
//		case R.id.BTNtabSearchSearch:
//			if (TxTName.getText().toString().equals("")
//					&& TxTPhone.getText().toString().equals("")) {
//				GetToast.Toast(context,
//						getString(R.string.Enter_Name_Or_Ph_Mobiel));
//				return;
//			}// End If
//			new getdata().execute(TxTName.getText().toString(), TxTPhone
//					.getText().toString());
//			break;
//
//		case R.id.buttonAddDetails:
//			BtnSaveDetails.setEnabled(true);
//			BtnRevertDetails.setEnabled(true);
//			BtnAddDetails.setEnabled(false);
//			BtnEditDetails.setEnabled(false);
//			CurrentMode = Mode.Add;
//			Enable();
//			FillSpinnerGroup();
//			cleartext();
//			break;
//
//		case R.id.buttonEditDetails:
//			// BtnAddDetails.performClick();
//			CurrentMode = Mode.Edit;
//			Enable();
//			// SpinnerClassification.setEnabled(true);
//			// SpinnerGroup.setEnabled(true);
//			FillSpinnerGroup();
//			BtnAddDetails.setEnabled(false);
//			BtnEditDetails.setEnabled(false);
//			detailsinvisible();
//			break;
//
//		case R.id.buttonSaveDetails:
//			SaveClient();
//			BtnSaveDetails.setEnabled(false);
//			break;
//
//		case R.id.buttonRevertDetails:
//			BtnSaveDetails.setEnabled(false);
//			BtnRevertDetails.setEnabled(false);
//			BtnAddDetails.setEnabled(true);
//			BtnEditDetails.setEnabled(true);
//			btnCheckin.setEnabled(true);
//			DisableBtn();
//			break;
//
//		case R.id.BtnTabContractViewContract:
//
//			Contract objContract = new Contract();
//			try {
//				if (TxTIDDetails.getText().toString().trim().equals("")) {
//					GetToast.Toast(context,
//							getString(R.string.Please_Select_Client));
//					return;
//				}// End If
//				else
//					listContract = objContract
//							.ContractsByClientIDAndStatus(Integer
//									.valueOf(TxTIDDetails.getText().toString()));
//				if (listContract == null) {
//					GetToast.Toast(context, getString(R.string.NoData));
//
//					return;
//				}// End if
//				else {
//					AddToExpandListViewContract();
//					// BtnReNew.setEnabled(true);
//				}
//			} catch (Exception e) {
//				Log.d("Btn Contract View in profile ", e.getMessage());
//			}// End Try Catch
//			break;
//
//
//		case R.id.BtnTabContractReNew:
//			boolean Check ;
//
//			Check = GetClientBlock(Integer
//					.valueOf(TxTIDDetails.getText().toString()));
//			if (Check == true){
//				break;
//			}
//			else{
//				ToReNew();
//			}
//
//			break;
//
//		case R.id.buttonCheckIn:
//			try {
//				subscriberlocation();
//				if (TxTIDDetails.getText().toString().equals("")) {
//					GetToast.Toast(context,
//							getString(R.string.Please_Select_Client));
//					return;
//				}
//				int x = objSubscriberLocation
//						.InsertSubscriberLocation(Profiles.this.objSubscriberLocation);
//				if (x > 0) {
//					GetToast.Toast(context, "Save Done");
//				} else {
//					GetToast.Toast(context, "Error");
//				}
//				btnCheckin.setEnabled(false);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//			break;
//
//		}// End switch
//		edtchngName.setText(CollectedText);
//	}// End On Click

	@Override
	public void onClick(View v) {
		CollectedText = edtchngName.getText().toString();

			if (v.getId() == R.id.BtnTabChngName) {
				CollectedText = CollectedText + "\nتغير الاسم الى";
				// TxTchngName="\nتغير الاسم الى";
				// edtchngName.setText(TxTchngName);
			}else if( v.getId() == R.id.BtnTabChngCompany) {
				CollectedText = CollectedText + "\nتغير اسم الشركة الى";
				// TxTchngCompany="\nتغير اسم الشركة الى";
				// edtchngName.setText(TxTchngCompany);
			}else if(v.getId() == R.id.BtnTabChngPhone) {
				CollectedText = CollectedText + "\nتغير هاتف الى";
				// TxTchngPhone="\nتغير هاتف الى";
				// edtchngName.setText(TxTchngPhone);
			}else if(v.getId() == R.id.BtnTabChngEmail) {
				CollectedText = CollectedText + "\nتغير الايميل الى";
				// TxTChngEmail="\nتغير الايميل الى";
				// edtchngName.setText(TxTChngEmail);
			}else if(v.getId() == R.id.BtnTabChngNationalNo){
				CollectedText = CollectedText + "\nتغير الوطني الى";
				// TxTChngNationalNo="\nتغير الوطني الى";
				// edtchngName.setText(TxTChngNationalNo);
			}else if(v.getId() == R.id.BtnTabChngMobile) {
				CollectedText = CollectedText + "\nتغير المحمول الى";
				// TxTChngMobile="\nتغير المحمول الى";
				// edtchngName.setText(TxTChngMobile);
			}else if(v.getId() == R.id.BtnTabChngNote) {
				CollectedText = CollectedText + "\nتغير المحلاظات الى";
				// TxTChngNote="\nتغير المحلاظات الى";
				// edtchngName.setText(TxTChngNote);
			}else if (v.getId() == R.id.BtnTabChngClassification) {
				CollectedText = CollectedText + "\nتغير التصنيف الى";
				// TxTChngClassification="\nتغير التصنيف الى";
				// edtchngName.setText(TxTChngClassification);
			}else if(v.getId() == R.id.BtnTabChngOffer) {
				CollectedText = CollectedText + "\nتغير العروض الى";
				// TxTChngOffer="\nتغير العروض الى";
				// edtchngName.setText(TxTChngOffer);
			}else if(v.getId() ==R.id.BtnTabFillInfo) {
				CollectedText = CollectedText + "\nاكمال معلومات";
				// edtchngName.setText(TxTFillInfo);
			}else if (v.getId() == R.id.BtnTabClear) {
				CollectedText = "";
			}else if (v.getId() == R.id.BTNtabSearchSearch) {
				if (TxTName.getText().toString().equals("")
						&& TxTPhone.getText().toString().equals("")) {
					GetToast.Toast(context,
							getString(R.string.Enter_Name_Or_Ph_Mobiel));
					return;
				}// End If
				new getdata().execute(TxTName.getText().toString(), TxTPhone
						.getText().toString());
			}else if (v.getId() == R.id.buttonAddDetails) {
				BtnSaveDetails.setEnabled(true);
				BtnRevertDetails.setEnabled(true);
				BtnAddDetails.setEnabled(false);
				BtnEditDetails.setEnabled(false);
				CurrentMode = Mode.Add;
				Enable();
				FillSpinnerGroup();
				cleartext();
			}else if(v.getId() == R.id.buttonEditDetails) {
				// BtnAddDetails.performClick();
				CurrentMode = Mode.Edit;
				Enable();
				// SpinnerClassification.setEnabled(true);
				// SpinnerGroup.setEnabled(true);
				FillSpinnerGroup();
				BtnAddDetails.setEnabled(false);
				BtnEditDetails.setEnabled(false);
				detailsinvisible();
			}else if (v.getId() == R.id.buttonSaveDetails) {
				SaveClient();
				BtnSaveDetails.setEnabled(false);
			}else if (v.getId() == R.id.buttonRevertDetails) {
				BtnSaveDetails.setEnabled(false);
				BtnRevertDetails.setEnabled(false);
				BtnAddDetails.setEnabled(true);
				BtnEditDetails.setEnabled(true);
				btnCheckin.setEnabled(true);
				DisableBtn();
			}else if (v.getId() == R.id.BtnTabContractViewContract) {

				Contract objContract = new Contract();
				try {
					if (TxTIDDetails.getText().toString().trim().equals("")) {
						GetToast.Toast(context,
								getString(R.string.Please_Select_Client));
						return;
					}// End If
					else
						listContract = objContract
								.ContractsByClientIDAndStatus(Integer
										.valueOf(TxTIDDetails.getText().toString()));
					if (listContract == null) {
						GetToast.Toast(context, getString(R.string.NoData));

						return;
					}// End if
					else {
						AddToExpandListViewContract();
						// BtnReNew.setEnabled(true);
					}
				} catch (Exception e) {
//					Log.d("Btn Contract View in profile ", e.getMessage());
				}// End Try Catch
			}else if(v.getId() == R.id.BtnTabContractReNew) {
				boolean Check;

				Check = GetClientBlock(Integer
						.valueOf(TxTIDDetails.getText().toString()));
				if (Check == true) {
					return;

				} else {
					ToReNew();
				}
			}else if(v.getId() == R.id.buttonCheckIn){
				try {
					subscriberlocation();
					if (TxTIDDetails.getText().toString().equals("")) {
						GetToast.Toast(context,
								getString(R.string.Please_Select_Client));
						return;
					}
					int x = objSubscriberLocation
							.InsertSubscriberLocation(Profiles.this.objSubscriberLocation);
					if (x > 0) {
						GetToast.Toast(context, "Save Done");
					} else {
						GetToast.Toast(context, "Error");
					}
					btnCheckin.setEnabled(false);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

		edtchngName.setText(CollectedText);
	}// End On Click

	private void subscriberlocation() {
		try {

			objSubscriberLocation.setPDAClientID(TxTIDDetails.getText()
					.toString());
			objSubscriberLocation.setPDAEntryUserID(DistributionMain.UserID);
			objSubscriberLocation.setDeviceID(DistributionShowmain.DeviceID);

			if (GPS.canGetLocation()) {
				Location location = GPS.getLocation();
				if (location != null) {
					/*
					 * 14
					 */objSubscriberLocation
							.setlatitude(location.getLatitude());//
					// GPS.getLatitude()
					/*
					 * 15
					 */objSubscriberLocation.setlongitude(location
							.getLongitude());//
					// GPS.getLongitude()
				}
				// getAddress(location);
			} else {
				/* 14 */objSubscriberLocation.setlatitude(0.0);// GPS.getLatitude()
				/* 15 */objSubscriberLocation.setlongitude(0.0);// GPS.getLongitude()
			}
		} catch (Exception e) {
			// TODO: handle exception
//			Log.e("Error subscribtion location", e.getMessage());
			e.printStackTrace();
		}
	}

	private void SaveClient() {

		FillObject();
		switch (CurrentMode) {
		case Edit:
			if (TxTIDDetails.getText().toString().equals("")) {
				GetToast.Toast(context,
						getString(R.string.Please_Select_Client));
				return;
			} else {
				AlertDialog.Builder builder1 = new AlertDialog.Builder(
						Profiles.this);
				builder1.setMessage(getString(R.string.AreYouSure));
				builder1.setCancelable(true);
				builder1.setPositiveButton(getString(R.string.Yes),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								try {

									int UserID = DistributionMain.UserID, SalesID = DistributionMain.SalesID;

									if (Connection
											.isConnectingToInternet(context)
											&& DistributionMain.UserID > 0
											&& objClient.InsertClient(
													Profiles.this.objClient,
													DistributionShowmain.DeviceID,
													edtchngName.getText()
															.toString()) > 0
											&& UserID > 0 && SalesID > 0) {
										GetToast.Toast(context,
												getString(R.string.Save_Done));
										// Profiles.this.finish();
									}// End If
									else {
										GetToast.Toast(context,
												getString(R.string.Error));
										Connection.showAlertDialog(context);
										dialog.cancel();
									}

									dialog.cancel();
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
							}
						});
				builder1.setNegativeButton(getString(R.string.No),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

				AlertDialog alert11 = builder1.create();
				alert11.show();
			}
			break;
		case Add:
			try {
				if (!IsValidToSave()) {
					GetToast.Toast(context, getString(R.string.YouHaveProblem));
					return;
				}// End if
				else {
					NewClientID = objClient.InsertClient(this.objClient,
							DistributionShowmain.DeviceID, "Add");
					Log.d("New ClientID ", "" + NewClientID);

					if (NewClientID > 0 && DistributionMain.UserID > 0 && DistributionMain.SalesID > 0) {

						GetToast.Toast(context, getString(R.string.Save_Done));
						ShowDialog();
					}// End If
					else {
						GetToast.Toast(context, getString(R.string.Error));
					}
				}
			} catch (Exception e) {
//				Log.d("Error In Profle  Add Client", e.getMessage());
			}// End Try Catch
			break;
		}// End Switch

	}// End Void Add Client

	private void FillObject() {
		try {
			if (DistributionMain.UserID == 0 && DistributionMain.SalesID == 0) {
				GetToast.Toast(context, getString(R.string.YouHaveProblem));
				return;
			}
			NationalNo = TxTNationalNo.getText().toString();
			ClientMobile = TxTMobileDetails.getText().toString();
			objClient.setClientName(TxTNameDetails.getText().toString());
			objClient.setClientMobile(ClientMobile);
			objClient.setClientPhone(TxTPhoneDetails.getText().toString());
			objClient.setClientEmail(TxTEmailDetails.getText().toString());
			objClient.setClientNationalNo(NationalNo);
			objClient.setClientCompanyName(TxTCompName.getText().toString());

			objClient
					.setClientClassification(AdapterClassification.getItem(
							SpinnerClassification.getSelectedItemPosition())
							.getID() + 1);
			objClient.setClientNote(TxTNote.getText().toString());

			if (DistributionMain.UserID > 0) {
				objClient.setEntryUserID(DistributionMain.UserID);
			} else {
				GetToast.Toast(context, getString(R.string.YouHaveProblem));
			}
			objClient.setIsPosted(false);
			objClient.setIsNew(true);
			int ClientID;

			if (TxTIDDetails.getText().toString().length() == 0)
				ClientID = 0;
			else
				ClientID = Integer.parseInt(TxTIDDetails.getText().toString());

			objClient.setClientID(ClientID);
			// if (GPS.canGetLocation()) {
			// Location location = GPS.getLocation();
			// if (location != null) {
			// /* 14
			// */objClient.setLatitude(Double.parseDouble(String.format("%.9f",location.getLatitude())));//
			// GPS.getLatitude()
			// /* 15
			// */objClient.setLongtiude(Double.parseDouble(String.format("%.9f",location.getLongitude())));//
			// GPS.getLongitude()
			// }
			// // getAddress(location);
			// } else {
			// /* 14 */objClient.setLatitude(0.0);// GPS.getLatitude()
			// /* 15 */objClient.setLongtiude(0.0);// GPS.getLongitude()
			// }
			objClient.setContractID(0);

		} catch (Exception e) {
			GetToast.Toast(context, "" + e.getMessage());
			e.printStackTrace();
		}// try

	}

	private boolean IsValidToSave() {
		int i = 0;
		if (TxTNameDetails.getText().toString().equals("")) {
			TxTNameDetails.setHint(getString(R.string.EnterName));
			TxTNameDetails.setHintTextColor(Color.RED);
			i = i + 1;
		}
		if (TxTCompName.getText().toString().equals(""))
			TxTCompName.setText(" ");

		if (TxTEmailDetails.getText().toString().equals(""))
			TxTEmailDetails.setText(" ");

		// if (TxTNationalNo.getText().toString().equals("")) {
		// TxTNationalNo.setHint(getString(R.string.EnterNumber));
		// TxTNationalNo.setHintTextColor(Color.RED);
		// i = i + 1;
		// }
		if (TxTMobileDetails.getText().toString().equals("")
				&& TxTPhoneDetails.getText().toString().equals("")) {
			TxTMobileDetails.setHint(getString(R.string.EnterNumber));
			TxTPhoneDetails.setHint(getString(R.string.EnterNumber));
			TxTPhoneDetails.setHintTextColor(Color.RED);
			TxTMobileDetails.setHintTextColor(Color.RED);
			i = i + 1;
		}
		if (TxTNote.getText().toString().equals(""))
			TxTNote.setText(" ");
		if (i == 0)
			return true;
		else
			return false;

	}// End Is Valid

	private void ShowDialog() {
		try {
			final Contract objContract = new Contract();
			final Intent ReNewApoointment = new Intent(Profiles.this,
					ReNewAppointment.class);

			AlertDialog.Builder builder1 = new AlertDialog.Builder(
					Profiles.this);
			builder1.setMessage(getString(R.string.NewContract));
			builder1.setCancelable(true);
			builder1.setPositiveButton(getString(R.string.Yes),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// Contract.putExtra("NewClientID", NewClientID);
							ReNewApoointment.putExtra("ClientID", NewClientID);
							objContract.SetClientID(NewClientID);
							ReNewApoointment.putExtra("ClientName",
									TxTNameDetails.getText().toString());
							objContract.SetClientName(TxTNameDetails.getText()
									.toString());
							ReNewApoointment.putExtra("CompanyName",
									TxTCompName.getText().toString());
							objContract.setCompanyName(TxTCompName.getText()
									.toString());
							ReNewApoointment.putExtra("Phone", TxTPhoneDetails
									.getText().toString());
							objContract.SetClientPhone(TxTPhoneDetails
									.getText().toString());

							ReNewApoointment.putExtra("NationalNumber",
									TxTNationalNo.getText().toString());
							objContract.SetNationalNo(TxTNationalNo.getText()
									.toString());

							ReNewApoointment.putExtra("AppointmentTypeID", 2);
							ReNewApoointment.putExtra("AppointmentTypeName",
									"New Subscriptions");

							ReNewApoointment.putExtra("Note", TxTNote.getText()
									.toString());

							ReNewApoointment.putExtra("Note", TxTNote.getText()
									.toString());
							objContract.SetAddressComments(TxTNote.getText()
									.toString());
							ReNewApoointment.putExtra("Address", TxTAddress
									.getText().toString());
							objContract.SetAddress(TxTAddress.getText()
									.toString());
							ReNewApoointment.putExtra("requestCode", "s");
							ReNewApoointment.putExtra("objContract",
									objContract);
							startActivity(ReNewApoointment);
							dialog.cancel();
							finish();
						}
					});
			builder1.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
							finish();
						}
					});

			AlertDialog alert11 = builder1.create();
			alert11.show();

		} catch (Exception e) {
//			Log.d("Error Profile Show Dialog ", e.getMessage());
			e.printStackTrace();
		}// end Try catch

	}// end show Dialog

	private void cleartext() {
		TxTIDDetails.setText("");
		TxTNameDetails.setText("");
		TxTCompName.setText("");
		TxTNationalNo.setText("");
		TxTPhoneDetails.setText("");
		TxTMobileDetails.setText("");
		TxTEmailDetails.setText("");
		TxTAddress.setText("");
		TxTNote.setText("");
		SpinnerClassification.setSelection(0);
		SpinnerGroup.setSelection(0);
		TxTIDDetails.setEnabled(false);
	}// End Clear Text

	private void FillSpinnerGroup() {
		try {
			ClientTypeGroup objClientTypeGroup = new ClientTypeGroup();
			listclientTypeGroups = objClientTypeGroup
					.getClientTypeGroupByClientTypeID(-1);
			if (listclientTypeGroups == null) {
				GetToast.Toast(context, getString(R.string.NoData));
				return;
			}// if list =null
			AdapterTypeGroup = new ArrayAdapter<ClientTypeGroup>(this,
					android.R.layout.simple_spinner_item, listclientTypeGroups);
			AdapterTypeGroup
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			SpinnerGroup.setAdapter(AdapterTypeGroup);
		} catch (Exception e) {
//			Log.d("Error Profile Fill Spinner ", e.getMessage());
		}// End Try Catch

	}// End fill Spinner

	private void AddToExpandListViewContract() {
		try {
			ArrayList<String> parent = new ArrayList<String>();
			ArrayList<Object> Child = new ArrayList<Object>();
			for (Contract e : listContract) {
				ArrayList<Object> child = new ArrayList<Object>();
				String x = e.GetToDate();
				String arra[] = x.split("T");
				String x2 = e.GetFromDate();
				String arra2[] = x2.split("T");
				parent.add(e.GetContractNo() + "||" + arra[0] + "||" + arra2[0]);
				child.add(e.GetContractNo());
				String x5 = e.GetFromDate();
				String arra5[] = x5.split("T");
				child.add(arra5[0]);
				String x3 = e.GetToDate();
				String arra3[] = x3.split("T");
				child.add(arra3[0]);
				String x4 = e.GetMaxGraceDate();
				String arra4[] = x4.split("T");
				child.add(arra4[0]);
				child.add(e.GetCopiesNo());
				child.add(e.GetNetAmount());
				child.add(e.GetAddress());
				ToDateToReNew = e.GetToDate();
				ContractIdToReNew = e.GetContractID();
				Child.add(child);
			}// End For
			ExViewContract
					.setAdapter(new ExpandbleListAdapter(
							Profiles.this,
							parent,
							Child,
							ExpandbleListAdapter.CurrentMode.View));

		} catch (Exception e) {
//			Log.d("Error AddToExpandListViewContract ", e.getMessage());
		}// End try Catch

	}// end AddToExpandListViewContract

	private void AddToExpandList() {
		try {

			ArrayList<String> parent = new ArrayList<String>();
			ArrayList<Object> Child = new ArrayList<Object>();
			for (Client e : listProfile) {
				ArrayList<Object> child = new ArrayList<Object>();
				parent.add(e.getClientName());
				child.add(e.getID());
				child.add(e.getClientName());
				child.add(e.getClientPhone());
				child.add(e.getClientMobile());
				// child.add(e.GetBuildingNo());
				child.add(e.getClientNote());
				child.add(e.GetLatitude());
				child.add(e.GetLongtiude());
				Child.add(child);
			}// End For
			ExListProfile
					.setAdapter(new ExpandbleListAdapter(
							Profiles.this,
							parent,
							Child,
							ExpandbleListAdapter.CurrentMode.Search));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}// End Function

	private void ToDetails(int id) {
		try {
			for (Client e : listProfile) {
				if (id == e.getID()) {
					TxTIDDetails.setText("" + e.getID());
					TxTEmailDetails.setText(e.getClientEmail());
					TxTCompName.setText(e.getClientCompanyName());
					TxTMobileDetails.setText(e.getClientMobile());
					TxTNameDetails.setText(e.getClientName());
					TxTPhoneDetails.setText(e.getClientPhone());
					TxTNationalNo.setText(e.getClientNationalNo());
					TxTNote.setText(e.getClientNote());
					TxTAddress.setText(e.getAddress());
					break;
				}// end if
			}// End For
		} catch (Exception e) {
			Log.d("Error in To Details ", e.getMessage());
			e.printStackTrace();
		}// End try Catch
	}// end Function

	private void detailsinvisible() {
		// TxTName.setEnabled(false);
		TxTCompName.setEnabled(false);
		TxTEmailDetails.setEnabled(false);
		TxTMobileDetails.setEnabled(false);
		TxTNationalNo.setEnabled(false);
		TxTPhoneDetails.setEnabled(false);
		TxTNameDetails.setEnabled(false);
		TxTAddress.setEnabled(false);
		TxTNote.setEnabled(false);
		SpinnerClassification.setEnabled(false);
		SpinnerGroup.setEnabled(false);
		TxTIDDetails.setEnabled(false);
	}

	private void Enable() {
		switch (CurrentMode) {
		case Edit:

			BtnSaveDetails.setEnabled(true);
			BtnRevertDetails.setEnabled(true);
			// SpinnerClassification.setEnabled(true);
			// SpinnerGroup.setEnabled(true);

			BtnchngName.setEnabled(true);
			BtnchngCompany.setEnabled(true);
			BtnchngPhone.setEnabled(true);
			BtnChngEmail.setEnabled(true);
			BtnChngNationalNo.setEnabled(true);
			BtnChngMobile.setEnabled(true);
			BtnChngNote.setEnabled(true);
			BtnChngClassification.setEnabled(true);
			BtnChngOffer.setEnabled(true);
			BtnFillInfo.setEnabled(true);
			BtnClear.setEnabled(true);
			edtchngName.setEnabled(true);

			break;

		case Add:
			BtnSaveDetails.setEnabled(true);
			BtnRevertDetails.setEnabled(true);
			SpinnerClassification.setEnabled(true);
			SpinnerGroup.setEnabled(true);
			TxTCompName.setEnabled(true);
			TxTEmailDetails.setEnabled(true);
			TxTAddress.setEnabled(true);
			TxTNote.setEnabled(true);
			TxTMobileDetails.setEnabled(true);
			TxTNationalNo.setEnabled(true);
			TxTPhoneDetails.setEnabled(true);
			TxTNameDetails.setEnabled(true);
			break;
		}
	}// end Enable

	private void DisableBtn() {
		SpinnerClassification.setEnabled(false);
		SpinnerGroup.setEnabled(false);
		// BtnReNew.setEnabled(false);
		BtnSaveDetails.setEnabled(false);
		BtnRevertDetails.setEnabled(false);
		BtnchngName.setEnabled(false);
		BtnchngCompany.setEnabled(false);
		BtnchngPhone.setEnabled(false);
		BtnChngEmail.setEnabled(false);
		BtnChngNationalNo.setEnabled(false);
		BtnChngMobile.setEnabled(false);
		BtnChngNote.setEnabled(false);
		BtnChngClassification.setEnabled(false);
		BtnChngOffer.setEnabled(false);
		BtnFillInfo.setEnabled(false);
		BtnClear.setEnabled(false);
		edtchngName.setEnabled(false);

		TxTIDDetails.setEnabled(false);
		// TxTName.setEnabled(false);
		TxTCompName.setEnabled(false);
		TxTEmailDetails.setEnabled(false);
		TxTMobileDetails.setEnabled(false);
		TxTNationalNo.setEnabled(false);
		TxTPhoneDetails.setEnabled(false);
		TxTNameDetails.setEnabled(false);
		TxTAddress.setEnabled(false);
		TxTNote.setEnabled(false);
		CollectedText = "";
	}// End disable

	private void NewSubscriptions() {
		try {

			int IDDetails = Integer.parseInt(TxTIDDetails.getText().toString());
			Contract objContract = new Contract();
			ReNewShow.putExtra("ClientID", IDDetails);
			objContract.SetClientID(IDDetails);
			ReNewShow.putExtra("ClientName", TxTNameDetails.getText()
					.toString());
			objContract.SetClientName(TxTNameDetails.getText().toString());
			ReNewShow.putExtra("CompanyName", TxTCompName.getText().toString());
			objContract.setCompanyName(TxTCompName.getText().toString());
			ReNewShow.putExtra("Phone", TxTPhoneDetails.getText().toString());
			objContract.SetClientPhone(TxTPhoneDetails.getText().toString());

			ReNewShow.putExtra("NationalNumber", TxTNationalNo.getText()
					.toString());
			objContract.SetNationalNo(TxTNationalNo.getText().toString());

			ReNewShow.putExtra("AppointmentTypeID", 2);
			ReNewShow.putExtra("AppointmentTypeName", "New Subscriptions");

			ReNewShow.putExtra("Note", TxTNote.getText().toString());

			objContract.SetAddressComments(TxTNote.getText().toString());
			ReNewShow.putExtra("Address", TxTAddress.getText().toString());
			objContract.SetAddress(TxTAddress.getText().toString());
			ReNewShow.putExtra("requestCode", "s");
			ReNewShow.putExtra("objContract", objContract);
			startActivity(ReNewShow);
			this.finish();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("New Subscriptions", e.getMessage());
			// CatchMsg.WriteMSG("New Subscriptions", e.getMessage());
			e.printStackTrace();
		}
	}
	//---------------------------------------------------------------------------------
	private boolean GetClientBlock(int ClientID){
		boolean Check = false;
		try {

			Contract objContract = new Contract();
			Check = objContract.GetClientBlockk(ClientID);
			
			if (Check == true ){
				
				GetToast.Toast(context, getString(R.string.PleaseContactSupport));
				return true ;
				
			}
			else
			{
				Check =  false;	
			}
		     
			
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Error Area", e.getMessage());
		}
	
		return Check ;
		}
		
	
	
//------------------------------------------------------------------------------
//	private boolean CheckClientBlock(int ClientID) {
//		boolean Check = false;
//		try {
//
//			Contract objContract = new Contract();
//			Check = objContract.CheckClientBlock(ClientID);
//			
//			if (Check == true ){
//				
//				GetToast.Toast(context, "Please Contact Support");
//				return true ;
//				
//			}
//			else
//			{
//				Check =  false;	
//			}
//		     
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.e("Error Area", e.getMessage());
//		}
//	
//		return Check ;
//		}
//	//----------------------------------------------------------------------------------
//	private boolean CheckPDAClientBlock(int ClientID) {
//		boolean Check = false;
//		try {
//
//			Contract objContract = new Contract();
//			Check = objContract.CheckPDAClientBlock(ClientID);
//			
//			if (Check == true ){
//				
//				GetToast.Toast(context, "Please Contact Support OR Wait");
//				return true ;
//				
//			}
//			else
//			{
//				Check =  false;	
//			}
//		     
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.e("Error Area", e.getMessage());
//		}
//	
//		return Check ;
//		}


//----------------------------------------------------------------------------
	private void ToReNew() {
		if (ContractNo == 0) {
			// GetToast.Toast(context,
			// getString(R.string.Please_Select_Client));
			NewSubscriptions();
			return;
		}

		try {
			for (Contract e : listContract) {
				if (e.GetContractID() == ContractIdToReNew) {

					Contract objContract = new Contract();

					ReNewShow.putExtra("ClientName", TxTNameDetails.getText()
							.toString());
					int SubscriptionTypeID = e.GetSubscriptionTypeID();
					ReNewShow
							.putExtra("SubscriptionTypeID", SubscriptionTypeID);
					objContract.SetClientName(TxTNameDetails.getText()
							.toString());
					ReNewShow.putExtra("ClientID",
							Integer.valueOf(TxTIDDetails.getText().toString()));
					objContract.SetClientID(Integer.valueOf(TxTIDDetails
							.getText().toString()));
					ReNewShow.putExtra("ContractId", ContractIdToReNew);

					ReNewShow.putExtra("ContractNo",
							Integer.valueOf(String.valueOf(e.GetContractNo())));

					ReNewShow.putExtra("ContractTypeID", e.GetContractTypeID());
					ReNewShow.putExtra("CopiesNo", e.GetCopiesNo());

					ReNewShow.putExtra("Address", e.GetAddress());

					ReNewShow.putExtra("Phone", this.TxTPhoneDetails.getText()
							.toString());

					ReNewShow.putExtra("AppointmentTypeID", 1);
					ReNewShow.putExtra("AppointmentTypeName",
							"Renewal Subscriptions");

					objContract.SetClientPhone(this.TxTPhoneDetails.getText()
							.toString());
					// String todate[]=e.GetToDate().split("T");
					// ReNewShow.putExtra("ToDate", todate[0]);
					// Todate=todate[0];
					// objContract.SetToDate(todate[0]);
					ReNewShow.putExtra("CompanyName", this.TxTCompName
							.getText().toString());
					objContract.setCompanyName(this.TxTCompName.getText()
							.toString());

					ReNewShow.putExtra("NationalNumber", this.TxTNationalNo
							.getText().toString());
					objContract.SetNationalNo(this.TxTNationalNo.getText()
							.toString());

					ReNewShow.putExtra("Note", this.TxTNote.getText()
							.toString());
					ReNewShow.putExtra("requestCode", "DateFromProfile");
					objContract = e;
					ReNewShow.putExtra("objContract", objContract);

				}// End if
			}
			startActivity(ReNewShow);
			this.finish();
		} catch (Exception e) {
			e.printStackTrace();
		}// End Try Catch

	}// End To ReNew

	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		if (parent.getId() == SpinnerGroup.getId()) {
			FillspinnerClassification(AdapterTypeGroup.getItem(
					SpinnerGroup.getSelectedItemPosition()).getID());
		}
	}

	private void FillspinnerClassification(int GroupID) {
		try {
			ClientTypeGroupClassification objClassification = new ClientTypeGroupClassification();
			listClassifications = objClassification
					.getClientTypeGroupClassificationByGroupID(GroupID);
			if (listClassifications == null) {
				GetToast.Toast(context,
						getString(R.string.NoDataForClassification));
				return;
			}// end If
			AdapterClassification = new ArrayAdapter<ClientTypeGroupClassification>(
					this, android.R.layout.simple_spinner_item,
					listClassifications);
			AdapterClassification
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			SpinnerClassification.setAdapter(AdapterClassification);
		} catch (Exception e) {
//			Log.d("Error In Profils FillSpinner Classification ",
//					e.getMessage());
			e.printStackTrace();
		}// End try Catch
	}// End fill spinner classification

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

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
			listProfile = objClient.SelectClientByNameAndPhone(params[0],
					params[1]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			if (listProfile == null) {
				GetToast.Toast(context, getString(R.string.NoData));
				return;
			}// End if list =null
			AddToExpandList();
		}
	}// Get data
}// End Class Profiles
