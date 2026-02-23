package com.example.time.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.time.R;
import com.example.time.db.DatebaseHelper;



public class MemoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {;
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memo, container, false);
    }
}