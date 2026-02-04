import java.util.ArrayList;
import java.util.Scanner;

// 1. Class to represent a single Student
class Student {
    private String name;
    private double grade;

    public Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }
}

// 2. Main Class containing the logic
class GradeTracker {

    // List to store student objects
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Student Grade Tracker!");

        while (true) {
            printMenu();
            int choice = getValidIntInput();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayAllGrades();
                    break;
                case 3:
                    calculateAverage();
                    break;
                case 4:
                    findHighestAndLowest();
                    break;
                case 5:
                    System.out.println("Exiting program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    // --- Helper Methods ---

    private static void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Add a Student");
        System.out.println("2. Display All Grades");
        System.out.println("3. Calculate Class Average");
        System.out.println("4. Find Highest & Lowest Grades");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.next(); // Use nextLine() if you want full names with spaces

        double grade = -1;
        while (grade < 0 || grade > 100) {
            System.out.print("Enter grade (0-100): ");
            if (scanner.hasNextDouble()) {
                grade = scanner.nextDouble();
                if (grade < 0 || grade > 100) {
                    System.out.println("Error: Grade must be between 0 and 100.");
                }
            } else {
                System.out.println("Error: Please enter a valid number.");
                scanner.next(); // Consume invalid input
            }
        }

        students.add(new Student(name, grade));
        System.out.println("Student added successfully!");
    }

    private static void displayAllGrades() {
        if (students.isEmpty()) {
            System.out.println("No students recorded yet.");
            return;
        }
        System.out.println("\n--- Student List ---");
        for (Student s : students) {
            System.out.println("Name: " + s.getName() + " | Grade: " + s.getGrade());
        }
    }

    private static void calculateAverage() {
        if (students.isEmpty()) {
            System.out.println("No data available to calculate average.");
            return;
        }

        double sum = 0;
        for (Student s : students) {
            sum += s.getGrade();
        }
        double average = sum / students.size();
        System.out.printf("Class Average: %.2f%n", average);
    }

    private static void findHighestAndLowest() {
        if (students.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        Student highest = students.get(0);
        Student lowest = students.get(0);

        for (Student s : students) {
            if (s.getGrade() > highest.getGrade()) {
                highest = s;
            }
            if (s.getGrade() < lowest.getGrade()) {
                lowest = s;
            }
        }

        System.out.println("\n--- Statistics ---");
        System.out.println("Highest: " + highest.getName() + " (" + highest.getGrade() + ")");
        System.out.println("Lowest:  " + lowest.getName() + " (" + lowest.getGrade() + ")");
    }
    
    // Safety method to prevent crashing if user types letters instead of numbers
    private static int getValidIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}