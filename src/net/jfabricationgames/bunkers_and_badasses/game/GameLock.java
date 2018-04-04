package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;
import java.util.Objects;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;

public class GameLock implements Serializable {
	
	private static final long serialVersionUID = -4293113969266457912L;
	
	private String errorMessage;//the error message that is displayed if a player attacks a field with an attack lock
	
	private int lockTurnCount;//the number of turns that the lock stays (remove when 0 is reached)
	
	private Field field;//a field that can be locked
	
	private LockType lockType;
	
	public enum LockType {
		ARSCHGAULS_FIELD_LOCK,
		ANGELS_HERO_LOCK;
	}

	public GameLock(String errorMessage, int lockTurnCount, LockType lockType, Field field) {
		this.errorMessage = errorMessage;
		this.lockTurnCount = lockTurnCount;
		this.lockType = lockType;
		this.field = field;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(lockType, field);
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameLock) {
			GameLock gameLock = (GameLock) obj;
			return gameLock.getLockType() == this.getLockType() && Objects.equals(gameLock.getField(), this.getField());
		}
		return false;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	public int getLockTurnCount() {
		return lockTurnCount;
	}
	public void decreaseCounter() {
		lockTurnCount--;
	}
	public boolean isCounterExpired() {
		return lockTurnCount <= 0;
	}
	
	public LockType getLockType() {
		return lockType;
	}
	
	public Field getField() {
		return field;
	}
	public boolean isFieldLock() {
		return field != null;
	}
}