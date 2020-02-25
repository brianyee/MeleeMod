package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import globals.Constants;
import melee_mod.FalconCharacterMod;
import melee_mod.falcon.cards.keyword_card_helpers.ComboCardHelper;
import melee_mod.falcon.powers.helpers.CardCostHelper;
import melee_mod.falcon.powers.interfaces.ICostReducingBuff;

import java.util.ArrayList;

import static globals.Enums.CostAction.REDUCE;

public class JohnnyStockPower extends AbstractPower implements ICostReducingBuff {
    private static final String POWER_ID = Constants.Powers.JOHNNY_STOCK;
    private static final String NAME = "Johnny Stock";
    private ArrayList<AbstractCard> cardsToChange = new ArrayList<>();

    public JohnnyStockPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
        setCardGroup();
    }

    @Override
    public void updateDescription() {
        this.description = "While you have 20% or less of your max health, ALL attack cards add an additional combo-point to enemies and cost " + this.amount + " less [E].";
    }

    @Override
    public void onInitialApplication() {
        reduceCardCosts(this.amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {

    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK ){
            if (card.target == AbstractCard.CardTarget.ENEMY && action.target != null){
                ComboCardHelper.addComboPoint(action.target);
            } else if (card.target == AbstractCard.CardTarget.ALL || card.target == AbstractCard.CardTarget.ALL_ENEMY){
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    ComboCardHelper.addComboPoint(m);
                }
            }
        }
    }

    @Override
    public void onRemove() {
        AbstractPlayer p = AbstractDungeon.player;
        CardCostHelper.resetCardCost(this.cardsToChange);
        ComboPointPower.initializeComboPointCosts();
        CardCostHelper.initializeBuffCosts(this);

//        if (p.hasPower(L_CANCELED)){
//            p.getPower(L_CANCELED).onInitialApplication();
//        }
//        if (p.hasPower(EDGE_CANCELING)){
//            p.getPower(EDGE_CANCELING).onInitialApplication();
//        }
    }

    private void setCardGroup(){
        ArrayList<AbstractCard> allCards = new ArrayList<>();
        allCards.addAll(AbstractDungeon.player.hand.group);
        allCards.addAll(AbstractDungeon.player.drawPile.group);
        allCards.addAll(AbstractDungeon.player.discardPile.group);
        allCards.addAll(AbstractDungeon.player.exhaustPile.group);
        for (AbstractCard c : allCards) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                this.cardsToChange.add(c);
            }
        }
    }

    private void reduceCardCosts(int reduction){
        CardCostHelper.setCardCosts(this.cardsToChange, REDUCE, reduction);
    }
}
