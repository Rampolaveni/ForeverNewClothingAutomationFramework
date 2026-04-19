# 📘 Forever NewClothing Web Automation Framework

## 📌 Overview

This project is a **Java + Selenium + Maven + TestNG web automation framework** built using a **layered, enterprise-grade architecture**.

The framework is designed for **scalability, maintainability, parallel execution, and CI/CD readiness**, following industry best practices used in large consulting and enterprise environments.

---

## ✨ Key Features

The framework supports:

* Browser selection via JVM arguments
* Environment-based configuration
* Thread-safe WebDriver handling using `ThreadLocal`
* Page Object Model using PageFactory
* Listener-driven test lifecycle
* Scenario orchestration layer
* DAO interaction layer
* Centralized reusable utilities
* Centralized **Log4j2 logging**
* Rich **Extent Spark HTML reporting**
* Parallel execution ready
* CI/CD friendly architecture

---

## 🏗️ Framework Architecture

```
Test Class
   ↓
Scenario Layer
   ↓
DAO Layer
   ↓
Object Repository (Locators)
   ↓
Selenium WebDriver
```

---

## 📂 Package Structure

```
com.kpmg.webAutomation
│
├── automationTests
│   └── functional          → TestNG test classes
│
├── scenarios               → Business flows (orchestration layer)
│
├── dao                     → Page interaction logic
│
├── objectRepository        → Page locators (@FindBy)
│
├── utils                   → Common reusable Selenium utilities
│
├── controllers
│   ├── DriverManager       → ThreadLocal WebDriver storage
│   ├── DriverClass         → Browser instantiation
│   └── SetUpTest           → Test lifecycle base class
│
├── common
│   ├── Listeners           → TestNG listeners
│   └── Log4jUtil           → Central Log4j2 bootstrap
│
└── resources
    ├── webConfig           → Environment configuration
    └── commonConfig        → log4j2.xml
```

---

## 🚀 Execution Flow

### ✅ 1. TestNG starts execution

Each test class extends `SetUpTest`.

---

### ✅ 2. Listener initializes logging & browser

Inside `Listeners.beforeInvocation()`:

* Log4j2 is initialized via `Log4jUtil`
* Browser value is read from JVM arguments
* WebDriver instance is created
* Driver is stored in `ThreadLocal`
* Application URL is launched

```java
WebDriver driver = DriverClass.createInstance(browserName);
DriverManager.setDriver(driver);
driver.get(SetUpTest.strUrlVal);
```

---

### ✅ 3. Scenario Layer orchestrates business flows

```java
public void verifyUserDataFromHomePage() throws Exception {
    getHomePageDAO().enterUserData();
}
```

✔ Scenarios contain **only business logic**
✔ No Selenium code
✔ No assertions

---

### ✅ 4. DAO Layer performs page actions

DAO initializes:

* Thread-safe WebDriver
* PageFactory locators
* Utility classes

```java
this.driverInstance = DriverManager.getDriver();
this.homeLocatorsPage =
        PageFactory.initElements(driverInstance, HomePageLocators.class);
```

---

### ✅ 5. Object Repository holds locators

```java
@FindBy(xpath = "//input[@id='name']")
public WebElement txtName;
```

✔ No logic
✔ Only locators

---

### ✅ 6. Utilities wrap Selenium actions

```java
public boolean type(WebElement element, String message, String value)
```

✔ Explicit waits
✔ Logging
✔ Error handling
✔ Reusability

---

### ✅ 7. Tear Down

After test execution:

```java
DriverManager.getDriver().quit();
```

---

## ⚙️ Running Tests

### ▶ From IntelliJ

Add VM options:

```
-DTestEnv=qa
-Dbrowser=chrome
```

Run TestNG test.

---

### ▶ From Maven

```
mvn clean test -DTestEnv=qa -Dbrowser=chrome
```

---

## 🌍 Environment Configuration

Stored in:

```
src/main/resources/webConfig/environment.properties
```

Example:

