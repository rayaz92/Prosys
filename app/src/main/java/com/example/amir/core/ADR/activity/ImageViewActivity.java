package com.example.amir.core.ADR.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.amir.core.ADR.activity.Classes.DesignRequest;
import com.example.amir.core.ADR.activity.Classes.Issue;
import com.example.amir.core.R;

import java.net.URL;
import java.util.List;

public class ImageViewActivity extends AppCompatActivity {

    ImageView img;
    WebView wv;
    Button btnApprove , btnDesignIDEdit,btnImgViewDesignIDUpdate;
    //Spinner spnImageViewDesignID;

    String url ,ClientName = "" ;
    Integer ClientID , ContractID , ContractTypeID , ContractPublishedDetailID ,ContractStatusID, DesignID ,IssueID, IssueNo,SerialNo , PublicationID;
    Double AdsCm ,AdsCol ;
    Boolean IsSalesApprove ;

    List<DesignRequest> DesignRequestlist;
    ArrayAdapter<DesignRequest> arrayAdapterDesignRequest;

    URL u ;
    Bitmap bmp;
//    Integer NewDesignID = 0 ;
    AlertDialog alertDialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        try {
            img  = (ImageView) findViewById(R.id.img);
            wv = (WebView) findViewById(R.id.wv);
            btnApprove = (Button) findViewById(R.id.btnApprove);
            btnDesignIDEdit = (Button) findViewById(R.id.btnDesignIDEdit);
//            spnImageViewDesignID = (Spinner)findViewById(R.id.spnImageViewDesignID);
            btnImgViewDesignIDUpdate = (Button)findViewById(R.id.btnImgViewDesignIDUpdate);

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContractStatusID == 5 && IsSalesApprove == false) {
                    new Approve().execute();
                }else {
                    Toast.makeText(ImageViewActivity.this, getString(R.string.NotAllowed) , Toast.LENGTH_SHORT).show();
                }
            }
        });

            btnDesignIDEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (IsSalesApprove == false) {
                        new Android_SelectDesignRequest().execute();
                    }
                }
            });

            btnImgViewDesignIDUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //new UpdateContractPublishDetailDesignID().execute();

                    if(IsSalesApprove == false && ClientID > 0){
                        Intent i = new Intent(ImageViewActivity.this , DesignRequestActivity.class);
                        i.putExtra("ClientName", ClientName);
                        startActivity(i);
                    }
                }
            });

            Intent i = getIntent();
            url = i.getStringExtra("url");


            ClientID = i.getIntExtra("ClientID",0);
            ContractID = i.getIntExtra("ContractID" , 0);
            ContractTypeID = i.getIntExtra("ContractTypeID" , 0);
            ContractPublishedDetailID= i.getIntExtra("ContractPublishedDetailID" , 0);
            ContractStatusID = i.getIntExtra("ContractStatusID" , 0);
            DesignID = i.getIntExtra("DesignID" , 0);
            IssueID = i.getIntExtra("IssueID",0);
            SerialNo = i.getIntExtra("SerialNo" , 0);
            IsSalesApprove = i.getBooleanExtra("IsSalesApprove",true);
            ClientName = i.getStringExtra("ClientName");

//            AdsCm = i.getDoubleExtra("AdsCm",0.0);
//            AdsCol = i.getDoubleExtra("AdsCol" , 0.0);
            PublicationID = i.getIntExtra("PublicationID" , 0);
            IssueNo= i.getIntExtra("IssueNo",0);

            if(ContractPublishedDetailID  == 0){
                btnApprove.setVisibility(View.INVISIBLE);
                btnDesignIDEdit.setVisibility(View.INVISIBLE);
//                spnImageViewDesignID.setVisibility(View.INVISIBLE);
                btnImgViewDesignIDUpdate.setVisibility(View.INVISIBLE);;
            }
            if(IsSalesApprove == true){
                btnApprove.setEnabled(false);
                btnDesignIDEdit.setEnabled(false);
//                spnImageViewDesignID.setEnabled(false);
                btnImgViewDesignIDUpdate.setEnabled(false);
            }
