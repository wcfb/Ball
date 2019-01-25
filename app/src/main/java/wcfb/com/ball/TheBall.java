package wcfb.com.ball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Create by wcfb on 2018/12/24
 */
public class TheBall extends View{

    //Attributes of ball
    private int ballX;
    private int ballY;
    private double degree;
    public int speed;
    private int ballSize;
    private WindowManager windowManager;

    //Attributes of table
    public int tableWidth;
    private int tableHeight;
    private int racketFromBottom;

    public int racketX;
    private int racketY;
    public int racketWidth;
    private int racketHeight;

    private int wordsWidth1;
    private int wordsWidth2;
    public boolean finish;
    private boolean pause;
    private Timer time;
    private Handler handler;
    private boolean openMus;
    private boolean which;
    private String over;
    private String over1;
    private String over2;
    private int width;

    private Paint paint;

    public TheBall(Context context) {
        this(context,null);
    }

    public TheBall(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context){
        paint = new Paint();
        finish = false;
        speed = 10;
        racketX = 0;
        openMus = true;
        which = false;
        ballX = 200;
        ballY = 200;
        over1 = "Game over";
        over2 = "Game over";
        wordsWidth1 = (int)paint.measureText(over1);
        wordsWidth2 = (int)paint.measureText(over1);

        this.windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        this.degree = Math.random() * 2 * Math.PI;
        tableWidth = display.getWidth();
        tableHeight = display.getHeight();
        ballSize = tableHeight / 50;
        racketWidth = tableHeight/11;
        racketHeight = tableHeight/90;
        racketFromBottom = tableHeight/26;
        racketY = tableHeight - racketFromBottom - tableHeight / 10;
        receive();
    }

    public void receive(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123)
                    TheBall.this.invalidate();
            }
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#47C8ED"));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        if (finish){
            if (which){
                over = over1;
                which = false;
                width = wordsWidth1;
            }
            else {
                over = over2;
                width = wordsWidth2;
                which = true;
            }
            paint.setColor(Color.RED);
            paint.setTextSize((int)tableHeight / 26);
            canvas.drawText(over, (tableWidth - width)/2, tableHeight, paint);
        }
        else {
            paint.setColor(Color.rgb(120,120,0));
            canvas.drawCircle(ballX, ballY, ballSize, paint);
            paint.setColor(Color.rgb(80,80,200));
            canvas.drawRect(racketX, racketY, racketX + racketWidth, racketY
                    + racketHeight, paint);
        }
    }
    private void moveTrack(){
        if (ballX < 0 || ballY > tableWidth - ballSize){
            degree = Math.PI - degree;
        }
        if (ballY < 0 || ballY > tableHeight - ballSize){
            degree = -degree;
        }
        if (ballX + ballSize < racketX
                || ballX - ballSize > racketX + racketWidth){
            if (ballY + ballSize > racketY) {
                finish = true;
                time.cancel();
            }
        }
        else if(ballY + ballSize >= racketY) {
            degree = -degree;
        }
        ballX += speed * Math.cos(degree);
        ballY += speed * Math.sin(degree);
    }
    public void pause(){
        Toast.makeText(getContext(),"暂停",Toast.LENGTH_SHORT).show();
        pause = false;
        finish = false;
        this.degree = Math.random() * 2 * Math.PI;
        time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                moveTrack();
                handler.sendEmptyMessage(0);
            }
        }, 0, 20);
    }
    public void restartGame(){
        finish = false;
        ballX = 0;
        ballY = 0;
        speed = 10;
        this.degree = Math.random() * 2 * Math.PI;
        if(time != null)
            time.cancel();
        time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                moveTrack();
                handler.sendEmptyMessage(0x123);
            }
        }, 0, 20);
    }
}
