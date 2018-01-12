package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;

import net.jfabricationgames.bunkers_and_badasses.user.User;

public class GameStatistic implements Serializable {
	
	private static final long serialVersionUID = 758529797268373840L;

	public GameStatistic(User user) {
		this.user = user;
	}
	
	private User user;
	//user id and game id are checked by the server
	
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