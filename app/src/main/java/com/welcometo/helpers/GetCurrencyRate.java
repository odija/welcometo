package com.welcometo.helpers;

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

/**
 * Created by volodya on 6/24/15.
 */
public class GetCurrencyRate extends AsyncTask<Void, Void, String> {

    private String mFromCurrency;
    private String mToCurrency;
    private ICallback mCallback;

    public GetCurrencyRate(String fromCurrency, String toCurrency, ICallback callback) {
        this.mToCurrency = toCurrency;
        this.mFromCurrency = fromCurrency;
        this.mCallback = callback;
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

            Double fromCurrency = rates.getDouble(this.mFromCurrency);
            Double toCurrency = rates.getDouble(this.mToCurrency);

            if (mCallback != null) {
                mCallback.onComplete(fromCurrency / toCurrency);
            }
        }
        catch (Exception localException) {
            Log.e("", "Error parse JSON");
        }
    }

    public interface ICallback {
        void onComplete(double rate);
    }
}
