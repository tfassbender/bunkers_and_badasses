package net.jfabricationgames.bunkers_and_badasses.game_frame;

import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_command.Command;

public class FieldCommand {

	private Field field;
	private Command command;
	
	public FieldCommand(Field field, Command command) {
		this.field = field;
		this.command = command;
	}
	
	@Override
	public String toString() {
		return field.getName() + " [" + command.getName() + "]";
	}
	
	public Field getField() {
		return field;
	}
	public Command getCommand() {
		return command;
	}
}