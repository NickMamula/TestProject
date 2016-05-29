package ekkleric.com.vk.ukrbash;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by NICOLA on 23.05.2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {


    public LayoutInflater inflater;
    ArrayList<Worldpopulation> worldpopulations;
    ImageLoader imageLoader;
    Context context;

    public CustomAdapter(Context context, ArrayList<Worldpopulation> worldpopulations) {
        super();
        this.context = context;
        this.worldpopulations = worldpopulations;
        imageLoader = new ImageLoader(context);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Worldpopulation world = worldpopulations.get(position);
        holder.country.setText(world.population);
        imageLoader.DisplayImage(world.flag, holder.flag);
    }

    @Override
    public int getItemCount() {
        return worldpopulations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;

        TextView country;
        ImageView flag;


        public ViewHolder(View v) {
            super(v);
            itemView.setOnClickListener(this);
            cv = (CardView) itemView.findViewById(R.id.cv);
            country = (TextView) v.findViewById(R.id.country);
            flag = (ImageView) v.findViewById(R.id.flag);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "clicked=" + getPosition(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, SingleItemView.class);
                    intent.putExtra("country", worldpopulations.get(getPosition()).population);
                    intent.putExtra("flag", worldpopulations.get(getPosition()).flag);
                    context.startActivity(intent);


                }
            });
        }

        @Override
        public void onClick(View v) {
        }
    }
}
