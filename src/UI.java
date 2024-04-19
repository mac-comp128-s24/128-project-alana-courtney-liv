import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.ui.Button;

public class UI extends GraphicsGroup {
    
    public UI(int width, int height){
        this.add(new Rectangle(0, 0, width, height));
    }
}
