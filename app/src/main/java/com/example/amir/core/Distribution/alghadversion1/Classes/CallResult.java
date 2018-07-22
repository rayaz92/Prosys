package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.R.integer;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class CallResult {

	private int CallID = 0;
	private int CallTypeID = 0;
	private int ClientID = 0;
	private int ContractID = 0;
	private String ClientName = "";
	private String ClientPhone = "";
	private String ClientMobile = "";
	private String FromDate = "";
	private String ToDate = "";
	private String GraceEnd = "";
	private String Address = "";

	public void setCallID(int CallID) {
		this.CallID = CallID;
	}

	public void setCallTypeID(int CallTypeID) {
		this.CallTypeID = CallTypeID;
	}

	public void setClientID(int ClientID) {
		this.ClientID = ClientID;
	}

	public void setContractID(int ContractID) {
		this.ContractID = ContractID;
	}

	public void setClientName(String ClientName) {
		this.ClientName = ClientName;
	}

	public void setClientPhone(String ClientPhone) {
		this.ClientPhone = ClientPhone;
	}

	public void setClientMobile(String ClientMobile) {
		this.ClientMobile = ClientMobile;
	}

	public void setFromDate(String FromDate) {
		this.FromDate = FromDate;
	}

	public void setToDate(String ToDate) {
		this.ToDate = ToDate;
	}

	public void setGraceEnd(String GraceEnd) {
		this.GraceEnd = GraceEnd;
	}

	public void setAddress(String Address) {
		this.Address = Address;
	}

	public int getCallID() {
		return this.CallID;
	}

	public int getCallTypeID() {
		return this.CallTypeID;
	}

	public int getClientID() {
		return this.ClientID;
	}

	public int getContractID() {
		return this.ContractID;
	}

	public String getClientName() {
		return this.ClientName;
	}

	public String getClientPhone() {
		return this.ClientPhone;
	}

	public String getClientMobile() {
		return this.ClientMobile;
	}

	public String getFromDate() {
		return this.FromDate;
	}

	public String getToDate() {
		return this.ToDate;
	}

	public String getGraceEnd() {
		return this.GraceEnd;
	}

	public String getAddress() {
		return this.Address;
	}

	public List<CallResult> SelectCallsByDriverAndDate(int DriverID,
                                                       String DateFrom) {
		List<CallResult> list = null;
		String OPERATION_NAME = "SelectPDACallsByDriverAndDate";

		String SOAP_ACTION = "http://tempuri.org/SelectPDACallsByDriverAndDate";

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
		try {
			PropertyInfo PropertyInfo = new PropertyInfo();
			PropertyInfo.setName("DriverID");
			PropertyInfo.setValue(DriverID);
			PropertyInfo.setType(int.class);
			request.addProperty(PropertyInfo);

			PropertyInfo = new PropertyInfo();
			PropertyInfo.setName("EntryDateFrom");
			PropertyInfo.setValue(DateFrom);
			PropertyInfo.setType(String.class);
			request.addProperty(PropertyInfo);

			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();
			Log.d("result SelectCallsByDriverAndDate ", " " + response + " ");
			if (response.toString().equals("anyType{}") || response == null) {
				// list = null;
			} else {
				list = new ArrayList<CallResult>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("SelectCallsByDriverAndDate ", "" + resultsRequestSOAP
						+ "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					CallResult objCallResult = new CallResult();
					obj3 = (SoapObject) obj2.getProperty(i);

					objCallResult.setCallID((Integer.parseInt(obj3.getProperty(
							"CallID").toString())));// value
					objCallResult.setCallTypeID((Integer.parseInt(obj3
							.getProperty("CallTypeID").toString())));// value
					objCallResult.setClientID((Integer.parseInt(obj3
							.getProperty("ClientID").toString())));// value
					objCallResult.setContractID((Integer.parseInt(obj3
							.getProperty("ContractID").toString())));// value
					objCallResult.setClientName(String.valueOf(IsEmpty(obj3
							.getProperty("ClientName").toString())));
					objCallResult.setClientMobile(String.valueOf(IsEmpty(obj3
							.getProperty("ClientMobile").toString())));
					objCallResult.setClientPhone(String.valueOf(IsEmpty(obj3
							.getProperty("ClientPhone").toString())));
					// objCallResult.setGraceEnd(String.valueOf(IsEmpty(obj3
					// .getProperty("GraceEnd").toString())));
					objCallResult.setToDate(String.valueOf(IsEmpty(obj3
							.getProperty("ToDate").toString())));
					objCallResult.setFromDate(String.valueOf(IsEmpty(obj3
							.getProperty("FromDate").toString())));
					objCallResult.setAddress(String.valueOf(IsEmpty(obj3
							.getProperty("Address").toString())));
					list.add(objCallResult);
				}// End for
			}// End Else
		}// End try
		catch (Exception e) {
			Log.d("Error in SelectCallsByDriverAndDate ", e.getMessage());
			//CatchMsg.WriteMSG("CallRsult", e.getMessage());
			list = null;
			return list;
		}// End try Catch
		return list;

	}// End SelectCallsByDriverAndDate

	public String InsertCallResult(int CallID, int CallResultTypeID,
			int CallResultTypeReasonID, String CallResultNote, int EntryUserID) {

		String OPERATION_NAME = "InsertPDACallResult";

		String SOAP_ACTION = "http://tempuri.org/InsertPDACallResult";
		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo PropertyInfo = new PropertyInfo();
		PropertyInfo.setName("CallID");
		PropertyInfo.setValue(CallID);
		PropertyInfo.setType(String.class);
		request.addProperty(PropertyInfo);

		PropertyInfo = new PropertyInfo();
		PropertyInfo.setName("CallResultTypeID");
		PropertyInfo.setValue(CallResultTypeID);
		PropertyInfo.setType(integer.class);
		request.addProperty(PropertyInfo);

		PropertyInfo = new PropertyInfo();
		PropertyInfo.setName("CallResultTypeReasonID");
		PropertyInfo.setValue(CallResultTypeReasonID);
		PropertyInfo.setType(integer.class);
		request.addProperty(PropertyInfo);

		PropertyInfo = new PropertyInfo();
		PropertyInfo.setName("CallResultNote");
		PropertyInfo.setValue(CallResultNote);
		PropertyInfo.setType(integer.class);
		request.addProperty(PropertyInfo);

		PropertyInfo = new PropertyInfo();
		PropertyInfo.setName("EntryUserID");
		PropertyInfo.setValue(EntryUserID);
		PropertyInfo.setType(integer.class);
		request.addProperty(PropertyInfo);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(1);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		Object response = null;

		try {
			httpTransport.call(SOAP_ACTION, envelope);
			response = envelope.getResponse();
		} catch (Exception exception) {
			response = exception.toString();
			Log.i("Error Insert Call Result ", exception.getMessage());
		}// End try Catch
		return response.toString();
	}// End InsertCallResult

	private Object IsEmpty(Object Obj) {
		if (Obj.equals("anyType{}")) {
			return " Empty ";
		}// End if
		else
			return Obj;
	}// End Is Empty

}// End Class
