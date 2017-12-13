package test.dmisb.apps65.utils;

import java.util.Date;
import java.util.GregorianCalendar;

public class CalcUtils {

    public static int calcAge(Date dateBirth) {
        GregorianCalendar birthDay = new GregorianCalendar();
        birthDay.setTime(dateBirth);

        GregorianCalendar checkDay = new GregorianCalendar();
        checkDay.setTimeInMillis(System.currentTimeMillis());

        int years = checkDay.get(GregorianCalendar.YEAR) - birthDay.get(GregorianCalendar.YEAR);
        int checkMonth = checkDay.get(GregorianCalendar.MONTH);
        int birthMonth = birthDay.get(GregorianCalendar.MONTH);
        if ( checkMonth < birthMonth ) {
            years --;
        } else  if (checkMonth == birthMonth
                && checkDay.get(GregorianCalendar.DAY_OF_MONTH) < birthDay.get(GregorianCalendar.DAY_OF_MONTH)) {
            years --;
        }
        return years;
    }
}
