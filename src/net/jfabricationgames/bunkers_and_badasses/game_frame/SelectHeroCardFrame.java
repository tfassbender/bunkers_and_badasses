package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jfabricationgames.toolbox.graphic.ImagePanel;

import net.jfabricationgames.bunkers_and_badasses.error.BunkersAndBadassesException;
import net.jfabricationgames.bunkers_and_badasses.error.ResourceException;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game.GameState;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.HeroSelectionListener;
import net.jfabricationgames.bunkers_and_badasses.help.HelpMenuFrame;
import net.miginfocom.swing.MigLayout;

public class SelectHeroCardFrame extends JFrame {
	
	private static final long serialVersionUID = -6962661273110796419L;
	
	private final JPanel contentPanel = new JPanel();
	
	private Game game;
	
	private JTextField txtHero;
	private JTextField txtHeroAttack;
	private JTextField txtHerodefence;
	
	private Hero selectedHero;
	
	private HeroSelectionListener selectionListener;
	
	private DefaultListModel<Hero> heroListModel = new DefaultListModel<Hero>();
	private JButton btnAuswhlen;
	private JTextArea txtrSpecialeffect;
	private JList<Hero> list_heroes;
	private JTextField txtKosten;
	private JSpinner spinner;
	private ImagePanel panel_hero_image;
	private ResourceInfoPanel panel_resources;
	private JButton btnRekrutieren;
	
