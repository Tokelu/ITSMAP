package dk.witzell.assignment1_new.utils;

        import android.app.Activity;
        import android.content.res.AssetManager;
        import android.content.res.Resources;

        import java.io.BufferedReader;
        import java.io.FileReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.util.Arrays;


public class csvParser extends Activity
{
    private Resources resources;  //  Application resource handle
    private String    output;     //  for LogCat purposes
    private String    fileName = "jobs.csv";

    //  check if file exists in assets folder

    AssetManager aMgr = getResources().getAssets();
    InputStream is = null;

    void openFile(String fileName) throws IOException {

        try
        {
            is = aMgr.open(fileName);
            // DO STUFF HERE
        }


        catch (IOException e) { e.printStackTrace(); }
        finally { if(is != null) { is.close(); }}
    }
}
