package com.vison.androidutils.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.orhanobut.logger.Logger;
import com.vison.androidutils.common.utils.DBUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vison on 2016/10/14.
 * 利用反射生成建表语句
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "MySqLiteHelper";

    private Class mClazz;

    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySQLiteHelper(Context context, String db_name, int db_version, Class clazz) {
        this(context, db_name, null, db_version);
        this.mClazz = clazz;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + DBUtils.getTableName(mClazz));
        createTable(db);
    }

    /**
     * 根据制定类名创建表
     */
    private void createTable(SQLiteDatabase db) {
        db.execSQL(getCreateTableSql(mClazz));
    }

    /**
     * 得到建表语句
     *
     * @param clazz 指定类
     * @return sql语句
     */
    private String getCreateTableSql(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        String tabName = DBUtils.getTableName(clazz);
        sb.append("create table ").append(tabName).append(" (id  INTEGER PRIMARY KEY AUTOINCREMENT, ");
        Field[] fields = clazz.getDeclaredFields();
        for (Field fd : fields) {
            String fieldName = fd.getName();
            String fieldType = fd.getType().getName();
            if (fieldName.equalsIgnoreCase("_id") || fieldName.equalsIgnoreCase("id")) {
                continue;
            } else {
                sb.append(fieldName).append(DBUtils.getColumnType(fieldType)).append(", ");
            }
        }
        int len = sb.length();
        sb.replace(len - 2, len, ")");
        Logger.d("the result is " + sb.toString());
        return sb.toString();
    }
}
