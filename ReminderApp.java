import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Reminder {
    private String title;
    private String description;
    private String date;

    public Reminder(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}

public class ReminderApp {
    private List<Reminder> reminders;
    private Scanner scanner;

    public ReminderApp() {
        reminders = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addReminder() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter date: ");
        String date = scanner.nextLine();

        Reminder reminder = new Reminder(title, description, date);
        reminders.add(reminder);

        System.out.println("Reminder added successfully!");
    }

    public void viewReminders() {
        if (reminders.isEmpty()) {
            System.out.println("No reminders found.");
        } else {
            System.out.println("Reminders:");
            for (Reminder reminder : reminders) {
                System.out.println("Title: " + reminder.getTitle());
                System.out.println("Description: " + reminder.getDescription());
                System.out.println("Date: " + reminder.getDate());
                System.out.println("----------------------");
            }
        }
    }

    public static void main(String[] args) {
        ReminderApp app = new ReminderApp();
        app.run();
    }

    public void run() {
        while (true) {
            System.out.println("Reminder App Menu:");
            System.out.println("1. Add Reminder");
            System.out.println("2. View Reminders");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addReminder();
                    break;
                case 2:
                    viewReminders();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }
    }
}
