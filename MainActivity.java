package com.example.thomaslysaght.mytesttoolbar;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    ProgressBar timer;
    private int myTime; //the actual time of the progress bar
    private int maxTime; //max time for the progress bar


    ThreadProgress mThreadTimer; //thread for the progress bar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mThreadTimer = new ThreadProgress();
        mThreadTimer.run();
        timer = (ProgressBar) findViewById(R.id.progress_bar);
        timer.setProgress(80);
        myTime=timer.getProgress();
    }

    //My own class for manage the progressbar with a thread
    public class ThreadProgress extends Thread implements Runnable {

        @Override
        public void run() {

            //while the timer is not finished and is not stopped
            while( myTime > 0 && !mThreadTimer.isInterrupted()) {

                try{

                    //substract the time
                    myTime -= 1;//lessTime;

                    //create and send a message to the handler
                    Message message = new Message();
                    message.arg1 = myTime;
                    handler.sendMessage(message);

                    //sleep the thread for a little time
                    ThreadProgress.sleep(100);

                } catch (InterruptedException e){
                    e.printStackTrace();
                    break;
                }
            }

        }

    }

    //handler to manage the progress bar thread
    Handler handler = new Handler() {

        @Override
        public void handleMessage(final Message msg) {
            runOnUiThread(new Runnable() {

                public void run() {
                    Toast.makeText(getBaseContext(),"In handleMessage", Toast.LENGTH_SHORT).show();

                    //set the progress
                    timer.setProgress(msg.arg1);

                    //if the timer finish, call to endGame function
                    if((int) msg.arg1 == 0)
                    {
                        Toast.makeText(getBaseContext(),"In handleMessage", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
