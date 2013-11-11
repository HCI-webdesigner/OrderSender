package org.sel.ordersender;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.sel.ordersender.bean.Order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

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
		
		Intent intent = getIntent();
		Order order = (Order) intent.getSerializableExtra("order");
		o_id.setText(order.getOrder_id());
		o_content.setText(order.getContent());
		o_buyer.setText(order.getBuyer());
		o_tel.setText(order.getTel());
		o_address.setText(order.getAddress());

		o_button.setOnClickListener(new MyClickListener());
		returnBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				OrderContent.this.finish();
			}
		});
	}

	class MyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			try {
				
				//需要填写服务器地址
				HttpPost post = new HttpPost(
						"The server IP");
				/*
				 * HttpParams params = new BasicHttpParams();
				 * params.setParameter("id", o_id.getText().toString());
				 * post.setParams(params);
				 */
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id", o_id.getText()
						.toString()));
				post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpClient httpClient = new DefaultHttpClient();
				httpClient.execute(post);
				/*
				 * HttpResponse response =httpClient.execute(post);
				 * System.out.println(new BufferedReader(new
				 * InputStreamReader(response
				 * .getEntity().getContent())).readLine());
				 */
				OrderContent.this.finish();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
