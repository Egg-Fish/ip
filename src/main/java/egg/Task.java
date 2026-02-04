package egg;

/**
 * Represents a task in the system.
 * A task consists of a description and a completion status.
 */
public abstract class Task {
    /** The textual description of the task. */
    protected String description;
    
    /** The status of the task, where true indicates completion. */
    protected boolean isDone;

    /**
     * Initializes a new Task with the specified description.
     * By default, the task is marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as completed.
     */
    public void mark() {
        isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmark() {
        isDone = false;
    }

    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description; 
    }
}
