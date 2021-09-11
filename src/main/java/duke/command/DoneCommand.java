package duke.command;

import duke.DukeException;
import duke.Storage;
import duke.TaskList;

public class DoneCommand extends Command {
    private int taskIndex;

    /**
     * Constructor for DoneCommand, command to mark the task at the specified index as completed
     *
     * @param taskIndex The index of the task in taskManager to be marked as completed
     */
    public DoneCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Calls the completeTask method in taskManager to mark the specified task as completed
     *
     * @param taskList the taskManager that contains the task to be marked
     * @throws DukeException If there is no task at the specified index
     */
    @Override
    public void execute(TaskList taskList, Storage storage) throws DukeException {
        taskList.completeTask(taskIndex);
        storage.saveData(taskList);
    }
}