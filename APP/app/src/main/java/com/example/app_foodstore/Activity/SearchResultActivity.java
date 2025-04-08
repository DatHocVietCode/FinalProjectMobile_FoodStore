package com.example.app_foodstore.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.R;

public class SearchResultActivity extends AppCompatActivity {
    RecyclerView ss_result_rc_searchResult;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_search_results);

        AnhXa();
    }

    private void AnhXa() {
        ss_result_rc_searchResult = findViewById(R.id.ss_result_rc_searchResult);
        
    }
}
