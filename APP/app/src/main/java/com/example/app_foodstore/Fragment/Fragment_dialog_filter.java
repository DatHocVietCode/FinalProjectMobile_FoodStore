package com.example.app_foodstore.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.app_foodstore.Adapter.CategorySpinnerAdapter;
import com.example.app_foodstore.Model.CategoryModel;
import com.example.app_foodstore.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Fragment_dialog_filter extends DialogFragment {
    private AppCompatSpinner spinner;
    private OnFilterSelectedListener listener;

    public interface OnFilterSelectedListener {
        void onFilterSelected(String nameSort, String priceSort, CategoryModel selectedCategory);
    }

    public void setOnFilterSelectedListener(OnFilterSelectedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.fragment_dialog_filter);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        RadioGroup radioGroupName = dialog.findViewById(R.id.radioGroupName);
        RadioGroup radioGroupPrice = dialog.findViewById(R.id.radioGroupPrice);
        Button btnOk = dialog.findViewById(R.id.btnOk);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        setupSpinner(dialog);

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

            CategoryModel selectedCategory = (CategoryModel) spinner.getSelectedItem();

            if (listener != null) {
                listener.onFilterSelected(nameSort, priceSort, selectedCategory);
            }

            dismiss();
        });
        btnCancel.setOnClickListener(view -> dismiss());

        return dialog;
    }

    private void setupSpinner(Dialog dialog) {
        spinner = dialog.findViewById(R.id.spinnerCategory);
        List<CategoryModel> categoryList = new ArrayList<>();
        categoryList.add(new CategoryModel(0, "All", ""));
        categoryList.add(new CategoryModel(1, "Category A", ""));
        categoryList.add(new CategoryModel(2, "Category B", ""));
        categoryList.add(new CategoryModel(3, "Category C", ""));
        categoryList.add(new CategoryModel(4, "Category D", ""));

        CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(requireContext(), categoryList, (categoryModel, position) -> {
            spinner.post(() -> spinner.setSelection(position));
            try {
                Method method = Spinner.class.getDeclaredMethod("onDetachedFromWindow");
                method.setAccessible(true);
                method.invoke(spinner);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
    }
}
