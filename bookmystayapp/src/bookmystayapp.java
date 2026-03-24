import java.util.*;

// Reservation Class
class Reservation {

    private String reservationId;

    public Reservation(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getReservationId() {
        return reservationId;
    }
}

// BookingHistory Class
class BookingHistory {

    // List to store confirmed reservations
    private List<Reservation> confirmedReservations;

    // Constructor
    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    // Add reservation to history
    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    // Get all reservations
    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

// BookingReportService Class
class BookingReportService {

    // Generate report
    public void generateReport(BookingHistory history) {

        System.out.println("Booking History and Reporting");

        List<Reservation> reservations = history.getConfirmedReservations();

        for (Reservation r : reservations) {
            System.out.println("Reservation ID: " + r.getReservationId());
        }

        System.out.println("Total Bookings: " + reservations.size());
    }
}

// Main Class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        // Add confirmed reservations
        history.addReservation(new Reservation("Single-1"));
        history.addReservation(new Reservation("Double-2"));
        history.addReservation(new Reservation("Suite-3"));

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
    }
}}