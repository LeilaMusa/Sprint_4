package Tests;

import PageObject.HomePage;
import PageObject.OrderPage;
import PageObject.RentPage;
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

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;

    private final String name;
    private final String surname;
    private final String address;
    private final String metroStationFromOrder;
    private final String phoneNumber;
    private final String rentalDate;
    private final String comment;
    private final String driverName;

    public OrderTest(String name, String surname, String address, String metroStationFromOrder, String phoneNumber, String rentalDate, String comment, String driverName) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metroStationFromOrder = metroStationFromOrder;
        this.phoneNumber = phoneNumber;
        this.rentalDate = rentalDate;
        this.comment = comment;
        this.driverName = driverName;
    }

    @Parameterized.Parameters(name = "Тестовые данные для оформления заказа")
    public static Object[][] newOrderParams() {
        return new Object[][]{
                {"Боб", "Ким", "Адрес", "Сокольники", "79800989090", "21.12.2024", "без самоката", "Chrome"},
                {"Джо", "Ленон", "Гашека 7", "Маяковская", "+79169998877", "21122024", "жду самокат", "Chrome"},
                {"Луи", "Армстронг", "Адрес", "Сокол", "79998887766", "21.12.2024", "без самокат", "Firefox"},
                {"Дим", "Сан", "Красина 8", "Тверская", "+79169998877", "21122024", "самый красивый самокат", "Firefox"},
        };
    }

    @Before
    public void websiteLaunch() {
        if (driverName.equals("Chrome")) {
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
//        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
            driver = new ChromeDriver(options);
        } else if (driverName.equals("Firefox")) {
            System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
            FirefoxOptions options = new FirefoxOptions();
//        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
            driver = new FirefoxDriver(options);
        }
    }

    @Test
    public void createOrderWithHeaderOrderButtonTest() {

        HomePage homePage = new HomePage(driver);

        driver.get("https://qa-scooter.praktikum-services.ru/");
        homePage.waitForLoadOrderButton();
        homePage.openOrderPageWithHeaderOrderButton();

        OrderPage orderPage = new OrderPage(driver);
        orderPage.makingOrder(name, surname, address, metroStationFromOrder, phoneNumber);
        orderPage.clickNextButton();
        RentPage rentPage = new RentPage(driver);
        rentPage.makingRentPageScooter(rentalDate, comment);
        assertEquals("Не удалось оформить заказ", true, rentPage.getSuccessAlert().contains("Заказ оформлен"));
    }

    @Test
    public void createOrderMiddleOrderButtonTest() {

        HomePage homePage = new HomePage(driver);

        driver.get("https://qa-scooter.praktikum-services.ru/");
        homePage.waitForLoadOrderButton();
        homePage.openOrderPageWithMiddleOrderButton();

        OrderPage orderPage = new OrderPage(driver);
        orderPage.makingOrder(name, surname, address, metroStationFromOrder, phoneNumber);
        orderPage.clickNextButton();
        RentPage rentPage = new RentPage(driver);
        rentPage.makingRentPageScooter(rentalDate, comment);
        assertEquals("Не удалось оформить заказ", true, rentPage.getSuccessAlert().contains("Заказ оформлен"));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
