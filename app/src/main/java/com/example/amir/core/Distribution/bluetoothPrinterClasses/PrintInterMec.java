package com.example.amir.core.Distribution.bluetoothPrinterClasses;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;


import com.example.amir.core.Distribution.alghadversion1.Classes.CallResultReport;
import com.example.amir.core.Distribution.alghadversion1.Classes.Contract;
import com.example.amir.core.Distribution.alghadversion1.DistributionMain;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PrintInterMec {
	Paint paint;
	Bitmap bitmap1;
	Canvas canvas;

	public PrintInterMec() {

	}

	public void WriteOnIMGDailywork(FileOutputStream mfileOutputStream,
									List<Contract> listDailyWork, String Date) {
		paint = new Paint();
		paint.setTextSize(28);
		paint.setColor(Color.BLACK);

		Bitmap mbitmapData = Bitmap.createBitmap(800,
				((listDailyWork.size() + 5) * 18)
						+ ((listDailyWork.size() + 5) * 43),
				Bitmap.Config.RGB_565);
		canvas = new Canvas(mbitmapData);
		canvas.drawRGB(255, 255, 255);

		String Header = "جريدة الغد";
		float width = paint.measureText(Header, 0, Header.length());
		canvas.drawText(Header, (550 - width) / 2, 40, paint);

		Header = "تقرير اشتراكات التقدي";
		width = paint.measureText(Header, 0, Header.length());
		canvas.drawText(Header, (550 - width) / 2, 80, paint);

		String Header2 = "اليومي للمندوب";
		width = paint.measureText(Header2, 0, Header2.length());
		canvas.drawText(Header2, (550 - width) / 2, 120, paint);

		String DateToDay = "التاريخ :";
		width = paint.measureText(DateToDay + Date, 0,
				(DateToDay + Date).length());
		canvas.drawText(DateToDay + Date, 550 - width, 160, paint);

		String ColName = "اسم المندوب :";
		width = paint.measureText(ColName + DistributionMain.DriverName, 0,
				(ColName + DistributionMain.DriverName).length());
		canvas.drawText(ColName + DistributionMain.DriverName, 550 - width, 200, paint);

		//
		paint.setUnderlineText(true);

		String No = "الرقم ";
		width = paint.measureText(No, 0, No.length());
		canvas.drawText(No, 550 - width, 240, paint);

		String NameHeader = "اسم المشترك";
		width = paint.measureText(NameHeader, 0, NameHeader.length());
		canvas.drawText(NameHeader, (550 - width) / 2, 240, paint);

		String Net = "القيمة";
		width = paint.measureText(Net, 0, Net.length());
		canvas.drawText(Net, 0, 240, paint);

		int x = 1;
		double total = 0.0;
		int y = 280;
		paint.setUnderlineText(false);
		for (Contract e : listDailyWork) {
			width = paint.measureText("" + x, 0, String.valueOf(x).length());
			canvas.drawText("" + x, 550 - width, y, paint);

			String Name = e.getClientName();
			width = paint.measureText(e.getClientName(), 0, e.getClientName()
					.length());
			canvas.drawText(Name, (550 - width) / 2, y, paint);

			canvas.drawText("" + e.GetNetAmount(), 0, y, paint);

			total = e.GetNetAmount() + total;
			x++;
			y = y + 40;
		}
		width = paint.measureText("المجموع ", 0, "المجموع ".length());
		canvas.drawText("المجموع ", (550 - width) / 2, y + 40, paint);
		canvas.drawText("" + total, 0, y + 40, paint);
		mbitmapData.compress(CompressFormat.PNG, 90, mfileOutputStream);
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("WriteOnIMGDailywork", e.getMessage());
			// CatchMsg.WriteMSG("WriteOnIMGDailywork", e.getMessage());
		}
	}

	public void WriteOnIMGDailyCallReport(FileOutputStream mfileOutputStream,
										  List<CallResultReport> listCallResultReport, String Date) {
		paint = new Paint();
		paint.setTextSize(28);
		paint.setColor(Color.BLACK);

		Bitmap mbitmapData = Bitmap.createBitmap(800,
				((listCallResultReport.size() + 5) * 18)
						+ ((listCallResultReport.size() + 5) * 43),
				Bitmap.Config.RGB_565);
		canvas = new Canvas(mbitmapData);
		canvas.drawRGB(255, 255, 255);

		String Header = "جريدة الغد";
		float width = paint.measureText(Header, 0, Header.length());
		canvas.drawText(Header, (550 - width) / 2, 40, paint);

		Header = "تقرير اشتراكات التقدي";
		width = paint.measureText(Header, 0, Header.length());
		canvas.drawText(Header, (550 - width) / 2, 80, paint);

		String Header2 = "اليومي للمندوب";
		width = paint.measureText(Header2, 0, Header2.length());
		canvas.drawText(Header2, (550 - width) / 2, 120, paint);

		String DateToDay = "التاريخ :";
		width = paint.measureText(DateToDay + Date, 0,
				(DateToDay + Date).length());
		canvas.drawText(DateToDay + Date, 550 - width, 160, paint);

		String ColName = "اسم المندوب :";
		width = paint.measureText(ColName + DistributionMain.DriverName, 0,
				(ColName + DistributionMain.DriverName).length());
		canvas.drawText(ColName + DistributionMain.DriverName, 550 - width, 200, paint);

		//
		paint.setUnderlineText(true);

		String No = "الرقم ";
		width = paint.measureText(No, 0, No.length());
		canvas.drawText(No, 550 - width, 240, paint);

		String NameHeader = "اسم المشترك";
		width = paint.measureText(NameHeader, 0, NameHeader.length());
		canvas.drawText(NameHeader, (550 - width) / 2, 240, paint);

		String Net = "النتيجة";
		width = paint.measureText(Net, 0, Net.length());

		canvas.drawText(Net, 0, 240, paint);

		int x = 1;
		int total = 0;
		int y = 280;
		paint.setUnderlineText(false);
		for (CallResultReport e : listCallResultReport) {
			width = paint.measureText("" + x, 0, String.valueOf(x).length());
			canvas.drawText("" + x, 550 - width, y, paint);

			String Name = e.getClientName();
			width = paint.measureText(e.getClientName(), 0, e.getClientName()
					.length());
			canvas.drawText(Name, (550 - width) / 2, y, paint);

			// canvas.drawText("" + 2, 0, y, paint);

			total = total + 1;
			canvas.drawText("" + total, 0, y, paint);
			x++;
			y = y + 40;
		}
		width = paint.measureText("العدد ", 0, "العدد ".length());
		canvas.drawText("العدد ", (550 - width) / 2, y + 40, paint);
		canvas.drawText("" + total, 0, y + 40, paint);
		mbitmapData.compress(CompressFormat.PNG, 90, mfileOutputStream);
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			CatchMsg.WriteMSG("WriteOnIMGDailyCallReport", e.getMessage());
		}
	}

	@SuppressLint("NewApi")
	public void WriteOnIMGAlghad(FileOutputStream mfileOutputStream,
			Contract objContract, String Date) {
		Bitmap bitmapCreated = Bitmap.createBitmap(800, 960,
				Bitmap.Config.RGB_565);

		if (objContract == null)
			objContract = new Contract();
		paint = new Paint();
		paint.setTextSize(22);
		paint.setColor(Color.BLACK);
		canvas = new Canvas(bitmapCreated);

		canvas.drawRGB(255, 255, 255);
		int y = 170;

		canvas.drawText("" + objContract.GetNetAmount(), 0, y, paint);
		String paper = "القيمة";
		float width = paint.measureText(paper)
				+ paint.measureText("" + objContract.GetNetAmount() + paper);

		canvas.drawText("" + objContract.GetContractID(), (750 - width) / 2, y,
				paint);
		y = y + 45;

		// paint.setTextAlign(Align.LEFT);
		String array[];
		if (objContract.GetTime().isEmpty()) {
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			String strDate = sdf.format(c.getTime());
			// Calendar c = Calendar.getInstance();
			// int Time = (c.get(Calendar.HOUR_OF_DAY) * 10000)
			// + (c.get(Calendar.MINUTE) * 100);
			canvas.drawText("" + strDate, 0, y, paint);
		} else {
			array = objContract.GetTime().split("T");
			canvas.drawText("" + array[1].substring(0, 5), 0, y, paint);
		}

		// paint.setTextAlign(Align.RIGHT);

		paper = "التاريخ";
		// width = paint.measureText(paper) + paint.measureText(paper + Date);
		canvas.drawText("" + Date, (750 - width) / 2, y, paint);
		y = y + 40;

		// paint.setTextAlign(Align.LEFT);
		canvas.drawText(objContract.getContractTypeNameEn(), 0, y, paint);
		// paint.setTextAlign(Align.RIGHT);
		paper = "عدد النسخ";
		// width = paint.measureText(paper)
		// + paint.measureText("" + objContract.GetCopiesNo() + paper);

		canvas.drawText("" + objContract.GetCopiesNo(), (750 - width) / 2, y,
				paint);
		y = y + 60;

		// paint.setTextAlign(Align. CENTER);
		width = paint.measureText(objContract.getClientPhone(), 0, objContract
				.getClientPhone().length());
		canvas.drawText(objContract.getClientPhone(), (650 - width) / 2, y,
				paint);

		y = y + 55;
		width = paint.measureText(objContract.GetNationalNo(), 0, objContract
				.GetNationalNo().length());
		canvas.drawText(objContract.GetNationalNo(), (650 - width) / 2, y,
				paint);

		// paint.setTextAlign(Align.LEFT);
		paint.setTextSize(28);

		// paint.setTypeface(Typeface.DEFAULT_BOLD);
		y = y + 110;
		width = paint.measureText(objContract.getClientName(), 0, objContract
				.getClientName().length());
		canvas.drawText(objContract.getClientName(), (550 - width) / 2, y,
				paint);

		y = y + 85;
		width = paint.measureText(objContract.GetCompanyName(), 0, objContract
				.GetCompanyName().length());
		canvas.drawText(objContract.GetCompanyName(), (550 - width) / 2, y,
				paint);

		y = y + 85;
		width = paint.measureText(objContract.GetAddress(), 0, objContract
				.GetAddress().length());
		canvas.drawText(objContract.GetAddress(), (550 - width) / 2, y, paint);

		y = y + 85;
		width = paint.measureText(objContract.GetAddressComments(), 0,
				objContract.GetAddressComments().length());
		canvas.drawText(objContract.GetAddressComments(), (550 - width) / 2, y,
				paint);

		y = y + 85;
		array = objContract.GetFromDate().split("T");
		width = paint.measureText(array[0], 0, array[0].length());
		canvas.drawText(array[0], (550 - width) / 2, y, paint);

		y = y + 85;
		array = objContract.GetToDate().split("T");
		width = paint.measureText(array[0], 0, array[0].length());
		canvas.drawText(array[0], (550 - width) / 2, y, paint);

		y = y + 30;
		String x = "";
		width = paint.measureText(x, 0, x.length());
		canvas.drawText(x, (550 - width) / 2, y, paint);

		bitmapCreated.compress(CompressFormat.PNG, 90, mfileOutputStream);
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			// CatchMsg.WriteMSG("WriteOnIMGAlghad", e.getMessage());
		}

	}

	public void WriteOnIMGSigntuare(FileOutputStream mfileOutputStream,
			Bitmap Signtuare) {
		Bitmap mBitmap = Bitmap.createBitmap(990, 350, Bitmap.Config.RGB_565);
		paint = new Paint();
		paint.setTextSize(28);
		paint.setColor(Color.BLACK);
		canvas = new Canvas(mBitmap);
		canvas.drawRGB(255, 255, 255);
		canvas.drawBitmap(Signtuare, 0, 50, null);
		mBitmap.compress(CompressFormat.PNG, 90, mfileOutputStream);
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {

			e.printStackTrace();
			// CatchMsg.WriteMSG("WriteOnIMGSigntuare", e.getMessage());
		}

	}// Signature

	public void WriteOnIMGSpace(FileOutputStream mfileOutputStream) {
		Bitmap bitmapCreated = Bitmap.createBitmap(800, 265,
				Bitmap.Config.RGB_565);
		// if (objContract == null)
		// objContract = new Contract();
		paint = new Paint();
		paint.setTextSize(22);
		paint.setColor(Color.BLACK);
		canvas = new Canvas(bitmapCreated);

		canvas.drawRGB(255, 255, 255);
		int y = 265;

		canvas.drawText("" + "", 0, y, paint);
		String paper = "القيمة";
		float width = paint.measureText(paper)
				+ paint.measureText(""
						+ "\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n -"
						+ paper);

		canvas.drawText("" + "", (750 - width) / 2, y, paint);

		bitmapCreated.compress(CompressFormat.PNG, 90, mfileOutputStream);
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			// CatchMsg.WriteMSG("WriteOnIMGSpace", e.getMessage());
		}

	}

	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);

		// "RECREATE" THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
				matrix, false);
		return resizedBitmap;
	}

}// End Class
