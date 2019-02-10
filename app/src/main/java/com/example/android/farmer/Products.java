package com.example.android.farmer;

public class Products {
    private String CostOfProduct,descriptionOfProduct,imageUrl,nameOfProduct;

    public Products(String CostOfProduct, String descriptionOfProduct, String imageUrl, String nameOfProduct) {
        this.CostOfProduct = CostOfProduct;
        this.descriptionOfProduct = descriptionOfProduct;
        this.imageUrl = imageUrl;
        this.nameOfProduct = nameOfProduct;
    }

    public String getCostOfProduct() {

        return CostOfProduct;
    }

    public void setCostOfProduct(String costOfProduct) {
        CostOfProduct = costOfProduct;
    }

    public String getDescriptionOfProduct() {
        return descriptionOfProduct;
    }

    public void setDescriptionOfProduct(String descriptionOfProduct) {
        this.descriptionOfProduct = descriptionOfProduct;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNameOfProduct() {
        return nameOfProduct;
    }

    public void setNameOfProduct(String nameOfProduct) {
        this.nameOfProduct = nameOfProduct;
    }
}
