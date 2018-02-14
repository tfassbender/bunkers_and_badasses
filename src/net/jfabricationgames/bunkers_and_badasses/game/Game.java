package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;
import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_frame.GameFrame;
import net.jfabricationgames.bunkers_and_badasses.game_frame.InfoDialog;
import net.jfabricationgames.bunkers_and_badasses.game_storage.GameStore;
import net.jfabricationgames.bunkers_and_badasses.statistic.GameStatisticManager;
import net.jfabricationgames.bunkers_and_badasses.user.User;
import net.jfabricationgames.bunkers_and_badasses.user.UserManager;
import net.jfabricationgames.jfgserver.client.JFGClient;

public class Game implements Serializable {
	
	private static final long serialVersionUID = -6438265034888503813L;
	
	private transient JFGClient client;
	
	private Board board;
	private List<User> players;
	private User localUser;
	private User startingPlayer;
	private GameState gameState;
	private PlayerOrder playerOrder;
	private UserResourceManager resourceManager;
	private UserPlanManager planManager;
	private transient TurnExecutionManager turnExecutionManager;
	private GameTurnManager turnManager;
	private GameTurnBonusManager gameTurnBonusManager;
	private GameTurnGoalManager gameTurnGoalManager;
	private HeroCardManager heroCardManager;
	private PointManager pointManager;
	private UserColorManager colorManager;
	private SkillProfileManager skillProfileManager;
	private FightManager fightManager;
	private GameStatisticManager statisticManager;
	private GameLockManager gameLockManager;
	private transient GameFrame gameFrame;
	private static transient GameStore gameStore;
	private static transient GameVariableStorage gameVariableStorage;
	private static String version;
	
	private int id;//the game id in the database
	
	public Game(JFGClient client, List<User> players) {
		this.client = client;
		this.players = players;
		this.localUser = new User(UserManager.getUsername());
		gameState = GameState.PLAN;
		playerOrder = new PlayerOrder(players.size(), this);
		playerOrder.chooseRandomOrder(players);
		resourceManager = new UserResourceManager(players, this);
		pointManager = new PointManager(this);
		pointManager.initialize(players);
		gameTurnBonusManager = new GameTurnBonusManager(this);
		gameTurnBonusManager.chooseTurnBonusForGame(players.size());
		planManager = new UserPlanManager(this);
		gameTurnGoalManager = new GameTurnGoalManager(this);
		turnManager = new GameTurnManager(this);
		gameTurnGoalManager.setGameTurnManager(turnManager);
		gameTurnGoalManager.chooseTurnGoals();
		turnExecutionManager = new TurnExecutionManager(localUser, resourceManager, gameTurnBonusManager, gameTurnGoalManager, pointManager, this);
		heroCardManager = new HeroCardManager();
		heroCardManager.intitialize(players);
		colorManager = new UserColorManager();
		colorManager.chooseColors(players);
		fightManager = new FightManager(client, this, players, gameTurnBonusManager, gameTurnGoalManager, pointManager, turnExecutionManager, board);
		statisticManager = new GameStatisticManager();
		statisticManager.initialize(players);
		skillProfileManager = new SkillProfileManager();
		gameLockManager = new GameLockManager();
		gameStore = ((BunkersAndBadassesClientInterpreter) client.getClientInterpreter()).getGameStore();
		//initialize the GameFrame when the board is added.
		//gameFrame = new GameFrame(this);
	}
	
