import java.util.ArrayList;
import java.util.Scanner;

public class Monke {
    private enum Command {
        LIST,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE
    }

    private static ArrayList<Task> list = new ArrayList<>();

    public static void speak(String msg) {
        System.out.println("\t" + msg);
    }
    public static void greet() {
        Monke.speak("Hello, I'm Monke. OOGA BOOGA!");
        Monke.speak("What can I do for you?");
        Monke.printHorizontalLine();
    }

    public static void exit() {
        Monke.speak("Bye. Hope to see you again soon! OOGA BOOGA!");
        Monke.printHorizontalLine();
    }

    public static void printHorizontalLine() {
        for (int i = 0; i < 100; i++) {
            System.out.print("_");
        }
        System.out.println();
    }

    public static void execute(String command, String args) throws MonkeException{
        Command cmd;
        try {
            cmd = Command.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new MonkeException("OOGA??!! I'm sorry, but I don't know what that means :-(");
        }
        switch (cmd) {
            case LIST: {
                Monke.displayList();
                break;
            }
            case MARK: {
                int n = Monke.getListNumber(args);
                Task task = Monke.list.get(n - 1);
                task.mark();
                Monke.speak("Ooga booga! I've marked this task as done:");
                Monke.speak("\t" + task);
                break;
            }
            case UNMARK: {
                int n = Monke.getListNumber(args);
                Task task = Monke.list.get(n - 1);
                task.unmark();
                Monke.speak("Ooga booga! I've marked this task as done:");
                Monke.speak("\t" + task);
                break;
            }
            case TODO: {
                Todo todo = Monke.getTodo(args);
                Monke.addToList(todo);
                break;
            }
            case DEADLINE: {
                Deadline deadline = Monke.getDeadline(args);
                Monke.addToList(deadline);
                break;
            }
            case EVENT: {
                Event event = Monke.getEvent(args);
                Monke.addToList(event);
                break;
            }
            case DELETE: {
                int n = getListNumber(args);
                Monke.deleteFromList(n);
                break;
            }
            default: {
                throw new MonkeException("OOGA??!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }

    public static int getListNumber(String args) throws MonkeException {
        try {
            int n = Integer.parseInt(args);
            if (n > Monke.list.size()) {
                throw new MonkeException("OOGA BOOGA!! Your number is out of range. :(");
            }
            return n;
        } catch (NumberFormatException e) {
            throw new MonkeException("OOGA BOOGA!! You must provide a number from the list. :(");
        }
    }

    public static Todo getTodo(String args) throws MonkeException {
        if (args.isBlank()) {
            throw new MonkeException("OOGA BOOGA!! The description of a todo cannot be empty.");
        }
        return new Todo(args);
    }

    public static Deadline getDeadline(String args) throws MonkeException {
        String[] tmp = args.split(" /by ", 2);
        if (tmp.length < 2 || tmp[0].isBlank() || tmp[1].isBlank()) {
            throw new MonkeException("You must format your deadline like this:\n" +
                    "\t\tdeadline <task> /by <deadline>");
        }
        String description = tmp[0];
        String date = tmp[1];
        return new Deadline(description, date);
    }

    public static Event getEvent(String args) throws MonkeException {
        String[] tmp = args.split(" /from ", 2);
        String description = tmp[0];
        if (tmp.length < 2 || tmp[0].isBlank() || tmp[1].isBlank()) {
            throw new MonkeException("You must format your event like this:\n" +
                    "\t\tdeadline <task> /from <start time> /to <end time>");
        }
        String[] tmp2 = tmp[1].split(" /to ", 2);
        if (tmp2.length < 2 || tmp2[0].isBlank() || tmp2[1].isBlank()) {
            throw new MonkeException("You must format your event like this:\n" +
                    "\t\tdeadline <task> /from <start time> /to <end time>");
        }
        String start = tmp2[0];
        String end = tmp2[1];
        return new Event(description, start, end);
    }

    public static void addToList(Task task) {
        Monke.speak("Got it. I've added this task:");
        Monke.speak("\t" + task);
        Monke.list.add(task);
        Monke.speak("Now you have " + Monke.list.size() + " tasks in the list.");
    }

    public static void deleteFromList(int n) {
        Task task = Monke.list.get(n - 1);
        Monke.speak("Noted. I've removed this task:");
        Monke.speak("\t" + task);
        Monke.list.remove(n - 1);
        Monke.speak("Now you have " + Monke.list.size() + " tasks in the list.");
    }

    public static void displayList() {
        for (int i = 0; i < Monke.list.size(); i++) {
            Monke.speak((i + 1) + ". " + Monke.list.get(i));
        }
    }

    public static void main(String[] args) {
        Monke.printHorizontalLine();
        Monke.greet();

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            String[] temp = input.split(" ", 2);
            String command = temp[0];
            String commandArgs = temp.length > 1 ? temp[1] : "";
            Monke.printHorizontalLine();

            if (command.equals("bye")) {
                break;
            }

            try {
                Monke.execute(command, commandArgs);
            } catch (MonkeException e) {
                Monke.speak(e.getMessage());
            }

            Monke.printHorizontalLine();
        }
        sc.close();

        Monke.exit();
    }
}
