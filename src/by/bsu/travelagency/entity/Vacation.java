package by.bsu.travelagency.entity;

/**
 * Created by Михаил on 2/24/2016.
 */
public class Vacation extends Tour {

    private String hotel;
    private String destinationCity;
    private String destinationCountry;

    public Vacation() {
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }
}
