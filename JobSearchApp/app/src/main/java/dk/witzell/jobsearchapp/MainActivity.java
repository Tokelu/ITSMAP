package dk.witzell.jobsearchapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import dk.witzell.jobsearchapp.adaptors.ListElementAdaptor;
import dk.witzell.jobsearchapp.models.Job;
import dk.witzell.jobsearchapp.utils.csvParser;


public class MainActivity extends AppCompatActivity {
    private ListElementAdaptor listElementAdaptor;
    private List<Job> jobsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.activityListRecyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        Button btnExit = findViewById(R.id.activityListButton);

        jobsList = getPrevSessionJobList(getString(R.string.PREV_SESSION_JOB_LIST));
        if (jobsList != null)
        {
            listElementAdaptor = new ListElementAdaptor(jobsList, this);
        }
        else
        {
            List fileData = csvReader();
            jobsList = getJobObjects(fileData);
            listElementAdaptor = new ListElementAdaptor(jobsList, this);
        }

        //recyclerView.setAdapter(listElementAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnExit.setOnClickListener(v -> finish());
    }

    private List csvReader() {
        InputStream stream = getResources().openRawResource(R.raw.jobs);
        csvParser parser = new csvParser(stream);
        return parser.getAssets();
    }

    private List<Job> getJobObjects(List<Job> fileData)
    {
        ArrayList<Job> jobList = new ArrayList<>();

        for (int i = 0; i < fileData.size(); i++)
        {
            Job currentJob = fileData.get(i);
            if(!currentJob.getLocation().equals("location"))
            {
                jobList.add(currentJob);
            }
        }

        for (Job job : jobList)
        {
            job.setNotes("");
            job.setCoolScore("NA");
            job.setStatus(false);
        }
        return jobList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        listElementAdaptor.onActivityResult(requestCode,resultCode,data);
    }


    @Override
    protected void onPause()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(jobsList);
        editor.putString(getString(R.string.PREV_SESSION_JOB_LIST), json);
        editor.apply();
        super.onPause();
    }

    private List<Job> getPrevSessionJobList(String index) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(index, null);
        Type type = new TypeToken<List<Job>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

 /*   @Override
    protected void OnActivityResult(int requestCode, int resultCode, Intent data)
    {
        listElementAdaptor.onActivityResult(requestCode,resultCode,data);

   */
}

