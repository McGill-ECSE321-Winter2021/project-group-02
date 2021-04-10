package ca.mcgill.ecse321.android_scrs;

import com.loopj.android.http.AsyncHttpClient;

public class Variables {
    public static int userID = -1;

    public static String userType = null;

    //===== Http requests stuff =====
    private static String baseUrl = "https://scrs-backend-1.herokuapp.com/";
    public static AsyncHttpClient client = new AsyncHttpClient();

    public static String getAbsoluteUrl(String relativeUrl) {
        return baseUrl + relativeUrl;
    }

}
