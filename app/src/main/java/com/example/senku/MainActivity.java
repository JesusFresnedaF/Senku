package com.example.senku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton buttons[];
    private int dimension = 33;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons = new ImageButton[dimension];
        gridLayout = findViewById(R.id.gridLayout);
        loadIds();
    }

    public void loadIds(){
        int id = 1;
        for (int i = 0; i < dimension; i++) {
            int buttonId =getResources().getIdentifier("imageButton" + id, "id", getPackageName());
            ImageButton aux = findViewById(buttonId);
            if(aux != null){
                buttons[i] = aux;
                buttons[i].setImageResource(R.drawable.boton_equipo_2);
                buttons[i].setOnTouchListener(new View.OnTouchListener() {
                    private int lastAction;
                    private int initialX, initialY;
                    private float initialTouchX, initialTouchY;
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch(event.getActionMasked()){
                            case MotionEvent.ACTION_DOWN:
                                initialX = v.getLeft();
                                initialY = v.getTop();
                                initialTouchX = event.getRawX();
                                initialTouchY = event.getRawY();
                                lastAction = MotionEvent.ACTION_MOVE;
                                break;
                            case MotionEvent.ACTION_MOVE:
                                int deltaX = (int)(event.getRawX() - initialTouchX);
                                int deltaY = (int)(event.getRawY() - initialTouchY);

                                v.setX(initialX + deltaX);
                                v.setY(initialY + deltaY);

                                lastAction = MotionEvent.ACTION_MOVE;
                                break;
                            case MotionEvent.ACTION_UP:
                                if(lastAction == MotionEvent.ACTION_MOVE){
                                    int[] location = new int[2];
                                    gridLayout.getLocationOnScreen(location);
                                    int gridX = location[0];
                                    int gridY = location[1];

                                    // Calcular la posición en celdas del GridLayout
                                    int cellWidth = gridLayout.getWidth() / gridLayout.getColumnCount();
                                    int cellHeight = gridLayout.getHeight() / gridLayout.getRowCount();

                                    int newX = (int) (event.getRawX() - gridX) / cellWidth * cellWidth;
                                    int newY = (int) (event.getRawY() - gridY) / cellHeight * cellHeight;

                                    // Ajustar la posición del botón a una celda del GridLayout
                                    v.setX(newX);
                                    v.setY(newY);
                                }
                        }
                        return true;
                    }
                });
            }
            id++;
        }
        buttons[16].setImageResource(R.drawable.default_button);
    }
}