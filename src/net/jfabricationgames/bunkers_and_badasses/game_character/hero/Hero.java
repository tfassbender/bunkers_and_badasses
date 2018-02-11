package net.jfabricationgames.bunkers_and_badasses.game_character.hero;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import javax.swing.SpinnerNumberModel;

import com.jfabricationgames.toolbox.graphic.ImageLoader;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.building.EmptyBuilding;
import net.jfabricationgames.bunkers_and_badasses.game_command.Command;

public abstract class Hero implements Serializable {
	
	private static final long serialVersionUID = 4244972938699657617L;

	//protected HeroEffectTime time;
	
	protected Game game;
	
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
	
	protected transient final Predicate<Field> localPlayersField = field -> field.getAffiliation() != null && field.getAffiliation().equals(game.getLocalUser());
	protected transient final Predicate<Field> neutralField = field -> field.getAffiliation() == null;
	protected transient final Predicate<Field> neutralTroopField = field -> field.getAffiliation() == null && field.getNormalTroops() > 0;
	protected transient final Predicate<Field> nextToLocalPlayersField = field -> field.getAffiliation() != null && field.getNeighbours().stream().
			anyMatch(f -> f.getAffiliation() != null && f.getAffiliation().equals(game.getLocalUser()));
	protected transient final Predicate<Field> nextToOtherEnemiesField = field -> field.getNeighbours().stream().
			anyMatch(f -> f.getAffiliation() != null && !f.getAffiliation().equals(game.getLocalUser()) && !f.getAffiliation().equals(field.getAffiliation()));
	protected transient final Predicate<Field> normalTroopsOnField = field -> field.getNormalTroops() > 0;
	protected transient final Predicate<Field> badassTroopsOnField = field -> field.getBadassTroops() > 0;
	protected transient final Predicate<Field> fieldEmpty = normalTroopsOnField.or(badassTroopsOnField);
	protected transient final Predicate<Field> hasCommand = field -> field.getCommand() != null;
	protected transient final Predicate<Field> hasBuilding = field -> !(field.getBuilding() instanceof EmptyBuilding);
	protected transient final Predicate<Field> moreThanOneBandit = field -> field.getNormalTroops() > 1 || (field.getNormalTroops() == 1 && field.getBadassTroops() > 0);
	protected transient final Predicate<Field> moreThanTwoBandits = field -> field.getNormalTroops() > 2 || (field.getNormalTroops() == 2 && field.getBadassTroops() > 0);
	
	public enum ExecutionType {
		TURN_EFFECT,
		BEFORE_FIGHT,
		DURING_FIGHT,
		AFTER_FIGHT;
	}
	
	/**
	 * The components of the HeroEffectExecutionFrame that are needed for the execution of the hero's effect
	 */
	public enum ExecutionComponent {
		FIELD_START,
		FIELD_TARGET,
		SPINNER_NUMBER_PER_FIELD_NORMAL,
		SPINNER_NUMBER_PER_FIELD_BADASS,
		SPINNER_NUMBER_NORMAL,
		SPINNER_NUMBER_BADASS,
		SELECTION_COMMAND,
	}
	
	/**
	 * Data needed for the execution of a hero's effect (as turn; such as fields to select or spinner-models)
	 */
	public class ExecutionData {
		
		public ExecutionData() {
			//add some default values
			startFieldNormalTroopsModel = new SpinnerNumberModel(0, 0, 20, 1);
			startFieldBadassTroopsModel = new SpinnerNumberModel(0, 0, 20, 1);
			targetFieldNormalTroopsModel = new SpinnerNumberModel(0, 0, 20, 1);
			targetFieldBadassTroopsModel = new SpinnerNumberModel(0, 0, 20, 1);
			possibleStartFields = new ArrayList<Field>();
			possibleTargetFields = new ArrayList<Field>();
			targetFields = new ArrayList<Field>();
			targetFieldsNormalTroops = new HashMap<Field, Integer>();
			targetFieldsBadassTroops = new HashMap<Field, Integer>();
			possibleCommands = new ArrayList<Command>();
		}
		
		private List<Field> possibleStartFields;
		private Field startField;
		
		private List<Field> possibleTargetFields;
		private List<Field> targetFields;
		
		private Map<Field, Integer> targetFieldsNormalTroops;
		private Map<Field, Integer> targetFieldsBadassTroops;
		
		private int startFieldNormalTroops;
		private int startFieldBadassTroops;
		
		private SpinnerNumberModel startFieldNormalTroopsModel;
		private SpinnerNumberModel startFieldBadassTroopsModel;
		
