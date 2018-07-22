package com.example.amir.core.ADR.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amir.core.ADR.activity.Classes.Issue;
import com.example.amir.core.R;

public class AdsTextActivity extends AppCompatActivity {

    EditText txtAdsText;
    Button btnadrAdsTextSave ,btnadrAdsTextCancel ;

    Integer ContractID , ContractPublishedDetailID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adr_activity_ads_text);
//        getActionBar().hide();

        txtAdsText = (EditText) findViewById(R.id.txtAdsText);

        btnadrAdsTextSave = (Button) findViewById(R.id. btnadrAdsTextSave) ;
        btnadrAdsTextCancel = (Button) findViewById(R.id. btnadrAdsTextCancel) ;

        Intent i = getIntent();

        ContractID = i.getIntExtra("ContractID" , 0);
        ContractPublishedDetailID = i.getIntExtra("ContractPublishedDetailID" , 0);

        btnadrAdsTextSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Android_UpdateContractPublishedDetailAdsText().execute(String.valueOf(ContractPublishedDetailID),
                        txtAdsText.getText().toString());
            }
        });
        btnadrAdsTextCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = "";
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",result);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        });
    }

    class Android_UpdateContractPublishedDetailAdsText extends AsyncTask<String, String, String> {
        ShowProgressDialog objProgressDialog;

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            objProgressDialog = new ShowProgressDialog(AdsTextActivity.this,
                    getString(R.string.Please_Wait), getString(R.string.Wait),0);
            objProgressDialog.ShowDialog();

        }

        @Override
        protected String doInBackground(String... params) {
            String x = "";
            try {
                Issue objIssue = new Issue();
                Boolean Done =objIssue.Android_UpdateContractPublishedDetailAdsText(Integer.valueOf(params[0]) ,
                       params[1]);

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
            if(result.toString().equals("True")) {
                Toast.makeText(AdsTextActivity.this, getString(R.string.Save_Done), Toast.LENGTH_SHORT).show();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",result);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }
        }
    }
}