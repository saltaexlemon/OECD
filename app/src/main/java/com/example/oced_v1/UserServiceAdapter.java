package com.example.oced_v1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class UserServiceAdapter extends RecyclerView.Adapter<UserServiceAdapter.MyHolder>{

    Context context;
    List<ServiceModal> postModels;

    public UserServiceAdapter(Context context, List<ServiceModal> postModelList) {
        this.context = context;
        this.postModels = postModelList;
    }

    @NonNull
    @Override
    public UserServiceAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_post, parent , false);
        return new UserServiceAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserServiceAdapter.MyHolder holder, int position) {

        String name = postModels.get(position).getApName();
        String state = postModels.get(position).getApState();
        String image = postModels.get(position).getApImage();

        holder.showName.setText(name);
        holder.showState.setText(state);
        Glide.with(context).load(image).into(holder.showImage);

    }

    @Override
    public int getItemCount() {
        return postModels.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView showName,showState;
        ImageView showImage;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            showImage = itemView.findViewById(R.id.ShowImage);
            showName =itemView.findViewById(R.id.ShowName);
            showState =itemView.findViewById(R.id.ShowState);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent UserServiceDetailsActivity = new Intent(context,UserServiceDetailsActivity.class);
                    int position = getAdapterPosition();

                    UserServiceDetailsActivity.putExtra("ApFid",postModels.get(position).getApFid());
                    UserServiceDetailsActivity.putExtra("ApName",postModels.get(position).getApName());
                    UserServiceDetailsActivity.putExtra("ApImage",postModels.get(position).getApImage());

                    UserServiceDetailsActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(UserServiceDetailsActivity);

                }
            });

        }
    }

}
