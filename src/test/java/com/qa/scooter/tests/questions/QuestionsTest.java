package com.qa.scooter.tests.questions;

import com.qa.scooter.pageobject.home.HomePage;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

@RunWith(Parameterized.class)
public class QuestionsTest {

    private WebDriver driver;

    // Константы для тестовых данных
    public static final String[] EXPECTED_ANSWERS = new String[]{
            "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
            "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
            "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
            "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
            "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
            "Да, обязательно. Всем самокатов! И Москве, и Московской области."
    };

    private final int questionIndex;
    private final String driverName;

    public QuestionsTest(int questionIndex, String driverName) {
        this.questionIndex = questionIndex;
        this.driverName = driverName;
    }

    // Параметры для каждого теста (все вопросы для обоих браузеров)
    @Parameterized.Parameters(name = "Тест браузера {1}, Вопрос {0}")
    public static Object[][] newOrderParams() {
        Object[][] data = new Object[EXPECTED_ANSWERS.length * 2][2]; // Для 8 вопросов и 2 браузеров
        for (int i = 0; i < EXPECTED_ANSWERS.length; i++) {
            data[i * 2][0] = i; // Индекс вопроса
            data[i * 2][1] = "Chrome"; // Первый браузер - Chrome
            data[i * 2 + 1][0] = i; // Индекс вопроса
            data[i * 2 + 1][1] = "Firefox"; // Второй браузер - Firefox
        }
        return data;
    }

    @Before
    public void websiteLaunch() {
        if (driverName.equals("Chrome")) {
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
        } else if (driverName.equals("Firefox")) {
            System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
            FirefoxOptions options = new FirefoxOptions();
            driver = new FirefoxDriver(options);
        }
    }

    // Тест для проверки одного конкретного вопроса
    @Test
    public void checkSingleQuestionTest() {
        HomePage homePage = new HomePage(driver);

        // Открытие главной страницы
        homePage.openHomePage();
        homePage.clickCookieButton();
        homePage.scrollPageToEndList();

        // Клик по вопросу и проверка ответа
        homePage.clickQuestion(questionIndex);
        homePage.checkAnswerText(EXPECTED_ANSWERS[questionIndex], questionIndex);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
