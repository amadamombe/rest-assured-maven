package assertionexamples;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NumberRelatedAssertionTests {

    private static String URL = "https://reqres.in/api/users/";

    @Before
    public void setup() {
        // Any setup code  to run before each test
    }

    @Test
    public void testNumberAssertions() {
        given().when()
            .queryParam("page", 2)
            .get(URL)
            .then()
            .statusCode(200)
            .and()
            .assertThat()
            .body("page", equalTo(2))
            .body("per_page", greaterThan(4))
            .body("per_page", greaterThanOrEqualTo(6))
            .body("total", lessThan(14))
            .body("total_pages", lessThanOrEqualTo(3));
    }

    @Test
    public void testGreaterThanAssertions() {
        given().when()
            .queryParam("page", 2)
            .get(URL)
            .then()
            .statusCode(200)
            .and()
            .assertThat()
            .body("per_page", greaterThan(4))
            .body("per_page", greaterThanOrEqualTo(6));
    }

    @Test
    public void testLessThanAssertions() {
        given().when()
            .queryParam("page", 2)
            .get(URL)
            .then()
            .statusCode(200)
            .and()
            .assertThat()
            .body("total", lessThan(14))
            .body("total_pages", lessThanOrEqualTo(3));
    }

    @After
    public void teardown() {
        // Any teardown code you need to run after each test
    }
}