	/**
	 * Merge the data of the new game into this game.
	 * All data that the new game contains overwrites the old data in this game.
	 * 
	 * @param newData
	 * 		A Game object containing the new data from another player.
	 */
	public void merge(Game newData) {
		if (board != null) {
			//maybe the board doesn't exist on start
			board.merge(newData.getBoard());
		}
		else {
			board = newData.getBoard();
		}
		players = newData.getPlayers();
		startingPlayer = newData.getStartingPlayer();
		gameState = newData.getGameState();
		playerOrder.merge(newData.getPlayerOrder());
		resourceManager.merge(newData.getResourceManager());
		turnManager.merge(newData.getTurnManager());
		gameTurnBonusManager.merge(newData.getGameTurnBonusManager());
		gameTurnGoalManager.merge(newData.getGameTurnGoalManager());
		heroCardManager.merge(newData.getHeroCardManager());
		pointManager.merge(newData.getPointManager());
		colorManager.merge(newData.getColorManager());
		skillProfileManager.merge(newData.getSkillProfileManager());
		fightManager.merge(newData.getFightManager());
		gameLockManager.merge(newData.getGameLockManager());
		//update the frames
		if (gameFrame != null) {
			gameFrame.updateAllFrames();
		}
		//store the game if the local player is the starting player
		if (getStartingPlayer().equals(localUser) && board != null) {
			gameStore.storeGame(this, false);
		}
	}
	
	/**
	 * Add the Board to the game and initialize the frames.
	 * 
	 * @param board
	 */
	public void addBoard(Board board) {
		this.board = board;
		gameFrame = new GameFrame(this);
	}
	
	/**
	 * Start the game by collecting the start resources, commands, ...
	 */
	public void startGame() {
		//reload some variables
		gameTurnBonusManager.reloadVariables();
		resourceManager.loadStartResources();
		//reset some managers
		skillProfileManager.setManagers(pointManager, heroCardManager);
		//collect the start resources
		resourceManager.collectAllGameStartResources(players);
		//skillProfileManager.collectAllSkillResources(players);
		//prepare the turn start
		planManager.countCommands();
		gameFrame.getBoardOverviewFrame().setBoard(board);
		//update the game reference in the HeroCardManager and the hero cards
		heroCardManager.updateGame(this);
	}
	
	/**
	 * Start the game when it was loaded from the database.
	 * Therefore the game frame is created and some resources are loaded.
	 */
	public void startLoadedGame() {
		//load all images that were not stored in the database
		board.getFields().forEach(field -> field.getBuilding().loadImage());
		heroCardManager.getHeroCardStack().forEach(hero -> hero.loadImage());
		for (Field field : board.getFields()) {
			if (field.getCommand() != null) {
				field.getCommand().loadImage();
			}
		}
		planManager.countCommands();
		fightManager.setCurrentFight(null);//fights can't be stored right and need to be restarted
		((BunkersAndBadassesClientInterpreter) client.getClientInterpreter()).setGame(this);
		if (gameFrame == null) {
			//not sure if it's created by the addBoard method
			gameFrame = new GameFrame(this);			
		}
		//update the game reference in the HeroCardManager and the hero cards
		heroCardManager.updateGame(this);
		gameFrame.setVisible(true);
	}
	
	/**
	 * Receive an updated user list from the server and inform the players if one player leaves the game.
	 * 
	 * @param userListUpdate
	 * 		The new user list.
	 */
	public void receiveUserListUpdate(List<User> userListUpdate) {
		//remove all users from the list that don't take part in this game
		userListUpdate.removeIf(user -> !players.contains(user));
		User missing = null;
		for (User player : userListUpdate) {
			if (!player.isOnline()) {
				missing = player;
			}
		}
		if (missing != null && !turnManager.isGameEnded()) {
			//inform this player about the missing user
			new InfoDialog("Spieler ausgeloggt", "Die Verbindung zum Spieler [" + missing + "] wurde unterbrochen.\n\n"
					+ "Entweder hat der Spieler sich ausgeloggt, oder es gibt schwierigkeiten mit der Server-Verbindung.\n\n"
					+ "Das aktuelle Spiel wurde gespeichert und kann nach einem erneuten Login fortgesetzt werden.").setVisible(true);
		}
	}
	
	public JFGClient getClient() {
		return client;
	}
	public void setClient(JFGClient client) {
		this.client = client;
	}
	
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public List<User> getPlayers() {
		return players;
	}
	public void setPlayers(List<User> players) {
		this.players = players;
	}
	
