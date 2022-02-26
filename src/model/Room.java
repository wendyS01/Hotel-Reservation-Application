package model;

import java.util.Objects;

public class Room implements IRoom{
    private final String roomNumber;
    private final Double price;
    private final RoomType enumeration;

    public Room(String roomNumber,Double price,RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return (price==0);
    }

    @Override
    public String toString() {
        return "Room Number: "+this.roomNumber+"  Room Type: "+this.enumeration+"  Price: $"+this.price;
    }

    //https://www.liaoxuefeng.com/wiki/1252599548343744/1265117217944672
    @Override
    public boolean equals(Object o) {
        if (o instanceof Room) {
            Room room = (Room) o;
            return this.roomNumber.equals(room.roomNumber) && this.price.equals(room.price) && this.enumeration.equals(room.enumeration);
        }
        return false;
    }

    //https://www.liaoxuefeng.com/wiki/1252599548343744/1265117217944672
    @Override
    public int hashCode() {
        return Objects.hash(roomNumber,price,enumeration);
    }
}
