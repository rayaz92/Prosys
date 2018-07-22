package com.example.amir.core.ADR.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;


import com.example.amir.core.ADR.activity.Classes.CatchMsg;
import com.example.amir.core.ADR.activity.Classes.Contract;
import com.example.amir.core.ADR.activity.Classes.ContractPublishDetails;
import com.example.amir.core.ADR.activity.printing.DeviceList;
import com.example.amir.core.ADR.activity.printing.PrintInterMec;
import com.example.amir.core.R;
import com.honeywell.mobility.print.LinePrinter;
import com.honeywell.mobility.print.LinePrinterException;
import com.honeywell.mobility.print.PrintProgressEvent;
import com.honeywell.mobility.print.PrintProgressListener;
//import com.intermec.print.lp.LinePrinter;
//import com.intermec.print.lp.LinePrinterException;
//import com.intermec.print.lp.PrintProgressEvent;
//import com.intermec.print.lp.PrintProgressListener;

@SuppressLint({ "NewApi", "SimpleDateFormat" })
public class DailyWork extends Activity {
	EditText TxTDate;
	TextView TvMSG, TvTotal;
	ExpandableListView expandableListView;
	List<Contract> listDataContract;
	int ContractID = 0, SerialNo = 0;
	String ContractType = "";;
	List<ContractPublishDetails> listContractPublicDetails;
	// Contract objcontract = new Contract();
	ExpandableListAdapter exAdapter;
	Context context = DailyWork.this;
	ArrayList<String> parent = new ArrayList<String>();
	ArrayList<Object> listdata = new ArrayList<Object>();
	ArrayList<Object> child;
	Contract ObjContract = new Contract();
	Boolean isConnected = false;
	//
	File mypathHeader;

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;

	// /For Intermec
	String MacAddress;

    Button adrbtnDailyWorkPrint , adrbtnDailyWorkPrintSelected ;
	public enum Mode {
		Dailywork, Contract
	}

	public Mode mode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.adrdailywork);

		InitialView();
		InitPrinter();
		CopyAssetFiles();
	}// End OnCreate

	private void InitialView() {
		TvMSG = (TextView) findViewById(R.id.tvMSG_DailyWork);
		TvTotal = (TextView) findViewById(R.id.tvDailyWork_Total);
		TxTDate = (EditText) findViewById(R.id.DailyWork_TxTDate);
		TxTDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog(TxTDate);
			}
		});

        adrbtnDailyWorkPrint = (Button) findViewById(R.id.adrbtnDailyWorkPrint);
        adrbtnDailyWorkPrintSelected = (Button) findViewById(R.id.adrbtnDailyWorkPrintSelected);

        adrbtnDailyWorkPrint.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parent == null) {
                    GetToast.Toast(context, getString(R.string.PleaseCheckofDate));
                } else {
                    mode = Mode.Dailywork;
                    // CopyAssetFiles();
                    new PrintTask().execute(MacAddress);
                }
            }
        });

        adrbtnDailyWorkPrintSelected.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContractID > 0) {
                    mode = Mode.Contract;
//                    listContractPublicDetails = new ContractPublishDetails()
//                            .GetContractPublishDetailsByContractID(ContractID);
//                    ObjContract = new Contract();
//                    ObjContract = new Contract()
//                            .GetContractInfoByContractID(ContractID);
//                    if (listContractPublicDetails == null) {
//                        GetToast.Toast(context, getString(R.string.NoData));
//                        return;
                    new GetContractPublishDetailsByContractID().execute();
                } else {
                    GetToast.Toast(context, getString(R.string.PleaseSelectOne));
                }
            }
        });

		// Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
		// TxTDate.setText(""+calendar.getTime().toLocaleString());
		expandableListView = (ExpandableListView) findViewById(R.id.expandableListView1);
		expandableListView.setOnChildClickListener(new OnChildClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				v.setBackgroundColor(Color.GRAY);

				ContractID = (Integer) ((ArrayList) listdata.get(groupPosition))
						.get(0);
				SerialNo = (Integer) ((ArrayList) listdata.get(groupPosition))
						.get(1);
				ContractType = (String) ((ArrayList) listdata
						.get(groupPosition)).get(2);

                adrbtnDailyWorkPrintSelected.setEnabled(true);

				return true;
			}
		});
	}

	private void GetData() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));

		java.util.Date dateFrom;
		try {
			dateFrom = format.parse(TxTDate.getText().toString());

			new GetDaliyWork().execute(dateFrom);
//			listDataContract = ObjContract.DaliyWork(dateFrom);

		} catch (ParseException e) {
			CatchMsg.WriteMSG("DailyWork GetDate ", e.getMessage());
			e.printStackTrace();
		}
