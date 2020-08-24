import java.time.format.DateTimeParseException;

public class CommandHandler {
    String input;
    protected DukeCommandType commandType;

    public CommandHandler(String input, DukeCommandType commandType) {
        this.input = input;
        this.commandType = commandType;
    }

    public static void commandHandler(String input, DukeCommandType commandType, TaskList tasks) throws DukeException {
        String str = "";
        String task;
        switch (commandType) {
        case TODO:
            try {
                task = input.split("todo ")[1];
                Task newTask = new ToDos(task);
                tasks.addTask(newTask);
            } catch (ArrayIndexOutOfBoundsException exception) {
                try {
                    throw new DukeException("", DukeExceptionType.MISSING_DESCRIPTION, DukeCommandType.TODO);
                } catch (DukeException e) {
                    System.err.println(e);
                }
            }
            break;
        case DEADLINE:
            try {
                if (input.split("deadline ").length < 2) {
                    throw new DukeException("", DukeExceptionType.WRONG_FORMAT, DukeCommandType.DEADLINE);
                } else if (input.contains("/at")) {
                    throw new DukeException("", DukeExceptionType.WRONG_FORMAT, DukeCommandType.DEADLINE);
                } else if (!input.contains("/by ")) {
                    if (input.equals("deadline /by")) {
                        throw new DukeException("", DukeExceptionType.WRONG_FORMAT, DukeCommandType.DEADLINE);
                    } else {
                        throw new DukeException("", DukeExceptionType.MISSING_TIMING, DukeCommandType.DEADLINE);
                    }
                } else if (input.split("/by ").length < 2) {
                    throw new DukeException("", DukeExceptionType.WRONG_FORMAT, DukeCommandType.DEADLINE);
                } else {
                    try {
                        task = input.split("deadline ")[1].split("/by ")[0];
                        String due = input.split("deadline ")[1].split("/by ")[1];
                        if (task.equals("") && due.equals("")) {
                            throw new DukeException("", DukeExceptionType.WRONG_FORMAT, DukeCommandType.DEADLINE);
                        } else if (task.equals("")) {
                            throw new DukeException("", DukeExceptionType.MISSING_DESCRIPTION, DukeCommandType.DEADLINE);
                        } else if (due.equals("")) {
                            throw new DukeException("", DukeExceptionType.MISSING_TIMING, DukeCommandType.DEADLINE);
                        } else {
                            try {
                                Task newTask = new Deadlines(task, due);
                                tasks.addTask(newTask);
                            } catch (DateTimeParseException e) {
                                DukeException.wrongTimeFormat();
                            }
                        }
                    } catch (DukeException e){
                        System.err.println(e);
                    }
                }
            } catch (DukeException e) {
                System.err.println(e);
            }
            break;
        case EVENT:
            try {
                if (input.split("event ").length < 2) {
                    throw new DukeException("", DukeExceptionType.WRONG_FORMAT, DukeCommandType.EVENT);
                } else if (input.contains("/by")) {
                    throw new DukeException("", DukeExceptionType.WRONG_FORMAT, DukeCommandType.EVENT);
                } else if (!input.contains("/at ")) {
                    if (input.equals("event /at")) {
                        throw new DukeException("", DukeExceptionType.WRONG_FORMAT, DukeCommandType.EVENT);
                    } else {
                        throw new DukeException("", DukeExceptionType.MISSING_TIMING, DukeCommandType.EVENT);
                    }
                } else if (input.split("/at ").length < 2) {
                    throw new DukeException("", DukeExceptionType.WRONG_FORMAT, DukeCommandType.EVENT);
                } else {
                    try {
                        task = input.split("event ")[1].split("/at ")[0];
                        String due = input.split("event ")[1].split("/at ")[1];
                        if (task.equals("") && due.equals("")) {
                            throw new DukeException("", DukeExceptionType.WRONG_FORMAT, DukeCommandType.EVENT);
                        } else if (task.equals("")) {
                            throw new DukeException("", DukeExceptionType.MISSING_DESCRIPTION, DukeCommandType.DEADLINE);
                        } else if (due.equals("")) {
                            throw new DukeException("", DukeExceptionType.MISSING_TIMING, DukeCommandType.DEADLINE);
                        } else {
                            Task newTask = new Events(task, due);
                            tasks.addTask(newTask);
                        }
                    } catch (DukeException e){
                        System.err.println(e);
                    } catch (DateTimeParseException e) {
                        DukeException.wrongTimeFormat();
                    }
                }
            } catch (DukeException e) {
                System.err.println(e);
            }
            break;
        case LIST:
            tasks.getListOfTasks();
            break;
        case DONE:
            try {
                int index = Integer.parseInt(input.split(" ")[1]);
                tasks.done(index);
            } catch (IndexOutOfBoundsException exception) {
                try {
                    throw new DukeException("", DukeExceptionType.INVALID_INDEX, DukeCommandType.DONE);
                } catch (DukeException e) {
                    System.err.println(e);
                }
            }
            break;
        case DELETE:
            try {
                int index = Integer.parseInt(input.split(" ")[1]);
                tasks.delete(index);
            } catch (IndexOutOfBoundsException exception) {
                try {
                    throw new DukeException("", DukeExceptionType.INVALID_INDEX, DukeCommandType.DELETE);
                } catch (DukeException e) {
                    System.err.println(e);
                }
            }
            break;
        case HELP:
            Ui.getListOfCommands();
            break;
        case UNKNOWN:
            try {
                throw new DukeException("", DukeExceptionType.UNKNOWN);
            } catch (DukeException e) {
                System.err.println(e);
            }
            break;
        case EXIT:
            Ui.exit();
        }
    }
}
