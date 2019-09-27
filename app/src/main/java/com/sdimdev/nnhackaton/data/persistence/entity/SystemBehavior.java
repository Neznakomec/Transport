package com.sdimdev.nnhackaton.data.persistence.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class SystemBehavior {
	@PrimaryKey(autoGenerate = true)
	private long id;
	private long time;
	private String tag;
	private String message;

	@Ignore
	public SystemBehavior(long time, String tag, String message) {
		this.time = time;
		this.tag = tag;
		this.message = message;
	}

	public SystemBehavior() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTag() {

		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
