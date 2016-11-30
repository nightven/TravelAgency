package by.bsu.travelagency.entity;

import java.util.Date;

/**
 * Created by Михаил on 8/10/2016.
 */
public class Order extends Entity {

    /** The order id. */
    private Long orderId;
    
    /** The user id. */
    private Long userId;
    
    /** The tour id. */
    private Long tourId;
    
    /** The price. */
    private int price;
    
    /** The quantity. */
    private int quantity;
    
    /** The discount. */
    private double discount;
    
    /** The total price. */
    private int totalPrice;
    
    /** The order date. */
    private Date orderDate;
    
    /** The departure date. */
    private Date departureDate;
    
    /** The arrival date. */
    private Date arrivalDate;
    
    /** The tour type. */
    private TourType tourType;

    /**
     * Gets the order id.
     *
     * @return the order id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * Sets the order id.
     *
     * @param orderId the new order id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the tour id.
     *
     * @return the tour id
     */
    public Long getTourId() {
        return tourId;
    }

    /**
     * Sets the tour id.
     *
     * @param tourId the new tour id
     */
    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price the new price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Gets the quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity.
     *
     * @param quantity the new quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the discount.
     *
     * @return the discount
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Sets the discount.
     *
     * @param discount the new discount
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * Gets the total price.
     *
     * @return the total price
     */
    public int getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price.
     *
     * @param totalPrice the new total price
     */
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the order date.
     *
     * @return the order date
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the order date.
     *
     * @param orderDate the new order date
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
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
     * Gets the tour type.
     *
     * @return the tour type
     */
    public TourType getTourType() {
        return tourType;
    }

    /**
     * Sets the tour type.
     *
     * @param tourType the new tour type
     */
    public void setTourType(TourType tourType) {
        this.tourType = tourType;
    }
}
