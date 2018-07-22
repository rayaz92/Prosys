package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class CallResultReport {

	private String ClientName = "";
	private Long ContractNo;
	private String CallResultTypeNameEn = "";

	public void setClientName(String ClientName) {
		this.ClientName = ClientName;
	}

	public void setCallResultTypeNameEn(String CallResultTypeNameEn) {
		this.CallResultTypeNameEn = CallResultTypeNameEn;
	}

	public void setContractNo(Long ContractNo) {
		this.ContractNo = ContractNo;
	}

	public String getClientName() {
		return this.ClientName;
	}

	public String getCallResultTypeNameEn() {
		return this.CallResultTypeNameEn;
	}

	public Long getContractNo() {
		return this.ContractNo;
	}

	public List<CallResultReport> getCallResultReport(int EntryUserID,
                                                      String EntryDateFrom) {

		List<CallResultReport> list = null;
		String OPERATION_NAME = "SelectCallResultReport";

		String SOAP_ACTION = "http://tempuri.org/SelectCallResultReport";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		HttpTransportSE androidHttpTransport = new HttpTransportSE(
				Connection.ADDRESS);
		androidHttpTransport.debug = true;
		PropertyInfo piPropertyInfo = new PropertyInfo();
		piPropertyInfo.setName("EntryUserID");
		piPropertyInfo.setValue(EntryUserID);
		piPropertyInfo.setType(int.class);
		request.addProperty(piPropertyInfo);

		piPropertyInfo = new PropertyInfo();
		piPropertyInfo.setName("EntryDateFrom");
		piPropertyInfo.setValue(EntryDateFrom);
		piPropertyInfo.setType(String.class);
		request.addProperty(piPropertyInfo);
		try {

			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<CallResultReport>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;

				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("R", "" + resultsRequestSOAP + "");
				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					CallResultReport item = new CallResultReport();
					obj3 = (SoapObject) obj2.getProperty(i);

					item.setClientName(obj3.getProperty("ClientName")
							.toString());

					item.setContractNo(Long.valueOf(obj3.getProperty(
							"ContractNo").toString()));

					item.setCallResultTypeNameEn((obj3
							.getProperty("CallResultTypeNameEn").toString()));

					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In Call Result Type   ", e.getMessage());
			//CatchMsg.WriteMSG("Call Result Type", e.getMessage());
			list = null;
			return list;
		}// End try Catch
		return list;
	}// End Function getSubscriptionType
}// End CallResultReport
