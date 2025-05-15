package com.example.app_foodstore.Model.request;

public class PaymentRequest {
    private String paymentMethod;
    private String shippingMethod;
    private Long idVoucher;
    private Long idAddress;

    public PaymentRequest() {
    }

    public PaymentRequest(String paymentMethod, String shippingMethod, Long idVoucher, Long idAddress) {
        this.paymentMethod = paymentMethod;
        this.shippingMethod = shippingMethod;
        this.idVoucher = idVoucher;
        this.idAddress = idAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Long getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(Long idVoucher) {
        this.idVoucher = idVoucher;
    }

    public Long getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Long idAddress) {
        this.idAddress = idAddress;
    }
}

