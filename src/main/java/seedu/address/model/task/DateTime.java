package seedu.address.model.task;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.stream.IntStream;

/**
 * Represents a Task's start or end datetime in the address book.
 * Guarantees: is valid as declared in {@link #isValidDateTimeFormat(String, String)}
 * and {@link #isValidDateTimeValues(String, String)}.
 */
public class DateTime implements Comparable<DateTime> {

    public static final DateFormat INPUT_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    public static final DateFormat INPUT_TIME_FORMAT = new SimpleDateFormat("HHmm");
    public static final DateFormat INPUT_DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");
    public static final DateFormat OUTPUT_DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy");
    public static final DateFormat OUTPUT_TIME_FORMAT = new SimpleDateFormat("HH:mm");

    public static final String MESSAGE_DATETIME_FORMAT_CONSTRAINTS =
            "The date string must be 8 digits long and the time string must be 4 digits long.";
    public static final String MESSAGE_DATETIME_VALUE_CONSTRAINTS =
            "The date must be a valid calendar date and the time must be from 0000-2359 inclusive.";

    /*
     * Date string must be 8 digits long.
     */
    public static final String DATE_VALIDATION_REGEX = "\\d{8}";

    /*
     * Time string must be 4 digits long.
     */
    public static final String TIME_VALIDATION_REGEX = "\\d{4}";

    public final Calendar calendar;

    /**
     * Constructs a {@code DateTime} from input.
     *
     * @param date a valid date string.
     * @param time a valid time string.
     */
    public DateTime(String date, String time) {
        requireAllNonNull(date, time);
        checkArgument(isValidDateTimeFormat(date, time), MESSAGE_DATETIME_FORMAT_CONSTRAINTS);
        checkArgument(isValidDateTimeValues(date, time), MESSAGE_DATETIME_VALUE_CONSTRAINTS);
        this.calendar = createCalendar(date, time);
    }

    /**
     * Constructs a {@code DateTime} from storage.
     *
     * @param dateTime a valid calendar instance.
     */
    public DateTime(Calendar dateTime) {
        requireAllNonNull(dateTime);
        // TODO: check dateTime validity
        this.calendar = dateTime;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public String getDate() {
        return OUTPUT_DATE_FORMAT.format(calendar.getTime());
    }

    public String getTime() {
        return OUTPUT_TIME_FORMAT.format(calendar.getTime());
    }

    public String getInputDate() {
        return INPUT_DATE_FORMAT.format(calendar.getTime());
    }

    public String getInputTime() {
        return INPUT_TIME_FORMAT.format(calendar.getTime());
    }

    /**
     * Returns true if the given strings are of the correct format.
     */
    public static boolean isValidDateTimeFormat(String date, String time) {
        return date.matches(DATE_VALIDATION_REGEX) && time.matches(TIME_VALIDATION_REGEX);
    }

    /**
     * Returns true if the given strings are a valid date and time.
     */
    public static boolean isValidDateTimeValues(String date, String time) {
        int[] dateArray = splitDate(date);
        int[] timeArray = splitTime(time);
        int year = dateArray[0];
        int month = dateArray[1];
        int day = dateArray[2];
        int hour = timeArray[0];
        int minute = timeArray[1];
        return isValidDate(year, month, day) && isValidTime(hour, minute);
    }

    /**
     * Returns true if the given year, date and month are valid.
     */
    private static boolean isValidDate(int year, int month, int day) {
        if (month < 1 || month > 12
            || day < 1 || day > 31) {
            return false;
        }

        int[] longMonths = {1, 3, 5, 7, 8, 10, 12};
        int[] shortMonths = {4, 6, 9, 11};

        int maxDay;

        if (IntStream.of(longMonths).anyMatch(x -> x == month)) {
            maxDay = 31;
        } else if (IntStream.of(shortMonths).anyMatch(x -> x == month)) {
            maxDay = 30;
        } else if (isLeapYear(year)) {
            maxDay = 29;
        } else {
            maxDay = 28;
        }

        return day <= maxDay;
    }

    private static boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    /**
     * Returns true if the given hour and minute are valid.
     */
    private static boolean isValidTime(int hour, int minute) {
        return hour >= 0 && hour < 24 && minute >= 0 && minute < 60;
    }

    /**
     * Splits a date string into year, month and day.
     * @param date The date string to split.
     * @return an {@code int} array of year, month and day in that order.
     */
    private static int[] splitDate(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6));
        int[] dateArray = {year, month, day};
        return dateArray;
    }

    /**
     * Splits a time string into hour and minutes.
     * @param time The time string to split.
     * @return an {@code int} array of hour and minutes in that order.
     */
    private static int[] splitTime(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(2));
        int[] timeArray = {hour, minute};
        return timeArray;
    }

    /**
     * Creates a calendar from the given date and time strings.
     * @param date A valid date string.
     * @param time A valid time string.
     * @return a {@code Calendar} with the given date and time.
     */
    private static Calendar createCalendar(String date, String time) {
        Calendar result = Calendar.getInstance();
        result.setTime(INPUT_DATE_TIME_FORMAT.parse(date + time, new ParsePosition(0)));
        return result;
    }

    @Override
    public String toString() {
        return getDate() + ", " + getTime();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && calendar.equals(((DateTime) other).calendar)); // state check
    }

    @Override
    public int compareTo(DateTime other) {
        return calendar.compareTo(other.calendar);
    }

    @Override
    public int hashCode() {
        return calendar.hashCode();
    }

}
