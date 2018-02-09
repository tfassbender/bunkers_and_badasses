package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.jfabricationgames.toolbox.graphic.ImageLoader;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_command.Command;

public abstract class Hero implements Serializable {
	
	private static final long serialVersionUID = 4244972938699657617L;

	protected HeroEffectTime time;
	
	protected int attack;
	protected int defence;
	
	protected int usedAttack;
	protected int usedDefence;
	
	protected ExecutionType executionType;
	protected int executionPriority;//the order in which the hero effects are executed
	
	protected List<ExecutionComponent> componentsNeeded;
	
	protected String name;
	protected String effectDescription;
	protected String imagePath;
	protected String cardImagePath;
	
	protected transient BufferedImage image;
	protected transient BufferedImage cardImage;
	
	protected static ImageLoader imageLoader;
	
	protected enum ExecutionType {
		TURN_EFFECT,
		BEFORE_FIGHT,
		DURING_FIGHT,
		AFTER_FIGHT;
	}
	
	/**
	 * The components of the HeroEffectExecutionFrame that are needed for the execution of the hero's effect
	 */
	protected enum ExecutionComponent {
		FIELD_START,
		FIELD_TARGET,
		SPINNER_NUMBER_PER_FIELD,
		SPINNER_NUMBER_NORMAL,
		SPINNER_NUMBER_BADASS,
		SELECTION_COMMAND,
	}
	
	/**
	 * Data needed for the execution of a hero's effect (as turn; such as fields to select or spinner-models)
	 */
	public class ExecutionData {
		
		public ExecutionData() {
			
		}
		
		private List<Field> possibleStartFields;
		private Field startField;
		
		private List<Field> possibleTargetFields;
		private List<Field> targetFields;
		
		private Map<Field, Integer> targetFieldsNormalTroops;
		private Map<Field, Integer> targetFieldsBadassTroops;
		
		private int startFieldNormalTroops;
		private int startFieldBadassTroops;
		
		private Command command;
		private List<Command> possibleCommands;
		
		public List<Field> getPossibleStartFields() {
			return possibleStartFields;
		}
		public void setPossibleStartFields(List<Field> possibleStartFields) {
			this.possibleStartFields = possibleStartFields;
		}
		
		public Field getStartField() {
			return startField;
		}
		public void setStartField(Field startField) {
			this.startField = startField;
		}
		
		public List<Field> getPossibleTargetFields() {
			return possibleTargetFields;
		}
		public void setPossibleTargetFields(List<Field> possibleTargetFields) {
			this.possibleTargetFields = possibleTargetFields;
		}
		
		public List<Field> getTargetFields() {
			return targetFields;
		}
		public void setTargetFields(List<Field> targetFields) {
			this.targetFields = targetFields;
		}
		
		public Map<Field, Integer> getTargetFieldsNormalTroops() {
			return targetFieldsNormalTroops;
		}
		public void setTargetFieldsNormalTroops(Map<Field, Integer> targetFieldsNormalTroops) {
			this.targetFieldsNormalTroops = targetFieldsNormalTroops;
		}
		
		public Map<Field, Integer> getTargetFieldsBadassTroops() {
			return targetFieldsBadassTroops;
		}
		public void setTargetFieldsBadassTroops(Map<Field, Integer> targetFieldsBadassTroops) {
			this.targetFieldsBadassTroops = targetFieldsBadassTroops;
		}
		
		public int getStartFieldNormalTroops() {
			return startFieldNormalTroops;
		}
		public void setStartFieldNormalTroops(int startFieldNormalTroops) {
			this.startFieldNormalTroops = startFieldNormalTroops;
		}
		
		public int getStartFieldBadassTroops() {
			return startFieldBadassTroops;
		}
		public void setStartFieldBadassTroops(int startFieldBadassTroops) {
			this.startFieldBadassTroops = startFieldBadassTroops;
		}
		
		public Command getCommand() {
			return command;
		}
		public void setCommand(Command command) {
			this.command = command;
		}
		
		public List<Command> getPossibleCommands() {
			return possibleCommands;
		}
		public void setPossibleCommands(List<Command> possibleCommands) {
			this.possibleCommands = possibleCommands;
		}
	}
	
	static {
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/");
	}
	
	public Hero() {
		attack = 0;
		defence = 0;
		usedAttack = 0;
		usedDefence = 0;
		executionType = null;
		executionPriority = 0;
		componentsNeeded = new ArrayList<ExecutionComponent>();
	}
	
	public void loadImage() {
		image = imageLoader.loadImage(imagePath);
		cardImage = imageLoader.loadImage(cardImagePath);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Hero) {
			return name.equals(((Hero) obj).getName());
		}
		else {
			return super.equals(obj);
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, effectDescription, attack, defence);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * Execute the hero's special effect as a game turn.
	 * The default implementation only updates the statistics because not all hero's special effects can be used as turn.
	 */
	public void executeTurn(Game game) {
		//TODO add to the statistics
	}
	/**
	 * Generate the ExecutionData for the ExecuteHeroEffectFrame based on the data the user has already selected.
	 */
	public ExecutionData getExecutionData(ExecutionData selected) {
		//TODO
		return null;
	}
	/**
	 * Execute the selected move.
	 * 
	 * @return
	 * 		Returns true if the execution of this move is successful. False otherwise.
	 */
	public boolean execute(ExecutionData data) {
		//TODO
		return false;
	}
	
	/**
	 * Execute the hero's effect before, during or after a fight (depending on the ExecutionType).
	 */
	public void execute(Fight fight) {
		//TODO
	}
	
	/**
	 * Serialize the object without the transient parts (the images that are not stored in the database)
	 * 
	 * @param out
	 * 		The ObjectOutputStream that is used to write down the file.
	 */
	protected void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}
	/**
	 * Read the object without the transient parts (the images that are not stored in the database)
	 * 
	 * @param in
	 * 		The ObjectInputStream that is used to read the file.
	 */
	protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
	
	public int getAttack() {
		return attack;
	}
	public int getDefence() {
		return defence;
	}
	
	public String getName() {
		return name;
	}
	public String getEffectDescription() {
		return effectDescription;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public BufferedImage getCardImage() {
		return cardImage;
	}
	public void setCardImage(BufferedImage cardImage) {
		this.cardImage = cardImage;
	}
}