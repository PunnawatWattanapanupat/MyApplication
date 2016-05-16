package com.example.arashi.myapplication.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.arashi.myapplication.Store.UserLocalStore;

/**
 * Created by Ooppo on 16/5/2559.
 */
public class TabFragment5 extends Fragment {


Button yesButton,noButton;
    UserLocalStore userLocalStore;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
              View v = inflater.inflate(R.layout.tab_fragment_5, container, false);
            yesButton = (Button) v.findViewById(R.id.yesButton);
            noButton = (Button) v.findViewById(R.id.noButton);


            userLocalStore = new UserLocalStore(getActivity());
            if(userLocalStore.getLoggedInUser().is_teacher == 1){

                yesButton.setVisibility(View.GONE);
                noButton.setVisibility(View.GONE);

            }else{
                yesButton.setVisibility(View.VISIBLE);
                noButton.setVisibility(View.VISIBLE);
            }

                return v;
        }




}

