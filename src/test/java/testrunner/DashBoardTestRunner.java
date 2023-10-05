package testrunner;

import com.github.javafaker.Faker;
import config.EmployeeModel;
import config.Setup;
import io.qameta.allure.Allure;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.DashBoardPage;
import pages.LoginPage;
import utils.Utils;

import java.io.IOException;

public class DashBoardTestRunner extends Setup {
    DashBoardPage dashboardPage;
    LoginPage loginPage;

    @BeforeTest(groups = "smoke")
    public void login() throws InterruptedException, IOException, ParseException {
        loginPage = new LoginPage(driver);
        JSONArray jsonArray = Utils.readJSONList("./src/test/resources/employees.json");
        JSONObject empObj = (JSONObject) jsonArray.get(0);
        if (System.getProperty("userName") != null && (System.getProperty("password") != null)) {
            loginPage.doLogin(System.getProperty("userName"), System.getProperty("password"));
        } else {
            loginPage.doLogin(empObj.get("userName").toString(), empObj.get("password").toString());
        }
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(driver.findElement(By.className("oxd-userdropdown-img")).isDisplayed());
        softAssert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
        softAssert.assertAll();
        Allure.description("User login successfully");
        dashboardPage = new DashBoardPage(driver);
        dashboardPage.menuItems.get(1).click(); // click PIM
        Thread.sleep(2000);
    }

    @Test(priority = 1, description = "Check if Search button is working")
    public void clickonSearchButton() throws InterruptedException {

        driver.findElement(By.cssSelector("[type='submit']")).click();
        Thread.sleep(2000);
    }

    @Test(priority = 2, description = "Check if Reset button is working")
    public void clickonResetButton() throws InterruptedException {
        driver.findElement(By.cssSelector("[type=reset]")).click();
        Thread.sleep(2000);
        dashboardPage = new DashBoardPage(driver);
        dashboardPage.menuItems.get(1).click(); // click PIM
    }

    @Test(priority = 3, description = "Create new user keep firstname box blank")
    public void firstNamechcreateUser() throws InterruptedException {
        DashBoardPage dashBoardPage = new DashBoardPage(driver);
        dashboardPage.buttons.get(2).click();
        dashboardPage.formTextFields.get(3).sendKeys("Mahatab");
        dashboardPage.btnSwitch.click();
        Thread.sleep(2000);
        dashboardPage.formTextFields.get(5).sendKeys("abc123");
        dashboardPage.formTextFields.get(6).sendKeys("Pass123456");
        dashboardPage.formTextFields.get(7).sendKeys("Pass123456");
        dashboardPage.buttons.get(1).click(); // save data
        String textActual = driver.findElement(By.className("oxd-input-group__message")).getText();
        String textExpected = "Required";
        Assert.assertEquals(textActual, textExpected);
        Thread.sleep(4000);

        dashboardPage = new DashBoardPage(driver);
        dashboardPage.menuItems.get(1).click(); // click PIM

    }

    @Test(priority = 4, description = "Create new user keep lastname box blank")
    public void lastNamechcreateUser() throws InterruptedException {
        DashBoardPage dashBoardPage = new DashBoardPage(driver);
        dashboardPage.buttons.get(2).click();
        dashboardPage.formTextFields.get(1).sendKeys("Fahim");
        dashboardPage.btnSwitch.click();
        Thread.sleep(2000);
        dashboardPage.formTextFields.get(5).sendKeys("abc123");
        dashboardPage.formTextFields.get(6).sendKeys("Pass123456");
        dashboardPage.formTextFields.get(7).sendKeys("Pass123456");
        dashboardPage.buttons.get(1).click(); // save data
        String textActual = driver.findElement(By.className("oxd-input-group__message")).getText();
        String textExpected = "Required";
        Assert.assertEquals(textActual, textExpected);
        Thread.sleep(4000);

        dashboardPage = new DashBoardPage(driver);
        dashboardPage.menuItems.get(1).click(); // click PIM

    }

    @Test(priority = 5, description = "Check if password & confirm password do not match")
    public void passwordNotMatchcreateUser() throws InterruptedException {
        DashBoardPage dashBoardPage = new DashBoardPage(driver);
        dashboardPage.buttons.get(2).click();
        dashboardPage.formTextFields.get(1).sendKeys("Fahim");
        dashboardPage.formTextFields.get(3).sendKeys("Mahatab");
        Thread.sleep(2000);
        dashboardPage.btnSwitch.click();
        dashboardPage.formTextFields.get(5).sendKeys("abc123");
        dashboardPage.formTextFields.get(6).sendKeys("Pass12345");
        dashboardPage.formTextFields.get(7).sendKeys("Pass123456");
        dashboardPage.buttons.get(1).click(); // save data
        String textActual = driver.findElement(By.className("oxd-input-group__message")).getText();
        String textExpected = "Passwords do not match";
        Assert.assertEquals(textActual, textExpected);
        Thread.sleep(4000);

        dashboardPage = new DashBoardPage(driver);
        dashboardPage.menuItems.get(1).click(); // click PIM

    }

