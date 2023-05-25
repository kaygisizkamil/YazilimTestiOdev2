/**
*
* @author Kamil Kaygisiz kamil.kaygisiz1@ogr.sakarya.edu.tr
* @since 19.05.2023
* <p>
*Bu menudur,odevde istendigi gibi kullanicidan alinan inputa gore gerekli
*testleri docker containeri icinde headless olarak yapar
*testeri chromda acilir kapanir (visible ) chroma uzerinde deneyimlemek isterseniz
*lutfen chrome driveri kurup  System.setProperty("webdriver.chrome.driver", "your_path");
*image olusturma suresi yaklasik 5 dakikadir cunku standalone degil container icine kurulmus
* gercek tarayici uzerinde islemler gerceklesir
* </p>
*/

package program;
import systemTests.CartTests;
import systemTests.CheckBoxTests;
import systemTests.DropDownTests;
import systemTests.LinkTests;
import systemTests.NavBarTests;
import systemTests.PageLayoutTests;
import systemTests.PopUpTests;
import systemTests.Signuptests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.*;
import org.junit.platform.launcher.listeners.*;
import java.lang.reflect.Method;
import org.junit.platform.commons.support.ReflectionSupport;


import java.util.*;

public class TestMenu {
    public static void main(String[] args) {
    	System.out.println("Sistem testleri menusune hosgeldiniz. ");
    	System.out.println("Lutfen calistirmak istediginiz testi seciniz");
    	System.out.println("Testler --headless modda calismaktadir");
    	System.out.println("Jdk base image'ine kurulmus chrome tarayicisinda testler gerceklesecektir");

    	System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
    	 
        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        while (running) {
            displayMainMenu();
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("Exiting the test runner.");
                running = false;
            } else {
                try {
                    int testNumber = Integer.parseInt(input);
                    runTest(testNumber);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid test number.");
                }
            }
        }

        scanner.close();
    }

    private static void runTest(int testNumber) {
        Map<Integer, Class<?>> testClasses = new HashMap<>();
        testClasses.put(1, CheckBoxTests.class);
        testClasses.put(2, DropDownTests.class);
        testClasses.put(3, LinkTests.class);
        testClasses.put(4, NavBarTests.class);
        testClasses.put(5, PageLayoutTests.class);
        testClasses.put(6,PopUpTests.class);
        testClasses.put(7, Signuptests.class);
        testClasses.put(8, CartTests.class);
        Class<?> selectedTestClass = testClasses.get(testNumber);
        if (selectedTestClass != null) {
            invokeBeforeAll(selectedTestClass);
            executeTest(selectedTestClass);
            invokeAfterAll(selectedTestClass);
        } else {
            System.out.println("Gecersiz secenek .");
        }
    }

    private static void executeTest(Class<?> testClass) {

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(DiscoverySelectors.selectClass(testClass))
                .build();

        Launcher launcher = LauncherFactory.create();

        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);

        TestPlan testPlan = launcher.discover(request);
        launcher.execute(testPlan);

        TestExecutionSummary summary = listener.getSummary();
        listener = new SummaryGeneratingListener(); 

        System.out.println("Total tests: " + summary.getTestsFoundCount()+"tamamlandi");
      

    }
    private static void invokeBeforeAll(Class<?> testClass) {
        Method[] methods = testClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeAll.class)) {
                ReflectionSupport.invokeMethod(method, null);
            }
        }
    }

    private static void invokeAfterAll(Class<?> testClass) {
        Method[] methods = testClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(AfterAll.class)) {
                ReflectionSupport.invokeMethod(method, null);
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("Calistirmak istediginiz testi secin ('q' to exit):");
        System.out.println("1. CheckBox Tests");
        System.out.println("2. DropDown Tests");
        System.out.println("3. Link Tests");
        System.out.println("4. NavBar Tests");
        System.out.println("5. PageLayout Tests");
        System.out.println("6. PopUp Tests");
        System.out.println("7. SignUp Tests");
        System.out.println("8. Cart Tests");
    }
}
