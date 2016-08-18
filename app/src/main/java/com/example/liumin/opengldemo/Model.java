package com.example.liumin.opengldemo;


import java.nio.FloatBuffer;

/**
 * Created by liumin on 2016/8/16.
 */
public class Model {
    private int facetCount;
    private float[] verts;
    private float[] vnorms;
    private short[] remarks;

    private FloatBuffer vertBuffer;
    private FloatBuffer vnormsBuffer;

    float maxX;
    float minX;
    float maxY;
    float minY;
    float maxZ;
    float minZ;

    public Point getCentrePoint(){
        float cx= (maxX - minX)/ 2;
        float cy= (maxY - minY)/ 2;
        float cz= (maxZ - minZ)/ 2;
        return new Point(cx, cy, cz);
    }

    public float getR(){
        float dx= (maxX - minX);
        float dy= (maxY - minY);
        float dz= (maxZ - minZ);
        float max=dx;
        if(dy > max){
            max=dy;
        }
        if(dz > max){
            max=dz;
        }
        return max;
    }

    public int getFacetCount() {
        return facetCount;
    }

    public void setFacetCount(int facetCount) {
        this.facetCount = facetCount;
    }

    public float[] getVerts() {
        return verts;
    }

    public void setVerts(float[] verts){
        this.verts=verts;
        vertBuffer=Util.floatToBuffer(verts);
    }

    public float[] getVnorms() {
        return vnorms;
    }

    public void setVnorms(float[] vnorms){
        this.vnorms=vnorms;
        vnormsBuffer=Util.floatToBuffer(vnorms);
    }

    public short[] getRemarks() {
        return remarks;
    }

    public void setRemarks(short[] remarks) {
        this.remarks = remarks;
    }

    public FloatBuffer getVnormsBuffer() {
        return vnormsBuffer;
    }

    public FloatBuffer getVertBuffer() {
        return vertBuffer;
    }

}
