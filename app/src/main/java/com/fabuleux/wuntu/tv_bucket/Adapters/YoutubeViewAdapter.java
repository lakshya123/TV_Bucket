package com.fabuleux.wuntu.tv_bucket.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fabuleux.wuntu.tv_bucket.Models.YoutubelinksFinalModel;
import com.fabuleux.wuntu.tv_bucket.R;
import com.fabuleux.wuntu.tv_bucket.Utils.UrlConstants;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

/**
 * Created by Wuntu on 29-07-2017.
 */

public class YoutubeViewAdapter extends RecyclerView.Adapter<YoutubeViewAdapter.VideoInfoHolder> {

    private Context ctx;
    private ArrayList<YoutubelinksFinalModel> youtubeLinks = new ArrayList<>();
    private UrlConstants urlConstants = UrlConstants.getSingletonRef();

    public YoutubeViewAdapter(Context context, ArrayList<YoutubelinksFinalModel> youtubeLinks) {
        this.ctx = context;
        this.youtubeLinks = youtubeLinks;
    }

    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_list_view, parent, false);
        return new VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {

        holder.youtube_title.setText(youtubeLinks.get(position).getName());

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(urlConstants.Youtube_URL + youtubeLinks.get(position).getKey());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                ctx.startActivity(intent);
            }
        });


        final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };

        String DEVELOPER_KEY = "AIzaSyCbZWoplKqfMn6cKTWYXsKtWM1i5PnZrnI";
        holder.youTubeThumbnailView.initialize(DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                youTubeThumbnailLoader.setVideo(youtubeLinks.get(position).getKey());
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                //In case of failure
            }
        });
    }

    @Override
    public int getItemCount() {
        return youtubeLinks.size();
    }

    class VideoInfoHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
        ImageView playButton;
        TextView youtube_title;

        VideoInfoHolder(View itemView) {
            super(itemView);
            playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
            youtube_title = (TextView) itemView.findViewById(R.id.youtube_title);

            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
        }
    }
}
