package com.tg.zxingdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tg.test.zxing.google.zxing.client.android.CaptureActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnScan;
    private Button btnScanBga;
    private TextView tvResult;
    private int typeSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnScan = findViewById(R.id.btn_Scan);
        btnScanBga = findViewById(R.id.btn_ScanBga);
        tvResult = findViewById(R.id.tv_result);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    typeSelect = 1;
                    openScan(typeSelect);
                }
            }
        });
        btnScanBga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    typeSelect = 2;
                    openScan(typeSelect);
                }
            }
        });
    }

    private void openScan(int type) {
        Intent intent;
        if (1 == type) {
            intent = new Intent(MainActivity.this, CaptureActivity.class);
        } else {
            intent = new Intent(MainActivity.this, BgaqrCodeActivity.class);
        }
        startActivityForResult(intent, 100);
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        } else {
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (null != data) {
                tvResult.setText(data.getStringExtra("scandata"));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (0 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openScan(typeSelect);
            } else {
                Toast.makeText(this, "请打开拍照权限", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
