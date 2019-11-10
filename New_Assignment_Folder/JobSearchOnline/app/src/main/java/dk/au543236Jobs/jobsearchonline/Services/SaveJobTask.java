package dk.au543236Jobs.jobsearchonline.Services;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import dk.au543236Jobs.jobsearchonline.models.Job;
import dk.au543236Jobs.jobsearchonline.rooms.JobDb;


public class SaveJobTask extends AsyncTask<Job, Void, Void> {
    private WeakReference<AsyncJobService> jobServiceWeakReference;


    public SaveJobTask(AsyncJobService jobService) {
        jobServiceWeakReference = new WeakReference<>(jobService);
    }

    @Override
    protected Void doInBackground(Job... jobData) {
        AsyncJobService jobService = jobServiceWeakReference.get();
        JobDb.getJobDb(jobService).JobDbDAO().addJob(jobData[0]);
//        AsyncJobService.getFavouriteJobsList().add(jobData[0])
        return null;
    }
}
