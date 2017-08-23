package util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import model.Article;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataParser {
    private static final String WRONG_FORMAT = "Wrong data.json format. Just validate it!";
    //    static private String PATH = "C:/json/data.json";
    static private final String PATH = "src/test/resources/data.json";
    static private JsonStreamParser parser;
    static private Reader reader;
    private static final String FILE_NOT_FOUND = "'data.json' was not found under '%s' in the project.\nJust check it!";;
    private static final String PARSING_ERROR = "Error while parsing. Unexpected format. \nJust call the Police!";

    public static List<Article> parse() {
        List<Article> articlList = new ArrayList<>();
        try (InputStream is = new FileInputStream(PATH)){
            reader = new InputStreamReader(is, "UTF-8");
        } catch (FileNotFoundException e) {
            System.out.println(String.format(FILE_NOT_FOUND, PATH));
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(WRONG_FORMAT);
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        parser = new JsonStreamParser(reader);

        while (parser.hasNext()) {
            JsonElement e = parser.next();
            if (e.isJsonObject()) {
                return Collections.singletonList(gson.fromJson(e, Article.class));
            }
            if (e.isJsonArray()) {
                for (JsonElement element : e.getAsJsonArray()) {
                    articlList.add(gson.fromJson(element, Article.class));
                }
            } else {
                System.out.println(PARSING_ERROR);
                return Collections.EMPTY_LIST;
            }
        }
        return articlList;
    }
}
