package br.ufscar.auxiliares;

import java.util.Calendar;

public class EasyDate {
    private int day;
    private int month;
    private int year;

    public EasyDate() {
        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
    }

    public EasyDate(String date) {
        // String should be in format yyyy-mm-dd
        year = Integer.getInteger(date.substring(0, 4));
        month = Integer.getInteger(date.substring(5,7));
        day = Integer.getInteger(date.substring(8,10));
    }

    public static boolean nowInRange(EasyDate from, EasyDate to) {
        EasyDate now = new EasyDate();
        if (now.getYear() > from.getYear() && now.getYear() < to.getYear())
            return true;
        else if (now.getYear() == from.getYear() || now.getYear() == to.getYear())
            if (now.getMonth() > from.getMonth() && now.getMonth() < to.getMonth())
                return true;
            else if (now.getMonth() == from.getMonth() || now.getMonth() == to.getMonth())
                if (now.getDay() >= from.getDay() && now.getDay() <= from.getDay())
                    return true;

        return false;
    }

    // Outputs string to format yyyy-mm-dd
    public String toString() {
        return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
