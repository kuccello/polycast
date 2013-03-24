package com.google.tv.android.polycastengine.gl.shader;

import com.google.tv.android.polycastengine.C;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

public class ShaderPackage {

    public static final int NOT_INITIALIZED = -1;
    
    private Context mContext;
    private ShaderLoader mShaderLoader;
    private ShaderPackage mShaderPackage;
    private String mShaderPack;
    private int mVertexShader;
    private int mFragmentShader;
    private int mProgam = NOT_INITIALIZED;
    
    public ShaderPackage(Context context, String shaderPack) {
        mContext = context;
        mShaderLoader = new ShaderLoader(mContext);
        mShaderPack = shaderPack;
        mShaderPackage = null;
        loadShaderPack();
    }
    
    public ShaderPackage(Context context, String shaderPack, ShaderPackage shaderPackage) {
        mContext = context;
        mShaderLoader = new ShaderLoader(mContext);
        mShaderPack = shaderPack;
        mShaderPackage = shaderPackage;
        loadShaderPack();
    }
    
    private void loadShaderPack() {
        mVertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        if(mVertexShader == 0) {
            Log.e(C.TAG, "Failed to create Vertex Shader");
        }
        GLES20.glShaderSource(mVertexShader, mShaderLoader.getVertexShader(mShaderPack));
        GLES20.glCompileShader(mVertexShader);
        monitorShaderCompile(mVertexShader, "Vertex Shader");
        
        mFragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        if(mFragmentShader == 0) {
            Log.e(C.TAG, "Failed to create Fragment Shader");
        }
        GLES20.glShaderSource(mFragmentShader, mShaderLoader.getFragmentShader(mShaderPack));
        GLES20.glCompileShader(mFragmentShader);
        monitorShaderCompile(mFragmentShader, "Fragment Shader");
    }

    private void monitorShaderCompile(int iShader, String ...name) {
        String nameOf = "";
        if(name.length>0) {
            nameOf = name[0];
        }
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(iShader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.e(C.TAG, "Compliation for "+nameOf+" shader failed\n" + GLES20.glGetShaderInfoLog(iShader));
        }
    }
    
    public int getVertexShader() {
        return mVertexShader;
    }
    
    public int getFragmentShader() {
        return mFragmentShader;
    }
    
    public int getPackageProgram() {
        if(mProgam == NOT_INITIALIZED) {
            if(mShaderPackage == null) {
                mProgam = GLES20.glCreateProgram();
            } else {
                mProgam = mShaderPackage.getPackageProgram();
            }
            if(mProgam == 0) {
                Log.e(C.TAG, "Failed to create GL Program");
            }
            GLES20.glAttachShader(mProgam, mVertexShader);
            GLES20.glAttachShader(mProgam, mFragmentShader);
            GLES20.glLinkProgram(mProgam);
        }
        return mProgam;
    }
}
