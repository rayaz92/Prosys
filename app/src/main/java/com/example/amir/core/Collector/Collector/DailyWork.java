package com.example.amir.core.Collector.Collector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.example.amir.core.Collector.Adapter.AdapterExpandableList;
import com.example.amir.core.Collector.Classes.DailyWorkClass;
import com.example.amir.core.Collector.Classes.GNPAY;
import com.example.amir.core.Collector.Classes.GetToast;
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
import com.sprt.bluetooth.android.BluetoothPrinter;
import com.sprt.bluetooth.android.PrinterType;

import com.zj.btsdk.BluetoothService;
import com.zj.btsdk.PrintPic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

public class DailyWork extends Activity implements OnClickListener {
	EditText TxTDate;
	TextView tvTotal, TvMSG;
	Button btnSearch , collbtnPrint ,collbtnPrintSelected;
	ExpandableListView exListDailyWork;
	Context context = this;
	List<GNPAY> listGNPAY;
    List<DailyWorkClass> listDailyWorkClass;
	ArrayList<String> parent = new ArrayList<String>();
	// ArrayList<Object> listdata = new ArrayList<Object>();
	ArrayList<Object> Child = new ArrayList<Object>();
	AdapterExpandableList AdapterExlist;
	// ////////
	File myPathHeader;
	private BluetoothAdapter mBluetoothAdapter = null;

	// public static BluetoothPrinter mPrinter;
	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;

	// //
	String MacAddres = "";
	//
	BluetoothService mService = null;
	BluetoothDevice con_dev = null;

    java.util.Date dateFrom;

    public enum Mode {
        Dailywork, Contract
    }

    public Mode mode;
    int Index = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.colldailywork);

		InitialView();

