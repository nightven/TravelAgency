package by.bsu.travelagency.entity;

/**
 * Created by Михаил on 7/21/2016.
 */
public class Trip extends Tour {

    private String cities;
    private String attractions;

    public Trip() {
    }

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    public String getAttractions() {
        return attractions;
    }

    public void setAttractions(String attractions) {
        this.attractions = attractions;
    }
}
