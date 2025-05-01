package com.example.app_foodstore.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.app_foodstore.Adapter.CategorySpinnerAdapter;
import com.example.app_foodstore.Model.CategoryModel;
import com.example.app_foodstore.R;
import com.example.app_foodstore.ViewModel.CateViewModel;
import com.example.app_foodstore.ViewModel.FilterViewModel;
import com.example.app_foodstore.databinding.FragmentBtnFilterBinding;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Fragment_btn_filter extends Fragment {
    FragmentBtnFilterBinding binding;
    CateViewModel cateViewModel;
    AppCompatSpinner spinner;
    private Long selectedCategoryId = 0L; // mặc định là All
    private boolean isCategoryListPrepared = false;
    FilterViewModel filterViewModel;
    public Fragment_btn_filter() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        filterViewModel = new ViewModelProvider(requireActivity()).get(FilterViewModel.class);
        binding = FragmentBtnFilterBinding.inflate(inflater, container, false);
        binding.btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.fragment_dialog_filter);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                RadioGroup radioGroupName = dialog.findViewById(R.id.radioGroupName);
                RadioGroup radioGroupPrice = dialog.findViewById(R.id.radioGroupPrice);
                Button btnOk = dialog.findViewById(R.id.btnOk);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);

                setupSpinner(dialog);

                btnOk.setOnClickListener(v -> {
                    // Tên
                    int nameId = radioGroupName.getCheckedRadioButtonId();
                    String nameSort = "";
                    if (nameId == R.id.radioNameAZ) nameSort = "acs";
                    else if (nameId == R.id.radioNameZA) nameSort = "decs";

                    // Giá
                    int priceId = radioGroupPrice.getCheckedRadioButtonId();
                    String priceSort = "";
                    if (priceId == R.id.radioPriceLowHigh) priceSort = "acs";
                    else if (priceId == R.id.radioPriceHighLow) priceSort = "decs";

                    Toast.makeText(getActivity(),
                            nameSort + "\n" + priceSort, Toast.LENGTH_SHORT).show();

                    filterViewModel.setFilters(selectedCategoryId, nameSort, priceSort);
                    dialog.dismiss();
                });
                btnCancel.setOnClickListener(v -> dialog.dismiss());
                dialog.show();
            }
        });
        Bundle args = getArguments();
        if (args != null) {
            selectedCategoryId = args.getLong("categoryId", 0L);
        }
        return binding.getRoot();
    }
    private void setupSpinner(Dialog dialog) {
        spinner = dialog.findViewById(R.id.spinnerCategory);
        cateViewModel = new ViewModelProvider(this).get(CateViewModel.class);
        cateViewModel.getAllCate().observe(getViewLifecycleOwner(), categoryModels -> {
            if (categoryModels != null && !categoryModels.isEmpty()) {
                if (!isCategoryListPrepared) {
                    categoryModels.add(0, new CategoryModel(0L, "All", ""));
                    isCategoryListPrepared = true;
                }
                setupCategorySpinner(categoryModels);
            }
        });
    }

    private void setupCategorySpinner(List<CategoryModel> categoryModels) {
        CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(
                requireContext(),
                categoryModels,
                new CategorySpinnerAdapter.OnCategorySelectedListener() {
                    @Override
                    public void OnCategorySelectedListener(CategoryModel categoryModel, int position) {
                        // Đặt lại selected item
                        spinner.post(() -> spinner.setSelection(position));
                        selectedCategoryId = categoryModel.getId();
                        // Thực hiện hack để spinner reset dropdown (nếu cần)
                        try {
                            Method method = Spinner.class.getDeclaredMethod("onDetachedFromWindow");
                            method.setAccessible(true);
                            method.invoke(spinner);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        spinner.setAdapter(adapter);
        spinner.setSelection(Math.toIntExact(selectedCategoryId));
    }

}
