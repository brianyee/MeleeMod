package melee_mod.falcon.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import melee_mod.FalconCharacterMod;

import java.util.ArrayList;
import java.util.Arrays;

import static globals.Constants.Powers.POWER_SHIELDING;

public class PowerShieldingPower extends AbstractPower {
    private static final String POWER_ID = POWER_SHIELDING;
    private static final String NAME = "Power Shielding";
    public PowerShieldingPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(FalconCharacterMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = "Whenever you gain Block, deal " + this.amount + " damage to ALL enemies.";
    }

    @Override
    public void onGainedBlock(float blockAmount) {
        this.flash();
        this.addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
    }
}
