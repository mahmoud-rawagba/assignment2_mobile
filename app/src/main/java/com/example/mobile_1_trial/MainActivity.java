package com.example.mobile_1_trial;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LessonAdapter adapter;
    private List<Lesson> lessonList;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String LESSON_LIST_KEY = "lessonList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load the lesson list from shared preferences
        lessonList = loadLessonList();

        // Create and set the adapter
        adapter = new LessonAdapter(lessonList, this); // Pass the activity context
        recyclerView.setAdapter(adapter);
    }

    // Load the lesson list from shared preferences
    private List<Lesson> loadLessonList() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = sharedPreferences.getString(LESSON_LIST_KEY, null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Lesson>>() {}.getType();
            return gson.fromJson(json, type);
        } else {
            return createMockLessonData(); // Create initial lesson data if shared preferences is empty
        }
    }

    // Save the lesson list to shared preferences
    private void saveLessonList(List<Lesson> lessonList) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(lessonList);
        editor.putString(LESSON_LIST_KEY, json);
        editor.apply();
    }

    // Helper method to create initial mock lesson data
    private List<Lesson> createMockLessonData() {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson("Quizzes"));
        lessons.add(new Lesson("Lessons"));
        lessons.add(new Lesson("Games"));
        return lessons;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveLessonList(lessonList); // Save the lesson list to shared preferences before exiting
    }
}
