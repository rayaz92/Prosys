package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

public class DateTimeServer {
	public String GetDateTimeServer() {

		SoapObject request = new SoapObject(Connection.ADDRESS,
				"GetDateAndtime");

		/*
		 * pi = new PropertyInfo(); pi.setName("b"); pi.setValue(b);
		 * pi.setType(Integer.class); request.addProperty(pi);
		 */

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(1);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		Object response = null;

		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);

		try {
			httpTransport.call("http://tempuri.org/GetDateAndtime", envelope);
			response = envelope.getResponse();

		} catch (Exception exception) {
			response = exception.toString();
			// CatchMsg.WriteMSG("Date Time Server ", response.toString());
			Log.i("Error Try ", exception.getMessage());
		}// End try Catch
		return response.toString();
	}
}//
