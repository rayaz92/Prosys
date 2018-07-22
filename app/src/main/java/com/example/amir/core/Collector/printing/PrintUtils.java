package com.example.amir.core.Collector.printing;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.Base64;
import android.util.Log;

import com.example.amir.core.Collector.Classes.GNPAY;
import com.example.amir.core.Collector.Collector.LoginActivity;
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
		// Ã‰Ã¨Ã–Ãƒï¿½?ï¿½?Â¸ÃŸ
		mPrinter.setPrinter(BluetoothPrinter.COMM_LINE_HEIGHT, 80);

		// Â´Ã²Ã“Â¡ÃŽÃ„Â±Â¾
		mPrinter.printText(content);
		// Â»Â»ï¿½?ï¿½?
		mPrinter.setPrinter(BluetoothPrinter.COMM_PRINT_AND_NEWLINE);

	}

	public static void printTextToOther(BluetoothPrinter mPrinter,
			String content, String Name) {
		mPrinter.init();
		// Ã‰Ã¨Ã–Ãƒï¿½?ï¿½?Â¸ÃŸ
		mPrinter.setPrinter(BluetoothPrinter.COMM_LINE_HEIGHT, 80);

		// Â´Ã²Ã“Â¡ÃŽÃ„Â±Â¾
		mPrinter.printText(content);
		// Â»Â»2ï¿½?ï¿½?
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

	public  String printDailyWork(String Date, List<GNPAY> listGNPAY) {
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
			String Header = "التقرير اليومي للمحصليين";
			float width = paint.measureText(Header, 0, Header.length());
			pgImage.drawText(((550 - width) / 2), y, Header);
			y = y + 80;

			// String Header2 = "";
			// width = paint.measureText(Header2, 0, Header2.length());
			// pgImage.drawText((550 - width) / 2, y, Header2);
			// y = y + 80;
			String date = "التاريخ : ";
			width = paint.measureText(date + Date, 0, (date + Date).length());
			pgImage.drawText(550 - width, y, date + Date);
			y = y + 80;

			String drivername = "اسم المحصل :";
//			width = paint.measureText(drivername + LoginActivity.coll_name, 0,
//					(drivername + LoginActivity.coll_name).length());
//			pgImage.drawText(550 - width, y, drivername
//					+ LoginActivity.coll_name);

			width = paint.measureText(drivername + CoreLogInActivity.UserName, 0,
					(drivername + CoreLogInActivity.UserName).length());
			pgImage.drawText(550 - width, y, drivername
					+ CoreLogInActivity.UserName);


			y = y + 80;
			pgImage.setFontProperty(fpUnderLine);

			String No = "الرقم";
			width = paint.measureText(No, 0, No.length());
			pgImage.drawText((550 - width), y, No);

			String Name = "اسم الزبون";
			width = paint.measureText(Name, 0, Name.length());
			pgImage.drawText((550 - width) / 2, y, Name);

			// String NoPage = "Ø§Ù„Ù…Ø³Ø§Ø­Ø©";
			// width = paint.measureText(NoPage, 0, NoPage.length());
			// pgImage.drawText(((275 - width) + 137) / 2, y, NoPage);

			String Net = "القيمة";
			width = paint.measureText(Net, 0, Net.length());
			pgImage.drawText(0, y, Net);
			y = y + 60;
			pgImage.setFontProperty(fp);

			int x = 1;
			double total = 0.0;
			for (GNPAY e : listGNPAY) {
				width = paint
						.measureText("" + x, 0, String.valueOf(x).length());
				pgImage.drawText((550 - width), y, "" + x);
				width = paint.measureText(e.getClientName(), 0, e
						.getClientName().length());

				pgImage.drawText((550 - width) / 2, y, e.getClientName());
				pgImage.drawText(0, y, "" + e.getCR());
				total = e.getCR() + total;
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
