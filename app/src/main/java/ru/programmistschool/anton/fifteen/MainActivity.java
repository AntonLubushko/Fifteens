package ru.programmistschool.anton.fifteen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static Button[][] buttons = new Button[4][4];
    static int[][] field = Fifteens.createFieldByPlaying(10);
    static int rawZero = 0;
    static int colZero = 0;
    static TextView textStepsCounter;
    static int stepCounter = 0;
    static SeekBar seekBar;
    static TextView dificulty;

    static {
        searchZero:
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (field[i][j] == 16) {
                    rawZero = i;
                    colZero = j;
                    break searchZero;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textStepsCounter = (TextView) findViewById(R.id.textView2);
        textStepsCounter.setText(String.valueOf(stepCounter));
        buttons[0][0] = (Button) findViewById(R.id.button1);
        buttons[0][1] = (Button) findViewById(R.id.button2);
        buttons[0][2] = (Button) findViewById(R.id.button3);
        buttons[0][3] = (Button) findViewById(R.id.button4);
        buttons[1][0] = (Button) findViewById(R.id.button5);
        buttons[1][1] = (Button) findViewById(R.id.button6);
        buttons[1][2] = (Button) findViewById(R.id.button7);
        buttons[1][3] = (Button) findViewById(R.id.button8);
        buttons[2][0] = (Button) findViewById(R.id.button9);
        buttons[2][1] = (Button) findViewById(R.id.button10);
        buttons[2][2] = (Button) findViewById(R.id.button11);
        buttons[2][3] = (Button) findViewById(R.id.button12);
        buttons[3][0] = (Button) findViewById(R.id.button13);
        buttons[3][1] = (Button) findViewById(R.id.button14);
        buttons[3][2] = (Button) findViewById(R.id.button15);
        buttons[3][3] = (Button) findViewById(R.id.button16);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rawPressed = 0;
                int colPressed = 0;
                searchPressed:
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (v == buttons[i][j]) {
                            rawPressed = i;
                            colPressed = j;
                            break searchPressed;
                        }
                    }
                }
                boolean rawEquals = (rawPressed == rawZero);
                boolean colEquals = (colPressed == colZero);


                if (rawEquals) {
                    buttons[rawPressed][colPressed].setVisibility(View.INVISIBLE);
                    moveRaw(colZero - colPressed);
                    colZero = colPressed;
                    repaint();
                    textStepsCounter.setText(String.valueOf(++stepCounter));

                    if (Fifteens.isCorrectField(field)) {
                        Toast toast = Toast.makeText(MainActivity.this, "CONGRATULATIONS", Toast.LENGTH_LONG);
                        toast.show();
                    }

                }
                if (colEquals) {
                    buttons[rawPressed][colPressed].setVisibility(View.INVISIBLE);
                    moveCol(rawZero - rawPressed);
                    rawZero = rawPressed;
                    repaint();
                    textStepsCounter.setText(String.valueOf(++stepCounter));
                    if (Fifteens.isCorrectField(field)) {
                        Toast toast = Toast.makeText(MainActivity.this, "CONGRATULATIONS", Toast.LENGTH_LONG);
                        toast.show();
                    }

                }
            }
        };


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j].setOnClickListener(onClickListener);
                if (field[i][j] == 16) {
                    buttons[i][j].setVisibility(View.INVISIBLE);
                } else {
                    buttons[i][j].setVisibility(View.VISIBLE);
                }
                buttons[i][j].setText(String.valueOf(field[i][j]));
            }
        }

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        dificulty = (TextView) findViewById(R.id.dificulty);
        dificulty.setText(String.valueOf(seekBar.getProgress()));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 10) {
                    progress = 10;
                    seekBar.setProgress(progress);
                }
                dificulty.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void clickStart(View view) {
        int progress = seekBar.getProgress();
        field = Fifteens.createFieldByPlaying(progress);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (field[i][j] == 16) {
                    buttons[i][j].setVisibility(View.INVISIBLE);
                    rawZero = i;
                    colZero = j;
                } else {
                    buttons[i][j].setVisibility(View.VISIBLE);
                }
                buttons[i][j].setText(String.valueOf(field[i][j]));
            }
        }
        stepCounter = 0;
        textStepsCounter.setText(String.valueOf(stepCounter));
    }

    private void moveRaw(int difference) {
        if (difference > 0) {
            for (int i = colZero; i > colZero - difference; i--) {
                field[rawZero][i] = field[rawZero][i - 1];
            }
            field[rawZero][colZero - difference] = 16;
        }
        if (difference < 0) {
            for (int i = colZero; i < colZero - difference; i++) {
                field[rawZero][i] = field[rawZero][i + 1];
            }
            field[rawZero][colZero - difference] = 16;
        }
    }

    private void moveCol(int difference) {
        if (difference > 0) {
            for (int i = rawZero; i > rawZero - difference; i--) {
                field[i][colZero] = field[i - 1][colZero];
            }
            field[rawZero - difference][colZero] = 16;
        }
        if (difference < 0) {
            for (int i = rawZero; i < rawZero - difference; i++) {
                field[i][colZero] = field[i + 1][colZero];
            }
            field[rawZero - difference][colZero] = 16;
        }
    }

    private void repaint() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (field[i][j] == 16) {
                    buttons[i][j].setVisibility(View.INVISIBLE);

                } else {
                    buttons[i][j].setVisibility(View.VISIBLE);
                }
                buttons[i][j].setText(String.valueOf(field[i][j]));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}
