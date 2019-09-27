package com.sdimdev.nnhackaton.data.persistence.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class SyncInfo {
	@PrimaryKey(autoGenerate = true)
	private long id;
	private String status;
	private String title;
	private long time;
	private long startedTime;
	private int state;

	public SyncInfo() {
	}

	@Ignore
	public SyncInfo(String status, String title, long time, int state, long startedTime) {
		this.status = status;
		this.title = title;
		this.time = time;
		this.startedTime = startedTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getStartedTime() {
		return startedTime;
	}

	public void setStartedTime(long startedTime) {
		this.startedTime = startedTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
