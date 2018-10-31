package seedu.address.logic.parser.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser.
 */
public class ParseException extends IllegalValueException {
    private boolean isFormatString = false;

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, boolean isFormatString) {
        super(message);
        this.isFormatString = isFormatString;
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(String message, Throwable cause, boolean isFormatString) {
        super(message, cause);
        this.isFormatString = isFormatString;
    }

    @Override
    public String getMessage() {
        return getMessage("");
    }

    public String getMessage(String moduleName) {
        String res = super.getMessage();
        return isFormatString ? String.format(res, moduleName) : res;
    }
}
