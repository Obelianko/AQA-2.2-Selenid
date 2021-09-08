package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrderTest {
    String deliveryDate(int plusDays) {
        return LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void deliveryRequest() {
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id=city] input").setValue("Санкт-Петербург");
        form.$("[data-test-id=date] input").doubleClick();
        form.$("[data-test-id=date] input").sendKeys(Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(deliveryDate(3));
        form.$("[data-test-id=name] input").setValue("Белянко Ольга");
        form.$("[data-test-id=phone] input").setValue("+79211111111");
        form.$("[data-test-id=agreement] .checkbox__box").click();
        form.$("[role=button] .button__content").click();
        $("[data-test-id=notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Встреча успешно забронирована на " + deliveryDate(3)));
    }
}
