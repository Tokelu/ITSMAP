package dk.witzell.assignment1_new;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.DividerItemDecoration;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.preference.PreferenceManager;
        import android.widget.Button;


        import com.google.gson.Gson;
        import com.google.gson.reflect.TypeToken;

        import java.lang.reflect.Type;
        import java.util.List;

        import dk.witzell.assignment1_new.adaptors.ListElementAdaptor;
        import dk.witzell.assignment1_new.models.Job;


public class MainActivity extends AppCompatActivity {
    private ListElementAdaptor listElementAdaptor;
    private List<Job> jobsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.activityListRecyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        Button btnExit = findViewById(R.id.activityListButton);

        jobsList = getPrevSessionJobList(getString(R.string.PREV_SESSION_JOB_LIST));
        if (jobsList != null) {
            listElementAdaptor = new ListElementAdaptor(jobsList, this);
        } else {
            List fileData = csvReader();
            jobsList = getJobObjects(fileData);
            listElementAdaptor = new ListElementAdaptor(jobsList, this);
        }

        //recyclerView.setAdapter(listElementAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnExit.setOnClickListener(v -> finish());
    }

    private List<Job> getJobObjects(List fileData) {
        return null;
    }

    private List csvReader() {
        return null;
    }


    private List<Job> getPrevSessionJobList(String index) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(index, null);
        Type type = new TypeToken<List<Job>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    protected void onPause() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(jobsList);
        editor.putString(getString(R.string.PREV_SESSION_JOB_LIST), json);
        editor.apply();
        super.onPause();
    }

 /*   @Override
    protected void OnActivityResult(int requestCode, int resultCode, Intent data)
    {
        listElementAdaptor.onActivityResult(requestCode,resultCode,data);

   */
}

