package com.salah.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.salah.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    int images[] = {
            R.drawable.dua,
            R.drawable.ruku,
            R.drawable.sujud,
            R.drawable.hands
    };
    int titles[] = {
            R.string.os_s1_title,
            R.string.os_s2_title,
            R.string.os_s3_title,
            R.string.os_s4_title
    };
    int descs[] = {
            R.string.os_s1_desc,
            R.string.os_s2_desc,
            R.string.os_s3_desc,
            R.string.os_s4_desc
    };

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slides_layout, container, false);

        ImageView image = view.findViewById(R.id.sl_image);
        TextView tile = view.findViewById(R.id.sl_title);
        TextView desc = view.findViewById(R.id.sl_desc);

        image.setImageResource(images[position]);
        tile.setText(titles[position]);
        desc.setText(descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
