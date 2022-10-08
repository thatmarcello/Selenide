import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    String generateDate() {
        return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeAll
    static void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitRequest() {
        String date = generateDate();
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Москва");
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(date);
        form.$("[data-test-id=name] input").setValue("Пупов Михаил");
        form.$("[data-test-id=phone] input").setValue("+79088888888");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.exactText("Встреча успешно забронирована на " + date));

    }
}