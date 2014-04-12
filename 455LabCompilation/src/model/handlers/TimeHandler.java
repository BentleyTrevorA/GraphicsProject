package model.handlers;

import java.util.Calendar;

public class TimeHandler {

    public static int getCurrentTimeInSeconds() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    public static long getCurrentTimeInMilliseconds() {
        return Calendar.getInstance().getTimeInMillis();
    }
}
