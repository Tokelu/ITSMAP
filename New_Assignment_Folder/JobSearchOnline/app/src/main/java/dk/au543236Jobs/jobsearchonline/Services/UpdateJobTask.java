package dk.au543236Jobs.jobsearchonline.Services;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import dk.au543236Jobs.jobsearchonline.models.Job;
import dk.au543236Jobs.jobsearchonline.rooms.JobDb;


public class UpdateJobTask extends AsyncTask<Job, Void, Void> {
    private WeakReference<AsyncJobService> jobServiceWeakReference;


    public UpdateJobTask(AsyncJobService jobService) {
        jobServiceWeakReference = new WeakReference<>(jobService);
    }

    @Override
    protected Void doInBackground(Job... job) {
        JobDb.getJobDb(jobServiceWeakReference.get()).JobDbDAO().updateJob(job[0]);
        return null;
    }
}