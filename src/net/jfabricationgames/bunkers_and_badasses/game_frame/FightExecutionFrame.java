package net.jfabricationgames.bunkers_and_badasses.game_frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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

import com.jfabricationgames.toolbox.properties.dataView.PropertiesFile;
import com.jfabricationgames.toolbox.properties.event.PropertiesWindowListener;

import net.jfabricationgames.bunkers_and_badasses.game.Fight;
import net.jfabricationgames.bunkers_and_badasses.game.Game;
import net.jfabricationgames.bunkers_and_badasses.game_board.Field;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.Hero;
import net.jfabricationgames.bunkers_and_badasses.game_character.hero.HeroSelectionListener;
import net.miginfocom.swing.MigLayout;

public class FightExecutionFrame extends JFrame implements HeroSelectionListener {
	
	private static final long serialVersionUID = -8826989423127239957L;
	
	private JPanel contentPane;
	
	private Map<Field, Integer> fallingSupportTroops;
	private int fallingTroops;
	private int fallingTroopsLooser;
	
	private int fallingTroopsLeft;
	
	private Field selectedFallenTroopsField;
	private Field selectedFallenSupportTroopsField;
	private Map<Field, int[]> fallenTroops;
	private List<Field> fieldsFallenTroops;
	
	private JTextField txtAngreifer;
	private JTextField txtVerteidiger;
	private JTextField txtTruppenangreifer;
	private JTextField txtTruppenverteidiger;
	private JTextField txtUntersttzerangreifer;
	private JTextField txtUntersttzerverteidiger;
	private JTextField txtAngreifendesfeld;
	private JTextField txtVerteidigendesFeld;
	
	private DefaultListModel<Field> fieldNeighboursSupportModel = new DefaultListModel<Field>();
	private DefaultListModel<Field> fieldDeniedSupportModel = new DefaultListModel<Field>();
	private DefaultListModel<Field> fieldAttackerSupportModel = new DefaultListModel<Field>();
	private DefaultListModel<Field> fieldDefenderSupportModel = new DefaultListModel<Field>();
	private DefaultListModel<Field> fieldRetreadModel = new DefaultListModel<Field>();
	private DefaultListModel<Field> fieldFallingSupportModel = new DefaultListModel<Field>();
	private DefaultListModel<Field> fieldSelectionModel = new DefaultListModel<Field>();
	
	private JTextField txtHeroAttacker;
	private JTextField txtHerodefender;
	private JTextField txtHeroAttackerAtk;
	private JTextField txtHeroAttackerDef;
	private JTextField txtHeroDefenderAtk;
	private JTextField txtHeroDefenderDef;
	private JTextField txtSieger;
	private JTextField txtVerlierer;
	private JTextField txtFallendetruppen;
	private JTextField txtFallendetruppenbis;
	private JTextField txtFallendetruppengesammt;
	private JTextField txtPhase;
	private JTextField txtbrig;
	private JTextField txtFallendeTruppen;
	
	private JTextArea txtrHeroEffectAttacker;
	private JTextArea txtrHeroEffectDefender;
	private JButton btnAuswhlen;
	private JButton btnHeldAuswhlen;
	private JButton btnHeldAuswhlen_1;
	private JButton btnBesttigen_1;
	private JButton btnBesttigen;
	private JButton btnAuswahlZurcksetzen_1;
	private JSpinner spinner_fallende_truppen_gesammt;
	private JSpinner spinner_fallende_truppen_verlierer;
	private JSpinner spinner_fallende_truppen_unterstuetzer;
	private JSpinner spinner_normal_troups;
	private JSpinner spinner_badass_troups;
	private JButton btnKeinenHeldenVerwenden;
	private JButton btnKeinenHeldenVerwenden_1;
	private JList<Field> list_support_field;
	private JList<Field> list_rueckzug;
	private JRadioButton rdbtnEffektAttacker;
	private JRadioButton rdbtnStrkeAttacker;
	private JRadioButton rdbtnEffectDefender;
	private JRadioButton rdbtnStrkeDefender;
	private JButton btnAuswahlZurcksetzen;
	private JList<Field> list_field_fallen_troops;
	
	private Game game;
	
	private PropertiesFile propsFile = new PropertiesFile(this);
	
