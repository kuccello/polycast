
package com.google.tv.android.polycastengine.gl.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;
import android.util.Log;

import com.google.tv.android.polycastengine.C;
import com.google.tv.android.polycastengine.gl.renderer.GameRenderable;

public class GameModel implements GameRenderable {

    protected float mModelCoords[];

    protected short mDrawOrder[];

    protected float mColors[];

    // number of coordinates per vertex in the mModelCoords array
    static final int COORDS_PER_VERTEX = 3;

    protected FloatBuffer vertexBuffer;

    protected ShortBuffer drawListBuffer;

    private int mPositionHandle;

    private int mColorHandle;

    /**
     * Returns true if initialization of model succeeded otherwise false
     */
    public boolean initialize() {
        boolean result = false;
        if (mModelCoords != null && mDrawOrder != null) {
            initializeVertexBuffer();
            initializeDrawListBuffer();
            result = true;
        } else {
            Log.e(C.TAG, "Called GameModel#initialize before setting coords and draw order");
        }
        return result;
    }

    private void initializeDrawListBuffer() {
        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
        // (# of coordinate values * 2 bytes per short)
                mDrawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(mDrawOrder);
        drawListBuffer.position(0);
    }

    private void initializeVertexBuffer() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
        // (# of coordinate values * 4 bytes per float)
                mModelCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(mModelCoords);
        vertexBuffer.position(0);
    }

    @Override
    public void draw(int program) {
        Log.d(C.TAG, "GameModel#draw called");
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(program);

        if (mModelCoords.length % COORDS_PER_VERTEX > 0) {
            Log.e(C.TAG,
                    "ERROR: incomplete model vertex set - is the model missing a vertex coordinate?");
        }

        int vertexCount = mModelCoords.length / COORDS_PER_VERTEX;
        int vertexStride = COORDS_PER_VERTEX * vertexCount;

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        // Enable a handle to the model vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        if(C.DEBUG) {
            Log.d(C.TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            Log.d(C.TAG, "position handle: " + mPositionHandle);
            Log.d(C.TAG, "vertex buffer: " + mPositionHandle);
            Log.d(C.TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        // Prepare the model coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false,
                4, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(program, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, mColors, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
