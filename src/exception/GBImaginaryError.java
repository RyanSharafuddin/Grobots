package exception;

class GBImaginaryError extends GBArithmeticError {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9195654999185742960L;

	// public:
	@Override
	public String toString() {
		return "imaginary result";
	}
}