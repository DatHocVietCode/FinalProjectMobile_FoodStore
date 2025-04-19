package com.example.app_foodstore.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.SearchResultAdapter_rc;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    RecyclerView ss_result_rc_searchResult;
    List<FoodModel> foods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // Lấy keyword để sau này gọi API hoặc lọc danh sách kết quả
        String keyword = getIntent().getStringExtra("keyword");
        if (keyword != null) {
            Log.d("SearchResult", "Từ khóa: " + keyword);
        }

        AnhXa(); // khởi tạo RecyclerView
    }


    private void AnhXa() {
        setupRcSearchResult();
        setupBtnFilter();
    }

    private void setupBtnFilter() {
        ImageButton filterBtn = findViewById(R.id.ss_result_header_btn_filter);
        filterBtn.setOnClickListener(v -> {
            Dialog dialog = new Dialog(SearchResultActivity.this);
            dialog.setContentView(R.layout.dialog_filter);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            RadioGroup radioGroupName = dialog.findViewById(R.id.radioGroupName);
            RadioGroup radioGroupPrice = dialog.findViewById(R.id.radioGroupPrice);
            Button btnOk = dialog.findViewById(R.id.btnOk);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);

            btnOk.setOnClickListener(view -> {
                // Tên
                int nameId = radioGroupName.getCheckedRadioButtonId();
                String nameSort = "Tên: Mặc định";
                if (nameId == R.id.radioNameAZ) nameSort = "Tên: A → Z";
                else if (nameId == R.id.radioNameZA) nameSort = "Tên: Z → A";

                // Giá
                int priceId = radioGroupPrice.getCheckedRadioButtonId();
                String priceSort = "Giá: Mặc định";
                if (priceId == R.id.radioPriceLowHigh) priceSort = "Giá: Thấp → Cao";
                else if (priceId == R.id.radioPriceHighLow) priceSort = "Giá: Cao → Thấp";

                Toast.makeText(SearchResultActivity.this,
                        nameSort + "\n" + priceSort, Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            });
            btnCancel.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        });
    }

    private void setupRcSearchResult() {
        ss_result_rc_searchResult = findViewById(R.id.ss_result_rc_searchResult);
        foods = new ArrayList<>();
        foods.add(new FoodModel("Cate 1", "Món ăn 1",1));
        foods.add(new FoodModel("Cate 2", "Món ăn 2",1));
        foods.add(new FoodModel("Cate 3", "Món ăn 3",1));
        foods.add(new FoodModel("Cate 4", "Món ăn 4",1));
        foods.add(new FoodModel("Cate 5", "Món ăn 5",1));
        foods.add(new FoodModel("Cate 6", "Món ăn 6",1));
        foods.add(new FoodModel("Cate 7", "Món ăn 7",1));
        foods.add(new FoodModel("Cate 8", "Món ăn 8",1));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        ss_result_rc_searchResult.setLayoutManager(layoutManager);
        ss_result_rc_searchResult.setAdapter(new SearchResultAdapter_rc(this, foods));
    }
}
