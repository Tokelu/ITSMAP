package dk.witzell.jobsearchonline.rooms;

import android.app.Application;

import androidx.room.Room;

public class JobApp extends Application {
    //private JobDb jobDb;
    private JobDb jobDb;

    public JobDb getJobDb(){
        if(jobDb == null){
            jobDb = Room.databaseBuilder(this,JobDb.class,"jobs").build();
        }
        return jobDb;
    }
}
