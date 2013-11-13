package org.sel.ordersender.adapter;

import java.util.List;

import org.sel.ordersender.R;
import org.sel.ordersender.bean.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * @author Sel
 * ��ʾ������Ϣ��ListView��������
 */
public class MyAdapter extends BaseAdapter{
	
	//��������ListItem���ֵĲ��ּ�����
	private LayoutInflater inflater;
	//����������Ϣ��list
	private List<Order> list;
	
	public MyAdapter(Context context,List<Order> orderlist){
		this.inflater = LayoutInflater.from(context);
		this.list = orderlist;
	}

	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		  //����ListItem����
	      convertView = inflater.inflate(R.layout.list, null);
	      TextView id = (TextView)convertView.findViewById(R.id.order_id);
	      id.setText(list.get(position).getOrder_id());
	      TextView buyer = (TextView)convertView.findViewById(R.id.buyer);
	      buyer.setText(list.get(position).getBuyer());
	      TextView address = (TextView)convertView.findViewById(R.id.address);
	      address.setText(list.get(position).getAddress());
	      
	      return convertView;
	}
           
	
}
