package com.sdimdev.nnhackaton.utils.system.other;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

public class DecimalUtils {
    public static BigDecimal Zero = new BigDecimal(0);

    public static Integer toInt(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return bigDecimal.intValue();
    }

    /**
     * For most fiscal printer money is represented as integer result of
     * multiplication by 100.
     */
    public static Long moneyToLong(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return (long) (bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue() * 100.0);
    }

    public static Long toLong(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return bigDecimal.longValue();
    }

    public static Integer reminder(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        BigDecimal result2 = bigDecimal.subtract(bigDecimal.setScale(0, RoundingMode.DOWN));//0.25000
        return result2.setScale(2, RoundingMode.DOWN).unscaledValue().intValue();
    }

    public static String toString(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            bigDecimal = new BigDecimal(0);
        }
        return String.format(Locale.getDefault(), "%s.%02d", DecimalUtils.spaced(String.valueOf(toInt(bigDecimal)), 3),
                reminder(bigDecimal));
    }

    public static boolean equals(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
        if (bigDecimal == null && bigDecimal2 == null) {
            return true;
        }
        if (bigDecimal == null) {
            return false;
        }
        return bigDecimal.compareTo(bigDecimal2) == 0;
    }

    public static String spaced(String text, int interval) {
        if (interval <= 0) return text;
        if (text == null || text.length() == 0)
            return text;
        StringBuilder sb = new StringBuilder();
        int pos = text.length();
        do {
            int startAt = pos - interval;
            if (startAt < 0) startAt = 0;
            sb.insert(0, text.substring(startAt, pos));
            pos -= interval;
            if (pos > 0)
                sb.insert(0, " ");
        } while (pos > 0);
        return sb.toString();
    }
}
