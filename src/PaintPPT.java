
import java.awt.Rectangle;
import java.awt.font.TextLayout;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.awt.Color;
import javax.swing.*;

/**Klasa z przyciskami wyboru figur oraz okna edycji na ramce glownej*/
class MyButton extends JButton implements ActionListener
{
    /**Zmienna przechowujaca nazwe figury*/
    private String _name;
    /**Zmienna przechowujaca glowna ramke*/
    private MyFrame _frame;

    /**Konstruktor przycisku znajdujacego sie na glownej ramce*/
    MyButton(String name, MyFrame frame)
    {
        super(name);
        this._name = name;
        this._frame = frame;
        setSize(100,50);
        addActionListener(this);
    }

    @Override
    /**Funkcja sprawdzajaca, czy zostal wcisniety jakis przycisk*/
    public void actionPerformed(ActionEvent e)
    {
        switch (_name){
            case "Prostokat":
                _frame.getMyPanel().DrawRectangle();
                break;
            case "Kolo":
                _frame.getMyPanel().DrawCircle();
                break;
            case "Wielokat":
                _frame.getMyPanel().DrawPolygon();
                break;
        }
    }
}

/**Klasa przycisku do edycji figur*/
class editFigBtn extends JButton implements ActionListener{

    /**Zmienna przechowujaca nazwe przycisku do edytowania*/
    private String _name;
    /**Zmienna przechowujaca glowna ramke*/
    private MyFrame _Frame;

    /**Konstruktor klasy przycisku do edycji figur*/
    editFigBtn(String name,MyFrame frame){
        super(name);
        this._name = name;
        this._Frame = frame;
        setSize(100,50);
        addActionListener(this);
    }

    /**Funckja sprawdzajaca, czy  przycisku do edycji figur zostal wcisniety*/
    public void actionPerformed(ActionEvent e)
    {
        /**Warunek zapobiegajacy wlaczaniu kilku okienek edycji*/
        if(_Frame.editFramesNumber == 1){
        }
        else
        {
            _Frame.panel.editor.isInEditMode = true;
            toolFrame editMenu = new toolFrame(_Frame);
            editMenu.setVisible(true);
            _Frame.editFramesNumber ++;
        }
    }
}

/**Glowna klasa figur, po ktorej dziedzicza inne wielokaty*/
class Figura implements Serializable{

    /**Zmienna przechowujaca nazwe figury*/
    private String name;
    /**Zmienna przechowujaca aktualny kolor figury*/
    private Color figCurrentColor;
    /**Zmienna przechowujaca status ruchu figury*/
    private Boolean isBeingMoved = false;
    /**Zmienna przechowujaca punkt wewnatrz figury podczas jej poruszania*/
    private Point pickedPoint;

    /**Konstruktor klasy glownej dla figur*/
    Figura(String name)
    {
        this.name = name;
        this.figCurrentColor = Color.BLACK;
    }

    /**Getter zwracajacy nazwe danej figury*/
    public String getFigureName(){ return name; }

    /**Getter zwracajacy aktualny kolor danej figury*/
    public Color getFigCurrentColor(){ return this.figCurrentColor; }

    /**Setter zmieniajacy aktualny kolor danej figury*/
    public void setFigureCurrentColor(Color color){ this.figCurrentColor = color; }

    /**Funkcja wypisujaca informacje o pozycji danej figury*/
    public void showFigureInformation(Graphics g,int x, int y){
        g.drawString("*****",x + 18 ,y + 10);
        String _x = String.valueOf(x);
        String _y = String.valueOf(y);
        g.drawString(_x,x + 20,y + 17);
        g.drawString(_y,x + 20,y + 30);
        g.drawString("*****",x + 18 ,y + 42);
    }
}

/**Klasa prostokata dziedziczacego po klase Figura*/
class Rectangl extends Figura
{
    /**Zmienne przechowujace wierzcholki prostokata*/
    private int _x,_x2,_y,_y2;

    /**Konstruktor klasy prostokata*/
    Rectangl(int x, int x2, int y, int y2, String name)
    {
        super(name);
        this._x = x;
        this._x2 = x2;
        this._y = y;
        this._y2 = y2;
    }

    public int getX(){ return _x; }
    public int getX2(){ return _x2; }
    public int getY(){ return _y; }
    public int getY2(){ return _y2; }

    public void setX(int x){ this._x += x; }
    public void setY(int y){ this._y += y; }
    public void setX2(int x2){ this._x2 += x2; }
    public void setY2(int y2){ this._y2 += y2; }
}
/**Klasa okregu dziedziczacego po klase Figura*/
class Oval extends Figura
{
    /**Zmienne przechowujace dane o okregu*/
    private int _x,_x2,_y,_y2;

    /**Konstruktor klasy okregu*/
    Oval(int x, int x2, int y, int y2, String name)
    {
        super(name);
        this._x = x;
        this._x2 = x2;
        this._y = y;
        this._y2 = y2;
    }

    public int getX(){ return _x; }
    public int getX2(){ return _x2; }
    public int getY(){ return _y; }
    public int getY2(){ return _y2; }

    public void setX(int x){ this._x += x; }
    public void setY(int y){ this._y += y; }
    public void setX2(int x2){ this._x2 += x2; }
    public void setY2(int y2){ this._y2 += y2; }
}
/**Klasa polygonu dziedziczacego po klase Figura*/
class Polygon extends Figura
{
    /**Lista punktow danego polygonu*/
    private List<Object> _polygonPointsList;
    /**Tablica wspolrzednych 'x' z punktow danego polygonu*/
    private int[] _xPoints;
    /**Tablica wspolrzednych 'y' z punktow danego polygonu*/
    private int[] _yPoints;

    /**Konstruktor klasy polygonu*/
    Polygon(List<Object> polygonPointsList,String name){
        super(name);
        _polygonPointsList = polygonPointsList;
        _xPoints = new int[_polygonPointsList.size()];
        _yPoints = new int[_polygonPointsList.size()];
        for(int i = 0 ; i < _polygonPointsList.size() - 1 ; i ++){
            _xPoints[i] = (((polygonPoints)_polygonPointsList.get(i)).x);
            _yPoints[i] = (((polygonPoints)_polygonPointsList.get(i)).y);
        }
    }

