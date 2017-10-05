package util;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class TxtParser {

    private static File logPath = new File("src/test/resources/log.txt");
    private static File companiesPath = new File("src/test/resources/companyVocabulary.txt");
    private static String message = "%s - %s\n";
    private static String successfull = "INFO: Published";
    private static String unsuccessfull = "ERROR: Article wasn't published";

    public static void parse(boolean isSuccessfull) {
        try ( Writer r= new FileWriter(logPath, true)) {
            if (isSuccessfull) {
                r.append(String.format(message, LocalDateTime.now(), successfull));
            } else {
                r.append(String.format(message, LocalDateTime.now(), unsuccessfull));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getRandomCompanyName() {
        try {
            return choose(companiesPath);
        } catch (FileNotFoundException e) {
            System.out.println("Couldnt read the file companyVocabulary.txt");
            e.printStackTrace();
        }
        return "CompanyEmpty";
    }
    public static String choose(File f) throws FileNotFoundException
    {
        String result = null;
        Random rand = new Random();
        int n = rand.nextInt(14) + 1;
        int i = 0;
        for(Scanner sc = new Scanner(f); sc.hasNext(); i++)
        {
            String line = sc.nextLine();
            if (n == i)
                result = line;
        }

        return result;
    }

}
