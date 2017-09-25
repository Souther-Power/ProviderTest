package com.example.appq.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button addData;
    private Button queryData;
    private Button updateData;
    private Button deleteData;

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加数据
                Uri uri = Uri.parse("content://com.example.appq.databasetest.provider/book");
                ContentValues values = new ContentValues();
                values.put("name","A Clash of king");
                values.put("author","George Martin");
                values.put("pages",1201);
                values.put("price",22.35);
                //ContentResolver的insert()方法执行添加操作；
                //返回Uri对象，包含新增数据的id；
                //用getPathSegments()方法取出；
                Uri newUri = getContentResolver().insert(uri,values);
                newId = newUri.getPathSegments().get(1);
            }
        });
        queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询数据;
                Uri uri = Uri.parse("content://com.example.appq.databasetest.provider/book");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                if (cursor!=null){
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int  pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));

                        Log.d("MainActivity","Book name is "+ name);
                        Log.d("MainActivity","Book author is "+ author);
                        Log.d("MainActivity","Book pages is "+ pages);
                        Log.d("MainActivity","Book price is "+ price);
                    }
                    cursor.close();
                }
            }
        });

        updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新数据
                //将URI解析成Uri对象。。这里给尾部添加id，更新刚刚添加的那条数据
                Uri uri = Uri.parse("content://com.example.appq.databasetest.provider/book"+newId);
                ContentValues values = new ContentValues();
                values.put("name","A storm of Swords");
                values.put("pages",1021);
                values.put("price",21.32);
                getContentResolver().update(uri,values,null,null);
            }
        });

        deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除数据
                Uri uri = Uri.parse("content://com.example.appq.databasetest.provider/book"+newId);
                getContentResolver().delete(uri,null,null);
            }
        });


    }
}
