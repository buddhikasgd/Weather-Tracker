package com.bud.weather.api;

public class HelloWorld {
    public static void main(String[] args) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q={city},{country}&appid=b2e94810d748ed12518632c57f5e3835";
        System.out.println(url.replace("{city}", "texas").replace("{country}", "us"));
    }
}
