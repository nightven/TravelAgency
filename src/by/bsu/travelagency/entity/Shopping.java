package by.bsu.travelagency.entity;

/**
 * Created by Михаил on 7/21/2016.
 */
public class Shopping extends Tour {

    /** The destination city. */
    private String destinationCity;
    
    /** The destination country. */
    private String destinationCountry;
    
    /** The shops. */
    private String shops;

    /**
     * Instantiates a new shopping.
     */
    public Shopping() {
    }


    /**
     * Gets the destination city.
     *
     * @return the destination city
     */
    public String getDestinationCity() {
        return destinationCity;
    }

    /**
     * Sets the destination city.
     *
     * @param destinationCity the new destination city
     */
    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    /**
     * Gets the destination country.
     *
     * @return the destination country
     */
    public String getDestinationCountry() {
        return destinationCountry;
    }

    /**
     * Sets the destination country.
     *
     * @param destinationCountry the new destination country
     */
    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    /**
     * Gets the shops.
     *
     * @return the shops
     */
    public String getShops() {
        return shops;
    }

    /**
     * Sets the shops.
     *
     * @param shops the new shops
     */
    public void setShops(String shops) {
        this.shops = shops;
    }
}
