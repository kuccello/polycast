package com.google.tv.android.polycastengine.sample;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.tv.android.polycastengine.gl.model.GameModel;
import com.google.tv.android.polycastengine.gl.model.GameModelLoader;
import com.google.tv.android.polycastengine.gl.model.GameModelProvider;
import com.google.tv.android.polycastengine.gl.shader.ShaderPackage;

public class SampleGameModelProvider implements GameModelProvider {

    private Context mContext;
    private List<GameModel> mModels;
    private GameModelLoader mLoader;
    
    public SampleGameModelProvider(Context context) {
        mModels = new ArrayList<GameModel>();
        mContext = context;
        mLoader = new GameModelLoader(context);
    }

    @Override
    public List<GameModel> getModels(ShaderPackage shaderPackage) {
        if (mModels.size()==0) {
            // TODO load / create models
            GameModel loadModel = mLoader.loadModel("models/sample/square.model");
            loadModel.initialize();
            mModels.add(loadModel);
            
        }
        
        return mModels;
    }

}