//		if (listDataContract == null) {
//			if (parent.size() == 0)
//				GetToast.Toast(context, getString(R.string.NoData));
//			else {
//				parent.clear();
//				listdata.clear();
//				TvTotal.setText("");
//				exAdapter.notifyDataSetChanged();
//			}
//			return;
//		} else
//			FillList();
	}

	private void FillList() {
		try {
			double Total = 0.0;
			for (Contract e : listDataContract) {
				parent.add(e.GetClientName());
				child = new ArrayList<Object>();
				child.add(e.GetID());
				child.add(e.GetSerialNo());
				child.add(e.GetPaymentTypeName());
				String array[] = e.GetContractDate().split("T");
				child.add(array[0]);
				child.add(e.GetNetAmount());
				listdata.add(child);
				Total = Total + e.GetNetAmount();
			}// End For
			exAdapter = new ExpandableListAdapter(DailyWork.this, parent,
					listdata);
			expandableListView.setAdapter(exAdapter);
			exAdapter.notifyDataSetChanged();

			TvTotal.setText("" + Total);
		} catch (Exception e) {
//			Log.d("Error in Daily Work Show FillList", e.getMessage());
		}// end try catch
	}// end Fill List

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
				String Date = "" + dayOfMonth + "/" + monthPlusone + "/" + year;
				_textDialog.setText(Date);
				GetData();
			}
		};
		new DatePickerDialog(DailyWork.this, dateSetListener, Year, Month, Day)
				.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// menu.add("Print DailyWork");
		// menu.add("Print Selected Contract");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.adrdailywork, menu);
		// return super.onCreateOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.DailyWork_Print_DailyWork:
			if (parent == null) {
				GetToast.Toast(context, "Plese Select Date ..");
			} else {
				mode = Mode.Dailywork;
				// CopyAssetFiles();
				new PrintTask().execute(MacAddress);
			}
			break;
		case R.id.DailyWork_Print_Selected_Contract:
			if (ContractID > 0) {
				mode = Mode.Contract;
				listContractPublicDetails = new ContractPublishDetails()
						.GetContractPublishDetailsByContractID(ContractID);
				ObjContract = new Contract();
				ObjContract = new Contract()
						.GetContractInfoByContractID(ContractID);
				if (listContractPublicDetails == null) {
					GetToast.Toast(context, getString(R.string.NoData));
					return true;
				}
			} else {
				GetToast.Toast(context, "Please Select Contract...");
			}
			new PrintTask().execute(MacAddress);

			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressLint("SimpleDateFormat")
	private String DateNow() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(cal.getTime());
	}

	private void InitPrinter() {
		Intent serverIntent = new Intent(context, DeviceList.class);
		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
	}

	@Override
	public void onBackPressed() {
		this.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address
				String address = data.getExtras().getString(
						DeviceList.EXTRA_DEVICE_ADDRESS);
				MacAddress = address;
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode != Activity.RESULT_OK) {
				// User did not enable Bluetooth or an error occured
				GetToast.Toast(context, getString(R.string.EnabletoConnected));
				finish();
			}
		}
	}// On Activity Result

	public class PrintTask extends AsyncTask<String, Integer, String> {
		ProgressDialog progressDialog;

		@Override
		protected void onPreExecute() {
			// Shows a progress icon on the title bar to indicate
			// it is working on something.
			progressDialog = new ProgressDialog(context);
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setMessage(getString(R.string.Wait));
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.show();
			DailyWork.this.setProgressBarIndeterminateVisibility(true);
			TvMSG.setText("");
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
					}
				}
				if (numtries == maxretry)
					lp.connect();// Final retry
				switch (mode) {
				case Contract:
					try {
						CreateAlghad();
					} catch (Exception e) {
						if (!(e.getMessage() == null))
							sResult = e.getMessage();
						else
							sResult = "error Do In Back Create Result";
					}
					if (mypathHeader.exists()) {
						lp.writeGraphic(mypathHeader.getAbsolutePath(),
								LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
								900, 0);
					}
					try {
						CreateHeader();
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
					try {
						CreateContract();
					} catch (Exception e) {
						// e.printStackTrace();
						if (!(e.getMessage() == null))
							sResult = e.getMessage();
						else
							sResult = "error Do In Back Create Result";
					}
					if (mypathHeader.exists())
						lp.writeGraphic(mypathHeader.getAbsolutePath(),
								LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
								800, 0);
					else
						sResult = "File Not Exists";
					try {
						CreateResult();
					} catch (Exception e) {
						if (!(e.getMessage() == null))
							sResult = e.getMessage();
						else
							sResult = "error Do In Back Create Result";
					}
					if (mypathHeader.exists())
						lp.writeGraphic(mypathHeader.getAbsolutePath(),
								LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
								800, 0);
					else
						sResult = "File Not Exists";
//					try {
//						CreateSigntuare();
//					} catch (Exception e) {
//						if (!(e.getMessage() == null))
//							sResult = e.getMessage();
//						else
//							sResult = "error Do In Back Create Result";
//					}
//					if (mypathHeader.exists())
//						lp.writeGraphic(mypathHeader.getAbsolutePath(),
//								LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
//								800, 0);
                    try {
                        CreateTerms();
                    } catch (Exception e) {
                        if (!(e.getMessage() == null))
                            sResult = e.getMessage();
                        else
                            sResult = "error Do In Back Create Result";
                    }
                    if (mypathHeader.exists()) {
                        lp.writeGraphic(mypathHeader.getAbsolutePath(),
                                LinePrinter.GraphicRotationDegrees.DEGREE_0, 0,
                                700, 0);
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
			progressDialog.dismiss();
			if (!(mypathHeader == null))
				mypathHeader.delete();
			if (result != null) {
				GetToast.Toast(context, result);
				TvMSG.setText(result);
			}
			// Dismisses the progress icon on the title bar.
			DailyWork.this.setProgressBarIndeterminateVisibility(false);
		}
	} // end of class PrintTask

	@SuppressWarnings("unused")
	private void CreateHeader() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidADR1");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);

		new PrintInterMec()
				.WriteHeaderContract(mFileOutStream, TxTDate.getText()
						.toString(),  SerialNo, ObjContract, ContractType);
	}

	private void CreateContract() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidADR1");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
		new PrintInterMec().WriteOnIMGContractPublich(mFileOutStream, 0,
				listContractPublicDetails);
	}

    private void CreateTerms() throws FileNotFoundException {

        Bitmap bMap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.alyaumtermf);
        File directory = new File(Environment.getExternalStorageDirectory()
                + "/AndroidADR");
        if (!directory.exists())
            directory.mkdir(); // directory is created;
        String current = "Data.bmp";
        mypathHeader = new File(directory, current);
        FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
        //new PrintInterMec().WriteOnIMGAlghad(mFileOutStream, bMap);
        Bitmap bitmapCreated = Bitmap.createBitmap(1200, 700,
                Bitmap.Config.RGB_565);
        Canvas canvas;
        canvas = new Canvas(bitmapCreated);
        canvas.drawRGB(255, 255, 255);
        canvas.drawBitmap(bMap, 10, 0, null);
        bitmapCreated.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
        try {
            mFileOutStream.flush();
            mFileOutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private void CreateResult() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidADR1");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
		new PrintInterMec().WriteOnIMGResultContract(mFileOutStream,
				ObjContract);

	}

	private void CreateSigntuare() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidADR1");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
		byte[] te = Base64.decode(ObjContract.GetImage(), 0);
		Bitmap Signtuare = BitmapFactory.decodeByteArray(te, 0, te.length);
		new PrintInterMec().WriteOnIMGSigntuare(mFileOutStream, Signtuare);
	}

	private void CreateAlghad() throws FileNotFoundException {
		Bitmap bMap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.alyaumlogo);
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidADR");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
		new PrintInterMec().WriteOnIMGAlghad(mFileOutStream, bMap);
	}

	private void CreateDailyWork() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidADR");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
		new PrintInterMec().WriteOnIMGDailywork(mFileOutStream,
				listDataContract, TxTDate.getText().toString());
	}

	@SuppressLint("NewApi")
	private Boolean CopyAssetFiles() {
		// Copy the asset files we delivered with the application to a location
		// where the LinePrinter can access them.
		boolean result = false;
		try {
			AssetManager assetManager = getAssets();
			String[] files = { "printer_profiles.JSON", "Signature.bmp" };
			int i = 0;
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
			i++;
			return result;
		} catch (Exception ex) {
			TvMSG.append("Error copying asset files.");
			TvMSG.append(ex.getMessage());
			TvMSG.setTextColor(Color.RED);
			return false;
		}
	}


	class GetDaliyWork extends AsyncTask<Date, String, String> {
		ShowProgressDialog objProgressDialog;



		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			objProgressDialog = new ShowProgressDialog(DailyWork.this,
					getString(R.string.Please_Wait), getString(R.string.Wait),
					R.drawable.login_icon1);
			objProgressDialog.ShowDialog();

		}

		@Override
		protected String doInBackground(Date... params) {
			String x = "";
			try {
				ObjContract = new Contract();
				listDataContract = ObjContract.DaliyWork(params[0]);

			} catch (Exception e) {
				Log.d("Do In Back ", e.getMessage());
			}
			return x;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			objProgressDialog.EndDialog();
			if (listDataContract == null) {
				if (parent.size() == 0)
					GetToast.Toast(context, getString(R.string.NoData));
				else {
					parent.clear();
					listdata.clear();
					TvTotal.setText("");
					exAdapter.notifyDataSetChanged();
				}
				return;
			} else
				FillList();
		}
	}


    class GetContractPublishDetailsByContractID extends AsyncTask<Date, String, String> {
        ShowProgressDialog objProgressDialog;



        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(DailyWork.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),
                    R.drawable.login_icon1);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(Date... params) {
            String x = "";
            try {
                listContractPublicDetails = new ContractPublishDetails()
                        .GetContractPublishDetailsByContractID(ContractID);

                ObjContract = new Contract();
                ObjContract = new Contract()
                        .GetContractInfoByContractID(ContractID);

            } catch (Exception e) {
                Log.d("Do In Back ", e.getMessage());
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if (listContractPublicDetails == null) {
               GetToast.Toast(DailyWork.this ,getString(R.string.NoData));
            }else{

                new PrintTask().execute(MacAddress);
            }
        }
    }
}// End Class
//
// byte[] te = Base64.decode(ObjContract.GetImage(), 0);
// Bitmap bitmap = BitmapFactory.decodeByteArray(te, 0,
// te.length);
