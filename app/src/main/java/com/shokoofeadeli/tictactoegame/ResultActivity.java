package com.shokoofeadeli.tictactoegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
  public TextView txtResult;
  public Button btnExit;
  public Button btnPlay;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_result);

    txtResult = (TextView) findViewById(R.id.txtResult);
    btnExit = (Button) findViewById(R.id.btnExit);
    btnPlay = (Button) findViewById(R.id.btnPlay);

    Intent intent = getIntent();
    String result = intent.getStringExtra("RESULT");

    switch (result) {
      case "1":
        txtResult.setText("Winer is Player One");
        break;
      case "2":
        txtResult.setText("Winer is Player Two");
        break;
      case "0":
        txtResult.setText("No Winer!!!");
        break;
    }

    btnExit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

    btnPlay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
      }
    });
  }
}
