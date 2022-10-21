package PacDemo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class InputFile extends JFrame {

	private JPanel contentPane;
	private JTextField txtUrl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InputFile frame = new InputFile();
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
	public InputFile() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 613, 351);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtUrl = new JTextField();
		txtUrl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtUrl.setBounds(131, 112, 364, 26);
		contentPane.add(txtUrl);
		txtUrl.setColumns(10);

		JLabel lblNewLabel = new JLabel("URL :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(88, 119, 45, 13);
		contentPane.add(lblNewLabel);

		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File myObj = new File("C://Users//ADMIN//Desktop//PBL4//PBL4//src//PacDemo//data.txt");
					Scanner myReader = new Scanner(myObj);
					int n = myReader.nextInt();
					int[][] Matrix = new int[n][n];
					try {
						for (int i = 0; i < n; i++) {
							for (int j = 0; j < n; j++) {
								Matrix[i][j] = myReader.nextInt();
							}
						}
					} catch (Exception ex) {
						System.out.println(ex);
					}
					;
					myReader.close();
					new Client(Matrix);
				} catch (FileNotFoundException e1) {
					System.out.println("An error occurred.");
					e1.printStackTrace();
				}
			}
		});
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNext.setBounds(262, 195, 85, 21);
		contentPane.add(btnNext);
	}
}
