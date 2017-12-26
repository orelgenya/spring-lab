import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HoverflySample {

    private static final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";
    private static final String POST_1_URL = POSTS_URL + "/1";
    private static final String POST_1_BODY = "{\n" +
            "  \"userId\": 1,\n" +
            "  \"id\": 1,\n" +
            "  \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
            "  \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit " +
            "molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" +
            "}";
    private static final String USER_ID_10_ID_100 = ",\n" +
            "  {\n" +
            "    \"userId\": 10,\n" +
            "    \"id\": 100,";

    private RestTemplate restTemplate = new RestTemplate();

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule.inCaptureOrSimulationMode("posts.json");


    @Test
    public void at_1_should_get_all_post() {
        ResponseEntity<String> response = restTemplate.getForEntity(POSTS_URL, String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(response.getBody(), containsString(USER_ID_10_ID_100));
    }

    @Test
    public void at_2_should_get_a_post() {
        ResponseEntity<String> response = restTemplate.getForEntity(POST_1_URL, String.class);

        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
        assertThat(response.getBody(), is(equalToIgnoringWhiteSpace(POST_1_BODY)));
    }
}
