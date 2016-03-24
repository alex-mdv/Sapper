package Sapper;

import java.util.ArrayList;



public class CellField {
	int coordLine;
	int coordColumn;

	public CellField(int coordLine, int coordColumn) {
		this.coordLine = coordLine;
		this.coordColumn = coordColumn;
	}

	public int getCoordLine() {
		return coordLine;
	}

	public int getCoordColumn() {
		return coordColumn;
	}

	@Override
	public String toString() {
		return "Coord(" + coordLine + "," + coordColumn + ")";
	}

	public boolean equals(CellField obj) {
		if (coordLine == obj.coordLine && coordColumn == obj.coordColumn)
			return true;
		return false;
	}

	public boolean indexOfCell(ArrayList<CellField> zeroField) {
		for (CellField d : zeroField) {
			if (d.equals(this)) {
				return true;
			}
		}
		return false;
	}

}
