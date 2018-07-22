package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.amir.core.R;


public class Connection {
	public final static String NAMESPACE = "http://tempuri.org/";

//    public final static String ADDRESS = "http://87.101.143.70:1919/androidCRM/";
	public final static String ADDRESS = "https://prosys.alyaum.com:33/androidCRM/";

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
}// End Connection
