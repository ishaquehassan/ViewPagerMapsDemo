package info.devexchanges.viewpagercards;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailsActivity extends AppCompatActivity {
    MapView mapView;
    GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final LatLng latLng = getIntent().getParcelableExtra("position");
        final String titleStr = getIntent().getStringExtra("title");
        final String descp = getIntent().getStringExtra("descp");

        setTitle(titleStr+" Details");

        final TextView title = (TextView) findViewById(R.id.title);
        final TextView details = (TextView) findViewById(R.id.details);
        Button button = (Button) findViewById(R.id.button);

        title.setText(titleStr);
        details.setText(descp);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String URL = "https://www.google.com/maps/dir/?api=1&travelmode=driving&dir_action=navigate&destination="+latLng.latitude+","+latLng.longitude;
                Uri location = Uri.parse(URL);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);
            }
        });


        mapView = (MapView) findViewById(R.id.map);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                DetailsActivity.this.googleMap = googleMap;
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                googleMap.setBuildingsEnabled(false);
                googleMap.setIndoorEnabled(false);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                googleMap.addMarker(new MarkerOptions().title(titleStr).position(latLng));

            }
        });
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
    }
}
