package com.prospect.pdfgeneratorsimple;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

final public class MainActivity extends AppCompatActivity implements PDFUtility.OnDocumentClose
{
    private static final String TAG = MainActivity.class.getSimpleName();
    private AppCompatEditText rowCount;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rowCount = findViewById(R.id.rowCount);
        AppCompatButton button1 = findViewById(R.id.button1);


        getStorageDir();
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    PDFUtility.createPdf(v.getContext(),MainActivity.this,getSampleData(),path,true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e(TAG,"Error Creating Pdf");
                    Toast.makeText(v.getContext(),"Error Creating Pdf",Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }
    }

    @Override
    public void onPDFDocumentClose(File file)
    {
        Toast.makeText(this,"Sample Pdf Created",Toast.LENGTH_SHORT).show();
    }

    private List<String[]> getSampleData()
    {
        int count = 20;
        if(!TextUtils.isEmpty(rowCount.getText()))
        {
            count = Integer.parseInt(rowCount.getText().toString());
        }

        List<String[]> temp = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            temp.add(new String[] {"C1-R"+ (i+1),"C2-R"+ (i+1)});
        }
        return  temp;
    }
    public void getStorageDir() {
        //create folder
        File file = new File(Environment.getExternalStorageDirectory() + "/RestaurantReport");
        if (!file.mkdirs()) {
            file.mkdirs();
        }
        path = file.getAbsolutePath() + File.separator + "SampleText.pdf";
    }
}
