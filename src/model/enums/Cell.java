package model.enums;

import model.units.Unit;

public class Cell {
    private Unit unit;
    private CellType cellType;
    private double x;
    private double y;

    public Cell(CellType cellType){
        unit = null;
        this.cellType = cellType;
    }

    public Unit getUnit() {
        return unit;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
