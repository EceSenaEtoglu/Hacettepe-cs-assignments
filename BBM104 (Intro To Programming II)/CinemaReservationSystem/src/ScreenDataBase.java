import java.util.HashMap;

public class ScreenDataBase {

    private static int discountPercentage;
    private static String title;
    private static int maxErrorWithoutBlocked;
    private static int blockTimeSeconds;

    public static HashMap<String,Screen> screens = new HashMap<>();

    public static void addScreen(Screen screen) {
        screens.put(screen.getName(),screen);
    }
    public static Screen getScreen(String screenName) {
        return screens.get(screenName);
    }

    public static void setDiscountPercentage(int x) {
        discountPercentage = x;
    }

    public static int getDiscountPercentage() {
        return discountPercentage;
    }

    public static void setTitle(String title) {
        ScreenDataBase.title = title;
    }

    public static String getTitle() {
        return title;
    }

    public static int getMaxErrorWithoutBlocked() {
        return maxErrorWithoutBlocked;
    }

    public static int getBlockTimeSeconds() {
        return blockTimeSeconds;
    }

    public static void setMaxErrorWithoutBlocked(int maxErrorWithoutBlocked) {
        ScreenDataBase.maxErrorWithoutBlocked = maxErrorWithoutBlocked;
    }

    public static void setBlockTimeSeconds(int blockTimeSeconds) {
        ScreenDataBase.blockTimeSeconds = blockTimeSeconds;
    }
}
