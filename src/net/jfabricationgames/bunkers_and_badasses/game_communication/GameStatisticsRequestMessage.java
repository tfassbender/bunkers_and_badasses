package net.jfabricationgames.bunkers_and_badasses.game_communication;

import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.statistic.GameStatistic;

public class GameStatisticsRequestMessage extends BunkersAndBadassesMessage {
	
	private static final long serialVersionUID = 4638417323942266142L;
	
	private List<GameStatistic> statistics;
	
	public GameStatisticsRequestMessage() {
		
	}
	
	public List<GameStatistic> getStatistics() {
		return statistics;
	}
	public void setStatistics(List<GameStatistic> statistics) {
		this.statistics = statistics;
	}
}