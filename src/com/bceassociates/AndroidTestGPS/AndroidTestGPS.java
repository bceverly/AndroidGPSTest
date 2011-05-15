package com.bceassociates.AndroidTestGPS;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class AndroidTestGPS extends Activity implements LocationListener {
	LocationManager lm;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        lm = (LocationManager)getSystemService(LOCATION_SERVICE);
    }
    
    @Override
	protected void onResume() {
		/*
		 * onResume is is always called after onStart, even if the app hasn't been
		 * paused
		 *
		 * add location listener and request updates every 1000ms or 10m
		 */
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, this);
		super.onResume();
	}

	@Override
	protected void onPause() {
		/* GPS, as it turns out, consumes battery like crazy */
		lm.removeUpdates(this);
		super.onResume();
	}

	@Override
	public void onLocationChanged(Location location) {
		TextView longitudeTV = (TextView) findViewById(R.id.longitude);
		TextView latitudeTV = (TextView) findViewById(R.id.latitude);
		TextView altitudeTV = (TextView) findViewById(R.id.altitude);
		TextView accuracyTV = (TextView) findViewById(R.id.accuracy);
		
		longitudeTV.setText("  " + location.getLongitude());
		latitudeTV.setText("  " + location.getLatitude());
		altitudeTV.setText("  " + location.getAltitude());
		accuracyTV.setText("  " + location.getAccuracy());
	}

	@Override
	public void onProviderDisabled(String provider) {
		/* bring up the GPS settings */
		Intent intent = new Intent(
				android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(intent);
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
	
	@Override
	protected void onStop() {
		finish();
		super.onStop();
	}
}