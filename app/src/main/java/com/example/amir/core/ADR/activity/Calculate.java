package com.example.amir.core.ADR.activity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.zip.Inflater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLogTags;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amir.core.ADR.activity.Classes.Connection;
import com.example.amir.core.ADR.activity.Classes.Contract;
import com.example.amir.core.ADR.activity.Classes.DesignRequest;
import com.example.amir.core.ADR.activity.Classes.Discription;
import com.example.amir.core.ADR.activity.Classes.Issue;
import com.example.amir.core.ADR.activity.Classes.IssueDate;
import com.example.amir.core.ADR.activity.Classes.PageType;
import com.example.amir.core.ADR.activity.Classes.Publication;
import com.example.amir.core.CoreLogInActivity;
import com.example.amir.core.R;


@SuppressLint("NewApi")
public class Calculate extends AppCompatActivity implements OnClickListener,
		OnTouchListener, OnItemSelectedListener {
	Button BtnDone,btnCalculateSearchPageType;
	EditText TxTCm, TxTCol, TxTPagePrice, TxTPageNo, TxTQty, TxTFreeQty,
			TxTWord, TxTfreq, TxTFirstIssueDate, TxTFirstIssueID,
			TxTTotalPrice, TxTSCm, TxTScol;
	AutoCompleteTextView AutoTxTPageName;
	Spinner SpinPageType , SpinPublication , spFirstIssueDate;
	List<PageType> listPageType;
	ArrayAdapter<PageType> arrayAdapterPageType;
	List<Discription> listDiscription;
	ArrayAdapter<Discription> ArrayDiscription;
	Contract ObjContract = new Contract();
	int Page_type ,IssueID = 0;
	public static int SaveREsult = 0;
	Context context = this;
	AlertDialog alertDialog1;

	List<Issue> IssueList ;
	ArrayAdapter<Issue> IssueArrayAdapter;

	List<Publication> PublicationList ;
	ArrayAdapter<Publication> PublicationArrayAdapter;

	// ///

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adrcalculate);
		InitialView();
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		if (Connection.isConnectingToInternet(context)) {
			new FillSpinners(FillSpinners.ActivityMode.Calculate, this, this)
					.execute();
//			new getIssue().execute();
			new getPublication().execute();
		} else
			Connection.showAlertDialog(context);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
			setResult(1);
			this.finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void InitialView() {
		TxTTotalPrice = (EditText) findViewById(R.id.TxTtotalPrice);
		AutoTxTPageName = (AutoCompleteTextView) findViewById(R.id.Calculate_AutoTxTPageName);
		AutoTxTPageName.addTextChangedListener(new TextChange(this,
				AutoTxTPageName.getId(), AutoTxTPageName, this));

		AutoTxTPageName.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// String s =
				// parent.getItemAtPosition(position).toString().trim();
				AutoTxTPageName.setText(ArrayDiscription.getItem(position)
						.GetDiscription());
				TxTPagePrice.setError(null);
				TxTPagePrice.setText(""
						+ ArrayDiscription.getItem(position).GetPrice());
				Page_type = ArrayDiscription.getItem(position).GetID();
				Log.d("Page Type ", "" + Page_type);
				TxTTotalPrice.setText(TotalPrice());
			}
		});
		// -------BTN--------//
		BtnDone = (Button) findViewById(R.id.BtnCalcualt_Done);
		BtnDone.setOnClickListener(this);
		btnCalculateSearchPageType = (Button) findViewById(R.id.btnCalculateSearchPageType);
		btnCalculateSearchPageType.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
					new GetDescription().execute();
			}
		});
		// -------Edit Text--------13//
		TxTCm = (EditText) findViewById(R.id.TxTCm);
		TxTCm.setText("" + 1);
		TxTCm.setOnTouchListener(this);

		TxTCol = (EditText) findViewById(R.id.TxTCol);
		TxTCol.setText("" + 1);
		TxTCol.setOnTouchListener(this);