    public int[] get_xPoints(){ return _xPoints;}

    /**Setter konkretnego punktu w polygonie*/
    public void set_Point(int index, int x, int y){
        _xPoints[index] += x;
        _yPoints[index] += y;
    }

    /**Setter konkretnego punktu w polygonie podczas zmiany rozmiaru*/
    public void set_XPointSize(int index, int x){
        _xPoints[index] = x;
    }

    public void set_YPointSize(int index, int y){
        _yPoints[index] = y;
    }


    public int[] get_yPoints(){ return _yPoints; }

    public List<Object> getPolygonPointsList(){ return _polygonPointsList; }
}

/**Klasa punktu polygonu*/
class polygonPoints implements Serializable{

    /**Zmiennie przechowujace wspolrzedne danego punktu przyszlego polygonu*/
    public int x,y;

    /**Konstruktor klasy dla punktu polygonu*/
    polygonPoints(int x, int y){
        this.x = x;
        this.y = y;
    }
}

/**Klasa definiujaca panel do rysowania*/
class MyPanel extends JPanel
{
    /**Zmienne przechowujace poczatkowe i koncowe polozenie myszki*/
    public int x,y,x2,y2;

    /**Zmienne przechowujace poczatkowe polozenie myszki podczas przesuwania*/
    public int originX = 0, originY = 0;

    /**Zmiennia przechowujaca kierunek obrotu scroll'a*/
    public int resizeAmount = 0;

    /**Zmienna przechowujaca status mozliwosci rysowania*/
    public Boolean isAbleToDraw = false;

    /**Zmienna przechowujaca status rysowania*/
    public Boolean finishedDrawing = false;

    /**Zmienna przechowujaca status rysowania polygonu*/
    public Boolean finishedDrawingPolygon = false;

    /**Zmienna przechowujaca edytor figur*/
    public figureEditor editor = new figureEditor(this);

    /**Zmienna przechowujaca status zmiany wielkosci figur*/
    public Boolean isResizing = false;

    /**Lista przechowujaca punkty rysowanego polygonu*/
    public List<Object> currentPolygonPointsList = new ArrayList<Object>();

    /**Zmienna przechowujaca informacje, ktora figura zostala wybrana*/
    public Figures currentFigure = Figures.CIRCLE;

    /**Lista narysowanych figur*/
    private List <Object> figureList = new ArrayList <Object>();

    public enum Figures {
        CIRCLE, RECTANGLE, POLYGON;
    }

    public List <Object> getFigureList(){ return this.figureList; }


    public String getFigureName(int i){ return ((Figura)figureList.get(i)).getFigureName(); }

    public void DrawRectangle()
    {
        currentFigure = Figures.RECTANGLE;
        isAbleToDraw = true;
    }

    public void DrawCircle()
    {
        currentFigure = Figures.CIRCLE;
        isAbleToDraw = true;
    }

    public void DrawPolygon()
    {
        currentFigure = Figures.POLYGON;
        isAbleToDraw = true;
    }

    /**Konstruktor klasy panelu do rysowania*/
    public MyPanel()
    {
        setPreferredSize(new Dimension(600, 400));
        x=y=x2=y2=0;
        MyMouseListener listener = new MyMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        addMouseWheelListener(new MyMouseWheelListener());
    }

    public void setStartPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setEndPoint(int x, int y) {
        x2 = x;
        y2 = y;
    }
    /**Klasa wykrywajaca ruch scroll'a*/
    class MyMouseWheelListener implements MouseWheelListener{
        @Override
        public void mouseWheelMoved(MouseWheelEvent e){
            isResizing = true;
            resizeAmount = e.getWheelRotation();
            System.out.println("Rotation : " + resizeAmount);
            repaint();
        }

    }

    /**Klasa wykrywajaca ruch i uzywanie przyciskow myszy*/
    class MyMouseListener extends MouseAdapter{

        public void mousePressed(MouseEvent e) {
            if(editor.isInEditMode == true){
                setStartPoint(e.getX(), e.getY());
                if(originX == 0 && originY == 0 && editor.getColourMode() == false && editor.getMovingMode() == true){
                    originX = x;
                    originY = y;
                }
                else
                    repaint();
            }
            else{
                /** Warunek sprawdzajacy, czy pierwszy polygon zostal juz narysowany*/
                if(currentFigure == Figures.POLYGON && finishedDrawingPolygon == true && currentPolygonPointsList.size() > 0){
                    currentPolygonPointsList = new ArrayList<Object>();
                    finishedDrawingPolygon = false;
                }
                if(currentPolygonPointsList.size() == 0 && currentFigure == Figures.POLYGON){
                    x=y=x2=y2=0;
                    setStartPoint(e.getX(), e.getY());
                    addPolygonPoint(x,y);
                }
                else if(finishedDrawingPolygon == false && currentFigure == Figures.POLYGON ){
                    setStartPoint(e.getX(), e.getY());
                    addPolygonPoint(x,y);
                }
                else
                {
                    finishedDrawing = false;
                    setStartPoint(e.getX(), e.getY());
                }
            }
        }

        public void mouseDragged(MouseEvent e) {
            if(editor.isInEditMode == true){
                if(editor.getMovingMode() == true){
                    setEndPoint(e.getX(), e.getY());
                    repaint();
                }
            }
            else {
                if (finishedDrawingPolygon == false && currentFigure == Figures.POLYGON) {
                } else {
                    setEndPoint(e.getX(), e.getY());
                    finishedDrawing = false;
                    repaint();
                }
            }
        }

