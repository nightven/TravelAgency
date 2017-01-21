package by.bsu.travelagency.entity;

public class City extends Entity {

    /** The city ID. */
    private long idCity;

    /** The city name. */
    private String nameCity;

    /** The city's country. */
    private Country country;

    /**
     * Instantiates a new city.
     */
    public City() {
    }

    /**
     * Gets the city id.
     *
     * @return the city ID
     */
    public long getIdCity() {
        return idCity;
    }

    /**
     * Sets the city ID.
     *
     * @param idCity the new city ID
     */
    public void setIdCity(long idCity) {
        this.idCity = idCity;
    }

    /**
     * Gets the city name.
     *
     * @return the city name
     */
    public String getNameCity() {
        return nameCity;
    }

    /**
     * Sets the city name.
     *
     * @param nameCity the new city name
     */
    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    /**
     * Gets the country.
     *
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Sets the country.
     *
     * @param country the new city's country
     */
    public void setCountry(Country country) {
        this.country = country;
    }
}
