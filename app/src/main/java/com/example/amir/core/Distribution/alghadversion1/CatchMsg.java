package com.example.amir.core.Distribution.alghadversion1;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;

public class CatchMsg {
	public static void WriteMSG(String CalssName, String ErrorMSG) {
		Calendar c = Calendar.getInstance();
		String Name = getTodaysDate() + "_" + getCurrentTime();

		File diFile = new File(Environment.getExternalStorageDirectory()
				+ "/Alghad");
		if (!diFile.exists())
			diFile.mkdir(); // directory is created;
		File fileLog = new File(diFile, Name + ".txt");
		try {
			Writer writer = new OutputStreamWriter(
					new FileOutputStream(fileLog));
			writer.write("\n" + CalssName + "\n" + ErrorMSG + "\n");
			
			writer.close();
			// raf.close();
		} catch (Exception e) {
			Log.d("Error ", e.getMessage());
		}
	}

	private static String getTodaysDate() {
		final Calendar c = Calendar.getInstance();
		int todaysDate = (c.get(Calendar.YEAR) * 10000)
				+ ((c.get(Calendar.MONTH) + 1) * 100)
				+ (c.get(Calendar.DAY_OF_MONTH));
		Log.w("DATE:", String.valueOf(todaysDate));
		return (String.valueOf(todaysDate));
	}

	private static String getCurrentTime() {
		final Calendar c = Calendar.getInstance();
		int currentTime = (c.get(Calendar.HOUR_OF_DAY) * 10000)
				+ (c.get(Calendar.MINUTE) * 100) + (c.get(Calendar.SECOND));
		return (String.valueOf(currentTime));

	}
}
