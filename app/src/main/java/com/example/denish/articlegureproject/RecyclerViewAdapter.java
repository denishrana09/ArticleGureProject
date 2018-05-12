package com.example.denish.articlegureproject;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by denish on 11/5/18.
 */


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    public ArrayList<DataItem> mDataItems = new ArrayList<>();
    private Context mContext;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDBRef;
    private ChildEventListener mChildEventListener;

    public RecyclerViewAdapter(Context context, ArrayList<DataItem> dataItems) {
        mDataItems = dataItems;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDBRef = mFirebaseDatabase.getReference().child("pics");
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        final String imageName = mDataItems.get(position).getImgPic();

        int resID = mContext.getResources().getIdentifier("@drawable/"+imageName , "drawable", mContext.getPackageName());
        Picasso.get().load(resID).into(holder.img_pic);

        final int originalLikes = mDataItems.get(position).getLikes();

        Log.d(TAG, "onBindViewHolder: inside");
        holder.img_text.setText(mDataItems.get(position).getImgText());

        holder.likes.setText(originalLikes+"");

        Log.d(TAG, "onBindViewHolder: "+ mDataItems.get(position).toString());
        holder.img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDBRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        DataItem item = dataSnapshot.getValue(DataItem.class);
                        Log.d(TAG, "onChildAdded: imageName = " + imageName);
                        Log.d(TAG, "onChildAdded: item.getImagePic() = " + item.getImgPic());
                        int temp=0;
                        if(item.getImgPic().equals(imageName)){
                            String key = dataSnapshot.getKey();
                            mDBRef.child(key).child("likes").setValue((temp=item.getLikes()+1));
                            holder.likes.setText(temp+"");
                            int ind;
                            for(ind=0;ind<mDataItems.size();ind++){
                                DataItem me = mDataItems.get(ind);
                                if(me.getImgPic().equals(imageName)){
                                    break;
                                }
                            }
                            DataItem i1 = mDataItems.get(ind);
                            i1.setLikes(i1.getLikes()+1);
                            mDataItems.set(ind,i1);
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView img_pic;

        TextView img_text;
        TextView likes;

        ImageView img_like;
        ConstraintLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            img_pic = itemView.findViewById(R.id.img_pic);
            img_text = itemView.findViewById(R.id.img_text);
            likes = itemView.findViewById(R.id.like_text);
            img_like = itemView.findViewById(R.id.img_like);
            parentLayout = itemView.findViewById(R.id.parent_layout1);
        }
    }
}















