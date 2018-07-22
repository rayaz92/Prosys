package com.example.amir.core.Collector.Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.MarshalDate;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import android.R.string;
import android.util.Log;

import com.example.amir.core.CoreLogInActivity;

public class GNPAY implements Serializable {
	private int ser = 0;
//	private String serno ="";
	private String REF_NO = "";
	private double CR = 0.0;
	private String Note = "";
	private Boolean IsChecked = false;
	private String ClientName = "";
	private String accn_no = "";
	private String date = "";
	private double DR = 0.0;
	private String TY = "";
	private double RestAmount= 0.0;
	private String RestStatues= "";
	private String desc="";

	public void SetRestAmount(double Restamount){
		this.RestAmount=Restamount;
	}
	public double GetRestAmount(){
		return this.RestAmount;
	}
	public void SetRestStatues(String RestSatues){
		this.RestStatues=RestSatues;
	}
	public String GetRestStatues(){
		return this.RestStatues;
	}
	
	public void SetDesc(String Desc){
		this.desc=Desc;
	}
//	public void Setserno(String serno){
//		this.serno=serno;
//	}
//	public String getserno(){
//		return this.serno;
//	}
	
	public String GetDesc(){
		return this.desc;
	}
	
	public void setIsChecked(Boolean isChecked) {
		this.IsChecked = isChecked;
	}

	public Boolean getIsChecked() {
		return this.IsChecked;
	}

	public void setTY(String TY) {
		this.TY = TY;
	}

	public String getTY() {
		return this.TY;
	}

	public void setDR(double DR) {
		this.DR = DR;
	}

	public double getDR() {
		return this.DR;
	}

	public void setdate(String date) {
		this.date = date;
	}

	public String getdate() {
		return this.date;
	}

	public void setClientName(String ClientName) {
		this.ClientName = ClientName;
	}

	public void setaccn_no(String accn_no) {
		this.accn_no = accn_no;
	}

	public String getClientName() {
		return this.ClientName.trim();
	}

	public String getaccn_no() {
		return this.accn_no;
	}

	public void setser(int ser) {
		this.ser = ser;
	}

	public void setREF_NO(String REF_NO) {
		this.REF_NO = REF_NO;
	}

	public void setCR(double CR) {
		this.CR = CR;
	}

	public void setNote(String Note) {
		this.Note = Note;
	}

	public int getser() {
		return this.ser;
	}

	public String getREF_NO() {
		return this.REF_NO;
	}

	public double getCR() {
		return this.CR;
	}

	public String getNote() {
		return this.Note;
	}

