package sugoi.android.amazfun;

/**
 * Created by Shade on 5/9/2016.
 */

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private String[] details = {"Rs.200","Rs.200","Rs.300","Rs.300","Rs.300","Rs.300","Rs.500"
    };

    private int[] images = {R.drawable.mobile,
            R.drawable.tablet,
            R.drawable.laptop,
            R.drawable.desktop,
            R.drawable.network,
            R.drawable.printer,
            R.drawable.family,
            R.drawable.network,
            R.drawable.printer,



    };

    private String[] names={"Mobiles","Tablets","Laptops","Desktops","Networking","Printers","Parental Control"
    };

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;

        public TextView itemDetail;
        public TextView itemName;
        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);

            itemDetail =(TextView)itemView.findViewById(R.id.item_detail);
            itemName=(TextView) itemView.findViewById(R.id.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.itemDetail.setText(details[i]);
        viewHolder.itemImage.setImageResource(images[i]);
        viewHolder.itemName.setText(names[i]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }
}