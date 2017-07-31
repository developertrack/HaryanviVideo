package com.publiseyez.piyushpratap2009.haryanvivideo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.startapp.android.publish.ads.nativead.NativeAdDetails;
import com.startapp.android.publish.ads.nativead.NativeAdPreferences;
import com.startapp.android.publish.ads.nativead.StartAppNativeAd;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

    ListView myResultView;
    EditText mySearchText;
    Intent data;
    String title, songsearch;
    TextView tit;
    private ProgressDialog mProgressDialog;
    //	private StartAppAd startAppAd = new StartAppAd(this);
    private StartAppAd startAppAd = new StartAppAd(this);

    /**
     * StartApp Native Ad declaration
     */
    private StartAppNativeAd startAppNativeAd = new StartAppNativeAd(this);
    private NativeAdDetails nativeAd = null;
    /**
     * Native Ad Callback
     */
    private AdEventListener nativeAdListener = new AdEventListener() {

        @Override
        public void onReceiveAd(Ad ad) {

            // Get the native ad
            ArrayList<NativeAdDetails> nativeAdsList = startAppNativeAd.getNativeAds();
            if (nativeAdsList.size() > 0) {
                nativeAd = nativeAdsList.get(0);
            }

            // Verify that an ad was retrieved
            if (nativeAd != null) {

                // When ad is received and displayed - we MUST send impression
                nativeAd.sendImpression(MainActivity.this);


            }
        }

        @Override
        public void onFailedToReceiveAd(Ad ad) {

            // Error occurred while loading the native ad

        }
    };
    private TextWatcher myTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            if (s.toString().length() > 3) {
                new NetworkClass().execute(s.toString().trim().replaceAll(" ", ""));
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        StartAppSDK.init(this, "204552668", true);
        setContentView(com.publiseyez.piyushpratap2009.haryanvivideo.R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(com.publiseyez.piyushpratap2009.haryanvivideo.R.color.actionbar));
        }
        LinearLayout mainLayout = (LinearLayout) findViewById(com.publiseyez.piyushpratap2009.haryanvivideo.R.id.second_layout);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        songsearch = intent.getStringExtra("playername");

        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        myResultView = (ListView) findViewById(com.publiseyez.piyushpratap2009.haryanvivideo.R.id.listView_youTubeResults);
        mySearchText = (EditText) findViewById(com.publiseyez.piyushpratap2009.haryanvivideo.R.id.editText_searchKeyWord);
        tit = (TextView) findViewById(com.publiseyez.piyushpratap2009.haryanvivideo.R.id.tit);

        tit.setText(title);

        mySearchText.setVisibility(View.GONE);

        mySearchText.setVisibility(View.GONE);
        new NetworkClass().execute(songsearch);

        startAppNativeAd.loadAd(
                new NativeAdPreferences()
                        .setAdsNumber(1)
                        .setAutoBitmapDownload(true)
                        .setPrimaryImageSize(2),
                nativeAdListener);
        mySearchText.addTextChangedListener(myTextWatcher);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.publiseyez.piyushpratap2009.haryanvivideo.R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == com.publiseyez.piyushpratap2009.haryanvivideo.R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

//        final StartAppAd rewardedVideo = new StartAppAd(MainActivity.this);
//
//
//
//        rewardedVideo.setVideoListener(new VideoListener() {
//
//            @Override
//            public void onVideoCompleted() {
////                    Toast.makeText(DashBoard.this, "Rewarded video has completed - grant the user his reward", Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//
//        rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
//
//            @Override
//            public void onReceiveAd(Ad arg0) {
//                rewardedVideo.showAd();
//            }
//
//            @Override
//            public void onFailedToReceiveAd(Ad arg0) {
//
//                Log.e("MainActivity", "Failed to load rewarded video with reason: " + arg0.getErrorMessage());
//            }
//        });
        startAppAd.onBackPressed();
        super.onBackPressed();
    }

    class NetworkClass extends AsyncTask<String, Void, List<VideoItem>> {

        @Override
        protected void onPreExecute() {
            mProgressDialog.show();
        }

        @Override
        protected List<VideoItem> doInBackground(String... params) {
            // TODO Auto-generated method stub
            JSONObject jsonObject = null;

            try {
                String service = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=";
                String para = "&type=video&maxResults=40&key=AIzaSyC-88iZ8S9vuosrlfUqv55CfSPmbkMIQAQ&&format=json";
                String serchkeys = URLEncoder.encode(params[0], "UTF-8");
                String original = service + serchkeys + para;
                HttpGet httpGet = new HttpGet(original);
                HttpClient client = new DefaultHttpClient();
                HttpResponse response;
                StringBuilder stringBuilder = new StringBuilder();

                response = client.execute(httpGet);
                HttpEntity entity = response.getEntity();

                InputStream stream = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

//						    int b;
//						    while ((b = stream.read()) != -1) {
//						        stringBuilder.append((char) b);
//						    }

                String line = null;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

//						stream.close();
                jsonObject = new JSONObject(stringBuilder.toString());
//						Log.e("data",jsonObject.toString());
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            List<VideoItem> list = new DataRetiver().parseJason(jsonObject);
            return list;
        }

        @Override
        protected void onPostExecute(List<VideoItem> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            MyListAdapter adapter = new MyListAdapter(MainActivity.this, com.publiseyez.piyushpratap2009.haryanvivideo.R.layout.video_item, result);
            myResultView.setAdapter(adapter);
            mProgressDialog.dismiss();


        }
    }
}
