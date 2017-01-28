package by.bsu.travelagency.entity;

public class Country extends Entity {

    /** The country ID. */
    private long id;

    /** The country name. */
    private String nameCountry;

    /**
     * Instantiates a new country.
     */
    public Country() {
    }

    /**
     * Gets the country id.
     *
     * @return the country ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the country id.
     *
     * @param id the new country id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the country name.
     *
     * @return the country name
     */
    public String getNameCountry() {
        return nameCountry;
    }

    /**
     * Sets the country name.
     *
     * @param nameCountry the new country name
     */
    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }
}
