package by.bsu.travelagency.entity;

/**
 * Created by Михаил on 7/21/2016.
 */
public class Shopping extends Tour {

    private String destinationCity;
    private String destinationCountry;
    private String shops;

    public Shopping() {
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

    public String getShops() {
        return shops;
    }

    public void setShops(String shops) {
        this.shops = shops;
    }
}
