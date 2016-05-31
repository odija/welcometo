package com.welcometo.helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;


/**
 * Created by volodya on 6/24/15.
 */
public class InitCurrencyRate extends AsyncTask<Void, Void, String> {

    private Context mContext;
    private ICallback mCallback;

    public static String CURRENCY_RATE_SYNC_DATE = "crsd";

    public InitCurrencyRate(Context context, ICallback callback) {
        mContext = context;
        mCallback = callback;
    }

    protected String doInBackground(Void... paramVarArgs) {
        String apiURL = "https://openexchangerates.org/api/latest.json?app_id=b5ab93de71fd4a7c9192b5d89f965892";

        try {
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            LogHelper.d("GET: " + apiURL);

            // Starts the query
            conn.connect();

            String response = readStream(conn.getInputStream());

            LogHelper.d("The response code is: " + conn.getResponseCode());
            LogHelper.d("The response message is: " + response);

            return response;
        } catch (IOException localIOException) {
            Log.e("", localIOException.getMessage());
            return null;
        }
    }

    protected void onPostExecute(String paramString) {

        try {
            LogHelper.d("", paramString);

            JSONObject rates = new JSONObject(paramString).getJSONObject("rates");

            DataBaseHelper.getInstance(mContext).initCurrencyRates(rates);

            SharedPreferencesHelper.getInstance(mContext).putLong(CURRENCY_RATE_SYNC_DATE, getCurrentTimeStamp());

            if (mCallback != null) mCallback.onComplete();
        }
        catch (Exception localException) {
            LogHelper.e("", "Error parse JSON");
        }
    }

    private long getCurrentTimeStamp() {
        return new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()).getTime() / 1000;
    }

    private String readStream(InputStream in) {
       // InputStream in = address.openStream();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();

        } catch(IOException e) {
            return null;
        }
    }

    public interface ICallback {
        void onComplete();
    }
}
