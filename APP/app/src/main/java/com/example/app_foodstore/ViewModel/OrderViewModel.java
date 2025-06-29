package com.example.app_foodstore.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_foodstore.Model.MyOrderPendingDTO;
import com.example.app_foodstore.Repo.OrderRepository;
import com.example.app_foodstore.APIService.Order.APIServiceOrder;

import java.util.List;

public class OrderViewModel extends ViewModel {

    private final OrderRepository orderRepository;

    private final MutableLiveData<List<MyOrderPendingDTO>> ongoingOrders = new MutableLiveData<>();
    private final MutableLiveData<List<MyOrderPendingDTO>> historyOrders = new MutableLiveData<>();

    public OrderViewModel() {
        orderRepository = new OrderRepository();
    }

    public LiveData<List<MyOrderPendingDTO>> getOngoingOrders() {
        return ongoingOrders;
    }

    public LiveData<List<MyOrderPendingDTO>> getHistoryOrders() {
        return historyOrders;
    }

    public void loadPendingOrders() {
        orderRepository.fetchPendingOrders(ongoingOrders);
    }

    public void loadCompleteOrders() {
        orderRepository.fetchCompleteOrders(historyOrders);
    }

    public void cancelOrder(String token, Long orderId) {
        orderRepository.cancelOrder(token, orderId, new OrderRepository.CancelCallback() {
            @Override
            public void onCancelSuccess() {
                // Reload ongoing orders after cancel success
                loadPendingOrders();
            }

            @Override
            public void onCancelFailed(String errorMessage) {
                // Handle cancel error if needed
            }
        });
    }

}
