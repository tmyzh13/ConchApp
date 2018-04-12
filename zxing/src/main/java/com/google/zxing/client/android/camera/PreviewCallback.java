/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.client.android.camera;

import android.graphics.Point;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.czy.zbar.Config;
import com.czy.zbar.Image;
import com.czy.zbar.ImageScanner;
import com.czy.zbar.ScanCallback;
import com.czy.zbar.Symbol;
import com.czy.zbar.SymbolSet;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.DecodeHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings("deprecation") // camera APIs
final class PreviewCallback implements Camera.PreviewCallback {

  private static final String TAG = PreviewCallback.class.getSimpleName();

  private final CameraConfigurationManager configManager;
  private DecodeHandler previewHandler;
  private int previewMessage;

  private ExecutorService executorService = Executors.newSingleThreadExecutor();

  private ImageScanner mImageScanner;
  private Handler mHandler;
  private ScanCallback mCallback;

  private boolean allowAnalysis = true;
  private Image barcode;

  PreviewCallback(CameraConfigurationManager configManager) {
    this.configManager = configManager;
  }

  void setHandler(DecodeHandler previewHandler, int previewMessage) {
    this.previewHandler = previewHandler;
    this.previewMessage = previewMessage;
  }

  @Override
  public void onPreviewFrame(byte[] data, Camera camera) {
    Point cameraResolution = configManager.getCameraResolution();
    Handler thePreviewHandler = previewHandler;
    if (cameraResolution != null && thePreviewHandler != null) {
      Message message = thePreviewHandler.obtainMessage(previewMessage, cameraResolution.x,
          cameraResolution.y, data);
      message.sendToTarget();
      previewHandler = null;

      /*zbar*/

      Camera.Size size = camera.getParameters().getPreviewSize();

      barcode = new Image(size.width, size.height, "Y800");
      barcode.setData(data);
      // barcode.setCrop(startX, startY, width, height);

      executorService.execute(mAnalysisTask);




    } else {
      Log.d(TAG, "Got preview callback, but no handler or resolution available");
    }
  }
  private Runnable mAnalysisTask = new Runnable() {
    @Override
    public void run() {
      if(mImageScanner==null){
        mImageScanner = new ImageScanner();
        mImageScanner.setConfig(0, Config.X_DENSITY, 3);
        mImageScanner.setConfig(0, Config.Y_DENSITY, 3);
      }
      int result = mImageScanner.scanImage(barcode);

      String resultStr = null;
      if (result != 0) {
        SymbolSet symSet = mImageScanner.getResults();
        for (Symbol sym : symSet)
          resultStr = sym.getData();
      }

      if (!TextUtils.isEmpty(resultStr)) {
        Log.e("czy","zbar result::"+resultStr);
        if (previewHandler!=null){
          CaptureActivity activity = previewHandler.getActivity();
          activity.handleZbarResult(resultStr);

        }
        
      } else allowAnalysis = true;
    }
  };
}
