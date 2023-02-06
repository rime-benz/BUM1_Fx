package Gui;
import busniss_logic.BarcenaysCalculator;
import busniss_logic.Currency;
import busniss_logic.ExchangeCalculator;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;



/**
 * This class provides a graphic user interface for the Currency Exchange Calculator
 * application. Multiple conversions can be done in a single run. To quit the application
 * it is enough to  close the window.
 *
 */
public class CalculatorWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JPanel dataPane;
    private JPanel amountPane;
    private JTextField amountTextField;
    private JPanel fromPane;
    private JComboBox<String> fromComboBox;
    private JPanel toPane;
    private JComboBox<String> toComboBox;
    private JPanel resultsPane;
    private JButton calculateButton;
    private JTextArea resultTextArea;
    private ExchangeCalculator businesslogic = new BarcenaysCalculator();


    public CalculatorWindow() {

        this.setTitle("Currency Exchange Calculator");
        this.setBounds(100, 100, 600, 305);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Box Layout Manager has been used so that the display of the components can
         * easily be checked. But to circumvent its limited functionality the use of
         * subpanels has been mandatory. */

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        this.setContentPane(contentPane);

        // The different interactive graphic components are displayed, starting by the company logo

        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setIcon(new ImageIcon("C:\\Users\\ADMIN\\Desktop\\UPV_2023\\BUM1_Fx\\src\\main\\resources\\Picture\\Bank_Logo.png"));
        contentPane.add(logoLabel);

        /* Under the logo we insert the data subpanel, that in urn will contain three
         * sub-subpanels: amount, from and to */

        contentPane.add(Box.createVerticalGlue());
        dataPane = new JPanel();
        dataPane.setLayout(new BoxLayout(dataPane, BoxLayout.X_AXIS));
        contentPane.add(dataPane);

        amountPane = new JPanel();
        amountPane.setLayout(new BoxLayout(amountPane, BoxLayout.Y_AXIS));
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        amountPane.add(amountLabel);
        amountTextField = new JTextField(8);
        amountTextField.setFont(new Font("Dialog", Font.BOLD, 16));
        amountTextField.setMargin(new Insets(5, 5, 5, 5));
        amountTextField.setHorizontalAlignment(SwingConstants.CENTER);
        amountPane.add(amountTextField);
        dataPane.add(amountPane);

        fromPane = new JPanel();
        fromPane.setLayout(new BoxLayout(fromPane, BoxLayout.Y_AXIS));
        JLabel fromLabel = new JLabel("From currency:");
        fromLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fromPane.add(fromLabel);
        fromComboBox = new JComboBox<String>();
        fromComboBox.setModel(new DefaultComboBoxModel<String>(Currency.longNames()));
        fromPane.add(fromComboBox);
        dataPane.add(fromPane);

        toPane = new JPanel();
        toPane.setLayout(new BoxLayout(toPane, BoxLayout.Y_AXIS));
        JLabel toLabel = new JLabel("To currency:");
        toLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        toPane.add(toLabel);
        toComboBox = new JComboBox<String>();
        toComboBox.setModel(new DefaultComboBoxModel<String>(Currency.longNames()));
        toPane.add(toComboBox);
        dataPane.add(toPane);

        // Finally the results subpanel is constructed and inserted under the data subpanel

        contentPane.add(Box.createVerticalGlue());
        resultsPane = new JPanel();

		/* The CALCULATE button is the only part of the window that needs to be programmed,
		   since we can benefit from the native behavior of the rest of the components. */

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double origAmount = Double.parseDouble(amountTextField.getText());

					/* Invalid numbers will be trapped in the catch block. But
					   non-positive numbers will also be discarded */
                    if (origAmount <= 0) throw new NumberFormatException();
                    String origCurrency = (String) fromComboBox.getSelectedItem();
                    origCurrency = origCurrency.substring(0, 3);
                    String endCurrency = (String) toComboBox.getSelectedItem();
                    endCurrency = endCurrency.substring(0, 3);

					/* If both chosen currencies are equal the online converter
					   won't provide a result */
                    if (origCurrency.equals(endCurrency)) {
                        resultTextArea.setText("Please select different currencies");
                    }
                    else {
                        //ForexOperator operator = new ForexOperator(origCurrency,
                        //		origAmount, endCurrency);
                        try {
                            double destAmount = businesslogic.getChangeValue(origCurrency,
                                    origAmount, endCurrency);
                            //CommissionCalculator calculator = new CommissionCalculator(destAmount,
                            //endCurrency);
                            destAmount -= businesslogic.calculateComission(destAmount,endCurrency);
                            NumberFormat twoDecimal = NumberFormat.getNumberInstance(Locale.US);
                            twoDecimal.setMaximumFractionDigits(2);
                            twoDecimal.setRoundingMode(RoundingMode.FLOOR);
                            resultTextArea.setText(twoDecimal.format(destAmount));

                        } catch (Exception e1) {
                            resultTextArea.setText("Conversion could not be done");
                        }
                    }
                } catch (NumberFormatException e2) {
                    resultTextArea.setText("Please introduce a valid amount");
                }
            }
        });
        resultsPane.add(calculateButton);
        resultTextArea = new JTextArea();
        resultTextArea.setColumns(20);
        resultTextArea.setMargin(new Insets(5, 5, 5, 5));
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font("TextArea.font", Font.BOLD, 16));
        resultsPane.add(resultTextArea);
        contentPane.add(resultsPane);
    }

    /**
     * Launches the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    CalculatorWindow frame = new CalculatorWindow();
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}