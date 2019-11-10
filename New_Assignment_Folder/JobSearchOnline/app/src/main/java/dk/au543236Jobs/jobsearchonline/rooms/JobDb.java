package dk.au543236Jobs.jobsearchonline.rooms;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import dk.au543236Jobs.jobsearchonline.dao.JobDbDAO;
import dk.au543236Jobs.jobsearchonline.models.Job;

import static androidx.room.Room.databaseBuilder;



@Database(entities={Job.class}, version = 4, exportSchema = false)
//@TypeConverters({DateConverter.class})

public abstract class JobDb extends RoomDatabase {
    public abstract JobDbDAO JobDbDAO();
    private static JobDb jobDb;

    public static JobDb getJobDb(Context context) {
        if (jobDb == null) { jobDb = databaseBuilder(context.getApplicationContext(), JobDb.class, "JobDb").addMigrations(MIGRATION_3_4).build(); } return jobDb;
    }

        //https://developer.android.com/training/data-storage/room/migrating-db-versions
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) { database.execSQL("ALTER TABLE JOB ADD COLUMN isFavourite INTEGER NOT NULL DEFAULT 0"); }
    };

}
