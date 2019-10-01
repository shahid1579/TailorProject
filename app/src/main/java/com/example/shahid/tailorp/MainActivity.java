package com.example.shahid.tailorp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button register, log_in;
    EditText Full_Name, Last_Name, Email, Password ,MobileNumber,Address_Name,City_Name;
    String Full_Name_Holder,  EmailHolder, PasswordHolder,MobileNumber_Holder,Address_Holder,City_Holder;
    String finalResult ;
    String HttpURL = "192.168.10.4/TailorProject/UserRegistration.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign Id'S
        Full_Name = (EditText)findViewById(R.id.editText_Name);

        Email = (EditText)findViewById(R.id.editTextEmail);
        Password = (EditText)findViewById(R.id.editTextPassword);
        MobileNumber = (EditText)findViewById(R.id.editText_Number);
        Address_Name=(EditText)findViewById(R.id.editTextAddress);
        City_Name=(EditText)findViewById(R.id.editTextCity);

        register = (Button)findViewById(R.id.Submit);
        log_in = (Button)findViewById(R.id.Login);

        //Adding Click Listener on button.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    // If EditText is not empty and CheckEditText = True then this block will execute.

                    UserRegisterFunction(Full_Name_Holder, EmailHolder, PasswordHolder,MobileNumber_Holder,Address_Holder,City_Holder);

                }
                else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(MainActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }


            }
        });

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,UserLoginActivity.class);
                startActivity(intent);

            }
        });

    }

    public void CheckEditTextIsEmptyOrNot(){

        Full_Name_Holder = Full_Name.getText().toString();

        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();
        MobileNumber_Holder = Last_Name.getText().toString();
        City_Holder = City_Name.getText().toString();
        Address_Holder = Last_Name.getText().toString();


        if(TextUtils.isEmpty(Full_Name_Holder) || TextUtils.isEmpty(MobileNumber_Holder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }

    public void UserRegisterFunction(final String User_Name, final String email, final String password, final String number, final String address,final String city){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(MainActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(MainActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("f_name",params[0]);



                hashMap.put("email",params[1]);

                hashMap.put("password",params[2]);

                hashMap.put("number",params[3]);

                hashMap.put("address",params[4]);

                hashMap.put("city",params[5]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(User_Name,email,password,number,address,city);
    }
    }

