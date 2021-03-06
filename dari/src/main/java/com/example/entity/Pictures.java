package com.example.entity;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import javax.persistence.ManyToOne;

@Entity
public class Pictures {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String Image;
	@ManyToOne(cascade = CascadeType.ALL)
	private Announce announce;
	
	
	
	
	
	public Announce getAnnounce() {
		return announce;
	}


	public void setAnnounce(Announce announce) {
		this.announce = announce;
	}


	public Pictures(String image) {
		super();
		Image = image;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getImage() {
		return Image;
	}


	public void setImage(String image) {
		Image = image;
	}


	public Pictures(String image, Announce announce) {
		super();
		Image = image;
		this.announce = announce;
	}


	public Pictures() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
