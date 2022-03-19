import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {
    private MainMenu() {}
    public static void mainMenu(){
        while(true) {
            System.out.println("\n------------------------------");
            System.out.println("Welcome to the Hotel Reservation Application\n");
            System.out.println("Main Menu");
            System.out.println("------------------------------");
            System.out.println("1.Find and Reserve a Room");
            System.out.println("2.See My Reservations");
            System.out.println("3.Create an Account");
            System.out.println("4.Admin");
            System.out.println("5.Exit");
            System.out.println("------------------------------");
            System.out.println("Please select a number for the menu option.");

            //read user input
            Scanner scanner = new Scanner(System.in);
            int number = scanner.nextInt();
            switch(number) {
                case 1:
                    findAndReserveRoom();
                    break;
                case 2:
                    seeMyReservations();
                    break;
                case 3:
                    createAnAccount();
                    break;
                case 4:
                    AdminMenu.adminMenu();
                    break;
                case 5:
                    System.out.println("Thanks for using!");
                    System.exit(0);
                default:
                    System.out.println("Please enter a number from 1 to 5.");
            }
        }
    }
    public static void createAnAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.println("First Name:");
        String firstname = sc.nextLine();
        System.out.println("Last Name:");
        String lastname = sc.nextLine();
        System.out.println("Enter Email format: name@domain.com");
        String email;
        boolean keepRunning = true;
        while(keepRunning) {
            try {
                email = sc.nextLine();
                HotelResource.createACustomer(email, firstname, lastname);
                System.out.println("The Account Created!\n");
                keepRunning = false;
            } catch(IllegalArgumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
    }

    public static Date getCheckInDate() {
        //distinguish the MM for month and the mm for minute
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter CheckIn Date MM/dd/yyyy example 02/02/2020");
        Date checkInDate = null;
        boolean keepRunning = true;
        while (keepRunning) {
            try {
                String checkIn = sc.nextLine();
                checkInDate = format.parse(checkIn);
                Date today = new Date();
                if (checkInDate.before(today)) {
                    System.out.println("Please Enter a CheckIn Date That Not Before Today");
                } else {
                    keepRunning = false;
                }
            } catch (ParseException e) {
                System.out.print("Please Enter CheckIn Date Again in the Format MM/dd/yyy example 02/02/2020\n");
            }
        }
        return checkInDate;
    }

        public static Date getCheckOutDate(Date checkInDate) {
            Scanner sc = new Scanner(System.in);
            //distinguish the MM for month and the mm for minute
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            System.out.println("Enter CheckOut Date MM/dd/yyyy example 02/21/2020");
            Date checkOutDate = null;
            boolean keepRunning = true;
            while (keepRunning) {
                String checkOut = sc.nextLine();
                try {
                    checkOutDate = format.parse(checkOut);
                    if(checkOutDate.after(checkInDate)){
                        keepRunning = false;
                    } else {
                        System.out.println("Please Enter a CheckOut Date After CheckIn Date");
                    }
                } catch (ParseException e) {
                    System.out.print("Please Enter CheckOut Date Again in the Format MM/dd/yyy example 02/02/2020");
                }
            }
            return checkOutDate;
        }

    public static void findAndReserveRoom() {
        Date checkInDate = getCheckInDate();
        Date checkOutDate = getCheckOutDate(checkInDate);
        Collection<IRoom> emptyRooms = HotelResource.findARoom(checkInDate,checkOutDate);
        if(emptyRooms.isEmpty()) {
            System.out.println("Sorry, there is no room left on the dates you entered.");
            Date newCheckInDate = addDate(checkInDate);
            Date newCheckOutDate = addDate(checkOutDate);
            Collection<IRoom> newEmptyRooms = HotelResource.findARoom(newCheckInDate,newCheckOutDate);
            if(!newEmptyRooms.isEmpty()) {
                String pattern = "EEE MMM dd yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String checkIn = simpleDateFormat.format(newCheckInDate);
                String checkOut = simpleDateFormat.format(newCheckOutDate);
                System.out.println("But there are some rooms available on "+checkIn+" to "+checkOut+" :");
                HotelResource.printRoom(newEmptyRooms);
            } else {
                System.out.println("Our hotels are fully booked on that dates");
            }
        } else {
            HotelResource.printRoom(emptyRooms);
            Scanner sc = new Scanner(System.in);
            System.out.println("Would you like to book a room? y/n");
            String bookRoom = sc.next();
            if(bookRoom.equals("y")){
                System.out.println("Do you have an account with us? y/n");
                String haveAccount = sc.next();
                if(haveAccount.equals("y")) {
                    System.out.println("Enter Email Format: name@domain.com");
                    boolean keepRunning = true;
                    while(keepRunning) {
                        String email = sc.next();
                        //Check whether the account exists
                        Customer customer = HotelResource.getCustomer(email);
                        if (Optional.ofNullable(customer).isPresent()) {
                            System.out.println("What room number would you like to reserve?");
                            while(true) {
                                try {
                                    String roomNumber = sc.next();
                                    IRoom room = HotelResource.getRoom(roomNumber);
                                    Reservation reservation = HotelResource.bookARoom(email, room, checkInDate, checkOutDate);
                                    if(reservation != null) {
                                        System.out.println(reservation);
                                    }
                                    keepRunning = false;
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Please enter one of the room numbers above:");
                                }
                            }
                        } else {
                            System.out.println("The email you entered doesn't exit. Please enter it again:");
                        }
                    }
                } else if(haveAccount.equals("n")) {
                    System.out.println("Please create a new account to reserve a room.");
                }
            }
        }
    }

    public static void seeMyReservations() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Email Format: name@domain.com");
        String email = sc.next();
        //Check whether the account exists
        Customer customer = HotelResource.getCustomer(email);
        try {
            if (Optional.ofNullable(customer).isPresent()) {
                Collection<Reservation> reservations = HotelResource.getCustomerReservations(email);
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }
            } else {
                System.out.println("The email you entered doesn't exit.");
            }
        } catch(Exception e) {
            System.out.println("You don't have a reservation");
       }
    }

    public static Date addDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,7);
        return cal.getTime();
    }
}
