package view.interfaces;

import javax.swing.JButton;

import view.EventName;

public interface IGuiWindow {
    JButton getButton(EventName eventName);
}
