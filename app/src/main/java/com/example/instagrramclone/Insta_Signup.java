package com.example.instagrramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Insta_Signup extends AppCompatActivity {

    Button btnsignup,btnsignup_tologin;
    EditText edsignupname,edsignupemail,edsignuppass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insta__signup);

        btnsignup_tologin=(Button)findViewById(R.id.btn_signup_tologin);
        btnsignup=(Button)findViewById(R.id.btnsigunp);
        edsignupemail=(EditText) findViewById(R.id.ed_signup_email);
        edsignupname=(EditText) findViewById(R.id.ed_sigup_name);
        edsignuppass=(EditText) findViewById(R.id.ed_signup_pass);

        edsignuppass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER &&
                event.getAction()==KeyEvent.ACTION_DOWN){
                    btnsignup.callOnClick();
                }
                return false;
            }
        });
        btnsignup_tologin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Insta_Signup.this,Activity_Sign_in.class);
            startActivity(intent);
        }
    });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ParseUser appuser = new ParseUser();
                appuser.setEmail(edsignupemail.getText().toString());
                appuser.setUsername(edsignupname.getText().toString());
                appuser.setPassword(edsignuppass.getText().toString());

                if(edsignupemail.getText().equals("")||edsignupname.getText().equals("")||edsignuppass.getText().equals("")){
                 Toast.makeText(Insta_Signup.this,"Pleases fill all fieled",Toast.LENGTH_LONG).show();
                }else {

                    final ProgressDialog progressDialog = new ProgressDialog(Insta_Signup.this);
                    progressDialog.setMessage("Signing Up You" + edsignupname.getText().toString());
                    progressDialog.show();

                    appuser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(Insta_Signup.this, appuser.getUsername() + " is Signed up", Toast.LENGTH_LONG).show();
                                transitionSocailmediaActivity();
                            } else {
                                Toast.makeText(Insta_Signup.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();

                        }
                    });
                }
                }

        });
    }
    public  void Roottaplayout(View view){

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }
        }

        private  void transitionSocailmediaActivity(){
                Intent intent = new Intent(Insta_Signup.this,SocialMediaActivity.class);
                startActivity(intent);
                finish();
    }


}
