package net.jfabricationgames.bunkers_and_badasses.game_communication;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;

public class FightTransfereMessage extends BunkersAndBadassesMessage {
	
	private static final long serialVersionUID = -8418403517580075472L;
	
	private Fight fight;
	
	private boolean fromStartingPlayer;
	
	public FightTransfereMessage(Fight fight, boolean fromStartingPlayer) {
		this.fight = fight;
		this.fromStartingPlayer = fromStartingPlayer;
	}
	
	public Fight getFight() {
		return fight;
	}
	public void setFight(Fight fight) {
		this.fight = fight;
	}
	
	public boolean isFromStartingPlayer() {
		return fromStartingPlayer;
	}
	public void setFromStartingPlayer(boolean fromStartingPlayer) {
		this.fromStartingPlayer = fromStartingPlayer;
	}
}