package com.example.amir.core.ADR.activity.printing;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.Base64;
import android.util.Log;

import com.example.amir.core.ADR.activity.Classes.Contract;
import com.example.amir.core.ADR.activity.Classes.ContractPublishDetails;
import com.example.amir.core.ADR.activity.Main;
import com.example.amir.core.CoreLogInActivity;
import com.sprt.bluetooth.android.BluetoothPrinter;
import com.sprt.bluetooth.android.FontProperty;
import com.sprt.bluetooth.android.PrintGraphics;
import com.sprt.bluetooth.android.PrinterType;

public class PrintUtils {
	public BluetoothPrinter mPrinter;

	public PrintUtils(BluetoothPrinter mPrinter) {
		this.mPrinter = mPrinter;
	}

	public static void printTextToT9(BluetoothPrinter mPrinter, String content,
			String Name) {
		mPrinter.init();
		// ÉèÖÃ�?�?¸ß
		mPrinter.setPrinter(BluetoothPrinter.COMM_LINE_HEIGHT, 80);

		// ´òÓ¡ÎÄ±¾
		mPrinter.printText(content);
		// »»�?�?
		mPrinter.setPrinter(BluetoothPrinter.COMM_PRINT_AND_NEWLINE);

	}

	public static void printTextToOther(BluetoothPrinter mPrinter,
			String content, String Name) {
		mPrinter.init();
		// ÉèÖÃ�?�?¸ß
		mPrinter.setPrinter(BluetoothPrinter.COMM_LINE_HEIGHT, 80);

		// ´òÓ¡ÎÄ±¾
		mPrinter.printText(content);
		// »»2�?�?
		mPrinter.setPrinter(BluetoothPrinter.COMM_PRINT_AND_WAKE_PAPER_BY_LINE,
				2);
	}// COMM_PRINT_AND_WAKE_PAPER_BY_LINE

	public static void printCustomImage(BluetoothPrinter mPrinter,
			Integer RefernceNo, String Name, String NetAmount, String Time,
			String Date, String ContractType, String Phone, String NarionalNo,
			String CopesNo, String CompanName, String Address, String Note,
			String StartDate, String EndDate) {
		int NumberOfLine = 1;
		mPrinter.init();
		mPrinter.printText("");

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, 5);
		mPrinter.printText(" " + NetAmount + "                 " + RefernceNo);

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		mPrinter.printText(" " + Time + "                 " + Date);

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		mPrinter.printText("  " + ContractType + "                " + CopesNo);

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		mPrinter.printText("                        " + Phone);

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		mPrinter.printText("                 " + NarionalNo);

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		Paint paint = new Paint();
		PrintGraphics pgName = new PrintGraphics();
		pgName.init(PrinterType.T9);
		FontProperty fp = new FontProperty();
		fp.setFont(false, false, false, false, 36, null);
		paint.setTextSize(36);
		float width = paint.measureText(Name, 0, Name.length());
		pgName.setFontProperty(fp);
		pgName.drawText((550 - (int) width), 30, "  " + Name);
		mPrinter.printImage(pgName.getCanvasImage());

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		PrintGraphics pgCompanName = new PrintGraphics();
		pgCompanName.init(PrinterType.T9);
		pgCompanName.setFontProperty(fp);
		width = paint.measureText(CompanName, 0, CompanName.length());
		pgCompanName.drawText((550 - (int) width) / 2, 30, CompanName);
		mPrinter.printImage(pgCompanName.getCanvasImage());

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		PrintGraphics pgAddress = new PrintGraphics();
		pgAddress.init(PrinterType.T9);
		pgAddress.setFontProperty(fp);
		width = paint.measureText(Address, 0, Address.length());
		pgAddress.drawText((550 - (int) width) / 2, 30, Address);
		mPrinter.printImage(pgAddress.getCanvasImage());

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		PrintGraphics pgNote = new PrintGraphics();
		pgNote.init(PrinterType.T9);
		pgNote.setFontProperty(fp);
		width = paint.measureText(Note, 0, Note.length());
		pgNote.drawText((550 - (int) width), 30, Note);
		mPrinter.printImage(pgNote.getCanvasImage());

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		mPrinter.printText(StartDate);

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		mPrinter.printText(EndDate);

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		PrintGraphics pgS = new PrintGraphics();
		pgS.initCanvas(500);
		pgS.initPaint();
		// pgS.drawImage(0, 0, Signtuare.SaSa);

