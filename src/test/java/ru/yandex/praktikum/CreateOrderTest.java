package ru.yandex.praktikum;

import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Before;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private OrderSteps orderSteps;
    private final String[] color;

    @Before
    public void setUp() {
        orderSteps = new OrderSteps();
    }

    public CreateOrderTest(String color) {
        this.color = new String[]{color};
    }

    @Parameterized.Parameters
    public static Object[] getColorBody() {
        return new Object[][]{
                {"GREY"},
                {"BLACK"},
                {"GREY\" , \"BLACK"},
                {""}
        };
    }

    @Test
    @DisplayName("Создание заказа для разных цветов самоката")
    public void selectScooterColor() {

        OrderData orderData = OrderData.getRandom();
        orderData.setColor(color);
        int trackId = orderSteps.createOrder(orderData).assertThat()
                .statusCode(201)
                .extract()
                .path("track");
        assertThat("Некорректный track", trackId, is(not(0)));
    }
}
