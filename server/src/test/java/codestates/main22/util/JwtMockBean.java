package codestates.main22.util;

import codestates.main22.oauth2.filter.CorsFilter;
import codestates.main22.oauth2.handler.OAuth2UserSuccessHandler;
import codestates.main22.oauth2.jwt.JwtTokenizer;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.user.service.UserService;
import codestates.main22.utils.CustomCookie;
import codestates.main22.utils.Token;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import static java.lang.Character.FORMAT;

public class JwtMockBean {
    @Autowired
    protected MockMvc mockMvc;

    protected Gson gson;
    protected static String startWithUrl;

    @MockBean
    protected JwtTokenizer jwtTokenizer;

    @MockBean
    protected CustomAuthorityUtils customAuthorityUtils;

    @MockBean
    protected CustomCookie customCookie;

    @MockBean
    protected Token token;

    @MockBean
    protected UserService userService;

    public JwtMockBean() {
        gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            @Override
            public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
                return new JsonPrimitive(formatter.format(localDateTime));
            }
        }).registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public JsonElement serialize(LocalDate localDate, Type srcType, JsonSerializationContext context) {
                return new JsonPrimitive(formatter.format(localDate));
            }
        }).registerTypeAdapter(LocalDate.class, new JsonDeserializer <LocalDate>() {
            @Override
            public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                return LocalDate.parse(json.getAsString(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.KOREA));
            }
        }).setPrettyPrinting().create();
    }
}
