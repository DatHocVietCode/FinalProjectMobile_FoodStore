package com.example.app_foodstore.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_foodstore.Adapter.OrderOnGoingAdapter;
import com.example.app_foodstore.Activity.UserUtils;
import com.example.app_foodstore.Model.MyOrderPendingDTO;
import com.example.app_foodstore.ViewModel.OrderViewModel;
import com.example.app_foodstore.databinding.FragmentOrderOngoingBinding;

public class Fragment_order_ongoing extends Fragment {

    private OrderViewModel orderViewModel;
    private FragmentOrderOngoingBinding binding;
    private OrderOnGoingAdapter adapter;

    public Fragment_order_ongoing() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderOngoingBinding.inflate(inflater, container, false);

        binding.fragmentOrderOngoingRc.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrderOnGoingAdapter();

        // Đăng ký listener cho adapter
        adapter.setOnOrderClickListener(new OrderOnGoingAdapter.OnOrderClickListener() {
            @Override
            public void onTrackClicked(MyOrderPendingDTO order) {
                // TODO: Xử lý khi người dùng bấm Track
            }

            @Override
            public void onCancelClicked(MyOrderPendingDTO order) {
                String token = "Bearer " + UserUtils.getTokenFromPreferences(requireContext());
                // Gọi ViewModel hủy đơn hàng
                orderViewModel.cancelOrder(token, order.getIdOrder());
            }
        });

        binding.fragmentOrderOngoingRc.setAdapter(adapter);

        // Lấy token và gọi API load đơn hàng đang xử lý
        String token = "Bearer " + UserUtils.getTokenFromPreferences(requireContext());
        orderViewModel.loadPendingOrders(token);

        // Lắng nghe dữ liệu trả về cập nhật adapter
        orderViewModel.getOngoingOrders().observe(getViewLifecycleOwner(), orders -> {
            if (orders != null) {
                adapter.setData(orders);
                binding.fragmentOrderOngoingRc.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        return binding.getRoot();
    }
}
