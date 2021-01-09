package com.dayetfracso.codep25.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "runners_stats")
public class RunnerStats {

	@PrimaryKey(autoGenerate = true)
	private long statsId;
	private long obstacle1;
	private long obstacle2;
	private long pitstop;
	private long sprint1;
	private long sprint2;
	@ForeignKey
			(entity = Race.class,
					parentColumns = "raceId",
					childColumns = "raceId"
			)
	public long raceId;
	@ForeignKey
			(entity = Runner.class,
					parentColumns = "runnerId",
					childColumns = "runnerId"
			)
	public long runnerId;

	public long getRunnerId() {
		return runnerId;
	}

	public void setRunnerId(long runnerId) {
		this.runnerId = runnerId;
	}

	public long getRaceId() { return raceId; }

	public void setRaceId(long raceId) {
		this.raceId = raceId;
	}

	public long getStatsId() {
		return statsId;
	}

	public void setStatsId(long statsId) {
		this.statsId = statsId;
	}

	public long getObstacle1() {
		return obstacle1;
	}

	public void setObstacle1(long obstacle1) {
		this.obstacle1 = obstacle1;
	}

	public long getObstacle2() {
		return obstacle2;
	}

	public void setObstacle2(long obstacle2) {
		this.obstacle2 = obstacle2;
	}

	public long getPitstop() {
		return pitstop;
	}

	public void setPitstop(long pitstop) {
		this.pitstop = pitstop;
	}

	public long getSprint1() {
		return sprint1;
	}

	public void setSprint1(long sprint1) {
		this.sprint1 = sprint1;
	}

	public long getSprint2() {
		return sprint2;
	}

	public void setSprint2(long sprint2) {
		this.sprint2 = sprint2;
	}

	public long getGlobalTime() {
		return getSprint1() + getObstacle1() + getPitstop() + getSprint2() + getObstacle2();
	}
}

