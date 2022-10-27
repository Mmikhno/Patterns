package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        String date = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String generateCity() {
        String str =
                "Магас,Йошкар-Ола,Иваново,Челябинск,Майкоп,Горно-Алтайск,Уфа,Улан-Удэ,Махачкала,Нальчик,Элиста,Черкесск,"
                        + "Петрозаводск,Сыктывкар,Симферополь,Саранск,Якутск,Владикавказ,Казань,Кызыл,Ижевск,Абакан,Грозный,Чебоксары,Барнаул,Чита,Петропавловск-Камчатский,Краснодар,"
                        + "Красноярск,Пермь,Владивосток,Ставрополь,Хабаровск,Благовещенск,Архангельск,Астрахань,Белгород,Брянск,Владимир,"
                        + "Волгоград,Вологда,Воронеж,Иркутск,Калининград,Калуга,Кемерово,Киров,Кострома,Курган,Курск,Санкт-Петербург,Липецк,"
                        + "Магадан,Красногорск,Мурманск,Нижний Новгород,Великий Новгород,Новосибирск,Омск,Оренбург,Орёл,Пенза,Псков,"
                        + "Ростов-на-Дону,Рязань,Самара,Саратов,Южно-Сахалинск,Екатеринбург,Смоленск,Тамбов,Тверь,Томск,Тула,"
                        + "Тюмень,Ульяновск,Ярославль,Москва,Санкт-Петербург,Севастополь,Биробиджан,Нарьян-Мар,Ханты-Мансийск,Анадырь,Салехард";
        String[] arrayOfCity;
        arrayOfCity = str.split(",");
        int rnd = new Random().nextInt(arrayOfCity.length);
        String city = arrayOfCity[rnd];
        return city;
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            UserInfo user = new UserInfo(DataGenerator.generateCity(), DataGenerator.generateName(locale), DataGenerator.generatePhone(locale));
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }


}
