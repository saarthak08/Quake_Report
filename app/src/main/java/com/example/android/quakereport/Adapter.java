package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private List<String> mEarthquake[];
        private final int VIEW_TYPE_ITEM=1;
        private final int VIEW_TYPE_LOADING=0;

        private Context mContext;
        public Adapter(EarthquakeActivity context, List<String>[] cn)
        {
            mEarthquake=cn;
            mContext=context;
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

            if(viewType==VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
                return new ItemViewHolder(view);
            }
            else{
                View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_loading,viewGroup,false);
                return new LoadingViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            if(viewHolder instanceof  ItemViewHolder) {
                final int k = i;
                ItemViewHolder.textView1.setText(mEarthquake[1].get(i));
                ItemViewHolder.textView2.setText(mEarthquake[0].get(i));
                GradientDrawable magnitudeCircle = (GradientDrawable) ItemViewHolder.textView1.getBackground();
                int magnitudecolor = getMagnitudeColor(mEarthquake[1].get(i));
                magnitudeCircle.setColor(magnitudecolor);
                ItemViewHolder.textView3.setText(mEarthquake[2].get(i));
                ItemViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(mEarthquake[3].get(k)));
                        mContext.startActivity(i);
                    }
                });
            }
            else if(viewHolder instanceof LoadingViewHolder)
            {
                showLoadingView((LoadingViewHolder)viewHolder,i);
            }
        }
        public int getMagnitudeColor(String mag)
        {
            float magni=Float.parseFloat(mag);
            int result;
            if(magni>=5&&magni<=5.2)
            {
                result= ContextCompat.getColor(mContext, R.color.magnitude1);

            }
            else if(magni>5.2&&magni<=5.5)
            {
                result=ContextCompat.getColor(mContext, R.color.magnitude2);
            }
            else if(magni>5.5&&magni<=5.8)
            {
                result=ContextCompat.getColor(mContext, R.color.magnitude3);

            }
            else if(magni>5.8&&magni<=6.0)
            {
                result=ContextCompat.getColor(mContext, R.color.magnitude4);

            }
            else if(magni>6.0&&magni<=6.2)
            {
                result=ContextCompat.getColor(mContext, R.color.magnitude5);
            }
            else if(magni>6.2&&magni<=6.5)
            {
                result=ContextCompat.getColor(mContext, R.color.magnitude6);
            }
            else if(magni>6.5&&magni<=6.8)
            {
                result=ContextCompat.getColor(mContext, R.color.magnitude7);
            }
            else if(magni>6.8&&magni<=7.0)
            {
                result=ContextCompat.getColor(mContext, R.color.magnitude8);
            }
            else {
                result = ContextCompat.getColor(mContext, R.color.magnitude10plus);
            }
            return result;
        }

        @Override
        public int getItemViewType(int position) {
            return mEarthquake[0].get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }

        @Override
        public int getItemCount() {
            return mEarthquake[0]==null?0:mEarthquake[0].size();
        }
        private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        }
        static class ItemViewHolder extends RecyclerView.ViewHolder
     {
        public static TextView textView1;
        public static TextView textView2;
        public static TextView textView3;
        public static RelativeLayout relativeLayout;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.textView1);
            textView2=itemView.findViewById(R.id.textView2);
            textView3=itemView.findViewById(R.id.textView3);
            relativeLayout=itemView.findViewById(R.id.relative_layout);
        }
    }
    static class LoadingViewHolder extends RecyclerView.ViewHolder
    {
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar=itemView.findViewById(R.id.progressBar2);
        }
    }
}
