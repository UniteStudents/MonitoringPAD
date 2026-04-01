//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package Reporting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GenerateExtract {
    public static final String defaultEnvironment = "projdev";
    public static final String defaultFlowName = "block";

    public GenerateExtract() {
    }

    public static void main(String[] inputParams) {
        String envName = "projdev";
        String flowName = "block";
        if(inputParams != null && inputParams.length != 0) {
            envName = inputParams[0];
            flowName = inputParams[1];
            System.out.println("passed envName as an argument: " + inputParams[0]);
        }

        Calendar cal = Calendar.getInstance();
        cal.add(1, -1);
        Date DATE_FROM = cal.getTime();
        new ArrayList();
        new ArrayList();
        Object result = null;
        Object csvFileName = null;
        System.out.println("Result-------------- : " + result);
    }
}
