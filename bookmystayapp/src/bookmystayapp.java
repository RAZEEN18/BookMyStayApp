import java.util.*;

// Booking Request class
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Room Inventory (THREAD SAFE)
class RoomInventory {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    // Critical Section (synchronized)
    public synchronized boolean allocateRoom(String roomType) {
        int count = rooms.getOrDefault(roomType, 0);

        if (count > 0) {
            rooms.put(roomType, count - 1);
            return true;
        }
        return false;
    }

    public synchronized void displayInventory() {
        System.out.println("Final Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + " : " + rooms.get(type));
        }
    }
}

// Booking Processor (MULTI-THREAD)
class BookingProcessor extends Thread {

    private Queue<BookingRequest> queue;
    private RoomInventory inventory;

    public BookingProcessor(Queue<BookingRequest> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            BookingRequest request;

            // Synchronize queue access
            synchronized (queue) {
                if (queue.isEmpty()) break;
                request = queue.poll();
            }

            if (request != null) {
                boolean success = inventory.allocateRoom(request.roomType);

                if (success) {
                    System.out.println(Thread.currentThread().getName() +
                            " booked " + request.roomType +
                            " for " + request.guestName);
                } else {
                    System.out.println(Thread.currentThread().getName() +
                            " FAILED booking for " + request.guestName);
                }
            }
        }
    }
}

// Main Class
public class bookmystayapp {

    public static void main(String[] args) {

        Queue<BookingRequest> bookingQueue = new LinkedList<>();
        RoomInventory inventory = new RoomInventory();

        // Add booking requests
        bookingQueue.add(new BookingRequest("A", "Single"));
        bookingQueue.add(new BookingRequest("B", "Single"));
        bookingQueue.add(new BookingRequest("C", "Single")); // extra → test race
        bookingQueue.add(new BookingRequest("D", "Double"));
        bookingQueue.add(new BookingRequest("E", "Suite"));

        // Create multiple threads
        BookingProcessor t1 = new BookingProcessor(bookingQueue, inventory);
        BookingProcessor t2 = new BookingProcessor(bookingQueue, inventory);
        BookingProcessor t3 = new BookingProcessor(bookingQueue, inventory);

        t1.start();
        t2.start();
        t3.start();

        // Wait for threads
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        inventory.displayInventory();
    }
}