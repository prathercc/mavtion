package mavtion;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.MouseInfo;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.formdev.flatlaf.FlatDarkLaf;

import mavtion.domain.InstructionSet;
import mavtion.service.FileService;
import mavtion.service.InstructionService;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class Main extends JFrame {

	private JPanel contentPane;
	FileService fileService = new FileService();
	InstructionService instructionService = new InstructionService();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		logger.setUseParentHandlers(false);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlatDarkLaf.setup();
					Main frame = new Main();
					frame.setVisible(true);
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					frame.setLocation(dim.width / 2 - frame.getSize().width / 2,
							dim.height / 2 - frame.getSize().height / 2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws NativeHookException
	 */
	public Main() throws NativeHookException {
		setResizable(false);
		setTitle("Mavtion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 364, 113);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);

		JMenuItem loadScriptMenuItem = new JMenuItem("Load Script");
		mnNewMenu.add(loadScriptMenuItem);

		JMenuItem mntmNewMenuItem = new JMenuItem("Preferences");
		mntmNewMenuItem.setEnabled(false);
		mnNewMenu.add(mntmNewMenuItem);
		
		JCheckBox _stopJobBox = new JCheckBox("");
		menuBar.add(_stopJobBox);
		_stopJobBox.setVisible(false);
		instructionService.set_stopJobBox(_stopJobBox);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		final JButton startButton = new JButton("Start");
		startButton.setToolTipText("Assigned Hotkey: CAPS-LOCK");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startButton.setText(startButton.getText().equalsIgnoreCase("Start") ? "Stop" : "Start");
				if (startButton.getText().equalsIgnoreCase("Stop")) {
					_stopJobBox.setSelected(false);
					Thread thread = new Thread(new Runnable() {
			            @Override
			            public void run() {
			            	try {
				            	instructionService.executeInstructions();
				            	startButton.setText("Start");
				            	_stopJobBox.setSelected(true);
			            	}
			            	catch(Exception e) {
			            		JOptionPane.showMessageDialog(getParent(), "Error executing script!\nPlease review script and try again.", "Error",
										JOptionPane.WARNING_MESSAGE);
			            	}
			            }
			        });  
					thread.start();
				} else {
					_stopJobBox.setSelected(true);
				}
			}
		});
		startButton.setEnabled(false);
		JLabel coordinatesLabel = new JLabel("(" + (int) MouseInfo.getPointerInfo().getLocation().getX() + ", "
				+ (int) MouseInfo.getPointerInfo().getLocation().getY() + ")");

		JTextArea textArea = new JTextArea();
		instructionService.setTextArea(textArea);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(coordinatesLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(startButton))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
							.addComponent(startButton))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(34)
							.addComponent(coordinatesLabel)))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);

		loadScriptMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("Mavtion Script File (.mvsf)", "mvsf"));
				if (fc.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
					InstructionSet is = fileService.parseInstructions(fc.getSelectedFile());
					if (is != null) {
						instructionService.setInstructionSet(is);
						startButton.setEnabled(true);
					} else {
						JOptionPane.showMessageDialog(getParent(), "An invalid file format was detected.\nCould not load selected file.", "Error",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		GlobalScreen.registerNativeHook();
		GlobalScreen.addNativeKeyListener(new GlobalKeyListener(startButton));
		GlobalScreen.addNativeMouseListener(new GlobalMouseListener(coordinatesLabel));
		GlobalScreen.addNativeMouseMotionListener(new GlobalMouseListener(coordinatesLabel));
	}
}
