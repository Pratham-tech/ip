package duke;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all interaction with the user: reading input and printing
 * messages to the console.
 */
public class Ui {
    private final Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    public String readCommand() {
        return sc.nextLine();
    }

    public void close() {
        sc.close();
    }

    public void showGreeting() {
        System.out.println("Hello! I'm Pratbot.\nWhat can I do for you?");
    }

    public void showFarewell() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public void showStatusChange(String heading, Task t) {
        System.out.println(heading);
        System.out.println("  " + t);
    }

    public void showAddedTask(Task t, int totalTasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + t);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    public void showDeletedTask(Task t, int totalTasks) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + t);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    public void showError(String message) {
        System.out.println("OOPS!!! " + message);
    }

    public void showLoadingError() {
        System.out.println("OOPS!!! Could not load saved tasks; starting with an empty list.");
    }

    public void showMatchingTasks(ArrayList<Task> matches) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + "." + matches.get(i));
        }
    }
}