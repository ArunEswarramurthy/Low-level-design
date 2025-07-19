import java.time.LocalDate;
import java.util.*;

enum ContentType {
    MOVIE, SERIES
}

class Content {
    String id;
    String title;
    ContentType type;
    String genre;

    public Content(String id, String title, ContentType type, String genre) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " (" + type + ") - Genre: " + genre;
    }
}

class Plan {
    String name;
    int durationInDays;
    double price;
    List<ContentType> accessTypes;

    public Plan(String name, int duration, double price, List<ContentType> accessTypes) {
        this.name = name;
        this.durationInDays = duration;
        this.price = price;
        this.accessTypes = accessTypes;
    }

    @Override
    public String toString() {
        return name + " - ₹" + price + " for " + durationInDays + " days. Access: " + accessTypes;
    }
}

class Subscription {
    Plan plan;
    LocalDate startDate;
    LocalDate endDate;

    public Subscription(Plan plan) {
        this.plan = plan;
        this.startDate = LocalDate.now();
        this.endDate = startDate.plusDays(plan.durationInDays);
    }

    public boolean isActive() {
        return LocalDate.now().isBefore(endDate);
    }
}

class User {
    String id;
    String name;
    String email;
    Subscription subscription;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public void subscribe(Plan plan) {
        this.subscription = new Subscription(plan);
        System.out.println("Subscribed to " + plan.name + " plan. Valid till: " + subscription.endDate);
    }

    public boolean canWatch(Content content) {
        if (subscription == null || !subscription.isActive()) return false;
        return subscription.plan.accessTypes.contains(content.type);
    }
}

public class OTTSystem {
    static Map<String, User> users = new HashMap<>();
    static List<Content> contents = new ArrayList<>();
    static List<Plan> plans = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static int userCounter = 1;

    public static void main(String[] args) {
        seedPlans();
        seedContent();

        while (true) {
            System.out.println("\n=== OTT Subscription System ===");
            System.out.println("1. Register");
            System.out.println("2. View Plans");
            System.out.println("3. Subscribe to Plan");
            System.out.println("4. View Content");
            System.out.println("5. Watch Content");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> registerUser();
                case 2 -> showPlans();
                case 3 -> subscribeToPlan();
                case 4 -> showContents();
                case 5 -> watchContent();
                case 6 -> {
                    System.out.println("Thank you for using OTT System.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void seedPlans() {
        plans.add(new Plan("Basic", 30, 199, List.of(ContentType.MOVIE)));
        plans.add(new Plan("Premium", 30, 399, List.of(ContentType.MOVIE, ContentType.SERIES)));
    }

    static void seedContent() {
        contents.add(new Content("M1", "Inception", ContentType.MOVIE, "Sci-Fi"));
        contents.add(new Content("M2", "Interstellar", ContentType.MOVIE, "Sci-Fi"));
        contents.add(new Content("S1", "Stranger Things", ContentType.SERIES, "Horror"));
        contents.add(new Content("S2", "Breaking Bad", ContentType.SERIES, "Crime"));
    }

    static void registerUser() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        String userId = "U" + userCounter++;
        User user = new User(userId, name, email);
        users.put(userId, user);
        System.out.println("Registered successfully. Your User ID: " + userId);
    }

    static void showPlans() {
        System.out.println("\nAvailable Plans:");
        for (int i = 0; i < plans.size(); i++) {
            System.out.println((i + 1) + ". " + plans.get(i));
        }
    }

    static void subscribeToPlan() {
        System.out.print("Enter your User ID: ");
        String userId = scanner.nextLine();
        User user = users.get(userId);

        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        showPlans();
        System.out.print("Choose a plan (number): ");
        int planChoice = Integer.parseInt(scanner.nextLine());
        if (planChoice < 1 || planChoice > plans.size()) {
            System.out.println("Invalid plan selection.");
            return;
        }

        user.subscribe(plans.get(planChoice - 1));
    }

    static void showContents() {
        System.out.println("\nAvailable Content:");
        for (Content c : contents) {
            System.out.println(c);
        }
    }

    static void watchContent() {
        System.out.print("Enter your User ID: ");
        String userId = scanner.nextLine();
        User user = users.get(userId);

        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        showContents();
        System.out.print("Enter Content ID to watch: ");
        String contentId = scanner.nextLine();

        Optional<Content> contentOpt = contents.stream()
                .filter(c -> c.id.equalsIgnoreCase(contentId))
                .findFirst();

        if (contentOpt.isEmpty()) {
            System.out.println("Content not found!");
            return;
        }

        Content content = contentOpt.get();

        if (user.canWatch(content)) {
            System.out.println("Enjoy watching: " + content.title);
        } else {
            System.out.println("Your plan does not support this content type. Upgrade to Premium.");
        }
    }
}
