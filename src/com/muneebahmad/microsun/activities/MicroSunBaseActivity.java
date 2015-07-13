/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muneebahmad.microsun.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;
import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.nodes.Director.IDirectorLifecycleListener;
import com.wiyun.engine.opengl.WYGLSurfaceView;

/**
 *
 * @author muneebahmad
 */
public abstract class MicroSunBaseActivity extends Activity
            implements IDirectorLifecycleListener {
    
    /**
     *
     */
    protected WYGLSurfaceView mGLSurefaceView;
    private PowerManager.WakeLock wakeLock;
    
    static {
        //System.loadLibrary("wiskia");
        //System.loadLibrary("xml2");
        System.loadLibrary("wiengine");
        System.loadLibrary("wiengine_binding");
        System.loadLibrary("lua");
        //System.loadLibrary("chipmunk");
        System.loadLibrary("box2d");
        System.loadLibrary("wisound");
        System.loadLibrary("winetwork");
        //System.loadLibrary("sqlite");
    }
    
    /**
     * {@code com.muneebahmad.microsun.activities.MicroSunBaseActivity.java}
     * @return boolean
     */
    public boolean isTransparent() {
		return false;
	}
    
    /**
     *
     * @return String
     */
    protected String checkPrecondition() {
        return null;
    }
    
    /* Called when the Activity first is created */
    /**
     *
     * @param mBundle
     */
    @Override
    protected void onCreate(Bundle mBundle) {
        super.onCreate(mBundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.mGLSurefaceView = new WYGLSurfaceView(this, isTransparent());
        setContentView(mGLSurefaceView);
        Director.getInstance().addLifecycleListener(this);
    }
    
    /**
     *
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
        this.wakeLock.release();
    }
    
    /**
     *
     */
    @Override
    protected void onResume() {
	super.onResume();
	this.wakeLock = ((PowerManager) getSystemService("power")).newWakeLock(
				PowerManager.FULL_WAKE_LOCK, "MainActivity");
	this.wakeLock.acquire();
	Director.getInstance().resume();
    }
    
    /**
     *
     */
    @Override
    public void onSurfaceCreated() {
        
    }
    
    /**
     *
     * @param scale
     * @param width
     * @param height
     */
    protected void setBaseSize(int scale, int width, int height) {
	Director.getInstance().setScaleMode(scale);
	Director.getInstance().setBaseSize(width, height);
    }
}/* end class */
