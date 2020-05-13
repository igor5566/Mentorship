package com.github.tests.UItests;

import com.github.base.BaseTest;
import com.github.pages.LoginPage;
import com.github.pages.MainPage;
import core.utils.MavenUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class UITests extends BaseTest {

    private final String url = "http://github.com/login";
    private LoginPage loginPage;
    private MainPage mainPage;

    @Test(description = "Verify Home page is opened.")
    public void loginTest() {
        open(url);
        loginPage = new LoginPage(driver);
        mainPage = loginPage.logIn(MavenUtils.getEmail(), MavenUtils.getPass());
        log.info("Some info from logger.");
        assertThat(mainPage.isDashboardVisible()).as("Cannot login into the account.").isTrue();
    }
}
