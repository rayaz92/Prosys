package com.example.amir.core.Distribution.alghadversion1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.amir.core.CoreLogInActivity;
import com.example.amir.core.Distribution.alghadversion1.Classes.Bank;
import com.example.amir.core.Distribution.alghadversion1.Classes.Connection;
import com.example.amir.core.Distribution.alghadversion1.Classes.Contract;
import com.example.amir.core.Distribution.alghadversion1.Classes.ContractType;
import com.example.amir.core.Distribution.alghadversion1.Classes.GPSTracker;
import com.example.amir.core.Distribution.alghadversion1.Classes.IssueDate;
import com.example.amir.core.Distribution.alghadversion1.Classes.PaymentTypes;
import com.example.amir.core.Distribution.alghadversion1.Classes.SubscriberLocation;
import com.example.amir.core.Distribution.alghadversion1.Classes.SubscriptionType;
import com.example.amir.core.Distribution.bluetoothPrinterClasses.DeviceList;
import com.example.amir.core.Distribution.bluetoothPrinterClasses.PrintInterMec;
import com.example.amir.core.R;

import com.honeywell.mobility.print.LinePrinter;
import com.honeywell.mobility.print.LinePrinterException;
import com.honeywell.mobility.print.PrintProgressEvent;
import com.honeywell.mobility.print.PrintProgressListener;

