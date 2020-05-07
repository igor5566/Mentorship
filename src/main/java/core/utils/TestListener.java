package core.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;


public class TestListener extends TestListenerAdapter {

    @Attachment
    public void takeScreenshot(ITestResult result) {
        System.out.println("Failure detected...");
        String fileName = String.format("Screenshot-%s.jpg", Calendar.getInstance().getTimeInMillis());
        WebDriver driver = (WebDriver) result.getTestContext().getAttribute("webDriver");
        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        File destFile = new File("./screenshots/" + fileName);
        try {
            FileHandler.copy(srcFile, destFile);
            Path content = Paths.get(destFile.getPath());
            InputStream is = Files.newInputStream(content);
            Allure.addAttachment("ScreenShot", is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Screenshot taken");
    }

    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestSuccess(ITestResult result) {
    }

    @Override
    public void onTestFailure(ITestResult result) {
        takeScreenshot(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }
}
