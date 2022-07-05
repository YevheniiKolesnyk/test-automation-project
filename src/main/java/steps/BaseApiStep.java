package steps;

import io.restassured.response.Response;

import static org.testng.Assert.assertEquals;

public class BaseApiStep {

    protected static void assertSuccessfulResponseWithMessage(Response response) {
        assertEquals(response.statusCode(), 200,
                "Response status code is not equals to expected. " +
                        "Response error: " + response.path("message"));
    }

    private static void assertSuccessfulResponse(Response response) {
        assertEquals(response.statusCode(), 200,
                "Response status code is not equals to expected");
    }

}
