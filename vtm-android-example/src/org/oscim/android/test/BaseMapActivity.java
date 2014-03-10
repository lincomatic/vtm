/*
 * Copyright 2013 Hannes Janetzek
 *
 * This file is part of the OpenScienceMap project (http://www.opensciencemap.org).
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.oscim.android.test;

import org.oscim.android.MapActivity;
import org.oscim.android.MapView;
import org.oscim.android.cache.TileCache;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.theme.VtmThemes;
import org.oscim.tiling.TileSource;
import org.oscim.tiling.source.oscimap4.OSciMap4TileSource;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class BaseMapActivity extends MapActivity {

	private final static boolean USE_CACHE = true;

	MapView mMapView;
	VectorTileLayer mBaseLayer;
	TileSource mTileSource;

	private TileCache mCache;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		mMapView = (MapView) findViewById(R.id.mapView);
		registerMapView(mMapView);

		mTileSource = new OSciMap4TileSource();

		if (USE_CACHE) {
			mCache = new TileCache(this, null, "tile.db");
			mCache.setCacheSize(512 * (1 << 10));
			mTileSource.setCache(mCache);
		}
		mBaseLayer = mMap.setBaseMap(mTileSource);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (USE_CACHE)
			mCache.dispose();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
			case R.id.theme_default:
				mMap.setTheme(VtmThemes.DEFAULT);
				item.setChecked(true);
				return true;

			case R.id.theme_tubes:
				mMap.setTheme(VtmThemes.TRONRENDER);
				item.setChecked(true);
				return true;

			case R.id.theme_osmarender:
				mMap.setTheme(VtmThemes.OSMARENDER);
				item.setChecked(true);
				return true;

			case R.id.theme_newtron:
				mMap.setTheme(VtmThemes.NEWTRON);
				item.setChecked(true);
				return true;
		}

		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.theme_menu, menu);
		return true;
	}
}
