package snowdonout.handlerexample;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private Button button;
    private Button download;
    private Button processing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.world);
        download = (Button) findViewById(R.id.download);
        processing = (Button) findViewById(R.id.processing);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //doing some operation, it may be network operation
                        handler.sendEmptyMessage(0);

                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendMessage(Message.obtain(handler, 1, 1, 1));
                    }
                });
                thread.start();
            }
        });

        processing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //Main Thread is creating this handler ,
    //message queue will be bound to main thread
    //updates are received on main thread
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                button.setText(MainActivity.this.getText(R.string.bye));
                Toast.makeText(MainActivity.this, "Download Started", Toast.LENGTH_SHORT).show();
            } else if (msg.arg1 == 1 && msg.arg2 == 1) {
                button.setText(MainActivity.this.getText(R.string.hello));
                Toast.makeText(MainActivity.this, "Download complete", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    });
}
