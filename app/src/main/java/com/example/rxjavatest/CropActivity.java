package com.example.rxjavatest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.test.SearchBarView;

import me.pqpo.smartcropperlib.view.CropImageView;

public class CropActivity extends Activity {
    CropImageView iv_crop;
    SearchBarView searchbarview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        searchbarview=findViewById(R.id.searchbarview);
    }
    public void crop(){
        setContentView(R.layout.activity_corp);
        iv_crop=findViewById(R.id.iv_crop);
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.test);
        iv_crop.setImageToCrop(bitmap);
    }
    public void openAni(View view){
        searchbarview.startClose();
    }
    public void closeAni(View view){
        searchbarview.startOpen();
    }
}
