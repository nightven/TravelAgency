package by.bsu.travelagency.entity;

public class Country extends Entity {

    /** The country ID. */
    private long idCountry;

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
    public long getIdCountry() {
        return idCountry;
    }

    /**
     * Sets the country id.
     *
     * @param idCountry the new country id
     */
    public void setIdCountry(long idCountry) {
        this.idCountry = idCountry;
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
