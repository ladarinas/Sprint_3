package ru.yandex.praktikum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {

    private CourierSteps courierSteps;
    private int courierId;

    @Before
    public void setUp() {
        courierSteps = new CourierSteps();
    }

    @After
    public void deletedCourier(){
        courierSteps.delete(courierId);
    }

    @Test
    @DisplayName("Создание курьера")
    public void createCourier(){
        CourierRegister courier = CourierRegister.getRandom();
        boolean isCourierCreated = this.courierSteps.create(courier)
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");

        CourierLoginPass courierLoginPass = CourierLoginPass.from (courier);
        courierId = this.courierSteps.login(courierLoginPass)
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");

        assertTrue("Не удалось создать курьера",isCourierCreated);
        assertThat("Для курьера не создан id", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Создание курьера без имени")
    public void createCourierWithoutFirstName() {
        CourierRegister courier = CourierRegister.getRandomWithoutName();
        boolean isCourierCreated = this.courierSteps.create(courier)
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");

        CourierLoginPass courierLoginPass = CourierLoginPass.from (courier);
        courierId = this.courierSteps.login(courierLoginPass)
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");

        assertTrue("Не удалось создать курьера",isCourierCreated);
        assertThat("Для курьера не создан id", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void notCreateCouriersWithSameLogins() {
        CourierRegister courier = CourierRegister.getRandom();
        boolean isCourierCreated = this.courierSteps.create(courier)
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
        assertTrue("Не удалось создать курьера", isCourierCreated);

        CourierLoginPass courierLoginPass = CourierLoginPass.from (courier);
        courierId = this.courierSteps.login(courierLoginPass)
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
        assertThat("Для курьера не создан id", courierId, is(not(0)));

        String courierWithSameLogin = this.courierSteps.create(courier)
                .assertThat()
                .statusCode(409)
                .extract()
                .path("message");

        assertEquals("Создались два курьера с одним логином",
                "Этот логин уже используется", courierWithSameLogin);
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина")
    public void notCreateCourierWithoutLogin() {
        CourierRegister courier = new CourierRegister("", "qwerty", "Zlata");

        String withoutLogin = this.courierSteps.create(courier)
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
        assertThat( withoutLogin,
                equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без пароля")
    public void createCourierWithoutPasswordAndCheckStatusCodeError() {
        CourierRegister courier = new CourierRegister("ZlataLogin", "", "Zlata");

        String withoutPassword = this.courierSteps.create(courier)
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
        assertThat(withoutPassword,
                equalTo("Недостаточно данных для создания учетной записи"));
    }
}
