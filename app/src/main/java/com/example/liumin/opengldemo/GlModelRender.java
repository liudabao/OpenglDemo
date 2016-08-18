package com.example.liumin.opengldemo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by liumin on 2016/8/16.
 */
public class GlModelRender implements GLSurfaceView.Renderer {
    private Model model;
    private Point mCenterPoint;
    private Point eye=new Point(0 ,0 ,-3);
    private Point up=new Point(0, 1, 0);
    private Point center=new Point(0, 0, 0);
    private float mScalef=1f;
    private float mDegree=0;
    float r;
    float[] ambient = {0.9f, 0.9f, 0.9f, 1.0f};
    float[] diffuse = {0.5f, 0.5f, 0.5f, 1.0f};
    float[] specular = {1.0f, 1.0f, 1.0f, 1.0f};
    float[] lightPosition = {0.5f, 0.5f, 0.5f, 0.0f};

    float[] materialAmb = {0.4f, 0.4f, 1.0f, 1.0f,};
    float[] materialDiff = {0.0f, 0.0f, 1.0f, 1.0f,};
    float[] materialSpec = {1.0f, 0.5f, 0.0f, 1.0f,};

    public GlModelRender(Context context){
        try {
            model = new STLReader().parseBinStlInAssets(context, "huba.stl");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rotate(float degree){
        mDegree=degree;
    }

    public void scale(float scale){
        mScalef=scale/r;
        Log.e("TAG 1","mscalef: "+mScalef+" r: "+r);
    }
   
    @Override
    public void onDrawFrame(GL10 gl) {

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        GLU.gluLookAt(gl, eye.x, eye.y, eye.z, center.x,
                center.y, center.z, up.x, up.y, up.z);
        gl.glRotatef(mDegree, 0, 1, 0);
        gl.glScalef(mScalef, mScalef, mScalef);

        gl.glTranslatef(-mCenterPoint.x, -mCenterPoint.y, -mCenterPoint.z);

        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glNormalPointer(GL10.GL_FLOAT, 0, model.getVnormsBuffer());
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model.getVertBuffer());
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, model.getFacetCount()*3);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, ((float) width) / height, 1f, 100f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glClearDepthf(1.0f);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glShadeModel(GL10.GL_SMOOTH);

        openLight(gl);
        enableMaterial(gl);

        r=model.getR();
        mScalef=1f/r;
        mCenterPoint=model.getCentrePoint();

        Log.e("TAG 2","mscalef: "+mScalef+" r: "+r);
    }

    private void openLight(GL10 gl){
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, Util.floatToBuffer(ambient)) ;
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, Util.floatToBuffer(diffuse)) ;
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, Util.floatToBuffer(specular)) ;
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, Util.floatToBuffer(lightPosition)) ;

    }

    private void enableMaterial(GL10 gl){
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, Util.floatToBuffer(materialAmb));
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, Util.floatToBuffer(materialDiff));
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, Util.floatToBuffer(materialSpec));
    }

}
