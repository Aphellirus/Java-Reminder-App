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
            for (int i = 0; i < reminders.size(); i++) {
                Reminder reminder = reminders.get(i);
                System.out.println("Index: " + i);
                System.out.println("Title: " + reminder.getTitle());
                System.out.println("Description: " + reminder.getDescription());
                System.out.println("Date: " + reminder.getDate());
                System.out.println("----------------------");
            }
        }
    }

    public void editReminder() {
        System.out.print("Enter the index of the reminder to edit: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (index >= 0 && index < reminders.size()) {
            Reminder reminder = reminders.get(index);

            System.out.println("Current Title: " + reminder.getTitle());
            System.out.print("Enter new title: ");
            String newTitle = scanner.nextLine();

            System.out.println("Current Description: " + reminder.getDescription());
            System.out.print("Enter new description: ");
            String newDescription = scanner.nextLine();

            System.out.println("Current Date: " + reminder.getDate());
            System.out.print("Enter new date: ");
            String newDate = scanner.nextLine();

            reminder = new Reminder(newTitle, newDescription, newDate);
            reminders.set(index, reminder);

            System.out.println("Reminder updated successfully!");
        } else {
            System.out.println("Invalid index. Please try again.");
        }
    }

    public void deleteReminder() {
        System.out.print("Enter the index of the reminder to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (index >= 0 && index < reminders.size()) {
            reminders.remove(index);
            System.out.println("Reminder deleted successfully!");
        } else {
            System.out.println("Invalid index. Please try again.");
        }
    }

    public void run() {
        while (true) {
            System.out.println("Reminder App Menu:");
            System.out.println("1. Add Reminder");
            System.out.println("2. View Reminders");
            System.out.println("3. Edit Reminder");
            System.out.println("4. Delete Reminder");
            System.out.println("5. Exit");

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
                    editReminder();
                    break;
                case 4:
                    deleteReminder();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        ReminderApp app = new ReminderApp();
        app.run();
    }
}