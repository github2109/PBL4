package PacDemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Client extends JFrame implements Runnable {

	private JPanel contentPane;
	private JLabel lblDistance, lblWay;
	private JTextField txtSource;
	private JTextField txtDestination;
	private JPanel pnMap;
	public Socket soc;
	public DataInputStream dis;
	public DataOutputStream dos;
	int Matrix[][], source = 0, destination;
	JTextArea txtMap;

	public Client(int[][] Matrix) {
		this.Matrix = Matrix;

		try {
			soc = new Socket("localhost", 5000);
			this.dis = new DataInputStream(soc.getInputStream());
			this.dos = new DataOutputStream(soc.getOutputStream());
		} catch (IOException e) {
			System.out.println(e);
		}
		new Thread(this).start();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1020, 558);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Nguồn");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(40, 36, 61, 13);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_1 = new JLabel("Đích");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(127, 36, 61, 13);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);

		txtSource = new JTextField();
		txtSource.setBounds(40, 55, 65, 32);
		txtSource.setColumns(10);

		JButton btOK = new JButton("Chọn");
		btOK.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ObjectOutputStream output;
				try {
					source = Integer.parseInt(txtSource.getText());
					destination = Integer.parseInt(txtDestination.getText());
				} catch (Exception e1) {
					System.out.println(e1);
					return;
				}
				try {
					output = new ObjectOutputStream(dos);
					DataSendSchema obj = new DataSendSchema(Matrix, source, destination);
					output.writeObject(obj);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btOK.setBounds(72, 112, 116, 32);

		JLabel lblNewLabel_2 = new JLabel("Bảng chỉ đường");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(40, 334, 116, 13);

		txtMap = new JTextArea();
		txtMap.setBounds(40, 363, 239, 135);

		JLabel asdad = new JLabel("Đường đi:");
		asdad.setFont(new Font("Tahoma", Font.PLAIN, 15));
		asdad.setBounds(316, 366, 100, 13);

		JLabel asd = new JLabel("Khoảng cách:");
		asd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		asd.setBounds(316, 389, 100, 13);

		JButton btEnd = new JButton("Kết thúc");
		btEnd.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btEnd.setBounds(876, 466, 120, 32);

		txtDestination = new JTextField();
		txtDestination.setBounds(137, 55, 65, 32);
		txtDestination.setColumns(10);
		contentPane.setLayout(null);
		contentPane.add(btOK);
		contentPane.add(lblNewLabel_2);
		contentPane.add(asdad);
		contentPane.add(asd);
		contentPane.add(btEnd);
		contentPane.add(txtMap);
		contentPane.add(lblNewLabel);
		contentPane.add(txtSource);
		contentPane.add(lblNewLabel_1);
		contentPane.add(txtDestination);

		PacDemo.MapPanel mapPanel = new MapPanel(Matrix, source);
		contentPane.add(mapPanel);

		lblWay = new JLabel("");
		lblWay.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblWay.setBounds(414, 257, 263, 13);
		contentPane.add(lblWay);

		lblDistance = new JLabel("");
		lblDistance.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDistance.setBounds(414, 280, 263, 13);
		contentPane.add(lblDistance);
		this.setVisible(true);
	}

	@Override
	public void run() {
		while (true) {
			try {
				ObjectInputStream input = new ObjectInputStream(dis);
				DataReceiveSchema data = (DataReceiveSchema) input.readObject();
				String way = "";
				String map = "";
				int source = Integer.parseInt(txtSource.getText());
				int destination = Integer.parseInt(txtDestination.getText());
				txtMap.setText(map);
				lblWay.setText(way);
				lblDistance.setText(data.Distance[destination] + "");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}

class MapPanel extends JPanel {
	int Matrix[][], source;

	public MapPanel(int Matrix[][], int source) {
		this.Matrix = Matrix;
		this.source = source;
		this.setBounds(298, 10, 680, 322);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	@Override
	protected void paintComponent(Graphics g) {
		int n = Matrix.length;
		Point points[] = new Point[n];
		int temp = n % 2 == 0 ? n : n + 1;
		int gapX = 680 / ((temp - 2) / 2 + 1);
		points[0] = new Point(20,150);
		int x = -325;
		g.fillOval(20,150,10,10);
		for (int i = 1; i < temp / 2; i++) {
			x = x + gapX;
			int y = (int) (150*Math.sqrt(1-x*x/(325*325)));
			g.setColor(Color.BLUE);
			if(i != 1 && n%2 != 0) {
				points[n-i] = new Point(x+325,y+130);
				g.fillOval(x+325, y+130, 10, 10);
			}
			points[i] = new Point(x+325,-1*y+170);
			g.fillOval(x+325, -1*y+170, 10, 10);
		}
		points[temp/2] = new Point(640,150);
		g.fillOval(640, 150, 10, 10);
	}

}