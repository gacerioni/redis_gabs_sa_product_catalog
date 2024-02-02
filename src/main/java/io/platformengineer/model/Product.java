package io.platformengineer.model;

import java.util.List;

public class Product {

    private Long id;
    private String name;
    private String description;
    private String vendor;
    private Double price;
    private String currency;
    private Category mainCategory;
    private List<Image> images;

    public Product() {
    }

    public Product(String name, String description, String vendor, Double price, String currency, Category mainCategory) {
        this.name = name;
        this.description = description;
        this.vendor = vendor;
        this.price = price;
        this.currency = currency;
        this.mainCategory = mainCategory;
    }

    public Product(Long id, String name, String description, String vendor, Double price, String currency, Category mainCategory, List<Image> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.vendor = vendor;
        this.price = price;
        this.currency = currency;
        this.mainCategory = mainCategory;
        this.images = images;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Category getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(Category mainCategory) {
        this.mainCategory = mainCategory;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
