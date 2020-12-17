package com.example.addressbook;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.addressbook.database.MyOpenHelper;

public class MainActivity extends AppCompatActivity {

    private EditText mEtName;
    private EditText mEtNumber;
    private TextView mTvShow;
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase db;
    private EditText mEtSearchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        myOpenHelper = new MyOpenHelper(this);
    }

    /*添加*/
    public void insertInfo(View view) {

        String name = mEtName.getText().toString().trim();
        String number = mEtNumber.getText().toString().trim();
        Log.i("insertInfo", name + "--" + number);
        //创建一个可读写数据表的类对象
        db = myOpenHelper.getWritableDatabase();
        if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(number)) {
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("number", number);
            //参数1：表名。参数2：指定添加数据的字段名，如果写null，表示所有字段都添加数据。
            //参数3：要添加的值。
            db.insert(MyOpenHelper.TABLE_NAME, null, values);
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "请输入信息", Toast.LENGTH_SHORT).show();
        }
        db.close();//关闭数据库
    }

    /*查询全部数据*/
    public void queryInfo(View view) {
        //这里创建的是只读的对象
        db = myOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(MyOpenHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.getCount() == 0) {
            mTvShow.setText("没有数据");
        } else {
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            String infoFirst = "姓名" + name + "电话" + number;
            mTvShow.setText(infoFirst);
        }
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            String infoNext = "姓名" + name + "电话" + number;
            mTvShow.append("\n" + infoNext);
        }
        mEtName.setText("");
        mEtNumber.setText("");
        cursor.close();
        db.close();
    }

    /*按姓名查询*/
    public void searchName(View view) {
        db = myOpenHelper.getReadableDatabase();
        String searchName = mEtSearchName.getText().toString().trim();
        Cursor cursor = db.query(MyOpenHelper.TABLE_NAME, null, "name=?", new String[]{searchName}, null, null, null);
        if (cursor.getCount() == 0) {
            mTvShow.setText("没有数据");
        } else {
            cursor.moveToFirst();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            String infoFirst = "姓名" + name + "电话" + number;
            mTvShow.setText(infoFirst);
        }
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String number = cursor.getString(cursor.getColumnIndex("number"));
            String infoNext = "姓名" + name + "电话" + number;
            mTvShow.append("\n" + infoNext);
        }
        cursor.close();
        db.close();
    }
    /*修改
     * 按姓名修改电话号码
     */

    public void updateInfo(View view) {
        db = myOpenHelper.getWritableDatabase();
        String name = mEtName.getText().toString().trim();
        ContentValues values = new ContentValues();
        values.put("number", mEtNumber.getText().toString().trim());
        db.update(MyOpenHelper.TABLE_NAME, values, "name=?", new String[]{name});
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        db.close();
    }

    /*删除*/
    public void deleteInfo(View view) {
        String name = mEtName.getText().toString().trim();
        db = myOpenHelper.getWritableDatabase();
        if (!TextUtils.isEmpty(name)) {
            //删除姓名是name的
            db.delete(MyOpenHelper.TABLE_NAME, "name=?", new String[]{name});
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
            mEtName.setText("");
        } else {
            Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();

        }
        db.close();
    }
    //清空数据
    public void clearInfo(View view){
        String name = mEtName.getText().toString().trim();
        db = myOpenHelper.getWritableDatabase();
        //全部删除
        db.delete(MyOpenHelper.TABLE_NAME, null, null);
        Toast.makeText(this, "全部删除", Toast.LENGTH_SHORT).show();
    }



    private void initView() {
        mEtName = findViewById(R.id.et_name);
        mEtNumber = findViewById(R.id.et_number);
        mTvShow = findViewById(R.id.tv_show);
        mEtSearchName = findViewById(R.id.et_search_name);

    }
}
