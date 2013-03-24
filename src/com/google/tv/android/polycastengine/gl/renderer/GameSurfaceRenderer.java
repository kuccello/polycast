
package com.google.tv.android.polycastengine.gl.renderer;

import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import com.google.tv.android.polycastengine.C;
import com.google.tv.android.polycastengine.gl.model.GameModel;
import com.google.tv.android.polycastengine.gl.model.GameModelProvider;
import com.google.tv.android.polycastengine.gl.shader.ShaderPackage;

public class GameSurfaceRenderer implements Renderer {

    private Context mContext;

    private EGLConfig mGLConfig;

    private ShaderPackage mShaderPackage;

    private String mShaderPack;

    private int mWidth;

    private int mHeight;

    private GameModelProvider mGameModelProvider;

    private List<GameModel> mActiveModels;

    public GameSurfaceRenderer(Context context, String shaderPack,
            GameModelProvider gameModelProvider) {
        mContext = context;
        mShaderPack = shaderPack;
        mGameModelProvider = gameModelProvider;
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        Log.d(C.TAG, "onDrawFrame (#models = " + mActiveModels.size() + ")");
        // Redraw background color
        GLES20.glViewport(0, 0, mWidth, mHeight);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        for(GameModel model:mActiveModels) {
            model.draw(mShaderPackage.getPackageProgram());
        }
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        mGLConfig = config;
        Log.d(C.TAG, "onSurfaceCreated");
        // This is necessary to avoid calling GL functions outside the GL
        // thread.
        // See:
        // http://stackoverflow.com/questions/11299643/openg-gl-2-0-android-error-0x501-gl-invalid-value
        if (mShaderPackage == null) {
            mShaderPackage = new ShaderPackage(mContext, mShaderPack);
        }

        // Set the background frame color
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        // IMPORTANT: only create the models within the GL thread
        mActiveModels = mGameModelProvider.getModels(mShaderPackage);
    }

}
