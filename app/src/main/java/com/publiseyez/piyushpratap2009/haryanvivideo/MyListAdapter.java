package com.publiseyez.piyushpratap2009.haryanvivideo;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.startapp.android.publish.adsCommon.StartAppAd;

import java.util.List;

public class MyListAdapter extends ArrayAdapter<VideoItem> {

    private Activity contxt;
    private int res;
    private List<VideoItem> mylist;

    public MyListAdapter(Activity context, int resource, List<VideoItem> objects) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
        contxt = context;
        res = resource;
        mylist = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = contxt.getLayoutInflater().inflate(res, parent, false);
        }
        ImageView thumbnail = (ImageView) convertView.findViewById(com.publiseyez.piyushpratap2009.haryanvivideo.R.id.video_thumbnail);
        TextView title = (TextView) convertView.findViewById(com.publiseyez.piyushpratap2009.haryanvivideo.R.id.video_title);
        TextView description = (TextView) convertView.findViewById(com.publiseyez.piyushpratap2009.haryanvivideo.R.id.video_description);
        LinearLayout layout = (LinearLayout) convertView.findViewById(com.publiseyez.piyushpratap2009.haryanvivideo.R.id.activity_splash);

        VideoItem searchResult = mylist.get(position);

        Picasso.with(contxt).load(searchResult.getThumbnailURL()).into(thumbnail);
        title.setText(Html.fromHtml(searchResult.getTitle()));

        description.setText(searchResult.getDescription());

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetConnection.checkConnection(contxt)) {
                    VideoItem searchResult1 = mylist.get(position);
                    Intent play = new Intent(contxt, YoutubePlayerActivity.class);
                    play.putExtra("videoid", searchResult1.getId());
                    play.putExtra("title", searchResult1.getTitle());
                    play.putExtra("description", searchResult1.getDescription());
                    contxt.startActivity(play);
                    StartAppAd.showAd(contxt);

                } else {
                    Toast.makeText(contxt, "Internet not available", Toast.LENGTH_LONG).show();
                }
            }
        });

        return convertView;
    }


}
