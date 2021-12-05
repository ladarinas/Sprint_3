package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest {
    private CourierSteps courierSteps;
    private int courierId;
    private String message;

    CourierRegister courier = CourierRegister.getRandom();

    @Before
    public void setUp() {
        courierSteps = new CourierSteps();
        courierSteps.create(courier);
    }

    @After
    public void deletedCourier(){
        courierSteps.delete(courierId);
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    public void authorizationCourier() {
        CourierLoginPass courierLoginPass = CourierLoginPass.from (courier);
        courierId = courierSteps.login(courierLoginPass)
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
        assertThat("Запрос не возвращает id", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Нельзя авторизоваться без логина")
    public void notAuthorizationCourierWithoutLogin() {
        CourierLoginPass courierLoginPass = new CourierLoginPass (null, courier.password);
        message = courierSteps.login (courierLoginPass)
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
        assertThat( message, equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Нельзя авторизоваться без пароля")
    public void notAuthorizationCourierWithoutPassword() {
        CourierLoginPass courierLoginPass = new CourierLoginPass (courier.login, null);
        message = courierSteps.login (courierLoginPass)
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
        assertThat(message, equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Нельзя авторизоваться с некорректным логином")
    public void notAuthorizationCourierFakeLogin() {
        String fakeLogin = RandomStringUtils.randomAlphabetic(10);
        CourierLoginPass courierLoginPass = new CourierLoginPass (fakeLogin, courier.password);
         message = courierSteps.login (courierLoginPass)
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
        assertThat(message, equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Нельзя авторизоваться под удаленным пользователем")
    public void notAuthorizationNoExistCourier() {
        CourierLoginPass courierLoginPass = CourierLoginPass.from (courier);
        courierId = courierSteps.login(courierLoginPass)
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
        courierSteps.delete(courierId);
        message = courierSteps.login (courierLoginPass)
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
        assertThat(message, equalTo("Учетная запись не найдена"));
    }
}