    @Test(priority = 6, description = "Check if New Users username is invalid and user created unsuccessfully")
    public void invalidUsernamecreateUser() throws InterruptedException {
        DashBoardPage dashBoardPage = new DashBoardPage(driver);
        dashboardPage.buttons.get(2).click();
        dashboardPage.formTextFields.get(1).sendKeys("Fahim");
        dashboardPage.formTextFields.get(3).sendKeys("Mahatab");
        Thread.sleep(3000);
        dashboardPage.btnSwitch.click();
        dashboardPage.formTextFields.get(5).sendKeys("abc");
        dashboardPage.formTextFields.get(6).sendKeys("Pass12345");
        dashboardPage.formTextFields.get(7).sendKeys("Pass12345");
        dashboardPage.buttons.get(1).click(); // save data
        String textActual = driver.findElement(By.className("oxd-input-group__message")).getText();
        String textExpected = "Should be at least 5 characters";
        Assert.assertEquals(textActual, textExpected);
        Thread.sleep(4000);

        dashboardPage = new DashBoardPage(driver);
        dashboardPage.menuItems.get(1).click(); // click PIM

    }

    @Test(priority = 7, description = "Check if New User is created unsuccessfully")
    public void invaldPasscreateUser() throws IOException, ParseException, InterruptedException {
        DashBoardPage dashBoardPage = new DashBoardPage(driver);
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String employeeId = String.valueOf(faker.random().nextInt(1, 20));
        String userName = faker.name().username();
        String password = faker.internet().password(1, 2);

        EmployeeModel model = new EmployeeModel();
        model.setFirstName(firstName);
        model.setLastName(lastName);
        model.setemployeeId(String.valueOf(employeeId));
        model.setUserName(userName);
        model.setPassword(password);

        dashBoardPage.createUser(model);
        String textActual = driver.findElement(By.className("oxd-input-group__message")).getText();
        String textExpected = "Should have at least 7 characters";
        Assert.assertEquals(textActual, textExpected);
        Thread.sleep(5000);
        dashboardPage = new DashBoardPage(driver);
        dashboardPage.menuItems.get(1).click(); // click PIM

    }

    @Test(priority = 8, description = "Check if New User is created successfully with Employee id box blank")
    public void createUserEmployeeIDBlank() throws IOException, ParseException, InterruptedException {
        DashBoardPage dashBoardPage = new DashBoardPage(driver);
        dashboardPage.buttons.get(2).click();
        dashboardPage.formTextFields.get(1).sendKeys("Faarhan");
        dashboardPage.formTextFields.get(3).sendKeys("Akkhiaub");
        dashboardPage.formTextFields.get(4).sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        dashboardPage.btnSwitch.click();
        Thread.sleep(2000);
        dashboardPage.formTextFields.get(5).sendKeys("far7758");
        dashboardPage.formTextFields.get(6).sendKeys("Pass12345");
        dashboardPage.formTextFields.get(7).sendKeys("Pass12345");
        dashboardPage.buttons.get(1).click(); // save data
        Thread.sleep(7000);

        dashboardPage = new DashBoardPage(driver);
        dashboardPage.menuItems.get(1).click(); // click PIM
    }

    @Test(priority = 9, description = "Check if New User is created successfully")
    public void createUser() throws IOException, ParseException, InterruptedException {
        DashBoardPage dashBoardPage = new DashBoardPage(driver);
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String employeeId = String.valueOf(faker.random().nextInt(1, 20));
        String userName = faker.name().username();
        String password = faker.internet().password(9, 12, true, true, true);

        EmployeeModel model = new EmployeeModel();
        model.setFirstName(firstName);
        model.setLastName(lastName);
        model.setemployeeId(String.valueOf(employeeId));
        model.setUserName(userName);
        model.setPassword(password);

        dashBoardPage.createUser(model);
        String textTitleExpected = driver.findElement(By.xpath("//*[contains(text(),\"Personal Details\")]")).getText();
        Thread.sleep(5000);
        if (textTitleExpected.contains("Personal Details")) {
            Utils.saveEmployeeInfo(model);
        }
        Allure.description("User created successfully");
        Thread.sleep(3000);
    }

