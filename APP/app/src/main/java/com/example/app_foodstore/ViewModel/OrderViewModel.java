package com.example.app_foodstore.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.app_foodstore.Model.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends ViewModel {
    // Danh sách các đơn hàng đang xử lý (Ongoing)
    private final MutableLiveData<List<OrderModel>> ongoingOrders = new MutableLiveData<>(new ArrayList<>());

    // Danh sách các đơn hàng đã hoàn thành (History)
    private final MutableLiveData<List<OrderModel>> historyOrders = new MutableLiveData<>(new ArrayList<>());

    public OrderViewModel() {
        // Khởi tạo danh sách đơn hàng
        ongoingOrders.setValue(mockOngoingData());
        historyOrders.setValue(mockHistoryData());
    }
    // Tạo danh sách đơn hàng giả cho Ongoing
    private List<OrderModel> mockOngoingData() {
        List<OrderModel> list = new ArrayList<>();
        list.add(new OrderModel(false));
        return list;
    }

    // Tạo danh sách đơn hàng giả cho History
    private List<OrderModel> mockHistoryData() {
        List<OrderModel> list = new ArrayList<>();
        list.add(new OrderModel(true));
        return list;
    }
    public LiveData<List<OrderModel>> getOngoingOrders() {
        return ongoingOrders;
    }

    public LiveData<List<OrderModel>> getHistoryOrders() {
        return historyOrders;
    }

    // Thêm đơn hàng vào Ongoing
    public void addOrderToOngoing(OrderModel order) {
        List<OrderModel> currentOrders = ongoingOrders.getValue();
        currentOrders.add(order);
        ongoingOrders.setValue(currentOrders);
    }

    // Thêm đơn hàng vào History
    public void addOrderToHistory(OrderModel order) {
        List<OrderModel> currentOrders = historyOrders.getValue();
        currentOrders.add(order);
        historyOrders.setValue(currentOrders);
    }

    // Xóa đơn hàng khỏi History
    public void removeOrderFromHistory(OrderModel order) {
        List<OrderModel> currentOrders = historyOrders.getValue();
        currentOrders.remove(order);
        historyOrders.setValue(currentOrders);
    }
    public void reOrder(OrderModel order) {
        addOrderToOngoing(order);
    }
}