//            if(url.endsWith("pdf")) {
//
//                img.setVisibility(View.GONE);
//                wv.setVisibility(View.VISIBLE);
//
//                wv.getSettings().setJavaScriptEnabled(true);
//                wv.loadUrl(url);
//            }else {
                new GetImage().execute();
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class GetImage extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(ImageViewActivity.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {

                if(url.endsWith("pdf")) {
                    bmp = BitmapFactory.decodeResource(getResources(),R.drawable.pdf);
                }else {
                    u = new URL(url);
                    bmp = BitmapFactory.decodeStream(u.openConnection().getInputStream());
                }

            } catch (Exception e) {
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if(bmp != null){
            img.setImageBitmap(bmp);
        }else {
                img.setImageResource(R.drawable.logo);
            }
        }
    }

    class Approve extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(ImageViewActivity.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {
                Issue objIssue = new Issue();
                DesignRequest objDesignRequest = new DesignRequest();
                Boolean Done =objIssue.UpdateSalesApprove(ContractPublishedDetailID);
                Boolean UpdateDesignApprove = objDesignRequest.UpdateDesignRequestApprove(DesignID , 4,
//                        IssueID,ContractTypeID,SerialNo,ClientID,PublicationID);
                        IssueNo,ContractTypeID,SerialNo,ClientID,PublicationID);

                if(Done)
                    x = "True";
            } catch (Exception e) {
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if(result.toString().equals("True"))
                Toast.makeText(ImageViewActivity.this,getString(R.string.Save_Done),Toast.LENGTH_SHORT).show();
//            ImageViewActivity.this.finish();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",result);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
    }

    class Android_SelectDesignRequest extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;


        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(ImageViewActivity.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait), 0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {
                DesignRequest objDesignRequest = new DesignRequest();
                DesignRequestlist = objDesignRequest.Android_SelectDesignRequest(ClientID);

            } catch (Exception e) {
                Log.d("Do In Back ", e.getMessage());
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if (DesignRequestlist != null) {

                arrayAdapterDesignRequest= new ArrayAdapter<DesignRequest>(ImageViewActivity.this,
                        android.R.layout.simple_spinner_item, DesignRequestlist);
                arrayAdapterDesignRequest
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//                spnImageViewDesignID.setAdapter(arrayAdapterDesignRequest);
//                btnImgViewDesignIDUpdate.setEnabled(true);
                onCreateDialogSingleChoice(arrayAdapterDesignRequest);
            }
        }
    }

    class UpdateContractPublishDetailDesignID extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;

        //int NewDesignID = DesignRequestlist.get(spnImageViewDesignID.getId()).getID();
        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(ImageViewActivity.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {
                Issue objIssue = new Issue();
                Boolean Done =objIssue.UpdateContractPublishDetailDesignID(ContractPublishedDetailID , Integer.valueOf(params[0]));
                if(Done) {
                    x = "True";
                }
            } catch (Exception e) {
            }
            return x;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            objProgressDialog.EndDialog();
            if(result.toString().equals("True"))
                Toast.makeText(ImageViewActivity.this,getString(R.string.Save_Done),Toast.LENGTH_SHORT).show();
//            ImageViewActivity.this.finish();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",result);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
    }

    public void onCreateDialogSingleChoice(ArrayAdapter adapterArea){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setSingleChoiceItems(adapterArea, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO Auto-generated method stub
                ListView lw = ((AlertDialog)dialog).getListView();
                Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                DesignRequest SelectedArea = (DesignRequest)checkedItem;
                //Double c = Double.valueOf(SelectedArea.getCm());
               // Double cl = Double.valueOf(SelectedArea.getCol());
                //if( c == AdsCm && cl== AdsCol) {
                    Toast.makeText(getApplicationContext(),
                            checkedItem.toString(), Toast.LENGTH_SHORT).show();
                    int NewDesignID = SelectedArea.getID();
                    new UpdateContractPublishDetailDesignID().execute(String.valueOf(NewDesignID));
                //}else{
//                    Toast.makeText(getApplicationContext(),
//                            getString(R.string.DesignSizeDoesntMatchAdsSize), Toast.LENGTH_SHORT).show();
//                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }
}