    @Test(priority = 10, description = "Check if employeeId search unsuccessfully")
    public void searchByinvalidEmployeeId() throws IOException, ParseException, InterruptedException {
        dashboardPage = new DashBoardPage(driver);
        dashboardPage.menuItems.get(1).click(); // click PIM
        dashboardPage.formTextFields.get(1).sendKeys("d");
        driver.findElement(By.cssSelector("[type='submit']")).click();
        Utils.scroll(driver, 0, 500);
        Thread.sleep(3000);
    }

    @Test(priority = 11, groups = "smoke", description = "Check if employeeId search successfully")
    public void searchByEmployeeId() throws IOException, ParseException, InterruptedException {
        dashboardPage = new DashBoardPage(driver);
        dashboardPage.menuItems.get(1).click(); // click PIM
        dashboardPage.formTextFields.get(1).click();
        JSONArray empList = Utils.readJSONList("./src/test/resources/employees.json");
        JSONObject empObj = (JSONObject) empList.get(empList.size() - 1);
        String employeeId = empObj.get("employeeId").toString();
        dashboardPage.formTextFields.get(1).sendKeys(employeeId);
        driver.findElement(By.cssSelector("[type='submit']")).click();
        Utils.scroll(driver, 0, 500);
        Thread.sleep(3000);
    }

    @Test(priority = 12, groups = "smoke", description = "Check if logout Seccessfully")
    public void logout() {
        loginPage = new LoginPage(driver);
        loginPage.doLogout();
        String textActual = driver.findElement(By.className("orangehrm-login-title")).getText();
        String textExpected = "Login";
        Assert.assertEquals(textActual, textExpected);
    }

    @Test(priority = 13, description = "Invalid user can not login")
    public void invalidUserLogin() throws IOException, ParseException, InterruptedException {
        loginPage = new LoginPage(driver);
        loginPage.doLogin("fahim", "1234");
        String textActual = driver.findElement(By.className("oxd-alert-content-text")).getText();
        String textExpect = "Invalid credentials";
        Assert.assertTrue(textActual.contains(textExpect));
        Thread.sleep(3000);
    }

    @Test(priority = 14, groups = "smoke", description = "New user can login successfully")
    public void userLogin() throws IOException, ParseException, InterruptedException {
        loginPage = new LoginPage(driver);
        JSONArray empList = Utils.readJSONList("./src/test/resources/employees.json");
        JSONObject empObj = (JSONObject) empList.get(empList.size() - 1);
        String userName = (String) empObj.get("userName");
        String password = (String) empObj.get("password");
        loginPage.doLogin(userName, password);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(driver.findElement(By.className("oxd-userdropdown-img")).isDisplayed());
        softAssert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
        softAssert.assertAll();
        Allure.description("User login successfully");
        Thread.sleep(5000);
    }

    @Test(priority = 15, description = "Check if Scroll working,set Gender not Saved")
    public void genderNotSaved() throws InterruptedException {
        dashboardPage.menuItems.get(2).click(); // click My Info
        Utils.scroll(driver, 0, 700);
        Thread.sleep(2000);
        dashboardPage.radioBtn.get(0).click();
    }

    @Test(priority = 16, description = "Check if Scroll working,set Gender & Blood group")
    public void scrollSetGenderBloodGroup() throws InterruptedException {
        dashboardPage.menuItems.get(2).click(); // click My Info
        Utils.scroll(driver, 0, 700);
        Thread.sleep(2000);
        dashboardPage.radioBtn.get(0).click();
        dashboardPage.buttons.get(0).click();
        Utils.scroll(driver, 0, 800);
        dashboardPage.dropDown.get(2).click();
        Thread.sleep(3000);
        dashboardPage.dropDown.get(2).sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER);
        Thread.sleep(7000);
        dashboardPage.buttons.get(1).click();
    }

    @Test(priority = 17, groups = "smoke", description = "Check if Blood group update successfully")
    public void updateBloodGroup() throws InterruptedException {
        dashboardPage.menuItems.get(2).click(); // click My Info
        Utils.scroll(driver, 0, 1300);
        Thread.sleep(2000);
        dashboardPage.dropDown.get(2).click();
        Thread.sleep(3000);
        dashboardPage.dropDown.get(2).sendKeys(Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ARROW_DOWN, Keys.ENTER);
        Thread.sleep(3000);
        dashboardPage.buttons.get(1).click();
        Thread.sleep(3000);
    }

    @Test(priority = 18, groups = "smoke", description = "Check if User logout Seccessfully")
    public void userlogout() {
        loginPage = new LoginPage(driver);
        loginPage.doLogout();
        String textActual = driver.findElement(By.className("orangehrm-login-title")).getText();
        String textExpected = "Login";
        Assert.assertEquals(textActual, textExpected);
    }
}
