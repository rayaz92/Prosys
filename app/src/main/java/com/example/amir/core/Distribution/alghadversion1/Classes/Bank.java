package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class Bank {
	private int No = 0;
	private String Name = "";

	public void SetNo(int No) {
		this.No = No;
	}

	public void SetName(String Name) {
		this.Name = Name;
	}

	public int getNo() {
		return this.No;
	}

	public String getName() {
		return this.Name;
	}

	public List<Bank> getBankName() {
		List<Bank> list = null;
		String OPERATION_NAME = "SelectBanks";

		String SOAP_ACTION = "http://tempuri.org/SelectBanks";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		androidHttpTransport.debug = true;

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<Bank>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				// Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					Bank item = new Bank();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetNo(Integer.parseInt(obj3.getProperty("BankNo")
							.toString()));
					// value of column 1 ID
					item.SetName(obj3.getProperty("Bank").toString());
					// value of column 2 NameEn
					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			// Log.d("Error In Bank  ", e.getMessage());
			Log.e("Bank", e.getMessage());
			// CatchMsg.WriteMSG("Bank", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}// End Function Get Bank Name

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Name;
	}// end To Strong
}// End Class
