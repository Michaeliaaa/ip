package duke;

import java.util.Scanner;

/**
 * Ui class will handle the interactions with the user.
 */
public class Ui {

    /** Parser for parsing user's inputs */
    protected Parser parser;

    /** Boolean to determine if Duke should stop running */
    private boolean isExit = false;

    /**
     * Constructs a new Ui object.
     * @param parser the parser that deals with user input
     */
    public Ui(Parser parser) {
        this.parser = parser;
    }

    /**
     * Determines whether or not Duke should stop running.
     * @return true if Duke should stop running
     */
    public boolean isExit() {
        return this.isExit;
    }

    /**
     * Prints logo and greets the user.
     */
    public static String greeting() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String greet = "\n____________________________________________________________"
                + "\n Hello! I'm Duke"
                + "\n What can I do for you?"
                + "\n____________________________________________________________\n";
        String str = logo + greet;
        return str;
    }

    /**
     * Prints a list of commands which the user can use.
     */
    public static String getListOfCommands() {
        String commands = "   ____________________________________________________________"
                + "\n    Here are all your commands:"
                + "\n     list - show all tasks"
                + "\n     todo <your task> - add task"
                + "\n     deadline <your task> /by <your deadline> - add task with deadline"
                + "\n     event <your event> /at <event's timing> - add event"
                + "\n     done <index of task> - mark task as done"
                + "\n     delete <index of task> - delete task from list"
                + "\n   ____________________________________________________________\n";
        System.out.println(commands);
        return commands;
    }

    /**
     * Prints bye and quit the bot.
     */
    public static String exit() {
        String bye = "   ____________________________________________________________"
                + "\n    Bye! Hope to see you again soon."
                + "\n   ____________________________________________________________";
        System.out.println(bye);
        return bye;
    }

    /**
     * Prints error in loading the data from the file.
     */
    public static String showLoadingError() {
        return "    ERROR IN LOADING YOUR DATA :-(\n";
    }

    /**
     * Reads the user's inputs and then passes them to parser for parsing.
     */
    public void readInput() throws DukeException {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                this.isExit = true;
                exit();
                sc.close();
                break;
            } else {
                String reply = parser.parseInputs(input);
                System.out.println(reply);
            }
        }
    }
}