import com.fasterxml.jackson.core.JsonProcessingException;

public class main {
    public static void main(String[] args) throws JsonProcessingException {
        stringifyJSON test = new stringifyJSON();
        System.out.println(test.stringify("Hello CodeStates!"));
    }
}