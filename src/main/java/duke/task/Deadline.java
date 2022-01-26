package duke.task;

import duke.common.Const;

import java.time.LocalDateTime;

/**
 * Represents a Deadline.
 */
public class Deadline extends Task {
    private LocalDateTime by;

    /**
     * Creates an instance of deadline.
     *
     * @param description of deadline.
     * @param time        due of the deadline.
     */
    public Deadline(String description, LocalDateTime time) {
        super(description, Task.Type.DEADLINE);
        this.by = time;
    }

    /**
     * Gets the time due of deadline in a specific format.
     *
     * @return time due of deadline.
     */
    public String getBy() {
        return this.by.format(Const.OUT_TIME_FORMATTER);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + this.by.format(Const.OUT_TIME_FORMATTER) + ")";
    }
}
