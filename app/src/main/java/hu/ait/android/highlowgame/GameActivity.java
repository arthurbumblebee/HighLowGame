package hu.ait.android.highlowgame;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    public static final String KEY_RANDOM = "KEY_RANDOM";
    public static final String KEY_LAST_MESSAGE = "KEY_LAST_MESSAGE";
    public static final String KEY_NUMBER = "KEY_NUMBER";
    public static final String KEY_GUESSES = "KEY_GUESSES";
    private int numberOfGuesses = 0;
    private int generated = 0;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final EditText etGuess = (EditText) findViewById(R.id.etGuess);
        Button btnGuess = (Button) findViewById(R.id.btnGuess);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    //                if(etGuess.getText().toString().equals(""))

                    if (!TextUtils.isEmpty(etGuess.getText())) {
                        int guess = Integer.parseInt(etGuess.getText().toString());

                        if (guess < generated) {
                            tvStatus.setText("Your number is lesser");
                            numberOfGuesses++;
                        } else if (guess > generated) {
                            tvStatus.setText("Your number is higher");
                            numberOfGuesses++;
                        } else {
                            numberOfGuesses++;
                            tvStatus.setText("You have won! YEEEE");

                            Intent intentResult = new Intent();
                            intentResult.setClass(GameActivity.this,ResultActivity.class);

                            intentResult.putExtra(KEY_NUMBER, generated);
                            intentResult.putExtra(KEY_GUESSES, numberOfGuesses);

                            startActivity(intentResult);

                        }
                    } else {
                        etGuess.setError("This field cannot be empty");
                    }
                } catch (NumberFormatException nf) {
                    etGuess.setError("WrongContent");
                    nf.printStackTrace();
                }
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_RANDOM)) {
            generated = savedInstanceState.getInt(KEY_RANDOM, 0);
            tvStatus.setText(savedInstanceState.getString(KEY_LAST_MESSAGE, ""));

        } else {
            generateNewNumber();
        }

    }

    private void generateNewNumber() {
        generated = new Random(System.currentTimeMillis()).nextInt(4);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putInt(KEY_RANDOM, generated);
        outState.putString(KEY_LAST_MESSAGE, tvStatus.getText().toString());
    }
}
