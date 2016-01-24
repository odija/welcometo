package com.welcometo.helpers;


import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogHelper
{
    public static boolean IS_DEBUG_VERSION = true;

    private static String _LOG_TAG = "wlcm2";

    // Important: set this flag to true only in critical cases due to
    // big performance influence
    // IS_DEBUG_VERSION should be also true
    // see StoAmigo Local Folder/ log.txt
    private static boolean USE_TXT_FILE = false;

    public static void i(String tag, String msg)
    {
        if (IS_DEBUG_VERSION && msg != null) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg)
    {
        if (IS_DEBUG_VERSION && msg != null) {
            logToFile(msg);
            Log.d(tag, msg);
        }
    }

    public static void d(String msg)
    {
        if (IS_DEBUG_VERSION && msg != null) {
            logToFile(msg);
            Log.d(_LOG_TAG, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr)
    {
        if (IS_DEBUG_VERSION && msg != null) {
            logToFile(msg, tr);
            Log.e(tag, msg, tr);
        }
    }

    public static void e(String tag, String msg)
    {
        if (IS_DEBUG_VERSION && msg != null) {
            logToFile(msg);
            Log.e(tag, msg);
        }
    }

    public static void e(String msg){
        if (IS_DEBUG_VERSION && msg != null) {
            logToFile(msg);
            Log.e(_LOG_TAG, msg);
        }
    }

    public static void e(String msg, Throwable tr)
    {
        if (IS_DEBUG_VERSION && msg != null) {
            logToFile(msg, tr);
            Log.e(_LOG_TAG, msg, tr);
        }
    }

    private static void logToFile(String text, Throwable e) {
        if (USE_TXT_FILE) {
            if (e != null && e.getMessage() != null) {
                logToFile(text + "\n" + e.getMessage());
            } else {
                logToFile(text);
            }
        }
    }

    private static void logToFile(String text)
    {
        if (USE_TXT_FILE) {
            File logFile = new File("", "log.txt");
            if (!logFile.exists())
            {
                try
                {
                    logFile.createNewFile();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            try
            {
                //BufferedWriter for performance, true to set append to file flag
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append(text);
                buf.newLine();
                buf.close();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

