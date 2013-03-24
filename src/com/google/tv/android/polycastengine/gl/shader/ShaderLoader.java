package com.google.tv.android.polycastengine.gl.shader;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

public class ShaderLoader {

    private Context mContext;
    
    public ShaderLoader(Context context) {
        mContext = context;
    }
    
    public String getVertexShader(String name) {
        return doLoad("shaders/"+name+"/" + name + ".vtx");
    }
    
    public String getFragmentShader(String name) {
        return doLoad("shaders/"+name+"/" + name + ".frag");
    }

    private String doLoad(String name) {
        String shader = "";
        try {
            InputStream is = mContext.getAssets().open(name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            shader = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return shader;
    }
    
}
