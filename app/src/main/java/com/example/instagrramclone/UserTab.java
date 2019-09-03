package com.example.instagrramclone;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserTab extends Fragment implements AdapterView.OnItemClickListener , AdapterView.OnItemLongClickListener{

    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;

    public UserTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_tab, container, false);

        final TextView tvloadinguser = view.findViewById(R.id.loadingusertext);
        listView = view.findViewById(R.id.listview);
        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arrayList);

        listView.setOnItemClickListener(UserTab.this);
        listView.setOnItemClickListener(UserTab.this);

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> usersfromserver, ParseException e) {

                if(e==null){
                    if(usersfromserver.size()>0){
                        for (ParseUser user: usersfromserver){
                            arrayList.add(user.getUsername());
                        }
                        listView.setAdapter(arrayAdapter);
                        tvloadinguser.animate().alpha(0).setDuration(2000); //data has came disable textview
                        listView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        return  view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //So wee want detail of user on cliked
        //so we are sending data from usertba to userpost activyt
        //with the help of intent , also make arraylist<String>
        Intent intent = new Intent(getContext(),Userposts.class);
        intent.putExtra("username",arrayList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo("username",arrayList.get(position));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user!=null && e==null){
                   // Toast.makeText(getContext(),user.get("Profession")+"",Toast.LENGTH_LONG).show();

                    final PrettyDialog prettyDialog =  new PrettyDialog(getContext());

                    prettyDialog.setTitle(user.getUsername() + " 's Info")
                            .setMessage(user.get("Bio") + "\n"
                                    + user.get("Profession") + "\n"
                                    + user.get("Hobbies") + "\n"
                                    + user.get("Sport"))
                            .setIcon(R.drawable.person)
                            .addButton(
                                    "OK",     // button text
                                    R.color.pdlg_color_white,  // button text color
                                    R.color.pdlg_color_green,  // button background color
                                    new PrettyDialogCallback() {  // button OnClick listener
                                        @Override
                                        public void onClick() {
                                            // Do what you gotta do
                                            prettyDialog.dismiss();
                                        }
                                    }
                            )
                            .show();




                }
            }
        });
        return true;
    }
}
