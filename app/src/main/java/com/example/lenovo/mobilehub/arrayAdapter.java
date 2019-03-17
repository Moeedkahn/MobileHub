package com.example.lenovo.mobilehub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class arrayAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Item> modellist;
    private ArrayList<Item> ItemList;
    Bitmap bitmap;

    public  arrayAdapter(Context context, ArrayList<Item> list) {
        mContext = context;
        this.ItemList = list;
        inflater = LayoutInflater.from(mContext);
        this.modellist = new ArrayList<Item>();
        this.modellist.addAll(list);
    }
    static class ViewHolder{
        TextView Name;
        TextView Genre;
        ImageView Image;
    }
    @Override
    public int getCount() {
        return ItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return ItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Item currentShirt= ItemList.get(position);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.list, null, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.Name =
                    (TextView) convertView.findViewById(R.id.Name);
            viewHolder.Genre =
                    (TextView) convertView.findViewById(R.id.Genre);
            viewHolder.Image =
                    (ImageView) convertView.findViewById(R.id.Image);

            convertView.setTag(viewHolder);
        }

        TextView Name =
                ((ViewHolder) convertView.getTag()).Name;
        TextView Price=
                ((ViewHolder) convertView.getTag()).Genre;
        ImageView ContactImage =
                ((ViewHolder) convertView.getTag()).Image;

        Name.setText(currentShirt.name);
        Price.setText(currentShirt.genre);
        String imageUri=currentShirt.imageLink;

        Picasso.with(mContext).load(imageUri).into(ContactImage);
        return convertView;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ItemList.clear();
        if (charText.length() == 0) {
            ItemList.addAll(modellist);
        } else {
            for (Item latest : modellist) {
                if (latest.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    ItemList.add(latest);
                }
            }
        }
        notifyDataSetChanged();
    }

    private class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageWithURLTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
};
