package com.algorithmi.maker.breakfast.free.main;

/**
 * 
 * microSun Systems Inc.
 * algorithmi apps Inc. (c)2014.
 * http://www.sun.net/algorithmi
 */

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.algorithmi.maker.breakfast.scenes.MainMenuScene;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.ChartboostDelegate;
import com.muneebahmad.microsun.activities.MicroSunBaseActivity;
import com.vungle.sdk.VunglePub;
import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.opengl.WYGLSurfaceView;

/**
 * 
 * @author muneebahmad
 */

public class MainActivity extends MicroSunBaseActivity {

	private boolean mStarted;
	private Chartboost cb;
	private final String _APP_ID = "52cf708d9ddc356fa1af5ebd";
	private final String _APP_SIGNATURE = "04429e0674a13ce9db2f7024d0e9a4338fe7a810";
	final String app_id = "com.algorithmi.maker.breakfast.free.main";

	/* called when activity is first created */
	/**
	 * 
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mStarted = false;
		setBaseSize(1, 480, 800);
		this.mGLSurefaceView
				.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		Director.getInstance().setDisplayFPS(false);

		/**
		 * chartboost
		 * */
		this.cb = Chartboost.sharedChartboost();
		this.cb.onCreate(this, _APP_ID, _APP_SIGNATURE, chartBoostDelegate);
		this.cb.startSession();
		//this.cb.cacheInterstitial();
		
		/**
		 * Vungle init
		 * 
		 */

