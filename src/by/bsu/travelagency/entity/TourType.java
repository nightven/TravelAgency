package by.bsu.travelagency.entity;

/**
 * Created by Михаил on 8/10/2016.
 */
public enum TourType {
    VACATIONS("vacations"),
    TRIPS("trips"),
    SHOPPINGS("shoppings");

    private String name;
    TourType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
