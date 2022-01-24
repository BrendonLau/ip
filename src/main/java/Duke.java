import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Duke {
    private static final String INPUT_NAME = "You:";
    private static final String OUTPUT_NAME = "Duke:";
    private static TaskManager notebook = new TaskManager();
    protected static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    public static void main(String[] args) {
        start();
        Scanner sc = new Scanner(System.in);
        String input;

        do {
            System.out.println(INPUT_NAME);
            input = sc.nextLine();
            String output = OUTPUT_NAME + "\n";

            try {
                String commandLine[] = cleanInput(input);
                Command c = Command.getCommand(commandLine[0]);
                if (c == null) throw new DukeException.DukeInvalidCommandException();

                // main
                switch (c) {
                case BYE:
                    notebook.save();
                    output += "don't leave me don't leave me.";
                    break;
                case LIST:
                    output += "Here are the tasks in your list-\n" + notebook;
                    break;
                case MARK:
                    output += "I have marked this as done.\n";
                    output += notebook.markTask(Integer.parseInt(commandLine[1])) + "\n";
                    break;
                case UNMARK:
                    output += "I have unmarked this task.\n";
                    output += notebook.unmarkTask(Integer.parseInt(commandLine[1])) + "\n";
                    break;
                case TODO:
                    output += "Got it. I have added this task-\n";
                    output += notebook.addToDo(commandLine[1]) + "\n";
                    output += "Now you have " + notebook.size() + " tasks.\n";
                    break;
                case DEADLINE:
                    output += "Got it. I have added this task-\n";
                    LocalDateTime dateTime = LocalDateTime.parse(commandLine[2], timeFormatter);
                    output += notebook.addDeadline(commandLine[1],
                            dateTime.format(DateTimeFormatter.ofPattern("MMM d yyyy HH:mm"))) + "\n";
                    output += "Now you have " + notebook.size() + " tasks.\n";
                    break;
                case EVENT:
                    output += "Got it. I have added this task-\n";
                    LocalDateTime dateTime2 = LocalDateTime.parse(commandLine[2], timeFormatter);
                    output += notebook.addEvent(commandLine[1],
                            dateTime2.format(DateTimeFormatter.ofPattern("MMM d yyyy HH:mm")))+ "\n";
                    output += "Now you have " + notebook.size() + " tasks.\n";
                    break;
                case DELETE:
                    output += "Noted. I've removed this task-\n";
                    output += notebook.delete(Integer.parseInt(commandLine[1])) + "\n";
                    output += "Now you have " + notebook.size() + " tasks.\n";
                    break;
                }
                System.out.println(output);
            } catch (DukeException e) {
                System.out.println(OUTPUT_NAME + "\n" + e + "\n");
            }
        } while (!input.equals("bye"));
        sc.close();
    }

    public static String[] cleanInput(String input) throws DukeException {
        String[] commandLine = input.trim().split("\\s+", 2);
        String commandType = commandLine[0];
        boolean commandWithNo = commandType.equals("mark") || commandType.equals("unmark") ||
                commandType.equals("delete");

        // check if command exist.
        if (!Command.isValid(commandType)) throw new DukeException.DukeInvalidCommandException();

        // commands that requires a description except list.
        if (commandLine.length == 1) {
            if ((commandType.equals("todo")) || commandType.equals("deadline") ||
                    commandType.equals("event")) {
                throw new DukeException.DukeNoDescriptionFoundException();
            }
            if (commandWithNo) {
                throw new DukeException.DukeTaskNotFoundException();
            }
            // check if notebook is empty
            if (commandType.equals("list") & notebook.size() == 0) {
                throw new DukeException.DukeEmptyTaskException();
            }
        }

        if (commandLine.length == 2 && !commandType.equals("todo")) {
            String commandInfo = commandLine[1];
            String[] essentialInfo = new String[0];

            if (commandWithNo) {
                // check if task number is valid/
                if (!Pattern.matches("^\\d*[1-9]\\d*$", commandInfo)) {
                    throw new DukeException.DukeNotAValidNumberException();
                }
                int idx = Integer.parseInt(commandInfo);
                if (notebook.size() == 0) {
                    throw new DukeException.DukeEmptyTaskException();
                }
                if (idx > notebook.size()) {
                    throw new DukeException.DukeTaskNotFoundException();
                }
                return commandLine;
            }

            if (commandType.equals("deadline")) {
                if (!commandInfo.contains("/by")) throw new DukeException.DukeInvalidCommandException();
                // check if there is a description
                if (commandInfo.substring(0, commandInfo.indexOf("/by")).length() == 0) {
                    throw new DukeException.DukeNoDescriptionFoundException();
                }
                // check if there is a time
                if (commandInfo.substring(commandInfo.indexOf("/by") + 3).length() == 0) {
                    throw new DukeException.DukeTimeNotFoundException();
                }
                essentialInfo = commandInfo.split("/by");
            }

            if (commandType.equals("event")) {
                if (!commandInfo.contains("/at")) throw new DukeException.DukeInvalidCommandException();
                // check if there is a description
                if (commandInfo.substring(0, commandInfo.indexOf("/at")).length() == 0) {
                    throw new DukeException.DukeNoDescriptionFoundException();
                }
                // check if there is a time
                if (commandInfo.substring(commandInfo.indexOf("/at") + 3).length() == 0) {
                    throw new DukeException.DukeTimeNotFoundException();
                }
                essentialInfo = commandInfo.split("/at");
            }

            try {
                LocalDateTime.parse(essentialInfo[1].trim(), timeFormatter);
            } catch(DateTimeParseException e) {
                throw new DukeException.DukeDateTimeFormatException();
            }

            return new String[]{commandType, essentialInfo[0].trim(), essentialInfo[1].trim()};
        }
        return commandLine;
    }


    public static void start() {
        String logo = " ____        _\n"
                + "|  _ \\ _   _| | _____\n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        String msg = "Hello! I am Duke.\n"
                + "Your wish is my command.\n\n";
        System.out.print(logo + msg);
    }
}
