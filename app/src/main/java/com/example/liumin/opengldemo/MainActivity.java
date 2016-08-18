package com.example.liumin.opengldemo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;


public class MainActivity extends Activity {

   // private GLSurfaceView glSurfaceView;
    private float rotateDegreen=0;
    private GlModelRender glModelRender;
    private GLView glView;
    private SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init(){
        //glSurfaceView=new GLSurfaceView(this);
        glView=(GLView)findViewById(R.id.glView);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        glModelRender=new GlModelRender(this);
        glView.setRenderer(glModelRender);
        seekBar.setMax(100);
        seekBar.setProgress(80);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("TAG",1f*progress/100+"");
                glModelRender.scale(1f*progress/100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //glSurfaceView.setRenderer(new GLRender());
    }

    public void rotate(float degree){
        glModelRender.rotate(degree);
        glView.invalidate();
    }

    private Handler handler=new Handler(){
      @Override
      public void handleMessage(Message msg){
          rotate(rotateDegreen);
      }
    };

    @Override
    protected void onPause(){
        super.onPause();
        if(glView!=null){
            glView.onPause();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        glModelRender.scale(0.8f);
        if(glView!=null){
            glView.onResume();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            Thread.sleep(100);
                            rotateDegreen +=5;
                            handler.sendEmptyMessage(0x001);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}
