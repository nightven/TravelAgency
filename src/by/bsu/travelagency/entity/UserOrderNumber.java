package by.bsu.travelagency.entity;


/**
 * Created by Михаил on 8/21/2016.
 */
public class UserOrderNumber extends User {
    
    /** The order number. */
    private int orderNumber;

    /**
     * Gets the order number.
     *
     * @return the order number
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets the order number.
     *
     * @param orderNumber the new order number
     */
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