//import com.intermec.print.lp.LinePrinter;
//import com.intermec.print.lp.LinePrinterException;
//import com.intermec.print.lp.PrintProgressEvent;
//import com.intermec.print.lp.PrintProgressListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class ReNewAppointment extends Activity implements OnClickListener,
		OnCheckedChangeListener, OnItemSelectedListener {
	Context context = this;
	TextView tvName, tvBank, tvChequeNo, tvDueDate, tvNetAmount, tvPrice,
			tvMSG, tvAppointmentTypeName;
	EditText TxTCheckNo, TxTCheckDueDate, TxTStartDate, TxTEndDate,
			TxTCopiesNo;
	Spinner SpinSubscType, SpinPaymantType, SpinBankType, SpinContractType;
	CheckBox CheckCheque, CheckEndDate;//, CheckIncludeOffer;
	CheckBox CheckBoxLocation;
	DatePickerDialog.OnDateSetListener dateSetListener, dateSetListenerEndDate;
	List<SubscriptionType> listSubscriptionType;
	ArrayAdapter<SubscriptionType> AdapterSubscriptionType;
	List<PaymentTypes> listPaymentTypes;
	ArrayAdapter<PaymentTypes> AdapterPaymentTypes;
	List<Bank> listBanks;
	ArrayAdapter<Bank> AdapterBank;
	List<ContractType> listContractTypes;
	ArrayAdapter<ContractType> AdapterContractType;
	Integer ClientID, RenewContractID, AppointmentTypeID;
	RadioButton RadioOffer, RadioOfferSpecial;
    Button DistbtnSaveContract;
	
	//
	private CalendarView calendarView;
	
	private Calendar selectedDate;
	String weekNameDay, weekNameMonth, weekNameYear;
	GPSTracker GPS;
	SubscriberLocation objSubscriberLocation;
	ShowProgressDialog objShowProgressDialog;
	//
	// Date resultdate = new Date(c.getTime().);

	Date GetDay, GetMonth, GetYear;
	String DateAfter;
	Locale[] availableLocales;
	int ContractTypeIDFrom;
	int SubscriptionTypeIDfromappoi, SubscriptionTypeID;
	private boolean x = false;
	// Boolean IsPosted, IsExtendedPeriod, IsIncludeOffers, IsCheque;

	String TimeTo, TimeFromAppointment, AppointmentTypeName;
	int Period, Year, Month, Day;// ContractNo;

	Calendar c;

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;
	int ReferancNumber = 0;
	private String StartDate, EndDate;

	// String ContractName, Note, address, phone, NationalNumber, CompanyName;//
	// For
	// Printer
	File mypathHeader; // ScrollView
	String MacAddress;
	// LinearLayout layoutSignature;
	// public signature mSignature;
	public static Bitmap mBitmap;
	public static ImageView ImgSigntuare;
	byte[] ImageToDB;
	Contract objContract = new Contract();

	public File mypath, directory, directoryph;
	public static String current = null;
	private String uniqueId;
	public static String FinalPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.distrenewappointment);
		try {
			InitialView();
			InitPrinter();
			CopyAssetFiles();
			FillSpinner(ContractTypeIDFrom, SubscriptionTypeIDfromappoi);
			GPS = new GPSTracker(context);
			objSubscriberLocation = new SubscriberLocation();
			dateSetListener = new DatePickerDialog.OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					int monthPlusone = (monthOfYear + 1);
					DateAfter = "" + year + "-" + monthPlusone + "-"
							+ dayOfMonth;
					// DateAfter.split("T");
					Log.d("date After ", DateAfter);
					DateValid();
				}
			};
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}// End On Create

	// @Override
	// public void onBackPressed() {
	// System.exit(1);
	// }

	public byte[] convertBitmapToByteArray(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		} else {
			byte[] b = null;
			try {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				bitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
				b = byteArrayOutputStream.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
				tvMSG.append(e.getMessage());
				tvMSG.setTextColor(Color.RED);
			}
			return b;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:
			try {

				// When DeviceListActivity returns with a device to connect
				if (resultCode == Activity.RESULT_OK) {
					// Get the device MAC address
					MacAddress = data.getExtras().getString(
							DeviceList.EXTRA_DEVICE_ADDRESS);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			try {

				if (resultCode != Activity.RESULT_OK) {
					GetToast.Toast(ReNewAppointment.this,
							getString(R.string.Enable_To_Connected));
					finish();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		}

	}

	private void InitialView() {
		try {

            DistbtnSaveContract = (Button) findViewById(R.id.DistbtnSaveContract);
            DistbtnSaveContract.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ToSaveContract();
                        //SaveLocation();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
			SpinSubscType = (Spinner) findViewById(R.id.spinnerSubscTypeReNew);
			SpinPaymantType = (Spinner) findViewById(R.id.spinnerPaymentType);
			SpinBankType = (Spinner) findViewById(R.id.spinnerBank);
			SpinContractType = (Spinner) findViewById(R.id.spinnerContractType);

			tvName = (TextView) findViewById(R.id.tvNameReNewAppointment);
			tvMSG = (TextView) findViewById(R.id.tvMSG_ReNew);
			tvAppointmentTypeName = (TextView) findViewById(R.id.tvShow_AppointmentTypeName);

			TxTCheckNo = (EditText) findViewById(R.id.TxTChequeNo);
			TxTCheckDueDate = (EditText) findViewById(R.id.TxTDueDate);
			TxTStartDate = (EditText) findViewById(R.id.TxTStartDateReNew);
			TxTStartDate.setOnClickListener(this);
			TxTEndDate = (EditText) findViewById(R.id.TxTEndDateReNew);
			TxTEndDate.setOnClickListener(this);
			TxTStartDate.addTextChangedListener(new TextWatcher() {
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {

				}

				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {

				}

				public void afterTextChanged(Editable s) {

					int SelectedItem = SpinContractType
							.getSelectedItemPosition();
					if (SelectedItem != -1) {
						int ID = AdapterContractType.getItem(SelectedItem)
								.getID();
						GenerateEndDate(ID);
					}
				}
			});

			// TxTEndDate.setOnClickListener(this);
			TxTCopiesNo = (EditText) findViewById(R.id.TxTReNewNumberCopies);

			/*
			 * long da = Date.parse(TxTStartDate.getText().toString()); long d =
			 * da;
			 */

			// c = Calendar.getInstance(Locale.ENGLISH);
			// Locale.getDefault();
			// availableLocales = Locale.getAvailableLocales();

			tvBank = (TextView) findViewById(R.id.tvReNewBank);
			tvChequeNo = (TextView) findViewById(R.id.TvReNewAppointChequeNo);
			tvDueDate = (TextView) findViewById(R.id.tvReNewAppoDueDate);
			tvNetAmount = (TextView) findViewById(R.id.tvReNewNetAmount);
			tvPrice = (TextView) findViewById(R.id.tvReNewPrice);

			tvBank.setEnabled(false);
			tvChequeNo.setEnabled(false);
			tvDueDate.setEnabled(false);

			TxTCheckDueDate.setEnabled(false);
			TxTCheckNo.setEnabled(false);
			SpinBankType.setEnabled(false);

			CheckCheque = (CheckBox) findViewById(R.id.checkBoxCheque);
			CheckEndDate = (CheckBox) findViewById(R.id.checkBoxEndDateReNew);
			CheckBoxLocation = (CheckBox) findViewById(R.id.chbBoxLocation);
//			CheckIncludeOffer = (CheckBox) findViewById(R.id.checkBoxIncludeOffers);

			RadioOffer = (RadioButton) findViewById(R.id.radioButtonOffer);
			RadioOfferSpecial =(RadioButton) findViewById(R.id.radioButtonOfferSpecial );

			CheckCheque.setOnCheckedChangeListener(this);
			CheckEndDate.setOnCheckedChangeListener(this);
			CheckBoxLocation.setOnCheckedChangeListener(this);
//			CheckIncludeOffer.setOnCheckedChangeListener(this);

			RadioOffer.setOnClickListener(this);
			RadioOfferSpecial.setOnClickListener(this);

			TxTCheckDueDate.setOnClickListener(this);
			TxTCopiesNo.setText("" + 1);
			SpinContractType.setOnItemSelectedListener(this);
			ImgSigntuare = (ImageView) findViewById(R.id.imageViewSigntuare);

			Intent i = getIntent();
			tvName.setText(String.valueOf(i.getStringExtra("ClientName")));
			TimeTo = i.getStringExtra("TimeTo");
			TimeFromAppointment = i.getStringExtra("TimeFrom");

			ClientID = i.getIntExtra("ClientID", 0);
			ContractTypeIDFrom = i.getIntExtra("ContractTypeID", 0);
			SubscriptionTypeIDfromappoi = i
					.getIntExtra("SubscriptionTypeID", 0);
			// SubscriptionTypeID = i.getIntExtra("SubscriptionTypeID", 0);
			AppointmentTypeName = i.getStringExtra("AppointmentTypeName");

			// ContractNo = i.getIntExtra("ContractNo", 0);
			RenewContractID = i.getIntExtra("ContractId", 0);
			AppointmentTypeID = i.getIntExtra("AppointmentTypeID", 0);
			// tvAppointmentTypeName.setText("New Subscriptions");
			// tvAppointmentTypeName
			// .setTextColor(getResources().getColor(R.color.Red));
			if (AppointmentTypeID == 2) {
				tvAppointmentTypeName.setText(AppointmentTypeName);
				tvAppointmentTypeName.setTextColor(getResources().getColor(
						R.color.Red));
			} else {
				tvAppointmentTypeName.setText(AppointmentTypeName);
				tvAppointmentTypeName.setTextColor(getResources().getColor(
						R.color.Green));
			}
			TxTCopiesNo.setText(String.valueOf(i.getIntExtra("CopiesNo", 1)));

			TxTCopiesNo.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					if (TxTCopiesNo.getText().toString().length() == 0
							|| TxTCopiesNo.getText().toString().equals("0")) {
						GetToast.Toast(context,
								getString(R.string.Copies_No_Is_Not_Valid));
						return;
					}
					tvNetAmount.setText(""
							+ (Double.valueOf(Double.parseDouble(tvPrice
									.getText().toString())) * (Integer
									.parseInt(TxTCopiesNo.getText().toString()))));
				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
				}

				@Override
				public void afterTextChanged(Editable arg0) {

				}
			});

			ImgSigntuare.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					try {
						// startActivity(new Intent(ReNewAppointment.this,
						// Signtuare.class));

						startActivityForResult(new Intent(
								ReNewAppointment.this, Signtuare.class), 10);
					} catch (Exception e) {
						// TODO: handle exception
						Log.e("img Signtuare", e.getMessage());
						CatchMsg.WriteMSG("Renew img Signtuare", e.getMessage());
					}
				}
			});

			objContract = (Contract) i.getSerializableExtra("objContract");
			String RequestCode = i.getStringExtra("requestCode");
			if (RequestCode.equals("DateFromProfile")) {

				TxTStartDate.setText(GetStartDateFromProfile(ClientID,
						objContract.GetContractNo()));

//				TxTStartDate.setText(GetStartDate("", "",
//						DistributionMain.DriverId, ClientID));

			} else if (RequestCode.equals("DateFromAppointment"))

				TxTStartDate.setText(GetStartDate(TimeFromAppointment, TimeTo,
						DistributionMain.DriverId, ClientID));
			else
				TxTStartDate.setText(GetDateTodayPulse7Day(GetDateToday()));

			// phone = i.getStringExtra("Phone");
			// Note = i.getStringExtra("Note");
			// address = i.getStringExtra("Address");
			// CompanyName = i.getStringExtra("CompanyName");
			// NationalNumber = i.getStringExtra("NationalNumber");
			directory = new File(Environment.getExternalStorageDirectory()
					+ "/DCIM" + "/.thumbnails/");
			directoryph = new File(Environment.getExternalStorageDirectory()
					+ "/DCIM" + "/Camera/");
			directory = new File(Environment.getExternalStorageDirectory()
					+ "/GLP_Images");

			if (!directoryph.exists())
				directoryph.mkdir(); // directory is created;

			if (!directory.exists())
				directory.mkdir(); // directory is created;

			uniqueId = getTodaysDate() + "_" + getCurrentTime();
			current = uniqueId + ".bmp";

			mypath = new File(directory, current);
			FinalPath = directory + "/" + current;
			deleteDirectory(directory);
			if (mypath.exists())
				mypath.delete();
			if (directory.exists())
				directory.delete();
			deleteDirectory(directoryph);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {

			BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
					.getDefaultAdapter();
			if (mBluetoothAdapter == null) {
				// Device does not support Bluetooth
				GetToast.Toast(context, "Device does not support Bluetooth");
			} else {
				if (!mBluetoothAdapter.isEnabled()) {
					// Bluetooth is not enable :)
					GetToast.Toast(context, "Bluetooth is not enable :)");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}// End |Definition Value

	public static boolean deleteDirectory(File path) {
		// TODO Auto-generated method stub
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

	//
	private String getTodaysDate() {

		final Calendar c = Calendar.getInstance();
		int todaysDate = (c.get(Calendar.YEAR) * 10000)
				+ ((c.get(Calendar.MONTH) + 1) * 100)
				+ (c.get(Calendar.DAY_OF_MONTH));
		Log.w("DATE:", String.valueOf(todaysDate));
		return (String.valueOf(todaysDate));

	}

	private String getCurrentTime() {

		final Calendar c = Calendar.getInstance();
		int currentTime = (c.get(Calendar.HOUR_OF_DAY) * 10000)
				+ (c.get(Calendar.MINUTE) * 100) + (c.get(Calendar.SECOND));
		Log.w("TIME:", String.valueOf(currentTime));
		return (String.valueOf(currentTime));

	}

	private void subscriberlocation() {
		try {

			objSubscriberLocation.setPDAClientID("" + ClientID);
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

	private String GetDateTodayPulse7Day(String DateToday) {
		String dateInString = DateToday; // Start date
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		try {
			c.setTime(sdf.parse(dateInString));
		} catch (ParseException e) {

			e.printStackTrace();
		}
		c.add(Calendar.DATE, 10);
		sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date resultdate = new Date(c.getTimeInMillis());
		dateInString = sdf.format(resultdate);
		this.StartDate = dateInString;
		System.out.println("AddPeriodDay :" + dateInString);
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		// this.FromDate = sdf.format(resultdate);
		return dateInString;
	}

	//
	private String GetDateToday() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		return sdf.format(cal.getTimeInMillis());
		// return null;
	}// End Date Today

	private void DateValid() {
		if (IsValidDate(DateAfter, this.StartDate)) {// TxTStartDate.getText().toString()
			TxTStartDate.setText(DateAfter);
		}
	}

	private String GetStartDateFromProfile(Integer clientID, long ContractNo) {
		IssueDate objIssueDate = new IssueDate();
		String StartDate = "";
		String arra[];
		try {
			StartDate = objIssueDate.GetStartDateByClientIDAndContractNo(
					clientID, ContractNo);

			arra = StartDate.split("T");
			StartDate = arra[0];
			Calendar b = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
					Locale.ENGLISH);

			this.StartDate = StartDate;

			// Date resultdate = new Date();
			try {
				b.setTime(sdf.parse(StartDate));
				// c.add(Calendar.DATE, 1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			StartDate = sdf.format(b.getTimeInMillis());
		} catch (Exception e) {
			tvMSG.append(e.getMessage());
			tvMSG.setTextColor(Color.RED);
			StartDate = e.getMessage();
		}// End Try Catch
		return StartDate;
	}// End FromProlifs

	private String GetStartDate(String timeFrom, String timeTo, int driverId,
			int ClientID) {
		IssueDate objIssueDate = new IssueDate();
		String StartDate = "";
		String arra[];

		try {

			StartDate = objIssueDate.GetFirstIssueDate(driverId, timeFrom,
					ClientID);
			// GetDateToday(StartDate);
			arra = StartDate.split("T");
			StartDate = arra[0];

			Calendar b = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
					Locale.ENGLISH);
			this.StartDate = StartDate;
			try {
				b.setTime(sdf.parse(StartDate));
				// c.add(Calendar.DATE, 1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			StartDate = sdf.format(b.getTime());

		} catch (Exception e) {
//			Log.d("Error In Get Start date ", e.getMessage());
			tvMSG.append(e.getMessage());
			tvMSG.setTextColor(Color.RED);
			StartDate = e.getMessage();
		}// end Try Catch
		return StartDate;
	}// end Get Start Date

	public void FillSpinner(int id, int subctype) {
		try {
			SubscriptionType objSubscriptionType = new SubscriptionType();
			listSubscriptionType = objSubscriptionType.getSubscriptionType();
			AdapterSubscriptionType = new ArrayAdapter<SubscriptionType>(this,
					android.R.layout.simple_spinner_item, listSubscriptionType);
			AdapterSubscriptionType
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			SpinSubscType.setAdapter(AdapterSubscriptionType);
			SpinSubscType.setSelection(getIndexappointment(SpinSubscType,
					subctype) - 1);
			// Payment Type
			PaymentTypes objPaymentTypes = new PaymentTypes();
			listPaymentTypes = objPaymentTypes.getPayment();
			AdapterPaymentTypes = new ArrayAdapter<PaymentTypes>(this,
					android.R.layout.simple_spinner_item, listPaymentTypes);
			AdapterPaymentTypes
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			SpinPaymantType.setAdapter(AdapterPaymentTypes);
			// Bank Type
			Bank objBank = new Bank();
			listBanks = objBank.getBankName();
			AdapterBank = new ArrayAdapter<Bank>(this,
					android.R.layout.simple_spinner_item, listBanks);
			AdapterBank
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			SpinBankType.setAdapter(AdapterBank);
			// ContractType
			ContractType objContractType = new ContractType();
			listContractTypes = objContractType.getContractType();

			AdapterContractType = new ArrayAdapter<ContractType>(this,
					android.R.layout.simple_spinner_item, listContractTypes);
			AdapterContractType
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			SpinContractType.setAdapter(AdapterContractType);
			SpinContractType.setSelection(getIndex(SpinContractType, 12) - 1);
		} catch (Exception e) {
			tvMSG.append(e.getMessage());
			tvMSG.setTextColor(Color.RED);
			//Log.d("Error In Fill Spinner ", e.getMessage());
            e.printStackTrace();

		}// End Try Catch
	}// End FillSpinner

	private int getIndex(Spinner contractType2, Integer contractTypeID2) {
		int i = 0;
		try {

			for (ContractType e : listContractTypes) {
				i += 1;
				if (e.getID() == contractTypeID2) {
					Log.d("Contract Name", e.getNameEn());
					break;
				}
			}// End For
		} catch (Exception e) {
			tvMSG.append(e.getMessage());
			tvMSG.setTextColor(Color.RED);
			Log.d("Error In Get Index ", e.getMessage());
		}
		return i;
	}

	private int getIndexappointment(Spinner contractType2,
			Integer contractTypeID2) {
		int i = 0;
		try {

			for (SubscriptionType e : listSubscriptionType) {
				i += 1;
				if (e.getID() == contractTypeID2) {
					Log.d("Contract Name", e.getNameEn());
					break;
				}
			}// End For
		} catch (Exception e) {
			tvMSG.append(e.getMessage());
			tvMSG.setTextColor(Color.RED);
			Log.d("Error In Get Index ", e.getMessage());
		}
		return i;
	}

//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.TxTStartDateReNew:
//			Dialog(TxTStartDate, this.StartDate);// TxTStartDate.getText().toString()
//			break;
//		case R.id.TxTEndDateReNew:
//			int ID = AdapterContractType.getItem(
//					SpinContractType.getSelectedItemPosition()).getID();
//			GenerateEndDate(ID);
//			DialogEndDate(TxTEndDate, this.EndDate);
//			break;
//		case R.id.TxTDueDate:
//			Dialog(TxTCheckDueDate, AddPeriodDay(GetDateToday(), 1));
//			break;
//		}// End switch
//
//	}// End On Click


	@Override
	public void onClick(View v) {
			if(v.getId() == R.id.TxTStartDateReNew) {
				Dialog(TxTStartDate, this.StartDate);// TxTStartDate.getText().toString()
			}else if (v.getId() == R.id.TxTEndDateReNew) {
				int ID = AdapterContractType.getItem(
						SpinContractType.getSelectedItemPosition()).getID();
				GenerateEndDate(ID);
				DialogEndDate(TxTEndDate, this.EndDate);
			}else if (v.getId() == R.id.TxTDueDate){
				Dialog(TxTCheckDueDate, AddPeriodDay(GetDateToday(), 1));

		}// End switch

	}// End On Click


	// protected void Dialogg(final EditText TxTDate) {
	// Calendar calendar = Calendar.getInstance();
	// final int Year = calendar.get(Calendar.YEAR);
	// final int Month = calendar.get(Calendar.MONTH);
	// final int Day = calendar.get(Calendar.DAY_OF_MONTH);
	// final DatePickerDialog.OnDateSetListener dateSetListener;
	// dateSetListener = new DatePickerDialog.OnDateSetListener() {
	// @Override
	// public void onDateSet(DatePicker view, int year, int monthOfYear,
	// int dayOfMonth) {
	// int monthPlusone = (monthOfYear + 1);
	// String DateAfter = "" + year + "-" + monthPlusone + "-"
	// + dayOfMonth;
	// // Calendar C1 = Calendar.getInstance();
	// // C1.setTime(dfDate.parse(strserver));
	// // if (DateAfter.compareTo(StartDate) > 0) {
	// // TxTDate.setText(DateAfter);
	// // } else if(DateAfter.af){
	// // tvMSG.setText("Error");
	// // }
	//
	// }
	// };
	// new DatePickerDialog(context, dateSetListener, Year, Month, Day).show();
	// }

	protected void Dialog(final EditText TxTDate, final String DateBefor) {
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
				// String DateBefor = TxTEndDate.getText().toString();
				String DateAfter = "" + year + "-" + monthPlusone + "-"
						+ dayOfMonth;
				// String DateAftedr = "" + GetYear + "-" + GetMonth + "-"
				// + GetYear;
				DateAfter.split("T");
				DateBefor.split("T");
				if (IsValidDate(DateAfter, DateBefor))
					TxTDate.setText(DateAfter);
				else
					TxTDate.setText(DateBefor);
			}
		};
		new DatePickerDialog(context, dateSetListener, Year, Month, Day).show();
	}

	protected void DialogEndDate(final EditText TxTDate, final String DateBefor) {
		Calendar calendar = Calendar.getInstance();
		// final int Year = calendar.get(Calendar.YEAR);
		// final int Month = calendar.get(Calendar.MONTH);
		// final int Day = calendar.get(Calendar.DAY_OF_MONTH);
		// Integer.parseInt(weekNameYear)
		// Integer.parseInt(weekNameMonth)
		// Integer.parseInt(weekNameDay)
		int GetYear = Integer.parseInt(weekNameYear);
		int GetMonth = Integer.parseInt(weekNameMonth);
		int GetDay = Integer.parseInt(weekNameDay);
		final DatePickerDialog.OnDateSetListener dateSetListener;
		dateSetListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				int monthPlusone = (monthOfYear + 1);
				// String DateBefor = TxTEndDate.getText().toString();
				String DateAfter = "" + year + "-" + monthPlusone + "-"
						+ dayOfMonth;
				// String DateAftedr = "" + GetYear + "-" + GetMonth + "-"
				// + GetYear;
				DateAfter.split("T");
				DateBefor.split("T");
				if (IsValidDate(DateAfter, DateBefor))
					TxTDate.setText(DateAfter);
				else
					TxTDate.setText(DateBefor);
				// selectedDate.set(Integer.parseInt(weekNameYear),
				// Integer.parseInt(weekNameMonth),
				// Integer.parseInt(weekNameDay));
				// calendarView.
				// calendarView.setDate(selectedDate.getTimeInMillis());

			}
		};
		new DatePickerDialog(context, dateSetListener, GetYear, GetMonth - 1,
				GetDay).show();
	}

	private boolean IsValidDate(String DateAfter, String DateServer) {
		String strafter = DateAfter;
		String strserver = DateServer;
		boolean result = false;
		SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		try {
			Calendar C = Calendar.getInstance();
			C.setTime(dfDate.parse(strafter));

			Calendar C1 = Calendar.getInstance();
			C1.setTime(dfDate.parse(strserver));

			Date dateafter = new Date(C.getTimeInMillis());
			Date dateserver = new Date(C1.getTimeInMillis());

			if (dateafter.compareTo(dateserver) > 0) {
				result = true;
			}// End If
			else if (dateafter.after(dateserver))
				result = false;
		} catch (Exception e) {
			tvMSG.append(e.getMessage());
			tvMSG.setTextColor(Color.RED);
			Log.d("Error in valide ", e.getMessage());
		}
		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// menu.add("Save");
		// menu.add("Signtuare");
		// menu.add("Re-Set StartDate");
		// menu.add("Cancel");
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.distrenewappointment, menu);
		return true;
	}// End On Create Option Menu

	public boolean UpdateClientBlock(int ClientID){
		boolean Check = false;
		try {

			Contract objContract = new Contract();
			Check = objContract.UpdateClientBlock(ClientID);

			if (Check == true ){
				return true ;
			}
			}
	 catch (Exception e) {
			// TODO: handle exception
			Log.e("Error Area", e.getMessage());
		}
			return Check ;
		}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//
