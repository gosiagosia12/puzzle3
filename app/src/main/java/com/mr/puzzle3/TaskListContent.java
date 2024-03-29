package com.mr.puzzle3;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskListContent {

    public static final List<Task> ITEMS = new ArrayList<Task>();

    public static final Map<String, Task> ITEM_MAP = new HashMap<String, Task>();


    public static void addItem(Task item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Task createTask(int position, String title, String picPath) {
        return new Task(String.valueOf(position), title, picPath);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
          // for (int i = 0; i < position; i++) {
        builder.append("\nInformation about film.");
        return builder.toString();
    }


    public static class Task implements Parcelable {
        public final String id;
        public final String title;
        public String picPath;

        public Task(String id, String title) {
            this.id = id;
            this.title = title;
            this.picPath = "";
        }
        public Task(String id, String title, String picPath) {
            this.id = id;
            this.title = title;
            this.picPath = picPath;
        }

        protected Task(Parcel in) {
            id = in.readString();
            title = in.readString();
            picPath = in.readString();
        }

        public static final Creator<Task> CREATOR = new Creator<Task>() {
            @Override
            public Task createFromParcel(Parcel in) {
                return new Task(in);
            }

            @Override
            public Task[] newArray(int size) {
                return new Task[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(title);
            dest.writeString(picPath);
        }

        public void setPicPath(String path){
            this.picPath = path;
        }
    }

    public static void removeItem(int position){
        String itemId = ITEMS.get(position).id;
        ITEMS.remove(position);
        ITEM_MAP.remove(itemId);
    }

    public static void clearList(){
        ITEMS.clear();
        ITEM_MAP.clear();
    }
}