package ru.yandex.praktikum;

import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.*;

public class OrderListTest {

    private OrderSteps orderSteps;

    @Before
    public void setUp() {
        orderSteps = new OrderSteps();
    }

    @Test
    @DisplayName("Получение списка заказов")
    public void getListOfOrdersCheckStatusCode200AndReturnListOfOrders() {
        orderSteps.orderResponseBody().then().
                assertThat().body("orders.size()", is(not(0)));
    }
}
