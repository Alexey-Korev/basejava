package ru.basejava;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class mainDate {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
        System.out.println(System.currentTimeMillis());

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        System.out.println(cal.getTime());

        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        System.out.println(ld);
        System.out.println(lt);
        System.out.println(ldt);

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY*MMMM*dd");
        System.out.println(sdf.format(date));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY*MMMM*dd");
        System.out.println(ld.format(dtf));
    }
}
