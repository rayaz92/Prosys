package com.example.amir.core.Collector.Classes;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class GetToast {
	Context context;

	public GetToast(Context context, String MSG) {
		Toast toast = Toast.makeText(context, MSG,
				Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

}
