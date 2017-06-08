package net.jfabricationgames.bunkers_and_badasses.help;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import net.miginfocom.swing.MigLayout;

public class HelpMenuFrame extends JFrame {
	
	public static void main(String[] args) {
		new HelpMenuFrame().setVisible(true);
	}
	
	private static final long serialVersionUID = 8954900524712977138L;
	
	private JPanel contentPane;
	private JPanel panel_card;
	
	public HelpMenuFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(HelpMenuFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses - Hilfe");
		setBounds(100, 100, 1000, 600);
		setMinimumSize(new Dimension(1000, 600));
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		contentPane.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[:300px:350px,grow][700px,grow]", "[grow]"));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "cell 0 0,grow");
		
		JTree tree = new JTree();
		tree.setCellRenderer(new JFGCellRenderer());
		tree.setModel(new DefaultTreeModel(generateTree()));
		tree.setBackground(Color.LIGHT_GRAY);
		tree.setShowsRootHandles(true);
		tree.setRootVisible(false);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				HelpTreeNode node = (HelpTreeNode) tree.getLastSelectedPathComponent();
				if (node != null) {
					setHelpPanel(node);
				}
			}
		});
		scrollPane.setViewportView(tree);
		
		panel_card = new JPanel();
		panel.add(panel_card, "cell 1 0,grow");
		panel_card.setLayout(new CardLayout(0, 0));
	}
	
	public void buildHelpPanels(List<HelpContent> contents) {
		for (HelpContent helpContent : contents) {
			if (helpContent.isPanel()) {
				HelpMenuPanel panel = new HelpMenuPanel(helpContent);
				panel_card.add(panel, panel.getPanelName());
			}
		}
	}
	
	private void setHelpPanel(HelpTreeNode node) {
		if (node.isPanel()) {
			CardLayout layout = (CardLayout) panel_card.getLayout();
			layout.show(panel_card, node.getPanelName());	
		}
	}
	
	private DefaultMutableTreeNode generateTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("JTree");
		HelpTreeNode node_1;
		root.add(new HelpTreeNode("Data", "data_1", true));
		node_1 = new HelpTreeNode("Data2", "data2", false);
		node_1.add(new HelpTreeNode("data2_1", "data_2_1", true));
		node_1.add(new HelpTreeNode("data2_2", "data_2_1", true));
		root.add(node_1);
		root.add(new HelpTreeNode("Data3", "data3", true));
		return root;
	}
	
	private class JFGCellRenderer extends DefaultTreeCellRenderer {
		
		private static final long serialVersionUID = 3434791910888547395L;
		
		@Override
	    public Color getBackgroundNonSelectionColor() {
	        return null;
	    }
		
	    @Override
	    public Color getBackgroundSelectionColor() {
	        return Color.GRAY;
	    }
	    
	    @Override
	    public Color getBackground() {
	        return null;
	    }
	}
	
	private class HelpTreeNode extends DefaultMutableTreeNode {
		
		private static final long serialVersionUID = 7758184505313521731L;
		
		private String name;
		private String panelName;
		
		private boolean isPanel;
		
		public HelpTreeNode(String name, String panelName, boolean isPanel) {
			this.name = name;
			this.panelName = panelName;
			this.isPanel = isPanel;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		public String getPanelName() {
			return panelName;
		}
		
		public boolean isPanel() {
			return isPanel;
		}
	}
}