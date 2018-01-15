package net.jfabricationgames.bunkers_and_badasses.statistic;

import java.awt.BorderLayout;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class GameDetail {
	
	private Map<Integer, GameStatistic> gameStatistics;
	
	public enum Display {
		POSITION(false, false, 0, "Position"),
		POINTS_PLAYER(true, false, 0, "Punkteübersicht"),
		BATTLES_PLAYER(true, false, 0, "Kämpfe"),
		TROOPS_KILLED_TOTAL(true, false, 0, "Getötete Truppen"),
		RESOURCES_PLAYER(true, false, 0, "Resourcen"),
		SUPPORT_PLAYER(true, false, 0, "Unterstützungen"),
		COMMANDS_PLAYER(true, false, 0, "Befehle"),
		POINTS(true, true, 5, "Punkte"),
		POINTS_FIGHT(true, true, 6, "Punkte (Kampf)"),
		POINTS_FIELDS(true, true, 7, "Punkte (Felder)"),
		POINTS_GOALS(true, true, 9, "Punkte (Rundenziele)"),
		POINTS_BONUSES(true, true, 10, "Punkte (Rundenboni)"),
		POINTS_SKILLS(true, true, 11, "Punkte (Skillprofile)"),
		TROOPS_CONTROLLED_END(true, true, 15, "Kontrollierte Truppen (Ende)"),
		TROOPS_CONTROLLED_MAX(true, true, 16, "Kontrollierte Truppen (Maximum)"),
		FIELDS_END(true, true, 17, "Kontrollierte Felder (Ende)"),
		FIELDS_MAX(true, true, 18, "Kontrollierte Felder (Maximum)"),
		REGIONS_END(true, true, 19, "Kontrollierte Regionen (Ende)"),
		REGIONS_MAX(true, true, 21, "Kontrollierte Regionen (Maximum)"),
		REGIONS_VALUE_END(true, true, 20, "Regionen-Wert (Ende)"),
		REGIONS_VALUE_MAX(true, true, 22, "Regionen-Wert (Maximum)"),
		BATTLES_WON(true, true, 23, "Gewonnene Kämpfe"),
		BATTLES_LOST(true, true, 24, "Verlorene Kämpfe"),
		HEROS_USED_BATTLE(true, true, 25, "Helden Eingesetzt (Kampf)"),
		HEROS_USED_EFFECT(true, true, 26, "Helden Eingesetzt (Effekt)"),
		USED_CREDITS(true, true, 27, "Credits Verbraucht"),
		USED_AMMO(true, true, 28, "Munition Verbraucht"),
		USED_ERIDIUM(true, true, 29, "Eridium Verbraucht"),
		BUILDINGS_CREATED(true, true, 30, "Gebäude Errichtet"),
		BUILDINGS_UPGRADED(true, true, 31, "Gebäude Aufgerüstet"),
		BUILDINGS_DESTROYED(true, true, 32, "Gebäude Zerstört"),
		SUPPORT_GIVEN_SELF(true, true, 33, "Unterstützt (Selbst)"),
		SUPPORT_RECEIVED_SELF(true, true, 34, "Unterstützt Erhallten (Selbst)"),
		SUPPORT_GIVEN_OTHER(true, true, 35, "Unterstützt (Andere)"),
		SUPPORT_RECEIVED_OTHER(true, true, 36, "Unterstützung Erhallten (Andere)"),
		SUPPORT_REJECTED(true, true, 37, "Unterstützung Abgelehnt"),
		COMMANDS_RAID(true, true, 38, "Überfall Befehle"),
		COMMANDS_RETREAT(true, true, 39, "Rückzugs Befehle"),
		COMMANDS_MARCH(true, true, 40, "Bewegungs Befehle"),
		COMMANDS_BUILD(true, true, 41, "Aufbau Befehle"),
		COMMANDS_RECRUIT(true, true, 42, "Rekrutierungs Befehle"),
		COMMANDS_COLLECT(true, true, 43, "Resourcen Befehle"),
		COMMANDS_SUPPORT(true, true, 44, "Unterstützungs Befehle"),
		COMMANDS_DEFEND(true, true, 45, "Verteidigungs Befehle");
		
		private final boolean chart;
		private final boolean defaultChart;
		private final String text;
		private final int index;//the index for the default chart data
		
		private Display(boolean chart, boolean defaultChart, int index, String text) {
			this.chart = chart;
			this.defaultChart = defaultChart & chart;//no default chart if there's no char at all
			this.index = index;
			this.text = text;
		}
		
		public boolean isChart() {
			return chart;
		}
		public boolean isDefaultChart() {
			return defaultChart;
		}
		public int getIndex() {
			return index;
		}
		public String getText() {
			return text;
		}
	}
	
	public GameDetail() {
		gameStatistics = new HashMap<Integer, GameStatistic>();
	}
	
	public void addGameStatistic(GameStatistic stats) {
		gameStatistics.put(stats.getUser_id(), stats);
	}
	
	public JPanel generateChart(Display display, int userId) {
		JPanel chartPanel = new JPanel();
		chartPanel.setLayout(new BorderLayout());
		if (display.chart) {
			DefaultPieDataset dataset = new DefaultPieDataset();
			if (display.defaultChart) {
				//create a default chart
				gameStatistics.forEach((id, stats) -> dataset.setValue(stats.getUser().getUsername(), stats.asArray()[display.getIndex()]));
			}
			else {
				GameStatistic stats = gameStatistics.get(userId);
				//create a special chart
				switch (display) {
					case POINTS_PLAYER:
						dataset.setValue("Kämpfe", stats.getPoints_fight());
						dataset.setValue("Felder", stats.getPoints_fields());
						dataset.setValue("Regionen", stats.getPoints_regions());
						dataset.setValue("Rundenziele", stats.getPoints_goals());
						dataset.setValue("Rundenboni", stats.getPoints_bonuses());
						dataset.setValue("Skillprofile", stats.getPoints_skills());
						break;
					case BATTLES_PLAYER:
						dataset.setValue("Kämpfe Gewonnen", stats.getBattles_won());
						dataset.setValue("Kämpfe Verloren", stats.getBattles_lost());
						break;
					case TROOPS_KILLED_TOTAL:
						dataset.setValue("Normale Truppen", stats.getTroops_killed_normal());
						dataset.setValue("Badass Truppen", stats.getTroops_killed_badass());
						dataset.setValue("Neutrale Truppen", stats.getTroops_killed_neutral());
						break;
					case RESOURCES_PLAYER:
						dataset.setValue("Credits Verbraucht", stats.getUsed_credits());
						dataset.setValue("Munition Verbraucht", stats.getUsed_ammo());
						dataset.setValue("Eridium Verbraucht", stats.getUsed_eridium());
						break;
					case SUPPORT_PLAYER:
						dataset.setValue("Unterstützung Erhalten (Selbst)", stats.getSupport_received_self());
						dataset.setValue("Unterstützung Erhalten (Andere)", stats.getSupport_received_other());
						dataset.setValue("Unterstützung Gegeben (Andere)", stats.getSupport_given_other());
						dataset.setValue("Unterstützung Verweigert", stats.getSupport_rejected());
						break;
					case COMMANDS_PLAYER:
						dataset.setValue("Befehle - Überfall", stats.getCommands_raid());
						dataset.setValue("Befehle - Rückzug", stats.getCommands_retreat());
						dataset.setValue("Befehle - Bewegung", stats.getCommands_march());
						dataset.setValue("Befehle - Aufbau", stats.getCommands_build());
						dataset.setValue("Befehle - Rekrutierung", stats.getCommands_recruit());
						dataset.setValue("Befehle - Resourcen", stats.getCommands_resources());
						dataset.setValue("Befehle - Unterstützung", stats.getCommands_support());
						dataset.setValue("Befehle - Verteidigung", stats.getCommands_defense());
						break;
					default:
						throw new IllegalArgumentException("the given Display object is no special chart (" + display.name() + ")");
				}
			}
			JFreeChart chart = ChartFactory.createPieChart3D(display.getText(), dataset, true, true, false);
			PiePlot plot = (PiePlot) chart.getPlot();
			PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0} = {2}", new DecimalFormat("0"), new DecimalFormat("0.0%"));
			plot.setLabelGenerator(generator);
			chartPanel.add(new ChartPanel(chart), BorderLayout.CENTER);
		}
		else {
			//create a panel to inform the user that there is no chart for this
			JLabel label = new JLabel("Keine Grafik vorhanden");
			label.setFont(new Font("Tahoma", Font.BOLD, 16));
			chartPanel.add(label, BorderLayout.CENTER);
		}
		return chartPanel;
	}
	
	public String generateText(Display display, int userId) {
		StringBuilder sb = new StringBuilder();
		sb.append(display.getText());
		sb.append('\n');
		if (display.chart) {
			if (display.defaultChart) {
				gameStatistics.forEach((id, stats) -> sb.append(stats.getUser().getUsername() + ":" + stats.asArray()[display.getIndex()] + "\n"));
			}
			else {
				GameStatistic stats = gameStatistics.get(userId);
				switch (display) {
					case POINTS_PLAYER:
						sb.append("Kämpfe: ");
						sb.append(stats.getPoints_fight());
						sb.append('\n');
						sb.append("Felder: ");
						sb.append(stats.getPoints_fields());
						sb.append('\n');
						sb.append("Regionen: ");
						sb.append(stats.getPoints_regions());
						sb.append('\n');
						sb.append("Rundenziele: ");
						sb.append(stats.getPoints_goals());
						sb.append('\n');
						sb.append("Rundenboni: ");
						sb.append(stats.getPoints_bonuses());
						sb.append('\n');
						sb.append("Skillprofile: ");
						sb.append(stats.getPoints_skills());
						sb.append('\n');
						break;
					case BATTLES_PLAYER:
						sb.append("Kämpfe Gewonnen");
						sb.append(stats.getBattles_won());
						sb.append('\n');
						sb.append("Kämpfe Verloren");
						sb.append(stats.getBattles_lost());
						sb.append('\n');
						break;
					case TROOPS_KILLED_TOTAL:
						sb.append("Normale Truppen");
						sb.append(stats.getTroops_killed_normal());
						sb.append('\n');
						sb.append("Badass Truppen");
						sb.append(stats.getTroops_killed_badass());
						sb.append('\n');
						sb.append("Neutrale Truppen");
						sb.append(stats.getTroops_killed_neutral());
						sb.append('\n');
						break;
					case RESOURCES_PLAYER:
						sb.append("Credits Verbraucht");
						sb.append(stats.getUsed_credits());
						sb.append('\n');
						sb.append("Munition Verbraucht");
						sb.append(stats.getUsed_ammo());
						sb.append('\n');
						sb.append("Eridium Verbraucht");
						sb.append(stats.getUsed_eridium());
						sb.append('\n');
						break;
					case SUPPORT_PLAYER:
						sb.append("Unterstützung Erhalten (Selbst)");
						sb.append(stats.getSupport_received_self());
						sb.append('\n');
						sb.append("Unterstützung Erhalten (Andere)");
						sb.append(stats.getSupport_received_other());
						sb.append('\n');
						sb.append("Unterstützung Gegeben (Andere)");
						sb.append(stats.getSupport_given_other());
						sb.append('\n');
						sb.append("Unterstützung Verweigert");
						sb.append(stats.getSupport_rejected());
						sb.append('\n');
						break;
					case COMMANDS_PLAYER:
						sb.append("Befehle - Überfall");
						sb.append(stats.getCommands_raid());
						sb.append('\n');
						sb.append("Befehle - Rückzug");
						sb.append(stats.getCommands_retreat());
						sb.append('\n');
						sb.append("Befehle - Bewegung");
						sb.append(stats.getCommands_march());
						sb.append('\n');
						sb.append("Befehle - Aufbau");
						sb.append(stats.getCommands_build());
						sb.append('\n');
						sb.append("Befehle - Rekrutierung");
						sb.append(stats.getCommands_recruit());
						sb.append('\n');
						sb.append("Befehle - Resourcen");
						sb.append(stats.getCommands_resources());
						sb.append('\n');
						sb.append("Befehle - Unterstützung");
						sb.append(stats.getCommands_support());
						sb.append('\n');
						sb.append("Befehle - Verteidigung");
						sb.append(stats.getCommands_defense());
						sb.append('\n');
						break;
					default:
						throw new IllegalArgumentException("the given Display object is no special chart (" + display.name() + ")");
				}
			}
		}
		else {
			//only position
			sb.append(gameStatistics.get(userId).getPosition());
		}
		return sb.toString();
	}
}