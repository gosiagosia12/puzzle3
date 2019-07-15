package com.mr.puzzle3;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mr.puzzle3.TaskListContent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ImageChoosing extends AppCompatActivity
implements TaskFragment.OnListFragmentInteractionListener{

    private TaskListContent.Task currentTask;
    private final String CURRENT_TASK_KEY = "CurrentTask";
    private final String TASKS_JSON_FILE = "tasks.json";

    int CONTACT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_choosing);

        TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1, "kek", "flaming1"));
        restoreFromJson();
        FloatingActionButton fabCameraAdd = findViewById(R.id.fabCameraAdd);
        fabCameraAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityAdd.class);
                startActivityForResult(intent, CONTACT_REQUEST);
            }
        });

    }





    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            String taskTitle = data.getStringExtra("taskTitle");
            String selectedImage = data.getStringExtra("selectedImage");
            String picPath = data.getStringExtra("picPath");

            if(picPath == null){
                if(taskTitle.isEmpty()){
                    TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1, "kek", selectedImage));
                }else{
                    if(taskTitle.isEmpty())
                        taskTitle = "kek";

                    TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1, taskTitle, selectedImage));
                }
            }else
            if(taskTitle.isEmpty()){
                TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1, "kek", picPath));
            }else{
                if(taskTitle.isEmpty())
                    taskTitle = "kek";

                TaskListContent.addItem(new TaskListContent.Task("Task." + TaskListContent.ITEMS.size() + 1, taskTitle, picPath));
            }
            Toast.makeText(this, "Dodano nowy element do listy", Toast.LENGTH_SHORT).show();
            ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
        }
        else if(resultCode == RESULT_CANCELED) {}
    }


    private void saveTasksToJson(){
        Gson gson = new Gson();
        String listJson = gson.toJson(TaskListContent.ITEMS);
        FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(TASKS_JSON_FILE, MODE_PRIVATE);
            FileWriter writer = new FileWriter(outputStream.getFD());
            writer.write(listJson);
            writer.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void restoreFromJson(){
        Toast.makeText(this, "Wczytywanie listy...", Toast.LENGTH_SHORT).show();
        FileInputStream inputStream;
        int DEFAULT_BUFFER_SIZE = 10000;
        Gson gson = new Gson();
        String readJson;

        try {
            inputStream = openFileInput(TASKS_JSON_FILE);
            FileReader reader = new FileReader(inputStream.getFD());
            char[] buf = new char[DEFAULT_BUFFER_SIZE];
            int n;
            StringBuilder builder = new StringBuilder();
            while ((n = reader.read(buf)) >= 0) {
                String tmp = String.valueOf(buf);
                String substring = (n < DEFAULT_BUFFER_SIZE) ? tmp.substring(0, n) : tmp;
                builder.append(substring);
            }
            reader.close();
            readJson = builder.toString();
            Type collectionType = new TypeToken<List<TaskListContent.Task>>() {
            }.getType();
            List<TaskListContent.Task> o = gson.fromJson(readJson, collectionType);
            if (o != null) {
                TaskListContent.clearList();
                for (TaskListContent.Task task : o) {
                    TaskListContent.addItem(task);
                }
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    protected void onDestroy(){
        saveTasksToJson();
        Toast.makeText(this, "Wszystkie elementy listy zostały zapisane", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onListFragmentClickInteraction(TaskListContent.Task task, int position) {
        int pos = position + 1;
        Toast.makeText(this, "kek" + " " + pos + ". element listy", Toast.LENGTH_SHORT).show();
        currentTask = task;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            displayTaskInFragment(task);
        }else{
            startSecondActivity(task, position);
        }
    }//}

    @Override
    public void onDeleteClick(int position) {

    }


 /*   @Override
    public void onDeleteClick(int position) {
        showDeleteDialog();
        currentItemPosition = position;
    }*/

    public static final String taskExtra = "taskExtra";

    private void startSecondActivity(TaskListContent.Task task, int position){
        Intent intent = new Intent(this, TaskInfoActivity.class);
        intent.putExtra(taskExtra, task);
        startActivity(intent);
    }

    private void displayTaskInFragment(TaskListContent.Task task){
        TaskInfoFragment taskInfoFragment = ((TaskInfoFragment) getSupportFragmentManager().findFragmentById(R.id.displayFragment));
        if(taskInfoFragment != null){
            taskInfoFragment.displayTask(task);
        }
    }

 /*   private int currentItemPosition = -1;
    private void showDeleteDialog(){
        DeleteDialog.newInstance().show(getSupportFragmentManager(), getString(R.string.delete_dialog_tag));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(currentItemPosition != -1 && currentItemPosition < TaskListContent.ITEMS.size()){
            int pos = currentItemPosition + 1;
            Toast.makeText(this, getString(R.string.long_click_msg) + " " + pos + ". element listy", Toast.LENGTH_SHORT).show();
            TaskListContent.removeItem(currentItemPosition);}
        ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        View v = findViewById(R.id.addButton);
        if(v != null){
            Snackbar.make(v, "kek", Snackbar.LENGTH_LONG)
                    .setAction("kek", new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            showDeleteDialog();
                        }
                    }).show();
        }
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState){
        if(currentTask != null)
            outState.putParcelable(CURRENT_TASK_KEY, currentTask);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            if(currentTask != null)
                displayTaskInFragment(currentTask);
        }
    }

}
