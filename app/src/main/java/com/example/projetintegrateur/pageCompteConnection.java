package com.example.projetintegrateur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

            content.add(mail.toString());
            content.add(pw.toString());


            int index = 0;

            while(index < content.size() && code == 0)
            {
                if(content.get(index).matches("[$&+,\\/\\\\\\[\\]:;=?@#|'<>.^*()%!-]") || content.get(index).trim().isEmpty())
                {
                    code = 1;
                }
                index++;
            }

            //mail correct
            if(!mail.toString().matches("^\\w+@\\w+\\.\\w+$"))
            {
                code = 2;
            }


            if(code == 0)
            {

                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
                sqLiteManager.connectUser(mail.toString(), pw.toString());

                //redirect to connect
                Intent intent = new Intent(pageCompteConnection.this, pageCompteMenu.class);
                finish();
                startActivity(intent);
            }
        }
    }



}