	public SelectHeroCardFrame(Game game, boolean cardPlayable) {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				update();
			}
		});
		this.game = game;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(SelectHeroCardFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Helden auswählen - Bunkers and Badasses");
		setBounds(100, 100, 600, 700);
		setMinimumSize(new Dimension(600, 700));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			contentPanel.add(panel, "cell 0 0,grow");
			panel.setLayout(new MigLayout("", "[250px,grow][200px,grow]", "[300px,grow][:150px:200px,grow][:150px:200px,grow]"));
			
			JPanel panel_heroes = new JPanel();
			panel_heroes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_heroes.setBackground(Color.GRAY);
			panel.add(panel_heroes, "cell 0 0,grow");
			panel_heroes.setLayout(new MigLayout("", "[grow]", "[][grow][]"));
			
			JLabel lblVerfgbahreHelden = new JLabel("Verf\u00FCgbahre Helden:");
			panel_heroes.add(lblVerfgbahreHelden, "cell 0 0,alignx center");
			lblVerfgbahreHelden.setFont(new Font("Tahoma", Font.PLAIN, 14));
			
			JScrollPane scrollPane_heroes = new JScrollPane();
			panel_heroes.add(scrollPane_heroes, "cell 0 1,grow");
			
			list_heroes = new JList<Hero>(heroListModel);
			list_heroes.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent arg0) {
					selectedHero = list_heroes.getSelectedValue();
					updateHero();
				}
			});
			list_heroes.setToolTipText("<html>\r\nAlle Heldenkarten die du<br>\r\nauf der Hand hast\r\n</html>");
			list_heroes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list_heroes.setBackground(Color.LIGHT_GRAY);
			scrollPane_heroes.setViewportView(list_heroes);
			
			JButton btnAlleHeldenAnsehen = new JButton("Alle Helden ansehen");
			btnAlleHeldenAnsehen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					HelpMenuFrame helpMenu = game.getGameFrame().getHelpMenuFrame();
					helpMenu.setHelpPanel("heldenkarten_erhallten");
					helpMenu.setVisible(true);
					helpMenu.requestFocus();
				}
			});
			btnAlleHeldenAnsehen.setToolTipText("<html>\r\nAlle im Spiel verf\u00FCgbaren Helden ansehen\r\n</html>");
			btnAlleHeldenAnsehen.setBackground(Color.GRAY);
			panel_heroes.add(btnAlleHeldenAnsehen, "cell 0 2");
			
			panel_hero_image = new ImagePanel();
			panel_hero_image.setAdaptSizeKeepProportion(true);
			panel_hero_image.setCentered(true);
			panel_hero_image.setBackground(Color.GRAY);
			panel.add(panel_hero_image, "cell 1 0,grow");
			
			JPanel panel_selected = new JPanel();
			panel_selected.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_selected.setBackground(Color.GRAY);
			panel.add(panel_selected, "cell 0 1 1 2,grow");
			panel_selected.setLayout(new MigLayout("", "[][][50px][30px][grow]", "[][5px][][][5px][][grow][]"));
			
			JLabel lblHeld = new JLabel("Held:");
			lblHeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_selected.add(lblHeld, "cell 0 0");
			
			txtHero = new JTextField();
			txtHero.setEditable(false);
			txtHero.setBackground(Color.LIGHT_GRAY);
			txtHero.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_selected.add(txtHero, "cell 1 0 3 1,growx");
			txtHero.setColumns(10);
			
			JLabel lblAngriffskraft = new JLabel("Angriffskraft:");
			lblAngriffskraft.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_selected.add(lblAngriffskraft, "cell 0 2 2 1");
			
			txtHeroAttack = new JTextField();
			txtHeroAttack.setToolTipText("<html>\r\nDie Angriffskraft des Helden die <br>\r\nin einem Kampf zur bisherigen <br>\r\nKampfst\u00E4rke (als Angreifer) hinzuaddiert <br>\r\nwerden kann. (Dann wird der <br>\r\nSpezialeffekt aber nicht benutzt)\r\n</html>");
			txtHeroAttack.setHorizontalAlignment(SwingConstants.CENTER);
			txtHeroAttack.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtHeroAttack.setBackground(Color.LIGHT_GRAY);
			txtHeroAttack.setEditable(false);
			panel_selected.add(txtHeroAttack, "cell 2 2,growx");
			txtHeroAttack.setColumns(10);
			
			JLabel lblVerteidungungskraft = new JLabel("Verteidungungskraft:");
			lblVerteidungungskraft.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_selected.add(lblVerteidungungskraft, "cell 0 3 2 1");
			
			txtHerodefence = new JTextField();
			txtHerodefence.setToolTipText("<html>\r\nDie Verteidugungskraft des Helden <br>\r\ndie in einem Kampf zur bisherigen <br>\r\nKampfst\u00E4rke (als Verteidiger) hinzuaddiert <br>\r\nwerden kann. (Dann wird der <br>\r\nSpezialeffekt aber nicht benutzt)\r\n</html>");
			txtHerodefence.setHorizontalAlignment(SwingConstants.CENTER);
			txtHerodefence.setBackground(Color.LIGHT_GRAY);
			txtHerodefence.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_selected.add(txtHerodefence, "cell 2 3,growx");
			txtHerodefence.setColumns(10);
			
			JLabel lblEffekt = new JLabel("Spezial Effekt:");
			lblEffekt.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_selected.add(lblEffekt, "cell 0 5 2 1");
			
			JScrollPane scrollPane_special_effect = new JScrollPane();
			panel_selected.add(scrollPane_special_effect, "cell 0 6 5 1,grow");
			
			txtrSpecialeffect = new JTextArea();
			txtrSpecialeffect.setWrapStyleWord(true);
			txtrSpecialeffect.setLineWrap(true);
			txtrSpecialeffect.setEditable(false);
			txtrSpecialeffect.setBackground(Color.LIGHT_GRAY);
			txtrSpecialeffect.setFont(new Font("Tahoma", Font.PLAIN, 12));
			scrollPane_special_effect.setViewportView(txtrSpecialeffect);
			
			JPanel panel_buttons = new JPanel();
			panel_buttons.setBackground(Color.GRAY);
			panel_selected.add(panel_buttons, "cell 0 7 5 1,grow");
			panel_buttons.setLayout(new MigLayout("", "[grow][][][grow]", "[]"));
			
			btnAuswhlen = new JButton("Ausw\u00E4hlen");
			btnAuswhlen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectHero();
				}
			});
			btnAuswhlen.setEnabled(cardPlayable);
			btnAuswhlen.setBackground(Color.GRAY);
			panel_buttons.add(btnAuswhlen, "cell 1 0");
			
			JButton btnAbbrechen = new JButton("Abbrechen");
			btnAbbrechen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
			btnAbbrechen.setBackground(Color.GRAY);
			panel_buttons.add(btnAbbrechen, "cell 2 0");
			
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel_1.setBackground(Color.GRAY);
			panel.add(panel_1, "cell 1 1,grow");
			panel_1.setLayout(new MigLayout("", "[][grow][40px][grow]", "[][15px][][][5px:n:5px][][grow]"));
			
			JLabel lblHeldenRekrutieren = new JLabel("Helden Rekrutieren");
			lblHeldenRekrutieren.setFont(new Font("Tahoma", Font.PLAIN, 14));
			panel_1.add(lblHeldenRekrutieren, "cell 0 0 4 1,alignx center");
			
			JLabel lblNeueHeldenRekrutieren = new JLabel("Helden Rekrutieren:");
			lblNeueHeldenRekrutieren.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_1.add(lblNeueHeldenRekrutieren, "cell 0 2 2 1");
			
			spinner = new JSpinner();
			spinner.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					int cards = (Integer) spinner.getValue();
					txtKosten.setText((cards*10) + " Eridium");
				}
			});
			spinner.setBackground(Color.LIGHT_GRAY);
			spinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));
			panel_1.add(spinner, "cell 2 2,growx");
			
			JLabel lblKosten = new JLabel("Kosten:");
			lblKosten.setFont(new Font("Tahoma", Font.PLAIN, 12));
			panel_1.add(lblKosten, "cell 0 3,alignx trailing");
			
			txtKosten = new JTextField();
			txtKosten.setText("10 Eridium");
			txtKosten.setBackground(Color.LIGHT_GRAY);
			txtKosten.setEditable(false);
			panel_1.add(txtKosten, "cell 1 3 3 1,growx");
			txtKosten.setColumns(10);
			
			btnRekrutieren = new JButton("Rekrutieren");
			btnRekrutieren.setEnabled(false);
			btnRekrutieren.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					recruitHeros();
				}
			});
			btnRekrutieren.setBackground(Color.GRAY);
			panel_1.add(btnRekrutieren, "cell 0 5 4 1,alignx center");
			
			panel_resources = new ResourceInfoPanel();
			panel.add(panel_resources, "cell 1 2,grow");
			panel_resources.setBackground(Color.GRAY);
		}
	}
	
	private void recruitHeros() {
		try {
			int cards = (Integer) spinner.getValue();
			game.getResourceManager().getResources().get(game.getLocalUser()).payHeroCards(cards);
			try {
				game.getHeroCardManager().takeCards(game.getLocalUser(), cards);
				game.getHeroCardManager().setHeroCardsTaken(game.getLocalUser());
				game.getPlayerOrder().nextMove();
				game.getTurnExecutionManager().commit();
				game.getGameFrame().update();
				updateComponents();
				updateHeroList();
				updateResources();
			}
			catch (BunkersAndBadassesException be) {
				new ErrorDialog(be.getErrorText()).setVisible(true);
			}						
		}
		catch (ResourceException re) {
			new ErrorDialog(re.getErrorText()).setVisible(true);
		}
	}
	
	public void update() {
		updateComponents();
		updateHeroList();
		updateHero();
		updateResources();
	}
	
	private void updateComponents() {
		int cardsLeft = Game.getGameVariableStorage().getMaxHerosCards() - game.getHeroCardManager().getHeroCards(game.getLocalUser()).size();
		boolean herosRecruitable = game.getGameState().equals(GameState.ACT) && game.getPlayerOrder().isPlayersTurn(game.getLocalUser()) && !game.getHeroCardManager().isHeroCardsTaken(game.getLocalUser()) && cardsLeft >= 1;
		spinner.setModel(new SpinnerNumberModel(1, 1, (cardsLeft < 1 ? 1 : cardsLeft), 1));
		txtKosten.setText("10 Eridium");
		btnRekrutieren.setEnabled(herosRecruitable);
	}
	
	private void updateHeroList() {
		heroListModel.removeAllElements();
		for (Hero hero : game.getHeroCardManager().getHeroCards(game.getLocalUser())) {
			heroListModel.addElement(hero);
		}
		if (!heroListModel.contains(selectedHero)) {
			selectedHero = null;
		}
	}
	
	private void updateHero() {
		if (selectedHero == null) {
			txtHero.setText("");
			txtHeroAttack.setText("");
			txtHerodefence.setText("");
			txtrSpecialeffect.setText("");
		}
		else {
			txtHero.setText(selectedHero.getName());
			txtHeroAttack.setText(Integer.toString(selectedHero.getAttack()));
			txtHerodefence.setText(Integer.toString(selectedHero.getDefence()));
			txtrSpecialeffect.setText(selectedHero.getEffectDescription());
			panel_hero_image.setImage(selectedHero.getCardImage());
		}
	}
	
	private void updateResources() {
		panel_resources.updateResources(game, game.getLocalUser());
	}
	
	private void selectHero() {
		if (list_heroes.getSelectedIndex() == -1) {
			new ErrorDialog("Du musst einen Helden aus der Liste auswählen um ihn einzusetzen.").setVisible(true);
		}
		else {
			if (selectionListener == null) {
				throw new IllegalArgumentException("The listener mussn't be null.");
			}
			Hero hero = list_heroes.getSelectedValue();
			game.getHeroCardManager().putBackCards(hero);
			selectionListener.receiveSelectedHero(hero);
			setVisible(false);
		}
	}
	
	/**
	 * Enable or disable the selection of a hero. When enabled a listener has to be set.
	 * 
	 * @param enabled
	 * 		Enable or disable the selection.
	 * 
	 * @param listener
	 * 		The listener that receives the selected hero card.
	 * 
	 * @throws IllegalArgumentException
	 * 		An IllegalArgumentException is thrown when the selection is enabled and the listener is null.
	 */
	public void setCardSelectionEnabled(boolean enabled, HeroSelectionListener listener) throws IllegalArgumentException {
		if (enabled && listener == null) {
			throw new IllegalArgumentException("Can't enable the selection without a listener.");
		}
		this.selectionListener = listener;
		btnAuswhlen.setEnabled(enabled);
	}
}