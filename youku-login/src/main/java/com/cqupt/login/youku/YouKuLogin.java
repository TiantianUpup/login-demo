package com.cqupt.login.youku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * 模拟登陆优酷
 *
 * @author hetiantian
 * @date 2018/10/01
 * */
public class YouKuLogin {
    public static void main(String[] args) throws InterruptedException {
        //指定chromedriver安装位置，注意：必须和chrome在同个目录下
        System.setProperty("webdriver.chrome.driver", "/Applications/Google Chrome.app/Contents/MacOS/chromedriver");
        //设置google浏览器选项
        ChromeOptions chromeOptions = new ChromeOptions();
        //指定浏览器全屏
        chromeOptions.addArguments("--start-fullscreen");
        //创建chrome
        WebDriver driver = new ChromeDriver(chromeOptions);

        // 使用它访问 Google
        driver.get("https://v.youku.com/v_show/id_XMzYxMDM4MzY3Ng==.html");
        Thread.sleep(2*1000);

        //模拟点击登陆
        driver.findElement(By.xpath("//*[@id=\"ykPlayer\"]/div[2]/div[2]/div[5]/div[2]/a")).click();
        Thread.sleep(2*1000);

        //模拟点击登陆
        driver.findElement(By.xpath("//*[@id=\"YT-ytaccount\"]")).sendKeys("YourAccount");
        driver.findElement(By.xpath("//*[@id=\"YT-ytpassword\"]")).sendKeys("YourPassword");
        driver.findElement(By.xpath("//*[@id=\"YT-nloginSubmit\"]")).click();

        //让视频加载完成
        Thread.sleep(2000);

        // 检查页面标题
        System.out.println("Page title is: " + driver.getTitle());


        //退出浏览器
        driver.quit();
    }
}
