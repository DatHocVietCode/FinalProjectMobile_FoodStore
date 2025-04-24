package com.example.app_foodstore.Model;

public class PaymentInterfaceModel {
    private int img_method_selected;
    private int img_method_unselected;
    private String name_method;
    private boolean isChecked;

    public int getImg_method_selected() {
        return img_method_selected;
    }

    public void setImg_method_selected(int img_method_selected) {
        this.img_method_selected = img_method_selected;
    }

    public int getImg_method_unselected() {
        return img_method_unselected;
    }

    public void setImg_method_unselected(int img_method_unselected) {
        this.img_method_unselected = img_method_unselected;
    }

    public String getName_method() {
        return name_method;
    }

    public void setName_method(String name_method) {
        this.name_method = name_method;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public PaymentInterfaceModel(int img_method_selected, int img_method_unselected, String name_method, boolean isChecked) {
        this.img_method_selected = img_method_selected;
        this.img_method_unselected = img_method_unselected;
        this.name_method = name_method;
        this.isChecked = isChecked;
    }
}
