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
import java.awt.GridLayout;
import java.lang.management.ManagementFactory;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.sun.management.OperatingSystemMXBean;

import io.github.marcelovca90.common.MethodConfiguration;

public class View extends JFrame
{
    private static final long serialVersionUID = 1L;

    private JPanel mycontentPane;
    private JTextField txtJarPath;
    private JTextField txtMetadata;
    private JTextField txtSender;
    private JTextField txtRecipient;
    private JTextField txtServer;
    private JTextField txtOptions;
    private JTextField txtUsername;
    private JPasswordField fldPassword;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() -> {
            View frame = new View();
            frame.setVisible(true);
        });
    }

    /**
     * Create the frame.
     */
    public View()
    {
        setTitle("AntiSpamWeka");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 576);
        mycontentPane = new JPanel();
        mycontentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(mycontentPane);
        mycontentPane.setLayout(null);

        JPanel panelAntiSpamSettings = new JPanel();
        panelAntiSpamSettings.setBorder(new TitledBorder(null, "Anti Spam settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelAntiSpamSettings.setBounds(12, 107, 693, 284);
        mycontentPane.add(panelAntiSpamSettings);
        panelAntiSpamSettings.setLayout(null);

        JLabel lblJarPath = new JLabel("JAR path");
        lblJarPath.setBounds(12, 24, 60, 16);
        panelAntiSpamSettings.add(lblJarPath);

        txtJarPath = new JTextField();
        txtJarPath.setEditable(false);
        txtJarPath.setColumns(10);
        txtJarPath.setBounds(80, 21, 489, 22);
        panelAntiSpamSettings.add(txtJarPath);

        JButton btnChooseJarPath = new JButton("Choose...");
        btnChooseJarPath.setBounds(581, 20, 97, 25);
        panelAntiSpamSettings.add(btnChooseJarPath);

        JLabel lblMetadata = new JLabel("Metadata");
        lblMetadata.setBounds(12, 59, 60, 16);
        panelAntiSpamSettings.add(lblMetadata);

        txtMetadata = new JTextField();
        txtMetadata.setEditable(false);
        txtMetadata.setColumns(10);
        txtMetadata.setBounds(80, 56, 489, 22);
        panelAntiSpamSettings.add(txtMetadata);

        JButton btnChooseMetadata = new JButton("Choose...");
        btnChooseMetadata.setBounds(581, 55, 97, 25);
        panelAntiSpamSettings.add(btnChooseMetadata);

        JPanel panelMethods = new JPanel();
        panelMethods.setBounds(12, 93, 666, 120);
        panelAntiSpamSettings.add(panelMethods);
        panelMethods.setLayout(new GridLayout(0, 4));

        Arrays.stream(MethodConfiguration.values()).forEach(m -> panelMethods.add(new JCheckBox(m.toString())));

        JPanel panelOptions = new JPanel();
        panelOptions.setBounds(12, 218, 666, 53);
        panelAntiSpamSettings.add(panelOptions);
        panelOptions.setLayout(new GridLayout(0, 4));

        JCheckBox chkSkipTrain = new JCheckBox("Skip Train");
        panelOptions.add(chkSkipTrain);

        JCheckBox chkSkipTest = new JCheckBox("Skip Test");
        panelOptions.add(chkSkipTest);

        JCheckBox chkBalClass = new JCheckBox("Balance Classes");
        panelOptions.add(chkBalClass);

        JCheckBox chkIncEmpty = new JCheckBox("Include Empty Patterns");
        panelOptions.add(chkIncEmpty);

        JCheckBox chkRemOutliers = new JCheckBox("Remove Outliers");
        panelOptions.add(chkRemOutliers);

        JCheckBox chkSaveArff = new JCheckBox("Save ARFF");
        panelOptions.add(chkSaveArff);

        JCheckBox chkSaveModel = new JCheckBox("Save Model");
        panelOptions.add(chkSaveModel);

        JCheckBox chkSaveSets = new JCheckBox("Save Sets");
        panelOptions.add(chkSaveSets);

        JPanel panelMailSettings = new JPanel();
        panelMailSettings.setBorder(new TitledBorder(null, "Mail settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelMailSettings.setBounds(12, 394, 693, 122);
        mycontentPane.add(panelMailSettings);
        panelMailSettings.setLayout(null);

        JLabel lblSender = new JLabel("Sender");
        lblSender.setBounds(12, 26, 60, 16);
        panelMailSettings.add(lblSender);

        txtSender = new JTextField();
        txtSender.setText("sender@server.com");
        txtSender.setColumns(10);
        txtSender.setBounds(80, 23, 260, 22);
        panelMailSettings.add(txtSender);

        JLabel lblRecipient = new JLabel("Recipient");
        lblRecipient.setBounds(350, 26, 60, 16);
        panelMailSettings.add(lblRecipient);

        txtRecipient = new JTextField();
        txtRecipient.setText("recipient@server.com");
        txtRecipient.setColumns(10);
        txtRecipient.setBounds(418, 23, 260, 22);
        panelMailSettings.add(txtRecipient);

        JLabel lblServer = new JLabel("Server");
        lblServer.setBounds(12, 58, 60, 16);
        panelMailSettings.add(lblServer);

        txtServer = new JTextField();
        txtServer.setText("mail.server.com:port");
        txtServer.setColumns(10);
        txtServer.setBounds(80, 55, 260, 22);
        panelMailSettings.add(txtServer);

        JLabel lblOptions = new JLabel("Options");
        lblOptions.setBounds(350, 58, 60, 16);
        panelMailSettings.add(lblOptions);

        txtOptions = new JTextField();
        txtOptions.setText("tls=yes");
        txtOptions.setColumns(10);
        txtOptions.setBounds(418, 55, 260, 22);
        panelMailSettings.add(txtOptions);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(12, 90, 60, 16);
        panelMailSettings.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setText("sender@server.com");
        txtUsername.setColumns(10);
        txtUsername.setBounds(80, 87, 260, 22);
        panelMailSettings.add(txtUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(350, 93, 60, 16);
        panelMailSettings.add(lblPassword);

        fldPassword = new JPasswordField();
        fldPassword.setText("sender123password");
        fldPassword.setBounds(418, 87, 260, 22);
        panelMailSettings.add(fldPassword);

        JPanel panelJvmSettings = new JPanel();
        panelJvmSettings.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "JVM settings", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelJvmSettings.setBounds(12, 13, 694, 92);
        mycontentPane.add(panelJvmSettings);
        panelJvmSettings.setLayout(null);

        OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        long maxHeap = bean.getTotalPhysicalMemorySize() / 1024 / 1024;

        JSlider sliderHeapSize = new JSlider();
        sliderHeapSize.setSnapToTicks(true);
        sliderHeapSize.setPaintTicks(true);
        sliderHeapSize.setPaintLabels(true);
        sliderHeapSize.setMinimum((int) (maxHeap / 4));
        sliderHeapSize.setMaximum((int) maxHeap);
        sliderHeapSize.setMinorTickSpacing((int) (maxHeap / 4));
        sliderHeapSize.setMajorTickSpacing((int) (maxHeap / 4));
        sliderHeapSize.setValue((int) (3 * maxHeap / 4));
        sliderHeapSize.setBounds(84, 30, 252, 46);
        panelJvmSettings.add(sliderHeapSize);

        JLabel lblStackSize = new JLabel("Stack Size");
        lblStackSize.setBounds(350, 30, 60, 49);
        panelJvmSettings.add(lblStackSize);

        long maxStack = maxHeap / 1024;

        JSlider sliderStackSize = new JSlider();
        sliderStackSize.setValue(12211);
        sliderStackSize.setSnapToTicks(true);
        sliderStackSize.setPaintTicks(true);
        sliderStackSize.setPaintLabels(true);
        sliderStackSize.setMinimum((int) (maxStack / 4));
        sliderStackSize.setMaximum((int) maxStack);
        sliderStackSize.setMinorTickSpacing((int) (maxStack / 4));
        sliderStackSize.setMajorTickSpacing((int) (maxStack / 4));
        sliderStackSize.setValue((int) (3 * maxStack / 4));
        sliderStackSize.setBounds(430, 30, 252, 46);
        panelJvmSettings.add(sliderStackSize);

        JLabel lblHeapSize = new JLabel("Heap Size");
        lblHeapSize.setBounds(12, 30, 60, 49);
        panelJvmSettings.add(lblHeapSize);

        JPanel panelOutput = new JPanel();
        panelOutput.setBorder(new TitledBorder(null, "Output", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelOutput.setBounds(717, 13, 277, 503);
        mycontentPane.add(panelOutput);
        panelOutput.setLayout(null);

        JScrollPane scrollPaneOutput = new JScrollPane();
        scrollPaneOutput.setBounds(12, 24, 253, 432);
        panelOutput.add(scrollPaneOutput);

        JTextArea textAreaOutput = new JTextArea();
        scrollPaneOutput.setViewportView(textAreaOutput);
        textAreaOutput.setEditable(false);

        JButton btnStart = new JButton("Start");
        btnStart.setBounds(12, 465, 123, 25);
        panelOutput.add(btnStart);

        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(142, 465, 123, 25);
        panelOutput.add(btnClear);
    }
}
