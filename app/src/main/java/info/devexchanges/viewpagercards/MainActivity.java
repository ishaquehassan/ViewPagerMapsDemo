package info.devexchanges.viewpagercards;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MapView mapView;
    GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        final ArrayList<CardFragment> fragments=new ArrayList<>();
        fragments.add(createItem("Water Park One",getResources().getString(R.string.lorem_ipsum),new LatLng(24.8852577,67.0750451)));
        fragments.add(createItem("Water Park Two",getResources().getString(R.string.lorem_ipsum),new LatLng(24.8987657,67.0381824)));
        fragments.add(createItem("Water Park Three",getResources().getString(R.string.lorem_ipsum),new LatLng(24.9070641,66.9940388)));
        fragments.add(createItem("Water Park One",getResources().getString(R.string.lorem_ipsum),new LatLng(24.8852577,67.0750451)));
        fragments.add(createItem("Water Park Two",getResources().getString(R.string.lorem_ipsum),new LatLng(24.8987657,67.0381824)));
        fragments.add(createItem("Water Park Three",getResources().getString(R.string.lorem_ipsum),new LatLng(24.9070641,66.9940388)));

        CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),fragments, dpToPixels(2, this));
        ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
        fragmentCardShadowTransformer.enableScaling(true);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);

        mapView = (MapView) findViewById(R.id.map);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                MainActivity.this.googleMap = googleMap;
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                LatLng position = fragments.get(0).getArguments().getParcelable("position");
                googleMap.setBuildingsEnabled(false);
                googleMap.setIndoorEnabled(false);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,17));
                final Marker marker = googleMap.addMarker(new MarkerOptions().position(position));

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        CardFragment cardFragment = fragments.get(position);
                        Bundle bundle = cardFragment.getArguments();
                        LatLng latLng = bundle.getParcelable("position");
                        String title = bundle.getString("title");
                        marker.setPosition(latLng);
                        marker.setTitle(title);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17),1000,null);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        });
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

    }

    private CardFragment createItem(String title,String descp,LatLng location){
        CardFragment fragment = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("position",location);
        bundle.putString("title",title);
        bundle.putString("descp",descp);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}
