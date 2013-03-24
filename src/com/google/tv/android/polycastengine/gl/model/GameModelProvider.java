package com.google.tv.android.polycastengine.gl.model;

import java.util.List;

import com.google.tv.android.polycastengine.gl.shader.ShaderPackage;

public interface GameModelProvider {
    List<GameModel> getModels(ShaderPackage shaderPackage);
}
