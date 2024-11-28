package com.rankup.empire.currencies.feat.utils.text;

import com.rankup.empire.currencies.CurrenciesPlugin;
import lombok.val;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formats {

    private static final Pattern PATTERN = Pattern.compile("^(\\d+\\.?\\d*)(\\D+)");
    private static final List<String> CASH = CurrenciesPlugin.getPlugin(CurrenciesPlugin.class).getConfig().getStringList("CurrencyFormat");
    private static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("#.##");


    public static String format(double value) {
        if (isInvalid(value)) return "0";

        int index = 0;
        val format = CASH;

        double tmp;
        while ((tmp = value / 1000) >= 1) {
            if (index + 1 == format.size()) break;
            value = tmp;
            ++index;
        }

        return NUMBER_FORMAT.format(value) + format.get(index);
    }


    public static double parse(String string) {
        try {

            val value = Double.parseDouble(string);
            return isInvalid(value) ? 0 : value;

        } catch (Exception ignored) {
        }

        Matcher matcher = PATTERN.matcher(string);
        if (!matcher.find()) return 0;

        double amount = Double.parseDouble(matcher.group(1));
        String suffix = matcher.group(2);
        String fixedSuffix = suffix.equalsIgnoreCase("k") ? suffix.toLowerCase() : suffix.toUpperCase();

        int index = CASH.indexOf(fixedSuffix);

        val value = amount * Math.pow(1000, index);
        return isInvalid(value) ? 0 : value;
    }

    public static boolean isInvalid(double value) {
        return value < 0 || Double.isNaN(value) || Double.isInfinite(value);
    }

    public static String priceWithoutDecimal (Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        return formatter.format(price);
    }

    public static String priceToString(Double price) {
        return priceWithoutDecimal(price);
    }
}
