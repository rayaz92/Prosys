package com.example.amir.core.Collector.Classes;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;


import android.util.Log;

import com.example.amir.core.Collector.Adapter.PaymentItem;
import com.example.amir.core.CoreLogInActivity;

public class GnCheqe {

	public Integer InsertGnCheqe(GNPAY objGnpay, int Serial,
								 PaymentItem objPaymentItem, int UserID, int Coll_id , String AccountNo , Integer CheqID) {
		String SOAP_ACTION = "http://tempuri.org/InsertGnCheq";

		String OPERATION_NAME = "InsertGnCheq";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyinfo = new PropertyInfo();
		propertyinfo.setName("Serial");
		propertyinfo.setValue(Serial);
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);// 00

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Note");
		propertyinfo.setValue(objGnpay.getNote());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);// 01

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("TransType");
		propertyinfo.setValue(objPaymentItem.getTransType());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);// 02

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("BankNo");
		propertyinfo.setValue(objPaymentItem.getBankNo());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);// 03

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("DueDate");
		propertyinfo.setValue(objPaymentItem.getDueDate());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);// 04

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Amount");
		propertyinfo.setValue(objPaymentItem.getCheuqeAmount());
		propertyinfo.setType(Double.class);
		request.addProperty(propertyinfo);// 05

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("UserID");
		propertyinfo.setValue(1);
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);// 06

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("SalesID");
		propertyinfo.setValue(CoreLogInActivity.SalesID);
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);// 07

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Ref_No");
		propertyinfo.setValue(objGnpay.getREF_NO());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);// 08

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("ChequeNo");
		propertyinfo.setValue(objPaymentItem.getChequeNo());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);// 09

        propertyinfo = new PropertyInfo();
        propertyinfo.setName("AccountNo");
        propertyinfo.setValue(AccountNo);
        propertyinfo.setType(String.class);
        request.addProperty(propertyinfo);// 10

        propertyinfo = new PropertyInfo();
        propertyinfo.setName("CheqID");
        propertyinfo.setValue(CheqID);
        propertyinfo.setType(int.class);
        request.addProperty(propertyinfo);// 11



		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		Object response = null;
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		int res;
		try {
			new MarshalFloat().register(envelope);

			envelope.encodingStyle = SoapEnvelope.ENC;
			httpTransport.call(SOAP_ACTION, envelope);
			response = envelope.getResponse();
			Log.d("Insert result ", "" + response + "");

		} catch (Exception exception) {
			response = exception.toString();
//			Log.d("Error In Insert Save Contract  ", "" + response);
			res = 0;
			return res;
		}// End try Catch
		if (Integer.valueOf(response.toString()) > 0) {

			res = Integer.valueOf(response.toString());
		} else {
			res = 0;
		}
		return res;

	}
}//
