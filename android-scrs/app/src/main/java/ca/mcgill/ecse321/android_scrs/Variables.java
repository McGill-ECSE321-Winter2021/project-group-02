package ca.mcgill.ecse321.android_scrs;

import com.loopj.android.http.AsyncHttpClient;

public class Variables
{
    public static int userID = -1;
    public static String userType = null;

    // HTTP Request Constants

    private static final String baseUrl = "https://scrs-backend-1.herokuapp.com/";
    public static AsyncHttpClient client = new AsyncHttpClient();

    // HTTP Request Helpers
    public static String getAbsoluteUrl(String relativeUrl)
    {
        return baseUrl + relativeUrl;
    }

}
