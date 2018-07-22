package com.example.amir.core.ADR.activity.printing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.CompressFormat;

import com.example.amir.core.ADR.activity.Classes.Contract;
import com.example.amir.core.ADR.activity.Classes.ContractPublishDetails;
import com.example.amir.core.ADR.activity.Main;
import com.example.amir.core.CoreLogInActivity;

public class PrintInterMec {
	Paint paint;
	Bitmap bitmap1;
	Canvas canvas;

	public PrintInterMec() {

	}

	public void WriteOnIMGDailywork(FileOutputStream mfileOutputStream,
									List<Contract> listContract, String Date) {
		paint = new Paint();
		paint.setTextSize(28);
		paint.setColor(Color.BLACK);

		Bitmap mbitmapData = Bitmap.createBitmap(800,
				((listContract.size() + 5) * 18)
						+ ((listContract.size() + 5) * 40),
				Bitmap.Config.RGB_565);
		canvas = new Canvas(mbitmapData);
		canvas.drawRGB(255, 255, 255);

		String Header = "دار اليوم للصحافة و النشر";
		float width = paint.measureText(Header, 0, Header.length());
		canvas.drawText(Header, (550 - width) / 2, 40, paint);

		String Header2 = "التقرير اليومي للمندوب";
		width = paint.measureText(Header2, 0, Header2.length());
		canvas.drawText(Header2, (550 - width) / 2, 80, paint);

		String DateToDay = "التاريخ :";
		width = paint.measureText(DateToDay + Date, 0,
				(DateToDay + Date).length());
		canvas.drawText(DateToDay + Date, 550 - width, 120, paint);

		String ColName = "اسم المحصل :";
//		width = paint.measureText(ColName + Main.SalesName, 0,
//				(ColName + Main.SalesName).length());
//		canvas.drawText(ColName + Main.SalesName, 550 - width, 160, paint);

		width = paint.measureText(ColName + CoreLogInActivity.SalesName, 0,
				(ColName + CoreLogInActivity.SalesName).length());
		canvas.drawText(ColName + CoreLogInActivity.SalesName, 550 - width, 160, paint);

		//
		paint.setUnderlineText(true);

		String No = "الرقم ";
		float widthNo = paint.measureText(No, 0, No.length());
		width = paint.measureText(No, 0, No.length());
		canvas.drawText(No, 550 - widthNo, 200, paint);

		String NameHeader = "اسم المشترك";
		width = paint.measureText(NameHeader, 0, NameHeader.length());
		canvas.drawText(NameHeader, ((487 - width) + 274) / 2, 200, paint);

		String Area = "المساحة";
		width = paint.measureText(Area, 0, Area.length());
		canvas.drawText(Area, ((275 - width) + 137) / 2, 200, paint);

		String Net = "القيمة";
		width = paint.measureText(Net, 0, Net.length());

		canvas.drawText(Net, 0, 200, paint);

		int x = 1;
		double total = 0.0;
		int y = 240;
		paint.setUnderlineText(false);
		for (Contract e : listContract) {
			width = paint.measureText("" + x, 0, String.valueOf(x).length());
			canvas.drawText("" + x, 550 - width, y, paint);

			width = paint.measureText(e.GetClientName(), 0, e.GetClientName()
					.length());

			String Name = e.GetClientName();
			canvas.drawText(Name, ((486 - width) + 274) / 2, y, paint);

			width = paint.measureText("" + e.GetCm() * e.GetCol(), 0, String
					.valueOf((e.GetCm() * e.GetCol())).length());

			canvas.drawText("" + e.GetCm() * e.GetCol(),
					((275 - width) + 137) / 2, y, paint);

			canvas.drawText("" + e.GetNetAmount(), 0, y, paint);

			total = e.GetNetAmount() + total;
			x++;
			y = y + 40;
		}
		width = paint.measureText("المجموع ", 0, "المجموع ".length());
		canvas.drawText("المجموع ", (550 - width) / 2, y + 40, paint);
		canvas.drawText("" + total, 0, y + 40, paint);
		mbitmapData.compress(CompressFormat.PNG, 90, mfileOutputStream);
		// bitmap.recycle();
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void WriteOnIMGAlghad(FileOutputStream mfileOutputStream,
			Bitmap bitmapAlghd) {
//		Bitmap bitmapCreated = Bitmap.createBitmap(200, 90,
//				Bitmap.Config.RGB_565);
        Bitmap bitmapCreated = Bitmap.createBitmap(400, 190,
                Bitmap.Config.RGB_565);
		canvas = new Canvas(bitmapCreated);
		canvas.drawRGB(255, 255, 255);
		canvas.drawBitmap(bitmapAlghd, 10, 0, null);
		bitmapCreated.compress(CompressFormat.PNG, 90, mfileOutputStream);
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void WriteOnIMGSigntuare(FileOutputStream mfileOutputStream,
			Bitmap Signtuare) {
		Bitmap mBitmap = Bitmap.createBitmap(200, 90, Bitmap.Config.RGB_565);
		paint = new Paint();
		paint.setTextSize(28);
		paint.setColor(Color.BLACK);
		canvas = new Canvas(mBitmap);
		canvas.drawRGB(255, 255, 255);
		canvas.drawBitmap(Signtuare, 100, 50, null);
		mBitmap.compress(CompressFormat.PNG, 90, mfileOutputStream);
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}// Signature

	// @SuppressLint("NewApi")
	// public Boolean WriteOnIMGContract(InputStream input, OutputStream output,
	// String Date, String ContractNo, Contract ObjContract,
	// String ContractType,
	// List<ContractPublishDetails> listContractPublish) {
	// int Height = 1000;
	// try {
	// paint = new Paint();
	// paint.setTextSize(26);
	// paint.setColor(Color.BLACK);
	// Bitmap bitmap = Bitmap.createBitmap(50, 80, Bitmap.Config.RGB_565);
	// bitmap1 = getResizedBitmap(bitmap, 1000, 700);
	// canvas = new Canvas(bitmap1);
	// canvas.drawRGB(255, 255, 255);
	// int y = 40;
	// String Header = "التنفيذية للحلول البرمجية";
	// float width = paint.measureText(Header, 0, Header.length());
	// canvas.drawText(Header, ((550 - width) / 2), y, paint);
	//
	// y = y + 40;
	//
	// String Header2 = "حجز اعلان";
	// width = paint.measureText(Header2, 0, Header2.length());
	// canvas.drawText(Header2, ((550 - width) / 2), y, paint);
	// y = y + 40;
	//
	// String date = "التاريخ : ";
	// width = paint.measureText(date + Date, 0, (date + Date).length());
	// canvas.drawText(date + Date, 550 - width, y, paint);
	// y = y + 40;
	//
	// String Agency = "الوكالة : ";
	// width = paint.measureText(Agency + ObjContract.GetAgencyName(), 0,
	// (Agency + ObjContract.GetAgencyName()).length());
	// canvas.drawText(Agency + ObjContract.GetAgencyName(), 550 - width,
	// y, paint);
	// y = y + 40;
	//
	// String clientname = "الزبون :";
	// width = paint.measureText(clientname + ObjContract.GetClientName(),
	// 0, (clientname + ObjContract.GetClientName()).length());
	// canvas.drawText(clientname + ObjContract.GetClientName(),
	// 550 - width, y, paint);
	// y = y + 40;
	//
	// String drivername = "االمندوب : ";
	// width = paint.measureText(drivername + Main.SalesName, 0,
	// (drivername + Main.SalesName).length());
	// canvas.drawText(drivername + Main.SalesName, 550 - width, y, paint);
	// y = y + 40;
	//
	// String contractdate = " تاريخ العقد : ";
	// String array[] = ObjContract.GetContractDate().split("T");
	// width = paint.measureText(contractdate + array[0], 0,
	// (contractdate + array[0]).length());
	// canvas.drawText(contractdate + array[0], 550 - width, y, paint);
	// y = y + 40;
	//
	// String refernce = "رقم العقد :";
	// width = paint.measureText(refernce + ContractNo, 0,
	// (refernce + ContractNo).length());
	// canvas.drawText(refernce + ContractNo, 550 - width, y, paint);
	// y = y + 40;
	//
	// String contracttype = "نوع العقد :";
	// width = paint.measureText(contracttype + ContractType, 0,
	// (contracttype + ContractType).length());
	// canvas.drawText(contracttype + ContractType, 550 - width, y, paint);
	// y = y + 40;
	//
	// paint.setUnderlineText(true);
	//
	// String col = "عمود";
	// width = paint.measureText(col, 0, col.length());
	// canvas.drawText(col, 550 - width, y, paint);
	//
	// String Cm = "سم";
	// width = paint.measureText(Cm, 0, Cm.length());
	// canvas.drawText(Cm, (550 - width) / 2, y, paint);
	// y = y + 40;
	//
	// paint.setUnderlineText(false);
	//
	// width = paint.measureText("" + ObjContract.GetCol(), 0, String
	// .valueOf(ObjContract.GetCol()).length());
	// canvas.drawText("" + ObjContract.GetCol(), 550 - width, y, paint);
	//
	// width = paint.measureText("" + ObjContract.GetCm(), 0, String
	// .valueOf(ObjContract.GetCm()).length());
	// canvas.drawText("" + ObjContract.GetCm(), (550 - width) / 2, y,
	// paint);
	// y = y + 40;
	//
	// paint.setUnderlineText(true);
	//
	// String No = "الرقم ";
	// width = paint.measureText(No, 0, No.length());
	// canvas.drawText(No, 550 - width, y, paint);
	//
	// String Name = "تاريخ النشرة";
	// width = paint.measureText(Name, 0, Name.length());
	// canvas.drawText(Name, ((487 - width) + 274) / 2, y, paint);
	//
	// String NoPage = "رقم النشرة";
	// width = paint.measureText(NoPage, 0, NoPage.length());
	// canvas.drawText(NoPage, ((275 - width) + 137) / 2, y, paint);
	//
	// String Net = "القيمة";
	// width = paint.measureText(Net, 0, Net.length());
	// canvas.drawText(Net, 0, y, paint);
	// y = y + 40;
	//
	// paint.setUnderlineText(false);
	//
	// int x = 1;
	// for (ContractPublishDetails e : listContractPublish) {
	// width = paint
	// .measureText("" + x, 0, String.valueOf(x).length());
	// canvas.drawText("" + x, (550 - width), y, paint);
	//
	// String array1[] = e.GetIssueDate().split("T");
	// width = paint.measureText(array1[0], 0, array1[0].length());
	// canvas.drawText(array1[0], ((486 - width) + 274) / 2, y, paint);
	//
	// width = paint.measureText("" + e.GetIssueID(), 0, String
	// .valueOf(e.GetIssueID()).length());
	// canvas.drawText("" + e.GetIssueID(), ((275 - width) + 137) / 2,
	// y, paint);
	// canvas.drawText("" + e.GetIssuePrice(), 0, y, paint);
	// x++;
	// // if (bitmap1.getHeight() <= y) {
	// // Height = Height + 100;
	// // bitmap1 = getResizedBitmap(bitmap1, Height, 700);
	// // }
	// y = y + 40;
	// }// End For
	//
	// y = y + 50;
	//
	// String GroosTotal = "االقيمة";
	// width = paint.measureText(GroosTotal, 0, GroosTotal.length());
	// canvas.drawText(GroosTotal, (550 - width) / 2, y, paint);
	// canvas.drawText("" + ObjContract.GetGrossTotal(), 0, y, paint);
	// y = y + 40;
	//
	// // if (bitmap1.getHeight() <= y) {
	// // Height = Height + 100;
	// // bitmap1 = getResizedBitmap(bitmap1, Height, 700);
	// // }
	// String DiscountRate = "نسبة الخصم";
	// width = paint.measureText(DiscountRate, 0, DiscountRate.length());
	// canvas.drawText(DiscountRate, (550 - width) / 2, y, paint);
	// canvas.drawText("" + ObjContract.GetDiscountPer(), 0, y, paint);
	// y = y + 40;
	// //
	// // if (bitmap1.getHeight() <= y) {
	// // Height = Height + 100;
	// // bitmap1 = getResizedBitmap(bitmap1, Height, 700);
	// // }
	//
	// String VolumeDiscount = "قيمة الخصم";
	// width = paint.measureText(VolumeDiscount, 0,
	// VolumeDiscount.length());
	// canvas.drawText(VolumeDiscount, (550 - width) / 2, y, paint);
	// canvas.drawText("" + ObjContract.GetDiscountAmount(), 0, y, paint);
	// y = y + 60;
	//
	// if (bitmap1.getHeight() <= y) {
	// Height = Height + 200;
	// bitmap1 = getResizedBitmap(bitmap1, Height, 700);
	// canvas = new Canvas(bitmap1);
	// }
	// String VolumePressTax = "قيمة الضريبة";
	// width = paint.measureText(VolumePressTax, 0,
	// VolumePressTax.length());
	// canvas.drawText(VolumePressTax, (550 - width) / 2, y, paint);
	// canvas.drawText("" + ObjContract.GetPressTaxAmount(), 0, y, paint);
	// y = y + 40;
	//
	// // if (bitmap1.getHeight() <= y) {
	// // Height = Height + 100;
	// // bitmap1 = getResizedBitmap(bitmap1, Height, 700);
	// // }
	//
	// String Salestax = "ضريبة المبيعات";
	// width = paint.measureText(Salestax, 0, Salestax.length());
	// canvas.drawText(Salestax, (550 - width) / 2, y, paint);
	// canvas.drawText("" + ObjContract.GetSalesTaxAmount(), 0, y, paint);
	// y = y + 40;
	//
	// if (bitmap1.getHeight() <= y) {
	// Height = Height + 100;
	// bitmap1 = getResizedBitmap(bitmap1, Height, 700);
	// }
	// String stringSum = "المجموع";
	// width = paint.measureText(stringSum, 0, stringSum.length());
	// canvas.drawText(stringSum, (550 - width) / 2, y, paint);
	// canvas.drawText("" + ObjContract.GetNetAmount(), 0, y, paint);
	//
	// // if (bitmap1.getHeight() <= y) {
	// // Height = Height + 100;
	// // bitmap1 = getResizedBitmap(bitmap1, Height, 700);
	//
	// // }
	//
	// // canvas.drawBitmap(bitmap, matrix, paint);
	// // canvas.drawBitmap(bitmap, 90, 100, paint);
	// bitmap1.compress(CompressFormat.PNG, 90, output);
	// } catch (Exception e) {
	// return false;
	// }
	// try {
	// input.close();
	// input = null;
	// output.flush();
	// output.close();
	// return true;
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return false;
	// }
	// }

	public void WriteHeaderContract(FileOutputStream mfileOutputStream,
			String Date, Integer ContractNo, Contract ObjContract,
			String ContractType) {
		if (ObjContract == null) {
			ObjContract = new Contract();
		}
		Bitmap mBitmap = Bitmap.createBitmap(800, 600, Bitmap.Config.RGB_565);

		paint = new Paint();
		paint.setTextSize(28);
		paint.setColor(Color.BLACK);

		canvas = new Canvas(mBitmap);
		canvas.drawRGB(255, 255, 255);
		int y = 40;
		String Header = "دار اليوم للصحافة و النشر";
		float width = paint.measureText(Header, 0, Header.length());
		canvas.drawText(Header, ((550 - width) / 2), y, paint);
		y = y + 40;
		String Header2 = "حجز اعلان";
		width = paint.measureText(Header2, 0, Header2.length());
		canvas.drawText(Header2, ((550 - width) / 2), y, paint);
		y = y + 40;

		String date = "التاريخ : ";
		width = paint.measureText(date + Date, 0, (date + Date).length());
		canvas.drawText(date + Date, 550 - width, y, paint);
		y = y + 40;

		// String Agency = "الوكالة : ";
		// ObjContract.SetAgencyName("Long Tirm Contract");
		// width = paint.measureText(Agency + ObjContract.GetAgencyName(), 0,
		// (Agency + ObjContract.GetAgencyName()).length());
		// canvas.drawText(Agency + ObjContract.GetAgencyName(), 550 - width, y,
		// paint);
		// y = y + 40;

        String TaxNo = "الرقم الضريبي :";
        width = paint.measureText(TaxNo + CoreLogInActivity.TaxNo, 0,
                (TaxNo + CoreLogInActivity.TaxNo).length());
        canvas.drawText(TaxNo + CoreLogInActivity.TaxNo, 550 - width,
                y, paint);
        y = y + 40;

		String clientname = "اسم المعلن :";
		width = paint.measureText(clientname + ObjContract.GetClientName(), 0,
				(clientname + ObjContract.GetClientName()).length());
		canvas.drawText(clientname + ObjContract.GetClientName(), 550 - width,
				y, paint);
		y = y + 40;

		String drivername = "المندوب : ";
//		width = paint.measureText(drivername + Main.SalesName, 0,
//				(drivername + Main.SalesName).length());
//		canvas.drawText(drivername + Main.SalesName, 550 - width, y, paint);

		width = paint.measureText(drivername + CoreLogInActivity.SalesName, 0,
				(drivername + CoreLogInActivity.SalesName).length());
		canvas.drawText(drivername + CoreLogInActivity.SalesName, 550 - width, y, paint);

		y = y + 40;

		String contractdate = " تاريخ العقد : ";
		String array[] = ObjContract.GetContractDate().split("T");
		width = paint.measureText(contractdate + array[0], 0,
				(contractdate + array[0]).length());
		canvas.drawText(contractdate + array[0], 550 - width, y, paint);
		y = y + 40;

		String refernce = "رقم العقد :";
		width = paint.measureText((refernce + "" +ContractNo), 0,
				(refernce + "" + ContractNo).length());
		canvas.drawText((refernce + "" + ContractNo), 550 - width, y, paint);
		y = y + 40;

		String contracttype = "نوع العقد :";
		width = paint.measureText(contracttype + ContractType, 0,
				(contracttype + ContractType).length());
		canvas.drawText(contracttype + ContractType, 550 - width, y, paint);
		y = y + 40;

		String Subject = "الموضوع  : ";
		width = paint.measureText(Subject + ObjContract.GetSubject(), 0,
				(Subject + ObjContract.GetSubject()).length());
		canvas.drawText(Subject + ObjContract.GetSubject(), 550 - width, y,
				paint);
		y = y + 40;

		String Name = " الاسم  :";
		width = paint.measureText(Name + ObjContract.getAndroidClientName(), 0,
				(Name + ObjContract.getAndroidClientName()).length());
		canvas.drawText(Name + ObjContract.getAndroidClientName(), 550 - width,
				y, paint);
		y = y + 40;

		String Ph_Mobile = "  الهاتف/الموبايل :";
		width = paint.measureText(Ph_Mobile + ObjContract.getPh_Mobile(), 0,
				(Ph_Mobile + ObjContract.getPh_Mobile()).length());
		canvas.drawText(Ph_Mobile + ObjContract.getPh_Mobile(), 550 - width, y,
				paint);
		y = y + 40;

		paint.setUnderlineText(true);

		String col = "عمود";
		width = paint.measureText(col, 0, col.length());
		canvas.drawText(col, 550 - width, y, paint);

		String Cm = "سم";
		width = paint.measureText(Cm, 0, Cm.length());
		canvas.drawText(Cm, (550 - width) / 2, y, paint);

		String Size = "حجم الاعلان";
		width = paint.measureText(Size, 0, Size.length());
		canvas.drawText(Size, 0, y, paint);
		y = y + 40;

		paint.setUnderlineText(false);

		width = paint.measureText("" + ObjContract.GetCol(), 0,
				String.valueOf(ObjContract.GetCol()).length());
		canvas.drawText("" + ObjContract.GetCol(), 550 - width, y, paint);

		width = paint.measureText("" + ObjContract.GetCm(), 0,
				String.valueOf(ObjContract.GetCm()).length());
		canvas.drawText("" + ObjContract.GetCm(), (550 - width) / 2, y, paint);

		width = paint.measureText(
				"" + (ObjContract.GetCm() * ObjContract.GetCol()), 0, String
						.valueOf((ObjContract.GetCm() * ObjContract.GetCol()))
						.length());
		canvas.drawText("" + (ObjContract.GetCm() * ObjContract.GetCol()), 0,
				y, paint);
		// canvas.drawBitmap(addBlackBorder(mBitmap, 20), 200,
		// 200, null);
		y = y + 40;
		mBitmap.compress(CompressFormat.PNG, 90, mfileOutputStream);
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void WriteOnIMGContractPublich(FileOutputStream mfileOutputStream,
			int index, List<ContractPublishDetails> listContractPublish) {

		int height = ((listContractPublish.size()) * 18)
				+ ((listContractPublish.size()) * 40);
		// ((listContractPublish.size() + 1) * 30 )+
		// ((listContractPublish.size() + 1) * 40);//
		Bitmap mBitmap = Bitmap
				.createBitmap(800, height, Bitmap.Config.RGB_565);
		paint = new Paint();
		paint.setTextSize(28);
		paint.setColor(Color.BLACK);
		canvas = new Canvas(mBitmap);
		canvas.drawRGB(255, 255, 255);
		float width = 0;

		paint.setUnderlineText(true);

		int y = 20;

		String No = "الرقم ";
		width = paint.measureText(No, 0, No.length());
		canvas.drawText(No, 550 - width, y, paint);

		String Name = "تاريخ النشرة";
		width = paint.measureText(Name, 0, Name.length());
		// canvas.drawText(Name, ((487 - width) + 274) / 2, y, paint);
		canvas.drawText(Name, (550 - width) / 2, y, paint);

		// String NoPage = "رقم النشرة";
		// width = paint.measureText(NoPage, 0, NoPage.length());
		// canvas.drawText(NoPage, ((275 - width) + 137) / 2, y, paint);

		String Net = "القيمة";
		width = paint.measureText(Net, 0, Net.length());
		canvas.drawText(Net, 0, y, paint);
		y = y + 40;

		paint.setUnderlineText(false);

		int x = 1;
		for (ContractPublishDetails e : listContractPublish) {
			width = paint.measureText("" + x, 0, String.valueOf(x).length());
			canvas.drawText("" + x, (550 - width), y, paint);

			String array1[] = e.GetIssueDate().split("T");
			width = paint.measureText(array1[0], 0, array1[0].length());
			// canvas.drawText(array1[0], ((486 - width) + 274) / 2, y, paint);
			canvas.drawText(array1[0], (550 - width) / 2, y, paint);

			// width = paint.measureText("" + e.GetIssueID(), 0,
			// String.valueOf(e.GetIssueID()).length());
			// canvas.drawText("" + e.GetIssueID(), ((275 - width) + 137) / 2,
			// y,
			// paint);

			canvas.drawText("" + e.GetIssuePrice(), 0, y, paint);
			x++;
			y = y + 40;
			// if (y >= canvas.getHeight()) {
			// // mBitmap = getResizedBitmap(mBitmap, height + 250, 800);
			// mBitmap = getResizedBitmap(mBitmap, height + 1000, 800);
			//
			// y = y + 80;
			// // canvas = new Canvas(mBitmap);
			//
			// canvas.setBitmap(mBitmap);
			// }
		}// End For
		mBitmap.compress(CompressFormat.PNG, 90, mfileOutputStream);
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void WriteOnIMGResultContract(FileOutputStream mfileOutputStream,
			Contract ObjContract) {
		Bitmap mBitmap = Bitmap.createBitmap(800, 300, Bitmap.Config.RGB_565);
		paint = new Paint();
		paint.setTextSize(28);
		paint.setColor(Color.BLACK);
		canvas = new Canvas(mBitmap);
		canvas.drawRGB(255, 255, 255);

		float width = 0;
		int y = 40;
		String GroosTotal = "القيمة";
		width = paint.measureText(GroosTotal, 0, GroosTotal.length());
		canvas.drawText(GroosTotal, (550 - width) / 2, y, paint);
		canvas.drawText("" + ObjContract.GetGrossTotal(), 0, y, paint);
		y = y + 40;

		String DiscountRate = "نسبة الخصم";
		width = paint.measureText(DiscountRate, 0, DiscountRate.length());
		canvas.drawText(DiscountRate, (550 - width) / 2, y, paint);
		canvas.drawText("" + ObjContract.GetDiscountPer(), 0, y, paint);
		y = y + 40;

		String VolumeDiscount = "قيمة الخصم";
		width = paint.measureText(VolumeDiscount, 0, VolumeDiscount.length());
		canvas.drawText(VolumeDiscount, (550 - width) / 2, y, paint);
		canvas.drawText("" + ObjContract.GetDiscountAmount(), 0, y, paint);
		y = y + 40;
		String VolumePressTax = "نسبة القيمة المضافة %";

		width = paint.measureText(VolumePressTax, 0, VolumePressTax.length());
		canvas.drawText(VolumePressTax, (550 - width) / 2, y, paint);
		canvas.drawText("" + CoreLogInActivity.NewTax, 0, y, paint);
		y = y + 40;

		String Salestax = "قيمة القيمة المضافة";
		width = paint.measureText(Salestax, 0, Salestax.length());
		canvas.drawText(Salestax, (550 - width) / 2, y, paint);
		canvas.drawText("" + ObjContract.GetSalesTaxAmount(), 0, y, paint);
		y = y + 40;

		String stringSum = "المجموع";
		width = paint.measureText(stringSum, 0, stringSum.length());
		canvas.drawText(stringSum, (550 - width) / 2, y, paint);
		canvas.drawText("" + ObjContract.GetNetAmount(), 0, y, paint);

		mBitmap.compress(CompressFormat.PNG, 90, mfileOutputStream);
		try {
			mfileOutputStream.flush();
			mfileOutputStream.close();
		} catch (IOException e) {

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

}// End Class
