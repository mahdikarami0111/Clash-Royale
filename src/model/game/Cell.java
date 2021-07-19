package model.game;

import model.enums.CellType;
import model.units.Unit;

public class Cell {
    private Unit unit;
    private CellType cellType;

    public Cell(CellType cellType){
        unit = null;
        this.cellType = cellType;
    }

    public synchronized Unit getUnit() {
        return unit;
    }

    public  synchronized CellType getCellType() {
        return cellType;
    }

    public synchronized void setUnit(Unit unit) {
        this.unit = unit;
    }

    public synchronized void setCellType(CellType cellType) {
        this.cellType = cellType;
    }
}
