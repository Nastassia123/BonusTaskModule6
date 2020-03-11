package com.epam.automation.testsTestNG;

import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;

public class ParallelExecution {

    public static void setUpParallelExecution(){
        XmlSuite suite = new XmlSuite();
        suite.setName("TmpSuite");
        suite.setParallel(XmlSuite.ParallelMode.METHODS);
        suite.setThreadCount(2);
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);

    }
    public static void main() {
      setUpParallelExecution();
    }
}
