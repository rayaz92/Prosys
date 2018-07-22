package com.example.amir.core.ADR.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.amir.core.R;

public class EditTextActivity extends AppCompatActivity {

    TextView txtDesignRequestNoteHeader ;
    EditText txtDesignRequestDesignNote ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
//        txtDesignRequestNote.setEnabled(false);
        try {

            txtDesignRequestNoteHeader = (TextView) findViewById(R.id.txtDesignRequestNoteHeader);
            txtDesignRequestDesignNote = (EditText) findViewById(R.id.txtDesignRequestDesignNote);


            Intent i = getIntent();
            String Header = i.getStringExtra("DesignRequestNoteHeader");
            String Note = i.getStringExtra("DesignRequestNote");
            txtDesignRequestNoteHeader.setText(Header);
            txtDesignRequestDesignNote.setText(Note);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