        public void mouseReleased(MouseEvent e) {
            if(editor.isInEditMode == true){
                /**Warunek konczacy przesuwanie danej figury*/
                if(editor.getMovingMode() == true){
                    originX = 0;
                    originY = 0;
                    editor.moveY = 0;
                    editor.moveX = 0;
                    editor.setIdOfEditedFigure(-1);
                    editor.sthIsMoving = false;
                    repaint();
                }
            }
            else {
                if (finishedDrawingPolygon == false && currentFigure == Figures.POLYGON) {
                    repaint();
                } else {
                    setEndPoint(e.getX(), e.getY());
                    finishedDrawing = true;
                }

                if (isAbleToDraw) {
                    repaint();
                }
                /** Warunek zakonczenia rysowania polygonu*/
                if (currentFigure == Figures.POLYGON && ((Math.abs((((polygonPoints) currentPolygonPointsList.get(currentPolygonPointsList.size() - 1)).x) - ((polygonPoints) currentPolygonPointsList.get(0)).x))) < 10 && ((Math.abs((((polygonPoints) currentPolygonPointsList.get(currentPolygonPointsList.size() - 1)).y) - ((polygonPoints) currentPolygonPointsList.get(0)).y))) < 10  &&  currentPolygonPointsList.size() >= 2) {
                    currentPolygonPointsList.remove(currentPolygonPointsList.size() - 1);
                    addPolygonPoint((((polygonPoints) currentPolygonPointsList.get(0)).x), ((polygonPoints) currentPolygonPointsList.get(0)).y);
                    finishedDrawingPolygon = true;
                    addPolygonToList();
                    repaint();
                }
            }
        }
    }

    /**Funkcja rysujaca prostokat o danych parametrach*/
    public void drawRectangle(Graphics g, int x, int y, int x2, int y2, Boolean newFigure) {
        int px = Math.min(x,x2);
        int py = Math.min(y,y2);
        int pw = Math.abs(x-x2);
        int ph = Math.abs(y-y2);
        g.drawRect(px, py, pw, ph);
        if(finishedDrawing && newFigure){
            addFigureToList(x,y,x2,y2);
        }
    }

    /**Funkcja rysujaca okag o danych parametrach*/
    public void drawCircle(Graphics g, int x, int y, int x2, int y2, Boolean newFigure) {
        int px = Math.min(x,x2);
        int py = Math.min(y,y2);
        int pw = Math.abs(x-x2);
        int ph = Math.abs(y-y2);
        g.drawOval(px, py, pw, ph);
        if(finishedDrawing && newFigure){
            addFigureToList(x,y,x2,y2);
        }
    }

    /**Dodanie nowego polygonu do listy figur*/
    public void addPolygonToList(){
        Polygon pol = new Polygon(currentPolygonPointsList, "wielokat");
        figureList.add(pol);
    }

    /**Funkcja dodajaca punkt do listy wierzcholkow podczas rysowania danego polygonu*/
    public void addPolygonPoint(int x, int y){
        polygonPoints point = new polygonPoints(x,y);
        currentPolygonPointsList.add(point);
    }

    /**Funkcja dodajaca figure do listy figur*/
    public void addFigureToList( int x, int y, int x2, int y2){
        switch(currentFigure){
            case RECTANGLE:
                Rectangl rec = new Rectangl(x,x2,y,y2,"prostokat");
                figureList.add(rec);
                break;
            case CIRCLE:
                Oval elps = new Oval(x,x2,y,y2,"kolo");
                figureList.add(elps);
                break;
        }
    }

    /**Funckja rysujaca wszystkie dotychczas narysowane figury*/
    public void drawAll(Graphics g){
        for(int i = 0; i < figureList.size() ; i++){
            switch(((Figura)figureList.get(i)).getFigureName()){
                case "prostokat":
                    Rectangl rec = (Rectangl)figureList.get(i);
                    int px = Math.min(rec.getX(),rec.getX2());
                    int py = Math.min(rec.getY(),rec.getY2());
                    int pw = Math.abs(rec.getX()-rec.getX2());
                    int ph = Math.abs(rec.getY()-rec.getY2());
                    if(rec.getFigCurrentColor() != Color.BLACK){
                        g.setColor(rec.getFigCurrentColor());
                        g.fillRect(px,py,pw,ph);
                        if(editor.getIdOfEditedFigure() != - 1 && editor.getMovingMode() == true && editor.sthIsMoving == true){
                            g.setColor(Color.BLACK);
                            rec.showFigureInformation(g,x2,y2);
                        }
                    }
                    else{
                        g.setColor(rec.getFigCurrentColor());
                        drawRectangle(g, rec.getX(), rec.getY(), rec.getX2(), rec.getY2(), false);
                        if(editor.getIdOfEditedFigure() != - 1 && editor.getMovingMode() == true && editor.sthIsMoving == true){
                            g.setColor(Color.BLACK);
                            rec.showFigureInformation(g,x2,y2);
                        }
                    }
                    break;
                case "kolo":
                    Oval elps = (Oval)figureList.get(i);
                    px = Math.min(elps.getX(),elps.getX2());
                    py = Math.min(elps.getY(),elps.getY2());
                    pw = Math.abs(elps.getX()-elps.getX2());
                    ph = Math.abs(elps.getY()-elps.getY2());

                    if(elps.getFigCurrentColor() != Color.BLACK){
                        g.setColor(elps.getFigCurrentColor());
                        g.fillOval(px,py,pw,ph);
                        if(editor.getIdOfEditedFigure() != - 1 && editor.getMovingMode() == true && editor.sthIsMoving == true){
                            elps.showFigureInformation(g,x2,y2);
                        }
                    }
                    else{
                        g.setColor(elps.getFigCurrentColor());
                        drawCircle(g,elps.getX(),elps.getY(),elps.getX2(), elps.getY2(), false);
                        if(editor.getIdOfEditedFigure() != - 1 && editor.getMovingMode() == true && editor.sthIsMoving == true){
                            elps.showFigureInformation(g,x2,y2);
                        }
                    }
                    break;
                case "wielokat":
                    Polygon pol = (Polygon)figureList.get(i);
                    g.setColor(pol.getFigCurrentColor());
                    g.fillPolygon(pol.get_xPoints(),pol.get_yPoints(),pol.getPolygonPointsList().size() - 1);
                    break;
            }
        }
    }
    /**Funkcja rysujaca nowa figure*/
    public void drawCurrentFigureBeingPainted(Graphics g){
        switch (currentFigure) {
            case RECTANGLE:
                drawRectangle(g, x, y, x2, y2, true);
                break;
            case CIRCLE:
                drawCircle(g, x, y, x2, y2, true);
                break;
            case POLYGON:
                for(int  i = 1; i < currentPolygonPointsList.size(); i++){
                    g.drawLine((((polygonPoints)currentPolygonPointsList.get(i - 1)).x),(((polygonPoints)currentPolygonPointsList.get(i - 1)).y),(((polygonPoints)currentPolygonPointsList.get(i)).x),(((polygonPoints)currentPolygonPointsList.get(i)).y));
                }
                break;
        }
    }

