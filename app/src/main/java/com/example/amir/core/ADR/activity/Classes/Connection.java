package com.example.amir.core.ADR.activity.Classes;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.example.amir.core.R;

public class Connection {
	// http://tempuri.org/
	public final static String NAMESPACE = "http://tempuri.org/";

//	public final static String ADDRESS = "http://87.101.143.70:1919/Andriod%20ADR/";
public final static String ADDRESS = "http://212.119.73.58:1919/Andriod%20ADR/";

	// mail.prosysjo.com:1977/andro
	// http://mail.prosysjo.com:1977/andriod%20adr/
	// http://212.118.7.70:1980
	// http://212.118.5.43:9090/ADRAndriod/Service.asmx
	// 91.106.89.58:1977/adr Android For Out NetWork
	// 198.168.0.20:1977/AdrAndroid in network

	public static Intent GetProsyURL() {
		Intent browse = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://www.prosysjo.com/"));
		return browse;
	}

	public static boolean IsConnectedToInternet = false;

	public static boolean isConnectingToInternet(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						IsConnectedToInternet = true;
						return true;
					}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static void showAlertDialog(Context context) {

		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle("No Internet Connection");
		// Setting alert dialog icon
		// alertDialog.setIcon((status) ? R.drawable.correct_icon :
		// R.drawable.un80);
		alertDialog.setIcon(R.drawable.fail);
		// Setting Dialog Message
		// alertDialog.setMessage("You Dont Have Internet Connection");
		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		// Showing Alert Message
		alertDialog.show();
	}
}
