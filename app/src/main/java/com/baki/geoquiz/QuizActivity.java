package com.baki.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

  private static final String TAG = "QuizActivity";
  private static final String KEY_INDEX = "index";
  private static final String KEY_IS_CHEATER = "isCheaterArray";

  private Button trueButton;
  private Button falseButton;
  private ImageButton nextImageButton;
  private ImageButton prevImageButton;
  private TextView questionTextView;
  private Button cheatButton;

  private TrueFalse[] questions = new TrueFalse[]{
      new TrueFalse(R.string.question_americas, true),
      new TrueFalse(R.string.question_asia, true),
      new TrueFalse(R.string.question_mideast, false),
      new TrueFalse(R.string.question_oceans, true)
  };
  private int currentIndex;
  private boolean[] isCheaterArray;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null) {
      currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
      isCheaterArray = savedInstanceState.getBooleanArray(KEY_IS_CHEATER);
    }else {
      isCheaterArray = new boolean[questions.length];
    }
    Log.d(TAG, "pressed onCreate");
    setContentView(R.layout.activity_quiz);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setSubtitle("Bodies of Water");
    setSupportActionBar(toolbar);

    trueButton = (Button) findViewById(R.id.true_button);
    falseButton = (Button) findViewById(R.id.false_button);
    nextImageButton = (ImageButton) findViewById(R.id.next_button);
    prevImageButton = (ImageButton) findViewById(R.id.prev_button);
    questionTextView = (TextView) findViewById(R.id.question_text_view);
    cheatButton = (Button) findViewById(R.id.cheat_button);

    updateQuestion();

    setListeners();
  }

  private void setListeners() {
    trueButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkAnswer(true);
      }
    });
    falseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkAnswer(false);
      }
    });
    questionTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        incrementQuestion();
      }
    });
    nextImageButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        incrementQuestion();
      }
    });
    prevImageButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        decrementQuestion();
      }
    });
    cheatButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startCheatActivity();
      }
    });
  }

  private void startCheatActivity() {
    Intent intent = new Intent(this, CheatActivity.class);
    boolean answerIsTrue = questions[currentIndex].isTrueQuestion();
    intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
    startActivityForResult(intent, 0);
  }

  private void incrementQuestion() {
    currentIndex = (currentIndex + 1) % questions.length;
    updateQuestion();
  }

  private void decrementQuestion() {
    currentIndex = (currentIndex - 1) < 0 ? questions.length - 1 : currentIndex - 1;
    updateQuestion();
  }

  private void checkAnswer(boolean pressedTrue) {
    boolean answerIsTrue = questions[currentIndex].isTrueQuestion();
    int messageResId = 0;
    if (isCheaterArray[currentIndex]) {
      messageResId = R.string.judgment_toast;
    } else {
      messageResId = (pressedTrue == answerIsTrue) ?
          R.string.correct_toast : R.string.incorrect_toast;
    }
    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
  }

  private void updateQuestion() {
    questionTextView.setText(questions[currentIndex].getQuestion());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_quiz, menu);
    return true;
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Log.d(TAG, "pressed onSaveInstanceState");
    outState.putInt(KEY_INDEX, currentIndex);
    outState.putBooleanArray(KEY_IS_CHEATER, isCheaterArray);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (data == null) {
      return;
    }
    isCheaterArray[currentIndex] = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onStart() {
    super.onStart();
    Log.d(TAG, "pressed onStart");
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "pressed onResume");
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    Log.d(TAG, "pressed onRestart");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d(TAG, "pressed onPause");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.d(TAG, "pressed onStop");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "pressed onDestroy");
  }
}
