package com.example.allshayri.Adapters;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allshayri.Modals.ImageModal;
import com.example.allshayri.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.imageHolder>{
  ArrayList<ImageModal> ilist;
  Context context;
    public ImageAdapter(ArrayList<ImageModal> ilist, Context context) {
        this.ilist = ilist;
        this.context = context;
    }

    @NonNull
    @Override
    public imageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_row,parent,false);
        return new imageHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull imageHolder holder, int position) {
        ImageModal modal = ilist.get(position);
        Picasso.get().load(modal.getImage()).into(holder.image);
        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               shareTemp(getBitmapView(holder.image));
            }
        });
        holder.dwnImgRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImg(holder.image);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ilist.size();
    }
    class imageHolder extends RecyclerView.ViewHolder {
        TextView imgBgText;
        ImageView image,imgShare,dwnImgRow;
        public imageHolder(@NonNull View itemView) {
            super(itemView);
            imgBgText = itemView.findViewById(R.id.imgBgTextRow);
            image = itemView.findViewById(R.id.imageRow);
            imgShare = itemView.findViewById(R.id.imgShareRow);
            dwnImgRow = itemView.findViewById(R.id.dwnImgRow);
        }
    }

//    private void permission(String url) {
//        Dexter.withContext(context.getApplicationContext())
//                .withPermissions(
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE
//                ).withListener(new MultiplePermissionsListener() {
//            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
//                if(report.areAllPermissionsGranted()){
//                    downloadImage(url);
//                }
//                else{
//                    Toast.makeText(context.getApplicationContext(), "Give all permision", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
//        }).check();
//    }
//    private void downloadImage(String url) {
//        Toast.makeText(context.getApplicationContext(), "I came "+url, Toast.LENGTH_SHORT).show();
//        ProgressDialog pd = new ProgressDialog(context.getApplicationContext());
//        pd.setMessage("Downloading...");
//        pd.setCancelable(false);
//        pd.show();
//        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//         url = "https://firebasestorage.googleapis.com/v0/b/allshayri-d1eda.appspot.com/o/allImage%2F1676706023680.png?alt=media&token=4bcb7c96-b377-4b00-be92-d88bb0096eda";
//        PRDownloader.download(url, file.getPath(), URLUtil.guessFileName(url,null,null))
//                .build()
//                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
//                    @Override
//                    public void onStartOrResume() {
//
//                    }
//                })
//                .setOnPauseListener(new OnPauseListener() {
//                    @Override
//                    public void onPause() {
//
//                    }
//                })
//                .setOnCancelListener(new OnCancelListener() {
//                    @Override
//                    public void onCancel() {
//
//                    }
//                })
//                .setOnProgressListener(new OnProgressListener() {
//                    @Override
//                    public void onProgress(Progress progress) {
//                        long pr = progress.currentBytes*100/progress.totalBytes;
//                        pd.setMessage("Downloading .. "+pr+" %");
//                    }
//                })
//                .start(new OnDownloadListener() {
//                    @Override
//                    public void onDownloadComplete() {
//                        pd.dismiss();
//                        Toast.makeText(context.getApplicationContext(), "Downloaded successfully", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onError(Error error) {
//                        pd.dismiss();
//                        Toast.makeText(context.getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();
//                    }
//
//                });
//    }
public void downloadImg(View view){
//        Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.download);
    Bitmap b = getBitmapView(view);
//        Intent share = new Intent(Intent.ACTION_SEND);
//        share.setType("image/*");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
            b, new Date().getTime()+"", null);
//        Uri imageUri =  Uri.parse(path);
//        share.putExtra(Intent.EXTRA_STREAM, imageUri);
//        startActivity(Intent.createChooser(share, "Select"));
    if (path!=null){
        Toast.makeText(context.getApplicationContext(), "Downloaded successfully", Toast.LENGTH_SHORT).show();
    }else{
        Toast.makeText(context.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
    }
}
    public  void shareTemp(Bitmap bitmap){
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
            context.startActivity(Intent.createChooser(shareIntent, "Choose an app"));

        }
    }
    private  Bitmap getBitmapView(View view){
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable drawable = view.getBackground();
        if(drawable!=null){
            drawable.draw(canvas);
        }else{
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return bitmap;
    }
}
