package com.example.projetintegrateur;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class pageCompteConnection extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_compte_connection);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initWidget();
    }

    public void initWidget()
    {
        Button cancelBtn = (Button) findViewById(R.id.accConn_btn_cancel);
        Button confBtn = (Button) findViewById(R.id.accConn_btn_conf);

        cancelBtn.setOnClickListener(this);
        confBtn.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if(v.getId() == R.id.accConn_btn_cancel)
        {
            finish();
        } else if (v.getId() == R.id.accConn_btn_conf) {

            int code = 0;

            //get all inputs
            EditText mail = (EditText) findViewById(R.id.accConn_txtin_mail);
            EditText pw = (EditText) findViewById(R.id.accConn_txtpw_pw);

            ArrayList<String> content = new ArrayList<String>();

            content.add(mail.getText().toString());
            content.add(pw.getText().toString());


            int index = 0;

            while(index < content.size() && code == 0)
            {

                if(content.get(index).matches("^\\w*[$&+,:;=?#|'<>^*()%!-].*$") || TextUtils.isEmpty(content.get(index)))
                {
                    Toast.makeText(getApplicationContext(), "Un champ contient des characrères speciaux ou est vide", Toast.LENGTH_LONG).show();
                    code = 1;
                }
                index++;
            }

            //mail correct
            if(!Patterns.EMAIL_ADDRESS.matcher(content.get(0)).matches())
            {
                Toast.makeText(getApplicationContext(), "Un courriel est requis pour se connecter", Toast.LENGTH_LONG).show();
                code = 2;
            }


            if(code == 0)
            {

                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
                int err = sqLiteManager.connectUser(content.get(0), content.get(1));

                if(err == 1)
                {
                    Toast.makeText(getApplicationContext(), "Un courriel ou le mot de passe est invalide", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(pageCompteConnection.this, pageCompteMenu.class);
                    finish();
                    startActivity(intent);
                }

            }
        }
    }



}