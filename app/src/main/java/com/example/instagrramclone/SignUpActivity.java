package com.example.instagrramclone;

import android.content.pm.SigningInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
        private  EditText edname,edspeed,edpower,edpunch,edvalue;
        private  Button btnsave,btnbringalldata;
        private TextView getdata;
        String allkickboxers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnsave=(Button) findViewById(R.id.btnsave);
        btnsave.setOnClickListener(SignUpActivity.this);
        edname=(EditText)findViewById(R.id.ed1);
        edpower=(EditText) findViewById(R.id.ed2);
        edpunch=(EditText) findViewById(R.id.ed3);
        edspeed=(EditText) findViewById(R.id.ed4);
        edvalue=(EditText) findViewById(R.id.ed5);
        getdata=(TextView)findViewById(R.id.gettext);
        btnbringalldata=(Button) findViewById(R.id.btngetalldata);

        getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBooxer");
                parseQuery.getInBackground("B9KVe05D6D", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object!=null && e==null){
                            getdata.setText("Name: "+object.get("name")+"-"+ "Punch speed: "+object.get("punch"));
                        }

                    }
                });
            }
        });

        btnbringalldata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allkickboxers="";
                ParseQuery<ParseObject> queryall = ParseQuery.getQuery("KickBooxer");
                queryall.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if(e==null){
                            if(objects.size()>0){
                                for(ParseObject kickboxer:objects){
                                    allkickboxers= allkickboxers+kickboxer.get("name")+"\n";

                                }
                                Toast.makeText(getApplicationContext(),allkickboxers,Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

            }
        });

    }

    @Override
    public void onClick(View v) {

        final  ParseObject kickbocer =  new ParseObject("KickBooxer");
        kickbocer.put("name",edname.getText().toString());
        kickbocer.put("punchspeed",edspeed.getText().toString());
        kickbocer.put("punchpower",edpower.getText().toString());
        kickbocer.put("puchvalue",edvalue.getText().toString());
        kickbocer.put("punch",edpunch.getText().toString());
            kickbocer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        Toast.makeText(SignUpActivity.this,"Data save is"+kickbocer.get("name"),Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(SignUpActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
    }
//    public  void helleworldtap (View view){
//
//        final ParseObject boxer = new ParseObject("Boxer");
//        boxer.put("Punchspeed",200);
//        boxer.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e==null){
//               Toast.makeText(SignUpActivity.this,boxer.get("Punchspeed")+ "Data saved in server",Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//
//    }
}
