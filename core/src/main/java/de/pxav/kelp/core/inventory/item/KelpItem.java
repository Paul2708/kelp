package de.pxav.kelp.core.inventory.item;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.material.MaterialVersionTemplate;
import de.pxav.kelp.core.inventory.version.ItemVersionTemplate;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpItem {

  private ItemVersionTemplate itemVersionTemplate;
  private ItemTagVersionTemplate itemTagVersionTemplate;

  public KelpItem(ItemVersionTemplate itemVersionTemplate,
                  ItemTagVersionTemplate itemTagVersionTemplate) {
    this.itemVersionTemplate = itemVersionTemplate;
    this.itemTagVersionTemplate = itemTagVersionTemplate;
  }

  private KelpMaterial material = KelpMaterial.STONE;
  private int amount = 1;

  private int slot;
  private Inventory parent;

  private String displayName = " ";
  private List<String> itemDescription = Lists.newArrayList();

  private Collection<ItemFlag> itemFlags = Lists.newArrayList();
  private Map<Enchantment, Integer> enchantments = Maps.newHashMap();

  private boolean glowing = false;
  private boolean unbreakable = false;

  private Map<String, String> nbtTagStrings = Maps.newHashMap();
  private Collection<String> tagsToRemove = Lists.newArrayList();

  public KelpItem material(KelpMaterial material) {
    this.material = material;
    return this;
  }

  public KelpItem amount(int amount) {
    this.amount = amount;
    return this;
  }

  public KelpItem slot(int slot) {
    this.slot = slot;
    return this;
  }

  public KelpItem displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  public KelpItem addTag(String key, String value) {
    this.nbtTagStrings.put(key, value);
    return this;
  }

  public KelpItem removeTag(String key) {
    this.tagsToRemove.add(key);
    return this;
  }

  public KelpItem itemDescription(List<String> description) {
    this.itemDescription = description;
    return this;
  }

  public KelpItem itemDescription(String description) {
    this.itemDescription = Collections.singletonList(description);
    return this;
  }

  public KelpItem itemDescription(String... description) {
    this.itemDescription = Lists.newArrayList(description);
    return this;
  }

  public KelpItem addItemDescription(String description) {
    this.itemDescription.add(description);
    return this;
  }

  public KelpItem addItemDescription(List<String> description) {
    this.itemDescription.addAll(description);
    return this;
  }

  public KelpItem addItemDescription(String... description) {
    this.itemDescription.addAll(Arrays.asList(description));
    return this;
  }

  public ItemStack getItemStack() {
    ItemStack itemStack = itemVersionTemplate.newItemStack(this.material);
    itemStack.setAmount(this.amount);

    if (this.displayName != null) {
      itemStack = itemVersionTemplate.setDisplayName(itemStack, displayName);
    }

    if (this.unbreakable) {
      itemStack = itemVersionTemplate.makeUnbreakable(itemStack);
    } else {
      itemStack = itemVersionTemplate.makeBreakable(itemStack);
    }

    for (Map.Entry<String, String> tagEntry : this.nbtTagStrings.entrySet()) {
      itemStack = itemTagVersionTemplate.tagItem(itemStack, tagEntry.getKey(), tagEntry.getValue());
    }

    for (String currentTag : this.tagsToRemove) {
      itemStack = itemTagVersionTemplate.removeTag(itemStack, currentTag);
    }

    return itemStack;
  }

  public Inventory getParent() {
    return this.parent;
  }

  public int getSlot() {
    return slot;
  }

}
