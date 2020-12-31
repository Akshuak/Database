package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import static com.example.registration.Database.COLUMN_EMAIL;
import static com.example.registration.Database.COLUMN_PASSWORD;
import static com.example.registration.Database.TABLE_USER;

public class Sign_in extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout in_email,in_password;
    TextView tv_signUp;
    Button btn_signIN;

    SQLiteDatabase sqLiteDatabase;
    Database storeDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        in_email = findViewById(R.id.in_email);
        in_password = findViewById(R.id.in_password);
        btn_signIN = findViewById(R.id.btn_signIN);
        tv_signUp = findViewById(R.id.tv_signUp);

        btn_signIN.setOnClickListener(this);
        tv_signUp.setOnClickListener(this);

        storeDatabase = new Database(this);
        sqLiteDatabase = storeDatabase.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signIN:
                boolean loginAccount = true;

                String userEmail = in_email.getEditText().getText().toString();
                String userPassword = in_password.getEditText().getText().toString();

                if (userEmail.isEmpty()){
                    in_email.setError("Try again!");
                    loginAccount = false;
                }
                if (userPassword.isEmpty()){
                    in_password.setError("Try again!");
                    loginAccount = false;
                }
                if (loginAccount){

                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " +
                            COLUMN_EMAIL+ "=? AND "+COLUMN_PASSWORD+ "=? ",new String[]{userEmail,userPassword});

                    if (cursor != null & cursor.getCount() > 0){
                        cursor.moveToFirst();
                        Toast.makeText(this, "Welcome!"+userEmail, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Sign_in.this,MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.tv_signUp:
                Intent intent = new Intent(Sign_in.this,Sign_up.class);
                startActivity(intent);
                break;
        }
    }
}
