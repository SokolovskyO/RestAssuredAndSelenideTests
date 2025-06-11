package ui;

import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.FormPage;

import java.io.File;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;

public class FormTests {

    private final static String GENDER = "Male"; // Male/Female/Other
    private final static List<String> GENDER_LIST = List.of("Male", "Female", "Other");
    private final static List<String> SUBJECT_LIST = List.of("Maths", "Physics", "English");
    private final static String[][] STATE_CITY_PAIRS = {
            {"NCR", "Delhi"},
            {"Uttar Pradesh", "Agra"},
            {"Haryana", "Karnal"}
    };
    private final static String FIRST_NAME = "Test";
    private final static String LAST_NAME = "Testov";
    private final static String EMAIL = "testov@gmail.com";
    private final static String MOBILE_PHONE = "7999000111";
    private final static File PICTURE = new File("cat.jpg");

    @BeforeAll
    static void setupAll() {
        Configuration.browser = Browsers.CHROME;
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.headless = false;
    }

    @BeforeEach
    void setupEach() {
        open("/automation-practice-form");
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.executeJavaScript("sessionStorage.clear();");
    }

    @Test
    @DisplayName("Проверка успешной регистрации")
    void registrationFormTest() {
        FormPage formPage = new FormPage();
        formPage
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setUserEmail(EMAIL)
                .selectGender(GENDER)
                .setMobile(MOBILE_PHONE)
                .setDateOfBirth("January", 2000, 1)
                .setSubjects("Maths")
                .uploadPicture(PICTURE)
                .setHobbies()
                .setAddress("Sovetskaya st. 23")
                .setStateAndCity()
                .submit()
                .checkSubmitIsSuccessful();
    }

    @Test
    @DisplayName("Проверка отображения красной рамки, при незаполнении данных")
    void fieldsShouldBeRedAfterValidErrorTest() {
        FormPage formPage = new FormPage();
        formPage.submit();
        formPage.shouldHighlightFieldsWithRedBorder();
    }

    @Test
    @DisplayName("Проверка выбора разных гендеров")
    void shouldSelectDifferentGenders() {
        for (String gender : GENDER_LIST) {
            FormPage formPage = new FormPage();
            formPage
                    .setFirstName(FIRST_NAME)
                    .setLastName(LAST_NAME)
                    .selectGender(gender)
                    .setMobile(MOBILE_PHONE)
                    .setDateOfBirth("January", 2000, 1)
                    .setSubjects("Maths")
                    .setHobbies()
                    .setAddress("Sovetskaya st. 23")
                    .setStateAndCity()
                    .submit();
            formPage.checkGenderIsValid(gender);
            formPage.closeTableAfterSubmit();
        }
    }

    @Test
    @DisplayName("Проверка выбора разных предметов")
    void shouldSelectDifferentSubjects() {
        for (String subject : SUBJECT_LIST) {
            FormPage formPage = new FormPage();
            formPage
                    .setFirstName(FIRST_NAME)
                    .setLastName(LAST_NAME)
                    .selectGender(GENDER)
                    .setMobile(MOBILE_PHONE)
                    .setDateOfBirth("January", 2000, 1)
                    .setSubjects(subject)
                    .setHobbies()
                    .setAddress("Sovetskaya st. 23")
                    .setStateAndCity()
                    .submit();
            formPage.checkSubjectIsValid(subject);
            formPage.closeTableAfterSubmit();
        }
    }

    @Test
    @DisplayName("Проверка выбора разных штатов и городов")
    void shouldSelectDifferentStatesAndCities() {
        for (String[] pair : STATE_CITY_PAIRS) {
            FormPage formPage = new FormPage();
            formPage
                    .setFirstName(FIRST_NAME)
                    .setLastName(LAST_NAME)
                    .selectGender(GENDER)
                    .setMobile(MOBILE_PHONE)
                    .setDateOfBirth("January", 2000, 1)
                    .setSubjects("Maths")
                    .setHobbies()
                    .setAddress("Sovetskaya st. 23");
            formPage.setPairsOfStateAndCity(pair);
            formPage.submit();
            formPage.checkStateAndCityAreValid(pair);
            formPage.closeTableAfterSubmit();
        }
    }
}
