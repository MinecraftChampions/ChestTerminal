package me.qscbm.chestterminal;

import org.bstats.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import me.qscbm.chestterminal.items.AccessTerminal;
import me.qscbm.chestterminal.items.ExportBus;
import me.qscbm.chestterminal.items.ImportBus;
import me.qscbm.chestterminal.items.MilkyQuartz;
import me.qscbm.chestterminal.items.WirelessTerminal;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.updater.GitHubBuildsUpdater;
import io.github.thebusybiscuit.slimefun4.libraries.dough.updater.PluginUpdater;
import io.github.thebusybiscuit.slimefun4.libraries.dough.versions.PrefixedVersion;

public class Main extends JavaPlugin implements Listener, SlimefunAddon {
	
	@Override
	public void onEnable() {
	        getLogger().info("CT终端已启用");
		Config cfg = new Config(this);
		
		// Setting up bStats
		new Metrics(this, 15478);
		
		SlimefunItemStack milkyQuartz = new SlimefunItemStack("MILKY_QUARTZ", Material.QUARTZ, "&f钟乳石英");
		SlimefunItemStack ctPanel = new SlimefunItemStack("CT_PANEL", "7a44ff3a5f49c69cab676bad8d98a063fa78cfa61916fdef3e267557fec18283", "&3CT控制部件", "&7基础制作物品");
		
		SlimefunItemStack chestTerminal = new SlimefunItemStack("CHEST_TERMINAL", "7a44ff3a5f49c69cab676bad8d98a063fa78cfa61916fdef3e267557fec18283", "&3CT连接终端", "&7如果将此物品连接到货运网络", "&7那么将可以远程&3管理&7CT网络内任何物品");
		SlimefunItemStack importBus = new SlimefunItemStack("CT_IMPORT_BUS", "113db2e7e72ea4432eefbd6e58a85eaa2423f83e642ca41abc6a9317757b889", "&3CT输入终端", "&7如果将此物品连接到货运网络", "&7那么将可以远程&3输入&7CT货运网络内任何物品", "&7并将其放入到CT货运网络中");
		SlimefunItemStack exportBus = new SlimefunItemStack("CT_EXPORT_BUS", "113db2e7e72ea4432eefbd6e58a85eaa2423f83e642ca41abc6a9317757b889", "&3CT输出终端", "&7如果将此物品连接到货运网络", "&7那么将可以远程&3输出&7CT货运网络内任何物品", "&7并将其放入到储物方块中");
		
		SlimefunItemStack wirelessTerminal16 = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_16", Material.ITEM_FRAME, "&3CT无线连接终端 &b(16)", "&8\u21E8 &7连接到: &c无", "&8\u21E8 &7范围: &e16个方块", "&c&o&8\u21E8 &e\u26A1 &70 / 10 J", "", "&7如果将此物品连接到特定CT连接终端", "&7那么将可以远程&3访问&7CT网络内任何物品", "", "&7&e手持物品右键&7打开CT连接终端以连接");
		SlimefunItemStack wirelessTerminal64 = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_64", Material.ITEM_FRAME, "&3CT无线连接终端 &b(64)", "&8\u21E8 &7连接到: &c无", "&8\u21E8 &7范围: &e64个方块", "&c&o&8\u21E8 &e\u26A1 &70 / 25 J", "", "&7如果将此物品连接到特定CT连接终端", "&7那么将可以远程&3访问&7CT网络内任何物品", "", "&7&e手持物品右键&7打开CT连接终端以连接");
		SlimefunItemStack wirelessTerminal128 = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_128", Material.ITEM_FRAME, "&3CT无线连接终端 &b(128)", "&8\u21E8 &7连接到: &c无", "&8\u21E8 &7范围: &e128个方块", "&c&o&8\u21E8 &e\u26A1 &70 / 50 J", "", "&7如果将此物品连接到特定CT终端", "&7那么将可以远程&3访问&7CT网络内任何物品", "", "&7&e手持物品右键&7打开CT连接终端以连接");
		SlimefunItemStack wirelessTerminalTransdimensional = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_TRANSDIMENSIONAL", Material.ITEM_FRAME, "&3CT无线连接终端 &b(跨维度)", "&8\u21E8 &7连接到: &c无", "&8\u21E8 &7范围: &e无限", "&c&o&8\u21E8 &e\u26A1 &70 / 50 J", "", "&7如果将此物品连接到特定CT终端", "&7那么将可以远程&3访问&7CT网络内任何物品", "", "&7&e手持物品右键&7打开CT连接终端以连接");
		
		ItemGroup itemGroup = new ItemGroup(new NamespacedKey(this, "chest_terminal"), new CustomItemStack(chestTerminal, "&5Chest Terminal(CT货运)", "", "&a> 单击打开"));
		
		new SlimefunItem(itemGroup, milkyQuartz, RecipeType.GEO_MINER,
		new ItemStack[0])
		.register(this);
		
		new SlimefunItem(itemGroup, ctPanel, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.BLISTERING_INGOT_3, milkyQuartz, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.POWER_CRYSTAL, SlimefunItems.REDSTONE_ALLOY, milkyQuartz, SlimefunItems.BLISTERING_INGOT_3, milkyQuartz})
		.register(this);
		
