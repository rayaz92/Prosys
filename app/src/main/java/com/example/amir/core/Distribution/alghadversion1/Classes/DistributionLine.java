package com.example.amir.core.Distribution.alghadversion1.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class DistributionLine {
    private int ID			=0;
    private int DistributorID = 0;
    private String DistributorNameEn = "";
    private String DistributorNameAr = "";
    private int ContractID =0 ;
    private String SubscriberName= "";
    private String distributorName="";
    private int ClientID =0;
    private Double Latitude = 0.0;
    private Double Longtiude = 0.0;

    public void setLatitude(Double Latitude) {
        this.Latitude = Latitude;
    }

    public Double GetLatitude() {
        return this.Latitude;
    }

    public void setLongtiude(Double Longtiude) {
        this.Longtiude = Longtiude;
    }

    public Double GetLongtiude() {
        return this.Longtiude;
    }

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

    public void SetClientID(int ClientID) {
        this.ClientID= ClientID;
    }

    public int GetClientID() {
        return this.ClientID;
    }

    public List<DistributionLine> GetDailyDistributionByDistributorAndSupervisor(int DistributorID, int SupervisorID, int AreaID, int LineID) {
        List<DistributionLine> list = null;
        list = null;
        String OPERATION_NAME = "GetDailyDistributionByDistributorAndSupervisorAndroid";

        String SOAP_ACTION = "http://tempuri.org/GetDailyDistributionByDistributorAndSupervisorAndroid";

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
            PropertyInfo  pi = new PropertyInfo();
            pi.setName("DistributorID");
            pi.setValue(DistributorID);
            pi.setType(int.class);
            request.addProperty(pi);

            PropertyInfo  pi2 = new PropertyInfo();
            pi2.setName("SupervisorID");
            pi2.setValue(SupervisorID);
            pi2.setType(int.class);
            request.addProperty(pi2);

            PropertyInfo  pi3 = new PropertyInfo();
            pi3.setName("AreaID");
            pi3.setValue(AreaID);
            pi3.setType(int.class);
            request.addProperty(pi3);

            PropertyInfo  pi4 = new PropertyInfo();
            pi4.setName("LineID");
            pi4.setValue(LineID);
            pi4.setType(int.class);
            request.addProperty(pi4);


            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();

            if (response.toString().equals("anyType{}") || response == null) {
                list = null;
            } else {
                list = new ArrayList<DistributionLine>();
                SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                SoapObject o = (SoapObject) envelope.getResponse();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");
                // Log.d("R", "" + resultsRequestSOAP + "");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    DistributionLine item = new DistributionLine();
                    obj3 = (SoapObject) obj2.getProperty(i);
                    item.SetID(Integer.parseInt(obj3.getProperty("ContractDetailsID")
                            .toString()));
                    item.SetClientID(Integer.parseInt(obj3.getProperty("ClientID")
                            .toString()));
                    item.SetContractID(Integer.parseInt(obj3.getProperty("ContractNo")
                            .toString()));
                    item.SetDistributorNameAr(obj3.getProperty("distributorName").toString());
                    // value of column 2 NameEn
                    item.setSubscriberName(obj3.getProperty("ClientName").toString());
                    item.setLatitude((Double.parseDouble(obj3
                            .getProperty("Latitude").toString())));// value
                    item.setLongtiude((Double.parseDouble(obj3
                            .getProperty("Longitude").toString())));// value

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



    public Integer InsertContractDetailsLocation(DistributionLine objDistributionLine, int DistributorID) {

        String SOAP_ACTION = "http://tempuri.org/InsertContractDetailsLocation";

        String OPERATION_NAME = "InsertContractDetailsLocation";

        SoapObject request = new SoapObject(Connection.NAMESPACE,
                OPERATION_NAME);

        PropertyInfo propertyinfo = new PropertyInfo();
        propertyinfo.setName("ID");
        propertyinfo.setValue(objDistributionLine.GetID());
        propertyinfo.setType(String.class);
        request.addProperty(propertyinfo);/* 00 */


        propertyinfo = new PropertyInfo();
        propertyinfo.setName("Latitude ");
        propertyinfo.setValue(objDistributionLine.GetLatitude());
        propertyinfo.setType(String.class);
        request.addProperty(propertyinfo);/* 02 */

        propertyinfo = new PropertyInfo();
        propertyinfo.setName("Longitude");
        propertyinfo.setValue(objDistributionLine.GetLongtiude());
        propertyinfo.setType(String.class);
        request.addProperty(propertyinfo);/* 03 */

        propertyinfo = new PropertyInfo();
        propertyinfo.setName("DistributorID");
        propertyinfo.setValue(DistributorID);
        propertyinfo.setType(int.class);
        request.addProperty(propertyinfo);/* 03 */



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
            Log.d("Error In Insert Save Pos Location  ", "" + response);
            res = 0;
            // CatchMsg.WriteMSG("Insert Save SubscriberLocation ",
            // exception.getMessage()
            // + "\n" + response.toString());
            return res;
        }// End try Catch
        if (Integer.valueOf(response.toString()) > 0) {

            res = Integer.valueOf(response.toString());
        } else {
            res = 0;
        }
        return res;
    }// End SubscriberLocation


    public Boolean UpdatecontractDetailsLocation(DistributionLine objDistributionLine){//int ID,Double Latitude,Double Longitude){//,Double Latitude,Double Longitude) {
        String SOAP_ACTION = "http://tempuri.org/UpdatecontractDetailsLocation";

        String OPERATION_NAME = "UpdatecontractDetailsLocation";

        SoapObject request = new SoapObject(Connection.NAMESPACE,
                OPERATION_NAME);


        PropertyInfo  pi1 = new PropertyInfo();
        pi1.setName("ID");
        pi1.setValue(objDistributionLine.GetID());
        pi1.setType(String.class);
        request.addProperty(pi1);

        PropertyInfo  pi2 = new PropertyInfo();
        pi2.setName("Latitude");
        pi2.setValue(objDistributionLine.GetLatitude());
        pi2.setType(String.class);
        request.addProperty(pi2);

        PropertyInfo  pi3 = new PropertyInfo();
        pi3.setName("Longitude");
        pi3.setValue(objDistributionLine.GetLongtiude());
        pi3.setType(String.class);
        request.addProperty(pi3);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(1);

        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
                Connection.ADDRESS);
        HttpTransportSE httpTransport = new HttpTransportSE(Connection.ADDRESS);

        boolean re = false;

        try {
            new MarshalFloat().register(envelope);
            new MarshalBase64().register(envelope);

            envelope.encodingStyle = SoapEnvelope.ENC;
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

    public String toString() {
        // TODO Auto-generated method stub
        return this.toString();
    }// End To String
}
