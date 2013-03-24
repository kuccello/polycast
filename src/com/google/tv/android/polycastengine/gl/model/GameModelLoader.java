package com.google.tv.android.polycastengine.gl.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.google.tv.android.polycastengine.C;

public class GameModelLoader {

    enum Mode {
        NONE,
        VERTEX,
        ORDER,
        COLOR
    }

    private Context mContext;
    private Mode mCurrentMode = Mode.NONE;  
    
    public GameModelLoader(Context context) {
        mContext = context;
    }
    
    public GameModel loadModel(String name) {
        
        String modelData = doLoad(name);
        String[] modelDataSplit = modelData.split("\n");
        List<Float> vertexData = new ArrayList<Float>();
        List<Short> orderData = new ArrayList<Short>();
        List<Float> colorData = new ArrayList<Float>();
        for(String line:modelDataSplit) {
            if(checkModeSwitch(line)) {
                continue;
            }
            String[] split;
            switch(mCurrentMode) {
                case NONE:
                    continue;
                case VERTEX:
                    split = line.split(",\\s*");
                    for(String vertexPoint:split) {
                        vertexData.add(Float.parseFloat(vertexPoint));
                    }
                    break;
                case ORDER:
                    split = line.split(",\\s*");
                    for(String orderPoint:split) {
//                        int val = Integer.parseInt(orderPoint);
                        Log.d(C.TAG, "ORDER VALUE: ["+orderPoint+"]");
//                        new Short(""+Integer.parseInt(orderPoint));
//                        orderData.add(new Short(orderPoint));
                        orderData.add(new Short(orderPoint.trim()));
                    }
                    break;
                case COLOR:
                    split = line.split(",\\s*");
                    for(String color:split) {
                        colorData.add(Float.parseFloat(color));
                    }
                    break;
                default:
                    // error?
                    if(C.DEBUG){
                        Log.d(C.TAG, "default case on GameModelLoader#loadModel");
                    }
            }
        }
        GameModel model = new GameModel();
        populateModelCoords(vertexData, model);
        populateModelDrawOrder(orderData, model);
        populateModelColorData(colorData, model);
        return model;
    }

    private void populateModelColorData(List<Float> colorData, GameModel model) {
        model.mColors = new float[colorData.size()];
        for(int color = 0;color<colorData.size();color+=1) {
            model.mColors[color] = colorData.get(color);
        }
    }

    private void populateModelDrawOrder(List<Short> orderData, GameModel model) {
        model.mDrawOrder = new short[orderData.size()];
        for(int ord = 0;ord<orderData.size();ord+=1) {
            model.mDrawOrder[ord] = orderData.get(ord);
        }
    }

    private void populateModelCoords(List<Float> vertexData, GameModel model) {
        model.mModelCoords = new float[vertexData.size()];
        for(int coord = 0;coord<vertexData.size();coord+=1) {
            model.mModelCoords[coord] = vertexData.get(coord);
        }
    }
    
    private boolean checkModeSwitch(String line) {
        boolean result = false;
        if(line.startsWith("#")) {
            result = true;
        } else if(line.startsWith("[vertex]")) {
            mCurrentMode = Mode.VERTEX;
            result = true;
        } else if(line.startsWith("[order]")) {
            mCurrentMode = Mode.ORDER;
            result = true;
        } else if(line.startsWith("[color]")) {
            mCurrentMode = Mode.COLOR;
            result = true;
        }
        return result;
    }
    
    private String doLoad(String name) {
        String modelData = "";
        try {
            InputStream is = mContext.getAssets().open(name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            modelData = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return modelData;
    }
    
}
