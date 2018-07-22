package com.example.amir.core.Distribution.alghadversion1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.example.amir.core.Distribution.alghadversion1.Classes.Contract;
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

import com.sprt.bluetooth.android.BluetoothPrinter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DailyWorkShow extends Activity implements OnClickListener {
	EditText TxTDailyWorkDate;

	Button BtnGetData;
	ExpandableListView exlistDailyWork;
	List<Contract> listDailyWorks;
	Contract objContract;

	TextView tvSummation, tvMsg;
	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;
	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;

	public static BluetoothPrinter mPrinter;
	private String mConnectedDeviceName = null;
	private IntentFilter boundFilter = new IntentFilter(
			BluetoothDevice.ACTION_BOND_STATE_CHANGED);
	private BluetoothDevice currentDevice;
	// Is Connection
	private boolean isConnected;
	private boolean isConnecting;
	Integer ContractID, RefernceNo;
	String Time, MacAddress;
	File mypathHeader;
	Context context = this;

	public enum Mood {
		Contract, Dailywork
	}

	public Mood mood;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distdailywork);
		DefinitionValue();

		// mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		Date dt = new Date();
		Time = "" + dt.getHours() + ":" + dt.getMinutes();
		// If the adapter is null, then Bluetooth is not supported
		// if (mBluetoothAdapter == null) {
		// new GetToast(context, "Bluetooth is not available");
		// finish();
		// }
		// registerReceiver(mStateReceiver, new IntentFilter(
		// BluetoothAdapter.ACTION_STATE_CHANGED));

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		InitPrinter();
		CopyAssetFiles();
	}// End On Create

	private void DefinitionValue() {
		ContractID = 0;
		TxTDailyWorkDate = (EditText) findViewById(R.id.TxTDailyWorkDate);

		TxTDailyWorkDate.setOnClickListener(this);

		tvSummation = (TextView) findViewById(R.id.tvDailyWorkSummation);

		exlistDailyWork = (ExpandableListView) findViewById(R.id.expandableListViewDailyWork);

		exlistDailyWork.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,

			int groupPosition, int childPosition, long id) {
				ContractID = (Integer) ExpandbleListAdapter.childItem.get(0);
				FillObject(ContractID);
				v.setBackgroundColor(Color.GRAY);
				return false;
			}
		});

		BtnGetData = (Button) findViewById(R.id.BtnDailyWorkGetData);
		BtnGetData.setOnClickListener(this);
		tvMsg = (TextView) findViewById(R.id.tvMsgPrint);
	}// End Definition

	private void FillObject(int Contractid) {
		for (Contract e : listDailyWorks) {
			if (e.GetContractID() == Contractid) {
				objContract = e;
				break;
			}
		}
	}

