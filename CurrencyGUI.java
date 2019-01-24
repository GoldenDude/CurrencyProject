package com.azranozeri.finalproject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class CurrencyGUI {
    private JFrame frame;
    private JTable jTable;
    private JPanel topPanel, botPanel;
    private JScrollPane scroller;
    private JComboBox<String> from, to;
    private JTextField amount, result;
    private JLabel fromL, toL, resultL, date;
    private JButton convert;
    private CurrencyMap currencyMap;

    public CurrencyGUI(){
        String[] columnNames = {"Name", "Unit" , "Country","Country Code", "Rate", "Change"};
        frame = new JFrame("Currencies");
        frame.setLayout(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for(String cul: columnNames){
            model.addColumn(cul);
        }

        jTable  = new JTable(model);
        scroller = new JScrollPane(jTable);
        from = new JComboBox<>();
        to = new JComboBox<>();
        to.setPreferredSize(new Dimension(100, 25));
        from.setPreferredSize(new Dimension(100, 25));
        topPanel = new JPanel();
        botPanel = new JPanel();
        amount = new JTextField(10);
        result = new JTextField(10);
        result.setEditable(false);
        result.setBackground(Color.WHITE);
        date = new JLabel();
        toL = new JLabel("To");
        fromL = new JLabel("Amount");
        resultL = new JLabel("Result");
        convert = new JButton("Convert!");
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JTable getjTable() {
        return jTable;
    }

    public void setjTable(JTable jTable) {
        this.jTable = jTable;
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    public void setTopPanel(JPanel topPanel) {
        this.topPanel = topPanel;
    }

    public JPanel getBotPanel() {
        return botPanel;
    }

    public void setBotPanel(JPanel botPanel) {
        this.botPanel = botPanel;
    }

    public JScrollPane getScroller() {
        return scroller;
    }

    public void setScroller(JScrollPane scroller) {
        this.scroller = scroller;
    }

    public JComboBox<String> getFrom() {
        return from;
    }

    public void setFrom(JComboBox<String> from) {
        this.from = from;
    }

    public JComboBox<String> getTo() {
        return to;
    }

    public void setTo(JComboBox<String> to) {
        this.to = to;
    }

    public JTextField getAmount() {
        return amount;
    }

    public void setAmount(JTextField amount) {
        this.amount = amount;
    }

    public JTextField getResult() {
        return result;
    }

    public void setResult(JTextField result) {
        this.result = result;
    }

    public JLabel getFromL() {
        return fromL;
    }

    public void setFromL(JLabel fromL) {
        this.fromL = fromL;
    }

    public JLabel getToL() {
        return toL;
    }

    public void setToL(JLabel toL) {
        this.toL = toL;
    }

    public JLabel getResultL() {
        return resultL;
    }

    public void setResultL(JLabel resultL) {
        this.resultL = resultL;
    }

    public JLabel getDate() {
        return date;
    }

    public void setDate(JLabel date) {
        this.date = date;
    }

    public JButton getConvert() {
        return convert;
    }

    public void setConvert(JButton convert) {
        this.convert = convert;
    }

    public CurrencyMap getCurrencyMap(){
        return currencyMap;
    }

    public void setCurrencyMap(CurrencyMap currencyMap){
        this.currencyMap = currencyMap;
    }

    public void createUI(){
        frame.setMinimumSize(new Dimension(700, 400));
        topPanel.add(date);
        botPanel.add(fromL);
        botPanel.add(amount);
        botPanel.add(from);
        botPanel.add(toL);
        botPanel.add(to);
        botPanel.add(resultL);
        botPanel.add(result);
        botPanel.add(convert);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(botPanel, BorderLayout.SOUTH);
        frame.add(scroller, BorderLayout.CENTER);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(1);
            }
        });

        //convert.addActionListener(e -> SwingUtilities.invokeLater(new Calculator(CurrencyGUI.this, CurrencyGUI.this.getCurrencyMap())));
        convert.addActionListener(e -> new Calculator(CurrencyGUI.this, CurrencyGUI.this.getCurrencyMap()).run());
    }

    public void createList(List<Currency> list){
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        from.removeAllItems();
        to.removeAllItems();
        model.setRowCount(0);
        from.addItem("NIS");
        to.addItem("NIS");

        for(Currency cur: list){
            model.addRow(new Object[]{cur.getName(), cur.getUnit(), cur.getCountryName() ,cur.getCode(), cur.getRate(), cur.getChange()});
            from.addItem(cur.getCode());
            to.addItem(cur.getCode());
        }
    }
}
