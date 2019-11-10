package dk.au543236Jobs.jobsearchonline.Services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.text.Html;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import dk.au543236Jobs.jobsearchonline.ListActivity;
import dk.au543236Jobs.jobsearchonline.R;
import dk.au543236Jobs.jobsearchonline.models.Job;
import dk.au543236Jobs.jobsearchonline.helpers.ToastHelper;






public class AsyncJobService extends Service {
    private static final String TAG = "SERVICE_JOB";
    private static ArrayList<Job> jobList = new ArrayList<>();
    private final IBinder binder = new JobServiceBinder();
    ConnectivityManager connectivityManager;


    int updateDelay = 10000; //ms = 10 sec


    private String alarmReciecerAction = "alarmReceiver";
    public List<Job> favouriteJobsList = new ArrayList<>();
    public List<Job> currentJobList = new ArrayList<>();
    private static final String CHANNEL_ID = "jobServiceChannel";
    private static final int NOTIFICATION_ID = 1;
    public static boolean isRunning = false;
    private RequestQueue queue;
    public static final String BROADCAST_RESULT = "New jobs downloaded";
    private static String searchBaseString = "https://jobs.github.com/positions.json?&search=";
    public static String searchBaseStringEmpty = "https://jobs.github.com/positions.json";
    private Job currentJob;



    @Override   //cleaning the list, getting any saved jobs.
    public int onStartCommand(Intent intent, int flag, int startID) {
        notificationTimer();
        Notification jobNotification = buildNotification();
        startForeground(NOTIFICATION_ID, jobNotification);
        isRunning = true;
        currentJobList.clear();
        retrieveSavedJobs();
        return START_STICKY;
    }