		// File file = new File(Signtuare.SaSa);
		// file.delete();

		mPrinter.printImage(pgS.getCanvasImage());

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, 7);// COMM_PRINT_AND_WAKE_PAPER_BY_LINE
	}

	// BluetoothPrinter mPrinter
	public String printContract(String Date, String ContractNo,
								Contract ObjContract, String ContractType,
								List<ContractPublishDetails> listContractPublish) {

		try {
			int y = 40;
			//
			mPrinter.init();

			Paint paint = new Paint();

			FontProperty fp = new FontProperty();
			fp.setFont(false, false, false, false, 28, null);
			paint.setTextSize(28);

			PrintGraphics pgImage = new PrintGraphics();
			pgImage.init(PrinterType.T9);
			pgImage.setFontProperty(fp);
			String Header = "التنفيذية للحلول البرمجية";
			float width = paint.measureText(Header, 0, Header.length());
			pgImage.drawText(((550 - width) / 2), y, Header);
			y = y + 60;

			String Header2 = "حجز اعلان";
			width = paint.measureText(Header2, 0, Header2.length());
			pgImage.drawText((550 - width) / 2, y, Header2);
			y = y + 80;
			// mPrinter.printImage(pgImage.getCanvasImage());

			// PrintGraphics pgSubTitle = new PrintGraphics();
			// pgSubTitle.init(PrinterType.T9);
			// pgSubTitle.setFontProperty(fp);
			// String SubTitle = "اليومي للمندوب";
			// width = paint.measureText(SubTitle, 0, SubTitle.length());
			// pgSubTitle.drawText(((550 - width) / 2), 60, SubTitle);
			// mPrinter.printImage(pgSubTitle.getCanvasImage());
			//
			// PrintGraphics pgDate = new PrintGraphics();
			// pgDate.init(PrinterType.T9);
			// pgDate.setFontProperty(fp);
			String date = "التاريخ : ";
			width = paint.measureText(date + Date, 0, (date + Date).length());
			pgImage.drawText(550 - width, y, date + Date);
			y = y + 80;

			// objprintAsync = new printAsync();
			//
			// objprintAsync.execute(pgDate.getCanvasImage());
			// Thread.sleep(12000);

			/* mPrinter.printImage(pgImage.getCanvasImage()); */

			// PrintGraphics pgTime = new PrintGraphics();
			// pgTime.init(PrinterType.T9);
			// pgTime.setFontProperty(fp);
			String time = "الوكالة : ";
			width = paint.measureText(time + ObjContract.GetAgencyName(), 0,
					(time + ObjContract.GetAgencyName()).length());
			pgImage.drawText((550 - width), y,
					time + ObjContract.GetAgencyName());
			y = y + 80;

			// PrintGraphics pgClientName = new PrintGraphics();
			// pgClientName.init(PrinterType.T9);
			// pgClientName.setFontProperty(fp);
			String clientname = "الزبون :";
			width = paint.measureText(clientname + ObjContract.GetClientName(),
					0, (clientname + ObjContract.GetClientName()).length());
			pgImage.drawText(550 - width, y,
					clientname + ObjContract.GetClientName());
			y = y + 80;
			// mPrinter.printImage(pgClientName.getCanvasImage());

			//
			// PrintGraphics pgDriverName = new PrintGraphics();
			// pgDriverName.init(PrinterType.T9);
			// pgDriverName.setFontProperty(fp);
			String drivername = "االمندوب : ";
//			width = paint.measureText(drivername + Main.SalesName, 0,
//					(drivername + Main.SalesName).length());
//			pgImage.drawText(550 - width, y, drivername + Main.SalesName);


			width = paint.measureText(drivername + CoreLogInActivity.SalesName, 0,
					(drivername + CoreLogInActivity.SalesName).length());
			pgImage.drawText(550 - width, y, drivername + CoreLogInActivity.SalesName);
			y = y + 80;
			// mPrinter.printImage(pgDriverName.getCanvasImage());
			//
			// PrintGraphics pgConractDate = new PrintGraphics();
			// pgConractDate.init(PrinterType.T9);
			// pgConractDate.setFontProperty(fp);
			String contractdate = " تاريخ العقد : ";
			String array[] = ObjContract.GetContractDate().split("T");
			width = paint.measureText(contractdate + array[0], 0,
					(contractdate + array[0]).length());
			pgImage.drawText((550 - width), y, contractdate + array[0]);
			y = y + 80;
			// mPrinter.printImage(pgConractDate.getCanvasImage());
			//
			// PrintGraphics pgSerialNo = new PrintGraphics();
			// pgSerialNo.init(PrinterType.T9);
			// pgSerialNo.setFontProperty(fp);
			String refernce = "رقم العقد :";
			width = paint.measureText(refernce + ContractNo, 0,
					(refernce + ContractNo).length());
			pgImage.drawText(550 - width, y, refernce + ContractNo);
			y = y + 80;
			// mPrinter.printImage(pgSerialNo.getCanvasImage());
			//
			// PrintGraphics pgContractype = new PrintGraphics();
			// pgContractype.init(PrinterType.T9);
			// pgContractype.setFontProperty(fp);

			String contracttype = "نوع العقد :";
			width = paint.measureText(contracttype + ContractType, 0,
					(contracttype + ContractType).length());
			pgImage.drawText(550 - width, y, contracttype + ContractType);
			y = y + 80;
			// mPrinter.printImage(pgContractype.getCanvasImage());

			// --Under Line
			FontProperty fpUnderLine = new FontProperty();
			fpUnderLine.setFont(false, false, true, false, 28, null);

			// --//
			// PrintGraphics pgPageNameAndColAndCm = new PrintGraphics();
			// pgPageNameAndColAndCm.init(PrinterType.T9);
			// pgPageNameAndColAndCm.setFontProperty(fpUnderLine);

			String col = "عمود";
			width = paint.measureText(col, 0, col.length());
			pgImage.drawText((550 - width), y, col);
			// y = y + 40;

			String Cm = "سم";
			width = paint.measureText(Cm, 0, Cm.length());
			pgImage.drawText((550 - width) / 2, y, Cm);
			y = y + 40;
			// mPrinter.printImage(pgPageNameAndColAndCm.getCanvasImage());

			width = paint.measureText("" + ObjContract.GetCol(), 0, String
					.valueOf(ObjContract.GetCol()).length());
			pgImage.drawText((550 - width), y, "" + ObjContract.GetCol());
			// y = y + 40;
			width = paint.measureText("" + ObjContract.GetCm(), 0, String
					.valueOf(ObjContract.GetCm()).length());
			pgImage.drawText((550 - width) / 2, y, "" + ObjContract.GetCm());
			y = y + 80;
			// mPrinter.printImage(pgPageNameAndColAndCm.getCanvasImage());

			// PrintGraphics pgNoAndNaAndNe = new PrintGraphics();
			// pgNoAndNaAndNe.init(PrinterType.T9);
			// pgNoAndNaAndNe.setFontProperty(fpUnderLine);
			pgImage.setFontProperty(fpUnderLine);
			String No = "الرقم ";
			width = paint.measureText(No, 0, No.length());
			pgImage.drawText((550 - width), y, No);
			// y = y + 40;
			// (550-486)486 is 126.9+148.07+211.5
			// pgNoAndNaAndNe.drawLine((550 - 486)/2, 40, 63, 40);

			String Name = "تاريخ النشرة";
			width = paint.measureText(Name, 0, Name.length());
			pgImage.drawText(((487 - width) + 274) / 2, y, Name);
			// y = y + 40;
			// pgNoAndNaAndNe.drawText((550 - width) / 2, 40, Name);

			String NoPage = "رقم النشرة";
			width = paint.measureText(NoPage, 0, NoPage.length());
			pgImage.drawText(((275 - width) + 137) / 2, y, NoPage);
			// y = y + 40;
			// pgNoAndNaAndNe.drawText(0, 40, NoPage);
			// pgNoAndNaAndNe.drawLine(0, 40, 550, 40);

			// mPrinter.printImage(pgNoAndNaAndNe.getCanvasImage());

			String Net = "القيمة";
			width = paint.measureText(Net, 0, Net.length());
			pgImage.drawText(0, y, Net);
			y = y + 60;
			// mPrinter.printImage(pgNoAndNaAndNe.getCanvasImage());

			pgImage.setFontProperty(fp);

			int x = 1;

			for (ContractPublishDetails e : listContractPublish) {

				width = paint
						.measureText("" + x, 0, String.valueOf(x).length());
				pgImage.drawText((550 - width), y, "" + x);

				//
				String array1[] = e.GetIssueDate().split("T");
				width = paint.measureText(array1[0], 0, array1[0].length());

				pgImage.drawText(((486 - width) + 274) / 2, y, array1[0]);

				//
				width = paint.measureText("" + e.GetIssueID(), 0, String
						.valueOf(e.GetIssueID()).length());

				pgImage.drawText(((275 - width) + 137) / 2, y,
						"" + e.GetIssueID());

				// pgContractPublish.drawText(0, 40, "" + e.GetIssueID());

				pgImage.drawText(0, y, "" + e.GetIssuePrice());

				x++;
				y = y + 80;
			}// End For
				// mPrinter.printImage(pgImage.getCanvasImage());

			y = y + 80;
			// pgSum.initPaint();
			mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, 1);
			String GroosTotal = "االقيمة";
			width = paint.measureText(GroosTotal, 0, GroosTotal.length());
			pgImage.drawText((550 - width) / 2, y, GroosTotal);
			pgImage.drawText(0, y, "" + ObjContract.GetGrossTotal());
			// mPrinter.printImage(pgImage.getCanvasImage());

			//

			String DiscountRate = "نسبة الخصم";
			y = y + 80;
			width = paint.measureText(DiscountRate, 0, DiscountRate.length());
			pgImage.drawText((550 - width) / 2, y, DiscountRate);
			pgImage.drawText(0, y, "" + ObjContract.GetDiscountPer());
			// mPrinter.printImage(pgContractPublish.getCanvasImage());
			// objprintAsync.execute(pgContractPublish.getCanvasImage());
			//
			// pgContractPublish = new PrintGraphics();
			// pgContractPublish.init(PrinterType.T9);
			// pgContractPublish.setFontProperty(fp);

			y = y + 80;
			String VolumeDiscount = "قيمة الخصم";
			width = paint.measureText(VolumeDiscount, 0,
					VolumeDiscount.length());
			pgImage.drawText((550 - width) / 2, y, VolumeDiscount);
			pgImage.drawText(0, y, "" + ObjContract.GetDiscountAmount());
			// mPrinter.printImage(pgContractPublish.getCanvasImage());
			// objprintAsync.execute(pgContractPublish.getCanvasImage());
			//
			// pgContractPublish = new PrintGraphics();
			// pgContractPublish.init(PrinterType.T9);
			// pgContractPublish.setFontProperty(fp);
			y = y + 80;
			String VolumePressTax = "قيمة الضريبة";
			width = paint.measureText(VolumePressTax, 0,
					VolumePressTax.length());
			pgImage.drawText((550 - width) / 2, y, VolumePressTax);
			pgImage.drawText(0, y, "" + ObjContract.GetPressTaxAmount());
			// mPrinter.printImage(pgContractPublish.getCanvasImage());
			//
			// objprintAsync.execute(pgContractPublish.getCanvasImage());

			y = y + 80;
			String Salestax = "ضريبة المبيعات";
			width = paint.measureText(Salestax, 0, Salestax.length());
			pgImage.drawText((550 - width) / 2, y, Salestax);
			pgImage.drawText(0, y, "" + ObjContract.GetSalesTaxAmount());
			// mPrinter.printImage(pgContractPublish.getCanvasImage());

			y = y + 80;
			String stringSum = "المجموع";
			width = paint.measureText(stringSum, 0, stringSum.length());
			pgImage.drawText((550 - width) / 2, y, stringSum);
			pgImage.drawText(0, y, "" + ObjContract.GetNetAmount());
			mPrinter.printImage(pgImage.getCanvasImage());
			mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, 2);

			pgImage = new PrintGraphics();
			pgImage.init(PrinterType.T9);

			byte[] te = Base64.decode(ObjContract.GetImage(), 0);
			Bitmap bitmap = BitmapFactory.decodeByteArray(te, 0, te.length);
			pgImage.drawImage(0, 0, bitmap);

			// pgImage.drawImage(0, 0, Signtuare.Filepath);

			mPrinter.printImage(pgImage.getCanvasImage());
			// File f = new File(Signtuare.Filepath);
			// f.delete();

			mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, 5);
			return "Printing Completed";
		} catch (Exception e) {
			Log.d("Error", e.getMessage());
			return "Error while Print Contract ..." + e.getMessage();
		}
	}

	public String printDailyWork(String Date, List<Contract> listContract) {
		try {
			int y = 40;
			//
			mPrinter.init();

			Paint paint = new Paint();

			FontProperty fp = new FontProperty();
			fp.setFont(false, false, false, false, 28, null);
			paint.setTextSize(28);

			FontProperty fpUnderLine = new FontProperty();
			fpUnderLine.setFont(false, false, true, false, 28, null);

			PrintGraphics pgImage = new PrintGraphics();
			pgImage.init(PrinterType.T9);
			pgImage.setFontProperty(fp);
			String Header = "التنفيذية للحلول البرمجية";
			float width = paint.measureText(Header, 0, Header.length());
			pgImage.drawText(((550 - width) / 2), y, Header);
			y = y + 80;

			String Header2 = "التقرير اليومي للمندوب";
			width = paint.measureText(Header2, 0, Header2.length());
			pgImage.drawText((550 - width) / 2, y, Header2);
			y = y + 80;
			String date = "التاريخ : ";
			width = paint.measureText(date + Date, 0, (date + Date).length());
			pgImage.drawText(550 - width, y, date + Date);
			y = y + 80;

			String drivername = "االمندوب : ";
//			width = paint.measureText(drivername + Main.SalesName, 0,
//					(drivername + Main.SalesName).length());
//			pgImage.drawText(550 - width, y, drivername + Main.SalesName);

			width = paint.measureText(drivername + CoreLogInActivity.SalesName, 0,
					(drivername + CoreLogInActivity.SalesName).length());
			pgImage.drawText(550 - width, y, drivername + CoreLogInActivity.SalesName);
			y = y + 80;
			pgImage.setFontProperty(fpUnderLine);
			String No = "الرقم ";
			width = paint.measureText(No, 0, No.length());
			pgImage.drawText((550 - width), y, No);

			String Name = "اسم المشترك";
			width = paint.measureText(Name, 0, Name.length());
			pgImage.drawText(((487 - width) + 274) / 2, y, Name);

			String NoPage = "المساحة";
			width = paint.measureText(NoPage, 0, NoPage.length());
			pgImage.drawText(((275 - width) + 137) / 2, y, NoPage);

			String Net = "القيمة";
			width = paint.measureText(Net, 0, Net.length());
			pgImage.drawText(0, y, Net);
			y = y + 60;
			pgImage.setFontProperty(fp);

			int x = 1;
			double total = 0.0;
			for (Contract e : listContract) {
				width = paint
						.measureText("" + x, 0, String.valueOf(x).length());
				pgImage.drawText((550 - width), y, "" + x);
				width = paint.measureText(e.GetClientName(), 0, e
						.GetClientName().length());

				pgImage.drawText(((486 - width) + 274) / 2, y,
						e.GetClientName());

				width = paint.measureText("" + e.GetCm() * e.GetCol(), 0,
						String.valueOf((e.GetCm() * e.GetCol())).length());

				pgImage.drawText(((275 - width) + 137) / 2, y, ""
						+ (e.GetCm() + "*" + e.GetCol()));

				pgImage.drawText(0, y, "" + e.GetNetAmount());
				total = e.GetNetAmount() + total;
				x++;
				y = y + 80;
			}// End For
			y = y + 80;

			String stringSum = "المجموع";
			width = paint.measureText(stringSum, 0, stringSum.length());
			pgImage.drawText((550 - width) / 2, y, stringSum);
			pgImage.drawText(0, y, "" + total);
			mPrinter.printImage(pgImage.getCanvasImage());
			mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, 2);
			return "Printing Completed";
		} catch (Exception e) {
//			Log.d("Error In Print Daily Work ", e.getMessage());
			return "Error While Print.....";
		}
	}
}// End Class
