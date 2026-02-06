package com.foodDelivering.foodApp.model;

public enum FileCategory {
    PRODUCTS("uploads/products/"),
    CATEGORIES("uploads/category/"),
    BLOGS("uploads/blogs/"),
    BANNERS("uploads/banner/"),
    PROFILE("uploads/profile/"),
    COUNTRY("uploads/country/"),
    DEALS("uploads/deals/"),
    TOP_BRANDS("uploads/top-brand/");

    private final String prefix;

    FileCategory(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
