package com.welcometo.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.welcometo.helpers.InitCurrencyRate;

/**
 * Created by volodya on 7/3/15.
 */
public class CurrencyRateService extends JobService {
    @Override
    public boolean onStartJob(final JobParameters params) {

        Log.d("", "START JOB " + params.getJobId());

        new InitCurrencyRate(getApplicationContext(), new InitCurrencyRate.ICallback() {
            @Override
            public void onComplete() {
                jobFinished(params, true);
            }
        }).execute();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        Log.d("", "END JOB" + params.getJobId());

        return false;
    }
}
