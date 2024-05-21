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

public class pageCompteEditPW extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_compte_edit_pw);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initWidgets();
    }

    private void initWidgets()
    {


        Button cancelBtn = (Button) findViewById(R.id.accEditPw_btn_cancel);
        Button confBtn = (Button) findViewById(R.id.accEditPw_btn_conf);

        cancelBtn.setOnClickListener(this);
        confBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.accCrea_btn_cancel)
        {
            finish();
        } else if (v.getId() == R.id.accCrea_btn_conf) {

            int code = 0;

            //get all inputs
            EditText oldPw = (EditText) findViewById(R.id.accEditPw_txtpw_oldpw);
            EditText newPw = (EditText) findViewById(R.id.accEditPw_txtpw_newpw);
            EditText confPw = (EditText) findViewById(R.id.accEditPw_txtpw_confpw);

            ArrayList<String> content = new ArrayList<String>();
            content.add(oldPw.toString());
            content.add(newPw.toString());
            content.add(confPw.toString());

            int index = 0;

            while(index < content.size() && code == 0)
            {
                if(content.get(index).matches("[$&+,\\/\\\\\\[\\]:;=?@#|'<>.^*()%!-]") || content.get(index).trim().isEmpty())
                {
                    code = 1;
                }
                index++;
            }

            if(!newPw.toString().equals(confPw.toString()))
            {
                code = 3;
            }

            if(code == 0)
            {
                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
                sqLiteManager.updateUserPW(oldPw.toString(), newPw.toString());

                finish();
            }
        }
    }
}