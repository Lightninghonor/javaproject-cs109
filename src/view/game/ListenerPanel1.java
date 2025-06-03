package view.game;

import model.Direction;

import java.awt.event.MouseEvent;

import javax.swing.*;
import java.awt.*;
public abstract class ListenerPanel1 extends ListenerPanel {
    public ListenerPanel1() {

        new Thread(() -> {
            enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        }).start();
//        new Thread(() -> {
//            enableEvents(AWTEvent.KEY_EVENT_MASK);
//        }).start();
        this.setFocusable(true);
    }
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_CLICKED) {
            doMouseClick(e.getPoint());
        }
    }
}
