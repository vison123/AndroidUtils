package com.vison.androidutils.common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler instance;

    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    public void init(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 核心方法，当程序crash崩溃 会回调此方法，Throwable中存放这错误日志
     *
     * @param thread
     * @param ex
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        String logPath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            logPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                    + File.separator + "log";
            File file = new File(logPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                FileWriter fw = new FileWriter(logPath + File.separator + "errorLog.txt", true);
                //错误信息
                //这里还可以嘉盛当前的系统版本，机型型号等等
                StackTraceElement[] stackTrace = ex.getStackTrace();
                fw.write(ex.getMessage() + "\n");
                for (StackTraceElement aStackTrace : stackTrace) {
                    fw.write("file:" + aStackTrace.getFileName() + " class:" + aStackTrace.getClassName()
                            + " method:" + aStackTrace.getMethodName() + " line:" + aStackTrace.getLineNumber()
                            + "\n");
                }
                fw.write("\n");
                fw.close();
                //可以顺便上传错误到服务器 TODO
            } catch (IOException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
