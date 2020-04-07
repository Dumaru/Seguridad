package gui;

import model.DES;
import model.Util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

/**
 * DES Calculator Applet v2.0 Feb 2005.
 * The DES Calculator applet is used to encrypt or decrypt test data values
 * using DES block cipher.  It takes a 64-bit (16 hex digit) data value and 
 * 64-bit (16 hex digit) key.  It can optionally provide a trace 
 * of the calculations performed, with varying degrees of detail.
 * DEScalc was written and is Copyright 2005 by Lawrie Brown.
 * Permission is granted to copy, distribute, and use this applet
 * provided the author is acknowledged and this copyright notice remains intact.
 * See http://www.unsw.adfa.edu.au/~lpb/ for authors website.
 * 
 * Modified / updated in 2019 by Ferney Maldonado at Universidad de la Sabana.
 * - DES tracelevel.
 * - JCheckboxes for traces.
 * - GUI updated.
 * @author 
 *
 */
public class DEScalc extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -751621291901379130L;

	private DES des;

	private JPanel pControls;
	private JPanel pControlButton;
	private JPanel pTrace;

	private JLabel	lTitle;
	private JLabel	lData;
	private JLabel	lKey;
	private JLabel	lResult;
	private JLabel	lTrace;
	private JTextField	tData;
	private JTextField	tKey;
	private JTextField	tResult;
	private JButton	bEncrypt;
	private JButton	bDecrypt;
	private JButton	bAbout;
	private JButton	bQuit;

	/** checkboxes to specify desired level of trace information. */
	private ButtonGroup	cbTrace;
	private JRadioButton cTrace0, cTrace1, cTrace2, cTrace3, cTrace4;

	/** area to display  diagnostic trace information. */
	private JTextArea	taTrace;

	/** level of trace information desired. */
	private int traceLev = 2;

	/** Flag saying whether we're running as application or applet. */
	boolean		application = true;

	/** Usage and copyright message - displayed when applet first starts. */
	private static final String about = "DES Calculator Applet v2.0 Feb 2005.\n\n" +
			"The DES Calculator applet is used to encrypt or decrypt test data values\n" +
			"using DES block cipher.  It takes a 64-bit (16 hex digit) data value and a\n" +
			"64-bit (16 hex digit) key.  It can optionally provide a trace\n" +
			"of the calculations performed, with varying degrees of detail.\n\n" +
			"DEScalc was written and is Copyright 2005 by Lawrie Brown.\n" +
			"Permission is granted to copy, distribute, and use this applet\n" +
			"provided the author is acknowledged and this copyright notice remains intact.\n\n" +
			"See http://www.unsw.adfa.edu.au/~lpb/ for authors website.\n"
			+ "Modified in 2019 by Ferney Maldonado at Universidad de la Sabana.";

	/**
	 * 
	 */
	public DEScalc() {
		this.setSize(850, 850);
		//this.setResizable(false);
		
		String title = "DES Block Cipher Calculator";
		this.setTitle(title);

		this.setLayout(new BorderLayout());
		// nice plain white background
		this.setBackground(Color.white);

		// title
		Font fTitle = new Font("Helvetica", Font.PLAIN, 24);
		lTitle = new JLabel(title);
		lTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lTitle.setFont(fTitle);
		lTitle.setForeground(Color.DARK_GRAY);
		this.add(BorderLayout.NORTH, lTitle);

		// various inputs and controls
		pControls = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		pControls.setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		Font fLabels = new Font("Consolas", Font.PLAIN, 20);

		lData  = new JLabel("Input Data (in hex)");
		lData.setHorizontalAlignment(SwingConstants.LEFT);
		lData.setFont(fLabels);
		lData.setForeground(Color.blue);
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbl.setConstraints(lData, gbc);
		pControls.add(lData);

		// data
		tData  = new JTextField(2*DES.BLOCK_SIZE);
		tData.setFont(fLabels);
		tData.setText("");
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(tData, gbc);
		pControls.add(tData);

		// key
		lKey   = new JLabel("DES Key (in hex)");
		lKey.setHorizontalAlignment(SwingConstants.LEFT);
		lKey.setFont(fLabels);
		lKey.setForeground(Color.blue);
		gbc.gridwidth = 1;
		gbl.setConstraints(lKey, gbc);
		pControls.add(lKey);

		tKey = new JTextField(2*DES.KEY_LENGTH);
		tKey.setFont(fLabels);
		tKey.setText("");
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(tKey, gbc);
		pControls.add(tKey);

		lResult = new JLabel("Result will be displayed here.");
		lResult.setHorizontalAlignment(SwingConstants.LEFT);
		lResult.setFont(fLabels);
		lResult.setForeground(Color.blue);
		gbc.gridwidth = 1;
		gbl.setConstraints(lResult, gbc);
		pControls.add(lResult);

		tResult = new JTextField(2*DES.BLOCK_SIZE);
		tResult.setFont(fLabels);
		tResult.setEditable(false);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(tResult, gbc);
		pControls.add(tResult);

		
		// buttons to request various actions
		Font fButton = new Font("Helvetica", Font.PLAIN, 16);
		JPanel pButtons = new JPanel();
		Dimension butDimension = new Dimension(90, 40);
		bEncrypt = new JButton("Encrypt");
		bEncrypt.setFont(fButton);
		bEncrypt.setPreferredSize(butDimension);
		//bEncrypt.setForeground(Color.blue);
		pButtons.add(bEncrypt);
		bEncrypt.addActionListener(this);
		bDecrypt = new JButton("Decrypt");
		bDecrypt.setFont(fButton);
//		bDecrypt.setForeground(Color.blue);
		bDecrypt.setPreferredSize(butDimension);
		pButtons.add(bDecrypt);
		bDecrypt.addActionListener(this);
		
		bAbout = new JButton("About");
		bAbout.setFont(fButton);;
		pButtons.add(bAbout);
		bAbout.addActionListener(this);
		bAbout.setPreferredSize(butDimension);
		bQuit = new JButton("Quit");
		bQuit.setFont(fButton);
		// only place Quit button if an application
		if (application) {	
			pButtons.add(bQuit);
			bQuit.addActionListener(this);
			bQuit.setPreferredSize(butDimension);
		}
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(pButtons, gbc);
		pControls.add(pButtons);

		// and place panels into main GUI
		this.add(BorderLayout.CENTER, pControls);

		// lastly build the trace info text area
		pTrace = new JPanel();
		pTrace.setLayout(new BorderLayout());
		lTrace  = new JLabel("Trace of DES Calculations or Errors");
		lTrace.setHorizontalAlignment(SwingConstants.CENTER);
		lTrace.setFont(fLabels);
		lTrace.setForeground(Color.DARK_GRAY);
		pTrace.add(BorderLayout.NORTH, lTrace);

		//TODO cambiar el grupo de botones
		
		cbTrace = new ButtonGroup();
		pControlButton = new JPanel();
		pControlButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		Label lCB = new Label("Trace Level: ");
		lCB.setFont(fLabels);
		lCB.setForeground(Color.DARK_GRAY);
		pControlButton.add(lCB);
		// ctrace0 
		cTrace0 = new JRadioButton("0: none", (traceLev == 0 ? true : false));
		cTrace0.setFont(fLabels);
		cbTrace.add(cTrace0);
		pControlButton.add(cTrace0);
		cTrace0.addActionListener(this);
		//cTrace0.addItemListener(this);
		cTrace1 = new JRadioButton("1: calls", (traceLev == 1 ? true : false));
		cTrace1.setFont(fLabels);
		if (traceLev >= 1)	pControlButton.add(cTrace1);
		cTrace1.addActionListener(this);
		cbTrace.add(cTrace1);
		//cTrace1.addItemListener(this);
		cTrace2 = new JRadioButton("2: +rounds",  (traceLev == 2 ? true : false));
		cTrace2.setFont(fLabels);
		if (traceLev >= 2)	pControlButton.add(cTrace2);
		cTrace2.addActionListener(this);
		cbTrace.add(cTrace2);
		//cTrace2.addItemListener(this);
		cTrace3 = new JRadioButton("3: +steps", (traceLev == 3 ? true : false));
		if (traceLev >= 3)	 pControlButton.add(cTrace3);
		cTrace3.addActionListener(this);
		//	cTrace3.addItemListener(this);
		cTrace4 = new JRadioButton("4: +subkeys", (traceLev >= 4 ? true : false));
		if (traceLev >= 4)	 pControlButton.add(cTrace4);
		cTrace4.addActionListener(this);
		//cTrace4.addItemListener(this);
		pTrace.add(BorderLayout.CENTER, pControlButton);
		
		
		Font fTrace = new Font("Courier", Font.PLAIN, 18);
		taTrace = new JTextArea(about, 17, 80);
		taTrace.setFont(fTrace);
		taTrace.setEditable(false);
		taTrace.setAutoscrolls(true);
		taTrace.setBackground(Color.white);
		// Crear Container Scrolable y luego meter ese con scroll dentro del panel trace
		JScrollPane jScrollPane = new JScrollPane(taTrace);
		//jScrollPane.set
		jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		//pTrace.add(BorderLayout.SOUTH, taTrace);
		pTrace.add(BorderLayout.SOUTH, jScrollPane);

		this.add(BorderLayout.SOUTH, pTrace);
		// create the DES cipher object to use
		des = new DES();
		// and set level of trace info 
		des.traceLevel = traceLev;
	}

	/**
	 *  respond to Action Event: pressing one of the buttons.
	 */
	public void actionPerformed (ActionEvent e) {
		byte[]  data;				/* 64-bit DES data */
		byte[]  key;				/* 128-bit DES key */
		byte[]  result;				/* 64-bit DES result */
		String	hexdata;			/* hex string to use as data */
		String	hexkey;				/* hex string to use as key */
		String	info = "";			/* trace info to display */
		Object	source = e.getSource();		/* identify action source */

		if (source == bEncrypt) {		// want to encrypt the data
			// now extract key and data
			hexkey  = tKey.getText();
			if ((hexkey.length() != (2*DES.KEY_LENGTH)) ||
					(!Util.isHex(hexkey))) {
				lResult.setForeground(Color.red);
				lResult.setText("Error in Key!!!");
				taTrace.setForeground(Color.red);
				taTrace.setText("DES key [" + hexkey +
						"] must be strictly " + (2*DES.KEY_LENGTH) +
						" hex digits long.");
				tResult.setText("");
				return;
			}
			key = Util.hex2byte(hexkey);

			hexdata = tData.getText();
			if ((hexdata.length() != (2*DES.BLOCK_SIZE)) ||
					(!Util.isHex(hexdata))) {
				lResult.setForeground(Color.red);
				lResult.setText("Error in Data!!!");
				taTrace.setForeground(Color.red);
				taTrace.setText("DES data [" + hexdata +
						"] must be strictly " + (2*DES.BLOCK_SIZE) +
						" hex digits long.");
				tResult.setText("");
				return;
			}
			data = Util.hex2byte(hexdata);
			des.setKey(key);
			if (traceLev>0) info += des.traceInfo;

			result = des.encrypt(data);
			if (traceLev>0) info += des.traceInfo;

			lResult.setForeground(Color.BLUE);
			lResult.setText("Encrypted value is:");
			tResult.setText(Util.toHEX1(result));
			taTrace.setForeground(Color.BLACK);
			taTrace.setText(info);

		} else if (source == bDecrypt) {	// want to decrypt the data
			// now extract key and data
			hexkey  = tKey.getText();
			if ((hexkey.length() != (2*DES.KEY_LENGTH)) ||
					(!Util.isHex(hexkey))) {
				lResult.setForeground(Color.red);
				lResult.setText("Error in Key!!!");
				taTrace.setForeground(Color.red);
				taTrace.setText("DES key [" + hexkey +
						"] must be strictly " + (2*DES.KEY_LENGTH) +
						" hex digits long.");
				tResult.setText("");
				return;
			}
			key = Util.hex2byte(hexkey);

			hexdata = tData.getText();
			if ((hexdata.length() != (2*DES.BLOCK_SIZE)) ||
					(!Util.isHex(hexdata))) {
				lResult.setForeground(Color.red);
				lResult.setText("Error in Data!!!");
				taTrace.setForeground(Color.red);
				taTrace.setText("DES data [" + hexdata +
						"] must be strictly " + (2*DES.BLOCK_SIZE) +
						" hex digits long.");
				tResult.setText("");
				return;
			}
			data = Util.hex2byte(hexdata);

			des.setKey(key);
			if (traceLev>0) info += des.traceInfo;

			result = des.decrypt(data);
			if (traceLev>0) info += des.traceInfo;

			lResult.setForeground(Color.blue);
			lResult.setText("Decrypted value is:");
			tResult.setText(Util.toHEX1(result));
			taTrace.setForeground(Color.black);
			taTrace.setText(info);

		} else if (source == bAbout) {
			taTrace.setForeground(Color.black);	// display about message
			taTrace.setText(about);
		} else if ((source == bQuit) && application) {
			System.exit(0);	// can only quit applications
		} else if(source == cTrace1) {
			des.traceLevel = 1;
		} else if(source == cTrace2) {
			des.traceLevel = 2;
		} else if(source == cTrace0) {
			des.traceLevel = 0;
		}
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DEScalc descalc = new DEScalc();
		descalc.setVisible(true);
		descalc.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
