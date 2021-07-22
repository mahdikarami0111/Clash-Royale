package model.enums;

/**
 * a cell can have 4 types: player , bot , path , block: player is when cell is occupied by player
 * bot is when cell is occupied by bot, path is when troops can cross the cell, block is when troops can pass the cell
 */
public enum CellType {
    PLAYER,
    BOT,
    PATH,
    BLOCK,
}