	public Integer GetSerNo() {

		String SOAP_ACTION = "http://tempuri.org/GetSerNo";

		String OPERATION_NAME = "GetSerNo";
		
		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);


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
			new MarshalBase64().register(envelope);

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
	}// End

	public Integer GetSerbyacc(String accn_no) {

		String SOAP_ACTION = "http://tempuri.org/getser";

		String OPERATION_NAME = "gerser";
		
		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		
		PropertyInfo propertyinfo = new PropertyInfo();
		propertyinfo.setName("accn_no");
		propertyinfo.setValue(accn_no);
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		Object response = null;
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		Integer res;
		try {
			new MarshalFloat().register(envelope);

			envelope.encodingStyle = SoapEnvelope.ENC;
			httpTransport.call(SOAP_ACTION, envelope);
			response = envelope.getResponse();
			Log.d("Insert result ", "" + response + "");

		} catch (Exception exception) {
			response = exception.toString();
//			Log.d("Error In Insert Save Contract  ", "" + response);
			res = null;
			return res;
		}// End try Catch
		if (Integer.valueOf(response.toString()) > 0) {
			res = Integer.valueOf(response.toString());
		} else {
			res = null;
		}
		return res;

	}
	// Insert Contract With Location

	public Integer InsertGNPAY(GNPAY objGnpay, int UserID, String Agentaccn_no,String name,
                               String DRAccn_No,String DRAccnName ,String DeviceID) {

		String SOAP_ACTION = "http://tempuri.org/SaveRVCash";

		String OPERATION_NAME = "SaveRVCash";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyinfo = new PropertyInfo();
		propertyinfo.setName("Agentaccn_no");
		propertyinfo.setValue(Agentaccn_no);
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("AgentAccnName");
		propertyinfo.setValue(name);
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);

        propertyinfo = new PropertyInfo();
        propertyinfo.setName("DRAccn_No");
        propertyinfo.setValue(DRAccn_No);
        propertyinfo.setType(String.class);
        request.addProperty(propertyinfo);

        propertyinfo = new PropertyInfo();
        propertyinfo.setName("DRAccnName");
        propertyinfo.setValue(DRAccnName);
        propertyinfo.setType(String.class);
        request.addProperty(propertyinfo);

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Fixser");
		propertyinfo.setValue(objGnpay.getser());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("REF_NO");
		propertyinfo.setValue(objGnpay.getREF_NO());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("CR");
		propertyinfo.setValue(objGnpay.getCR());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("note");
		propertyinfo.setValue(objGnpay.getNote());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("user_id");
		propertyinfo.setValue(1);
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Coll_id");
		propertyinfo.setValue(CoreLogInActivity.SalesID);
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("DeviceID");
		propertyinfo.setValue(DeviceID);
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);

        propertyinfo = new PropertyInfo();
        propertyinfo.setName("OldTY");
        propertyinfo.setValue(objGnpay.getTY());
        propertyinfo.setType(String.class);
        request.addProperty(propertyinfo);

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
			new MarshalBase64().register(envelope);

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
	}// End

	public List<GNPAY> DaliyWork(Date Date, int UserID) {
		List<GNPAY> list = null;
		GNPAY ObjGNPAY;
		String SOAP_ACTION = "http://tempuri.org/Android_DaliyWorkCollector";

		String OPERATION_NAME = "Android_DaliyWorkCollector";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setName("DateFrom");
		propertyInfo.setValue(Date);
		propertyInfo.setType(Date.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("UserID");
		propertyInfo.setValue(UserID);
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(1);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		try {
            new MarshalDate().register(envelope);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response1 = (SoapObject) envelope.getResponse();

			if (response1.toString().equals("anyType{}") || response1 == null) {
				list = null;
			} else {
				list = new ArrayList<GNPAY>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
//				Log.d("Result DaliyWork Collector ", "" + resultsRequestSOAP
//						+ "");
				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					ObjGNPAY = new GNPAY();
					obj3 = (SoapObject) obj2.getProperty(i);
					ObjGNPAY.setser((Integer.parseInt(obj3.getProperty("ser")
							.toString())));
					ObjGNPAY.setaccn_no((obj3.getProperty("accn_no").toString()));
					ObjGNPAY.setClientName(obj3.getProperty("name").toString());
					ObjGNPAY.setCR(Double.valueOf(obj3.getProperty("CR")
							.toString()));
					ObjGNPAY.setNote((obj3.getProperty("note").toString()));
					list.add(ObjGNPAY);
				}// End for
			}// End try
		} catch (Exception e) {
//			Log.d("Error Daliy Work Collector ..", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}// end DaliyWork

	public List<GNPAY> SelectAccountInfo_DR(String accn_no) {
		List<GNPAY> list = null;
		GNPAY ObjGNPAY;
		String SOAP_ACTION = "http://tempuri.org/SelectAccount_No";

		String OPERATION_NAME = "SelectAccount_No";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setName("accn_no");
		propertyInfo.setValue(accn_no);
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(1);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response1 = (SoapObject) envelope.getResponse();

			if (response1.toString().equals("anyType{}") || response1 == null) {
				list = null;
			} else {
				list = new ArrayList<GNPAY>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("Result Account Info .. ", "" + resultsRequestSOAP
						+ "");
				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					ObjGNPAY = new GNPAY();
					obj3 = (SoapObject) obj2.getProperty(i);

//					ObjGNPAY.Setserno((obj3.getProperty("ser").toString()));
					ObjGNPAY.setser((Integer.parseInt(obj3.getProperty("ser").toString())));
					ObjGNPAY.setaccn_no((obj3.getProperty("accn_no").toString()));
					ObjGNPAY.setClientName(obj3.getProperty("name").toString());
					ObjGNPAY.setDR(Double.valueOf(obj3.getProperty("DR").toString()));
					ObjGNPAY.setdate((obj3.getProperty("date").toString()));
					ObjGNPAY.setTY((obj3.getProperty("ty").toString()));
					ObjGNPAY.SetRestAmount(Double.parseDouble(obj3.getProperty("RestAmount").toString()));
					ObjGNPAY.SetRestStatues((obj3.getProperty("RestStatus").toString()));
					ObjGNPAY.SetDesc((obj3.getProperty("desc").toString()));
					ObjGNPAY.setIsChecked(IsChecked);
					
					list.add(ObjGNPAY);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error Account Info ..", e.getMessage());
			list = null;
		}// End try Catch
		return list;
		
	}// account Info

	@Override
	public String toString() {
		return this.toString();
	}
}
