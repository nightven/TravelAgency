package by.bsu.travelagency.entity;

public class City extends Entity {

    /** The city ID. */
    private long id;

    /** The city name. */
    private String name;

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
    public long getId() {
        return id;
    }

    /**
     * Sets the city ID.
     *
     * @param id the new city ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the city name.
     *
     * @return the city name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the city name.
     *
     * @param name the new city name
     */
    public void setName(String name) {
        this.name = name;
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
