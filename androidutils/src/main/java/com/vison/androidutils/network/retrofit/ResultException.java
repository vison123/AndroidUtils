package com.vison.androidutils.network.retrofit;

/**
 *
 * Created by vison on 16/7/19.
 */
public class ResultException extends RuntimeException {

    private int errCode = 0;
    private String msg;

    public ResultException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
        this.msg = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    @Override
    public String toString() {
        return msg;
    }
}