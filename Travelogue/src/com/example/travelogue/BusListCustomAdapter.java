package com.example.travelogue;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class BusListCustomAdapter extends BaseAdapter {

	public ArrayList<String> src;
	public ArrayList<String> destin;
	public ArrayList<String> via;
	public ArrayList<String> time;
	public Activity context;
	LayoutInflater inflater;
@SuppressWarnings("static-access")
public BusListCustomAdapter(Activity context, ArrayList<String> src,ArrayList<String> via, ArrayList<String> destin,ArrayList<String> time)
	{
		super();
		this.context=context;
		this.src=src;
		this.via=via;
		this.destin=destin;
		this.time=time;
		this.inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return src.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static class Holder
	{
		TextView source;
		TextView via;
		TextView destination;
		TextView time;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2)
	{
		// TODO Auto-generated method stub
		Holder hv;
		if(arg1==null)
		{
			hv=new Holder();
			arg1=inflater.inflate(R.layout.trans_single, null);
			hv.source=(TextView)arg1.findViewById(R.id.srctxt);
			hv.via=(TextView)arg1.findViewById(R.id.viaPlace);
			hv.destination=(TextView)arg1.findViewById(R.id.desttxt);
			hv.time=(TextView)arg1.findViewById(R.id.timetxt);
		    arg1.setTag(hv);
		}
		else
		{
			hv=(Holder)arg1.getTag();
		}
			hv.source.setText(src.get(arg0));
			hv.via.setText(via.get(arg0));
			hv.destination.setText(destin.get(arg0));
			hv.time.setText(time.get(arg0));
			return arg1;
	}

}
