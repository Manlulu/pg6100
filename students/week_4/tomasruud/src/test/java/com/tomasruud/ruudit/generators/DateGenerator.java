package com.tomasruud.ruudit.generators;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DateGenerator {

    private static final Random RANDOM = new Random();

    public static Date randomDay() {

        final int year = 2015 + RANDOM.nextInt( 2 );
        final int dayOfYear = 1 + RANDOM.nextInt( 365 );

        Calendar calendar = Calendar.getInstance();
        calendar.set( Calendar.YEAR, year );
        calendar.set( Calendar.DAY_OF_YEAR, dayOfYear );

        return calendar.getTime();
    }
}
