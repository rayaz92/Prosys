package com.example.amir.core.ADR.activity.Classes;

import java.util.Date;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class IssueDate {
	private String IssueDate = "";

	public void SetIssueDate(String IssueDate) {
		this.IssueDate = IssueDate;
	}

	public String GetIssueDate() {
		return this.IssueDate;
	}

	// Android_CheckIssue

	public Integer CheckIssueDate(Date IssueDate) {
		String OPERATION_NAME = "Android_CheckIssue";
		String SOAP_ACTION = "http://tempuri.org/Android_CheckIssue";
		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);

		PropertyInfo PiID = new PropertyInfo();
		PiID.setName("IssueDate");
		PiID.setValue(IssueDate);
		PiID.setType(Date.class);
		request.addProperty(PiID);
		Integer Result = 0;

		androidHttpTransport.debug = true;
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		try {
			new MarshalDate().register(envelope);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object response = (Object) envelope.getResponse().toString().trim();
			Log.d("Id By Date In Chick ", "" + response);
			try {
				Result = Integer.valueOf(response.toString());
			} catch (Exception e) {
				Log.d("Error On Integer.val", e.getMessage());
				Result = 0;
			}
		} catch (Exception e) {
			Log.d("error check Date Id ", e.getMessage());
			new CatchMsg();
			CatchMsg.WriteMSG("Issue Date ", e.getMessage());
			Result = 0;
		}
		return Result;

	}


	public Integer NewCheckIssueDate(String IssueDate , int PublicationID) {
		String OPERATION_NAME = "Android_CheckIssue";
		String SOAP_ACTION = "http://tempuri.org/Android_CheckIssue";
		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);

		PropertyInfo PiID = new PropertyInfo();
		PiID.setName("IssueDate");
		PiID.setValue(IssueDate);
		PiID.setType(Date.class);
		request.addProperty(PiID);

		PropertyInfo PiPublicationID = new PropertyInfo();
		PiPublicationID.setName("PublicationID");
		PiPublicationID.setValue(PublicationID);
		PiPublicationID.setType(int.class);
		request.addProperty(PiPublicationID);

		Integer Result = 0;

		androidHttpTransport.debug = true;
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		try {
			new MarshalDate().register(envelope);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object response = (Object) envelope.getResponse().toString().trim();
			Log.d("Id By Date In Chick ", "" + response);
			try {
				Result = Integer.valueOf(response.toString());
			} catch (Exception e) {
				Log.d("Error On Integer.val", e.getMessage());
				Result = 0;
			}
		} catch (Exception e) {
			Log.d("error check Date Id ", e.getMessage());
			new CatchMsg();
			CatchMsg.WriteMSG("Issue Date ", e.getMessage());
			Result = 0;
		}
		return Result;

	}
}
