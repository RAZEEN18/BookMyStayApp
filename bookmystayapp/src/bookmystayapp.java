import java.io.*;
import java.util.*;

// Serializable RoomInventory
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public void allocateRoom(String type) {
        if (rooms.getOrDefault(type, 0) > 0) {
            rooms.put(type, rooms.get(type) - 1);
        }
    }

    public void display() {
        System.out.println("Inventory:");
        for (String key : rooms.keySet()) {
            System.out.println(key + " : " + rooms.get(key));
        }
    }
}

// Serializable Booking System
class BookingSystem implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, String> bookings;
    RoomInventory inventory;

    public BookingSystem() {
        bookings = new HashMap<>();
        inventory = new RoomInventory();
    }

    public void book(String id, String type) {
        bookings.put(id, type);
        inventory.allocateRoom(type);
        System.out.println("Booked: " + id);
    }

    public void show() {
        System.out.println("Bookings: " + bookings);
        inventory.display();
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "data.ser";

    public static void save(BookingSystem system) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(system);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    public static BookingSystem load() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("Data loaded successfully.");
            return (BookingSystem) in.readObject();
        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return new BookingSystem();
        }
    }
}

// Main Class
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        // Load previous state
        BookingSystem system = PersistenceService.load();

        // Perform operations
        system.book("R101", "Single");
        system.book("R102", "Double");

        System.out.println();
        system.show();

        // Save before exit
        PersistenceService.save(system);
    }
}