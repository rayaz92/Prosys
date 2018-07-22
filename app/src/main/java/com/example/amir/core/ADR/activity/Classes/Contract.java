package com.example.amir.core.ADR.activity.Classes;

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

import android.accounts.Account;
import android.util.Log;

import com.example.amir.core.ADR.activity.Main;
import com.example.amir.core.CoreLogInActivity;

public class Contract implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// ---18 integer--
	private int SerialNo = 0;// -- new contract
	private int ContractTypeID = 0;// new contract == payment Type
	private int ClientID = 0;// --new contract
	private int SalesID = 0;// --//new contract sales id by Agency
	private int FirstIssue = 0;// --Calculate
	private int Col = 0;// --Calculate
	private int SCm = 0;// --Calculate
	private int PageTypeID = 0;// --Calculate
	private double PriceIss = 0;// calculate
	private int QtyIss = 0;// --Calculate
	private int Free = 0;// --Calculate == free
	private int ContCategoryID = 0;// --new contract
	private int AgencyID = 0;// --new contract
	private int Page_Type = 0;// --calculate
	private int AdsTypeID = 0;// --new contract
	private int Word = 0;// --Calculate
	private int crmclientID = 0;// 0
	private int AdsSectorID = 0;// --new contract
	// ---4 string
	private String Subject = "";// --new contract
	private String ContractDate = "";// --Calculate
	private int SCol = 0;// --Calculate
	private String RefNo = "";// --new contract
	// ---15 double
	private double GrossTotal = 0.0;// calculate
	private double NetAmount = 0.0;
	private double Cm = 0.0;
	private double AreaBalance = 0.0;
	private double CurrentAreaBalance = 0.0;
	private double DiscountPer = 0.0;
	private double DiscountAmount = 0.0;
	private double PressTaxPer = 0.0;
	private double PressTaxAmount = 0.0;
	private double SalesTaxPer = 0.0;
	private double SalesTaxAmount = 0.0;
	private double CommisPer = 0.0;
	private double CommisAmount = 0.0;
	private double CRNotePer = 0.0;
	private double CRNote = 0.0;
	private byte[] Image = null;
	// ---- 1 boolean
	private boolean Status = false;
	// //For Contract Info
	private String ClientName = "";
	private String AgencyName = "";
	private String PageName = "";
	private double UnitPrice = 0.0;
	private String PaymentTypeName = "";
	private int ID = 0;
	//
	private String IssueDate = "";
	// For Print
	private String AndroidClientName = "";
	private String Ph_Mobile = "";
	private Integer PublicationID = 0;

	private String AccountName = "";
	private String AccountNo = "";

    private Integer DesignID = 0;
	private Double CRLimit = 0.0;
	private boolean IsCheckCRLimit = false ;


	public void setAndroidClientName(String AndroidClientName) {
		this.AndroidClientName = AndroidClientName;
	}

	public String getAndroidClientName() {
		return this.AndroidClientName;
	}

	public void setPh_Mobile(String Ph_Mobile) {
		this.Ph_Mobile = Ph_Mobile;
	}

	public String getPh_Mobile() {
		return this.Ph_Mobile;
	}

	public void SetIssueDate(String IssueDate) {
		this.IssueDate = IssueDate;
	}

	public String GetIssueDate() {
		return this.IssueDate;
	}

	public void SetID(int id) {
		this.ID = id;
	}

	public int GetID() {
		return this.ID;
	}

	public void SetPaymentTypeName(String PaymentTypeName) {
		this.PaymentTypeName = PaymentTypeName;
	}

	public String GetPaymentTypeName() {
		return this.PaymentTypeName;
	}

	public void SetImage(byte[] Image) {
		this.Image = Image;
	}

	public byte[] GetImage() {
		return this.Image;
	}

	public void SetUnitPrice(double UnitPrice) {
		this.UnitPrice = UnitPrice;
	}

	public double GetUnitPrice() {
		return this.UnitPrice;
	}

	public void SetPageName(String PageName) {
		this.PageName = PageName;
	}

	public String GetPageName() {
		return this.PageName;
	}

	public void SetClientName(String ClientName) {
		this.ClientName = ClientName;
	}

	public String GetClientName() {
		return this.ClientName;
	}

	public void SetAgencyName(String AgencyName) {
		this.AgencyName = AgencyName;
	}

	public String GetAgencyName() {
		return this.AgencyName;
	}

	public void SetStatus(boolean Status) {
		this.Status = Status;
	}

	public boolean GetStatus() {
		return this.Status;
	}

	// -------4 string
	public void SetSubject(String Subject) {
		this.Subject = Subject;
	}

	public void SetContractDate(String ContractDate) {
		this.ContractDate = ContractDate;
	}

	public void SetRefNo(String RefNo) {
		this.RefNo = RefNo;
	}

	public void SetSCol(int SCol) {
		this.SCol = SCol;
	}

	// -----4 get string
	public int GetSCol() {
		return this.SCol;
	}

	public String GetContractDate() {
		return this.ContractDate;
	}

	public String GetRefNo() {
		return this.RefNo;
	}

	public String GetSubject() {
		return this.Subject;
	}

	// ------ 18 set integer
	public void SetSerialNo(int SerialNo) {
		this.SerialNo = SerialNo;
	}

	public void SetAdsSectorID(int AdsSectorID) {
		this.AdsSectorID = AdsSectorID;
	}

	public void SetAdsTypeID(int AdsTypeID) {
		this.AdsTypeID = AdsTypeID;
	}

	public void SetAgencyID(int AgencyID) {
		this.AgencyID = AgencyID;
	}

	public void SetClientID(int ClientID) {
		this.ClientID = ClientID;
	}

	public void SetCol(int Col) {
		this.Col = Col;
	}

	public void SetContCategoryID(int ContCategoryID) {
		this.ContCategoryID = ContCategoryID;
	}

	public void SetContractTypeID(int ContractTypeID) {
		this.ContractTypeID = ContractTypeID;
	}

	public void SetcrmclientID(int crmclientID) {
		this.crmclientID = crmclientID;
	}

	public void SetFirstIssue(int FirstIssue) {
		this.FirstIssue = FirstIssue;
	}

	public void SetFree(int Free) {
		this.Free = Free;
	}

	public void SetPage_Type(int Page_Type) {
		this.Page_Type = Page_Type;
	}

	public void SetPageTypeID(int PageTypeID) {
		this.PageTypeID = PageTypeID;
	}

	public void SetPriceIss(double PriceIss) {
		this.PriceIss = PriceIss;
	}

	public void SetQtyIss(int QtyIss) {
		this.QtyIss = QtyIss;
	}

	public void SetSalesID(int SalesID) {
		this.SalesID = SalesID;
	}

	public void SetSCm(int SCm) {
		this.SCm = SCm;
	}

	public void SetWord(int Word) {
		this.Word = Word;
	}

	// ------set 15 double
	public void SetAreaBalance(double AreaBalance) {
		this.AreaBalance = AreaBalance;
	}// --1

	public void SetCm(double Cm) {
		this.Cm = Cm;
	}// --2

	public void SetCommisAmount(double CommisAmount) {
		this.CommisAmount = CommisAmount;
	}// --3

	public void SetCRNote(double CRNote) {
		this.CRNote = CRNote;
	}// --4

	public void SetCRNotePer(double CRNotePer) {
		this.CRNotePer = CRNotePer;
	}// --5

	public void SetCurrentAreaBalance(double CurrentAreaBalance) {
		this.CurrentAreaBalance = CurrentAreaBalance;
	}// --6

	public void SetDiscountAmount(double DiscountAmount) {
		this.DiscountAmount = DiscountAmount;
	}// --7

	public void SetDiscountPer(double DiscountPer) {
		this.DiscountPer = DiscountPer;
	}// --8

	public void SetGrossTotal(double GrossTotal) {
		this.GrossTotal = GrossTotal;
	}// --9

	public void SetNetAmount(double NetAmount) {
		this.NetAmount = NetAmount;
	}// --10

	public void SetPressTaxAmount(double PressTaxAmount) {
		this.PressTaxAmount = PressTaxAmount;
	}// --11

	public void SetPressTaxPer(double PressTaxPer) {
		this.PressTaxPer = PressTaxPer;
	}// --12

	public void SetSalesTaxAmount(double SalesTaxAmount) {
		this.SalesTaxAmount = SalesTaxAmount;
	}// --13

	public void SetSalesTaxPer(double SalesTaxPer) {
		this.SalesTaxPer = SalesTaxPer;
	}// --14

	public void SetCommisPer(double CommisPer) {
		this.CommisPer = CommisPer;
	}// --15
		// --------15 get double

	public double GetAreaBalance() {
		return this.AreaBalance;
	}

	public double GetCm() {
		return this.Cm;
	}

	public double GetCommisAmount() {
		return this.CommisAmount;
	}

	public double GetCommisPer() {
		return this.CommisPer;
	}

	public double GetCRNote() {
		return this.CRNote;
	}

	public double GetCRNotePer() {
		return this.CRNotePer;
	}

	public double GetCurrentAreaBalance() {
		return this.CurrentAreaBalance;
	}

	public double GetDiscountAmount() {
		return this.DiscountAmount;
	}

	public double GetDiscountPer() {
		return this.DiscountPer;
	}

	public double GetGrossTotal() {
		return this.GrossTotal;
	}

	public double GetNetAmount() {
		return this.NetAmount;
	}

	public double GetPressTaxAmount() {
		return this.PressTaxAmount;
	}

	public double GetSalesTaxAmount() {
		return this.SalesTaxAmount;
	}

	public double GetSalesTaxPer() {
		return this.SalesTaxPer;
	}

	public double GetPressTaxPer() {
		return this.PressTaxPer;
	}

	// ---------get 18 integer
	public int GetSerialNo() {
		return this.SerialNo;
	}

	public int GetAdsSectorID() {
		return this.AdsSectorID;
	}

	public int GetAdsTypeID() {
		return this.AdsTypeID;
	}

	public int GetAgencyID() {
		return this.AgencyID;
	}

	public long GetClientID() {
		return this.ClientID;
	}

	public int GetCol() {
		return this.Col;
	}

	public int GetContCategoryID() {
		return this.ContCategoryID;
	}

	public int GetContractTypeID() {
		return this.ContractTypeID;
	}

	public int GetcrmclientID() {
		return this.crmclientID;
	}

	public int GetFirstIssue() {
		return this.FirstIssue;
	}

	public int GetFree() {
		return this.Free;
	}

	public int GetPage_Type() {
		return this.Page_Type;
	}

	public int GetPageTypeID() {
		return this.PageTypeID;
	}

	public double GetPriceIss() {
		return this.PriceIss;
	}

	public int GetQtyIss() {
		return this.QtyIss;
	}

	public int GetSalesID() {
		return this.SalesID;
	}

	public int GetSCm() {
		return this.SCm;
	}

	public int GetWord() {
		return this.Word;
	}

	public int Android_SerialNoByPaymentTypeID(int PaymentID) {
		String SOAP_ACTION = "http://tempuri.org/Android_SerialNoByPaymentTypeID";

		String OPERATION_NAME = "Android_SerialNoByPaymentTypeID";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		PropertyInfo piPaymentID = new PropertyInfo();
		piPaymentID.setName("PaymentTypeID");
		piPaymentID.setValue(PaymentID);
		piPaymentID.setType(int.class);
		request.addProperty(piPaymentID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		int result = 0;
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object response = envelope.getResponse().toString();
//			Log.d("Result From Android Serial No ", "" + response);
			if (Integer.valueOf(response.toString()) > 0)
				result = Integer.valueOf(response.toString());

		} catch (Exception e) {
			Log.d("Error in Serial No ", e.getMessage());
		}// End Try Catch
		return result;
	}

	public Integer Android_CheckCreditExists(int SerialNo, int ContractTypeID) {
		String SOAP_ACTION = "http://tempuri.org/Android_CheckCreditExists";

		String OPERATION_NAME = "Android_CheckCreditExists";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		PropertyInfo piPaymentID = new PropertyInfo();
		piPaymentID.setName("SerialNo");
		piPaymentID.setValue(SerialNo);
		piPaymentID.setType(int.class);
		request.addProperty(piPaymentID);

		PropertyInfo piContractTypeID = new PropertyInfo();
		piContractTypeID.setName("ContractTypeID");
		piContractTypeID.setValue(ContractTypeID);
		piContractTypeID.setType(int.class);
		request.addProperty(piContractTypeID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		Integer result = 0;
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object response = envelope.getResponse().toString();
			// result = Boolean.parseBoolean(response.toString());
			result = Integer.valueOf(response.toString());
//			Log.d("Result From Android_Check Credit Exists", "" + response
//					+ "*/*" + result);

		} catch (Exception e) {
//			Log.d("Error in Android_Check Credit Exists ", e.getMessage());
		}// End Try Catch
		return result;
	}

	public Integer InsertContract(Contract ObjContract, int UserID, String AccountName , String AccountNo ,
			String DeviceID) {
		String SOAP_ACTION = "http://tempuri.org/Android_AddContract";

		String OPERATION_NAME = "Android_AddContract";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		PropertyInfo piAdsSectorID = new PropertyInfo();
		piAdsSectorID.setName("AdsSectorID");
		piAdsSectorID.setValue(ObjContract.GetAdsSectorID());
		piAdsSectorID.setType(int.class);
		request.addProperty(piAdsSectorID);//1

		PropertyInfo piDeviceID = new PropertyInfo();//2
		piDeviceID.setName("DeviceID");
		piDeviceID.setValue(DeviceID);
		piDeviceID.setType(String.class);
		request.addProperty(piDeviceID);

		PropertyInfo piAdsTypeID = new PropertyInfo();//3
		piAdsTypeID.setName("AdsTypeID");
		piAdsTypeID.setValue(ObjContract.GetAdsTypeID());
		piAdsTypeID.setType(int.class);
		request.addProperty(piAdsTypeID);

		PropertyInfo piAgencyID = new PropertyInfo();//4
		piAgencyID.setName("AgencyID");
		piAgencyID.setValue(ObjContract.GetAgencyID());
		piAgencyID.setType(int.class);
		request.addProperty(piAgencyID);

		PropertyInfo piClientID = new PropertyInfo();//5
		piClientID.setName("ClientID");
		piClientID.setValue(ObjContract.GetClientID());
		piClientID.setType(long.class);
		request.addProperty(piClientID);

		PropertyInfo piCol = new PropertyInfo();//6
		piCol.setName("Col");
		piCol.setValue(ObjContract.GetCol());
		piCol.setType(int.class);
		request.addProperty(piCol);

		PropertyInfo piContCategoryID = new PropertyInfo();//7
		piContCategoryID.setName("ContCategoryID");
		piContCategoryID.setValue(ObjContract.GetContCategoryID());
		piContCategoryID.setType(int.class);
		request.addProperty(piContCategoryID);

		PropertyInfo piContractTypeID = new PropertyInfo();//8
		piContractTypeID.setName("ContractTypeID");
		piContractTypeID.setValue(ObjContract.GetContractTypeID());
		piContractTypeID.setType(int.class);
		request.addProperty(piContractTypeID);

		PropertyInfo picrmclientID = new PropertyInfo();//9
		picrmclientID.setName("crmclientID");
		picrmclientID.setValue(ObjContract.GetcrmclientID());
		picrmclientID.setType(int.class);
		request.addProperty(picrmclientID);

		PropertyInfo piFirstIssue = new PropertyInfo();//10
		piFirstIssue.setName("FirstIssue");
		piFirstIssue.setValue(ObjContract.GetFirstIssue());
		piFirstIssue.setType(int.class);
		request.addProperty(piFirstIssue);

		PropertyInfo piFree = new PropertyInfo();//11
		piFree.setName("Free");
		piFree.setValue(ObjContract.GetFree());
		piFree.setType(int.class);
		request.addProperty(piFree);

		PropertyInfo piPage_Type = new PropertyInfo();//12
		piPage_Type.setName("Page_Type");
		piPage_Type.setValue(ObjContract.GetPage_Type());
		piPage_Type.setType(int.class);
		request.addProperty(piPage_Type);

		PropertyInfo piPageTypeID = new PropertyInfo();//13
		piPageTypeID.setName("PageTypeID");
		piPageTypeID.setValue(ObjContract.GetPageTypeID());
		piPageTypeID.setType(int.class);
		request.addProperty(piPageTypeID);

		// PropertyInfo piPriceIss = new PropertyInfo();
		// piPriceIss.setName("PriceIss");
		// piPriceIss.setValue(ObjContract.GetPriceIss());
		// piPriceIss.setType(int.class);
		// request.addProperty(piPriceIss);

		PropertyInfo piQtyIss = new PropertyInfo();//14
		piQtyIss.setName("QtyIss");
		piQtyIss.setValue(ObjContract.GetQtyIss());
		piQtyIss.setType(int.class);
		request.addProperty(piQtyIss);

		PropertyInfo piSalesID = new PropertyInfo();//15
		piSalesID.setName("SalesID");
		piSalesID.setValue(ObjContract.GetSalesID());
		piSalesID.setType(int.class);
		request.addProperty(piSalesID);

		PropertyInfo piSCm = new PropertyInfo();//16
		piSCm.setName("SCm");
		piSCm.setValue(ObjContract.GetSCm());
		piSCm.setType(int.class);
		request.addProperty(piSCm);

		PropertyInfo piSerialNo = new PropertyInfo();//17
		piSerialNo.setName("SerialNo");
		piSerialNo.setValue(ObjContract.GetSerialNo());
		piSerialNo.setType(int.class);
		request.addProperty(piSerialNo);

		// ------4 string
		PropertyInfo piContractDate = new PropertyInfo();//18
		piContractDate.setName("ContractDate");
		piContractDate.setValue(ObjContract.GetContractDate());
		piContractDate.setType(String.class);
		request.addProperty(piContractDate);

		PropertyInfo piRefNo = new PropertyInfo();//19
		piRefNo.setName("RefNo");
		piRefNo.setValue(ObjContract.GetRefNo());
		piRefNo.setType(String.class);
		request.addProperty(piRefNo);

		PropertyInfo piSubject = new PropertyInfo();//20
		piSubject.setName("Subject");
		piSubject.setValue(ObjContract.GetSubject());
		piSubject.setType(String.class);
		request.addProperty(piSubject);

		PropertyInfo piAndroidName = new PropertyInfo();//21
		piAndroidName.setName("AndroidName");
		piAndroidName.setValue(ObjContract.getAndroidClientName());
		piAndroidName.setType(String.class);
		request.addProperty(piAndroidName);

		PropertyInfo piAndroidPh_Mobile = new PropertyInfo();//22
		piAndroidPh_Mobile.setName("AndroidPh_Mobile");
		piAndroidPh_Mobile.setValue(ObjContract.getPh_Mobile());
		piAndroidPh_Mobile.setType(String.class);
		request.addProperty(piAndroidPh_Mobile);

		PropertyInfo piInsertUser = new PropertyInfo();//23
		piInsertUser.setName("insertUser");
		piInsertUser.setValue(UserID);
		piInsertUser.setType(int.class);
		request.addProperty(piInsertUser);

		PropertyInfo piSCol = new PropertyInfo();//24
		piSCol.setName("SCol");
		piSCol.setValue(ObjContract.GetSCol());
		piSCol.setType(int.class);
		request.addProperty(piSCol);
		// -------- 1 boolean
		PropertyInfo piStatus = new PropertyInfo();//25
		piStatus.setName("Status");
		piStatus.setValue(ObjContract.GetStatus());
		piStatus.setType(Boolean.class);
		request.addProperty(piStatus);

		PropertyInfo piPublicationID = new PropertyInfo();//25
		piPublicationID.setName("PublicationID");
		piPublicationID.setValue(ObjContract.getPublicationID());
		piPublicationID.setType(int.class);
		request.addProperty(piPublicationID);

		PropertyInfo piAccountName = new PropertyInfo();//25
		piAccountName.setName("UserAccountName");
		piAccountName.setValue(AccountName);
		piAccountName.setType(String.class);
		request.addProperty(piAccountName);

		PropertyInfo piAccountNo = new PropertyInfo();//25
		piAccountNo.setName("UserAccountNo");
		piAccountNo.setValue(AccountNo);
		piAccountNo.setType(String.class);
		request.addProperty(piAccountNo);

        PropertyInfo piDesignID = new PropertyInfo();//25
        piDesignID.setName("DesignID");
        piDesignID.setValue(ObjContract.getDesignID());
        piDesignID.setType(int.class);
        request.addProperty(piDesignID);

		// -----15 double
		request.addProperty("AreaBalance", ObjContract.GetAreaBalance());//26
		request.addProperty("Cm", ObjContract.GetCm());//27
		request.addProperty("CommisAmount", ObjContract.GetCommisAmount());//28
		request.addProperty("CommisPer", ObjContract.GetCommisPer());//29
		request.addProperty("CRNote", ObjContract.GetCRNote());//30
		request.addProperty("CRNotePer", ObjContract.GetCRNotePer());//31
		request.addProperty("CurrentAreaBalance",
				ObjContract.GetCurrentAreaBalance());//32
		request.addProperty("DiscountAmount", ObjContract.GetDiscountAmount());//33
		request.addProperty("DiscountPer", ObjContract.GetDiscountPer());//34
		request.addProperty("GrossTotal", ObjContract.GetGrossTotal());//35
		request.addProperty("NetAmount", ObjContract.GetNetAmount());//36
		request.addProperty("PressTaxAmount", ObjContract.GetPressTaxAmount());//37
		request.addProperty("PressTaxPer", ObjContract.GetPressTaxPer());//38
		request.addProperty("SalesTaxAmount", ObjContract.GetSalesTaxAmount());//39
		request.addProperty("SalesTaxPer", ObjContract.GetSalesTaxPer());//40
		request.addProperty("PriceIss", ObjContract.GetPriceIss());//41

//		request.addProperty("Image", ObjContract.GetImage());//42

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
			Log.d("Insert Contract result ", "" + response + "");
		} catch (Exception e) {
//			Log.d("Error In Insert Save Contract  ", "" + response);
			new CatchMsg();
			CatchMsg.WriteMSG("Contract.Save Contract", e.getMessage());
			res = 0;
			return res;
		}// End Try Catch
		if (Integer.valueOf(response.toString()) > 0) {
			res = Integer.valueOf(response.toString());
		} else {
			res = 0;
		}
		return res;
	}// End Insert Contract

	public Contract GetContractInfoByContractID(int ContractID) {
		List<Contract> list;
		String SOAP_ACTION = "http://tempuri.org/Android_GetContractInfo";
		String OPERATION_NAME = "Android_GetContractInfo";
		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);
		PropertyInfo piPaymentID = new PropertyInfo();
		piPaymentID.setName("ContractID");
		piPaymentID.setValue(ContractID);
		piPaymentID.setType(int.class);
		request.addProperty(piPaymentID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		Object response = null;

		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		Contract item = new Contract();
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response1 = (SoapObject) envelope.getResponse();

			if (response1.toString().equals("anyType{}") || response1 == null) {
				list = null;
			} else {
				list = new ArrayList<Contract>();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					obj3 = (SoapObject) obj2.getProperty(i);
					item.SetClientName((obj3.getProperty("ClientName")
							.toString()));
					item.SetContractDate(obj3.getProperty("ContractDate")
							.toString());
					item.SetAgencyName(obj3.getProperty("AgencyName")
							.toString());
					item.SetCm(Double
							.valueOf(obj3.getProperty("Cm").toString()));
					item.SetCol(Integer.valueOf(obj3.getProperty("Col")
							.toString()));
					item.SetQtyIss(Integer.valueOf(obj3.getProperty("QtyIss")
							.toString()));
					item.SetFree(Integer.valueOf(obj3.getProperty("Free")
							.toString()));
					item.SetNetAmount(Double.valueOf(obj3.getProperty(
							"NetAmount").toString()));
					item.SetPageName(obj3.getProperty("PageName").toString());
					item.SetContractTypeID(Integer.valueOf(obj3.getProperty(
							"ContractTypeID").toString()));
					// item.SetClientID(Integer.valueOf(obj3.getProperty(
					// "ClientID").toString()));
					item.SetSalesID(Integer.valueOf(obj3.getProperty("SalesID")
							.toString()));
					item.SetSubject(obj3.getProperty("Subject").toString());
					item.SetGrossTotal(Double.valueOf(obj3.getProperty(
							"GrossTotal").toString()));
					item.SetFirstIssue(Integer.valueOf(obj3.getProperty(
							"FirstIssue").toString()));
					item.SetSCm(Integer.valueOf(obj3.getProperty("SCm")
							.toString()));
					item.SetPriceIss(Double.valueOf(obj3
							.getProperty("PriceIss").toString()));
					item.SetSCol(Integer.valueOf(obj3.getProperty("SCol")
							.toString()));
					item.SetPageTypeID(Integer.valueOf(obj3.getProperty(
							"PageTypeID").toString()));
					item.SetDiscountPer(Double.valueOf(obj3.getProperty(
							"DiscountPer").toString()));
					item.SetDiscountAmount(Double.valueOf(obj3.getProperty(
							"DiscountAmount").toString()));
					item.SetPressTaxPer(Double.valueOf(obj3.getProperty(
							"PressTaxPer").toString()));
					item.SetPressTaxAmount(Double.valueOf(obj3.getProperty(
							"PressTaxAmount").toString()));
					item.SetSalesTaxPer(Double.valueOf(obj3.getProperty(
							"SalesTaxPer").toString()));
					item.SetSalesTaxAmount(Double.valueOf(obj3.getProperty(
							"SalesTaxAmount").toString()));
					item.SetCommisPer(Double.valueOf(obj3.getProperty(
							"CommisPer").toString()));
					item.SetCommisAmount(Double.valueOf(obj3.getProperty(
							"CommisAmount").toString()));
					item.SetCRNotePer(Double.valueOf(obj3.getProperty(
							"CRNotePer").toString()));
					item.SetCRNote(Double.valueOf(obj3.getProperty("CRNote")
							.toString()));
					item.SetContCategoryID(Integer.valueOf(obj3.getProperty(
							"ContCategoryID").toString()));
					item.SetStatus(Boolean.getBoolean(obj3
							.getProperty("Status").toString()));
					item.SetAgencyID(Integer.valueOf(obj3.getProperty(
							"AgencyID").toString()));
					item.SetAdsTypeID(Integer.valueOf(obj3.getProperty(
							"AdsTypeID").toString()));
					item.SetUnitPrice(Double.valueOf(obj3.getProperty(
							"UnitPrice").toString()));
					item.SetClientID(Integer.valueOf(obj3.getProperty(
							"ClientID").toString()));
					item.SetFirstIssue(Integer.valueOf(obj3.getProperty(
							"FirstIssue").toString()));
					item.SetPage_Type(Integer.valueOf(obj3.getProperty(
							"DetaiedPageID").toString()));
//					item.SetImage(String.valueOf(
//							obj3.getProperty("Image").toString()).getBytes());
//
//					item.setAndroidClientName(IsEmpty(obj3.getProperty(
//							"AndroidName").toString()));
//					item.setPh_Mobile(obj3.getProperty("AndroidPh_Mobile")
//							.toString());
					// list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			CatchMsg.WriteMSG("Contract.GetContractInfoByContractID",
					e.getMessage());
//			Log.d("Contract.GetContractInfoByContractID", e.getMessage());
			return item;
		}// End try Catch
		return item;
	}

	private String IsEmpty(String IsEmpty) {
		if (IsEmpty.contains("anyType")) {
			return " ";
		} else
			return (String) IsEmpty;
	}

	public List<Contract> DaliyWork(Date Date) {
		List<Contract> list = null;
		Contract ObjContract;
		String SOAP_ACTION = "http://tempuri.org/DaliyWork";

		String OPERATION_NAME = "DaliyWork";

		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo piDate = new PropertyInfo();
		piDate.setName("FromDate");
		piDate.setValue(Date);
		piDate.setType(Date.class);
		request.addProperty(piDate);

		PropertyInfo piUserID = new PropertyInfo();
		piUserID.setName("UserID");
//		piUserID.setValue(Main.UserID);
		piUserID.setValue(CoreLogInActivity.UserID);
		piUserID.setType(int.class);
		request.addProperty(piUserID);
//		Log.d("User Id ", ""+Main.UserID);

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
				list = new ArrayList<Contract>();
				SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				Log.d("Result DaliyWork", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					ObjContract = new Contract();
					obj3 = (SoapObject) obj2.getProperty(i);
					ObjContract.SetSerialNo(Integer.parseInt(obj3.getProperty(
							"serialno").toString()));
					ObjContract.SetClientID(Integer.parseInt(obj3.getProperty(
							"ClientID").toString()));
					ObjContract.SetContractDate((obj3
							.getProperty("ContractDate").toString()));
					ObjContract.SetNetAmount(Double.valueOf(obj3.getProperty(
							"NetAmount").toString()));
					ObjContract.SetCm(Double.valueOf(obj3.getProperty("Cm")
							.toString()));
					ObjContract.SetCol(Integer.valueOf(obj3.getProperty("Col")
							.toString()));

					ObjContract.SetPaymentTypeName(obj3.getProperty("Name")
							.toString());
					ObjContract.SetID(Integer.valueOf(obj3.getProperty("id")
							.toString()));
					ObjContract.SetClientName(obj3.getProperty("ClientName")
							.toString().trim());
//					ObjContract.setAndroidClientName(obj3.getProperty(
//							"AndroidName").toString());
//					ObjContract.setPh_Mobile(obj3.getProperty(
//							"AndroidPh_Mobile").toString());
					list.add(ObjContract);
				}// End for
			}// End try
		} catch (Exception e) {
			Log.d("Error Daliy Work ", e.getMessage());
			CatchMsg.WriteMSG("Contract.DaliyWork", e.getMessage());
			list = null;
		}// End try Catch
		return list;
	}// end DaliyWork

	public Integer getPublicationID() {
		return PublicationID;
	}

	public void setPublicationID(Integer publicationID) {
		PublicationID = publicationID;
	}

	public String getAccountName() {
		return AccountName;
	}

	public void setAccountName(String accountName) {
		AccountName = accountName;
	}

	public String getAccountNo() {
		return AccountNo;
	}

	public void setAccountNo(String accountNo) {
		AccountNo = accountNo;
	}

    public Integer getDesignID() {
        return DesignID;
    }

    public void setDesignID(Integer designID) {
        DesignID = designID;
    }

	public  List<Contract> GetCRLimit(String AccountNo) {
		List<Contract> list;
		String SOAP_ACTION = "http://tempuri.org/Android_SelectCRLimit";
		String OPERATION_NAME = "Android_SelectCRLimit";
		SoapObject request = new SoapObject(Connection.NAMESPACE,
				OPERATION_NAME);

		PropertyInfo piPaymentID = new PropertyInfo();

		piPaymentID.setName("AccountNo");
		piPaymentID.setValue(AccountNo);
		piPaymentID.setType(String.class);
		request.addProperty(piPaymentID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);
		Object response = null;

		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
				Connection.ADDRESS);
		Contract item = new Contract();
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response1 = (SoapObject) envelope.getResponse();

			if (response1.toString().equals("anyType{}") || response1 == null) {
				list = null;
			} else {
				list = new ArrayList<Contract>();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					obj3 = (SoapObject) obj2.getProperty(i);

					item.setCRLimit(Double.valueOf(obj3.getProperty("CRLimit")
							.toString()));
					item.setCheckCRLimit(Boolean.valueOf(obj3.getProperty("IsCheckCRLimit")
							.toString()));

					list.add(item);
				}// End for
			}// End try
		} catch (Exception e) {
			CatchMsg.WriteMSG("Contract.Android_SelectCRLimit",
					e.getMessage());
			return null;
		}// End try Catch
		return list;
	}

