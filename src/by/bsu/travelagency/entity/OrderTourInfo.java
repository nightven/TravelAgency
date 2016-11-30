package by.bsu.travelagency.entity;

/**
 * Created by Михаил on 8/13/2016.
 */
public class OrderTourInfo extends Order {

    /** The name. */
    private String name;
    
    /** The path image. */
    private String pathImage;

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
