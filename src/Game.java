import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Game extends JFrame implements MouseListener, MouseMotionListener, ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    //variables
    Timer t = new Timer(40, this);//наш таймер
    JButton runer = new JButton("catch me");//наша кнопка
    int bx = 100, by = 100, currTargetX = bx, currTargetY = by;//переменные координаты кнопки и текущей цели кнопки
    int mouseX = 0, mouseY = 0;// координаты мышки
    int buttW = 300, buttH = 300;// размеры кнопки
    int buttonSpeedX = 10, buttonSpeedY = 10;// прирост координат кнопки
    int screenX = 700, screenY = 500;// стартовые размеры экрана
    boolean nearMouse = false;//рядом ли мышка
    Random r = new Random();//наш рандом

    public Game(JFrame f) {
        super("catch the button!");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//не закроется вся программа
        addWindowListener(new WindowListener() {//это вот все ради закрытие не всей программы, а тоько игровой формы. можно убрать
            @Override
            public void windowIconified(WindowEvent e) {
                // заглушка
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                // заглушка
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                // заглушка
            }

            @Override
            public void windowOpened(WindowEvent windowEvent) {
                // заглушка
            }

            @Override
            public void windowClosing(WindowEvent e) {// вернет видимым меню, а закроет только игровой фрейм
                f.setVisible(true);
                Game.this.dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                // заглушка
            }

            @Override
            public void windowActivated(WindowEvent e) {
                // заглушка
            }
        });
        setSize(700, 500);
        setLocation(300, 100);//стартовая настройка фрейма
        setLayout(null);

        runer.setBounds(0, 0, buttW, buttH);//настройка кнопки
        runer.setLocation(bx, by);

        runer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {//действия при нажатии на кнопку
                f.setVisible(true);
                Game.this.dispose();//уход в главное меню

            }

        });
        add(runer);//добавление кнопки
        setVisible(true);

        t.start();//запуск таймера

        addMouseListener(this);//добавление слушателей мыши
        addMouseMotionListener(this);
    }

    public boolean mouseNearMe() {//функция для проверки рядом ли мышь. коэфф можно менять, увеличивая или уменьшая расстояние
        if (mouseX >= bx - buttonSpeedX * 2 && mouseX <= bx + buttW + buttonSpeedX * 2 && mouseY >= by - buttonSpeedY * 1 && mouseY <= by + buttH + buttonSpeedY * 4) {
            return true;
        }
        return false;
    }

    public void buttonRuner() //функция движения кнопки
    {
        if (!mouseNearMe()) {//если кнопка не рядом с нами
            //если мы достигли условной цели
            if (bx >= currTargetX - buttonSpeedX && bx <= currTargetX + buttonSpeedX && by >= currTargetY - buttonSpeedX && by <= currTargetY + buttonSpeedX) {
                currTargetX = r.nextInt(screenX - buttW);
                currTargetY = r.nextInt(screenY - buttH);// генерируем новую
            } else {//если не достигли
                if (bx < currTargetX) bx += buttonSpeedX;
                if (bx > currTargetX) bx -= buttonSpeedX;//движемся к ней, приращивая координаты
                if (by < currTargetY) by += buttonSpeedY;
                if (by > currTargetY) by -= buttonSpeedY;
            }
        } else {//если мышь все-таки рядом
            currTargetX = r.nextInt(screenX - buttW);
            currTargetY = r.nextInt(screenY - buttH);//генерируем новые координаты движения
            double rasst = Math.sqrt(Math.pow(mouseX - bx, 2) + (Math.pow(mouseY - by, 2)));//рассчитываем гипотенузу по формуле прямой
            double xd = (bx - mouseX) / (rasst / buttonSpeedX);//формула прямой, прирост по х
            double yd = (by - mouseY) / (rasst / buttonSpeedY);//формула прямой, прирост по у
            if (bx + (-xd) > 0 && bx + (-xd) < screenX - buttW) {//если не выходим за границы по х
                if (mouseX <= bx) bx += xd * 5;//двигаемся по прямой от мыши
                if (mouseX >= bx + buttW && bx + yd * 5 > 0) bx += yd * 5;
            } else {
                if (bx + xd * 5 < screenX - buttW && bx + xd * 5 > 0)
                    bx += xd * 5;//отскок, коэфф регулирует силу отскока
            }
            if (by + yd > 0 && by + yd < screenY - buttH - 35) {//если не выходим за границы по у
                if (mouseY <= by) by += yd * 5;//двигаемся по прямой от мыши
                if (mouseY >= by + buttH) by += xd * 5;
            } else {
                if (by + yd * 5 > 0 && by + yd * 5 < screenY - buttH - 35) by += yd * 5;//отскок
            }
            if (mouseX > bx && mouseX < bx + buttW && mouseY > by && mouseY < by + buttH) {
                int hy = r.nextInt(50) - 25;
                int hx = r.nextInt(50) - 25;//если курсор на кнопке она отпрыгивает в случайное место
                if (bx + hx > 0 && bx + hx < screenX - buttW) bx += hx;
                if (by + hy > 0 && by + hy < screenY - buttH) by += hy;
            }
        }
        runer.setLocation(bx, by);//перезаписываем координаты
    }

    @Override
    public void mouseDragged(MouseEvent e) {//ненужные методы мыши, обязаны переопределять
        // заглушка
    }

    @Override
    public void mouseMoved(MouseEvent e) {//записываем положение мыши
        mouseX = e.getX();
        mouseY = e.getY();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // заглушка
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // заглушка
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // заглушка
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // заглушка
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // заглушка
    }//ненужные методы

    @Override
    public void actionPerformed(ActionEvent e) {//метод таймера
        buttonRuner();//вызов основной функции движения
        screenX = getSize().width;
        screenY = getSize().height;//получаем размер экрана
    }
}
