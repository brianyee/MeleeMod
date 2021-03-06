package melee_mod.falcon.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import melee_mod.FalconCharacterMod;
import melee_mod.falcon.cards.keyword_card_helpers.FinisherCard;
import melee_mod.falcon.patches.AbstractCardEnum;
import globals.Constants;

import static melee_mod.falcon.patches.CustomTags.AERIAL;

public class BackAir extends FinisherCard {
    private static final String ID = Constants.CardNames.BACK_AIR;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BASE_DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 3;

    public BackAir() {
        super(ID, NAME, FalconCharacterMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.FALCON_BLUE, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = BASE_DAMAGE;
        this.tags.add(AERIAL);
    }

    @Override
    public AbstractCard makeCopy() {
        return new BackAir();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
        }
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        DamageInfo info = new DamageInfo(player, this.damage, this.damageTypeForTurn);
        DamageAction action = new DamageAction(monster, info, AbstractGameAction.AttackEffect.SMASH);
        AbstractDungeon.actionManager.addToBottom(action);
    }
}
