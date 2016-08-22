package it.sad.sii.transit.sdk.utils;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Utilities for reading and writing DateTime objects.
 */
public class DateUtils {
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd");
    private static final DateTimeFormatter DATE_NICE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("HH:mm:ss");

    /**
     * Parses the given String in the form "yyyy-MM-dd HH:mm:ss" to a DateTime object.
     *
     * @param date String containing a date in the form "yyyy-MM-dd HH:mm:ss"
     * @return a DateTime object
     */
    public static DateTime parseDateTime(String date) {
        return DATETIME_FORMATTER.parseDateTime(date);
    }

    /**
     * Prints the given DateTime object to a String in the form "yyyy-MM-dd HH:mm:ss".
     *
     * @param date a DateTime object
     * @return String containing a date in the form "yyyy-MM-dd HH:mm:ss"
     */
    public static String printDateTime(DateTime date) {
        return DATETIME_FORMATTER.print(date);
    }

    /**
     * Parses the given String in the form "yyyyMMdd" to a DateTime object.
     *
     * @param date String containing a date in the form "yyyyMMdd"
     * @return a DateTime object
     */
    public static LocalDate parseDate(String date) {
        return DATE_FORMATTER.parseLocalDate(date);
    }

    /**
     * Prints the given DateTime object to a String in the form "yyyyMMdd".
     *
     * @param date a DateTime object
     * @return String containing a date in the form "yyyyMMdd"
     */
    public static String printDate(DateTime date) {
        return DATE_FORMATTER.print(date);
    }

    /**
     * Prints the given DateTime object to a String in the form "yyyyMMdd".
     *
     * @param date a DateTime object
     * @return String containing a date in the form "yyyyMMdd"
     */
    public static String printDate(LocalDate date) {
        return DATE_FORMATTER.print(date);
    }

    /**
     * Parses the given String in the form "yyyy-MM-dd" to a DateTime object.
     *
     * @param date String containing a date in the form "yyyy-MM-dd"
     * @return a DateTime object
     */
    public static LocalDate parseDateNice(String date) {
        return DATE_NICE_FORMATTER.parseLocalDate(date);
    }

    /**
     * Prints the given DateTime object to a String in the form "yyyy-MM-dd".
     *
     * @param date a DateTime object
     * @return String containing a date in the form "yyyy-MM-dd"
     */
    public static String printDateNice(DateTime date) {
        return DATE_NICE_FORMATTER.print(date);
    }

    /**
     * Prints the given DateTime object to a String in the form "yyyy-MM-dd".
     *
     * @param date a DateTime object
     * @return String containing a date in the form "yyyy-MM-dd"
     */
    public static String printDateNice(LocalDate date) {
        return DATE_NICE_FORMATTER.print(date);
    }

    /**
     * Parses the given String in the form "HH:mm:ss" to a DateTime object.
     *
     * @param date String containing a date in the form "HH:mm:ss"
     * @return a DateTime object
     */
    public static LocalTime parseTime(String date) {
        return TIME_FORMATTER.parseLocalTime(date);
    }

    /**
     * Prints the given DateTime object to a String in the form "HH:mm:ss".
     *
     * @param date a DateTime object
     * @return String containing a date in the form "HH:mm:ss"
     */
    public static String printTime(DateTime date) {
        return TIME_FORMATTER.print(date);
    }

    /**
     * Prints the given DateTime object to a String in the form "HH:mm:ss".
     *
     * @param date a DateTime object
     * @return String containing a date in the form "HH:mm:ss"
     */
    public static String printTime(LocalTime date) {
        return TIME_FORMATTER.print(date);
    }

    /**
     * Converts the given seconds since midnight to a DateTime object with today's date.
     *
     * @param secondsSinceMidnight seconds since midnight
     * @return DateTime containing the time that corresponds to secondsSinceMidnight of today
     */
    public static DateTime secondsSinceMidnightToDate(int secondsSinceMidnight) {
        return secondsSinceMidnightToDate(secondsSinceMidnight, new LocalDate());
    }

    /**
     * Converts the given seconds since midnight to a DateTime object.
     *
     * @param secondsSinceMidnight seconds since midnight
     * @param date                 the date at which the seconds are added YYYYMMdd
     * @return DateTime containing the time that corresponds to secondsSinceMidnight of the given date
     */
    public static DateTime secondsSinceMidnightToDate(int secondsSinceMidnight, LocalDate date) {
        return date.toDateTimeAtStartOfDay().plusSeconds(secondsSinceMidnight);
    }

    /**
     * Converts the given date to seconds since midnight.
     *
     * @param date the date to convert
     * @return the secondsSinceMidnight
     */
    public static int dateToSecondsSinceMidnight(DateTime date) {
        return date.getSecondOfDay();
    }
}
