package duke;

import duke.command.AddCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.ExitCommand;
import duke.command.ListCommand;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

public class CommandParser {
    private static final String COMMAND_EXIT = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_MARK_DONE = "done";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_NEW_TODO = "todo";
    private static final String COMMAND_NEW_DEADLINE = "deadline";
    private static final String COMMAND_NEW_EVENT = "event";

    /**
     * Takes in the userInput String and returns the corresponding command to be executed
     *
     * @param userInput The String that contains the user's input
     * @return The corresponding Command based on the user's input
     * @throws DukeException If the userInput String is not in the correct format or
     *                       does not correspond to a valid command
     */
    public static Command parse(String userInput) throws DukeException {
        if (userInput.equals(COMMAND_EXIT)) {
            return new ExitCommand();
        }
        if (userInput.equals(COMMAND_LIST)) {
            return new ListCommand();
        }
        if (userInput.startsWith(COMMAND_MARK_DONE)) {
            int taskIndex = -1;
            try {
                taskIndex = Integer.parseInt(userInput.substring(5).strip()) - 1;
            } catch (NumberFormatException e) {
                throw new DukeException("Please provide a valid number.");
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Please specify the index of the task.");
            }
            return new DoneCommand(taskIndex);
        }
        if (userInput.startsWith(COMMAND_DELETE)) {
            int taskIndex = -1;
            try {
                taskIndex = Integer.parseInt(userInput.substring(7).strip()) - 1;
            } catch (NumberFormatException e) {
                throw new DukeException("Please provide a valid number.");
            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Please specify the index of the task.");
            }
            return new DeleteCommand(taskIndex);
        }
        if (userInput.startsWith(COMMAND_NEW_TODO)) {
            Task task = parseTask(userInput, TaskType.TODO);
            return new AddCommand(task);
        }
        if (userInput.startsWith(COMMAND_NEW_DEADLINE)) {
            Task task = parseTask(userInput, TaskType.DEADLINE);
            return new AddCommand(task);
        }
        if (userInput.startsWith(COMMAND_NEW_EVENT)) {
            Task task = parseTask(userInput, TaskType.EVENT);
            return new AddCommand(task);
        }
        throw new DukeException("Sorry, I do not understand your command.");
    }

    /**
     * Takes in the userInput String and returns a Task that is to be added to taskManager
     *
     * @param userInput The String that contains the user's input, must correspond to a
     *                  command that creates a Task
     * @param taskType The Enum TaskType that specifies the Task subclass to be created
     * @return The Task that contains the details specified in userInput
     * @throws DukeException If the userInput String is not in the correct format
     *                       or has missing information
     */
    private static Task parseTask(String userInput, TaskType taskType) throws DukeException {
        switch (taskType) {
        case TODO:
            try {
                String todoName = userInput.substring(5).strip();
                if (todoName.equals("")) {
                    throw new DukeException("Please specify a task name.");
                }
                return new ToDo(todoName);
            } catch (Exception e) {
                throw new DukeException("Please specify a task name.");
            }
        case DEADLINE:
            int byIndex = userInput.indexOf(" /by ");
            if (byIndex == -1) {
                throw new DukeException("Please specify a do by date by using the \" /by \" separator.");
            }
            String deadlineName = userInput.substring(9, byIndex).strip();
            if (deadlineName.equals("")) {
                throw new DukeException("Please specify a deadline name.");
            }
            String deadlineDate = userInput.substring(byIndex + 5).strip();
            if (deadlineDate.equals("")) {
                throw new DukeException("Please specify a do by date.");
            }
            return new Deadline(deadlineName, deadlineDate);
        case EVENT:
            int atIndex = userInput.indexOf(" /at ");
            if (atIndex == -1) {
                throw new DukeException("Please specify an event date by using the \" /at \" separator.");
            }
            String eventName = userInput.substring(6, atIndex).strip();
            if (eventName.equals("")) {
                throw new DukeException("Please specify an event name.");
            }
            String eventDate = userInput.substring(atIndex + 5).strip();
            if (eventDate.equals("")) {
                throw new DukeException("Please specify an event date.");
            }
            return new Event(eventName, eventDate);
        }
        return null;
    }
}
