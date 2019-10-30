package dk.witzell.jobsearchonline.Services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.room.Room;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import dk.witzell.jobsearchonline.R;
import dk.witzell.jobsearchonline.models.Job;
import dk.witzell.jobsearchonline.rooms.JobDb;



public class AsyncJobService extends Service {
    private static final String TAG = "SERVICE_JOB";
    private static ArrayList<Job> jobList = new ArrayList<>();
    private final IBinder binder = new ServiceBinder();
    ConnectivityManager connectivityManager;

    int updateDelay = 10000; //ms = 10 sec

    /*private JobDb jobDb;
    public AsyncJobService(){
        jobDb = Room.databaseBuilder(getApplicationContext(), JobDb.class, "Online Job App").build();
    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Job Service Created.");
        //  TODO call function to get data.
        //  TODO create said functions
        timedJobUpdate();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Job Service Destroyed");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startID) {
        timedJobUpdate();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {return binder;}

        //  This was quite tricky ... I "stole" this from a StackOverflow post: https://bit.ly/2peEgHy
        //  this should run the data loading part og the app Asynchronously
    private void timedJobUpdate() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //  TODO call function to get data.
                        }
                        catch (Exception e) {
                            Log.d(TAG, "timedJobUpdate task failed");
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask,0, updateDelay);
    }

    // TODO Remember to put in source for inspiration.
    public void broadcastTaskResult(List<Job> result) {
        Log.d(TAG, "Shouting out Task Result (Broadcast)");
        Intent intent = new Intent();
        intent.setAction(getString(R.string.broadcastRXNotificationString));
        intent.putExtra("Data", (Serializable) result);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public Job updateJob(String companyName) {
        Job job = new Job(companyName);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        String result = null;

        //checking for internetconnection
        if (networkInfo != null) {
            // Get job Data in BG Thread.
            AsyncJobService jobService = new AsyncJobService();
            try {
                // Getting string from JSON object
                result = jobService.execute(companyName).get();
                JSONObject jobData = new JSONObject(result);

                job.update(jobData);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (ExecutionException e) {
                e.printStackTrace();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(this, "No internet connection available", Toast.LENGTH_LONG).show();
        }
        return job;
    }

    public void addJob(String companyName) {
        jobList.add(updateJob(companyName));
        //TODO save jobs to pref-list.
        broadcastTaskResult(jobList);
    }

    public void removeJob(int position) {
        jobList.remove(position);
        broadcastTaskResult(jobList);
        //TODO save jobs to pref-list.
    }

    public void retrieveJobData() {
        Log.d(TAG, "Retrieving Job data");
        jobList.clear();    // to ensure no dupes
        //TODO load jobs  from pref-list.
        Log.d(TAG, "Recieved new Job Data");
        
    }




    public class ServiceBinder extends Binder {
        public AsyncJobService getService() {return AsyncJobService.this;}
    }

}
