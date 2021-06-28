package com.arman.traplus.menu;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.arman.traplus.Quest;
import com.arman.traplus.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Formatter;

// this class is the item adapter for make the menu list
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    private Context context;
    private Menu menu;
    private int fontSize;

    public ItemAdapter(Context context, Menu menu) {
        this.context = context;
        this.menu = menu;
    }

    // Create new views
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.recycleview_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Item item = menu.getItems()[position];

        holder.zhNameTextView.setText(item.getZhName());
        holder.zhNameTextView.setTextSize(fontSize);
        holder.jaNameTextView.setText(item.getJaName());
        holder.jaNameTextView.setTextSize(fontSize-2);
        holder.countTextView.setText(Integer.toString(item.getCount()));
        holder.countTextView.setTextSize(fontSize);

        if (menu.getViewAllProduct()) {
            holder.plusButton.setVisibility(View.VISIBLE);
            holder.minusButton.setVisibility(View.VISIBLE);
            String zhName = holder.zhNameTextView.getText().toString();
            String jaName = holder.jaNameTextView.getText().toString();
            holder.zhNameTextView.setText(zhName);
            holder.zhNameTextView.setTextSize(fontSize);
            holder.jaNameTextView.setText(jaName);
            holder.jaNameTextView.setVisibility(View.VISIBLE);
        }
        else {
            holder.plusButton.setVisibility(View.GONE);
            holder.minusButton.setVisibility(View.GONE);
            String zhName = holder.zhNameTextView.getText().toString();
            String jaName = holder.jaNameTextView.getText().toString();
            holder.zhNameTextView.setText(jaName);
            holder.zhNameTextView.setTextSize(19);
            holder.jaNameTextView.setText(zhName);
            holder.jaNameTextView.setVisibility(View.INVISIBLE);
            holder.countTextView.setTextSize(19);
        }

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog(item);
            }
        });

        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.addToCart(item.getId(), 1);
                holder.countTextView.setText(Integer.toString(item.getCount()));
            }
        });

        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.addToCart(item.getId(), -1);
                holder.countTextView.setText(Integer.toString(item.getCount()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return menu.getItems().length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView zhNameTextView, jaNameTextView, countTextView;
        private LinearLayout itemLayout;
        private Button plusButton, minusButton;

        public ViewHolder(View itemView) {
            super(itemView);
            zhNameTextView = itemView.findViewById(R.id.item_zhName);
            jaNameTextView = itemView.findViewById(R.id.item_jaName);
            countTextView = itemView.findViewById(R.id.item_count);
            itemLayout = itemView.findViewById(R.id.recycleview_layout);
            plusButton = itemView.findViewById(R.id.plus_item);
            minusButton = itemView.findViewById(R.id.minus_item);
        }
    }

    // Create dialog to show item details
    // we need to add the method to show the the image
    // so this is need to modify
    public void createDialog(final Item item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // the title of the dialog
        builder.setTitle("Item Selected");

        View itemView = ((Activity)context).getLayoutInflater().inflate(R.layout.item_dialog, null);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        layoutParams.gravity = Gravity.CENTER;

        TextView zhName = itemView.findViewById(R.id.dialog_zhName);
        zhName.setText(item.getZhName());
        TextView jaName = itemView.findViewById(R.id.dialog_jaName);
        jaName.setText(item.getJaName());

        ImageView imageView = itemView.findViewById(R.id.imageViewWeb);
        Button showBtn = itemView.findViewById(R.id.showImageBtn);
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long startTime = System.currentTimeMillis();
                Drawable drawable = imageView.getDrawable();
                String path = "http://armanser.asuscomm.com:8080/find/" + item.getJaName();
                Glide.with(context)
                        .load(path)
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //don't save in the mobile device
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            //for load fail case will wait 1sec to reload again
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        if(drawable == imageView.getDrawable()) {
                                            refresh(itemView, item.getJaName(), imageView, startTime);
                                        }
                                    }
                                }, 1000);   //1 seconds
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                long endTime = System.currentTimeMillis();
                                String methodTime= new Formatter().format("%.3f", (double)((endTime-startTime))/1000).toString();
                                Log.e("Timer", item.getJaName() + "The first change time is "+methodTime+"sec.");
                                return false;
                            }

                        })
                        .into(imageView);



            }
        });

        ImageView imgQuest = itemView.findViewById(R.id.imageViewQuest);
        imgQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Quest.class);
                i.putExtra("jaText", item.getJaName());
                context.startActivity(i);

            }
        });

        builder.setView(itemView);
        builder.setNeutralButton("Close", null);
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void refresh(View activity1, String ja, ImageView imageView, long startTime){
        String path = "http://armanser.asuscomm.com:8080/file/" + ja +".jpg";
        Glide.with(activity1)
                .load(path)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE) //don't save in the mobile device
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        long endTime = System.currentTimeMillis();
                        String methodTime= new Formatter().format("%.3f", (double)((endTime-startTime))/1000).toString();
                        Log.v("Timer", ja + "The second change time is "+methodTime+"sec.");
                        return false;
                    }

                })
                .into(imageView);
    }


    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
}
