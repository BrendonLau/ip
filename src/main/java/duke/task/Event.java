package duke.task;

import duke.common.Const;

import java.time.LocalDateTime;

/**
 * Represents an Event.
 */
public class Event extends Task {
    private LocalDateTime at;

    /**
     * Creates an instance of Event.
     *
     * @param description of Event.
     * @param time        due of the Event.
     */
    public Event(String description, LocalDateTime time) {
        super(description, Task.Type.EVENT);
        this.at = time;
    }

    /**
     * Gets the time due of event in a specific format.
     *
     * @return time due of event.
     */
    public String getAt() {
        return this.at.format(Const.OUT_TIME_FORMATTER);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(at: " + this.at.format(Const.OUT_TIME_FORMATTER) + ")";
    }
}