		VunglePub.init(this, app_id);
		VunglePub.setSoundEnabled(true);
		VunglePub.setEventListener(new VunglePub.EventListener() {

			public void onVungleView(double timeWatched, double adLength) {
				/**
				 * The user has exited the ad, and at least SOME portion of the
				 * ad was viewed. Both 'timeWatched' and 'adLength' are in
				 * seconds
				 */
				double percent_scene = timeWatched / adLength;
				if (percent_scene >= 0.0) {
					Log.i("Vungle-Advert",
							"Vungle would consider this a 'Completed View'");
				}
			}

			public void onVungleAdStart() {
				/**
				 * the user has just started watching an ad
				 */
				Log.i("Vungle-Advert", "Just started and advertisement...");
			}

			public void onVungleAdEnd() {
				/**
				 * The user has completely exited an advert
				 */
				Log.i("Vungle-Advert", "No longer seeing an advertisement...");
			}
		});

	}/* end onCreate(Bundle) */
	

	/**
     *
     */
	@Override
	public void onPause() {
		super.onPause();
		Director.getInstance().pause();
		VunglePub.onPause();
	}

	/**
     *
     */
	@Override
	public void onDestroy() {
		Director.getInstance().end();
		super.onDestroy();
		this.cb.onDestroy(this);
	}

	/**
     *
     */
	@Override
	public void onStart() {
		super.onStart();
		this.cb.onStart(this);
	}

	/**
     *
     */
	@Override
	public void onStop() {
		super.onStop();
		this.cb.onStop(this);
	}

	/**
     *
     */
	@Override
	public void onBackPressed() {
		if (this.cb.onBackPressed()) {
			return;
		} else {
			super.onBackPressed();
		}
	}

	/**
     *
     */
	@Override
	public void onDirectorEnded() {
		// TODO Auto-generated method stub

	}

	/**
     *
     */
	@Override
	public void onDirectorPaused() {
		// TODO Auto-generated method stub

	}

	/**
     *
     */
	@Override
	public void onDirectorResumed() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @param path
	 */
	@Override
	public void onDirectorScreenCaptured(String path) {

	}

	/**
	 * 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void onSurfaceChanged(int arg0, int arg1) {
		// TODO Auto-generated method stub
		if (!(this.mStarted)) {
			this.mStarted = true;
			if (this.mGLSurefaceView == null) {
				setContentView(new WYGLSurfaceView(this, isTransparent()));
			}
			this.cb.showInterstitial();
			Director.getInstance().runWithScene(new MainMenuScene());
		}
	}

	/**
     *
     */
	@Override
	public void onSurfaceCreated() {
		// TODO Auto-generated method stub
		super.onSurfaceCreated();
	}

	/**
     *
     */
	@Override
	public void onSurfaceDestroyed() {
		// TODO Auto-generated method stub

	}

	private ChartboostDelegate chartBoostDelegate = new ChartboostDelegate() {

		/*
		 * Chartboost delegate methods
		 * 
		 * Implement the delegate methods below to finely control Chartboost's
		 * behavior in your app
		 * 
		 * Minimum recommended: shouldDisplayInterstitial()
		 */

		/*
		 * shouldDisplayInterstitial(String location)
		 * 
		 * This is used to control when an interstitial should or should not be
		 * displayed If you should not display an interstitial, return false
		 * 
		 * For example: during gameplay, return false.
		 * 
		 * Is fired on: - showInterstitial() - Interstitial is loaded & ready to
		 * display
		 */
		@Override
		public boolean shouldDisplayInterstitial(String location) {
			Log.i("", "SHOULD DISPLAY INTERSTITIAL '" + location + "'?");
			return true;
		}

		/*
		 * shouldRequestInterstitial(String location)
		 * 
		 * This is used to control when an interstitial should or should not be
		 * requested If you should not request an interstitial from the server,
		 * return false
		 * 
		 * For example: user should not see interstitials for some reason,
		 * return false.
		 * 
		 * Is fired on: - cacheInterstitial() - showInterstitial() if no
		 * interstitial is cached
		 * 
		 * Notes: - We do not recommend excluding purchasers with this delegate
		 * method - Instead, use an exclusion list on your campaign so you can
		 * control it on the fly
		 */
		@Override
		public boolean shouldRequestInterstitial(String location) {
			Log.i("", "SHOULD REQUEST INSTERSTITIAL '" + location + "'?");
			return true;
		}

		/*
		 * didCacheInterstitial(String location)
		 * 
		 * Passes in the location name that has successfully been cached
		 * 
		 * Is fired on: - cacheInterstitial() success - All assets are loaded
		 * 
		 * Notes: - Similar to this is: cb.hasCachedInterstitial(String
		 * location) Which will return true if a cached interstitial exists for
		 * that location
		 */
		@Override
		public void didCacheInterstitial(String location) {
			Log.i("", "INTERSTITIAL '" + location + "' CACHED");
		}

		/*
		 * didFailToLoadInterstitial(String location)
		 * 
		 * This is called when an interstitial has failed to load for any reason
		 * 
		 * Is fired on: - cacheInterstitial() failure - showInterstitial()
		 * failure if no interstitial was cached
		 * 
		 * Possible reasons: - No network connection - No publishing campaign
		 * matches for this user (go make a new one in the dashboard)
		 */
		@Override
		public void didFailToLoadInterstitial(String location) {
			// Show a house ad or do something else when a chartboost
			// interstitial fails to load

			Log.i("", "INTERSTITIAL '" + location + "' REQUEST FAILED");
			Toast.makeText(MainActivity.this,
					"Interstitial '" + location + "' Load Failed",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didDismissInterstitial(String location)
		 * 
		 * This is called when an interstitial is dismissed
		 * 
		 * Is fired on: - Interstitial click - Interstitial close
		 * 
		 * #Pro Tip: Use the delegate method below to immediately re-cache
		 * interstitials
		 */
		@Override
		public void didDismissInterstitial(String location) {

			// Immediately re-caches an interstitial
			cb.cacheInterstitial(location);

			Log.i("", "INTERSTITIAL '" + location + "' DISMISSED");
			Toast.makeText(MainActivity.this,
					"Dismissed Interstitial '" + location + "'",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didCloseInterstitial(String location)
		 * 
		 * This is called when an interstitial is closed
		 * 
		 * Is fired on: - Interstitial close
		 */
		@Override
		public void didCloseInterstitial(String location) {
			Log.i("", "INSTERSTITIAL '" + location + "' CLOSED");
			Toast.makeText(MainActivity.this,
					"Closed Interstitial '" + location + "'",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didClickInterstitial(String location)
		 * 
		 * This is called when an interstitial is clicked
		 * 
		 * Is fired on: - Interstitial click
		 */
		@Override
		public void didClickInterstitial(String location) {
			Log.i("", "DID CLICK INTERSTITIAL '" + location + "'");
			Toast.makeText(MainActivity.this,
					"Clicked Interstitial '" + location + "'",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didShowInterstitial(String location)
		 * 
		 * This is called when an interstitial has been successfully shown
		 * 
		 * Is fired on: - showInterstitial() success
		 */
		@Override
		public void didShowInterstitial(String location) {
			Log.i("", "INTERSTITIAL '" + location + "' SHOWN");
		}

		/*
		 * didFailToLoadURL(String location)
		 * 
		 * This is called when a url after a click has failed to load for any
		 * reason
		 * 
		 * Is fired on: - Interstitial click - More-Apps click
		 * 
		 * Possible reasons: - No network connection - no valid activity to
		 * launch - unable to parse url
		 */
		@Override
		public void didFailToLoadUrl(String url) {
			// Show a house ad or do something else when a chartboost
			// interstitial fails to load

			Log.i("", "URL '" + url + "' REQUEST FAILED");
			Toast.makeText(MainActivity.this, "URL '" + url + "' Load Failed",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * More Apps delegate methods
		 */

		/*
		 * shouldDisplayLoadingViewForMoreApps()
		 * 
		 * Return false to prevent the pretty More-Apps loading screen
		 * 
		 * Is fired on: - showMoreApps()
		 */
		@Override
		public boolean shouldDisplayLoadingViewForMoreApps() {
			return true;
		}

		/*
		 * shouldRequestMoreApps()
		 * 
		 * Return false to prevent a More-Apps page request
		 * 
		 * Is fired on: - cacheMoreApps() - showMoreApps() if no More-Apps page
		 * is cached
		 */
		@Override
		public boolean shouldRequestMoreApps() {

			return true;
		}

		/*
		 * shouldDisplayMoreApps()
		 * 
		 * Return false to prevent the More-Apps page from displaying
		 * 
		 * Is fired on: - showMoreApps() - More-Apps page is loaded & ready to
		 * display
		 */
		@Override
		public boolean shouldDisplayMoreApps() {
			Log.i("", "SHOULD DISPLAY MORE APPS?");
			return true;
		}

		/*
		 * didFailToLoadMoreApps()
		 * 
		 * This is called when the More-Apps page has failed to load for any
		 * reason
		 * 
		 * Is fired on: - cacheMoreApps() failure - showMoreApps() failure if no
		 * More-Apps page was cached
		 * 
		 * Possible reasons: - No network connection - No publishing campaign
		 * matches for this user (go make a new one in the dashboard)
		 */
		@Override
		public void didFailToLoadMoreApps() {
			Log.i("", "MORE APPS REQUEST FAILED");
			Toast.makeText(MainActivity.this, "More Apps Load Failed",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didCacheMoreApps()
		 * 
		 * Is fired on: - cacheMoreApps() success - All assets are loaded
		 */
		@Override
		public void didCacheMoreApps() {
			Log.i("", "MORE APPS CACHED");
		}

		/*
		 * didDismissMoreApps()
		 * 
		 * This is called when the More-Apps page is dismissed
		 * 
		 * Is fired on: - More-Apps click - More-Apps close
		 */
		@Override
		public void didDismissMoreApps() {
			Log.i("", "MORE APPS DISMISSED");
			Toast.makeText(MainActivity.this, "Dismissed More Apps",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didCloseMoreApps()
		 * 
		 * This is called when the More-Apps page is closed
		 * 
		 * Is fired on: - More-Apps close
		 */
		@Override
		public void didCloseMoreApps() {
			Log.i("", "MORE APPS CLOSED");
			Toast.makeText(MainActivity.this, "Closed More Apps",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didClickMoreApps()
		 * 
		 * This is called when the More-Apps page is clicked
		 * 
		 * Is fired on: - More-Apps click
		 */
		@Override
		public void didClickMoreApps() {
			Log.i("", "MORE APPS CLICKED");
			Toast.makeText(MainActivity.this, "Clicked More Apps",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * didShowMoreApps()
		 * 
		 * This is called when the More-Apps page has been successfully shown
		 * 
		 * Is fired on: - showMoreApps() success
		 */
		@Override
		public void didShowMoreApps() {
			Log.i("", "MORE APPS SHOWED");
		}

		/*
		 * shouldRequestInterstitialsInFirstSession()
		 * 
		 * Return false if the user should not request interstitials until the
		 * 2nd startSession()
		 */
		@Override
		public boolean shouldRequestInterstitialsInFirstSession() {
			return true;
		}
	};

	/**
	 * 
	 * @param view
	 */
	public void onLoadButtonClick(View view) {
		/*
		 * showInterstitial()
		 * 
		 * Shows an interstitial on the screen
		 * 
		 * Notes: - Shows a cached interstitial if one exists - Otherwise
		 * requests an interstitial and shows it
		 */
		this.cb.showInterstitial();

		Log.i("", "showInterstitial");
		String toastStr = "Loading Interstitial";
		if (cb.hasCachedInterstitial())
			toastStr = "Loading Interstitial From Cache";
		Toast.makeText(this, toastStr, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * @param view
	 */
	public void onMoreButtonClick(View view) {
		/*
		 * showMoreApps()
		 * 
		 * Shows the More-Apps page
		 * 
		 * Notes: - Shows a cached More-Apps page if one exists - Otherwise
		 * requests the More-Apps page and shows it
		 */
		this.cb.showMoreApps();

		Log.i("", "showMoreApps");
		String toastStr = "Showing More-Apps";
		if (cb.hasCachedMoreApps())
			toastStr = "Showing More-Apps From Cache";
		Toast.makeText(this, toastStr, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * @param v
	 */
	public void onPreloadClick(View v) {
		/*
		 * cacheInterstitial()
		 * 
		 * Requests an interstitial from the server
		 */
		this.cb.cacheInterstitial();

		Log.i("", "cacheInterstitial");
		Toast.makeText(this, "Caching Interstitial", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * @param v
	 */
	public void onPreloadMoreAppsClick(View v) {
		/*
		 * cacheMoreApps()
		 * 
		 * Requests a More-Apps page from the server
		 */
		cb.cacheMoreApps();

		Log.i("", "cacheMoreApps");
		Toast.makeText(this, "Caching More-Apps", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 
	 * @param v
	 */
	public void onPreloadClearClick(View v) {
		/*
		 * clearCache()
		 * 
		 * Requests a More-Apps page from the server
		 */
		cb.clearCache();

		Log.i("", "clearCache");
		Toast.makeText(this, "Clearing cache", Toast.LENGTH_SHORT).show();
	}

}// end class
