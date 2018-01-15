package net.jfabricationgames.bunkers_and_badasses.statistic;

import java.util.Vector;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class UserStatistic implements Comparable<UserStatistic> {
	
	private int userId;
	private User user;
	private int gameId;
	
	private static int[] sortKeys = new int[3];
	private static boolean[] desc = new boolean[3];
	
	private int[] values = new int[32];
	
	public enum Value {
		POSITION_AVG(0),
		GAMES_WON(1),
		GAMES_LOST(2),
		GAMES_WIN_RATE(3),
		GAMES_PLAYED(4),
		POINTS_TOTAL(5),
		POINTS_AVG(6),
		BATTLES_WON_TOTAL(7),
		BATTLES_WON_AVG(8),
		BATTLES_LOST_TOTAL(9),
		BATTLES_LOST_AVG(10),
		TROOPS_KILLED_TOTAL(11),
		TROOPS_KILLED_AVG(12),
		HEROS_USED_TOTAL(13),
		HEROS_USED_AVG(14),
		RESOURCES_USED_CREDITS_AVG(15),
		RESOURCES_USED_AMMO_AVG(16),
		RESOURCES_USED_ERIDIUM_AVG(17),
		SUPPORT_RECEIVED_TOTAL(18),
		SUPPORT_RECEIVED_AVG(19),
		SUPPORT_GIVEN_TOTAL(20),
		SUPPORT_GIVEN_AVG(21),
		SUPPORT_REJECTED_TOTAL(22),
		SUPPORT_REJECTED_AVG(23),
		COMMANDS_USED_RAID_AVG(24),
		COMMANDS_USED_RETREAT_AVG(25),
		COMMANDS_USED_MARCH_AVG(26),
		COMMANDS_USED_BUILD_AVG(27),
		COMMANDS_USED_RECRUIT_AVG(28),
		COMMANDS_USED_COLLECT_AVG(29),
		COMMANDS_USED_SUPPORT_AVG(30),
		COMMANDS_USED_DEFEND_AVG(31);
		
		private final int index;
		
		private Value(int index) {
			this.index = index;
		}
		
		public int getIndex() {
			return index;
		}
		
		@Override
		public String toString() {
			return name();
		}
	}
	
	public UserStatistic(int userId, User user, int gameId) {
		this.userId = userId;
		this.user = user;
		this.gameId = gameId;
	}
	
	@Override
	public int compareTo(UserStatistic stats) {
		if (values[sortKeys[0]] == stats.getValues()[sortKeys[0]]) {
			if (values[sortKeys[1]] == stats.getValues()[sortKeys[1]]) {
				if (values[sortKeys[2]] == stats.getValues()[sortKeys[2]]) {
					return 0;
				}
				else if (values[sortKeys[2]] < stats.getValues()[sortKeys[2]]) {
					if (desc[2]) {
						return 1;
					}
					else {
						return -1;
					}
				}
				else {
					if (desc[2]) {
						return -1;
					}
					else {
						return 1;
					}
				}
			}
			else if (values[sortKeys[1]] < stats.getValues()[sortKeys[1]]) {
				if (desc[1]) {
					return 1;
				}
				else {
					return -1;
				}
			}
			else {
				if (desc[1]) {
					return -1;
				}
				else {
					return 1;
				}
			}
		}
		else if (values[sortKeys[0]] < stats.getValues()[sortKeys[0]]) {
			if (desc[0]) {
				return 1;
			}
			else {
				return -1;
			}
		}
		else {
			if (desc[0]) {
				return -1;
			}
			else {
				return 1;
			}
		}
	}
	
	public void addStatistic(GameStatistic statistic) {
		values[Value.POSITION_AVG.getIndex()] += statistic.getPosition();
		if (statistic.getPosition() == 1) {
			values[Value.GAMES_WON.getIndex()]++;
		}
		else {
			values[Value.GAMES_LOST.getIndex()]++;
		}
		values[Value.GAMES_PLAYED.getIndex()]++;
		values[Value.POINTS_TOTAL.getIndex()] += statistic.getPoints();
		values[Value.BATTLES_WON_TOTAL.getIndex()] += statistic.getBattles_won();
		values[Value.BATTLES_LOST_TOTAL.getIndex()] += statistic.getBattles_lost();
		values[Value.TROOPS_KILLED_TOTAL.getIndex()] += statistic.getTroops_killed_normal() + 2* statistic.getTroops_killed_badass() + 
				statistic.getTroops_killed_neutral();
		values[Value.HEROS_USED_TOTAL.getIndex()] += statistic.getHeros_used_battle() + statistic.getHeros_used_effect();
		values[Value.RESOURCES_USED_CREDITS_AVG.getIndex()] += statistic.getUsed_credits();
		values[Value.RESOURCES_USED_AMMO_AVG.getIndex()] += statistic.getUsed_ammo();
		values[Value.RESOURCES_USED_ERIDIUM_AVG.getIndex()] += statistic.getUsed_eridium();
		values[Value.SUPPORT_RECEIVED_TOTAL.getIndex()] += statistic.getSupport_received_self() + statistic.getSupport_received_other();
		values[Value.SUPPORT_GIVEN_TOTAL.getIndex()] += statistic.getSupport_given_self() + statistic.getSupport_given_other();
		values[Value.SUPPORT_REJECTED_TOTAL.getIndex()] += statistic.getSupport_rejected();
		values[Value.COMMANDS_USED_RAID_AVG.getIndex()] += statistic.getCommands_raid();
		values[Value.COMMANDS_USED_RETREAT_AVG.getIndex()] += statistic.getCommands_retreat();
		values[Value.COMMANDS_USED_MARCH_AVG.getIndex()] += statistic.getCommands_march();
		values[Value.COMMANDS_USED_BUILD_AVG.getIndex()] += statistic.getCommands_build();
		values[Value.COMMANDS_USED_RECRUIT_AVG.getIndex()] += statistic.getCommands_recruit();
		values[Value.COMMANDS_USED_COLLECT_AVG.getIndex()] += statistic.getCommands_resources();
		values[Value.COMMANDS_USED_SUPPORT_AVG.getIndex()] += statistic.getCommands_support();
		values[Value.COMMANDS_USED_DEFEND_AVG.getIndex()] += statistic.getCommands_defense();
	}
	public void calculateAverages() {
		values[Value.POSITION_AVG.getIndex()] /= values[Value.GAMES_PLAYED.getIndex()];
		values[Value.GAMES_WIN_RATE.getIndex()] = values[Value.GAMES_WON.getIndex()] / values[Value.GAMES_PLAYED.getIndex()];
		values[Value.POINTS_AVG.getIndex()] = values[Value.POINTS_TOTAL.getIndex()] / values[Value.GAMES_PLAYED.getIndex()];
		values[Value.BATTLES_WON_AVG.getIndex()] = values[Value.BATTLES_WON_TOTAL.getIndex()] / values[Value.GAMES_PLAYED.getIndex()];
		values[Value.BATTLES_LOST_AVG.getIndex()] = values[Value.BATTLES_LOST_TOTAL.getIndex()] / values[Value.GAMES_PLAYED.getIndex()];
		values[Value.TROOPS_KILLED_AVG.getIndex()] = values[Value.TROOPS_KILLED_TOTAL.getIndex()] / values[Value.GAMES_PLAYED.getIndex()];
		values[Value.HEROS_USED_AVG.getIndex()] = values[Value.HEROS_USED_TOTAL.getIndex()] / values[Value.GAMES_PLAYED.getIndex()];
		values[Value.RESOURCES_USED_CREDITS_AVG.getIndex()] /= values[Value.GAMES_PLAYED.getIndex()];
		values[Value.RESOURCES_USED_AMMO_AVG.getIndex()] /= values[Value.GAMES_PLAYED.getIndex()];
		values[Value.RESOURCES_USED_ERIDIUM_AVG.getIndex()] /= values[Value.GAMES_PLAYED.getIndex()];
		values[Value.SUPPORT_RECEIVED_AVG.getIndex()] = values[Value.SUPPORT_RECEIVED_TOTAL.getIndex()] / values[Value.GAMES_PLAYED.getIndex()];
		values[Value.SUPPORT_GIVEN_AVG.getIndex()] = values[Value.SUPPORT_GIVEN_TOTAL.getIndex()] / values[Value.GAMES_PLAYED.getIndex()];
		values[Value.SUPPORT_REJECTED_AVG.getIndex()] = values[Value.SUPPORT_REJECTED_TOTAL.getIndex()] / values[Value.GAMES_PLAYED.getIndex()];
		values[Value.COMMANDS_USED_RAID_AVG.getIndex()] /= values[Value.GAMES_PLAYED.getIndex()];
		values[Value.COMMANDS_USED_RETREAT_AVG.getIndex()] /= values[Value.GAMES_PLAYED.getIndex()];
		values[Value.COMMANDS_USED_MARCH_AVG.getIndex()] /= values[Value.GAMES_PLAYED.getIndex()];
		values[Value.COMMANDS_USED_BUILD_AVG.getIndex()] /= values[Value.GAMES_PLAYED.getIndex()];
		values[Value.COMMANDS_USED_RECRUIT_AVG.getIndex()] /= values[Value.GAMES_PLAYED.getIndex()];
		values[Value.COMMANDS_USED_COLLECT_AVG.getIndex()] /= values[Value.GAMES_PLAYED.getIndex()];
		values[Value.COMMANDS_USED_SUPPORT_AVG.getIndex()] /= values[Value.GAMES_PLAYED.getIndex()];
		values[Value.COMMANDS_USED_DEFEND_AVG.getIndex()] /= values[Value.GAMES_PLAYED.getIndex()];
	}
	
	public int[] getValues() {
		return values;
	}
	public Vector<String> getColumnVector() {
		Vector<String> cols = new Vector<String>(values.length+1);
		cols.add("Username");
		cols.add("Game-ID");
		for (Value value : Value.values()) {
			cols.addElement(value.name());
		}
		return cols;
	}
	public static Vector<Value> getValueSelections() {
		Vector<Value> cols = new Vector<Value>();
		for (Value value : Value.values()) {
			cols.addElement(value);
		}
		return cols;
	}
	public Vector<Object> getDataVector() {
		Vector<Object> vec = new Vector<Object>(values.length+1);
		vec.add(user.getUsername());
		vec.add(gameId);
		for (int i : values) {
			vec.add(i);
		}
		return vec;
	}
	
	public static void setSortKey(int key, int order, boolean desc) {
		sortKeys[order-1] = key;
		UserStatistic.desc[order-1] = desc;
	}
	
	public int getUserId() {
		return userId;
	}
	public User getUser() {
		return user;
	}
	public int getGameId() {
		return gameId;
	}
}