package com.sdimdev.nnhackaton.presentation.view.zxing;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.sdimdev.nnhackaton.R;


public class ScanActivity extends CaptureActivity {

    @Override
    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.activity_scan);
        return (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
    }
}
