package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Reservation {
    final Customer customer;
    final IRoom room;
    final Date checkInDate;
    final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        String pattern = "EEE MMM dd yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String checkIn = simpleDateFormat.format(this.checkInDate);
        String checkOut = simpleDateFormat.format(this.checkOutDate);
        return "Reservation\n"+this.customer.getFirstname()+" "+this.customer.getLastname()+"\n"+this.room+"\nCheckin Date: "+checkIn+"\nCheckout Date: "+checkOut+"\n";
    }

    public boolean isReserved(Date checkInDate, Date checkOutDate) {
        if (checkInDate.before(this.checkOutDate)&&checkOutDate.after(this.checkInDate)) {
            return true;
        } else {
            return false;
        }
    }

    public IRoom getRoom() {
        return this.room;
    }

    //https://www.liaoxuefeng.com/wiki/1252599548343744/1265117217944672
    @Override
    public boolean equals(Object o) {
        if(o instanceof Reservation) {
            Reservation reservation = (Reservation) o;
            return this.customer.equals(reservation.customer) && this.room.equals(reservation.room) && this.checkInDate.equals(reservation.checkInDate) && this.checkOutDate.equals(reservation.checkOutDate);
        }
        return false;
    }

    //https://www.liaoxuefeng.com/wiki/1252599548343744/1265117217944672
    @Override
    public int hashCode() {
        return Objects.hash(customer,room,checkInDate,checkOutDate);
    }

}