        // The weather search demo was useful insight here
    public void sendRequest(String url) {
        if (queue == null){
            queue = Volley.newRequestQueue(this);
        }
        final boolean isSearch = url.contains("&search=");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            Log.d(TAG, "Volley request success");
            if (response == null || response.equals("[]")) {
                ToastHelper.showToast(AsyncJobService.this, R.string.jobsNotFound);
                currentJobList.clear();
                sendBroadcast();
            }
            else {
                jsonParser(response, isSearch);
                sendBroadcast();
            }
        }, error -> {
            Log.d(TAG, "onErrorResponse: failed");
            ToastHelper.showToast(AsyncJobService.this, R.string.noNetworkConnection);
        });
        queue.add(stringRequest);
    }

    private void jsonParser(String jsonString, boolean isSearch) {
        Gson gson = new GsonBuilder().create();
        Job[] jobList = gson.fromJson(jsonString, Job[].class);
        if(jobList != null) {
            Job[] parsedJobList = jsonParserGetDescription(jobList);
            if(isSearch) {
                listUpdaterWithSearch(parsedJobList);
            }
            else {
                listUpdaterNoSearch(parsedJobList);
            }
        }
    }

    private Job[] jsonParserGetDescription(Job[] jobs) {
        for (Job job: jobs) {
            String jobDescription = job.getDescription();
            job.setDescription(Html.fromHtml(jobDescription).toString());
        }
        return jobs;
    }

    private void listUpdaterWithSearch(Job[] jobs) {
        currentJobList.clear();
        currentJobList.addAll(favouriteJobsList);

        for (Job job : jobs) {
            if (!favouriteJobsList.contains(job)) {
                currentJobList.add(job);
            }
        }
    }

    private void listUpdaterNoSearch(Job[] jobs) {
        int sizeCurrentList = favouriteJobsList.size();
        int nbrOfJobs = (jobs.length >= 10 ? 10 : jobs.length);

        // remove non DB jobs.
        List<Job> tempJobs = favouriteJobsList;
        currentJobList.clear();
        currentJobList.addAll(favouriteJobsList);

        if (sizeCurrentList != 0) {
            for (int i = 0; i < nbrOfJobs; ++i) {
                if (!tempJobs.contains(jobs[i])) {
                    currentJobList.add(jobs[i]);
                }
                else {
                    nbrOfJobs++;
                }
            }
        }
        else {
            currentJobList.addAll(Arrays.asList(jobs).subList(0, nbrOfJobs));
        }
    }
    //More or less stolen from service demo
    public void sendBroadcast() {
        Intent intent = new Intent();
        intent.setAction(BROADCAST_RESULT);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public List<Job> retrieveCurrentJobList() { return currentJobList; }

    private Notification buildNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel jobServiceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    getString(R.string.jobServiceChannel),
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(jobServiceChannel);
            }
        }

        Intent intent = new Intent(this, ListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.notificationTitle))
                .setContentText(getString(R.string.notificationText))
                .setSmallIcon(R.drawable.job_search_icon)
                .setContentIntent(pendingIntent)
                .build();
    }

    public void retrieveSavedJobs() {
        GetJobListTask jobTask = new GetJobListTask(this);
        jobTask.execute();
    }

    public void listUpdater(String search) {
        String searchString = searchBaseString + search;
        sendRequest(searchString);
    }

    public void removeJob(Job job) {
        if (job != null) {  // delete job from list and from DB
            if (job.getIsFavoriteMarked()) {
                RemoveJobTask task = new RemoveJobTask(this);
                task.execute(job);

                currentJobList.remove(job);
                ToastHelper.showToast(this, R.string.jobRmFavouriteAndList);
            }
            else {
                currentJobList.remove(job);
                ToastHelper.showToast(this, R.string.jobRmFromList);
            }
            sendBroadcast();
        }
    }

    public void updateJob(Job job) {
        if (job != null) {
            //save job in DB
            if (job.getIsFavoriteMarked() && !favouriteJobsList.contains(job)) {
                SaveJobTask task = new SaveJobTask(this);
                task.execute(job);
                ToastHelper.showToast(this, R.string.jobAddedToFavourites);
            }
            else if (job.getIsFavoriteMarked() && favouriteJobsList.contains(job)) {
                UpdateJobTask task = new UpdateJobTask(this);
                task.execute(job);
                ToastHelper.showToast(this, R.string.jobUpdated);

            } else if (!job.getIsFavoriteMarked() && favouriteJobsList.contains(job)) {
                RemoveJobTask task = new RemoveJobTask(this);
                task.execute(job);
                ToastHelper.showToast(this, R.string.jobRmFromFavourites);

            } else {
                ToastHelper.showToast(this, R.string.tempSaved);
            }
            sendBroadcast();
        }
    }

    private void randomJobNotify() {
        if (!favouriteJobsList.isEmpty()) {
            Random rnd = new Random(42);
            int randomJobPos = rnd.nextInt(favouriteJobsList.size());
            Job job = favouriteJobsList.get(randomJobPos);

            Intent intent = new Intent(this, ListActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.job_search_icon)
                    .setSmallIcon(R.drawable.ic_launcher_background2)
                    .setContentTitle(job.getTitle())
                    .setContentText(job.getCompanyName())
                    .setSubText(getString(R.string.jobNotificationText))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
    }

    private void notificationTimer() {
        final int notificationInterval = 60000; //ms = 60 sec
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(alarmReciecerAction);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+notificationInterval, notificationInterval, pendingIntent);
        }

        //Registering the receiver.
        IntentFilter filter = new IntentFilter(alarmReciecerAction);
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        registerReceiver(alarmReceiver, filter);

    }
    public void addJob(List<Job> jobs)                 {this.currentJobList.addAll(jobs);}
    public void setCurrentJob(Job job)                 {this.currentJob = job;}
    public Job getCurrentJob()                         {return this.currentJob;}
    public void setFavouriteJobsList(List<Job> jobs)   {this.favouriteJobsList = jobs;}
    public List<Job> getFavouriteJobsList()            {return  this.favouriteJobsList;}

    public class JobServiceBinder extends Binder {
        public AsyncJobService getService() {return AsyncJobService.this;}
    }

    @Override
    public IBinder onBind(Intent intent) {return binder;}

    public class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "RX: Notification" );
            randomJobNotify();
        }
    }
}
