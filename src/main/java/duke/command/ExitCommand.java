package duke.command;

import duke.Storage;
import duke.TaskList;

public class ExitCommand extends Command {

    /**
     * Returns true as the Command type is exitCommand
     *
     * @return true
     */
    @Override
    public boolean isExit() {
        return true;
    }

    /**
     * Does nothing
     *
     * @param taskList Does not matter in this subclass implementation
     */
    @Override
    public void execute(TaskList taskList, Storage storage) {
    }
}