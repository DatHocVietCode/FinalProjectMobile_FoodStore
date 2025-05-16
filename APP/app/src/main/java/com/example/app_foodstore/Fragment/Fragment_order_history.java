package com.example.app_foodstore.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_foodstore.Activity.AddressActivity;
import com.example.app_foodstore.Activity.UserUtils;
import com.example.app_foodstore.Adapter.OrderHistoryAdapter;
import com.example.app_foodstore.Interface.AddressSelectionListener;
import com.example.app_foodstore.Model.OrderModel;
import com.example.app_foodstore.Model.response.AddressResponse;
import com.example.app_foodstore.ViewModel.OrderViewModel;
import com.example.app_foodstore.databinding.FragmentOrderHistoryBinding;

public class Fragment_order_history extends Fragment implements AddressSelectionListener {
    OrderViewModel orderViewModel;
    private ActivityResultLauncher<Intent> addressLauncher;
    private Long idAddress;
    private EditText etAddress;
    FragmentOrderHistoryBinding binding;
    public Fragment_order_history() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.fragmentOrderHistoryRc.setLayoutManager(layoutManager);



        String token = "Bearer " + UserUtils.getTokenFromPreferences(requireContext());
        orderViewModel.loadCompleteOrders(token);

        // Quan sát dữ liệu từ ViewModel
        orderViewModel.getHistoryOrders().observe(getViewLifecycleOwner(), orders -> {
            if (orders != null)
            {
                OrderHistoryAdapter adapter = new OrderHistoryAdapter(requireActivity(), orders, this);
                binding.fragmentOrderHistoryRc.setAdapter(adapter);
                adapter.setData(orders);
                Log.d("OrderHistory", "OrderHistory observe được dữ liệu mới ");
            }

        });
        addressLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                AddressResponse selectedAddress = (AddressResponse) result.getData().getSerializableExtra("selectedAddress");
                if (selectedAddress != null) {
                    // Gọi hàm của interface để cập nhật địa chỉ vào EditText
                    idAddress = selectedAddress.getId();
                    if (etAddress != null) {
                        etAddress.setText(selectedAddress.getAddress());
                        Toast.makeText(requireContext(), "Đã chọn địa chỉ: " + selectedAddress.getAddress(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onAddressEditClicked(EditText etAddress) {
        this.etAddress = etAddress;
        Intent intent = new Intent(requireContext(), AddressActivity.class);
        addressLauncher.launch(intent);
    }
}
