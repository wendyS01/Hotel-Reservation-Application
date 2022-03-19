import api.AdminResource;
import api.HotelResource;
import model.*;


import java.util.*;

public class AdminMenu {
    private AdminMenu() {}
    public static void adminMenu(){
        while(true) {
            System.out.println("\n------------------------------");
            System.out.println("Welcome to the Hotel Reservation Application\n");
            System.out.println("Admin Menu");
            System.out.println("------------------------------");
            System.out.println("1.See all Customers");
            System.out.println("2.See all Rooms");
            System.out.println("3.See all Reservations");
            System.out.println("4.Add a Room");
            System.out.println("5.Add Test Data");
            System.out.println("6.Back to Main Menu");
            System.out.println("------------------------------");
            System.out.println("Please select a number for the menu option.");

            //read user input
            Scanner scanner = new Scanner(System.in);
            int number = scanner.nextInt();
            switch(number) {
                case 1:
                    getAllCustomers();
                    break;
                case 2:
                    getAllRooms();
                    break;
                case 3:
                    AdminResource.displayAllReservations();
                    break;
                case 4:
                    addARoom();
                    break;
                case 5:
                    addTestData();
                    break;
                case 6:
                    MainMenu.mainMenu();
                    break;
                default:
                    System.out.println("Please enter a number from 1 to 5.");
            }
        }
    }

    public static void getAllCustomers() {
        Collection<Customer> customers = AdminResource.getAllCustomers();
        for(Customer customer : customers) {
            System.out.println(customer);
        }
    }

    public static void getAllRooms() {
        Collection<IRoom> rooms = AdminResource.getAllRooms();
        HotelResource.printRoom(rooms);
    }

    public static void addARoom() {
        Scanner sc = new Scanner(System.in);
        List<IRoom> rooms = new ArrayList<IRoom>();
        boolean keepRunning = true;
        while(keepRunning) {
            System.out.println("Enter room number:");
            String roomNumber = sc.next();
            System.out.println("Enter price per night");
            Double price = sc.nextDouble();
            System.out.println("Enter room type: 1 for single bed, 2 for double bed");
            int roomType = sc.nextInt();
            if (price != 0.0) {
                if (roomType == 1) {
                    RoomType myRoomType = RoomType.SINGLE;
                    rooms.add(new Room(roomNumber, price, myRoomType));
                } else if (roomType == 2) {
                    RoomType myRoomType = RoomType.DOUBLE;
                    rooms.add(new Room(roomNumber, price, myRoomType));
                }
            } else {
                if (roomType == 1) {
                    RoomType myRoomType = RoomType.SINGLE;
                    rooms.add(new FreeRoom(roomNumber, 0.0, myRoomType));
                } else if (roomType == 2) {
                    RoomType myRoomType = RoomType.DOUBLE;
                    rooms.add(new FreeRoom(roomNumber, 0.0, myRoomType));
                }
            }
            System.out.println("Would you like to add another room? y/n");
            while (true) {
                String addAnotherRoom = sc.next();
                if (addAnotherRoom.equals("y")) {
                    break;
                } else if (addAnotherRoom.equals("n")) {
                    AdminResource.addRoom(rooms);
                    keepRunning = false;
                    break;
                } else {
                    System.out.println("Please enter y (Yes) or n (No)");
                }
            }
        }
    }

    public static void addTestData() {
        //create test accounts
        HotelResource.createACustomer("tang@gmail.com","tang","zi");
        HotelResource.createACustomer("w@gmail.com","w","zi");
        //create test rooms
        List<IRoom> rooms = new ArrayList<IRoom>();
        rooms.add(new Room("1",80.0 ,RoomType.DOUBLE));
        rooms.add(new Room("2",80.0 ,RoomType.DOUBLE));
        rooms.add(new Room("3",80.0 ,RoomType.SINGLE));
        rooms.add(new Room("4",100.0 ,RoomType.SINGLE));
        AdminResource.addRoom(rooms);

        //create reservations
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,2);
        Date checkOutDate1 = c.getTime();
        HotelResource.bookARoom("tang@gmail.com",HotelResource.getRoom("1"),today,checkOutDate1);
        c.add(Calendar.DATE,5);
        Date checkOutDate2 = c.getTime();
        HotelResource.bookARoom("w@gmail.com",HotelResource.getRoom("2"),today,checkOutDate2);
        System.out.println("Test data is added.");
    }
}
