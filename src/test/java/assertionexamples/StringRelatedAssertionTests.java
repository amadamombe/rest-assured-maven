package assertionexamples;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class StringRelatedAssertionTests {

    private static RequestSpecification requestSpecification;
    private static ResponseSpecification responseSpecification;

    @BeforeClass
    public static void setupSpecBuilder() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/api/users/")
                .addQueryParam("page", 2)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter());
        
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectBody("page", equalTo(2));

        responseSpecification = responseSpecBuilder.build();
        requestSpecification = requestSpecBuilder.build();
    }

    @Test
    public void testStringAssertions() {
        given()
            .spec(requestSpecification)
            .get()
            .then()
            .spec(responseSpecification)
            .assertThat()
            .body("data[0].first_name", equalTo("Michael"))
            .body("data[0].first_name", equalToIgnoringCase("MICHael"))
            .body("data[0].email", containsString("michael.lawson"))
            .body("data[0].last_name", startsWith("L"))
            .body("data[0].last_name", endsWith("n"));
    }

    @Test
    public void testNotNullAssertions() {
        given()
            .spec(requestSpecification)
            .get()
            .then()
            .spec(responseSpecification)
            .and()
            .assertThat()
            .body("data[0].first_name", is(Matchers.notNullValue()));
    }

    @Test
    public void testHasKeyAssertion() {
        given()
            .spec(requestSpecification)
            .get()
            .then()
            .spec(responseSpecification)
            .and()
            .assertThat()
            .body("data[0]", hasKey("email"))
            .body("support", hasKey("url"))
            .body("$", hasKey("page"))
            .body("$", hasKey("total"));
    }

    @Test
    public void testNotAssertions() {
        given()
            .spec(requestSpecification)
            .get()
            .then()
            .spec(responseSpecification)
            .and()
            .assertThat()
            .body("data", not(emptyArray()))
            .body("data[0].first_name", not(equalTo("George")))
            .body("data.size()", greaterThan(5));
    }

    @Test
    public void testMultipleAssertStatement() {
        given()
            .spec(requestSpecification)
            .get()
            .then()
            .spec(responseSpecification)
            .and()
            .assertThat()
            .body("page", equalTo(2), "data[0].first_name", equalTo("Michael"), "support.url", is(notNullValue()));
    }
}