    @Override
    /**Funkcja rysujaca na ekranie*/
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAll(g);
        if(editor.isInEditMode == false){
            drawCurrentFigureBeingPainted(g);
        }
        else if(editor.isInEditMode == true){
            editor.chooseFigureToEdit();
            if(editor.getColourMode() == true){
            }
            else if(editor.getMovingMode() == true && editor.getIdOfEditedFigure() != -1){
                editor.MoveFigure(editor.getIdOfEditedFigure());
            }
            if(isResizing == true && editor.getIdOfEditedFigure() != -1 ){
                if(resizeAmount == 1){
                    editor.resizeFigure(editor.getIdOfEditedFigure(),3);
                    isResizing = false;
                }
                else if (resizeAmount == - 1 && editor.getIdOfEditedFigure() != -1){
                    editor.resizeFigure(editor.getIdOfEditedFigure(), - 3);
                    isResizing = false;
                }
            }
        }
    }
}

/**Klasa odpowiadajaca za dzialanie edytowania figur*/
class figureEditor{

    /**Zmienna przechowujaca status edycji*/
    public Boolean isInEditMode = false;
    /**Zmienne przechowujace zmiane polozenia punktow figury*/
    public int moveX = 0, moveY = 0;


    private MyPanel _panel;

    /**Zmienna przechowujaca indeks edytowanej figury*/
    private int editedFigureID = -1;

    /**Zmienna przechowujaca status mowiacy, czy uzytkownik wybral jakas figure*/
    private Boolean _foundFigure = false;

    /**Zmienna przechowujaca statuc mowiacy, czy uzytkownik koloruje figure*/
    private Boolean colourMode = false;

    /**Zmienna przechowujaca statuc mowiacy, czy uzytkownik bedzie przesuwal figury*/
    private Boolean movingMode = false;

    /**Zmienna przechowujaca statuc mowiacy, czy uzytkownik przesuwa figure*/
    static Boolean sthIsMoving = false;

    int max_X = 0;
    int max_Y = 0;
    int min_X = 2000;
    int min_Y = 0;
    int x_Det = 0;
    int y_Det = 0;
    int averageX = 0;
    int averageY = 0;
    int x_mid = 0;
    int y_mid = 0;
    int width_startX = 0;
    int width_startY = 0;
    int width_currentX = 0;
    int width_currentY = 0;

    public int getIdOfEditedFigure(){ return this.editedFigureID; }

    public void setIdOfEditedFigure(int id){ this.editedFigureID = id; }

    /**Konstruktor klasy edytora figur*/
    figureEditor(MyPanel panel){ this._panel = panel; }

    public Boolean getColourMode(){ return this.colourMode; }

    public void setColourMode(Boolean b){ this.colourMode = b; }

    public Boolean getMovingMode(){ return this.movingMode; }

    public void setMovingMode(Boolean b){ this.movingMode = b; }


    /**Funkcja sprawdzajaca, czy dany punkt znajduje sie w danym prostokacie*/
    public Boolean isInRectangle(Rectangl rec, int x, int y){
        int px = Math.min(rec.getX(), rec.getX2());
        int py = Math.min(rec.getY(), rec.getY2());
        int pw = Math.abs(rec.getX() - rec.getX2());
        int ph = Math.abs(rec.getY() - rec.getY2());
        Point p = new Point(x,y);
        Rectangle rec2 = new Rectangle(px, py, pw, ph);
        if (rec2.contains(p)) {
            return true;
        }
        else
            return false;
    }
    /**Funkcja sprawdzajaca, czy dany punkt znajduje sie w danej elipsie*/
    public Boolean isInEllipse(Oval circle, int x, int y){
        Point p = new Point(x,y);
        int _mX = (circle.getX() + circle.getX2())/2;
        int _mY = (circle.getY() + circle.getY2())/2;
        int _omegaX = (Math.abs(-circle.getX() + circle.getX2()))/2;
        int _omegaY = (Math.abs(-circle.getY() + circle.getY2()))/2;

        if (((Math.pow((p.x - _mX),2)/Math.pow(_omegaX,2)) + (((Math.pow((p.y - _mY),2))/(Math.pow(_omegaY,2)))) <= 1))
        {
            return true;
        }
        else
            return false;
    }
    /**Funkcja sprawdzajaca, czy jakas figura zostala wybrana przez uzytkownika*/
    public void chooseFigureToEdit(){

        Point p = new Point(_panel.x, _panel.y);

        for(int j =_panel.getFigureList().size() - 1;j >= 0; j--) {
            if (this._foundFigure == false && sthIsMoving == false) {
                switch (_panel.getFigureName(j)) {
                    case "prostokat":

                        Rectangl rec = (Rectangl)_panel.getFigureList().get(j);

                        int px = Math.min(rec.getX(), rec.getX2());
                        int py = Math.min(rec.getY(), rec.getY2());
                        int pw = Math.abs(rec.getX() - rec.getX2());
                        int ph = Math.abs(rec.getY() - rec.getY2());

                        Rectangle rec2 = new Rectangle(px, py, pw, ph);
                        if (rec2.contains(p)) {
                            editedFigureID = j;
                            this._foundFigure = true;
                        }
                        break;
                    case "kolo":

                        Oval circle = (Oval)_panel.getFigureList().get(j);

                        int _mX = (circle.getX() + circle.getX2())/2;
                        int _mY = (circle.getY() + circle.getY2())/2;
                        int _omegaX = (Math.abs(-circle.getX() + circle.getX2()))/2;
                        int _omegaY = (Math.abs(-circle.getY() + circle.getY2()))/2;

                        if (((Math.pow((p.x - _mX),2)/Math.pow(_omegaX,2)) + (((Math.pow((p.y - _mY),2))/(Math.pow(_omegaY,2)))) <= 1))
                        {
                            editedFigureID = j;
                            this._foundFigure = true;
                        }
                        break;
                    case "wielokat":

                        Polygon pol = (Polygon)_panel.getFigureList().get(j);
                        java.awt.Polygon pol2 = new java.awt.Polygon(pol.get_xPoints(),pol.get_yPoints(),pol.getPolygonPointsList().size() - 1);

                        if(pol2.contains(p.x, p.y)){
                            editedFigureID = j;
                            this._foundFigure = true;
                        }
                        break;
                }
            }
        }
        this._foundFigure = false;
    }

