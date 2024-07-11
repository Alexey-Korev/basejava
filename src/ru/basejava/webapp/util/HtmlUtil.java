package ru.basejava.webapp.util;

import ru.basejava.webapp.model.Period;

import static ru.basejava.webapp.util.DateUtil.format;

public class HtmlUtil {


    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(Period period) {
        return format(period.getStartDate()) + " - " + format(period.getEndDate());
    }
}
