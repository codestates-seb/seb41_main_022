import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.ls.LSOutput;

import java.util.*;

/**
 * 1. Browser에 존재하는 stringify 함수를 직접 구현해 봅니다.
 * stringify 함수는 input 값을 JSON 형식으로 변환합니다.
 *
 * 2. stringify는 아래와 같이 작동합니다.
 * - Boolean이 input으로 주어졌을 경우
 * stringify(true);                // "true"
 * - String이 input으로 주어졌을 경우
 * stringify("foo");               // "foo"
 * - null이 주어졌을 경우
 * stringify(null)                 // "null"
 * - HashMap이 input으로 주어졌을 경우
 * HashMap<Object, Object> map = new HashMap<>();
 * map.put("null", 2);
 * map.put("true", "false");
 * stringify(map);                // "{"null":2,"true":"false"}"
 * Map 자료형의 Key는 항상 String으로 저장됩니다. null은 입력할 수 없습니다.
 *
 * 예시에 해당되지 않는 자료형의 경우 모두 null을 반환합니다.
 *
 * 3. test/java/stringifyJSON_test.java의 테스트에서 어떤 input 값들이 주어지고, 어떻게 stringify 주어야 할지 생각해 보세요.
 *
 *
 * 4. 입력받은 전달인자의 타입은 instanceof 메서드를 활용합니다.
 *
 * 5. 그냥 테스트 통과를 하고 싶으시다면, 다음과 같이 구현하면 될 거예요.
 * ObjectMapper mapper = new ObjectMapper();
 * return mapper.writeValueAsString(data);
 * 위의 코드는 ObjectMapper 메소드로 이미 구현되어 있습니다. main 클래스에서 직접 사용해보세요!
 *
 * 하지만 이 과제의 목적은 재귀를 공부하는 것이니, 처음부터 구현해봐야겠지요?:
 */

public class stringifyJSON {

  public String ObjectMapper(Object data) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(data);
  }

  public String stringify(Object data) {

    //입력된 값이 문자열일 경우
    if (data instanceof String){return "\"" + data + "\"";}
    //입력된 값이 Integer일 경우
    if (data instanceof Integer){return String.valueOf(data);}
    //입력된 값이 Boolean일 경우
    if (data instanceof Boolean){return String.valueOf(data);}
    //입력된 값이 Object[]일 경우
    if (data instanceof Object[]){
      String result = "[";
      for(Object x : (Object[]) data){result += stringify(x) + ",";}
      result += "]";
      result = result.replace(",]","]");
      return result;
    }
    //입력된 값이 HashMap일 경우
    if (data instanceof HashMap){
      HashMap<Object, Object> map = (HashMap) data;
      String result = "{";
      for(Object key : map.keySet()) {
        result += (stringify(key) + ":" + stringify(map.get(key)) + ",");}
        result += "}";
        result = result.replace(",}","}");
        return result;

    }
    //지정되지 않은 타입의 경우에는 "null"을 리턴합니다.
    return "null";
  }
}

// 해쉬맵 열기 {
// 해쉬맵 키를 탐색하고 키에 맞는 값을 키:값 으로 넣어주기
// 해쉬맵 닫기 }