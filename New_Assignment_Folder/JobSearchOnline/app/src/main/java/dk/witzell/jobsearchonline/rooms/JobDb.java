package dk.witzell.jobsearchonline.rooms;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import dk.witzell.jobsearchonline.dao.JobDbDAO;
import dk.witzell.jobsearchonline.models.Job;

@Database(entities={Job.class}, version = 1)
@TypeConverters({DateConverter.class})

public abstract class JobDb extends RoomDatabase {
    public abstract JobDbDAO JobDbDAO();
}
