#  视频网站登陆脚本
### 前期调研  
因为要模拟打开网页的操作，需要使用浏览器，所以想使用无头浏览器去实现。无头浏览器有很多种，因为自己学习的语言是java，所以重点调查了支持java语言的无头浏览器。比较常用的是PhantomJS、Selenium、jBrowserDriver。最终确定使用Selenium，因为另外两种方式不支持flash播放，那么有些视频网站的地址就不能正确播放。
对于登陆的问题，本来决定使用cookie登陆，后来在写Selenium简单使用例子的时候觉得可以通过获取网页的XPath，模拟填入内容和点击的效果。

### 前期准备
selenium需要依赖驱动，所以首先得安装drive。以google浏览器为例，需要安装chromedriver。需要注意：
- 安装的chromedriver需要和google版本一致
- 安装位置必须和google同一路径，否则将会报错：
```
Exception in thread "main" java.lang.IllegalStateException: The path to the driver executable must be set by the webdriver.chrome.driver system property; for more information, see https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver. The latest version can be downloaded from http://chromedriver.storage.googleapis.com/index.html
    at com.google.common.base.Preconditions.checkState(Preconditions.java:847)
    at org.openqa.selenium.remote.service.DriverService.findExecutable(DriverService.java:124)
    at org.openqa.selenium.chrome.ChromeDriverService.access$000(ChromeDriverService.java:32)
    at org.openqa.selenium.chrome.ChromeDriverService$Builder.findDefaultExecutable(ChromeDriverService.java:137)
    at org.openqa.selenium.remote.service.DriverService$Builder.build(DriverService.java:339)
    at org.openqa.selenium.chrome.ChromeDriverService.createDefaultService(ChromeDriverService.java:88)
    at org.openqa.selenium.chrome.ChromeDriver.<init>(ChromeDriver.java:116)
    at com.shuwen.test.Selenium2Test.main(Selenium2Test.java:15)
```

### 优酷登陆
- 打开浏览器
```
 //指定chromedriver安装位置，注意：必须和chrome在同个目录下
System.setProperty("webdriver.chrome.driver", "/Applications/Google Chrome.app/Contents/MacOS/chromedriver");
 //设置google浏览器选项
ChromeOptions chromeOptions = new ChromeOptions();
//指定浏览器全屏
chromeOptions.addArguments("--start-fullscreen");
//创建chrome
WebDriver driver = new ChromeDriver(chromeOptions);
```
通过 ChromeOptions设置了一些浏览器参数，比如希望浏览器全屏打开
执行此段代码以后会发现弹出了一个浏览器，并且打开了指定的页面，但是：
![页面.png](https://upload-images.jianshu.io/upload_images/9358011-9d5d7b9ebc1dbc4c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
需要点击登陆一下才能登陆
- 模拟点击登陆
检查该页面
![点击.png](https://upload-images.jianshu.io/upload_images/9358011-49e8958b34c8752e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
可以很快的获得xpath为`//*[@id="ykPlayer"]/div[2]/div[2]/div[5]/div[2]/a`
模拟点击
```
driver.findElement(By.xpath("//*[@id=\"ykPlayer\"]/div[2]/div[2]/div[5]/div[2]/a")).click();
```
.click 模拟点击操作
- 输入账号密码登陆
![image.png](https://upload-images.jianshu.io/upload_images/9358011-516429e2271ff9f4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
以同样的方式获得对应的xpath路径  
账号xpath：`//*[@id=\"YT-ytaccount\"]`  
密码xpath：`//*[@id=\"YT-ytpassword\"]`  
登陆xpath：`//*[@id=\"YT-nloginSubmit\"]`
```
 driver.findElement(By.xpath("//*[@id=\"YT-ytaccount\"]")).sendKeys("YourAccount");
        driver.findElement(By.xpath("//*[@id=\"YT-ytpassword\"]")).sendKeys("YourPassword");
        driver.findElement(By.xpath("//*[@id=\"YT-nloginSubmit\"]")).click();
```
.sendKey往输入框填入指定数据
- 退出浏览器
```
driver.quit();
```
- 完整demo
```
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
```


附文档地址：https://www.jianshu.com/p/70dc2ada7151

### TODO
- 腾讯登陆
- 爱奇艺登陆
