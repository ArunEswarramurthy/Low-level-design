import java.util.Scanner;

enum Direction {
    UP, DOWN, IDLE
}

class Request {
    private int floorNumber;
    private Direction direction;

    public Request(int floorNumber, Direction direction) {
        this.floorNumber = floorNumber;
        this.direction = direction;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public Direction getDirection() {
        return direction;
    }
}

class Lift {
    private int currentFloor;
    private Direction currentDirection;
    private boolean isMoving;

    public Lift() {
        this.currentFloor = 0; // Ground floor
        this.currentDirection = Direction.IDLE;
        this.isMoving = false;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void moveToFloor(int targetFloor) {
        if (targetFloor == currentFloor) {
            System.out.println("Lift is already at floor " + currentFloor);
            return;
        }

        System.out.println("Lift moving from floor " + currentFloor + " to floor " + targetFloor);
        currentFloor = targetFloor;
        isMoving = false;
        currentDirection = Direction.IDLE;
        System.out.println("Lift reached floor " + currentFloor);
    }

    public void setDirection(Direction direction) {
        this.currentDirection = direction;
        this.isMoving = true;
    }

    public boolean isMoving() {
        return isMoving;
    }
}

class LiftController {
    private Lift lift;

    public LiftController(Lift lift) {
        this.lift = lift;
    }

    public void handleRequest(Request request) {
        int targetFloor = request.getFloorNumber();
        Direction requestDirection = request.getDirection();

        int currentFloor = lift.getCurrentFloor();

        if (targetFloor > currentFloor) {
            lift.setDirection(Direction.UP);
        } else if (targetFloor < currentFloor) {
            lift.setDirection(Direction.DOWN);
        } else {
            System.out.println("Lift is already on the requested floor: " + currentFloor);
            return;
        }

        lift.moveToFloor(targetFloor);
    }
}

public class LiftSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Lift lift = new Lift();
        LiftController controller = new LiftController(lift);

        while (true) {
            System.out.println("\n--- Lift System Menu ---");
            System.out.println("1. Request Lift");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            if (choice == 1) {
                System.out.print("Enter direction (UP/DOWN): ");
                String dirInput = scanner.nextLine().trim().toUpperCase();

                Direction direction;
                if (dirInput.equals("UP")) {
                    direction = Direction.UP;
                } else if (dirInput.equals("DOWN")) {
                    direction = Direction.DOWN;
                } else {
                    System.out.println("Invalid direction! Please enter UP or DOWN.");
                    continue;
                }

                System.out.print("Enter floor number (0 - 10): ");
                int floor;
                try {
                    floor = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid floor number!");
                    continue;
                }

                if (floor < 0 || floor > 10) {
                    System.out.println("Floor must be between 0 and 10.");
                    continue;
                }

                Request request = new Request(floor, direction);
                controller.handleRequest(request);

            } else if (choice == 2) {
                System.out.println("Exiting Lift System.");
                break;
            } else {
                System.out.println("Invalid option! Try again.");
            }
        }

        scanner.close();
    }
}
