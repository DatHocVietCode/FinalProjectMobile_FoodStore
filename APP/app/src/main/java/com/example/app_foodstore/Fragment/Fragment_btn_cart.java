package com.example.app_foodstore.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_foodstore.Activity.CartActivity;
import com.example.app_foodstore.Activity.HomeScreenActivity;
import com.example.app_foodstore.databinding.FragmentBtnCartBinding;

public class Fragment_btn_cart extends Fragment {
    FragmentBtnCartBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBtnCartBinding.inflate(inflater, container, false);
        binding.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
    public void updateCartNotify(int count)
    {
        if (count > 0)
        {
            binding.cartNotify.setVisibility(View.VISIBLE);
            binding.cartNotify.setText(String.valueOf(count));
        }
        else
        {
            binding.cartNotify.setVisibility(View.INVISIBLE);
        }
    }
}
