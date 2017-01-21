package by.bsu.travelagency.entity;

/**
 * Created by Михаил on 2/24/2016.
 */
public class Vacation extends Tour {

    /** The hotel. */
    private String hotel;

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
}
