package br.ufscar.auxiliares;

import java.util.Calendar;

public class EasyTime {
    private int hour; // from 0 to 23
    private int minute;
    private int second;

    public EasyTime() {
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        second = c.get(Calendar.SECOND);
    }

    public EasyTime(String date) {
        // String should be in format HH:MM:SS, with HH in 24-hour format
        hour = Integer.getInteger(date.substring(0, 2));
        minute = Integer.getInteger(date.substring(3,5));
        second = Integer.getInteger(date.substring(6,8));
    }

    public static boolean nowInRange(EasyTime from, EasyTime to) {
        EasyTime now = new EasyTime();
        if (now.getHour() > from.getHour() && now.getHour() < to.getHour())
            return true;
        else if (now.getHour() == from.getHour() || now.getHour() == to.getHour())
            if (now.getMinute() > from.getMinute() && now.getMinute() < to.getMinute())
                return true;
            else if (now.getMinute() == from.getMinute() || now.getMinute() == to.getMinute())
                if (now.getSecond() >= from.getSecond() && now.getSecond() <= from.getSecond())
                    return true;

        return false;
    }

    // Outputs string to format HH:MM:SS, with HH in 24-hour format
    public String toString() {
        return Integer.toString(hour) + ":" + Integer.toString(minute) + ":" + Integer.toString(second);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }
}
