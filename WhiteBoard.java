import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Button;

public class WhiteBoard extends JFrame {
	private boolean serverOn = false;
	private TCPServer server = null;
	private final static int BUTTON_WIDTH = 65;
	private final static int BUTTON_HEIGHT = 30;
	private Canvas canvas = new Canvas(this);
	private JFrame mainFrame;
	private Color selectedColor;
	
	protected JTextField inputField;
	protected JComboBox<String> fontPicker;	
	protected Font font;
	protected JComboBox fontSelect;
	


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WhiteBoard frame = new WhiteBoard();
					frame.setVisible(true);
					//WhiteBoard frame2 = new WhiteBoard();
					//frame2.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the frame.
	 */
	public WhiteBoard() {
		super("WhiteBoardTest");
		mainFrame = this;
		this.setSize(900, 400);
		this.showControls();
		this.showCanvas();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Shows the right hand side of the GUI
	 */
	private void showCanvas() {
		canvas.setBackground(Color.WHITE);
		canvas.setMaximumSize(new Dimension(400, 400));
		canvas.setMinimumSize(new Dimension(400, 400));
		this.getContentPane().add(BorderLayout.CENTER, this.canvas);
	}

	/**
	 * Shows the left hand side of the GUI
	 */
	public void showControls() {
		Box containerBox = Box.createVerticalBox();
		Box addShapeBox = this.getAddShapeBox();
		Box textBox = this.getTextBox();
		JPanel setColorPanel = this.getSetColorPanel();
		Box orderModifierBox = this.getOrderModifierBox();
		Box saveLoadBox = this.getSaveLoadBox();
		Box networkingBox = this.getNetworkingBox();
		JScrollPane tableScrollPane = new JScrollPane(this.getTable());

		containerBox.setPreferredSize(new Dimension(500, 400));
		containerBox.add(addShapeBox);
		containerBox.add(Box.createVerticalStrut(5));
		containerBox.add(textBox);
		containerBox.add(Box.createVerticalStrut(5));
		containerBox.add(setColorPanel);
		containerBox.add(Box.createVerticalStrut(5));
		containerBox.add(orderModifierBox);
		containerBox.add(Box.createVerticalStrut(5));
		containerBox.add(saveLoadBox);
		containerBox.add(Box.createVerticalStrut(5));
		containerBox.add(networkingBox);
		containerBox.add(Box.createVerticalStrut(5));
		containerBox.add(tableScrollPane);
		this.getContentPane().add(BorderLayout.WEST, containerBox);
	}

	private Box getAddShapeBox() {
		Box addShapeBox = Box.createHorizontalBox();
		addShapeBox.setMaximumSize(new Dimension(500, 50));
		//add label
		JLabel infoLabel = new JLabel(" Add Shapes:");
		infoLabel.setFont(new Font("Lucida Grande", Font.ITALIC, 14));
		addShapeBox.add(infoLabel);
		addShapeBox.add(Box.createRigidArea(new Dimension(2, 0)));

		//adds rect btn
		JButton addRectBtn = new JButton("Rect");
		addRectBtn.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		addShapeBox.add(Box.createRigidArea(new Dimension(2, 0)));
		addShapeBox.add(addRectBtn);

		//adds oval btn
		JButton addOvalBtn = new JButton("Oval");
		addOvalBtn.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		addShapeBox.add(Box.createRigidArea(new Dimension(2, 0)));
		addShapeBox.add(addOvalBtn);

		//adds oval btn
		JButton addLineBtn = new JButton("Line");
		addLineBtn.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		addShapeBox.add(Box.createRigidArea(new Dimension(2, 0)));
		addShapeBox.add(addLineBtn);

		//adds text btn
		JButton addTxtBtn = new JButton("Text");
		addTxtBtn.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		addShapeBox.add(Box.createRigidArea(new Dimension(2, 0)));
		addShapeBox.add(addTxtBtn);

		addRectBtn.addActionListener(e -> {
			DRectModel model = new DRectModel();
			this.setShapeDefaults(model);
			canvas.addShape(model);// we add the shape to canvas list
			if(serverOn)
				server.notifyAll("doing something with rect", model);			
			disableTextButtons();
		});
		addOvalBtn.addActionListener(e -> {
			DOvalModel model = new DOvalModel();
			this.setShapeDefaults(model);
			
			disableTextButtons();
			
			canvas.addShape(model);// we add the shape to canvas list
		});
		addLineBtn.addActionListener(e -> {
			DLineModel model = new DLineModel();
			model.setP1(new Point(10, 10));
			model.setP2(new Point(20, 20));
			
			disableTextButtons();
			
			canvas.addShape(model);// we add the shape to canvas list
		});
		
		addTxtBtn.addActionListener(e -> {
			DTextModel model = new DTextModel();
			this.setShapeDefaults(model);
			
			model.setWidth(50);
			
			enableTextButtons();
			fontPicker.setSelectedIndex(50);
			
			model.setText("Hello");
			inputField.setText(model.getText());
			model.setFontName("Dialog");
			model.setFontStyle(Font.PLAIN);
			model.setFontSize(30);

			canvas.addShape(model);
		});
		return addShapeBox;
	}

	private Box getTextBox() {
		// TODO: When a text is selected, it's content needs to appear in the 
		// field and be changed based on user input
		Box textBox = Box.createHorizontalBox();
		textBox.setMaximumSize(new Dimension(500, 30));
		JLabel infoLabel = new JLabel(" Edit Text:  ");
		infoLabel.setFont(new Font("Lucida Grande", Font.ITALIC, 14));
		JTextField inputField = new JTextField();

		
		inputField.setPreferredSize(new Dimension(100, 14));
		String[] fontList = GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getAvailableFontFamilyNames();
		JComboBox<String> fontPicker = new JComboBox<String>(fontList);
		fontPicker.setPreferredSize(new Dimension(150, 20));
		
		
		fontPicker.addActionListener(e -> {
			if (canvas.getSelectedShape() == null)
				return;
			
			if (canvas.getSelectedShape() instanceof DText) {
				DTextModel model = (DTextModel) canvas.getSelectedShape().getModel();
				model.setFontName((String) fontPicker.getSelectedItem());
				int index = fontPicker.getSelectedIndex();
				model.setIndex(index);

				canvas.repaint();
			}
			
			
			
		});
		
		
		inputField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				DTextModel model = (DTextModel) canvas.getSelectedShape().getModel();
				model.setText(inputField.getText());
				canvas.repaint();
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				DTextModel model = (DTextModel) canvas.getSelectedShape().getModel();
				model.setText(inputField.getText());
				canvas.repaint();
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			
				
			}
		});
		
		this.inputField = inputField;
		this.fontPicker = fontPicker;;
		this.fontSelect = fontPicker;
		
		fontPicker.setSelectedIndex(50);
		
		disableTextButtons();
		

		
		textBox.add(infoLabel);
		textBox.add(inputField);
		textBox.add(fontPicker);
		return textBox;
	}
	
	private JPanel getSetColorPanel() {

		JPanel setColorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		setColorPanel.setMaximumSize(new Dimension(500, 35));
		JLabel infoLabel = new JLabel("Set Shape Color:");
		infoLabel.setFont(new Font("Lucida Grande", Font.ITALIC, 14));
		JButton setColorButton = new JButton("Set Color");
		JColorChooser colorChooser = new JColorChooser();
		setColorButton.addActionListener(e -> {
			if (canvas.getSelectedShape() == null)
				return;
			Color currentColor = canvas.getSelectedShape().getModel().getColor();
			selectedColor = JColorChooser.showDialog(colorChooser,
					"Set the Shape's Color", currentColor);
			canvas.getSelectedShape().getModel().setColor(selectedColor);
		});
		setColorPanel.add(infoLabel);
		setColorPanel.add(setColorButton);
		return setColorPanel;
	}

	private Box getOrderModifierBox() {
		Box modifierBox = Box.createHorizontalBox();
		modifierBox.setMaximumSize(new Dimension(500, 40));
		JLabel infoLabel = new JLabel(" Edit Shapes:");
		infoLabel.setFont(new Font("Lucida Grande", Font.ITALIC, 14));
		modifierBox.add(infoLabel);
		JButton toFrontBtn = new JButton("Send to Front");
		toFrontBtn.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		modifierBox.add(toFrontBtn);
		JButton toBackBtn = new JButton("Send to Back");
		toFrontBtn.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		modifierBox.add(toBackBtn);
		JButton removeBtn = new JButton("Remove This Shape");
		removeBtn.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		modifierBox.add(removeBtn);

		toFrontBtn.addActionListener(e -> {
			canvas.sendSelectedToFront();
		});
		toBackBtn.addActionListener(e -> {
			canvas.sendSelectedToBack();
		});
		removeBtn.addActionListener(e -> {
			canvas.deleteSelectedShape();
		});

		return modifierBox;
	}

	private Box getSaveLoadBox() {
		Box saveLoadBox = Box.createHorizontalBox();
		saveLoadBox.setMaximumSize(new Dimension(500, 40));
		JLabel infoLabel = new JLabel(" Edit/Save Content: ");
		infoLabel.setFont(new Font("Lucida Grande", Font.ITALIC, 14));
		saveLoadBox.add(infoLabel);
		JButton saveBtn = new JButton("Save");
		saveLoadBox.add(saveBtn);
		JButton loadBtn = new JButton("Load");
		saveLoadBox.add(loadBtn);
		JButton saveImgBtn = new JButton("Save as PNG");
		saveLoadBox.add(saveImgBtn);

		saveBtn.addActionListener(new ActionListener()// adding action on save
		{
			public void actionPerformed(ActionEvent e) {
				String path = null;
				ObjectOutputStream os = null;
				ArrayList<DShapeModel> list = canvas.getShapeModelArray();
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setApproveButtonText("Save");
				fileChooser.setCurrentDirectory(
						new File(System.getProperty("user.home")));
				int returnVal = fileChooser.showSaveDialog(mainFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) //getting the path user has indicated 
				{
					path = fileChooser.getSelectedFile().getAbsolutePath();
				}
					if (path != null && !path.endsWith(".ser")) // append .ser if it is not there.
					{
						path += ".ser";
					}

				try // creation of file with extension .ser that contains needed data 
				{
					os = new ObjectOutputStream(new FileOutputStream(path));
					os.writeObject(list);
				} catch (Exception e1) {
					//e1.printStackTrace();
					System.out.println("Please, enter the name of the file.");
					System.exit(0);
				}
			}
		});

		loadBtn.addActionListener(new ActionListener()// adding action on open button
		{
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				String path = null;
				ArrayList<DShapeModel> array = null;
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(
						new File(System.getProperty("user.home")));
				int returnVal = fileChooser.showOpenDialog(mainFrame);
				if (returnVal == JFileChooser.APPROVE_OPTION) 
				{
					path = fileChooser.getSelectedFile().getAbsolutePath();
				}
				try {
					ObjectInputStream inStr = new ObjectInputStream(
							new FileInputStream(path));
					array = (ArrayList<DShapeModel>) inStr.readObject();
					inStr.close();
				} catch (Exception e1) {
					System.out.println("Something went wrong with file loading.");
					System.exit(0);
				}
				canvas.makeThisArrayCurrent(array);
			}
		});

		// saveimgbutton action
		saveImgBtn.addActionListener(e -> {
			System.out.println("This is a placeholder for saving as PNG");
		});
		return saveLoadBox;
	}

	private Box getNetworkingBox() {
		Box networkingBox = Box.createHorizontalBox();
		networkingBox.setMaximumSize(new Dimension(500, 40));
		JLabel infoLabel = new JLabel(" Networking Options: ");
		infoLabel.setFont(new Font("Lucida Grande", Font.ITALIC, 14));
		networkingBox.add(infoLabel);
		JButton serverBtn = new JButton("Start Server");
		networkingBox.add(serverBtn);
		JButton clientBtn = new JButton("Start Client");
		networkingBox.add(clientBtn);

		// the button we need to add when we click start server. 
		JLabel statusLabel = new JLabel("");
		statusLabel.setFont(new Font("Lucida Grande", Font.ITALIC, 14));
		networkingBox.add(statusLabel);

		serverBtn.addActionListener(e -> {
			Integer s = 39587;
			JOptionPane pane = new JOptionPane();
			String result = JOptionPane.showInputDialog(mainFrame,
					"Enter port number you want to use:");
			if (result != null) {
				if (result.equals("")) {
					serverOn = true;
				}
				else {
					try {
						s = Integer.parseInt(result);
						if (s < 65536) 
						{
							serverOn = true;
						}
					} catch (Exception e1) {
						System.out.println("bad");
					}
				}
				server = new TCPServer(s);
				Thread t = new Thread(server);
				t.start();
			}
			if (serverOn) 
			{
				serverBtn.setEnabled(false);
				clientBtn.setEnabled(false);
				statusLabel.setText(" Server mode, port " + s);
			}
		});

		clientBtn.addActionListener(e -> {
			Integer i = 39587;
			JOptionPane pane = new JOptionPane();
			String result = JOptionPane.showInputDialog(mainFrame,
					"Enter port number you want to use:");
			if (result != null) {
				try {
					if (!result.equals(""))
						i = Integer.parseInt(result);
					if (i < 65536) {
						statusLabel.setText("Client mode, port " + i);

					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			TCPClient  client = new TCPClient("localhost",i);
			Thread t = new Thread(client);
			t.start();
		});

		return networkingBox;
	}

	private JTable getTable() {
		JTable table = new JTable(canvas.getTable());
		table.setFillsViewportHeight(true);
		return table;
	}
	
	public void enableTextButtons() {
		inputField.setEnabled(true);
		fontPicker.setEnabled(true);	
	}
	
	public void disableTextButtons() {
		inputField.setEnabled(false);
		fontPicker.setEnabled(false);
		inputField.setText("");
	}

	/**
	 * All new shape should have the x, y, width, and height initilized
	 * to these values
	 * @param model
	 */
	private void setShapeDefaults(DShapeModel model) {
		model.setX(10);
		model.setY(10);
		model.setWidth(20);
		model.setHeight(20);
	}
}
