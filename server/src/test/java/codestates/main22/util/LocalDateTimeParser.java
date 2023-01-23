package codestates.main22.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;

public class LocalDateTimeParser {
    public Gson initGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        return gsonBuilder.setPrettyPrinting().create();
    }

    public String toJson(Object requestDto) {
        Gson gson = initGson();
        String json = gson.toJson(requestDto);
        return json.replaceAll("\n", "").replaceAll(" ", "");
    }
}
