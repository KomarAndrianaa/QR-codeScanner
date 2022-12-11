package com.example.qrcodescanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
 //View object
   private Button buttonScanning;
   private TextView textViewName,textViewClass,textViewNim;
 //qr scanning object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view object
        buttonScanning = (Button) findViewById(R.id.buttonScan);
        textViewName = (TextView) findViewById(R.id.textViewNama);
        textViewClass = (TextView) findViewById(R.id.textViewKelas);
        textViewNim = (TextView) findViewById(R.id.textViewNim);

        //Inisialisasi Scan
        qrScan = new IntentIntegrator(this);
        //Inisialisasi onClickListener
        buttonScanning.setOnClickListener(this);
    }

        //untuk mendapatkan hasil scanning
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator .parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            //jika qrCode tidak ada sama sekali
            if (result.getContents() == null) {
                Toast.makeText(this, "Hasil Scanning Tidak Ada",
                        Toast.LENGTH_LONG).show();
            } else{
                //jika qCode tidak ditemukan datanya
                try {
                //konversi datanya ke json
                    JSONObject obj = new JSONObject(result.getContents());
                //diset nilai datanya ke textview
                textViewName.setText(obj.getString("Nama"));
                textViewClass.setText(obj.getString("Kelas"));
                textViewNim.setText(obj.getString("Nim"));
                } catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(),
                            Toast.LENGTH_LONG).show();
                }
            }
        } else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onClick(View v) {
        //inisialisasi qrCode scanning
        qrScan.initiateScan();
    }
}