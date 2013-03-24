package com.google.tv.android.polycastengine.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.google.tv.android.polycastengine.gl.renderer.GameSurfaceRenderer;
import com.google.tv.android.polycastengine.sample.SampleGameModelProvider;

public class GameSurfaceView extends GLSurfaceView {

    public GameSurfaceView(Context context) {
        this(context,null);
    }
    
    public GameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        
        // Attach the GameRenderer to the view
        GameSurfaceRenderer gsRenderer = new GameSurfaceRenderer(context, "simple", new SampleGameModelProvider(context));
        setRenderer(gsRenderer);
        
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


}
