package com.qa.scooter.tests.order;

import com.qa.scooter.pageobject.home.HomePage;
import com.qa.scooter.pageobject.order.OrderPage;
import com.qa.scooter.pageobject.rent.RentPage;
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

    // Тестовые данные как константы
    public static final String[] NAMES = {"Боб", "Джо", "Луи", "Дим"};
    public static final String[] SURNAMES = {"Ким", "Ленон", "Армстронг", "Сан"};
    public static final String[] ADDRESSES = {"Адрес", "Гашека 7", "Адрес", "Красина 8"};
    public static final String[] METRO_STATIONS = {"Сокольники", "Маяковская", "Сокол", "Тверская"};
    public static final String[] PHONE_NUMBERS = {"79800989090", "+79169998877", "79998887766", "+79169998877"};
    public static final String[] RENTAL_DATES = {"21.12.2024", "21122024", "21.12.2024", "21122024"};
    public static final String[] COMMENTS = {"без самоката", "жду самокат", "без самокат", "самый красивый самокат"};
    public static final String[] DRIVER_NAMES = {"Chrome", "Chrome", "Firefox", "Firefox"};

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
        Object[][] data = new Object[4][8];
        for (int i = 0; i < 4; i++) {
            data[i][0] = NAMES[i];
            data[i][1] = SURNAMES[i];
            data[i][2] = ADDRESSES[i];
            data[i][3] = METRO_STATIONS[i];
            data[i][4] = PHONE_NUMBERS[i];
            data[i][5] = RENTAL_DATES[i];
            data[i][6] = COMMENTS[i];
            data[i][7] = DRIVER_NAMES[i];
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

    @Test
    public void createOrderWithHeaderOrderButtonTest() {
        HomePage homePage = new HomePage(driver);
        homePage.openHomePage();
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
        homePage.openHomePage();
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