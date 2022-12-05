import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextEditor extends JFrame implements ActionListener {
    JTextArea textArea ;
    JScrollPane scrollPane;
    JSpinner fontSizeSpinner;
    JLabel fontLabel ;
    JButton fontColorButton;
    JComboBox fontBox;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;


    TextEditor(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Text Editor");
        this.setSize(500,500);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        textArea= new JTextArea();
        textArea.setLineWrap(true);//IMPORTANT for making the text move to the next raw
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial",Font.PLAIN,50));

        scrollPane =new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450,450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(100,25));
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)fontSizeSpinner.getValue()));
            }
        });

        fontLabel = new JLabel("Font");

        fontColorButton =new JButton("Color");
        //button hold the color chooser exist in action performed method which allow the user to change between colors
        fontColorButton.addActionListener(this);

         String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox= new JComboBox<>(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");



        //-----menu-bar---------
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem=new JMenuItem("save");
        exitItem =new JMenuItem("Exit");
        fileMenu.add(openItem); //adding the item dropdown down list
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu); //adding one item on the menu bar
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);







         this.add(fontLabel);
        this.add(fontSizeSpinner);
        this.setVisible(true);
        this.add(fontColorButton); //in action performed
        this.add(scrollPane);
        this.add(fontBox);
        this.setJMenuBar(menuBar);




    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == fontColorButton){
         //   JColorChooser colorChooser = new JColorChooser(); it is a static method
            Color color = JColorChooser.showDialog(null,"choose color",Color.BLACK);
            //the color button to change the text
            textArea.setForeground(color);

        }
        if(e.getSource() ==fontBox){
            textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
        }
        if(e.getSource()==openItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory((new File(".")));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("text files","txt");
            fileChooser.setFileFilter(filter);
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn =null;

                try {
                    fileIn = new Scanner(file);
                    while (fileIn.hasNext()){
                        String line =fileIn.nextLine()+"\n";
                        textArea.append(line);

                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                finally {
                    fileIn.close();
                }
            }

        }
        if(e.getSource()==saveItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showDialog(null,"choose file");
            if(response==JFileChooser.APPROVE_OPTION){
                  File file;
                PrintWriter fileOut =null;
                File file1 = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    fileOut = new PrintWriter(file1);
                    fileOut.println(textArea.getText());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                finally {
                    fileOut.close();
                }

            }

        }
        if(e.getSource()==exitItem){
            System.exit(0);
        }

    }
}
