package by.bsu.travelagency.entity;

/**
 * Created by Михаил on 5/28/2016.
 */
public enum Transport {
    
    /** The plane. */
    PLANE("Самолет"),
    
    /** The boat. */
    BOAT("Корабль"),
    
    /** The train. */
    TRAIN("Поезд"),
    
    /** The bus. */
    BUS("Автобус");

    /** The name. */
    private String name;
    
    /**
     * Instantiates a new transport.
     *
     * @param name the name
     */
    Transport(String name){
        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}
