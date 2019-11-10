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
import android.widget.EditText;
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
    private List<Job> jobsList = new ArrayList<>();
    private RecyclerView recyclerView;
    boolean isBound = false;
    private EditText searchStringInput;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter listElementAdaptor;
    private AsyncJobService jobService;
    private Button btnUpdate;
    private Button btnExit;
    private static final int requestCodeNotes = 1;



    @Override
    protected void onSaveInstanceState(@NonNull Bundle saveState) {
        super.onSaveInstanceState(saveState);
        saveState.putParcelableArrayList(getString(R.string.jobListSaved),(ArrayList) jobsList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "onCreate: Now in onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (!AsyncJobService.isRunning) {
            Log.d(TAG, "onCreate: No job running ");
            Intent intent = new Intent(this, AsyncJobService.class);
            ContextCompat.startForegroundService(this,intent);
        }
        if (savedInstanceState != null) {
            Log.d(TAG, "onCreate: Job running ");
            jobsList = savedInstanceState.getParcelable(getString(R.string.jobListSaved));
        }


        searchStringInput = findViewById(R.id.activityListTextViewSearchString);
        recyclerView = findViewById(R.id.activityListRecyclerView);
        layoutManager  = new LinearLayoutManager(ListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        listElementAdaptor = new ListElementAdaptor(jobsList, ListActivity.this);
        recyclerView.setAdapter(listElementAdaptor);
        btnExit = findViewById(R.id.activityListButtonExit);
        btnUpdate = findViewById(R.id.activityListButtonUpdate);
        btnExit.setOnClickListener(v -> finish());
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
            isBound = false;
        }
    }
}

