# Advanced Selenium Certification Project on LambdaTest

This repository contains a Java project aimed at solve the task for the Advanced Selenium Certification. The project focuses on using Selenium WebDriver and JUnit to write advanced automation scripts utilizing Java 8.

## Table of Contents

- [Project Overview](#project-overview)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Project Overview

This project demonstrates advanced Selenium WebDriver automation techniques using JUnit and Java 8. It covers given test scenario:

1. Navigate to https://www.lambdatest.com/.
2. Perform an explicit wait till the time all the elements in the DOM
are available.
3. Scroll to the WebElement ‘SEE ALL INTEGRATIONS’ using the
scrollIntoView() method. You are free to use any of the available
web locators (e.g., XPath, CssSelector, etc.)
4. Click on the link and ensure that it opens in a new Tab.
5. Save the window handles in a List (or array). Print the window handles
of the opened windows (now there are two windows open).
6. Verify whether the URL is the same as the expected URL (if not, throw
an Assert).
7. On that page, scroll to the page where the WebElement
(Codeless Automation) is present.
8. Click the ‘LEARN MORE’ link for Testing Whiz. The page should open
in the same window.
9. Check if the title of the page is ‘TestingWhiz Integration | LambdaTest’. If not, raise an Assert.
10. Close the current window using the window handle [which we
obtained in step (5)]
11. Print the current window count.
12. On the current window, set the URL to
https://www.lambdatest.com/blog.
13. Click on the ‘Community’ link and verify whether the URL is
https://community.lambdatest.com/.
14. Close the current browser window.
This test scenario will be run on two parallel classes on LambdaTest using this configuration:
1. Chrome + 86.0 + Windows 10 (Test Scenario 1)
2. Microsoft Edge + 87.0 + macOS Sierra (Test Scenario 2)

## Prerequisites

Before you begin, ensure you have the following prerequisites:

- Java Development Kit (JDK) 8 or higher installed
- Maven build tool
- Internet Connection

## How to run the test

You could run the test via "mvn clean verify" or by run the test folder directly.
