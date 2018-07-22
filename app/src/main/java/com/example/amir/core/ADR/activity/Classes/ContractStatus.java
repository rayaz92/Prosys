package com.example.amir.core.ADR.activity.Classes;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class ContractStatus {

    private Integer ID  = 0 ;
    private String Name = "" ;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<ContractStatus> getContractStatus() {
        List<ContractStatus> list = null;
        String OPERATION_NAME = "Android_SelectContractStatus";

        String SOAP_ACTION = "http://tempuri.org/Android_SelectContractStatus";

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
                list = new ArrayList<ContractStatus>();
                SoapObject resultsRequestSOAP = (SoapObject) envelope.bodyIn;
                SoapObject o = (SoapObject) envelope.getResponse();
                SoapObject obj, obj1, obj2, obj3;
                obj = (SoapObject) envelope.getResponse();
                obj1 = (SoapObject) obj.getProperty("diffgram");
                obj2 = (SoapObject) obj1.getProperty("NewDataSet");
                Log.d("R", "" + resultsRequestSOAP + "");

                for (int i = 0; i < obj2.getPropertyCount(); i++) {
                    ContractStatus item = new ContractStatus();
                    obj3 = (SoapObject) obj2.getProperty(i);
                    item.setID(Integer.parseInt(obj3.getProperty(
                            "ID").toString()));
                    item.setName(obj3.getProperty(
                            "Name").toString());

                    list.add(item);
                }// End for
            }// End try
        } catch (Exception e) {
            // CatchMsg.WriteMSG("Payment Type", e.getMessage());
            list = null;
            return list;
        }// End try Catch
        return list;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return Name;
    }// End To String

}