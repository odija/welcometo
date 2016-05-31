package com.welcometo.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.welcometo.R;
import com.welcometo.helpers.DataBaseHelper;
import com.welcometo.helpers.LogHelper;

import java.math.BigDecimal;
import java.security.acl.LastOwnerException;

public class CurrencyFragment extends Fragment {

  public static final String PARAM_CURRENT_CURRENCY = "currency";
  public static final String PARAM_NATIVE_CURRENCY = "ncurrency";

  private TextView lblRate;
  private double mCurrencyRate = 1.0D;
  private TextView txtFrom;
    private DataBaseHelper db;

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {

    View localView = paramLayoutInflater.inflate(R.layout.currency, paramViewGroup, false);

    TextView lblFrom;
    TextView lblTo;

      db = DataBaseHelper.getInstance(getActivity());

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

        LogHelper.d("(CurrencyFragment.onCreateView) currency FROM: " + db.getCurrencyRate(currencyFrom));
        LogHelper.d("(CurrencyFragment.onCreateView) currency TO: " + db.getCurrencyRate(currencyTo));

        mCurrencyRate = db.getCurrencyRate(currencyTo) / db.getCurrencyRate(currencyFrom);
        mCurrencyRate = CurrencyFragment.round(mCurrencyRate, 3, 4);
        showRateLabel(currencyFrom, currencyTo, mCurrencyRate);
    } else {
      return localView;
    }

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

    private void showRateLabel(String from, String to, double rate) {
        this.lblRate.setText("1 " + from + " = " + rate + " " + to);
    }
}