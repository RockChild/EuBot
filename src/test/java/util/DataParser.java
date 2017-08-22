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
//    static private String path = "C:/json/data.json";
    static private String path = "src/test/resources/data.json";

    public static List<Article> parse() throws UnsupportedEncodingException, FileNotFoundException {
        List<Article> articlList = new ArrayList<>();
        InputStream is = new FileInputStream(path);
        Reader r = new InputStreamReader(is, "UTF-8");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonStreamParser p = new JsonStreamParser(r);

        while (p.hasNext()) {
            JsonElement e = p.next();
            if (e.isJsonObject()) {
                return Collections.singletonList(gson.fromJson(e, Article.class));
            }
            if (e.isJsonArray()) {
                for (JsonElement element : e.getAsJsonArray()) {
                    articlList.add(gson.fromJson(element, Article.class));
                }
            }
        }
        return articlList;
    }
}
