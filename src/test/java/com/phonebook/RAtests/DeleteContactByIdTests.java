package com.phonebook.RAtests;

import com.phonebook.dto.ContactDto;
import com.phonebook.dto.MessageDto;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class DeleteContactByIdTests extends TestBase{

     String id;

    @BeforeMethod
    public void precondition(){

        ContactDto dto = ContactDto.builder()
                .name("Lila")
                .lastName("Priss")
                .email("irinapr11@gm.com")
                .phone("123456788711")
                .address("Berlin")
                .description("teacher")
                .build();

       String message=
           given()
                .contentType(ContentType.JSON)
                .header(AUTH, token)
                .body(dto)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().path("message");

       String[] split = message.split(": ");
       id = split[1];
    }

    @Test
    public void deleteContactByIdSuccessTest(){
        MessageDto dto =
                given()
                .header(AUTH, token)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(MessageDto.class);

        System.out.println(dto.getMessage());
    }

    @Test
    public void deleteContactByIdSuccessMessageTest(){

                given()
                        .header(AUTH, token)
                        .when()
                        .delete("contacts/" + id)
                        .then()
                        .assertThat().statusCode(200)
                        .assertThat().body("message",equalTo("Contact was deleted!"));

    }
}

