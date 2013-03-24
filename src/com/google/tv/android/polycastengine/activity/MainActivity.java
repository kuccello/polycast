
package com.google.tv.android.polycastengine.activity;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.google.tv.android.polycastengine.view.GameSurfaceView;

public class MainActivity extends Activity {
    
    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGLView = new GameSurfaceView(this);
        setContentView(mGLView);
    }

}
