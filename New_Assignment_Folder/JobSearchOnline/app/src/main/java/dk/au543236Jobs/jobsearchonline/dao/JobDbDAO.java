package dk.au543236Jobs.jobsearchonline.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import static androidx.room.OnConflictStrategy.REPLACE;
import java.util.List;

import dk.au543236Jobs.jobsearchonline.models.Job;

@Dao
public interface JobDbDAO {

    @Query("SELECT * FROM Job")
    List<Job> getAll();

    @Insert
    void addJob(Job... jobs);

    @Delete
    void delete(Job... job);

    @Update(onConflict = REPLACE)
    void updateJob(Job... job);

    @Insert(onConflict = REPLACE)
    void showFavoriteJob(Job job);

}