    /**Funkcja zmieniajaca rozmiar danej figury*/
    public void resizeFigure(int editedFigureID, int x){
        switch(_panel.getFigureName(editedFigureID)) {
            case "prostokat":
                ((Rectangl) _panel.getFigureList().get(editedFigureID)).setX(-x);
                ((Rectangl) _panel.getFigureList().get(editedFigureID)).setX2(x);
                ((Rectangl) _panel.getFigureList().get(editedFigureID)).setY(-x);
                ((Rectangl) _panel.getFigureList().get(editedFigureID)).setY2(x);
                break;
            case "kolo":
                ((Oval) _panel.getFigureList().get(editedFigureID)).setX(-x);
                ((Oval) _panel.getFigureList().get(editedFigureID)).setX2(x);
                ((Oval) _panel.getFigureList().get(editedFigureID)).setY(-x);
                ((Oval) _panel.getFigureList().get(editedFigureID)).setY2(x);
                break;
            case "wielokat":

                Polygon pol = ((Polygon) _panel.getFigureList().get(editedFigureID));
                int _xMin = 2000, _yMin = 2000, xMax = 0, yMax = 0, h,w;
                for(int i = 0; i < pol.get_xPoints().length; i++){
                    _xMin = Math.min(_xMin, pol.get_xPoints()[i]);
                    _yMin = Math.min(_yMin, pol.get_yPoints()[i]);
                    xMax = Math.max(xMax,pol.get_xPoints()[i]);
                    yMax = Math.max(yMax,pol.get_yPoints()[i]);
                }

                h = yMax - _yMin;
                w = xMax - _xMin;

                for(int i = 0; i < pol.get_xPoints().length - 1; i++){
                    ((Polygon) _panel.getFigureList().get(editedFigureID)).get_xPoints()[i] = _xMin + ((((Polygon) _panel.getFigureList().get(editedFigureID)).get_xPoints()[i] - _xMin)*(w + x))/w;
                    ((Polygon) _panel.getFigureList().get(editedFigureID)).get_yPoints()[i] = _yMin + ((((Polygon) _panel.getFigureList().get(editedFigureID)).get_yPoints()[i] - _yMin)*(h + x))/h;
                }
            break;
        }
    }

    /** Funckja przesuwajaca dana figure*/
    public void MoveFigure(int editedFigureID){

        switch(_panel.getFigureName(editedFigureID)){
            case "prostokat":
                if( isInRectangle(((Rectangl)_panel.getFigureList().get(editedFigureID)),_panel.originX + moveX,_panel.originY + moveY)){
                    sthIsMoving = true;
                    ((Rectangl)_panel.getFigureList().get(editedFigureID)).setX(-moveX);
                    ((Rectangl)_panel.getFigureList().get(editedFigureID)).setX2(-moveX);
                    ((Rectangl)_panel.getFigureList().get(editedFigureID)).setY(-moveY);
                    ((Rectangl)_panel.getFigureList().get(editedFigureID)).setY2(-moveY);

                    moveX = _panel.x2 - _panel.originX;
                    moveY = _panel.y2 - _panel.originY;

                    ((Rectangl)_panel.getFigureList().get(editedFigureID)).setX(moveX);
                    ((Rectangl)_panel.getFigureList().get(editedFigureID)).setX2(moveX);
                    ((Rectangl)_panel.getFigureList().get(editedFigureID)).setY(moveY);
                    ((Rectangl)_panel.getFigureList().get(editedFigureID)).setY2(moveY);

                }
                break;

            case "kolo":
                if(isInEllipse(((Oval)_panel.getFigureList().get(editedFigureID)),_panel.originX + moveX,_panel.originY + moveY)){
                    sthIsMoving = true;
                    ((Oval)_panel.getFigureList().get(editedFigureID)).setX(-moveX);
                    ((Oval)_panel.getFigureList().get(editedFigureID)).setX2(-moveX);
                    ((Oval)_panel.getFigureList().get(editedFigureID)).setY(-moveY);
                    ((Oval)_panel.getFigureList().get(editedFigureID)).setY2(-moveY);

                    moveX = _panel.x2 - _panel.originX;
                    moveY = _panel.y2 - _panel.originY;

                    ((Oval)_panel.getFigureList().get(editedFigureID)).setX(moveX);
                    ((Oval)_panel.getFigureList().get(editedFigureID)).setX2(moveX);
                    ((Oval)_panel.getFigureList().get(editedFigureID)).setY(moveY);
                    ((Oval)_panel.getFigureList().get(editedFigureID)).setY2(moveY);
                }
                break;
            case "wielokat":
                Polygon pol = (Polygon)_panel.getFigureList().get(editedFigureID);
                java.awt.Polygon pol2 = new java.awt.Polygon(pol.get_xPoints(),pol.get_yPoints(),pol.getPolygonPointsList().size() - 1);

                if(pol2.contains(_panel.originX + moveX, _panel.originY + moveY)){
                    sthIsMoving = true;
                    for(int i = 0; i <= pol.getPolygonPointsList().size() - 1; i++) {
                        ((Polygon)_panel.getFigureList().get(editedFigureID)).set_Point(i, -moveX, -moveY);
                    }

                    moveX = _panel.x2 - _panel.originX;
                    moveY = _panel.y2 - _panel.originY;

                    for(int i = 0; i <= pol.getPolygonPointsList().size() - 1; i++){
                        ((Polygon)_panel.getFigureList().get(editedFigureID)).set_Point(i,moveX,moveY);
                    }
                }
                break;
        }
    }
}

