package net.jfabricationgames.bunkers_and_badasses.game;

import java.io.Serializable;
import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game_board.Board;
import net.jfabricationgames.bunkers_and_badasses.game_frame.GameFrame;
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
	private transient GameFrame gameFrame;
	private static transient GameVariableStorage gameVariableStorage;
	
	private int id;//the game id in the database
	
	public Game(JFGClient client, List<User> players) {
		this.client = client;
		this.players = players;
		this.localUser = new User(UserManager.getUsername());
		gameState = GameState.PLAN;
		playerOrder = new PlayerOrder(players.size(), this);
		playerOrder.chooseRandomOrder(players);
		resourceManager = new UserResourceManager(players, this);
		pointManager = new PointManager();
		pointManager.initialize(players);
		gameTurnBonusManager = new GameTurnBonusManager(pointManager);
		gameTurnBonusManager.chooseTurnBonusForGame(players.size());
		planManager = new UserPlanManager(this);
		gameTurnGoalManager = new GameTurnGoalManager(pointManager);
		turnManager = new GameTurnManager(this);
		gameTurnGoalManager.setGameTurnManager(turnManager);
		gameTurnGoalManager.chooseTurnGoals();
		turnExecutionManager = new TurnExecutionManager(localUser, resourceManager, gameTurnBonusManager, gameTurnGoalManager, pointManager, this);
		heroCardManager = new HeroCardManager();
		heroCardManager.intitialize(players);
		colorManager = new UserColorManager();
		colorManager.chooseRandomColors(players);
		fightManager = new FightManager(client, this, players, gameTurnBonusManager, gameTurnGoalManager, pointManager, turnExecutionManager, board);
		skillProfileManager = new SkillProfileManager();
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
		//update the frames
		if (gameFrame != null) {
			gameFrame.updateAllFrames();			
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
}