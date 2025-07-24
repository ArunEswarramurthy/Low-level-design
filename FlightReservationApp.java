import java.util.*;

class Flight {
    String flightNo;
    String from;
    String to;
    int seats;
    double price;

    public Flight(String flightNo, String from, String to, int seats, double price) {
        this.flightNo = flightNo;
        this.from = from;
        this.to = to;
        this.seats = seats;
        this.price = price;
    }

    public void display() {
        System.out.println("Flight: " + flightNo + " | From: " + from + " | To: " + to + 
                           " | Seats: " + seats + " | Price: ₹" + price);
    }
}

class Booking {
    String username;
    Flight flight;
    int seatCount;

    public Booking(String username, Flight flight, int seatCount) {
        this.username = username;
        this.flight = flight;
        this.seatCount = seatCount;
    }

    public void display() {
        System.out.println("Booking: " + seatCount + " seat(s) on Flight " + flight.flightNo +
                           " | From: " + flight.from + " -> " + flight.to +
                           " | Price: ₹" + (seatCount * flight.price));
    }
}

class User {
    String username;
    String password;
    List<Booking> bookings = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void bookFlight(Scanner sc, List<Flight> flights) {
        System.out.println("\nAvailable Flights:");
        for (int i = 0; i < flights.size(); i++) {
            System.out.print((i + 1) + ". ");
            flights.get(i).display();
        }
        System.out.print("Select flight number: ");
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice < 1 || choice > flights.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Flight flight = flights.get(choice - 1);
        System.out.print("Enter number of seats to book: ");
        int seats = sc.nextInt();
        sc.nextLine();

        if (seats <= 0 || seats > flight.seats) {
            System.out.println("Invalid seat count.");
            return;
        }

        flight.seats -= seats;
        Booking booking = new Booking(this.username, flight, seats);
        bookings.add(booking);
        System.out.println("Booking successful!");
        booking.display();
    }

    public void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            System.out.println("\n--- Your Bookings ---");
            for (Booking b : bookings) {
                b.display();
            }
        }
    }

    public void cancelBooking(Scanner sc, List<Flight> flights) {
        if (bookings.isEmpty()) {
            System.out.println("No bookings to cancel.");
            return;
        }
        System.out.println("\nYour Bookings:");
        for (int i = 0; i < bookings.size(); i++) {
            System.out.print((i + 1) + ". ");
            bookings.get(i).display();
        }
        System.out.print("Select booking to cancel: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice < 1 || choice > bookings.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Booking booking = bookings.remove(choice - 1);
        booking.flight.seats += booking.seatCount;
        System.out.println(" Booking canceled.");
    }
}

public class FlightReservationApp {
    static Map<String, User> users = new HashMap<>();
    static List<Flight> flights = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        seedFlights();

        while (true) {
            System.out.println("\n--- Flight Reservation System ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void register() {
        System.out.print("Enter username: ");
        String uname = sc.nextLine();
        System.out.print("Enter password: ");
        String pwd = sc.nextLine();

        if (users.containsKey(uname)) {
            System.out.println(" User already exists.");
        } else {
            users.put(uname, new User(uname, pwd));
            System.out.println(" Registration successful!");
        }
    }

    static void login() {
        System.out.print("Enter username: ");
        String uname = sc.nextLine();
        System.out.print("Enter password: ");
        String pwd = sc.nextLine();

        User user = users.get(uname);
        if (user != null && user.password.equals(pwd)) {
            System.out.println(" Login successful.");
            dashboard(user);
        } else {
            System.out.println(" Invalid credentials.");
        }
    }

    static void dashboard(User user) {
        while (true) {
            System.out.println("\n--- User Dashboard ---");
            System.out.println("1. View Flights");
            System.out.println("2. Book Ticket");
            System.out.println("3. View My Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Logout");
            System.out.print("Choose: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> {
                    System.out.println("\nAvailable Flights:");
                    for (Flight f : flights) f.display();
                }
                case 2 -> user.bookFlight(sc, flights);
                case 3 -> user.viewBookings();
                case 4 -> user.cancelBooking(sc, flights);
                case 5 -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void seedFlights() {
        flights.add(new Flight("AI101", "Delhi", "Mumbai", 50, 4500));
        flights.add(new Flight("AI202", "Bangalore", "Chennai", 40, 3000));
        flights.add(new Flight("AI303", "Kolkata", "Delhi", 60, 4200));
        flights.add(new Flight("AI404", "Hyderabad", "Goa", 30, 3500));
    }
}
