package ru.yandex.praktikum;

public class CourierLoginPass {

    public final String login;
    public final String password;

    public CourierLoginPass(String login, String password) {
        this.login = login;
        this.password = password;

    }
    static public CourierLoginPass from (CourierRegister courier){
        return new CourierLoginPass(courier.login, courier.password);
    }
}
