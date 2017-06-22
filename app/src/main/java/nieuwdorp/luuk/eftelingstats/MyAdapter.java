package nieuwdorp.luuk.eftelingstats;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Luuk Nieuwdorp on 21-6-2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<EftelingItem> listItems;
    private Context mContext;

    public MyAdapter(List<EftelingItem> listItems, Context mContext) {
        this.listItems = listItems;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final EftelingItem item = listItems.get(position);

        holder.heading.setText(item.getHeading());
        holder.image.setImageResource(item.getPathToImage());
        holder.waitingTime.setText(String.valueOf(item.getWaitingTime()) + " min");

        final int count = item.getCount();

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setCount(count + 1);
                notifyDataSetChanged();
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getCount() <= 0){
                    return;
                }
                item.setCount(count - 1);
                notifyDataSetChanged();
            }
        });

        holder.count.setText(String.valueOf(item.getCount()));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView heading;
        private ImageView image;
        private TextView count;
        private ImageButton btnAdd;
        private ImageButton btnRemove;
        private TextView waitingTime;

        public ViewHolder(View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.tvName);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            count = (TextView) itemView.findViewById(R.id.tvCount);
            btnAdd = (ImageButton) itemView.findViewById(R.id.btnAdd);
            btnRemove = (ImageButton) itemView.findViewById(R.id.btnRemove);
            waitingTime = (TextView) itemView.findViewById(R.id.tvWaitingTime);
        }
    }
}
