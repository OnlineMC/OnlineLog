package cn.onlinemc.onlinelog;

import java.util.Timer;

import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin {

	Timer timer = new Timer();
	@Override
	public void onEnable() {
		saveDefaultConfig();
		Config.inid(this);
		timer.schedule(new TimeCycle(this), 0, Config.time);

	}
	
	@Override
	public void onDisable() {
		timer.cancel();
	}
	
}
