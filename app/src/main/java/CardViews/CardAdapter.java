package CardViews;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.style.TtsSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import com.example.asuna.cradview_text.*;

public class CardAdapter extends RecyclerView.Adapter <CardAdapter.ViewHolder>{

    private List<CardView> cardlist;

    public void CardAdapter(List<CardView> list){
        cardlist=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CardView cardView=cardlist.get(i);
        viewHolder.image.setImageResource(cardView.getImage());
        viewHolder.title.setText(cardView.getTitle());
    }

    @Override
    public int getItemCount() {
        return cardlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.cardview_image);
            title=(TextView)itemView.findViewById(R.id.cardview_title);
        }
        public ImageView image;
        public TextView title;
    }
}
