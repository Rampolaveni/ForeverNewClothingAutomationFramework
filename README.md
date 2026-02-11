# 📘 KPMG Automation Framework

## 📌 Overview

This project is a web automation testing framework (**Java + Selenium + Maven + TestNG automation framework**) built using a layered, enterprise level architecture.

The framework supports:

* Browser selection via JVM arguments
* Environment-based configuration
* Thread-safe WebDriver handling
* Page Object Model using PageFactory
* Listener-driven test lifecycle
* Scenario orchestration layer
* DAO interaction layer
* Centralized utilities
* Reporting-ready architecture
* **Centralized Log4j2 logging**

---

---

# 🏗️ Framework Architecture

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

---

# 📂 Package Structure

```
com.kpmg.webAutomation
│
├── automationTests
│   └── functional          → TestNG test classes
│
├── scenarios               → Business flows
│
├── dao                     → Page interaction logic
│
├── objectRepository        → @FindBy locators
│
├── utils                   → Common reusable actions
│
├── controllers
│   ├── DriverManager       → ThreadLocal WebDriver
│   ├── DriverClass         → Browser creation
│   └── SetUpTest           → Test lifecycle hooks
│
├── common
│   ├── Listeners           → TestNG listeners
│   └── Log4jUtil           → Central logging bootstrap
│
└── resources
    ├── webConfig           → Environment configs
    └── commonConfig        → log4j2.xml
```

---

---

# 🚀 Execution Flow

---

## ✅ 1. TestNG starts test

Test class extends `SetUpTest`.

---

## ✅ 2. Listener initializes logging + browser

Inside `Listeners.beforeInvocation()`:

* Initializes Log4j via `Log4jUtil`
* Reads browser from JVM
* Creates WebDriver
* Stores in `ThreadLocal`
* Opens application URL

```java
WebDriver driver = DriverClass.createInstance(browserName);
DriverManager.setDriver(driver);
driver.get(SetUpTest.strUrlVal);
```

---

---

## ✅ 3. Scenario Layer orchestrates flow

Example:

```java
public void verifyUserDataFromHomePage() throws Exception {
    getHomePageDAO().enterUserData();
}
```

Scenarios contain **only business flows**.

---

---

## ✅ 4. DAO performs actions

DAO initializes:

* WebDriver
* PageFactory locators
* Utilities

```java
this.driverInstance = DriverManager.getDriver();
this.homeLocatorsPage =
        PageFactory.initElements(driverInstance, HomePageLocators.class);
```

---

---

## ✅ 5. Locators defined separately

```java
@FindBy(xpath = "//input[@id='name']")
public WebElement btnName;
```

---

---

## ✅ 6. Utilities wrap Selenium

```java
public boolean type(WebElement element, String message, String value)
```

All waits and interactions live here.

---

---

## ✅ 7. Tear down

After method:

```java
DriverManager.getDriver().quit();
```

---

---

# ⚙️ Running Tests

---

## ▶ From IntelliJ

Add VM options:

```
-DTestEnv=qa
-Dbrowser=chrome
```

Run TestNG test.

---

---

## ▶ From Maven

```
-DTestEnv=qa -Dbrowser=chrome
```

---

---

# 🌍 Environment Configuration

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

---

# 🪵 Logging with Log4j2

This framework uses **Log4j2** for enterprise-grade logging.

Logging is:

✔ Initialized centrally
✔ Folder structure created automatically
✔ Grouped by execution date
✔ Separate log file per run
✔ Rolling by size
✔ Printed to console + file
✔ CI/CD friendly

---

---

## 📂 Log Folder Structure

Each execution creates:

```
webAutomationLogs/
   └── dd-MM-yyyy/
         ├── dd-MM-yyyy_HH-mm-ss.log
         ├── dd-MM-yyyy_HH-mm-ss.log
```

Example:

```
webAutomationLogs/
   └── 11-02-2026/
         ├── 11-02-2026_12-18-14.log
         └── 11-02-2026_12-29-34.log
```

---

---

## ⚙️ Log4j Initialization Flow

```
TestNG Listener
     ↓
Log4jUtil.init()
     ↓
Create webAutomationLogs folder
     ↓
Create today's date folder
     ↓
Generate runId timestamp
     ↓
Set JVM properties
     ↓
Load log4j2.xml
     ↓
All classes obtain logger via Log4jUtil
```

---

---

## 🧩 Central Logger Utility

All loggers must be obtained through:

```java
Logger log = Log4jUtil.loadLogger(MyClass.class);
```

⚠️ No class should call `LogManager.getLogger()` directly — this ensures:

* folders exist first
* runId is set
* correct configuration is used

---

---

## ▶ Troubleshooting Logging

Enable debug once:

```
-Dlog4j2.debug=true
```

This prints:

* which config file loaded
* which appenders started
* file locations

---

---

# 🧠 Key Design Concepts

---

## 🔐 ThreadLocal Driver

Each test thread has its own WebDriver:

```java
private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
```

---

---

## 🧩 Listener Driven Startup

Driver creation happens in Listener — **not in DAO/Test**.

This avoids:

❌ null drivers
❌ race conditions
❌ parallel failures

---

---

## ⚠️ Common Pitfall (What You Hit)

### ❌ Creating DAO too early

If DAO is constructed **before** Listener runs:

```
DriverManager.getDriver() → null
```

👉 leads to:

```
searchContext is null
```

---

### ✅ Correct Pattern

Create DAO **inside Scenario method**, not constructor.

```java
private HomePageDAO getHomePageDAO() {

    if (homePageDAO == null) {
        homePageDAO = new HomePageDAO();
    }

    return homePageDAO;
}
```

---

---

# 🛠️ Best Practices

✔ Never initialize DAO in Scenario constructor
✔ Always lazy-load DAO
✔ Keep Scenarios thin
✔ DAO handles page only
✔ Utilities wrap Selenium
✔ DriverManager is single source of driver
✔ No static WebDriver fields elsewhere
✔ Centralized logging only via Log4jUtil

---

---

# 🔮 Future Enhancements

* ExtentReports integration
* Retry analyzer
* Screenshot capture on failure
* Docker/Grid support
* CI/CD pipelines
* Parallel execution
* TestRail integration
* API + UI hybrid testing
* Dynamic test data management