//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.TxTDailyWorkDate:
//			Dialog(TxTDailyWorkDate);
//			break;
//		case R.id.BtnDailyWorkGetData:
//			if (TxTDailyWorkDate.getText().toString().trim().equals("")) {
//				GetToast.Toast(context, getString(R.string.PleaseEnterDate));
//				return;
//			}// End if
//			GetData();
//			break;
//		}
//
//	}// End On Click


	@Override
	public void onClick(View v) {
			if(v.getId() == R.id.TxTDailyWorkDate) {
				Dialog(TxTDailyWorkDate);
			}else if(v.getId() == R.id.BtnDailyWorkGetData) {
				if (TxTDailyWorkDate.getText().toString().trim().equals("")) {
					GetToast.Toast(context, getString(R.string.PleaseEnterDate));
					return;
				}// End if
				GetData();

		}

	}// End On Click

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
				String DateAfter = "" + year + "/" + monthPlusone + "/"
						+ dayOfMonth;
				TxTDate.setText(DateAfter);
			}
		};
		new DatePickerDialog(context, dateSetListener, Year, Month, Day).show();
	}

	private void GetData() {
		try {
			listDailyWorks = new Contract().SelectContract(DistributionMain.UserID,
					TxTDailyWorkDate.getText().toString());
			if (listDailyWorks == null) {
				GetToast.Toast(context, getString(R.string.NoData));
				return;
			}// End if
			else
				FillList();
		} catch (Exception e) {
//			Log.d("Error in Daily work Show Get Data", e.getMessage());
			// CatchMsg.WriteMSG("GetData", e.getMessage());
		}// End try Catch
	}// End Get Data

	private void FillList() {
		try {
			ArrayList<String> parent = new ArrayList<String>();
			ArrayList<Object> Child = new ArrayList<Object>();
			Double x = 0.0;
			for (Contract e : listDailyWorks) {
				ArrayList<Object> child = new ArrayList<Object>();
				parent.add(e.getClientName() + " No: " + e.GetCopiesNo()
						+ " Net: " + e.GetNetAmount());
				child.add(e.GetContractID());

				child.add(e.GetClientID());
				child.add(e.GetCopiesNo());

				child.add(e.getClientPhone());
				child.add(e.getClientMobile());
				child.add(e.GetAddress());
				String time = e.GetTime();
				String arra[] = time.split("T");

				child.add(arra[1].substring(0, 5));
				child.add(e.GetNetAmount());
				x = x + e.GetNetAmount();
				Child.add(child);
			}// End For
			tvSummation.setText("" + x);
			exlistDailyWork
					.setAdapter(new ExpandbleListAdapter(
							context,
							parent,
							Child,
							ExpandbleListAdapter.CurrentMode.DailyWork));
		} catch (Exception e) {
//			Log.d("Error in Daily Work Show FillList", e.getMessage());
			// CatchMsg.WriteMSG("FillList", e.getMessage());
		}// end try catch

	}// end Fill List

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// menu.add("Print");
		// menu.add("Print Select Contract");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.distdailywork, menu);
		return true;
		// return super.onCreateOptionsMenu(menu);
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.DailyWork_Print:
//			try {
//
//				if (listDailyWorks == null) {
//					GetToast.Toast(context, getString(R.string.NoData));
//					return true;
//				} else {
//					// InitPrinter();
//					mood = Mood.Dailywork;
//					new PrintTask().execute(MacAddress);
//
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//				// CatchMsg.WriteMSG("DailyWork_Print", e.getMessage());
//			}
//			break;
//		case R.id.DailyWork_Print_Select_Contract:
//			try {
//
//				if (this.ContractID > 0) {
//					mood = Mood.Contract;
//					new PrintTask().execute(MacAddress);
//
//				} else {
//					GetToast.Toast(context,
//							getString(R.string.Please_Select_Client));
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//				// CatchMsg.WriteMSG("DailyWork_Print_Select_Contract",
//				// e.getMessage());
//			}
//			break;
//		default:
//			break;
//		}
//		return true;
//	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
			if(item.getItemId() == R.id.DailyWork_Print) {
				try {

					if (listDailyWorks == null) {
						GetToast.Toast(context, getString(R.string.NoData));
						return true;
					} else {
						// InitPrinter();
						mood = Mood.Dailywork;
						new PrintTask().execute(MacAddress);

					}
				} catch (Exception e) {
					// TODO: handle exception
					// CatchMsg.WriteMSG("DailyWork_Print", e.getMessage());
				}
			}else if(item.getItemId() == R.id.DailyWork_Print_Select_Contract){
				try {

					if (this.ContractID > 0) {
						mood = Mood.Contract;
						new PrintTask().execute(MacAddress);

					} else {
						GetToast.Toast(context,
								getString(R.string.Please_Select_Client));
					}
				} catch (Exception e) {
					// TODO: handle exception
					// CatchMsg.WriteMSG("DailyWork_Print_Select_Contract",
					// e.getMessage());
				}
		}
		return true;
	}

	private void InitPrinter() {
		Intent serverIntent = new Intent(DailyWorkShow.this, DeviceList.class);
		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
	}

	@Override
	public void onBackPressed() {
		if (isConnected)
			mPrinter.closeConnection();

		this.finish();
	}

	// receive the state change of the bluetooth.
	private BroadcastReceiver mStateReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			switch (mBluetoothAdapter.getState()) {
			case BluetoothAdapter.STATE_ON:
				GetToast.Toast(context,
						getString(R.string.Bluetooth_has_opened));
				break;
			case BluetoothAdapter.STATE_OFF:
				GetToast.Toast(context,
						getString(R.string.Bluetooth_has_Closed));
				isConnected = false;
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address
				MacAddress = data.getExtras().getString(
						DeviceList.EXTRA_DEVICE_ADDRESS);
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode != Activity.RESULT_OK) {
				// User did not enable Bluetooth or an error occured
				GetToast.Toast(context, getString(R.string.Enable_To_Connected));
				finish();
			}
		}
	}

	public class PrintTask extends AsyncTask<String, Integer, String> {
		ShowProgressDialog showProgressDialog;

		@Override
		protected void onPreExecute() {
			// Shows a progress icon on the title bar to indicate
			// it is working on something.
			showProgressDialog = new ShowProgressDialog(context,
					getString(R.string.Please_Wait));

			showProgressDialog.ShowDialog();
			DailyWorkShow.this.setProgressBarIndeterminateVisibility(true);
			tvMsg.setText("");
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

				switch (mood) {
				case Contract:
					try {
						CreateAlghad();
					} catch (Exception e) {
						if (!(e.getMessage() == null)) {
							sResult = e.getMessage();
							// CatchMsg.WriteMSG("CreateAlghad",
							// e.getMessage());
						} else {
							sResult = "error Do In Back Create Result";
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
							// CatchMsg.WriteMSG("CreateSigntuare",
							// e.getMessage());
						} else {
							sResult = "error Do In Back Create Result";
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
							// CatchMsg.WriteMSG("WriteOnIMGSpace",
							// e.getMessage());
						} else
							sResult = "error Do In Back Create Result";
					}
					if (mypathHeader.exists())
						lp.writeGraphic(mypathHeader.getAbsolutePath(),
								LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
								800, 0);
					break;

				case Dailywork:
					try {
						CreateDailyWork();
					} catch (Exception e) {
						if (!(e.getMessage() == null)) {
							sResult = e.getMessage();
							// CatchMsg.WriteMSG("CreateDailyWork",
							// e.getMessage());
						} else {
							sResult = "Error Do Back Ground";
						}
					}
					if (mypathHeader.exists())
						lp.writeGraphic(mypathHeader.getAbsolutePath(),
								LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
								800, 0);
					else
						sResult = "File Not Exists";
					break;
				}

				lp.disconnect(); // Disconnects from the printer
				lp.close(); // Releases resources
			} catch (LinePrinterException ex) {
				sResult = "LinePrinterException: " + ex.getMessage();
				// CatchMsg.WriteMSG("LinePrinterException: ", ex.getMessage());
			} catch (Exception ex) {
				if (ex.getMessage() != null) {
					sResult = "Unexpected exception: " + ex.getMessage();
					// CatchMsg.WriteMSG("Unexpected exception: ",
					// ex.getMessage());
				} else {
					sResult = "Unexpected exception.";
				}
			}
			return sResult;
		}

		@Override
		protected void onPostExecute(String result) {
			showProgressDialog.EndDialog();
			if (!(mypathHeader == null))
				mypathHeader.delete();
			if (result != null) {
				GetToast.Toast(context, result);
				tvMsg.setText(result);
			}
			// Dismisses the progress icon on the title bar.
			DailyWorkShow.this.setProgressBarIndeterminateVisibility(false);
		}
	} // end of class PrintTask

	private String GetDateToday() {
		String datetoday = TxTDailyWorkDate.getText().toString();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);
		try {
			cal.setTime(sdf.parse(datetoday));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date resultdatetodayDate = new Date(cal.getTimeInMillis());

		return datetoday = sdf.format(resultdatetodayDate);
		// return null;
	}// End Date Today

	private void CreateDailyWork() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidCRM");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
		new PrintInterMec().WriteOnIMGDailywork(mFileOutStream, listDailyWorks,
				TxTDailyWorkDate.getText().toString());
	}

	private void WriteOnIMGSpace() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidCRM");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
		new PrintInterMec().WriteOnIMGSpace(mFileOutStream);
	}

	private void CreateAlghad() throws FileNotFoundException {

		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidCRM");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
		new PrintInterMec().WriteOnIMGAlghad(mFileOutStream, objContract,
				TxTDailyWorkDate.getText().toString());
	}

	private void CreateSigntuare() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidCRM");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
		byte[] te = Base64.decode(objContract.getSignImage(), 0);
		Bitmap Signtuare = BitmapFactory.decodeByteArray(te, 0, te.length);
		new PrintInterMec().WriteOnIMGSigntuare(mFileOutStream, Signtuare);
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
			tvMsg.append("Error copying asset files.");
			tvMsg.append(ex.getMessage());
			tvMsg.setTextColor(Color.RED);
			return false;
		}
	}
}// End Class
