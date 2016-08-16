package com.example.liumin.opengldemo;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by liumin on 2016/8/16.
 */
public class STLReader {
    private StlLoadListener stlLoadListener;

    public Model parseBinStlInSDcard(String path) throws IOException {
        File file=new File(path);
        FileInputStream fis=new FileInputStream(file);
        return parseBinStl(fis);
    }

    public Model parseBinStlInAssets(Context context, String fileName) throws IOException {
        InputStream is=context.getAssets().open(fileName);
        return parseBinStl(is);
    }

    private Model parseBinStl(InputStream in) throws IOException {
        if(stlLoadListener!=null){
            stlLoadListener.onStart();
        }
        Model model= new Model();
        in.skip(80);
        byte[] bytes=new byte[4];
        in.read(bytes);
        int facetCount=Util.byte4ToInt(bytes, 0);
        model.setFacetCount(facetCount);
        if(facetCount==0){
            in.close();
            return model;
        }
        byte[] facetBytes=new byte[50*facetCount];
        in.read(facetBytes);
        in.close();
        parseModel(model, facetBytes);
        if(stlLoadListener!=null){
            stlLoadListener.onFinished();
        }
        return model;
    }

    private void parseModel(Model model, byte[] facetBytes) {
        int facetCount=model.getFacetCount();
        float[] verts=new float[facetCount*3*3];
        float[] vnorms=new float[facetCount*3*3];
        short[] remarks=new short[facetCount];
        int stlOffSet=0;
        try{
            for(int i=0; i< facetCount;i++){
                if(stlLoadListener!=null){
                    stlLoadListener.onLoading(i, facetCount);
                }
                for(int j=0; j<4 ; j++){
                    float x=Util.byte4ToFloat(facetBytes, stlOffSet);
                    float y=Util.byte4ToFloat(facetBytes, stlOffSet + 4);
                    float z=Util.byte4ToFloat(facetBytes, stlOffSet + 8);
                    stlOffSet +=12;
                    if(j==0){
                        vnorms[i*9]= x;
                        vnorms[i*9 + 1]= y;
                        vnorms[i*9 + 2]= z;
                        vnorms[i*9 + 3]= x;
                        vnorms[i*9 + 4]= y;
                        vnorms[i*9 + 5]= z;
                        vnorms[i*9 + 6]= x;
                        vnorms[i*9 + 7]= y;
                        vnorms[i*9 + 8]= z;
                    }
                    else {
                        verts[i*9+(j-1)*3]= x;
                        verts[i*9+(j-1)*3 + 1]= y;
                        verts[i*9+(j-1)*3 + 2]= z;
                        if(i==0 && j==1){
                            model.minX = model.maxX = x;
                            model.minY = model.maxY = y;
                            model.minZ = model.maxZ = z;
                        }
                        else {
                            model.minX = Math.min(model.minX, x);
                            model.minY = Math.min(model.minY, y);
                            model.minZ = Math.min(model.minZ, z);
                            model.maxX = Math.min(model.maxX, x);
                            model.maxY = Math.min(model.maxY, y);
                            model.maxZ = Math.min(model.maxZ, z);
                        }
                    }
                }
                short r=Util.byte2ToShort(facetBytes, stlOffSet);
                stlOffSet = stlOffSet + 2;
                remarks[i]=r;
            }
        }catch (Exception e){

            if(stlLoadListener !=null){
                stlLoadListener.onFailure(e);
            }
            else {
                e.printStackTrace();
            }

        }finally {

        }
        model.setVerts(verts);
        model.setVnorms(vnorms);
        model.setRemarks(remarks);

    }

    public interface StlLoadListener{
        void onStart();
        void onLoading(int cur, int total);
        void onFinished();
        void onFailure(Exception e);
    }
}
