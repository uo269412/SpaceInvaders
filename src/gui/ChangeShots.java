package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.ImageIcon;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangeShots extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private MainWindow parent = null;
	private JLabel lblShots;
	private JSpinner spinner;
	private JLabel lblImage;

	/**
	 * Create the dialog.
	 * @param mainWindow 
	 */
	
	public MainWindow getParent() {
		return parent;
	}
	public ChangeShots(MainWindow mainWindow) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChangeShots.class.getResource("/img/shoot.png")));
		setTitle("Space Invaders :: Shots Option");
		setBounds(100, 100, 450, 211);
		parent = mainWindow;
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.BLACK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		contentPanel.add(getLblShots());
		contentPanel.add(getSpinner());
		contentPanel.add(getLblImage());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.BLACK);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						parent.getGame().changeShots((int) spinner.getValue());
						parent.closeChangeShotsWindow();
					}
				});
				okButton.setForeground(Color.WHITE);
				okButton.setBackground(Color.GREEN);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						parent.closeChangeShotsWindow();
					}
				});
				cancelButton.setBackground(Color.RED);
				cancelButton.setForeground(Color.WHITE);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	private JLabel getLblShots() {
		if (lblShots == null) {
			lblShots = new JLabel("How many shots do you want to play with?");
			lblShots.setForeground(Color.WHITE);
			lblShots.setFont(new Font("Consolas", Font.PLAIN, 17));
			lblShots.setBounds(29, 11, 395, 54);
		}
		return lblShots;
	}
	private JSpinner getSpinner() {
		if (spinner == null) {
			spinner = new JSpinner();
			spinner.setModel(new SpinnerNumberModel(4, 2, 6, 1));
			spinner.setFont(new Font("Consolas", Font.PLAIN, 15));
			spinner.setBounds(314, 76, 63, 30);
		}
		return spinner;
	}
	private JLabel getLblImage() {
		if (lblImage == null) {
			lblImage = new JLabel("New label");
			lblImage.setIcon(new ImageIcon(ChangeShots.class.getResource("/img/shoot.png")));
			lblImage.setBounds(71, 64, 81, 52);
		}
		return lblImage;
	}
}