	public FightExecutionFrame(Game game) {
		addWindowListener(new PropertiesWindowListener(propsFile, PropertiesWindowListener.WINDOW_CLOSING_EVENT));
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				update();
			}
		});
		this.game = game;
		
		fallingSupportTroops = new HashMap<Field, Integer>();
		fallenTroops = new HashMap<Field, int[]>();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(FightExecutionFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Kampf Ausführung - Bunkers and Badasses");
		setBounds(100, 100, 1100, 699);
		setMinimumSize(new Dimension(800, 600));
		
		propsFile.alignWindow();
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[400px,grow]", "[400px,grow][300px,grow]"));
		
		JPanel panel_top_bar = new JPanel();
		panel_top_bar.setBackground(Color.GRAY);
		panel.add(panel_top_bar, "cell 0 0,grow");
		panel_top_bar.setLayout(new MigLayout("", "[300px,grow][:100px:200px,grow][300px,grow]", "[grow][grow]"));
		
		JPanel panel_info = new JPanel();
		panel_info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_info.setBackground(Color.GRAY);
		panel_top_bar.add(panel_info, "cell 0 0 1 2,grow");
		panel_info.setLayout(new MigLayout("", "[][grow][50px,grow][5px][grow][50px,grow]", "[][5px,grow][][][5px][][][5px][][][10px][][grow]"));
		
		JLabel lblKampfInfo = new JLabel("Kampf Info:");
		lblKampfInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_info.add(lblKampfInfo, "cell 0 0 6 1,alignx center");
		
		JLabel lblAngreifer = new JLabel("Angreifer:");
		lblAngreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblAngreifer, "cell 0 2,alignx trailing");
		
		txtAngreifer = new JTextField();
		txtAngreifer.setEditable(false);
		txtAngreifer.setBackground(Color.LIGHT_GRAY);
		txtAngreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(txtAngreifer, "cell 1 2 5 1,growx");
		txtAngreifer.setColumns(10);
		
		JLabel lblVerteidiger = new JLabel("Verteidiger:");
		lblVerteidiger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblVerteidiger, "cell 0 3,alignx trailing");
		
		txtVerteidiger = new JTextField();
		txtVerteidiger.setEditable(false);
		txtVerteidiger.setBackground(Color.LIGHT_GRAY);
		txtVerteidiger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(txtVerteidiger, "cell 1 3 5 1,growx");
		txtVerteidiger.setColumns(10);
		
		JLabel lblAngreifendesFeld = new JLabel("Angreifendes Feld:");
		lblAngreifendesFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblAngreifendesFeld, "cell 0 5 2 1");
		
		txtAngreifendesfeld = new JTextField();
		txtAngreifendesfeld.setBackground(Color.LIGHT_GRAY);
		txtAngreifendesfeld.setEditable(false);
		txtAngreifendesfeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(txtAngreifendesfeld, "cell 2 5 4 1,growx");
		txtAngreifendesfeld.setColumns(10);
		
		JLabel lblVerteidigendesFeld = new JLabel("Verteidigendes Feld:");
		lblVerteidigendesFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblVerteidigendesFeld, "cell 0 6 2 1");
		
		txtVerteidigendesFeld = new JTextField();
		txtVerteidigendesFeld.setBackground(Color.LIGHT_GRAY);
		txtVerteidigendesFeld.setEditable(false);
		txtVerteidigendesFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(txtVerteidigendesFeld, "cell 2 6 4 1,growx");
		txtVerteidigendesFeld.setColumns(10);
		
		JLabel lblTruppen = new JLabel("Angreifer Kampfst\u00E4rke:");
		lblTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblTruppen, "cell 0 8 2 1,alignx trailing");
		
		txtTruppenangreifer = new JTextField();
		txtTruppenangreifer.setToolTipText("<html>\r\nDie momentane gesammte Kampfst\u00E4rke<br>\r\ndes angreifenden Spielers\r\n</html>");
		txtTruppenangreifer.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppenangreifer.setEditable(false);
		txtTruppenangreifer.setBackground(Color.LIGHT_GRAY);
		txtTruppenangreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(txtTruppenangreifer, "cell 2 8,growx");
		txtTruppenangreifer.setColumns(10);
		
		JLabel lblUntersttzung = new JLabel("Unterst\u00FCtzer:");
		lblUntersttzung.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblUntersttzung, "cell 4 8,alignx trailing");
		
		txtUntersttzerangreifer = new JTextField();
		txtUntersttzerangreifer.setToolTipText("<html>\r\nDie Anzahl der unterst\u00FCtzenden Felder (f\u00FCr den Angreifer)\r\n</html>");
		txtUntersttzerangreifer.setHorizontalAlignment(SwingConstants.CENTER);
		txtUntersttzerangreifer.setEditable(false);
		txtUntersttzerangreifer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtUntersttzerangreifer.setBackground(Color.LIGHT_GRAY);
		panel_info.add(txtUntersttzerangreifer, "cell 5 8,growx");
		txtUntersttzerangreifer.setColumns(10);
		
		JLabel lblTruppen_1 = new JLabel("Verteidiger Kampfst\u00E4rke:");
		lblTruppen_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblTruppen_1, "cell 0 9 2 1,alignx trailing");
		
		txtTruppenverteidiger = new JTextField();
		txtTruppenverteidiger.setToolTipText("<html>\r\nDie momentane gesammte Kampfst\u00E4rke<br>\r\ndes verteidigenden Spielers\r\n</html>");
		txtTruppenverteidiger.setHorizontalAlignment(SwingConstants.CENTER);
		txtTruppenverteidiger.setEditable(false);
		txtTruppenverteidiger.setBackground(Color.LIGHT_GRAY);
		txtTruppenverteidiger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(txtTruppenverteidiger, "cell 2 9,growx");
		txtTruppenverteidiger.setColumns(10);
		
		JLabel lblUntersttzer = new JLabel("Unterst\u00FCtzer:");
		lblUntersttzer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblUntersttzer, "cell 4 9,alignx trailing");
		
		txtUntersttzerverteidiger = new JTextField();
		txtUntersttzerverteidiger.setToolTipText("<html>\r\nDie Anzahl der unterst\u00FCtzenden Felder (f\u00FCr den Verteidiger)\r\n</html>");
		txtUntersttzerverteidiger.setHorizontalAlignment(SwingConstants.CENTER);
		txtUntersttzerverteidiger.setEditable(false);
		txtUntersttzerverteidiger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtUntersttzerverteidiger.setBackground(Color.LIGHT_GRAY);
		panel_info.add(txtUntersttzerverteidiger, "cell 5 9,growx");
		txtUntersttzerverteidiger.setColumns(10);
		
		JLabel lblAktion = new JLabel("Phase:");
		lblAktion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(lblAktion, "cell 0 11,alignx trailing");
		
		txtPhase = new JTextField();
		txtPhase.setToolTipText("<html>\r\nDie aktuell auszuf\u00FChrende Aktion\r\n</html>");
		txtPhase.setEditable(false);
		txtPhase.setBackground(Color.LIGHT_GRAY);
		txtPhase.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_info.add(txtPhase, "cell 1 11 5 1,growx");
		txtPhase.setColumns(10);
		
		JPanel panel_defender_support = new JPanel();
		panel_top_bar.add(panel_defender_support, "cell 1 0,grow");
		panel_defender_support.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_defender_support.setBackground(Color.GRAY);
		panel_defender_support.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblVerteidigerUntersttzung = new JLabel("Verteidiger Unterst\u00FCtzung:");
		lblVerteidigerUntersttzung.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_defender_support.add(lblVerteidigerUntersttzung, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_defender_support = new JScrollPane();
		panel_defender_support.add(scrollPane_defender_support, "cell 0 2,grow");
		
		JList<Field> list_defender_support = new JList<Field>(fieldDefenderSupportModel);
		list_defender_support.setToolTipText("<html>\r\nAlle Nachbarfelder die den<br>\r\nVerteidiger unterst\u00FCtzen\r\n</html>");
		list_defender_support.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_defender_support.setBackground(Color.LIGHT_GRAY);
		list_defender_support.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_defender_support.setViewportView(list_defender_support);
		
		JPanel panel_heroes = new JPanel();
		panel_top_bar.add(panel_heroes, "cell 2 0 1 2,grow");
		panel_heroes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_heroes.setBackground(Color.GRAY);
		panel_heroes.setLayout(new MigLayout("", "[grow][grow]", "[][5px][][5px][grow]"));
		
		JLabel lblHelden = new JLabel("Helden:");
		lblHelden.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_heroes.add(lblHelden, "cell 0 0 2 1,alignx center");
		
		JLabel lblAngreifer_1 = new JLabel("Angreifer:");
		lblAngreifer_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_heroes.add(lblAngreifer_1, "cell 0 2,alignx center");
		
		JLabel lblVerteidiger_1 = new JLabel("Verteidiger:");
		lblVerteidiger_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_heroes.add(lblVerteidiger_1, "cell 1 2,alignx center");
		
		JPanel panel_hero_attacker = new JPanel();
		panel_hero_attacker.setBackground(Color.GRAY);
		panel_heroes.add(panel_hero_attacker, "cell 0 4,grow");
		panel_hero_attacker.setLayout(new MigLayout("", "[][25px,grow][][25px,grow]", "[][5px][][5px][][5px][grow][][]"));
		
		JLabel lblHeld = new JLabel("Held:");
		lblHeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_hero_attacker.add(lblHeld, "cell 0 0,alignx trailing");
		
		txtHeroAttacker = new JTextField();
		txtHeroAttacker.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtHeroAttacker.setBackground(Color.LIGHT_GRAY);
		txtHeroAttacker.setEditable(false);
		panel_hero_attacker.add(txtHeroAttacker, "cell 1 0 3 1,growx");
		txtHeroAttacker.setColumns(10);
		
		JLabel lblAtk = new JLabel("Atk.:");
		lblAtk.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_hero_attacker.add(lblAtk, "cell 0 2,alignx trailing");
		
		txtHeroAttackerAtk = new JTextField();
		txtHeroAttackerAtk.setToolTipText("<html>\r\nDie Angriffspunkte des Helden\r\n</html>");
		txtHeroAttackerAtk.setHorizontalAlignment(SwingConstants.CENTER);
		txtHeroAttackerAtk.setBackground(Color.LIGHT_GRAY);
		txtHeroAttackerAtk.setEditable(false);
		txtHeroAttackerAtk.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_hero_attacker.add(txtHeroAttackerAtk, "cell 1 2,growx");
		txtHeroAttackerAtk.setColumns(10);
		
		JLabel lblVert = new JLabel("Vert.:");
		lblVert.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_hero_attacker.add(lblVert, "cell 2 2,alignx trailing");
		
		txtHeroAttackerDef = new JTextField();
		txtHeroAttackerDef.setToolTipText("<html>\r\nDie Verteidugungspunkte des Helden\r\n</html>");
		txtHeroAttackerDef.setHorizontalAlignment(SwingConstants.CENTER);
		txtHeroAttackerDef.setBackground(Color.LIGHT_GRAY);
		txtHeroAttackerDef.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtHeroAttackerDef.setEditable(false);
		panel_hero_attacker.add(txtHeroAttackerDef, "cell 3 2,growx");
		txtHeroAttackerDef.setColumns(10);
		
		rdbtnEffektAttacker = new JRadioButton("Effekt");
		rdbtnEffektAttacker.setEnabled(false);
		rdbtnEffektAttacker.setForeground(Color.BLACK);
		rdbtnEffektAttacker.setBackground(Color.GRAY);
		panel_hero_attacker.add(rdbtnEffektAttacker, "cell 0 4 2 1,alignx center");
		
		rdbtnStrkeAttacker = new JRadioButton("St\u00E4rke");
		rdbtnStrkeAttacker.setSelected(true);
		rdbtnStrkeAttacker.setForeground(Color.BLACK);
		rdbtnStrkeAttacker.setBackground(Color.GRAY);
		rdbtnStrkeAttacker.setEnabled(false);
		panel_hero_attacker.add(rdbtnStrkeAttacker, "cell 2 4 2 1,alignx center");
		
		JScrollPane scrollPane_hero_effect_atk = new JScrollPane();
		panel_hero_attacker.add(scrollPane_hero_effect_atk, "cell 0 6 4 1,grow");
		
		txtrHeroEffectAttacker = new JTextArea();
		txtrHeroEffectAttacker.setEditable(false);
		txtrHeroEffectAttacker.setToolTipText("<html>\r\nDer Spezialeffekt des Helden\r\n</html>");
		txtrHeroEffectAttacker.setWrapStyleWord(true);
		txtrHeroEffectAttacker.setLineWrap(true);
		txtrHeroEffectAttacker.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtrHeroEffectAttacker.setBackground(Color.LIGHT_GRAY);
		scrollPane_hero_effect_atk.setViewportView(txtrHeroEffectAttacker);
		
		btnHeldAuswhlen = new JButton("Held Ausw\u00E4hlen");
		btnHeldAuswhlen.setEnabled(false);
		btnHeldAuswhlen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectHero();
			}
		});
		btnHeldAuswhlen.setBackground(Color.GRAY);
		panel_hero_attacker.add(btnHeldAuswhlen, "cell 0 7 4 1,alignx center");
		
		btnKeinenHeldenVerwenden = new JButton("Keinen Helden Verwenden");
		btnKeinenHeldenVerwenden.setEnabled(false);
		btnKeinenHeldenVerwenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectEmptyHero();
			}
		});
		btnKeinenHeldenVerwenden.setBackground(Color.GRAY);
		panel_hero_attacker.add(btnKeinenHeldenVerwenden, "cell 0 8 4 1,alignx center");
		
		JPanel panel_hero_defender = new JPanel();
		panel_hero_defender.setBackground(Color.GRAY);
		panel_heroes.add(panel_hero_defender, "cell 1 4,grow");
		panel_hero_defender.setLayout(new MigLayout("", "[][25px,grow][][25px,grow]", "[][5px][][5px][][5px][grow][][]"));
		
		JLabel lblHeld_1 = new JLabel("Held:");
		lblHeld_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_hero_defender.add(lblHeld_1, "cell 0 0,alignx trailing");
		
		txtHerodefender = new JTextField();
		txtHerodefender.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtHerodefender.setBackground(Color.LIGHT_GRAY);
		txtHerodefender.setEditable(false);
		panel_hero_defender.add(txtHerodefender, "cell 1 0 3 1,growx");
		txtHerodefender.setColumns(10);
		
		JLabel label = new JLabel("Atk.:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_hero_defender.add(label, "cell 0 2,alignx trailing");
		
		txtHeroDefenderAtk = new JTextField();
		txtHeroDefenderAtk.setToolTipText("<html>\r\nDie Angriffspunkte des Helden\r\n</html>");
		txtHeroDefenderAtk.setHorizontalAlignment(SwingConstants.CENTER);
		txtHeroDefenderAtk.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtHeroDefenderAtk.setEditable(false);
		txtHeroDefenderAtk.setColumns(10);
		txtHeroDefenderAtk.setBackground(Color.LIGHT_GRAY);
		panel_hero_defender.add(txtHeroDefenderAtk, "cell 1 2,growx");
		
		JLabel label_1 = new JLabel("Vert.:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_hero_defender.add(label_1, "cell 2 2,alignx trailing");
		
		txtHeroDefenderDef = new JTextField();
		txtHeroDefenderDef.setToolTipText("<html>\r\nDie Verteidugungspunkte des Helden\r\n</html>");
		txtHeroDefenderDef.setHorizontalAlignment(SwingConstants.CENTER);
		txtHeroDefenderDef.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtHeroDefenderDef.setEditable(false);
		txtHeroDefenderDef.setColumns(10);
		txtHeroDefenderDef.setBackground(Color.LIGHT_GRAY);
		panel_hero_defender.add(txtHeroDefenderDef, "cell 3 2,growx");
		
		rdbtnEffectDefender = new JRadioButton("Effekt");
		rdbtnEffectDefender.setEnabled(false);
		rdbtnEffectDefender.setForeground(Color.BLACK);
		rdbtnEffectDefender.setBackground(Color.GRAY);
		panel_hero_defender.add(rdbtnEffectDefender, "cell 0 4 2 1,alignx center");
		
		rdbtnStrkeDefender = new JRadioButton("St\u00E4rke");
		rdbtnStrkeDefender.setSelected(true);
		rdbtnStrkeDefender.setForeground(Color.BLACK);
		rdbtnStrkeDefender.setEnabled(false);
		rdbtnStrkeDefender.setBackground(Color.GRAY);
		panel_hero_defender.add(rdbtnStrkeDefender, "cell 2 4 2 1,alignx center");
		
		JScrollPane scrollPane_hero_effect_def = new JScrollPane();
		panel_hero_defender.add(scrollPane_hero_effect_def, "cell 0 6 4 1,grow");
		
		txtrHeroEffectDefender = new JTextArea();
		txtrHeroEffectDefender.setEditable(false);
		txtrHeroEffectDefender.setToolTipText("<html>\r\nDer Spezialeffekt des Helden\r\n</html>");
		txtrHeroEffectDefender.setWrapStyleWord(true);
		txtrHeroEffectDefender.setLineWrap(true);
		txtrHeroEffectDefender.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtrHeroEffectDefender.setBackground(Color.LIGHT_GRAY);
		scrollPane_hero_effect_def.setViewportView(txtrHeroEffectDefender);
		
		btnHeldAuswhlen_1 = new JButton("Held Ausw\u00E4hlen");
		btnHeldAuswhlen_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectHero();
			}
		});
		btnHeldAuswhlen_1.setEnabled(false);
		btnHeldAuswhlen_1.setBackground(Color.GRAY);
		panel_hero_defender.add(btnHeldAuswhlen_1, "cell 0 7 4 1,alignx center");
		
		btnKeinenHeldenVerwenden_1 = new JButton("Keinen Helden Verwenden");
		btnKeinenHeldenVerwenden_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectEmptyHero();
			}
		});
		btnKeinenHeldenVerwenden_1.setEnabled(false);
		btnKeinenHeldenVerwenden_1.setBackground(Color.GRAY);
		panel_hero_defender.add(btnKeinenHeldenVerwenden_1, "cell 0 8 4 1,alignx center");
		
		JPanel panel_attacker_support = new JPanel();
		panel_top_bar.add(panel_attacker_support, "cell 1 1,grow");
		panel_attacker_support.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_attacker_support.setBackground(Color.GRAY);
		panel_attacker_support.setLayout(new MigLayout("", "[grow]", "[][5px][grow]"));
		
		JLabel lblAngreiferUntersttzung = new JLabel("Angreifer Unterst\u00FCtzung:");
		lblAngreiferUntersttzung.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_attacker_support.add(lblAngreiferUntersttzung, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_attacker_support = new JScrollPane();
		panel_attacker_support.add(scrollPane_attacker_support, "cell 0 2,grow");
		
		JList<Field> list_attacker_support = new JList<Field>(fieldAttackerSupportModel);
		list_attacker_support.setToolTipText("<html>\r\nAlle Nachbarfelder die den <br>\r\nAngreifer unterst\u00FCtzen\r\n</html>");
		list_attacker_support.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_attacker_support.setBackground(Color.LIGHT_GRAY);
		list_attacker_support.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane_attacker_support.setViewportView(list_attacker_support);
		
		JPanel panel_low_right_bar = new JPanel();
		panel_low_right_bar.setBackground(Color.GRAY);
		panel.add(panel_low_right_bar, "cell 0 1,grow");
		panel_low_right_bar.setLayout(new MigLayout("", "[:300px:500px,grow][:200px:200px,grow][200px,grow][200px]", "[300px,grow]"));
		
		JPanel panel_end = new JPanel();
		panel_end.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_end.setBackground(Color.GRAY);
		panel_low_right_bar.add(panel_end, "cell 0 0,grow");
		panel_end.setLayout(new MigLayout("", "[grow][][25px][25px:n:25px][][25px:n:25px][50px][grow]", "[][5px,grow][][][5px][][grow]"));
		
		JLabel lblKampfAusgang = new JLabel("Kampf Ausgang:");
		lblKampfAusgang.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_end.add(lblKampfAusgang, "cell 1 0 6 1,alignx center");
		
		JLabel lblSieger = new JLabel("Sieger:");
		lblSieger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(lblSieger, "cell 1 2");
		
		txtSieger = new JTextField();
		txtSieger.setEditable(false);
		txtSieger.setBackground(Color.LIGHT_GRAY);
		txtSieger.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(txtSieger, "cell 2 2 5 1,growx");
		txtSieger.setColumns(10);
		
		JLabel lblVerlierer = new JLabel("Verlierer:");
		lblVerlierer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(lblVerlierer, "cell 1 3");
		
		txtVerlierer = new JTextField();
		txtVerlierer.setEditable(false);
		txtVerlierer.setBackground(Color.LIGHT_GRAY);
		txtVerlierer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(txtVerlierer, "cell 2 3 5 1,growx");
		txtVerlierer.setColumns(10);
		
		JLabel lblFallendeTruppen = new JLabel("Fallende Truppen:");
		lblFallendeTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(lblFallendeTruppen, "cell 1 5 2 1");
		
		txtFallendetruppen = new JTextField();
		txtFallendetruppen.setHorizontalAlignment(SwingConstants.CENTER);
		txtFallendetruppen.setEditable(false);
		txtFallendetruppen.setBackground(Color.LIGHT_GRAY);
		txtFallendetruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(txtFallendetruppen, "cell 3 5,growx");
		txtFallendetruppen.setColumns(10);
		
		JLabel lblBis = new JLabel("bis");
		lblBis.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(lblBis, "cell 4 5,alignx center");
		
		txtFallendetruppenbis = new JTextField();
		txtFallendetruppenbis.setHorizontalAlignment(SwingConstants.CENTER);
		txtFallendetruppenbis.setEditable(false);
		txtFallendetruppenbis.setBackground(Color.LIGHT_GRAY);
		txtFallendetruppenbis.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_end.add(txtFallendetruppenbis, "cell 5 5,growx");
		txtFallendetruppenbis.setColumns(10);
		
		JPanel panel_retread = new JPanel();
		panel_retread.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_retread.setBackground(Color.GRAY);
		panel_low_right_bar.add(panel_retread, "cell 1 0,grow");
		panel_retread.setLayout(new MigLayout("", "[grow]", "[][5px][grow][]"));
		
		JLabel lblRckzugsfeld = new JLabel("R\u00FCckzugsfeld:");
		lblRckzugsfeld.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_retread.add(lblRckzugsfeld, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_rueckzug = new JScrollPane();
		panel_retread.add(scrollPane_rueckzug, "cell 0 2,grow");
		
		list_rueckzug = new JList<Field>(fieldRetreadModel);
		list_rueckzug.setToolTipText("<html>\r\nDas Feld in das sich der Verlierer zur\u00FCck <br>\r\nzieht (verliert der angreifer zieht er sich<br>\r\nimmer in das Feld zur\u00FCck aus dem er <br>\r\ngekommen ist)\r\n</html>");
		list_rueckzug.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_rueckzug.setFont(new Font("Tahoma", Font.PLAIN, 12));
		list_rueckzug.setBackground(Color.LIGHT_GRAY);
		scrollPane_rueckzug.setViewportView(list_rueckzug);
		
		btnAuswhlen = new JButton("Ausw\u00E4hlen");
		btnAuswhlen.setEnabled(false);
		btnAuswhlen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectRetreatField();
			}
		});
		btnAuswhlen.setBackground(Color.GRAY);
		panel_retread.add(btnAuswhlen, "cell 0 3,alignx center");
		
		JPanel panel_fallen_troups = new JPanel();
		panel_fallen_troups.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_fallen_troups.setBackground(Color.GRAY);
		panel_low_right_bar.add(panel_fallen_troups, "flowx,cell 2 0,grow");
		panel_fallen_troups.setLayout(new MigLayout("", "[grow][][50px:50px,fill][10px:n][grow][grow]", "[][5px:5px,grow][][][100px][][5px][][grow]"));
		
		JLabel lblFallendeTruppen_1 = new JLabel("Fallende Truppen:");
		lblFallendeTruppen_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_fallen_troups.add(lblFallendeTruppen_1, "cell 1 0 4 1,alignx center");
		
		JLabel lblGesammt = new JLabel("Gesammt:");
		lblGesammt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups.add(lblGesammt, "cell 1 2");
		
		spinner_fallende_truppen_gesammt = new JSpinner();
		spinner_fallende_truppen_gesammt.setEnabled(false);
		spinner_fallende_truppen_gesammt.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				fallingTroops = (Integer) spinner_fallende_truppen_gesammt.getValue();
				updateFallingTroopsLeft();
				updateFallingTroopSpinnerModels();
			}
		});
		spinner_fallende_truppen_gesammt.setToolTipText("<html>\r\nDie gesammte Anzahl an fallenden Truppen (auf <br>\r\nbeiden Seiten). Die maximal m\u00F6gliche Anzahl entspricht<br>\r\ndem Overhead, die minimal m\u00F6gliche Anzahl dem <br>\r\nhalben abgerundeten Overhead (genauere Beschreibung <br>\r\nin der Regel Hilfe)\r\n</html>");
		spinner_fallende_truppen_gesammt.setForeground(Color.LIGHT_GRAY);
		spinner_fallende_truppen_gesammt.setBackground(Color.LIGHT_GRAY);
		panel_fallen_troups.add(spinner_fallende_truppen_gesammt, "cell 2 2");
		
		JLabel lblUntersttzendesFeld = new JLabel("Unterst\u00FCtzende Felder:");
		lblUntersttzendesFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups.add(lblUntersttzendesFeld, "cell 4 2,alignx center");
		
		JLabel lblVerlierer_1 = new JLabel("Verlierer:");
		lblVerlierer_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups.add(lblVerlierer_1, "cell 1 3");
		
		spinner_fallende_truppen_verlierer = new JSpinner();
		spinner_fallende_truppen_verlierer.setEnabled(false);
		spinner_fallende_truppen_verlierer.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				fallingTroopsLooser = (Integer) spinner_fallende_truppen_verlierer.getValue();
				updateFallingTroopsLeft();
			}
		});
		spinner_fallende_truppen_verlierer.setToolTipText("<html>\r\nDie Anzahl an Truppen die in dem Feld fallen, <br>\r\ndass den Kampf verloren hat (nicht die <br>\r\nUnterst\u00FCtzer). Mindestens die H\u00E4lfte der <br>\r\ninsgesammt fallenden Truppen muss in diesem<br>\r\nFeld fallen (genauere Beschreibung in der <br>\r\nRegel Hilfe)\r\n</html>");
		spinner_fallende_truppen_verlierer.setForeground(Color.LIGHT_GRAY);
		spinner_fallende_truppen_verlierer.setBackground(Color.LIGHT_GRAY);
		panel_fallen_troups.add(spinner_fallende_truppen_verlierer, "cell 2 3");
		
		JScrollPane scrollPane_support_field = new JScrollPane();
		panel_fallen_troups.add(scrollPane_support_field, "cell 4 3 1 2,grow");
		
		list_support_field = new JList<Field>(fieldFallingSupportModel);
		list_support_field.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				selectedFallenSupportTroopsField = list_support_field.getSelectedValue();
				updateFallingTroopSupportSelection(list_support_field.getSelectedValue());
				updateFallingTroopsLeft();
			}
		});
		list_support_field.setToolTipText("<html>\r\nDie Felder die den verlierer unterst\u00FCtzt <br>\r\nhaben und in denen Truppen fallen k\u00F6nnen\r\n</html>");
		list_support_field.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_support_field.setBackground(Color.LIGHT_GRAY);
		scrollPane_support_field.setViewportView(list_support_field);
		
		JLabel lblbrig = new JLabel("\u00DCbrig:");
		lblbrig.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups.add(lblbrig, "cell 1 4");
		
		txtbrig = new JTextField();
		txtbrig.setEditable(false);
		txtbrig.setToolTipText("<html>\r\nDie Anzahl an fallenden Truppen, die noch <br>\r\nauf den Verlierer und die unterst\u00FCtzenden <br>\r\nFelder verteilt werden m\u00FCssen\r\n</html>");
		txtbrig.setHorizontalAlignment(SwingConstants.CENTER);
		txtbrig.setBackground(Color.LIGHT_GRAY);
		txtbrig.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups.add(txtbrig, "cell 2 4,growx");
		txtbrig.setColumns(10);
		
		JLabel lblUntersttzer_1 = new JLabel("Unterst\u00FCtzer:");
		lblUntersttzer_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups.add(lblUntersttzer_1, "cell 1 5");
		
		spinner_fallende_truppen_unterstuetzer = new JSpinner();
		spinner_fallende_truppen_unterstuetzer.setEnabled(false);
		spinner_fallende_truppen_unterstuetzer.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int fallingSupport = (Integer) spinner_fallende_truppen_unterstuetzer.getValue();
				if (list_support_field.getSelectedValue() != null) {
					fallingSupportTroops.put(list_support_field.getSelectedValue(), fallingSupport);
					updateFallingTroopsLeft();					
				}
			}
		});
		spinner_fallende_truppen_unterstuetzer.setToolTipText("<html>\r\nDie Anzahl der fallenden Truppen in einem <br>\r\nder unterst\u00FCtzenden Felder (maximal die <br>\r\nh\u00E4lfte der Truppen in einem unterst\u00FCtzenden <br>\r\nFeld (abgerundet) kann fallen; genauere <br>\r\nBeschreibung in der Regel Hilfe)\r\n</html>");
		spinner_fallende_truppen_unterstuetzer.setModel(new SpinnerNumberModel(0, 0, 5, 1));
		spinner_fallende_truppen_unterstuetzer.setForeground(Color.LIGHT_GRAY);
		spinner_fallende_truppen_unterstuetzer.setBackground(Color.LIGHT_GRAY);
		panel_fallen_troups.add(spinner_fallende_truppen_unterstuetzer, "cell 2 5");
		
		btnBesttigen_1 = new JButton("Best\u00E4tigen");
		btnBesttigen_1.setEnabled(false);
		btnBesttigen_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmFallingTroopSelection();
			}
		});
		
		btnAuswahlZurcksetzen = new JButton("Auswahl zur\u00FCcksetzen");
		btnAuswahlZurcksetzen.setEnabled(false);
		btnAuswahlZurcksetzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFallingSupportTroops();
			}
		});
		btnAuswahlZurcksetzen.setBackground(Color.GRAY);
		panel_fallen_troups.add(btnAuswahlZurcksetzen, "cell 4 5,alignx center");
		btnBesttigen_1.setBackground(Color.GRAY);
		panel_fallen_troups.add(btnBesttigen_1, "cell 1 7 4 1,alignx center");
		
		JPanel panel_fallen_troups_looser = new JPanel();
		panel_fallen_troups_looser.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_fallen_troups_looser.setBackground(Color.GRAY);
		panel_low_right_bar.add(panel_fallen_troups_looser, "cell 3 0,grow");
		panel_fallen_troups_looser.setLayout(new MigLayout("", "[grow][][][40px:50px][5px][175px][grow]", "[][5px:5px][][][][][5px:n][grow]"));
		
		JLabel lblFallendeTruppenAuswhlen = new JLabel("Fallende Truppen Ausw\u00E4hlen:");
		lblFallendeTruppenAuswhlen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel_fallen_troups_looser.add(lblFallendeTruppenAuswhlen, "cell 0 0 7 1,alignx center");
		
		JLabel lblFallendeTruppen_2 = new JLabel("<html>Fallende<br>Truppen:</html>");
		lblFallendeTruppen_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups_looser.add(lblFallendeTruppen_2, "cell 1 2 2 1");
		
		txtFallendeTruppen = new JTextField();
		txtFallendeTruppen.setEditable(false);
		txtFallendeTruppen.setToolTipText("<html>\r\nDie Anzahl an Truppen die auf diesem Feld fallen\r\n</html>");
		txtFallendeTruppen.setHorizontalAlignment(SwingConstants.CENTER);
		txtFallendeTruppen.setBackground(Color.LIGHT_GRAY);
		txtFallendeTruppen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups_looser.add(txtFallendeTruppen, "cell 3 2,growx");
		txtFallendeTruppen.setColumns(10);
		
		JLabel lblFeld = new JLabel("Feld:");
		lblFeld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups_looser.add(lblFeld, "cell 5 2,alignx center");
		
		JLabel lblInstgesammt = new JLabel("Ausgew\u00E4hlt:");
		panel_fallen_troups_looser.add(lblInstgesammt, "cell 1 3 2 1");
		lblInstgesammt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtFallendetruppengesammt = new JTextField();
		txtFallendetruppengesammt.setHorizontalAlignment(SwingConstants.CENTER);
		txtFallendetruppengesammt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups_looser.add(txtFallendetruppengesammt, "cell 3 3");
		txtFallendetruppengesammt.setToolTipText("<html>\r\nDie Anzahl an ausgew\u00E4hlten Truppen, <br>\r\ndie auf diesem Feld fallen (Normale <br>\r\nTruppen z\u00E4hlen einen Punkt, Badasses <br>\r\nz\u00E4hlen zwei)\r\n</html>");
		txtFallendetruppengesammt.setEditable(false);
		txtFallendetruppengesammt.setBackground(Color.LIGHT_GRAY);
		txtFallendetruppengesammt.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_fallen_troups_looser.add(scrollPane, "cell 5 3 1 2,grow");
		
		list_field_fallen_troops = new JList<Field>(fieldSelectionModel);
		list_field_fallen_troops.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectedFallenTroopsField = list_field_fallen_troops.getSelectedValue();
				updateSelectedFallenTroopField();
			}
		});
		list_field_fallen_troops.setBackground(Color.LIGHT_GRAY);
		list_field_fallen_troops.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list_field_fallen_troops);
		
		JLabel lblNormaleTruppen_1 = new JLabel("<html>Normale<br>Truppen:</html>");
		lblNormaleTruppen_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups_looser.add(lblNormaleTruppen_1, "cell 1 4 2 1");
		
		spinner_normal_troups = new JSpinner();
		spinner_normal_troups.setEnabled(false);
		spinner_normal_troups.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateFallenTroops();
			}
		});
		panel_fallen_troups_looser.add(spinner_normal_troups, "cell 3 4,growx");
		
		JLabel lblBadassTruppen_1 = new JLabel("<html>Badass<br>Truppen:</html>");
		lblBadassTruppen_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_fallen_troups_looser.add(lblBadassTruppen_1, "cell 1 5 2 1");
		
		spinner_badass_troups = new JSpinner();
		spinner_badass_troups.setEnabled(false);
		spinner_badass_troups.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateFallenTroops();
			}
		});
		panel_fallen_troups_looser.add(spinner_badass_troups, "cell 3 5,growx");
		
		btnAuswahlZurcksetzen_1 = new JButton("Auswahl zur\u00FCcksetzen");
		btnAuswahlZurcksetzen_1.setEnabled(false);
		btnAuswahlZurcksetzen_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFallenTroops();
			}
		});
		btnAuswahlZurcksetzen_1.setBackground(Color.GRAY);
		panel_fallen_troups_looser.add(btnAuswahlZurcksetzen_1, "cell 5 5,alignx center");
		
		btnBesttigen = new JButton("Best\u00E4tigen");
		btnBesttigen.setEnabled(false);
		btnBesttigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmFallenTroopSelection();
			}
		});
		panel_fallen_troups_looser.add(btnBesttigen, "cell 0 7 7 1,alignx center");
		btnBesttigen.setBackground(Color.GRAY);
	}
	
	@Override
	public void receiveSelectedHero(Hero hero) {
		if (hero != null) {
			Fight fight = game.getFightManager().getCurrentFight();
			if (fight.getAttackingPlayer().equals(game.getLocalUser())) {
				fight.setAttackingHero(hero);
				fight.setAttackingHeroChosen(true);
				fight.setUseAttackingHeroEffect(rdbtnEffektAttacker.isSelected());
				btnHeldAuswhlen.setEnabled(false);
				btnKeinenHeldenVerwenden.setEnabled(false);
			}
			else if (fight.getDefendingPlayer() != null && fight.getDefendingPlayer().equals(game.getLocalUser())) {
				fight.setDefendingHero(hero);
				fight.setDefendingHeroChosen(true);
				fight.setUseDefendingHeroEffect(rdbtnEffectDefender.isSelected());
				btnHeldAuswhlen_1.setEnabled(false);
				btnKeinenHeldenVerwenden_1.setEnabled(false);
			}
			else {
				throw new IllegalArgumentException("Selecting player is neither attacker nor defender.");
			}
			//the information is sent but hold back by the frame
			game.getFightManager().update();
		}
	}
	
	private void selectHero() {
		if (game.getFightManager().getCurrentFight() != null && !game.getFightManager().getCurrentFight().isRetreatAnnounced()) {
			SelectHeroCardFrame frame = game.getGameFrame().getSelectHeroCardFrame();
			frame.setVisible(true);
			frame.requestFocus();
			frame.setCardSelectionEnabled(true, this);			
		}
	}
	private void selectEmptyHero() {
		if (game.getFightManager().getCurrentFight() != null && !game.getFightManager().getCurrentFight().isRetreatAnnounced()) {
			Fight fight = game.getFightManager().getCurrentFight();
			if (fight.getAttackingPlayer().equals(game.getLocalUser())) {
				fight.setAttackingHero(null);
				fight.setAttackingHeroChosen(true);
				fight.setUseAttackingHeroEffect(false);
				btnHeldAuswhlen.setEnabled(false);
				btnKeinenHeldenVerwenden.setEnabled(false);
			}
			else if (fight.getDefendingPlayer() != null && fight.getDefendingPlayer().equals(game.getLocalUser())) {
				fight.setDefendingHero(null);
				fight.setDefendingHeroChosen(true);
				fight.setUseDefendingHeroEffect(false);
				btnHeldAuswhlen_1.setEnabled(false);
				btnKeinenHeldenVerwenden_1.setEnabled(false);
			}
			else {
				throw new IllegalArgumentException("Selecting player is neither attacker nor defender.");
			}
			game.getFightManager().update();
		}
	}
	
	private void selectRetreatField() {
		Fight fight = game.getFightManager().getCurrentFight();
		if (list_rueckzug.getSelectedIndex() == -1 && !fieldRetreadModel.isEmpty()) {
			new ErrorDialog("Du musst ein Feld auswählen um den Rückzug zu befehlen. ").setVisible(true);
		}
		else {
			if (fieldRetreadModel.isEmpty()) {
				fight.setRetreatField(null);
				fight.setRetreatFieldChosen(true);
				game.getFightManager().update();
			}
			else {
				fight.setRetreatField(list_rueckzug.getSelectedValue());
				fight.setRetreatFieldChosen(true);
				game.getFightManager().update();
			}
			btnAuswhlen.setEnabled(false);
		}
	}
	
	private void confirmFallingTroopSelection() {
		Fight fight = game.getFightManager().getCurrentFight();
		if (fallingTroopsLeft > 0) {
			new ErrorDialog("Du kannst die Fallenden Truppen nicht bestätigen wenn noch Truppen übrig sind.\n\nGnade ist hier unangebracht (und nicht erlaubt).").setVisible(true);
		}
		else if (fallingTroopsLeft < 0) {
			new ErrorDialog("Du kannst nicht mehr Truppen töten als du insgesammt ausgewählt hast.\n\nWenn alle auf einmal draufgehn wird Dein nächster Zug doch total langweilig.").setVisible(true);
		}
		else {
			fight.setFallingTroopsTotal(fallingTroops);
			fight.setFallingTroopsLooser(fallingTroopsLooser);
			fight.setFallingTroopsSupport(fallingSupportTroops);
			fight.setFallingTroopsChosen(true);
			game.getFightManager().update();
			//disable the components
			txtbrig.setText("0");
			btnBesttigen_1.setEnabled(false);
			btnAuswahlZurcksetzen.setEnabled(false);
			spinner_fallende_truppen_unterstuetzer.setEnabled(false);
			spinner_fallende_truppen_gesammt.setEnabled(false);
			spinner_fallende_truppen_verlierer.setEnabled(false);
		}
	}
	
	private void confirmFallenTroopSelection() {
		Fight fight = game.getFightManager().getCurrentFight();
		boolean fallenTroopsSelected = true;
		boolean toManyFallenTroopsSelected = false;
		int fallingTroops;
		int[] fallenTroopCount;
		for (Field field : fieldsFallenTroops) {
			/*if (fight.getAttackingField().equals(field)) {
				fallingTroops = fight.getFallingTroopsTotal();
			}
			else if (fight.getDefendingField().equals(field)) {
				fallingTroops = fight.getFallingTroopsLooser();
			}*/
			if (fight.getWinningField().equals(field)) {
				fallingTroops = fight.getFallingTroopsTotal();
			}
			else if (fight.getLoosingField().equals(field)) {
				fallingTroops = fight.getFallingTroopsLooser();
			}
			else {
				int[] troops = fight.getFallenTroops().get(field);
				if (troops == null) {
					troops = new int[2];
					fight.getFallenTroops().put(field, troops);
				}
				fallingTroops = troops[0] + troops[1];
			}
			fallenTroopCount = fallenTroops.get(field);
			if (fallenTroopCount == null) {
				fallenTroopCount = new int[2];
				fallenTroops.put(field, fallenTroopCount);
			}
			fallenTroopsSelected &= fallingTroops <= fallenTroopCount[0] + 2*fallenTroopCount[1];//minimum amount selected
			toManyFallenTroopsSelected |= (fallingTroops - fallenTroopCount[0] - 2*fallenTroopCount[1]) < -1;//maximum of 1 more than needed selected
		}
		if (!fallenTroopsSelected) {
			new ErrorDialog("Du hast in (mindestens) einem Feld zu wenige Truppen ausgewählt die fallen.").setVisible(true);
		}
		else if (toManyFallenTroopsSelected) {
			new ErrorDialog("Du hast in (mindestens) einem Feld zu viele Truppen ausgewählt die fallen.\n\nLass deinen Gegnern doch auch noch was zu tun übrig.").setVisible(true);
		}
		else if (fallenTroops.isEmpty()) {
			new ErrorDialog("Du hast an diesem Kampf garnicht teilgenommen.\n\nDu musst schon kämpfen wenn du willst das deine Truppen ins Gras beißen.").setVisible(true);
		}
		else {
			boolean onlyNeutralFallenTroops = true;
			for (Field field : fallenTroops.keySet()) {
				if (field != null) {
					onlyNeutralFallenTroops &= field.getAffiliation() == null;
				}
			}
			if (onlyNeutralFallenTroops) {
				new ErrorDialog("Du hast an diesem Kampf garnicht teilgenommen.\n\nDu musst schon kämpfen wenn du willst das deine Truppen ins Gras beißen.").setVisible(true);
			}
			else {
				fight.addFallenTroops(fallenTroops);
				fight.setFallenTroopsChosen(true);
				game.getFightManager().update();
				//disable the components
				btnBesttigen.setEnabled(false);
				btnAuswahlZurcksetzen_1.setEnabled(false);
				spinner_normal_troups.setEnabled(false);
				spinner_badass_troups.setEnabled(false);				
			}
		}
	}
	
	public void clearAll() {
		//reset the stored fields
		fallingSupportTroops = new HashMap<Field, Integer>();
		fallingTroops = 0;
		fallingTroopsLooser = 0;
		fallingTroopsLeft = 0;
		selectedFallenTroopsField = null;
		fallenTroops = new HashMap<Field, int[]>();
		fieldsFallenTroops = new ArrayList<Field>();
		//clear all list models
		fieldNeighboursSupportModel.removeAllElements();
		fieldDeniedSupportModel.removeAllElements();
		fieldAttackerSupportModel.removeAllElements();
		fieldDefenderSupportModel.removeAllElements();
		fieldRetreadModel.removeAllElements();
		fieldFallingSupportModel.removeAllElements();
		fieldSelectionModel.removeAllElements();
		txtAngreifer.setText("");
		txtVerteidiger.setText("");
		txtTruppenangreifer.setText("");
		txtTruppenverteidiger.setText("");
		txtUntersttzerangreifer.setText("");
		txtUntersttzerverteidiger.setText("");
		txtAngreifendesfeld.setText("");
		txtVerteidigendesFeld.setText("");
		txtHeroAttacker.setText("");
		txtHerodefender.setText("");
		txtHeroAttackerAtk.setText("");
		txtHeroAttackerDef.setText("");
		txtHeroDefenderAtk.setText("");
		txtHeroDefenderDef.setText("");
		txtSieger.setText("");
		txtVerlierer.setText("");
		txtFallendetruppen.setText("");
		txtFallendetruppenbis.setText("");
		txtFallendetruppengesammt.setText("");
		txtPhase.setText("");
		txtbrig.setText("");
		txtFallendeTruppen.setText("");
		txtrHeroEffectAttacker.setText("");
		txtrHeroEffectDefender.setText("");
	}
	
	public void update() {
		disableAll();
		updateFightInfo();
		updateSupportLists();
		updateHeroInfo();
		updateFightEnd();
		updateRetreatField();
		updateFallingTroops();
		updateFallenTroopSelection();
		updateSelectedFallenTroopField();
	}
	
	private void updateFightInfo() {
		Fight fight = game.getFightManager().getCurrentFight();
		if (fight != null) {
			txtAngreifer.setText(fight.getAttackingPlayer().getUsername());
			if (fight.getDefendingPlayer() != null) {
				txtVerteidiger.setText(fight.getDefendingPlayer().getUsername());				
			}
			else {
				if (!fight.getDefendingField().getTroops().isEmpty()) {
					txtVerteidiger.setText(fight.getDefendingField().getTroops().get(0).getName());
				}
				else {
					txtVerteidiger.setText("Skags (Neutral)");
				}
			}
			txtAngreifendesfeld.setText(fight.getAttackingField().getName());
			txtVerteidigendesFeld.setText(fight.getDefendingField().getName());
			txtTruppenangreifer.setText(Integer.toString(fight.getCurrentAttackingStrength()));
			txtTruppenverteidiger.setText(Integer.toString(fight.getCurrentDefendingStrength()));
			txtUntersttzerangreifer.setText(Integer.toString(fight.getAttackingSupportStrength()));
			txtUntersttzerverteidiger.setText(Integer.toString(fight.getDefendingSupportStrength()));
			switch (fight.getBattleState()) {
				case Fight.STATE_SUPPORT:
					txtPhase.setText("Verstärkung anfordern");
					break;
				case Fight.STATE_HEROS:
					txtPhase.setText("Helden einsetzen");
					break;
				case Fight.STATE_RETREAT_FIELD:
					txtPhase.setText("Rückzugsfeld bestimmen");
					break;
				case Fight.STATE_FALLEN_TROOP_SELECTION:
					txtPhase.setText("Fallende Truppen auswählen");
					break;
				case Fight.STATE_FALLEN_TROOP_REMOVING:
					txtPhase.setText("Gefallene Truppen vom Feld nehmen");
					break;
				case Fight.STATE_FIGHT_ENDED:
					txtPhase.setText("Kampf Beendet");
					break;
				default:
					txtPhase.setText("Unknown state");
					break;
			}
		}
		else {
			txtAngreifer.setText("");
			txtVerteidiger.setText("");
			txtAngreifendesfeld.setText("");
			txtVerteidigendesFeld.setText("");
			txtTruppenangreifer.setText("");
			txtTruppenverteidiger.setText("");
			txtUntersttzerangreifer.setText("");
			txtUntersttzerverteidiger.setText("");
			txtPhase.setText("");
		}
	}
	
	private void updateSupportLists() {
		Fight fight = game.getFightManager().getCurrentFight();
		fieldNeighboursSupportModel.removeAllElements();
		fieldDeniedSupportModel.removeAllElements();
		fieldAttackerSupportModel.removeAllElements();
		fieldDefenderSupportModel.removeAllElements();
		if (fight != null) {
			for (Field field : fight.getPossibleSupporters()) {
				fieldNeighboursSupportModel.addElement(field);
			}
			for (Field field : fight.getSupportRejections()) {
				fieldDeniedSupportModel.addElement(field);
			}
			for (Field field : fight.getAttackSupporters()) {
				fieldAttackerSupportModel.addElement(field);
			}
			for (Field field : fight.getDefenceSupporters()) {
				fieldDefenderSupportModel.addElement(field);
			}
		}
	}
	
	private void updateHeroInfo() {
		Fight fight = game.getFightManager().getCurrentFight();
		txtHeroAttacker.setText("");
		txtHeroAttackerAtk.setText("");
		txtHeroAttackerDef.setText("");
		txtrHeroEffectAttacker.setText("");
		txtHerodefender.setText("");
		txtHeroDefenderAtk.setText("");
		txtHeroDefenderDef.setText("");
		txtrHeroEffectDefender.setText("");
		if (fight != null && fight.getBattleState() >= Fight.STATE_HEROS) {
			if (fight.getAttackingPlayer().equals(game.getLocalUser()) && !fight.isAttackingHeroChosen()) {
				btnHeldAuswhlen.setEnabled(true);
				btnKeinenHeldenVerwenden.setEnabled(true);				
			}
			else if (fight.getDefendingPlayer() != null && fight.getDefendingPlayer().equals(game.getLocalUser()) && !fight.isDefendingHeroChosen() && !fight.isRetreatAnnounced()) {
				btnHeldAuswhlen_1.setEnabled(true);
				btnKeinenHeldenVerwenden_1.setEnabled(true);				
			}
			Hero attackingHero = fight.getAttackingHero();
			Hero defendingHero = fight.getDefendingHero();
			//only show the players own choice
			if (attackingHero != null) {
				if (fight.getBattleState() > Fight.STATE_HEROS || fight.getAttackingPlayer().equals(game.getLocalUser())) {
					txtHeroAttacker.setText(attackingHero.getName());
					txtHeroAttackerAtk.setText(Integer.toString(attackingHero.getAttack()));
					txtHeroAttackerDef.setText(Integer.toString(attackingHero.getDefence()));
					txtrHeroEffectAttacker.setText(attackingHero.getEffectDescription());					
				}
			}
			if (defendingHero != null) {
				if (fight.getBattleState() > Fight.STATE_HEROS || (fight.getDefendingPlayer() != null && fight.getDefendingPlayer().equals(game.getLocalUser()))) {
					txtHerodefender.setText(fight.getDefendingHero().getName());
					txtHeroDefenderAtk.setText(Integer.toString(defendingHero.getAttack()));
					txtHeroDefenderDef.setText(Integer.toString(defendingHero.getDefence()));
					txtrHeroEffectDefender.setText(defendingHero.getEffectDescription());					
				}
			}
		}
	}
	
	private void updateFightEnd() {
		Fight fight = game.getFightManager().getCurrentFight();
		if (fight != null && fight.getBattleState() >= Fight.STATE_RETREAT_FIELD) {
			switch (fight.getWinner()) {
				case Fight.ATTACKERS:
					if (fight.getDefendingPlayer() != null) {
						txtVerlierer.setText(fight.getDefendingPlayer().getUsername());						
					}
					else {
						txtVerlierer.setText(fight.getDefendingField().getTroops().get(0).getName());
					}
					txtSieger.setText(fight.getAttackingPlayer().getUsername());
					break;
				case Fight.DEFENDERS:
					if (fight.getDefendingPlayer() != null) {
						txtSieger.setText(fight.getDefendingPlayer().getUsername());						
					}
					else {
						txtSieger.setText(fight.getDefendingField().getTroops().get(0).getName());
					}
					txtVerlierer.setText(fight.getAttackingPlayer().getUsername());
					break;
			}
			if (fight.getDefendingPlayer() != null) {
				int[] fallingTroops = fight.calculateFallingTroops();
				txtFallendetruppen.setText(Integer.toString(fallingTroops[0]));
				txtFallendetruppenbis.setText(Integer.toString(fallingTroops[1]));				
			}
		}
	}
	
	private void updateRetreatField() {
		fieldRetreadModel.removeAllElements();
		Fight fight = game.getFightManager().getCurrentFight();
		if (fight != null && fight.getBattleState() == Fight.STATE_RETREAT_FIELD && fight.getLoosingPlayer() != null && fight.getLoosingPlayer().equals(game.getLocalUser())) {
			for (Field field : fight.calculateRetreatFields()) {
				fieldRetreadModel.addElement(field);
			}
			btnAuswhlen.setEnabled(true);
		}
	}
	
	private void updateFallingTroops() {
		Fight fight = game.getFightManager().getCurrentFight();
		int selectedField = list_support_field.getSelectedIndex();
		fieldFallingSupportModel.removeAllElements();
		if (fight != null && fight.getWinningPlayer() != null && fight.getWinningPlayer().equals(game.getLocalUser()) && fight.getBattleState() == Fight.STATE_FALLEN_TROOP_SELECTION) {
			int[] fallingTroops = fight.calculateFallingTroops();
			//int maxFallingSupport = fight.calculateMaxFallingSupportTroops();
			spinner_fallende_truppen_gesammt.setEnabled(true);
			spinner_fallende_truppen_gesammt.setModel(new SpinnerNumberModel(fallingTroops[0], fallingTroops[0], fallingTroops[1], 1));
			spinner_fallende_truppen_verlierer.setEnabled(true);
			spinner_fallende_truppen_verlierer.setModel(new SpinnerNumberModel(fallingTroops[0], fallingTroops[0], fallingTroops[1], 1));
			spinner_fallende_truppen_unterstuetzer.setEnabled(true);
			//spinner_fallende_truppen_unterstuetzer.setModel(new SpinnerNumberModel(0, 0, maxFallingSupport, 1));
			List<Field> supportLoosers = fight.getLoosingSupporters();
			if (supportLoosers != null) {
				for (Field field : supportLoosers) {
					fieldFallingSupportModel.addElement(field);
				}
				list_support_field.setSelectedIndex(selectedField);
			}
			updateFallingTroopSpinnerModels();
			updateFallingTroopsLeft();
			btnBesttigen_1.setEnabled(true);
			btnAuswahlZurcksetzen.setEnabled(true);
		}
	}
	private void updateFallingTroopSpinnerModels() {
		Fight fight = game.getFightManager().getCurrentFight();
		if (fight != null && fight.getBattleState() >= Fight.STATE_FALLEN_TROOP_SELECTION) {
			int[] fallingTroops = fight.calculateFallingTroops();
			int fallingTroopsTotal = (Integer) spinner_fallende_truppen_gesammt.getValue();
			int current = (Integer) spinner_fallende_truppen_verlierer.getValue();
			spinner_fallende_truppen_verlierer.setModel(new SpinnerNumberModel(current, fallingTroopsTotal/2, fallingTroops[1], 1));
			current = (Integer) spinner_fallende_truppen_unterstuetzer.getValue();
			if (selectedFallenSupportTroopsField != null) {
				int maxFallingSupport = fight.calculateMaxFallingSupportTroops(fallingTroopsTotal, selectedFallenSupportTroopsField);
				current = Math.max(current, 0);
				current = Math.min(current, maxFallingSupport);
				spinner_fallende_truppen_unterstuetzer.setModel(new SpinnerNumberModel(current, 0, maxFallingSupport, 1));
			}
		}
	}
	private void updateFallingTroopSupportSelection(Field field) {
		updateFallingTroopSpinnerModels();
		Integer troops = fallingSupportTroops.get(field);
		if (troops != null) {
			spinner_fallende_truppen_unterstuetzer.setValue(fallingSupportTroops.get(field));
		}
		else {
			spinner_fallende_truppen_unterstuetzer.setValue(0);			
		}
	}
	private void updateFallingTroopsLeft() {
		fallingTroops = (Integer) spinner_fallende_truppen_gesammt.getValue();
		fallingTroopsLooser = (Integer) spinner_fallende_truppen_verlierer.getValue(); 
		fallingTroopsLeft = fallingTroops - fallingTroopsLooser;
		for (Field field : fallingSupportTroops.keySet()) {
			fallingTroopsLeft -= fallingSupportTroops.get(field);
		}
		txtbrig.setText(Integer.toString(fallingTroopsLeft));
	}
	private void clearFallingSupportTroops() {
		fallingSupportTroops = new HashMap<Field, Integer>();
		spinner_fallende_truppen_unterstuetzer.setValue(0);
		updateFallingTroopsLeft();
	}
	
	private void updateFallenTroopSelection() {
		Fight fight = game.getFightManager().getCurrentFight();
		fieldsFallenTroops = new ArrayList<Field>();
		fieldSelectionModel.removeAllElements();
		if (fight != null && fight.getBattleState() == Fight.STATE_FALLEN_TROOP_REMOVING) {
			//check if the fallen troops contain the one of this player's fields
			boolean fallenTroopsChosen = false;
			if (fight.getFallenTroops() != null) {
				for (Field field : fight.getFallenTroops().keySet()) {
					//attacking field is mine and is in the field list
					fallenTroopsChosen |= fight.getAttackingField().getAffiliation().equals(game.getLocalUser()) && field.getName().equals(fight.getAttackingField().getName());
					//defending field is mine and is in the field list
					fallenTroopsChosen |= fight.getDefendingField().getAffiliation() != null && fight.getDefendingField().getAffiliation().equals(game.getLocalUser()) && field.getName().equals(fight.getDefendingField().getName());//TODO nullpointer here (?!)						
					//a support field that is mine is in the field list
					for (Field supportField : fight.getFallingTroopsSupport().keySet()) {
						fallenTroopsChosen |= field.getName().equals(supportField.getName()) && supportField.getAffiliation() != null && supportField.getAffiliation().equals(game.getLocalUser());
					}
				}
			}
			if (!fallenTroopsChosen) {
				spinner_normal_troups.setEnabled(true);
				spinner_badass_troups.setEnabled(true);
				if (fight.getAttackingField().getAffiliation().equals(game.getLocalUser())) {
					fieldSelectionModel.addElement(fight.getAttackingField());
					fieldsFallenTroops.add(fight.getAttackingField());
				}
				else if (fight.getDefendingField().getAffiliation() != null && fight.getDefendingField().getAffiliation().equals(game.getLocalUser())) {
					fieldSelectionModel.addElement(fight.getDefendingField());
					fieldsFallenTroops.add(fight.getDefendingField());
				}
				Map<Field, Integer> fallingTroopsSupport = fight.getFallingTroopsSupport();
				for (Field field : fallingTroopsSupport.keySet()) {
					if (field.getAffiliation().equals(game.getLocalUser())) {
						fieldSelectionModel.addElement(field);
						fieldsFallenTroops.add(field);
					}
				}
				btnBesttigen.setEnabled(true);
				btnAuswahlZurcksetzen_1.setEnabled(true);				
			}
		}
	}
	private void clearFallenTroops() {
		fallenTroops = new HashMap<Field, int[]>();
		updateFallenTroops();
	}
	private void updateFallenTroops() {
		int fallenNormalTroops = (Integer) spinner_normal_troups.getValue(); 
		int fallenBadassTroops = ((Integer) spinner_badass_troups.getValue()); 
		int fallenTroops = fallenNormalTroops + 2*fallenBadassTroops;
		txtFallendetruppengesammt.setText(Integer.toString(fallenTroops));
		int[] troops = this.fallenTroops.get(selectedFallenTroopsField);
		if (troops == null) {
			troops = new int[2];
			this.fallenTroops.put(selectedFallenTroopsField, troops);
		}
		troops[0] = fallenNormalTroops;
		troops[1] = fallenBadassTroops;
	}
	private void updateSelectedFallenTroopField() {
		Fight fight = game.getFightManager().getCurrentFight();
		if (fight != null && selectedFallenTroopsField != null) {
			int[] troops = fallenTroops.get(selectedFallenTroopsField);
			if (troops == null) {
				troops = new int[2];
				fallenTroops.put(selectedFallenTroopsField, troops);
			}
			int [] maxFallenTroops = new int[] {selectedFallenTroopsField.getNormalTroops(), selectedFallenTroopsField.getBadassTroops()};
			if (selectedFallenTroopsField.equals(fight.getAttackingField())) {
				//only the attacking troops can fall
				maxFallenTroops[0] = fight.getAttackingNormalTroops();
				maxFallenTroops[1] = fight.getAttackingBadassTroops();
			}
			spinner_normal_troups.setModel(new SpinnerNumberModel(troops[0], 0, maxFallenTroops[0], 1));
			spinner_badass_troups.setModel(new SpinnerNumberModel(troops[1], 0, maxFallenTroops[1], 1));
			if (fight.getWinningField().equals(selectedFallenTroopsField)) {
				txtFallendeTruppen.setText(Integer.toString(fight.getFallingTroopsTotal()));
			}
			else if (fight.getLoosingField().equals(selectedFallenTroopsField)) {
				txtFallendeTruppen.setText(Integer.toString(fight.getFallingTroopsLooser()));
			}
			else {
				txtFallendeTruppen.setText(Integer.toString(fight.getFallingTroopsSupport().get(selectedFallenTroopsField)));
			}
			/*if (fight.getAttackingField().equals(selectedFallenTroopsField)) {
				txtFallendeTruppen.setText(Integer.toString(fight.getFallingTroopsTotal()));
			}
			else if (fight.getDefendingField().equals(selectedFallenTroopsField)) {
				txtFallendeTruppen.setText(Integer.toString(fight.getFallingTroopsLooser()));
			}
			else {
				txtFallendeTruppen.setText(Integer.toString(fight.getFallingTroopsSupport().get(selectedFallenTroopsField)));
			}*/
			updateFallenTroops();
		}
	}
	
	private void disableAll() {
		btnAuswhlen.setEnabled(false);
		btnHeldAuswhlen.setEnabled(false);
		btnKeinenHeldenVerwenden.setEnabled(false);
		btnKeinenHeldenVerwenden_1.setEnabled(false);
		btnHeldAuswhlen_1.setEnabled(false);
		btnAuswahlZurcksetzen.setEnabled(false);
		btnAuswahlZurcksetzen_1.setEnabled(false);
		btnBesttigen_1.setEnabled(false);
		btnBesttigen.setEnabled(false);
		spinner_fallende_truppen_gesammt.setEnabled(false);
		spinner_fallende_truppen_verlierer.setEnabled(false);
		spinner_fallende_truppen_unterstuetzer.setEnabled(false);
		spinner_normal_troups.setEnabled(false);
		spinner_badass_troups.setEnabled(false);
	}
}