package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormPage {

    private final SelenideElement firstName = $x("//input[@id='firstName']");

    private final SelenideElement lastName = $x("//input[@id='lastName']");

    private final SelenideElement userEmail = $x("//input[@id='userEmail']");

    private final SelenideElement mobileField = $x("//input[@placeholder='Mobile Number']");

    private final SelenideElement dateOfBirthField = $x("//input[@id='dateOfBirthInput']");

    private final SelenideElement monthList = $x("//option[text()='January']//..");

    private final SelenideElement yearList = $x("//option[text()='1900']//..");

    private final SelenideElement subjectsField = $x("//input[@id='subjectsInput']");

    private final SelenideElement sportsCheckBox = $x("//label[text()='Sports']//..");

    private final SelenideElement readingCheckBox = $x("//label[text()='Reading']//..");

    private final SelenideElement musicCheckBox = $x("//label[text()='Music']//..");

    private final SelenideElement uploadButton = $x("//input[@id='uploadPicture']");

    private final SelenideElement addressField = $x("//textarea[@placeholder='Current Address']");

    private final SelenideElement stateSelector = $x("//div[text()='Select State']");

    private final SelenideElement citySelector = $x("//div[text()='Select City']");

    private final SelenideElement submitButton = $x("//button[text()='Submit']");

    private final SelenideElement closeTableButton = $x("//button[text()='Close']");


    public FormPage setFirstName(String value) {
        closeTableButton.shouldNotBe(visible, Duration.ofSeconds(5));
        firstName.sendKeys(value);
        return this;
    }

    public FormPage setLastName(String value) {
        lastName.sendKeys(value);
        return this;
    }

    public FormPage setUserEmail(String value) {
        userEmail.sendKeys(value);
        return this;
    }

    public FormPage selectGender(String gender) {
        setGender(gender).click();
        return this;
    }

    public FormPage setMobile(String value) {
        mobileField.sendKeys(value);
        return this;
    }

    public FormPage setDateOfBirth(String month, int year, int number) {
        dateOfBirthField.click();
        monthList.click();
        chooseMonth(month).click();

        yearList.click();
        chooseYear(year).click();
        chooseNumber(number).click();
        return this;
    }

    public FormPage setSubjects(String txt) {
        subjectsField.sendKeys(txt);
        getSubject(txt).click();
        return this;
    }

    public FormPage uploadPicture(File file) {
        uploadButton.uploadFile(file);
        return this;
    }

    public FormPage setHobbies() {
        sportsCheckBox.click();
        readingCheckBox.click();
        musicCheckBox.click();
        return this;
    }

    public FormPage setAddress(String address) {
        addressField.sendKeys(address);
        return this;
    }

    public FormPage setStateAndCity() {
        stateSelector.click();
        $x("//div[text()='NCR']").click();
        citySelector.click();
        $x("//div[text()='Delhi']").click();
        return this;
    }

    public FormPage setPairsOfStateAndCity(String[] pair) {
        stateSelector.click();
        Selenide.$x("//div[text()='" + pair[0] + "']").click();
        citySelector.click();
        Selenide.$x("//div[text()='" + pair[1] + "']").click();
        return this;
    }

    public FormPage submit() {
        submitButton.click();
        return this;
    }

    public FormPage checkSubmitIsSuccessful() {
        $x("//div[text()='Thanks for submitting the form']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='Student Name']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='Test Testov']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='Student Email']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='testov@gmail.com']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='Gender']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='Male']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='Mobile']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='7999000111']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='Date of Birth']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='01 January,2000']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='Subjects']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='Maths']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='Hobbies']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='Sports, Reading, Music']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='Picture']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='cat.jpg']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='Address']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='Sovetskaya st. 23']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='State and City']").shouldBe(visible, Duration.ofSeconds(10));
        $x("//td[text()='NCR Delhi']").shouldBe(visible, Duration.ofSeconds(10));
        return this;
    }

    public FormPage checkGenderIsValid(String gender) {
        Selenide.$x("//td[text()='Gender']/following-sibling::td").shouldHave(Condition.text(gender));
        return this;
    }

    public FormPage checkSubjectIsValid(String subject) {
        Selenide.$x("//td[text()='Subjects']/following-sibling::td").shouldHave(Condition.text(subject));
        return this;
    }

    public FormPage checkStateAndCityAreValid(String[] pair) {
        Selenide.$x("//td[text()='State and City']/following-sibling::td").shouldHave(com.codeborne.selenide.Condition.text(pair[0] + " " + pair[1]));
        return this;
    }

    public FormPage closeTableAfterSubmit() {
        closeTableButton.click();
        return this;
    }

    public void shouldHighlightFieldsWithRedBorder() {
        sleep(1000);
        String borderColorFirstName = firstName.getCssValue("border-color");
        assertEquals("rgb(220, 53, 69)", borderColorFirstName);
        String borderColorLastName = lastName.getCssValue("border-color");
        assertEquals("rgb(220, 53, 69)", borderColorLastName);
        String borderColorMobileNumber = mobileField.getCssValue("border-color");
        assertEquals("rgb(220, 53, 69)", borderColorMobileNumber);
    }

    private SelenideElement setGender(String gender) {
        return $x(String.format("//input[@name='gender' and @value='%s']//..", gender));
    }

    private SelenideElement chooseMonth(String month) {
        return $x(String.format("//option[text()='%s']", month));
    }

    private SelenideElement chooseYear(int value) {
        return $x(String.format("//option[@value='%d']", value));
    }

    private SelenideElement chooseNumber(int number) {
        return $x(String.format("//div[text()='%d']", number));
    }

    private SelenideElement getSubject(String subject) {
        return $x(String.format("(//div[text()='%s'])[2]", subject));
    }
}
