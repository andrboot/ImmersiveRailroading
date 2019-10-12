package cam72cam.immersiverailroading.items;

import java.util.List;

import cam72cam.immersiverailroading.ImmersiveRailroading;
import cam72cam.immersiverailroading.items.nbt.ItemGauge;
import cam72cam.immersiverailroading.library.GuiText;
import cam72cam.mod.item.ItemBase;
import cam72cam.mod.item.ItemStack;
import cam72cam.mod.util.CollectionUtil;

public class ItemCastRail extends ItemBase {
	public ItemCastRail() {
		super(ImmersiveRailroading.MODID, "item_cast_rail", 16, ItemTabs.MAIN_TAB);
	}
	
	@Override
    public List<String> getTooltip(ItemStack stack)
    {
        return CollectionUtil.listOf(GuiText.GAUGE_TOOLTIP.toString(ItemGauge.get(stack)));
    }
}
