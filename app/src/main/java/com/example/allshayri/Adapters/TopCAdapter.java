package com.example.allshayri.Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allshayri.Modals.BackgroundModal;
import com.example.allshayri.Modals.TopContentModal;
import com.example.allshayri.Modals.TopicModal;
import com.example.allshayri.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TopCAdapter extends RecyclerView.Adapter<TopCAdapter.TopCHolder> {
    FirebaseDatabase myDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myref;
    String allFavs = "allFavs";
    String allBacks = "allBacks";
    String shayri = "shayri";
    String shayriId = "shayriId";
    String hshayri = "";
    ArrayList<BackgroundModal> blist;
    int cnt = 0;
    ArrayList<TopContentModal> tlist;
    Context context;

    public TopCAdapter(ArrayList<TopContentModal> tlist, Context context) {
        this.tlist = tlist;
        this.context = context;
        getList();
    }

    @NonNull
    @Override
    public TopCHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_content_row, parent, false);
        return new TopCHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopCHolder holder, int position) {
        TopContentModal topicModal = tlist.get(position);
        holder.shayriName.setText(topicModal.getShayri().trim() + "\n \n");
        cnt = new Random().nextInt(blist.size());
        Picasso.get().load(blist.get(cnt).getBackImage()).placeholder(R.drawable.bg_six).into(holder.backImage);
        holder.prevText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnt = new Random().nextInt(blist.size());
                Picasso.get().load(blist.get(cnt).getBackImage()).placeholder(R.drawable.bg_six).into(holder.backImage);
//                Random r = new Random();
//                int color = Color.argb(255,r.nextInt(256),r.nextInt(256),r.nextInt(256));
//                holder.cnvLaout.setBackgroundColor(color);

            }
        });
        holder.nextText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cnt != blist.size() - 1) {
                    cnt++;
                } else {
                    cnt = 0;
                }
                Picasso.get().load(blist.get(cnt).getBackImage()).placeholder(R.drawable.fourteen).into(holder.backImage);
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hshayri = holder.shayriName.getText().toString();
                saveFavorite();
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTemp(getBitmapView(holder.cnvLaout));
            }
        });
        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText("copy", holder.shayriName.getText().toString());
                clipboardManager.setPrimaryClip(data);
                Toast.makeText(v.getContext(), "Copied....", Toast.LENGTH_SHORT).show();
            }
        });
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImg(holder.cnvLaout);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tlist.size();
    }

    class TopCHolder extends RecyclerView.ViewHolder {
        TextView shayriName, prevText, nextText;
        ImageView backImage, like, share, download, copy;
        LinearLayout cnvLaout;

        public TopCHolder(@NonNull View itemView) {
            super(itemView);
            shayriName = itemView.findViewById(R.id.shayriNameRow);
            backImage = itemView.findViewById(R.id.backImageRow);
            like = itemView.findViewById(R.id.likeRow);
            share = itemView.findViewById(R.id.shareRow);
            download = itemView.findViewById(R.id.downRow);
            copy = itemView.findViewById(R.id.copyRow);
            prevText = itemView.findViewById(R.id.prevText);
            nextText = itemView.findViewById(R.id.nextText);
            cnvLaout = itemView.findViewById(R.id.cnvLaout);
        }
    }

    public void saveFavorite() {
        myref = myDatabase.getReference().child(allFavs).push();
        Map<String, Object> map = new HashMap<>();
        map.put(shayri, hshayri);
        map.put(shayriId, myref.getKey());
        myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context.getApplicationContext(), "Added to favorite 🤩", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context.getApplicationContext(), "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void downloadImg(View view) {
        Bitmap b = getBitmapView(view);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                b, new Date().getTime() + "", null);
        if (path != null) {
            Toast.makeText(context.getApplicationContext(), "Downloaded successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareTemp(Bitmap bitmap) {
        try {

            File cachePath = new File(context.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(new File(cachePath, "image.png")); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File imagePath = new File(context.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(context, "com.example.allshayri.fileprovider", newFile);
        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, context.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(shareIntent);
        }
    }

    private Bitmap getBitmapView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return bitmap;
    }

    public void getList() {
        blist = new ArrayList<>();
        myDatabase.getReference().child(allBacks).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        BackgroundModal topicModal = dataSnapshot.getValue(BackgroundModal.class);
                        blist.add(topicModal);
                    }
                } else {
                    Toast.makeText(context.getApplicationContext(), "Doesn't exits", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context.getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
