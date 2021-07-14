package model.enums;

import model.units.Unit;

public class Cell {
    private Unit unit;
    private CellType cellType;


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


}
