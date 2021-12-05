package ru.yandex.praktikum;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierRegister {
    public final String login;
    public final String password;
    public final String firstName;

    public CourierRegister(String login, String password, String firstName ) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static CourierRegister getRandom(){
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new CourierRegister(login, password, firstName);
    }

    public static CourierRegister getRandomWithoutName(){
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        return new CourierRegister(login, password, null);
    }

}
