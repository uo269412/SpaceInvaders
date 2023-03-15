package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import logic.Board;
import logic.Game;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnDice;
	private JLabel lblSpaceShip;
	private JLabel lblScore;
	private JLabel lblEarth;
	private JTextField txtScore;
	private JPanel pnShots;
	private JPanel pnBoard;

//	private ShootButton sB = null;

	private Game game = new Game();
	private JMenuBar menuBar;
	private JMenu mnGame;
	private JMenu mnHelp;
	private JMenuItem mntmNewGame;
	private JSeparator separator;
	private JMenuItem mntmExit;
	private JMenuItem mntmContents;
	private JSeparator separator_1;
	private JMenuItem mntmAbout;
	private JButton btnCell;

	private ChangeShots changeShots = null;

	private DoDrag dd = new DoDrag();
	private MyPropertyChangeListener mpc = new MyPropertyChangeListener();
	private JMenu mnOptions;
	private JMenuItem mntmChangeNumberOf;

	class ShootButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			shoot(Integer.parseInt(((JButton) e.getSource()).getActionCommand()));
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
//		sB = new ShootButton();
		setBackground(Color.BLACK);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/img/invader.jpg")));
		setTitle("Space Invaders");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 400);
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getBtnDice());
		contentPane.add(getLblSpaceShip());
		contentPane.add(getLblScore());
		contentPane.add(getLblEarth());
		contentPane.add(getTxtScore());
		contentPane.add(getPnShots());
		contentPane.add(getPnBoard());

		txtScore.setText(String.valueOf(game.getScore()));
		enableBoard(false);
	}

	public Game getGame() {
		return game;
	}

	private void openShotsWindow() {
		changeShots = new ChangeShots(this);
		changeShots.setModal(true);
		changeShots.setLocationRelativeTo(this);
		changeShots.setVisible(true);
	}

	private void enableBoard(boolean state) {
		for (Component c : pnBoard.getComponents()) {
			c.setEnabled(state);
		}
	}

	private JLabel newShot() {
		JLabel shot = new JLabel();
		shot.setBorder(new LineBorder(Color.GREEN, 1));
		shot.setIcon(ImageFactory.getImage());
		shot.addMouseListener(dd);
		shot.setForeground(Color.RED);
		shot.setTransferHandler(new TransferHandler("foreground"));
		return shot;
	}

	private JButton newCell(int i) {
		btnCell = new JButton("");
		btnCell.setActionCommand(String.valueOf(i));
		btnCell.setBorder(new LineBorder(Color.BLUE, 2));
		btnCell.setBackground(Color.BLACK);
//		btnCell.addActionListener(sB);
		btnCell.addPropertyChangeListener(mpc);
		btnCell.setTransferHandler(new TransferHandler("foreground"));
		return btnCell;
	}

	private void paintCells() {
		for (int i = 0; i <= Game.BOARD_SIZE; i++) {
			pnBoard.add(newCell(i));
		}
		validate();
	}

	private void paintShots() {
		for (int i = 0; i < game.getShots(); i++) {
			pnShots.add(newShot());
		}
		validate();
	}

	private void resetCells() {
		for (int i = 0; i <= Game.BOARD_SIZE; i++) {
			((JButton) pnBoard.getComponent(i)).setForeground(Color.black);
		}
		validate();
	}

	private void resetShots() {
		pnShots.removeAll();
		pnShots.repaint();
		validate();
	}

	private void initGame() {
		game.launch();
		paintShots();
		btnDice.setEnabled(false);
		enableBoard(true);
	}

	private void removeShot() {
		pnShots.remove(0);
		pnShots.repaint();
	}

	private void paintCell(int position) {
		ImageIcon icon = ImageFactory.getImagen(game.getBoard().getCells()[position]);
		((JButton) pnBoard.getComponent(position)).setIcon(icon);
		((JButton) pnBoard.getComponent(position)).setDisabledIcon(icon);
		((JButton) pnBoard.getComponent(position)).setBackground(Color.black);
	}

	private void paintBoard() {
		for (int i = 0; i < Board.DIM; i++) {
			paintCell(i);
		}
	}

	private void paintBlack() {
		for (int i = 0; i < Board.DIM; i++) {
			((JButton) pnBoard.getComponent(i)).setIcon(null);
		}
	}

	private void updateStateOfTheGame(int position) {
		txtScore.setText(String.valueOf(game.getScore()));
		removeShot();
		paintCell(position);
		if (game.isShipProtected()) {
			getLblSpaceShip().setIcon(new ImageIcon(MainWindow.class.getResource("/img/spaceshipShield.png")));
		} else {
			resetSpaceShip();
		}
		if (game.isGameOver()) {
			enableBoard(false);
			paintBoard();
			if (game.isGameOverWin()) {
				JOptionPane.showMessageDialog(this, "The alien was defeated! Hurray!", "Space Invasion",
						JOptionPane.INFORMATION_MESSAGE);

			} else {
				JOptionPane.showMessageDialog(this, "Game Over! You lost!", "Space Invasion",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private void resetSpaceShip() {
		lblSpaceShip.setIcon(new ImageIcon(MainWindow.class.getResource("/img/spaceship.png")));
	}

	private void shoot(int position) {
		game.shoot(position);
		updateStateOfTheGame(position);

	}

	private JButton getBtnDice() {
		if (btnDice == null) {
			btnDice = new JButton("");
			btnDice.setFocusPainted(false);
			btnDice.setDisabledIcon(new ImageIcon(MainWindow.class.getResource("/img/dice.jpg")));
			btnDice.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					initGame();
				}
			});
			btnDice.setToolTipText("Launch the dice");
			btnDice.setBorderPainted(false);
			btnDice.setIcon(new ImageIcon(MainWindow.class.getResource("/img/dice.jpg")));
			btnDice.setBackground(Color.BLACK);
			btnDice.setBounds(10, 8, 106, 113);
		}
		return btnDice;
	}

	private JLabel getLblSpaceShip() {
		if (lblSpaceShip == null) {
			lblSpaceShip = new JLabel("");
			lblSpaceShip.setIcon(new ImageIcon(MainWindow.class.getResource("/img/spaceship.png")));
			lblSpaceShip.setBounds(236, 17, 143, 84);
		}
		return lblSpaceShip;
	}

	private JLabel getLblScore() {
		if (lblScore == null) {
			lblScore = new JLabel("Score");
			lblScore.setHorizontalAlignment(SwingConstants.CENTER);
			lblScore.setFont(new Font("Consolas", Font.PLAIN, 27));
			lblScore.setForeground(Color.WHITE);
			lblScore.setBounds(537, 8, 106, 38);
		}
		return lblScore;
	}

	private JLabel getLblEarth() {
		if (lblEarth == null) {
			lblEarth = new JLabel("");
			lblEarth.setIcon(new ImageIcon(MainWindow.class.getResource("/img/earth.jpg")));
			lblEarth.setBounds(682, 17, 192, 166);
		}
		return lblEarth;
	}

	private JTextField getTxtScore() {
		if (txtScore == null) {
			txtScore = new JTextField();
			txtScore.setEditable(false);
			txtScore.setHorizontalAlignment(SwingConstants.CENTER);
			txtScore.setText("0");
			txtScore.setFont(new Font("Consolas", Font.PLAIN, 30));
			txtScore.setForeground(Color.GREEN);
			txtScore.setBackground(Color.BLACK);
			txtScore.setBounds(547, 57, 96, 35);
			txtScore.setColumns(10);
		}
		return txtScore;
	}

	private JPanel getPnShots() {
		if (pnShots == null) {
			pnShots = new JPanel();
			pnShots.setBorder(new EmptyBorder(0, 0, 0, 0));
			pnShots.setBackground(Color.BLACK);
			pnShots.setBounds(113, 132, 397, 75);
		}
		return pnShots;
	}

	private JPanel getPnBoard() {
		if (pnBoard == null) {
			pnBoard = new JPanel();
			pnBoard.setBorder(new LineBorder(Color.BLUE, 4));
			pnBoard.setBackground(Color.BLUE);
			pnBoard.setBounds(31, 232, 820, 102);
			pnBoard.setLayout(new GridLayout(1, 0, 0, 0));
			paintCells();
		}
		return pnBoard;
	}

	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnGame());
			menuBar.add(getMnOptions());
			menuBar.add(getMnHelp());
		}
		return menuBar;
	}

	private JMenu getMnGame() {
		if (mnGame == null) {
			mnGame = new JMenu("Game");
			mnGame.add(getMntmNewGame());
			mnGame.add(getSeparator());
			mnGame.add(getMntmExit());
		}
		return mnGame;
	}

	private JMenu getMnHelp() {
		if (mnHelp == null) {
			mnHelp = new JMenu("Help");
			mnHelp.add(getMntmContents());
			mnHelp.add(getSeparator_1());
			mnHelp.add(getMntmAbout());
		}
		return mnHelp;
	}

	private JMenuItem getMntmNewGame() {
		if (mntmNewGame == null) {
			mntmNewGame = new JMenuItem("New Game");
			mntmNewGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					resetShots();
					resetCells();
					resetSpaceShip();
					paintBlack();
					game.initialize();
					getTxtScore().setText(String.valueOf(game.getScore()));
					btnDice.setEnabled(true);
				}
			});
			mntmNewGame.setMnemonic('G');
			mntmNewGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		}
		return mntmNewGame;
	}

	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}

	private JMenuItem getMntmExit() {
		if (mntmExit == null) {
			mntmExit = new JMenuItem("Exit");
			mntmExit.setMnemonic('X');
			mntmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
		}
		return mntmExit;
	}

	private JMenuItem getMntmContents() {
		if (mntmContents == null) {
			mntmContents = new JMenuItem("Contents");
			mntmContents.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Functionality still not added", "Space Invasion",
							JOptionPane.INFORMATION_MESSAGE);
				}
			});
			mntmContents.setMnemonic('C');
			mntmContents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		}
		return mntmContents;
	}

	private JSeparator getSeparator_1() {
		if (separator_1 == null) {
			separator_1 = new JSeparator();
		}
		return separator_1;
	}

	private JMenuItem getMntmAbout() {
		if (mntmAbout == null) {
			mntmAbout = new JMenuItem("About");
			mntmAbout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "Space Invasion Ver 0.1" + "\n" + "Made by Javier Carrillo",
							"Space Invasion", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			mntmAbout.setMnemonic('A');
		}
		return mntmAbout;
	}

	class DoDrag extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent arg0) {
			JComponent c = (JComponent) arg0.getSource();
			TransferHandler handler = c.getTransferHandler();
			handler.exportAsDrag(c, arg0, TransferHandler.COPY);
		}
	}

	class MyPropertyChangeListener implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals("foreground") && !(evt.getNewValue().equals(Color.black))) {
				JButton btn = (JButton) evt.getSource();
				int position = Integer.parseInt(btn.getActionCommand());
				shoot(position);

			}
		}
	}

	private JMenu getMnOptions() {
		if (mnOptions == null) {
			mnOptions = new JMenu("Options");
			mnOptions.setMnemonic('O');
			mnOptions.add(getMntmChangeNumberOf());
		}
		return mnOptions;
	}

	private JMenuItem getMntmChangeNumberOf() {
		if (mntmChangeNumberOf == null) {
			mntmChangeNumberOf = new JMenuItem("Change number of shoots ");
			mntmChangeNumberOf.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					openShotsWindow();
				}
			});
			mntmChangeNumberOf.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		}
		return mntmChangeNumberOf;
	}

	public void closeChangeShotsWindow() {
		changeShots.setVisible(false);
		
	}

////while (!conditions) {
////try {
//	number = Integer.parseInt(JOptionPane
//			.showInputDialog("What will be the maximum number of shots? (Current value: "
//					+ game.getNumberOfShots() + ")"));
//} catch (NumberFormatException nfe) {
//	if (number < 6 && number > 2) {
//		conditions = true;
//	}
//}
}
