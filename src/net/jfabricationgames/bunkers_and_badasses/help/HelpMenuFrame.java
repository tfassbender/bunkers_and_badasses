package net.jfabricationgames.bunkers_and_badasses.help;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.swing.tree.TreePath;

import net.miginfocom.swing.MigLayout;

public class HelpMenuFrame extends JFrame {
	
	private static final long serialVersionUID = 8954900524712977138L;
	
	private JPanel contentPane;
	private JPanel panel_card;
	private JTree tree;
	
	private List<HelpContent> contents;
	private Map<Integer, HelpTreeNode> helpTreeNodes;
	
	public HelpMenuFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(HelpMenuFrame.class.getResource("/net/jfabricationgames/bunkers_and_badasses/images/jfg/icon.png")));
		setTitle("Bunkers and Badasses - Hilfe");
		setBounds(100, 100, 1000, 600);
		setMinimumSize(new Dimension(1000, 600));
		setLocationRelativeTo(null);
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
		
		tree = new JTree();
		tree.setCellRenderer(new JFGCellRenderer());
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
	
	/**
	 * Build the help panels and the JTree.
	 * 
	 * @param contents
	 * 		The contents from the database.
	 */
	public void buildHelpMenu(List<HelpContent> contents) {
		this.contents = contents;
		buildHelpPanels(contents);
		buildHelpTree(contents);
		setHelpPanel("bunkers_and_badasses");
	}
	
	/**
	 * Builds the panels that include the help content from the database and adds them to the card panel.
	 * 
	 * @param contents
	 * 		The loaded help contents from the database.
	 */
	private void buildHelpPanels(List<HelpContent> contents) {
		for (HelpContent helpContent : contents) {
			if (helpContent.isPanel()) {
				HelpMenuPanel panel = new HelpMenuPanel(helpContent);
				panel_card.add(panel, panel.getPanelName());
			}
		}
	}
	
	/**
	 * Generate the JTree of the help contents.
	 * 
	 * @param contents
	 * 		The loaded contents from the database.
	 */
	private void buildHelpTree(List<HelpContent> contents) {
		helpTreeNodes = new HashMap<Integer, HelpTreeNode>();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		//generate all nodes and add them to the map
		for (HelpContent helpContent : contents) {
			HelpTreeNode helpNode = new HelpTreeNode(helpContent);
			helpTreeNodes.put(helpContent.getId(), helpNode);
		}
		//build the tree from the nodes
		for (Integer key : helpTreeNodes.keySet()) {
			HelpTreeNode node = helpTreeNodes.get(key);
			DefaultMutableTreeNode superNode = helpTreeNodes.get(node.getSuperId());
			if (superNode == null) {
				superNode = root;
			}
			superNode.add(node);
		}
		//set the model and repaint
		tree.setModel(new DefaultTreeModel(root));
		revalidate();
		repaint();
	}
	
	/**
	 * Select the visible help panel by the selected object in the JTree.
	 * 
	 * @param node
	 * 		The selected node.
	 */
	private void setHelpPanel(HelpTreeNode node) {
		if (node.isPanel()) {
			CardLayout layout = (CardLayout) panel_card.getLayout();
			layout.show(panel_card, node.getPanelName());	
		}
	}
	
	public void setHelpPanel(String name) {
		//show the panel
		CardLayout layout = (CardLayout) panel_card.getLayout();
		layout.show(panel_card, name);
		//expand the tree
		for (Integer i : helpTreeNodes.keySet()) {
			HelpTreeNode node = helpTreeNodes.get(i);
			if (node.getPanelName() != null && node.getPanelName().equals(name)) {
				tree.expandPath(new TreePath(node.getPath()));
			}
		}
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
		
		private int superId;
		
		public HelpTreeNode(HelpContent helpContent) {
			this(helpContent.getTitle(), helpContent.getPanelName(), helpContent.isPanel(), helpContent.getSuperId());
		}
		public HelpTreeNode(String name, String panelName, boolean isPanel, int superId) {
			this.name = name;
			this.panelName = panelName;
			this.isPanel = isPanel;
			this.superId = superId;
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
		public int getSuperId() {
			return superId;
		}
	}
	
	public List<HelpContent> getContents() {
		return contents;
	}
}