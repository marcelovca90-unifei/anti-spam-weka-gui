//**********************************************************************
// Copyright (c) 2017 Telefonaktiebolaget LM Ericsson, Sweden.
// All rights reserved.
// The Copyright to the computer program(s) herein is the property of
// Telefonaktiebolaget LM Ericsson, Sweden.
// The program(s) may be used and/or copied with the written permission
// from Telefonaktiebolaget LM Ericsson or in accordance with the terms
// and conditions stipulated in the agreement/contract under which the
// program(s) have been supplied.
// **********************************************************************
package io.github.marcelovca90.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.marcelovca90.common.MethodConfiguration;
import io.github.marcelovca90.helper.ExecutionHelper;
import io.github.marcelovca90.helper.MailHelper;
import io.github.marcelovca90.helper.MailHelper.CryptoProtocol;

public class UserInterface extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(UserInterface.class);
    private static final String USER_HOME = System.getProperty("user.home");

    private JButton btnRun;
    private JCheckBox chkBalanceClasses;
    private JCheckBox chkEmailResults;
    private JCheckBox chkIncludeEmpty;
    private JCheckBox chkRemoveOutliers;
    private JCheckBox chkSaveArff;
    private JCheckBox chkSaveModel;
    private JCheckBox chkSaveSets;
    private JCheckBox chkShrinkFeatures;
    private JCheckBox chkSkipTest;
    private JCheckBox chkSkipTrain;
    private JPanel contentPane;
    private JPanel panelEmailSettings;
    private JPasswordField fldPassword;
    private JTextField txtMetadata;
    private JTextField txtRecipient;
    private JTextField txtRuns;
    private JTextField txtSender;
    private JTextField txtServer;
    private JTextField txtUsername;
    private JComboBox<CryptoProtocol> cbProtocol;
    private Set<String> selectedMethods;

    public static JProgressBar progressBar;
    private JLabel lblConfirm;
    private JPasswordField fldConfirm;
    private JButton btnReset;
    private JButton btnValidate;

    public static void main(String[] args)
    {
        EventQueue.invokeLater(() ->
        {
            UserInterface frame = new UserInterface();
            frame.setVisible(true);
        });
    }

    public UserInterface()
    {
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("logo.png")).getImage());
        setResizable(false);
        setTitle("AntiSpamWeka");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 768, 576);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        selectedMethods = new LinkedHashSet<>(Arrays.asList("A1DE", "NB", "J48", "FRF", "MLP", "RBF", "LIBSVM", "SPEGASOS"));
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panelTop = new JPanel();
        contentPane.add(panelTop, BorderLayout.CENTER);
        panelTop.setLayout(null);

        JPanel panelAntiSpamSettings = new JPanel();
        panelAntiSpamSettings.setBounds(6, 6, 746, 500);
        panelTop.add(panelAntiSpamSettings);
        panelAntiSpamSettings.setBorder(new TitledBorder(null, "Anti Spam settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelAntiSpamSettings.setLayout(null);

        JLabel lblMetadata = new JLabel("Metadata");
        lblMetadata.setBounds(12, 18, 60, 45);
        panelAntiSpamSettings.add(lblMetadata);

        txtMetadata = new JTextField();
        txtMetadata.setText(USER_HOME + "!git!anti-spam-weka-data!2017_BASE2!metadata.txt".replaceAll("!", File.separator));
        txtMetadata.setEditable(false);
        txtMetadata.setColumns(10);
        txtMetadata.setBounds(79, 18, 399, 45);
        panelAntiSpamSettings.add(txtMetadata);

        JButton btnChooseMetadata = new JButton("Choose");
        btnChooseMetadata.addActionListener(ae ->
        {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showOpenDialog(null);
            File selectedFile = jFileChooser.getSelectedFile();
            if (selectedFile != null)
            {
                txtMetadata.setText(selectedFile.getAbsolutePath());
            }
        });
        btnChooseMetadata.setBounds(485, 19, 97, 45);
        panelAntiSpamSettings.add(btnChooseMetadata);

        JPanel panelMethods = new JPanel();
        panelMethods.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Methods", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelMethods.setBounds(12, 72, 412, 416);
        panelAntiSpamSettings.add(panelMethods);
        panelMethods.setLayout(new GridLayout(0, 4));

        Arrays.stream(MethodConfiguration.values()).forEach(method ->
        {
            String methodName = method.name();

            JCheckBox checkBox = new JCheckBox(methodName);
            checkBox.setToolTipText(methodName);
            if (selectedMethods.contains(methodName))
            {
                checkBox.setSelected(true);
            }
            checkBox.addActionListener(ae ->
            {
                JCheckBox source = (JCheckBox) ae.getSource();
                if (source.isSelected())
                    selectedMethods.add(source.getText());
                else
                    selectedMethods.remove(source.getText());
            });

            panelMethods.add(checkBox);
        });

        JPanel panelRunSettings = new JPanel();
        panelRunSettings.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Run settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelRunSettings.setBounds(432, 72, 304, 150);
        panelAntiSpamSettings.add(panelRunSettings);
        panelRunSettings.setLayout(new GridLayout(0, 2));

        chkSkipTrain = new JCheckBox("Skip Train");
        chkSkipTrain.setToolTipText("Do not train the classifier(s)");
        panelRunSettings.add(chkSkipTrain);

        chkSkipTest = new JCheckBox("Skip Test");
        chkSkipTest.setToolTipText("Do not test the classifier(s)");
        panelRunSettings.add(chkSkipTest);

        chkShrinkFeatures = new JCheckBox("Shrink Features");
        chkShrinkFeatures.setToolTipText("Perform dimensionality reduction in the feature space");
        chkShrinkFeatures.setSelected(true);
        panelRunSettings.add(chkShrinkFeatures);

        chkBalanceClasses = new JCheckBox("Balance Classes");
        chkBalanceClasses.setToolTipText("Equalize the number of instances for each class (i.e.ham and spam)");
        chkBalanceClasses.setSelected(true);
        panelRunSettings.add(chkBalanceClasses);

        chkIncludeEmpty = new JCheckBox("Include Empty");
        chkIncludeEmpty.setToolTipText("Include empty patterns while testing the classifier");
        chkIncludeEmpty.setSelected(true);
        panelRunSettings.add(chkIncludeEmpty);

        chkRemoveOutliers = new JCheckBox("Remove Outliers");
        chkRemoveOutliers.setToolTipText("Rollback evaluations that contain outliers");
        panelRunSettings.add(chkRemoveOutliers);

        chkSaveArff = new JCheckBox("Save ARFF");
        chkSaveArff.setToolTipText("Save the whole data set to a .arff file");
        panelRunSettings.add(chkSaveArff);

        chkSaveModel = new JCheckBox("Save Model");
        chkSaveModel.setToolTipText("Save the classifier to a .model file");
        panelRunSettings.add(chkSaveModel);

        chkSaveSets = new JCheckBox("Save Sets");
        chkSaveSets.setToolTipText("Save the training and testing data sets to a .csv file");
        panelRunSettings.add(chkSaveSets);

        chkEmailResults = new JCheckBox("E-mail results");
        chkEmailResults.addActionListener(ae ->
        {
            if (((JCheckBox) ae.getSource()).isSelected())
            {
                setPanelEnabled(panelEmailSettings, true);
                btnRun.setEnabled(false);
            }
            else
            {
                setPanelEnabled(panelEmailSettings, false);
                btnRun.setEnabled(true);
            }
        });
        chkEmailResults.setSelected(true);
        panelRunSettings.add(chkEmailResults);

        txtRuns = new JTextField();
        txtRuns.setHorizontalAlignment(SwingConstants.CENTER);
        txtRuns.setText("10");
        txtRuns.setColumns(10);
        txtRuns.setBounds(658, 18, 78, 45);
        panelAntiSpamSettings.add(txtRuns);

        JLabel lblRuns = new JLabel("No. Runs");
        lblRuns.setBounds(594, 18, 62, 45);
        panelAntiSpamSettings.add(lblRuns);

        panelEmailSettings = new JPanel();
        panelEmailSettings.setBounds(432, 225, 304, 263);
        panelAntiSpamSettings.add(panelEmailSettings);
        panelEmailSettings.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "E-mail settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelEmailSettings.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel lblSender = new JLabel("Sender");
        panelEmailSettings.add(lblSender);

        txtSender = new JTextField();
        txtSender.setText("sender@server.com");
        txtSender.setColumns(10);
        panelEmailSettings.add(txtSender);

        JLabel lblRecipient = new JLabel("Recipient");
        panelEmailSettings.add(lblRecipient);

        txtRecipient = new JTextField();
        txtRecipient.setText("recipient@server.com");
        txtRecipient.setColumns(10);
        panelEmailSettings.add(txtRecipient);

        JLabel lblServer = new JLabel("Server");
        panelEmailSettings.add(lblServer);

        txtServer = new JTextField();
        txtServer.setText("smtp.mail.server.com");
        txtServer.setColumns(10);
        panelEmailSettings.add(txtServer);

        JLabel lblProtocol = new JLabel("Protocol");
        panelEmailSettings.add(lblProtocol);

        cbProtocol = new JComboBox<>();
        Arrays.stream(CryptoProtocol.values()).forEach(v -> cbProtocol.addItem(v));
        cbProtocol.setSelectedItem(CryptoProtocol.TLS);
        panelEmailSettings.add(cbProtocol);

        JLabel lblUsername = new JLabel("Username");
        panelEmailSettings.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setText("sender@server.com");
        txtUsername.setColumns(10);
        panelEmailSettings.add(txtUsername);

        JLabel lblPassword = new JLabel("Password");
        panelEmailSettings.add(lblPassword);

        fldPassword = new JPasswordField();
        fldPassword.setText("sender123password");
        panelEmailSettings.add(fldPassword);

        lblConfirm = new JLabel("Confirm Password");
        panelEmailSettings.add(lblConfirm);

        fldConfirm = new JPasswordField();
        fldConfirm.setText("sender123password");
        panelEmailSettings.add(fldConfirm);

        btnValidate = new JButton("Validate");
        btnValidate.addActionListener(ae ->
        {
            if (!new String(fldPassword.getPassword()).equals(new String(fldConfirm.getPassword())))
            {
                JOptionPane.showMessageDialog(null, "Password and confirmation do not match");
                btnRun.setEnabled(false);
            }
            else
            {
                boolean dryRunStatus = MailHelper.sendMail(
                    (CryptoProtocol) cbProtocol.getSelectedItem(),
                    txtUsername.getText(), new String(fldPassword.getPassword()), txtServer.getText(), "", "", "", "", "", true);

                if (!dryRunStatus)
                {
                    JOptionPane.showMessageDialog(null, "Connection error");
                    btnRun.setEnabled(false);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Connection success");
                    btnRun.setEnabled(true);
                }
            }
        });
        panelEmailSettings.add(btnValidate);

        btnReset = new JButton("Reset");
        btnReset.addActionListener(ae ->
        {
            txtSender.setText("sender@server.com");
            txtRecipient.setText("recipient@server.com");
            txtServer.setText("smtp.mail.server.com");
            cbProtocol.setSelectedItem(CryptoProtocol.TLS);
            txtUsername.setText("sender@server.com");
            fldPassword.setText("sender123password");
            fldConfirm.setText("sender123password");
            btnRun.setEnabled(false);
        });
        panelEmailSettings.add(btnReset);

        JPanel panelBottom = new JPanel();
        contentPane.add(panelBottom, BorderLayout.SOUTH);
        panelBottom.setLayout(new BorderLayout(0, 0));

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        panelBottom.add(progressBar, BorderLayout.CENTER);

        btnRun = new JButton("Run");
        btnRun.setEnabled(false);
        panelBottom.add(btnRun, BorderLayout.EAST);
        btnRun.addActionListener(ae ->
        {
            try
            {
                setUpExecutionHelper();

                if (!ExecutionHelper.isRunning)
                {
                    setPanelEnabled(contentPane, false);
                    btnRun.setText("Exit");
                    btnRun.setEnabled(true);
                    progressBar.setEnabled(true);

                    new Thread(() -> ExecutionHelper.run()).start();
                }
                else
                {
                    System.exit(0);
                }
            }
            catch (Exception e)
            {
                LOGGER.error(e);
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });
    }

    private void setUpExecutionHelper() throws IOException
    {
        // anti spam settings
        ExecutionHelper.setUpMetadata(txtMetadata.getText());
        ExecutionHelper.setUpMethods(selectedMethods);
        ExecutionHelper.numberOfRuns = Integer.parseInt(txtRuns.getText());

        // run settings
        ExecutionHelper.skipTrain = chkSkipTrain.isSelected();
        ExecutionHelper.skipTest = chkSkipTest.isSelected();
        ExecutionHelper.shrinkFeatures = chkShrinkFeatures.isSelected();
        ExecutionHelper.balanceClasses = chkBalanceClasses.isSelected();
        ExecutionHelper.includeEmpty = chkIncludeEmpty.isSelected();
        ExecutionHelper.removeOutliers = chkRemoveOutliers.isSelected();
        ExecutionHelper.saveArff = chkSaveArff.isSelected();
        ExecutionHelper.saveModel = chkSaveModel.isSelected();
        ExecutionHelper.saveSets = chkSaveSets.isSelected();
        ExecutionHelper.emailResults = chkEmailResults.isSelected();

        // e-mail settings
        ExecutionHelper.sender = txtSender.getText();
        ExecutionHelper.recipient = txtRecipient.getText();
        ExecutionHelper.server = txtServer.getText();
        ExecutionHelper.protocol = (CryptoProtocol) cbProtocol.getSelectedItem();
        ExecutionHelper.username = txtUsername.getText();
        ExecutionHelper.password = new String(fldPassword.getPassword());
    }

    private void setPanelEnabled(JPanel panel, Boolean isEnabled)
    {
        panel.setEnabled(isEnabled);
        for (Component component : panel.getComponents())
        {
            if (component instanceof JPanel)
                setPanelEnabled((JPanel) component, isEnabled);
            component.setEnabled(isEnabled);
        }
    }
}
