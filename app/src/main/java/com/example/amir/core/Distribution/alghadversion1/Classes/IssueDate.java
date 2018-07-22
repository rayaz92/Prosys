package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class IssueDate {

	private String IssueDate = "";

	public void SetIssueDate(String IssueDate) {
		this.IssueDate = IssueDate;
	}// End Set Date

	public String GetIssueDate() {
		return this.IssueDate;
	}

	public String GetFirstIssueDate(int DriverID, String TimeFrom, int ClientID) {

		String SOAP_ACTION = "http://tempuri.org/SelectFirstUnClosedIssueDateByPublicationID";

		String OPERATION_NAME = "SelectFirstUnClosedIssueDateByPublicationID";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		envelope.implicitTypes = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		androidHttpTransport.debug = true;

		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setName("PublicationID");
		propertyInfo.setValue(1);
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("DriverID");
		propertyInfo.setValue(DriverID);
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("TimeFrom");
		propertyInfo.setValue(TimeFrom);
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		// PropertyInfo pTimeTo = new PropertyInfo();
		// pTimeTo.setName("TimeTo");
		// pTimeTo.setValue(TimeTo);
		// pTimeTo.setType(String.class);
		// request.addProperty(pTimeTo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("ClientID");
		propertyInfo.setValue(ClientID);
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		envelope.setOutputSoapObject(request);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		Object response = null;
		// SoapObject response = null;

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			response = envelope.getResponse().toString();
			SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;

		} catch (Exception e) {
			Log.d("Error In Issue Date   ", e.getMessage());
			e.printStackTrace();

		}// End try Catch
		return response.toString();
	}// End Function

	public String GetStartDateByClientIDAndContractNo(int ClientID,
			long ContractNo) {

		String SOAP_ACTION = "http://tempuri.org/SelectFirstUnClosedIssueByContractAndClientID";

		String OPERATION_NAME = "SelectFirstUnClosedIssueByContractAndClientID";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		envelope.implicitTypes = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		androidHttpTransport.debug = true;

		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setName("PublicationID");
		propertyInfo.setValue(1);
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("ClientID");
		propertyInfo.setValue(ClientID);
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("ContractNo");
		propertyInfo.setValue(ContractNo);
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		envelope.setOutputSoapObject(request);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		Object response = null;
		// SoapObject response = null;
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			response = envelope.getResponse().toString();
			SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
		} catch (Exception e) {
			// CatchMsg.WriteMSG("Issue Date By Client ID", e.getMessage());
			Log.d("Error In Issue Date ByClient ID   ", e.getMessage());
			// e.printStackTrace();
			return response.toString();
		}// End try Catch
		return response.toString();
	}
}// End Class
