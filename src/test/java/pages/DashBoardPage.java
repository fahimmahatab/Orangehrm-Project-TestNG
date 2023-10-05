package pages;

import config.EmployeeModel;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.security.PublicKey;
import java.util.List;

public class DashBoardPage {
    @FindBy(className = "oxd-main-menu-item--name")
    public List<WebElement> menuItems;

    @FindBy(className = "oxd-button")
    public List<WebElement> buttons;
    @FindBy(className = "oxd-input")
    public List<WebElement> formTextFields;

    //    @FindBy(className = "oxd-autocomplete-text-input")
//    public List<WebElement> formAutoFields;
    @FindBy(className = "oxd-radio-input")
    public List<WebElement> radioBtn;
    @FindBy(className = "oxd-select-text-input")
    public List<WebElement> dropDown;
    @FindBy(className = "oxd-switch-input")
    public WebElement btnSwitch;

    public DashBoardPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void createUser(EmployeeModel model) throws InterruptedException {

        buttons.get(2).click(); // click add button
        Thread.sleep(3000);
        formTextFields.get(1).sendKeys(model.getFirstName());
        formTextFields.get(3).sendKeys(model.getLastName());
        formTextFields.get(4).click();
        formTextFields.get(4).sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        formTextFields.get(4).sendKeys(model.getemployeeId());
        btnSwitch.click();     // toggle Switch
        formTextFields.get(5).sendKeys(model.userName);
        formTextFields.get(6).sendKeys(model.password);
        formTextFields.get(7).sendKeys(model.password); //Confirm Password
        buttons.get(1).click(); // save data
    }
}
