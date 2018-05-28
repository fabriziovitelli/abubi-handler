package com.me.abubi.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.me.abubi.utils.DateFormatter;
import com.me.abubi.utils.Day;
import com.me.abubi.utils.FileManager;
import com.me.abubi.utils.Month;
import com.me.abubi.utils.Year;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = -1400766053235784296L;
	
	public static final Font DEFAULTFONT = new Font("Courier New", Font.BOLD, 13);
	
	private JPanel contentPane;
	private JTextField filePathTextFiled;
	private JButton selectFileButton;
	private JPanel chartPanel;
	
	private File currentFile;
	
	private ArrayList<Year> data;
	
	private Year currentYear;
	private JButton refreshButton;
	private JLabel currentDateLabel;
	private JLabel currentDayLabel;
	private JLabel totalLabel;
	private JLabel counterLabel;
	private JButton newDayButton;
	private JMenuBar menuBar;

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 666, 666);
		setTitle("Abubi Counter 2.0");
		setVisible(true);
		setResizable(false);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		createComponents();
		addComponents();
	}

	private void createComponents() {
		//textFields
		filePathTextFiled = new JTextField();
		filePathTextFiled.setBounds(37, 37, 427, 34);
		filePathTextFiled.setColumns(10);

		//buttons
		selectFileButton = new JButton("Select File");
		selectFileButton.setBounds(487, 20, 134, 29);
		selectFileButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				if( fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION ){
					filePathTextFiled.setText(currentFile.getAbsolutePath());
					currentFile = fc.getSelectedFile();
					try {
						data = FileManager.parseData(currentFile);
						if( data.size()!=0 ) {
							currentYear = data.get(data.size()-1);
						}
					}catch (Exception ex) {
						ex.printStackTrace();
						try {
							data = FileManager.importCsv(currentFile);
						}catch (Exception ex2) {
							ex2.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error: file not recognized", "ERROR", JOptionPane.ERROR_MESSAGE);
							throw ex2;
						}
					}
					JOptionPane.showMessageDialog(null, "File correctly loaded", "Success", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		refreshButton = new JButton("Refresh");
		refreshButton.setBounds(487, 61, 134, 29);
		refreshButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				if( fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION ){
					filePathTextFiled.setText(currentFile.getAbsolutePath());
					currentFile = fc.getSelectedFile();
					data = FileManager.loadData(currentFile);
					if( data.size()!=0 ) {
						currentYear = data.get(data.size()-1);
					}
					JOptionPane.showMessageDialog(null, "File correctly loaded", "Success", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		//panels
		chartPanel = new JPanel();
		chartPanel.setBounds(6, 331, 654, 307);
			
		//labels
		currentDateLabel = new JLabel("CurrentDay:");
		currentDateLabel.setFont(DEFAULTFONT);
		currentDateLabel.setForeground(Color.WHITE);
		currentDateLabel.setBounds(37, 88, 98, 16);
		
		counterLabel = new JLabel("0");
		counterLabel.setForeground(Color.WHITE);
		counterLabel.setBounds(320, 87, 88, 16);
		
		currentDayLabel = new JLabel("00/00/0000");
		currentDayLabel.setForeground(Color.WHITE);
		currentDayLabel.setFont(DEFAULTFONT);
		currentDayLabel.setBounds(132, 87, 98, 16);

		totalLabel = new JLabel("Total:");
		totalLabel.setForeground(Color.WHITE);
		totalLabel.setBounds(250, 87, 61, 16);

		newDayButton = new JButton("New Day");
		newDayButton.setBounds(487, 152, 134, 29);
		newDayButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(data!=null || !data.isEmpty()) {
					Calendar instance = Calendar.getInstance();
					Integer yearNumber = instance.get(Calendar.YEAR);
					Integer monthNumber = instance.get(Calendar.MONTH)+1;
					Integer dayNumber = instance.get(Calendar.DAY_OF_MONTH);
					Year y = new Year(String.valueOf(String.valueOf(yearNumber)));
					if( !data.contains(y) ) {
						data.add(y);
						currentYear=y;
					}
					Month month = new Month(monthNumber);
					if( !currentYear.contains(monthNumber) ){
						currentYear.addMonth(month);
					}
					Day d = new Day(dayNumber);
					if(! month.contains(dayNumber) ) {
						month.addDay(d);;
					}
					currentDayLabel.setText(DateFormatter.simpleFormat.format(instance.getTime()));
				}
			}
		});
	}

	private void addComponents() {
		//textFields
		contentPane.add(filePathTextFiled);
		
		//buttons
		contentPane.add(selectFileButton);
		contentPane.add(refreshButton);
		contentPane.add(newDayButton);
		
		//panels
		contentPane.add(chartPanel);
		
		//labels
		contentPane.add(currentDateLabel);
		
		contentPane.add(currentDayLabel);
		
		contentPane.add(totalLabel); 
		contentPane.add(counterLabel);

	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					new MainFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
