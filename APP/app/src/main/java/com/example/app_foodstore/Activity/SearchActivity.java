package com.example.app_foodstore.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_foodstore.Fragment.Fragment_BottomNavigation;
import com.example.app_foodstore.Fragment.Fragment_SearchBar;
import com.example.app_foodstore.R;

public class SearchActivity extends AppCompatActivity {
    Fragment_BottomNavigation bottomNavigationFragment;
    Fragment_SearchBar searchBarFragment;
    ImageButton btn_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (savedInstanceState == null) {
            bottomNavigationFragment = new Fragment_BottomNavigation();
            searchBarFragment = new Fragment_SearchBar();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ss_fragment_bottomNavigation_container, bottomNavigationFragment)
                    .replace(R.id.ss_fragment_searchBar_container, searchBarFragment)
                    .runOnCommit(() -> {
                        // Fragment đã được commit và View đã sẵn sàng
                        EditText editText = searchBarFragment.getSearchEditText();
                        editText.setFocusable(true);
                        editText.setFocusableInTouchMode(true);
                        editText.requestFocus();
                        LinearLayout searchBar = searchBarFragment.getSearchBar();
                        searchBar.setFocusable(false);
                        searchBar.setClickable(false);
                    })
                    .commit();
        } else {
            bottomNavigationFragment = (Fragment_BottomNavigation) getSupportFragmentManager()
                    .findFragmentById(R.id.ss_fragment_bottomNavigation_container);
            searchBarFragment = (Fragment_SearchBar) getSupportFragmentManager()
                    .findFragmentById(R.id.ss_fragment_searchBar_container);
        }
        AnhXa();
    }
    private void AnhXa() {
        btn_back = findViewById(R.id.ss_header_btn_back);
        btn_back.setOnClickListener(v -> finish());

    }
}
