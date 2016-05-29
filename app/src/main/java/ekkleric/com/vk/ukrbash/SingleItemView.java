package ekkleric.com.vk.ukrbash;

/**
 * Created by NICOLA on 22.05.2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleItemView extends Activity {

    String rank;
    String country;
    String population;
    String flag;

    ImageLoader imageLoader = new ImageLoader(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.singleitemview);

        Intent i = getIntent();

        rank = i.getStringExtra("rank");

        country = i.getStringExtra("country");

        population = i.getStringExtra("population");

        flag = i.getStringExtra("flag");


        TextView txtcountry = (TextView) findViewById(R.id.country);


        ImageView imgflag = (ImageView) findViewById(R.id.flag);


        txtcountry.setText(country);


        imageLoader.DisplayImage(flag, imgflag);
    }
}