//		  mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//		 if (!(mBluetoothAdapter.isEnabled())) {
//		 new GetToast(context,getString(R.string.BluetoothNotAvailable) );
//		 onBackPressed();
//		 } else
//			 mService = new BluetoothService(this, mHandler);
		  InitPrinter();
	}

	private void InitialView() {
		TxTDate = (EditText) findViewById(R.id.DailyWork_TxTDate);
		TxTDate.setOnClickListener(this);

		tvTotal = (TextView) findViewById(R.id.tvTotal_DailyWork);
		btnSearch = (Button) findViewById(R.id.BtnSearch_DaliyWork);
		btnSearch.setOnClickListener(this);
        collbtnPrint = (Button) findViewById(R.id.collbtnPrint);
        collbtnPrint.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listDailyWorkClass == null) {
                    new GetToast(context, getString(R.string.NoDataForPrinting));
                } else {
                    mode = Mode.Dailywork;
                    CopyAssetFiles();
                    new PrintTask().execute(MacAddres);
                }
            }
        });
        collbtnPrintSelected = (Button) findViewById(R.id.collbtnPrintSelected);
        collbtnPrintSelected.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listDailyWorkClass == null) {
                    new GetToast(context, getString(R.string.NoDataForPrinting));
                } else {
                    mode = Mode.Contract;
                    CopyAssetFiles();
                    new PrintTask().execute(MacAddres , String.valueOf(Index));
                }
            }
        });
		exListDailyWork = (ExpandableListView) findViewById(R.id.expandableList_DailyWork);
        exListDailyWork.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                v.setBackgroundColor(Color.GRAY);
                mode = Mode.Contract;
                Index = groupPosition;

                return true;
            }
        });
		//TvMSG = (TextView) findViewById(R.id.tvMSG_DailtWork);
	}
	protected void Dialog(final TextView _textDialog) {
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
				String Date = "" + dayOfMonth + "-" + monthPlusone + "-" + year;
				_textDialog.setText(Date);
			}
		};
		new DatePickerDialog(context, dateSetListener, Year, Month, Day).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(getString(R.string.PrintDaliyWork));
		menu.add(getString(R.string.PrintCoustmFiled));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals(getString(R.string.PrintDaliyWork))) {
			if (listGNPAY == null) {
				new GetToast(context, getString(R.string.NoDataForPrinting));
			} else {
				 CopyAssetFiles();
				 new PrintTask().execute(MacAddres);
			}
		  // InitPrinter();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.DailyWork_TxTDate:
			Dialog(TxTDate);
			break;
		case R.id.BtnSearch_DaliyWork:
			if (TxTDate.getText().toString().trim().equals("")) {
				new GetToast(context, getString(R.string.PleaseEnterDate));
			} else {
				if (AdapterExlist != null) {
					parent.clear();
					Child.clear();
					AdapterExlist.notifyDataSetChanged();

				}
                GetDate();

				new GetDailyWork().execute();
			}
			break;
		}
	}// On Click

	private void InitPrinter() {
//		if(LoginActivity.PrinterName == "P2Inches"){
//			
//			Intent serverIntent = new Intent(context,DeviceListActivity.class);
//			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
//		}
//		else{
			Intent serverIntent = new Intent(context, DeviceList.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
	//	}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras().getString(
                        DeviceList.EXTRA_DEVICE_ADDRESS);
                MacAddres = address;
            }
            break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode != Activity.RESULT_OK) {
				// User did not enable Bluetooth or an error occured
				new GetToast(context, getString(R.string.Enabletoconnected));
				finish();
			}
		}
	}// On Activity Result
    private void GetDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy" , Locale.ENGLISH);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            dateFrom = format.parse(TxTDate.getText().toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

	private class GetDailyWork extends AsyncTask<Void, Void, Void> {
		ProgressDialog progressDialog;
//		String Date = TxTDate.getText().toString();

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(context);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage(getString(R.string.Loading));
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
                DailyWorkClass objDailyWork =  new DailyWorkClass();
//				listGNPAY
                        listDailyWorkClass= objDailyWork.DaliyWork(dateFrom,
						CoreLogInActivity.SalesID);
//						LoginActivity.coll_id);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
//			if (listGNPAY == null) {
            if(listDailyWorkClass == null){
				new GetToast(context, getString(R.string.NoDataForPrinting));
			} else
				AddToExpandList();
			super.onPostExecute(result);
		}
	}// Get Daily work

	private void AddToExpandList() {
		double Total = 0.0;
		//for (GNPAY e : listGNPAY) {
        for(DailyWorkClass e : listDailyWorkClass){
			ArrayList<Object> child = new ArrayList<Object>();
			parent.add(e.getClientName());
			child.add(e.getAccn_no());
			child.add(e.getNote());
			child.add(e.getCR());
			Child.add(child);
			Total = Total + e.getCR();
		}
		tvTotal.setText("" + Total);
		AdapterExlist = new AdapterExpandableList(context, parent, this.Child,
				0);
		exListDailyWork.setAdapter(AdapterExlist);
	}

	public class PrintTask extends AsyncTask<String, Integer, String> {

		ShowProgressDialog objProgressDialog;

		@Override
		protected void onPreExecute() {
			// Shows a progress icon on the title bar to indicate
			// it is working on something.
			objProgressDialog = new ShowProgressDialog(context,
					getString(R.string.Pleasewait),
					getString(R.string.PrintingData));
			objProgressDialog.ShowDialog();
			DailyWork.this.setProgressBarIndeterminateVisibility(true);
		}

		/**
		 * This method runs on a background thread. The specified parameters are
		 * the parameters passed to the execute method by the caller of this
		 * task. This method can call publishProgress to publish updates on the
		 * UI thread.
		 */
		
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
                // A retry sequence in case the bluetooth socket is temporarily
                // not ready
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

                switch (mode) {
                    case Contract:

                        try {
                            CreateLogo();
                        } catch (Exception e) {
                            if (!(e.getMessage() == null))
                                sResult = e.getMessage();
                            else
                                sResult = "error Do In Back Create Result";
                        }
                        if (myPathHeader.exists()) {
                            lp.writeGraphic(myPathHeader.getAbsolutePath(),
                                    LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
                                    800, 0);
                        }
                        // ContractInfo

                        try {
                            CreateAlmadina(Index);
                        } catch (Exception e) {
                            if (!(e.getMessage() == null))
                                sResult = e.getMessage();
                            else
                                sResult = "error Do In Back Create Result";
                        }
                        if (myPathHeader.exists()) {
                            lp.writeGraphic(myPathHeader.getAbsolutePath(),
                                    LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
                                    800, 0);//1050
                        }

                        break;
                    case Dailywork:
                        try {
                            CreateDailyWork();
                        } catch (Exception e) {
                            if (!(e.getMessage() == null))
                                sResult = e.getMessage();
                            else
                                sResult = "Error Do Back Ground";
                        }
                        if (myPathHeader.exists())
                            lp.writeGraphic(myPathHeader.getAbsolutePath(),
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
			} catch (Exception ex) {
				if (ex.getMessage() != null) {
					sResult = "Unexpected exception: " + ex.getMessage();
				} else {
					sResult = "Unexpected exception.";
				}
			}
			return sResult;

			}
		@Override
		protected void onPostExecute(String result) {
			objProgressDialog.EndDialog();
			if (result != null) {
				new GetToast(context, result);
				//TvMSG.setText(result);
			}
			setProgressBarIndeterminateVisibility(false);
		}
	} // end of class PrintTask

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
			TvMSG.append("Error copying asset files.");
			TvMSG.append(ex.getMessage());
			TvMSG.setTextColor(Color.RED);
			return false;
		}
	}

	private void CreateDailyWork() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()	+ "/AndroidCollector");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		myPathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(myPathHeader);
        new PrintInterMec().WriteOnIMGDailywork(mFileOutStream, listDailyWorkClass,TxTDate.getText().toString());
