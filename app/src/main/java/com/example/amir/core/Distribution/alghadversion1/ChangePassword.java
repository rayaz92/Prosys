package com.example.amir.core.Distribution.alghadversion1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.amir.core.Distribution.alghadversion1.Classes.Users;
import com.example.amir.core.R;


public class ChangePassword extends Activity implements OnClickListener {
	EditText TxTOldPass, TxTNewPass, TxTConfirmPass;
	TextView tvUserName, tvDriverName, tvError;
	Button BtnSave, BtnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.distchangepassword);
		TxTOldPass = (EditText) findViewById(R.id.TxTChangePassOldPass);
		TxTNewPass = (EditText) findViewById(R.id.TxTChangePassNewPassword);
		TxTConfirmPass = (EditText) findViewById(R.id.TxTChangePassConfirmPass);

		tvUserName = (TextView) findViewById(R.id.tvUserNameChangePass);
		tvDriverName = (TextView) findViewById(R.id.tvDriverNameChangePass);
		tvError = (TextView) findViewById(R.id.TvErrorChangePass);

		tvDriverName.setText(DistributionMain.DriverName);
		tvUserName.setText(DistributionMain.UserName);

		BtnSave = (Button) findViewById(R.id.BtnChangePassSave);
		BtnCancel = (Button) findViewById(R.id.BTNChangePassCancel);
		BtnSave.setOnClickListener(this);
		BtnCancel.setOnClickListener(this);

	}// End On Create

	@Override
	public void onClick(View v) {
		tvError.setText("");
		if (v.getId() == BtnSave.getId()) {
			if (!IsEmptyText()) {
				if (TxTNewPass.getText().toString().equals(TxTConfirmPass.getText().toString())) {
					UpdatePassword();
				}// End If
				else {
					tvError.setTextColor(Color.RED);
					tvError.setText(getString(R.string.Password_Not_Confirmation));
				}
			}// End If Empty Text
			else
				tvError.setText(getString(R.string.Enter_Old_And_New_Pass));
		}// End If

		if (v.getId() == BtnCancel.getId()) {
			this.finish();
		}// End If Btn Cancel
	}// End On click

	public Boolean IsEmptyText() {
		if (TxTNewPass.getText().toString().trim().equals("")
				&& TxTOldPass.getText().toString().trim().equals(""))
			return true;
		else
			return false;
	}// End Is Empty

	private void UpdatePassword() {
		try {
			Users objUsers = new Users();
			if (objUsers.UpdatePassword(DistributionMain.UserID, TxTConfirmPass.getText()
					.toString().trim())) {
				tvError.setText(getString(R.string.Done_Up_date));
				tvError.setTextColor(Color.BLACK);
			}// end If
			else {
				tvError.setText(getString(R.string.Unable_To_Update));
				tvError.setTextColor(Color.RED);
			}
		} catch (Exception e) {
			Log.d("Erro in Update Pass", e.getMessage());
			tvError.setText(e.getMessage());
		}// End Try Catch

	}// End Update
}// End Change Password
