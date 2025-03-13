package rpg;

public class ActionResult {
    private final boolean isSuccess;
    private final String message;

    public ActionResult(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }
}