		private SpinnerNumberModel targetFieldNormalTroopsModel;
		private SpinnerNumberModel targetFieldBadassTroopsModel;
		
		private Command command;
		private List<Command> possibleCommands;
		
		public List<Field> getPossibleStartFields() {
			return possibleStartFields;
		}
		public void setPossibleStartFields(List<Field> possibleStartFields) {
			if (possibleStartFields == null) {
				possibleStartFields = new ArrayList<Field>();
			}
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
			if (possibleTargetFields == null) {
				possibleTargetFields = new ArrayList<Field>();
			}
			this.possibleTargetFields = possibleTargetFields;
		}
		
		public List<Field> getTargetFields() {
			return targetFields;
		}
		public void setTargetFields(List<Field> targetFields) {
			if (targetFields == null) {
				targetFields = new ArrayList<Field>();
			}
			this.targetFields = targetFields;
		}
		
		public Map<Field, Integer> getTargetFieldsNormalTroops() {
			return targetFieldsNormalTroops;
		}
		public void setTargetFieldsNormalTroops(Map<Field, Integer> targetFieldsNormalTroops) {
			if (targetFieldsNormalTroops == null) {
				targetFieldsNormalTroops = new HashMap<Field, Integer>();
			}
			this.targetFieldsNormalTroops = targetFieldsNormalTroops;
		}
		
		public Map<Field, Integer> getTargetFieldsBadassTroops() {
			return targetFieldsBadassTroops;
		}
		public void setTargetFieldsBadassTroops(Map<Field, Integer> targetFieldsBadassTroops) {
			if (targetFieldsBadassTroops == null) {
				targetFieldsBadassTroops = new HashMap<Field, Integer>();
			}
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
		
		public SpinnerNumberModel getStartFieldNormalTroopsModel() {
			return startFieldNormalTroopsModel;
		}
		public void setStartFieldNormalTroopsModel(SpinnerNumberModel startFieldNormalTroopsModel) {
			this.startFieldNormalTroopsModel = startFieldNormalTroopsModel;
		}
		
		public SpinnerNumberModel getStartFieldBadassTroopsModel() {
			return startFieldBadassTroopsModel;
		}
		public void setStartFieldBadassTroopsModel(SpinnerNumberModel startFieldBadassTroopsModel) {
			this.startFieldBadassTroopsModel = startFieldBadassTroopsModel;
		}
		
		public SpinnerNumberModel getTargetFieldNormalTroopsModel() {
			return targetFieldNormalTroopsModel;
		}
		public void setTargetFieldNormalTroopsModel(SpinnerNumberModel targetFieldNormalTroopsModel) {
			this.targetFieldNormalTroopsModel = targetFieldNormalTroopsModel;
		}
		
		public SpinnerNumberModel getTargetFieldBadassTroopsModel() {
			return targetFieldBadassTroopsModel;
		}
		public void setTargetFieldBadassTroopsModel(SpinnerNumberModel targetFieldBadassTroopsModel) {
			this.targetFieldBadassTroopsModel = targetFieldBadassTroopsModel;
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
			if (possibleCommands == null) {
				possibleCommands = new ArrayList<Command>();
			}
			this.possibleCommands = possibleCommands;
		}
	}
	
	static {
		imageLoader = new ImageLoader();
		imageLoader.setDefaultPathPrefix("net/jfabricationgames/bunkers_and_badasses/images/");
	}
	
	protected Hero() {
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
	 * Generate the ExecutionData for the ExecuteHeroEffectFrame based on the data the user has already selected.
	 */
	public ExecutionData getExecutionData(ExecutionData selected) {
		return null;
	}
	/**
	 * Execute the selected move.
	 * 
	 * @return
	 * 		Returns true if the execution of this move is successful. False otherwise.
	 */
	public boolean execute(ExecutionData data) {
		return false;
	}
	
	/**
	 * Execute the hero's effect before, during or after a fight (depending on the ExecutionType).
	 */
	public void execute(Fight fight) {
		
	}
	/**
	 * Adds the execution to the statistics. This method should be called after the turn was successfully executed.
	 */
	protected void addHeroEffectExecutionToStatistics(ExecutionData executionData) {
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
	
	public List<ExecutionComponent> getExecutionComponentsNeeded() {
		return componentsNeeded;
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
	
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	
	public ExecutionType getExecutionType() {
		return executionType;
	}
	public int getExecutionPriority() {
		return executionPriority;
	}
}