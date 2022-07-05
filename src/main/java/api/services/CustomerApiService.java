package api.services;

import api.dto.CustomerDto;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class CustomerApiService extends ApiService {

    private final String resourceUrl;

    public CustomerApiService() {
        super();
        resourceUrl = "http://automationpractice.com/index.php?controller=authentication";
    }

    public Response post(String email, Map<String, String> cookies) {
        return super.postWithCookies(resourceUrl, cookies, CustomerDto.buildCustomer(email));
    }

}
