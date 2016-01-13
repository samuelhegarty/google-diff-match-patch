package com.googlecode.diffmatchpatch.ui;

import com.googlecode.diffmatchpatch.core.*;
import com.googlecode.diffmatchpatch.core.content.FileContentSource;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by shegarty on 13/01/2016.
 */
public class DiffPanel extends JPanel{
	public static final Style EQUAL_STYLE;
	public static final Style DELETE_STYLE;
	public static final Style INSERT_STYLE;

	static{
		StyleContext context = new StyleContext();
		EQUAL_STYLE = context.addStyle("equal", null);
		INSERT_STYLE = context.addStyle("insert", EQUAL_STYLE);
		StyleConstants.setForeground(INSERT_STYLE, new Color(0xFF0080FF));
		DELETE_STYLE = context.addStyle("delete", EQUAL_STYLE);
		StyleConstants.setForeground(DELETE_STYLE, new Color(0xFFC0C0C0));
		StyleConstants.setStrikeThrough(DELETE_STYLE, true);
	}

	public static void main(String...args){
		JFrame frame = new JFrame("DiffPanel Test");
		DiffPanel panel = new DiffPanel();
		frame.setSize(800, 600);
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		ContentSource leftSource = new FileContentSource("file1.txt");
		ContentSource rightSource = new FileContentSource("file2.txt");
		try{
			panel.setSources(leftSource, rightSource);
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
	}

	private final DiffMatchPatch diff = new DiffMatchPatch(new DiffSettings().setMatchThreshold(0f));

	private ContentSource leftSource;
	private ContentSource rightSource;

	private final DefaultStyledDocument leftDoc = new DefaultStyledDocument();
	private final DefaultStyledDocument rightDoc = new DefaultStyledDocument();

	private final JTextPane leftPane = new JTextPane(leftDoc);
	private final JTextPane rightPane = new JTextPane(rightDoc);

	private List<Diff> patch;
	private final boolean editable = false;

	private Map<Operation, Style> styles = new HashMap<>();

	public DiffPanel(){
		Container c = new Container();
		BoxLayout layout = new BoxLayout(c, BoxLayout.X_AXIS);
		c.setLayout(layout);
		c.add(leftPane);
		c.add(rightPane);
		this.add(c);
		try{
			setStyle(Operation.EQUAL, EQUAL_STYLE);
			setStyle(Operation.DELETE, DELETE_STYLE);
			setStyle(Operation.INSERT, INSERT_STYLE);
		}
		catch(IOException e){
			//since sources are currently null, this cannot happen
		}
	}
	public void setLeftSource(ContentSource source) throws IOException{
		setSources(source, this.rightSource);
	}
	public void setRightSource(ContentSource source) throws IOException{
		setSources(this.leftSource, source);
	}
	public void setSources(ContentSource leftSource, ContentSource rightSource) throws IOException{
		this.leftSource = leftSource;
		this.rightSource = rightSource;
		leftPane.setText("");
		rightPane.setText("");
		if(leftSource == null){
			if(rightSource != null){
				rightPane.setText(rightSource.getContent());
			}
		}
		else if(rightSource == null){
			leftPane.setText(leftSource.getContent());
		}
		else{
			patch = diff.diffMain(leftSource.getContent(), rightSource.getContent());
			try{
				for(Diff d: patch){
					Operation op = d.getOperation();
					Style style = styles.get(op);
					String text = d.getText();
					switch(op){
						case EQUAL:{
							leftDoc.insertString(leftDoc.getLength(), text, style);
							rightDoc.insertString(rightDoc.getLength(), text, style);
							break;
						}
						case INSERT:{
							rightDoc.insertString(rightDoc.getLength(), text, style);
							break;
						}
						case DELETE:{
							leftDoc.insertString(leftDoc.getLength(), text, style);
						}
					}
				}
			}
			catch(BadLocationException ex){
				ex.printStackTrace();
			}
		}
		System.err.println("Sources set:\n\tLeft source:" + leftSource + "\n\tRight source:" + rightSource);
	}
	public void setStyle(Operation op, Style style) throws IOException{
		if(op == null || style == null)throw new IllegalArgumentException("Operation(" + op + ") and Style(" + style + ") must not be null.");
		styles.put(op, style);
		setSources(leftSource, rightSource);
	}
}
