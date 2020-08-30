package duke;

/**
 * DukeException stores all duke-specific exceptions.
 */
public class DukeException extends Exception {

    /** Exception type */
    protected DukeExceptionType exceptionType;
    /** Command type */
    protected DukeCommandType commandType;

    /**
     * Initialises DukeException using exception type.
     *
     * @param e
     * @param exceptionType
     */
    public DukeException(String e, DukeExceptionType exceptionType) {
        super(e);
        this.exceptionType = exceptionType;
    }

    /**
     * Initialises DukeException using exception type and command type.
     *
     * @param e
     * @param exceptionType
     * @param commandType
     */
    public DukeException(String e, DukeExceptionType exceptionType, DukeCommandType commandType) {
        super(e);
        this.exceptionType = exceptionType;
        this.commandType = commandType;
    }

    /**
     * Returns error messages depending on the exception type and command type.
     *
     * @return error messages
     */
    @Override
    public String toString() {
        String error = "";
        switch (exceptionType) {
        case EMPTY_LIST:
            error += "    YOUR LIST IS EMPTY :-(\n";
            break;
        case UNKNOWN:
            error += "    I DON'T KNOW WHAT YOU MEAN :-(\n";
            break;
        case NO_MATCHING_FOUND:
            error += "    NO MATCHES FOUND :-(\n";
            break;
        case WRONG_FORMAT:
            switch (commandType) {
            case DEADLINE:
                error += "    ERROR IN ADDING DEADLINE: WRONG FORMAT\n    ";
                error += "Format: deadline <description> /by <datetime>\n";
                break;
            case EVENT:
                error += "    ERROR IN ADDING EVENT: WRONG FORMAT\n    Format: event <description> /at <datetime>\n";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + commandType);
            }
            break;
        case MISSING_DESCRIPTION:
            switch (commandType) {
            case TODO:
                error += "    ERROR IN ADDING TODO: MISSING DESCRIPTION\n";
                break;
            case DEADLINE:
                error += "    ERROR IN ADDING DEADLINE: MISSING DESCRIPTION\n";
                break;
            case EVENT:
                error += "    ERROR IN ADDING EVENT: MISSING DESCRIPTION\n";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + commandType);
            }
            break;
        case MISSING_TIMING:
            switch (commandType) {
            case DEADLINE:
                error += "    ERROR IN ADDING DEADLINE: MISSING DUE DATE\n";
                break;
            case EVENT:
                error += "    ERROR IN ADDING EVENT: MISSING SCHEDULED DATE\n";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + commandType);
            }
            break;
        case INVALID_INDEX:
            switch (commandType) {
            case DONE:
                error += "    ERROR IN MARKING TASK DONE: INVALID INDEX\n";
                break;
            case DELETE:
                error += "    ERROR IN DELETING TASK: INVALID INDEX\n";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + commandType);
            }
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + exceptionType);
        }
        return error;
    }

    /**
     * Returns error in user's input due to wrong time format.
     */
    public static void wrongTimeFormat() {
        System.err.println("    ERROR IN ADDING DEADLINE: WRONG FORMAT\n    Format: YYYY-MM-DD\n");
    }
}