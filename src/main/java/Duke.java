import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Duke() {
        ui = new Ui();
        storage = new Storage();
        tasks = new TaskList();
        try {
            storage.load(tasks);
        } catch (Exception e){
            Ui.showLoadingError();
        }
    }

    public void run() throws DukeException {
        Scanner sc = new Scanner(System.in);
        Ui.greeting();
        String input = sc.nextLine();
        while (!input.isEmpty()) {
            if (input.equals("bye")) {
                Ui.exit();
                break;
            } else if (input.equals("help")) {
                Ui.getListOfCommands();
            } else if (input.equals("list")) {
                tasks.getListOfTasks();
            } else if (input.startsWith("todo")) {
                String task;
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
            } else if (input.startsWith("deadline")) {
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
                            String task = input.split("deadline ")[1].split("/by ")[0];
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
            } else if (input.startsWith("event")) {
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
                            String task = input.split("event ")[1].split("/at ")[0];
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
            } else if (input.startsWith("done")) {
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
                input = sc.nextLine();
                continue;
            } else if (input.startsWith("delete")) {
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
                input = sc.nextLine();
                continue;
            } else {
                try {
                    throw new DukeException("", DukeExceptionType.UNKNOWN);
                } catch (DukeException e) {
                    System.err.println(e);
                }
                input = sc.nextLine();
                continue;
            }
            input = sc.nextLine();
        }
        storage.save(TaskList.tasks);
    }

    public static void main(String[] args) throws DukeException {
        new Duke().run();
    }
}
