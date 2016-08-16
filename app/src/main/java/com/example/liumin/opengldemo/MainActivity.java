package com.example.liumin.opengldemo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        init();
    }


    private void init(){
        glSurfaceView=new GLSurfaceView(this);
        setContentView(glSurfaceView);
        glSurfaceView.setRenderer(new GLRender());
    }

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
        }
    }
}
