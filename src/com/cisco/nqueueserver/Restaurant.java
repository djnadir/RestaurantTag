package com.cisco.nqueueserver;

import java.util.ArrayList;


class Restaurant{
	
	private String name;
	ArrayList<Client> queue;
	private String server_ip;
	private int server_port;
	
	
	
	Restaurant(String name, String server_ip){
		this.name = name;
		this.server_ip = server_ip;
		queue = new ArrayList<Client>();
	}
	
	public String getServerIp(){
		return server_ip;
	}
	public int getServerPort(){
		return server_port;
	}
	
	
	
}



class Client {
	
	int id;
	String phone_num;
	
	Client(int id, String phone_num){
		this.id=id;
		this.phone_num=phone_num;
	}
	
	
}