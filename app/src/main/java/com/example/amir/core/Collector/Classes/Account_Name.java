package com.example.amir.core.Collector.Classes;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.util.Log;

public class Account_Name {
	private long accn_no = 0;
	private String Name = "";
    private String OldAccountNo = "";


	public void SetID(long ID) {
		this.accn_no = ID;
	}

	public void SetNameEnglish(String NameEnglish) {
		this.Name = NameEnglish;
	}

	public long GetID() {
		return this.accn_no;
	}

	public String GetNameEnglish() {
		return this.Name;
	}

	public List<Account_Name> Getaccn_noByAccn_Name(String NameEn) {
		Account_Name objAccount_Name = new Account_Name();
		List<Account_Name> list = null;
		String SOAP_ACTION = "http://tempuri.org/SelectAccn_noByNamefromAgeAR";

		String OPERATION_NAME = "SelectAccn_noByNamefromAgeAR";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setName("Name");
		propertyInfo.setValue(NameEn);
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(2);

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
				list = new ArrayList<Account_Name>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					objAccount_Name = new Account_Name();
					obj3 = (SoapObject) obj2.getProperty(i);
					
					objAccount_Name.SetID(Long.parseLong(obj3.getProperty("Accn_no").toString().trim()));
					objAccount_Name.SetNameEnglish(obj3.getProperty("Name").toString().trim());
//                    objAccount_Name.setOldAccountNo(obj3.getProperty("Old_Accn").toString().trim());
					list.add(objAccount_Name);
				}// End for
			}// End try
		} catch (Exception e) {
//			Log.d("Error Get Accn_no by name", "" + e);
			list = null;
		}// End try Catch
		return list;
	}
	
	public List<Account_Name> Getaccn_noByAccn_Name_New(String NameEn, String Accn_no) {
		Account_Name objAccount_Name = new Account_Name();
		List<Account_Name> list = null;          
		String SOAP_ACTION = "http://tempuri.org/SelectAccn_noByNamefromAgeAR_New";

		String OPERATION_NAME = "SelectAccn_noByNamefromAgeAR_New";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setName("Name");
		propertyInfo.setValue(NameEn);
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);
		
		propertyInfo = new PropertyInfo();
		propertyInfo.setName("Accon_no");
		propertyInfo.setValue(Accn_no);
		propertyInfo.setType(String.class);
		request.addProperty(propertyInfo);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(2);

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
				list = new ArrayList<Account_Name>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					objAccount_Name = new Account_Name();
					obj3 = (SoapObject) obj2.getProperty(i);
					
					objAccount_Name.SetID(Long.parseLong(obj3.getProperty("Accn_no").toString().trim()));
					objAccount_Name.SetNameEnglish((obj3.getProperty("Name").toString().trim()));
                    objAccount_Name.setOldAccountNo(obj3.getProperty("Old_Accn").toString().trim());
					list.add(objAccount_Name);
				}// End for
			}// End try
		} catch (Exception e) {
//			Log.d("Error Get Accn_no by name", "" + e);
			list = null;
		}// End try Catch
		return list;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Name;// + "||" + ID;// + "-" + ID;
	}

    public String getOldAccountNo() {
        return OldAccountNo;
    }

    public void setOldAccountNo(String oldAccountNo) {
        OldAccountNo = oldAccountNo;
    }
}
