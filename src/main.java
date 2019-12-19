import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main extends JFrame {
    static JFrame f;
    static JLabel logo;
    static JButton startB;
    static Font logoFont;

    public static void main(String[] args) {
        f = new JFrame("Running button");
        startB = new JButton("Start");
        logo = new JLabel("Running Button");//инициализация элементов меню
        logoFont = new Font("Arial", 20, 50); // настройка шрифтов
        startB.setForeground(Color.yellow); // цвет шрифта
        startB.setBackground(Color.white); // цвет шрифта
        startB.setFont(logoFont);//настройка элементов меню
        logo.setForeground(Color.blue); // цвет шрифта
        logo.setFont(logoFont); // шрифт

        startB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //событие на кнопку старт
                f.setVisible(false);
                Game g_c = new Game(f);
            }
        });

        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        f.setSize(500, 200);
        f.setLocation(300, 100); //настройка фрейма
        f.setLayout(new FlowLayout(FlowLayout.CENTER));
        f.add(logo);
        f.add(startB); //добавляем компоненты
        f.setVisible(true);
    }
}