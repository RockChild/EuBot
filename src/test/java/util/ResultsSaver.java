package util;

import java.io.*;
import java.time.LocalDateTime;

public class ResultsSaver {

    private static File path = new File("src/test/resources/log.txt");
    private static String message = "%s - %s\n";
    private static String successfull = "INFO: Published";
    private static String unsuccessfull = "ERROR: Article wasn't published";

    public static void parse(boolean isSuccessfull) {
        try ( Writer r= new FileWriter(path, true)) {
            if (isSuccessfull) {
                r.append(String.format(message, LocalDateTime.now(), successfull));
            } else {
                r.append(String.format(message, LocalDateTime.now(), unsuccessfull));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
