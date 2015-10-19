package com.gildder.invenbras.gestionactivos;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.covics.zxingscanner.OnDecodeCompletionListener;
import com.covics.zxingscanner.ScannerView;

public class ScanbarActivity extends Activity implements OnDecodeCompletionListener {
    private ScannerView scannerView;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanbar);

        scannerView = (ScannerView) findViewById(R.id.scanner_view);
        txtResult = (TextView) findViewById(R.id.txtResult);
        //条码扫描后回调自己的onDecodeCompletion
        scannerView.setOnDecodeListener(this);
    }

    @Override
    public void onDecodeCompletion(String barcodeFormat, String barcode, Bitmap bitmap) {
        txtResult.setText("Barcode Format:" + barcodeFormat + "  Barcode:" + barcode);

    }


    @Override
    protected void onResume() {
        super.onResume();

        scannerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        scannerView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
