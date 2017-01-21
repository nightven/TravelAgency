package by.bsu.travelagency.entity;

/**
 * Created by Михаил on 7/21/2016.
 */
public class Trip extends Tour {
    
    /** The attractions. */
    private String attractions;

    /**
     * Instantiates a new trip.
     */
    public Trip() {
    }

    /**
     * Gets the attractions.
     *
     * @return the attractions
     */
    public String getAttractions() {
        return attractions;
    }

    /**
     * Sets the attractions.
     *
     * @param attractions the new attractions
     */
    public void setAttractions(String attractions) {
        this.attractions = attractions;
    }
}
