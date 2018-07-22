package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.R.string;
import android.util.Log;


import com.example.amir.core.Distribution.alghadversion1.CatchMsg;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contract implements Serializable {

	private int ContractID = 0;
	private int CopiesNo = 0;
	private int ContractID1 = 0;
	private int SubscriptionTypeID = 0;
	private int PaymentTypeID = 0;
	private int ContractTypeID = 0;
	private long ContractNo = 0;
	private String Address = "";
	private String AddressComments = "";
	private String ToDate = "";
	private String FromDate = "";
	private String MaxGraceDate = "";
	private Double NetAmount = 0.0;
	// 9 int
	private int ClientID = 0;
	private int SalesID = 0;
	private int EntryUserID = 0;
	private Integer Bank = 0;
	private int RenewContractID = 0;
	// 3 double
	private Double Latitude = 0.0;
	private Double Longitude = 0.0;
	// 6 String
	private String DeviceID = "";
	private String ChequeNo = "";
	private String ChequeDueDate = "";
	// /DailyWork
	private String ClientName = "";
	private String ClientPhone = "";
	private String ClientMobile = "";
	private String PaymentTypeNameEn = "";
	private String ContractTypeNameEn = "";
	private String SubscriptionTypeNameEn = "";
	private String CompanyName = "";
	private String Time = "";
	private String NationalNo = "";
	// private String EntryDate = "";
	// 4 Boolean
	private Boolean IsPosted = false;
	private Boolean IsExtendedPeriod = false;
	private Boolean IsIncludeOffers = false;
	private Boolean IsCheque = false;

	private byte[] SignImage = null;

	private Boolean ClientBlock = false ;
	private Boolean PDAClientBlock ;
	
	public void SetNationalNo(String NationalNo) {
		this.NationalNo = NationalNo;
	}

	public String GetNationalNo() {
		return this.NationalNo;
	}

	public void setSignImage(byte[] SignImage) {
		this.SignImage = SignImage;
	}

	public byte[] getSignImage() {
		return this.SignImage;
	}

	// 9 Set Int
	public void SetClientID(int ClientID) {
		this.ClientID = ClientID;
	}

	public void SetSalesID(int SalesID) {
		this.SalesID = SalesID;
	}

	public void SetEntryUserID(int EntryUserID) {
		this.EntryUserID = EntryUserID;
	}

	public void SetBank(int Bank) {
		this.Bank = Bank;
	}

	public void SetRenewContractID(int RenewContractID) {
		this.RenewContractID = RenewContractID;
	}

	// 9 Get Int
	public int GetClientID() {
		return this.ClientID;
	}

	public int GetSalesID() {
		return this.SalesID;
	}

	public int GetEntryUserID() {
		return this.EntryUserID;
	}

	public Integer GetBank() {
		return this.Bank;
	}

	public int GetRenewContractID() {
		return this.RenewContractID;
	}

	// 3 Set Double

	public void SetLatitude(Double Latitude) {
		this.Latitude = Latitude;
	}

	public void SetLongitude(Double Longitude) {
		this.Longitude = Longitude;
	}

	public Double GetLatitude() {
		return this.Latitude;
	}

	public Double GetLongitude() {
		return this.Longitude;
	}
	public void SetClientBlock(Boolean ClientBlock) {
		this.ClientBlock = ClientBlock;
	}

	public Boolean GetClientBlock() {
		return this.ClientBlock;
	}
	
	public void SetPDAClientBlock(Boolean PDAClientBlock) {
		this.PDAClientBlock = PDAClientBlock;
	}
	public Boolean GetPDAClientBlock() {
		return this.PDAClientBlock;
	}
		
	// 6 Set String

	public void SetDeviceID(String DeviceID) {
		this.DeviceID = DeviceID;
	}

	public void SetChequeNo(String ChequeNo) {
		this.ChequeNo = ChequeNo;
	}

	public void SetChequeDueDate(String ChequeDueDate) {
		this.ChequeDueDate = ChequeDueDate;
	}

	//
	public void setTime(String Time) {
		this.Time = Time;
	}// Set Time

	public void setCompanyName(String CompanyName) {
		this.CompanyName = CompanyName;
	}

	public void SetClientName(String ClientName) {
		this.ClientName = ClientName;
	}

	public void SetClientPhone(String ClientPhone) {
		this.ClientPhone = ClientPhone;
	}

	public void SetClientMobile(String ClientMobile) {
		this.ClientMobile = ClientMobile;
	}

	public void SetPaymentTypeNameEn(String PaymentTypeNameEn) {
		this.PaymentTypeNameEn = PaymentTypeNameEn;
	}

	public void SetContractTypeNameEn(String ContractTypeNameEn) {
		this.ContractTypeNameEn = ContractTypeNameEn;
	}

	public void SetSubscriptionTypeNameEn(String SubscriptionTypeNameEn) {
		this.SubscriptionTypeNameEn = SubscriptionTypeNameEn;
	}

	// 6 get String

	public String GetDeviceID() {
		return this.DeviceID;
	}

	public String GetChequeNo() {
		return this.ChequeNo;
	}

	public String GetChequeDueDate() {
		return this.ChequeDueDate;
	}

	// 4 Set Boolean
	public void SetIsPosted(Boolean IsPosted) {
		this.IsPosted = IsPosted;
	}

	public void SetIsExtendedPeriod(Boolean IsExtendedPeriod) {
		this.IsExtendedPeriod = IsExtendedPeriod;
	}

	public void SetIsIncludeOffers(Boolean IsIncludeOffers) {
		this.IsIncludeOffers = IsIncludeOffers;
	}

	public void SetIsCheque(Boolean IsCheque) {
		this.IsCheque = IsCheque;
	}

	// 4 get Boolean
	public Boolean GetIsPosted() {
		return this.IsPosted;
	}

	public Boolean GetIsExtendedPeriod() {
		return this.IsExtendedPeriod;
	}

	public Boolean GetIsIncludeOffers() {
		return this.IsIncludeOffers;
	}

	public Boolean GetIsCheque() {
		return this.IsCheque;
	}

	public void SetContractID(int ContractID) {
		this.ContractID = ContractID;
	}

	public void SetCopiesNo(int CopiesNo) {
		this.CopiesNo = CopiesNo;
	}

	public void SetContractID1(int ContractID1) {
		this.ContractID1 = ContractID1;
	}

	public void SetSubscriptionTypeID(int SubscriptionTypeID) {
		this.SubscriptionTypeID = SubscriptionTypeID;
	}

	public void SetPaymentTypeID(int PaymentTypeID) {
		this.PaymentTypeID = PaymentTypeID;
	}

	public void SetContractTypeID(int ContractTypeID) {
		this.ContractTypeID = ContractTypeID;
	}

	public void SetContractNo(long ContractNo) {
		this.ContractNo = ContractNo;
	}

	public void SetAddress(String Address) {
		this.Address = Address;
	}

	public void SetAddressComments(String AddressComments) {
		this.AddressComments = AddressComments;
	}

	public void SetToDate(String ToDate) {
		this.ToDate = ToDate;
	}

	public void SetFromDate(String FromDate) {
		this.FromDate = FromDate;
	}

	public void SetMaxGraceDate(String MaxGraceDate) {
		this.MaxGraceDate = MaxGraceDate;
	}

	public void SetNetAmount(Double NetAmount) {
		this.NetAmount = NetAmount;
	}

	public int GetContractID() {
		return this.ContractID;
	}

	public int GetCopiesNo() {
		return this.CopiesNo;
	}

	public int GetContractID1() {
		return this.ContractID1;
	}

	public int GetSubscriptionTypeID() {
		return this.SubscriptionTypeID;
	}

	public int GetPaymentTypeID() {
		return this.PaymentTypeID;
	}

	public int GetContractTypeID() {
		return this.ContractTypeID;
	}

	public long GetContractNo() {
		return this.ContractNo;
	}

	public Double GetNetAmount() {
		return this.NetAmount;
	}

	public String GetAddress() {
		return this.Address;
	}

	public String GetAddressComments() {
		return this.AddressComments;
	}

	public String GetCompanyName() {
		return this.CompanyName;
	}

	//
	public String getClientName() {
		return this.ClientName;
	}

	public String getClientPhone() {
		return this.ClientPhone;
	}

	public String getClientMobile() {
		return this.ClientMobile;
	}

	public String getPaymentTypeNameEn() {
		return this.PaymentTypeNameEn;
	}

	public String getContractTypeNameEn() {
		return this.ContractTypeNameEn;
	}

	public String getSubscriptionTypeNameEn() {
		return this.SubscriptionTypeNameEn;
	}

	public String GetToDate() {
		return this.ToDate;
	}

	public String GetFromDate() {
		return this.FromDate;
	}

	public String GetMaxGraceDate() {
		return this.MaxGraceDate;
	}

	public List<Contract> ContractsByClientIDAndStatus(int ClientID) {
		List<Contract> list = null;
		String OPERATION_NAME = "SelectContractsByClientIDAndStatus";

		String SOAP_ACTION = "http://tempuri.org/SelectContractsByClientIDAndStatus";

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
			PropertyInfo pi = new PropertyInfo();
			pi.setName("ClientID");
			pi.setValue(ClientID);
			pi.setType(int.class);
			request.addProperty(pi);

			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				// list = null;
			} else {
				list = new ArrayList<Contract>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
//				Log.d("SelectClientByNameAndPhone ", "" + resultsRequestSOAP
//						+ "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					Contract objContract = new Contract();
					obj3 = (SoapObject) obj2.getProperty(i);

					objContract.SetContractID((Integer.parseInt(obj3
							.getProperty("ContractID").toString())));// value
					objContract.SetCopiesNo((Integer.parseInt(obj3.getProperty(
							"CopiesNo").toString())));// value
					// objContract.SetContractID1((Integer.parseInt(obj3
					// .getProperty("ContractID1").toString())));// value
					objContract.SetSubscriptionTypeID((Integer.parseInt(obj3
							.getProperty("SubscriptionTypeID").toString())));// value
					objContract.SetPaymentTypeID((Integer.parseInt(obj3
							.getProperty("PaymentTypeID").toString())));// value
					objContract.SetContractTypeID((Integer.parseInt(obj3
							.getProperty("ContractTypeID").toString())));// value

					objContract.SetFromDate(String.valueOf(IsEmpty(obj3
							.getProperty("FromDate").toString())));
					objContract.SetToDate(String.valueOf(IsEmpty(obj3
							.getProperty("ToDate").toString())));
					objContract.SetMaxGraceDate(String.valueOf(IsEmpty(obj3
							.getProperty("MaxGraceDate").toString())));
					objContract.SetAddress(String.valueOf(IsEmpty(obj3
							.getProperty("Address").toString())));
					objContract.SetAddressComments(String.valueOf(IsEmpty(obj3
							.getProperty("AddressComments").toString())));

					objContract.SetNetAmount(Double.valueOf(obj3.getProperty(
							"NetAmount").toString()));

					objContract.SetContractNo(Long.valueOf(obj3.getProperty(
							"ContractNo").toString()));
					list.add(objContract);
				}// End for
			}// End Else
		}// End try
		catch (Exception e) {
//			Log.d("Error in SelectContractsByClientIDAndStatus ",
//					e.getMessage());
			// CatchMsg.WriteMSG("Contract ", e.getMessage());
			list = null;
			return list;
		}// End try Catch
		return list;

	}
	//-------------------------------------------------------------------------


	// Insert Contract With Location
	public Integer InsertContractWithLocation(Contract ObjContract) {

		String SOAP_ACTION = "http://tempuri.org/InsertPDAContractWithLocation";

		String OPERATION_NAME = "InsertPDAContractWithLocation";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo propertyinfo = new PropertyInfo();
		propertyinfo.setName("ClientID");
		propertyinfo.setValue(ObjContract.GetClientID());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 00 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("SubscriptionTypeID");
		propertyinfo.setValue(ObjContract.GetSubscriptionTypeID());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 01 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("PaymentTypeID");
		propertyinfo.setValue(ObjContract.GetPaymentTypeID());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 02 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("ContractTypeID");
		propertyinfo.setValue(ObjContract.GetContractTypeID());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 03 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("CopiesNo");
		propertyinfo.setValue(ObjContract.GetCopiesNo());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 04 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("SalesID");
		propertyinfo.setValue(ObjContract.GetSalesID());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 05 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("EntryUserID");
		propertyinfo.setValue(ObjContract.GetEntryUserID());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 06 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Bank");
		propertyinfo.setValue(ObjContract.GetBank());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 07 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("RenewContractID");
		propertyinfo.setValue(ObjContract.GetRenewContractID());
		propertyinfo.setType(int.class);
		request.addProperty(propertyinfo);/* 08 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("NetAmount");
		propertyinfo.setValue(ObjContract.GetNetAmount());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 09 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Latitude");
		propertyinfo.setValue(ObjContract.GetLatitude());
		propertyinfo.setType(string.class);
		request.addProperty(propertyinfo);/* 10 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("Longitude");
		propertyinfo.setValue(ObjContract.GetLongitude());
		propertyinfo.setType(string.class);
		request.addProperty(propertyinfo);/* 11 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("FromDate");
		propertyinfo.setValue(ObjContract.GetFromDate());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 12 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("ToDate");
		propertyinfo.setValue(ObjContract.GetToDate());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 13 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("DeviceID");
		propertyinfo.setValue(ObjContract.GetDeviceID());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 14 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("ChequeNo");
		propertyinfo.setValue(ObjContract.GetChequeNo());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 15 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("ChequeDueDate");
		propertyinfo.setValue(ObjContract.GetChequeDueDate());
		propertyinfo.setType(String.class);
		request.addProperty(propertyinfo);/* 16 */

		// PropertyInfo piEntryDate = new PropertyInfo();
		// piEntryDate.setName("EntryDate");
		// piEntryDate.setValue(EntryDate);
		// piEntryDate.setType(String.class);
		// request.addProperty(piEntryDate);

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("IsPosted");
		propertyinfo.setValue(ObjContract.GetIsPosted());
		propertyinfo.setType(Boolean.class);
		request.addProperty(propertyinfo);/* 17 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("IsExtendedPeriod");
		propertyinfo.setValue(ObjContract.GetIsExtendedPeriod());
		propertyinfo.setType(Boolean.class);
		request.addProperty(propertyinfo);/* 18 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("IsIncludeOffers");
		propertyinfo.setValue(ObjContract.GetIsIncludeOffers());
		propertyinfo.setType(Boolean.class);
		request.addProperty(propertyinfo);/* 19 */

		propertyinfo = new PropertyInfo();
		propertyinfo.setName("IsCheque");
		propertyinfo.setValue(ObjContract.GetIsCheque());
		propertyinfo.setType(Boolean.class);
		request.addProperty(propertyinfo);/* 20 */

		// PropertyInfo piSingImage = new PropertyInfo();
		// piSingImage.setName("Image");
		// piSingImage.setValue(array);
		// piSingImage.setValue(MarshalBase64.BYTE_ARRAY_CLASS);
		request.addProperty("Image", ObjContract.getSignImage());/* 21 */

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
//			Log.d("Error In Insert Save Contract  ", "" + response);
			res = 0;
			CatchMsg.WriteMSG("Insert Save Contract ", exception.getMessage()
					+ "\n" + response.toString());
			return res;
		}// End try Catch
		if (Integer.valueOf(response.toString()) > 0) {

			res = Integer.valueOf(response.toString());
		} else {
			res = 0;
		}
		return res;
	}// End InsertPDAContractWithLocation

	public List<Contract> SelectContract(int EntryUserID, String DateFrom) {
		List<Contract> listContract = null;
		String OPERATION_NAME = "PDASelectContract";

		String SOAP_ACTION = "http://tempuri.org/PDASelectContract";

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
			PropertyInfo propertyInfo = new PropertyInfo();
			propertyInfo.setName("EntryUserID");
			propertyInfo.setValue(EntryUserID);
			propertyInfo.setType(int.class);
			request.addProperty(propertyInfo);

			propertyInfo = new PropertyInfo();
			propertyInfo.setName("DateFrom");
			propertyInfo.setValue(DateFrom);
			propertyInfo.setType(String.class);
			request.addProperty(propertyInfo);

			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();
			Log.d("result SelectContract ", " " + response + " ");
			if (response.toString().equals("anyType{}") || response == null) {
				// list = null;
			} else {
				listContract = new ArrayList<Contract>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("PDASelectContract ", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					Contract objContract = new Contract();
					obj3 = (SoapObject) obj2.getProperty(i);

					objContract.SetContractID((Integer.parseInt(obj3
							.getProperty("ID").toString())));// value
					objContract.SetClientID((Integer.parseInt(obj3.getProperty(
							"ClientID").toString())));// value
					objContract.SetPaymentTypeID((Integer.parseInt(obj3
							.getProperty("PaymentTypeID").toString())));// value
					objContract.SetContractTypeID((Integer.parseInt(obj3
							.getProperty("ContractTypeID").toString())));// value
					objContract.SetSubscriptionTypeID((Integer.parseInt(obj3
							.getProperty("SubscriptionTypeID").toString())));// value
					objContract.SetCopiesNo((Integer.parseInt(obj3.getProperty(
							"CopiesNo").toString())));// value

					objContract.SetClientName(String.valueOf(IsEmpty(obj3
							.getProperty("ClientName").toString())));
					objContract.SetClientPhone(String.valueOf(IsEmpty(obj3
							.getProperty("ClientPhone").toString())));
					objContract.SetClientMobile(String.valueOf(IsEmpty(obj3
							.getProperty("ClientMobile").toString())));

					objContract.SetNationalNo(String.valueOf(IsEmpty(obj3
							.getProperty("NationalNo").toString())));

					objContract.setCompanyName(String.valueOf(IsEmpty(obj3
							.getProperty("CompanyName").toString())));

					objContract.SetAddress(String.valueOf(IsEmpty(obj3
							.getProperty("Address").toString())));
					objContract.SetAddressComments(String.valueOf(IsEmpty(obj3
							.getProperty("AddressComments").toString())));
					objContract.SetPaymentTypeNameEn(String
							.valueOf(IsEmpty(obj3.getProperty(
									"PaymentTypeNameEn").toString())));
					objContract.SetContractTypeNameEn(String
							.valueOf(IsEmpty(obj3.getProperty(
									"ContractTypeNameEn").toString())));
					objContract.SetSubscriptionTypeNameEn(String
							.valueOf(IsEmpty(obj3.getProperty(
									"SubscriptionTypeNameEn").toString())));
					objContract.SetFromDate(String.valueOf(IsEmpty(obj3
							.getProperty("FromDate").toString())));
					objContract.SetToDate(String.valueOf(IsEmpty(obj3
							.getProperty("ToDate").toString())));

					objContract.SetNetAmount(Double.valueOf(obj3.getProperty(
							"NetAmount").toString()));
					objContract.setSignImage(String.valueOf(
							obj3.getProperty("SignImage").toString())
							.getBytes());
					objContract.setTime(String.valueOf(obj3.getProperty(
							"DateAndTime").toString()));
					listContract.add(objContract);
				}// End for
			}// End Else
		}// End try
		catch (Exception e) {
//			Log.d("Error in PDASelectContract ", e.getMessage());
			listContract = null;
			CatchMsg.WriteMSG("Daily Work ", e.getMessage());
			return listContract;
		}// End try Catch
		return listContract;

	}// End Select Contract


	public  Boolean GetClientBlockk(int ClientID) {
		String SOAP_ACTION = "http://tempuri.org/GetClientBlock";

		String OPERATION_NAME = "GetClientBlock";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		boolean res = false ;

			PropertyInfo pi = new PropertyInfo();
			pi.setName("ClientID");
			pi.setValue(ClientID);
			pi.setType(int.class);
			request.addProperty(pi);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(1);

			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
					Connection.ADDRESS);

			try{
				androidHttpTransport.call(SOAP_ACTION, envelope);
				Object response = (Object) envelope.getResponse();
		        ClientBlock  = Boolean.parseBoolean(response.toString());


	} catch (Exception e) {

	}
			return ClientBlock;
}
	public Boolean UpdateClientBlock(int ClientID) {
		String SOAP_ACTION = "http://tempuri.org/UpdateClientBlock";

		String OPERATION_NAME = "UpdateClientBlock";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo pi = new PropertyInfo();
		pi.setName("ClientID");
		pi.setValue(ClientID);
		pi.setType(int.class);
		request.addProperty(pi);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(1);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object response = (Object) envelope.getResponse();
			if (response.toString().equals("True"))
				return true;
			else
				return false;
		} catch (Exception e) {
			Log.d("Error In Update ", e.getMessage());
			return false;
		}// End try catch
	}// End Update block
	
	
	//----------------------------------------------
	private Object IsEmpty(Object Obj) {

		if (Obj.equals("anyType{}")) {
			return " Empty ";
		}// End if
		else
			return Obj;
	}// End Is Empty

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.toString();
	}

	public String GetTime() {

		return this.Time;
	}

}// End Class Contract
