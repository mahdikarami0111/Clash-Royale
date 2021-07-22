package model.game;

import model.enums.CellType;
import model.units.Unit;
/**
 * a cell in map
 */
public class Cell {
    private Unit unit;
    private CellType cellType;

    /**
     *a cell will be initialized based upon its type
     * @param cellType is the type
     *
     */
    public Cell(CellType cellType){
        unit = null;
        this.cellType = cellType;
    }

    /**
     *
     * @return unit in cell
     */
    public synchronized Unit getUnit() {
        return unit;
    }

    /**
     *
     * @return cell type
     */
    public  synchronized CellType getCellType() {
        return cellType;
    }

    /**
     *
     * @param unit is the unit put in cell
     */
    public synchronized void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     *
     * @param cellType is the cell type of cell
     */
    public synchronized void setCellType(CellType cellType) {
        this.cellType = cellType;
    }
}