		new AccessTerminal(itemGroup, chestTerminal, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER_3, milkyQuartz, SlimefunItems.POWER_CRYSTAL, ctPanel, SlimefunItems.POWER_CRYSTAL, SlimefunItems.PLASTIC_SHEET, SlimefunItems.ENERGY_REGULATOR, SlimefunItems.PLASTIC_SHEET})
		.register(this);
		
		new ImportBus(itemGroup, importBus, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {SlimefunItems.REDSTONE_ALLOY, SlimefunItems.POWER_CRYSTAL, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.CARGO_INPUT_NODE, SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.PLASTIC_SHEET, SlimefunItems.CARGO_MOTOR, SlimefunItems.PLASTIC_SHEET})
		.register(this);
		
		new ExportBus(itemGroup, exportBus, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {null, SlimefunItems.DAMASCUS_STEEL_INGOT, null, SlimefunItems.ALUMINUM_BRONZE_INGOT, importBus, SlimefunItems.ALUMINUM_BRONZE_INGOT, SlimefunItems.PLASTIC_SHEET, SlimefunItems.GOLD_10K, SlimefunItems.PLASTIC_SHEET})
		.register(this);
		
		new WirelessTerminal(itemGroup, wirelessTerminal16, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER, milkyQuartz, SlimefunItems.COBALT_INGOT, chestTerminal, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return 16;
			}

            @Override
			public float getMaxItemCharge(ItemStack item) {
			    return 10;
			}
			
		}.register(this);
		
		new WirelessTerminal(itemGroup, wirelessTerminal64, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER, milkyQuartz, SlimefunItems.COBALT_INGOT, wirelessTerminal16, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return 64;
			}

            @Override
            public float getMaxItemCharge(ItemStack item) {
                return 25;
            }
			
		}.register(this);
		
		new WirelessTerminal(itemGroup, wirelessTerminal128, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER_2, milkyQuartz, SlimefunItems.COBALT_INGOT, wirelessTerminal64, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return 128;
			}

            @Override
            public float getMaxItemCharge(ItemStack item) {
                return 50;
            }
			
		}.register(this);
		
		new WirelessTerminal(itemGroup, wirelessTerminalTransdimensional, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER_4, milkyQuartz, SlimefunItems.COBALT_INGOT, wirelessTerminal128, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return -1;
			}

            @Override
            public float getMaxItemCharge(ItemStack item) {
                return 50;
            }
			
		}.register(this);
		
		new MilkyQuartz(this, milkyQuartz).register();
	}

	@Override
	public JavaPlugin getJavaPlugin() {
		return this;
	}

	@Override
	public String getBugTrackerURL() {
		return "https://github.com/mcchampions/ChestTerminal/issues";
	}
}