//		switch (item.getItemId()) {
//		case R.id.ReNewAppo_Save:
//
//			try {
//				ToSaveContract();
//				SaveLocation();
//				//UpdateClientBlock(ClientID);
//				//item.setEnabled(false);
//
//			} catch (Exception e) {
//				// TODO: handle exception
//				Log.e("ToSaveContract", e.getMessage());
//				// CatchMsg.WriteMSG("ToSaveContract", e.getMessage());
//			}
//
//			break;
//		case R.id.ReNewAppo_Cancel:
//			this.finish();
//			break;
//		case R.id.ReNewAppo_ReSetStartDate:
//			if (!(TimeTo == null && TimeFromAppointment == null)) {
//				TxTStartDate.setText(GetStartDate(TimeFromAppointment, TimeTo,
//						DistributionMain.DriverId, ClientID));
//			}// End If
//			else
//				TxTStartDate.setText(GetStartDateFromProfile(ClientID,
//						objContract.GetContractNo()));
//			break;
//		case R.id.ReNewAppo_Signtuare:
//			ImgSigntuare.performClick();
//			break;
//		default:
//			break;
//		}
//		return super.onOptionsItemSelected(item);
//	}// End On Option Item Selected

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

			if(item.getItemId() == R.id.ReNewAppo_Save) {

				try {
					ToSaveContract();
					SaveLocation();
					//UpdateClientBlock(ClientID);
					//item.setEnabled(false);

				} catch (Exception e) {
					// TODO: handle exception
					//Log.e("ToSaveContract", e.getMessage());
					// CatchMsg.WriteMSG("ToSaveContract", e.getMessage());
				}
			}else if (item.getItemId() == R.id.ReNewAppo_Cancel) {
				this.finish();
			}else if (item.getItemId() == R.id.ReNewAppo_ReSetStartDate) {
				if (!(TimeTo == null && TimeFromAppointment == null)) {
					TxTStartDate.setText(GetStartDate(TimeFromAppointment, TimeTo,
							DistributionMain.DriverId, ClientID));
				}// End If
				else
					TxTStartDate.setText(GetStartDateFromProfile(ClientID,
							objContract.GetContractNo()));
			}else if(item.getItemId() == R.id.ReNewAppo_Signtuare){
				ImgSigntuare.performClick();

		}
		return super.onOptionsItemSelected(item);
	}// End On Option Item Selected

	private boolean SaveLocation()
	{

		if (CheckBoxLocation.isChecked()){
			subscriberlocation();
			int SubscriberLocation = objSubscriberLocation
					.InsertSubscriberLocation(ReNewAppointment.this.objSubscriberLocation);
			if (SubscriberLocation > 0) {
				GetToast.Toast(context, getString(R.string.BoxLocationSavedDone));
			} else {
				GetToast.Toast(context, "Error");
			}
		}
		return true;
	}

