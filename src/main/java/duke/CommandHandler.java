package duke;

import java.time.format.DateTimeParseException;
import static duke.DukeCommandType.*;

/**
 * CommandHandler class will handle all the related mechanisms initiated by user's command.
 */
public class CommandHandler {
    /** Input's command type */
    protected DukeCommandType commandType;

    /** User's input */
    private String input;

    /**
     * Initialises CommandHandler with user's input and its command type.
     *
     * @param input
     * @param commandType
     */
    public CommandHandler(String input, DukeCommandType commandType) {
        this.input = input;
        this.commandType = commandType;
    }

    /**
     * Handles user's inputs depending on command type.
     *
     * @param input
     * @param commandType
     * @param tasks
     * @throws DukeException
     */
    public static String handleCommands(String input, DukeCommandType commandType, TaskList tasks) throws DukeException {
        String task;
        String output = "";
        switch (commandType) {
        case TODO:
            try {
                task = input.split("todo ")[1];
                Task newTask = new ToDos(task);
                output += tasks.addTask(newTask);
            } catch (ArrayIndexOutOfBoundsException exception) {
                try {
                    throw new DukeException("", DukeExceptionType.MISSING_DESCRIPTION, TODO);
                } catch (DukeException e) {
                    output += e;
                }
            }
            break;
        case DEADLINE:
            try {
                if (input.split("deadline ").length < 2) {
                    throw new DukeException("", DukeExceptionType.WRONG_FORMAT, DEADLINE);
                } else if (input.contains("/at")) {
                    throw new DukeException("", DukeExceptionType.WRONG_FORMAT, DEADLINE);
                } else if (!input.contains("/by ")) {
                    if (input.equals("deadline /by")) {
                        throw new DukeException("", DukeExceptionType.WRONG_FORMAT, DEADLINE);
                    } else {
                        throw new DukeException("", DukeExceptionType.MISSING_TIMING, DEADLINE);
                    }
                } else if (input.split("/by ").length < 2) {
                    throw new DukeException("", DukeExceptionType.WRONG_FORMAT, DEADLINE);
                } else {
                    try {
                        task = input.split("deadline ")[1].split("/by ")[0];
                        String due = input.split("deadline ")[1].split("/by ")[1];
                        if (task.equals("") && due.equals("")) {
                            throw new DukeException("", DukeExceptionType.WRONG_FORMAT, DEADLINE);
                        } else if (task.equals("")) {
                            throw new DukeException("", DukeExceptionType.MISSING_DESCRIPTION, DEADLINE);
                        } else if (due.equals("")) {
                            throw new DukeException("", DukeExceptionType.MISSING_TIMING, DEADLINE);
                        } else {
                            try {
                                Task newTask = new Deadlines(task, due);
                                output += tasks.addTask(newTask);
                            } catch (DateTimeParseException e) {
                                DukeException.wrongTimeFormat();
                            }
                        }
                    } catch (DukeException e) {
                        output += e;
                    }
                }
            } catch (DukeException e) {
                output += e;
            }
            break;
        case EVENT:
            try {
                if (input.split("event ").length < 2) {
                    throw new DukeException("", DukeExceptionType.WRONG_FORMAT, EVENT);
                } else if (input.contains("/by")) {
                    throw new DukeException("", DukeExceptionType.WRONG_FORMAT, EVENT);
                } else if (!input.contains("/at ")) {
                    if (input.equals("event /at")) {
                        throw new DukeException("", DukeExceptionType.WRONG_FORMAT, EVENT);
                    } else {
                        throw new DukeException("", DukeExceptionType.MISSING_TIMING, EVENT);
                    }
                } else if (input.split("/at ").length < 2) {
                    throw new DukeException("", DukeExceptionType.WRONG_FORMAT, EVENT);
                } else {
                    try {
                        task = input.split("event ")[1].split("/at ")[0];
                        String due = input.split("event ")[1].split("/at ")[1];
                        if (task.equals("") && due.equals("")) {
                            throw new DukeException("", DukeExceptionType.WRONG_FORMAT, EVENT);
                        } else if (task.equals("")) {
                            throw new DukeException("", DukeExceptionType.MISSING_DESCRIPTION, DEADLINE);
                        } else if (due.equals("")) {
                            throw new DukeException("", DukeExceptionType.MISSING_TIMING, DEADLINE);
                        } else {
                            Task newTask = new Events(task, due);
                            output += tasks.addTask(newTask);
                        }
                    } catch (DukeException e) {
                        output += e;
                    } catch (DateTimeParseException e) {
                        DukeException.wrongTimeFormat();
                    }
                }
            } catch (DukeException e) {
                output += e;
            }
            break;
        case LIST:
            output += tasks.getListOfTasks();
            break;
        case FIND:
            String keyword = input.split(" ")[1];
            System.out.println(keyword);
            output += tasks.findTasks(keyword);
            break;
        case DONE:
            try {
                int index = Integer.parseInt(input.split(" ")[1]);
                output += tasks.done(index);
            } catch (IndexOutOfBoundsException exception) {
                try {
                    throw new DukeException("", DukeExceptionType.INVALID_INDEX, DONE);
                } catch (DukeException e) {
                    output += e;
                }
            }
            break;
        case DELETE:
            try {
                int index = Integer.parseInt(input.split(" ")[1]);
                output += tasks.delete(index);
            } catch (IndexOutOfBoundsException exception) {
                try {
                    throw new DukeException("", DukeExceptionType.INVALID_INDEX, DELETE);
                } catch (DukeException e) {
                    output += e;
                }
            }
            break;
        case HELP:
            output += Ui.getListOfCommands();
            break;
        case EXIT:
            output += Ui.exit();
            break;
        default:
            try {
                throw new DukeException("", DukeExceptionType.UNKNOWN);
            } catch (DukeException e) {
                output += e;
            }
            break;
        }
        return output;
    }
}