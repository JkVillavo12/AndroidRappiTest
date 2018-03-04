package jkvillavo.co.com.rappiandroidtest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import jkvillavo.co.com.rappiandroidtest.R;

/**
 * Created by JkVillavo12Col on 2/03/18.
 */

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.CategoryItemViewHolder> {

    private List<String> listResults;
    private Context context;
    private View.OnClickListener listener;
    private IOnCategoryAction mListener;

    public interface IOnCategoryAction {

        void IOnCategoryAction_show(int position);

    }

    public AdapterCategories(Context context, List<String> listTitles) {

        if (context instanceof IOnCategoryAction) {
            mListener = (IOnCategoryAction) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IOnCategoryAction");
        }

        this.context = context;
        this.listResults = listTitles;
    }

    @NonNull
    @Override
    public CategoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new AdapterCategories.CategoryItemViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryItemViewHolder holder, final int position) {

        String title = listResults.get(position);
        holder.textViewTitulo.setText(title);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.IOnCategoryAction_show(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listResults.size();
    }

    class CategoryItemViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitulo;
        CardView cardView;

        public CategoryItemViewHolder(View view) {

            super(view);
            textViewTitulo = (TextView) view.findViewById(R.id.itemCategory_text);
            cardView = (CardView) view.findViewById(R.id.itemCategory_card);

        }

    }
}
