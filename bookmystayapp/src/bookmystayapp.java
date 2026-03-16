public class bookmystayapp{

    abstract static class Room {
        protected String roomType;
        protected int beds;
        protected int size;
        protected double price;

        public Room(String roomType, int beds, int size, double price) {
            this.roomType = roomType;
            this.beds = beds;
            this.size = size;
            this.price = price;
        }

        public void displayRoomDetails(int availableRooms) {
            System.out.println(roomType + ":");
            System.out.println("Beds: " + beds);
            System.out.println("Size: " + size + " sqft");
            System.out.println("Price per night: " + price);
            System.out.println("Available Rooms: " + availableRooms);
            System.out.println();
        }
    }

    static class SingleRoom extends Room {
        public SingleRoom() {
            super("Single Room", 1, 250, 1500);
        }
    }

    static class DoubleRoom extends Room {
        public DoubleRoom() {
            super("Double Room", 2, 400, 2500);
        }
    }

    static class SuiteRoom extends Room {
        public SuiteRoom() {
            super("Suite Room", 3, 750, 5000);
        }
    }

    static class Inventory {
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 0; // example: suite unavailable
    }

    static class SearchService {

        public void searchAvailableRooms(Inventory inventory) {

            System.out.println("Available Rooms\n");

            Room single = new SingleRoom();
            Room doubleRoom = new DoubleRoom();
            Room suite = new SuiteRoom();

            if (inventory.singleAvailable > 0) {
                single.displayRoomDetails(inventory.singleAvailable);
            }

            if (inventory.doubleAvailable > 0) {
                doubleRoom.displayRoomDetails(inventory.doubleAvailable);
            }

            if (inventory.suiteAvailable > 0) {
                suite.displayRoomDetails(inventory.suiteAvailable);
            }
        }
    }

    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        SearchService searchService = new SearchService();

        searchService.searchAvailableRooms(inventory);
    }
}