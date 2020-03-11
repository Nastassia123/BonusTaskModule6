package com.epam.automation;


import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    static TestNG tng = new TestNG();
    static XmlSuite suite = new XmlSuite();

    private static List<String> buildSuite() {
        suite.setName("Calculator operations suite");
        List<String> files = new ArrayList<>();
        files.addAll(new ArrayList<String>() {
                         {
                             add("src/test/resources/configutationTest.xml");
                         }
                     }
        );
        return files;
    }

    private static void runSuite() {
        suite.setSuiteFiles(buildSuite());
        suite.setParallel(XmlSuite.ParallelMode.METHODS);
        suite.setThreadCount(4);
        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suite);
        tng.setXmlSuites(suites);
        tng.run();
    }

    public static void main(String[] args) {
        buildSuite();
        runSuite();
    }


}



