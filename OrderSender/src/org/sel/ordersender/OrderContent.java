package org.sel.ordersender;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.sel.ordersender.bean.Order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author Sel
 * 用来显示订单详细信息的Activity
 */

public class OrderContent extends Activity {

	private TextView o_id, o_content, o_buyer, o_tel, o_address;
	private Button o_button,returnBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content);

		o_id = (TextView) findViewById(R.id.o_id);
		o_content = (TextView) findViewById(R.id.o_content);
		o_buyer = (TextView) findViewById(R.id.o_buyer);
		o_tel = (TextView) findViewById(R.id.o_tel);
		o_address = (TextView) findViewById(R.id.o_address);
		o_button = (Button) findViewById(R.id.o_button);
		returnBtn = (Button)findViewById(R.id.returnBtn);
		
		//获取由主Activity传过来的信息,并显示在对应的TextView上
		Intent intent = getIntent();
		Order order = (Order) intent.getSerializableExtra("order");
		o_id.setText(order.getOrder_id());
		o_content.setText(order.getContent());
		o_buyer.setText(order.getBuyer());
		o_tel.setText(order.getTel());
		o_address.setText(order.getAddress());

		//为已送按钮添加监听器
		o_button.setOnClickListener(new MyClickListener());
		
		//为返回按钮添加监听器，点击后，关闭当前Activity
		returnBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				OrderContent.this.finish();
			}
		});
	}

	/**
	 * 
	 * @author Sel
	 * 已送按钮的监听器，点击后向服务器发送请求，将该订单改为已送状态
	 */
	class MyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			try {
				
				//需要填写服务器地址
				HttpPost post = new HttpPost(
						"The server IP");
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				//在请求中添加包含订单ID的参数
				params.add(new BasicNameValuePair("id", o_id.getText()
						.toString()));
				post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpClient httpClient = new DefaultHttpClient();
				httpClient.execute(post);
				
				OrderContent.this.finish();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
