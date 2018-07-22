package com.example.amir.core.Collector.printing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Bitmap.CompressFormat;

import com.example.amir.core.Collector.Adapter.AdapterListPayment;
import com.example.amir.core.Collector.Adapter.PaymentItem;
import com.example.amir.core.Collector.Classes.DailyWorkClass;
import com.example.amir.core.Collector.Classes.GNPAY;
import com.example.amir.core.Collector.Collector.LoginActivity;
import com.example.amir.core.CoreLogInActivity;


public class PrintInterMec {
	Paint paint;
	Bitmap bitmap1;
	Canvas canvas;

	public void WriteOnIMGDailywork(FileOutputStream mfileOutputStream,
                                    List<DailyWorkClass> listGNPAY, String Date) {
//									List<GNPAY> listGNPAY, String Date) {
		Paint paint = new Paint();
		paint.setTextSize(28);
		paint.setColor(Color.BLACK);

		Bitmap mbitmapData = Bitmap.createBitmap(800,
				((listGNPAY.size() + 5) * 18) + ((listGNPAY.size() + 5) * 40),
				Bitmap.Config.RGB_565);

		Canvas canvas = new Canvas(mbitmapData);
		canvas.drawRGB(255, 255, 255);

		String Header = "التقرير اليومي للمحصلين";
		float width = paint.measureText(Header, 0, Header.length());
		canvas.drawText(Header, (550 - width) / 2, 40, paint);

		String DateToDay = "التاريخ :";
		width = paint.measureText(DateToDay + Date, 0,
				(DateToDay + Date).length());
		canvas.drawText(DateToDay + Date, 550 - width, 80, paint);

		String ColName = "اسم المحصل :";
//		width = paint.measureText(ColName + LoginActivity.coll_name, 0,
//				(ColName + LoginActivity.coll_name).length());
//		canvas.drawText(ColName + LoginActivity.coll_name, 550 - width, 120,
//				paint);

		width = paint.measureText(ColName + CoreLogInActivity.UserName, 0,
				(ColName + CoreLogInActivity.UserName).length());
		canvas.drawText(ColName + CoreLogInActivity.UserName, 550 - width, 120,
				paint);

		String No = "الرقم ";
		width = paint.measureText(No, 0, No.length());
		canvas.drawText(No, 550 - width, 160, paint);

		String clientName = "اسم الزبون ";
		width = paint.measureText(clientName, 0, clientName.length());
		canvas.drawText(clientName, (550 - width) / 2, 160, paint);

		String Amount = "القيمة";
		width = paint.measureText(Amount, 0, Amount.length());
		canvas.drawText(Amount, 0, 160, paint);
		int x = 1;
		double totale = 0.0;
		int y = 200;
//		for (GNPAY e : listGNPAY) {
        for (DailyWorkClass e : listGNPAY) {
			width = paint.measureText("" + x, 0, String.valueOf(x).length());
			canvas.drawText("" + x, 550 - width, y, paint);

			width = paint.measureText(e.getClientName(), 0, e.getClientName()
					.length());
			String Name = e.getClientName();

			// while (((550 - width) / 2 > 300)) {
			// Name = e.getClientName().substring(0,
			// e.getClientName().length() - 1);
			// width = paint.measureText(Name, 0, Name.length());
			//
			// }
			canvas.drawText(Name, (550 - width) / 2, y, paint);

			width = paint.measureText("" + e.getCR(), 0,
					("" + e.getCR()).length());
			canvas.drawText("" + e.getCR(), 0, y, paint);
			x = x + 1;
			y = y + 40;
			totale = totale + e.getCR();
		}
		canvas.drawText("المجموع " + totale, 0, y + 40, paint);
		mbitmapData.compress(CompressFormat.PNG, 90, mfileOutputStream);
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void WriteOnIMGDailywork2(FileOutputStream mfileOutputStream,
                                     List<DailyWorkClass> listGNPAY, String Date) {
//			List<GNPAY> listGNPAY, String Date) {
		Paint paint = new Paint();
		paint.setTextSize(21);
		paint.setColor(Color.BLACK);

		Bitmap mbitmapData = Bitmap.createBitmap(550,
				((listGNPAY.size() + 5) * 18) + ((listGNPAY.size() + 5) * 40),
				Bitmap.Config.RGB_565);

		Canvas canvas = new Canvas(mbitmapData);
		canvas.drawRGB(255, 255, 255);

		String Header = "التقرير اليومي للمحصلين";
		float width = paint.measureText(Header, 0, Header.length());
		canvas.drawText(Header, (366 - width) / 2, 40, paint);

		String DateToDay = "التاريخ :";
		width = paint.measureText(DateToDay + Date, 0,
				(DateToDay + Date).length());
		canvas.drawText(DateToDay + Date, 366 - width, 80, paint);

		String ColName = "اسم المحصل :";
//		width = paint.measureText(ColName + LoginActivity.coll_name, 0,
//				(ColName + LoginActivity.coll_name).length());
//		canvas.drawText(ColName + LoginActivity.coll_name, 366 - width, 120,
//				paint);

		width = paint.measureText(ColName + CoreLogInActivity.UserName, 0,
				(ColName + CoreLogInActivity.UserName).length());
		canvas.drawText(ColName + CoreLogInActivity.UserName, 366 - width, 120,
				paint);

		String No = "الرقم ";
		width = paint.measureText(No, 0, No.length());
		canvas.drawText(No, 366 - width, 160, paint);

		String clientName = "اسم الزبون ";
		width = paint.measureText(clientName, 0, clientName.length());
		canvas.drawText(clientName, (366 - width) / 2, 160, paint);

		String Amount = "القيمة";
		width = paint.measureText(Amount, 0, Amount.length());
		canvas.drawText(Amount, 0, 106, paint);
		int x = 1;
		double totale = 0.0;
		int y = 200;
//		for (GNPAY e : listGNPAY) {
            for (DailyWorkClass e : listGNPAY) {
			width = paint.measureText("" + x, 0, String.valueOf(x).length());
			canvas.drawText("" + x, 366 - width, y, paint);

			width = paint.measureText(e.getClientName(), 0, e.getClientName()
					.length());
			String Name = e.getClientName();

			// while (((550 - width) / 2 > 300)) {
			// Name = e.getClientName().substring(0,
			// e.getClientName().length() - 1);
			// width = paint.measureText(Name, 0, Name.length());
			//
			// }
			canvas.drawText(Name, (366 - width) / 2, y, paint);

			width = paint.measureText("" + e.getCR(), 0,
					("" + e.getCR()).length());
			canvas.drawText("" + e.getCR(), 0, y, paint);
			x = x + 1;
			y = y + 40;
			totale = totale + e.getCR();
		}
		canvas.drawText("المجموع " + totale, 0, y + 40, paint);
		mbitmapData.compress(CompressFormat.PNG, 90, mfileOutputStream);
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public void WriteAlmadinaLogo(FileOutputStream mfileOutputStream,
			Bitmap bitmapAlmadina) {
		try {
			Bitmap bitmapCreated = Bitmap.createBitmap(1000, 500,
					Bitmap.Config.RGB_565);//400,190
			canvas = new Canvas(bitmapCreated);
			canvas.drawRGB(255, 255, 255);
			canvas.drawBitmap(bitmapAlmadina, 10, 0, null);
			bitmapCreated.compress(CompressFormat.PNG, 90, mfileOutputStream);
			try {
				mfileOutputStream.flush();
				mfileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// Almadina Logo

	public void WriteAlmadinaLogo2(FileOutputStream mfileOutputStream,
			Bitmap bitmapAlmadina) {
		try {
			Bitmap bitmapCreated = Bitmap.createBitmap(150,90,
					Bitmap.Config.RGB_565);
			canvas = new Canvas(bitmapCreated);
			canvas.drawRGB(255, 255, 255);
			canvas.drawBitmap(bitmapAlmadina, 10, 0, null);
			bitmapCreated.compress(CompressFormat.PNG, 90, mfileOutputStream);
			try {
				mfileOutputStream.flush();
				mfileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// Almadina Logo2
	@SuppressLint("NewApi")
	public void WriteOnIMGAlmadina(FileOutputStream mfileOutputStream,
			GNPAY objContract, String Date, String BankName, String CheqNumber , String NewSer , String Note) {
		Bitmap bitmapCreated = Bitmap.createBitmap(800, 750,
				Bitmap.Config.RGB_565);

		if (objContract == null)
			objContract = new GNPAY();
		paint = new Paint();
		paint.setTextSize(22);
		paint.setColor(Color.BLACK);
		canvas = new Canvas(bitmapCreated);

		canvas.drawRGB(255, 255, 255);
		String Header = "مؤسسة عكاظ للصحافة و النشر";
		float width = paint.measureText(Header, 0, Header.length());
		canvas.drawText(Header, (550 - width) / 2, 40, paint);

		Header = "إدارة التحصيل";
		width = paint.measureText(Header, 0, Header.length());
		canvas.drawText(Header, (550 - width) / 2, 80, paint);

		int y = 150;

		String DateToDay = "التاريخ";
		width = paint.measureText(DateToDay, 0, DateToDay.length());
		canvas.drawText(DateToDay, 550 - width, y, paint);

		width = paint.measureText("" + Date, 0, Date.length());
		canvas.drawText("" + Date, (550 - width) / 2, y, paint);

		y = y + 40;
		// Time And Date
		String VRNumber = "رقم الفاتورة";
		width = paint.measureText(VRNumber, 0, VRNumber.length());
		canvas.drawText(VRNumber, (550 - width), y, paint);

		width = paint.measureText("" + NewSer, 0, String
				.valueOf(NewSer).length());
		canvas.drawText("" + NewSer , (550 - width) / 2, y,
				paint);
		//
		y = y + 40;
		String Name = "الاسم";
		width = paint.measureText(Name, 0, Name.length());
		canvas.drawText(Name, (550 - width), y, paint);

		width = paint.measureText("" + objContract.getClientName(), 0,
				objContract.getClientName().length());
		canvas.drawText("" + objContract.getClientName(), (550 - width) / 2, y,
				paint);

        y = y + 40;
        String Notee = "ملاحظات";
        width = paint.measureText(Notee, 0, Notee.length());
        canvas.drawText(Notee, (550 - width), y, paint);

        width = paint.measureText("" + Note, 0,
                Note.length());
        canvas.drawText("" + Note, (550 - width) / 2, y,
                paint);

		y = y + 40;
		String AccountNo = "رقم الحساب";
		width = paint.measureText(AccountNo, 0, AccountNo.length());
		canvas.drawText(AccountNo, (550 - width), y, paint);

		width = paint.measureText("" + objContract.getaccn_no(), 0, objContract
				.getaccn_no().length());
		canvas.drawText("" + objContract.getaccn_no(), (550 - width) / 2, y,
				paint);

		y = y + 40;
		String Amount = "القيمة";
		width = paint.measureText(Amount, 0, Amount.length());
		canvas.drawText(Amount, 550 - width, y, paint);

		String CheqNumberlabel = "رقم الشيك";
		width = paint.measureText(CheqNumberlabel, 0, CheqNumberlabel.length());
		canvas.drawText(CheqNumberlabel, (550 - width) / 2, y, paint);

		String CheqDate = "اسم البنك";
		width = paint.measureText(CheqDate, 0, CheqDate.length());
		canvas.drawText(CheqDate, 0, y, paint);

		y = y + 40;

//		width = paint.measureText("" + objContract.GetRestAmount(), 0, String
//				.valueOf(objContract.GetRestAmount()).length());
//		canvas.drawText("" + objContract.GetRestAmount(), 550 - width, y, paint);

		width = paint.measureText("" + objContract.getCR(), 0, String
				.valueOf(objContract.getCR()).length());
		canvas.drawText("" + objContract.getCR(), 550 - width, y, paint);

		width = paint.measureText("" + CheqNumber, 0, CheqNumber.length());
		canvas.drawText(CheqNumber, (550 - width) / 2, y, paint);

		width = paint.measureText("" + BankName, 0, BankName.length());
		canvas.drawText(BankName, 0, y, paint);

		bitmapCreated.compress(CompressFormat.PNG, 90, mfileOutputStream);
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	@SuppressLint("NewApi")
	public void WriteOnIMGAlmadina2(FileOutputStream mfileOutputStream,
			GNPAY objContract, String Date, String BankName, String CheqNumber) {
		Bitmap bitmapCreated = Bitmap.createBitmap(550, 750,
				Bitmap.Config.RGB_565);

		if (objContract == null)
			objContract = new GNPAY();
		paint = new Paint();
		paint.setTextSize(21);
		paint.setColor(Color.BLACK);
		//-----------------------------------------------------	
		Typeface currentTypeFace =   paint.getTypeface();
		Typeface bold = Typeface.create(currentTypeFace, Typeface.BOLD);
		paint.setTypeface(bold);
//-----------------------------------------------------
		canvas = new Canvas(bitmapCreated);

		canvas.drawRGB(255, 255, 255);
		String Header = "دار اليوم للصحافة و النشر";
		float width = paint.measureText(Header, 0, Header.length());
		canvas.drawText(Header, (350 - width) / 2, 40, paint);

		Header = "إدارة الاشتراكات";
		width = paint.measureText(Header, 0, Header.length());
		canvas.drawText(Header, (300 - width) / 2, 80, paint);

		int y = 150;

		String DateToDay = "التاريخ";
		width = paint.measureText(DateToDay, 0, DateToDay.length());
		canvas.drawText(DateToDay, 366 - width, y, paint);

		width = paint.measureText("" + Date, 0, Date.length());
		canvas.drawText("" + Date, (366 - width) / 2, y, paint);

		y = y + 40;
		// Time And Date
		String VRNumber = "رقم الفاتورة";
		width = paint.measureText(VRNumber, 0, VRNumber.length());
		canvas.drawText(VRNumber, (366 - width), y, paint);

		width = paint.measureText("" + objContract.GetSerNo(), 0, String
				.valueOf(objContract.GetSerNo()).length());
		canvas.drawText("" + objContract.GetSerNo(), (366 - width) / 2, y,
				paint);
		//
		y = y + 40;
		// Time And Date
		String Name = "الاسم";
		width = paint.measureText(Name, 0, Name.length());
		canvas.drawText(Name, (366 - width), y, paint);

		width = paint.measureText("" + objContract.getClientName(), 0,
				objContract.getClientName().length());
		canvas.drawText("" + objContract.getClientName(), (366 - width) / 2, y,
				paint);

		y = y + 40;
		String AccountNo = "رقم الحساب";
		width = paint.measureText(AccountNo, 0, AccountNo.length());
		canvas.drawText(AccountNo, (366 - width), y, paint);

		width = paint.measureText("" + objContract.getaccn_no(), 0, objContract
				.getaccn_no().length());
		canvas.drawText("" + objContract.getaccn_no(), (366 - width) / 2, y,
				paint);

		y = y + 40;
		String Amount = "القيمة";
		width = paint.measureText(Amount, 0, Amount.length());
		canvas.drawText(Amount, 366 - width, y, paint);

		String CheqNumberlabel = "رقم الشيخ";
		width = paint.measureText(CheqNumberlabel, 0, CheqNumberlabel.length());
		canvas.drawText(CheqNumberlabel, (366 - width) / 2, y, paint);

		String CheqDate = "اسم البنك";
		width = paint.measureText(CheqDate, 0, CheqDate.length());
		canvas.drawText(Amount, 0, y, paint);

		y = y + 40;

		width = paint.measureText("" + objContract.GetRestAmount(), 0, String
				.valueOf(objContract.GetRestAmount()).length());
		canvas.drawText("" + objContract.GetRestAmount(), 366 - width, y, paint);

		width = paint.measureText("" + CheqNumber, 0, CheqNumber.length());
		canvas.drawText(CheqNumber, (366 - width) / 2, y, paint);

		width = paint.measureText("" + BankName, 0, BankName.length());
		canvas.drawText(BankName, 0, y, paint);

		bitmapCreated.compress(CompressFormat.PNG, 90, mfileOutputStream);
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressLint("NewApi")
	public void WriteOnIMGOkazNew(FileOutputStream mfileOutputStream,
								  GNPAY objContract, AdapterListPayment adapterListPayment, String Date,String NewSer,
								  String Note) {
		Bitmap bitmapCreated = Bitmap.createBitmap(800, 750,
				Bitmap.Config.RGB_565);

		if (objContract == null)
			objContract = new GNPAY();
		paint = new Paint();
		paint.setTextSize(22);
		paint.setColor(Color.BLACK);
		canvas = new Canvas(bitmapCreated);

		canvas.drawRGB(255, 255, 255);
		String Header = "مؤسسة عكاظ للصحافة و النشر";
		float width = paint.measureText(Header, 0, Header.length());
		canvas.drawText(Header, (550 - width) / 2, 40, paint);

		Header = "إدارة التحصيل";
		width = paint.measureText(Header, 0, Header.length());
		canvas.drawText(Header, (550 - width) / 2, 80, paint);

		int y = 150;

		String DateToDay = "التاريخ";
		width = paint.measureText(DateToDay, 0, DateToDay.length());
		canvas.drawText(DateToDay, 550 - width, y, paint);

		width = paint.measureText("" + Date, 0, Date.length());
		canvas.drawText("" + Date, (550 - width) / 2, y, paint);

		y = y + 40;
		// Time And Date
		String VRNumber = "رقم الفاتورة";
		width = paint.measureText(VRNumber, 0, VRNumber.length());
		canvas.drawText(VRNumber, (550 - width), y, paint);

		width = paint.measureText("" + NewSer, 0, String
				.valueOf(NewSer).length());
		canvas.drawText("" + NewSer , (550 - width) / 2, y,
				paint);
		//
		y = y + 40;
		String Name = "الاسم";
		width = paint.measureText(Name, 0, Name.length());
		canvas.drawText(Name, (550 - width), y, paint);

		width = paint.measureText("" + objContract.getClientName(), 0,
				objContract.getClientName().length());
		canvas.drawText("" + objContract.getClientName(), (550 - width) / 2, y,
				paint);

		y = y + 40;
		String Notee = "ملاحظات";
		width = paint.measureText(Notee, 0, Notee.length());
		canvas.drawText(Notee, (550 - width), y, paint);

		width = paint.measureText("" + Note, 0,
				Note.length());
		canvas.drawText("" + Note, (550 - width) / 2, y,
				paint);

		y = y + 40;
		String AccountNo = "رقم الحساب";
		width = paint.measureText(AccountNo, 0, AccountNo.length());
		canvas.drawText(AccountNo, (550 - width), y, paint);

		width = paint.measureText("" + objContract.getaccn_no(), 0, objContract
				.getaccn_no().length());
		canvas.drawText("" + objContract.getaccn_no(), (550 - width) / 2, y,
				paint);

		y = y + 40;
		String Amount = "القيمة";
		width = paint.measureText(Amount, 0, Amount.length());
		canvas.drawText(Amount, 550 - width, y, paint);

		String CheqNumberlabel = "رقم الشيك";
		width = paint.measureText(CheqNumberlabel, 0, CheqNumberlabel.length());
		canvas.drawText(CheqNumberlabel, (550 - width) / 2, y, paint);

		String CheqDate = "اسم البنك";
		width = paint.measureText(CheqDate, 0, CheqDate.length());
		canvas.drawText(CheqDate, 0, y, paint);

		y = y + 40;

//		width = paint.measureText("" + objContract.GetRestAmount(), 0, String
//				.valueOf(objContract.GetRestAmount()).length());
//		canvas.drawText("" + objContract.GetRestAmount(), 550 - width, y, paint);

		for (int i = 0 ; i <= adapterListPayment.getCount() ; i ++){
			PaymentItem paymentItem = adapterListPayment.getItem((i));

			width = paint.measureText("" + objContract.getCR(), 0, String
					.valueOf(objContract.getCR()).length());
			canvas.drawText("" + objContract.getCR(), 550 - width, y, paint);

			width = paint.measureText("" + paymentItem.getChequeNo(), 0, paymentItem.getChequeNo().length());
			canvas.drawText(paymentItem.getChequeNo(), (550 - width) / 2, y, paint);

			width = paint.measureText("" + paymentItem.getBankNo(), 0, String.valueOf(paymentItem.getBankNo()).length());
			canvas.drawText(String.valueOf((paymentItem.getBankNo())), 0, y, paint);
		}


		bitmapCreated.compress(CompressFormat.PNG, 90, mfileOutputStream);
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}// End class
