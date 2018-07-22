package com.example.amir.core.Collector.Classes;

import android.content.Intent;
import android.net.Uri;

public class Connection {
	public final static String NAMESPACE = "http://tempuri.org/";

	// public final static String ADDRESS =
	// "http://212.118.5.43:9090/Collector/Service.asmx";
	//public final static String ADDRESS = "http://192.168.0.80/AndroidFinance/Service.asmx";

//	public final static String ADDRESS = "http://87.101.143.70:1919/AndroidFinance/";
public final static String ADDRESS = "http://212.119.73.58:1919/AndroidFinance/";

    //"http://192.168.0.89/androidfinance/Service.asmx";
	// public final static String ADDRESS =
	// "http://192.168.0.89/androidfinances";
	// http://192.168.0.89/androidfinance/
	// http://212.118.5.43:9090/Collector
	// http://192.168.0.80/AndroidFinance/Service.asmx

	public static Intent GetProsyURL() {
		Intent browse = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://www.prosysjo.com/"));
		return browse;
	}
}
