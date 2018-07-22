package com.example.amir.core.Collector.Classes;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class AgeAR {
	private String ColName = "";
	private String Name = "";
	private String accn_no = "";
	private double Balance = 0.0;

	private String A0_30 = "";
	private String A31_60 = "";
	private String A61_90 = "";
	private String A91_120 = "";
	private String A121_150 = "";
	private String A151_180 = "";
	private String A181 = "";
	
	private String accn_no_agents ="";
	private String Nameagents="";

	public AgeAR(String accn_no, String Name) {
		this.accn_no = accn_no;
		this.Nameagents = Name;
	}
	public AgeAR() {

	}
	
	public void setA0_30(String A0_30) {
		this.A0_30 = A0_30;
	}

	public void setA31_60(String A31_60) {
		this.A31_60 = A31_60;
	}

	public void setA61_90(String A61_90) {
		this.A61_90 = A61_90;
	}

	public void setA91_120(String A91_120) {
		this.A91_120 = A91_120;
	}

	public void setA121_150(String A121_150) {
		this.A121_150 = A121_150;
	}

	public void setA151_180(String A151_180) {
		this.A151_180 = A151_180;
	}

	public void setA181(String A181) {
		this.A181 = A181;
	}

	public String getA0_30() {
		return this.A0_30;
	}

	public String getA31_60() {
		return this.A31_60;
	}

	public String getA61_90() {
		return this.A61_90;
	}

	public String getA91_120() {
		return this.A91_120;
	}

	public String getA121_150() {
		return this.A121_150;
	}

	public String getA151_180() {
		return this.A151_180;
	}

	public String getA181() {
		return this.A181;
	}

	public void setColName(String ColName) {
		this.ColName = ColName;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public void setaccn_no(String accn_no) {
		this.accn_no = accn_no;
	}
	public void setaccn_no_agents(String accn_no_agents) {
		this.accn_no_agents = accn_no_agents;
	}
	public void setNameagents(String Nameagents) {
		this.Nameagents= Nameagents;
	}

	public void setBalance(double Balance) {
		this.Balance = Balance;
	}

	public String getColName() {
		return this.ColName;
	}

	public String getName() {
		return this.Name;
	}

	public String getaccn_no() {
		return this.accn_no;
	}
	public String getaccn_no_agents() {
		return this.accn_no_agents;
	}
	public String getNameagents() {
		return this.Nameagents;
	}

	public double getBalance() {
		return this.Balance;
	}

	public List<AgeAR> GetARAge_With_Coll_Details(int collid, String accn_no,
			String Col) {
		AgeAR objAgeAR = new AgeAR();
		List<AgeAR> list = null;
		String SOAP_ACTION = "http://tempuri.org/Android_GetARAge_With_Coll_Details";

		String OPERATION_NAME = "Android_GetARAge_With_Coll_Details";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setName("collid");
		propertyInfo.setValue(collid);
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("Accn_no");
		propertyInfo.setValue(accn_no);
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("Col");
		propertyInfo.setValue(Col);
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);
		
//		propertyInfo = new PropertyInfo();
//		propertyInfo.setName("accn_no");
//		propertyInfo.setValue(AgeAcco_NO);
//		propertyInfo.setType(String.class);
//		request.addProperty(propertyInfo);

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
				list = new ArrayList<AgeAR>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("R", "" + resultsRequestSOAP + "");
				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					objAgeAR = new AgeAR();
					obj3 = (SoapObject) obj2.getProperty(i);
					objAgeAR.setColName(obj3.getProperty(Col).toString());
					objAgeAR.setaccn_no(obj3.getProperty("accn_no").toString());
					objAgeAR.setBalance(Double.valueOf(obj3.getProperty(
							"Balance").toString()));
					objAgeAR.setName(obj3.getProperty("Name").toString());
					list.add(objAgeAR);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In User Log In   ", e.getMessage());
			// this.MSG = e.getMessage();
			list = null;
			// UserID = 0;
		}// End try Catch
		return list;
	}

	public List<AgeAR> GetARAge_With_Coll_Details(int collid, String accn_no) {
		AgeAR objAgeAR = new AgeAR();
		List<AgeAR> list = null;
		String SOAP_ACTION = "http://tempuri.org/Android_GetAllARAge_With_Coll_Details";

		String OPERATION_NAME = "Android_GetAllARAge_With_Coll_Details";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setName("collid");
		propertyInfo.setValue(collid);
		propertyInfo.setType(int.class);
		request.addProperty(propertyInfo);

		propertyInfo = new PropertyInfo();
		propertyInfo.setName("Accn_no");
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
				list = new ArrayList<AgeAR>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("R", "" + resultsRequestSOAP + "");
				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					objAgeAR = new AgeAR();
					obj3 = (SoapObject) obj2.getProperty(i);
					objAgeAR.setA0_30(obj3.getProperty("A0_30").toString());
					objAgeAR.setA121_150(obj3.getProperty("A121_150")
							.toString());
					objAgeAR.setA151_180(obj3.getProperty("A151_180")
							.toString());
					objAgeAR.setA181(obj3.getProperty("A181").toString());
					objAgeAR.setA31_60(obj3.getProperty("A31_60").toString());
					objAgeAR.setA61_90(obj3.getProperty("A61_90").toString());
					objAgeAR.setA91_120(obj3.getProperty("A91_120").toString());
					objAgeAR.setaccn_no(obj3.getProperty("Accn_no").toString());
					objAgeAR.setBalance(Double.valueOf(obj3.getProperty(
							"Balance").toString()));
					objAgeAR.setName(obj3.getProperty("Name").toString());
					list.add(objAgeAR);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In User Log In   ", e.getMessage());
			// this.MSG = e.getMessage();
			list = null;
			// UserID = 0;
		}// End try Catch
		return list;
	}

	public List<AgeAR> GetglocaAgents() {
		List<AgeAR> list = null;
		String OPERATION_NAME = "Android_GetGlcoa_Agents";
		String SOAP_ACTION = "http://tempuri.org/Android_GetGlcoa_Agents";
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
				list = new ArrayList<AgeAR>();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					AgeAR item = new AgeAR();
					obj3 = (SoapObject) obj2.getProperty(i);
					item.setaccn_no_agents(obj3.getProperty("accn_no")
							.toString());
					item.setNameagents(obj3.getProperty("name").toString());
					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error In AdsType ", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}

	public String toString() {
		// TODO Auto-generated method stub
		return Nameagents;
	}// End To String
}// End Class
