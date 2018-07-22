package com.example.amir.core.ADR.activity;

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
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.amir.core.ADR.activity.Classes.CatchMsg;
import com.example.amir.core.ADR.activity.Classes.Contract;
import com.example.amir.core.ADR.activity.Classes.ContractPublishDetails;
import com.example.amir.core.ADR.activity.Classes.Issue;
import com.example.amir.core.ADR.activity.Classes.IssueDate;
import com.example.amir.core.ADR.activity.Classes.Publication;
import com.example.amir.core.R;

public class ShowList extends Activity {
	ListView listView;
	TextView TvCount, TvPrice, TvError;
	Button BtnSave;
	DatePickerDialog.OnDateSetListener dateSetListener;
	listAdapter adapter;
	Contract ObjContract;
	int freq, totalQty;
	String Date;
	double total, PriceForOne;
	double Net;
	ArrayList<ContractPublishDetails> listContractPublishDetails = new ArrayList<ContractPublishDetails>();
	List<Publication> PublicationList ;
	ArrayAdapter<Publication> PublicationArrayAdapter;
	int IssueID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adrlist);
		InitialView();
	}

	@SuppressWarnings("unchecked")
	private void InitialView() {
		listView = (ListView) findViewById(R.id.listView1);
		TvError = (TextView) findViewById(R.id.tvError_list);
		BtnSave = (Button) findViewById(R.id.list_BtnSave);
		BtnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("Obj", listContractPublishDetails);
				setResult(200, intent);
				finish();
			}
		});
		ObjContract = (Contract) getIntent().getExtras().getSerializable(
				"ObjContract");
		Intent i = getIntent();
		freq = i.getIntExtra("Freq", 0);
		listContractPublishDetails = (ArrayList<ContractPublishDetails>) i
				.getSerializableExtra("ContractPublish");

		String firstDate = ObjContract.GetIssueDate();

		totalQty = ObjContract.GetFree() + ObjContract.GetQtyIss();

		total = ObjContract.GetGrossTotal();

		PriceForOne = (ObjContract.GetNetAmount() / ObjContract.GetQtyIss());
		BigDecimal v = new BigDecimal(PriceForOne).setScale(3,
				BigDecimal.ROUND_HALF_UP);
		PriceForOne = v.doubleValue();
		Date = firstDate;

		TvCount = (TextView) findViewById(R.id.tvShowCount);
		TvCount.setText("" + ObjContract.GetQtyIss());
		TvPrice = (TextView) findViewById(R.id.tvShowtotal);
		TvPrice.setText("" + total);

		adapter = new listAdapter(ShowList.this, R.layout.adrlist_item,
				listContractPublishDetails);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				TextView tvID = (TextView) view.findViewById(R.id.tvShowID);
				TextView tvDialog = (TextView) view
						.findViewById(R.id.tvShowDate);
				Dialog(tvID, tvDialog, position);
			}
		});
	}

	@Override
	public void onBackPressed() {

		// super.onBackPressed();
	}

	@SuppressLint("SimpleDateFormat")
	private int DateValid(String dateAfter, final TextView tvDate) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

//		format.setTimeZone(TimeZone.getTimeZone("GMT"));

//		SimpleDateFormat newformat = new SimpleDateFormat("yyyy-MM-dd" , Locale.ENGLISH);
//		Date newDate = format.parse(dateAfter);
//
//		format = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
//		String date = format.format(newDate);

		java.util.Date d;
		int id = 0;
		try {
//			d = format.parse(dateAfter);
//			id = new IssueDate().CheckIssueDate(d);
			new GetIssueDate().execute(dateAfter);
		} catch (Exception e) {
			new CatchMsg();

			CatchMsg.WriteMSG("Date Valid Show list ", e.getMessage());
			e.printStackTrace();
		}
