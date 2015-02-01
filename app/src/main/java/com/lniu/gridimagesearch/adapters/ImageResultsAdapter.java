package com.lniu.gridimagesearch.adapters;

import android.content.Context;
import android.graphics.Point;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lniu.gridimagesearch.R;
import com.lniu.gridimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lniu on 1/29/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

    public ImageResultsAdapter(Context context, List<ImageResult> objects) {
        super(context, R.layout.item_image_result, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageResult = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);


        int screenWidth = getScreenWidth();

        // We want two column images warp 90% of the screen.
        int imageWidth = (int)(0.95f * screenWidth / 2.0f);

        // Scale image height based on imageWidth.
        int imageHeight = (int)(imageResult.getTbHeight() *
                imageWidth / imageResult.getTbWidth() * 1.0f);

        ivImage.getLayoutParams().width = imageWidth;
        ivImage.getLayoutParams().height = imageHeight;
        ivImage.setImageResource(0);
        Picasso.with(getContext()).load(imageResult.getThumbUrl()).into(ivImage);

        // TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        // tvTitle.setText(Html.fromHtml(imageResult.getTitle()));
        return convertView;
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
