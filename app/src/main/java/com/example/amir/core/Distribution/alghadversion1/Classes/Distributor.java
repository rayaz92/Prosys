package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class Distributor {
	private int ID			=0;
	private int DistributorID = 0;
	private String DistributorNameEn = "";
	private String DistributorNameAr = "";
	private int ContractID =0 ;
	private String SubscriberName= "";
	private String distributorName="";
	
	public void SetdistributorName(String distributorName){
		this.distributorName=distributorName;
	}
	public String GetdistributorName(){
		return this.distributorName;
	}
	
	public void setSubscriberName(String SubscriberName){
		this.SubscriberName=SubscriberName;
	}		
	public String GetSubscriberName(){
		return this.SubscriberName;
	}
			
	public void SetDistributorID(int DistributorID) {
		this.DistributorID = DistributorID;
	}

	public void SetDistributorNameEn(String DistributorNameEn) {
		this.DistributorNameEn = DistributorNameEn;
	}

	public void SetDistributorNameAr(String DistributorNameAr) {
		this.DistributorNameAr = DistributorNameAr;
	}

	public int GetDistributorID() {
		return this.DistributorID;
	}

	public String GetDistributorNameEn() {
		return this.DistributorNameEn;
	}

	public String GetDistributorNameAr() {
		return this.DistributorNameAr;
	}
	public void SetContractID(int ContractID) {
		this.ContractID = ContractID;
	}

	public int GetContractID() {
		return this.ContractID;
	}

	public void SetID(int ID) {
		this.ID = ID;
	}

	public int GetID() {
		return this.ID;
	}

	public List<Distributor> getDistributor(int ID) {
		List<Distributor> list = null;
		String OPERATION_NAME = "SelectDistributorBySupervisorID";

		String SOAP_ACTION = "http://tempuri.org/SelectDistributorBySupervisorID";

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
			PropertyInfo  pi = new PropertyInfo();
			pi.setName("SupervisorID");
			pi.setValue(ID);
			pi.setType(String.class);
			request.addProperty(pi);

			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<Distributor>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				// Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					Distributor item = new Distributor();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetDistributorID(Integer.parseInt(obj3.getProperty("DistributorID")
							.toString()));
					// value of column 1 ID
					item.SetDistributorNameEn(obj3.getProperty("DistributorNameEn").toString());

					// value of column 2 NameEn
					item.SetDistributorNameAr(obj3.getProperty("DistributorNameAr").toString());
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

	public List<Distributor> GetDailyDistributionByDistributor(int ID) {
		List<Distributor> list = null;
		String OPERATION_NAME = "GetDailyDistributionByDistributor";

		String SOAP_ACTION = "http://tempuri.org/GetDailyDistributionByDistributor";

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
			PropertyInfo  pi = new PropertyInfo();
			pi.setName("DistributorID");
			pi.setValue(ID);
			pi.setType(String.class);
			request.addProperty(pi);
			
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<Distributor>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				// Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					Distributor item = new Distributor();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetID(Integer.parseInt(obj3.getProperty("ID")
							.toString()));
					// value of column 1 ID
//					item.setSubscriberName(obj3.getProperty("SubscriberName").toString());
					item.SetDistributorNameAr(obj3.getProperty("SubscriberName").toString());
					// value of column 2 NameEn
					item.SetdistributorName(obj3.getProperty("distributorName").toString());
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

	//@Override
	public String toString() {
		 //TODO Auto-generated method stub
		return DistributorNameAr;
	}// end To Strong
	
	
}
