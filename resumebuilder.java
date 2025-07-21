import java.util.*;

class Resume {
    String name;
    String github;
    String linkedIn;
    String about;
    String education;
    String projects;
    String skills;
    String hobbies;
    String achievements;
    String awards;

    public Resume(String name, String github, String linkedIn, String about, String education, String projects,
                  String skills, String hobbies, String achievements, String awards) {
        this.name = name;
        this.github = github;
        this.linkedIn = linkedIn;
        this.about = about;
        this.education = education;
        this.projects = projects;
        this.skills = skills;
        this.hobbies = hobbies;
        this.achievements = achievements;
        this.awards = awards;
    }

    public void display() {
        System.out.println("\n===============================================");
        System.out.println("                   RESUME                      ");
        System.out.println("===============================================");
        System.out.printf("%-15s: %s%n", "Name", name);
        System.out.printf("%-15s: %s%n", "GitHub", github);
        System.out.printf("%-15s: %s%n", "LinkedIn", linkedIn);
        System.out.printf("%-15s: %s%n", "About", about);
        System.out.println("-----------------------------------------------");
        System.out.println("Education:\n" + education);
        System.out.println("-----------------------------------------------");
        System.out.println("Projects:\n" + projects);
        System.out.println("-----------------------------------------------");
        System.out.println("Skills:\n" + skills);
        System.out.println("-----------------------------------------------");
        System.out.println("Hobbies:\n" + hobbies);
        System.out.println("-----------------------------------------------");
        System.out.println("Achievements:\n" + achievements);
        System.out.println("-----------------------------------------------");
        System.out.println("Awards:\n" + awards);
        System.out.println("===============================================\n");
    }
}

class User {
    String username;
    String password;
    Resume resume;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void createResume(Scanner sc) {
        sc.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter GitHub URL: ");
        String github = sc.nextLine();
        System.out.print("Enter LinkedIn URL: ");
        String linkedIn = sc.nextLine();
        System.out.print("Write About Yourself: ");
        String about = sc.nextLine();
        System.out.print("Enter Education: ");
        String education = sc.nextLine();
        System.out.print("Enter Projects: ");
        String projects = sc.nextLine();
        System.out.print("Enter Skills: ");
        String skills = sc.nextLine();
        System.out.print("Enter Hobbies: ");
        String hobbies = sc.nextLine();
        System.out.print("Enter Achievements: ");
        String achievements = sc.nextLine();
        System.out.print("Enter Awards: ");
        String awards = sc.nextLine();

        this.resume = new Resume(name, github, linkedIn, about, education, projects, skills, hobbies, achievements, awards);
        System.out.println("Resume created successfully!");
    }

    public void viewResume() {
        if (resume != null) {
            resume.display();
        } else {
            System.out.println("No resume found. Please create one first.");
        }
    }
}

// Main system class
public class ResumeBuilderApp {
    static Scanner sc = new Scanner(System.in);
    static Map<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n==== Resume Builder ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> registerUser();
                case 2 -> loginUser();
                case 3 -> {
                    System.out.println("Thank you! Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void registerUser() {
        System.out.print("Enter username: ");
        String uname = sc.next();
        if (users.containsKey(uname)) {
            System.out.println("Username already exists.");
            return;
        }
        System.out.print("Enter password: ");
        String pwd = sc.next();
        users.put(uname, new User(uname, pwd));
        System.out.println("Registration successful!");
    }

    static void loginUser() {
        System.out.print("Enter username: ");
        String uname = sc.next();
        System.out.print("Enter password: ");
        String pwd = sc.next();

        User user = users.get(uname);
        if (user != null && user.password.equals(pwd)) {
            System.out.println("Login successful!");
            userDashboard(user);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    static void userDashboard(User user) {
        while (true) {
            System.out.println("\n--- User Dashboard ---");
            System.out.println("1. Create Resume");
            System.out.println("2. View Resume");
            System.out.println("3. Logout");
            System.out.print("Choose option: ");
            int ch = sc.nextInt();
            switch (ch) {
                case 1 -> user.createResume(sc);
                case 2 -> user.viewResume();
                case 3 -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
