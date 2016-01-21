package com.baki.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

  public static final String EXTRA_ANSWER_IS_TRUE =
      "com.bignerdranch.android.geoquiz.answer_is_true";
  public static final String EXTRA_ANSWER_SHOWN =
      "com.bignerdranch.android.geoquiz.answer_shown";
  private static final String KEY_ANSWER_IS_SHOWN = "answerIsShown";

  private boolean answerIsShown;
  private boolean answerIsTrue;
  private TextView answerTextView;
  private Button showAnswerButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    setAnswerShownResult(false);

    if (savedInstanceState != null) {
      setAnswerShownResult(savedInstanceState.getBoolean(KEY_ANSWER_IS_SHOWN, false));
    }

    answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

    answerTextView = (TextView) findViewById(R.id.answerTextView);
    showAnswerButton = (Button) findViewById(R.id.showAnswerButton);
    showAnswerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (answerIsTrue) {
          answerTextView.setText(R.string.true_button);
        } else {
          answerTextView.setText(R.string.false_button);
        }
        setAnswerShownResult(true);
      }
    });
  }

  private void setAnswerShownResult(boolean isAnswerShown) {
    Intent data = new Intent();
    data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
    setResult(RESULT_OK, data);
    answerIsShown = isAnswerShown;
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(KEY_ANSWER_IS_SHOWN, answerIsShown);
  }
}
