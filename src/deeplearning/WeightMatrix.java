package deeplearning;

public class WeightMatrix {
	
	private boolean isInitialized = false;
	
	private WeightMatrixRow[] rows;
	
	public WeightMatrix(int length, int width) {
		if (length <= 0 || width <= 0)
			throw new IllegalArgumentException(String.format("Expected positive, got length=%s, width=%s", length, width));
		rows = new WeightMatrixRow[length];
		for (int x = 0; x < length; x++) {
			rows[x] = new WeightMatrixRow(width);
		}
	}
	
	public void initialize(double[][] values) {
		if (isInitialized)
			throw new UnsupportedOperationException("Matrix has already been initialized");
		isInitialized = true;
		if (values.length != rows.length)
			throw new IllegalArgumentException(String.format("Expected %s, got %s", rows.length, values.length));
		for (int y = 0; y < values.length; y++) {
			rows[y].initialize(values[y]);
		}
	}
	
	public double getValue(int row, int column) {
		return rows[row].getValue(column);
	}
	
	public void add(WeightMatrix adjustments, double multiplier) {
		for (int y = 0; y < rows.length; y++) {
			WeightMatrixRow row = rows[y];
			for (int x = 0; x < rows[0].getLength(); x++) {
				row.add(x, adjustments.getValue(y, x) * multiplier);
			}
		}
	}
	
	private class WeightMatrixRow {
		
		private double[] weights;
		
		private WeightMatrixRow(int length) {
			weights = new double[length];
		}
		
		private void initialize(double[] values) {
			for (int x = 0; x < weights.length; x++) {
				weights[x] = values[x];
			}
		}
		
		private double getValue(int index) {
			return weights[index];
		}
		
		private void add(int index, double value) {
			weights[index] += value;
		}
		
		private int getLength() {
			return weights.length;
		}
	}
}