package com.shokoofeadeli.tictactoegame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.shokoofeadeli.tictactoegame.G.preferences;

public class MainActivity extends AppCompatActivity {
  private ImageView[] imageList = new ImageView[9];
  private boolean isPlayerOne = true;
  private byte[][] cells = new byte[3][3];
  private ImageView imgPlayer1;
  private ImageView imgPlayer2;
  private int countPlayerOneWin;
  private int countPlayerTwoWin;
  private TextView txtCounterPalyerOneWin;
  private TextView txtCounterPalyerTwoWin;
  private int moveCount;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initialize();
    preferencesData();

  }

  private void initialize() {
    imageList[0] = (ImageView) findViewById(R.id.img1);
    imageList[1] = (ImageView) findViewById(R.id.img2);
    imageList[2] = (ImageView) findViewById(R.id.img3);
    imageList[3] = (ImageView) findViewById(R.id.img4);
    imageList[4] = (ImageView) findViewById(R.id.img5);
    imageList[5] = (ImageView) findViewById(R.id.img6);
    imageList[6] = (ImageView) findViewById(R.id.img7);
    imageList[7] = (ImageView) findViewById(R.id.img8);
    imageList[8] = (ImageView) findViewById(R.id.img9);
    imgPlayer1 = (ImageView) findViewById(R.id.imgPlayer1);
    imgPlayer2 = (ImageView) findViewById(R.id.imgPlayer2);
    txtCounterPalyerOneWin = (TextView) findViewById(R.id.txtCounterPalyerOneWin);
    txtCounterPalyerTwoWin = (TextView) findViewById(R.id.txtCounterPalyerTwoWin);

    View.OnClickListener imageClickListener = new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        ImageView imageView = (ImageView) view;
        int tagId = (Integer) imageView.getTag();
        int row = (int) Math.floor(tagId / 3);
        int column = tagId % 3;

        if (cells[row][column] != 0)
          return;
        if (isPlayerOne) {
          imageView.setImageResource(R.drawable.o_icon);
          cells[row][column] = 1;
        } else {
          imageView.setImageResource(R.drawable.x_icon);
          cells[row][column] = 2;
        }
        isPlayerOne = !isPlayerOne;
        choosePlayer();
        checkWin();
      }
    };

    for (int i = 0; i < 9; i++) {
      imageList[i].setTag(i);
      imageList[i].setOnClickListener(imageClickListener);
    }

  }

  public void preferencesData() {
    countPlayerOneWin = G.preferences.getInt("WIN_PLAYER_ONE", 0);
    countPlayerTwoWin = G.preferences.getInt("WIN_PLAYER_TWO", 0);
    txtCounterPalyerOneWin.setText("" + countPlayerOneWin);
    txtCounterPalyerTwoWin.setText("" + countPlayerTwoWin);
    int starter = G.preferences.getInt("WINER", 0);
    if (starter == 2) {
      isPlayerOne = false;
    }
    choosePlayer();
  }

  private void choosePlayer() {
    if (isPlayerOne) {
      imgPlayer1.setVisibility(View.VISIBLE);
      imgPlayer2.setVisibility(View.INVISIBLE);
    } else {
      imgPlayer2.setVisibility(View.VISIBLE);
      imgPlayer1.setVisibility(View.INVISIBLE);
    }
  }

  private void checkWin() {
    moveCount++;
    for (int i = 0; i < 3; i++) {
      if ((cells[i][0] != 0) && (cells[i][0] == cells[i][1]) && (cells[i][1] == cells[i][2])) {
        endGame(cells[i][0]);
        return;
      }
      if ((cells[0][i] != 0) && (cells[0][i] == cells[1][i]) && (cells[1][i] == cells[2][i])) {
        endGame(cells[0][i]);
        return;
      }
      if ((cells[0][0] != 0) && (cells[0][0] == cells[1][1]) && (cells[1][1] == cells[2][2])) {
        endGame(cells[0][0]);
        return;
      }
      if ((cells[0][2] != 0) && (cells[0][2] == cells[1][1]) && (cells[1][1] == cells[2][0])) {
        endGame(cells[0][2]);
        return;
      }
    }
    if (moveCount >= 9) {

      endGame(0);
    }
  }


  private void endGame(int winer) {
    if (winer == 1) {
      countPlayerOneWin++;
    } else if (winer == 2) {
      countPlayerTwoWin++;
    }
    txtCounterPalyerOneWin.setText("" + countPlayerOneWin);
    txtCounterPalyerTwoWin.setText("" + countPlayerTwoWin);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putInt("WIN_PLAYER_ONE", countPlayerOneWin);
    editor.putInt("WIN_PLAYER_TWO", countPlayerTwoWin);
    editor.putInt("WINER", winer);
    editor.apply();

    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
    intent.putExtra("RESULT", "" + winer);
    startActivity(intent);
    finish();

  }

}
