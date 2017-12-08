package tulisandigital.com.swipe;

/**
 * Created by PDAK-Dev on 11/19/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder>{
    Context context;
    ArrayList<HashMap<String, String>> list_data;
    public AdapterList(FragmentActivity ArticleFragment, ArrayList<HashMap<String, String>> list_data) {
        this.context = ArticleFragment;
        this.list_data = list_data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_article, null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context)
                .load(AndroidTransaction.URL_PATH_ASSETS+list_data.get(position).get("foto"))
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.fotonya);
        holder.titlenya.setText(list_data.get(position).get("title"));
        holder.idnya.setText(list_data.get(position).get("id"));

        holder.idnya.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String empId = list_data.get(position).get("id").toString();
                Intent intent = new Intent(context, DetailArticleActivity.class);
                intent.putExtra(AndroidTransaction.TAG_ID_ARTICLE,empId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idnya;
        TextView titlenya;
        ImageView fotonya;

        public ViewHolder(View itemView) {
            super(itemView);
            idnya = (TextView) itemView.findViewById(R.id.idnya);
            titlenya = (TextView) itemView.findViewById(R.id.titlenya);
            fotonya = (ImageView) itemView.findViewById(R.id.fotonya);
        }
    }
}