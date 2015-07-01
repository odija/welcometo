package com.welcometo.ui;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.welcometo.R;
import com.welcometo.helpers.ConnectionHelper;
import com.welcometo.helpers.GetCurrencyRate;
import com.welcometo.helpers.SharedPreferencesHelper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class CurrencyFragment extends Fragment {

  public static final String PARAM_CURRENT_CURRENCY = "currency";
  public static final String PARAM_NATIVE_CURRENCY = "ncurrency";

  private TextView lblRate;
  private double mCurrencyRate = 1.0D;
  private TextView txtFrom;

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {

    View localView = paramLayoutInflater.inflate(R.layout.currency, paramViewGroup, false);

    TextView lblFrom;
    TextView lblTo;

    this.txtFrom = ((TextView)localView.findViewById(R.id.txtFrom));
    this.txtFrom.addTextChangedListener(this.txtFromWatcher);
    this.txtFrom.requestFocus();
    this.txtTo = ((TextView)localView.findViewById(R.id.txtTo));
    this.txtTo.addTextChangedListener(this.txtToWatcher);
    lblTo = ((TextView)localView.findViewById(R.id.lblTo));
    lblFrom = ((TextView)localView.findViewById(R.id.lblFrom));
    this.lblRate = ((TextView)localView.findViewById(R.id.lblRate));

    Bundle localBundle = getArguments();
    final String currencyFrom;
    final String currencyTo;

    if (localBundle != null) {
      currencyFrom = localBundle.getString(PARAM_NATIVE_CURRENCY);
      currencyTo = localBundle.getString(PARAM_CURRENT_CURRENCY);
      lblFrom.setText(currencyFrom);
      lblTo.setText(currencyTo);
      if (ConnectionHelper.isConnected(getActivity())) {
        new GetCurrencyRate(getActivity(), currencyFrom, currencyTo, new GetCurrencyRate.ICallback(){
            @Override
            public void onComplete(double rate) {
                CurrencyFragment.this.mCurrencyRate = rate;
                CurrencyFragment.this.mCurrencyRate = CurrencyFragment.round(CurrencyFragment.this.mCurrencyRate, 3, 4);
                CurrencyFragment.this.showRateLabel(currencyFrom, currencyTo, CurrencyFragment.this.mCurrencyRate);
                SharedPreferencesHelper.getInstance(CurrencyFragment.this.getActivity()).putString(currencyTo, String.valueOf(CurrencyFragment.this.mCurrencyRate));
            }
        }).execute();
      }
    } else {
      return localView;
    }

    if (SharedPreferencesHelper.getInstance(getActivity()).getString(currencyFrom, null) != null) {
      this.mCurrencyRate = Float.valueOf(SharedPreferencesHelper.getInstance(getActivity()).getString(currencyFrom, null));
      showRateLabel(currencyTo, currencyFrom, this.mCurrencyRate);
      return localView;
    }

    this.mCurrencyRate = 1.0D;
    showRateLabel("no internet");
    return localView;
  }

    private TextWatcher txtFromWatcher = new TextWatcher() {
        public void afterTextChanged(Editable paramAnonymousEditable) {}

        public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}

        public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {
            if (CurrencyFragment.this.txtFrom.isFocused()) {
                try {
                    Double localDouble = Double.valueOf(paramAnonymousCharSequence.toString());
                    TextView localTextView = CurrencyFragment.this.txtTo;
                    localTextView.setText(String.format("%.2f", localDouble * CurrencyFragment.this.mCurrencyRate));
                }
                catch (Exception localException) {
                    CurrencyFragment.this.txtTo.setText("");
                }
            }
        }
    };
    private TextView txtTo;
    private TextWatcher txtToWatcher = new TextWatcher()
    {
        public void afterTextChanged(Editable paramAnonymousEditable) {}

        public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}

        public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
            if (CurrencyFragment.this.txtTo.isFocused()) {
                try
                {
                    Double localDouble = Double.valueOf(paramAnonymousCharSequence.toString());
                    TextView localTextView = CurrencyFragment.this.txtFrom;
                    localTextView.setText(String.format("%.2f", localDouble / CurrencyFragment.this.mCurrencyRate));
                }
                catch (Exception localException)
                {
                    CurrencyFragment.this.txtFrom.setText("");
                }
            }
        }
    };

    public static double round(double paramDouble, int paramInt1, int paramInt2) {
        return new BigDecimal(paramDouble).setScale(paramInt1, paramInt2).doubleValue();
    }

    private void showRateLabel(String paramString) {

        this.lblRate.setText(paramString);
    }

    private void showRateLabel(String paramString1, String paramString2, double paramDouble) {
        this.lblRate.setText("1 " + paramString1 + " = " + paramDouble + " " + paramString2);
    }
}