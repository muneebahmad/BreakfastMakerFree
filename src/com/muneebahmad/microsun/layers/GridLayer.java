package com.muneebahmad.microsun.layers;

/**
 * @author muneebahmad
 *
 */
import com.algorithmi.maker.breakfast.data.SharedData;
import com.algorithmi.maker.breakfast.free.main.R;
import com.algorithmi.maker.breakfast.model.AddOn;
import com.algorithmi.maker.breakfast.model.Cup;
import com.algorithmi.maker.breakfast.model.Drink;
import com.algorithmi.maker.breakfast.model.Plate;
import com.muneebahmad.microsun.util.Core;
import com.wiyun.engine.actions.CallFunc;
import com.wiyun.engine.actions.FiniteTimeAction;
import com.wiyun.engine.actions.MoveTo;
import com.wiyun.engine.actions.Sequence;
import com.wiyun.engine.nodes.Button;
import com.wiyun.engine.nodes.Director;
import com.wiyun.engine.nodes.Layer;
import com.wiyun.engine.nodes.Sprite;
import com.wiyun.engine.opengl.Texture2D;
import com.wiyun.engine.types.WYPoint;
import com.wiyun.engine.types.WYSize;

/**
 *
 * @author muneebahmad
 */
public class GridLayer extends Layer {

    private Sprite bg;
    private Button endButton;
    private GridInterface gridInterface;
    /**
     *
     */
    public GridView gridView;
    private WYSize wySize = Director.getInstance().getWindowSize();

    /**
     *
     * @param gridInterface
     */
    public GridLayer(GridInterface gridInterface) {
        this.gridInterface = gridInterface;
        this.gridView = new GridView(gridInterface);
        addChild(this.gridView, 1);
        setGridBackground();
        addEndButton();
    }

    private void setGridBackground() {
        this.bg = Sprite.make(Texture2D.make("bgs/grid_bg.png"));
        this.bg.setPosition(this.wySize.width / 2.0f,
                this.wySize.height / 2.0f - 30.0f);
        addChild(this.bg);
        this.bg.setScale(1.0f);
        this.bg.autoRelease(true);
    }

    private void addEndButton() {
        this.endButton = Button.make(Sprite.make(Texture2D.make("ui/end_button1.png")),
                Sprite.make(Texture2D.make("ui/end_button12.png")), this, null, null, "onEndButtonClicked");
        this.endButton.setPosition(this.bg.getWidth() - 60.0f, this.bg.getHeight() - 10.0f);
        this.bg.addChild(this.endButton);
        this.endButton.autoRelease(true);
    }

    /**
     *
     */
    public void onEndButtonClicked() {
        this.gridInterface.onCrossButtonClicked();
    }

    /**
     *
     */
    public void populateGrid() {
        int l = 0;
        int m = 0;
        int i = 0;
        int j = 0;
        int k = 0;
        int a = 0;
        int b = 0;
        int x = 0;
        int y = 0;
        int i3;
        int i5;
        int i7;
        int i9;
        i3 = -1 + SharedData.getInstance().plateList.size();
        i5 = -1 + SharedData.getInstance().drinkList.size();
        i7 = -1 + SharedData.getInstance().cupList.size();
        i9 = -1 + SharedData.getInstance().addOnList.size();

        switch (Core.gridModeCurrent) {
            case 1:
                for (k = 0; k <= i3; k++) {
                    j++;
                     if (k % 3 == 0) {
                    i -= 130;
                    j = 1;
                }
                     
                Sprite localSprite = Sprite.make(Texture2D.make(((Plate) SharedData.getInstance()
                        .plateList.get(k)).getImageResourceName()));
                localSprite.setScale(0.20000000298023223877f);
                Button plateButt = this.gridView.getScrollItem(WYPoint.make(j * 135, i), 
                        localSprite, ((Plate) SharedData.getInstance().plateList.get(k)).isLocked(),
                        ((Plate) SharedData.getInstance().plateList.get(k)).getId());
                this.gridView.addScrollableChild(plateButt);   
                }//end loop
                break;
            case 2:
               for (k = 0; k <= i5; k++) {
                   l++;
                   if (k % 3 == 0) {
                       m -= 130;
                       l = 1;
                   }
                   
                   Sprite localSprite = Sprite.make(Texture2D.make(((Drink) SharedData.getInstance()
                        .drinkList.get(k)).getImageResourceName()));
                localSprite.setScale(0.30f);
                Button plateButt = this.gridView.getScrollItem(WYPoint.make(l * 135, m), 
                        localSprite, ((Drink) SharedData.getInstance().drinkList.get(k)).isLocked(),
                        ((Drink) SharedData.getInstance().drinkList.get(k)).getId());
                this.gridView.addScrollableChild(plateButt);
               }//end loop 
                break;
            case 3:
                for (k = 0; k <= i7; k++) {
                a++;
                if (k % 3 == 0) {
                    b -= 130;
                    a = 1;
                }
                Sprite localSprite = Sprite.make(Texture2D.make(((Cup) SharedData.getInstance()
                        .cupList.get(k)).getImageResourceName()));
                localSprite.setScale(0.30f);
                Button plateButt = this.gridView.getScrollItem(WYPoint.make(a * 135, b), 
                        localSprite, ((Cup) SharedData.getInstance().cupList.get(k)).isLocked(),
                        ((Cup) SharedData.getInstance().cupList.get(k)).getId());
                this.gridView.addScrollableChild(plateButt);
                        
                }//end loop
                break;
            case 5:
                for (k = 0; k <= i9; k++) {
                    x++;
                    if (k % 3 == 0) {
                        y -= 130;
                        x = 1;
                    }
                    Sprite localSprite = Sprite.make(Texture2D.make(((AddOn) SharedData.getInstance()
                            .addOnList.get(k)).getImageResourceName()));
                    localSprite.setScale(0.5000f);
                    Button butt = this.gridView.getScrollItem(WYPoint.make(x * 135, y), 
                        localSprite, ((AddOn) SharedData.getInstance().addOnList.get(k)).isLocked(),
                        ((AddOn) SharedData.getInstance().addOnList.get(k)).getId());
                this.gridView.addScrollableChild(butt);
                }//end loop
        }
    }

    /**
     *
     */
    public void removeGridView() {
        removeChild(this.gridView, true);
        removeChild(this.bg, true);
    }

    /**
     *
     */
    public void removeGridViewWithAction() {
        MoveTo localMoveTo = MoveTo.make(0.5f, 0.0f, 0.0f, 0.0f, -1000.0f);
        CallFunc callFunc = CallFunc.make(this, "removeGridView");
        FiniteTimeAction[] arrayOfFiniteTimeAction = new FiniteTimeAction[1];
        arrayOfFiniteTimeAction[0] = callFunc;
        runAction(Sequence.make(localMoveTo, arrayOfFiniteTimeAction));
    }
}// end class
