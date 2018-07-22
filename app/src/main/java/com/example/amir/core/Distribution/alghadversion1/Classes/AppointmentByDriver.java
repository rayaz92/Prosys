package com.example.amir.core.Distribution.alghadversion1.Classes;

//import java.sql.Date;
//import java.text.SimpleDateFormat;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

//import java.util.Set;
//import com.example.alghadversion1.R;
//import com.example.alghadversion1.R.id;
//import com.example.alghadversion1.R.layout;
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Color;
//import android.renderscript.Element;
//import android.sax.StartElementListener;
//import android.text.style.BackgroundColorSpan;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.View.OnClickListener;
//import android.webkit.WebView.FindListener;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.CheckedTextView;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;

public class AppointmentByDriver {

	private int ID = 0;
	private int ClientID = 0;
	private int AppointmentTypeID = 0;
	private String AppointmentTypeName = "";
	private String ClientName = "";
	private String CompanyName = "";
	private String ClientPhone = "";
	private String ClientMobile = "";
	/* *** */private String ToDate = "";
	/* *** */private String FromDate = "";
	private double NetAmount = 0.0;
	private int CopiesNo = 0;
	private int ContractTypeID = 0;
	/* *** */private String AppointmentTime = "";
	private String AppointmentNote = "";
	private String Address = "";
	private String AddressComments = "";
	private String NationalNo = "";
	private int ContractID = 0;
	private int SubscriptionTypeID = 0;
	private int PaymentTypeID = 0;
	/* *** */private String MaxGraceDate = "";

	public AppointmentByDriver() {
	}

	public double getNetAmount() {
		return this.NetAmount;
	}

	public void setNetAmount(double NetAmount) {
		this.NetAmount = NetAmount;
	}

	public void setAppointmentTypeName(String AppointmentTypeName) {
		this.AppointmentTypeName = AppointmentTypeName;
	}

	public String GetAppointmentTypeName() {
		return this.AppointmentTypeName;
	}

	public void setAppointmentTypeID(int AppointmentTypeID) {
		this.AppointmentTypeID = AppointmentTypeID;
	}

	public int GetAppointmentTypeID() {
		return this.AppointmentTypeID;
	}

	public int getContractID() {
		return this.ContractID;
	}

	public void setContractID(int ContractId) {
		ContractID = ContractId;
	}

	public int getID() {
		return this.ID;
	}

	public int getClientID() {
		return this.ClientID;
	}

	public int getCopiesNo() {
		return this.CopiesNo;
	}

	public int getContractTypeID() {
		return this.ContractTypeID;
	}

	public int getSubscriptionTypeID() {
		return this.SubscriptionTypeID;
	}

	public int getPaymentTypeID() {
		return this.PaymentTypeID;
	}

	public void setID(int Id) {
		this.ID = Id;
	}

	public void setClientID(int ClientId) {
		this.ClientID = ClientId;
	}

	public void setCopiesNo(int CopiesNo) {
		this.CopiesNo = CopiesNo;
	}

	public void setContractTypeID(int ContractTypeId) {
		this.ContractTypeID = ContractTypeId;
	}

	public void setSubscriptionTypeID(int SubscriptionTypeID) {
		this.SubscriptionTypeID = SubscriptionTypeID;
	}

	public void setPaymentTypeID(int PaymentTypeID) {
		this.PaymentTypeID = PaymentTypeID;
	}

	public void setFromDate(String FromDate) {
		this.FromDate = FromDate;
	}

	public String getClientName() {
		return this.ClientName;
	}

	public String getCompanyName() {
		return this.CompanyName;
	}

	public String getClientPhone() {
		return this.ClientPhone;
	}

	public String getClientMobile() {
		return this.ClientMobile;
	}

	public String getToDate() {
		return this.ToDate;
	}

	public String GetFromDate() {
		return this.FromDate;
	}

	public String getAppointmentTime() {
		return this.AppointmentTime;
	}

	public String getAppointmentNote() {
		return this.AppointmentNote;
	}

	public String getAddress() {
		return this.Address;
	}

	public String getAddressComments() {
		return this.AddressComments;
	}

	public String getNationalNo() {
		return this.NationalNo;
	}

	public String getMaxGraceDate() {
		return this.MaxGraceDate;
	}

	public void setClientName(String ClientName) {
		this.ClientName = ClientName;
	}

	public void setCompanyName(String CompanyName) {
		this.CompanyName = CompanyName;
	}

	public void setClientPhone(String ClientPhone) {
		this.ClientPhone = ClientPhone;
	}

	public void setClientMobile(String ClientMobile) {
		this.ClientMobile = ClientMobile;
	}

	public void setToDate(String ToDate) {
		this.ToDate = ToDate;
	}

	public void setAppointmentTime(String AppointmentTime) {
		this.AppointmentTime = AppointmentTime;
	}

	public void setAppointmentNote(String AppointmentNote) {
		this.AppointmentNote = AppointmentNote;
	}

	public void setAddress(String Address) {
		this.Address = Address;
	}

	public void setAddressComments(String AddressComments) {
		this.AddressComments = AddressComments;
	}

	public void setNationalNo(String NationalNo) {
		this.NationalNo = NationalNo;
	}

	public void setMaxGraceDate(String MaxGraceDate) {
		this.MaxGraceDate = MaxGraceDate;
	}

