package steps;

import api.services.CustomerApiService;
import api.services.IndexApiService;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

public class ApiSteps extends BaseApiStep {

    @Step("Register a new customer: {email} and verify response")
    public static void registerNewCustomer(String email) {
        Response response = new IndexApiService().getIndex();

        String token = new IndexApiService().getToken(response);
        Map<String, String> cookies = new IndexApiService().getCookies(response);

        new IndexApiService().post(email, cookies, token).then().statusCode(200);

        new CustomerApiService().post(email, cookies).then().statusCode(200);
    }

}