//	public Double GetCRLimit(String AccountNo) {
//
//		String OPERATION_NAME = "Android_SelectCRLimit";
//		String SOAP_ACTION = "http://tempuri.org/Android_SelectCRLimit";
//
//		SoapObject request = new SoapObject(Connection.NAMESPACE,
//				OPERATION_NAME);
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//				SoapEnvelope.VER11);
//		envelope.setOutputSoapObject(request);
//		envelope.dotNet = true;
//		HttpTransportSE androidHttpTransport = new HttpTransportSE(
//				Connection.ADDRESS);
//		androidHttpTransport.debug = true;
//		PropertyInfo PiAccountNo = new PropertyInfo();
//
//		PiAccountNo.setName("AccountNo");
//		PiAccountNo.setValue(AccountNo);
//		PiAccountNo.setType(String.class);
//		request.addProperty(PiAccountNo);
//
//		androidHttpTransport.debug = true;
//		envelope.setOutputSoapObject(request);
//		envelope.dotNet = true;
//		try {
//			androidHttpTransport.call(SOAP_ACTION, envelope);
//			Object response = (Object) envelope.getResponse().toString().trim();
//			return Double.valueOf(response.toString());
//		} catch (Exception e) {
//			new CatchMsg().WriteMSG("Payment ", e.getMessage());
//			return 0.0;
//		}
//	}


	public Double getCRLimit() {
		return CRLimit;
	}

	public void setCRLimit(Double CRLimit) {
		this.CRLimit = CRLimit;
	}

	public boolean isCheckCRLimit() {
		return IsCheckCRLimit;
	}

	public void setCheckCRLimit(boolean checkCRLimit) {
		IsCheckCRLimit = checkCRLimit;
	}
}// End Class