	public List<AppointmentByDriver> GetAppointmentByDriver(int DriverID,
                                                            String TimeFrom) {
		List<AppointmentByDriver> list = null;
		String OPERATION_NAME = "GetAppointmentByDriver";

		String SOAP_ACTION = "http://tempuri.org/GetAppointmentByDriver";

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
			pi.setName("DriverID");
			pi.setValue(DriverID);
			pi.setType(int.class);
			request.addProperty(pi);

			PropertyInfo piTimeFrom = new PropertyInfo();
			piTimeFrom.setName("AppointmentTimeFrom");
			piTimeFrom.setValue(TimeFrom);
			piTimeFrom.setType(String.class);
			request.addProperty(piTimeFrom);

			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response.toString().equals("anyType{}") || response == null) {
				list = null;
			} else {
				list = new ArrayList<AppointmentByDriver>();
				// SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
				// SoapObject o = (SoapObject) envelope.getResponse();
				SoapObject obj, obj1, obj2, obj3;
				obj = (SoapObject) envelope.getResponse();
				obj1 = (SoapObject) obj.getProperty("diffgram");
				obj2 = (SoapObject) obj1.getProperty("NewDataSet");
				// Log.d("R", "" + resultsRequestSOAP + "");

				for (int i = 0; i < obj2.getPropertyCount(); i++) {
					AppointmentByDriver objAppointmentsClass = new AppointmentByDriver();
					obj3 = (SoapObject) obj2.getProperty(i);

					objAppointmentsClass.setID((Integer.parseInt(obj3
							.getProperty("ID").toString())));// value
					objAppointmentsClass.setClientID((Integer.parseInt(obj3
							.getProperty("ClientID").toString())));// value
					objAppointmentsClass.setContractTypeID((Integer
							.parseInt(obj3.getProperty("ContractTypeID")
									.toString())));// value
					objAppointmentsClass.setCopiesNo((Integer.parseInt(obj3
							.getProperty("CopiesNo").toString())));// value
					objAppointmentsClass.setNetAmount((Double.parseDouble(obj3
							.getProperty("NetAmount").toString())));// value

					objAppointmentsClass.setContractID((Integer.parseInt(obj3
							.getProperty("ContractID").toString())));// value
					objAppointmentsClass.setPaymentTypeID((Integer
							.parseInt(obj3.getProperty("PaymentTypeID")
									.toString())));// value
					objAppointmentsClass.setSubscriptionTypeID((Integer
							.parseInt(obj3.getProperty("SubscriptionTypeID")
									.toString())));// value

					objAppointmentsClass.setClientName(String
							.valueOf(IsEmpty(obj3.getProperty("ClientName")
									.toString())));
					objAppointmentsClass.setAddress(String.valueOf(IsEmpty(obj3
							.getProperty("Address").toString())));
					objAppointmentsClass
							.setAddressComments(String
									.valueOf(IsEmpty(obj3.getProperty(
											"AddressComments").toString())));
					objAppointmentsClass
							.setAppointmentNote(String
									.valueOf(IsEmpty(obj3.getProperty(
											"AppointmentNote").toString())));
					objAppointmentsClass
							.setAppointmentTime(String
									.valueOf(IsEmpty(obj3.getProperty(
											"AppointmentTime").toString())));
					objAppointmentsClass.setClientMobile(String
							.valueOf(IsEmpty(obj3.getProperty("ClientMobile")
									.toString())));
					objAppointmentsClass.setClientPhone(String
							.valueOf(IsEmpty(obj3.getProperty("ClientPhone")
									.toString())));
					objAppointmentsClass.setCompanyName(String
							.valueOf(IsEmpty(obj3.getProperty("CompanyName")
									.toString())));
					objAppointmentsClass.setMaxGraceDate(String
							.valueOf(IsEmpty(obj3.getProperty("MaxGraceDate")
									.toString())));
					objAppointmentsClass.setNationalNo(String
							.valueOf(IsEmpty(obj3.getProperty("NationalNo")
									.toString())));
					objAppointmentsClass.setAppointmentTypeID((Integer
							.parseInt(obj3.getProperty("AppointmentTypeID")
									.toString())));
					objAppointmentsClass.setAppointmentTypeName(String
							.valueOf(IsEmpty(obj3.getProperty(
									"AppointmentTypeName").toString())));
					objAppointmentsClass.setToDate(String.valueOf(IsEmpty(obj3
							.getProperty("ToDate").toString())));
					objAppointmentsClass.setFromDate(String
							.valueOf(IsEmpty(obj3.getProperty("FromDate")
									.toString())));
					list.add(objAppointmentsClass);
				}// End for
			}// End Else
		}// End try
		catch (Exception e) {
			// Log.d("Error in Appointment By Drivaer ", e.getMessage());
			Log.e("Appointment By Driver", e.getMessage());
			// CatchMsg.WriteMSG("Appointment By Driver", e.getMessage());
			list = null;
			return list;
		}// End try Catch
		return list;
	}// End GetAppointmentByDriver

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.toString();
	}// End To String

	private Object IsEmpty(Object Obj) {

		if (Obj.equals("anyType{}")) {
			return " Empty ";
		}// End if
		else
			return Obj;
	}// End Is Empty

}// End Class AppointmentByDriver
