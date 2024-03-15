package com.phonebook.RAtests;

import com.phonebook.dto.ContactDto;
import com.phonebook.dto.ErrorDto;
import com.phonebook.dto.MessageDto;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;


public class UpdateContactTests extends TestBase {

    ContactDto contactDto;

    @BeforeMethod
    public void precondition () {


           contactDto = ContactDto.builder()
                   .name("Elena")
                   .lastName("Smith")
                   .email("elenaxsm56@gm.com")
                   .phone("67667544589")
                   .address("New York")
                   .description("QA")
                   .build();
           String message = given()
                   .contentType(ContentType.JSON)
                   .header(AUTH, token)
                   .body(contactDto)
                   .when()
                   .post("contacts")
                   .then()
                   .assertThat().statusCode(200)
                   .extract().path("message");
           String[] array = message.split(": ");
           contactDto.setId(array[1]);
       }


               //TEST1 PASS
    @Test
    public void updateContactNamePositiveTest() {

        contactDto.setName("Pascal");

     MessageDto dto = given()
                .contentType(ContentType.JSON)
                .header(AUTH, token)
                .body(contactDto)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(MessageDto.class);

        System.out.println(dto.getMessage());
    }

              ///TEST2 PASS
    @Test
    public void updateContactNamePositiveMessageTest() {

        given()
                .contentType(ContentType.JSON)
                .header(AUTH, token)
                .body(contactDto)
                .when()
                .put("contacts")
                .then()
                .assertThat().body("message", containsString("Contact was updated"));

    }

                         ///TEST3 FAILED (GET200)
                        /// It is possible to set invalid data to name field
          @Test
          public void updateContactNameWithNumbersTest() {


        contactDto.setName("677654");
         ErrorDto errorDto = given()
                      .contentType(ContentType.JSON)
                      .header(AUTH, token)
                      .body(contactDto)
                      .when()
                      .put("contacts")
                      .then()
                      .assertThat().statusCode(400)
                      .extract().path("Error");
              System.out.println(errorDto.getMessage());

          }

                               //Test 4  FAILED  (GET 400)
               //According to the documentation, 404 Contact not found is expected

    @Test
    public void updateContactWithWrongIdTest() {
        contactDto.setId("70cf2381-ec5954c87ae75e9");
        given()
                .contentType(ContentType.JSON)
                .header(AUTH, token)
                .body(contactDto)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(404);

    }

                         ////TEST 5 PASS

    @Test
    public void updateContactNameWithWrongIdMessageTest() {
        contactDto.setId("70cf2381-ec5954c87ae75e9");
        given()
                .contentType(ContentType.JSON)
                .header(AUTH, token)
                .body(contactDto)
                .when()
                .put("contacts")
                .then()
                .assertThat().body("message", equalTo("Contact with id: " + contactDto.getId() + " not found in your contacts!"));
    }

                                     //TEST 6 FAILED(GET 403)
                  //According to the documentation, 401 Unauthorized as expected
    @Test
    public void updateContactWithOutAUTHTest(){

        ErrorDto errorDto = given()
                .contentType(ContentType.JSON)
                .header(AUTH, "")
                .body(contactDto)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(401)
                .extract().response().as(ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage(),"Unauthorized");

        System.out.println(errorDto.getMessage()+ " *** " +errorDto.getError());
    }








}

