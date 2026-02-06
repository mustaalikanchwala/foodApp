package com.foodDelivering.foodApp.model.FoodProductModel;

public enum FoodCategory{

        VEG("veg"),
        NON_VEG("non-veg"),
        SALAD("salad"),
        DIET("diet"),
        DESSERT("dessert"),
        BEVERAGES("beverages");

        private final String folderName;

        FoodCategory(String folderName) {
            this.folderName = folderName;
        }

        public String getFolderName() {
            return folderName;
        }

}
