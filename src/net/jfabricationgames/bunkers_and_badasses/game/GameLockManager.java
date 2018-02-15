package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class GameLockManager implements Serializable {
	
	private static final long serialVersionUID = 6313341869519093199L;
	
	public Map<User, List<GameLock>> userLocks;
	public Map<User, Map<String, List<GameLock>>> fieldLocks;//use the field name because the Field class has no correct hash and can't be changed...
	
	public GameLockManager() {
		userLocks = new HashMap<User, List<GameLock>>();
		fieldLocks = new HashMap<User, Map<String, List<GameLock>>>();
	}
	
	public void merge(GameLockManager lockManager) {
		userLocks = lockManager.getUserLocks();
		fieldLocks = lockManager.getFieldLocks();
	}
	
	/**
	 * Reduce the counter of all locks and remove those whose counter is 0
	 */
	public void removeLocks() {
		userLocks.forEach((user, locks) -> locks.forEach(GameLock::decreaseCounter));
		fieldLocks.forEach((user, map) -> map.forEach((user2, locks) -> locks.forEach(GameLock::decreaseCounter)));
		userLocks.forEach((user, locks) -> locks.removeIf(GameLock::isCounterExpired));
		fieldLocks.forEach((user, map) -> map.forEach((field, locks) -> locks.removeIf(GameLock::isCounterExpired)));
	}
	
	public void addLock(User user, GameLock lock) {
		List<GameLock> locks = Optional.ofNullable(userLocks.get(user)).orElse(new ArrayList<GameLock>(2));
		locks.add(lock);
		userLocks.put(user, locks);
	}
	public void addLock(User user, Field field, GameLock lock) {
		if (field == null) {
			addLock(user, lock);
		}
		else {
			Map<String, List<GameLock>> usersFieldLocks = Optional.ofNullable(fieldLocks.get(user)).orElse(new HashMap<String, List<GameLock>>(2));
			List<GameLock> locks = Optional.ofNullable(usersFieldLocks.get(field.getName())).orElse(new ArrayList<GameLock>(2));
			locks.add(lock);
			usersFieldLocks.put(field.getName(), locks);
			fieldLocks.put(user, usersFieldLocks);
		}
	}
	
	public List<GameLock> getGameLocks(User user) {
		return Optional.ofNullable(userLocks.get(user)).orElse(new ArrayList<GameLock>(0));
	}
	public List<GameLock> getGameLocks(User user, Field field) {
		if (field == null) {
			return getGameLocks(user);
		}
		else {
			Map<String, List<GameLock>> usersFieldLocks = Optional.ofNullable(fieldLocks.get(user)).orElse(new HashMap<String, List<GameLock>>());
			List<GameLock> locks = Optional.ofNullable(usersFieldLocks.get(field.getName())).orElse(new ArrayList<GameLock>(0));
			return locks;
		}
	}
	
	private Map<User, List<GameLock>> getUserLocks() {
		return userLocks;
	}
	private Map<User, Map<String, List<GameLock>>> getFieldLocks() {
		return fieldLocks;
	}
}