/**Klasa glownej ramki naszego programu*/
class MyFrame extends JFrame
{
    MyPanel panel;

    /**Zmienna zapewniajaca maksymalna ilosc okienek edycji*/
    public int maxEditFrames = 1;
    /**Zmienna przechowujaca ilosc okienek edycji*/
    public int editFramesNumber;

    /** Konstruktor glownej ramki naszego programu*/
    MyFrame()
    {
        setSize(1800,1080);
        setTitle("PaintPPT");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        MyButton prostokat = new MyButton("Prostokat",this);
        MyButton wielokat = new MyButton("Wielokat",this);
        MyButton kolo = new MyButton("Kolo",this);
        editFigBtn editBtn = new editFigBtn("Edytuj",this);

        panel = new MyPanel();
        setContentPane(panel);

        MenuBar mb = new MenuBar();
        Menu menu = new Menu("Menu");
        MenuItem i1 = new MenuItem("Info");
        MenuItem i2 = new MenuItem("Zapisz");
        MenuItem i3 = new MenuItem("Odczytaj plik");

        mb.add(menu);
        menu.add(i1);
        menu.add(i2);
        menu.add(i3);
        setMenuBar(mb);

        i1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoFrame _infoFrame = new infoFrame(panel);
            }
        });

        i2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileSaver objectIO = new fileSaver();
                objectIO.WriteObjectToFile(panel.getFigureList());
                panel.getFigureList().clear();
                panel.repaint();
            }
        });

        i3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileSaver objectIO = new fileSaver();
                objectIO.LoadObjectFromFile(panel,panel.getFigureList().size() - 1);
            }
        });

        add(kolo);
        add(prostokat);
        add(wielokat);
        add(editBtn);

    }

    public MyPanel getMyPanel() { return panel; }
}

/** Glowna klasa programu*/
public class PaintPPT
{
    public static void main(String[] args)
    {
        MyFrame Paint = new MyFrame();
    }
}

/**Klasa przycisku w okienku edycji*/
class toolButton extends JButton implements ActionListener{

    /**Zmienna przechowujaca nazwe danego przycisku w okienku edycji*/
    String _name;
    toolFrame _frame;
    private MyFrame _MyFrame;
    static colourFrame color;

    /**Konstruktor przycisku w okienku edycji*/
    toolButton(String name, toolFrame frame, MyFrame myFrame){
        super(name);
        this._name = name;
        this._frame = frame;
        this._MyFrame = myFrame;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch(_name)
        {
            case "Kolory":
                this.color  = new colourFrame(_MyFrame);
                _MyFrame.getMyPanel().editor.setMovingMode(false);
                _MyFrame.getMyPanel().editor.setColourMode(true);
                break;
            case "Przesuwanie":
                _MyFrame.getMyPanel().editor.setMovingMode(true);
                if(this.color != null){
                    this.color.setVisible(false);
                    _MyFrame.getMyPanel().editor.setColourMode(false);
                }
                break;
        }
    }
}

/** Klasa ramki z przyciskami edytujacymi figury*/
class toolFrame extends JFrame{

    public JButton changeColour,sizeTransform;
    public JPanel panel;

    private MyFrame _frame;

    /**Konstruktor ramki z przyciskami edytujacymi figury*/
    public toolFrame(MyFrame frame){
        this._frame = frame;
        setSize(100,240);
        setTitle("PaintPPT");
        setLayout(new GridLayout(4,2));
        setLocation(1800,0);
        changeColour = new toolButton("Kolory",this, this._frame);
        sizeTransform = new toolButton("Przesuwanie",this, this._frame);
        addWindowListener(new MyWindowAdapter(this, frame, (toolButton) changeColour));

        changeColour.setBackground(Color.white);
        sizeTransform.setBackground(Color.white);

        panel = new JPanel();
        setContentPane(panel);
        add(changeColour);
        add(sizeTransform);
        setResizable(false);

        _frame.getMyPanel().editor.setColourMode(false);
        _frame.getMyPanel().editor.setMovingMode(false);
    }
}
/**Klasa adaptera zamykajacego okienko edycji*/
class MyWindowAdapter extends WindowAdapter
{
    public toolFrame _frame;
    private MyFrame _Frame;
    private toolButton _button;

    /**Konstruktor adaptera zamykajacego okienko edycji*/
    public MyWindowAdapter(toolFrame f, MyFrame frame, toolButton button){
        this._frame = f;
        this._Frame = frame;
        this._button = button;
    }

    public void windowClosing(WindowEvent e)
    {
        _Frame.panel.editor.isInEditMode = false;
        /** Warunek upewniajacy nas, ze okienko kolorowania zostalo otwarte*/
        if(_button.color != null){
            _button.color.setVisible(false);
            _Frame.getMyPanel().editor.setColourMode(false);
        }
        _Frame.editFramesNumber = 0;
    }
}

/**Klasa okienka z przyciskami do kolorowania figur*/
class colourFrame extends JFrame
{
    /**Zmienne przechowujace pliki obrazowe dla przyciskow dla kolorow*/
    public Image redImg, blueImg, yellowImg, greenImg, greyImg, purpleImg;

    private MyFrame _frame;

    colourButton redBtn,blueBtn,yellowBtn,greenBtn,greyBtn,purpleBtn;

