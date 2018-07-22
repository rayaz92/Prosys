package com.example.amir.core.Collector.Classes;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class PayTerms {
	private int payid = 0;
	private String payTerms = "";

	public PayTerms() {
		// TODO Auto-generated constructor stub
	}

	public PayTerms(int payid, String payTerms) {
		this.payid = payid;
		this.payTerms = payTerms;
		// TODO Auto-generated constructor stub
	}

	public void setpayid(int payid) {
		this.payid = payid;
	}

	public void setpayTerms(String payTerms) {
		this.payTerms = payTerms;
	}

	public int getpayid() {
		return this.payid;
	}

	public String getpayTerms() {
		return this.payTerms;
	}

	public List<PayTerms> GetPayTerms() {
		List<PayTerms> list = null;
		String OPERATION_NAME = "AdsType";
		String SOAP_ACTION = "http://tempuri.org/AdsType";
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
				list = new ArrayList<PayTerms>();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					PayTerms item = new PayTerms();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.setpayid(Integer.parseInt(obj3.getProperty("PayId")
							.toString()));
					item.setpayTerms(obj3.getProperty("PayTerms").toString());
					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In AdsType ", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}

	@Override
	public String toString() {

		return payTerms.toString();
	}

}// End Class
