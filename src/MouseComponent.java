package Sapper;

import java.awt.Graphics;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class MouseComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	public static final int SIDELEGTH = 16;
	public static final int LEFTBUTTON = 1;
	public static final int RIGHTBUTTON = 3;
	HashSet<Integer> cellMines;
	public ArrayList<Integer> imag;
	public ArrayList<Image> image;

	int sizeIcon = 16;
	int height;
	int width;
	int mines = 40;

	int status = 0;
	int zero = 0;
	int closeCell = 0;
	int openCell = 1;
	int markMine = 2;
	int cellMine = 11;
	int shotMine = 12;
	int errorMine = 13;

	public int field[][][];
	int yCellMouse, xCellMouse;
	boolean endGame = false;

	public MouseComponent(int height, int width) {
		this.height = height;
		this.width = width;
		image = new ArrayList<Image>();
		for (int i = 0; i < 14; i++) {
			String fileName = "../Alex/src/Sapper/" + String.valueOf(i)
					+ ".gif";
			image.add(new ImageIcon(fileName).getImage());
		}

		cellMines = new HashSet<Integer>();
		field = new int[height][width][2];
		addMouseListener(new MouseHandler());

		startGame();

	}

	// ------------------------------------------------------------------------
	public void startGame() {
		for (int line = 0; line < height; line++) {
			for (int column = 0; column < width; column++) {
				field[line][column][status] = zero;
				field[line][column][openCell] = closeCell;
			}
		}
		setMines();
		setCell();
		repaint();
	}

	// -------------------------------------------------------------------------
	public void endGame(String winOrLosing) {

		Object[] options = { "Èãðàòü åùå", "Âûõîä", };
		int npos = JOptionPane.showOptionDialog(null,
				winOrLosing,// "ÂÛ ÏÎÁÅÄÈËÈ",
				"ÈÃÐÀ ÎÊÎÍ×ÅÍÀ", JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (npos == 0) {
			startGame();
		} else {
			System.exit(0);
		}
	}

	// ------------------------------------------------------------------------
	public void zeroCellField(int column, int line) {
		ArrayList<CellField> zeroField = new ArrayList<>();
		int countCell = 0;

		zeroField.add(new CellField(line, column));
		field[line][column][openCell] = openCell;

		for (int u = 0; u < zeroField.size(); u++) {
			column = zeroField.get(u).getCoordColumn();
			line = zeroField.get(u).getCoordLine();

			for (int besideLine = -1; besideLine <= 1; besideLine++) {
				for (int besideColumn = -1; besideColumn <= 1; besideColumn++) {
					int yBesideCell = line + besideLine;
					if (yBesideCell < 0 || yBesideCell >= height)
						continue;
					int xBesideCell = column + besideColumn;
					if (xBesideCell < 0 || xBesideCell >= width)
						continue;
					if (xBesideCell == column && yBesideCell == line)
						continue;
					if (field[yBesideCell][xBesideCell][openCell] != markMine) {
						if (field[yBesideCell][xBesideCell][status] == zero) {
							CellField zeroCell = new CellField(yBesideCell,
									xBesideCell);
							if (zeroCell.indexOfCell(zeroField) == false) {
								zeroField.add(zeroCell);
							}
						}
						field[yBesideCell][xBesideCell][openCell] = openCell;
					}
				}
			}
			countCell++;
		}
	}

	// ------------------------------------------------------------------------
	public void setMines() {
		cellMines.clear();
		while (cellMines.size() < mines) {
			int numMine = (int) (Math.random() * height * width);
			cellMines.add(numMine);
		}

		for (Integer r : cellMines) {
			field[(int) r / width][(int) r % width][status] = cellMine;
		}
	}

	// ------------------------------------------------------------------------
	public void setCell() {
		for (int line = 0; line < height; line++) {
			for (int column = 0; column < width; column++) {
				if (field[line][column][status] != cellMine) {
					int countMine = 0;

					for (int besideLine = -1; besideLine <= 1; besideLine++) {
						for (int besideColumn = -1; besideColumn <= 1; besideColumn++) {
							
							int yBesideCell = line + besideLine;
							if (yBesideCell < 0 || yBesideCell >= height)
								continue;
							int xBesideCell = column + besideColumn;
							if (xBesideCell < 0 || xBesideCell >= width)
								continue;
							if (xBesideCell == column && yBesideCell == line)
								continue;

							if (field[yBesideCell][xBesideCell][status] == cellMine)
								++countMine;
						}
					}
					field[line][column][status] = countMine;
				}
			}
		}
	}

	// ------------------------------------------------------------------------
	public void paintComponent(Graphics g) {
		int x0Screen = 1;
		int y0Screen = 1;
		for (int line = 0; line < height; line++) {
			for (int column = 0; column < width; column++) {
				Image fil = image.get(field[line][column][status]);
				if (field[line][column][openCell] == closeCell) {
					fil = image.get(9);
				}
				if (field[line][column][openCell] == markMine) {
					fil = image.get(10);
				}
				g.drawImage(fil, x0Screen + sizeIcon * column, y0Screen
						+ sizeIcon * line, null);
			}
		}
	}

	// ==================================================================

	public void winGame() {
		int markMinesAll = 0;
		int countOpenCell = 0;
		for (int line = 0; line < height; line++) {
			for (int column = 0; column < width; column++) {
				if (field[line][column][status] == cellMine
						&& field[line][column][openCell] == markMine)
					++markMinesAll;
				if (field[line][column][openCell] == openCell)
					++countOpenCell;
			}
		}
		if (mines == markMinesAll && countOpenCell == (height * width - mines)) {
			endGame("ÂÛ ÏÎÁÅÄÈËÈ");
		}
	}

	// ==================================================================
	public void looseGame() {
		for (int line = 0; line < height; line++) {
			for (int column = 0; column < width; column++) {
				if (field[line][column][status] == cellMine
						&& field[line][column][openCell] != markMine) {
					field[line][column][openCell] = openCell;
				}
				if (field[line][column][openCell] == markMine
						&& field[line][column][status] != cellMine) {
					field[line][column][status] = errorMine;
					field[line][column][openCell] = openCell;
				}
			}
		}
		field[yCellMouse][xCellMouse][status] = shotMine;
		repaint();
		endGame("ÂÛ ÏÐÎÈÃÐÀËÈ");
	}

	// ==================================================================
	public class MouseHandler extends MouseAdapter {
		public void mousePressed(MouseEvent event) {
			yCellMouse = ((int) event.getPoint().getY() - 1) / sizeIcon;
			xCellMouse = ((int) event.getPoint().getX() - 1) / sizeIcon;

			if (event.getButton() == LEFTBUTTON) {
				if (field[yCellMouse][xCellMouse][status] == zero
						&& field[yCellMouse][xCellMouse][openCell] == closeCell) {
					zeroCellField(xCellMouse, yCellMouse);
				}
				if (field[yCellMouse][xCellMouse][openCell] == closeCell) {
					field[yCellMouse][xCellMouse][openCell] = openCell;
				}
				if (field[yCellMouse][xCellMouse][status] == cellMine
						&& field[yCellMouse][xCellMouse][openCell] != markMine) {
					looseGame();
				}
			}
			// ------------------------------------------------------
			if (event.getButton() == RIGHTBUTTON) {
				if (field[yCellMouse][xCellMouse][openCell] == closeCell) {
					field[yCellMouse][xCellMouse][openCell] = markMine;
				} else {
					if (field[yCellMouse][xCellMouse][openCell] == markMine) {
						field[yCellMouse][xCellMouse][openCell] = closeCell;
					}
				}
			}
			repaint();
			winGame();
		}
	}
}
