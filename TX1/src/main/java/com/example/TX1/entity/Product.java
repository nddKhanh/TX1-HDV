package com.example.TX1.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Tên sản phẩm không được để trống")
    @Size(max=255)
    @Column(nullable=false)
    private String name;

    @NotNull(message="Giá không được để trống")
    @DecimalMin(value="0.0",inclusive=false,message="Giá phải lớn hơn 0")
    @Column(nullable=false)
    private Double price;

    @NotNull(message="Tồn kho không được để trống")
    @Min(value=0,message="Tồn kho phải >= 0")
    @Column(nullable=false)
    private Integer stock;

    @NotNull(message="Vui lòng chọn danh mục")
    @ManyToOne(fetch=FetchType.LAZY,optional=false)
    @JoinColumn(name="category_id",nullable=false)
    private Category category;

    public Long getId() {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}