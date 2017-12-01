package com.yb.demo.weights;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by qtfreet on 2016/12/23.
 */

public class GLSurfaceView extends android.opengl.GLSurfaceView {

    public GLSurfaceView(Context context) {
        super(context);
        init();
    }

    public GLSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        GpuRender gpuRender = new GpuRender();
        setRenderer(gpuRender);
    }
}
