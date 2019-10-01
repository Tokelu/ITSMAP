package dk.witzell.jobsearchapp.utils;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dk.witzell.jobsearchapp.models.Job;


public class csvParser
{
    private InputStream inputStream;
    public  csvParser(InputStream inputStream) { this.inputStream = inputStream; }

    public List getAssets()
    {
        List list = new ArrayList<Job>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try
        {
            String row;
            while ((row = reader.readLine()) != null)
            {
                String[] col = row.split(";");
                list.add(new Job(col[0], col[1], col[2], col[3]));
            }
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Error: Could not read csv file.");
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (IOException ex)
            {
                throw new RuntimeException("Error: Could not close input stream.");
            }
        }
        return list;
    }
}
