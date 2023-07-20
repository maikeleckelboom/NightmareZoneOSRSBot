package src.utils;

import java.text.DecimalFormat;

public class NumberFormatter {
    public static String formatNumberWithCommas(long number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number);
    }
}