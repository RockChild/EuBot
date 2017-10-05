package util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    private static Random r = new Random();
    public static String generateString() {
        r.nextInt(26);
        return "";
    }

    public static String generateEmail() {
        StringBuilder sb = new StringBuilder();
        int num = r.nextInt(5) + 5;
        sb.append(generateString(num))
            .append("@")
            .append(generateString(4))
            .append(".")
            .append(generateString(2));

        return sb.toString();
    }

    public static String generateCompanyName() {
        StringBuilder sb = new StringBuilder();
        int num = r.nextInt(6) + 1;
        sb.append(TxtParser.getRandomCompanyName()).append(" ").append(Country.values()[num].getValue());
        return sb.toString();

    }

    private static char getRandomChar() {
        return Character.toChars((char) r.nextInt(26) + 97)[0];
    }

    private static String generateString(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(getRandomChar());
        }
        return sb.toString();
    }

}
enum Country {
    Cz("CZ", 1), Pl("PL", 2), Eu("EU", 3), Bl("PL", 4), Ge("GE", 5), D("D", 6), Bg("BG", 7);
    Country(String value, int num) {
        this.value = value;
    }
    private String value;
    public String getValue() {
        return value;
    }
}
