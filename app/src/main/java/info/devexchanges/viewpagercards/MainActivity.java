package info.devexchanges.viewpagercards;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity {

    ImageView bigImage;
    ArrayList<CardFragment> fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ViewPager viewPager = findViewById(R.id.viewPager);
        bigImage = findViewById(R.id.bigImage);

        fragments =new ArrayList<>();
        fragments.add(createItem("Water Park One",getResources().getString(R.string.lorem_ipsum),new LatLng(24.8852577,67.0750451),"https://upload.wikimedia.org/wikipedia/commons/thumb/f/f9/Munsu_Water_Park_-_Pyongyang%2C_North_Korea_%2811511854045%29.jpg/1200px-Munsu_Water_Park_-_Pyongyang%2C_North_Korea_%2811511854045%29.jpg"));
        fragments.add(createItem("Water Park Two",getResources().getString(R.string.lorem_ipsum),new LatLng(24.8987657,67.0381824),"https://cdn.thecrazytourist.com/wp-content/uploads/2016/09/Six-Flags-New-England-Agawam-770x430.jpg"));
        fragments.add(createItem("Water Park Three",getResources().getString(R.string.lorem_ipsum),new LatLng(24.9070641,66.9940388),"https://cdn.thecrazytourist.com/wp-content/uploads/2016/09/Zoombezi-Bay-Powell-770x430.jpg"));
        fragments.add(createItem("Water Park One",getResources().getString(R.string.lorem_ipsum),new LatLng(24.8852577,67.0750451),"https://cdn.thecrazytourist.com/wp-content/uploads/2016/09/Carowinds-Charlotte.jpg"));
        fragments.add(createItem("Water Park Two",getResources().getString(R.string.lorem_ipsum),new LatLng(24.8987657,67.0381824),"https://img.budgettravel.com/_contentHero1x/ph2011010603569-08201420-130006_original.jpg?mtime=20140903194027"));
        fragments.add(createItem("Water Park Three",getResources().getString(R.string.lorem_ipsum),new LatLng(24.9070641,66.9940388),"http://static.asiawebdirect.com/m/phuket/portals/phuket-com/homepage/phuket-magazine/best-water-parks-in-thailand/pagePropertiesImage/best-water-parks-in-thailand.jpg"));

        CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),fragments, dpToPixels(2, this));
        ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(viewPager, pagerAdapter);
        fragmentCardShadowTransformer.enableScaling(true);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);

        loadImage(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                loadImage(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void loadImage(int position){
        String url = fragments.get(position).getArguments().getString("bigImage");
        if(url != null && !url.isEmpty()){
            Glide.with(this).load(url).transition(withCrossFade(500)).into(bigImage);
        }
    }

    private CardFragment createItem(String title,String descp,LatLng location,String bigImage){
        CardFragment fragment = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("position",location);
        bundle.putString("title",title);
        bundle.putString("descp",descp);
        bundle.putString("bigImage",bigImage);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}
