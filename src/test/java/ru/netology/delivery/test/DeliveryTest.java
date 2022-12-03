package ru.netology.delivery.test;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;

import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

     @BeforeAll
    static void setupAll(){
        SelenideLogger.addListener("allure",new AllureSelenide());
    }
    @AfterAll
    static void tearDown(){
        SelenideLogger.removeListener("allure");
    }
    @Test
    @Description(value = "Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='name'] input").setValue(validUser.getName());
        SelenideElement dateInput = $("[data-test-id='date'] input");
        dateInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        dateInput.setValue(firstMeetingDate);
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $$("[data-test-id='agreement']").first().click();
        $$("button").find(exactText("Запланировать")).click();
        SelenideElement notification = $("[data-test-id='success-notification']");
        notification.$(".notification__title").shouldHave(exactText("Успешно!"));
        notification.$(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        dateInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        dateInput.setValue(secondMeetingDate);
        $$("button").find(exactText("Запланировать")).click();
        SelenideElement replanNotification = $("[data-test-id='replan-notification']");
        replanNotification.shouldBe(visible, Duration.ofMillis(15000));
        replanNotification.$(".notification__title").shouldHave(exactText("Необходимо подтверждение"));
        replanNotification.$(".notification__content").shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("button").find(exactText("Перепланировать")).click();
        notification.$(".notification__title").shouldHave(exactText("Успешно!"));
        notification.$(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }

    @Test
    @Description(value="Replan meeting when city is changed")
    void shouldSuccessfulPlanMeetingWhenCityChanged() {
        //изменить город вместе с датой
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        SelenideElement city = $("[data-test-id='city'] input");
        city.setValue(validUser.getCity());
        $("[data-test-id='name'] input").setValue(validUser.getName());
        SelenideElement dateInput = $("[data-test-id='date'] input");
        dateInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        dateInput.setValue(firstMeetingDate);
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $$("[data-test-id='agreement']").first().click();
        $$("button").find(exactText("Запланировать")).click();
        SelenideElement notification = $("[data-test-id='success-notification']");
        notification.shouldBe(visible, Duration.ofMillis(15000));
        notification.$(".notification__title").shouldHave(exactText("Успешно!"));
        notification.$(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        dateInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        dateInput.setValue(secondMeetingDate);
        city.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        city.setValue(DataGenerator.generateCity());
        $$("button").find(exactText("Запланировать")).click();
        notification.$(".notification__title").shouldHave(exactText("Успешно!"));
        notification.$(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }

    @Test
    @Description(value="Replan meeting when name is changed")
    void shouldSuccessfulPlanMeetingWhenNameChanged() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 5;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        SelenideElement city = $("[data-test-id='city'] input");
        city.setValue(validUser.getCity());
        SelenideElement name = $("[data-test-id='name'] input");
        name.setValue(validUser.getName());
        SelenideElement dateInput = $("[data-test-id='date'] input");
        dateInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        dateInput.setValue(firstMeetingDate);
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $$("[data-test-id='agreement']").first().click();
        $$("button").find(exactText("Запланировать")).click();
        SelenideElement notification = $("[data-test-id='success-notification']");
        notification.shouldBe(visible, Duration.ofMillis(15000));
        notification.$(".notification__title").shouldHave(exactText("Успешно!"));
        notification.$(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        dateInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        dateInput.setValue(secondMeetingDate);
        name.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        name.setValue(DataGenerator.generateName("ru"));
        $$("button").find(exactText("Запланировать")).click();
        notification.$(".notification__title").shouldHave(exactText("Успешно!"));
        notification.$(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));

    }

    @Test
    @Description(value="Replan meeting when phone is changed")
    void shouldSuccessfulPlanMeetingWhenPhoneChanged() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 5;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='name'] input").setValue(validUser.getName());
        SelenideElement dateInput = $("[data-test-id='date'] input");
        dateInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        dateInput.setValue(firstMeetingDate);
        SelenideElement phone = $("[data-test-id='phone'] input");
        phone.setValue(validUser.getPhone());
        $$("[data-test-id='agreement']").first().click();
        $$("button").find(exactText("Запланировать")).click();
        SelenideElement notification = $("[data-test-id='success-notification']");
        notification.shouldBe(visible, Duration.ofMillis(15000));
        notification.$(".notification__title").shouldHave(exactText("Успешно!"));
        notification.$(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        dateInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        dateInput.setValue(secondMeetingDate);
        phone.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        phone.setValue(DataGenerator.generatePhone("ru"));
        $$("button").find(exactText("Запланировать")).click();
        notification.$(".notification__title").shouldHave(exactText("Успешно!"));
        notification.$(".notification__content").shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }

}
