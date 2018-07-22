package com.example.amir.core.Distribution.bluetoothPrinterClasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.Base64;

import com.example.amir.core.Distribution.alghadversion1.Classes.Contract;
import com.example.amir.core.Distribution.alghadversion1.DistributionMain;
import com.example.amir.core.Distribution.alghadversion1.Signtuare;
import com.sprt.bluetooth.android.BluetoothPrinter;
import com.sprt.bluetooth.android.FontProperty;
import com.sprt.bluetooth.android.PrintGraphics;
import com.sprt.bluetooth.android.PrinterType;

import java.io.File;
import java.util.List;

//import com.example.alghadversion1.Classes.DailyWork;

public class PrintUtils {

	public static void printTextToT9(BluetoothPrinter mPrinter, String content,
			String Name) {
		mPrinter.init();
		// ÉèÖÃÐÐ¸ß
		mPrinter.setPrinter(BluetoothPrinter.COMM_LINE_HEIGHT, 80);

		// ´òÓ¡ÎÄ±¾
		mPrinter.printText(content);
		// »»ÐÐ
		mPrinter.setPrinter(BluetoothPrinter.COMM_PRINT_AND_NEWLINE);

	}

	public static void printTextToOther(BluetoothPrinter mPrinter,
			String content, String Name) {
		mPrinter.init();
		// ÉèÖÃÐÐ¸ß
		mPrinter.setPrinter(BluetoothPrinter.COMM_LINE_HEIGHT, 80);

		// ´òÓ¡ÎÄ±¾
		mPrinter.printText(content);
		// »»2ÐÐ
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
		pgS.drawImage(0, 0, Signtuare.FinalPath);

		File file = new File(Signtuare.FinalPath);
		file.delete();

		mPrinter.printImage(pgS.getCanvasImage());

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, 7);// COMM_PRINT_AND_WAKE_PAPER_BY_LINE
	}

	public static void printDalywork(BluetoothPrinter mPrinter, String Date,
			String Time, List<Contract> listDailWork) {

		mPrinter.init();

		Paint paint = new Paint();

		FontProperty fp = new FontProperty();
		fp.setFont(false, false, false, false, 28, null);
		paint.setTextSize(28);
		FontProperty fpUnderLine = new FontProperty();
		fpUnderLine.setFont(false, false, true, false, 28, null);

		int y = 60;
		PrintGraphics pgImage = new PrintGraphics();
		pgImage.init(PrinterType.T9);
		pgImage.setFontProperty(fp);
		String Header = "جريدة الغد";
		float width = paint.measureText(Header, 0, Header.length());
		pgImage.drawText(((550 - width) / 2), y, Header);
		y = y + 60;
		// mPrinter.printImage(pgImage.getCanvasImage());

		// PrintGraphics pgTitle = new PrintGraphics();
		// pgTitle.init(PrinterType.T9);
		// pgTitle.setFontProperty(fp);
		String Title = "تقرير اشتراكات التقدي";
		width = paint.measureText(Title, 0, Title.length());
		pgImage.drawText(((550 - width) / 2), y, Title);
		y = y + 60;
		// mPrinter.printImage(pgTitle.getCanvasImage());

		// PrintGraphics pgSubTitle = new PrintGraphics();
		// pgSubTitle.init(PrinterType.T9);
		// pgSubTitle.setFontProperty(fp);
		String SubTitle = "اليومي للمندوب";
		width = paint.measureText(SubTitle, 0, SubTitle.length());
		pgImage.drawText(((550 - width) / 2), y, SubTitle);
		y = y + 60;
		// mPrinter.printImage(pgSubTitle.getCanvasImage());

		// PrintGraphics pgDate = new PrintGraphics();
		// pgDate.init(PrinterType.T9);
		// pgDate.setFontProperty(fp);
		String date = "التاريخ ";
		width = paint.measureText(date + Date, 0, (date + Date).length());
		pgImage.drawText(550 - width, y, date + Date);
		y = y + 60;
		// mPrinter.printImage(pgDate.getCanvasImage());

		// PrintGraphics pgTime = new PrintGraphics();
		// pgTime.init(PrinterType.T9);
		// pgTime.setFontProperty(fp);
		String time = "الوقت ";
		width = paint.measureText(time + Time, 0, (time + Time).length());
		pgImage.drawText((550 - width), y, time + Time);
		y = y + 60;
		// mPrinter.printImage(pgTime.getCanvasImage());

		// PrintGraphics pgDriverName = new PrintGraphics();
		// pgDriverName.init(PrinterType.T9);
		// pgDriverName.setFontProperty(fp);
		String drivername = "االمندوب ";
		width = paint.measureText(drivername + DistributionMain.DriverName, 0,
				(drivername + DistributionMain.DriverName).length());
		pgImage.drawText(550 - width, y, drivername + DistributionMain.DriverName);
		y = y + 40;
		// mPrinter.printImage(pgDriverName.getCanvasImage());

		// PrintGraphics pgNoAndNaAndNe = new PrintGraphics();
		// pgNoAndNaAndNe.init(PrinterType.T9);
		// pgNoAndNaAndNe.setFontProperty(fp);
		pgImage.setFontProperty(fpUnderLine);
		String No = "الرقم";
		width = paint.measureText(No, 0, No.length());
		pgImage.drawText(550 - width, y, No);

		String ClientName = "اسم المشترك";
		width = paint.measureText(ClientName, 0, ClientName.length());
		pgImage.drawText((550 - width) / 2, y, ClientName);

		String Net = "القيمة";
		width = paint.measureText(Net, 0, Net.length());
		pgImage.drawText(0, y, Net);
		y = y + 40;

		// mPrinter.printImage(pgNoAndNaAndNe.getCanvasImage());
		pgImage.setFontProperty(fp);
		// PrintGraphics pgDailyWork;
		int x = 1;
		Double Sum = 0.0;
		for (Contract e : listDailWork) {
			// pgDailyWork = new PrintGraphics();
			// pgDailyWork.init(PrinterType.T9);
			// pgDailyWork.setFontProperty(fpUnderLine);
			width = paint.measureText("" + x, 0, String.valueOf(x).length());
			pgImage.drawText(550 - width, y, "" + x);
			width = paint.measureText(e.getClientName(), 0, e.getClientName()
					.length());
			pgImage.drawText((550 - width) / 2, y, e.getClientName());

			pgImage.drawText(0, y, "" + e.GetNetAmount());
			// mPrinter.printImage(pgDailyWork.getCanvasImage());

			// pgDailyWork.drawText(0, 40, sb.toString());
			// mPrinter.printImage(pgDailyWork.getCanvasImage());

			// width = paint.measureText(String.valueOf(x), 0, String.valueOf(x)
			// .length());
			// pgDailyWork.drawText(550 - width, 40, "" + x);
			//
			// width = paint.measureText("" + e.getNetAmount(), 0,
			// String.valueOf(e.getNetAmount()).length());
			// pgDailyWork.drawText(550 - width, 40, e.getClientName());
			//
			// width = paint.measureText(String.valueOf(x) + e.getClientName(),
			// 0,
			// String.valueOf(x).length() + e.getClientName().length());
			// pgDailyWork.drawText(0, 40, "" + e.getNetAmount());
			x++;
			Sum = Sum + e.GetNetAmount();
			y = y + 40;
		}// End For
		pgImage.setFontProperty(fp);

		// PrintGraphics pgSum = new PrintGraphics();
		// pgSum.init(PrinterType.T9);
		// pgSum.setFontProperty(fpUnderLine);
		pgImage.drawLine(0, y, 550, y);
		y = y + 40;
		String stringSum = "المجموع";
		width = paint.measureText(stringSum, 0, stringSum.length());
		pgImage.drawText((550 - width) / 2, y, stringSum);
		pgImage.drawText(0, y, "" + Sum);
		mPrinter.printImage(pgImage.getCanvasImage());
		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, 7);
	}

	public static void PrintCustomContract(BluetoothPrinter mPrinter,
			Contract ObjDailyWork, String Date) {
		int NumberOfLine = 1;
		mPrinter.init();
		mPrinter.printText("");

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, 5);
		mPrinter.printText(" " + ObjDailyWork.GetNetAmount()
				+ "                 " + ObjDailyWork.GetContractID());

		String[] array1 = ObjDailyWork.GetTime().split("T");
		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		mPrinter.printText(" " + array1[1].substring(0, 5)
				+ "                 " + Date);

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		mPrinter.printText("  " + ObjDailyWork.getContractTypeNameEn()
				+ "                " + ObjDailyWork.GetCopiesNo());

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		mPrinter.printText("                        "
				+ ObjDailyWork.getClientPhone());

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		mPrinter.printText("                 " + ObjDailyWork.GetNationalNo());
		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		Paint paint = new Paint();
		PrintGraphics pgName = new PrintGraphics();
		pgName.init(PrinterType.T9);
		FontProperty fp = new FontProperty();
		fp.setFont(false, false, false, false, 36, null);
		paint.setTextSize(36);
		float width = paint.measureText(ObjDailyWork.getClientName(), 0,
				ObjDailyWork.getClientName().length());
		pgName.setFontProperty(fp);
		pgName.drawText((550 - (int) width), 30,
				"  " + ObjDailyWork.getClientName());
		mPrinter.printImage(pgName.getCanvasImage());

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		PrintGraphics pgCompanName = new PrintGraphics();
		pgCompanName.init(PrinterType.T9);
		pgCompanName.setFontProperty(fp);
		width = paint.measureText(ObjDailyWork.GetCompanyName(), 0,
				ObjDailyWork.GetCompanyName().length());
		pgCompanName.drawText((550 - (int) width) / 2, 30,
				ObjDailyWork.GetCompanyName());
		mPrinter.printImage(pgCompanName.getCanvasImage());

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		PrintGraphics pgAddress = new PrintGraphics();
		pgAddress.init(PrinterType.T9);
		pgAddress.setFontProperty(fp);
		width = paint.measureText(ObjDailyWork.GetAddress(), 0, ObjDailyWork
				.GetAddress().length());
		pgAddress.drawText((550 - (int) width) / 2, 30,
				ObjDailyWork.GetAddress());
		mPrinter.printImage(pgAddress.getCanvasImage());

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		PrintGraphics pgNote = new PrintGraphics();
		pgNote.init(PrinterType.T9);
		pgNote.setFontProperty(fp);
		width = paint.measureText(ObjDailyWork.GetAddressComments(), 0,
				ObjDailyWork.GetAddressComments().length());
		pgNote.drawText((550 - (int) width), 30,
				ObjDailyWork.GetAddressComments());
		mPrinter.printImage(pgNote.getCanvasImage());

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		String arra[] = ObjDailyWork.GetFromDate().split("T");
		mPrinter.printText(arra[0]);

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		String a[] = ObjDailyWork.GetToDate().split("T");
		mPrinter.printText(a[0]);

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, NumberOfLine);
		PrintGraphics pgS = new PrintGraphics();
		pgS.initCanvas(500);
		pgS.initPaint();

		byte[] te = Base64.decode(ObjDailyWork.getSignImage(), 0);
		Bitmap bitmap = BitmapFactory.decodeByteArray(te, 0, te.length);
		pgS.drawImage(0, 0, bitmap);
		mPrinter.printImage(pgS.getCanvasImage());

		mPrinter.setPrinter(BluetoothPrinter.COMM_WAKE_PRINTER, 5);

	}

}// End Class
