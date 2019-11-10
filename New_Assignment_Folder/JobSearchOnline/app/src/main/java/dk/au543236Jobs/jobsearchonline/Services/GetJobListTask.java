package dk.au543236Jobs.jobsearchonline.Services;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import dk.au543236Jobs.jobsearchonline.models.Job;
import dk.au543236Jobs.jobsearchonline.rooms.JobDb;

/*TODO    ##################### THIS IS DONE ######################################*/


public class GetJobListTask extends AsyncTask<Void,Void, List<Job>> {
    private WeakReference<AsyncJobService> jobServiceWeakReference;

    public GetJobListTask(AsyncJobService asyncJobService) {
        jobServiceWeakReference = new WeakReference<>(asyncJobService);
    }

    @Override
    protected List<Job> doInBackground(Void... voids) {
        return JobDb.getJobDb(jobServiceWeakReference.get()).JobDbDAO().getAll();
    }

    @Override
    public void onPostExecute(List<Job> jobs) {
        super.onPostExecute(jobs);

        AsyncJobService jobService = jobServiceWeakReference.get();
        jobService.addJob(jobs);
        jobService.setFavouriteJobsList(jobs);
        jobService.sendBroadcast();
    }
}
