package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import io.github.vampirestudios.artifice.api.ArtificeResourcePack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StonecuttingPieceRecipe extends PieceRecipe {
	private PieceIngredient input;

	public StonecuttingPieceRecipe(PieceType output, int count, PieceIngredient input) {
		super(output, count);
		this.input = input;
	}

	public StonecuttingPieceRecipe(PieceType output, int count, PieceType input) {
		this(output, count, new PieceIngredient(input));
	}
	
	public StonecuttingPieceRecipe(PieceType output, int count, ItemConvertible input) {
		this(output, count, new PieceIngredient(input));
	}
	
	public StonecuttingPieceRecipe(PieceType output, int count, TagKey input) {
		this(output, count, new PieceIngredient(input));
	}

	public PieceIngredient getInput() {
		return input;
	}

	public void add(ArtificeResourcePack.ServerResourcePackBuilder data, Identifier id, PieceSet set) {
		data.addStonecuttingRecipe(id, recipe -> {
			recipe.result(Registry.BLOCK.getId(getOutput(set)));
			recipe.group(Registry.BLOCK.getId(getOutput(set)));
			recipe.count(getCount());
			PieceIngredient pi = getInput();
			if(pi.isTag()) recipe.ingredientTag(pi.getId(set));
			else recipe.ingredientItem(pi.getId(set));
		});
	}

	@Override
	public boolean canAddForSet(PieceSet set) {
		return input.hasIngredientInSet(set);
	}
}
