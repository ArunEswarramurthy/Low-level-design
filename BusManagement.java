import java.util.*;

class Passenger {
    String id;
    String name;
    List<Booking> bookings;

    public Passenger(String id, String name) {
        this.id = id;
        this.name = name;
        this.bookings = new ArrayList<>();
    }
}

class Admin {
    String id;
    String name;

    public Admin(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Bus {
    String id;
    String number;
    int totalSeats;
    int availableSeats;

    public Bus(String id, String number, int totalSeats) {
        this.id = id;
        this.number = number;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }
}

class Route {
    String id;
    String origin;
    String destination;
    List<String> stops;

    public Route(String id, String origin, String destination, List<String> stops) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.stops = stops;
    }
}

class Schedule {
    String id;
    Bus bus;
    Route route;
    String departureTime;
    String arrivalTime;

    public Schedule(String id, Bus bus, Route route, String departureTime, String arrivalTime) {
        this.id = id;
        this.bus = bus;
        this.route = route;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }
}

class Booking {
    String id;
    Passenger passenger;
    Schedule schedule;
    int seatNumber;
    double fare;

    public Booking(String id, Passenger passenger, Schedule schedule, int seatNumber, double fare) {
        this.id = id;
        this.passenger = passenger;
        this.schedule = schedule;
        this.seatNumber = seatNumber;
        this.fare = fare;
    }
}

class BMTCSystem {
    Map<String, Passenger> passengers = new HashMap<>();
    Map<String, Bus> buses = new HashMap<>();
    Map<String, Route> routes = new HashMap<>();
    Map<String, Schedule> schedules = new HashMap<>();
    List<Booking> bookings = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public void registerPassenger() {
        System.out.print("Enter passenger ID: ");
        String id = sc.next();
        System.out.print("Enter name: ");
        String name = sc.next();
        passengers.put(id, new Passenger(id, name));
        System.out.println("Passenger registered.");
    }

    public void addBus() {
        System.out.print("Enter bus ID: ");
        String id = sc.next();
        System.out.print("Enter number plate: ");
        String number = sc.next();
        System.out.print("Enter total seats: ");
        int seats = sc.nextInt();
        buses.put(id, new Bus(id, number, seats));
        System.out.println("Bus added.");
    }

    public void addRoute() {
        System.out.print("Enter route ID: ");
        String id = sc.next();
        System.out.print("Enter origin: ");
        String origin = sc.next();
        System.out.print("Enter destination: ");
        String dest = sc.next();
        sc.nextLine();
        System.out.print("Enter comma-separated stops: ");
        String stopLine = sc.nextLine();
        List<String> stops = Arrays.asList(stopLine.split(","));
        routes.put(id, new Route(id, origin, dest, stops));
        System.out.println("Route added.");
    }

    public void addSchedule() {
        System.out.print("Enter schedule ID: ");
        String id = sc.next();
        System.out.print("Enter bus ID: ");
        String busId = sc.next();
        System.out.print("Enter route ID: ");
        String routeId = sc.next();
        System.out.print("Enter departure time: ");
        String dep = sc.next();
        System.out.print("Enter arrival time: ");
        String arr = sc.next();

        Bus bus = buses.get(busId);
        Route route = routes.get(routeId);
        if (bus == null || route == null) {
            System.out.println("Invalid bus or route.");
            return;
        }
        Schedule schedule = new Schedule(id, bus, route, dep, arr);
        schedules.put(id, schedule);
        System.out.println("Schedule added.");
    }

    public void viewRoutes() {
        for (Route r : routes.values()) {
            System.out.println(r.id + ": " + r.origin + " -> " + r.destination);
        }
    }

    public void bookTicket() {
        System.out.print("Enter passenger ID: ");
        String pid = sc.next();
        Passenger p = passengers.get(pid);
        if (p == null) {
            System.out.println("Passenger not found.");
            return;
        }

        viewRoutes();
        System.out.print("Enter schedule ID to book: ");
        String sid = sc.next();
        Schedule schedule = schedules.get(sid);
        if (schedule == null || schedule.bus.availableSeats == 0) {
            System.out.println("Invalid schedule or no seats.");
            return;
        }

        int seatNum = schedule.bus.totalSeats - schedule.bus.availableSeats + 1;
        double fare = 10.0 * schedule.route.stops.size();

        Booking b = new Booking(UUID.randomUUID().toString(), p, schedule, seatNum, fare);
        bookings.add(b);
        p.bookings.add(b);
        schedule.bus.availableSeats--;
        System.out.println("Ticket booked! Seat: " + seatNum + ", Fare: Rs." + fare);
    }

    public void viewBookingHistory() {
        System.out.print("Enter passenger ID: ");
        String pid = sc.next();
        Passenger p = passengers.get(pid);
        if (p == null) {
            System.out.println("Passenger not found.");
            return;
        }
        for (Booking b : p.bookings) {
            System.out.println("Booking: " + b.id + " | Route: " + b.schedule.route.origin + " -> " + b.schedule.route.destination +
                    " | Time: " + b.schedule.departureTime + " | Seat: " + b.seatNumber);
        }
    }

    public void menu() {
        while (true) {
            System.out.println("\n1. Register Passenger\n2. Add Bus\n3. Add Route\n4. Add Schedule\n5. View Routes\n6. Book Ticket\n7. Booking History\n8. Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> registerPassenger();
                case 2 -> addBus();
                case 3 -> addRoute();
                case 4 -> addSchedule();
                case 5 -> viewRoutes();
                case 6 -> bookTicket();
                case 7 -> viewBookingHistory();
                case 8 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}

public class BMTCApp {
    public static void main(String[] args) {
        new BMTCSystem().menu();
    }
}
