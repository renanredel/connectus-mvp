package renan.augusto.connectusprototype;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;



    public SliderAdapter (Context context)
    {
        this.context = context;
}

    //Arrays

    public int[] slide_images = {/*
            R.drawable.eat_icon,
            R.drawable.code_icon,
            R.drawable.sleep_icon*/
    };

    public String[] slide_headings = {
            "",
            "CODE",
            "SLEEP"
    };

    public String[] slide_desc = {
            "",
            "SLIDE 2222222222",
            "SLIDE 3333333333"
    };

    public int[] slide_bg = {
            R.drawable.slidescreen_1,
            R.drawable.slidescreen_2,
            R.drawable.slidescreen_3
    };




    public int getCount(){
        return slide_headings.length;
    }

    public boolean isViewFromObject(View view, Object o){
        return view == (RelativeLayout) o;
    }

    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

      //  ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        //extView slideHeading = (TextView) view.findViewById(R.id.slide_title);
       // TextView slideDesc = (TextView) view.findViewById(R.id.slide_desc);
        ImageView bgSlideImage = (ImageView) view.findViewById(R.id.bgCarrousel);


       // slideImageView.setImageResource(slide_images [position]);
       // slideHeading.setText(slide_headings [position]);
       // slideDesc.setText(slide_desc [position]);
        bgSlideImage.setImageResource(slide_bg [position]);

        container.addView(view);




        return view;

    }

    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((RelativeLayout)object);
    }
}
