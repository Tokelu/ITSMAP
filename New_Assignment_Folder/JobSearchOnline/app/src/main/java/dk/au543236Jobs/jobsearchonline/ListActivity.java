package dk.au543236Jobs.jobsearchonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dk.au543236Jobs.jobsearchonline.Services.AsyncJobService;
import dk.au543236Jobs.jobsearchonline.adaptors.ListElementAdaptor;
import dk.au543236Jobs.jobsearchonline.helpers.ToastHelper;
import dk.au543236Jobs.jobsearchonline.models.Job;
//import dk.witzell.jobsearchonline.utils.csvParser;


public class ListActivity extends AppCompatActivity implements ListElementAdaptor.OnClickedJobListener{
    private static final String TAG = "LIST_ACTIVITY";
    private List<Job> jobsList;
    private RecyclerView recyclerView;
    boolean isBound = false;
    private TextView searchStringInput;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter listElementAdaptor;
    private AsyncJobService jobService;
    private Button btnRemove;
    private Button btnExit;
    private static final int requestCodeNotes = 1;

//    private ListElementAdaptor listElementAdaptor;
//    private static final String URL_DATA = "https://jobs.github.com/positions.json";
//    private final int jsonKey = 1;
//    private static ProgressDialog progressDialog;
//    boolean ActivityBeforeBinding = false;
//    int position;
//    private EditText searchStringInput;


    @Override
    protected void onSaveInstanceState(@NonNull Bundle saveState) {
        super.onSaveInstanceState(saveState);
        saveState.putSerializable(getString(R.string.jobListSaved),(ArrayList) jobsList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (!AsyncJobService.isRunning) {
            Intent intent = new Intent(this, AsyncJobService.class);
            ContextCompat.startForegroundService(this,intent);
        }
        if (savedInstanceState != null) {
            jobsList = (ArrayList<Job>) savedInstanceState.getSerializable(ListElementAdaptor.JOB_FROM_ADAPTOR);
        }


        recyclerView = findViewById(R.id.activityListRecyclerView);
        searchStringInput = findViewById(R.id.activityListTextViewSearchField);
        layoutManager  = new LinearLayoutManager(ListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        listElementAdaptor = new ListElementAdaptor(jobsList, ListActivity.this);
        recyclerView.setAdapter(listElementAdaptor);
        btnExit = findViewById(R.id.activityListButtonExit);
        btnRemove = findViewById(R.id.activityListButtonUpdate);
        btnExit.setOnClickListener(v -> finish());

/*
        jobsList = getJobListFromPrevSession(getString(R.string.PREV_SESSION_JOB_LIST));
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listElementAdaptor);
*/
    }




    public void btnClickedUpdate(View view) {
        String searchStringInput_ = searchStringInput.getText().toString();
        if (isBound) {
                //  Searching using userinput
            if (!searchStringInput_.equals("")) {
                jobService.listUpdater(searchStringInput_);
                ToastHelper.showToast(this, R.string.searchingForJobs);
            }
                //  Searching with no user input
            else {
                jobService.sendRequest(AsyncJobService.searchBaseStringEmpty);
                ToastHelper.showToast(this, R.string.searchingForJobsNoString);
            }
        }
    }

    @Override
    public void onClickedJob(int position) {
        Intent intent = new Intent(this, JobActivity.class);
        jobService.setCurrentJob(jobsList.get(position));
        startActivity(intent);
    }

    @Override
    public void onLongClickedJob(int position) {
        Intent intent = new Intent(this, NotesActivity.class);
        jobService.setCurrentJob(jobsList.get(position));
        startActivityForResult(intent, requestCodeNotes);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == requestCodeNotes) {
            listElementAdaptor.notifyDataSetChanged();
        }
    }

    private ServiceConnection jobServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            AsyncJobService.JobServiceBinder binder = (AsyncJobService.JobServiceBinder) iBinder;
            jobService = binder.getService();
            Log.d(TAG, "onServiceConnected: Connected to Service");
            isBound = true;

            if (jobsList.isEmpty()) {
                jobService.sendRequest(AsyncJobService.searchBaseStringEmpty);
                ToastHelper.showToast(ListActivity.this, R.string.searchingForJobsNoString);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: Broadcast recived in ListActivity");
            if (isBound) {
                jobsList.clear();
                jobsList.addAll(jobService.retrieveCurrentJobList());
                listElementAdaptor.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, AsyncJobService.class);
        bindService(intent, jobServiceConnection, Context.BIND_AUTO_CREATE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AsyncJobService.BROADCAST_RESULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(jobServiceConnection);
        }
    }

/*
    private List csvReader() {
        InputStream stream = getResources().openRawResource(R.raw.jobs);
        csvParser parser = new csvParser(stream);
        return parser.getAssets();
    }
    */
/*
    private List<Job> getJobObjects(List<Job> fileData)
    {
        ArrayList<Job> jobList = new ArrayList<>();

        for (int i = 0; i < fileData.size(); i++)
        {
            Job currentJob = fileData.get(i);
            if(!currentJob.getCompanyName().equals("company"))
            {
                jobList.add(currentJob);
            }
        }

        for (Job job : jobList)
        {
            job.setHasUserNotes(false);     //job.setNotes("");
            job.setHasCoolnessScore(false); //job.setCoolScore("NA");
            job.setHasApplied(false);
            job.setCoolnessScore(job.getcoolnessScore());
        }
        return jobList;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        listElementAdaptor.onActivityResult(requestCode, resultCode, data);
    }
*/
/*
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
*/
/*
    private List<Job> getJobListFromPrevSession(String index) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(index, null);
        Type type = new TypeToken<List<Job>>() {}.getType();
        return gson.fromJson(json, type);
    }
*/
//    @Override
//    protected void OnActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        listElementAdaptor.onActivityResult(requestCode, resultCode, data);
//    }


}

