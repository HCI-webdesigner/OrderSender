package org.sel.ordersender.bean;

import java.io.Serializable;


/**
 * 
 * @author Sel
 * 订单类，包含订单的详细信息
 */
public class Order implements Serializable{

	private String order_id;
	private String content;
	private String buyer;
	private String tel;
	private String address;
	
	public Order(String order_id, String content, String buyer, String tel,
			String address) {
		this.order_id = order_id;
		this.content = content;
		this.buyer = buyer;
		this.tel = tel;
		this.address = address;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