    /**Konstruktor okienka z przyciskami do kolorowania figur*/
    colourFrame(MyFrame frame){
        this._frame = frame;
        setLocation(1800,220);
        setSize(100,240);
        setTitle("Kolory");
        setLayout(new GridLayout(3,2));
        addWindowListener(new MyWindowAdapter2(this._frame, this));
        redBtn = new colourButton("red",this._frame);
        redImg = new ImageIcon(this.getClass().getResource("RED.png")).getImage();
        redBtn.setIcon(new ImageIcon(redImg));
        redBtn.setBackground(Color.white);
        redBtn.setBorderPainted(false);
        blueBtn = new colourButton("blue",this._frame);
        blueImg = new ImageIcon(this.getClass().getResource("BLUE.png")).getImage();
        blueBtn.setIcon(new ImageIcon(blueImg));
        blueBtn.setBackground(Color.white);
        blueBtn.setBorderPainted(false);
        yellowBtn = new colourButton("yellow",this._frame);
        yellowImg = new ImageIcon(this.getClass().getResource("YELLOW.png")).getImage();
        yellowBtn.setIcon(new ImageIcon(yellowImg));
        yellowBtn.setBackground(Color.white);
        yellowBtn.setBorderPainted(false);
        greenBtn = new colourButton("green",this._frame);
        greenImg = new ImageIcon(this.getClass().getResource("GREEN.png")).getImage();
        greenBtn.setIcon(new ImageIcon(greenImg));
        greenBtn.setBackground(Color.white);
        greenBtn.setBorderPainted(false);
        greyBtn = new colourButton("gray",this._frame);
        greyImg = new ImageIcon(this.getClass().getResource("GREY.png")).getImage();
        greyBtn.setIcon(new ImageIcon(greyImg));
        greyBtn.setBackground(Color.white);
        greyBtn.setBorderPainted(false);
        purpleBtn = new colourButton("pink",this._frame);
        purpleImg = new ImageIcon(this.getClass().getResource("PINK.png")).getImage();
        purpleBtn.setIcon(new ImageIcon(purpleImg));
        purpleBtn.setBackground(Color.white);
        purpleBtn.setBorderPainted(false);


        add(blueBtn);
        add(redBtn);
        add(yellowBtn);
        add(greenBtn);
        add(greyBtn);
        add(purpleBtn);

        setVisible(true);
        setResizable(false);
    }
}
/**Klasa Adaptera zamykajacego okienko palety barw*/
class MyWindowAdapter2 extends WindowAdapter
{
    private MyFrame _Frame;
    private colourFrame _colourFrame;

    /**Konstruktor Adaptera zamykajacego okienko palety barw*/
    public MyWindowAdapter2(MyFrame frame, colourFrame colourFrame){
        this._Frame = frame;
        this._colourFrame = colourFrame;
    }

    public void windowClosing(WindowEvent e)
    {
        _Frame.panel.editor.setColourMode(false);
        _Frame.panel.editor.setMovingMode(false);
    }
}
/**Klasa przycisku do kolorowania*/
class colourButton extends JButton implements ActionListener{

    String _name;
    private MyFrame _myFrame;

    /**Konstruktor przycisku do kolorowania*/
    colourButton(String name, MyFrame myFrame){
        super(name);
        this._name = name;
        this._myFrame = myFrame;
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(_myFrame.getMyPanel().editor.getIdOfEditedFigure() != -1){
            switch(_name){
                case "red":
                    ((Figura)_myFrame.getMyPanel().getFigureList().get(_myFrame.panel.editor.getIdOfEditedFigure())).setFigureCurrentColor(Color.RED);
                    _myFrame.getMyPanel().repaint();
                    break;
                case "yellow":
                    ((Figura)_myFrame.getMyPanel().getFigureList().get(_myFrame.panel.editor.getIdOfEditedFigure())).setFigureCurrentColor(Color.YELLOW);
                    _myFrame.getMyPanel().repaint();
                    break;
                case "blue":
                    ((Figura)_myFrame.getMyPanel().getFigureList().get(_myFrame.panel.editor.getIdOfEditedFigure())).setFigureCurrentColor(Color.BLUE);
                    _myFrame.getMyPanel().repaint();
                    break;
                case "pink":
                    ((Figura)_myFrame.getMyPanel().getFigureList().get(_myFrame.panel.editor.getIdOfEditedFigure())).setFigureCurrentColor(Color.PINK);
                    _myFrame.getMyPanel().repaint();
                    break;
                case "green":
                    ((Figura)_myFrame.getMyPanel().getFigureList().get(_myFrame.panel.editor.getIdOfEditedFigure())).setFigureCurrentColor(Color.GREEN);
                    _myFrame.getMyPanel().repaint();
                    break;
                case "gray":
                    ((Figura)_myFrame.getMyPanel().getFigureList().get(_myFrame.panel.editor.getIdOfEditedFigure())).setFigureCurrentColor(Color.GRAY);
                    _myFrame.getMyPanel().repaint();
                    break;
            }
        }
        else
            System.out.println("WYBIERZ FIGURE");
    }
}
class infoFrame extends JFrame{
    MyPanel _panel;
    public void paint(Graphics g){
        super.paintComponents(g);
        g.drawString("Autor:", 55, 45);
        g.drawString("Adam Mucha", 35, 65);
        g.drawString("Przeznaczenie:", 30, 95);
        g.drawString("Kurs Programowania", 16, 115);
    }

    public infoFrame(MyPanel panel){
        super("PaintPPT");
        this._panel = panel;
        setSize(150,200);
        setLocation(1780,700);
        setTitle("Informacje");
        setLayout(new FlowLayout());
        setVisible(true);
        setResizable(false);
        paint(getGraphics());


    }
}

class fileSaver {

    private static final String filepath="C:\\Users\\Muszka\\Desktop\\ZapisanyPlik";

