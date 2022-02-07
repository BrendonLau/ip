package duke;

import duke.common.Constant;

/**
 * This class handles errors in Duke.
 */
public class DukeException extends Exception {

    public static class DukeInvalidCommandException extends DukeException {
        @Override
        public String toString() {
            return "What do you want me to do? Valid command only. :O";
        }
    }

    public static class DukeNoDescriptionFoundException extends DukeException {
        @Override
        public String toString() {
            return "What is the description? :(";
        }
    }

    public static class DukeTimeNotFoundException extends DukeException {
        @Override
        public String toString() {
            return "When?????????, I can't read your mind";
        }
    }

    public static class DukeDateTimeFormatException extends DukeException {
        @Override
        public String toString() {
            return "Please use this format " + Constant.INPUT_DATE_TIME_FORMAT;
        }
    }

    public static class DukePastTimeException extends DukeException {
        @Override
        public String toString() {
            return "Please enter a time in the future! We ain't time travellers.";
        }
    }

    public static class DukeTaskNotFoundException extends DukeException {
        @Override
        public String toString() {
            return "please enter a task index from the list.";
        }
    }

    public static class DukeNotAValidNumberException extends DukeException {
        @Override
        public String toString() {
            return "Stop trying to be funny, enter a valid task number from the list only.";
        }
    }

    public static class DukeEmptyTaskException extends DukeException {
        @Override
        public String toString() {
            return "You do not have any task, please add one!";
        }
    }

    public static class DukeIoException extends DukeException {
        @Override
        public String toString() {
            return "There is an error processing the data :(";
        }
    }

    public static class DukeMarkedException extends DukeException {
        @Override
        public String toString() {
            return "You have already marked this task. ;/";
        }
    }

    public static class DukeUnMarkException extends DukeException {
        @Override
        public String toString() {
            return "This task is already not marked. ;/";
        }
    }
}
