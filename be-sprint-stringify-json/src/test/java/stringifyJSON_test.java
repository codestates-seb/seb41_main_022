import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class stringifyJSON_test {

    private static stringifyJSON test = new stringifyJSON();

    public String ObjectMapper(Object data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(data);
    }

    @Test
    @DisplayName("null을 입력받을 경우, 알맞은 형태의 JSON으로 변환합니다")
    public void null을_JSON으로_변경합니다() throws JsonProcessingException {
        assertThat(test.stringify(null)).isEqualTo(ObjectMapper(null));
    }

    @Test
    @DisplayName("Boolean 타입을 입력받을 경우, 알맞은 형태의 JSON으로 변환합니다")
    public void Boolean_타입의_입력을_JSON으로_변경합니다_1() throws JsonProcessingException {
        assertThat(test.stringify(false)).isEqualTo(ObjectMapper(false));
    }

    @Test
    @DisplayName("Boolean 타입을 입력받을 경우, 알맞은 형태의 JSON으로 변환합니다")
    public void Boolean_타입의_입력을_JSON으로_변경합니다_2() throws JsonProcessingException {
        assertThat(test.stringify(true)).isEqualTo(ObjectMapper(true));
    }

    @Test
    @DisplayName("String 타입을 입력받을 경우, 알맞은 형태의 JSON으로 변환합니다")
    public void String_타입의_입력을_JSON으로_변경합니다() throws JsonProcessingException {
        assertThat(test.stringify("Hello CodeStates!")).isEqualTo(ObjectMapper("Hello CodeStates!"));
    }

    @Test
    @DisplayName("배열을 입력받을 경우, 알맞은 형태의 JSON으로 변환합니다")
    public void 배열을_입력받았을_경우_JSON으로_변경합니다_1() throws JsonProcessingException {
        assertThat(test.stringify(new Object[]{})).isEqualTo(ObjectMapper(new Object[]{}));
    }

    @Test
    @DisplayName("배열을 입력받을 경우, 알맞은 형태의 JSON으로 변환합니다")
    public void 배열을_입력받았을_경우_JSON으로_변경합니다_2() throws JsonProcessingException {
        assertThat(test.stringify(new Object[]{8})).isEqualTo(ObjectMapper(new Object[]{8}));
    }

    @Test
    @DisplayName("배열을 입력받을 경우, 알맞은 형태의 JSON으로 변환합니다")
    public void 배열을_입력받았을_경우_JSON으로_변경합니다_3() throws JsonProcessingException {
        assertThat(test.stringify(new Object[]{"hi"})).isEqualTo(ObjectMapper(new Object[]{"hi"}));
    }

    @Test
    @DisplayName("배열을 입력받을 경우, 알맞은 형태의 JSON으로 변환합니다")
    public void 배열을_입력받았을_경우_JSON으로_변경합니다_4() throws JsonProcessingException {
        assertThat(test.stringify(new Object[]{8, "hi"})).isEqualTo(ObjectMapper(new Object[]{8, "hi"}));
    }

    @Test
    @DisplayName("배열을 입력받을 경우, 알맞은 형태의 JSON으로 변환합니다")
    public void 배열을_입력받았을_경우_JSON으로_변경합니다_5() throws JsonProcessingException {
        assertThat(test.stringify(new Object[]{8, new Object[]{new Object[]{}, 3, 4}})).isEqualTo(ObjectMapper(new Object[]{8, new Object[]{new Object[]{}, 3, 4}}));
    }

    @Test
    @DisplayName("배열을 입력받을 경우, 알맞은 형태의 JSON으로 변환합니다")
    public void 배열을_입력받았을_경우_JSON으로_변경합니다_6() throws JsonProcessingException {
        assertThat(test.stringify(new Object[]{new Object[]{new Object[]{}}})).isEqualTo(ObjectMapper(new Object[]{new Object[]{new Object[]{}}}));
    }

    @Test
    @DisplayName("HashMap을 입력받을 경우, 알맞은 형태의 JSON으로 변환합니다")
    public void Map_을_입력받았을_경우_JSON으로_변경합니다_1() throws JsonProcessingException {
        HashMap<Object, Object> map = new HashMap<>();
        assertThat(test.stringify(map)).isEqualTo(ObjectMapper(map));
    }

    @Test
    @DisplayName("HashMap을 입력받을 경우, 알맞은 형태의 JSON으로 변환합니다")
    public void Map_을_입력받았을_경우_JSON으로_변경합니다_2() throws JsonProcessingException {
        HashMap<Object, Object> map = new HashMap(){{
            put("a", "apple");
        }};
        assertThat(test.stringify(map)).isEqualTo(ObjectMapper(map));
    }

    @Test
    @DisplayName("HashMap을 입력받을 경우, 알맞은 형태의 JSON으로 변환합니다")
    public void Map_을_입력받았을_경우_JSON으로_변경합니다_3() throws JsonProcessingException {
        HashMap<Object, Object> map = new HashMap(){{
            put("foo", true);
            put("bar", false);
            put("baz", null);
        }};
        assertThat(test.stringify(map)).isEqualTo(ObjectMapper(map));
    }
}
