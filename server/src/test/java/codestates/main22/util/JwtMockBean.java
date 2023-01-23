package codestates.main22.util;

import codestates.main22.oauth2.jwt.JwtTokenizer;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.user.service.UserService;
import codestates.main22.utils.Token;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
        }).setPrettyPrinting().create();
    }
}
