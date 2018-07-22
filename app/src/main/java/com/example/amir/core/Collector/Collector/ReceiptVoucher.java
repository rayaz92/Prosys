package com.example.amir.core.Collector.Collector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;


import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amir.core.Collector.Adapter.AdapterAccountInfo;
import com.example.amir.core.Collector.Adapter.AdapterListPayment;
import com.example.amir.core.Collector.Adapter.PaymentItem;
import com.example.amir.core.Collector.Classes.Bank;
import com.example.amir.core.Collector.Classes.GNPAY;
import com.example.amir.core.Collector.Classes.GetToast;
import com.example.amir.core.Collector.Classes.GnCheqe;
import com.example.amir.core.Collector.printing.DeviceList;
import com.example.amir.core.Collector.printing.DeviceListActivity;
import com.example.amir.core.Collector.printing.PrintInterMec;
import com.example.amir.core.CoreLogInActivity;
import com.example.amir.core.R;

import com.honeywell.mobility.print.LinePrinter;
import com.honeywell.mobility.print.LinePrinterException;
import com.honeywell.mobility.print.PrintProgressEvent;
import com.honeywell.mobility.print.PrintProgressListener;

//import com.intermec.print.lp.LinePrinter;
//import com.intermec.print.lp.LinePrinterException;
//import com.intermec.print.lp.PrintProgressEvent;
//import com.intermec.print.lp.PrintProgressListener;

import com.zj.btsdk.BluetoothService;
import com.zj.btsdk.PrintPic;
public class ReceiptVoucher extends Activity implements OnClickListener,
		OnCheckedChangeListener {
	Context context = this;
	TextView tvDueDate, tvChequqNo, tvChequqAmount, tvBank, tvVoucherNo,
			tvTotalPayment;
	EditText TxTDate, TxTDueDate, TxTTotalAmount, TxTNote, TxTChequeNo,
			TxTClientName, TxTChequeAmount, TxTRefNo;
	CheckBox checkBoxCheque;
	Button BtnSave, BtnCancel, BtnAddPayment;
	Spinner SpinBank;
	ListView listViewPayment;
	
	File mypathHeader; // ScrollView
	ShowProgressDialog objShowProgressDialog;
	List<Bank> listBanks;
	ArrayAdapter<Bank> AdapterBank;
	String serNo,TY;// Get this number from DB
	String accn_no;
	AdapterListPayment adapterListPayment;
	ArrayList<PaymentItem> ArraylistPayment;
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;
	String MacAddress;
	 //GNPAY obgnpay = new GNPAY();
	 GNPAY objGnpay = new GNPAY();
	
	ArrayList<GNPAY> arrayListGnpay;
	AdapterAccountInfo adapterAccountInfo;
	
	BluetoothService mService = null;
	BluetoothDevice con_dev = null;

	int NewSerNo = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collreceiptvoucher);
		InitialView();
