package com.example.instagrramclone;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    Button btnupdateinfo;

    EditText edprofessions,edhobby,edbios,edsports,edprofilenames;
    public ProfileTab() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile_tab, container, false);//Fragment is alreeay attch to activity so false
            edprofilenames=view.findViewById(R.id.edprofilename);
            edbios=view.findViewById(R.id.edbio);
            edhobby=view.findViewById(R.id.edhobbies);
            edsports=view.findViewById(R.id.edsport);
            edprofessions=view.findViewById(R.id.edprofesssion);

            btnupdateinfo = view.findViewById(R.id.btnupdateinfo);

        final ParseUser parseUser = ParseUser.getCurrentUser();

            if(parseUser.get("ProfileName")==null){
                edprofilenames.setText("");
            }else{
                edprofilenames.setText(parseUser.get("ProfileName").toString());
            }

            edprofessions.setText(parseUser.get("Profession")+ "");                 //Why using + "" concatentaion empty//to save app from crash using tostring() while data is not aavailable on server will crash tha app
            //edprofilenames.setText(parseUser.get("ProfileName") + "");
            edbios.setText(parseUser.get("Bio")+ "");
            edhobby.setText(parseUser.get("Hobbies")+ "");
            edsports.setText(parseUser.get("Sport")+ "");

            btnupdateinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    parseUser.put("ProfileName",edprofilenames.getText().toString());
                    parseUser.put("Profession",edprofessions.getText().toString());
                    parseUser.put("Bio",edbios.getText().toString());
                    parseUser.put("Sport",edsports.getText().toString());
                    parseUser.put("Hobbies",edhobby.getText().toString());

                    parseUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {


                            if(e==null ){
                                Toast.makeText(getContext(), "Information Is Saved in the background", Toast.LENGTH_SHORT).show();
                            }else
                            {
                                Toast.makeText(getContext(), "Error in saving", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });


            return  view;

    }

}