    public void WriteObjectToFile(Object serObj) {

        try {

            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void LoadObjectFromFile(MyPanel panel, int figureAmount){
        FileInputStream istream = null;

        ObjectInputStream p;
        List<Figura> fig;
        try {
            istream = new FileInputStream("C:\\Users\\Muszka\\Desktop\\ZapisanyPlik");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            p = new ObjectInputStream(istream);
            try {
                fig = (List<Figura>) p.readObject();
                for(int i = 0; i <= figureAmount; i++){
                    panel.getFigureList().add(fig.get(i));
                }
                //panel.repaint();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            istream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}



/*
int max_X = 0;
                int max_Y = 0;
                int min_X = 0;
                int min_Y = 0;
                int x_Det = 0;
                int y_Det = 0;

                for(int i = 0; i <= pol.getPolygonPointsList().size() - 1; i++)
                {
                    if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i] >= max_X){
                        max_X = ((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i];
                    }
                    if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i] <= min_X){
                        min_X = ((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i];
                    }

                    if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i] >= max_Y)
                    {
                        max_Y = ((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i];
                    }
                    if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i] <= min_Y){
                        min_Y = ((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i];
                    }
                    //((Polygon)_panel.getFigureList().get(editedFigureID)).set_Point(i, x, x);
                }
                x_Det = min_X + (max_X - min_X)/2;
                y_Det = min_Y + (max_Y - min_Y)/2;

                for(int i = 0; i <= pol.getPolygonPointsList().size() - 1; i++)
                {
                    Punkty [x] po prawej stronie od srodka
                    if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i] <= max_X && ((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i] >= x_Det){
                            ((Polygon)_panel.getFigureList().get(editedFigureID)).set_Point(i, -x, 0);

                            Punkty [x] po lewej stronie od srodka
                            if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i] >= min_X && ((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i] <= x_Det){
                            ((Polygon)_panel.getFigureList().get(editedFigureID)).set_Point(i, x, 0);
                            }

                            /**Punkty [y] po prawej stronie od srodka
                            if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i] <= max_Y && ((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i] >= y_Det){
                            ((Polygon)_panel.getFigureList().get(editedFigureID)).set_Point(i, 0, -x);
                            }
                            /**Punkty [y] po lewej stronie od srodka
                            if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i] >= min_Y && ((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i] <= y_Det){
                            ((Polygon)_panel.getFigureList().get(editedFigureID)).set_Point(i, 0, x);
                            }
                            //((Polygon)_panel.getFigureList().get(editedFigureID)).set_Point(i, x, x);
                            }*/


/*for(int i = 0; i <= pol.getPolygonPointsList().size() - 1; i++)
                {
                    averageX += pol.get_xPoints()[i];
                }
                averageX = averageX/pol.getPolygonPointsList().size();
                System.out.println("AverageX = " + averageX);

                for(int i = 0; i <= pol.getPolygonPointsList().size() - 1; i++)
                {
                    averageY += pol.get_yPoints()[i];
                }
                averageY = averageY/pol.getPolygonPointsList().size();
                System.out.println("AverageX = " + averageY);

                if(width_startX == 0 && width_startY == 0){
                        for(int i = 0; i < pol.getPolygonPointsList().size() - 1; i++)
        {
        if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i] > max_X){
        max_X = ((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i];
        }
        else if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i] < min_X){
        min_X = ((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i];
        }

        if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i] > max_Y)
        {
        max_Y = ((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i];
        }
        else if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i] < min_Y){
        min_Y = ((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i];
        }
        //((Polygon)_panel.getFigureList().get(editedFigureID)).set_Point(i, x, x);
        }

        x_mid = min_X + (max_X - min_X);
        y_mid = min_Y + (max_Y - min_Y);
        width_startX = Math.abs(max_X - min_X);
        width_startY = Math.abs(max_Y - min_Y);
        System.out.println("x_mid : " + x_mid);
        System.out.println("Width start : " + width_startX);
        }
        else
        {
        for(int i = 0; i < pol.getPolygonPointsList().size() - 1; i++)
        {
        if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i] >= max_X){
        max_X = ((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i];
        }

        if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i] <= min_X){
        min_X = ((Polygon)_panel.getFigureList().get(editedFigureID)).get_xPoints()[i];
        }

        if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i] >= max_Y)
        {
        max_Y = ((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i];
        }
        if(((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i] <= min_Y){
        min_Y = ((Polygon)_panel.getFigureList().get(editedFigureID)).get_yPoints()[i];
        }
        //((Polygon)_panel.getFigureList().get(editedFigureID)).set_Point(i, x, x);
        }

        width_currentX = Math.abs(max_X - min_X);
        width_currentY = Math.abs(max_Y - min_Y);

        for(int i = 0; i < pol.getPolygonPointsList().size() - 1; i++) {

        int x_actual = (width_currentX / width_startX) * (((Polygon) _panel.getFigureList().get(editedFigureID)).get_xPoints()[i] - x_mid) + x_mid;
        int y_actual = (width_currentY / width_startY) * (((Polygon) _panel.getFigureList().get(editedFigureID)).get_yPoints()[i] - y_mid) + y_mid;
        //System.out.println("X = " + ((Polygon) _panel.getFigureList().get(editedFigureID)).get_xPoints()[i]);
        //System.out.println(x_actual);
        ((Polygon) _panel.getFigureList().get(editedFigureID)).set_XPointSize(i, x_actual + x);
        ((Polygon) _panel.getFigureList().get(editedFigureID)).set_YPointSize(i, y_actual + x);
        }
        }

//   /**Punkty [x] po prawej stronie od srodka*/
//if (((Polygon) _panel.getFigureList().get(editedFigureID)).get_xPoints()[i] >= x_Det && ((Polygon) _panel.getFigureList().get(editedFigureID)).get_xPoints()[i] <= max_X) {
// ((Polygon) _panel.getFigureList().get(editedFigureID)).set_Point(i, -x, 0);
//}
///**Punkty [x] po lewej stronie od srodka*/
//if ( ((Polygon) _panel.getFigureList().get(editedFigureID)).get_xPoints()[i] <= x_Det && ((Polygon) _panel.getFigureList().get(editedFigureID)).get_xPoints()[i] >= min_X) {
//    ((Polygon) _panel.getFigureList().get(editedFigureID)).set_Point(i, x, 0);
//}
///**Punkty [y] po prawej stronie od srodka*/
//if ( ((Polygon) _panel.getFigureList().get(editedFigureID)).get_yPoints()[i] >= y_Det && ((Polygon) _panel.getFigureList().get(editedFigureID)).get_yPoints()[i] <= max_Y ) {
//  ((Polygon) _panel.getFigureList().get(editedFigureID)).set_Point(i, 0, -x);
// }
// /**Punkty [y] po lewej stronie od srodka*/
// if ( ((Polygon) _panel.getFigureList().get(editedFigureID)).get_yPoints()[i] <= y_Det && ((Polygon) _panel.getFigureList().get(editedFigureID)).get_yPoints()[i] >= min_Y) {
//     ((Polygon) _panel.getFigureList().get(editedFigureID)).set_Point(i, 0, x);
//}
//((Polygon)_panel.getFigureList().get(editedFigureID)).set_Point(i, x, x);