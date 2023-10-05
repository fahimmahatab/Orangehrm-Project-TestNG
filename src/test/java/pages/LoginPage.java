package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoginPage {
    @FindBy(name = "username")
    WebElement txtUsername;

    @FindBy(name = "password")
    WebElement txtPassword;

    @FindBy(tagName = "button")
    WebElement btnLogin;
    @FindBy(className = "oxd-userdropdown-name")
    public WebElement btnUserProfile;
    @FindBy(className = "oxd-userdropdown-link")
   public List<WebElement> linkSubItems;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void doLogin(String username, String password) {
        txtUsername.sendKeys(username);
        txtPassword.sendKeys(password);
        btnLogin.click();
    }

    public void doLogout(){
        btnUserProfile.click();
        linkSubItems.get(3).click();
    }
}
