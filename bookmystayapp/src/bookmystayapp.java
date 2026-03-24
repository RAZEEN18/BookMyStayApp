import java.util.*;

// Custom Exception Class
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

// RoomInventory Class
class RoomInventory {

    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public int getAvailability(String roomType) {
        return rooms.getOrDefault(roomType, 0);
    }
}

// ReservationValidator Class
class ReservationValidator {

    public void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        // Validate guest name
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Validate room type (CASE SENSITIVE ⚠)
        if (!(roomType.equals("Single") ||
                roomType.equals("Double") ||
                roomType.equals("Suite"))) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        // Validate availability
        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for selected type.");
        }
    }
}

// Main Class
public class bookmystayapp {

    public static void main(String[] args) {

        System.out.println("Booking Validation");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();

        try {
            // Input
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Validation
            validator.validate(guestName, roomType, inventory);

            // If valid
            System.out.println("Booking successful!");

        } catch (InvalidBookingException e) {

            // Handle error
            System.out.println("Booking failed: " + e.getMessage());

        } finally {
            scanner.close();
        }
    }
}