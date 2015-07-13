package com.algorithmi.maker.breakfast.free.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @author muneebahmad
 * @splash activity default launcher: 
 * @see config changes
 * */

public class SplashActivity extends Activity implements OnClickListener {
	
	private ImageView logo;
        private ImageView loading;
        private Button skip;
        private Animation logoAnim;
        private Animation skipAnim;
        private Animation mLoading;
        private Toast toast;
	
	/**
     *
     * @param savedInstanceState
     */
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		this.logo = (ImageView) findViewById(R.id.logo_algo);
                this.skip = (Button) findViewById(R.id.skip);
                this.loading = (ImageView) findViewById(R.id.loading);
                this.skip.setOnClickListener(this);
                
                this.logoAnim = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
                this.skipAnim = AnimationUtils.loadAnimation(this, R.anim.right);
                this.mLoading = AnimationUtils.loadAnimation(this, R.anim.sett_anim);
                this.logo.setAnimation(logoAnim);
                this.skip.setAnimation(skipAnim);
                this.skip.setVisibility(View.INVISIBLE);
                this.loading.setAnimation(mLoading);
                
                
		LayoutInflater mInflater = getLayoutInflater();
		View layout = mInflater.inflate(R.layout.toast2,
				(ViewGroup) findViewById(R.id.custom_toast2));
		TextView text = (TextView) layout
				.findViewById(R.id.toast_text2);
		text.setText("Breakfast Maker pro is available on Playstore.");
		toast = new Toast(getApplicationContext());
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.setView(layout);
		toast.show();
		
		thread.start();

	}/* end onCreate(Bundle) */
    
    Thread thread = new Thread() {
    	@Override
    	public void run() {
    		try {
    			sleep(5000);
    			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    			startActivity(intent);
    		} catch (InterruptedException e) {
    			
    		} finally {
    			finish();
    		}
    	}
    }; /* thread delay*/
	
	/**
     *
     * @param mVIew
     */
    @Override
	public void onClick(View mVIew) {
		if (mVIew == this.skip) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			
			SplashActivity.this.finish();
		}
	}
        
        /**
     *
     */
        @Override
        public void onBackPressed() {
           toast = Toast.makeText(this, "Please Press SKIP when it loads!", Toast.LENGTH_LONG);
           toast.setGravity(Gravity.CENTER_VERTICAL, 50, 0);
           toast.show();
        }
	
}/* end class */
