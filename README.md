# AppiumTestAutomatization

<b>PREREQUISITES</b>

In order to successful run NavigatorTest.java placed in NavigatorAutomation, the following prerequisites are necessary:
<br/>1. Eclipse is installed
<br/>2. Android SDK and APIs for recent versions of Android
<br/>3. Selenium Java Jar files
<br/>4. Appium Java client Jar files

When the project is opened inside of Eclipse, right-click on it, and add external JARs (Selenium and Appium Java client) to Java build path.

Since the apk is stored in the computer and is not already installed on the device we need to create a file object which represents the actual apk file on the disk. I placed the folder Navigator which contains the Navigator.apk file inside the Eclipse project.

<i>File classpathRoot = new File(System.getProperty(“user.dir”)); // path to Eclipse project
<br/>File appDir = new File(classpathRoot, “/Navigator”); // path to <project folder>/Navigator
<br/>File app = new File(appDir, “Navigator.apk”); path to <project folder>/Navigator/Navigator.apk</i>


<b>CODE OVERVIEW</b>

To be able to test the app on an actual device Desired Capabilities need to be set. That is the first phase of writing test. Desired Capabilities are a set of keys and values sent to the Appium server to tell the server what kind of automation session we’re interested in starting up. There are also capabilities used to modify the behaviour of the server during automation.

We set platform to Android, absolute local path to the APK, Java package of tested Android app, name for the Android activity run from the package, and initialize driver object.

The second phase is writing the actual test. In this example, smoke test for Navigator application is written. It verifies whether search function works properly, and if information on place like title, image, description are shown. The first step is to search for the place with ‘atlantbh’ keyword and open the place in new screen. After that, following elements are validated: title name, place name, address container, working hours, place description and place image. Gallery that opens after click on place image is tested for swiping function between three images.

The third phase is to quit the driver using tearDown method, and that is completion of the test.


<b>RUNNING THE TEST</b>

The code comes with the pom.xml file containing the information about the project and configuration details used by Maven to build the project. Target is build directory, and tests are in java file under src/test/NavigatorTest.java. POM file contains, in this case, jar files needed for project to run successfully. 
You can easily import the existing Maven project, and test the application by yourself. 


<i>Source: https://university.utest.com/how-to-set-up-your-first-android-automation-project-with-appium/</i>


