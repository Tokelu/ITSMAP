package dk.witzell.jobsearchonline.Services;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

//  partly inspired from https://bit.ly/2osdIlw

public class AsyncJobLoader extends AsyncTask<String, Void, String> {

    final String TAG = "AsyncJobLoader";
    final int CONNECTION_TIMEOUT = 3000; // ms = 3 sec
    URL dataURL = null;
    String returnStringJSON = null;

    @Override
    protected String doInBackground(String... params) {
        String companyID = params[0];
        String dataURLComposites = "https://jobs.github.com/positions/" + companyID;

        //Creating URL
        try {
            dataURL = new URL(dataURLComposites);
        }
        catch (MalformedURLException e) {
            Log.d(TAG, "Error creating URL");
            e.printStackTrace();
            Log.d(TAG, "Stacktrace end");
        }

        //Retrieving data from URL
        try {
            Log.d(TAG, "URL: " + dataURLComposites);
            returnStringJSON = retrieveData(dataURL);
            Log.d(TAG, "Data from DL (JSON) " + returnStringJSON);
        }
        catch (IOException e) {
            Log.d(TAG, "Error retrieving data");
            e.printStackTrace();
            Log.d(TAG, "Stacktrace end");
        }
        return returnStringJSON;
    }

    protected void onPostExecute(String result) {result = returnStringJSON;}

    private String retrieveData(URL url) throws IOException {
        InputStream inputStream = null;
        HttpsURLConnection connection = null;
        String result = null;

        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(CONNECTION_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP ErrorCode: " + responseCode);
            }
            inputStream = connection.getInputStream();
            if (inputStream != null) {
                result = streamToString(inputStream);
            }
        }
        finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }


    private String streamToString (InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();

        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                //  Adding linebreak after each line
                stringBuffer.append(line).append('\n');
            }
        }
        catch (IOException e) {
            Log.d(TAG, "Error Writing to buffer");
            e.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                Log.d(TAG, "Error closing Stream");
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }

}
