package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

public class SubscriberLocation {
	private String PDAClientID = "";
	private int PDAEntryUserID = 0;
	private String DeviceID = "";
	private Double latitude = 0.0;
	private Double longitude = 0.0;

	public void setPDAClientID(String PDAClientID) {
		this.PDAClientID = PDAClientID;
	}

	public void setPDAEntryUserID(int PDAEntryUserID) {
		this.PDAEntryUserID = PDAEntryUserID;
	}

	public void setDeviceID(String DeviceID) {
		this.DeviceID = DeviceID;
	}

	public void setlatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setlongitude(Double longitude) {
		this.longitude = longitude;
	}

	public int getPDAEntryUserID() {
		return this.PDAEntryUserID;

	}

	public String getPDAClient() {
		return this.PDAClientID;
	}

	public String getDeviceID() {
		return this.DeviceID;
	}

	public Double getlatitude() {
		return this.latitude;
	}

	public Double getlongitude() {
		return this.longitude;
	}

	// insert Subscriber Location
	public Integer InsertSubscriberLocation(
			SubscriberLocation ObjSubscriberLocation) {

		String SOAP_ACTION = "http://tempuri.org/insertAndroidSubscriberLocation";

		String OPERATION_NAME = "insertAndroidSubscriberLocation";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyinfo = new PropertyInfo();
		propertyinfo.setName("PDAClientID");
		propertyinfo.setValue(ObjSubscriberLocation.getPDAClient());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 00 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("PDAEntryUserID");
		propertyinfo.setValue(ObjSubscriberLocation.getPDAEntryUserID());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 01 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("DeviceID");
		propertyinfo.setValue(ObjSubscriberLocation.getDeviceID());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 02 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Latitude");
		propertyinfo.setValue(ObjSubscriberLocation.getlatitude());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 03 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Longitude");
		propertyinfo.setValue(ObjSubscriberLocation.getlongitude());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 04 */

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		Object response = null;
		boolean re = false;
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		int res;
		try {
			new MarshalFloat().register(envelope);
			new MarshalBase64().register(envelope);

			envelope.encodingStyle = SoapEnvelope.ENC;
			httpTransport.call(SOAP_ACTION, envelope);
			response = envelope.getResponse();
			Log.d("Insert result ", "" + response + "");

		} catch (Exception exception) {
			response = exception.toString();
			Log.d("Error In Insert Save SubscriberLocation  ", "" + response);
			res = 0;
			// CatchMsg.WriteMSG("Insert Save SubscriberLocation ",
			// exception.getMessage()
			// + "\n" + response.toString());
			return res;
		}// End try Catch
		if (Integer.valueOf(response.toString()) > 0) {

			res = Integer.valueOf(response.toString());
		} else {
			res = 0;
		}
		return res;
	}// End SubscriberLocation
}