//	@Override
//	public void onCheckedChanged(CompoundButton buttonview, boolean isChecked) {
//		switch (buttonview.getId()) {
//		case R.id.checkBoxCheque:
//			if (isChecked) {
//				TxTCheckDueDate.setEnabled(true);
//				TxTCheckNo.setEnabled(true);
//				SpinBankType.setEnabled(true);
//				tvBank.setEnabled(true);
//				tvChequeNo.setEnabled(true);
//				tvDueDate.setEnabled(true);
//
//			} else {
//				tvBank.setEnabled(false);
//				tvChequeNo.setEnabled(false);
//				tvDueDate.setEnabled(false);
//				TxTCheckDueDate.setEnabled(false);
//				TxTCheckNo.setEnabled(false);
//				SpinBankType.setEnabled(false);
//			}// End Else
//			break;
//		case R.id.checkBoxEndDateReNew:
//			if (isChecked) {
//				TxTEndDate.setEnabled(true);
//
//			}// End If
//			else
//				TxTEndDate.setEnabled(false);
//			break;
//		case R.id.chbBoxLocation:
//			if(isChecked)
//			{
//
//			}
////		case R.id.checkBoxIncludeOffers:
////
////			break;
//
//		}// End Switch
//
//	}// End onCheckedChanged

	@Override
	public void onCheckedChanged(CompoundButton buttonview, boolean isChecked) {
			if(buttonview.getId() == R.id.checkBoxCheque) {
				if (isChecked) {
					TxTCheckDueDate.setEnabled(true);
					TxTCheckNo.setEnabled(true);
					SpinBankType.setEnabled(true);
					tvBank.setEnabled(true);
					tvChequeNo.setEnabled(true);
					tvDueDate.setEnabled(true);

				} else {
					tvBank.setEnabled(false);
					tvChequeNo.setEnabled(false);
					tvDueDate.setEnabled(false);
					TxTCheckDueDate.setEnabled(false);
					TxTCheckNo.setEnabled(false);
					SpinBankType.setEnabled(false);
				}// End Else
			}else if(buttonview.getId() == R.id.checkBoxEndDateReNew) {
				if (isChecked) {
					TxTEndDate.setEnabled(true);

				}// End If
				else
					TxTEndDate.setEnabled(false);
			}else if(buttonview.getId() == R.id.chbBoxLocation){
				if(isChecked)
				{

				}
//		case R.id.checkBoxIncludeOffers:
//
//			break;

		}// End Switch

	}// End onCheckedChanged

	private void GenerateEndDate(int ContractType) {
		tvPrice.setText(""	+ AdapterContractType.getItem(SpinContractType.getSelectedItemPosition()).getPrice());
		int x = AdapterContractType.getItem(SpinContractType.getSelectedItemPosition()).getPeriod();
		TxTEndDate.setText(GetDateTodayPulseDay(TxTStartDate.getText().toString(), x));
	}

	private String GetDateTodayPulseDay(String DateToday, int Period) {
		String dateInString = DateToday; // Start date
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		this.EndDate = dateInString;
		try {
			c.setTime(sdf.parse(dateInString));
		} catch (ParseException e) {

			e.printStackTrace();
		}
		c.add(Calendar.DATE, Period);
		sdf = new SimpleDateFormat("dd", Locale.ENGLISH);
		sdf.setCalendar(c);
		weekNameDay = sdf.format(c.getTime());

		sdf = new SimpleDateFormat("MM", Locale.ENGLISH);
		sdf.setCalendar(c);
		weekNameMonth = sdf.format(c.getTime());

		sdf = new SimpleDateFormat("yyyy", Locale.ENGLISH);
		sdf.setCalendar(c);
		weekNameYear = sdf.format(c.getTime());

		sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date resultdate = new Date(c.getTimeInMillis());

		dateInString = sdf.format(resultdate);

		System.out.println("AddPeriodDay :" + dateInString);
		sdf = new SimpleDateFormat("MM/dd/yyyy");
		// this.FromDate = sdf.format(resultdate);
		return dateInString;
	}

	// public void printDifference(Date startDate, Date endDate) {
	//
	// // milliseconds
	// long different = endDate.getTime() - startDate.getTime();
	//
	// System.out.println("startDate : " + startDate);
	// System.out.println("endDate : " + endDate);
	// System.out.println("different : " + different);
	//
	// long secondsInMilli = 1000;
	// long minutesInMilli = secondsInMilli * 60;
	// long hoursInMilli = minutesInMilli * 60;
	// long daysInMilli = hoursInMilli * 24;
	//
	// long elapsedDays = different / daysInMilli;
	// different = different % daysInMilli;
	//
	// long elapsedHours = different / hoursInMilli;
	// different = different % hoursInMilli;
	//
	// long elapsedMinutes = different / minutesInMilli;
	// different = different % minutesInMilli;
	//
	// long elapsedSeconds = different / secondsInMilli;
	//
	// System.out.printf("%d days, %d hours, %d minutes, %d seconds%n",
	// elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
	//
	// }

	private void ToSaveContract() {

		try {
			FillObject();
			// Bitmap bitmap = BitmapFactory.decodeFile(Signtuare.FinalPath);
			// ImageToDB = convertBitmapToByteArray(bitmap);
			AlertDialog.Builder builderr = new AlertDialog.Builder(
					ReNewAppointment.this);
			builderr.setMessage("Do You Want To Save?\n" + "Contract Amount: "
					+ tvNetAmount.getText().toString());

			builderr.setCancelable(false);
			builderr.setPositiveButton(getString(R.string.Yes),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {

							if (Integer.parseInt(TxTCopiesNo.getText()
									.toString()) <= 0) {
								GetToast.Toast(context,getString(R.string.CopiesNumberMustbe1orMore));
//										"CopiesNumber Must be 1 or More");
								return;
							}// End If

							String startdate = TxTStartDate.getText()
									.toString();
							String EndDate = TxTEndDate.getText().toString();

							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd", Locale.ENGLISH);
							Calendar c = Calendar.getInstance();
							try {
								c.setTime(sdf.parse(startdate));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							Calendar c1 = Calendar.getInstance();
							try {
								c1.setTime(sdf.parse(EndDate));

							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Date resultStartdate = new Date(c.getTimeInMillis());
							Date resultEnddate = new Date(c1.getTimeInMillis());
							long date = resultStartdate.getTime()
									- resultEnddate.getTime();
							// long Period = resultStartdate.getDate()
							// + resultEnddate.getDate();
							int presumedDays = (int) ((c.getTimeInMillis() - c1
									.getTimeInMillis()) / (1000 * 60 * 60 * 24));
							int Period = Math.abs(presumedDays);
							// startdate = sdf.format(resultdate);
							// EndDate = sdf.format(resultEnddate);
							int PositionContractType = AdapterContractType
									.getItem(
											SpinContractType
													.getSelectedItemPosition())
									.getPeriod();

							// if (Double.valueOf(tvNetAmount.getText()
							// .toString()) >= 0.0) {
							// GetToast.Toast(context,
							// getString(R.string.InValidNetAmount));
							// } else
							if (date >= 0) {
								GetToast.Toast(context,
										getString(R.string.InValidDate));
							} else if (PositionContractType < Period) {
								GetToast.Toast(context,
										getString(R.string.PleaseCheckofDate));
							} else {

								if (Connection.isConnectingToInternet(context)
										&& CoreLogInActivity.UserID > 0 && CoreLogInActivity.SalesID > 0) {
								    new SaveContractTask().execute();
								     UpdateClientBlock(ClientID);
									 x= true ;
								} else {
									GetToast.Toast(context, "Error");
									Connection.showAlertDialog(context);
									dialog.cancel();
								}
							}
						}
					});
			builderr.setNegativeButton(getString(R.string.No),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});

			AlertDialog alertt = builderr.create();
			alertt.show();
			if(x == true)
		{
			alertt.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
		}


		} catch (Exception e) {
			tvMSG.append(e.getMessage());
			tvMSG.setTextColor(Color.RED);
			CatchMsg.WriteMSG("SaveContractTask", e.getMessage());
			Log.d("To Save ReNew ", e.getMessage());

		}// End Try Catch

	}// End To save


	private void FillObject() {
		try {
			if (objContract == null && DistributionMain.UserID == 0) {
				GetToast.Toast(context, getString(R.string.YouHaveProblem));
				return;
			}
			/* 0 */objContract.SetSubscriptionTypeID(AdapterSubscriptionType
					.getItem(SpinSubscType.getSelectedItemPosition()).getID());
			/* 1 */objContract.SetPaymentTypeID(AdapterPaymentTypes.getItem(
					SpinPaymantType.getSelectedItemPosition()).getID());

			objContract.SetContractTypeNameEn(AdapterContractType.getItem(
					SpinContractType.getSelectedItemPosition()).getNameEn());

			objContract.SetNationalNo(getIntent().getExtras().getString(
					"NationalNumber"));

			objContract.SetClientPhone(getIntent().getExtras().getString(
					"Phone"));

			objContract.setCompanyName(getIntent().getExtras().getString(
					"CompanyName"));

			/* 2 */objContract.SetContractTypeID(AdapterContractType.getItem(
					SpinContractType.getSelectedItemPosition()).getID());

			/* 3 */objContract.SetCopiesNo(Integer.parseInt(TxTCopiesNo
					.getText().toString()));

			if (CoreLogInActivity.UserID > 0 && CoreLogInActivity.SalesID > 0) {
				/* 4 */objContract.SetSalesID(CoreLogInActivity.SalesID);
				/* 5 */objContract.SetEntryUserID(CoreLogInActivity.UserID);
			} else {
				GetToast.Toast(context, getString(R.string.YouHaveProblem));
			}
			/* 6 */objContract.SetNetAmount(Double.valueOf(tvNetAmount
					.getText().toString()));
			/* 7 */objContract.SetFromDate(TxTStartDate.getText().toString());
			/* 8 */objContract.SetToDate(TxTEndDate.getText().toString());
			/* 9 */objContract.SetDeviceID(DistributionShowmain.DeviceID);
			/* 10 */objContract.SetIsPosted(false);
			/* 11 */objContract.SetIsExtendedPeriod(CheckEndDate.isChecked());

//			/* 12 */objContract.SetIsIncludeOffers(CheckIncludeOffer
//					.isChecked());

			objContract.SetIsIncludeOffers(RadioOfferSpecial
					.isChecked());
			/* 13 */objContract.SetIsCheque(CheckCheque.isChecked());

			if (GPS.canGetLocation()) {
				Location location = GPS.getLocation();
				if (location != null) {
					/* 14 */objContract.SetLatitude(location.getLatitude());// GPS.getLatitude()
					/* 15 */objContract.SetLongitude(location.getLongitude());// GPS.getLongitude()
				}
				// getAddress(location);
			} else {
				/* 14 */objContract.SetLatitude(0.0);// GPS.getLatitude()
				/* 15 */objContract.SetLongitude(0.0);// GPS.getLongitude()
			}
			if (AppointmentTypeID == 2) {
				objContract.SetRenewContractID(0);
			} else {
				/* 16 */objContract.SetRenewContractID(RenewContractID);
			}
			/* 17 */objContract.SetClientID(ClientID);

			ImgSigntuare.buildDrawingCache();
			Bitmap bitmap = ImgSigntuare.getDrawingCache();
			/* 18 */objContract.setSignImage(convertBitmapToByteArray(bitmap));

			if (CheckCheque.isChecked()) {
				/* 19 */objContract.SetChequeDueDate(TxTCheckDueDate.getText()
						.toString());
				/* 20 */objContract.SetBank(AdapterBank.getItem(
						SpinBankType.getSelectedItemPosition()).getNo());
				/* 21 */objContract
						.SetChequeNo(TxTCheckNo.getText().toString());
			}// end if
			else {
				objContract.SetChequeDueDate("");
				objContract.SetBank(0);
				objContract.SetChequeNo("");
			}
			// objContract.SetContractTypeNameEn(AdapterContractType.getItem(
			// SpinContractType.getSelectedItemPosition()).getNameEn());
			objContract.SetClientName(tvName.getText().toString());


		} catch (Exception e) {
			Log.d("Error In Fill Object ", e.getMessage());
			tvMSG.append(e.getMessage());
			tvMSG.setTextColor(Color.RED);
			e.printStackTrace();
			// CatchMsg.WriteMSG("Fill Object", e.getMessage());
		}
	}// End Fill Object

	private void InitPrinter() {
		try {

			Intent serverIntent = new Intent(ReNewAppointment.this,
					DeviceList.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		try {

			if (parent.getId() == SpinContractType.getId()) {
				tvPrice.setText(""
						+ AdapterContractType.getItem(
								SpinContractType.getSelectedItemPosition())
								.getPrice());
				tvNetAmount.setText(""
						+ (Double.valueOf(Double.parseDouble(tvPrice.getText()
								.toString())) * (Integer.parseInt(TxTCopiesNo
								.getText().toString()))));
				int id = AdapterContractType.getItem(
						SpinContractType.getSelectedItemPosition()).getID();
				GenerateEndDate(id);

				// TxTEndDate.setText(""+);
				// TxTEndDate.setText(AddPeriodDay(TxTStartDate.getText().toString(),
				// AdapterContractType.getItem(position).getPeriod()));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private String AddPeriodDay(String Date, int Period) {
		String dateInString = Date; // Start date
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);

		System.out.println("String befor date:" + dateInString);
		try {
			c.setTime(sdf.parse(dateInString));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(Calendar.DATE, Period);
		sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date resultdate = new Date(c.getTimeInMillis());
		System.out.println("AddPeriodDay :" + dateInString);
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		dateInString = sdf.format(resultdate);
		// this.ToDate = sdf.format(resultdate);
		// return dateInString;
		return "" + resultdate;
	}// End Add Period Day

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

	public class PrintTask extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {

			objShowProgressDialog = new ShowProgressDialog(context,
					getString(R.string.Please_Wait),
					getString(R.string.Printing_Contract));
			objShowProgressDialog.ShowDialog();
			ReNewAppointment.this.setProgressBarIndeterminateVisibility(true);
			tvMSG.setText("");
		}

		@Override
		protected String doInBackground(String... args) {
			String sResult = null;
			String sMacAddr = args[0];

			if (sMacAddr.contains(":") == false && sMacAddr.length() == 12) {
				// If the MAC address only contains hex digits without the
				// ":" delimiter, then add ":" to the MAC address string.
				char[] cAddr = new char[17];

				for (int i = 0, j = 0; i < 12; i += 2) {
					sMacAddr.getChars(i, i + 2, cAddr, j);
					j += 2;
					if (j < 17) {
						cAddr[j++] = ':';
					}
				}
				sMacAddr = new String(cAddr);
			}
			String sPrinterURI = "bt://" + sMacAddr;
			LinePrinter.ExtraSettings exSettings = new LinePrinter.ExtraSettings();
			exSettings.setContext(context);

			try {
				File profiles = new File(getExternalFilesDir(null),
						"printer_profiles.JSON");
				LinePrinter lp = new LinePrinter(profiles.getAbsolutePath(),
						"PR3", sPrinterURI, exSettings);
				// Registers to listen for the print progress events.
				lp.addPrintProgressListener(new PrintProgressListener() {
					public void receivedStatus(PrintProgressEvent aEvent) {
						// Publishes updates on the UI thread.
						publishProgress(aEvent.getMessageType());
					}
				});

				int numtries = 0;
				int maxretry = 2;

				while (numtries < maxretry) {
					try {
						lp.connect(); // Connects to the printer
						break;
					} catch (LinePrinterException ex) {

						numtries++;
						Thread.sleep(1000);
						// CatchMsg.WriteMSG("Connects to the printer",
						// ex.getMessage());
					}
				}
				if (numtries == maxretry)
					lp.connect();// Final retry

				try {
					CreateAlghad();
				} catch (Exception e) {
					if (!(e.getMessage() == null)) {
						sResult = e.getMessage();
						// CatchMsg.WriteMSG("Create Alghad", sResult);
					} else {
						sResult = "Error Do In Back Create Result";
					}
				}
				if (mypathHeader.exists()) {
					lp.writeGraphic(mypathHeader.getAbsolutePath(),
							LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
							900, 1100);
				}

				try {
					CreateSigntuare();
				} catch (Exception e) {
					if (!(e.getMessage() == null)) {
						sResult = e.getMessage();
						// CatchMsg.WriteMSG("CreateSigntuare", sResult);
						Log.e("CreateSigntuare", sResult);
					} else {
						sResult = "Error Do In Back Create Result";
					}
				}
				if (mypathHeader.exists())
					lp.writeGraphic(mypathHeader.getAbsolutePath(),
							LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
							800, 0);
				try {
					WriteOnIMGSpace();
				} catch (Exception e) {
					if (!(e.getMessage() == null)) {
						sResult = e.getMessage();
						// CatchMsg.WriteMSG("WriteOnIMGSpace", e.getMessage());
					} else {
						sResult = "Error Do In Back Create Result";

					}
				}
				if (mypathHeader.exists())
					lp.writeGraphic(mypathHeader.getAbsolutePath(),
							LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
							800, 0);
				// else
				// sResult = "File Not Exists";

				lp.disconnect(); // Disconnects from the printer
				lp.close(); // Releases resources

			} catch (LinePrinterException ex) {
				sResult = "LinePrinterException: " + ex.getMessage();
				// CatchMsg.WriteMSG("LinePrinterException: ", sResult);
			} catch (Exception ex) {
				if (ex.getMessage() != null) {
					sResult = "Unexpected exception: " + ex.getMessage();
					// CatchMsg.WriteMSG("Unexpected exception: ", sResult);
				} else {
					sResult = "Unexpected exception.";
				}
			}
			return sResult;
		}

		@Override
		protected void onPostExecute(String result) {
			objShowProgressDialog.EndDialog();
			ReNewAppointment.this.setProgressBarIndeterminateVisibility(false);
			if (!(mypathHeader == null))
				mypathHeader.delete();
			if (result != null) {
				GetToast.Toast(context, result);
				tvMSG.setText(result);
			}
			setResult(1);
			ReNewAppointment.this.finish();
			// Intent objIntent=new Intent(context,DistributionShowmain.class);
			// startActivity(objIntent);
			// Dismisses the progress icon on the title bar.
		}
	} // end of class PrintTask

	private void CreateAlghad() throws FileNotFoundException {
		try {

			File directory = new File(Environment.getExternalStorageDirectory()
					+ "/AndroidCRM");
			if (!directory.exists())
				directory.mkdir(); // directory is created;
			String current = "Data.bmp";

			mypathHeader = new File(directory, current);

			FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);

			new PrintInterMec().WriteOnIMGAlghad(mFileOutStream, objContract,
					GetDateToday());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void CreateSigntuare() throws FileNotFoundException {
		try {

			File directory = new File(Environment.getExternalStorageDirectory()
					+ "/AndroidCRM");
			if (!directory.exists())
				directory.mkdir(); // directory is created;
			String current = "Data.bmp";

			mypathHeader = new File(directory, current);

			FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);

			byte[] te = objContract.getSignImage(); //Base64.decode(, 0);
			Bitmap Signtuare = BitmapFactory.decodeByteArray(te, 0, te.length);

			// String current = "Data.bmp";
			// mypathHeader = new File(directory, current);
			// FileOutputStream mFileOutStream = new
			// FileOutputStream(mypathHeader);
			// byte[] te = Base64.decode(objContract.getSignImage(), 0);
			// Bitmap Signtuare = BitmapFactory.decodeByteArray(te, 0,
			// te.length);

			// Bitmap Signtuare = ImgSigntuare.getDrawingCache();
			new PrintInterMec().WriteOnIMGSigntuare(mFileOutStream, Signtuare);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void WriteOnIMGSpace() throws FileNotFoundException {
		try {

			File directory = new File(Environment.getExternalStorageDirectory()
					+ "/AndroidCRM");
			if (!directory.exists())
				directory.mkdir(); // directory is created;
			String current = "Data.bmp";
			mypathHeader = new File(directory, current);
			FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
			new PrintInterMec().WriteOnIMGSpace(mFileOutStream);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	private Boolean CopyAssetFiles() {
		// Copy the asset files we delivered with the application to a location
		// where the LinePrinter can access them.
		boolean result = false;
		try {
			AssetManager assetManager = getAssets();
			String[] files = { "printer_profiles.JSON", "Signature.bmp" };
			for (String filename : files) {
				InputStream input = null;
				OutputStream output = null;
				input = assetManager.open(filename);
				File outputFile = new File(getExternalFilesDir(null), filename);
				output = new FileOutputStream(outputFile);
				byte[] buf = new byte[1024];
				int len;
				while ((len = input.read(buf)) > 0) {
					output.write(buf, 0, len);
				}
				input.close();
				input = null;
				output.flush();
				output.close();
				output = null;
				result = true;
			}

			return result;
		} catch (Exception ex) {
			tvMSG.append(ex.getMessage());
			tvMSG.setTextColor(Color.RED);
			// CatchMsg.WriteMSG("CopyAssetFiles", ex.getMessage());
			ex.printStackTrace();
			return false;
		}
	}

	private class SaveContractTask extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute() {
			objShowProgressDialog = new ShowProgressDialog(context,
					getString(R.string.Please_Wait),
					getString(R.string.Save_Contract));
			objShowProgressDialog.ShowDialog();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub

			return objContract.InsertContractWithLocation(objContract);
			
					}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			try {
                objShowProgressDialog.EndDialog();
				if (result > 0 && CoreLogInActivity.UserID > 0 && CoreLogInActivity.SalesID > 0) {
					objContract.SetContractID(result);
					GetToast.Toast(context, getString(R.string.Save_Done));
					
					 
//					 setResult(1);
//					 ReNewAppointment.this.finish();
                    if(MacAddress.length() > 0 || MacAddress != null) {
                        new PrintTask().execute(MacAddress);
                    }else{
                        GetToast.Toast(context, getString(R.string.NoPrinterFound));
                        setResult(1);
                        ReNewAppointment.this.finish();
                    }

				}// end If

			} catch (Exception e) {
				// TODO: handle exception
				Log.e("RenewAppointment", e.getMessage());
				e.printStackTrace();
				// CatchMsg.WriteMSG("RenewAppointment", e.getMessage());
			}

		}
	}
	
	public void ConnectionSpeed()
	{
		   TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);      


		        	if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_GPRS)) {
		        	GetToast.Toast(context, "GPRS");
		        	//Log.d("Type", "GPRS");
		        	} 
		        	else if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EDGE)) {
		        	GetToast.Toast(context, "2G");
		        	//Log.d("Type", "EDGE 2g");
		        }
	}

