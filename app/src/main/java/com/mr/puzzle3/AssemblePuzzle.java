package com.mr.puzzle3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mr.puzzle3.CustomAdapter;
import com.mr.puzzle3.GestureDetectGridView;
import com.mr.puzzle3.R;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class AssemblePuzzle extends AppCompatActivity {

    private static GestureDetectGridView mGridView;

    public static int COLUMNS;
    public static int DIMENSIONS;

    private static int mColumnWidth, mColumnHeight;

    public static final String up = "up";
    public static final String down = "down";
    public static final String left = "left";
    public static final String right = "right";

    private static String[] elements;

    int BUTTON_REQUEST = 1;

    private static ArrayList<BitmapDrawable> drawa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_puzzle);

        final Bundle extras = getIntent().getExtras();
        //System.out.println(extras.size());
        //System.out.println(extras.getInt("dimensions"));
        COLUMNS = extras.getInt("dimensions");
        DIMENSIONS = COLUMNS*COLUMNS;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flaming);
        drawa = splitImage(bitmap, DIMENSIONS, getApplicationContext());
        init();
    }

//init
    private void init(){
        //System.out.println(COLUMNS);
        //System.out.println(RESULT_OK);

        mGridView = (GestureDetectGridView)findViewById(R.id.puzzle);
        mGridView.setNumColumns(COLUMNS);
        elements = new String[DIMENSIONS];
        for (int i = 0; i < DIMENSIONS; i++) {
            elements[i] = String.valueOf(i);
        }

        int index;
        String temp;
        Random random = new Random();

        for (int i = elements.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = elements[index];
            elements[index] = elements[i];
            elements[i] = temp;
        }

        setDimensions();
    }

//setDimensions
    private void setDimensions() {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                int statusbarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusbarHeight;

                mColumnWidth = displayWidth / COLUMNS;
                mColumnHeight = requiredHeight / COLUMNS;

                display(getApplicationContext());
            }
        });
    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    private static void display(Context context) {
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;

        for (int i = 0; i < elements.length; i++) {
            button = new Button(context);

            switch(elements[i]) {
                case "0" : button.setBackground(drawa.get(0)) ; break;
                case "1" : button.setBackground(drawa.get(1)) ; break;
                case "2" : button.setBackground(drawa.get(2)) ; break;
                case "3" : button.setBackground(drawa.get(3)) ; break;
                case "4" : button.setBackground(drawa.get(4)) ; break;
                case "5" : button.setBackground(drawa.get(5)) ; break;
                case "6" : button.setBackground(drawa.get(6)) ; break;
                case "7" : button.setBackground(drawa.get(7)) ; break;
                case "8" : button.setBackground(drawa.get(8)) ; break;
                case "9" : button.setBackground(drawa.get(9)) ; break;
                case "10": button.setBackground(drawa.get(10)); break;
                case "11": button.setBackground(drawa.get(11)); break;
                case "12": button.setBackground(drawa.get(12)); break;
                case "13": button.setBackground(drawa.get(13)); break;
                case "14": button.setBackground(drawa.get(14)); break;
                case "15": button.setBackground(drawa.get(15)); break;
                case "16": button.setBackground(drawa.get(16)); break;
                case "17": button.setBackground(drawa.get(17)); break;
                case "18": button.setBackground(drawa.get(18)); break;
                case "19": button.setBackground(drawa.get(19)); break;
                case "20": button.setBackground(drawa.get(20)); break;
                case "21": button.setBackground(drawa.get(21)); break;
                case "22": button.setBackground(drawa.get(22)); break;
                case "23": button.setBackground(drawa.get(23)); break;
                case "24": button.setBackground(drawa.get(24)); break;
                case "25": button.setBackground(drawa.get(25)); break;
                case "26": button.setBackground(drawa.get(26)); break;
                case "27": button.setBackground(drawa.get(27)); break;
                case "28": button.setBackground(drawa.get(28)); break;
                case "29": button.setBackground(drawa.get(29)); break;
                case "30": button.setBackground(drawa.get(30)); break;
                case "31": button.setBackground(drawa.get(31)); break;
                case "32": button.setBackground(drawa.get(32)); break;
                case "33": button.setBackground(drawa.get(33)); break;
                case "34": button.setBackground(drawa.get(34)); break;
                case "35": button.setBackground(drawa.get(35)); break;
            }
            buttons.add(button);
        }

        mGridView.setAdapter(new CustomAdapter(buttons, mColumnWidth, mColumnHeight));
    }

    private static void swap(Context context, int currentPosition, int swap) {
        String newPosition = elements[currentPosition + swap];
        elements[currentPosition + swap] = elements[currentPosition];
        elements[currentPosition] = newPosition;
        display(context);

        if (isSolved()) Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
    }

    public static void move(Context context, String direction, int position) {

        // Upper-left-corner tile
        if (position == 0) {

            if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-center tiles
        } else if (position > 0 && position < COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-right-corner tile
        } else if (position == COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Left-side tiles
        } else if (position > COLUMNS - 1 && position < DIMENSIONS - COLUMNS &&
                position % COLUMNS == 0) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Right-side AND bottom-right-corner tiles
        } else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) {

                // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                // right-corner tile.
                if (position <= DIMENSIONS - COLUMNS - 1) swap(context, position,
                        COLUMNS);
                else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-left corner tile
        } else if (position == DIMENSIONS - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-center tiles
        } else if (position < DIMENSIONS - 1 && position > DIMENSIONS - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Center tiles
        } else {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else swap(context, position, COLUMNS);
        }
    }

    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < elements.length; i++) {
            if (elements[i].equals(String.valueOf(i))) {
                solved = true;
            } else {
                solved = false;
                break;
            }
        }
        return solved;
    }

    private ArrayList<BitmapDrawable> splitImage(Bitmap bitmap, int chunkNumbers, Context context) {

        //For the number of rows and columns of the grid to be displayed
        int rows,cols;

        //For height and width of the small image chunks
        int chunkHeight,chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        ArrayList<Bitmap> chunkedImages;
        chunkedImages = new ArrayList<Bitmap>(chunkNumbers);

        //Getting the scaled bitmap of the source image
       // BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
      //  Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        rows = cols = (int) Math.sqrt(chunkNumbers);
        chunkHeight = bitmap.getHeight()/rows;
        chunkWidth = bitmap.getWidth()/cols;
        ArrayList<BitmapDrawable> drawables  = new ArrayList<BitmapDrawable>(chunkNumbers);
        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for(int x=0; x<rows; x++){
            int xCoord = 0;
            for(int y=0; y<cols; y++){

                chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                BitmapDrawable bdrawable = new BitmapDrawable(context.getResources(),Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                drawables.add(bdrawable);
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }
        return drawables;
    }

}
