package com.untamedears.itemexchange.rules.modifiers;

import static vg.civcraft.mc.civmodcore.util.NullCoalescing.chain;

import co.aikar.commands.annotation.CommandAlias;
import com.google.common.base.Strings;
import com.untamedears.itemexchange.commands.SetCommand;
import com.untamedears.itemexchange.rules.interfaces.Modifier;
import com.untamedears.itemexchange.rules.interfaces.ModifierData;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BookMeta.Generation;
import vg.civcraft.mc.civmodcore.serialization.NBTCompound;
import vg.civcraft.mc.civmodcore.serialization.NBTSerializationException;
import vg.civcraft.mc.civmodcore.util.EnumUtils;
import vg.civcraft.mc.civmodcore.util.Iteration;

@CommandAlias(SetCommand.ALIAS)
@Modifier(slug = "BOOK", order = 100)
public final class BookModifier extends ModifierData<BookModifier> {

	private static final String TITLE_KEY = "title";

	private static final String AUTHOR_KEY = "author";

	private static final String GENERATION_KEY = "generation";

	private static final String HAS_PAGES_KEY = "hasPages";

	private static final String BOOK_HASH_KEY = "bookHash";

	private String title;

	private String author;

	private Generation generation;

	private boolean hasPages;

	private int bookHash;

	@Override
	public BookModifier construct() {
		return new BookModifier();
	}

	@Override
	public BookModifier construct(ItemStack item) {
		BookMeta meta = chain(() -> (BookMeta) item.getItemMeta());
		if (meta == null) {
			return null;
		}
		BookModifier modifier = new BookModifier();
		if (meta.hasTitle()) {
			modifier.setTitle(meta.getTitle());
		}
		if (meta.hasAuthor()) {
			modifier.setAuthor(meta.getAuthor());
		}
		if (meta.hasGeneration()) {
			modifier.setGeneration(Objects.requireNonNull(meta.getGeneration()));
		}
		if (meta.hasPages()) {
			modifier.setHasPages(true);
			modifier.setBookHash(bookHash(meta.getPages()));
		}
		return modifier;
	}

	@Override
	public boolean isBroken() {
		return false;
	}

	@Override
	public boolean conforms(ItemStack item) {
		BookMeta meta = chain(() -> (BookMeta) item.getItemMeta());
		if (meta == null) {
			return false;
		}
		if (meta.hasTitle() != hasTitle()) {
			return false;
		}
		if (meta.hasAuthor() != hasAuthor()) {
			return false;
		}
		if (hasGeneration()) {
			if (meta.getGeneration() != getGeneration()) {
				return false;
			}
		}
		if (meta.hasPages() != hasPages()) {
			return false;
		}
		if (meta.hasPages()) {
			if (bookHash(meta.getPages()) != getBookHash()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void serialize(NBTCompound nbt) throws NBTSerializationException {
		nbt.setString(TITLE_KEY, this.title);
		nbt.setString(AUTHOR_KEY, this.author);
		nbt.setString(GENERATION_KEY, EnumUtils.getSlug(this.generation));
		nbt.setBoolean(HAS_PAGES_KEY, this.hasPages);
		nbt.setInteger(BOOK_HASH_KEY, this.bookHash);
	}

	@Override
	public void deserialize(NBTCompound nbt) throws NBTSerializationException {
		this.title = nbt.getString(TITLE_KEY);
		this.author = nbt.getString(AUTHOR_KEY);
		this.generation = EnumUtils.fromSlug(Generation.class, nbt.getString(GENERATION_KEY), false);
		this.hasPages = nbt.getBoolean(HAS_PAGES_KEY);
		this.bookHash = nbt.getInteger(BOOK_HASH_KEY);
	}

	@Override
	public String getDisplayedListing() {
		if (Strings.isNullOrEmpty(this.title)) {
			return null;
		}
		return title;
	}

	@Override
	public List<String> getDisplayedInfo() {
		List<String> lines = new ArrayList<>();
		lines.add(ChatColor.DARK_AQUA + "Author: " + ChatColor.GRAY + (hasAuthor() ? getAuthor() : ""));
		if (hasGeneration()) {
			lines.add(ChatColor.DARK_AQUA + "Generation: " + ChatColor.GRAY + getGeneration().name());
		}
		return lines;
	}

	// ------------------------------------------------------------
	// Getters + Setters
	// ------------------------------------------------------------

	public boolean hasTitle() {
		return !Strings.isNullOrEmpty(this.title);
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean hasAuthor() {
		return !Strings.isNullOrEmpty(this.title);
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean hasGeneration() {
		return this.generation != null;
	}

	public Generation getGeneration() {
		return this.generation;
	}

	public void setGeneration(Generation generation) {
		this.generation = generation;
	}

	public boolean hasPages() {
		return this.hasPages;
	}

	public void setHasPages(boolean hasPages) {
		this.hasPages = hasPages;
	}

	public int getBookHash() {
		return this.bookHash;
	}

	public void setBookHash(int bookHash) {
		this.bookHash = bookHash;
	}

	private static int bookHash(List<String> pages) {
		if (Iteration.isNullOrEmpty(pages)) {
			return 0;
		}
		return String.join("/r", pages).hashCode();
	}

}
