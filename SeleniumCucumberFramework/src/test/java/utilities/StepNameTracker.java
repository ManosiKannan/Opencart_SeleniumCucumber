package utilities;

public class StepNameTracker {
	private static final ThreadLocal<String> stepName = new ThreadLocal<>();

	public static void setStepName(String name) {
		stepName.set(name);
	}

	public static String getStepName() {
		return stepName.get();
	}
}
