package dk.au543236Jobs.jobsearchonline.Services;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import dk.au543236Jobs.jobsearchonline.models.Job;
import dk.au543236Jobs.jobsearchonline.rooms.JobDb;



public class RemoveJobTask extends AsyncTask<Job, Void, Void> {
    private WeakReference<AsyncJobService> jobServiceWeakReference;


    public RemoveJobTask(AsyncJobService jobService) {
        jobServiceWeakReference = new WeakReference<>(jobService);
    }

    @Override
    protected Void doInBackground(Job... jobs) {
        AsyncJobService JobService = jobServiceWeakReference.get();

        JobDb.getJobDb(JobService).JobDbDAO().delete(jobs[0]);
        JobService.getFavouriteJobsList().remove(jobs[0]);
        return null;
    }
}
