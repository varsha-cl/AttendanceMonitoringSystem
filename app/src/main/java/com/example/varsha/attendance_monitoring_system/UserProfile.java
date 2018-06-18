package com.example.varsha.attendance_monitoring_system;

/**
 * Created by Varsha on 05-04-2018.
 */

public class UserProfile {
    public String user_name;
    public String user_Email;
    public String subject_Code;
    public UserProfile(String user_name,String user_Email,String subject_Code)
    {
        this.user_name=user_name;
        this.user_Email=user_Email;
        this.subject_Code=subject_Code;
    }
}