//		TxTFirstIssueDate = (EditText) findViewById(R.id.TxTFirstIssDate);
//		TxTFirstIssueDate.setText(DateIssueToday());
//		TxTFirstIssueDate.setOnClickListener(this);
		TxTFirstIssueID = (EditText) findViewById(R.id.TxTFirstIssID);
		TxTFirstIssueID.setText("" + FirstIssueID(DateIssueToday()));

		TxTFreeQty = (EditText) findViewById(R.id.TxTFreeQty);
		TxTFreeQty.setText("" + 0);

		TxTfreq = (EditText) findViewById(R.id.TxTFreq);
		TxTfreq.setText("" + 1);
		TxTPageNo = (EditText) findViewById(R.id.TxTPageNo);
		TxTPageNo.setText("" + 0);

		TxTPagePrice = (EditText) findViewById(R.id.TxTPagePrice);
		TxTPagePrice.setText("" + 0);
		TxTQty = (EditText) findViewById(R.id.TxTQty);
		TxTQty.setText("1");
		TxTQty.setOnTouchListener(this);

		TxTSCm = (EditText) findViewById(R.id.TxTSCm);
		TxTSCm.setText("" + 1);

		TxTScol = (EditText) findViewById(R.id.TxTSCol);
		TxTScol.setText("" + 1);
		TxTWord = (EditText) findViewById(R.id.TxTWord);
		TxTTotalPrice.setText("" + 0);
		TxTWord.setText("" + 0);
		// --------Spinner----------//
		SpinPageType = (Spinner) findViewById(R.id.SpinnerCalculatePageType);
		SpinPublication = (Spinner) findViewById(R.id.SpinPublication);
		SpinPublication.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				if(PublicationList != null){
					new getIssue().execute();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
		spFirstIssueDate = (Spinner) findViewById(R.id.spFirstIssueDate);
		spFirstIssueDate.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

				if (IssueList != null) {
					TxTFirstIssueID.setText(String.valueOf(IssueList.get(i).getIssueNo()));
					IssueID = IssueList.get(i).getID();
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		ObjContract = (Contract) getIntent().getExtras().getSerializable(
				"ObjContract");
		Log.d("Object Ads Type", "" + ObjContract.GetAdsTypeID());

        Double DesignCm = getIntent().getDoubleExtra("DesignCm", 0.0);
        Integer DesignCol = getIntent().getIntExtra("DesignCol" , 0);

        if(DesignCm > 0.0 && DesignCol > 0.0){
            TxTCm.setEnabled(false);
            TxTCm.setText(String.valueOf(DesignCm));

            TxTCol.setEnabled(false);
            TxTCol.setText(String.valueOf(DesignCol));
        }
	}

	@SuppressLint("SimpleDateFormat")
	private int FirstIssueID(String dateIssue) {

//		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//		format.setTimeZone(TimeZone.getTimeZone("GMT"));
//		java.util.Date date;



		int id = 0;
		try {
//			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date newDate = format.parse(dateIssue);

			format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(newDate);

//			date = format.parse(dateIssue);
//			if (Connection.IsConnectedToInternet) {
//				id = new IssueDate().NewCheckIssueDate(date);
//			} else
//				GetToast.Toast(context, getString(R.string.NoInternet));
			// new GetToast(Calculate.this, "" + date + "-*-" + id);

			if(PublicationList != null) {
				new GetIssueDate().execute(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (IssueID == 1 || IssueID == 0) {
			GetToast.Toast(context, getString(R.string.ThisDateIsClosed));// "This Issue Is Close");
			return 0;
		}
		// else if (id == 0) {
		// GetToast.Toast(context, "Please Insert This Date In Issue");
		// return 0;
		// }
		return id;
	}

	private String TotalPrice() {
		String Result = "";
		try {
			if (arrayAdapterPageType.getItem(
					SpinPageType.getSelectedItemPosition()).GetPart_ID() == 41) {
				Result = "" + Double.valueOf(TxTPagePrice.getText().toString())
						* Double.valueOf(TxTQty.getText().toString())
						* Double.valueOf(TxTWord.getText().toString());
			} else if (arrayAdapterPageType.getItem(
					SpinPageType.getSelectedItemPosition()).GetPart_ID() == 42)
				Result = "" + Double.valueOf(TxTPagePrice.getText().toString())
						* Double.valueOf(TxTQty.getText().toString())
						* Double.valueOf(TxTWord.getText().toString())
						+ (5 * Integer.valueOf(TxTQty.getText().toString()));
			else
				Result = "" + Double.valueOf(TxTPagePrice.getText().toString())
						* Double.valueOf(TxTQty.getText().toString())
						* Double.valueOf(TxTCm.getText().toString())
						* Integer.valueOf(TxTCol.getText().toString());
		} catch (Exception e) {
			Log.d("", e.getMessage());
		}
		return Result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// menu.add("Calculate");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.adrcalculate, menu);
		return true;

		// return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.BtnCalcualt_Done:
			Intent Calculate_Result = new Intent(Calculate.this,
					Calculate_Result.class);
			if (IsValid()) {
				Calculate_Result.putExtra("Total",
						Double.valueOf(TxTTotalPrice.getText().toString()));
				Calculate_Result.putExtra("Freq",
						Integer.parseInt(TxTfreq.getText().toString()));
				Calculate_Result.putExtra("FirstIssueDate", spFirstIssueDate.getSelectedItem().toString());
//						.getText().toString());
				Calculate_Result.putExtra("FirstIssueID",
						Integer.valueOf(TxTFirstIssueID.getText().toString()));
				// Integer.valueOf(TxTFirstIssueID.getText().toString()));
				Calculate_Result.putExtra("Qty",
						Integer.valueOf(TxTQty.getText().toString()));
				Calculate_Result.putExtra("Free",
						Integer.valueOf(TxTFreeQty.getText().toString()));
				Calculate_Result.putExtra("PageNo",String.valueOf(TxTPageNo).toString());
				if (fillObject()) {
					Bundle bundle = new Bundle();
					bundle.putSerializable("ObjContract",
							(Serializable) ObjContract);
					Calculate_Result.putExtras(bundle);
					startActivityForResult(Calculate_Result, SaveREsult);// (Calculate_Result);
				} else
					GetToast.Toast(context, getString(R.string.ErrorInData));
			}
			break;
//		case R.id.TxTFirstIssDate:
//			Dialog(TxTFirstIssueDate);
//			break;
		}
	}

	protected void Dialog(final TextView _textDialog) {
		Calendar calendar = Calendar.getInstance();
		int Year = calendar.get(Calendar.YEAR);
		int Month = calendar.get(Calendar.MONTH);
		int Day = calendar.get(Calendar.DAY_OF_MONTH) + 1;
		final DatePickerDialog.OnDateSetListener dateSetListener;
		dateSetListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				int monthPlusone = (monthOfYear + 1);
				String Date = "" + dayOfMonth + "/" + monthPlusone + "/" + year;
				_textDialog.setText(Date);
				 FirstIssueID(Date);
				if (IssueID == 0) {
					TxTFirstIssueID
							.setError(getString(R.string.ThisDateIsClosed));// "This Issue Is Close");
					TxTFirstIssueID.setText("" + IssueID);
				}// End if
				else {
					TxTFirstIssueID.setText("" + IssueID);
					TxTFirstIssueID.setError(null);
				}// End Else
			}
		};
		new DatePickerDialog(Calculate.this, dateSetListener, Year, Month, Day)
				.show();
	}

	private boolean fillObject() {
        try{
		ObjContract.SetCm(Double.valueOf(TxTCm.getText().toString()));// -1-
		ObjContract.SetCol(Integer.valueOf(TxTCol.getText().toString()));// -2-
		ObjContract.SetPageTypeID(arrayAdapterPageType.getItem(
				SpinPageType.getSelectedItemPosition()).GetPart_ID());// -3-
		ObjContract.SetWord(Integer.valueOf(TxTWord.getText().toString()));// -4-
		ObjContract.SetFree(Integer.valueOf(TxTFreeQty.getText().toString()));// -5-
		ObjContract.SetSCm(Integer.valueOf(TxTSCm.getText().toString()));// -6-
		ObjContract.SetSCol(Integer.valueOf(TxTScol.getText().toString()));// -7-
		ObjContract.SetPage_Type(Page_type);// -8-
		ObjContract.setPublicationID(PublicationList.get(SpinPublication.getSelectedItemPosition()).getID());

//		int id = Integer.valueOf(FirstIssueID(TxTFirstIssueDate.getText()
//				.toString()));
		if (IssueID > 0)
			ObjContract.SetFirstIssue(IssueID);
		else {
			return false;
		}

		ObjContract.SetQtyIss(Integer.valueOf(TxTQty.getText().toString()));// -10-

		ObjContract.SetGrossTotal(Double.valueOf(TxTTotalPrice.getText()
				.toString()));// -11-
		ObjContract.SetPriceIss(ObjContract.GetGrossTotal()
				/ ObjContract.GetQtyIss());// -12-
//		ObjContract.SetIssueDate(TxTFirstIssueDate.getText().toString());
		ObjContract.SetIssueDate(spFirstIssueDate.getSelectedItem().toString());

		return true;
	}catch (Exception e){
            e.printStackTrace();
            return false;
	    }
    }

	@SuppressLint("SimpleDateFormat")
	private String DateIssueToday() {
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		return sdf.format(c.getTime());
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		v.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus)
					if (IsValid())
						TxTTotalPrice.setText(TotalPrice());
			}
		});
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		return false;
	}

	private Boolean IsValid() {
        try {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            if (!(TxTPagePrice.getText().toString().equals(""))) {
                if (Double.valueOf(TxTPagePrice.getText().toString()) == 0.0) {
                    TxTPagePrice
                            .setError(getString(R.string.Please_Enter_Page_Name));
                    return false;
                }
            } else {
                TxTPagePrice.setError(getString(R.string.Please_Enter_Page_Name));
                return false;
            }
            if (!(TxTCm.getText().toString().equals(""))) {
                if (Double.valueOf(TxTCm.getText().toString()) == 0) {
                    TxTCm.setError(getString(R.string.PleaseEnterCm));
                    return false;
                }// End If 0
            } else {
                TxTCm.setError(getString(R.string.PleaseEnterCm));
                return false;
            }
            if (!(TxTCol.getText().toString().equals(""))) {
                if (Integer.valueOf(TxTCol.getText().toString()) == 0) {
                    TxTCol.setError(getString(R.string.PleaseEnterCol));
                    return false;
                }// End if 0
            } else {
                TxTCol.setError(getString(R.string.PleaseEnterCol));
                return false;
            }
            if (!(TxTQty.getText().toString().equals(""))) {
                if (Integer.valueOf(TxTQty.getText().toString()) == 0) {
                    TxTQty.setError(getString(R.string.QtyIsNotValid));
                    return false;
                }
            } else {
                TxTQty.setError(getString(R.string.PleaseEnterQty));
                return false;
            }

            if (TxTFirstIssueID.getText().toString().equals("0"))
                return false;
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

	@Override
	public void onItemSelected(AdapterView<?> arg0, View view, int postion,
			long id) {
		arrayAdapterPageType.getItem(SpinPageType.getSelectedItemPosition())
				.GetPart_ID();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	class GetDescription extends AsyncTask<String, String, String> {
		ShowProgressDialog objProgressDialog;
		String Auto = AutoTxTPageName.getText()
				.toString();

		int Page_typeID = arrayAdapterPageType.getItem(SpinPageType.getSelectedItemPosition()).GetPart_ID();
        int PublicationID   =   PublicationArrayAdapter.getItem(SpinPublication.getSelectedItemPosition()).getID();
		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			objProgressDialog = new ShowProgressDialog(Calculate.this,
					getString(R.string.Loading), getString(R.string.Wait),0);
			objProgressDialog.ShowDialog();

		}

		@Override
		protected String doInBackground(String... params) {
			String x = "";
			try {

				listDiscription = new Discription().SelectDiscriptionAndPriceByPartTypeID(Page_typeID,Auto,PublicationID);

			} catch (Exception e) {
				Log.d("Do In Back ", e.getMessage());
			}
			return x;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			objProgressDialog.EndDialog();
			AutgetView();
		}
	}

	private void AutgetView(){
		try{

//			listArrayClient = new EnglishNameWithAgency().GetEnglishNameWithAgency(AutoClientName.getText().toString().trim());

			if(listDiscription != null) {
				ArrayDiscription = new ArrayAdapter<Discription>(context,
						android.R.layout.simple_spinner_dropdown_item,
						listDiscription);
			}
			onCreateDialogSingleChoice(ArrayDiscription);
//			Dialog AreaChoice=	onCreateDialogSingleChoice(onCreateDialogSingleChoice);
//			AreaChoice.show();

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void onCreateDialogSingleChoice(ArrayAdapter adapterArea){


		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setSingleChoiceItems(adapterArea, 0, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						//TODO Auto-generated method stub
						ListView lw = ((AlertDialog)dialog).getListView();
						Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());

						Discription Selected = (Discription)checkedItem;

						Toast.makeText(getApplicationContext(),
								checkedItem.toString(), Toast.LENGTH_SHORT).show();
						//txtSearchArea.setText(checkedItem.toString());
						AutoTxTPageName.setText(Selected.GetDiscription());
//						ObjContract.SetClientName(Selected.GetNameEnglish());

						TxTPagePrice.setError(null);
						TxTPagePrice.setText(""
								+ Selected.GetPrice());
						Page_type = Selected.GetID();
//						Log.d("Page Type ", "" + Page_type);
						TxTTotalPrice.setText(TotalPrice());
						alertDialog1.dismiss();
					}
				});
		alertDialog1 = builder.create();
		alertDialog1.show();

	}

	class GetIssueDate extends AsyncTask<String, String, String> {
		ShowProgressDialog objProgressDialog;

		Integer PublicationID = PublicationList.get(SpinPublication.getSelectedItemPosition()).getID() ;

		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			objProgressDialog = new ShowProgressDialog(Calculate.this,
					getString(R.string.Please_Wait), getString(R.string.Wait),0);
			objProgressDialog.ShowDialog();

		}

		@Override
		protected String doInBackground(String... params) {
			String x = "";
			try {

				IssueID = new IssueDate().NewCheckIssueDate(params[0],PublicationID);

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

//	public Dialog onCreateDialogSingleChoice(ArrayAdapter adapterArea) {
//
//
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//		builder.setTitle("Select")
//				.setSingleChoiceItems(adapterArea, 0, new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						//TODO Auto-generated method stub
//
//					}
//				})
//				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int id) {
//						ListView lw = ((AlertDialog)dialog).getListView();
//						Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
//
//						Discription Selected = (Discription)checkedItem;
//
//						Toast.makeText(getApplicationContext(),
//								checkedItem.toString(), Toast.LENGTH_SHORT).show();
//						//txtSearchArea.setText(checkedItem.toString());
//						AutoTxTPageName.setText(Selected.GetDiscription());
////						ObjContract.SetClientName(Selected.GetNameEnglish());
//
//						TxTPagePrice.setError(null);
//						TxTPagePrice.setText(""
//								+ Selected.GetPrice());
//						Page_type = Selected.GetID();
////						Log.d("Page Type ", "" + Page_type);
//						TxTTotalPrice.setText(TotalPrice());
//
//
//					}
//				})
//				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int id) {
//						dialog.cancel();
//					}
//				});
//		return builder.create();
//	}

	class getIssue extends AsyncTask<String, String, String> {
		ShowProgressDialog objProgressDialog;

		Integer PublicationID = PublicationList.get(SpinPublication.getSelectedItemPosition()).getID() ;


		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			objProgressDialog = new ShowProgressDialog(Calculate.this,
					getString(R.string.Please_Wait), getString(R.string.Wait),0);
			objProgressDialog.ShowDialog();

		}

		@Override
		protected String doInBackground(String... params) {
			String x = "";
			try {

				Issue objIssue = new Issue();
				if(PublicationID > 0 ) {
					IssueList = objIssue.getIssue(PublicationID , false);
				}

			} catch (Exception e) {
				Log.d("Do In Back ", e.getMessage());
			}
			return x;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			objProgressDialog.EndDialog();
			if(IssueList != null){
				IssueArrayAdapter = new ArrayAdapter<Issue>(Calculate.this,
						android.R.layout.simple_spinner_item, IssueList);
				IssueArrayAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				spFirstIssueDate.setAdapter(IssueArrayAdapter);
			}
		}
	}

	class getPublication extends AsyncTask<String, String, String> {
		ShowProgressDialog objProgressDialog;


		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			objProgressDialog = new ShowProgressDialog(Calculate.this,
					getString(R.string.Please_Wait), getString(R.string.Wait),0);
			objProgressDialog.ShowDialog();

		}

		@Override
		protected String doInBackground(String... params) {
			String x = "";
			try {

				Publication objIssue = new Publication();
				PublicationList = objIssue.getPublication();


			} catch (Exception e) {
//				Log.d("Do In Back ", e.getMessage());
			}
			return x;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			objProgressDialog.EndDialog();
			if(PublicationList != null){
				PublicationArrayAdapter = new ArrayAdapter<Publication>(Calculate.this,
						android.R.layout.simple_spinner_item, PublicationList);
				PublicationArrayAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				SpinPublication.setAdapter(PublicationArrayAdapter);
                for (int i = 0 ; i < PublicationList.size() ; i++){
                    int PubID = PublicationList.get(i).getID();
                    if(PubID == CoreLogInActivity.DefaultPublication){
                        SpinPublication.setSelection(i);
                        break;
                    }
                }
			}
		}
	}
}
