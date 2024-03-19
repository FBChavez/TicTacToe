package com.example.midtermpractice;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean playerOTurn = true;
    private int roundCount;
    private TextView textViewPlayer, textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer = findViewById(R.id.TVplayerTurn);
        textViewResult = findViewById(R.id.TVwinner);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "btn" + (i + 1) + "_" + (j + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonRestart = findViewById(R.id.btnRestart);
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals(getString(R.string.empty))) {
            return;
        }

        if (playerOTurn) {
            ((Button) v).setText(getString(R.string.O));
            v.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        } else {
            ((Button) v).setText(getString(R.string.X));
            v.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        }

        roundCount++;

        if (checkForWin()) {
            if (playerOTurn) {
                playerOWins();
            } else {
                playerXWins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            playerOTurn = !playerOTurn;
            updatePlayerTurn();
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals(getString(R.string.empty))) {
                return true;
            }
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals(getString(R.string.empty))) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals(getString(R.string.empty))) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals(getString(R.string.empty))) {
            return true;
        }

        return false;
    }

    private void playerOWins() {
        textViewResult.setText(getString(R.string.pOwin));
        Toast.makeText(this, getString(R.string.pOwin), Toast.LENGTH_SHORT).show();
    }

    private void playerXWins() {
        textViewResult.setText(getString(R.string.pXwin));
        Toast.makeText(this, getString(R.string.pXwin), Toast.LENGTH_SHORT).show();
    }

    private void draw() {
        textViewResult.setText(getString(R.string.draw));
        Toast.makeText(this, getString(R.string.draw), Toast.LENGTH_SHORT).show();
    }

    private void updatePlayerTurn() {
        if (playerOTurn) {
            textViewPlayer.setText(getString(R.string.pO));
        } else {
            textViewResult.setText(getString(R.string.pOwin));
        }
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(getString(R.string.empty));
                buttons[i][j].setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            }
        }
        textViewResult.setText(getString(R.string.empty));
        playerOTurn = true;
        roundCount = 0;
        updatePlayerTurn();
    }
}