//		FillSpinner();
        new FillBank().execute();
		InitialValue();
		InitPrinter();
		CopyAssetFiles();
		mService = new BluetoothService(this, mHandler);

	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mService != null) 
			mService.stop();
		mService = null; 
	}
	private void InitPrinter() {
	
		if(LoginActivity.PrinterName == "P2Inches"){
			Intent serverIntenti = new Intent(ReceiptVoucher.this,DeviceListActivity.class);
			startActivityForResult(serverIntenti,REQUEST_CONNECT_DEVICE);
		}
		else
		{	Intent serverIntent = new Intent(context, DeviceList.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
		}
		}
	
	   private final  Handler mHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	            case BluetoothService.MESSAGE_STATE_CHANGE:
	                switch (msg.arg1) {
	                case BluetoothService.STATE_CONNECTED:   //زرء¬½س
	                	Toast.makeText(getApplicationContext(), "Connect successful",
	                            Toast.LENGTH_SHORT).show();
	       
	                    break;
	                case BluetoothService.STATE_CONNECTING:  //ص‎شعء¬½س
	                	Log.d("ہ¶رہµ÷تش","ص‎شعء¬½س.....");
	                    break;
	                case BluetoothService.STATE_LISTEN:     //¼àج‎ء¬½سµؤµ½ہ´
	                case BluetoothService.STATE_NONE:
	                	Log.d("ہ¶رہµ÷تش","µب´‎ء¬½س.....");
	                    break;
	                }
	                break;
	            case BluetoothService.MESSAGE_CONNECTION_LOST:    //ہ¶رہزر¶د؟ھء¬½س
	                Toast.makeText(getApplicationContext(), "Device connection was lost",
	                               Toast.LENGTH_SHORT).show();
	   
	                break;
	            case BluetoothService.MESSAGE_UNABLE_CONNECT:     //خق·¨ء¬½سةè±¸
	            	Toast.makeText(getApplicationContext(), "Unable to connect device",
	                        Toast.LENGTH_SHORT).show();
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
				if(LoginActivity.PrinterName == "P2Inches")
				{
				    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);  //»ٌب،ءذ±يدîضذةè±¸µؤmacµطض·
				    	con_dev = mService.getDevByMac(address);   
				    	mService.connect(con_dev);
				}
			}

			break;

		}

	}

	private void FillSpinner() {

		Bank objBank = new Bank();

		listBanks = objBank.getBankName();

		AdapterBank = new ArrayAdapter<Bank>(this,
				android.R.layout.simple_spinner_item, listBanks);
		AdapterBank
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		SpinBank.setAdapter(AdapterBank);
	}

	private void InitialView() {
		SpinBank = (Spinner) findViewById(R.id.Receipt_SpinBank);
		checkBoxCheque = (CheckBox) findViewById(R.id.Receipt_checkBoxCheque);
		checkBoxCheque.setOnCheckedChangeListener(this);
		tvDueDate = (TextView) findViewById(R.id.tvDueDate_Receipt);
		tvVoucherNo = (TextView) findViewById(R.id.tvVoucherNo_RV);
		tvTotalPayment = (TextView) findViewById(R.id.tvTotalAmountPayment_ReceiptVoucher);

		tvChequqNo = (TextView) findViewById(R.id.tvChequeNo_Receipt);
		tvChequqAmount = (TextView) findViewById(R.id.tvCheuqeAmount_Receipt);
		tvBank = (TextView) findViewById(R.id.tvBank_Recepit);
		TxTChequeNo = (EditText) findViewById(R.id.Receipt_ChequeNo);
		TxTChequeAmount = (EditText) findViewById(R.id.Receipt_CheuqeAmount);
		TxTDate = (EditText) findViewById(R.id.Receipt_Date);
		TxTDueDate = (EditText) findViewById(R.id.Receipt_DueDate);
		TxTDueDate.setOnClickListener(this);
		TxTNote = (EditText) findViewById(R.id.Receipt_Note);
		TxTTotalAmount = (EditText) findViewById(R.id.Receipt_TotalAmount);
		TxTClientName = (EditText) findViewById(R.id.Receip_ClientName);
		TxTRefNo = (EditText) findViewById(R.id.Receipt_RefNo);
		BtnCancel = (Button) findViewById(R.id.Receipt_BtnCancel);
		BtnCancel.setOnClickListener(this);
		BtnSave = (Button) findViewById(R.id.Receipt_BtnSave);
		BtnSave.setOnClickListener(this);
		BtnAddPayment = (Button) findViewById(R.id.BtnAddPayment_ReceiptVoucher);
		BtnAddPayment.setOnClickListener(this);

		listViewPayment = (ListView) findViewById(R.id.listPayment_ReceiptVoucher);
		registerForContextMenu(listViewPayment);
		listViewPayment
				.setOnItemLongClickListener(new OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						TxTChequeAmount.setText(""
								+ adapterListPayment.getItem(position)
										.getCheuqeAmount());
						TxTDueDate.setText(adapterListPayment.getItem(position)
								.getDueDate());
						TxTChequeNo.setText(""
								+ adapterListPayment.getItem(position)
										.getChequeNo());

						SpinBank.setSelection(GetPositionBankNo(adapterListPayment
								.getItem(position).getBankNo()));
						return false;
					}
				});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.add(getString(R.string.Delete));
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		if (item.getTitle().equals(getString(R.string.Delete))) {
			PaymentItem paymentItem = adapterListPayment.getItem(info.position);
			tvTotalPayment
					.setText(""
							+ ((Double.parseDouble(tvTotalPayment.getText()
									.toString())) - paymentItem
									.getCheuqeAmount()));
			adapterListPayment.remove(paymentItem);
			adapterListPayment.notifyDataSetChanged();
		}// Delete

		return super.onContextItemSelected(item);
	}

	private int GetPositionBankNo(int bankNo) {
		int i = 0;
		try {
			for (int x = 0; x < AdapterBank.getCount(); x++) {
				Bank bank = (Bank) SpinBank.getItemAtPosition(i);
				if (bank.getNo() == bankNo)
					return i;
				else
					i = i + 1;
			}
			return i;
		} catch (Exception e) {
			return 0;

		}

	}

	private void InitialValue() {
		TxTDate.setText(GetDateToday());
		TxTChequeAmount.setText("" + 0.0);
		Intent i = getIntent();
		TxTClientName.setText(i.getStringExtra("AccountName"));
		accn_no = i.getStringExtra("accn_no");
		serNo = ("" + i.getStringExtra("ser"));
        TY = ("" + i.getStringExtra("TY"));
//		tvVoucherNo.setText(i.getStringExtra("amir"));

//		NewSerNo = objGnpay.GetSerNo();
//sdasd
//		tvVoucherNo.setText(String.valueOf(NewSerNo));
        new GetSerNo().execute();
	
		statusControl(true);
		ArraylistPayment = new ArrayList<PaymentItem>();
		adapterListPayment = new AdapterListPayment(context, 0,
				ArraylistPayment);
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
				String DateAfter = "" + dayOfMonth + "-" + monthPlusone + "-"
						+ year;
				TxTDate.setText(DateAfter);
			}
		};
		new DatePickerDialog(context, dateSetListener, Year, Month, Day).show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Receipt_BtnCancel:
			this.finish();
			break;
		case R.id.Receipt_BtnSave:
			if (IsValidSave()) {
				SaveDialog();
			}
			break;
		case R.id.Receipt_DueDate:
			Dialog(TxTDueDate);
			break;
		case R.id.BtnAddPayment_ReceiptVoucher:
			AddPayment();
			break;
		}
	}

	private void AddPayment() {
		try {
			int BankNo = AdapterBank
					.getItem(SpinBank.getSelectedItemPosition()).getNo();

			String DueDate = TxTDueDate.getText().toString();
			String ChequeNo = TxTChequeNo.getText().toString();
			String TransType = "";
			double ChequeAmount = Double.valueOf(TxTChequeAmount.getText()
					.toString());

			if (AdapterBank.getItemId(SpinBank.getSelectedItemPosition()) == 0) {
				TransType = "Cash";
			} else {
				TransType = "Cheque";
				if (!IsValidDate(TxTDueDate.getText().toString(), TxTDate
						.getText().toString())) {
					new GetToast(context, getString(R.string.InValidDate));
					return;
				}
				if (DueDate.trim().length() == 0
						|| ChequeNo.trim().length() == 0) {
					new GetToast(context,
							getString(R.string.PleaseComChequeInfo));
					return;
				}
			}
			if (ChequeAmount == 0.0) {
				new GetToast(context,
						getString(R.string.ChequeAmountIsNotValid));
				return;
			}
			adapterListPayment.add(new PaymentItem(BankNo, DueDate, ChequeNo,
					ChequeAmount, TransType));
			adapterListPayment = new AdapterListPayment(context, 0,
					ArraylistPayment);
			listViewPayment.setAdapter(adapterListPayment);
			adapterListPayment.notifyDataSetChanged();
			Utility.setListViewHeightBasedOnChildren(listViewPayment);
			tvTotalPayment
					.setText(""
							+ (Double.valueOf(tvTotalPayment.getText()
									.toString()) + Double
									.valueOf(TxTChequeAmount.getText()
											.toString())));

			TxTChequeAmount.setText("");
			TxTDueDate.setText("");
			TxTChequeNo.setText("");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void SaveDialog() {

		AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
		builder1.setMessage(getString(R.string.AreYouSure));
		builder1.setCancelable(true);
		builder1.setPositiveButton(getString(R.string.Yes),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						new SaveRV().execute();
						dialog.cancel();
					}
				});
		builder1.setNegativeButton(getString(R.string.No),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder1.create();
		alert.show();
	}

	@SuppressLint("SimpleDateFormat")
	private String GetDateToday() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);//("dd/MM/yyyy");
		return sdf.format(cal.getTime());
		// return null;
	}// End Date Today

	@SuppressLint("SimpleDateFormat")
	private boolean IsValidDate(String DateAfter, String Datetoday) {

		String strafter = DateAfter;
		String strtoday = Datetoday;
		boolean result = false;

		try {
			SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd",
				Locale.ENGLISH);//("dd/MM/yyyy");
			Calendar C = Calendar.getInstance();
			C.setTime(dfDate.parse(strafter));

			Calendar C1 = Calendar.getInstance();
			C1.setTime(dfDate.parse(strtoday));

			Date dateafter = new Date(C.getTimeInMillis());
			Date datetoday = new Date(C1.getTimeInMillis());
			if (dateafter.compareTo(datetoday) >= 0) {
				result = true;
			}// End If
			else if (datetoday.after(dateafter))
				result = false;
		} catch (Exception e) {
			Log.d("Error in valide ", e.getMessage());
		}
		return result;
	}

	private void statusControl(Boolean isChecked) {
		TxTChequeNo.setEnabled(isChecked);
		TxTDueDate.setEnabled(isChecked);
		TxTChequeAmount.setEnabled(isChecked);
		SpinBank.setEnabled(isChecked);
		tvDueDate.setEnabled(isChecked);
		tvChequqNo.setEnabled(isChecked);
		tvChequqAmount.setEnabled(isChecked);
		tvBank.setEnabled(isChecked);
		BtnAddPayment.setEnabled(isChecked);
	}

	private Boolean IsValidSave() {
		if (TxTTotalAmount.getText().toString().trim().equals("")) {
			new GetToast(context, getString(R.string.PleaseInsertAmount));
			return false;
		}
		double x, y;
		x = Double.parseDouble(tvTotalPayment.getText().toString());
		y = Double.parseDouble(TxTTotalAmount.getText().toString());
		if (x != y) {
			new GetToast(context,
					getString(R.string.TotalAmountDoesntEqualRequiredValue));
			return false;
		}
		return true;
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
		if (TxTTotalAmount.getText().toString().trim().length() == 0) {
			new GetToast(context, getString(R.string.PleaseInsertTotalAmount));
			TxTTotalAmount.setError(getString(R.string.ThisAmountIsNotValid));
			checkBoxCheque.setChecked(!isChecked);
			return;
		} else
			TxTTotalAmount.setError(null);
		statusControl(isChecked);
	}

	public class PrintTask extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {

			objShowProgressDialog = new ShowProgressDialog(context,
					getString(R.string.Please_Wait),
					getString(R.string.Printing_receipt_voucher));
			objShowProgressDialog.ShowDialog();
//			ReceiptVoucher.this.setProgressBarIndeterminateVisibility(true);
			// tvMSG.setText("");
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

			if(LoginActivity.PrinterName == "P2Inches")
			{
				try {
					CreateAlmadinaLogo2();
					} catch (Exception e) {
						if (!(e.getMessage() == null))
							sResult = e.getMessage();
						else
							sResult = "error Do In Back Create Result";
					}

					if (mypathHeader.exists()) {
						byte[] sendData = null;
						PrintPic pp = new PrintPic();
						pp.initCanvas(384);  
						pp.initPaint();
						pp.drawImage(0, 0, mypathHeader.getAbsolutePath());
						sendData = pp.printDraw();
				    	mService.write(sendData);  
					}
					
					try {
						CreateAlmadina2();
//						CreateAlmadina();
					} catch (Exception e) {
						if (!(e.getMessage() == null))
							sResult = e.getMessage();
						else
							sResult = "error Do In Back Create Result";

					}
					if (mypathHeader.exists()) {
						byte[] sendData = null;
						PrintPic pp = new PrintPic();
						pp.initCanvas(384);  
						pp.initPaint();
						pp.drawImage(0, 0, mypathHeader.getAbsolutePath());
						sendData = pp.printDraw();
				    	mService.write(sendData); 
					}
					return sResult;
				
			}
			else
			{
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
					}
				}
				if (numtries == maxretry)
					lp.connect();// Final retry
				// print data
				try {
					CreateAlmadinaLogo();
				} catch (Exception e) {
					if (!(e.getMessage() == null))
						sResult = e.getMessage();
					else
						sResult = "error Do In Back Create Result";
				}
				if (mypathHeader.exists()) {
					lp.writeGraphic(mypathHeader.getAbsolutePath(),
							LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
							800, 0);
				}
				// ContractInfo
				try {
					CreateAlmadina();
				} catch (Exception e) {
					if (!(e.getMessage() == null))
						sResult = e.getMessage();
					else
						sResult = "error Do In Back Create Result";
				}
				if (mypathHeader.exists()) {
					lp.writeGraphic(mypathHeader.getAbsolutePath(),
							LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
							800, 0);//1050
				}

				lp.disconnect(); // Disconnects from the printer
				lp.close(); // Releases resources
			
			} catch (LinePrinterException ex) {
				sResult = "LinePrinterException: " + ex.getMessage();
			} catch (Exception ex) {
				if (ex.getMessage() != null) {
					sResult = "Unexpected exception: " + ex.getMessage();
				} else {
					sResult = "Unexpected exception.";
				}
			}
			return sResult;
		}
	}
		

		@Override
		protected void onPostExecute(String result) {
			objShowProgressDialog.EndDialog();
//			ReceiptVoucher.this.setProgressBarIndeterminateVisibility(false);
			if (!(mypathHeader == null))
				mypathHeader.delete();

			if (result != null) {
				new GetToast(context, result);
				// tvMSG.setText(result);
			}
			setResult(1);
			ReceiptVoucher.this.finish();
		}
	} // end of class PrintTask

	private void CreateAlmadinaLogo() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidColleter");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.alyaumlogo);
		new PrintInterMec().WriteAlmadinaLogo(mFileOutStream, bitmap);
	}// Create Almadina Logo
	
	private void CreateAlmadinaLogo2() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidColleter");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.alyaumlogo);
		new PrintInterMec().WriteAlmadinaLogo2(mFileOutStream, bitmap);
	}// Create Almadina Logo
	
	

	private void CreateAlmadina() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidColleter");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
		new PrintInterMec().WriteOnIMGAlmadina(mFileOutStream, objGnpay,
				GetDateToday(), SpinBank.getSelectedItem().toString(),
				TxTChequeNo.getText().toString() , String.valueOf(NewSerNo).toString(),
                String.valueOf(TxTNote.getText().toString()));

	}// Create Almadina

	private void CreateAlmadina2() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidColleter");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
		new PrintInterMec().WriteOnIMGAlmadina(mFileOutStream, objGnpay,
				GetDateToday(), SpinBank.getSelectedItem().toString(),
				TxTChequeNo.getText().toString(),String.valueOf(NewSerNo).toString(),
                String.valueOf(TxTNote.getText().toString()));

	}// Create Almadina2


	@SuppressLint("NewApi")
	private Boolean CopyAssetFiles() {
		// Copy the asset files we delivered with the application to a location
		// where the LinePrinter can access them.
		boolean result = false;
		try {
			AssetManager assetManager = getAssets();
			String[] files = { "printer_profiles.JSON" };
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
			new GetToast(context, ex.getMessage());
			// tvMSG.setTextColor(Color.RED);
			return false;
		}
	}

	private class SaveRV extends AsyncTask<Void, Void, Integer> {
		ShowProgressDialog objProgressDialog;
		//GNPAY objGnpay;
		String ClientName = TxTClientName.getText()
				.toString();

		@Override
		protected void onPreExecute() {
			objProgressDialog = new ShowProgressDialog(context,
					getString(R.string.Loading));

			objProgressDialog.ShowDialog();

			objGnpay = new GNPAY();
			objGnpay.setCR(Double.valueOf(TxTTotalAmount.getText().toString()));
			objGnpay.setNote(TxTNote.getText().toString());
			objGnpay.setREF_NO((TxTRefNo.getText().toString()));
			objGnpay.setser(Integer.valueOf(serNo.toString()));//NewSerNo

			objGnpay.setClientName(ClientName.toString().trim());
			objGnpay.setaccn_no(accn_no.toString());
			objGnpay.setTY(TY);

//			objGnpay.setser(Integer.valueOf(tvVoucherNo.getText().toString()));
		}

		@Override
		protected Integer doInBackground(Void... params) {

			int Serial = new GNPAY().InsertGNPAY(objGnpay,
					CoreLogInActivity.UserID, accn_no, ClientName ,
                    CoreLogInActivity.UserAccountNo,CoreLogInActivity.UserAccountName ,
                    Main.DeviceID);
//					LoginActivity.coll_id, accn_no, ClientName , Main.DeviceID);
			if (Serial > 0) {
				for (int i = 0; i < adapterListPayment.getCount(); i++) {
					PaymentItem paymentItem = adapterListPayment.getItem(i);
					if (new GnCheqe().InsertGnCheqe(objGnpay, Serial,
							paymentItem, CoreLogInActivity.UserID,CoreLogInActivity.UserID,
							CoreLogInActivity.UserAccountNo,0) > 0) {
//							paymentItem, LoginActivity.coll_id,
//							LoginActivity.coll_id) > 0) {
						continue;
					} else
						return 0;
				}
			} else
				return 0;
			return Serial;
		}
		@Override
		protected void onPostExecute(Integer result) {
			objProgressDialog.EndDialog();
			if (result > 0) {
				new GetToast(context, getString(R.string.DoneInsert));
				LoginActivity.PrinterName = "P3Inches";
				//ReceiptVoucher.this.finish();
				if(LoginActivity.PrinterName=="P2Inches"){
					new PrintTask().execute(con_dev.toString());
				}
				else{
					new PrintTask().execute(MacAddress);
				}
			} else
				new GetToast(context, getString(R.string.ErrorInInsert));
		}
	}

	public static class Utility {
		public static void setListViewHeightBasedOnChildren(ListView listView) {
			ListAdapter listAdapter = listView.getAdapter();
			if (listAdapter == null) {
				return;
			}

			int totalHeight = 0;
			for (int i = 0; i < listAdapter.getCount(); i++) {
				View listItem = listAdapter.getView(i, null, listView);
				listItem.measure(0, 0);
				totalHeight += listItem.getMeasuredHeight();
			}

			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight
					+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
			listView.setLayoutParams(params);
		}
	}

    class FillBank extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;


        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(ReceiptVoucher.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait));
            objProgressDialog.ShowDialog();


        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {

                Bank objBank = new Bank();
                listBanks = objBank.getBankName();


            } catch (Exception e) {
                Log.d("Do In Back ", e.getMessage());
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if (listBanks != null) {
                AdapterBank = new ArrayAdapter<Bank>(ReceiptVoucher.this,
                        android.R.layout.simple_spinner_item, listBanks);

                AdapterBank
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                SpinBank.setAdapter(AdapterBank);
             }
        }
    }


    class GetSerNo extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;


        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(ReceiptVoucher.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait));
            objProgressDialog.ShowDialog();


        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {
                NewSerNo = objGnpay.GetSerNo();
            } catch (Exception e) {
                Log.d("Do In Back ", e.getMessage());
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();

            tvVoucherNo.setText(String.valueOf(NewSerNo));
        }
    }

}// ReceiptVoucher
