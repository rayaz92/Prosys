package com.example.amir.core.ADR.activity;

import java.util.ArrayList;
import java.util.List;



import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amir.core.ADR.activity.Classes.Connection;
import com.example.amir.core.ADR.activity.Classes.Discription;
import com.example.amir.core.ADR.activity.Classes.EnglishNameWithAgency;
import com.example.amir.core.R;

public class TextChange implements TextWatcher {

	Context context;
	int TextID;
	AutoCompleteTextView AutoText;
	NewContract contract;
	Calculate calculate;

	public enum ActivityMode {
		Conract, Calculate
	}

	private ActivityMode activityMode;

	public TextChange(Context context, int AutoCompleteTextID,
			AutoCompleteTextView AutoCompleteText, Activity activity,
			ActivityMode mode) {
		this.TextID = AutoCompleteTextID;
		this.context = context;
		this.AutoText = AutoCompleteText;
		contract = (NewContract) activity;
		// Connection.isConnectingToInternet(context);

	}

	public TextChange(Context context, int AutoCompleteTextID,
			AutoCompleteTextView AutoCompleteText, Activity activity) {
		this.TextID = AutoCompleteTextID;
		this.context = context;
		this.AutoText = AutoCompleteText;
		calculate = (Calculate) activity;
		// Connection.isConnectingToInternet(context);

	}

	public TextChange(Context context, int AutoCompleteTextID,
			AutoCompleteTextView AutoCompleteText, int AgencyID, int SectorID,
			Activity activity) {
		this.TextID = AutoCompleteTextID;
		this.context = context;
		contract = (NewContract) activity;
		this.AutoText = AutoCompleteText;
		// Connection.isConnectingToInternet(context);
	}

	public TextChange(ClientManagement clientManagement, int id,
			AutoCompleteTextView autoNameEn) {
		this.TextID = id;
		this.context = clientManagement;
		this.AutoText = autoNameEn;

		// Connection.isConnectingToInternet(context);
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

		if (!Connection.isConnectingToInternet(this.context)) {
			GetToast.Toast(context, "No Internet");
			return;
		}
		switch (TextID) {
		case R.id.txtAutoClientNameEn:
			if (AutoText.getText().length() > 2
					&& AutoText.getText().length() < 12)
				NameEnAutoComplete(AutoText.getText().toString());
			break;
		case R.id.NewContract_AutoTxTClientName:
			if (AutoText.getText().length() > 2
					&& AutoText.getText().length() < 12) {
				ClientNameByAgencyID(AutoText.getText().toString());
			}
			break;
		case R.id.Calculate_AutoTxTPageName:
			if (AutoText.getText().length() >= 5) {
			} else
				SelectDiscriptionAndPrice(AutoText.getText().toString());
			break;
		}
	}

	private void NameEnAutoComplete(String NameEn) {
		List<EnglishNameWithAgency> listEnglishNameWithAgency = null;
		EnglishNameWithAgency objEnglishNameWithAgency = new EnglishNameWithAgency();
		listEnglishNameWithAgency = objEnglishNameWithAgency
				.GetEnglishNameWithAgency(NameEn);
		if (listEnglishNameWithAgency == null) {
			return;
		}
		ArrayAdapter<EnglishNameWithAgency> ArrayClient;
		ArrayClient = new ArrayAdapter<EnglishNameWithAgency>(context,
				android.R.layout.simple_list_item_1, listEnglishNameWithAgency);
		AutoText.setAdapter(ArrayClient);

	}

	// type 1 if new contract

	private void ClientNameByAgencyID(String NameBrief) {

		if (contract.AgencyID == 0) {
			GetToast.Toast(context, context.getString(R.string.SelectAgency));
			return;
		}
		if (contract.SectorID == 0) {
			GetToast.Toast(context, context.getString(R.string.Selectsector));
			return;
		}
		EnglishNameWithAgency objEnglishNameWithAgency = new EnglishNameWithAgency();
		contract.listEnglishNameWithAgency = objEnglishNameWithAgency
				.GetClientNameByAgencyId(contract.AgencyID, contract.SectorID,
						NameBrief);

		if (contract.listEnglishNameWithAgency == null) {
			return;
		}

		contract.ArrayClient = new ArrayAdapter<EnglishNameWithAgency>(context,
				android.R.layout.simple_dropdown_item_1line,
				contract.listEnglishNameWithAgency);
		AutoText.setAdapter(contract.ArrayClient);
	}

	private void SelectDiscriptionAndPrice(String priText) {
		List<Discription> listDiscription = null;
		try {
			Discription objDiscription = new Discription();
			listDiscription = objDiscription
					.SelectDiscriptionAndPriceByPartTypeID(
							calculate.arrayAdapterPageType.getItem(
									calculate.SpinPageType
											.getSelectedItemPosition())
									.GetPart_ID(), priText,
                            calculate.PublicationArrayAdapter.getItem(calculate.SpinPublication.getSelectedItemPosition()).getID());

		} catch (Exception e) {
			Log.d("SelectDiscription CAl", e.getMessage());
		}

		if (listDiscription == null) {
			return;
		} else {
			calculate.ArrayDiscription = new ArrayAdapter<Discription>(context,
					android.R.layout.simple_dropdown_item_1line,
					listDiscription);
			// ArrayDiscription
			// .setDropDownViewResource(android.R.layout.simple_list_item_1);
			AutoText.setAdapter(calculate.ArrayDiscription);
		}
	}
}
