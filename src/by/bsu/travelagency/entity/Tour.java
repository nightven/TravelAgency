package by.bsu.travelagency.entity;

import java.util.ArrayList;
import java.util.Date;

public abstract class Tour extends Entity {

    /** The id. */
    private long id;
    
    /** The name. */
    private String name;
    
    /** The summary. */
    private String summary;
    
    /** The description. */
    private String description;
    
    /** The departure date. */
    private Date departureDate;
    
    /** The arrival date. */
    private Date arrivalDate;

    /** The cities. */
    private ArrayList<City> cities;
    
    /** The price. */
    private double price;
    
    /** The last minute. */
    private boolean lastMinute;
    
    /** The transport. */
    private Transport transport;
    
    /** The services. */
    private String services;
    
    /** The path image. */
    private String pathImage;

    /**
     * Instantiates a new tour.
     */
    public Tour() {
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the summary.
     *
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the summary.
     *
     * @param summary the new summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the departure date.
     *
     * @return the departure date
     */
    public Date getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the departure date.
     *
     * @param departureDate the new departure date
     */
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * Gets the arrival date.
     *
     * @return the arrival date
     */
    public Date getArrivalDate() {
        return arrivalDate;
    }

    /**
     * Sets the arrival date.
     *
     * @param arrivalDate the new arrival date
     */
    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    /**
     * Gets cities.
     *
     * @return cities
     */
    public ArrayList<City> getCities() {
        return cities;
    }

    /**
     * Sets the cities.
     *
     * @param cities new cities
     */
    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price the new price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the last minute.
     *
     * @return the last minute
     */
    public boolean getLastMinute() {
        return lastMinute;
    }

    /**
     * Sets the last minute.
     *
     * @param lastMinute the new last minute
     */
    public void setLastMinute(boolean lastMinute) {
        this.lastMinute = lastMinute;
    }

    /**
     * Gets the transport.
     *
     * @return the transport
     */
    public Transport getTransport() {
        return transport;
    }

    /**
     * Sets the transport.
     *
     * @param transport the new transport
     */
    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    /**
     * Gets the services.
     *
     * @return the services
     */
    public String getServices() {
        return services;
    }

    /**
     * Sets the services.
     *
     * @param services the new services
     */
    public void setServices(String services) {
        this.services = services;
    }

    /**
     * Gets the path image.
     *
     * @return the path image
     */
    public String getPathImage() {
        return pathImage;
    }

    /**
     * Sets the path image.
     *
     * @param pathImage the new path image
     */
    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

}
