package sk.stuba.fei.uim.oop.gui;

import sk.stuba.fei.uim.oop.playability.Logic;

import javax.swing.*;
import java.awt.*;

public class Pipes {

    public static final String RESET_BUTTON_NAME= "RESET";
    public static final String CHECK_BUTTON_NAME= "CHECK ROUTE";
    public Pipes() {
        JFrame frame = new JFrame("Pipes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(725, 720);
        frame.getContentPane().setBackground(new Color(150, 150, 150));
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        Logic logic = new Logic(frame);
        frame.addKeyListener(logic);


        JPanel menu = new JPanel();
        menu.setBackground(new Color(147, 112, 219));

        JButton resetButton = new JButton(RESET_BUTTON_NAME);
        resetButton.setPreferredSize(new Dimension(100, 40));
        resetButton.addActionListener(logic);
        resetButton.setFocusable(false);

        JButton routeButton = new JButton(CHECK_BUTTON_NAME);
        routeButton.setPreferredSize(new Dimension(140, 40));
        routeButton.addActionListener(logic);
        routeButton.setFocusable(false);


        JSlider slider = new JSlider(JSlider.HORIZONTAL, 8, 12, 8);
        slider.setPreferredSize(new Dimension(330, 40));
        slider.setMinorTickSpacing(1);
        slider.setMajorTickSpacing(1);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(logic);

        menu.setLayout(new GridBagLayout());
        menu.add(routeButton);
        menu.add(Box.createHorizontalStrut(5));
        menu.add(resetButton);
        menu.add(Box.createHorizontalStrut(5));
        menu.add(slider);
        menu.add(Box.createHorizontalStrut(5));
        menu.add(logic.getLevelLabel());
        menu.add(Box.createHorizontalStrut(5));
        menu.add(logic.getSizeLabel());


        frame.add(menu, BorderLayout.SOUTH);

        frame.setVisible(true);

    }
}
