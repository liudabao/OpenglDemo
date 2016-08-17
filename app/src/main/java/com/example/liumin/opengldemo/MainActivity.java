package com.example.liumin.opengldemo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    private GLSurfaceView glSurfaceView;
    private float rotateDegreen=0;
    private GlModelRender glModelRender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        init();
    }


    private void init(){
        glSurfaceView=new GLSurfaceView(this);
        glModelRender=new GlModelRender(this);
        glSurfaceView.setRenderer(glModelRender);
        setContentView(glSurfaceView);
        //glSurfaceView.setRenderer(new GLRender());
    }

    public void rotate(float degree){
        glModelRender.rotate(degree);
        glSurfaceView.invalidate();
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
        if(glSurfaceView!=null){
            glSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(glSurfaceView!=null){
            glSurfaceView.onResume();
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
