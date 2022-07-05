package api.services;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public abstract class ApiService {

    private final RequestSpecification spec;

    ApiService() {
        spec = given()
                .contentType(JSON)
                .accept(JSON)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    Response post(String url, Object body) {
        return given()
                .spec(spec)
                .with()
                .body(body)
                .when()
                .post(url);
    }

    Response postWithCookies(String url, Map<String, String> cookies, Object body) {
        return given()
                .spec(spec)
                .with()
                .body(body)
                .cookies(cookies)
                .when()
                .post(url);
    }

    Response put(String url, Object body) {
        return given()
                .spec(spec)
                .with()
                .body(body)
                .when()
                .put(url);
    }

    Response get(String url) {
        return given()
                .spec(spec)
                .when()
                .get(url);
    }

    Response get(String url, String field, String value, String condition) {
        return given()
                .spec(spec)
                .params("searchCriteria[filterGroups][1][filters][0][field]", field)
                .params("searchCriteria[filterGroups][1][filters][0][value]", value)
                .params("searchCriteria[filterGroups][1][filters][0][conditionType]", condition)
                .when()
                .get(url);
    }

    Response delete(String url) {
        return given()
                .spec(spec)
                .when()
                .delete(url);
    }

}