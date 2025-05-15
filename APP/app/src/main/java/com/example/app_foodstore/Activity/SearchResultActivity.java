package com.example.app_foodstore.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_foodstore.Adapter.CategorySpinnerAdapter;
import com.example.app_foodstore.Adapter.FoodTabLayoutAdapter;
import com.example.app_foodstore.Adapter.SearchResultAdapter_rc;
import com.example.app_foodstore.Fragment.Fragment_btn_cart;
import com.example.app_foodstore.Fragment.Fragment_btn_filter;
import com.example.app_foodstore.Model.CategoryModel;
import com.example.app_foodstore.Model.FoodModel;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.FilterViewModel;
import com.example.app_foodstore.ViewModel.FoodViewModel;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    RecyclerView ss_result_rc_searchResult;
    List<FoodModel> foods;
    FoodViewModel foodViewModel;
    FilterViewModel filterViewModel;
    Long categoryId;
    String sortByName;
    String sortByPrice;
    String keyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // Lấy keyword để sau này gọi API hoặc lọc danh sách kết quả
        keyword = getIntent().getStringExtra("keyword");
        if (keyword != null) {
            Log.d("SearchResult", "Từ khóa: " + keyword);
        }

        AnhXa(); // khởi tạo RecyclerView
    }


    private void AnhXa() {
        initViewModel();
        setupRcSearchResult();
        setupBtnFilter();
        initObservers();
    }

    private void initObservers() {
        if (filterViewModel == null) {
            Log.e("observe", "initObservers: filterViewModel is null");
            return;
        }

        filterViewModel.setFilters(categoryId, sortByName, sortByPrice);
        MediatorLiveData<Object> mediator = new MediatorLiveData<>();
        mediator.addSource(filterViewModel.getCategoryId(), val -> mediator.setValue(new Object()));
        mediator.addSource(filterViewModel.getSortByName(), val -> mediator.setValue(new Object()));
        mediator.addSource(filterViewModel.getSortByPrice(), val -> mediator.setValue(new Object()));
        mediator.observe(this, val -> getFoodsFromKeyWord());
    }

    private void initViewModel() {
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        filterViewModel = new ViewModelProvider(this).get(FilterViewModel.class);
    }

    private void setupBtnFilter() {
        /*ImageButton filterBtn = findViewById(R.id.ss_result_header_btn_filter);
        filterBtn.setOnClickListener(v -> {

        });*/
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment_btn_filter btn_filter = new Fragment_btn_filter();
        transaction.replace(R.id.ss_result_fragmentContainer_btnFilter, btn_filter);
        transaction.commit();
    }

    private void setupRcSearchResult() {
        // Chỉ setup layout manager thôi, chưa set adapter
        ss_result_rc_searchResult = findViewById(R.id.ss_result_rc_searchResult);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        ss_result_rc_searchResult.setLayoutManager(layoutManager);

        // Gọi hàm lấy dữ liệu từ ViewModel
        getFoodsFromKeyWord();
    }

    private void getFoodsFromKeyWord() {
        String keyword = getIntent().getStringExtra("keyword");

        // Lấy các giá trị từ FilterViewModel
        Long categoryId = filterViewModel.getCategoryId().getValue();
        String nameSort = filterViewModel.getSortByName().getValue();
        String priceSort = filterViewModel.getSortByPrice().getValue();

        Log.d("FilterDebug", "getFoodsFromKeyWord: " +
                "Keyword: " + keyword + ", CategoryId: " + categoryId +
                ", NameSort: " + nameSort + ", PriceSort: " + priceSort);

        // Gọi API lấy danh sách sản phẩm với các filter
        foodViewModel.getFoods(keyword, categoryId, nameSort, priceSort)
                .observe(this, foodModels -> {
                    if (foodModels != null && !foodModels.isEmpty()) {
                        foods = foodModels;

                        // Sau khi có dữ liệu thì mới set adapter
                        SearchResultAdapter_rc adapter = new SearchResultAdapter_rc(this, foods);
                        ss_result_rc_searchResult.setAdapter(adapter);

                        // Thông báo cập nhật dữ liệu
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d("FilterDebug", "Không tìm thấy sản phẩm phù hợp với bộ lọc.");
                        Toast.makeText(this, "Không tìm thấy sản phẩm phù hợp!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
