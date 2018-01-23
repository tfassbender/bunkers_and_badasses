package net.jfabricationgames.bunkers_and_badasses.statistic;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class GameStatistic implements Serializable, Comparable<GameStatistic> {
	
	private static final long serialVersionUID = 758529797268373840L;

	public GameStatistic(User user) {
		this.user = user;
	}
	
	public GameStatistic(ResultSet result) throws SQLException {
		user_id = result.getInt(1);
		game_id = result.getInt(2);
		position = result.getInt(3);
		players = result.getInt(4);
		points = result.getInt(5);
		points_fight = result.getInt(6);
		points_fields = result.getInt(7);
		points_regions = result.getInt(8);
		points_goals = result.getInt(9);
		points_bonuses = result.getInt(10);
		points_skills = result.getInt(11);
		troops_killed_normal = result.getInt(12);
		troops_killed_badass = result.getInt(13);
		troops_killed_neutral = result.getInt(14);
		troops_controlled_end = result.getInt(15);
		troops_controlled_max = result.getInt(16);
		fields_end = result.getInt(17);
		fields_max = result.getInt(18);
		regions_end = result.getInt(19);
		regions_value_end = result.getInt(20);
		regions_max = result.getInt(21);
		regions_value_max = result.getInt(22);
		battles_won = result.getInt(23);
		battles_lost = result.getInt(24);
		heros_used_battle = result.getInt(25);
		heros_used_effect = result.getInt(26);
		used_credits = result.getInt(27);
		used_ammo = result.getInt(28);
		used_eridium = result.getInt(29);
		buildings_created = result.getInt(30);
		buildings_upgraded = result.getInt(31);
		buildings_destroyed = result.getInt(32);
		support_given_self = result.getInt(33);
		support_received_self = result.getInt(34);
		support_given_other = result.getInt(35);
		support_received_other = result.getInt(36);
		support_rejected = result.getInt(37);
		commands_raid = result.getInt(38);
		commands_retreat = result.getInt(39);
		commands_march = result.getInt(40);
		commands_build = result.getInt(41);
		commands_recruit = result.getInt(42);
		commands_resources = result.getInt(43);
		commands_support = result.getInt(44);
		commands_defense = result.getInt(45);
		user = new User(result.getString(46));
		map_id = result.getInt(47);
		map_name = result.getString(48);
	}
	
	private User user;
	//user id and game id are checked and added by the server
	private int user_id;
	private int game_id;
	private int map_id;
	private String map_name;
	
	private int position;//the players position at the end of the game
	private int players;//the number of players in the game
	
	//the player's points during the game
	private int points;
	private int points_fight;
	private int points_fields;
	private int points_regions;
	private int points_goals;
	private int points_bonuses;
	private int points_skills;
	
	//the total number of troops the player has killed (only enemy troops killed during attacks of the player); implemented in FightManager
	private int troops_killed_normal;
	private int troops_killed_badass;
	private int troops_killed_neutral;
	
	//the number of troops the player controlled at the end or during the game (badasses are counted as two)
	private int troops_controlled_end;
	private int troops_controlled_max;
	
	//the number of fields and regions the player has (or had) and the value (points) of the regions
	private int fields_end;
	private int fields_max;
	private int regions_end;
	private int regions_value_end;
	private int regions_max;
	private int regions_value_max;
	
	//the number of battles that the player on or lost
	private int battles_won;
	private int battles_lost;
	
	//the number of heros used (in battle or as effect); implemented in heros and FightManager
	private int heros_used_battle;
	private int heros_used_effect;
	
	//the resources a player used during the game; implemented in UserPlanManager and UserResource
	private int used_credits;
	private int used_ammo;
	private int used_eridium;
	
	//the buildings the player built, upgraded or destroyed; implemented in command execution panel
	private int buildings_created;
	private int buildings_upgraded;
	private int buildings_destroyed;
	
	//the support the player has given or received (from his own or enemy troops); implemented in FightManager
	private int support_given_self;
	private int support_received_self;
	private int support_given_other;
	private int support_received_other;
	private int support_rejected;
	
	//the commands the player used (added to the field in planing phase; not only executed ones); implemented in UserPlanManager
	private int commands_raid;
	private int commands_retreat;
	private int commands_march;
	private int commands_build;
	private int commands_recruit;
	private int commands_resources;
	private int commands_support;
	private int commands_defense;

	@Override
	public int compareTo(GameStatistic stats) {
		if (game_id != stats.getGame_id()) {
			return stats.getGame_id() - game_id;
		}
		else {
			return stats.getPosition() - position;
		}
	}
	
	public static Vector<String> getColumnVector() {
		Vector<String> cols = new Vector<String>();
		cols.add("Game-ID");
		cols.add("User");
		cols.add("Map");
		cols.add("Position");
		cols.add("Players");
		cols.add("Points");
		cols.add("Points_fight");
		cols.add("Points_fields");
		cols.add("Points_regions");
		cols.add("Points_goals");
		cols.add("Points_bonuses");
		cols.add("Points_skills");
		cols.add("Troops_killed_normal");
		cols.add("Troops_killed_badass");
		cols.add("Troops_killed_neutral");
		cols.add("Troops_controlled_end");
		cols.add("Troops_controlled_max");
		cols.add("Fields_end");
		cols.add("Fields_max");
		cols.add("Regions_end");
		cols.add("Regions_value_end");
		cols.add("Regions_max");
		cols.add("Regions_value_max");
		cols.add("Battles_won");
		cols.add("Battles_lost");
		cols.add("Heros_used_battle");
		cols.add("Heros_used_effect");
		cols.add("Used_credits");
		cols.add("Used_ammo");
		cols.add("Used_eridium");
		cols.add("Buildings_created");
		cols.add("Buildings_upgraded");
		cols.add("Buildings_destroyed");
		cols.add("Support_given_self");
		cols.add("Support_received_self");
		cols.add("Support_given_other");
		cols.add("Support_received_other");
		cols.add("Support_rejected");
		cols.add("Commands_raid");
		cols.add("Commands_retreat");
		cols.add("Commands_march");
		cols.add("Commands_build");
		cols.add("Commands_recruit");
		cols.add("Commands_resources");
		cols.add("Commands_support");
		cols.add("Commands_defense");
		return cols;
	}
	public Vector<Object> getDataVector() {
		Vector<Object> data = new Vector<Object>();
		data.add(game_id);
		data.add(user.getUsername());
		data.add(map_name);
		data.add(position);
		data.add(players);
		data.add(points);
		data.add(points_fight);
		data.add(points_fields);
		data.add(points_regions);
		data.add(points_goals);
		data.add(points_bonuses);
		data.add(points_skills);
		data.add(troops_killed_normal);
		data.add(troops_killed_badass);
		data.add(troops_killed_neutral);
		data.add(troops_controlled_end);
		data.add(troops_controlled_max);
		data.add(fields_end);
		data.add(fields_max);
		data.add(regions_end);
		data.add(regions_value_end);
		data.add(regions_max);
		data.add(regions_value_max);
		data.add(battles_won);
		data.add(battles_lost);
		data.add(heros_used_battle);
		data.add(heros_used_effect);
		data.add(used_credits);
		data.add(used_ammo);
		data.add(used_eridium);
		data.add(buildings_created);
		data.add(buildings_upgraded);
		data.add(buildings_destroyed);
		data.add(support_given_self);
		data.add(support_received_self);
		data.add(support_given_other);
		data.add(support_received_other);
		data.add(support_rejected);
		data.add(commands_raid);
		data.add(commands_retreat);
		data.add(commands_march);
		data.add(commands_build);
		data.add(commands_recruit);
		data.add(commands_resources);
		data.add(commands_support);
		data.add(commands_defense);
		return data;
	}
	
	public void prepareStatement(PreparedStatement statement) throws SQLException {
		statement.setInt(1, user_id);
		statement.setInt(2, game_id);
		statement.setInt(3, position);
		statement.setInt(4, players);
		statement.setInt(5, points);
		statement.setInt(6, points_fight);
		statement.setInt(7, points_fields);
		statement.setInt(8, points_regions);
		statement.setInt(9, points_goals);
		statement.setInt(10, points_bonuses);
		statement.setInt(11, points_skills);
		statement.setInt(12, troops_killed_normal);
		statement.setInt(13, troops_killed_badass);
		statement.setInt(14, troops_killed_neutral);
		statement.setInt(15, troops_controlled_end);
		statement.setInt(16, troops_controlled_max);
		statement.setInt(17, fields_end);
		statement.setInt(18, fields_max);
		statement.setInt(19, regions_end);
		statement.setInt(20, regions_value_end);
		statement.setInt(21, regions_max);
		statement.setInt(22, regions_value_max);
		statement.setInt(23, battles_won);
		statement.setInt(24, battles_lost);
		statement.setInt(25, heros_used_battle);
		statement.setInt(26, heros_used_effect);
		statement.setInt(27, used_credits);
		statement.setInt(28, used_ammo);
		statement.setInt(29, used_eridium);
		statement.setInt(30, buildings_created);
		statement.setInt(31, buildings_upgraded);
		statement.setInt(32, buildings_destroyed);
		statement.setInt(33, support_given_self);
		statement.setInt(34, support_received_self);
		statement.setInt(35, support_given_other);
		statement.setInt(36, support_received_other);
		statement.setInt(37, support_rejected);
		statement.setInt(38, commands_raid);
		statement.setInt(39, commands_retreat);
		statement.setInt(40, commands_march);
		statement.setInt(41, commands_build);
		statement.setInt(42, commands_recruit);
		statement.setInt(43, commands_resources);
		statement.setInt(44, commands_support);
		statement.setInt(45, commands_defense);
	}
	
	public int[] asArray() {
		int[] data = new int[46];
		data[0] = map_id;
		data[1] = user_id;
		data[2] = game_id;
		data[3] = position;
		data[4] = players;
		data[5] = points;
		data[6] = points_fight;
		data[7] = points_fields;
		data[8] = points_regions;
		data[9] = points_goals;
		data[10] = points_bonuses;
		data[11] = points_skills;
		data[12] = troops_killed_normal;
		data[13] = troops_killed_badass;
		data[14] = troops_killed_neutral;
		data[15] = troops_controlled_end;
		data[16] = troops_controlled_max;
		data[17] = fields_end;
		data[18] = fields_max;
		data[19] = regions_end;
		data[20] = regions_value_end;
		data[21] = regions_max;
		data[22] = regions_value_max;
		data[23] = battles_won;
		data[24] = battles_lost;
		data[25] = heros_used_battle;
		data[26] = heros_used_effect;
		data[27] = used_credits;
		data[28] = used_ammo;
		data[29] = used_eridium;
		data[30] = buildings_created;
		data[31] = buildings_upgraded;
		data[32] = buildings_destroyed;
		data[33] = support_given_self;
		data[34] = support_received_self;
		data[35] = support_given_other;
		data[36] = support_received_other;
		data[37] = support_rejected;
		data[38] = commands_raid;
		data[39] = commands_retreat;
		data[40] = commands_march;
		data[41] = commands_build;
		data[42] = commands_recruit;
		data[43] = commands_resources;
		data[44] = commands_support;
		data[45] = commands_defense;
		return data;
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public int getGame_id() {
		return game_id;
	}
	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}
	
	public int getMap_id() {
		return map_id;
	}
	public void setMap_id(int map_id) {
		this.map_id = map_id;
	}
	
	public String getMap_name() {
		return map_name;
	}
	public void setMap_name(String map_name) {
		this.map_name = map_name;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPlayers() {
		return players;
	}
	public void setPlayers(int players) {
		this.players = players;
	}
	
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getPoints_fight() {
		return points_fight;
	}
	public void setPoints_fight(int points_fight) {
		this.points_fight = points_fight;
	}
	
	public int getPoints_fields() {
		return points_fields;
	}
	public void setPoints_fields(int points_fields) {
		this.points_fields = points_fields;
	}
	
	public int getPoints_regions() {
		return points_regions;
	}
	public void setPoints_regions(int points_regions) {
		this.points_regions = points_regions;
	}
	
	public int getPoints_goals() {
		return points_goals;
	}
	public void setPoints_goals(int points_goals) {
		this.points_goals = points_goals;
	}
	
	public int getPoints_bonuses() {
		return points_bonuses;
	}
	public void setPoints_bonuses(int points_bonuses) {
		this.points_bonuses = points_bonuses;
	}
	
	public int getPoints_skills() {
		return points_skills;
	}
	public void setPoints_skills(int points_skills) {
		this.points_skills = points_skills;
	}
	
	public int getTroops_killed_normal() {
		return troops_killed_normal;
	}
	public void setTroops_killed_normal(int troops_killed_normal) {
		this.troops_killed_normal = troops_killed_normal;
	}
	
	public int getTroops_killed_badass() {
		return troops_killed_badass;
	}
	public void setTroops_killed_badass(int troops_killed_badass) {
		this.troops_killed_badass = troops_killed_badass;
	}
	
	public int getTroops_killed_neutral() {
		return troops_killed_neutral;
	}
	public void setTroops_killed_neutral(int troops_killed_neutral) {
		this.troops_killed_neutral = troops_killed_neutral;
	}
	
	public int getTroops_controlled_end() {
		return troops_controlled_end;
	}
	public void setTroops_controlled_end(int troops_controlled_end) {
		this.troops_controlled_end = troops_controlled_end;
	}
	
	public int getTroops_controlled_max() {
		return troops_controlled_max;
	}
	public void setTroops_controlled_max(int troops_controlled_max) {
		this.troops_controlled_max = troops_controlled_max;
	}
	
	public int getFields_end() {
		return fields_end;
	}
	public void setFields_end(int fields_end) {
		this.fields_end = fields_end;
	}
	
	public int getFields_max() {
		return fields_max;
	}
	public void setFields_max(int fields_max) {
		this.fields_max = fields_max;
	}
	
	public int getRegions_end() {
		return regions_end;
	}
	public void setRegions_end(int regions_end) {
		this.regions_end = regions_end;
	}
	
	public int getRegions_value_end() {
		return regions_value_end;
	}
	public void setRegions_value_end(int regions_value_end) {
		this.regions_value_end = regions_value_end;
	}
	
	public int getRegions_max() {
		return regions_max;
	}
	public void setRegions_max(int regions_max) {
		this.regions_max = regions_max;
	}
	
	public int getRegions_value_max() {
		return regions_value_max;
	}
	public void setRegions_value_max(int regions_value_max) {
		this.regions_value_max = regions_value_max;
	}
	
	public int getBattles_won() {
		return battles_won;
	}
	public void setBattles_won(int battles_won) {
		this.battles_won = battles_won;
	}
	
	public int getBattles_lost() {
		return battles_lost;
	}
	public void setBattles_lost(int battles_lost) {
		this.battles_lost = battles_lost;
	}
	
	public int getHeros_used_battle() {
		return heros_used_battle;
	}
	public void setHeros_used_battle(int heros_used_battle) {
		this.heros_used_battle = heros_used_battle;
	}
	
	public int getHeros_used_effect() {
		return heros_used_effect;
	}
	public void setHeros_used_effect(int heros_used_effect) {
		this.heros_used_effect = heros_used_effect;
	}
	
	public int getUsed_credits() {
		return used_credits;
	}
	public void setUsed_credits(int used_credits) {
		this.used_credits = used_credits;
	}
	
	public int getUsed_ammo() {
		return used_ammo;
	}
	public void setUsed_ammo(int used_ammo) {
		this.used_ammo = used_ammo;
	}
	
	public int getUsed_eridium() {
		return used_eridium;
	}
	public void setUsed_eridium(int used_eridium) {
		this.used_eridium = used_eridium;
	}
	
	public int getBuildings_created() {
		return buildings_created;
	}
	public void setBuildings_created(int buildings_created) {
		this.buildings_created = buildings_created;
	}
	
	public int getBuildings_upgraded() {
		return buildings_upgraded;
	}
	public void setBuildings_upgraded(int buildings_upgraded) {
		this.buildings_upgraded = buildings_upgraded;
	}
	
	public int getBuildings_destroyed() {
		return buildings_destroyed;
	}
	public void setBuildings_destroyed(int buildings_destroyed) {
		this.buildings_destroyed = buildings_destroyed;
	}
	
	public int getSupport_given_self() {
		return support_given_self;
	}
	public void setSupport_given_self(int support_given_self) {
		this.support_given_self = support_given_self;
	}
	
	public int getSupport_received_self() {
		return support_received_self;
	}
	public void setSupport_received_self(int support_received_self) {
		this.support_received_self = support_received_self;
	}
	
	public int getSupport_given_other() {
		return support_given_other;
	}
	public void setSupport_given_other(int support_given_other) {
		this.support_given_other = support_given_other;
	}
	
	public int getSupport_received_other() {
		return support_received_other;
	}
	public void setSupport_received_other(int support_received_other) {
		this.support_received_other = support_received_other;
	}
	
	public int getSupport_rejected() {
		return support_rejected;
	}
	public void setSupport_rejected(int support_rejected) {
		this.support_rejected = support_rejected;
	}
	
	public int getCommands_raid() {
		return commands_raid;
	}
	public void setCommands_raid(int commands_raid) {
		this.commands_raid = commands_raid;
	}
	
	public int getCommands_retreat() {
		return commands_retreat;
	}
	public void setCommands_retreat(int commands_retreat) {
		this.commands_retreat = commands_retreat;
	}
	
	public int getCommands_march() {
		return commands_march;
	}
	public void setCommands_march(int commands_march) {
		this.commands_march = commands_march;
	}
	
	public int getCommands_build() {
		return commands_build;
	}
	public void setCommands_build(int commands_build) {
		this.commands_build = commands_build;
	}
	
	public int getCommands_recruit() {
		return commands_recruit;
	}
	public void setCommands_recruit(int commands_recruit) {
		this.commands_recruit = commands_recruit;
	}
	
	public int getCommands_resources() {
		return commands_resources;
	}
	public void setCommands_resources(int commands_resources) {
		this.commands_resources = commands_resources;
	}
	
	public int getCommands_support() {
		return commands_support;
	}
	public void setCommands_support(int commands_support) {
		this.commands_support = commands_support;
	}
	
	public int getCommands_defense() {
		return commands_defense;
	}
	public void setCommands_defense(int commands_defense) {
		this.commands_defense = commands_defense;
	}
}