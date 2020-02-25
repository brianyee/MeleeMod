package melee_mod.falcon.powers.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import globals.Enums;
import melee_mod.falcon.powers.interfaces.ICostReducingBuff;

import java.util.ArrayList;

public class CardCostHelper {
    public static void setCardCosts(ArrayList<AbstractCard> cards, Enums.CostAction action, int difference){
        for (AbstractCard c : cards) {
            setCardCost(c, action, difference);
        }
    }

    public static void resetCardCost(ArrayList<AbstractCard> cards){
        for (AbstractCard c : cards) {
            setCardCost(c, Enums.CostAction.RESET, 0);
        }
    }

    // I bet at the moment this overwrites Mummified hand and Enchiridion
    private static void setCardCost(AbstractCard c, Enums.CostAction action, int difference){
        if (action == Enums.CostAction.REDUCE){
            c.setCostForTurn(c.costForTurn - difference);
        } else if (action == Enums.CostAction.INCREASE) {
            c.setCostForTurn(c.costForTurn + difference);
        } else {
            resetCardCost(c);
        }
    }

    public static void resetCardCost(AbstractCard c) {
        c.costForTurn = c.cost;
        c.isCostModifiedForTurn = false;
    }

    public static void initializeBuffCosts(AbstractPower currentPower){
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractPower power: p.powers) {
            if (power != currentPower && power instanceof ICostReducingBuff) {
                power.onInitialApplication();
            }
        }
    }
}