//		if (id == 1) {
//			TvError.setText(getString(R.string.ThisDateIsClosed));
//			TvError.setTextColor(Color.RED);
//			tvDate.setError(getString(R.string.ThisDateIsClosed));
//			BtnSave.setEnabled(false);
//			return 0;
//		} else if (id == 0) {
//			TvError.setText(getString(R.string.ThisDateIsNotValied));
//			TvError.setTextColor(Color.RED);
//			tvDate.setError(getString(R.string.ThisDateIsNotValied));
//			BtnSave.setEnabled(false);
//			return 0;
//		}
//		tvDate.setError(null);
//		TvError.setText("");
//		BtnSave.setEnabled(true);
		return IssueID;
	}

	@SuppressLint("SimpleDateFormat")
	private int FirstIssueID(String dateIssue) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date d;
		int id = 0;
		try {
			d = format.parse(dateIssue);
			id = new IssueDate().CheckIssueDate(d);
		} catch (ParseException e) {
			new CatchMsg();
			CatchMsg.WriteMSG("Show List ", e.getMessage());
			e.printStackTrace();
		}
		// int id = new IssueDate().CheckIssueDate(d);
		if (id == 1 || id == 0) {
			TvError.setText(getString(R.string.YouHaveErrorInDate));
			TvError.setTextColor(Color.RED);
			return 0;
		}
		// else if (id == 0) {
		// TvError.setText("You Have error In Date ..");
		// TvError.setTextColor(Color.RED);
		// return 0;
		// }
		TvError.setText("");
		return id;
	}

	private class listAdapter extends ArrayAdapter<ContractPublishDetails> {

		private Context context;

		public listAdapter(Context context, int resource,
				ArrayList<ContractPublishDetails> ArrContractPublishLists) {
			super(context, resource, ArrContractPublishLists);
			this.context = context;
			// ArrayListContractPublishAdapter = ArrContractPublishLists;

			// TODO Auto-generated constructor stub
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			// return ArrayListContractPublishAdapter.size();
			return listContractPublishDetails.size();
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup viewGroup) {

			final holder objholder;

			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.adrlist_item, viewGroup,
					false);
			objholder = new holder();
			objholder.tvDate = (TextView) convertView
					.findViewById(R.id.tvShowDate);

			objholder.tvID = (TextView) convertView.findViewById(R.id.tvShowID);

			objholder.tvPrice = (TextView) convertView
					.findViewById(R.id.tvShowPrice);

			objholder.imageView = (ImageView) convertView
					.findViewById(R.id.imageEditeDate);

			objholder.tvDate.setText(listContractPublishDetails.get(position)
					.GetIssueDate());
			objholder.tvID.setText(""
					+ listContractPublishDetails.get(position).GetIssueID());

			if (listContractPublishDetails.get(position).GetIssueID() == 0) {
				objholder.tvID.setError("Please Insert This Date");
			}
			objholder.tvPrice.setText(""
					+ listContractPublishDetails.get(position).GetIssuePrice());
			objholder.imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					Dialog(objholder.tvID, objholder.tvDate, position);
				};
			});
			return convertView;
		}
	}// End Class List

	protected void Dialog(final TextView tvID, final TextView _textDialog,
			final int position) {

		Calendar calendar = Calendar.getInstance();
		final int Year = calendar.get(Calendar.YEAR);
		final int Month = calendar.get(Calendar.MONTH);
		final int Day = calendar.get(Calendar.DAY_OF_MONTH);
		final DatePickerDialog.OnDateSetListener dateSetListener;
		dateSetListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				try {
					int monthPlusone = (monthOfYear + 1);
//					String DateAfter = "" + dayOfMonth + "/" + monthPlusone + "/"
//							+ year;

					String DateAfter = "" + year + "-" + monthPlusone + "-"
							+ dayOfMonth;

					tvID.setText("" + DateValid(DateAfter, _textDialog));
					_textDialog.setText(DateAfter);
					listContractPublishDetails.get(position).SetIssueDate(
							_textDialog.getText().toString());
					listContractPublishDetails.get(position).SetIssueID(
							Integer.valueOf(tvID.getText().toString()));
					// listContractPublishDetails.get(position).SetIssueID(
					// Integer.valueOf(tvID.getText().toString()));
//					Log.d("Id From Object List", ""
//							+ listContractPublishDetails.get(position).GetIssueID());
					adapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		};
		new DatePickerDialog(ShowList.this, dateSetListener, Year, Month, Day)
				.show();
	}

	public static class holder {
		TextView tvDate;
		TextView tvID;
		TextView tvPrice;
		ImageView imageView;

		public holder() {
			// TODO Auto-generated constructor stub
		}

		public holder(TextView textView, TextView tvid, TextView tvprice,
				ImageView imageView) {
			tvDate = textView;
			tvID = tvid;
			tvPrice = tvprice;
			this.imageView = imageView;
		}

		public void setEditText(TextView t) {
			this.tvDate = t;
		}

		public void settvID(TextView tvID) {
			this.tvID = tvID;
		}

		public void setImageView(ImageView imageView) {
			this.imageView = imageView;
		}

		public void settvPrice(TextView tvPrice) {
			this.tvPrice = tvPrice;
		}

		public TextView getTextView() {
			return this.tvDate;
		}

		public ImageView getImageView() {
			return this.imageView;
		}

		public TextView getTvID() {
			return this.tvID;
		}

		public TextView gettvprice() {
			return this.tvPrice;
		}
	}

//	class getIssue extends AsyncTask<String, String, String> {
//		ShowProgressDialog objProgressDialog;
//
//		Integer PublicationID = PublicationList.get(SpinPublication.getSelectedItemPosition()).getID() ;
//
//
//		@Override
//		protected void onPreExecute() {
//			// super.onPreExecute();
//			objProgressDialog = new ShowProgressDialog(ShowList.this,
//					getString(R.string.Please_Wait), getString(R.string.Wait),0);
//			objProgressDialog.ShowDialog();
//
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			String x = "";
//			try {
//
//				Issue objIssue = new Issue();
//				if(PublicationID > 0 ) {
//					IssueList = objIssue.getIssue(PublicationID , false);
//				}
//
//			} catch (Exception e) {
//				Log.d("Do In Back ", e.getMessage());
//			}
//			return x;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			// TODO Auto-generated method stub
//			objProgressDialog.EndDialog();
//			if(IssueList != null){
//				IssueArrayAdapter = new ArrayAdapter<Issue>(Calculate.this,
//						android.R.layout.simple_spinner_item, IssueList);
//				IssueArrayAdapter
//						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//				spFirstIssueDate.setAdapter(IssueArrayAdapter);
//			}
//		}
//	}

	class GetIssueDate extends AsyncTask<String, String, String> {
		ShowProgressDialog objProgressDialog;


		@Override
		protected void onPreExecute() {
			// super.onPreExecute();
			objProgressDialog = new ShowProgressDialog(ShowList.this,
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

			int id = 0;
			if (IssueID == 1) {
				TvError.setText(getString(R.string.ThisDateIsClosed));
				TvError.setTextColor(Color.RED);
//				tvDate.setError(getString(R.string.ThisDateIsClosed));
				BtnSave.setEnabled(false);
//				return 0;
			} else if (IssueID == 0) {
				TvError.setText(getString(R.string.ThisDateIsNotValied));
				TvError.setTextColor(Color.RED);
//				tvDate.setError(getString(R.string.ThisDateIsNotValied));
				BtnSave.setEnabled(false);
//				return 0;
			}
//			tvDate.setError(null);
			TvError.setText("");
			BtnSave.setEnabled(true);
//			return id;
		}
	}
}
