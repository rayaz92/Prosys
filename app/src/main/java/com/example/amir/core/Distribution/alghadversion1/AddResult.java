package com.example.amir.core.Distribution.alghadversion1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.amir.core.Distribution.alghadversion1.Classes.CallResult;
import com.example.amir.core.Distribution.alghadversion1.Classes.CallResultReason;
import com.example.amir.core.Distribution.alghadversion1.Classes.CallResultType;
import com.example.amir.core.R;

import java.util.List;

public class AddResult extends Activity implements OnItemClickListener,
		OnClickListener {

	Spinner spinnerResultType, spinnerReason;
	List<CallResultReason> listCallResultReasons;
	List<CallResultType> listCallResultTypes;
	ArrayAdapter<CallResultType> AdapterResultType;
	int CallTypeID, CallResutTypeID, CallResultTypeReasonID, CallID;
	Button BtnSave;
	EditText TxTNote;
	ArrayAdapter<CallResultReason> dataAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distaddresult);
		spinnerResultType = (Spinner) findViewById(R.id.spinnerAddResultResutType);
		spinnerReason = (Spinner) findViewById(R.id.spinneraddResultReason);

		BtnSave = (Button) findViewById(R.id.BtnAddResultSave);
		TxTNote = (EditText) findViewById(R.id.TxTAddResultNote);
		BtnSave.setOnClickListener(this);
		Intent i = getIntent();
		CallTypeID = Integer.valueOf(i.getIntExtra("CallTypeId", 0));
		CallID = Integer.valueOf(i.getIntExtra("CallIdToAddResult", 0));
		// This For Hidden Keyboard
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		FillSpinnerType(CallTypeID);
		spinnerResultType
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long arg3) {
						String Name = (String) parentView.getItemAtPosition(
								position).toString();
						if (Name == "UnKnown") {
							return;
						}// End If
						if (AdapterResultType.getItem(
								spinnerResultType.getSelectedItemPosition())
								.GetID() == 0)
							return;
						// FillSpinnerReason(AdapterResultType.getItem(
						// spinnerResultType.getSelectedItemPosition())
						// .GetID());
						int x = AdapterResultType.getItem(position).GetID();
						FillSpinner(x);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
					// add some code here
				});

	}// End On Create

	// private void FillSpinnerReason(int id) {
	// try {
	// List<Object> list = new ArrayList<Object>();
	// for (CallResultReason e : listCallResultReasons) {
	// if (e.GetCallResultTypeID() == id) {
	// list.add(e.GetNameEn());
	// CallResultTypeReasonID = e.GetID();
	// }// End if
	// }// End for
	// ArrayAdapter<Object> dataAdapter = new ArrayAdapter<Object>(this,
	// android.R.layout.simple_spinner_item, list);
	// dataAdapter
	// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// spinnerReason.setAdapter(dataAdapter);
	// } catch (Exception e) {
	// Log.d("Error in FillSpinnerResona", e.getMessage());
	// }// End Try Catch
	// }// End

	private void FillSpinner(int CallResutlyTypeID) {
		try {
			CallResultReason objCallResultReason = new CallResultReason();
			listCallResultReasons = objCallResultReason
					.SelectCallResultReason(CallResutlyTypeID);
			if (listCallResultReasons == null) {
				dataAdapter.clear();
				Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show();
				return;
			}
			dataAdapter = new ArrayAdapter<CallResultReason>(this,
					android.R.layout.simple_spinner_item, listCallResultReasons);

			spinnerReason.setAdapter(dataAdapter);

		} catch (Exception e) {
			Log.e("addResult fillSpinner", e.getMessage());
			// CatchMsg.WriteMSG("addResult fillSpinner", e.getMessage());
		}// End try Catch
	}// End fill spinner

	private void FillSpinnerType(int typeid) {
		CallResultType objCallResultType = new CallResultType();
		listCallResultTypes = objCallResultType.SelectCallResultType(typeid,
				true);

		if (listCallResultTypes == null) {
			GetToast.Toast(AddResult.this, getString(R.string.NoData));

			return;
		}
		AdapterResultType = new ArrayAdapter<CallResultType>(this,
				android.R.layout.simple_spinner_item, listCallResultTypes);
		AdapterResultType
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerResultType.setAdapter(AdapterResultType);
	}// End Function

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		try {
			String item = parent.getItemAtPosition(position).toString();
			for (CallResultType e : listCallResultTypes) {
				if (e.GetNameEn().equals(item)) {
					CallTypeID = e.GetID();
					Log.d("CallTypeID ", "" + CallTypeID);
				}// End If
			}// End For

		} catch (Exception e) {
			Log.d("Error In On Item Click ", e.getMessage());
		}// End try Catch
	}

	@Override
	public void onClick(View v) {
		InsertCallRuslt();
	}// end On Click

	private void InsertCallRuslt() {
		try {
			CallResult objCallResult = new CallResult();
			if (objCallResult.InsertCallResult(
					CallID,AdapterResultType.getItem(spinnerResultType.getSelectedItemPosition()).GetID(),
							dataAdapter.getItem(spinnerReason.getSelectedItemPosition()).GetID(),
							TxTNote.getText().toString().trim(),
					DistributionMain.UserID).equals(true)) {
				GetToast.Toast(AddResult.this, "Error Save");

			} else {
				GetToast.Toast(AddResult.this, getString(R.string.Save_Done));
				this.finish();
			}
		} catch (Exception e) {
//			Log.e("Add Reulst Insert Call Result() ", e.getMessage());
			// CatchMsg.WriteMSG("Add Reulst Insert Call Result() ",
			// e.getMessage());
		}// End Try Catch

	}// End Insert Call Result
}// End Class