	public User getLocalUser() {
		return localUser;
	}
	public void setLocalUser(User localUser) {
		this.localUser = localUser;
	}
	
	public User getStartingPlayer() {
		return startingPlayer;
	}
	public void setStartingPlayer(User startingPlayer) {
		this.startingPlayer = startingPlayer;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	public void setState(GameState gameState) {
		this.gameState = gameState;
	}
	
	public PlayerOrder getPlayerOrder() {
		return playerOrder;
	}
	public void setOrder(PlayerOrder playerOrder) {
		this.playerOrder = playerOrder;
	}
	
	public UserResourceManager getResourceManager() {
		return resourceManager;
	}
	public void setResourceManager(UserResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}
	
	public UserPlanManager getPlanManager() {
		return planManager;
	}
	public void setPlanManager(UserPlanManager planManager) {
		this.planManager = planManager;
	}
	
	public TurnExecutionManager getTurnExecutionManager() {
		return turnExecutionManager;
	}
	public void setTurnExecutionManager(TurnExecutionManager turnExecutionManager) {
		this.turnExecutionManager = turnExecutionManager;
	}
	
	public GameTurnManager getTurnManager() {
		return turnManager;
	}
	public void setTurnManager(GameTurnManager turnManager) {
		this.turnManager = turnManager;
	}
	
	public GameTurnBonusManager getGameTurnBonusManager() {
		return gameTurnBonusManager;
	}
	public void setGameTurnBonusManager(GameTurnBonusManager gameTurnBonusManager) {
		this.gameTurnBonusManager = gameTurnBonusManager;
	}
	
	public GameTurnGoalManager getGameTurnGoalManager() {
		return gameTurnGoalManager;
	}
	public void setGameTurnGoalManager(GameTurnGoalManager gameTurnGoalManager) {
		this.gameTurnGoalManager = gameTurnGoalManager;
	}
	
	public HeroCardManager getHeroCardManager() {
		return heroCardManager;
	}
	public void setHeroCardManager(HeroCardManager heroCardManager) {
		this.heroCardManager = heroCardManager;
	}
	
	public PointManager getPointManager() {
		return pointManager;
	}
	public void setPointManager(PointManager pointManager) {
		this.pointManager = pointManager;
	}
	
	public UserColorManager getColorManager() {
		return colorManager;
	}
	public void setColorManager(UserColorManager colorManager) {
		this.colorManager = colorManager;
	}
	
	public SkillProfileManager getSkillProfileManager() {
		return skillProfileManager;
	}
	public void setSkillProfileManager(SkillProfileManager skillProfileManager) {
		this.skillProfileManager = skillProfileManager;
		skillProfileManager.setUserResourceManager(resourceManager);
	}
	
	public static GameStore getGameStore() {
		return gameStore;
	}
	
	public static GameVariableStorage getGameVariableStorage() {
		return gameVariableStorage;
	}
	public static void setGameVariableStorage(GameVariableStorage gameVariableStorage) {
		Game.gameVariableStorage = gameVariableStorage;
		UserPlanManager.receiveStartCommands(gameVariableStorage.getUserCommands());
	}
	
	public GameFrame getGameFrame() {
		return gameFrame;
	}
	public void setGameFrame(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public FightManager getFightManager() {
		return fightManager;
	}
	public void setFightManager(FightManager fightManager) {
		this.fightManager = fightManager;
	}
	
	public GameStatisticManager getStatisticManager() {
		return statisticManager;
	}
	public void setStatisticManager(GameStatisticManager statisticManager) {
		this.statisticManager = statisticManager;
	}
	
	public GameLockManager getGameLockManager() {
		return gameLockManager;
	}
	public void setGameLockManager(GameLockManager gameLockManager) {
		this.gameLockManager = gameLockManager;
	}
	
	public static String getVersion() {
		return version;
	}
	public static void setVersion(String version) {
		Game.version = version;
	}
}