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

public class QuizActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private QuizAdapter adapter;
    private List<Quiz> quizList;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String QUIZ_LIST_KEY = "quizList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Get the lesson name from the intent
        String lessonName = getIntent().getStringExtra("lessonName");

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load the quiz list for the lesson from shared preferences
        quizList = loadQuizList(lessonName);

        // Create and set the adapter
        adapter = new QuizAdapter(quizList);
        recyclerView.setAdapter(adapter);
    }

    // Load the quiz list for the lesson from shared preferences
// Load the quiz list for the lesson from shared preferences
    private List<Quiz> loadQuizList(String lessonName) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = sharedPreferences.getString(QUIZ_LIST_KEY, null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Quiz>>() {}.getType();
            List<Quiz> savedQuizList = gson.fromJson(json, type);
            if (savedQuizList != null) {
                List<Quiz> filteredQuizList = new ArrayList<>();
                for (Quiz quiz : savedQuizList) {
                    if (quiz.getName().equals(lessonName)) {
                        filteredQuizList.add(quiz);
                    }
                }
                if (!filteredQuizList.isEmpty()) {
                    return filteredQuizList;
                }
            }
        }

        // Create a new quiz list if it doesn't exist or if the lesson quiz is not found
        List<Quiz> quizList = createMockQuizData(lessonName);
        saveQuizList( quizList);
        return quizList;
    }



    // Save the quiz list to shared preferences
    private void saveQuizList(List<Quiz> quizList) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(quizList);
        editor.putString(QUIZ_LIST_KEY, json);
        editor.apply();
    }

    // Helper method to create initial mock quiz data
    private List<Quiz> createMockQuizData(String lessonName) {
        List<Quiz> quizzes = new ArrayList<>();
        quizzes.add(new Quiz("Quiz 1"));
        quizzes.add(new Quiz("Quiz 2"));
        quizzes.add(new Quiz("Quiz 3"));

        return quizzes;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String lessonName = getIntent().getStringExtra("lessonName");
        saveQuizList( quizList); // Save the quiz list to shared preferences before exiting
    }
}
