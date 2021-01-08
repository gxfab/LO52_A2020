package com.example.lo52_project_v2;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.List;

public class MyArrayAdapter<T> extends ArrayAdapter<T> {

    HashMap<Integer, Boolean> itemClickable = new HashMap<Integer, Boolean>();
    Boolean isAllClickable=true;

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    public boolean isEnabled ( int position ) {
        if(!isAllClickable){
            return false;
        }
        else{
            return itemClickable.get(position);
        }
    }

    public void setItemClickable(int position,Boolean typeValue){
            itemClickable.put(position,typeValue);
    }

    public void setAllItemsClickable(Boolean clickable){
        this.isAllClickable = clickable;
    }

    public MyArrayAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);

        for(int j=0;j<objects.size();j++){
            itemClickable.put(j,true);
        }

    }
}
