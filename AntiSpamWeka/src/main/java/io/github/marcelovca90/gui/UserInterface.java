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
import java.awt.FlowLayout;
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
        panelAntiSpamSettings.setBounds(6, 6, 746, 493);
        panelTop.add(panelAntiSpamSettings);
        panelAntiSpamSettings.setBorder(new TitledBorder(null, "Anti Spam settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelAntiSpamSettings.setLayout(null);

        JLabel lblMetadata = new JLabel("Metadata");
        lblMetadata.setBounds(12, 18, 60, 45);
        panelAntiSpamSettings.add(lblMetadata);

        txtMetadata = new JTextField();
        txtMetadata.setText(USER_HOME + "!git!anti-spam-weka-data!2017_BASE2!metadataUpTo16.txt".replaceAll("!", File.separator));
        txtMetadata.setEditable(false);
        txtMetadata.setColumns(10);
        txtMetadata.setBounds(79, 18, 399, 45);
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
        btnChooseMetadata.setBounds(490, 19, 97, 45);
        panelAntiSpamSettings.add(btnChooseMetadata);

        JPanel panelMethods = new JPanel();
        panelMethods.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Methods", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelMethods.setBounds(12, 72, 412, 410);
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
        panelRunSettings.setBounds(432, 72, 304, 202);
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

        txtRuns = new JTextField();
        txtRuns.setHorizontalAlignment(SwingConstants.CENTER);
        txtRuns.setText("10");
        txtRuns.setColumns(10);
        txtRuns.setBounds(658, 18, 78, 45);
        panelAntiSpamSettings.add(txtRuns);

        JLabel lblRuns = new JLabel("No. Runs");
        lblRuns.setBounds(599, 18, 57, 45);
        panelAntiSpamSettings.add(lblRuns);

        JPanel panelMailSettings = new JPanel();
        panelMailSettings.setBounds(432, 280, 304, 202);
        panelAntiSpamSettings.add(panelMailSettings);
        panelMailSettings.setBorder(new TitledBorder(null, "Mail settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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

        JPanel panelBottom = new JPanel();
        FlowLayout fl_panelBottom = (FlowLayout) panelBottom.getLayout();
        fl_panelBottom.setAlignment(FlowLayout.TRAILING);
        contentPane.add(panelBottom, BorderLayout.SOUTH);

        JButton btnRun = new JButton("Run");
        panelBottom.add(btnRun);
        btnRun.addActionListener(ae ->
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

                if (!ExecutionHelper.isRunning)
                {
                    setPanelEnabled(contentPane, false);
                    btnRun.setText("Exit");
                    btnRun.setEnabled(true);

                    new Thread(() -> ExecutionHelper.run()).start();
                }
                else
                {
                    System.exit(0);
                }
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        });
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
