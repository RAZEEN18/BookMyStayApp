import java.util.*;

// Service Class
class Service {

    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// Manager Class
class AddOnServiceManager {

    private Map<String, List<Service>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, Service service) {
        servicesByReservation.putIfAbsent(reservationId, new ArrayList<>());
        servicesByReservation.get(reservationId).add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {

        double total = 0;

        List<Service> services = servicesByReservation.get(reservationId);

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// Main Class
public class bookmystayapp {

    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "Single-1";

        // Adding services
        manager.addService(reservationId, new Service("Breakfast", 500));
        manager.addService(reservationId, new Service("Spa", 1000));

        // Output
        System.out.println("Add-On Service Selection");
        System.out.println("Reservation ID: " + reservationId);

        double total = manager.calculateTotalServiceCost(reservationId);

        System.out.println("Total Add-On Cost: " + total);
    }
}