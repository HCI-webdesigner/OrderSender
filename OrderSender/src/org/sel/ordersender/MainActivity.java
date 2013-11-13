package org.sel.ordersender;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.sel.ordersender.adapter.MyAdapter;
import org.sel.ordersender.bean.Order;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 
 * @author Sel
 * 主Activity，主要显示订单列表
 */
public class MainActivity extends Activity {

	private List<Order> orderlist;
	private ListView list;
	private MyAdapter adapter;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		list = (ListView) findViewById(R.id.list);
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 01234) {
					//收到信息通知后，更新listView的信息
					adapter = new MyAdapter(MainActivity.this, orderlist);
					list.setAdapter(adapter);
				}
			}

		};
		
		//设置定时器，每隔30秒向服务器发送一次获取订单信息的请求
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				orderlist = getOrderList();
				//发送一条空信息，通知listView更新信息
                handler.sendEmptyMessage(01234);
			}
		}, 0, 30000);

		//设置listView的监听器
		list.setOnItemClickListener(new MyListener());
	}

	
	/**
	 * 由其他Activity切回本Activity时，向服务器发送请求，更新订单信息
	 */
	@Override
	protected void onResume() {
		orderlist = getOrderList();
		handler.sendEmptyMessage(01234);
		super.onResume();
	}



	@Override
	protected void onDestroy() {

		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * 向服务器发送请求，获取订单信息并返回包含订单信息的List
	 * @return
	 */
	private List<Order> getOrderList() {
		HttpClient httpClient;
		HttpGet get;
		HttpResponse responce;
		HttpEntity entity;
		InputStream is;
		BufferedReader br;
		String line;
		String[] order;
		List<Order> orderlist = new ArrayList<Order>();
		String order_id, content, buyer, tel, address;
		try {
			httpClient = new DefaultHttpClient();
			//需要填写服务器请求地址
			get = new HttpGet("The server IP");
			responce = httpClient.execute(get);
			entity = responce.getEntity();
			is = entity.getContent();
			br = new BufferedReader(new InputStreamReader(is));
			//这里订单信息的获取方式由于没有实际的需求，所以单纯是我自己的模拟，到时要根据实际情况修改
			while ((line = br.readLine()) != null) {
				order = line.split("\t");
				order_id = order[0];
				content = order[1];
				buyer = order[2];
				tel = order[3];
				address = order[4];
				orderlist
						.add(new Order(order_id, content, buyer, tel, address));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderlist;
	}

	
	/**
	 * 
	 * @author Sel
	 * ListView的监听器类，用来实现点击时，打开另一Activity来显示订单的详细信息
	 */
	class MyListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Order order = orderlist.get(position);
			Bundle data = new Bundle();
			//将订单信息添加到intent中，将其传到新的Activity
			data.putSerializable("order", order);
			Intent intent = new Intent(MainActivity.this, OrderContent.class);
			intent.putExtras(data);
			startActivity(intent);
		}

	}

}
