package allrecipes.mpip.wbs.com.allrecipesmpip.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jovan on 3/14/2015.
 */
public class ShoppingCartItem implements Parcelable {

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_DONE = "done";

    private Long id;
    private String name;
    private String quantity;
    private int done;

    public ShoppingCartItem(Long _id, String _name, String _quantity, int _done){
        super();
        id = _id;
        name = _name;
        quantity = _quantity;
        done = _done;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    @Override
    public String toString() {
        String s = "id: " +id +"/n"+
                "name: "+name+
                "done: "+done;
        return s;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();

        bundle.putLong(KEY_ID, id);
        bundle.putString(KEY_NAME, name);
        bundle.putString(KEY_QUANTITY, quantity);
        bundle.putInt(KEY_DONE, done);

        dest.writeBundle(bundle);
    }
}
