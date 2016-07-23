package by.bsu.travelagency.entity;

/**
 * Created by Михаил on 5/28/2016.
 */
public enum Transport {
    PLANE("Самолет"),
    BOAT("Корабль"),
    TRAIN("Поезд"),
    BUS("Автобус");

    private String name;
    Transport(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
