package api.services;

import api.dto.IndexDto;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class IndexApiService extends ApiService {

    private final String resourceUrl;

    public IndexApiService() {
        super();
        resourceUrl = "http://automationpractice.com/index.php";
    }

    public Response getIndex() {
        Response response = super.get(resourceUrl);
        response.then()
                .statusCode(200);
        return response;
    }

    public String getToken(Response response) {
        String responseBody = response
                .then()
                .extract()
                .body()
                .asString();
        return StringUtils.substringBetween(responseBody, "var token = '", "';");
    }

    public Map<String, String> getCookies(Response response) {
        return response.getCookies();
    }

    public Response post(String email, Map<String, String> cookies, String token) {
        return super.postWithCookies(resourceUrl, cookies, IndexDto.buildCustomer(email, token));
    }

}