//	private void ToSaveContractNew() {
//
//		try {
//
////			FillObject();
//			// Bitmap bitmap = BitmapFactory.decodeFile(Signtuare.FinalPath);
//			// ImageToDB = convertBitmapToByteArray(bitmap);
//			AlertDialog.Builder builderr = new AlertDialog.Builder(
//					ReNewAppointment.this);
//			builderr.setMessage("Do You Want To Save?\n" + "Contract Amount: "
//					+ tvNetAmount.getText().toString());
//
//			builderr.setCancelable(false);
//			builderr.setPositiveButton(getString(R.string.Yes),
//					new DialogInterface.OnClickListener() {				
//						public void onClick(DialogInterface dialog, int id) {
//											
//							if (Integer.parseInt(TxTCopiesNo.getText()
//									.toString()) <= 0) {
//								GetToast.Toast(context,getString(R.string.CopiesNumberMustbe1orMore));
//										"CopiesNumber Must be 1 or More");
//								return;
//							}// End If
//							
//							String startdate = TxTStartDate.getText()
//									.toString();
//							String EndDate = TxTEndDate.getText().toString();
//
//							SimpleDateFormat sdf = new SimpleDateFormat(
//									"yyyy-MM-dd", Locale.ENGLISH);
//							Calendar c = Calendar.getInstance();
//							try {
//								c.setTime(sdf.parse(startdate));
//							} catch (ParseException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//
//							Calendar c1 = Calendar.getInstance();
//							try {
//								c1.setTime(sdf.parse(EndDate));
//
//							} catch (ParseException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							Date resultStartdate = new Date(c.getTimeInMillis());
//							Date resultEnddate = new Date(c1.getTimeInMillis());
//							long date = resultStartdate.getTime()
//									- resultEnddate.getTime();
//							// long Period = resultStartdate.getDate()
//							// + resultEnddate.getDate();
//							int presumedDays = (int) ((c.getTimeInMillis() - c1
//									.getTimeInMillis()) / (1000 * 60 * 60 * 24));
//							int Period = Math.abs(presumedDays);
//							// startdate = sdf.format(resultdate);
//							// EndDate = sdf.format(resultEnddate);
//							int PositionContractType = AdapterContractType
//									.getItem(
//											SpinContractType
//													.getSelectedItemPosition())
//									.getPeriod();
//
//							// if (Double.valueOf(tvNetAmount.getText()
//							// .toString()) >= 0.0) {
//							// GetToast.Toast(context,
//							// getString(R.string.InValidNetAmount));
//							// } else
//							if (date >= 0) {
//								GetToast.Toast(context,
//										getString(R.string.InValidDate));
//							} else if (PositionContractType < Period) {
//								GetToast.Toast(context,
//										getString(R.string.PleaseCheckofDate));
//							} else {
//							
//								if (Connection.isConnectingToInternet(context)
//										&& DistributionMain.UserID > 0 && DistributionMain.SalesID > 0) {
//									new SaveContractTask().execute();
//									UpdateClientBlock(ClientID);
//									x= true ;	
//								} else {
//									GetToast.Toast(context, "Error");
//									Connection.showAlertDialog(context);
//									dialog.cancel();
//								}
//							}
//						}
//					});
//			builderr.setNegativeButton(getString(R.string.No),
//					new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialog, int id) {
//							dialog.cancel();
//						}
//					});
//			
//			AlertDialog alertt = builderr.create();
//			alertt.show();
//			if(x == true)
//		{
//			alertt.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//		}
//		} catch (Exception e) {
//			tvMSG.append(e.getMessage());
//			tvMSG.setTextColor(Color.RED);
//			CatchMsg.WriteMSG("SaveContractTask", e.getMessage());
//			Log.d("To Save ReNew ", e.getMessage());
//
//		}// End Try Catch
//
//	}// End To save
	
}// End Class