package duke;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final String DATA_FILE_PATH = "data" + File.separator + "duke.txt";

    public static void main(String[] args) {
        printGreeting();
        Storage storage = new Storage(DATA_FILE_PATH);
        ArrayList<Task> tasks = storage.load();
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        while (!input.equals("bye")) {
            try {
                runCommand(input, tasks);
                storage.save(tasks);
            } catch (DukeExceptions e) {
                printError(e.getMessage());
            } catch (NumberFormatException e) {
                printError("Please provide a valid task number.");
            } catch (IndexOutOfBoundsException e) {
                printError("That task number doesn't exist.");
            }
            input = sc.nextLine();
        }
        printFarewell();
        sc.close();
    }

    private static void runCommand(String input, ArrayList<Task> tasks) throws DukeExceptions {
        if (input.equals("list")) {
            printTaskList(tasks);
        } else if (input.startsWith("mark ")) {
            Task t = getTaskByCommand(tasks, input, "mark ");
            t.markAsDone();
            printStatusChange("Nice! I've marked this task as done:", t);
        } else if (input.startsWith("unmark ")) {
            Task t = getTaskByCommand(tasks, input, "unmark ");
            t.unmarkAsDone();
            printStatusChange("OK, I've marked this task as not done yet:", t);
        } else if (input.equals("todo") || input.startsWith("todo ")) {
            addTask(tasks, parseTodo(input));
        } else if (input.equals("deadline") || input.startsWith("deadline ")) {
            addTask(tasks, parseDeadline(input));
        } else if (input.equals("event") || input.startsWith("event ")) {
            addTask(tasks, parseEvent(input));
        } else {
            throw new DukeExceptions("I'm sorry, but I don't know what that means :-(");
        }
    }

    private static Todo parseTodo(String input) throws DukeExceptions {
        String description = input.length() > 4 ? input.substring(5).trim() : "";
        if (description.isEmpty()) {
            throw new DukeExceptions("The description of a todo cannot be empty.");
        }
        return new Todo(description);
    }

    private static Deadline parseDeadline(String input) throws DukeExceptions {
        String remainder = input.length() > 8 ? input.substring(9).trim() : "";
        if (remainder.isEmpty()) {
            throw new DukeExceptions("The description of a deadline cannot be empty.");
        }
        String[] parts = remainder.split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new DukeExceptions("A deadline needs a description and a '/by' date, "
                    + "e.g. deadline return book /by Sunday");
        }
        return new Deadline(parts[0].trim(), parts[1].trim());
    }

    private static Event parseEvent(String input) throws DukeExceptions {
        String remainder = input.length() > 5 ? input.substring(6).trim() : "";
        if (remainder.isEmpty()) {
            throw new DukeExceptions("The description of an event cannot be empty.");
        }
        String[] fromSplit = remainder.split(" /from ", 2);
        if (fromSplit.length < 2 || fromSplit[0].trim().isEmpty()) {
            throw new DukeExceptions("An event needs a '/from' time, "
                    + "e.g. event meeting /from Mon 2pm /to 4pm");
        }
        String[] toSplit = fromSplit[1].split(" /to ", 2);
        if (toSplit.length < 2 || toSplit[0].trim().isEmpty() || toSplit[1].trim().isEmpty()) {
            throw new DukeExceptions("An event needs a '/to' time, "
                    + "e.g. event meeting /from Mon 2pm /to 4pm");
        }
        return new Event(fromSplit[0].trim(), toSplit[0].trim(), toSplit[1].trim());
    }

    private static Task getTaskByCommand(ArrayList<Task> tasks, String input, String prefix) {
        int taskNumber = Integer.parseInt(input.substring(prefix.length()).trim());
        return tasks.get(taskNumber - 1);
    }

    private static void addTask(ArrayList<Task> tasks, Task t) {
        tasks.add(t);
        printAddedTask(t, tasks.size());
    }

    /** Prints the opening greeting shown when Duke starts. */
    private static void printGreeting() {
        System.out.println("Hello! I'm Pratbot.\nWhat can I do for you?");
    }

    /** Prints the closing message shown when the user types "bye". */
    private static void printFarewell() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /** Prints the numbered task list. */
    private static void printTaskList(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /** Prints the confirmation shown after a task is marked or unmarked. */
    private static void printStatusChange(String heading, Task t) {
        System.out.println(heading);
        System.out.println("  " + t);
    }

    /** Prints the confirmation shown after a task is added. */
    private static void printAddedTask(Task t, int totalTasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + t);
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /** Prints an error message to the user, prefixed consistently. */
    private static void printError(String message) {
        System.out.println("OOPS!!! " + message);
    }
}