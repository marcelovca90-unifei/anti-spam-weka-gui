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
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.sun.management.OperatingSystemMXBean;

import io.github.marcelovca90.common.MethodConfiguration;

public class View extends JFrame
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
    private JSlider sliderHeapSize;
    private JSlider sliderStackSize;
    private JTextField txtJarPath;
    private JTextField txtMetadata;
    private JTextField txtOptions;
    private JTextField txtRecipient;
    private JTextField txtRuns;
    private JTextField txtSender;
    private JTextField txtServer;
    private JTextField txtUsername;
    private Set<String> selectedMethods;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() ->
        {
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
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panelAntiSpamSettings = new JPanel();
        panelAntiSpamSettings.setBorder(new TitledBorder(null, "Anti Spam settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelAntiSpamSettings.setBounds(12, 107, 693, 284);
        contentPane.add(panelAntiSpamSettings);
        panelAntiSpamSettings.setLayout(null);

        JLabel lblJarPath = new JLabel("JAR path");
        lblJarPath.setBounds(12, 24, 60, 16);
        panelAntiSpamSettings.add(lblJarPath);

        txtJarPath = new JTextField();
        txtJarPath.setText(USER_HOME + "!git!anti-spam-weka!AntiSpamWeka!target!AntiSpamWeka-0.0.1-SNAPSHOT-jar-with-dependencies.jar".replaceAll("!", File.separator));
        txtJarPath.setEditable(false);
        txtJarPath.setColumns(10);
        txtJarPath.setBounds(80, 21, 378, 22);
        panelAntiSpamSettings.add(txtJarPath);

        JButton btnChooseJarPath = new JButton("Choose...");
        btnChooseJarPath.addActionListener(ae ->
        {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showOpenDialog(null);
            File selectedFile = jFileChooser.getSelectedFile();
            if (selectedFile != null)
            {
                txtJarPath.setText(selectedFile.getAbsolutePath());
            }
        });
        btnChooseJarPath.setBounds(470, 20, 97, 25);
        panelAntiSpamSettings.add(btnChooseJarPath);

        JLabel lblMetadata = new JLabel("Metadata");
        lblMetadata.setBounds(12, 59, 60, 16);
        panelAntiSpamSettings.add(lblMetadata);

        txtMetadata = new JTextField();
        txtMetadata.setText(USER_HOME + "!git!anti-spam-weka-data!2017_BASE2!metadataUpTo1024.txt".replaceAll("!", File.separator));
        txtMetadata.setEditable(false);
        txtMetadata.setColumns(10);
        txtMetadata.setBounds(80, 56, 378, 22);
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
        btnChooseMetadata.setBounds(470, 55, 97, 25);
        panelAntiSpamSettings.add(btnChooseMetadata);

        JPanel panelMethods = new JPanel();
        panelMethods.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelMethods.setBounds(12, 93, 376, 180);
        panelAntiSpamSettings.add(panelMethods);
        panelMethods.setLayout(new GridLayout(0, 4));

        selectedMethods = new LinkedHashSet<>(Arrays.asList("A1DE", "NB", "J48", "FRF", "MLP", "RBF", "LIBSVM", "SPEGASOS")); // default methods

        Arrays.stream(MethodConfiguration.values()).forEach(method ->
        {
            JCheckBox checkBox = new JCheckBox(method.name());
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
        panelOptions.setBounds(398, 93, 280, 180);
        panelAntiSpamSettings.add(panelOptions);
        panelOptions.setLayout(new GridLayout(0, 2));

        chkSkipTrain = new JCheckBox("Skip Train");
        panelOptions.add(chkSkipTrain);

        chkSkipTest = new JCheckBox("Skip Test");
        panelOptions.add(chkSkipTest);

        chkShrinkFeatures = new JCheckBox("Shrink Features");
        chkShrinkFeatures.setSelected(true);
        panelOptions.add(chkShrinkFeatures);

        chkBalanceClasses = new JCheckBox("Balance Classes");
        chkBalanceClasses.setSelected(true);
        panelOptions.add(chkBalanceClasses);

        chkIncludeEmpty = new JCheckBox("Include Empty");
        chkIncludeEmpty.setSelected(true);
        panelOptions.add(chkIncludeEmpty);

        chkRemoveOutliers = new JCheckBox("Remove Outliers");
        panelOptions.add(chkRemoveOutliers);

        chkSaveArff = new JCheckBox("Save ARFF");
        panelOptions.add(chkSaveArff);

        chkSaveModel = new JCheckBox("Save Model");
        panelOptions.add(chkSaveModel);

        chkSaveSets = new JCheckBox("Save Sets");
        panelOptions.add(chkSaveSets);

        txtRuns = new JTextField();
        txtRuns.setHorizontalAlignment(SwingConstants.CENTER);
        txtRuns.setText("10");
        txtRuns.setColumns(10);
        txtRuns.setBounds(579, 56, 99, 22);
        panelAntiSpamSettings.add(txtRuns);

        JLabel lblRuns = new JLabel("Runs");
        lblRuns.setHorizontalAlignment(SwingConstants.CENTER);
        lblRuns.setBounds(579, 24, 99, 16);
        panelAntiSpamSettings.add(lblRuns);

        JPanel panelMailSettings = new JPanel();
        panelMailSettings.setBorder(new TitledBorder(null, "Mail settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelMailSettings.setBounds(12, 394, 693, 122);
        contentPane.add(panelMailSettings);
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
        contentPane.add(panelJvmSettings);
        panelJvmSettings.setLayout(null);

        OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        long maxHeap = bean.getTotalPhysicalMemorySize() / 1024 / 1024;

        sliderHeapSize = new JSlider();
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

        sliderStackSize = new JSlider();
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
        contentPane.add(panelOutput);
        panelOutput.setLayout(null);

        JScrollPane scrollPaneOutput = new JScrollPane();
        scrollPaneOutput.setBounds(12, 24, 253, 432);
        panelOutput.add(scrollPaneOutput);

        JTextArea textAreaOutput = new JTextArea();
        textAreaOutput.setFont(new Font("Consolas", Font.PLAIN, 12));
        textAreaOutput.setTabSize(4);
        scrollPaneOutput.setViewportView(textAreaOutput);
        textAreaOutput.setEditable(false);

        JButton btnGenerate = new JButton("Generate Script");
        btnGenerate.addActionListener(ae ->
        {
            String script = buildScript();
            textAreaOutput.setText(script);
            setClipboardContent(script);
        });
        btnGenerate.setBounds(12, 465, 123, 25);
        panelOutput.add(btnGenerate);

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(ae ->
        {
            textAreaOutput.setText("");
            setClipboardContent("");
        });
        btnClear.setBounds(142, 465, 123, 25);
        panelOutput.add(btnClear);
    }

    private void setClipboardContent(String script)
    {
        StringSelection selection = new StringSelection(script);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    private String buildScript()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("#!/bin/bash\n\n");

        sb.append("# Java Virtual Machine settings\n");
        sb.append("MAX_HEAP_SIZE=" + sliderHeapSize.getValue() + "m\n");
        sb.append("MAX_STACK_SIZE=" + sliderStackSize.getValue() + "m\n\n");

        sb.append("# E-mail settings\n");
        sb.append("SENDER='" + txtSender.getText() + "'\n");
        sb.append("RECIPIENT='" + txtRecipient.getText() + "'\n");
        sb.append("SERVER='" + txtServer.getText() + "'\n");
        sb.append("OPTIONS='" + txtOptions.getText() + "'\n");
        sb.append("USERNAME='" + txtUsername.getText() + "'\n");
        sb.append("PASSWORD='" + new String(fldPassword.getPassword()) + "'\n\n");

        sb.append("# Log settings\n");
        sb.append("LOG_LEVELS=(ERROR WARN INFO DEBUG TRACE)\n\n");

        sb.append("# Anti Spam settings\r\n");
        sb.append("JAR_PATH=\"" + txtJarPath.getText() + "\"\n");
        sb.append("METADATA=\"" + txtMetadata.getText() + "\"\n");
        sb.append("METHODS=(" + selectedMethods.stream().collect(Collectors.joining(" ")) + ")\n");
        sb.append("RUNS=" + txtRuns.getText() + "\n\n");

        sb.append("for METHOD in \"${METHODS[@]}\"; do\n\n");

        sb.append("    # run the experiments\n");
        sb.append("    VM_OPTIONS=\"-Xmx${MAX_HEAP_SIZE} -Xss${MAX_STACK_SIZE} -XX:+UseG1GC\"\n");
        sb.append("    RUN_COMMAND=\"java ${VM_OPTIONS} -jar \\\"${JAR_PATH}\\\" -Metadata ${METADATA} -Methods ${METHOD} -Runs ${RUNS}");
        if (chkSkipTrain.isSelected()) sb.append(" -SkipTrain");
        if (chkSkipTest.isSelected()) sb.append(" -SkipTest");
        if (chkShrinkFeatures.isSelected()) sb.append(" -ShrinkFeatures");
        if (chkBalanceClasses.isSelected()) sb.append(" -BalanceClasses");
        if (chkIncludeEmpty.isSelected()) sb.append(" -IncludeEmpty");
        if (chkRemoveOutliers.isSelected()) sb.append(" -RemoveOutliers");
        if (chkSaveArff.isSelected()) sb.append(" -SaveArff");
        if (chkSaveModel.isSelected()) sb.append(" -SaveModel");
        if (chkSaveSets.isSelected()) sb.append(" -SaveSets");
        sb.append("\"\n");
        sb.append("    LOG_FILENAME=\"" + txtJarPath.getText().substring(0, txtJarPath.getText().lastIndexOf(File.separator) + 1) + "${METHOD}.log\"\n");
        sb.append("    echo \"$(date) - Executing [${RUN_COMMAND}] >> [${LOG_FILENAME}] and sending results to [${RECIPIENT}]\"\n");
        sb.append("    eval ${RUN_COMMAND} >> ${LOG_FILENAME}\n");
        sb.append("    for LEVEL in \"${LOG_LEVELS[@]}\"; do\n");
        sb.append("        cat ${LOG_FILENAME} | grep ${LEVEL} >> $(echo ${LOG_FILENAME} | sed s/.log/_${LEVEL}.log/)\n");
        sb.append("    done\n\n");

        sb.append("    # zip the log files\n");
        sb.append("    LOG_FILENAME_ZIP=\"${LOG_FILENAME}.zip\"\n");
        sb.append("    ZIP_COMMAND=\"zip --junk-paths ${LOG_FILENAME_ZIP} $(dirname ${LOG_FILENAME})/${METHOD}*.log\"\n");
        sb.append("    eval ${ZIP_COMMAND}\n\n");

        sb.append("    # mail the zipped log file\n");
        sb.append("    MAIL_SUBJECT=\"[ASW] $(date) - $(basename ${LOG_FILENAME})\"\n");
        sb.append("    MAIL_BODY=\"$(du -h ${LOG_FILENAME} | cut -f1) $(file ${LOG_FILENAME})\"\n");
        sb.append("    MAIL_COMMAND=\"sendemail -f ${SENDER} -t ${RECIPIENT} -u ${MAIL_SUBJECT} -m ${MAIL_BODY} -a ${LOG_FILENAME_ZIP} -s ${SERVER} -o ${OPTIONS} -xu ${USERNAME} -xp ${PASSWORD}\"\n");
        sb.append("    eval ${MAIL_COMMAND}\n\n");

        sb.append("done\n");

        return sb.toString();
    }
}
