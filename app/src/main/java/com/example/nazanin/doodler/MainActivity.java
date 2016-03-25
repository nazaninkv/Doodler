package com.example.nazanin.doodler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private DrawingView getDrawingView() {
        int id = this.getResources().getIdentifier("drawing", "id", this.getPackageName());
        return (DrawingView)this.findViewById(id);
    }

    public void onColorButtonClick(View buttonView) {
        ColorDrawable background = (ColorDrawable)buttonView.getBackground();
        getDrawingView().setStrokeColor(background.getColor());
    }

    public void onThicknessButtonClick(View thicknessButton) {
        String tag = (String)thicknessButton.getTag();
        if (tag.equals("thin")) {
            getDrawingView().setStrokeWidth(10);
        } else if (tag.equals("medium")) {
            getDrawingView().setStrokeWidth(50);
        } else if (tag.equals("thick")) {
            getDrawingView().setStrokeWidth(100);
        }
    }

    public void undo(View undoButton) {
        getDrawingView().undo();
    }
}
