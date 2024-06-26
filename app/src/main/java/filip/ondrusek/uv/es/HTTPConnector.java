package filip.ondrusek.uv.es;

import android.os.AsyncTask;
import android.os.StrictMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

class HTTPConnector extends AsyncTask<String, Void, JSONObject> {
    private final String UrlString = "https://dadesobertes.gva.es/es/api/3/action/datastore_search?resource_id=7fd9a2bf-ffee-4604-907e-643a8009b04e&limit=1000";

    @Override
    protected JSONObject doInBackground(String... urlString) {
        JSONObject jsonObject = null;
        HttpURLConnection urlConnection = null;
        URL url = null;
        try {
            url = new URL(UrlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            urlConnection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        urlConnection.setReadTimeout(10000);
        urlConnection.setConnectTimeout(15000);
        urlConnection.setDoOutput(true);
        try {
            urlConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();

        String line = null;
        while (true) {
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append(line + "\n");
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonString = sb.toString();

        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    protected void onPostExecute(JSONObject municipality) {
    }
}
