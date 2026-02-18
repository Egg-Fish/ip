package egg;

import egg.task.Task;
import egg.task.TaskList;

public abstract class Ui {
    public abstract void printMessage(String message);
    public abstract void printTaskAddedMessage(TaskList tasks, Task addedTask);
    public abstract void printTaskDeletedMessage(TaskList tasks, Task deletedTask);
    public abstract void printTaskList(TaskList tasks, String message);
}
