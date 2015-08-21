package allrecipes.mpip.wbs.com.allrecipesmpip.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import allrecipes.mpip.wbs.com.allrecipesmpip.R;

/**
 * Created by Mac on 7/1/15.
 */
public class ShoppingCartViewHolder extends RecyclerView.ViewHolder{

    public CheckBox cbDone;
    public TextView tvName;
    public TextView tvQuantity;
    public ImageView btnDelete;

    public ShoppingCartViewHolder(View view){
        super(view);
        cbDone = (CheckBox) view.findViewById(R.id.cb_sc_item);
        tvName = (TextView) view.findViewById(R.id.tv_sc_item_name);
        tvQuantity = (TextView) view.findViewById(R.id.tv_sc_item_quantity);
        btnDelete = (ImageView) view.findViewById(R.id.btn_delete_sc_item);
    }

}
