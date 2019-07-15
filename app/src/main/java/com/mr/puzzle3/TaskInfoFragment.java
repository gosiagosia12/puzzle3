package com.mr.puzzle3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.mr.puzzle3.TaskListContent;



/**
 * A simple {@link Fragment} subclass.
 */
public class TaskInfoFragment extends Fragment/* implements View.OnClickListener */{


    public TaskInfoFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_info, container, false);
    }


    public void displayTask(TaskListContent.Task task){
        FragmentActivity activity = getActivity();
        (activity.findViewById(R.id.displayFragment)).setVisibility(View.VISIBLE);
        TextView taskInfoTitle = activity.findViewById(R.id.taskInfoTitle);

        final ImageView taskInfoImage = activity.findViewById(R.id.taskInfoImage);

        taskInfoTitle.setText("Tytuł: " + task.title);
        if(task.picPath != null && !task.picPath.isEmpty()) {
            if (task.picPath.contains("avatar")) {
                Drawable taskDrawable;
                switch (task.picPath) {
                    case "1":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                        break;
                    case "2":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                        break;
                    case "3":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                        break;
                    case "4":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                        break;
                    case "5":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                        break;
                    case "6":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                        break;
                    case "7":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                        break;
                    case "8":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                        break;
                    case "9":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                        break;
                    case "10":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                        break;
                    case "11":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                        break;
                    case "12":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                        break;
                    case "14":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                        break;
                    case "15":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                        break;
                    case "16":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                        break;
                    default:
                        taskDrawable = activity.getResources().getDrawable(R.drawable.flaming);
                }
                taskInfoImage.setImageDrawable(taskDrawable);
            }else{
                Bitmap image = BitmapFactory.decodeFile(task.picPath);
                taskInfoImage.setImageBitmap(image);
            }

        }else{
            taskInfoImage.setImageDrawable(activity.getResources().getDrawable(R.drawable.flaming));
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        Intent intent = getActivity().getIntent();
        if(intent != null){
            TaskListContent.Task receivedTask = intent.getParcelableExtra(ImageChoosing.taskExtra);
            if(receivedTask != null){
                displayTask(receivedTask) ;
            }
        }
    }
}