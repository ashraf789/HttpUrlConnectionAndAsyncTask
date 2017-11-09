package net.a6te.lazycoder.httpurlconnectiontest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String baseUrl = "http://www.google.com";
    public static final String TAG = "HttpUrlConnectionTest";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);

        AsyncTaskHttp asyncTask = new AsyncTaskHttp();
        asyncTask.execute("");
    }


    class AsyncTaskHttp extends AsyncTask<String,String,String >{
        @Override
        protected String doInBackground(String... params) {
            String webPage = "",data="";
            try {
                URL url = new URL(baseUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

                while ((data = bufferedReader.readLine())!=null){
                    webPage+=data;
                }

            } catch (java.io.IOException e) {
                e.printStackTrace();
                return "";
            }
            return webPage;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d(TAG, "onPostExecute: "+s);
            webView.loadData(s, "text/html", "UTF-8");
        }
    }
}
