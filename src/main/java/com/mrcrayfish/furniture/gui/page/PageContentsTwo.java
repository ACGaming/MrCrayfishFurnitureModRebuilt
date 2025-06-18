package com.mrcrayfish.furniture.gui.page;

import com.mrcrayfish.furniture.gui.GuiRecipeBook;
import com.mrcrayfish.furniture.gui.RecipePage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

import java.util.List;

public class PageContentsTwo extends RecipePage
{
    private GuiButton buttonMineBay;
    private GuiButton buttonEnderShop;

    private GuiRecipeBook book;

    public PageContentsTwo(GuiRecipeBook book)
    {
        super("contents");
        this.book = book;
    }

    @Override
    public void init(List<GuiButton> buttonList)
    {
    	String buttonText = I18n.format("cfm.recipe_book.button.go");
        int leftPageCenter = (book.width) / 2 - (book.bookWidth / 2);
        this.buttonMineBay = new GuiButton(0, leftPageCenter + 25, 40, 35, 20, buttonText);
        this.buttonEnderShop = new GuiButton(0, leftPageCenter + 25, 64, 35, 20, buttonText);

        onClose();

        buttonList.add(this.buttonMineBay);
        buttonList.add(this.buttonEnderShop);
    }

    @Override
    public void draw(GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
    {
        int leftPageCenter = (book.width) / 2 - (book.bookWidth / 2);
        FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRenderer;
        fontRendererObj.drawString(I18n.format("cfm.recipe_book.page.minebay"), leftPageCenter - 55, 45, 16739840);
        fontRendererObj.drawString(I18n.format("cfm.recipe_book.page.endershop"), leftPageCenter - 55, 45 + 24, 16739840);
    }

    @Override
    public void handleButtonClick(GuiButton button)
    {
        if(button == buttonMineBay)
        {
            book.gotoPage("minebay");
        }
        else if(button == buttonEnderShop)
        {
            book.gotoPage("endershop");
        }
    }

    @Override
    public void drawOverlay(Minecraft mc, GuiRecipeBook gui, int x, int y, int mouseX, int mouseY, float partialTicks)
    {

    }

    @Override
    public void onShown()
    {
        this.buttonMineBay.visible = true;
        this.buttonEnderShop.visible = true;
    }

    @Override
    public void onClose()
    {
        this.buttonMineBay.visible = false;
        this.buttonEnderShop.visible = false;
    }

    @Override
    public String getTitle()
    {
        return I18n.format("cfm.recipe_book.page.contents");
    }
}
