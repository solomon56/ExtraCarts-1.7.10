package com.dta.extracarts.mods.enderio.gui;

import com.dta.extracarts.mods.enderio.container.ContainerCapacitorBankCart;
import com.dta.extracarts.mods.enderio.entities.EntityCapacitorBankCart;
import crazypants.enderio.gui.TextFieldEIO;
import crazypants.enderio.machine.power.PowerDisplayUtil;
import crazypants.gui.GuiContainerBase;
import crazypants.gui.GuiToolTip;
import crazypants.render.RenderUtil;
import crazypants.util.Lang;
import crazypants.vecmath.VecmathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

/**
 * Created by Skylar on 4/11/2015.
 */
public class GuiCapacitorBankCart extends GuiContainerBase {

	private static final int POWER_X = 11 + 18;
	private static final int POWER_Y = 9;
	private static final int POWER_WIDTH = 10;
	private static final int POWER_HEIGHT = 68;
	protected static final int BOTTOM_POWER_Y = POWER_Y + POWER_HEIGHT;

	private EntityCapacitorBankCart entityCapacitorBankCart;

	private int inputX = 78 + 24;
	private int inputY = 18;

	private int outputX = 78 + 24;
	private int outputY = 36;

	private int rightMargin = 8 + 24;

	private TextFieldEIO maxInputTF;
	private TextFieldEIO maxOutputTF;

	//private final ContainerCapacitorBankCart containerCapacitorBankCart;

	public GuiCapacitorBankCart(EntityPlayer player, final EntityCapacitorBankCart entityCapacitorBankCart) {
		super(new ContainerCapacitorBankCart(player, player.inventory, entityCapacitorBankCart));
		this.entityCapacitorBankCart = entityCapacitorBankCart;
		//containerCapacitorBankCart = (ContainerCapacitorBankCart) inventorySlots;

		xSize = 176 + 42;

		addToolTip(new GuiToolTip(new Rectangle(5, POWER_Y, POWER_WIDTH, POWER_HEIGHT), "") {

			@Override
			protected void updateText() {
				text.clear();
				text.add(PowerDisplayUtil.formatPower(entityCapacitorBankCart.getEnergyStored()) + " " + PowerDisplayUtil.ofStr());
				text.add(EnumChatFormatting.WHITE + PowerDisplayUtil.formatPower(entityCapacitorBankCart.getMaxEnergyStored()) + " " + EnumChatFormatting.GRAY
						+ PowerDisplayUtil.abrevation());
				/*
				float change = network.getAverageChangePerTick();
				String color = EnumChatFormatting.WHITE.toString();
				if(change > 0) {
					color = EnumChatFormatting.GREEN.toString() + "+";
				} else if(change < 0) {
					color = EnumChatFormatting.RED.toString();
				}
				text.add(String.format("%s%s%s" + PowerDisplayUtil.abrevation() + PowerDisplayUtil.perTickStr(), color,
						PowerDisplayUtil.formatPower(Math.round(change)), " "
								+ EnumChatFormatting.GRAY.toString()));
				 */
			}
		});

		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

		int x = inputX - 24;
		int y = inputY;
		maxInputTF = new TextFieldEIO(fontRenderer, x, y, 68, 16);
		maxInputTF.setMaxStringLength(10);
		maxInputTF.setCharFilter(TextFieldEIO.FILTER_NUMERIC);

		x = outputX - 24;
		y = outputY;
		maxOutputTF = new TextFieldEIO(fontRenderer, x, y, 68, 16);
		maxOutputTF.setMaxStringLength(10);
		maxOutputTF.setCharFilter(TextFieldEIO.FILTER_NUMERIC);

		textFields.add(maxInputTF);
		textFields.add(maxOutputTF);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderUtil.bindTexture("enderio:textures/gui/capacitorBank.png");
		int sx = (width - xSize) / 2;
		int sy = (height - ySize) / 2;

		drawTexturedModalRect(sx, sy, 0, 0, xSize - 21, ySize);

		int i1 = getEnergyStoredScaled(POWER_HEIGHT);
		drawTexturedModalRect(sx + POWER_X, sy + BOTTOM_POWER_Y - i1, 176 + 21, 0, POWER_WIDTH, i1);

		for (int i = 0; i < buttonList.size(); ++i) {
			GuiButton guibutton = (GuiButton) buttonList.get(i);
			guibutton.drawButton(mc, 0, 0);
		}

		int midX = sx + xSize / 2;

		String str = Lang.localize("gui.capBank.maxIo") + " " + PowerDisplayUtil.formatPower(entityCapacitorBankCart.getMaxIO()) +
				" " + PowerDisplayUtil.abrevation() + PowerDisplayUtil.perTickStr();
		FontRenderer fontRenderer = getFontRenderer();
		int swid = fontRenderer.getStringWidth(str);
		int x = midX - swid / 2;
		int y = guiTop + 5;

		drawString(fontRenderer, str, x, y, -1);

		str = Lang.localize("gui.capBank.maxInput") + ":";
		swid = fontRenderer.getStringWidth(str);
		x = guiLeft + inputX - swid - 3;
		y = guiTop + inputY + 2;
		drawString(fontRenderer, str, x, y, -1);

		str = Lang.localize("gui.capBank.maxOutput") + ":";
		swid = fontRenderer.getStringWidth(str);
		x = guiLeft + outputX - swid - 3;
		y = guiTop + outputY + 2;
		drawString(fontRenderer, str, x, y, -1);

		super.drawGuiContainerBackgroundLayer(par1, par2, par3);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		super.drawHoveringText(par1List, par2 + 24, par3, font);
		GL11.glPopAttrib();
		GL11.glPopAttrib();
	}

	@Override
	public int getGuiLeft() {
		return guiLeft + 24;
	}

	@Override
	public int getGuiTop() {
		return guiTop;
	}

	@Override
	public int getXSize() {
		return xSize - 42;
	}

	@Override
	public int getOverlayOffsetX() {
		return 21;
	}

	@Override
	public FontRenderer getFontRenderer() {
		return Minecraft.getMinecraft().fontRenderer;
	}

	private int getEnergyStoredScaled(int scale) {
		return (int) VecmathUtil.clamp(Math.round(scale * entityCapacitorBankCart.getEnergyStoredRatio()), 0, scale);
	}

	private void updateFieldsFromState() {
		maxInputTF.setText(PowerDisplayUtil.formatPower(entityCapacitorBankCart.getMaxInput()));
		maxOutputTF.setText(PowerDisplayUtil.formatPower(entityCapacitorBankCart.getMaxOutput()));
	}

}