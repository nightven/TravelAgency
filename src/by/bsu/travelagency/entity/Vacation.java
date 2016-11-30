package by.bsu.travelagency.entity;

/**
 * Created by Михаил on 2/24/2016.
 */
public class Vacation extends Tour {

    /** The hotel. */
    private String hotel;
    
    /** The destination city. */
    private String destinationCity;
    
    /** The destination country. */
    private String destinationCountry;

    /**
     * Instantiates a new vacation.
     */
    public Vacation() {
    }

    /**
     * Gets the hotel.
     *
     * @return the hotel
     */
    public String getHotel() {
        return hotel;
    }

    /**
     * Sets the hotel.
     *
     * @param hotel the new hotel
     */
    public void setHotel(String hotel) {
        this.hotel = hotel;
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
}
