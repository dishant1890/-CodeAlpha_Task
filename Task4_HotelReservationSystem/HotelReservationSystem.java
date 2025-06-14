import java.util.*;
import java.io.*;

public class HotelReservationSystem {
    // Room class
    static class Room {
        int roomNumber;
        String category;
        boolean isBooked;

        Room(int roomNumber, String category) {
            this.roomNumber = roomNumber;
            this.category = category;
            this.isBooked = false;
        }

        public String toString() {
            return "Room " + roomNumber + " (" + category + ") - " + (isBooked ? "Booked" : "Available");
        }
    }

    // Reservation class
    static class Reservation {
        String name;
        int roomNumber;
        String category;
        String paymentStatus;

        Reservation(String name, int roomNumber, String category, String paymentStatus) {
            this.name = name;
            this.roomNumber = roomNumber;
            this.category = category;
            this.paymentStatus = paymentStatus;
        }

        public String toString() {
            return "Name: " + name + ", Room: " + roomNumber + " (" + category + "), Payment: " + paymentStatus;
        }
    }

    // Variables
    static List<Room> rooms = new ArrayList<>();
    static List<Reservation> bookings = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String fileName = "reservations.txt";

    public static void main(String[] args) {
        initializeRooms();
        loadBookings();

        while (true) {
            System.out.println("\n--- Hotel Reservation System ---");
            System.out.println("1. View Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // clear input buffer

            switch (choice) {
                case 1 -> viewRooms();
                case 2 -> bookRoom();
                case 3 -> cancelBooking();
                case 4 -> viewBookings();
                case 5 -> {
                    saveBookings();
                    System.out.println("Thank you for using the system!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void initializeRooms() {
        for (int i = 101; i <= 105; i++) rooms.add(new Room(i, "Standard"));
        for (int i = 201; i <= 203; i++) rooms.add(new Room(i, "Deluxe"));
        for (int i = 301; i <= 302; i++) rooms.add(new Room(i, "Suite"));
    }

    static void viewRooms() {
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    static void bookRoom() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter category (Standard/Deluxe/Suite): ");
        String category = sc.nextLine();

        for (Room room : rooms) {
            if (!room.isBooked && room.category.equalsIgnoreCase(category)) {
                room.isBooked = true;
                Reservation r = new Reservation(name, room.roomNumber, category, "Paid");
                bookings.add(r);
                System.out.println("Room booked successfully! Room number: " + room.roomNumber);
                return;
            }
        }
        System.out.println("No available rooms in that category.");
    }

    static void cancelBooking() {
        System.out.print("Enter your name to cancel booking: ");
        String name = sc.nextLine();
        Iterator<Reservation> it = bookings.iterator();

        while (it.hasNext()) {
            Reservation r = it.next();
            if (r.name.equalsIgnoreCase(name)) {
                it.remove();
                for (Room room : rooms) {
                    if (room.roomNumber == r.roomNumber) {
                        room.isBooked = false;
                        break;
                    }
                }
                System.out.println("Booking cancelled.");
                return;
            }
        }
        System.out.println("No booking found under that name.");
    }

    static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No current bookings.");
        } else {
            for (Reservation r : bookings) {
                System.out.println(r);
            }
        }
    }

    static void loadBookings() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                Reservation r = new Reservation(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3]);
                bookings.add(r);
                for (Room room : rooms) {
                    if (room.roomNumber == r.roomNumber) {
                        room.isBooked = true;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            // File might not exist yet
        }
    }

    static void saveBookings() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Reservation r : bookings) {
                pw.println(r.name + "," + r.roomNumber + "," + r.category + "," + r.paymentStatus);
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings.");
        }
    }
}
