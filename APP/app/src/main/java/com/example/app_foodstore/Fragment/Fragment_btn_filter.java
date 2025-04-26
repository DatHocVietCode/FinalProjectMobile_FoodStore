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

import com.example.app_foodstore.Adapter.CategorySpinnerAdapter;
import com.example.app_foodstore.Model.CategoryModel;
import com.example.app_foodstore.R;
import com.example.app_foodstore.databinding.FragmentBtnFilterBinding;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Fragment_btn_filter extends Fragment {
    FragmentBtnFilterBinding binding;
    AppCompatSpinner spinner;
    public Fragment_btn_filter() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                    String nameSort = "Tên: Mặc định";
                    if (nameId == R.id.radioNameAZ) nameSort = "Tên: A → Z";
                    else if (nameId == R.id.radioNameZA) nameSort = "Tên: Z → A";

                    // Giá
                    int priceId = radioGroupPrice.getCheckedRadioButtonId();
                    String priceSort = "Giá: Mặc định";
                    if (priceId == R.id.radioPriceLowHigh) priceSort = "Giá: Thấp → Cao";
                    else if (priceId == R.id.radioPriceHighLow) priceSort = "Giá: Cao → Thấp";

                    Toast.makeText(getActivity(),
                            nameSort + "\n" + priceSort, Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                });
                btnCancel.setOnClickListener(v -> dialog.dismiss());
                dialog.show();
            }
        });
        return binding.getRoot();
    }
    private void setupSpinner(Dialog dialog) {
        spinner = dialog.findViewById(R.id.spinnerCategory);
        List<CategoryModel> categoryList = new ArrayList<>();
        categoryList.add(new CategoryModel(0, "All", ""));
        categoryList.add(new CategoryModel(1, "Category A", ""));
        categoryList.add(new CategoryModel(2, "Category B", ""));
        categoryList.add(new CategoryModel(3, "Category C", ""));
        categoryList.add(new CategoryModel(4, "Category D", ""));

        CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(
                requireContext(),
                categoryList,
                new CategorySpinnerAdapter.OnCategorySelectedListener() {
                    @Override
                    public void OnCategorySelectedListener(CategoryModel categoryModel, int position) {
                        // Đặt lại selected item
                        spinner.post(() -> spinner.setSelection(position));

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
        // Đặt mặc định là dòng đầu tiên (không chọn voucher)
        spinner.setSelection(0);
    }
}
