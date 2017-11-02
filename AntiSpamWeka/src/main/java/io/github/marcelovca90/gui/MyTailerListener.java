package io.github.marcelovca90.gui;

import javax.swing.JTextArea;

import org.apache.commons.io.input.TailerListenerAdapter;

public class MyTailerListener extends TailerListenerAdapter
{
    private JTextArea textAreaOutput;

    public MyTailerListener(JTextArea textAreaOutput)
    {
        this.textAreaOutput = textAreaOutput;
    }

    @Override
    public void handle(String line)
    {
        textAreaOutput.append(line + "\n");
    }
}
