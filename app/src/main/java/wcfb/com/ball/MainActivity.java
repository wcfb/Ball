package wcfb.com.ball;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TheBall ball;
    private TextView time;
    private int speed;
    private ImageButton start;
    private ImageButton mus;
    private Timer timer;
    private int second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        mus = findViewById(R.id.mus);
        mus.setOnClickListener(this);
        time = findViewById(R.id.timer);
        start = findViewById(R.id.start);
        start.setOnClickListener(this);
        ball = findViewById(R.id.theBall1);
        speed = ball.speed;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                time.setText("0");
                if (timer != null){
                    timer.cancel();
                    second = 0;
                }
                ball.restartGame();
                startTime();
                break;
            case R.id.mus:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN
                || event.getAction() == MotionEvent.ACTION_MOVE) {
            if (x >= ball.tableWidth / 2) {
                if (ball.racketX + ball.racketWidth < ball.tableWidth) {
                    ball.racketX += 30;
                }
            } else if (x <= ball.tableWidth / 2) {
                if (ball.racketX > 0) {
                    ball.racketX -= 30;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                if (speed <= 30) {
                    ball.speed += 4;
                    speed += 4;
                    break;
                } else {
                    ball.speed = 30;
                    speed = 30;
                    break;
                }
            case 1:
                if (speed > 0) {
                    ball.speed -= 4;
                    speed -= 4;
                } else {
                    ball.speed = 1;
                    speed = 1;
                }
                break;
            case 2:
                ball.restartGame();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, 0, "1");
        menu.add(Menu.NONE, 1, 1, "2");
        menu.add(Menu.NONE, 2, 2, "3");
        return super.onCreateOptionsMenu(menu);
    }

    private void startTime() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(ball.finish){
                }else{
                    handler.sendEmptyMessage(0x123);
                }
            }
        }, 0, 1000);
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if(msg.what == 0x123){
                time.setText(""+second+++"秒");
                if(second == 10){
                    ball.speed += 4;
                }else if(second == 20){
                    ball.speed += 4;
                }else if(second == 30){
                    ball.speed += 6;
                }else if(second == 40){
                    ball.speed += 6;
                }else if(second == 50){
                    ball.speed += 8;
                }else if(second == 60){
                    ball.speed += 8;
                }else if(second == 70){
                    ball.speed += 10;
                }else if(second == 80){
                    ball.speed += 10;
                }else if(second == 90){
                    ball.speed += 12;
                }else if(second == 100){
                    ball.speed += 12;
                }
            }
        };
    };

}
