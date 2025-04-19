package com.example.app_foodstore.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.app_foodstore.Activity.FoodDetailActivity;
import com.example.app_foodstore.R;

public class Fragment_foodDisplay2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food_display2, container, false);
        CardView cardView = rootView.findViewById(R.id.fragment_food_display2_container);
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FoodDetailActivity.class);
            startActivity(intent);
        });
        return rootView;
    }
}
