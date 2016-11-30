package by.bsu.travelagency.entity;

/**
 * Created by Михаил on 8/10/2016.
 */
public enum TourType {
    
    /** The vacations. */
    VACATIONS("vacations"),
    
    /** The trips. */
    TRIPS("trips"),
    
    /** The shoppings. */
    SHOPPINGS("shoppings");

    /** The name. */
    private String name;
    
    /**
     * Instantiates a new tour type.
     *
     * @param name the name
     */
    TourType(String name){
        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}
