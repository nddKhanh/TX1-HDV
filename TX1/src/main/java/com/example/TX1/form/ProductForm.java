package com.example.TX1.form;
import jakarta.validation.constraints.*;

public class ProductForm {
     private Long id;

     @NotBlank(message="Tên sản phẩm không được để trống")
     private String name;

     @NotNull(message="Giá không được để trống")
     @DecimalMin(value="0.0",inclusive=false,message="Giá phải lớn hơn 0")
     private Double price;

     @NotNull(message="Tồn kho không được để trống")
     @Min(value=0,message="Tồn kho phải >= 0")
     private Integer stock;

     @NotNull(message="Vui lòng chọn danh mục")
     private Long categoryId;

     public Long getId(){
         return id;
     }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}