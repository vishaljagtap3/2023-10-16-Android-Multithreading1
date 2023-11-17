package com.bitcodetech.multithreading1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

class DownloadThread extends AsyncTask<String, Integer, Float> {

    private ProgressDialog progressDialog;
    private Context context;
    private Handler handler;

    public DownloadThread(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("BitCode");
        progressDialog.setMessage("Downloading");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
    }

    @Override
    protected Float doInBackground(String... params) {

        for(String file : params) {

            progressDialog.setMessage("Downloading " + file);

            for (int i = 0; i <= 100; i++) {
                progressDialog.setProgress(i);
                //btnDownload.setText(i + "%"); //will not work

                Integer [] progress = new Integer[1];
                progress[0] = i;
                publishProgress(progress);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Log.e("tag", "Downloading: " + file + " " + i + "%");
            }
        }

        return 98.456f;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Message message = new Message();
        message.what = 1;
        message.obj = values[0];
        handler.sendMessage(message);
    }

    @Override
    protected void onPostExecute(Float result) {
        progressDialog.dismiss();
        Message message = new Message();
        message.what = 2;
        message.obj = new Float(result);
        handler.sendMessage(message);
        //handler.handleMessage(message);
    }
}