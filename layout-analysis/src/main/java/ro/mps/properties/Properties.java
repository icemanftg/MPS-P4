package ro.mps.properties;

public enum Properties {

	APP_TITLE("Layout Analysis"),
	OUTPUT_FORMAT("jpg");
	
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	Properties(String value) {
		this.value = value;
	}
}
