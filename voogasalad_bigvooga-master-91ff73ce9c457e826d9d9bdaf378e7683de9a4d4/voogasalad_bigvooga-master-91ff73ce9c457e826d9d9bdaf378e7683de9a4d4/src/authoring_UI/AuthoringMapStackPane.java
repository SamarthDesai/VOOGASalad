package authoring_UI;

import java.util.function.BiConsumer;
import java.util.function.Function;

import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.DefaultSpriteObject;
import authoring_UI.Map.MapLayer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class AuthoringMapStackPane extends StackPane {

	MapLayer myML;
	ObjectProperty<Integer> rowSpanProperty;
	ObjectProperty<Integer> colSpanProperty;
	private int cellSize;
	private boolean active;
	private ObjectProperty<Boolean> activeProperty;
	private Background activeBackground;
	private Background inactiveBackground;
	private BiConsumer<AbstractSpriteObject, Integer> myBindSpriteWidth;
	private BiConsumer<AbstractSpriteObject, Integer> myBindSpriteHeight;
	private AbstractSpriteObject coveringSprite;
	private AbstractSpriteObject mySO;

	private ObjectProperty<Boolean> coveredByStretchedSpriteProperty;
	// private int myRow =

	public AuthoringMapStackPane(MapLayer ML) {
		super();
		activeBackground = new Background(new BackgroundFill(Color.MAGENTA, CornerRadii.EMPTY, Insets.EMPTY));
		inactiveBackground = new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY));
		cellSize = MapLayer.CELL_SIZE;

		initializeProperties();
		myML = ML;
	}

	private void initializeProperties() {
		if (rowSpanProperty == null) {
			rowSpanProperty = new SimpleObjectProperty<Integer>();
			rowSpanProperty.set(1);
		}
		this.setRowSpan(1); // Needed to handle removing children

		if (colSpanProperty == null) {
			colSpanProperty = new SimpleObjectProperty<Integer>();
			colSpanProperty.set(1);
		}

		this.setColSpan(1); // Needed to handle removing children

		colSpanProperty.addListener((observable, oldValue, newValue) -> {
			getOnColSpanChange().accept(oldValue, newValue);
		});

		rowSpanProperty.addListener((observable, oldValue, newValue) -> {
			getOnRowSpanChange().accept(oldValue, newValue);
		});

		createActiveProperty();
		createCoveredByOtherSpriteProperty();
		createShapeSpriteWidth();
		createShapeSpriteHeight();
	}
	
	public void setInactiveBackground(Background bg){
		this.inactiveBackground = bg;
		this.setBackground(bg);
	}
	
	public void setInactiveBackground(Color c){
		this.setInactiveBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
	}
	

	public void setCoveringSprite(AbstractSpriteObject ASO) {
		this.coveringSprite = ASO;
	}

	public void removeCoveringSprite() {
		this.coveringSprite = null;
	}

	private BiConsumer<Integer, Integer> getOnRowSpanChange() {
		BiConsumer<Integer, Integer> onRowChange = new BiConsumer<Integer, Integer>() {
			@Override
			public void accept(Integer oldValue, Integer newValue) {
//				;
//				;
				int diff = newValue - oldValue;
				int startRow = (diff > 0) ? getRowIndex() + oldValue : getRowIndex() + oldValue - 1;
				for (int i = 0; i < Math.abs(diff); i++) {
					int newRow = startRow + (int) Math.signum(diff) * i;
					for (int column = getColIndex(); column <= getFarRightColumn(); column++) {
						getMapLayer().getChildAtPosition(newRow, column).changeCoveredByOtherSprite();
						getMapLayer().getChildAtPosition(newRow, column).setInactive();

						if (getMapLayer().getChildAtPosition(newRow, column).isCoveredByOtherSprite()) {
							getMapLayer().getChildAtPosition(newRow, column).setCoveringSprite(mySO);
						}

					}
				}

			}
		};
		return onRowChange;

	}

	private BiConsumer<Integer, Integer> getOnColSpanChange() {
		BiConsumer<Integer, Integer> onColChange = new BiConsumer<Integer, Integer>() {

			@Override
			public void accept(Integer oldValue, Integer newValue) {
				int diff = newValue - oldValue;
				int startCol = (diff > 0) ? getColIndex() + oldValue : getColIndex() + oldValue - 1;
				for (int i = 0; i < Math.abs(diff); i++) {
					int newColumn = startCol + (int) Math.signum(diff) * i;
					for (int row = getRowIndex(); row <= getFarBottomRow(); row++) {
						getMapLayer().getChildAtPosition(row, newColumn).changeCoveredByOtherSprite();
						getMapLayer().getChildAtPosition(row, newColumn).setInactive();
						if (getMapLayer().getChildAtPosition(row, newColumn).isCoveredByOtherSprite()) {
							getMapLayer().getChildAtPosition(row, newColumn).setCoveringSprite(mySO);
						}
					}
				}

			}

		};
		return onColChange;
	}

	private void createCoveredByOtherSpriteProperty() {
		coveredByStretchedSpriteProperty = new SimpleObjectProperty<Boolean>();
		coveredByStretchedSpriteProperty.set(false);
		coveredByStretchedSpriteProperty.addListener((e, oldVal, newVal) -> {
			if (!newVal) {
				this.coveringSprite = null;
			}
		});

	}

	public void setCoveredByOtherSprite(boolean covered) {
		coveredByStretchedSpriteProperty.set(covered);
	}

	public void changeCoveredByOtherSprite() {
		coveredByStretchedSpriteProperty.set(!coveredByStretchedSpriteProperty.get());
	}

	public boolean isCoveredByOtherSprite() {
		return coveredByStretchedSpriteProperty.get();
	}

	public AbstractSpriteObject getCoveringSprite() {
		return this.coveringSprite;
	}

	private void createActiveProperty() {
		activeProperty = new SimpleObjectProperty<Boolean>();
		activeProperty.set(false);
		activeProperty.addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				this.getMapLayer().addActive(this);
				this.setBackground(activeBackground);
			} else {
				this.getMapLayer().removeActive(this);
				this.setBackground(inactiveBackground);
			}
		});
	}
	
	
	

	public boolean isActive() {
		return activeProperty.get();
	}

	public void setActive() {
		activeProperty.set(true);
	}

	public void setInactive() {
		activeProperty.set(false);
	}

	public void setActiveProperty(boolean in) {
		activeProperty.set(in);
	}

	public void switchActive() {
		activeProperty.set(!activeProperty.get());
	}

	public MapLayer getMapLayer() {
		return myML;
	}

	public void createShapeSpriteWidth() {
		myBindSpriteWidth = new BiConsumer<AbstractSpriteObject, Integer>() {

			@Override
			public void accept(AbstractSpriteObject t, Integer u) {
				t.setFitWidth(u * cellSize);
			}

		};
	}

	public void createDefaultShapeSpriteWidth() {
		myBindSpriteWidth = new BiConsumer<AbstractSpriteObject, Integer>() {

			@Override
			public void accept(AbstractSpriteObject t, Integer u) {
				// Nothing
			}

		};
	}

	public void createDefaultShapeSpriteHeight() {
		myBindSpriteHeight = new BiConsumer<AbstractSpriteObject, Integer>() {

			@Override
			public void accept(AbstractSpriteObject t, Integer u) {
				// Nothing
			}

		};
	}

	public void createShapeSpriteHeight() {
		myBindSpriteHeight = new BiConsumer<AbstractSpriteObject, Integer>() {

			@Override
			public void accept(AbstractSpriteObject t, Integer u) {
				t.setFitHeight(u * cellSize);
			}

		};
	}

	private BiConsumer<AbstractSpriteObject, Integer> getShapeSpriteHeightBiConsumer() {
		return myBindSpriteHeight;
	}

	private BiConsumer<AbstractSpriteObject, Integer> getShapeSpriteWidthBiConsumer() {
		return myBindSpriteWidth;
	}

	public void shapeSpriteToCellSize() {
		createShapeSpriteWidth();
		createShapeSpriteHeight();
		this.setAlignment(mySO, Pos.CENTER_LEFT);

		
		this.rowSpanProperty.addListener((observable, oldValue, newValue) -> {
			this.getShapeSpriteHeightBiConsumer().accept(mySO, newValue);
		});

		this.colSpanProperty.addListener((observable, oldValue, newValue) -> {
			getShapeSpriteWidthBiConsumer().accept(mySO, newValue);
		});
	}

	public boolean checkCanAcceptChild(AbstractSpriteObject ASO) {
		return checkChangeSizeIsValid(ASO, getRowIndex(), getRowIndex() + ASO.getNumCellsHeight() - 1, getColIndex(),
				getColIndex() + ASO.getNumCellsWidth() - 1);
	}

	public boolean addChild(AbstractSpriteObject ASO) {

		if (checkCanAcceptChild(ASO)) {
			mySO = ASO;
			this.getChildren().clear();
			setCoveredByOtherSprite(true);
			this.setCoveringSprite(mySO);
			shapeSpriteToCellSize();

			setRowSpan(mySO.getNumCellsHeight());
			setColSpan(mySO.getNumCellsWidth());
			mySO.setFitHeight(this.rowSpanProperty.get() * cellSize);
			mySO.setFitWidth(this.colSpanProperty.get() * cellSize);

			// ;
			this.getChildren().add(mySO);
			mySO.setWidthFunction(widthCheckValidFunction());
			mySO.setHeightFunction(heightCheckValidFunction());
			System.out.println("We added a child which is good SLACK "+ mySO.getName());
			mySO.setPositionOnGrid(this.getPositionOnGrid());
			return true;
		}
		return false;

	}

	public void removeChild() {
		createDefaultShapeSpriteWidth();
		createDefaultShapeSpriteHeight();

		setRowSpan(1);
		setColSpan(1);
		this.setCoveredByOtherSprite(false);
		this.getChildren().clear();
	}

	public AbstractSpriteObject getChild(){
		return this.mySO;

	}

	public boolean hasChild() {
		int size = this.getChildren().size();
		if (this.getChildren().size() == 1) {
			Node child = this.getChildren().get(0);
			if (child instanceof DefaultSpriteObject) {
				return false;
			}
			return true;
		}
		return size > 0;
	}

	// private void createRowSpanProperty(){
	//
	// }

	public void setRowSpan(int span) {
		// ;
		if (span<=0){
			span=1;
		}
		getMapLayer().setRowSpan(this, span);
		rowSpanProperty.set(span);
	}

	public void setColSpan(int span) {
		// ;
		if (span<=0){
			span = 1;
		}
		getMapLayer().setColumnSpan(this, span);
		colSpanProperty.set(span);
	}

	public int getRowSpan() {

		return getMapLayer().getRowSpan(this);
	}

	public int getColSpan() {
		return getMapLayer().getColumnSpan(this);
	}

	public int getRowIndex() {
		return getMapLayer().getRowIndex(this);
	}

	public int getColIndex() {
		return getMapLayer().getColumnIndex(this);
	}
	
	public Integer[] getPositionOnGrid(){
		Integer[] ret = new Integer[2];
		ret[0] = getRowIndex();
		ret[1] = getColIndex();
		return ret;
	}

	private int getFarRightColumn() {
		return getColIndex() + getColSpan() - 1;
	}

	private int getFarBottomRow() {
		return getRowIndex() + getRowSpan() - 1;
	}

	private boolean checkChangeRowSpanIsValid(int newRowSpan) {
		if (newRowSpan <= this.getRowSpan()) {
			return true;
		} else {
			int startRow = this.getFarBottomRow() + 1;
			int endRow = this.getRowIndex() + newRowSpan - 1;
			int startCol = this.getColIndex();
			int endCol = this.getFarRightColumn();
			return checkChangeSizeIsValid(null, startRow, endRow, startCol, endCol);
		}

	}

	private boolean checkChangeSizeIsValid(AbstractSpriteObject ASO, Integer startRow, Integer endRow, Integer startColumn, Integer endColumn) {

		for (int row = startRow; row <= endRow; row++) {
			for (int column = startColumn; column <= endColumn; column++) {
//				;
				AuthoringMapStackPane newCoveredCell = getMapLayer().getChildAtPosition(row, column);
				if (newCoveredCell.isCoveredByOtherSprite()) {
					if (ASO!=null&&newCoveredCell.getCoveringSprite().equals(ASO)){
						// Nothing just keep checking cells
					} else {
					return false;
					}

				}
			}
		}
		return true;
	}

	private boolean checkChangeColumnSpanIsValid(Integer newColumnSpan) {
//		;
		if (newColumnSpan <= this.getColSpan()) {
			return true;
		} else {
			int endCol = this.getColIndex() + newColumnSpan - 1;
			return this.checkChangeSizeIsValid(null, this.getRowIndex(), this.getFarBottomRow(), this.getFarRightColumn() + 1,
					endCol);
		}
	}

	public Function<Integer, Boolean> heightCheckValidFunction() {
		Function<Integer, Boolean> consumer = new Function<Integer, Boolean>() {
			@Override
			public Boolean apply(Integer t) {
				boolean ret = true;
				if (t <= 0) {
					ret = false;
				} else if (t < getRowSpan()) {

					ret = true;
				} else {

					ret = checkChangeRowSpanIsValid(t);
				}

				if (ret) {
					setRowSpan(t);
				}
				return ret;
			}

		};
		return consumer;
	}

	public Function<Integer, Boolean> widthCheckValidFunction() {
		Function<Integer, Boolean> consumer = new Function<Integer, Boolean>() {
			@Override
			public Boolean apply(Integer t) {
				boolean ret = true;
				if (t <= 0) {
					ret = false;
				} else if (t < getColSpan()) {
					ret = true;
				} else {
					ret = checkChangeColumnSpanIsValid(t);

				}

				if (ret) {
					setColSpan(t);
				}
				return ret;

			}

		};
		return consumer;
	}
}
