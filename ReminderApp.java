import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Reminder {
    // This reminder class represents a single reminder with its title, description, date, recurrence, and priority
    private String title;
    private String description;
    private String date;
    private Recurrence recurrence;
    private Priority priority;

    public Reminder(String title, String description, String date, Recurrence recurrence, Priority priority) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.recurrence = recurrence;
        this.priority = priority;
    }

    // Getters for the Reminder properties
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public Priority getPriority() {
        return priority;
    }
}

enum Recurrence {
    NONE, DAILY, WEEKLY, MONTHLY, YEARLY
}

enum Priority {
    LOW, MEDIUM, HIGH
}

public class ReminderApp {
    private List<Reminder> reminders;
    private Scanner scanner;
    private static final String DATA_FILE = "reminders.txt";

    public ReminderApp() {
        reminders = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadReminders();
    }

    // Method to add a new reminder to the list
    public void addReminder() {
        System.out.print("Enter the title: ");
        String title = scanner.nextLine();

        System.out.print("Enter the description: ");
        String description = scanner.nextLine();

        System.out.print("Enter the date (dd/MM/yyyy): ");
        String dateStr = scanner.nextLine();

        // Code to parse the date and handle any exceptions
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(dateStr, formatter);
            String formattedDate = date.format(formatter);

            // Ask for recurrence and priority of the reminder
            Recurrence recurrence = askForRecurrence();
            Priority priority = askForPriority();

            Reminder newReminder = new Reminder(title, description, formattedDate, recurrence, priority);
            reminders.add(newReminder);

            System.out.println("Reminder added successfully.");
            saveReminders();
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use the format: dd/MM/yyyy");
        }
    }

    // Method to load reminders from the .txt file
    private void loadReminders() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] reminderData = line.split(",");
                String title = reminderData[0];
                String description = reminderData[1];
                String date = reminderData[2];
                Recurrence recurrence = Recurrence.valueOf(reminderData[3]);
                Priority priority = Priority.valueOf(reminderData[4]);
                Reminder reminder = new Reminder(title, description, date, recurrence, priority);
                reminders.add(reminder);
            }
            System.out.println("Reminders loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while loading reminders: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid recurrence or priority value in the data file.");
        }
    }

    // Method to save reminders to the .txt file
    private void saveReminders() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Reminder reminder : reminders) {
                writer.write(reminder.getTitle() + "," + reminder.getDescription() + "," + reminder.getDate() + "," +
                        reminder.getRecurrence() + "," + reminder.getPriority());
                writer.newLine();
            }
            System.out.println("Reminders saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving reminders: " + e.getMessage());
        }
    }

    // Method to view and sort reminders
    public void viewReminders() {
        if (reminders.isEmpty()) {
            System.out.println("No reminders found.");
        } else {
            // Display the sorting menu and get the user's choice
            int choice = displaySortingMenu();

            // Sort the reminders based on the user's choice
            sortReminders(choice);

            // Display the sorted reminders
            displayReminders();
        }
    }

    // Method to delete a reminder from the list
    public void deleteReminder() {
        if (reminders.isEmpty()) {
            System.out.println("No reminders found.");
        } else {
            displayReminders(); // Display the reminders to choose from

            System.out.print("Enter the index of the reminder to delete: ");
            int index = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (index >= 0 && index < reminders.size()) {
                Reminder deletedReminder = reminders.remove(index);
                System.out.println("Reminder deleted:");
                System.out.println("Title: " + deletedReminder.getTitle());
                System.out.println("Description: " + deletedReminder.getDescription());
                System.out.println("Date: " + deletedReminder.getDate());
                System.out.println("Recurrence: " + deletedReminder.getRecurrence());
                System.out.println("Priority: " + deletedReminder.getPriority());

                saveReminders(); // Save the updated reminders list
            } else {
                System.out.println("Invalid index. No reminder deleted.");
            }
        }
    }

    // Method to display the sorting menu
    private int displaySortingMenu() {
        System.out.println("View Reminders Menu:");
        System.out.println("1. Sort by Title");
        System.out.println("2. Sort by Date");
        System.out.println("3. Sort by Recurrence");
        System.out.println("4. Sort by Priority");
        System.out.println("5. Back to Main Menu");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        return choice;
    }

    // Method to sort the reminders based on the user's choice
    private void sortReminders(int choice) {
        switch (choice) {
            case 1:
                Collections.sort(reminders, Comparator.comparing(Reminder::getTitle));
                break;
            case 2:
                Collections.sort(reminders, Comparator.comparing(Reminder::getDate));
                break;
            case 3:
                Collections.sort(reminders, Comparator.comparing(Reminder::getRecurrence));
                break;
            case 4:
                Collections.sort(reminders, Comparator.comparing(Reminder::getPriority));
                break;
            case 5:
                return; // Return to the main menu
            default:
                System.out.println("Invalid choice. Returning to the main menu.");
                return; // Return to the main menu
        }
    }

    // Method to display the sorted reminders
    private void displayReminders() {
        System.out.println("Reminder List:");
        int index = 0;
        for (Reminder reminder : reminders) {
            System.out.println("Index: " + index);
            System.out.println("Title: " + reminder.getTitle());
            System.out.println("Description: " + reminder.getDescription());
            System.out.println("Date: " + reminder.getDate());
            System.out.println("Recurrence: " + reminder.getRecurrence());
            System.out.println("Priority: " + reminder.getPriority());
            System.out.println();
            index++;
        }
    }

    // Method to ask for the recurrence of a reminder
    private Recurrence askForRecurrence() {
        System.out.print("Enter the recurrence (NONE, DAILY, WEEKLY, MONTHLY, YEARLY): ");
        String recurrenceStr = scanner.nextLine().toUpperCase();
        return Recurrence.valueOf(recurrenceStr);
    }

    // Method to ask for the priority of a reminder
    private Priority askForPriority() {
        System.out.print("Enter the priority (LOW, MEDIUM, HIGH): ");
        String priorityStr = scanner.nextLine().toUpperCase();
        return Priority.valueOf(priorityStr);
    }

    public static void main(String[] args) {
        ReminderApp app = new ReminderApp();
        app.run();
    }

    // Method to display the main menu and handle user choices
    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println("Reminder App Menu:");
            System.out.println("1. Add Reminder");
            System.out.println("2. View Reminders");
            System.out.println("3. Delete Reminder");
            System.out.println("4. Exit");

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
                    deleteReminder();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 4.");
                    break;
            }
        }
        System.out.println("Exiting Reminder App. Goodbye!");
    }
}
