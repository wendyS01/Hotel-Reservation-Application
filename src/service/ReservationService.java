package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private ReservationService() {}
    private static Map<String, IRoom> mapOfRoom = new HashMap<String, IRoom>();
    public static void addRoom(IRoom room) {
        //create a map abject to store IRoom class by using roomId as key
        mapOfRoom.put(room.getRoomNumber(),room);
    }

    public static IRoom getARoom(String roomId) {
        return mapOfRoom.get(roomId);
    }

    private static Map<Customer, Collection<Reservation>> mapOfReservation = new HashMap<Customer, Collection<Reservation>>();
    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if (isRoomReserved(room, checkInDate, checkOutDate)) {
            Collection<IRoom> emptyRoom = findRooms(checkInDate, checkOutDate);
            if (!emptyRoom.isEmpty()) {
                System.out.println("Sorry, the room you entered is not available!");
                System.out.println("Here are some other available rooms:");
                for (IRoom iRoom : emptyRoom
                ) {
                    System.out.println(iRoom);
                }
            }
            return null;
        }
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        Collection<Reservation> reservations = getCustomerReservation(customer);
        if (!Optional.ofNullable(getCustomerReservation(customer)).isPresent()) {
            reservations = new ArrayList<>();
        }
        reservations.add(reservation);
        mapOfReservation.put(customer, reservations);
        return reservation;
    }

        public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> roomReserved = roomReserved(checkInDate, checkOutDate);
        Collection<IRoom> emptyRoom = new ArrayList<>();
        for ( IRoom room: mapOfRoom.values()) {
            if (roomReserved.contains(room)) {
                continue;
            } else {
                emptyRoom.add(room);
            }
        }
        return emptyRoom;
    }

    public static Collection<Reservation> getCustomerReservation(Customer customer) {
        return mapOfReservation.get(customer);
    }

    public static void printAllReservation() {
        for (Collection<Reservation> reservations: mapOfReservation.values()) {
            for(Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    public static Collection<IRoom> getAllRooms() {
        return mapOfRoom.values();
    }

    private static boolean isRoomReserved(IRoom room, Date checkInDate, Date checkOutDate) {
        List<IRoom> roomReserved = roomReserved(checkInDate, checkOutDate);
        if(roomReserved.contains(room)) {
            return true;
        } else {
            return false;
        }
    }

    static List<IRoom> roomReserved(Date checkInDate, Date checkOutDate) {
        List<IRoom> roomReserved = new ArrayList<>();
        for(Collection<Reservation> reservations : mapOfReservation.values()) {
            for(Reservation reservation :reservations) {
                if (reservation.isReserved(checkInDate, checkOutDate)) {
                    roomReserved.add(reservation.getRoom());
                }
            }
        }
        return roomReserved;
    }
}
