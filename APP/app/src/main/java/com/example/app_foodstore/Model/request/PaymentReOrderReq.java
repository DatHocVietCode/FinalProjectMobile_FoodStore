package com.example.app_foodstore.Model.request;

public class PaymentReOrderReq {

    private String paymentMethod;
    private String shippingMethod;
    private Long idVoucher;
    private Long idAddress;

    private Long idOrder;

    public PaymentReOrderReq(String paymentMethod, Long idOrder, Long idVoucher, String shippingMethod, Long idAddress) {
        this.paymentMethod = paymentMethod;
        this.idOrder = idOrder;
        this.idVoucher = idVoucher;
        this.shippingMethod = shippingMethod;
        this.idAddress = idAddress;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
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
