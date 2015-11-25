package org.galebar.sogungyee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Code on 2015-07-23.
 */
public class WallListAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<Wallpaper> walls;

    private LayoutInflater inflater;

    public WallListAdapter(Context c, ArrayList<Wallpaper> walls){
        mContext = c;
        this.walls = walls;

        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount(){
        return walls.size();
    }

    public Object getItem(int position){
        return walls.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Wallpaper wall = walls.get(position);

        TextView txtTitle;
        ImageView img;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_img, null, false);
        } else {

        }

        txtTitle = (TextView)convertView.findViewById(R.id.txt_name);
        img = (ImageView)convertView.findViewById(R.id.img_background);

        txtTitle.setText(wall.getTitle());
        img.setImageBitmap(wall.getBitmap());

        return convertView;
    }
}
