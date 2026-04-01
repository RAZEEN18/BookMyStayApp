import java.util.*;

// RoomInventory class
class RoomInventory {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public void incrementRoom(String roomType) {
        rooms.put(roomType, rooms.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + " : " + rooms.get(type));
        }
    }
}

// Cancellation Service
class CancellationService {

    private Stack<String> releasedRooms;
    private Map<String, String> reservationMap;

    public CancellationService() {
        releasedRooms = new Stack<>();
        reservationMap = new HashMap<>();
    }

    // Register booking
    public void registerBooking(String reservationId, String roomType) {
        reservationMap.put(reservationId, roomType);
        System.out.println("Booking confirmed: " + reservationId + " -> " + roomType);
    }

    // Cancel booking
    public void cancelBooking(String reservationId, RoomInventory inventory) {

        if (!reservationMap.containsKey(reservationId)) {
            System.out.println("Invalid cancellation! Reservation not found.");
            return;
        }

        String roomType = reservationMap.get(reservationId);

        releasedRooms.push(roomType);
        inventory.incrementRoom(roomType);
        reservationMap.remove(reservationId);

        System.out.println("Booking cancelled: " + reservationId);
    }

    // Show rollback history
    public void showRollbackHistory() {
        System.out.println("Rollback History (Recent first):");
        for (int i = releasedRooms.size() - 1; i >= 0; i--) {
            System.out.println(releasedRooms.get(i));
        }
    }
}

// Main class
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        service.registerBooking("R101", "Single");
        service.registerBooking("R102", "Double");

        System.out.println();

        service.cancelBooking("R101", inventory);

        System.out.println();

        inventory.displayInventory();

        System.out.println();

        service.showRollbackHistory();
    }
}