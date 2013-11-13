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
 * ��Activity����Ҫ��ʾ�����б�
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
					//�յ���Ϣ֪ͨ�󣬸���listView����Ϣ
					adapter = new MyAdapter(MainActivity.this, orderlist);
					list.setAdapter(adapter);
				}
			}

		};
		
		//���ö�ʱ����ÿ��30�������������һ�λ�ȡ������Ϣ������
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				orderlist = getOrderList();
				//����һ������Ϣ��֪ͨlistView������Ϣ
                handler.sendEmptyMessage(01234);
			}
		}, 0, 30000);

		//����listView�ļ�����
		list.setOnItemClickListener(new MyListener());
	}

	
	/**
	 * ������Activity�лر�Activityʱ����������������󣬸��¶�����Ϣ
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
	 * ��������������󣬻�ȡ������Ϣ�����ذ���������Ϣ��List
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
			//��Ҫ��д�����������ַ
			get = new HttpGet("The server IP");
			responce = httpClient.execute(get);
			entity = responce.getEntity();
			is = entity.getContent();
			br = new BufferedReader(new InputStreamReader(is));
			//���ﶩ����Ϣ�Ļ�ȡ��ʽ����û��ʵ�ʵ��������Ե��������Լ���ģ�⣬��ʱҪ����ʵ������޸�
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
	 * ListView�ļ������࣬����ʵ�ֵ��ʱ������һActivity����ʾ��������ϸ��Ϣ
	 */
	class MyListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Order order = orderlist.get(position);
			Bundle data = new Bundle();
			//��������Ϣ��ӵ�intent�У����䴫���µ�Activity
			data.putSerializable("order", order);
			Intent intent = new Intent(MainActivity.this, OrderContent.class);
			intent.putExtras(data);
			startActivity(intent);
		}

	}

}