```
qa=https://testautomationpractice.blogspot.com/
prod=https://prod.example.com
```

---

## 🪵 Logging with Log4j2

This framework uses **Log4j2** for centralized, enterprise-grade logging.

### 🔹 Logging Capabilities

✔ Initialized centrally via Listener
✔ Thread-safe logging
✔ Console + file logging
✔ Date-based folders
✔ Separate log file per execution
✔ Rolling by size
✔ CI/CD friendly

---

### 📂 Log Folder Structure

```
webAutomationLogs/
   └── dd-MM-yyyy/
         ├── dd-MM-yyyy_HH-mm-ss.log
         └── dd-MM-yyyy_HH-mm-ss.log
```

---

### 🧩 Central Logging Utility

All loggers must be obtained via:

```java
Logger log = Log4jUtil.loadLogger(MyClass.class);
```

⚠️ No class should call `LogManager.getLogger()` directly.

---

### ▶ Logging Troubleshooting

Enable once if needed:

```
-Dlog4j2.debug=true
```

---

## 📊 Reporting with Extent Reports

The framework integrates **ExtentReports (Spark Reporter)** to generate rich, interactive HTML reports.

### ✨ Report Features

✔ Pass / Fail / Skip summary
✔ Execution timeline
✔ Test duration
✔ Tags & categories
✔ Interactive dashboard
✔ Expandable test details
✔ Spark UI theme

---

### 📂 Extent Report Output Structure

#### Root Directory

```
C:\Automation\Regression_Report\
```

#### Date-Based Folder

```
C:\Automation\Regression_Report\03-01-2026\
```

#### Suite & Execution Type

```
C:\Automation\Regression_Report\03-01-2026\REGRESSION\
```

#### Feature / Module Level

```
C:\Automation\Regression_Report\03-01-2026\REGRESSION\login\
```

#### Final HTML Report

```
C:\Automation\Regression_Report\03-01-2026\REGRESSION\login\
verifyLoginfromHomePageTwo2026.03.01.12.12.10.html
```

✔ One report per execution
✔ No overwrites
✔ Parallel-safe

---

### ⚙️ Extent Initialization Flow

```
TestNG Listener
     ↓
Create date folder
     ↓
Create suite folder
     ↓
Create feature folder
     ↓
Generate timestamped report name
     ↓
Initialize ExtentSparkReporter
     ↓
Attach ExtentReports
     ↓
Log test lifecycle
     ↓
Flush report on finish
```

---

## 🧠 Key Design Concepts

### 🔐 ThreadLocal WebDriver

```java
private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
```

✔ Each test thread has its own driver
✔ Safe for parallel execution

---

### 🧩 Listener-Driven Startup

✔ Driver creation handled in Listener
✔ Logging initialized before test execution
✔ Reports managed centrally

This avoids:

❌ Null drivers
❌ Race conditions
❌ Parallel execution failures

---

### ⚠️ Common Pitfall (Avoid This)

#### ❌ Creating DAO too early

If DAO is initialized **before Listener runs**:

```
DriverManager.getDriver() → null
```

Result:

```
searchContext is null
```

---

### ✅ Correct Pattern (Lazy Loading)

```java
private HomePageDAO getHomePageDAO() {

    if (homePageDAO == null) {
        homePageDAO = new HomePageDAO();
    }
    return homePageDAO;
}
```

---

## 🛠️ Best Practices

✔ Never initialize DAO in Scenario constructor
✔ Always lazy-load DAO
✔ Keep Scenarios thin
✔ DAO handles page-level actions only
✔ Utilities wrap Selenium
✔ DriverManager is the single source of WebDriver
✔ No static WebDriver references
✔ Centralized logging via Log4jUtil only
✔ Reporting logic only in Listener

---

## 🔮 Future Enhancements

* Screenshot capture on failure
* Attach screenshots to Extent Reports
* Retry analyzer
* Docker & Selenium Grid support
* CI/CD pipeline integration
* Parallel execution tuning
* TestRail integration
* API + UI hybrid testing
* Dynamic test data management

