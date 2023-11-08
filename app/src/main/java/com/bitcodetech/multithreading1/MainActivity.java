package com.bitcodetech.multithreading1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDownload = findViewById(R.id.btnDownload);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code to start download thread
                String [] files = {
                        "http://bitcode.in/android/demos.zip",
                        "http://bitcode.in/java/demos.zip",
                };
                new DownloadThread().execute(files);
            }
        });
    }

    class DownloadThread extends AsyncTask<String, Integer, Float> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
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
                        Thread.sleep(50);
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
            btnDownload.setText(values[0] + "%");
        }

        @Override
        protected void onPostExecute(Float result) {
            mt("res = " + result);
            progressDialog.dismiss();
        }
    }

    private void mt(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}