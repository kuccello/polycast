
package com.google.tv.android.polycastengine.sample;

import com.google.tv.android.polycastengine.gl.model.GameModel;
import com.google.tv.android.polycastengine.gl.model.GameModelLoader;
import com.google.tv.android.polycastengine.gl.renderer.GameRenderable;

public class Triangle implements GameRenderable {

    private GameModel mGameModel;
    
    public Triangle(GameModelLoader modelLoader) {
        mGameModel = modelLoader.loadModel("models/sample/triangle.model");
    }

    @Override
    public void draw(int program) {
        mGameModel.draw(program);
    }

}
