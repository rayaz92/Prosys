package com.example.amir.core.ADR.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;

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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.amir.core.ADR.activity.Classes.CatchMsg;
import com.example.amir.core.ADR.activity.Classes.Connection;
import com.example.amir.core.ADR.activity.Classes.Contract;
import com.example.amir.core.ADR.activity.Classes.ContractPublishDetails;
import com.example.amir.core.ADR.activity.Classes.Contract_Offer;
import com.example.amir.core.ADR.activity.Classes.IssueDate;
import com.example.amir.core.ADR.activity.Classes.Payment;
import com.example.amir.core.ADR.activity.printing.DeviceList;
import com.example.amir.core.ADR.activity.printing.PrintInterMec;
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

@SuppressLint("SimpleDateFormat")
public class Calculate_Result extends Activity implements OnTouchListener,
		OnFocusChangeListener, OnClickListener, OnItemSelectedListener,
		OnCheckedChangeListener {
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;

	File mypathHeader;
	// /For
	String MacAddress;

	Button BtnShow, BtnSave;
	Spinner SpinOffer;
	List<Contract_Offer> listContract_Offer = null;
	ArrayAdapter<Contract_Offer> ArrayAdapterContract_Offer = null;
	EditText TxTdiscountPer, TxTdiscount, TxTPressTaxPer, TxTPressTax,
			TxTSalesTaxPer, TxTSalesTax, TxTTotal, TxTsubTotal, TxTNet,
			TxTCommisPer, TxTCommis, TxTCr_NotePer, TxTCr_Note;
	Contract ObjContract = new Contract();
	int ResultAddContract,IssueID = 0 , NewSerialNo = 0 ;
	int Qty, free, totalQty, OfferId, Freq , FirstIssueID = 0, PageNo = 0;// To Show
	CheckBox CheckFreeSTax, CheckFreePTax;

	ArrayList<ContractPublishDetails> ListContractPublish;

	List<Contract> ContractCheckCRLimitList;

	// public static Bitmap mBitmap;

	public static ImageView imageViewSigntuare;
	byte[] ImageToDB;
	//
	int NoPaid = -1;
	int NoFree = -1;
	Context context = this;
	Double CRLimit = 0.0 ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adrcalculateresult);
		InitialView();
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		//new FillSpinners(FillSpinners.ActivityMode.CalculateResult, this, this)
		//		.execute();
		 InitPrinter();
		 CopyAssetFiles();
	}

	public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
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
				CatchMsg.WriteMSG("Cal_Reslt convertBitmapToByteArray",
						e.getMessage());
			}
			return b;
		}
	}

	private void InitialView() {
		// ----image view -----//
		imageViewSigntuare = (ImageView) findViewById(R.id.imageViewSigntuare);
		imageViewSigntuare.buildDrawingCache();
		imageViewSigntuare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(Calculate_Result.this,
						Signtuare.class), 10);
			}
		});
		// Get Contract Object
		ObjContract = (Contract) getIntent().getExtras().getSerializable(
				"ObjContract");

		// --------CheckBox-----------//
		CheckFreePTax = (CheckBox) findViewById(R.id.CheckBoxFreePTax);
		CheckFreePTax.setOnCheckedChangeListener(this);
		CheckFreeSTax = (CheckBox) findViewById(R.id.CheckBoxFreeSTax);
		CheckFreeSTax.setOnCheckedChangeListener(this);
		// --------spinner -----------//
		SpinOffer = (Spinner) findViewById(R.id.SpinnerOffer);
		SpinOffer.setOnItemSelectedListener(this);
		// --------Edit Text --------//
		TxTdiscount = (EditText) findViewById(R.id.TxTDiscount);
		TxTdiscountPer = (EditText) findViewById(R.id.TxTDiscountPer);
		TxTdiscountPer.setOnTouchListener(this);
		TxTdiscountPer.setText("" + 0);
		TxTsubTotal = (EditText) findViewById(R.id.TxTSubTotal);

		TxTPressTaxPer = (EditText) findViewById(R.id.TxTPressTaxPer);
		TxTPressTaxPer.setOnTouchListener(this);
		TxTPressTaxPer.setText("" + 1);

		TxTPressTax = (EditText) findViewById(R.id.TxTPressTax);

		TxTSalesTax = (EditText) findViewById(R.id.TxTSalesTax);
		TxTSalesTax.setText("");
		// TxTSalesTax.setOnTouchListener(this);

		TxTSalesTaxPer = (EditText) findViewById(R.id.TxTSalesTaxPer);
		TxTSalesTaxPer.setOnTouchListener(this);
		TxTSalesTaxPer.setText("" + CoreLogInActivity.NewTax);

		TxTNet = (EditText) findViewById(R.id.TxTNet);

		TxTCommisPer = (EditText) findViewById(R.id.TxTCommisPer);
		TxTCommisPer.setOnTouchListener(this);
		TxTCommisPer.setText("" + 0);
		TxTCommis = (EditText) findViewById(R.id.TxTCommis);
		// TxTCommis.setOnTouchListener(this);
		TxTCr_NotePer = (EditText) findViewById(R.id.TxTCr_NotePer);
		TxTCr_NotePer.setText("" + 0);
		TxTCr_Note = (EditText) findViewById(R.id.TxTCr_Note);
		// -----btn ------//
		BtnShow = (Button) findViewById(R.id.BtnCalculate_Result);
		BtnSave = (Button) findViewById(R.id.BtnCalculate_Result_Save);
		// BtnSave.setEnabled(false);
		BtnSave.setOnClickListener(this);
		final Intent i = getIntent();
		// i.getStringExtra("Total");
		Freq = i.getIntExtra("Freq", 0);
		TxTTotal = (EditText) findViewById(R.id.CalculateResult_TxTTotal);
		// TxTTotal.setText("" + i.getDoubleExtra("Total", 0.0));
		TxTTotal.setText("" + ObjContract.GetGrossTotal());
		Qty = ObjContract.GetQtyIss();
		free = ObjContract.GetFree();
		// totalQty = Qty + free;
		totalQty = ObjContract.GetQtyIss() + ObjContract.GetFree();
		// BtnCalculate.setEnabled(false);
		BtnShow.setOnClickListener(this);

		PageNo = i.getIntExtra("PageNo" , 0);
        //FirstIssueID = i.getIntExtra("FirstIssueID" , 0);
		FirstIssueID = ObjContract.GetFirstIssue();

		TxTPressTaxPer.setEnabled(false);
		TxTSalesTaxPer.setEnabled(false);
		CheckFreePTax.setEnabled(false);
		CheckFreeSTax.setEnabled(false);

		TxTPressTaxPer.setText("0.0");
		//TxTSalesTaxPer.setText("0.0");
		CalculateNet();
        CheckFreeSTax.setChecked(false);
        CheckFreePTax.setChecked(true);

	}// End Initial View

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		v.setOnFocusChangeListener(this);
		return false;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (!hasFocus) {
			CalculateNet();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			if (resultCode == 200) {
				Log.d("ss", "s200");
				ListContractPublish = (ArrayList<ContractPublishDetails>) data
						.getExtras().get("Obj");
			}
			// if (resultCode == 0)
			// lisContractPublishDetails = new
			// ArrayList<ContractPublishDetails>();
			// else {

			// lisContractPublishDetails = ((ArrayList<ContractPublishDetails>)
			// data
			// .getExtras().getSerializable("Obj"));
			// lisContractPublishDetails=(ArrayList<ContractPublishDetails>)
			// bundle.get("Obj");
			// lisContractPublishDetails = (ArrayList<ContractPublishDetails>)
			// data
			// .getExtras().get("Obj");

			// }
			if (resultCode == 100) {
				Log.d("ss", "s100");
				// BtnSave.setEnabled(true);
				if (!(data.getExtras().get("Image") == null))
					imageViewSigntuare.setImageBitmap((Bitmap) data.getExtras()
							.get("Image"));
				else
					GetToast.Toast(context, "Null");
			}
			switch (requestCode) {
			case REQUEST_CONNECT_DEVICE:
				// When DeviceListActivity returns with a device to connect
				if (resultCode == Activity.RESULT_OK) {
					String address = data.getExtras().getString(
							DeviceList.EXTRA_DEVICE_ADDRESS);
					MacAddress = address;
				}
				break;
			case REQUEST_ENABLE_BT:
				// When the request to enable Bluetooth returns
				if (resultCode != Activity.RESULT_OK) {
					// User did not enable Bluetooth or an error occured
					GetToast.Toast(context,
							getString(R.string.EnabletoConnected));
					finish();
				}
			}
		}

	}

	private void CalculateNet() {
		// Float3 DiscountPer = Math.round(((TxTdiscountPer.getText()
		// .toString()) * 1000) / 1000);
		if (TxTdiscountPer.getText().toString().equals("")) {
			GetToast.Toast(context, getString(R.string.PleaseEnterDiscount));

			return;
		}
		Float DiscountPer = Float
				.valueOf((TxTdiscountPer.getText().toString()));
		DiscountPer = (float) (DiscountPer * 1000 / 1000);
		BigDecimal v = new BigDecimal(DiscountPer).setScale(3,
				BigDecimal.ROUND_HALF_UP);

		TxTdiscountPer.setText("" + v);

		Float DiscountAmount = Float.valueOf(TxTTotal.getText().toString())
				* (DiscountPer / 100);
		DiscountAmount = Float.valueOf((DiscountAmount * 1000 / 1000));
		v = new BigDecimal(DiscountAmount)
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		TxTdiscount.setText("" + v);

		Float SubTotal = Float.valueOf(TxTTotal.getText().toString())
				- Float.valueOf(TxTdiscount.getText().toString());
		SubTotal = (SubTotal * 1000 / 1000);
		v = new BigDecimal(SubTotal).setScale(3, BigDecimal.ROUND_HALF_UP);
		TxTsubTotal.setText("" + v);

		Float PressTax = ((Float.valueOf(TxTPressTaxPer.getText().toString()) * 1000) / 1000);
		PressTax = ((Float.valueOf(PressTax) * 1000) / 1000);
		v = new BigDecimal(PressTax).setScale(3, BigDecimal.ROUND_HALF_UP);
		TxTPressTaxPer.setText("" + v);

		Float PressTaxAmount = Float.valueOf(TxTTotal.getText().toString())
				* (PressTax / 100);
		PressTaxAmount = (PressTaxAmount * 1000 / 1000);
		v = new BigDecimal(PressTaxAmount)
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		TxTPressTax.setText("" + v);

		float SalesTax = ((Float.valueOf(TxTSalesTaxPer.getText().toString()) * 1000) / 1000);
		TxTSalesTaxPer.setText("" + SalesTax);
		float STaxAmount = (SubTotal + PressTaxAmount) * (SalesTax / 100);
		STaxAmount = (STaxAmount * 1000 / 1000);
		v = new BigDecimal(STaxAmount).setScale(3, BigDecimal.ROUND_HALF_UP);
		TxTSalesTax.setText("" + v);

		double Net = (double) SubTotal + STaxAmount + PressTaxAmount;
		Net = Math.ceil(Net * 1000) / 1000;
		TxTNet.setText("" + Net);

		double CommTax = Double.valueOf(TxTCommisPer.getText().toString());
		CommTax = Math.ceil(CommTax * 1000) / 1000;
		TxTCommisPer.setText("" + CommTax);
		double CommTaxAmount = SubTotal * (CommTax / 100);
		CommTaxAmount = Math.ceil(CommTaxAmount * 1000) / 1000;
		TxTCommis.setText("" + CommTaxAmount);
		calculateCRNoteAmount();

	}

	private void calculateCRNoteAmount() {
		double CRNote = Double.valueOf(TxTCr_NotePer.getText().toString());
		CRNote = Math.round(CRNote * 1000) / 1000;
		TxTCr_NotePer.setText("" + CRNote);

		double Total = Double.valueOf(TxTTotal.getText().toString());
		Total = Math.round(Total * 1000) / 1000;

		double CRNoteAmount = Total * (CRNote / 100);
		CRNoteAmount = Math.round(CRNoteAmount * 1000) / 1000;
		TxTCr_Note.setText("" + CRNoteAmount);
		FillObject();
		FillContractPublishWithoutOffer();

	}

	@Override
	public void onClick(View v) {



        if (TxTNet.length() > 0) {
            if (Double.valueOf(TxTNet.getText().toString()) > 0.0) {
                if (v.getId() == BtnSave.getId()) {
                    CalculateNet();
                    FillObject();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Calculate_Result.this);
                    builder1.setMessage(getString(R.string.AreYouSure));
                    builder1.setTitle(getString(R.string.SaveContract));
                    builder1.setIcon(R.drawable.save_icon);
                    builder1.setCancelable(true);
                    builder1.setNeutralButton(getString(R.string.Yes),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

										if (CheckCRLimit()){
											if (CoreLogInActivity.UserID > 0 & CoreLogInActivity.SalesID > 0) {
												InsertContract();
											} else {
												Toast.makeText(Calculate_Result.this, getString(R.string.PleaseReLogIn), Toast.LENGTH_SHORT).show();
												Calculate_Result.this.finish();
												Intent i = new Intent(Calculate_Result.this, CoreLogInActivity.class);
												startActivity(i);
											}
										}else{
											GetToast.Toast(context,getString(R.string.NoCredit));
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
                }// End If btn save
                else {

                    if (TxTNet.length() == 0) {
                        GetToast.Toast(context, getString(R.string.PleaseEnterDiscount));
                        return;
                    } else {
                        Intent ShowExpandableList = new Intent(Calculate_Result.this,
                                ShowList.class);
                        Bundle bundle = new Bundle();
                        Intent i = getIntent();
                        bundle.putSerializable("ObjContract",
                                (Serializable) ObjContract);
                        bundle.putSerializable("ContractPublish",
                                (Serializable) ListContractPublish);
                        ShowExpandableList.putExtras(bundle);
                        ShowExpandableList.putExtra("Freq", i.getIntExtra("Freq", 0));
                        startActivityForResult(ShowExpandableList, 0);
                    }
                }// End Else
            } else {
                GetToast.Toast(context, getString(R.string.PleaseInsertAmount));
            }
        } else {
            GetToast.Toast(context, getString(R.string.PleaseInsertAmount));
        }
    }


	private void InsertContract() {

		if (IsIssueIdValid()) {//testamir
			if (Connection.isConnectingToInternet(context)) {
				new AsyncTask<Void, Void, Void>() {
					ShowProgressDialog showProgressDialog;

					protected void onPreExecute() {
						showProgressDialog = new ShowProgressDialog(context,
								getString(R.string.SaveContract),
								getString(R.string.Wait), 0);
						showProgressDialog.ShowDialog();
					};

					@Override
					protected Void doInBackground(Void... params) {
							ResultAddContract = ObjContract.InsertContract(
									ObjContract, CoreLogInActivity.UserID,
									CoreLogInActivity.UserAccountName,
									CoreLogInActivity.UserAccountNo,
									ShowMain.DeviceID);
							NewSerialNo = new Payment().GetSerialNoByTypeID(ObjContract.GetContractTypeID());
//						ObjContract, Main.UserID, ShowMain.DeviceID);
						return null;
					}
					@Override
					protected void onPostExecute(Void result) {
						showProgressDialog.EndDialog();
						if (ResultAddContract > 0) {
							GetToast.Toast(context,
									getString(R.string.SavedSuccessfully));
//							int ser = new Payment().GetSerialNoByTypeID(1);
							ObjContract.SetSerialNo(NewSerialNo);
							new InsertContractPublish().execute();
						}// End If Result
						else
							GetToast.Toast(context,
									getString(R.string.ErrorInSaveContract));
					}
				}.execute();
			} else
				// If No Internet
				GetToast.Toast(context, getString(R.string.NoInternet));
		} else
			GetToast.Toast(context, getString(R.string.ErrorInData));// "You Have Problem Please Check Date ");
	}// End Void Insert Contract

	private String DateAndFreq(int freq, String firstDate) {
		Calendar cal = Calendar.getInstance();
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd" , Locale.ENGLISH);
		try {
			cal.setTime(sdf.parse(firstDate));
			cal.add(Calendar.DATE, freq);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String DateAfterFreq = sdf.format(cal.getTime());
		return DateAfterFreq;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// menu.add("Signtuare");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.adrcalculate_result, menu);
		return true;
		// return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		imageViewSigntuare.performClick();
		return super.onOptionsItemSelected(item);
	}

	private void InsertContractPublichwithOffer(int ContractID) {
		int y = 0;
		String DateContract = ObjContract.GetIssueDate();
		for (int totalQ = 0; totalQ < (totalQty / (NoFree + NoPaid)); totalQ++) {
			for (int No_Paid = 0; No_Paid < NoPaid; No_Paid++) {

				if (y == 0) {
					ContractPublishDetails objContractPublishDetails = new ContractPublishDetails();
					objContractPublishDetails.SetCm(ObjContract.GetCm());
					objContractPublishDetails.SetCol(ObjContract.GetCol());
					objContractPublishDetails.SetCommisAmount(ObjContract
							.GetCommisAmount() / ObjContract.GetQtyIss());
					objContractPublishDetails.SetCommisPer(ObjContract
							.GetCommisPer());
					objContractPublishDetails.SetContractID(ContractID);
					objContractPublishDetails
							.SetCRNote(ObjContract.GetCRNote());
					objContractPublishDetails.SetCRNotePer(ObjContract
							.GetCRNotePer());
					objContractPublishDetails.SetdetailedPageTypeID(ObjContract
							.GetPage_Type());
					objContractPublishDetails.SetDiscountAmount(ObjContract
							.GetDiscountAmount() / ObjContract.GetQtyIss());
					objContractPublishDetails.SetDiscountPer(ObjContract
							.GetDiscountPer());
					objContractPublishDetails.SetGrossTotal((ObjContract
							.GetGrossTotal() / ObjContract.GetQtyIss()));
					if (ListContractPublish != null) {
						if (ListContractPublish.get(No_Paid).GetIssueID() == 0) {
							objContractPublishDetails.SetIssueID(Integer
									.valueOf(FirstIssueID(DateContract)));
						} else
							objContractPublishDetails
									.SetIssueID(ListContractPublish
											.get(No_Paid).GetIssueID());
					} else
						objContractPublishDetails.SetIssueID(Integer
								.valueOf(FirstIssueID(DateContract)));

					objContractPublishDetails.Setsubject(ObjContract
							.GetSubject());
					objContractPublishDetails.SetNetAmount(ObjContract
							.GetNetAmount() / ObjContract.GetQtyIss());
					objContractPublishDetails.SetPage_Type(ObjContract
							.GetPageTypeID());
					objContractPublishDetails.SetPageNo(PageNo);
					objContractPublishDetails.SetPressTaxAmount(ObjContract
							.GetPressTaxAmount() / ObjContract.GetQtyIss());
					objContractPublishDetails.SetPressTaxPer(ObjContract
							.GetPressTaxPer());
					objContractPublishDetails.SetSalesTaxAmount(ObjContract
							.GetSalesTaxAmount() / ObjContract.GetQtyIss());
					objContractPublishDetails.SetSalesTaxPer(ObjContract
							.GetSalesTaxPer());
					objContractPublishDetails.SetSCm(ObjContract.GetSCm());
					objContractPublishDetails.SetSCol(ObjContract.GetSCol());
					objContractPublishDetails.SetStatusID(5);
					objContractPublishDetails
							.InsertContractPublishDetails(objContractPublishDetails,ObjContract.getPublicationID());
					y++;
				} else {
					ContractPublishDetails objContractPublishDetails = new ContractPublishDetails();
					DateContract = DateAndFreq(Freq, DateContract);
					objContractPublishDetails.SetCm(ObjContract.GetCm());
					objContractPublishDetails.SetCol(ObjContract.GetCol());
					objContractPublishDetails.SetCommisAmount(ObjContract
							.GetCommisAmount() / ObjContract.GetQtyIss());
					objContractPublishDetails.SetCommisPer(ObjContract
							.GetCommisPer());
					objContractPublishDetails.Setsubject(ObjContract
							.GetSubject());
					objContractPublishDetails.SetContractID(ContractID);
					objContractPublishDetails
							.SetCRNote(ObjContract.GetCRNote());
					objContractPublishDetails.SetCRNotePer(ObjContract
							.GetCRNotePer());
					objContractPublishDetails.SetdetailedPageTypeID(ObjContract
							.GetPage_Type());
					objContractPublishDetails.SetDiscountAmount(ObjContract
							.GetDiscountAmount() / ObjContract.GetQtyIss());
					objContractPublishDetails.SetDiscountPer(ObjContract
							.GetDiscountPer());
					objContractPublishDetails.SetGrossTotal((ObjContract
							.GetGrossTotal() / ObjContract.GetQtyIss()));
					if (ListContractPublish != null) {
						if (ListContractPublish.get(No_Paid).GetIssueID() == 0) {
							objContractPublishDetails.SetIssueID(Integer
									.valueOf(FirstIssueID(DateContract)));
						} else
							objContractPublishDetails
									.SetIssueID(ListContractPublish
											.get(No_Paid).GetIssueID());
					} else
						objContractPublishDetails.SetIssueID(Integer
								.valueOf(FirstIssueID(DateContract)));
					objContractPublishDetails.SetNetAmount(ObjContract
							.GetNetAmount() / ObjContract.GetQtyIss());
					objContractPublishDetails.SetPage_Type(ObjContract
							.GetPageTypeID());
					objContractPublishDetails.SetPageNo(PageNo);
					objContractPublishDetails.SetPressTaxAmount(ObjContract
							.GetPressTaxAmount() / ObjContract.GetQtyIss());
					objContractPublishDetails
							.SetPressTaxPer(objContractPublishDetails
									.GetPressTaxPer());
					objContractPublishDetails.SetSalesTaxAmount(ObjContract
							.GetSalesTaxAmount() / ObjContract.GetQtyIss());
					objContractPublishDetails.SetSalesTaxPer(ObjContract
							.GetSalesTaxPer());
					objContractPublishDetails.SetSCm(ObjContract.GetSCm());
					objContractPublishDetails.SetSCol(ObjContract.GetSCol());
					objContractPublishDetails.SetStatusID(5);
					objContractPublishDetails.SetIssuePrice(0.00);
					objContractPublishDetails
							.InsertContractPublishDetails(objContractPublishDetails,ObjContract.getPublicationID());

				}

			}// End For No Paid
			for (int No_Free = 0; No_Free < NoFree; No_Free++) {

				if (y == 0) {
					ContractPublishDetails objContractPublishDetails = new ContractPublishDetails();
					objContractPublishDetails.SetCm(ObjContract.GetCm());
					objContractPublishDetails.SetCol(ObjContract.GetCol());
					objContractPublishDetails.SetCommisAmount(0.0);
					objContractPublishDetails.SetCommisPer(0.0);
					objContractPublishDetails.SetContractID(ContractID);
					objContractPublishDetails
							.SetCRNote(ObjContract.GetCRNote());
					objContractPublishDetails.SetCRNotePer(ObjContract
							.GetCRNotePer());
					objContractPublishDetails.SetdetailedPageTypeID(ObjContract
							.GetPage_Type());
					objContractPublishDetails.SetPage_Type(ObjContract
							.GetPageTypeID());
					objContractPublishDetails.SetDiscountAmount(0.0);
					objContractPublishDetails.SetDiscountPer(0.0);
					objContractPublishDetails.SetGrossTotal(0.0);
					if (ListContractPublish != null) {
						if (ListContractPublish.get(Qty + NoFree).GetIssueID() == 0)
							objContractPublishDetails.SetIssueID(Integer
									.valueOf(FirstIssueID(DateContract)));
						else
							objContractPublishDetails
									.SetIssueID(ListContractPublish.get(
											Qty + NoFree).GetIssueID());
					}
					// objContractPublishDetails.SetIssueID(Integer
					// .valueOf(FirstIssueID(DateContract)));
					else
						objContractPublishDetails.SetIssueID(Integer
								.valueOf(FirstIssueID(DateContract)));
					objContractPublishDetails.Setsubject(ObjContract
							.GetSubject());
					objContractPublishDetails.SetNetAmount(0.0);
					objContractPublishDetails.SetPage_Type(ObjContract
							.GetPageTypeID());
					objContractPublishDetails.SetPageNo(PageNo);
					objContractPublishDetails.SetPressTaxAmount(0.0);
					objContractPublishDetails.SetPressTaxPer(0.0);
					objContractPublishDetails.SetSalesTaxAmount(0.0);
					objContractPublishDetails.SetSalesTaxPer(0.0);
					objContractPublishDetails.SetSCm(ObjContract.GetSCm());
					objContractPublishDetails.SetSCol(ObjContract.GetSCol());
					objContractPublishDetails.SetStatusID(5);
					objContractPublishDetails
							.InsertContractPublishDetails(objContractPublishDetails,ObjContract.getPublicationID());

					y++;
				} else {
					ContractPublishDetails objContractPublishDetails = new ContractPublishDetails();
					DateContract = DateAndFreq(Freq, DateContract);
					objContractPublishDetails.SetCm(ObjContract.GetCm());
					objContractPublishDetails.SetCol(ObjContract.GetCol());
					objContractPublishDetails.SetCommisAmount(0.0);
					objContractPublishDetails.SetCommisPer(0.0);
					objContractPublishDetails.SetContractID(ContractID);
					objContractPublishDetails
							.SetCRNote(ObjContract.GetCRNote());
					objContractPublishDetails.SetCRNotePer(ObjContract
							.GetCRNotePer());
					objContractPublishDetails.SetdetailedPageTypeID(ObjContract
							.GetPage_Type());
					objContractPublishDetails.SetPage_Type(ObjContract
							.GetPageTypeID());
					objContractPublishDetails.SetDiscountAmount(0.0);
					objContractPublishDetails.SetDiscountPer(0.0);
					objContractPublishDetails.SetGrossTotal(0.0);
					if (ListContractPublish != null) {
						if (ListContractPublish.get(Qty + NoFree).GetIssueID() == 0)
							objContractPublishDetails.SetIssueID(Integer
									.valueOf(FirstIssueID(DateContract)));
						else
							objContractPublishDetails
									.SetIssueID(ListContractPublish.get(
											Qty + NoFree).GetIssueID());
					}
					// objContractPublishDetails.SetIssueID(Integer
					// .valueOf(FirstIssueID(DateContract)));
					else
						objContractPublishDetails.SetIssueID(Integer
								.valueOf(FirstIssueID(DateContract)));
					objContractPublishDetails.Setsubject(ObjContract
							.GetSubject());
					objContractPublishDetails.SetNetAmount(0.0);
					objContractPublishDetails.SetPage_Type(ObjContract
							.GetPageTypeID());
					objContractPublishDetails.SetPageNo(PageNo);
					objContractPublishDetails.SetPressTaxAmount(0.0);
					objContractPublishDetails.SetPressTaxPer(0.0);
					objContractPublishDetails.SetSalesTaxAmount(0.0);
					objContractPublishDetails.SetSalesTaxPer(0.0);
					objContractPublishDetails.SetSCm(ObjContract.GetSCm());
					objContractPublishDetails.SetSCol(ObjContract.GetSCol());
					objContractPublishDetails.SetStatusID(5);
					objContractPublishDetails
							.InsertContractPublishDetails(objContractPublishDetails,ObjContract.getPublicationID());
				}
			}// End For
		}// End Total Qty
	}// end With Offer

	private void FillContractPublishWithoutOffer() {
		double PriceForOne = (ObjContract.GetNetAmount() / ObjContract
				.GetQtyIss());
		BigDecimal v = new BigDecimal(PriceForOne).setScale(3,
				BigDecimal.ROUND_HALF_UP);
		PriceForOne = v.doubleValue();
		ListContractPublish = new ArrayList<ContractPublishDetails>();
		int y = 0;
		String FirstIssueDate = ObjContract.GetIssueDate();
		for (int q = 0; q < ObjContract.GetQtyIss(); q++) {
			if (y == 0) {
				ContractPublishDetails objContractPublishDetails = new ContractPublishDetails();
				objContractPublishDetails.SetIssueDate(FirstIssueDate);
				objContractPublishDetails.SetIssuePrice(PriceForOne);
				objContractPublishDetails.SetIssueID(Integer
						.valueOf(FirstIssueID(FirstIssueDate)));
				// ObjContract.GetContractDate())));
				ListContractPublish.add(objContractPublishDetails);
				y++;
			} else {
				ContractPublishDetails objContractPublishDetails = new ContractPublishDetails();

				FirstIssueDate = DateAndFreq(7, FirstIssueDate);

				objContractPublishDetails.SetIssuePrice(PriceForOne);

				objContractPublishDetails.SetIssueDate(FirstIssueDate);

				objContractPublishDetails.SetIssueID(Integer
						.valueOf(FirstIssueID(FirstIssueDate)));

				ListContractPublish.add(objContractPublishDetails);
			}
		}// For
		for (int f = 0; f < ObjContract.GetFree(); f++) {
			ContractPublishDetails objContractPublishDetails = new ContractPublishDetails();

			FirstIssueDate = DateAndFreq(7, FirstIssueDate);

			objContractPublishDetails.SetIssueID(Integer
					.valueOf(FirstIssueID(FirstIssueDate)));

			objContractPublishDetails.SetIssueDate(FirstIssueDate);

			objContractPublishDetails.SetIssuePrice(0.0);

			ListContractPublish.add(objContractPublishDetails);

		}// End for Free

	}// End Function

	private void FillContractPublishWithOffer() {
		int y = 0;
		double PriceForOne = (ObjContract.GetNetAmount() / ObjContract
				.GetQtyIss());
		BigDecimal v = new BigDecimal(PriceForOne).setScale(3,
				BigDecimal.ROUND_HALF_UP);
		PriceForOne = v.doubleValue();
		String FirstIssueDate = ObjContract.GetIssueDate();

		ListContractPublish = new ArrayList<ContractPublishDetails>();
		for (int totalQ = 0; totalQ < (totalQty / (NoFree + NoPaid)); totalQ++) {
			for (int No_Paid = 0; No_Paid < NoPaid; No_Paid++) {

				if (y == 0) {
					ContractPublishDetails objContractPublishDetails = new ContractPublishDetails();

					objContractPublishDetails.SetIssueDate(FirstIssueDate);

					objContractPublishDetails.SetIssuePrice(PriceForOne);

					objContractPublishDetails.SetIssueID(Integer
							.valueOf(FirstIssueID(FirstIssueDate)));

					y++;
					ListContractPublish.add(objContractPublishDetails);
				} else {
					ContractPublishDetails objContractPublishDetails = new ContractPublishDetails();
					FirstIssueDate = DateAndFreq(Freq, FirstIssueDate);

					objContractPublishDetails.SetIssueDate(FirstIssueDate);

					objContractPublishDetails.SetIssueID(Integer
							.valueOf(FirstIssueID(FirstIssueDate)));

					objContractPublishDetails.SetIssuePrice(PriceForOne);
					ListContractPublish.add(objContractPublishDetails);
				}
			}// End For No Paid
			for (int No_Free = 0; No_Free < NoFree; No_Free++) {

				if (y == 0) {
					ContractPublishDetails objContractPublishDetails = new ContractPublishDetails();

					objContractPublishDetails.SetIssueID(Integer
							.valueOf(FirstIssueID(FirstIssueDate)));

					objContractPublishDetails.SetIssueDate(FirstIssueDate);

					objContractPublishDetails.SetIssuePrice(0.0);

					ListContractPublish.add(objContractPublishDetails);
					y++;
				} else {
					ContractPublishDetails objContractPublishDetails = new ContractPublishDetails();

					FirstIssueDate = DateAndFreq(Freq, FirstIssueDate);

					objContractPublishDetails.SetIssueDate(FirstIssueDate);

					objContractPublishDetails.SetIssuePrice(0.0);

					objContractPublishDetails.SetIssueID(Integer
							.valueOf(FirstIssueID(FirstIssueDate)));

					ListContractPublish.add(objContractPublishDetails);
				}
			}// End For
		}// End Total Qty
	}// End Fill Contract Publish With Offer

	private void InsertToContractPublishDetalis(int ContractID) {
		try{
		int y = 0;
		String FirstIssueDate = ObjContract.GetIssueDate();
		for (int q = 0; q < ObjContract.GetQtyIss(); q++) {
			if (y == 0) {
				ContractPublishDetails objContractPublishDetails = new ContractPublishDetails();
				objContractPublishDetails.SetCm(ObjContract.GetCm());
				objContractPublishDetails.SetCol(ObjContract.GetCol());
				objContractPublishDetails.SetCommisAmount(ObjContract
						.GetCommisAmount() / ObjContract.GetQtyIss());
				objContractPublishDetails.SetCommisPer(ObjContract
						.GetCommisPer());
				objContractPublishDetails.SetContractID(ContractID);
				objContractPublishDetails.SetCRNote(ObjContract.GetCRNote());
				objContractPublishDetails.SetCRNotePer(ObjContract
						.GetCRNotePer());
				objContractPublishDetails.SetdetailedPageTypeID(ObjContract
						.GetPage_Type());
				objContractPublishDetails.SetDiscountAmount(ObjContract
						.GetDiscountAmount() / ObjContract.GetQtyIss());
				objContractPublishDetails.SetDiscountPer(ObjContract
						.GetDiscountPer());
				objContractPublishDetails.SetGrossTotal((ObjContract
						.GetGrossTotal() / ObjContract.GetQtyIss()));
//				if (ListContractPublish != null) {
//					if (ListContractPublish.get(q).GetIssueID() == 0) {
//						objContractPublishDetails.SetIssueID(Integer
//								.valueOf(FirstIssueID(FirstIssueDate)));
//					} else
//						objContractPublishDetails
//								.SetIssueID(ListContractPublish.get(q)
//										.GetIssueID());
//				} else
//					objContractPublishDetails.SetIssueID(Integer
//							.valueOf(FirstIssueID(FirstIssueDate)));

                objContractPublishDetails.SetIssueID(Integer
							.valueOf(FirstIssueID));
//amir
				// ObjContract.GetContractDate())));
				objContractPublishDetails.Setsubject(ObjContract.GetSubject());
				objContractPublishDetails.SetNetAmount(ObjContract
						.GetNetAmount() / ObjContract.GetQtyIss());
				objContractPublishDetails.SetPage_Type(ObjContract
						.GetPageTypeID());//amir
				objContractPublishDetails.SetPageNo(PageNo);
				objContractPublishDetails.SetPressTaxAmount(ObjContract
						.GetPressTaxAmount() / ObjContract.GetQtyIss());

				objContractPublishDetails.SetPressTaxPer(ObjContract
						.GetPressTaxPer());

				objContractPublishDetails.SetSalesTaxAmount(ObjContract
						.GetSalesTaxAmount() / ObjContract.GetQtyIss());
				objContractPublishDetails.SetSalesTaxPer(ObjContract
						.GetSalesTaxPer());
				objContractPublishDetails.SetSCm(ObjContract.GetSCm());
				objContractPublishDetails.SetSCol(ObjContract.GetSCol());
				objContractPublishDetails.SetStatusID(5);
				objContractPublishDetails
						.InsertContractPublishDetails(objContractPublishDetails,ObjContract.getPublicationID());
				y++;
			} else {
				ContractPublishDetails objContractPublishDetails = new ContractPublishDetails();
//				FirstIssueDate = DateAndFreq(Freq, FirstIssueDate);
				objContractPublishDetails.SetCm(ObjContract.GetCm());
				objContractPublishDetails.SetCol(ObjContract.GetCol());
				objContractPublishDetails.SetCommisAmount(ObjContract
						.GetCommisAmount() / ObjContract.GetQtyIss());
				objContractPublishDetails.SetCommisPer(ObjContract
						.GetCommisPer());
				objContractPublishDetails.Setsubject(ObjContract.GetSubject());
				objContractPublishDetails.SetContractID(ContractID);
				objContractPublishDetails.SetCRNote(ObjContract.GetCRNote());
				objContractPublishDetails.SetCRNotePer(ObjContract
						.GetCRNotePer());
				objContractPublishDetails.SetdetailedPageTypeID(ObjContract
						.GetPage_Type());
				objContractPublishDetails.SetDiscountAmount(ObjContract
						.GetDiscountAmount() / ObjContract.GetQtyIss());
				objContractPublishDetails.SetDiscountPer(ObjContract
						.GetDiscountPer());
				objContractPublishDetails.SetGrossTotal((ObjContract
						.GetGrossTotal() / ObjContract.GetQtyIss()));
//				if (ListContractPublish != null) {
//					if (ListContractPublish.get(q).GetIssueID() == 0) {
//						objContractPublishDetails.SetIssueID(Integer
//								.valueOf(FirstIssueID(FirstIssueDate)));
//					} else
//						objContractPublishDetails
//								.SetIssueID(ListContractPublish.get(q)
//										.GetIssueID());
//				} else
//					objContractPublishDetails.SetIssueID(Integer
//							.valueOf(FirstIssueID(FirstIssueDate)));

                objContractPublishDetails.SetIssueID(Integer
                        .valueOf(FirstIssueID + q));

				objContractPublishDetails.SetNetAmount(ObjContract
						.GetNetAmount() / ObjContract.GetQtyIss());
				objContractPublishDetails.SetPage_Type(ObjContract
						.GetPageTypeID());
				objContractPublishDetails.SetPageNo(PageNo);
				objContractPublishDetails.SetPressTaxAmount(ObjContract
						.GetPressTaxAmount() / ObjContract.GetQtyIss());
				objContractPublishDetails
						.SetPressTaxPer(objContractPublishDetails
								.GetPressTaxPer());
				objContractPublishDetails.SetSalesTaxAmount(ObjContract
						.GetSalesTaxAmount() / ObjContract.GetQtyIss());
				objContractPublishDetails.SetSalesTaxPer(ObjContract
						.GetSalesTaxPer());
				objContractPublishDetails.SetSCm(ObjContract.GetSCm());
				objContractPublishDetails.SetSCol(ObjContract.GetSCol());
				objContractPublishDetails.SetStatusID(5);
				objContractPublishDetails.SetIssuePrice(0.00);
				objContractPublishDetails
						.InsertContractPublishDetails(objContractPublishDetails,ObjContract.getPublicationID());
			}
		}// For
		for (int f = 0; f < ObjContract.GetFree(); f++) {
			ContractPublishDetails objContractPublishDetails = new ContractPublishDetails();
			FirstIssueDate = DateAndFreq(7, FirstIssueDate);
			objContractPublishDetails.SetCm(ObjContract.GetCm());
			objContractPublishDetails.SetCol(ObjContract.GetCol());
			objContractPublishDetails.SetCommisAmount(0.0);
			objContractPublishDetails.SetCommisPer(0.0);
			objContractPublishDetails.SetContractID(ContractID);
			objContractPublishDetails.SetCRNote(ObjContract.GetCRNote());
			objContractPublishDetails.SetCRNotePer(ObjContract.GetCRNotePer());
			objContractPublishDetails.SetdetailedPageTypeID(ObjContract
					.GetPage_Type());
			objContractPublishDetails.SetPage_Type(ObjContract.GetPageTypeID());
			objContractPublishDetails.SetDiscountAmount(0.0);
			objContractPublishDetails.SetDiscountPer(0.0);
			objContractPublishDetails.SetGrossTotal(0.0);

//			if (ListContractPublish != null) {
//				if (ListContractPublish.get(ObjContract.GetQtyIss() + f)
//						.GetIssueID() == 0)
//					objContractPublishDetails.SetIssueID(Integer
//							.valueOf(FirstIssueID(FirstIssueDate)));
//				else
//					objContractPublishDetails.SetIssueID(ListContractPublish
//							.get(ObjContract.GetQtyIss() + f).GetIssueID());
//			}
//			// objContractPublishDetails.SetIssueID(Integer
//			// .valueOf(FirstIssueID(DateContract)));
//			else
//				objContractPublishDetails.SetIssueID(Integer
//						.valueOf(FirstIssueID(FirstIssueDate)));

            objContractPublishDetails.SetIssueID(Integer
                    .valueOf(FirstIssueID + Qty)) ;

			objContractPublishDetails.Setsubject(ObjContract.GetSubject());
			objContractPublishDetails.SetNetAmount(0.0);
			objContractPublishDetails.SetPage_Type(ObjContract.GetPageTypeID());
			objContractPublishDetails.SetPageNo(PageNo);
			objContractPublishDetails.SetPressTaxAmount(0.0);
			objContractPublishDetails.SetPressTaxPer(0.0);
			objContractPublishDetails.SetSalesTaxAmount(0.0);
			objContractPublishDetails.SetSalesTaxPer(0.0);
			objContractPublishDetails.SetSCm(ObjContract.GetSCm());
			objContractPublishDetails.SetSCol(ObjContract.GetSCol());
			objContractPublishDetails.SetStatusID(5);
			objContractPublishDetails
					.InsertContractPublishDetails(objContractPublishDetails,ObjContract.getPublicationID());
		}
	}catch (Exception e){
			e.printStackTrace();
		}
	}

//	private String FirstIssueID(String dateIssue) {
//		int id = 0;
//		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//		format.setTimeZone(TimeZone.getTimeZone("GMT"));
//		java.util.Date d;
//		try {
//			d = format.parse(dateIssue);
//			id = new IssueDate().CheckIssueDate(d);
//
//		} catch (ParseException e) {
//
//			e.printStackTrace();
//		}
//		if (id == 1) {
//			return "1";
//		} else if (id == 0) {
//			return "0";
//		}
//		return "" + id;
//	}

	private String FirstIssueID(String dateIssue) {
		int id = 0;
		try {
//			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.ENGLISH);
		Date newDate = format.parse(dateIssue);

		format = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
		String date = format.format(newDate);

			new GetIssueDate().execute(date);

		} catch (ParseException e) {

			e.printStackTrace();
		}
		id = IssueID;
		return "" + id;
	}

	private void FillObject() {
		try {
			imageViewSigntuare.buildDrawingCache();
			Bitmap bitmap = imageViewSigntuare.getDrawingCache();
			ObjContract.SetImage(convertBitmapToByteArray(bitmap));
			ObjContract.SetDiscountPer(Double.valueOf(TxTdiscountPer.getText()
					.toString()));// -1-
			ObjContract.SetDiscountAmount(Double.valueOf(TxTdiscount.getText()
					.toString()));// -2-
			ObjContract.SetPressTaxPer(Double.valueOf(TxTPressTaxPer.getText()
					.toString()));// -3-
			ObjContract.SetPressTaxAmount(Double.valueOf(TxTPressTax.getText()
					.toString()));// -4-
			ObjContract.SetSalesTaxPer(Double.valueOf(TxTSalesTaxPer.getText()
					.toString()));// -5-
			ObjContract.SetSalesTaxAmount(Double.valueOf(TxTSalesTax.getText()
					.toString()));// -6-
			ObjContract.SetCommisPer(Double.valueOf(TxTCommisPer.getText()
					.toString()));// -7-
			ObjContract.SetCommisAmount(Double.valueOf(TxTCommis.getText()
					.toString()));// -8-
			ObjContract.SetCRNotePer(Double.valueOf(TxTCr_NotePer.getText()
					.toString()));// -9-
			ObjContract.SetCRNote(Double.valueOf(TxTCr_Note.getText()
					.toString()));// -10-
			ObjContract.SetNetAmount(Double
					.valueOf(TxTNet.getText().toString()));// -11-

		} catch (Exception e) {

			CatchMsg.WriteMSG("Cal_Res Fill Object", e.getMessage());
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int postion,
			long id) {
		OfferId = ArrayAdapterContract_Offer.getItem(
				SpinOffer.getSelectedItemPosition()).GetID();
		if (OfferId == 0) {
			BtnShow.setEnabled(true);
			return;
		}
		CheckOffer(OfferId);
	}

	private void CheckOffer(int OfferID) {
		Contract_Offer objContract_Offer = new Contract_Offer();
		objContract_Offer = objContract_Offer
				.SelectNo_PaidAndNo_FreeByOfferID(OfferID);
		NoPaid = objContract_Offer.GetOffer_No_Paid();
		NoFree = objContract_Offer.GetOffer_No_Free();
		int FreeAndPaid = NoFree + NoPaid;
		if (totalQty % FreeAndPaid != 0) {
			BtnShow.setEnabled(false);
			GetToast.Toast(context, "Your Qty not compatible with the Offer");
		} else {
			BtnShow.setEnabled(true);
			FillContractPublishWithOffer();
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

	private boolean IsIssueIdValid() {
//		String DateContract = ObjContract.GetIssueDate();
//		for (int DateValID = 0; DateValID < (ObjContract.GetQtyIss() + ObjContract
//				.GetFree()); DateValID++) {
//			if (Integer.valueOf(FirstIssueID(DateAndFreq(Freq, DateContract))) > 0) {
//				DateContract = DateAndFreq(Freq, DateContract);
//			} else
//
//				return false;
//		}
        if(FirstIssueID > 0){
            return true;
        }else{
            return false;
        }

	}// End IsIssueIdValid
		// <patams,progress,result>

	private class InsertContractPublish extends AsyncTask<Void, Void, Integer> {

		ShowProgressDialog objProgressDialog;

		@Override
		protected void onPreExecute() {
			objProgressDialog = new ShowProgressDialog(context,
					getString(R.string.SaveInContractDetails),
					getString(R.string.Wait), 0);
			objProgressDialog.ShowDialog();
		}

		@Override
		protected Integer doInBackground(Void... params) {

			// if (!IsIssueIdValid())
			// return -1;
			if (NoPaid == -1) {
				InsertToContractPublishDetalis(ResultAddContract);
			} else
				InsertContractPublichwithOffer(ResultAddContract);
			return 1;
		}

		@Override
		protected void onPostExecute(Integer result) {
			objProgressDialog.EndDialog();
			if (result == -1) {
				GetToast.Toast(context, getString(R.string.ErrorInData));// "You Have Problem Please Check Date ");
				BtnSave.setEnabled(false);
			} else
				BtnSave.setEnabled(true);
			setResult(1);

//			if(MacAddress.length() == 12) {
			if(MacAddress != null) {
				new PrintTask().execute(MacAddress);
			}else{
				GetToast.Toast(context,getString(R.string.NoPrinterFound).toString());

				Calculate_Result.this.setProgressBarIndeterminateVisibility(false);
				setResult(1);
				Calculate_Result.this.finish();
			}
//			finish();
//			Calculate_Result.this.finish();
//			startActivity(new Intent(Calculate_Result.this, ShowMain.class));

		}
	}// End Calss Insert

	private void InitPrinter() {
		Intent serverIntent = new Intent(context, DeviceList.class);
		startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
	}

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
			Log.d("Error On Copy ", ex.getMessage());
			// GetToast.Toast(context, "Error copying asset files.");
			CatchMsg.WriteMSG("copy Asset File .. ", ex.getMessage());
			return false;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		if (isChecked) {
			if (buttonView.getId() == CheckFreePTax.getId()) {
				TxTPressTaxPer.setText("0.0");
				CalculateNet();
			}// End If
			else {
				TxTSalesTaxPer.setText("0.0");
				CalculateNet();
			}// end Else
		} else {
			TxTPressTaxPer.setText("1.0");
			TxTSalesTaxPer.setText("16.0");
			CalculateNet();
		}
	}

	public class PrintTask extends AsyncTask<String, Integer, String> {
		ShowProgressDialog objProgressDialog;

		@Override
		protected void onPreExecute() {
			objProgressDialog = new ShowProgressDialog(context,
					getString(R.string.Wait));// "Plase wait..");

			objProgressDialog.ShowDialog();

			Calculate_Result.this.setProgressBarIndeterminateVisibility(true);

		}

		@Override
		protected String doInBackground(String... args) {
			try {
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
								800, 0);
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
					try {
						CreateSigntuare();
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
			} catch (Exception e){
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(String result) {
			objProgressDialog.EndDialog();
			if (!(mypathHeader == null))
				mypathHeader.delete();
			if (result != null) {
				GetToast.Toast(getApplicationContext(), result);
			}
			// Dismisses the progress icon on the title bar.
			Calculate_Result.this.setProgressBarIndeterminateVisibility(false);
			setResult(1);
			Calculate_Result.this.finish();
		}
	} // end of class PrintTask

	private void CreateHeader() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidADR");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);

        String ContractTypeName = "عقد نقدي ";

        if(ObjContract.GetContractTypeID() == 8){
            ContractTypeName = "عقد أجل";
        }

		new PrintInterMec().WriteHeaderContract(mFileOutStream,
				ObjContract.GetContractDate(),  NewSerialNo,
				ObjContract, ContractTypeName);
	}

	private void CreateContract() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidADR");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
		new PrintInterMec().WriteOnIMGContractPublich(mFileOutStream, 0,
				ListContractPublish);
	}

	private void CreateResult() throws FileNotFoundException {
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidADR");
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
				+ "/AndroidADR");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);

		Bitmap bitmap = imageViewSigntuare.getDrawingCache();
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);

//		 byte[] te = Base64.decode(ObjContract.GetImage(), 0);
//		 Bitmap Signtuare = BitmapFactory.decodeByteArray(te, 0, te.length);

//		byte[] te = Base64.decode(ObjContract.GetImage(), 0);
//		Bitmap Signtuare = BitmapFactory.decodeByteArray(te, 0, te.length);

		new PrintInterMec().WriteOnIMGSigntuare(mFileOutStream,bitmap);// bitmap);
	}

	private void CreateAlghad() throws FileNotFoundException {

		Bitmap bMap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.okazlogotrans);
		File directory = new File(Environment.getExternalStorageDirectory()
				+ "/AndroidADR");
		if (!directory.exists())
			directory.mkdir(); // directory is created;
		String current = "Data.bmp";
		mypathHeader = new File(directory, current);
		FileOutputStream mFileOutStream = new FileOutputStream(mypathHeader);
		new PrintInterMec().WriteOnIMGAlghad(mFileOutStream, bMap);
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

	class GetIssueDate extends AsyncTask<String, String, String> {
		ShowProgressDialog objProgressDialog;


		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			objProgressDialog = new ShowProgressDialog(Calculate_Result.this,
					getString(R.string.LogIn), getString(R.string.Wait),
					R.drawable.login_icon1);
			objProgressDialog.ShowDialog();

		}

		@Override
		protected String doInBackground(String... params) {
			String x = "";
			try {

				IssueID = new IssueDate().NewCheckIssueDate(params[0],ObjContract.getPublicationID());

			} catch (Exception e) {
				Log.d("Do In Back ", e.getMessage());
			}
			return x;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			objProgressDialog.EndDialog();

		}
	}

	private class GetCRLimit extends AsyncTask<Void, Void, Integer> {

		ShowProgressDialog objProgressDialog;

		@Override
		protected void onPreExecute() {
			objProgressDialog = new ShowProgressDialog(context,
					getString(R.string.Please_Wait),
					getString(R.string.Wait), 0);
			objProgressDialog.ShowDialog();
		}

		@Override
		protected Integer doInBackground(Void... params) {


			ContractCheckCRLimitList = ObjContract.GetCRLimit(CoreLogInActivity.AgencyAccountNo);
			return 1;
		}

		@Override
		protected void onPostExecute(Integer result) {
			objProgressDialog.EndDialog();

		}
	}

	private Boolean CheckCRLimit(){
		try {

			new GetCRLimit().execute();

			Boolean Valid = true;
			Double CRLimit =0.0;
			Double NetAmount = Double.valueOf(TxTNet.getText().toString());

			if (ContractCheckCRLimitList != null){
				if (ContractCheckCRLimitList.size() > 0){
					if (ContractCheckCRLimitList.get(0).isCheckCRLimit()){
						CRLimit = ContractCheckCRLimitList.get(0).getCRLimit();
						if (CRLimit < NetAmount){
							Valid = false;
						}
					}
				}
			}
			return Valid;
		}catch (Exception e){
			e.printStackTrace();
			return true;
		}
	}
}// End Class Calculate_
