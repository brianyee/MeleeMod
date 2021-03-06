package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.patches.CustomTags;
import melee_mod.falcon.powers.helpers.CardCostHelper;
import melee_mod.falcon.powers.interfaces.ICostReducingBuff;

import java.util.ArrayList;

import static globals.Constants.Powers.*;
import static globals.Enums.CostAction.REDUCE;

public class LCanceledPower extends AbstractPower implements ICostReducingBuff {
    private static final String POWER_ID = L_CANCELED;
    private static final String NAME = "Just L-Canceled";
    private ArrayList<AbstractCard> cardsToChange = new ArrayList<>();

    public LCanceledPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
        this.setCardGroup();
    }

    @Override
    public void updateDescription() {
        this.description = "Cost of your next non-Aerial card is reduced by " + this.amount + " [E] .";
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.tags.contains(CustomTags.AERIAL)){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void onRemove() {
        CardCostHelper.resetCardCost(this.cardsToChange);
        ComboPointPower.initializeComboPointCosts();
        CardCostHelper.initializeBuffCosts(this);
    }

    @Override
    public void onInitialApplication() {
        CardCostHelper.setCardCosts(this.cardsToChange, REDUCE, this.amount);
    }

    @Override
    public void onCardDraw(AbstractCard c) {
        if (!c.tags.contains(CustomTags.AERIAL) && !c.isCostModifiedForTurn) {
            CardCostHelper.initializeBuffCostsForCard(c);
        }
    }

    @Override
    public boolean shouldAddToCardGroup(AbstractCard c){
        return !c.tags.contains(CustomTags.AERIAL);
    }

    @Override
    public int getReduction(){
        return this.amount;
    }

    @Override
    public ArrayList<AbstractCard> getCardsToChange() {
        return this.cardsToChange;
    }
}