//		new PrintInterMec().WriteOnIMGDailywork(mFileOutStream, listGNPAY,TxTDate.getText().toString());
	}

private void CreateDailyWork2() throws FileNotFoundException {
	File directory = new File(Environment.getExternalStorageDirectory()	+ "/AndroidCollector");
	if (!directory.exists())
		directory.mkdir(); // directory is created;
	String current = "Data.bmp";
	myPathHeader = new File(directory, current);
	FileOutputStream mFileOutStream = new FileOutputStream(myPathHeader);
//	new PrintInterMec().WriteOnIMGDailywork2(mFileOutStream, listGNPAY,TxTDate.getText().toString());
    new PrintInterMec().WriteOnIMGDailywork2(mFileOutStream, listDailyWorkClass,TxTDate.getText().toString());}

    private void CreateLogo() throws FileNotFoundException {
        File directory = new File(Environment.getExternalStorageDirectory()
                + "/AndroidColleter");
        if (!directory.exists())
            directory.mkdir(); // directory is created;
        String current = "Data.bmp";
        myPathHeader = new File(directory, current);
        FileOutputStream mFileOutStream = new FileOutputStream(myPathHeader);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.okazlogotrans);
        new PrintInterMec().WriteAlmadinaLogo(mFileOutStream, bitmap);
    }// Create Almadina Logo

    private void CreateAlmadina(int Index) throws FileNotFoundException {
        File directory = new File(Environment.getExternalStorageDirectory()
                + "/AndroidColleter");
        if (!directory.exists())
            directory.mkdir(); // directory is created;
        String current = "Data.bmp";
        myPathHeader = new File(directory, current);

        GNPAY objGnpay = new GNPAY();
        String SerNo = String.valueOf(listDailyWorkClass.get(Index).getSer());
        String Note = String.valueOf(listDailyWorkClass.get(Index).getNote());
        //for (int i = 0 ; i <= listDailyWorkClass.size() ; i++){
            objGnpay.setClientName(listDailyWorkClass.get(Index).getClientName());
            objGnpay.setaccn_no(listDailyWorkClass.get(Index).getAccn_no());
            objGnpay.setCR(listDailyWorkClass.get(Index).getCR());
       // }
        FileOutputStream mFileOutStream = new FileOutputStream(myPathHeader);
        new PrintInterMec().WriteOnIMGAlmadina(mFileOutStream, objGnpay,
                "", "", "" , SerNo ,Note);

    }// Create Almadina
}// DailyWork

