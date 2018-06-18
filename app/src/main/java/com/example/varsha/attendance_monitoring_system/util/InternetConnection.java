package com.example.varsha.attendance_monitoring_system.util;

/**
 * Created by Varsha on 06-04-2018.
 */


        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.support.annotation.NonNull;


public class InternetConnection {

    /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
    public static boolean checkConnection(@NonNull Context context) {
        return  ((ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}