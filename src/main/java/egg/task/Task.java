package egg.task;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a task in the system.
 * A task consists of a description and a completion status.
 */
public class Task {
    private static Pattern tagPattern =
        Pattern.compile("#([^ ]+)");

    /** The textual description of the task. */
    protected String description;

    /** The status of the task, where true indicates completion. */
    protected boolean isDone;

    /** The tags associated with the task. */
    protected HashSet<String> tags;

    /**
     * Initializes a new Task with the specified description.
     * By default, the task is marked as not done, and has
     * no tags.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.tags = new HashSet<String>();
    }

    public String getDescription() {
        return description;
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

    /**
     * Adds a tag to the task.
     */
    public void tag(String tag) {
        this.tags.add(tag);
    }

    /**
     * Removes a tag from the task.
     */
    public void untag(String tag) {
        this.tags.remove(tag);
    }

    public String getTagString() {
        String tagString = "";
        for (String tag : tags) {
            tagString += "#" + tag + " ";
        }

        return tagString.trim();
    }

    public void addTagsFromString(String tagString) {
        Matcher matcher = tagPattern.matcher(tagString);

        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                this.tags.add(matcher.group(i));
            }
        }
    }

    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }
}
