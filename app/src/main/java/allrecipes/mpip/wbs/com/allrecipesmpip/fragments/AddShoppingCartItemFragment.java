package allrecipes.mpip.wbs.com.allrecipesmpip.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import allrecipes.mpip.wbs.com.allrecipesmpip.R;
import allrecipes.mpip.wbs.com.allrecipesmpip.contentProvider.ShoppingCartContentProvider;


/**
 * Created by Mac on 4/24/15.
 */
public class AddShoppingCartItemFragment extends Fragment {

    //Promenlivi za da go zemime ona shto ke se vnesuva
    EditText eTxtName;
    EditText eTxtQuantity;
    Button btnAdd;
    Button cancelBtn;
    long id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.add_shopping_cart_item, container, false);

        eTxtName = (EditText)rootView.findViewById(R.id.edit_txt_name);
        eTxtQuantity = (EditText)rootView.findViewById(R.id.edit_txt_quantity);
        btnAdd = (Button) rootView.findViewById(R.id.btn_add_item);
        cancelBtn = (Button) rootView.findViewById(R.id.btn_cancel_item);

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            id = bundle.getLong("id");
        }

        //Ako id != 0 znaci imame edit
        if (id != 0 && id > 0){
            ContentResolver cr = getActivity().getContentResolver();
            String[] projection = {ShoppingCartContentProvider.DBOpenHelper.COLUMN_ID, ShoppingCartContentProvider.DBOpenHelper.COLUMN_NAME,
                                    ShoppingCartContentProvider.DBOpenHelper.COLUMN_QUANTITY};

            Uri uri = ContentUris.withAppendedId(ShoppingCartContentProvider.CONTENT_URI, id);

            Cursor cursor = cr.query(uri, projection, null, null, null);
            cursor.moveToFirst();

            eTxtName.setText(cursor.getString(1));
            eTxtQuantity.setText(cursor.getString(2));
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver cr = getActivity().getContentResolver();
                ContentValues values = new ContentValues();

                if(!eTxtName.getText().toString().trim().equals("")) {
                    //gi stavame vo values
                    values.put(ShoppingCartContentProvider.DBOpenHelper.COLUMN_NAME, eTxtName.getText().toString());
                    values.put(ShoppingCartContentProvider.DBOpenHelper.COLUMN_QUANTITY, eTxtQuantity.getText().toString());
                    values.put(ShoppingCartContentProvider.DBOpenHelper.COLUMN_DONE, 0);

                    //Ako ni e nov item
                    if (id == 0) {
                        cr.insert(ShoppingCartContentProvider.CONTENT_URI, values);
                    } else {
                        Uri uri = ContentUris.withAppendedId(ShoppingCartContentProvider.CONTENT_URI, id);
                        cr.update(uri, values, null, null);
                    }
                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        return rootView;
    }

}
