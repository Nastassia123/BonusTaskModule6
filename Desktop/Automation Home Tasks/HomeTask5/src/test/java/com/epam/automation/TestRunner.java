package com.epam.automation;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static void main(String[] args) {
          TestNG tng = new TestNG();
          XmlSuite suite = new XmlSuite();
          suite.setName("Calculator operations suite");
          List<String> files = new ArrayList<>();
          files.addAll(new ArrayList<String>() {{
              add("src/test/resources/configutationTest.xml"); }
          }
          );
        suite.setSuiteFiles(files);
        suite.setParallel(XmlSuite.ParallelMode.METHODS);
        suite.setThreadCount(4);
        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suite);
        tng.setXmlSuites(suites);
        tng.run();





       ;


    }


}



