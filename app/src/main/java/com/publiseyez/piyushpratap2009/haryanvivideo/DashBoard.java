package com.publiseyez.piyushpratap2009.haryanvivideo;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.startapp.android.publish.ads.nativead.NativeAdDetails;
import com.startapp.android.publish.ads.nativead.NativeAdPreferences;
import com.startapp.android.publish.ads.nativead.StartAppNativeAd;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashBoard extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private StartAppAd startAppAd = new StartAppAd(this);

    /** StartApp Native Ad declaration */
    private StartAppNativeAd startAppNativeAd = new StartAppNativeAd(this);
    private NativeAdDetails nativeAd = null;
    /** Native Ad Callback */
    private AdEventListener nativeAdListener = new AdEventListener() {

        @Override
        public void onReceiveAd(Ad ad) {

            // Get the native ad
            ArrayList<NativeAdDetails> nativeAdsList = startAppNativeAd.getNativeAds();
            if (nativeAdsList.size() > 0){
                nativeAd = nativeAdsList.get(0);
            }

            // Verify that an ad was retrieved
            if (nativeAd != null){

                // When ad is received and displayed - we MUST send impression
                nativeAd.sendImpression(DashBoard.this);


            }
        }

        @Override
        public void onFailedToReceiveAd(Ad ad) {

            // Error occurred while loading the native ad

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StartAppSDK.init(this, "204552668", true);
        setContentView(com.publiseyez.piyushpratap2009.haryanvivideo.R.layout.dashboard);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(com.publiseyez.piyushpratap2009.haryanvivideo.R.color.actionbar));
        }
        expandableListView = (ExpandableListView) findViewById(com.publiseyez.piyushpratap2009.haryanvivideo.R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        final int groupPosition, final int childPosition, long id) {

                if (InternetConnection.checkConnection(DashBoard.this)) {

                   /* Intent youtubelist = new Intent(DashBoard.this, MainActivity.class);
                    youtubelist.putExtra("title", expandableListDetail.get(
                            expandableListTitle.get(groupPosition)).get(
                            childPosition));
                    youtubelist.putExtra("playername", expandableListDetail.get(
                            expandableListTitle.get(groupPosition)).get(
                            childPosition) + " Bhojpuri ");
                    startActivity(youtubelist);*/

                    startAppAd.showAd(new AdDisplayListener() {

                        @Override
                        public void adHidden(Ad ad) {

                            // Run second activity right after the ad was hidden
                            Intent youtubelist = new Intent(DashBoard.this, MainActivity.class);
                            youtubelist.putExtra("title", expandableListDetail.get(
                                    expandableListTitle.get(groupPosition)).get(
                                    childPosition));
                            youtubelist.putExtra("playername", expandableListDetail.get(
                                    expandableListTitle.get(groupPosition)).get(
                                    childPosition) + " Haryanvi ");
                            startActivity(youtubelist);
                            StartAppAd.showAd(DashBoard.this);
                        }


                        @Override
                        public void adDisplayed(Ad ad) {

                        }


                        @Override
                        public void adClicked(Ad arg0) {

                        }


                        @Override
                        public void adNotDisplayed(Ad arg0) {

                        }
                    });

                } else {
                    Toast.makeText(DashBoard.this, "Internet not available", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        startAppNativeAd.loadAd(
                new NativeAdPreferences()
                        .setAdsNumber(1)
                        .setAutoBitmapDownload(true)
                        .setPrimaryImageSize(2),
                nativeAdListener);
    }
    @Override
    public void onResume() {
        super.onResume();
        startAppAd.onResume();
    }

    /**
     * Part of the activity's life cycle, StartAppAd should be integrated here
     * for the home button exit ad integration.
     */
    @Override
    public void onPause() {
        super.onPause();
        startAppAd.onPause();
    }

    /**
     * Part of the activity's life cycle, StartAppAd should be integrated here
     * for the back button exit ad integration.
     */
    @Override
    public void onBackPressed() {

//        startAppAd.onBackPressed();
        super.onBackPressed();
    }

}
