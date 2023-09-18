import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class ProjectImages {

    // URL for arrows in buttons
    private final static String URLBase =
            "https://liveexample.pearsoncmg.com/common";

    private final static HashMap<String,Image> images = new HashMap<>();


    public static Image getRightArrow() {
        return images.get("right arrow");
    }
    public static Image getLeftArrow() {
        return images.get("left arrow");
    }

    public static void initImages() {

        images.put("reserved seat",new Image("assets\\icons\\reserved_seat.png"));
        images.put("empty seat",new Image("assets\\icons\\empty_seat.png"));
        images.put("right arrow",new Image(URLBase+"/image/right.gif"));
        images.put("left arrow",new Image(URLBase+"/image/left.gif"));
        images.put("title image", new Image("assets\\icons\\logo.png"));
    }

    public static Image getReservedSeat()
    {

        return images.get("reserved seat");

    }

    public static Image getEmptySeat() {
        return images.get("empty seat");
    }

    public static Image getTitleImage() {
        return images.get("title image");
    }



}
