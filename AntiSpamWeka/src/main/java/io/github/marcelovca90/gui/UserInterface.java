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

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import io.github.marcelovca90.common.MethodConfiguration;
import io.github.marcelovca90.helper.ExecutionHelper;

public class UserInterface extends JFrame
{
    private static final long serialVersionUID = 1L;
    private static final String USER_HOME = System.getProperty("user.home");

    private JCheckBox chkBalanceClasses;
    private JCheckBox chkIncludeEmpty;
    private JCheckBox chkRemoveOutliers;
    private JCheckBox chkSaveArff;
    private JCheckBox chkSaveModel;
    private JCheckBox chkSaveSets;
    private JCheckBox chkShrinkFeatures;
    private JCheckBox chkSkipTest;
    private JCheckBox chkSkipTrain;
    private JPanel contentPane;
    private JPasswordField fldPassword;
    private JTextField txtMetadata;
    private JTextField txtOptions;
    private JTextField txtRecipient;
    private JTextField txtRuns;
    private JTextField txtSender;
    private JTextField txtServer;
    private JTextField txtUsername;
    private Set<String> selectedMethods;

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
        setTitle("AntiSpamWeka");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 576);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panelAntiSpamSettings = new JPanel();
        panelAntiSpamSettings.setBorder(new TitledBorder(null, "Anti Spam settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelAntiSpamSettings.setBounds(12, 13, 693, 244);
        contentPane.add(panelAntiSpamSettings);
        panelAntiSpamSettings.setLayout(null);

        JLabel lblMetadata = new JLabel("Metadata");
        lblMetadata.setBounds(12, 24, 60, 16);
        panelAntiSpamSettings.add(lblMetadata);

        txtMetadata = new JTextField();
        txtMetadata.setText(USER_HOME + "!git!anti-spam-weka-data!2017_BASE2!metadataUpTo1024.txt".replaceAll("!", File.separator));
        txtMetadata.setEditable(false);
        txtMetadata.setColumns(10);
        txtMetadata.setBounds(80, 21, 378, 22);
        panelAntiSpamSettings.add(txtMetadata);

        JButton btnChooseMetadata = new JButton("Choose...");
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
        btnChooseMetadata.setBounds(455, 21, 97, 25);
        panelAntiSpamSettings.add(btnChooseMetadata);

        JPanel panelMethods = new JPanel();
        panelMethods.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelMethods.setBounds(12, 52, 376, 177);
        panelAntiSpamSettings.add(panelMethods);
        panelMethods.setLayout(new GridLayout(0, 4));

        selectedMethods = new LinkedHashSet<>(Arrays.asList("A1DE", "NB", "J48", "FRF", "MLP", "RBF", "LIBSVM", "SPEGASOS")); // default methods

        Arrays.stream(MethodConfiguration.values()).forEach(method ->
        {
            JCheckBox checkBox = new JCheckBox(method.name());
            checkBox.setToolTipText(method.getName());
            if (selectedMethods.contains(method.name()))
                checkBox.setSelected(true);
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

        JPanel panelOptions = new JPanel();
        panelOptions.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelOptions.setBounds(398, 52, 280, 177);
        panelAntiSpamSettings.add(panelOptions);
        panelOptions.setLayout(new GridLayout(0, 2));

        chkSkipTrain = new JCheckBox("Skip Train");
        chkSkipTrain.setToolTipText("Do not train the classifier(s)");
        panelOptions.add(chkSkipTrain);

        chkSkipTest = new JCheckBox("Skip Test");
        chkSkipTest.setToolTipText("Do not test the classifier(s)");
        panelOptions.add(chkSkipTest);

        chkShrinkFeatures = new JCheckBox("Shrink Features");
        chkShrinkFeatures.setToolTipText("Perform dimensionality reduction in the feature space");
        chkShrinkFeatures.setSelected(true);
        panelOptions.add(chkShrinkFeatures);

        chkBalanceClasses = new JCheckBox("Balance Classes");
        chkBalanceClasses.setToolTipText("Equalize the number of instances for each class (i.e.ham and spam)");
        chkBalanceClasses.setSelected(true);
        panelOptions.add(chkBalanceClasses);

        chkIncludeEmpty = new JCheckBox("Include Empty");
        chkIncludeEmpty.setToolTipText("Include empty patterns while testing the classifier");
        chkIncludeEmpty.setSelected(true);
        panelOptions.add(chkIncludeEmpty);

        chkRemoveOutliers = new JCheckBox("Remove Outliers");
        chkRemoveOutliers.setToolTipText("Rollback evaluations that contain outliers");
        panelOptions.add(chkRemoveOutliers);

        chkSaveArff = new JCheckBox("Save ARFF");
        chkSaveArff.setToolTipText("Save the whole data set to a .arff file");
        panelOptions.add(chkSaveArff);

        chkSaveModel = new JCheckBox("Save Model");
        chkSaveModel.setToolTipText("Save the classifier to a .model file");
        panelOptions.add(chkSaveModel);

        chkSaveSets = new JCheckBox("Save Sets");
        chkSaveSets.setToolTipText("Save the training and testing data sets to a .csv file");
        panelOptions.add(chkSaveSets);

        txtRuns = new JTextField();
        txtRuns.setHorizontalAlignment(SwingConstants.CENTER);
        txtRuns.setText("10");
        txtRuns.setColumns(10);
        txtRuns.setBounds(600, 21, 78, 22);
        panelAntiSpamSettings.add(txtRuns);

        JLabel lblRuns = new JLabel("Runs");
        lblRuns.setBounds(564, 24, 38, 16);
        panelAntiSpamSettings.add(lblRuns);

        JPanel panelMailSettings = new JPanel();
        panelMailSettings.setBorder(new TitledBorder(null, "Mail settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelMailSettings.setBounds(711, 13, 307, 190);
        contentPane.add(panelMailSettings);
        panelMailSettings.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel lblSender = new JLabel("Sender");
        panelMailSettings.add(lblSender);

        txtSender = new JTextField();
        txtSender.setText("sender@server.com");
        txtSender.setColumns(10);
        panelMailSettings.add(txtSender);

        JLabel lblRecipient = new JLabel("Recipient");
        panelMailSettings.add(lblRecipient);

        txtRecipient = new JTextField();
        txtRecipient.setText("recipient@server.com");
        txtRecipient.setColumns(10);
        panelMailSettings.add(txtRecipient);

        JLabel lblServer = new JLabel("Server");
        panelMailSettings.add(lblServer);

        txtServer = new JTextField();
        txtServer.setText("mail.server.com:port");
        txtServer.setColumns(10);
        panelMailSettings.add(txtServer);

        JLabel lblOptions = new JLabel("Options");
        panelMailSettings.add(lblOptions);

        txtOptions = new JTextField();
        txtOptions.setText("tls=yes");
        txtOptions.setColumns(10);
        panelMailSettings.add(txtOptions);

        JLabel lblUsername = new JLabel("Username");
        panelMailSettings.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setText("sender@server.com");
        txtUsername.setColumns(10);
        panelMailSettings.add(txtUsername);

        JLabel lblPassword = new JLabel("Password");
        panelMailSettings.add(lblPassword);

        fldPassword = new JPasswordField();
        fldPassword.setText("sender123password");
        panelMailSettings.add(fldPassword);

        JPanel panelOutput = new JPanel();
        panelOutput.setBorder(new TitledBorder(null, "Output", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelOutput.setBounds(12, 260, 1006, 285);
        contentPane.add(panelOutput);
        panelOutput.setLayout(null);

        JScrollPane scrollPaneOutput = new JScrollPane();
        scrollPaneOutput.setBounds(12, 24, 988, 248);
        panelOutput.add(scrollPaneOutput);

        JTextArea textAreaOutput = new JTextArea();
        textAreaOutput.setFont(new Font("Consolas", Font.PLAIN, 12));
        textAreaOutput.setTabSize(4);
        scrollPaneOutput.setViewportView(textAreaOutput);
        textAreaOutput.setEditable(false);

        JPanel panelExecution = new JPanel();
        panelExecution.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Execution", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelExecution.setBounds(711, 204, 307, 53);
        contentPane.add(panelExecution);
        panelExecution.setLayout(new GridLayout(1, 2, 0, 0));

        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(ae ->
        {
            try
            {
                ExecutionHelper.setUpMetadata(txtMetadata.getText());
                ExecutionHelper.setUpMethods(selectedMethods);
                ExecutionHelper.numberOfRuns = Integer.parseInt(txtRuns.getText());
                ExecutionHelper.skipTrain = chkSkipTrain.isSelected();
                ExecutionHelper.skipTest = chkSkipTest.isSelected();
                ExecutionHelper.shrinkFeatures = chkShrinkFeatures.isSelected();
                ExecutionHelper.balanceClasses = chkBalanceClasses.isSelected();
                ExecutionHelper.includeEmpty = chkIncludeEmpty.isSelected();
                ExecutionHelper.removeOutliers = chkRemoveOutliers.isSelected();
                ExecutionHelper.saveArff = chkSaveArff.isSelected();
                ExecutionHelper.saveModel = chkSaveModel.isSelected();
                ExecutionHelper.saveSets = chkSaveSets.isSelected();
                ExecutionHelper.run();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });
        panelExecution.add(btnStart);

        JButton btnClearOutput = new JButton("Clear output");
        btnClearOutput.addActionListener(ae -> textAreaOutput.setText(""));
        panelExecution.add(btnClearOutput);
    }
}
