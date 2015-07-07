package com.welcometo.helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by volodya on 6/24/15.
 */
public class InitCurrencyRate extends AsyncTask<Void, Void, String> {

    private Context mContext;
    private ICallback mCallback;

    private static String CURRENCY_RATE_SYNC_DATE = "crsd";

    public InitCurrencyRate(Context context, ICallback callback) {
        mContext = context;
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        long lastSyncTime = SharedPreferencesHelper.getInstance(mContext).getLong(CURRENCY_RATE_SYNC_DATE, 0);

        if (lastSyncTime > 0) {
            long currentTimeStamp = getCurrentTimeStamp();

            if (currentTimeStamp - lastSyncTime < 3600 * 24) {
                cancel(true);
            }
        }
    }

    protected String doInBackground(Void... paramVarArgs) {
        DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();

        try {
            //String apiURL = "http://www.freecurrencyconverterapi.com/api/v3/convert?q=" + this.mFromCurrency + "_" + this.mToCurrency + "&compact=y";
            //HttpResponse localHttpResponse = localDefaultHttpClient.execute(new HttpGet("http://rate-exchange.appspot.com/currency?from=" + this.mFromCurrency + "&to=" + this.mToCurrency));

            String apiURL = "https://openexchangerates.org/api/latest.json?app_id=b5ab93de71fd4a7c9192b5d89f965892";

            Log.d("", "HTTP load: " + apiURL);
            HttpResponse localHttpResponse = localDefaultHttpClient.execute(new HttpGet(apiURL));
            StatusLine localStatusLine = localHttpResponse.getStatusLine();

            Log.d("", "HTTP return code: " + localStatusLine.getStatusCode());

            if (localStatusLine.getStatusCode() == 200) {
                ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
                localHttpResponse.getEntity().writeTo(localByteArrayOutputStream);
                localByteArrayOutputStream.close();
                return localByteArrayOutputStream.toString();
            }
            localHttpResponse.getEntity().getContent().close();
            throw new IOException(localStatusLine.getReasonPhrase());
        }
        catch (ClientProtocolException localClientProtocolException) {
            Log.e("", localClientProtocolException.getMessage());
            return null;
        }
        catch (IOException localIOException) {
            Log.e("", localIOException.getMessage());
            return null;
        }
    }

    protected void onPostExecute(String paramString) {

        try {
            Log.d("", paramString);

            JSONObject rates = new JSONObject(paramString).getJSONObject("rates");

            DataBaseHelper.getInstance(mContext).initCurrencyRates(rates);

            SharedPreferencesHelper.getInstance(mContext).putLong(CURRENCY_RATE_SYNC_DATE, getCurrentTimeStamp());

            if (mCallback != null) mCallback.onComplete();
        }
        catch (Exception localException) {
            Log.e("", "Error parse JSON");
        }
    }

    private long getCurrentTimeStamp() {
        return new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()).getTime() / 1000;
    }

    public interface ICallback {
        void onComplete();
    }
}
