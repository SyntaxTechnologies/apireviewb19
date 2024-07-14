package utils;

import org.json.JSONObject;

public class PayloadConstants {
    public static String generateTokenPayload(){
        String tokenPayload="{\n" +
                "  \"email\": \"testbatch19@test123.com\",\n" +
                "  \"password\": \"test@123\"\n" +
                "}";
        return  tokenPayload;
    }

    public static String createEmployeePayload(){
        JSONObject obj=new JSONObject();
        obj.put("emp_firstname","moazzam");
        obj.put("emp_lastname","sadiq");
        obj.put("emp_middle_name","ms");
        obj.put("emp_gender","M");
        obj.put("emp_birthday","1976-06-16");
        obj.put("emp_status","permanent");
        obj.put("emp_job_title","QA Manager");
        return obj.toString();

    }
}
